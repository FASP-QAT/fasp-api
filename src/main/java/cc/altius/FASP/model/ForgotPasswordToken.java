/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import cc.altius.utils.DateUtils;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author akil
 */
public class ForgotPasswordToken implements Serializable {

    private int userId;
    private String username;
    private String token;
    private Date tokenGenerationDate;
    private Date tokenTriggeredDate;
    private Date tokenCompletionDate;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getTokenGenerationDate() {
        return tokenGenerationDate;
    }

    public void setTokenGenerationDate(Date tokenGenerationDate) {
        this.tokenGenerationDate = tokenGenerationDate;
    }

    public Date getTokenTriggeredDate() {
        return tokenTriggeredDate;
    }

    public void setTokenTriggeredDate(Date tokenTriggeredDate) {
        this.tokenTriggeredDate = tokenTriggeredDate;
    }

    public Date getTokenCompletionDate() {
        return tokenCompletionDate;
    }

    public void setTokenCompletionDate(Date tokenCompletionDate) {
        this.tokenCompletionDate = tokenCompletionDate;
    }

    public boolean isValidForTriggering() {
        return inValidReasonForTriggering().isEmpty();
    }
    
    public String inValidReasonForTriggering() {
        if (this.tokenTriggeredDate != null || this.tokenCompletionDate != null) {
            return "Token already used";
        } 
        Calendar allowedTriggerDate = Calendar.getInstance();
        allowedTriggerDate.setTime(this.tokenGenerationDate);
        allowedTriggerDate.add(Calendar.MINUTE, 15);
        Calendar curDate = Calendar.getInstance();
        curDate.setTime(DateUtils.getCurrentDateObject(DateUtils.EST));
        if (DateUtils.compareDate(allowedTriggerDate.getTime(), curDate.getTime())==1) {
            return "";
        } else {
            return "Token expired";
        }
    }
    
    public boolean isValidForCompletion() {
        return inValidReasonForCompletion().isEmpty();
    }
    
    public String inValidReasonForCompletion() {
        if (this.tokenTriggeredDate == null) {
            return "Invalid Token";
        } else if (this.tokenCompletionDate != null) {
            return "Token already used";
        }
        Calendar allowedCompletionDate = Calendar.getInstance();
        allowedCompletionDate.setTime(this.tokenTriggeredDate);
        allowedCompletionDate.add(Calendar.MINUTE, 15);
        Calendar curDate = Calendar.getInstance();
        curDate.setTime(DateUtils.getCurrentDateObject(DateUtils.EST));
        if (DateUtils.compareDate(allowedCompletionDate.getTime(), curDate.getTime())==1) {
            return "";
        } else {
            return "Token expired";
        }
    }
}
