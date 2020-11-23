/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.ARTMIS.controller;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.ProgramIntegrationDTO;
import cc.altius.FASP.model.EmailTemplate;
import cc.altius.FASP.model.Emailer;
import cc.altius.FASP.model.ProgramData;
import cc.altius.FASP.service.EmailService;
import cc.altius.FASP.service.ProgramDataService;
import cc.altius.FASP.service.UserService;
import cc.altius.utils.DateUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import static jxl.biff.BaseCellFeatures.logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author akil
 */
@Controller
public class ExportSupplyPlanJson {

    @Autowired
    private ProgramDataService programDataService;
    @Autowired
    private UserService userService;
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

    @GetMapping("/exportSupplyPlan")
    public void exportSupplyPlan(Authentication auth) {
        EmailTemplate emailTemplate = this.emailService.getEmailTemplateByEmailTemplateId(4);
        String[] subjectParam = new String[]{};
        String[] bodyParam = null;
        Emailer emailer = new Emailer();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy hh:mm a");
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        CustomUserDetails curUser = this.userService.getCustomUserByUserId(1); // Super User for the Export
        List<ProgramIntegrationDTO> integrationList = this.programDataService.getSupplyPlanToExportList();
        for (ProgramIntegrationDTO iDto : integrationList) {
            try {
                File directory = new File(QAT_FILE_PATH + EXPORT_SUPPLY_PLAN_FILE_PATH + iDto.getFolderName());
                if (directory.isDirectory()) {
                    ProgramData programData = this.programDataService.getProgramData(iDto.getProgramId(), iDto.getVersionId(), curUser);
                    ObjectMapper mapper = new ObjectMapper();
                    String json;
                    Class<?> viewClass = Class.forName(iDto.getIntegrationViewName());
                    json = mapper
                            .writerWithView(viewClass)
                            .writeValueAsString(programData);
                    String path = directory.getPath() + "/" + iDto.getFinalFileName(curDate);
                    FileWriter fileWriter = new FileWriter(path);
                    fileWriter.write(json);
                    fileWriter.flush();
                    fileWriter.close();
                    logger.info("Export supply plan successful for ProgramId:" + iDto.getProgramId() + " VersionId:" + iDto.getVersionId() + " IntegrationName:" + iDto.getIntegrationName());
                    break;
                } else {
                    subjectParam = new String[]{"supply plan", "Directory does not exists for " + iDto.getIntegrationName()};
                    bodyParam = new String[]{"supply plan", simpleDateFormat.format(curDate), "Directory does not exist for " + iDto.getIntegrationName(), "Directory does not exist for " + iDto.getIntegrationName()};
                    emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), toList, ccList, subjectParam, bodyParam);
                    int emailerId = this.emailService.saveEmail(emailer);
                    emailer.setEmailerId(emailerId);
                    this.emailService.sendMail(emailer);
                    logger.error("Directory does not exists for " + iDto.getIntegrationName());
                }
            } catch (FileNotFoundException e) {
                subjectParam = new String[]{"supply plan", "File not found"};
                bodyParam = new String[]{"supply plan", simpleDateFormat.format(curDate), "File not found", e.getMessage()};
                emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), toList, ccList, subjectParam, bodyParam);
                int emailerId = this.emailService.saveEmail(emailer);
                emailer.setEmailerId(emailerId);
                this.emailService.sendMail(emailer);
                logger.error("File not found exception occured", e);
            } catch (IOException e) {
                subjectParam = new String[]{"supply plan", "Input/Output error"};
                bodyParam = new String[]{"supply plan", simpleDateFormat.format(curDate), "Input/Output error", e.getMessage()};
                emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), toList, ccList, subjectParam, bodyParam);
                int emailerId = this.emailService.saveEmail(emailer);
                emailer.setEmailerId(emailerId);
                this.emailService.sendMail(emailer);
                logger.error("IO exception occured", e);
            } catch (BadSqlGrammarException e) {
                subjectParam = new String[]{"supply plan", "SQL Exception"};
                bodyParam = new String[]{"supply plan", simpleDateFormat.format(curDate), "SQL Exception", e.getMessage()};
                emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), toList, ccList, subjectParam, bodyParam);
                int emailerId = this.emailService.saveEmail(emailer);
                emailer.setEmailerId(emailerId);
                this.emailService.sendMail(emailer);
                logger.error("SQL exception occured", e);
            } catch (Exception e) {
                subjectParam = new String[]{"supply plan", e.getClass().getName().toString()};
                bodyParam = new String[]{"supply plan", simpleDateFormat.format(curDate), e.getClass().getName().toString(), e.getMessage()};
                emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), toList, ccList, subjectParam, bodyParam);
                int emailerId = this.emailService.saveEmail(emailer);
                emailer.setEmailerId(emailerId);
                this.emailService.sendMail(emailer);
                logger.error("Export supply plan exception occured", e);
            }
        }
    }
}
