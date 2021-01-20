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
        logger.info("tokenTriggeredDate----" + tokenTriggeredDate);
        logger.info("tokenCompletionDate---" + tokenCompletionDate);
        if (this.tokenTriggeredDate != null || this.tokenCompletionDate != null) {
            logger.info("Either tokenTriggeredDate is not null or  tokenCompletionDate is not null");
            return "static.message.user.forgotPasswordTokenUsed";
        }
        Calendar allowedTriggerDate = Calendar.getInstance();
        allowedTriggerDate.setTime(this.tokenGenerationDate);
        allowedTriggerDate.add(Calendar.MINUTE, 15);
        logger.info("allowedTriggerDate---" + allowedTriggerDate);
        Calendar curDate = Calendar.getInstance();
        curDate.setTime(DateUtils.getCurrentDateObject(DateUtils.EST));
        logger.error("allowed trigger date---" + allowedTriggerDate.getTime());
        logger.error("cur date---" + curDate.getTime());
        logger.error("difference---" + DateUtils.compareDate(allowedTriggerDate.getTime(), curDate.getTime()));
        if (DateUtils.compareDate(allowedTriggerDate.getTime(), curDate.getTime()) == 1) {
            logger.info("compare date inside if");
            return "";
        } else {
            logger.info("compare dates inside else");
            return "static.message.user.forgotPasswordTokenUsed";
        }
    }

    public boolean isValidForCompletion() {
        return inValidReasonForCompletion().isEmpty();
    }

    public String inValidReasonForCompletion() {
        if (this.tokenTriggeredDate == null) {
            logger.info("this.tokenTriggeredDate---" + this.tokenTriggeredDate);
            return "static.message.user.invalidToken";
        } else if (this.tokenCompletionDate != null) {
            logger.info("this.tokenCompletionDate---" + this.tokenCompletionDate);
            return "static.message.user.forgotPasswordTokenUsed";
        }
        Calendar allowedCompletionDate = Calendar.getInstance();
        allowedCompletionDate.setTime(this.tokenTriggeredDate);
        allowedCompletionDate.add(Calendar.MINUTE, 3);
        logger.info("allowedCompletionDate---" + allowedCompletionDate);
        Calendar curDate = Calendar.getInstance();
        curDate.setTime(DateUtils.getCurrentDateObject(DateUtils.EST));
        logger.info("curDate---" + curDate);
        if (DateUtils.compareDate(allowedCompletionDate.getTime(), curDate.getTime()) == 1) {
            logger.info("compare date d inside if---");
            return "";
        } else {
            logger.info("compare date d inside else---");
            return "static.message.user.TokenExpired";
        }
    }

    @Override
    public String toString() {
        return "ForgotPasswordToken{" + "logger=" + logger + ", userId=" + userId + ", username=" + username + ", token=" + token + ", tokenGenerationDate=" + tokenGenerationDate + ", tokenTriggeredDate=" + tokenTriggeredDate + ", tokenCompletionDate=" + tokenCompletionDate + '}';
    }
    
}
