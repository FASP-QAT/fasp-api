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
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 * @author altius
 */
@Service
public class BroadcastServiceImpl implements BroadcastService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final int BCC_BATCH_SIZE = 200;
    private static final String SUPPORT_EMAIL = "support@quantificationanalytics.org";
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

        String formattedHtml = buildEmailHtml(input.getBodyHtml(), category);

        int sentBatches = 0;
        for (int i = 0; i < emailList.size(); i += BCC_BATCH_SIZE) {
            List<String> batch = emailList.subList(i, Math.min(i + BCC_BATCH_SIZE, emailList.size()));
            String bccString = String.join(",", batch);

            Emailer emailer = new Emailer();
            emailer.setToSend(fromAddress);
            emailer.setBccToSend(bccString);
            emailer.setSubject("[QAT Announcement] " + input.getSubject());
            emailer.setBody(formattedHtml);
            emailer.setAttempts(0);
            emailer.setStatus(0);

            try {
                // Insert into em_emailer table for the email scheduler to pick up and send
                int emailerId = this.emailService.saveEmail(emailer);
                emailer.setEmailerId(emailerId);
                sentBatches++;
                logger.info("Broadcast batch {}/{} (Email ID {}) queued in em_emailer for scheduler delivery.", sentBatches, totalBatches, emailerId);
            } catch (Exception e) {
                logger.error("Error queueing broadcast batch {}/{}", (i / BCC_BATCH_SIZE) + 1, totalBatches, e);
            }
        }

        this.broadcastDao.updateBroadcastStatus(broadcastId, "QUEUED", emailList.size(), sentBatches);
        return broadcastId;
    }

    private String buildEmailHtml(String rawBody, EmailCategory category) {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html><html><head><meta charset='UTF-8'>");
        html.append("<style>");
        html.append("body { font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif; line-height: 1.6; color: #333333; margin: 0; padding: 0; background-color: #f7f9fa; }");
        html.append(".container { max-width: 680px; margin: 20px auto; background: #ffffff; border: 1px solid #e2e8f0; border-radius: 8px; overflow: hidden; }");
        html.append(".header { background: #002f6c; color: #ffffff; padding: 20px 28px; }");
        html.append(".header h2 { margin: 0; font-size: 20px; font-weight: 600; letter-spacing: 0.5px; }");
        html.append(".content { padding: 28px; font-size: 15px; }");
        html.append(".footer { background: #f8fafc; border-top: 1px solid #e2e8f0; padding: 20px 28px; font-size: 12px; color: #64748b; }");
        html.append(".footer a { color: #0066cc; text-decoration: none; }");
        html.append(".footer a:hover { text-decoration: underline; }");
        html.append("</style></head><body>");
        html.append("<div class='container'>");
        html.append("<div class='header'><h2>Quantification Analytics Tool (QAT)</h2></div>");
        html.append("<div class='content'>");
        html.append(rawBody);
        html.append("</div>");
        html.append("<div class='footer'>");
        html.append("<p style='margin: 0 0 8px 0;'><strong>This is a broadcast message.</strong> Users cannot reply directly to this email. For questions or support, please email <a href='mailto:").append(SUPPORT_EMAIL).append("'>").append(SUPPORT_EMAIL).append("</a>.</p>");

        if (category.isAllowOptOut()) {
            String unsubUrl = this.hostUrl + "/#/unsubscribe";
            html.append("<p style='margin: 0; color: #94a3b8;'>You received this email according to your QAT notification settings. You can manage your preferences inside QAT under your User Profile or <a href='").append(unsubUrl).append("'>update email preferences here</a>.</p>");
        } else {
            html.append("<p style='margin: 0; color: #94a3b8;'>This is a mandatory system/administrative announcement.</p>");
        }

        html.append("</div></div></body></html>");
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
