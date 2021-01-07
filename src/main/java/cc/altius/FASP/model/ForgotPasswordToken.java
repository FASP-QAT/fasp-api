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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author akil
 */
public class ForgotPasswordToken implements Serializable {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
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
        logger.error("tokenTriggeredDate----" + tokenTriggeredDate);
        logger.error("tokenCompletionDate---" + tokenCompletionDate);
        System.out.println("this.tokenTriggeredDate---" + this.tokenTriggeredDate);
        System.out.println("this.tokenCompletionDate---" + this.tokenCompletionDate);
        if (this.tokenTriggeredDate != null || this.tokenCompletionDate != null) {
            System.out.println("Either tokenTriggeredDate is not null or  tokenCompletionDate is not null");
            return "static.message.user.forgotPasswordTokenUsed";
        }
        Calendar allowedTriggerDate = Calendar.getInstance();
        allowedTriggerDate.setTime(this.tokenGenerationDate);
        allowedTriggerDate.add(Calendar.MINUTE, 15);
        System.out.println("allowedTriggerDate---" + allowedTriggerDate);
        Calendar curDate = Calendar.getInstance();
        curDate.setTime(DateUtils.getCurrentDateObject(DateUtils.EST));
        System.out.println("curDate---" + curDate);
        logger.error("allowed trigger date---" + allowedTriggerDate.getTime());
        logger.error("cur date---" + curDate.getTime());
        logger.error("difference---" + DateUtils.compareDate(allowedTriggerDate.getTime(), curDate.getTime()));
        if (DateUtils.compareDate(allowedTriggerDate.getTime(), curDate.getTime()) == 1) {
            System.out.println("compare date inside if");
            return "";
        } else {
            System.out.println("compare dates inside else");
            return "static.message.user.forgotPasswordTokenUsed";
        }
    }

    public boolean isValidForCompletion() {
        return inValidReasonForCompletion().isEmpty();
    }

    public String inValidReasonForCompletion() {
        if (this.tokenTriggeredDate == null) {
            System.out.println("this.tokenTriggeredDate---" + this.tokenTriggeredDate);
            return "static.message.user.invalidToken";
        } else if (this.tokenCompletionDate != null) {
            System.out.println("this.tokenCompletionDate---" + this.tokenCompletionDate);
            return "static.message.user.forgotPasswordTokenUsed";
        }
        Calendar allowedCompletionDate = Calendar.getInstance();
        allowedCompletionDate.setTime(this.tokenTriggeredDate);
        allowedCompletionDate.add(Calendar.MINUTE, 3);
        System.out.println("allowedCompletionDate---" + allowedCompletionDate);
        Calendar curDate = Calendar.getInstance();
        curDate.setTime(DateUtils.getCurrentDateObject(DateUtils.EST));
        System.out.println("curDate---" + curDate);
        if (DateUtils.compareDate(allowedCompletionDate.getTime(), curDate.getTime()) == 1) {
            System.out.println("compare date d inside if---");
            return "";
        } else {
            System.out.println("compare date d inside else---");
            return "static.message.user.TokenExpired";
        }
    }
}
