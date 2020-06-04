/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.ImportArtemisDataDao;
import cc.altius.FASP.model.EmailTemplate;
import cc.altius.FASP.model.Emailer;
import cc.altius.FASP.service.EmailService;
import cc.altius.FASP.service.ImportArtemisDataService;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public void importOrderAndShipmentData(String orderDataFilePath, String shipmentDataFilePath) throws ParserConfigurationException, SAXException, IOException, FileNotFoundException {
        EmailTemplate emailTemplate = this.emailService.getEmailTemplateByEmailTemplateId(3);
        String[] subjectParam = new String[]{};
        String[] bodyParam = null;
        Emailer emailer = new Emailer();
        try {
            this.importArtemisDataDao.importOrderAndShipmentData(orderDataFilePath, shipmentDataFilePath);
        } catch (FileNotFoundException e) {
            subjectParam = new String[]{"Order/Shipment", "File not found"};
            bodyParam = new String[]{"Order/Shipment", "02-06-2020 12:50 PM IST", "File not found", e.getMessage()};
            emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), "anchal.c@altius.cc,shubham.y@altius.cc,priti.p@altius.cc,sameer.g@altiusbpo.com", "shubham.y@altius.cc,priti.p@altius.cc,sameer.g@altiusbpo.com", subjectParam, bodyParam);
            int emailerId = this.emailService.saveEmail(emailer);
            emailer.setEmailerId(emailerId);
            this.emailService.sendMail(emailer);
            System.out.println("file not found exception occured-------------------------------------");
            e.printStackTrace();
        } catch (SAXException e) {
            subjectParam = new String[]{"Order/Shipment", "Xml syntax error"};
            bodyParam = new String[]{"Order/Shipment", "02-06-2020 12:50PM IST", "Xml syntax error", e.getMessage()};
            emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), "anchal.c@altius.cc", "shubham.y@altius.cc,priti.p@altius.cc,sameer.g@altiusbpo.com", subjectParam, bodyParam);
            int emailerId = this.emailService.saveEmail(emailer);
            emailer.setEmailerId(emailerId);
            this.emailService.sendMail(emailer);
            System.out.println("SAX exception occured-------------------------------------");
            e.printStackTrace();
        } catch (IOException e) {
            subjectParam = new String[]{"Order/Shipment", "Input/Output error"};
            bodyParam = new String[]{"Order/Shipment", "02-06-2020 12:50PM IST", "Input/Output error", e.getMessage()};
            emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), "anchal.c@altius.cc", "shubham.y@altius.cc,priti.p@altius.cc,sameer.g@altiusbpo.com", subjectParam, bodyParam);
            int emailerId = this.emailService.saveEmail(emailer);
            emailer.setEmailerId(emailerId);
            this.emailService.sendMail(emailer);
            System.out.println("IO exception occured-------------------------------------");
            e.printStackTrace();
        } catch (Exception e) {
            subjectParam = new String[]{"Order/Shipment", e.getClass().toString()};
            bodyParam = new String[]{"Order/Shipment", "02-06-2020 12:50PM IST", e.getClass().toString(), e.getMessage()};
            emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), "anchal.c@altius.cc", "shubham.y@altius.cc,priti.p@altius.cc,sameer.g@altiusbpo.com", subjectParam, bodyParam);
            int emailerId = this.emailService.saveEmail(emailer);
            emailer.setEmailerId(emailerId);
            this.emailService.sendMail(emailer);
            System.out.println("General exception occured-------------------------------------");
            e.printStackTrace();
        }
    }

}
