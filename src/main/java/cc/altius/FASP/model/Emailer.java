/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

/**
 *
 * @author altius
 */
public class Emailer {

    private Integer emailerId;
    private String toSend;
    private String subject;
    private String body;
    private String ccToSend;
    private String bccToSend;
    private Integer attempts;
    private String reason;
    private Integer status;

    public Integer getEmailerId() {
        return emailerId;
    }

    public void setEmailerId(Integer emailerId) {
        this.emailerId = emailerId;
    }

    public String getToSend() {
        return toSend;
    }

    public void setToSend(String toSend) {
        this.toSend = toSend;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCcToSend() {
        return ccToSend;
    }

    public void setCcToSend(String ccToSend) {
        this.ccToSend = ccToSend;
    }

    public String getBccToSend() {
        return bccToSend;
    }

    public void setBccToSend(String bccToSend) {
        this.bccToSend = bccToSend;
    }

    public Integer getAttempts() {
        return attempts;
    }

    public void setAttempts(Integer attempts) {
        this.attempts = attempts;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Emailer{" + "emailerId=" + emailerId + ", toSend=" + toSend + ", subject=" + subject + ", body=" + body + ", ccToSend=" + ccToSend + ", bccToSend=" + bccToSend + ", attempts=" + attempts + ", reason=" + reason + ", status=" + status + '}';
    }

}
