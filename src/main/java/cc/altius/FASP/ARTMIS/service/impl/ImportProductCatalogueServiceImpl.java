/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.ARTMIS.service.impl;

import cc.altius.FASP.ARTMIS.dao.ImportProductCatalogueDao;
import cc.altius.FASP.ARTMIS.service.ImportProductCatalogueService;
import cc.altius.FASP.model.EmailTemplate;
import cc.altius.FASP.model.Emailer;
import cc.altius.FASP.service.EmailService;
import cc.altius.utils.DateUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import javax.xml.parsers.ParserConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    @Value("${qat.homeFolder}")
    private String QAT_FILE_PATH;
    @Value("${qat.catalogFilePath}")
    private String CATALOG_FILE_PATH;
    @Value("${email.catalogToList}")
    private String toList;
    @Value("${email.catalogCCList}")
    private String ccList;
    private static final String br = "\n<br/>";

    @Override
    @Transactional
    public String importProductCatalogue() {
        EmailTemplate emailTemplate = this.emailService.getEmailTemplateByEmailTemplateId(3);
        String[] subjectParam = new String[]{};
        String[] bodyParam = null;
        StringBuilder sb = new StringBuilder();

        logger.info("-------------- Import ARTMIS Product Catalog job started ---------------");
        sb.append("-------------- Import ARTMIS Product Catalog job started ---------------").append(br);
        Emailer emailer = new Emailer();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy hh:mm a");
        String date = simpleDateFormat.format(DateUtils.getCurrentDateObject(DateUtils.EST));
        try {
            File directory = new File(QAT_FILE_PATH + CATALOG_FILE_PATH);
            if (directory.isDirectory()) {
                this.importProductCatalogueDao.importProductCatalogue(sb);
            } else {
                subjectParam = new String[]{"Product Catalog", "Directory does not exists"};
                bodyParam = new String[]{"Product Catalog", "Directory does not exists", "Directory does not exists"};
                emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), toList, ccList, "", subjectParam, bodyParam);
                int emailerId = this.emailService.saveEmail(emailer);
                emailer.setEmailerId(emailerId);
                this.emailService.sendMail(emailer);
                logger.error("Directory does not exists");
                sb.append("Directory does not exists").append(br);
            }
        } catch (FileNotFoundException e) {
            subjectParam = new String[]{"Product Catalog", "File not found"};
            bodyParam = new String[]{"Product Catalog", "File not found", e.getMessage()};
            emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), toList, ccList, "", subjectParam, bodyParam);
            int emailerId = this.emailService.saveEmail(emailer);
            emailer.setEmailerId(emailerId);
            this.emailService.sendMail(emailer);
            logger.error("File not found exception occured", e);
            sb.append("File not found exception occured").append(br).append(e.getMessage()).append(br);
            this.importProductCatalogueDao.rollBackAutoIncrement(sb);
        } catch (SAXException e) {
            subjectParam = new String[]{"Product Catalog", "Xml syntax error"};
            bodyParam = new String[]{"Product Catalog", "Xml syntax error", e.getMessage()};
            emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), toList, ccList, "", subjectParam, bodyParam);
            int emailerId = this.emailService.saveEmail(emailer);
            emailer.setEmailerId(emailerId);
            this.emailService.sendMail(emailer);
            logger.error("SAX exception occured", e);
            sb.append("SAX exception occured").append(br).append(e.getMessage()).append(br);
            this.importProductCatalogueDao.rollBackAutoIncrement(sb);
        } catch (IOException e) {
            subjectParam = new String[]{"Product Catalog", "Input/Output error"};
            bodyParam = new String[]{"Product Catalog", "Input/Output error", e.getMessage()};
            emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), toList, ccList, "", subjectParam, bodyParam);
            int emailerId = this.emailService.saveEmail(emailer);
            emailer.setEmailerId(emailerId);
            this.emailService.sendMail(emailer);
            logger.error("IO exception occured", e);
            sb.append("IO exception occured").append(br).append(e.getMessage()).append(br);
            this.importProductCatalogueDao.rollBackAutoIncrement(sb);
        } catch (BadSqlGrammarException | DataIntegrityViolationException e) {
            subjectParam = new String[]{"Product Catalog", "SQL Exception"};
            bodyParam = new String[]{"Product Catalog", "SQL Exception", e.getMessage()};
            emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), toList, ccList, "", subjectParam, bodyParam);
            int emailerId = this.emailService.saveEmail(emailer);
            emailer.setEmailerId(emailerId);
            this.emailService.sendMail(emailer);
            logger.error("SQL exception occured", e);
            sb.append("SQL exception occured").append(br).append(e.getMessage()).append(br);
            this.importProductCatalogueDao.rollBackAutoIncrement(sb);
        } catch (Exception e) {
            subjectParam = new String[]{"Product Catalog", e.getClass().toString()};
            bodyParam = new String[]{"Product Catalog", e.getClass().toString(), e.getMessage()};
            emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), toList, ccList, "", subjectParam, bodyParam);
            int emailerId = this.emailService.saveEmail(emailer);
            emailer.setEmailerId(emailerId);
            this.emailService.sendMail(emailer);
            logger.error("Import ARTMIS Product Catalog exception occured", e);
            sb.append("Import ARTMIS Product Catalog exception occured").append(br).append(e.getMessage()).append(br);
            this.importProductCatalogueDao.rollBackAutoIncrement(sb);
        }
        return sb.toString();
    }

    @Override
    public String importProductCatalogueLegacy() {
        try {
            return this.importProductCatalogueDao.importProductCatalogueLegacy();
        } catch (ParserConfigurationException ex) {
            java.util.logging.Logger.getLogger(ImportProductCatalogueServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return "Exception";
        } catch (SAXException ex) {
            java.util.logging.Logger.getLogger(ImportProductCatalogueServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return "Exception";
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(ImportProductCatalogueServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return "Exception";
        } catch (BadSqlGrammarException ex) {
            java.util.logging.Logger.getLogger(ImportProductCatalogueServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return "Exception";
        }
    }

}
