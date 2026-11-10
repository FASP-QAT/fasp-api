/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author altius
 */
public class UserNotificationPreference implements Serializable {

    private int preferenceId;
    private int userId;
    private int categoryId;
    private String categoryCode;
    private Label label;
    private String categoryName;
    private boolean allowOptOut;
    private boolean optedOut;
    private Date lastModifiedDate;

    public UserNotificationPreference() {
    }

    public int getPreferenceId() {
        return preferenceId;
    }

    public void setPreferenceId(int preferenceId) {
        this.preferenceId = preferenceId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public String getCategoryName() {
        return label != null ? label.getLabel_en() : (categoryName != null ? categoryName : "");
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public boolean isAllowOptOut() {
        return allowOptOut;
    }

    public void setAllowOptOut(boolean allowOptOut) {
        this.allowOptOut = allowOptOut;
    }

    public boolean isOptedOut() {
        return optedOut;
    }

    public void setOptedOut(boolean optedOut) {
        this.optedOut = optedOut;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

}
