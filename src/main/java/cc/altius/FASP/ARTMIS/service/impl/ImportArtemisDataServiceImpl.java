/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.ARTMIS.service.impl;

import cc.altius.FASP.ARTMIS.dao.ImportArtemisDataDao;
import cc.altius.FASP.model.EmailTemplate;
import cc.altius.FASP.model.Emailer;
import cc.altius.FASP.service.EmailService;
import cc.altius.FASP.ARTMIS.service.ImportArtemisDataService;
import cc.altius.utils.DateUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import javax.xml.parsers.ParserConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

/**
 *
 * @author altius
 */
@Service
public class ImportArtemisDataServiceImpl implements ImportArtemisDataService {

    @Autowired
    private ImportArtemisDataDao importArtemisDataDao;
    @Autowired
    private EmailService emailService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${qat.filePath}")
    private String QAT_FILE_PATH;
    @Value("${catalogFilePath}")
    private String CATALOG_FILE_PATH;
    @Value("${email.toList}")
    private String toList;
    @Value("${email.ccList}")
    private String ccList;

    @Override
    public void importOrderAndShipmentData(String orderDataFilePath, String shipmentDataFilePath) throws ParserConfigurationException, SAXException, IOException, FileNotFoundException {
        EmailTemplate emailTemplate = this.emailService.getEmailTemplateByEmailTemplateId(3);
        String[] subjectParam = new String[]{};
        String[] bodyParam = null;
        Emailer emailer = new Emailer();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy hh:mm a");
        String date = simpleDateFormat.format(DateUtils.getCurrentDateObject(DateUtils.EST));
        try {
            File directory = new File(QAT_FILE_PATH+CATALOG_FILE_PATH);
            if (directory.isDirectory()) {
                this.importArtemisDataDao.importOrderAndShipmentData(orderDataFilePath, shipmentDataFilePath);
            } else {
                subjectParam = new String[]{"Order/Shipment", "Directory does not exists"};
                bodyParam = new String[]{"Order/Shipment", date, "Directory does not exists", "Directory does not exists"};
                emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), toList, ccList, subjectParam, bodyParam);
                int emailerId = this.emailService.saveEmail(emailer);
                emailer.setEmailerId(emailerId);
                this.emailService.sendMail(emailer);
                logger.error("Directory does not exists");
            }
        } catch (FileNotFoundException e) {
            subjectParam = new String[]{"Order/Shipment", "File not found"};
            bodyParam = new String[]{"Order/Shipment", date, "File not found", e.getMessage()};
            emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), toList, ccList, subjectParam, bodyParam);
            int emailerId = this.emailService.saveEmail(emailer);
            emailer.setEmailerId(emailerId);
            this.emailService.sendMail(emailer);
            System.out.println("file not found exception occured-------------------------------------");
            e.printStackTrace();
        } catch (SAXException e) {
            subjectParam = new String[]{"Order/Shipment", "Xml syntax error"};
            bodyParam = new String[]{"Order/Shipment", date, "Xml syntax error", e.getMessage()};
            emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), toList, ccList, subjectParam, bodyParam);
            int emailerId = this.emailService.saveEmail(emailer);
            emailer.setEmailerId(emailerId);
            this.emailService.sendMail(emailer);
            System.out.println("SAX exception occured-------------------------------------");
            e.printStackTrace();
        } catch (IOException e) {
            subjectParam = new String[]{"Order/Shipment", "Input/Output error"};
            bodyParam = new String[]{"Order/Shipment", date, "Input/Output error", e.getMessage()};
            emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), toList, ccList, subjectParam, bodyParam);
            int emailerId = this.emailService.saveEmail(emailer);
            emailer.setEmailerId(emailerId);
            this.emailService.sendMail(emailer);
            System.out.println("IO exception occured-------------------------------------");
            e.printStackTrace();
        } catch (BadSqlGrammarException | DataIntegrityViolationException e) {
            subjectParam = new String[]{"Order/Shipment", "SQL Exception"};
            bodyParam = new String[]{"Order/Shipment", date, "SQL Exception", e.getMessage()};
            emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), toList, ccList, subjectParam, bodyParam);
            int emailerId = this.emailService.saveEmail(emailer);
            emailer.setEmailerId(emailerId);
            this.emailService.sendMail(emailer);
            logger.error("SQL exception occured", e);
        } catch (Exception e) {
            subjectParam = new String[]{"Order/Shipment", e.getClass().toString()};
            bodyParam = new String[]{"Order/Shipment", date, e.getClass().toString(), e.getMessage()};
            emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), toList, ccList, subjectParam, bodyParam);
            int emailerId = this.emailService.saveEmail(emailer);
            emailer.setEmailerId(emailerId);
            this.emailService.sendMail(emailer);
            System.out.println("General exception occured-------------------------------------");
            e.printStackTrace();
        }
    }

}
