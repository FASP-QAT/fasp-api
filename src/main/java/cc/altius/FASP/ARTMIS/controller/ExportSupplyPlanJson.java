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
import cc.altius.FASP.model.Views;
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
import org.springframework.web.bind.annotation.ResponseBody;

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
    @Value("${email.exportToList}")
    private String toList;
    @Value("${email.exportCCList}")
    private String ccList;

    @GetMapping("/exportSupplyPlan")
    @ResponseBody
    public String exportSupplyPlan(Authentication auth) {
        String newLine = "<br/>\n";
        StringBuilder sb = new StringBuilder();
        EmailTemplate emailTemplate = this.emailService.getEmailTemplateByEmailTemplateId(4);
        String[] subjectParam = new String[]{};
        String[] bodyParam = null;
        Emailer emailer = new Emailer();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy hh:mm a");
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        CustomUserDetails curUser = this.userService.getCustomUserByUserId(1); // Super User for the Export
        List<ProgramIntegrationDTO> integrationList = this.programDataService.getSupplyPlanToExportList();
        sb.append("Found ").append(integrationList.size()).append(" supply plans to export").append(newLine);
        for (ProgramIntegrationDTO iDto : integrationList) {
            try {
                File directory = new File(QAT_FILE_PATH + EXPORT_SUPPLY_PLAN_FILE_PATH + iDto.getFolderName());
                sb.append("Begining export for ProgramId:").append(iDto.getProgramId()).append(" VersionId:").append(iDto.getVersionId()).append(" Integeration ").append(iDto.getIntegrationName()).append(newLine);
                sb.append("Checking if Directory exists ").append(directory).append(newLine);
                if (directory.isDirectory()) {
                    sb.append("Directory exists ").append(newLine);
                    ProgramData programData = this.programDataService.getProgramData(iDto.getProgramId(), iDto.getVersionId(), curUser, true);
                    sb.append("Got the Program Data").append(newLine);
                    ObjectMapper mapper = new ObjectMapper();
                    String json = "";
                    sb.append("View set for ").append(iDto.getIntegrationViewName()).append(newLine);
                    switch (iDto.getIntegrationViewId()) {
                        case 2:
                            json = mapper
                                    .writerWithView(Views.ArtmisView.class)
                                    .writeValueAsString(programData);
                            break;
                        case 3:
                            json = mapper
                                    .writerWithView(Views.GfpVanView.class)
                                    .writeValueAsString(programData);
                            break;
                    }
                    String path = directory.getPath() + "/" + iDto.getFinalFileName(curDate);
                    sb.append("Absolute path of File ").append(path).append(newLine);
                    FileWriter fileWriter = new FileWriter(path);
                    fileWriter.write(json);
                    fileWriter.flush();
                    fileWriter.close();
                    sb.append("Export completed").append(newLine).append(newLine);
                    logger.info("Export supply plan successful for ProgramId:" + iDto.getProgramId() + " VersionId:" + iDto.getVersionId() + " IntegrationName:" + iDto.getIntegrationName());
                    this.programDataService.updateSupplyPlanAsExported(iDto.getProgramVersionTransId(), iDto.getIntegrationId());
                } else {
                    subjectParam = new String[]{"supply plan", "Directory does not exists for " + iDto.getIntegrationName()};
                    bodyParam = new String[]{"supply plan", simpleDateFormat.format(curDate), "Directory does not exist for " + iDto.getIntegrationName(), "Directory does not exist for " + iDto.getIntegrationName()};
                    emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), toList, ccList, subjectParam, bodyParam);
                    int emailerId = this.emailService.saveEmail(emailer);
                    emailer.setEmailerId(emailerId);
                    this.emailService.sendMail(emailer);
                    sb.append("Directory does not exists for " + iDto.getIntegrationName()).append(newLine).append(newLine);
                    logger.error("Directory does not exists for " + iDto.getIntegrationName());
                }
            } catch (FileNotFoundException e) {
                subjectParam = new String[]{"supply plan", "File not found"};
                bodyParam = new String[]{"supply plan", simpleDateFormat.format(curDate), "File not found", e.getMessage()};
                emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), toList, ccList, subjectParam, bodyParam);
                int emailerId = this.emailService.saveEmail(emailer);
                emailer.setEmailerId(emailerId);
                sb.append("File not found exception occured").append(newLine).append(newLine);
                logger.error("File not found exception occured", e);
            } catch (IOException e) {
                subjectParam = new String[]{"supply plan", "Input/Output error"};
                bodyParam = new String[]{"supply plan", simpleDateFormat.format(curDate), "Input/Output error", e.getMessage()};
                emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), toList, ccList, subjectParam, bodyParam);
                int emailerId = this.emailService.saveEmail(emailer);
                emailer.setEmailerId(emailerId);
                this.emailService.sendMail(emailer);
                sb.append("IO exception occured").append(newLine).append(newLine);
                logger.error("IO exception occured", e);
            } catch (BadSqlGrammarException e) {
                subjectParam = new String[]{"supply plan", "SQL Exception"};
                bodyParam = new String[]{"supply plan", simpleDateFormat.format(curDate), "SQL Exception", e.getMessage()};
                emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), toList, ccList, subjectParam, bodyParam);
                int emailerId = this.emailService.saveEmail(emailer);
                emailer.setEmailerId(emailerId);
                this.emailService.sendMail(emailer);
                sb.append("SQL exception occured").append(newLine).append(newLine);
                logger.error("SQL exception occured", e);
            } catch (Exception e) {
                subjectParam = new String[]{"supply plan", e.getClass().getName().toString()};
                bodyParam = new String[]{"supply plan", simpleDateFormat.format(curDate), e.getClass().getName().toString(), e.getMessage()};
                emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), toList, ccList, subjectParam, bodyParam);
                int emailerId = this.emailService.saveEmail(emailer);
                emailer.setEmailerId(emailerId);
                this.emailService.sendMail(emailer);
                sb.append("Export supply plan exception occured").append(newLine).append(newLine);
                logger.error("Export supply plan exception occured", e);
            }
        }
        return sb.toString();
    }
}
