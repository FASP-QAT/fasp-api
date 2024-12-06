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
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

/**
 *
 * @author altius
 */
@Controller
@Tag(
    name = "Export Order IDs",
    description = "Export Order IDs to CSV"
)
public class ExportOrderIdsCsv {

    @Autowired
    private ExportArtmisDataService exportArtmisDataService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private EmailService emailService;
    @Value("${qat.homeFolder}")
    private String QAT_FILE_PATH;
    @Value("${qat.exportSupplyPlanFilePath}")
    private String EXPORT_SUPPLY_PLAN_FILE_PATH;
    @Value("${email.exportToList}")
    private String toList;
    @Value("${email.exportCCList}")
    private String ccList;

    @RequestMapping(value = "/exportOrderData")
    @ResponseBody
    @Operation(summary = "Export order data", description = "Export order data")
//    @Scheduled(cron = "0 0 21 * * MON-FRI",zone="EST")
//    @Scheduled(cron = "00 */02 * * * *")
    public String exportOrderData() {
        StringBuilder sb = new StringBuilder("");
        String br = "<br/>";
        logger.info("-------------- Export QAT Orders job started ---------------");
        sb.append("-------------- Export QAT Orders job started ---------------").append(br);
        EmailTemplate emailTemplate = this.emailService.getEmailTemplateByEmailTemplateId(4);
        String[] subjectParam = new String[]{};
        String[] bodyParam = null;
        Emailer emailer = new Emailer();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy hh:mm a");
        String date = simpleDateFormat.format(DateUtils.getCurrentDateObject(DateUtils.EST));
        try {
            String curDate = DateUtils.getCurrentDateString(DateUtils.EST, DateUtils.YMD);
            String path;
            Date lastDate;
            lastDate = this.exportArtmisDataService.getLastDate("ARTMIS", "QAT_Orders");
            if (lastDate == null) {
                lastDate = DateUtils.getDateFromString("2020-01-01 00:00:00", DateUtils.YMDHMS);
            }
            List<ExportOrderDataDTO> exportOrderDataDT = this.exportArtmisDataService.exportOrderData(lastDate);
            logger.info("Found " + exportOrderDataDT.size() + " records");
            sb.append("Found " + exportOrderDataDT.size() + " records").append(br);
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
                int cnt = 0;
                Date maxDate = lastDate;
                for (ExportOrderDataDTO e : exportOrderDataDT) {
                    fileWriter.append(Integer.toString(e.getShipmentId()));
                    fileWriter.append(',');
                    fileWriter.append(e.getSkuCode());
                    fileWriter.append(',');
                    fileWriter.append(Integer.toString(e.getProgramId()));
                    fileWriter.append(',');
                    fileWriter.append(e.getProcurementAgentCode());
                    fileWriter.append(',');
                    fileWriter.append(Long.toString(e.getShipmentQty()));
                    fileWriter.append(',');
                    fileWriter.append(DateUtils.formatDate(e.getExpectedDeliveryDate(), DateUtils.YMD));
                    fileWriter.append(',');
                    fileWriter.append(Integer.toString(e.getTracerCategoryId()));
                    fileWriter.append(',');
                    fileWriter.append(e.getTracerCategoryDesc());
                    fileWriter.append(',');
                    fileWriter.append((e.isActive() ? "1" : "0"));
                    fileWriter.append('\n');
                    cnt++;
                    if (maxDate == null) {
                        maxDate = e.getLastModifiedDate();
                    } else {
                        if (DateUtils.compareDate(e.getLastModifiedDate(), maxDate) > 0) {
                            maxDate = e.getLastModifiedDate();
                        }
                    }
                }
                fileWriter.flush();
                fileWriter.close();
                logger.info(cnt + " records written to the file");
                sb.append(cnt).append(" records written to the file").append(br);
                this.exportArtmisDataService.updateLastDate("ARTMIS", "QAT_Orders", maxDate);
                logger.info("Export QAT Orders job successfully completed");
                sb.append("Export QAT Orders job successfully completed").append(br);
            } else {
                subjectParam = new String[]{"QAT Order Data", "Directory does not exists"};
                bodyParam = new String[]{"QAT Order Data", date, "Directory does not exists", "Directory does not exists"};
                emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), toList, ccList, "", subjectParam, bodyParam);
                int emailerId = this.emailService.saveEmail(emailer);
                emailer.setEmailerId(emailerId);
                this.emailService.sendMail(emailer);
                logger.error("Directory does not exists");
                sb.append("Directory does not exists").append(br);
            }
        } catch (FileNotFoundException e) {
            subjectParam = new String[]{"QAT Order Data", "File not found"};
            bodyParam = new String[]{"QAT Order Data", date, "File not found", e.getMessage()};
            emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), toList, ccList, "", subjectParam, bodyParam);
            int emailerId = this.emailService.saveEmail(emailer);
            emailer.setEmailerId(emailerId);
            this.emailService.sendMail(emailer);
            logger.error("File not found exception occured", e);
            sb.append("File not found exception occured").append(br).append(e.getMessage()).append(br);
        } catch (IOException e) {
            subjectParam = new String[]{"QAT Order Data", "Input/Output error"};
            bodyParam = new String[]{"QAT Order Data", date, "Input/Output error", e.getMessage()};
            emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), toList, ccList, "", subjectParam, bodyParam);
            int emailerId = this.emailService.saveEmail(emailer);
            emailer.setEmailerId(emailerId);
            this.emailService.sendMail(emailer);
            logger.error("IO exception occured", e);
            sb.append("IO exception occured").append(br).append(e.getMessage()).append(br);
        } catch (BadSqlGrammarException e) {
            subjectParam = new String[]{"QAT Order Data", "SQL Exception"};
            bodyParam = new String[]{"QAT Order Data", date, "SQL Exception", e.getMessage()};
            emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), toList, ccList, "", subjectParam, bodyParam);
            int emailerId = this.emailService.saveEmail(emailer);
            emailer.setEmailerId(emailerId);
            this.emailService.sendMail(emailer);
            logger.error("SQL exception occured", e);
            sb.append("SQL exception occured").append(br).append(e.getMessage()).append(br);
        } catch (Exception e) {
            subjectParam = new String[]{"QAT Order Data", e.getClass().getName().toString()};
            bodyParam = new String[]{"QAT Order Data", date, e.getClass().getName().toString(), e.getMessage()};
            emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), toList, ccList, "", subjectParam, bodyParam);
            int emailerId = this.emailService.saveEmail(emailer);
            emailer.setEmailerId(emailerId);
            this.emailService.sendMail(emailer);
            logger.error("Export QAT Order Data exception occured", e);
            sb.append("Export QAT Order Data exception occured").append(br).append(e.getMessage()).append(br);
        }
        return sb.toString();
    }
}
