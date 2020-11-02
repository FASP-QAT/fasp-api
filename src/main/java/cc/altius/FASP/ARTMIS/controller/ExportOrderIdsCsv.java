/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.ARTMIS.controller;

import cc.altius.FASP.ARTMIS.service.ExportArtmisDataService;
import cc.altius.FASP.model.DTO.ExportOrderDataDTO;
import cc.altius.FASP.model.EmailTemplate;
import cc.altius.FASP.model.Emailer;
import cc.altius.FASP.service.EmailService;
import cc.altius.utils.DateUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author altius
 */
@Controller
public class ExportOrderIdsCsv {

    @Autowired
    private ExportArtmisDataService exportArtmisDataService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private EmailService emailService;
    @Value("${qat.filePath}")
    private String QAT_FILE_PATH;
    @Value("${exportSupplyPlanFilePath}")
    private String EXPORT_SUPPLY_PLAN_FILE_PATH;
    @Value("${email.toList}")
    private String toList;
    @Value("${email.ccList}")
    private String ccList;

    @RequestMapping(value = "/exportOrderData")
//    @Scheduled(cron = "0 0 21 * * MON-FRI",zone="EST")
//    @Scheduled(cron = "00 */02 * * * *")
    public void exportProductData() {
        logger.info("--------------Order Id schedular started---------------");
        EmailTemplate emailTemplate = this.emailService.getEmailTemplateByEmailTemplateId(4);
        String[] subjectParam = new String[]{};
        String[] bodyParam = null;
        Emailer emailer = new Emailer();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy hh:mm a");
        String date = simpleDateFormat.format(DateUtils.getCurrentDateObject(DateUtils.EST));
        try {

            String curDate = DateUtils.getCurrentDateString(DateUtils.EST, DateUtils.YMD);
            String path, json;
            List<ExportOrderDataDTO> exportOrderDataDT = this.exportArtmisDataService.exportOrderData();
            System.out.println("ExportOrderDataDT --- " + exportOrderDataDT);
            File directory = new File(QAT_FILE_PATH + EXPORT_SUPPLY_PLAN_FILE_PATH);
            if (directory.isDirectory()) {
                path = QAT_FILE_PATH + EXPORT_SUPPLY_PLAN_FILE_PATH + "QAT_Orders_" + curDate + ".csv";
                FileWriter fileWriter = new FileWriter(path);
                fileWriter.append("SHIPMENT_ID");
                fileWriter.append(',');
                fileWriter.append("SKU_CODE");
                fileWriter.append(',');
                fileWriter.append("PROGRAM_ID");
                fileWriter.append(',');
                fileWriter.append("PROCUREMENT_AGENT_CODE");
                fileWriter.append(',');
                fileWriter.append("SHIPMENT_QTY");
                fileWriter.append(',');
                fileWriter.append("EXPECTED_DELIVERY_DATE");
                fileWriter.append(',');
                fileWriter.append("TRACER_CATEGORY_ID");
                fileWriter.append(',');
                fileWriter.append("TRACER_CATEGORY_DESC");
                fileWriter.append(',');
                fileWriter.append("ACTIVE");
                fileWriter.append('\n');

                for (ExportOrderDataDTO e : exportOrderDataDT) {
                    fileWriter.append(Integer.toString(e.getShipmentId()));
                    fileWriter.append(',');
                    fileWriter.append(e.getSkuCode());
                    fileWriter.append(',');
                    fileWriter.append(Integer.toString(e.getProgramId()));
                    fileWriter.append(',');
                    fileWriter.append(e.getProcurementAgentCode());
                    fileWriter.append(',');
                    fileWriter.append(Integer.toString(e.getShipmentQty()));
                    fileWriter.append(',');
                    fileWriter.append(DateUtils.formatDate(e.getExpectedDeliveryDate(), DateUtils.YMD));
                    fileWriter.append(',');
                    fileWriter.append(Integer.toString(e.getTracerCategoryId()));
                    fileWriter.append(',');
                    fileWriter.append(e.getTracerCategoryDesc());
                    fileWriter.append(',');
                    fileWriter.append((e.isActive() ? "1" : "0"));
                    fileWriter.append('\n');
                }
                fileWriter.flush();
                fileWriter.close();
                logger.info("Export qat order data successful");
            } else {
                subjectParam = new String[]{"QAT Order Data", "Directory does not exists"};
                bodyParam = new String[]{"QAT Order Data", date, "Directory does not exists", "Directory does not exists"};
                emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), toList, ccList, subjectParam, bodyParam);
                int emailerId = this.emailService.saveEmail(emailer);
                emailer.setEmailerId(emailerId);
                this.emailService.sendMail(emailer);
                logger.error("Directory does not exists");
            }
        } catch (FileNotFoundException e) {
            subjectParam = new String[]{"QAT Order Data", "File not found"};
            bodyParam = new String[]{"QAT Order Data", date, "File not found", e.getMessage()};
            emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), toList, ccList, subjectParam, bodyParam);
            int emailerId = this.emailService.saveEmail(emailer);
            emailer.setEmailerId(emailerId);
            this.emailService.sendMail(emailer);
            logger.error("File not found exception occured", e);
        } catch (IOException e) {
            subjectParam = new String[]{"QAT Order Data", "Input/Output error"};
            bodyParam = new String[]{"QAT Order Data", date, "Input/Output error", e.getMessage()};
            emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), toList, ccList, subjectParam, bodyParam);
            int emailerId = this.emailService.saveEmail(emailer);
            emailer.setEmailerId(emailerId);
            this.emailService.sendMail(emailer);
            logger.error("IO exception occured", e);
        } catch (BadSqlGrammarException e) {
            subjectParam = new String[]{"QAT Order Data", "SQL Exception"};
            bodyParam = new String[]{"QAT Order Data", date, "SQL Exception", e.getMessage()};
            emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), toList, ccList, subjectParam, bodyParam);
            int emailerId = this.emailService.saveEmail(emailer);
            emailer.setEmailerId(emailerId);
            this.emailService.sendMail(emailer);
            logger.error("SQL exception occured", e);
        } catch (Exception e) {
            subjectParam = new String[]{"QAT Order Data", e.getClass().getName().toString()};
            bodyParam = new String[]{"QAT Order Data", date, e.getClass().getName().toString(), e.getMessage()};
            emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), toList, ccList, subjectParam, bodyParam);
            int emailerId = this.emailService.saveEmail(emailer);
            emailer.setEmailerId(emailerId);
            this.emailService.sendMail(emailer);
            logger.error("Export QAT Order Data exception occured", e);
        }

    }
}
