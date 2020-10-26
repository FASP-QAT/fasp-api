/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.ARTMIS.service.impl;

import cc.altius.FASP.model.EmailTemplate;
import cc.altius.FASP.model.Emailer;
import cc.altius.FASP.service.EmailService;
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
import cc.altius.FASP.ARTMIS.service.ImportArtmisDataService;
import java.io.FileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;

/**
 *
 * @author altius
 */
@Service
public class ImportArtmisDataServiceImpl implements ImportArtmisDataService {

//    @Autowired
//    private ImportArtmisDataDao importArtemisDataDao;
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
    private static final String ERR_CODE_NO_FILE_FOUND = "No Order file found for import";
    private static final String ERR_CODE_NO_DIRECTORY = "Directory not found";
    private static final String ERR_CODE_XML_ERROR = "Error occurred while trying to read XML file";
    private static final String ERR_CODE_IO_EXCEPTION = "IO Exception";
    private static final String ERR_CODE_OTHER_EXCEPTION = "General exception";
    private static final String ERR_CODE_SQL_EXCEPTION = "SQL Exception";

    @Override
    public void importOrderAndShipmentData() throws ParserConfigurationException, SAXException, IOException, FileNotFoundException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy hh:mm a");
        String date = simpleDateFormat.format(DateUtils.getCurrentDateObject(DateUtils.EST));
        FileFilter fileFilter = new WildcardFileFilter("order_data_*.xml");
        String errorCode = "";
        String exceptionMessage = "";
        String fileName = "";
        String[] subjectParam, bodyParam;

        File dir = new File(QAT_FILE_PATH + CATALOG_FILE_PATH);
        if (dir.isDirectory()) {
            File[] files = dir.listFiles(fileFilter);
            if (files.length > 0) {
                for (File file : files) {
                    fileName = file.getName();
                    try {
//                        this.importArtemisDataDao.importOrderAndShipmentData(file);
//                    } catch (SAXException e) {
//                        errorCode = ERR_CODE_XML_ERROR;
//                        exceptionMessage = e.getMessage();
//                    } catch (FileNotFoundException e) {
//                        errorCode = ERR_CODE_NO_DIRECTORY;
//                        exceptionMessage = e.getMessage();
//                    } catch (IOException e) {
//                        errorCode = ERR_CODE_IO_EXCEPTION;
//                        exceptionMessage = e.getMessage();
                    } catch (BadSqlGrammarException | DataIntegrityViolationException e) {
                        errorCode = ERR_CODE_SQL_EXCEPTION;
                        exceptionMessage = e.getMessage();
                    } catch (Exception e) {
                        errorCode = ERR_CODE_OTHER_EXCEPTION;
                        exceptionMessage = e.getMessage();
                    }
                    if (!errorCode.isEmpty()) {
                        break;
                    }
                }
            } else {
                errorCode = ERR_CODE_NO_FILE_FOUND;
            }
        } else {
            errorCode = ERR_CODE_NO_DIRECTORY;
        }

        if (!errorCode.isEmpty()) {
            errorCode += " " + fileName;
            subjectParam = new String[]{"Order/Shipment", errorCode};
            bodyParam = new String[]{"Order/Shipment", date, errorCode, exceptionMessage};
            logger.info(errorCode + " " + exceptionMessage);
            EmailTemplate emailTemplate = this.emailService.getEmailTemplateByEmailTemplateId(3);
            Emailer emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), toList, ccList, subjectParam, bodyParam);
            int emailerId = this.emailService.saveEmail(emailer);
            emailer.setEmailerId(emailerId);
            this.emailService.sendMail(emailer);
        }
    }

}
