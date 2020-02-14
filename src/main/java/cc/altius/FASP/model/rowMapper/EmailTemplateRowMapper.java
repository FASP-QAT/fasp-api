/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;


import cc.altius.FASP.model.EmailTemplate;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class EmailTemplateRowMapper implements RowMapper<EmailTemplate> {

    @Override
    public EmailTemplate mapRow(ResultSet rs, int rowNum) throws SQLException {
        EmailTemplate emailTemplate = new EmailTemplate();
        emailTemplate.setEmailTemplateId(rs.getInt("EMAIL_TEMPLATE_ID"));
        emailTemplate.setSubject(rs.getString("SUBJECT"));
        emailTemplate.setSubjectParam(rs.getString("SUBJECT_PARAM"));
        emailTemplate.setEmailBody(rs.getString("EMAIL_BODY"));
        emailTemplate.setEmailBodyParam(rs.getString("EMAIL_BODY_PARAM"));
        emailTemplate.setCcTo(rs.getString("CC_TO"));
        emailTemplate.setToSend(rs.getString("TO_SEND"));
        emailTemplate.setBccTo(rs.getString("BCC"));
        return emailTemplate;
    }

}
