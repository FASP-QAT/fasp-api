/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.ARTMIS.service.impl;

import cc.altius.FASP.ARTMIS.dao.ImportArtmisDataDao;
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
import cc.altius.FASP.dao.ProgramDao;
import cc.altius.FASP.dao.ProgramDataDao;
import cc.altius.FASP.model.EmailTemplate;
import cc.altius.FASP.model.Emailer;
import cc.altius.FASP.utils.FileNameComparator;
import java.io.FileFilter;
import java.text.ParseException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.io.filefilter.WildcardFileFilter;

/**
 *
 * @author altius
 */
@Service
public class ImportArtmisDataServiceImpl implements ImportArtmisDataService {

    @Autowired
    private ImportArtmisDataDao importArtmisDataDao;
    @Autowired
    private ProgramDataDao programDataDao;
    @Autowired
    private ProgramDao programDao;
    @Autowired
    private EmailService emailService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${qat.filePath}")
    private String QAT_FILE_PATH;
    @Value("${catalogFilePath}")
    private String CATALOG_FILE_PATH;
    @Value("${email.orderToList}")
    private String toList;
    @Value("${email.orderCCList}")
    private String ccList;
    @Value("${catalogBkpFilePath}")
    private String BKP_CATALOG_FILE_PATH;
    private static final String ERR_CODE_NO_FILE_FOUND = "No Order file found for import";
    private static final String ERR_CODE_NO_SHIPMENT_FILE_FOUND = "No Shipment file found for import";
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
        String orderFileName = "";
        String[] subjectParam, bodyParam;

        File dir = new File(QAT_FILE_PATH + CATALOG_FILE_PATH);
        List<Integer> programList = new LinkedList<>();
        if (dir.isDirectory()) {
            File[] files = dir.listFiles(fileFilter);
            Arrays.sort(files, new FileNameComparator());
            if (files.length > 0) {
                for (File orderFile : files) {
                    orderFileName = orderFile.getName();
                    try {
                        File shipmentFile = new File(QAT_FILE_PATH + CATALOG_FILE_PATH + "/shipment_data_" + orderFileName.substring(11));
                        if (shipmentFile.exists() && shipmentFile.isFile()) {
                            List<Integer> newList = this.importArtmisDataDao.importOrderAndShipmentData(orderFile, shipmentFile);
                            newList.forEach(p -> {
                                if (programList.indexOf(p) == -1) {
                                    programList.add(p);
                                }
                            });

                            File directory = new File(QAT_FILE_PATH + BKP_CATALOG_FILE_PATH);
//                            System.out.println("directory--------------" + directory.getPath());
                            if (directory.isDirectory()) {
                                orderFile.renameTo(new File(QAT_FILE_PATH + BKP_CATALOG_FILE_PATH + orderFile.getName()));
                                shipmentFile.renameTo(new File(QAT_FILE_PATH + BKP_CATALOG_FILE_PATH + shipmentFile.getName()));
                                logger.info("Order/Shipment files moved into processed folder-Orderfile--->" + orderFile.getName() + " Shipment File--->" + shipmentFile.getName());
                            }
                        } else {
                            errorCode = ERR_CODE_NO_SHIPMENT_FILE_FOUND;
                        }
                    } catch (SAXException e) {
                        errorCode = ERR_CODE_XML_ERROR;
                        exceptionMessage = e.getMessage();
                    } catch (FileNotFoundException e) {
                        errorCode = ERR_CODE_NO_DIRECTORY;
                        exceptionMessage = e.getMessage();
                    } catch (IOException e) {
                        errorCode = ERR_CODE_IO_EXCEPTION;
                        exceptionMessage = e.getMessage();
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
        logger.info("errorCode = " + errorCode);
        logger.info("exceptionMessage = " + exceptionMessage);
        if (!errorCode.isEmpty()) {
            errorCode += " " + orderFileName;
            subjectParam = new String[]{"Order/Shipment - ", errorCode};
            bodyParam = new String[]{"Order/Shipment", errorCode, exceptionMessage};
            logger.info(errorCode + " " + exceptionMessage);
            EmailTemplate emailTemplate = this.emailService.getEmailTemplateByEmailTemplateId(3);
            Emailer emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), toList, ccList, "", subjectParam, bodyParam);
            int emailerId = this.emailService.saveEmail(emailer);
            emailer.setEmailerId(emailerId);
            this.emailService.sendMail(emailer);
            logger.info("Email sent out for error");
        }
        logger.info("Going to rebuild Supply Plans for any Programs that were updated");
//        System.out.println("programList----" + programList);
        programList.forEach(p -> {
            try {
//                System.out.println("p-----------" + p);
                int versionId = this.programDao.getLatestVersionForPrograms(""+p).get(0).getVersionId();
                logger.info("Going to rebuild Supply plan for Program " + p + " Version " + versionId);
                this.programDataDao.getNewSupplyPlanList(p, -1, true, false);
                logger.info("Supply plan rebuilt");
                // Move to processed folder

            } catch (ParseException ex) {
                logger.info("Could not rebuild supply plan", ex);
            }
        });
    }

}
