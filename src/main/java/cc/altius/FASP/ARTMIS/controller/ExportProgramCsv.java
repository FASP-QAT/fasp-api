/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.ARTMIS.controller;

import cc.altius.FASP.ARTMIS.service.ExportArtmisDataService;
import cc.altius.FASP.model.DTO.ExportProgramDataDTO;
import cc.altius.FASP.model.EmailTemplate;
import cc.altius.FASP.model.Emailer;
import cc.altius.FASP.service.EmailService;
import cc.altius.utils.DateUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
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
public class ExportProgramCsv {

    @Autowired
    private ExportArtmisDataService exportArtmisDataService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private EmailService emailService;
    @Value("${qat.filePath}")
    private String QAT_FILE_PATH;
    @Value("${exportSupplyPlanFilePath}")
    private String EXPORT_SUPPLY_PLAN_FILE_PATH;

    @RequestMapping(value = "exportProductData")
//    @Scheduled(cron = "00 */05 * * * *")
    public void exportProductData() {
        EmailTemplate emailTemplate = this.emailService.getEmailTemplateByEmailTemplateId(4);
        String[] subjectParam = new String[]{};
        String[] bodyParam = null;
        Emailer emailer = new Emailer();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy hh:mm a");
        String date = simpleDateFormat.format(DateUtils.getCurrentDateObject(DateUtils.EST));
        try {

            String curDate = DateUtils.getCurrentDateString(DateUtils.EST, DateUtils.YMD);
            String path, json;
            List<ExportProgramDataDTO> exportProgramData = this.exportArtmisDataService.exportProgramData();
            System.out.println("exportProgramData---" + exportProgramData);

            File directory = new File(QAT_FILE_PATH+EXPORT_SUPPLY_PLAN_FILE_PATH);

            if (directory.isDirectory()) {
                Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                Type typeList = new TypeToken<List<ExportProgramDataDTO>>() {
                }.getType();
                json = gson.toJson(exportProgramData, typeList);
                path = QAT_FILE_PATH+EXPORT_SUPPLY_PLAN_FILE_PATH + "QAT_Programs_" + curDate + ".csv";
                FileWriter fileWriter = new FileWriter(path);
                fileWriter.write(json);
                fileWriter.flush();
                fileWriter.close();
                logger.info("Export qat program data successful");
            } else {
                subjectParam = new String[]{"QAT Program Data", "Directory does not exists"};
                bodyParam = new String[]{"QAT Program Data", date, "Directory does not exists", "Directory does not exists"};
                emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), "anchal.c@altius.cc,shubham.y@altius.cc,priti.p@altius.cc,sameer.g@altiusbpo.com", "shubham.y@altius.cc,priti.p@altius.cc,sameer.g@altiusbpo.com", subjectParam, bodyParam);
                int emailerId = this.emailService.saveEmail(emailer);
                emailer.setEmailerId(emailerId);
                this.emailService.sendMail(emailer);
                logger.error("Directory does not exists");
            }
        } catch (FileNotFoundException e) {
            subjectParam = new String[]{"QAT Program Data", "File not found"};
            bodyParam = new String[]{"QAT Program Data", date, "File not found", e.getMessage()};
            emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), "anchal.c@altius.cc,shubham.y@altius.cc,priti.p@altius.cc,sameer.g@altiusbpo.com", "shubham.y@altius.cc,priti.p@altius.cc,sameer.g@altiusbpo.com", subjectParam, bodyParam);
            int emailerId = this.emailService.saveEmail(emailer);
            emailer.setEmailerId(emailerId);
            this.emailService.sendMail(emailer);
            logger.error("File not found exception occured", e);
        } catch (IOException e) {
            subjectParam = new String[]{"QAT Program Data", "Input/Output error"};
            bodyParam = new String[]{"QAT Program Data", date, "Input/Output error", e.getMessage()};
            emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), "anchal.c@altius.cc", "shubham.y@altius.cc,priti.p@altius.cc,sameer.g@altiusbpo.com", subjectParam, bodyParam);
            int emailerId = this.emailService.saveEmail(emailer);
            emailer.setEmailerId(emailerId);
            this.emailService.sendMail(emailer);
            logger.error("IO exception occured", e);
        } catch (BadSqlGrammarException e) {
            subjectParam = new String[]{"QAT Program Data", "SQL Exception"};
            bodyParam = new String[]{"QAT Program Data", date, "SQL Exception", e.getMessage()};
            emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), "anchal.c@altius.cc", "shubham.y@altius.cc,priti.p@altius.cc,sameer.g@altiusbpo.com", subjectParam, bodyParam);
            int emailerId = this.emailService.saveEmail(emailer);
            emailer.setEmailerId(emailerId);
            this.emailService.sendMail(emailer);
            logger.error("SQL exception occured", e);
        } catch (Exception e) {
            subjectParam = new String[]{"QAT Program Data", e.getClass().getName().toString()};
            bodyParam = new String[]{"QAT Program Data", date, e.getClass().getName().toString(), e.getMessage()};
            emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), "anchal.c@altius.cc,shubham.y@altius.cc,priti.p@altius.cc,sameer.g@altiusbpo.com", "shubham.y@altius.cc,priti.p@altius.cc,sameer.g@altiusbpo.com", subjectParam, bodyParam);
            int emailerId = this.emailService.saveEmail(emailer);
            emailer.setEmailerId(emailerId);
            this.emailService.sendMail(emailer);
            logger.error("Export QAT Program Data exception occured", e);
        }

    }
}
