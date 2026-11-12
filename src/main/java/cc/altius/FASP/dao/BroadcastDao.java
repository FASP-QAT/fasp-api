/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao;

import cc.altius.FASP.model.BroadcastMessage;
import cc.altius.FASP.model.BroadcastRecipient;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.EmailCategory;
import cc.altius.FASP.model.UserNotificationPreference;
import java.util.List;

/**
 *
 * @author altius
 */
public interface BroadcastDao {

    public List<EmailCategory> getEmailCategoryList();

    public EmailCategory getEmailCategoryById(int categoryId);

    public List<UserNotificationPreference> getNotificationPreferencesByUserId(int userId);

    public int saveNotificationPreferences(int userId, List<UserNotificationPreference> preferences);

    public List<BroadcastRecipient> getBroadcastRecipients(int categoryId, List<String> targetRoles, List<String> businessFunctionIds, List<Integer> languageIds);

    public List<BroadcastRecipient> getBroadcastRecipients(int categoryId, List<String> businessFunctionIds, List<Integer> languageIds);

    public int saveBroadcastMessage(BroadcastMessage broadcastMessage, CustomUserDetails curUser);

    public int updateBroadcastDraft(BroadcastMessage broadcastMessage, CustomUserDetails curUser);

    public BroadcastMessage getBroadcastMessageById(int broadcastId);

    public List<BroadcastRecipient> getRecipientsByBroadcastId(int broadcastId);

    public int updateBroadcastStatus(int broadcastId, String status, int totalRecipients, int batchCount);

    public List<BroadcastMessage> getBroadcastHistory();

    public String getOrCreateUnsubscribeToken(int userId);

    public Integer getUserIdByUnsubscribeToken(String token);

    public int unsubscribeByToken(String token, int categoryId);

}
