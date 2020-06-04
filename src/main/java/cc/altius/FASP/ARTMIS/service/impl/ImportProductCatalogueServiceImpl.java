/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.ARTMIS.service.impl;

import cc.altius.FASP.ARTMIS.dao.ImportProductCatalogueDao;
import cc.altius.FASP.model.EmailTemplate;
import cc.altius.FASP.model.Emailer;
import cc.altius.FASP.service.EmailService;
import cc.altius.FASP.service.ImportProductCatalogueService;
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
public class ImportProductCatalogueServiceImpl implements ImportProductCatalogueService {

    @Autowired
    private ImportProductCatalogueDao importProductCatalogueDao;
    @Autowired
    private EmailService emailService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${catalogFilePath}")
    private String CATALOG_FILE_PATH;

    @Override
//    @Transactional(propagation = Propagation.REQUIRED)
//    @Transactional
    public void importProductCatalogue(String filePath) throws ParserConfigurationException, SAXException, IOException, FileNotFoundException, BadSqlGrammarException {
        EmailTemplate emailTemplate = this.emailService.getEmailTemplateByEmailTemplateId(3);
        String[] subjectParam = new String[]{};
        String[] bodyParam = null;
        Emailer emailer = new Emailer();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy hh:mm a");
        String date = simpleDateFormat.format(DateUtils.getCurrentDateObject(DateUtils.EST));
        try {
            File directory = new File(CATALOG_FILE_PATH);
            if (directory.isDirectory()) {
                this.importProductCatalogueDao.importProductCatalogue(filePath);
//            this.importProductCatalogueDao.pullUnitTable();
//        this.importProductCatalogueDao.pullTracerCategoryFromTmpTables();
//        this.importProductCatalogueDao.pullForecastingUnitFromTmpTables();
//        this.importProductCatalogueDao.pullPlanningUnitFromTmpTables();
//        this.importProductCatalogueDao.pullSupplierFromTmpTables();
//        this.importProductCatalogueDao.pullProcurementUnitFromTmpTables();
            } else {
                subjectParam = new String[]{"Product Catalogue", "Directory does not exists"};
                bodyParam = new String[]{"Product Catalogue", date, "Directory does not exists", "Directory does not exists"};
                emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), "anchal.c@altius.cc,shubham.y@altius.cc,priti.p@altius.cc,sameer.g@altiusbpo.com", "shubham.y@altius.cc,priti.p@altius.cc,sameer.g@altiusbpo.com", subjectParam, bodyParam);
                int emailerId = this.emailService.saveEmail(emailer);
                emailer.setEmailerId(emailerId);
                this.emailService.sendMail(emailer);
                logger.error("Directory does not exists");
            }
        } catch (FileNotFoundException e) {
            subjectParam = new String[]{"Product Catalogue", "File not found"};
            bodyParam = new String[]{"Product Catalogue", date, "File not found", e.getMessage()};
            emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), "anchal.c@altius.cc,shubham.y@altius.cc,priti.p@altius.cc,sameer.g@altiusbpo.com", "shubham.y@altius.cc,priti.p@altius.cc,sameer.g@altiusbpo.com", subjectParam, bodyParam);
            int emailerId = this.emailService.saveEmail(emailer);
            emailer.setEmailerId(emailerId);
            this.emailService.sendMail(emailer);
            logger.error("File not found exception occured", e);
        } catch (SAXException e) {
            subjectParam = new String[]{"Product Catalogue", "Xml syntax error"};
            bodyParam = new String[]{"Product Catalogue", date, "Xml syntax error", e.getMessage()};
            emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), "anchal.c@altius.cc,shubham.y@altius.cc,priti.p@altius.cc,sameer.g@altiusbpo.com", "shubham.y@altius.cc,priti.p@altius.cc,sameer.g@altiusbpo.com", subjectParam, bodyParam);
            int emailerId = this.emailService.saveEmail(emailer);
            emailer.setEmailerId(emailerId);
            this.emailService.sendMail(emailer);
            logger.error("SAX exception occured", e);
        } catch (IOException e) {
            subjectParam = new String[]{"Product Catalogue", "Input/Output error"};
            bodyParam = new String[]{"Product Catalogue", date, "Input/Output error", e.getMessage()};
            emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), "anchal.c@altius.cc,shubham.y@altius.cc,priti.p@altius.cc,sameer.g@altiusbpo.com", "shubham.y@altius.cc,priti.p@altius.cc,sameer.g@altiusbpo.com", subjectParam, bodyParam);
            int emailerId = this.emailService.saveEmail(emailer);
            emailer.setEmailerId(emailerId);
            this.emailService.sendMail(emailer);
            logger.error("IO exception occured", e);
        } catch (BadSqlGrammarException | DataIntegrityViolationException e) {
            subjectParam = new String[]{"Product Catalogue", "SQL Exception"};
            bodyParam = new String[]{"Product Catalogue", date, "SQL Exception", e.getMessage()};
            emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), "anchal.c@altius.cc,shubham.y@altius.cc,priti.p@altius.cc,sameer.g@altiusbpo.com", "shubham.y@altius.cc,priti.p@altius.cc,sameer.g@altiusbpo.com", subjectParam, bodyParam);
            int emailerId = this.emailService.saveEmail(emailer);
            emailer.setEmailerId(emailerId);
            this.emailService.sendMail(emailer);
            logger.error("SQL exception occured", e);
        } catch (Exception e) {
            subjectParam = new String[]{"Product Catalogue", e.getClass().toString()};
            bodyParam = new String[]{"Product Catalogue", date, e.getClass().toString(), e.getMessage()};
            emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), "anchal.c@altius.cc,shubham.y@altius.cc,priti.p@altius.cc,sameer.g@altiusbpo.com", "shubham.y@altius.cc,priti.p@altius.cc,sameer.g@altiusbpo.com", subjectParam, bodyParam);
            int emailerId = this.emailService.saveEmail(emailer);
            emailer.setEmailerId(emailerId);
            this.emailService.sendMail(emailer);
            logger.error("Export supply plan exception occured", e);
        }
    }

}
