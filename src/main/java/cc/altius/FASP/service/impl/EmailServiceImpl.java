/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.EmailDao;
import cc.altius.FASP.model.EmailTemplate;
import cc.altius.FASP.model.Emailer;
import cc.altius.FASP.service.EmailService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 * @author altius
 */
@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private EmailDao emailDao;

    private @Value("#{scheduler['schedulerActive']}")
    String SCHEDULER_ACTIVE;

    @Override
    public EmailTemplate getEmailTemplateByEmailTemplateId(int emailTemplateId) {
        try {
            return this.emailDao.getEmailTemplateByEmailTemplateId(emailTemplateId);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public int saveEmail(Emailer emailer) {
        try {
            return this.emailDao.saveEmail(emailer);
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public int updateEmail(int status, int attempts, String reason, int emailerId) {
        try {
            return this.emailDao.updateEmail(status, attempts, reason, emailerId);
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public List<Emailer> getEmailerList() {
        try {
            return this.emailDao.getEmailerList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public int emailerFilePathMapping(int emailerId, int fileId) {
        try {
            return this.emailDao.emailerFilePathMapping(emailerId, fileId);
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public List<String> getFilePathForEmailerId(int emailerId) {
        try {
            return this.emailDao.getFilePathForEmailerId(emailerId);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void sendMail(Emailer emailer) {
        try {
            if (SCHEDULER_ACTIVE.equals("1")) {
//                System.out.println("---------------send email------------------");
                this.emailDao.sendMail(emailer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Emailer buildEmail(int emailTemplateId, String toSend, String ccTo, String bccTo, String[] subjectParam, String[] bodyParam) {
        try {
            return this.emailDao.buildEmail(emailTemplateId, toSend, ccTo, bccTo, subjectParam, bodyParam);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public int insertIntoFileStoreUnique(String fileName) {
        try {
            return this.emailDao.insertIntoFileStoreUnique(fileName);
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public Emailer getEmailByEmailerId(int emailerId) {
        return this.emailDao.getEmailByEmailerId(emailerId);
    }

}
