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
import cc.altius.FASP.model.ManualIntegration;
import cc.altius.FASP.model.ProgramData;
import cc.altius.FASP.model.Views;
import cc.altius.FASP.service.EmailService;
import cc.altius.FASP.service.IntegrationProgramService;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

/**
 *
 * @author akil
 */
@Controller
@Tag(
    name = "Export Supply Plan",
    description = "Export Supply Plan to JSON"
)
public class ExportSupplyPlanJson {

    @Autowired
    private ProgramDataService programDataService;
    @Autowired
    private IntegrationProgramService integrationProgramService;
    @Autowired
    private UserService userService;
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
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/exportSupplyPlan")
    @ResponseBody
    @Operation(summary = "Export supply plan", description = "Export supply plan")
    public String exportSupplyPlan(Authentication auth) {
        logger.info(" ################ Going to start Supply Plan export process ############## ");
        String newLine = "<br/>\n";
        StringBuilder sb = new StringBuilder();
        EmailTemplate emailTemplate = this.emailService.getEmailTemplateByEmailTemplateId(4);
        String[] subjectParam = new String[]{};
        String[] bodyParam = null;
        Emailer emailer = new Emailer();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy hh:mm a");
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        CustomUserDetails curUser = this.userService.getCustomUserByUserId(1); // Super User for the Export
        logger.info("About to pull the Supply plan list that need to be exported");
        List<ProgramIntegrationDTO> integrationList = this.programDataService.getSupplyPlanToExportList();
        logger.info("Found " + integrationList.size() + " supply plans");
        sb.append("Found ").append(integrationList.size()).append(" supply plans to export").append(newLine);
        for (ProgramIntegrationDTO iDto : integrationList) {
            try {
                logger.info("Creating file and folder name");
                logger.info("Starting export for " + iDto);
                File directory = new File(QAT_FILE_PATH + EXPORT_SUPPLY_PLAN_FILE_PATH + iDto.getFolderName());
                logger.info("Folder created");
                sb.append("Begining export for ProgramId:").append(iDto.getProgramId()).append(" VersionId:").append(iDto.getVersionId()).append(" Integeration ").append(iDto.getIntegrationName()).append(newLine);
                sb.append("Checking if Directory exists ").append(directory).append(newLine);
                logger.info("Checking if directory exists");
                if (directory.isDirectory()) {
                    sb.append("Directory exists ").append(newLine);
                    logger.info("Directory exists");
                    ProgramData programData = this.programDataService.getProgramData(iDto.getProgramId(), iDto.getVersionId(), curUser, true, false);
                    sb.append("Got the Program Data").append(newLine);
                    logger.info("Got the Program Data");
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
                    logger.info("Export completed");
                    logger.info("Export supply plan successful for ProgramId:" + iDto.getProgramId() + " VersionId:" + iDto.getVersionId() + " IntegrationName:" + iDto.getIntegrationName());
                    if (this.programDataService.updateSupplyPlanAsExported(iDto.getProgramVersionTransId(), iDto.getIntegrationId())) {
                        logger.info("Integration marked as Completed");
                    } else {
                        logger.info("Integration could not be marked as Completed, will be triggered again in the next cycle");
                    }
                } else {
                    subjectParam = new String[]{"supply plan", "Directory does not exists for " + iDto.getIntegrationName()};
                    bodyParam = new String[]{"supply plan", simpleDateFormat.format(curDate), "Directory does not exist for " + iDto.getIntegrationName(), "Directory does not exist for " + iDto.getIntegrationName()};
                    emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), toList, ccList, "", subjectParam, bodyParam);
                    int emailerId = this.emailService.saveEmail(emailer);
                    emailer.setEmailerId(emailerId);
                    this.emailService.sendMail(emailer);
                    sb.append("Directory does not exists for " + iDto.getIntegrationName()).append(newLine).append(newLine);
                    logger.error("Directory does not exists for " + iDto.getIntegrationName());
                }
            } catch (FileNotFoundException e) {
                subjectParam = new String[]{"supply plan", "File not found"};
                bodyParam = new String[]{"supply plan", simpleDateFormat.format(curDate), "File not found", e.getMessage()};
                emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), toList, ccList, "", subjectParam, bodyParam);
                int emailerId = this.emailService.saveEmail(emailer);
                emailer.setEmailerId(emailerId);
                sb.append("File not found exception occured").append(newLine).append(newLine);
                logger.error("File not found exception occured", e);
            } catch (IOException e) {
                subjectParam = new String[]{"supply plan", "Input/Output error"};
                bodyParam = new String[]{"supply plan", simpleDateFormat.format(curDate), "Input/Output error", e.getMessage()};
                emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), toList, ccList, "", subjectParam, bodyParam);
                int emailerId = this.emailService.saveEmail(emailer);
                emailer.setEmailerId(emailerId);
                this.emailService.sendMail(emailer);
                sb.append("IO exception occured").append(newLine).append(newLine);
                logger.error("IO exception occured", e);
            } catch (BadSqlGrammarException e) {
                subjectParam = new String[]{"supply plan", "SQL Exception"};
                bodyParam = new String[]{"supply plan", simpleDateFormat.format(curDate), "SQL Exception", e.getMessage()};
                emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), toList, ccList, "", subjectParam, bodyParam);
                int emailerId = this.emailService.saveEmail(emailer);
                emailer.setEmailerId(emailerId);
                this.emailService.sendMail(emailer);
                sb.append("SQL exception occured").append(newLine).append(newLine);
                logger.error("SQL exception occured", e);
            } catch (Exception e) {
                subjectParam = new String[]{"supply plan", e.getClass().getName().toString()};
                bodyParam = new String[]{"supply plan", simpleDateFormat.format(curDate), e.getClass().getName().toString(), e.getMessage()};
                emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), toList, ccList, "", subjectParam, bodyParam);
                int emailerId = this.emailService.saveEmail(emailer);
                emailer.setEmailerId(emailerId);
                this.emailService.sendMail(emailer);
                sb.append("Export supply plan exception occured").append(newLine).append(newLine);
                logger.error("Export supply plan exception occured", e);
            }
        }
        logger.info(" #################### Completed Export process ####################### ");
        return sb.toString();
    }
    
    @GetMapping("/exportManualJson")
    @ResponseBody
    @Operation(summary = "Export manual supply plan json", description = "Export manual supply plan json")
    public String exportManualJson(Authentication auth) {
        logger.info(" ################ Going to start Manual Json export process ############## ");
        String newLine = "<br/>\n";
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy hh:mm a");
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        CustomUserDetails curUser = this.userService.getCustomUserByUserId(1); // Super User for the Export
        logger.info("About to pull the Manual Json list that need to be exported");
        List<ManualIntegration> integrationList = this.integrationProgramService.getManualJsonPushForScheduler();
        logger.info("Found " + integrationList.size() + " manual jsons");
        sb.append("Found ").append(integrationList.size()).append(" manual jsons to export").append(newLine);
        for (ManualIntegration iDto : integrationList) {
            try {
                logger.info("Creating file and folder name");
                logger.info("Starting export for " + iDto);
                File directory = new File(QAT_FILE_PATH + EXPORT_SUPPLY_PLAN_FILE_PATH + iDto.getFolderName());
                logger.info("Folder created");
                sb.append("Begining export for ProgramId:").append(iDto.getProgram().getId()).append(" VersionId:").append(iDto.getVersionId()).append(" Integeration ").append(iDto.getIntegrationName()).append(newLine);
                sb.append("Checking if Directory exists ").append(directory).append(newLine);
                logger.info("Checking if directory exists");
                if (directory.isDirectory()) {
                    sb.append("Directory exists ").append(newLine);
                    logger.info("Directory exists");
                    ProgramData programData = this.programDataService.getProgramData(iDto.getProgram().getId(), iDto.getVersionId(), curUser, true, false);
                    sb.append("Got the Program Data").append(newLine);
                    logger.info("Got the Program Data");
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
                    logger.info("Export completed");
                    logger.info("Export manual json successful for ProgramId:" + iDto.getProgram().getId() + " VersionId:" + iDto.getVersionId() + " IntegrationName:" + iDto.getIntegrationName());
                    this.integrationProgramService.updateManualIntegrationProgramAsProcessed(iDto.getManualIntegrationId());
                } else {
                    sb.append("Directory does not exists for " + iDto.getIntegrationName()).append(newLine).append(newLine);
                    logger.error("Directory does not exists for " + iDto.getIntegrationName());
                }
            } catch (FileNotFoundException e) {
                sb.append("File not found exception occured").append(newLine).append(newLine);
                logger.error("File not found exception occured", e);
            } catch (IOException e) {
                sb.append("IO exception occured").append(newLine).append(newLine);
                logger.error("IO exception occured", e);
            } catch (BadSqlGrammarException e) {
                sb.append("SQL exception occured").append(newLine).append(newLine);
                logger.error("SQL exception occured", e);
            } catch (Exception e) {
                sb.append("Export Manual json exception occured").append(newLine).append(newLine);
                logger.error("Export Manual josn exception occured", e);
            }
        }
        logger.info(" #################### Completed Manual Json Export process ####################### ");
        return sb.toString();
    }
}
