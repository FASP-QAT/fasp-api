/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service;

import cc.altius.FASP.model.BroadcastMessage;
import cc.altius.FASP.model.BroadcastMessageInput;
import cc.altius.FASP.model.BroadcastRecipient;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.EmailCategory;
import cc.altius.FASP.model.UserNotificationPreference;
import java.util.List;

/**
 *
 * @author altius
 */
public interface BroadcastService {

    public List<EmailCategory> getEmailCategoryList();

    public List<UserNotificationPreference> getNotificationPreferences(CustomUserDetails curUser);

    public int saveNotificationPreferences(List<UserNotificationPreference> preferences, CustomUserDetails curUser);

    public int getRecipientCount(int categoryId, List<String> targetRoles, List<String> businessFunctionIds, List<Integer> languageIds);

    public int getRecipientCount(int categoryId, List<String> businessFunctionIds, List<Integer> languageIds);

    public int sendBroadcast(BroadcastMessageInput input, CustomUserDetails curUser);

    public int saveDraft(BroadcastMessageInput input, CustomUserDetails curUser);

    public BroadcastMessage getBroadcastMessageById(int broadcastId);

    public List<BroadcastRecipient> getRecipientsByBroadcastId(int broadcastId);

    public List<BroadcastMessage> getBroadcastHistory();

    public int unsubscribeByToken(String token, int categoryId);

    public List<UserNotificationPreference> getNotificationPreferencesByToken(String token);

    public int updatePreferencesByToken(String token, List<UserNotificationPreference> preferences);

}
