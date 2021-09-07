/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.exception.CouldNotSaveException;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ProgramData;
import cc.altius.FASP.model.ProgramIdAndVersionId;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.model.SupplyPlanCommitRequest;
import cc.altius.FASP.model.UpdateProgramVersion;
import cc.altius.FASP.model.Views;
import cc.altius.FASP.model.report.SupplyPlanCommitRequestInput;
import cc.altius.FASP.service.ProgramDataService;
import cc.altius.FASP.service.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author akil
 */
@RestController
@RequestMapping("/api")
public class ProgramDataRestController {

    private final Logger logger = LoggerFactory.getLogger(ProgramDataRestController.class);

    @Autowired
    private ProgramDataService programDataService;
    @Autowired
    private UserService userService;
//    @Autowired
//    private EmailService emailService;
//    @Value("${qat.filePath}")
//    private String QAT_FILE_PATH;
//    @Value("${exportSupplyPlanFilePath}")
//    private String EXPORT_SUPPLY_PLAN_FILE_PATH;
//    @Value("${email.toList}")
//    private String toList;
//    @Value("${email.ccList}")
//    private String ccList;

    @JsonView(Views.InternalView.class)
    @GetMapping("/programData/programId/{programId}/versionId/{versionId}")
    public ResponseEntity getProgramData(@PathVariable(value = "programId", required = true) int programId, @PathVariable(value = "versionId", required = true) int versionId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.programDataService.getProgramData(programId, versionId, curUser, false), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to get ProgramData", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to get ProgramData", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to get ProgramData", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @JsonView(Views.InternalView.class)
    @PostMapping("/programData")
    public ResponseEntity getLoadProgramData(@RequestBody List<ProgramIdAndVersionId> programVersionList, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.programDataService.getProgramData(programVersionList, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to get ProgramData", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to get ProgramData", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to get ProgramData", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @JsonView(Views.ArtmisView.class)
    @GetMapping("/programData/artmis/programId/{programId}/versionId/{versionId}")
    public ResponseEntity getProgramDataArtmis(@PathVariable(value = "programId", required = true) int programId, @PathVariable(value = "versionId", required = true) int versionId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.programDataService.getProgramData(programId, versionId, curUser, false), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to get ProgramData", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to get ProgramData", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to get ProgramData", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @JsonView(Views.GfpVanView.class)
    @GetMapping("/programData/gfpvan/programId/{programId}/versionId/{versionId}")
    public ResponseEntity getProgramDataGfpVan(@PathVariable(value = "programId", required = true) int programId, @PathVariable(value = "versionId", required = true) int versionId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.programDataService.getProgramData(programId, versionId, curUser, false), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to get ProgramData", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to get ProgramData", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to get ProgramData", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Part 1 of the Commit Request
    @PutMapping("/programData")
    public ResponseEntity putProgramData(@RequestBody ProgramData programData, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            int commitRequestId = this.programDataService.saveProgramData(programData, curUser);
            return new ResponseEntity(commitRequestId, HttpStatus.OK);
//            this.programDataService.getProgramData(programData.getProgramId(), v.getVersionId(), curUser,false)
        } catch (CouldNotSaveException e) {
            logger.error("Error while trying to update ProgramData", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.PRECONDITION_FAILED);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to update ProgramData", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to update ProgramData", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to update ProgramData", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Part 2 of the Commit Request
//    @GetMapping("/processCommitRequest")
    //sec min hour day_of_month month day_of_week
    @Scheduled(cron = "00 */5 * * * *")
    public ResponseEntity processCommitRequest() {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(1);
            this.programDataService.processCommitRequest(curUser);
            return new ResponseEntity(HttpStatus.OK);
//        } catch (CouldNotSaveException e) {
//            logger.error("Error while trying to processCommitRequest", e);
//            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.PRECONDITION_FAILED);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to processCommitRequest", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to processCommitRequest", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to processCommitRequest", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/getCommitRequest/{requestStatus}")
    public ResponseEntity getProgramDataCommitRequest(@RequestBody SupplyPlanCommitRequestInput spcr, @PathVariable(value = "requestStatus", required = true) int requestStatus, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            List<SupplyPlanCommitRequest> spcrList = this.programDataService.getSupplyPlanCommitRequestList(spcr, requestStatus, curUser);
            return new ResponseEntity(spcrList, HttpStatus.OK);
//            this.programDataService.getProgramData(programData.getProgramId(), v.getVersionId(), curUser,false)
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to get SupplyPlanCommitRequest list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to get SupplyPlanCommitRequest list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/versionType")
    public ResponseEntity getVersionType(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.programDataService.getVersionTypeList(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to get Version Type", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/versionStatus")
    public ResponseEntity getVersionStatus(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.programDataService.getVersionStatusList(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to get Version Status", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/programVersion/programId/{programId}/versionId/{versionId}/realmCountryId/{realmCountryId}/healthAreaId/{healthAreaId}/organisationId/{organisationId}/versionTypeId/{versionTypeId}/versionStatusId/{versionStatusId}/dates/{startDate}/{stopDate}")
    public ResponseEntity getProgramVersionList(
            @PathVariable(value = "programId", required = true) int programId,
            @PathVariable(value = "versionId", required = true) int versionId,
            @PathVariable(value = "realmCountryId", required = true) int realmCountryId,
            @PathVariable(value = "healthAreaId", required = true) int healthAreaId,
            @PathVariable(value = "organisationId", required = true) int organisationId,
            @PathVariable(value = "versionTypeId", required = true) int versionTypeId,
            @PathVariable(value = "versionStatusId", required = true) int versionStatusId,
            @PathVariable(value = "startDate", required = true) String startDate,
            @PathVariable(value = "stopDate", required = true) String stopDate,
            Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.programDataService.getProgramVersionList(programId, versionId, realmCountryId, healthAreaId, organisationId, versionTypeId, versionStatusId, startDate, stopDate, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to get ProgramData", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to get ProgramData", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to get ProgramData", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/programVersion/programId/{programId}/versionId/{versionId}/versionStatusId/{versionStatusId}")
    public ResponseEntity updateProgramVersion(@RequestBody UpdateProgramVersion updateProgramVersion, @PathVariable(value = "programId", required = true) int programId, @PathVariable(value = "versionId", required = true) int versionId, @PathVariable(value = "versionStatusId", required = true) int versionStatusId, Authentication auth) {
//        EmailTemplate emailTemplate = this.emailService.getEmailTemplateByEmailTemplateId(4);
//        String[] subjectParam = new String[]{};
//        String[] bodyParam = null;
//        Emailer emailer = new Emailer();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy hh:mm a");
//        String date = simpleDateFormat.format(DateUtils.getCurrentDateObject(DateUtils.EST));
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            //Generate supply plan files
//            if (versionStatusId == 2) {
//                File directory = new File(QAT_FILE_PATH + EXPORT_SUPPLY_PLAN_FILE_PATH);
//                if (directory.isDirectory()) {
//                    try {
//                        ProgramData programData = this.programDataService.getProgramData(programId, versionId, curUser);
//                        ObjectMapper mapper = new ObjectMapper();
//                        String json = mapper
//                                .writerWithView(Views.ArtmisView.class)
//                                .writeValueAsString(programData);
//                        System.out.println("json---" + json);
//                        String path = QAT_FILE_PATH + EXPORT_SUPPLY_PLAN_FILE_PATH + "QAT_SupplyPlan_" + StringUtils.leftPad(Integer.toString(programData.getProgramId()), 8, "0") + ".json";
//                        FileWriter fileWriter = new FileWriter(path);
//                        fileWriter.write(json);
//                        fileWriter.flush();
//                        fileWriter.close();
//                        logger.info("Export supply plan successful");
//                    } catch (FileNotFoundException e) {
//                        subjectParam = new String[]{"supply plan", "File not found"};
//                        bodyParam = new String[]{"supply plan", date, "File not found", e.getMessage()};
//                        emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), toList, ccList, subjectParam, bodyParam);
//                        int emailerId = this.emailService.saveEmail(emailer);
//                        emailer.setEmailerId(emailerId);
//                        this.emailService.sendMail(emailer);
//                        logger.error("File not found exception occured", e);
//                    } catch (IOException e) {
//                        subjectParam = new String[]{"supply plan", "Input/Output error"};
//                        bodyParam = new String[]{"supply plan", date, "Input/Output error", e.getMessage()};
//                        emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), toList, ccList, subjectParam, bodyParam);
//                        int emailerId = this.emailService.saveEmail(emailer);
//                        emailer.setEmailerId(emailerId);
//                        this.emailService.sendMail(emailer);
//                        logger.error("IO exception occured", e);
//                    } catch (BadSqlGrammarException e) {
//                        subjectParam = new String[]{"supply plan", "SQL Exception"};
//                        bodyParam = new String[]{"supply plan", date, "SQL Exception", e.getMessage()};
//                        emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), toList, ccList, subjectParam, bodyParam);
//                        int emailerId = this.emailService.saveEmail(emailer);
//                        emailer.setEmailerId(emailerId);
//                        this.emailService.sendMail(emailer);
//                        logger.error("SQL exception occured", e);
//                    } catch (Exception e) {
//                        subjectParam = new String[]{"supply plan", e.getClass().getName().toString()};
//                        bodyParam = new String[]{"supply plan", date, e.getClass().getName().toString(), e.getMessage()};
//                        emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), toList, ccList, subjectParam, bodyParam);
//                        int emailerId = this.emailService.saveEmail(emailer);
//                        emailer.setEmailerId(emailerId);
//                        this.emailService.sendMail(emailer);
//                        logger.error("Export supply plan exception occured", e);
//                    }
//                } else {
//                    subjectParam = new String[]{"supply plan", "Directory does not exists"};
//                    bodyParam = new String[]{"supply plan", date, "Directory does not exists", "Directory does not exists"};
//                    emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), toList, ccList, subjectParam, bodyParam);
//                    int emailerId = this.emailService.saveEmail(emailer);
//                    emailer.setEmailerId(emailerId);
//                    this.emailService.sendMail(emailer);
//                    logger.error("Directory does not exists");
//                }
//
//            }
            return new ResponseEntity(this.programDataService.updateProgramVersion(programId, versionId, versionStatusId, updateProgramVersion.getNotes(), curUser, updateProgramVersion.getReviewedProblemList()), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to update ProgramVersion", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to update ProgramVersion", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to update ProgramVersion", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/programData/checkErpOrder/orderNo/{orderNo}/primeLineNo/{primeLineNo}/realmCountryId/{realmCountryId}/planningUnitId/{planningUnitId}")
    public ResponseEntity checkErpOrder(
            @PathVariable(value = "orderNo", required = true) String orderNo,
            @PathVariable(value = "primeLineNo", required = true) String primeLineNo,
            @PathVariable(value = "realmCountryId", required = true) int realmCountryId,
            @PathVariable(value = "planningUnitId", required = true) int planningUnitId,
            Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.programDataService.checkErpOrder(orderNo, primeLineNo, realmCountryId, planningUnitId), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to update ProgramVersion", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/programData/shipmentSync/programId/{programId}/versionId/{versionId}/userId/{userId}/lastSyncDate/{lastSyncDate}")
    public ResponseEntity shipmentSync(@PathVariable(value = "programId", required = true) int programId, @PathVariable(value = "versionId", required = true) int versionId, @PathVariable(value = "userId", required = true) int userId, @PathVariable("lastSyncDate") String lastSyncDate, Authentication auth) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sdf.parse(lastSyncDate);
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.programDataService.getShipmentListForSync(programId, versionId, userId, lastSyncDate, curUser), HttpStatus.OK);
        } catch (ParseException p) {
            logger.error("Error while getting Sync list for Shipments", p);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.PRECONDITION_FAILED);
        } catch (Exception e) {
            logger.error("Error while getting Sync list for Shipments", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Sample JSON
     * [{"programId":2535,"versionId":3},{"programId":2001,"versionId":5}]
     *
     * @param programVersionList
     * @param auth
     * @return
     */
    @PostMapping("/programData/checkNewerVersions")
    public ResponseEntity checkNewerVersions(@RequestBody List<ProgramIdAndVersionId> programVersionList, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.programDataService.checkNewerVersions(programVersionList, curUser), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to check ProgramVersion", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to check ProgramVersion", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/programData/getLatestVersionForProgram/{programId}")
    public ResponseEntity getLatestVersionForProgram(@PathVariable(value = "programId", required = true) int programId) {
        try {
            return new ResponseEntity(this.programDataService.getLatestVersionForProgram(programId), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to get latest version for program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to get latest version for program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/programData/getLastModifiedDateForProgram/{programId}/{versionId}")
    public ResponseEntity getLastModifiedDateForProgram(@PathVariable(value = "programId", required = true) int programId, @PathVariable(value = "versionId", required = true) int versionId) {
        try {
            return new ResponseEntity(this.programDataService.getLastModifiedDateForProgram(programId, versionId), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to get last modified date for program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to get last modified date for program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/programData/checkIfCommitRequestExistsForProgram/{programId}")
    public ResponseEntity checkIfCommitRequestExistsForProgram(@PathVariable(value = "programId", required = true) int programId) {
        try {
            return new ResponseEntity(this.programDataService.checkIfCommitRequestExistsForProgram(programId), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to check if commit request exists for program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to check if commit request exists for program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
