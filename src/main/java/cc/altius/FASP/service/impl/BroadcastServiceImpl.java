/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.BroadcastDao;
import cc.altius.FASP.model.BroadcastMessage;
import cc.altius.FASP.model.BroadcastMessageInput;
import cc.altius.FASP.model.BroadcastRecipient;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.EmailCategory;
import cc.altius.FASP.model.Emailer;
import cc.altius.FASP.model.UserNotificationPreference;
import cc.altius.FASP.service.BroadcastService;
import cc.altius.FASP.service.EmailService;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 *
 * @author altius
 */
@Service
public class BroadcastServiceImpl implements BroadcastService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final int BCC_BATCH_SIZE = 200;
    private static final String SUPPORT_EMAIL = "support@quantificationanalytics.org";
    private static final String QMT_EMAIL = "QMT@Quantificationanalytics.org";
    private static final String HELPDESK_PORTAL_URL = "https://qathelpdesk.atlassian.net/servicedesk/customer/portals";
    private static final String DEFAULT_NOREPLY = "QAT_noreply@quantificationanalytics.org";

    @Autowired
    private BroadcastDao broadcastDao;

    @Autowired
    private EmailService emailService;

    @Value("#{credentials['email.username']}")
    private String emailUsername;

    @Value("${qat.urlHost:https://qat.quantificationanalytics.org}")
    private String hostUrl;

    @Override
    public List<EmailCategory> getEmailCategoryList() {
        return this.broadcastDao.getEmailCategoryList();
    }

    @Override
    public List<UserNotificationPreference> getNotificationPreferences(CustomUserDetails curUser) {
        return this.broadcastDao.getNotificationPreferencesByUserId(curUser.getUserId());
    }

    @Override
    public List<UserNotificationPreference> getNotificationPreferencesByUserId(int userId) {
        return this.broadcastDao.getNotificationPreferencesByUserId(userId);
    }

    @Override
    public int saveNotificationPreferences(List<UserNotificationPreference> preferences, CustomUserDetails curUser) {
        return this.broadcastDao.saveNotificationPreferences(curUser.getUserId(), preferences);
    }

    @Override
    public int getRecipientCount(int categoryId, List<String> businessFunctionIds, List<Integer> languageIds) {
        return getRecipientCount(categoryId, null, businessFunctionIds, languageIds);
    }

    @Override
    public int getRecipientCount(int categoryId, List<String> targetRoles, List<String> businessFunctionIds, List<Integer> languageIds) {
        List<BroadcastRecipient> recipients = this.broadcastDao.getBroadcastRecipients(categoryId, targetRoles, businessFunctionIds, languageIds);
        return recipients != null ? recipients.size() : 0;
    }

    @Override
    public List<BroadcastRecipient> getBroadcastRecipients(int categoryId, List<String> targetRoles, List<String> businessFunctionIds, List<Integer> languageIds) {
        return this.broadcastDao.getBroadcastRecipients(categoryId, targetRoles, businessFunctionIds, languageIds);
    }

    @Override
    public int sendBroadcast(BroadcastMessageInput input, CustomUserDetails curUser) {
        if (input == null || input.getSubject() == null || input.getSubject().trim().isEmpty() || input.getBodyHtml() == null) {
            return 0;
        }

        EmailCategory category = this.broadcastDao.getEmailCategoryById(input.getCategoryId());
        if (category == null) {
            logger.warn("Invalid broadcast category: {}", input.getCategoryId());
            return 0;
        }

        List<BroadcastRecipient> recipients = this.broadcastDao.getBroadcastRecipients(
                input.getCategoryId(),
                input.getTargetRoles(),
                input.getTargetBusinessFunctions(),
                input.getTargetLanguages()
        );

        if (recipients == null || recipients.isEmpty()) {
            logger.info("No recipients found matching broadcast criteria.");
            return 0;
        }

        BroadcastMessage bm = new BroadcastMessage();
        bm.setCategoryId(input.getCategoryId());
        bm.setSubject(input.getSubject());
        bm.setBodyHtml(input.getBodyHtml());
        bm.setTargetRoles(input.getTargetRoles());
        bm.setTargetBusinessFunctions(input.getTargetBusinessFunctions());
        bm.setTargetLanguages(input.getTargetLanguages());
        bm.setTotalRecipients(recipients.size());
        bm.setStatus("QUEUED");

        int broadcastId = input.getBroadcastId();
        if (broadcastId > 0) {
            bm.setBroadcastId(broadcastId);
            this.broadcastDao.updateBroadcastDraft(bm, curUser);
        } else {
            broadcastId = this.broadcastDao.saveBroadcastMessage(bm, curUser);
            bm.setBroadcastId(broadcastId);
        }

        // Separate email addresses
        List<String> emailList = recipients.stream()
                .map(r -> r.getEmailId().trim())
                .filter(e -> !e.isEmpty())
                .distinct()
                .collect(Collectors.toList());

        int totalBatches = (int) Math.ceil((double) emailList.size() / BCC_BATCH_SIZE);
        String fromAddress = (this.emailUsername != null && !this.emailUsername.trim().isEmpty()) ? this.emailUsername : DEFAULT_NOREPLY;

        String baseUrl = getBaseUrl();
        String formattedHtml = buildEmailHtml(input.getBodyHtml(), category, baseUrl);

        String categoryPrefix = (category.getCategoryId() == 2 || "COMMUNITY".equalsIgnoreCase(category.getCategoryCode()))
                ? "[QAT Community Update]"
                : "[QAT System Update]";

        String finalSubject = input.getSubject().trim();
        if (!finalSubject.toLowerCase().startsWith(categoryPrefix.toLowerCase())) {
            finalSubject = categoryPrefix + " " + finalSubject;
        }

        List<Emailer> emailerBatches = new ArrayList<>();
        for (int i = 0; i < emailList.size(); i += BCC_BATCH_SIZE) {
            List<String> batch = emailList.subList(i, Math.min(i + BCC_BATCH_SIZE, emailList.size()));
            String bccString = String.join(",", batch);

            Emailer emailer = new Emailer();
            emailer.setToSend(fromAddress);
            emailer.setBccToSend(bccString);
            emailer.setSubject(finalSubject);
            emailer.setBody(formattedHtml);
            emailer.setAttempts(0);
            emailer.setStatus(0);

            try {
                int emailerId = this.emailService.saveEmail(emailer);
                emailer.setEmailerId(emailerId);
                emailerBatches.add(emailer);
                logger.info("Broadcast batch {}/{} (Email ID {}) recorded in em_emailer.", emailerBatches.size(), totalBatches, emailerId);
            } catch (Exception e) {
                logger.error("Error saving broadcast batch {}/{}", (i / BCC_BATCH_SIZE) + 1, totalBatches, e);
            }
        }

        final int finalBroadcastId = broadcastId;
        final int totalRecipients = emailList.size();
        final int totalBatchesCount = emailerBatches.size();

        this.broadcastDao.updateBroadcastStatus(broadcastId, "QUEUED", totalRecipients, totalBatchesCount);

        if (!emailerBatches.isEmpty()) {
            CompletableFuture.runAsync(() -> {
                int successfulBatches = 0;
                for (int b = 0; b < emailerBatches.size(); b++) {
                    Emailer batchEmailer = emailerBatches.get(b);
                    try {
                        logger.info("Sending broadcast batch {}/{} (Email ID {}) for broadcastId {}",
                                (b + 1), totalBatchesCount, batchEmailer.getEmailerId(), finalBroadcastId);
                        int result = this.emailService.sendMail(batchEmailer);
                        if (result == 1) {
                            successfulBatches++;
                        } else {
                            logger.warn("sendMail returned {} for broadcast batch {}/{} (Email ID {})",
                                    result, (b + 1), totalBatchesCount, batchEmailer.getEmailerId());
                        }
                    } catch (Exception ex) {
                        logger.error("Exception sending broadcast batch {}/{} (Email ID {}) for broadcastId {}",
                                (b + 1), totalBatchesCount, batchEmailer.getEmailerId(), finalBroadcastId, ex);
                    }
                }
                String finalStatus = (successfulBatches == totalBatchesCount) ? "COMPLETED" : (successfulBatches > 0 ? "PARTIAL" : "FAILED");
                try {
                    this.broadcastDao.updateBroadcastStatus(finalBroadcastId, finalStatus, totalRecipients, successfulBatches);
                    logger.info("Broadcast {} background delivery finished with status: {} ({}/{} batches succeeded)",
                            finalBroadcastId, finalStatus, successfulBatches, totalBatchesCount);
                } catch (Exception ex) {
                    logger.error("Failed to update final status for broadcast {}", finalBroadcastId, ex);
                }
            });
        }

        return broadcastId;
    }

    private String getBaseUrl() {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null && attributes.getRequest() != null) {
                HttpServletRequest request = attributes.getRequest();
                String scheme = request.getHeader("X-Forwarded-Proto");
                if (scheme == null || scheme.isEmpty()) {
                    scheme = request.getScheme();
                }
                String host = request.getHeader("X-Forwarded-Host");
                if (host == null || host.isEmpty()) {
                    host = request.getHeader("Host");
                }
                if (host == null || host.isEmpty()) {
                    host = request.getServerName() + (request.getServerPort() == 80 || request.getServerPort() == 443 ? "" : ":" + request.getServerPort());
                }
                return scheme + "://" + host;
            }
        } catch (Exception e) {
            logger.warn("Could not determine dynamic baseUrl, falling back to configured hostUrl", e);
        }
        return this.hostUrl != null ? this.hostUrl.replaceAll("/#.*$", "").replaceAll("/+$", "") : "https://qat.quantificationanalytics.org";
    }

    private String buildEmailHtml(String rawBody, EmailCategory category, String baseUrl) {
        String cleanBase = baseUrl != null ? baseUrl.replaceAll("/#.*$", "").replaceAll("/+$", "") : "https://qat.quantificationanalytics.org";
        String unsubUrl = cleanBase + "/#/unsubscribe";

        boolean isCommunity = (category != null && (category.getCategoryId() == 2 || "COMMUNITY".equalsIgnoreCase(category.getCategoryCode()) || category.isAllowOptOut()));
        String headerTitle = isCommunity ? "QAT Community Update" : "QAT System Update";

        StringBuilder footerSb = new StringBuilder();
        footerSb.append("<p style=\"margin: 0 0 10px 0;\">")
                .append("This is an automated broadcast message. For questions or support, submit a ")
                .append("<a href=\"").append(HELPDESK_PORTAL_URL).append("\" style=\"color: #0066cc; text-decoration: none;\">QAT helpdesk ticket</a>")
                .append(" or email the QAT Management Team at ")
                .append("<a href=\"mailto:").append(QMT_EMAIL).append("\" style=\"color: #0066cc; text-decoration: none;\">").append(QMT_EMAIL).append("</a>. ")
                .append("Do NOT reply to this message, as this inbox is unmonitored.")
                .append("</p>");

        if (isCommunity) {
            footerSb.append("<p style=\"margin: 0; color: #64748b;\">")
                    .append("You're receiving this QAT community update based on your notification settings. To change them, go to your User Profile or ")
                    .append("<a href=\"").append(unsubUrl).append("\" style=\"color: #0066cc; text-decoration: none;\">update email preferences here</a>.")
                    .append("</p>");
        } else {
            footerSb.append("<p style=\"margin: 0; color: #64748b;\">")
                    .append("You're receiving this mandatory system announcement because you have a registered QAT account. These announcements can't be turned off.")
                    .append("</p>");
        }

        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html><html><head><meta charset='UTF-8'>");
        html.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
        html.append("<!--[if mso]><style type=\"text/css\">body, table, td {font-family: Arial, Helvetica, sans-serif !important;}</style><![endif]-->");
        html.append("<style>");
        html.append("body { font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif; line-height: 1.6; color: #333333; margin: 0; padding: 0; background-color: #f7f9fa; }");
        html.append(".main-table { width: 100%; max-width: 680px; margin: 20px auto; background-color: #ffffff; border: 1px solid #e2e8f0; border-radius: 8px; border-collapse: separate; }");
        html.append(".header-cell { background-color: #002f6c; color: #ffffff; padding: 20px 28px; }");
        html.append(".content-cell { padding: 28px; font-size: 15px; color: #333333; line-height: 1.6; }");
        html.append(".footer-cell { background-color: #f8fafc; border-top: 1px solid #e2e8f0; padding: 20px 28px; font-size: 12px; color: #64748b; line-height: 1.5; }");
        html.append(".footer-cell a { color: #0066cc; text-decoration: none; }");
        html.append(".footer-cell a:hover { text-decoration: underline; }");
        html.append("</style></head><body style=\"margin: 0; padding: 0; background-color: #f7f9fa;\">");
        html.append("<!--[if (gte mso 9)|(IE)]>");
        html.append("<table width=\"680\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:680px; margin:20px auto;\"><tr><td>");
        html.append("<![endif]-->");
        html.append("<table class=\"main-table\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width: 100%; max-width: 680px; margin: 20px auto; background-color: #ffffff; border: 1px solid #e2e8f0; border-radius: 8px; border-collapse: separate;\">");
        html.append("<tr>");
        html.append("<td class=\"header-cell\" bgcolor=\"#002f6c\" style=\"background-color: #002f6c; color: #ffffff; padding: 20px 28px; border-top-left-radius: 8px; border-top-right-radius: 8px;\">");
        html.append("<h2 style=\"margin: 0; font-size: 20px; font-weight: 600; color: #ffffff; letter-spacing: 0.5px;\">").append(headerTitle).append("</h2>");
        html.append("</td>");
        html.append("</tr>");
        html.append("<tr>");
        html.append("<td class=\"content-cell\" style=\"padding: 28px; font-size: 15px; color: #333333; line-height: 1.6;\">");
        html.append(rawBody);
        html.append("</td>");
        html.append("</tr>");
        html.append("<tr>");
        html.append("<td class=\"footer-cell\" bgcolor=\"#f8fafc\" style=\"background-color: #f8fafc; border-top: 1px solid #e2e8f0; padding: 20px 28px; font-size: 12px; color: #64748b; line-height: 1.5; border-bottom-left-radius: 8px; border-bottom-right-radius: 8px;\">");
        html.append(footerSb.toString());
        html.append("</td>");
        html.append("</tr>");
        html.append("</table>");
        html.append("<!--[if (gte mso 9)|(IE)]>");
        html.append("</td></tr></table>");
        html.append("<![endif]-->");
        html.append("</body></html>");
        return html.toString();
    }

    @Override
    public int saveDraft(BroadcastMessageInput input, CustomUserDetails curUser) {
        if (input == null) {
            return 0;
        }
        List<BroadcastRecipient> recipients = this.broadcastDao.getBroadcastRecipients(
                input.getCategoryId(),
                input.getTargetRoles(),
                input.getTargetBusinessFunctions(),
                input.getTargetLanguages()
        );
        int recipientCount = recipients != null ? recipients.size() : 0;

        BroadcastMessage bm = new BroadcastMessage();
        bm.setCategoryId(input.getCategoryId());
        bm.setSubject(input.getSubject() != null ? input.getSubject().trim() : "");
        bm.setBodyHtml(input.getBodyHtml() != null ? input.getBodyHtml() : "");
        bm.setTargetRoles(input.getTargetRoles());
        bm.setTargetBusinessFunctions(input.getTargetBusinessFunctions());
        bm.setTargetLanguages(input.getTargetLanguages());
        bm.setTotalRecipients(recipientCount);
        bm.setStatus("DRAFT");

        if (input.getBroadcastId() > 0) {
            bm.setBroadcastId(input.getBroadcastId());
            this.broadcastDao.updateBroadcastDraft(bm, curUser);
            return input.getBroadcastId();
        } else {
            return this.broadcastDao.saveBroadcastMessage(bm, curUser);
        }
    }

    @Override
    public BroadcastMessage getBroadcastMessageById(int broadcastId) {
        return this.broadcastDao.getBroadcastMessageById(broadcastId);
    }

    @Override
    public List<BroadcastRecipient> getRecipientsByBroadcastId(int broadcastId) {
        return this.broadcastDao.getRecipientsByBroadcastId(broadcastId);
    }

    @Override
    public List<BroadcastMessage> getBroadcastHistory() {
        return this.broadcastDao.getBroadcastHistory();
    }

    @Override
    public int unsubscribeByToken(String token, int categoryId) {
        return this.broadcastDao.unsubscribeByToken(token, categoryId);
    }

    @Override
    public List<UserNotificationPreference> getNotificationPreferencesByToken(String token) {
        Integer userId = this.broadcastDao.getUserIdByUnsubscribeToken(token);
        if (userId == null) {
            return new ArrayList<>();
        }
        return this.broadcastDao.getNotificationPreferencesByUserId(userId);
    }

    @Override
    public int updatePreferencesByToken(String token, List<UserNotificationPreference> preferences) {
        Integer userId = this.broadcastDao.getUserIdByUnsubscribeToken(token);
        if (userId == null) {
            return 0;
        }
        return this.broadcastDao.saveNotificationPreferences(userId, preferences);
    }

}
