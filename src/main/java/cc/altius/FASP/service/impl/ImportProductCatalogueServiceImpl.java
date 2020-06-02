/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.ImportProductCatalogueDao;
import cc.altius.FASP.model.EmailTemplate;
import cc.altius.FASP.model.Emailer;
import cc.altius.FASP.service.EmailService;
import cc.altius.FASP.service.ImportProductCatalogueService;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import javax.xml.parsers.ParserConfigurationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

/**
 *
 * @author altius
 */
@Service
public class ImportProductCatalogueServiceImpl implements ImportProductCatalogueService {

    @Autowired
    private ImportProductCatalogueDao importProductCatalogueDao;
    @Autowired
    private EmailService emailService;

    @Override
//    @Transactional(propagation = Propagation.REQUIRED)
//    @Transactional
    public void importProductCatalogue(String filePath) throws ParserConfigurationException, SAXException, IOException {
        EmailTemplate emailTemplate = this.emailService.getEmailTemplateByEmailTemplateId(3);
        String[] subjectParam = new String[]{};
        String[] bodyParam = null;
        Emailer emailer = new Emailer();
        try {
            this.importProductCatalogueDao.importProductCatalogue(filePath);
//            this.importProductCatalogueDao.pullUnitTable();
//        this.importProductCatalogueDao.pullTracerCategoryFromTmpTables();
//        this.importProductCatalogueDao.pullForecastingUnitFromTmpTables();
//        this.importProductCatalogueDao.pullPlanningUnitFromTmpTables();
//        this.importProductCatalogueDao.pullSupplierFromTmpTables();
//        this.importProductCatalogueDao.pullProcurementUnitFromTmpTables();
        } catch (FileNotFoundException e) {
            subjectParam = new String[]{"Product Catalogue", "File not found"};
            bodyParam = new String[]{"Product Catalogue", "02-06-2020 12:50 PM IST", "File not found", e.getMessage()};
            emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), "anchal.c@altius.cc,shubham.y@altius.cc,priti.p@altius.cc,sameer.g@altiusbpo.com", "shubham.y@altius.cc,priti.p@altius.cc,sameer.g@altiusbpo.com", subjectParam, bodyParam);
            int emailerId = this.emailService.saveEmail(emailer);
            emailer.setEmailerId(emailerId);
            this.emailService.sendMail(emailer);
            System.out.println("file not found exception occured-------------------------------------");
            e.printStackTrace();
        } catch (SAXException e) {
            subjectParam = new String[]{"Product Catalogue", "Xml syntax error"};
            bodyParam = new String[]{"Product Catalogue", "02-06-2020 12:50PM IST", "Xml syntax error", e.getMessage()};
            emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), "anchal.c@altius.cc", "shubham.y@altius.cc,priti.p@altius.cc,sameer.g@altiusbpo.com", subjectParam, bodyParam);
            int emailerId = this.emailService.saveEmail(emailer);
            emailer.setEmailerId(emailerId);
            this.emailService.sendMail(emailer);
            System.out.println("SAX exception occured-------------------------------------");
            e.printStackTrace();
        } catch (IOException e) {
            subjectParam = new String[]{"Product Catalogue", "Input/Output error"};
            bodyParam = new String[]{"Product Catalogue", "02-06-2020 12:50PM IST", "Input/Output error", e.getMessage()};
            emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), "anchal.c@altius.cc", "shubham.y@altius.cc,priti.p@altius.cc,sameer.g@altiusbpo.com", subjectParam, bodyParam);
            int emailerId = this.emailService.saveEmail(emailer);
            emailer.setEmailerId(emailerId);
            this.emailService.sendMail(emailer);
            System.out.println("IO exception occured-------------------------------------");
            e.printStackTrace();
        } catch (Exception e) {
            subjectParam = new String[]{"Product Catalogue", e.getClass().toString()};
            bodyParam = new String[]{"Product Catalogue", "02-06-2020 12:50PM IST", e.getClass().toString(), e.getMessage()};
            emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), "anchal.c@altius.cc", "shubham.y@altius.cc,priti.p@altius.cc,sameer.g@altiusbpo.com", subjectParam, bodyParam);
            int emailerId = this.emailService.saveEmail(emailer);
            emailer.setEmailerId(emailerId);
            this.emailService.sendMail(emailer);
            System.out.println("General exception occured-------------------------------------");
            e.printStackTrace();
        }
    }

}
