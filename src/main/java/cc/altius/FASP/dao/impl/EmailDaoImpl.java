/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.EmailDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.EmailTemplate;
import cc.altius.FASP.model.Emailer;
import cc.altius.FASP.model.rowMapper.EmailTemplateRowMapper;
import cc.altius.FASP.model.rowMapper.EmailerRowMapper;
import cc.altius.utils.DateUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

/**
 *
 * @author altius
 */
@Repository
public class EmailDaoImpl implements EmailDao {

    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public EmailTemplate getEmailTemplateByEmailTemplateId(int emailTemplateId) {
        String sql = "SELECT * FROM em_email_template WHERE em_email_template.`EMAIL_TEMPLATE_ID`=?";
        return this.jdbcTemplate.queryForObject(sql, new EmailTemplateRowMapper(), emailTemplateId);
    }

    @Override
    public int saveEmail(Emailer emailer) {
        int emailerId = 0;
//        int curUser = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        String curDate = DateUtils.getCurrentDateString(DateUtils.GMT, DateUtils.YMDHMS);
        SimpleJdbcInsert insert = new SimpleJdbcInsert(dataSource).withTableName("em_emailer").usingGeneratedKeyColumns("EMAILER_ID");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("TO_SEND", emailer.getToSend());
        params.put("SUBJECT", emailer.getSubject());
        params.put("BODY", emailer.getBody());
        params.put("CC_SEND_TO", emailer.getCcToSend());
        params.put("BCC", emailer.getBccToSend());
        params.put("CREATED_DATE", curDate);
        params.put("ATTEMPTS", 0);
        params.put("LAST_MODIFIED_DATE", curDate);
        params.put("STATUS", 0);
        params.put("REASON", emailer.getReason());
        emailerId = insert.executeAndReturnKey(params).intValue();
        return emailerId;
    }

    @Override
    public int updateEmail(int status, int attempts, String reason, int emailerId) {
        String curDate = DateUtils.getCurrentDateString(DateUtils.GMT, DateUtils.YMDHMS);
        String query = "UPDATE em_emailer e SET e.`STATUS`=?,e.`ATTEMPTS`=?,e.`REASON`=?, e.`LAST_MODIFIED_DATE`=?"
                + " WHERE e.`EMAILER_ID`=?";
        return this.jdbcTemplate.update(query, status, attempts, reason, curDate, emailerId);
    }

    @Override
    public List<Emailer> getEmailerList() {
        String sql = "SELECT * FROM em_emailer WHERE em_emailer.`STATUS`=0 AND em_emailer.`ATTEMPTS`<3;";
        return this.jdbcTemplate.query(sql, new EmailerRowMapper());
    }

    @Override
    public int emailerFilePathMapping(int emailerId, int fileId) {
        SimpleJdbcInsert si = new SimpleJdbcInsert(dataSource).withTableName("em_emailer_filepath_mapping").usingGeneratedKeyColumns("EMAILER_FILEPATH_MAPPING_ID");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("EMAILER_ID", emailerId);
        params.put("FILE_ID", fileId);
        return si.executeAndReturnKey(params).intValue();
    }

    @Override
    public List<String> getFilePathForEmailerId(int emailerId) {
        String sql = "SELECT fs.`FILE_PATH` FROM em_emailer_filepath_mapping efm \n"
                + " LEFT JOIN em_file_store fs ON fs.`FILE_ID`=efm.`FILE_ID`\n"
                + " WHERE efm.`EMAILER_ID`=?;";
        return this.jdbcTemplate.queryForList(sql, String.class, emailerId);
    }

    @Override
    public void sendMail(Emailer emailer) {
        int status = 0;
        String reason = null;
        int attempts = emailer.getAttempts();
        String from = "QAT_noreply@quantificationanalytics.org";
//        String from = "fasptestemail@gmail.com";
        String password = "#42Workingwombats";
//        String password = "bzczjrnpdkhrzxhf";
//        pass123%$";
        try {
            Properties props = System.getProperties();
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.host", "smtp.office365.com");
//            props.setProperty("mail.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.port", "587");
//            props.setProperty("mail.smtp.port", "587");
            props.setProperty("mail.smtp.auth", "true");
            props.setProperty("mail.smtp.socketFactory.port", "587");
            props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

//            props.setProperty("mail.transport.protocol", "smtp");
            props.setProperty("mail.user", from);
            props.setProperty("mail.password", password);
            props.setProperty("mail.debug", "true");
            Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(from, password);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailer.getToSend()));
            if (emailer.getCcToSend() != null && !emailer.getCcToSend().equals("")) {
                message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(emailer.getCcToSend()));
            }
            if (emailer.getBccToSend() != null && !emailer.getBccToSend().equals("")) {
                message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(emailer.getBccToSend()));
            }
            message.setSubject(emailer.getSubject());
            message.setContent(emailer.getBody(), "text/html");
            Transport.send(message);
            status = 1; //1 for success
            attempts++;
            reason = "Success";
            int rows = this.updateEmail(status, attempts, reason, emailer.getEmailerId());
//            LogUtils.schedulerLogger.info(LogUtils.buildStringForSystemLog(rows + " rows updated"));
        } catch (MessagingException ex) {
            reason = ex.toString();
            attempts++;
            status = 2;
            this.updateEmail(status, attempts, reason, emailer.getEmailerId());
        } catch (Exception ex) {
//            LogUtils.schedulerLogger.info(LogUtils.buildStringForSystemLog(ex));
//            LogUtils.systemLogger.error(LogUtils.buildStringForSystemLog(GlobalConstants.TAG_SCHEDULER, ex));
            reason = ex.toString();
            attempts++;
            status = 2;
            this.updateEmail(status, attempts, reason, emailer.getEmailerId());
//            LogUtils.schedulerLogger.info(LogUtils.buildStringForSystemLog(r + " rows updated"));
        }
    }

    @Override
    public Emailer buildEmail(int emailTemplateId, String toSend, String ccTo, String bccTo, String[] subjectParam, String[] bodyParam) {
        EmailTemplate emailTemplate = this.getEmailTemplateByEmailTemplateId(emailTemplateId);
        String subjectString = emailTemplate.getSubject();
        String emailBodyString = emailTemplate.getEmailBody();
        if (subjectParam.length != 0) {
            String[] eTemplateSubjectParamList = emailTemplate.getSubjectParam().split(",");
            for (int i = 0; i < subjectParam.length; i++) {
                subjectString = subjectString.replaceAll("<%" + eTemplateSubjectParamList[i] + "%>", subjectParam[i]);
            }
        }
        if (emailTemplate.getEmailBodyParam() != null) {
            String[] eTemplateBodyParamList = emailTemplate.getEmailBodyParam().split(",");
            for (int i = 0; i < bodyParam.length; i++) {
                if (bodyParam[i] == null) {
                    bodyParam[i] = "";
                }
                emailBodyString = emailBodyString.replaceAll("<%" + eTemplateBodyParamList[i] + "%>", bodyParam[i]);
            }
        }
        Emailer emailer = new Emailer();
        emailer.setBody(emailBodyString);
        emailer.setSubject(subjectString);
        emailer.setToSend(toSend);
        emailer.setCcToSend(ccTo);
        emailer.setBccToSend(bccTo);
        emailer.setAttempts(0);

        return emailer;
    }

    @Override
    public int insertIntoFileStoreUnique(String fileName) {
        int curUser;
        try {
            curUser = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        } catch (Exception e) {
            curUser = 1;
        }
        String curDate = DateUtils.getCurrentDateString(DateUtils.GMT, DateUtils.YMDHMS);
        NamedParameterJdbcTemplate nm = new NamedParameterJdbcTemplate(dataSource);
        MapSqlParameterSource params1 = new MapSqlParameterSource();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT IGNORE INTO em_file_store(`FILE_ID`,`FILE_PATH`,`CREATED_DATE`,`CREATED_BY`) VALUES ( NULL,:filePath,:createdDate,:createdBy); ";
        params1.addValue("filePath", fileName);
        params1.addValue("createdDate", curDate);
        params1.addValue("createdBy", curUser);
        nm.update(sql, params1, keyHolder);
        if (keyHolder.getKey() != null) {
            return keyHolder.getKey().intValue();
        } else {
            return 0;
        }
    }

    @Override
    public Emailer getEmailByEmailerId(int emailerId) {
        return this.jdbcTemplate.queryForObject("SELECT * FROM em_emailer e where e.EMAILER_ID=?", new EmailerRowMapper(), emailerId);
    }

}
