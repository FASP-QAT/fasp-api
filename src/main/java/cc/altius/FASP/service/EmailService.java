/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service;

import cc.altius.FASP.model.EmailTemplate;
import cc.altius.FASP.model.Emailer;
import java.util.List;

/**
 *
 * @author altius
 */
public interface EmailService {

    public EmailTemplate getEmailTemplateByEmailTemplateId(int emailTemplateId);

    public int saveEmail(Emailer emailer);

    public int updateEmail(int status, int attempts, String reason, int emailerId);

    public List<Emailer> getEmailerList();

    public int emailerFilePathMapping(int emailerId, int fileId);

    public List<String> getFilePathForEmailerId(int emailerId);

    public void sendMail(Emailer emailer);

    public Emailer buildEmail(int emailTemplateId, String toSend, String ccTo, String bccTo, String[] subjectParam, String[] bodyParam);

    public int insertIntoFileStoreUnique(String fileName);
    
    public Emailer getEmailByEmailerId(int emailerId);
}
