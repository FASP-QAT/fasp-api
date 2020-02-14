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
public class EmailTemplate {

    private Integer emailTemplateId;
    private String subject;
    private String subjectParam;
    private String emailBody;
    private String emailBodyParam;
    private String toSend;
    private String ccTo;
    private String bccTo;

    public Integer getEmailTemplateId() {
        return emailTemplateId;
    }

    public void setEmailTemplateId(Integer emailTemplateId) {
        this.emailTemplateId = emailTemplateId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubjectParam() {
        return subjectParam;
    }

    public void setSubjectParam(String subjectParam) {
        this.subjectParam = subjectParam;
    }

    public String getEmailBody() {
        return emailBody;
    }

    public void setEmailBody(String emailBody) {
        this.emailBody = emailBody;
    }

    public String getEmailBodyParam() {
        return emailBodyParam;
    }

    public void setEmailBodyParam(String emailBodyParam) {
        this.emailBodyParam = emailBodyParam;
    }

    public String getToSend() {
        return toSend;
    }

    public void setToSend(String toSend) {
        this.toSend = toSend;
    }

    public String getCcTo() {
        return ccTo;
    }

    public void setCcTo(String ccTo) {
        this.ccTo = ccTo;
    }

    public String getBccTo() {
        return bccTo;
    }

    public void setBccTo(String bccTo) {
        this.bccTo = bccTo;
    }

}
