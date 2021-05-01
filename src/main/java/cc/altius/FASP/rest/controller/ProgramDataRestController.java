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
import cc.altius.FASP.model.UpdateProgramVersion;
import cc.altius.FASP.model.Version;
import cc.altius.FASP.model.Views;
import cc.altius.FASP.service.ProgramDataService;
import cc.altius.FASP.service.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    /**
     * API used to get ProgramData for ProgramId and VersionId
     *
     * @param programId programId that you want the ProgramData for
     * @param versionId versionId that you want the ProgramData for
     * @param auth
     * @return return the ProgramData object based on specified ProgramId and
     * VersionId
     */
    @JsonView(Views.InternalView.class)
    @GetMapping("/programData/programId/{programId}/versionId/{versionId}")
    @Operation(description = "API used to get ProgramData for ProgramId and VersionId", summary = "Get ProgramData for a ProgramId and VersionId", tags = ("programData"))
    @Parameters({
        @Parameter(name = "programId", description = "programId that you want to the ProgramData for"),
        @Parameter(name = "versionId", description = "versionId that you want to the ProgramData for")})
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the ProgramData")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the ProgramData specified does not exist")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of ProgramData")
    public ResponseEntity getProgramData(@PathVariable(value = "programId", required = true) int programId, @PathVariable(value = "versionId", required = true) int versionId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.programDataService.getProgramData(programId, versionId, curUser), HttpStatus.OK);
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

    /**
     * API used to get ProgramData list for listed ProgramIds and VersionIds
     *
     * @param programVersionList programVersionList list of ProgramIds and
     * VersionIds that you want a ProgramData list for
     * @param auth
     * @return return the ProgramData list for listed ProgramIds and VersionIds
     * VersionId
     */
    @JsonView(Views.InternalView.class)
    @PostMapping("/programData")
    @Operation(description = "API used to get ProgramData list for listed ProgramIds and VersionIds", summary = "Get ProgramData list for listed ProgramIds and VersionIds", tags = ("programData"))
    @Parameters(
            @Parameter(name = "programVersionList", description = "programVersionList list of ProgramIds and VersionIds that you want a ProgramData list for"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the ProgramData list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the ProgramData specified does not exist")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of ProgramData")
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

    /**
     * API used to get Artmis view ProgramData list for given ProgramId and
     * VersionId
     *
     * @param programId programId that you want the ProgramData for
     * @param versionId versionId that you want the ProgramData for
     * @param auth
     * @return return the ProgramData list for given ProgramId and VersionId
     */
    @JsonView(Views.ArtmisView.class)
    @GetMapping("/programData/artmis/programId/{programId}/versionId/{versionId}")
    @Operation(description = "API used to get Artmis view ProgramData list for given ProgramId and VersionId", summary = "Get Artmis view ProgramData list for given ProgramId and VersionId", tags = ("programData"))
    @Parameters({
        @Parameter(name = "programId", description = "programId that you want to the ProgramData for"),
        @Parameter(name = "versionId", description = "versionId that you want to the ProgramData for")})
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the ProgramData list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the ProgramData specified does not exist")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of ProgramData")
    public ResponseEntity getProgramDataArtmis(@PathVariable(value = "programId", required = true) int programId, @PathVariable(value = "versionId", required = true) int versionId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.programDataService.getProgramData(programId, versionId, curUser), HttpStatus.OK);
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

    /**
     * API used to get GfpVan view ProgramData list for given ProgramId and
     * VersionId
     *
     * @param programId programId that you want the ProgramData for
     * @param versionId versionId that you want the ProgramData for
     * @param auth
     * @return return the ProgramData list for given ProgramId and VersionId
     */
    @JsonView(Views.GfpVanView.class)
    @GetMapping("/programData/gfpvan/programId/{programId}/versionId/{versionId}")
    @Operation(description = "API used to get GfpVan view ProgramData list for given ProgramId and VersionId", summary = "Get Artmis view ProgramData list for given ProgramId and VersionId", tags = ("programData"))
    @Parameters({
        @Parameter(name = "programId", description = "programId that you want to the ProgramData for"),
        @Parameter(name = "versionId", description = "versionId that you want to the ProgramData for")})
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the ProgramData list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the ProgramData specified does not exist")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of ProgramData")
    public ResponseEntity getProgramDataGfpVan(@PathVariable(value = "programId", required = true) int programId, @PathVariable(value = "versionId", required = true) int versionId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.programDataService.getProgramData(programId, versionId, curUser), HttpStatus.OK);
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

    /**
     * API used to update a ProgramData
     *
     * @param programData programData object that you want to update
     * @param auth
     * @return returns a Success code if the operation was successful
     */
    @PutMapping("/programData")
    @Operation(description = "API used to update a ProgramData", summary = "Update ProgramData", tags = ("programData"))
    @Parameters(
            @Parameter(name = "programData", description = "programData object that you want to update"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "412", description = "Returns a HttpStatus.PRECONDITION_FAILED if certain conditions to update the date does not met")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the ProgramData does not exist")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    public ResponseEntity putProgramData(@RequestBody ProgramData programData, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            Version v = this.programDataService.saveProgramData(programData, curUser);
            return new ResponseEntity(this.programDataService.getProgramData(programData.getProgramId(), v.getVersionId(), curUser), HttpStatus.OK);
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

    /**
     * API used to get the complete list of VersionType
     *
     * @param auth
     * @return returns the complete list of VersionType
     */
    @GetMapping("/versionType")
    @Operation(description = "API used to get the complete list of VersionType", summary = "Get the complete list of VersionType", tags = ("programData"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the list of VersionType")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of list of VersionType")
    public ResponseEntity getVersionType(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.programDataService.getVersionTypeList(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to get Version Type", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the complete list of VersionStatus
     *
     * @param auth
     * @return returns the complete list of VersionStatus
     */
    @GetMapping("/versionStatus")
    @Operation(description = "API used to get the complete list of VersionStatus", summary = "Get the complete list of VersionStatus", tags = ("programData"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the list of VersionStatus")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of list of VersionStatus")
    public ResponseEntity getVersionStatus(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.programDataService.getVersionStatusList(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to get Version Status", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the list of programVersion based on given parameters and
     * date range
     *
     * @param programId programId that you want programVersion list for
     * @param versionId versionId that you want programVersion list for
     * @param realmCountryId realmCountryId that you want programVersion list
     * for
     * @param healthAreaId healthAreaId that you want programVersion list for
     * @param organisationId organisationId that you want programVersion list
     * for
     * @param versionTypeId versionTypeId that you want programVersion list for
     * @param versionStatusId versionStatusId that you want programVersion list
     * for
     * @param startDate startDate that you want programVersion list from
     * @param stopDate stopDate that you want programVersion list till
     * @param auth
     *
     * @return returns the list of programVersion based on given parameters and
     * date range
     */
    @GetMapping("/programVersion/programId/{programId}/versionId/{versionId}/realmCountryId/{realmCountryId}/healthAreaId/{healthAreaId}/organisationId/{organisationId}/versionTypeId/{versionTypeId}/versionStatusId/{versionStatusId}/dates/{startDate}/{stopDate}")
    @Operation(description = "API used to get the list of programVersion based on given parameters and date range", summary = "Get the list of programVersion based on given parameters and date range", tags = ("programData"))
    @Parameters({
        @Parameter(name = "programId", description = "programId that you want to the programVersion list for"),
        @Parameter(name = "versionId", description = "versionId that you want to the programVersion list for"),
        @Parameter(name = "realmCountryId", description = "realmCountryId that you want to the programVersion list for"),
        @Parameter(name = "healthAreaId", description = "healthAreaId that you want to the programVersion list for"),
        @Parameter(name = "organisationId", description = "organisationId that you want to the programVersion list for"),
        @Parameter(name = "versionTypeId", description = "versionTypeId that you want to the programVersion list for"),
        @Parameter(name = "versionStatusId", description = "versionStatusId that you want to the programVersion list for"),
        @Parameter(name = "startDate", description = "startDate that you want to the programVersion list from"),
        @Parameter(name = "stopDate", description = "stopDate that you want to the programVersion list till")})
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the list of programVersion")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the programVersion does not exist")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of list of programVersion")
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

    /**
     * API used to update a programVersion for given programId and versionId
     *
     * @param updateProgramVersion updateProgramVersion object that you want to
     * update
     * @param programId programId that you want to update programVersion for
     * @param versionId versionId that you want to update programVersion for
     * @param versionStatusId versionStatusId that you want to update in
     * programVersion
     * @param auth
     * @return returns a Success code if the operation was successful
     */
    @PutMapping("/programVersion/programId/{programId}/versionId/{versionId}/versionStatusId/{versionStatusId}")
    @Operation(description = "API used to update a programVersion for given programId and versionId", summary = "Update a programVersion for given programId and versionId", tags = ("programData"))
    @Parameters({
        @Parameter(name = "updateProgramVersion", description = "updateProgramVersion object that you want to update"),
        @Parameter(name = "programId", description = "programId that you want to update programVersion for"),
        @Parameter(name = "versionId", description = "versionId that you want to update programVersion for"),
        @Parameter(name = "versionStatusId", description = "versionStatusId that you want to update in programVersion")})
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the programVersion does not exist")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")

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

    /**
     * API to check ERP order status
     *
     * @param orderNo orderNo that want the status for
     * @param primeLineNo primeLineNo that want the status for
     * @param realmCountryId realmCountryId that want the status for
     * @param planningUnitId planningUnitId that want the status for
     * @param auth
     * @return order status
     */
    @GetMapping("/programData/checkErpOrder/orderNo/{orderNo}/primeLineNo/{primeLineNo}/realmCountryId/{realmCountryId}/planningUnitId/{planningUnitId}")
    @Operation(description = "API to check ERP order status", summary = "Check ERP order status", tags = ("programData"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the ordr status")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of ERP order status")

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

    /**
     * API used to get list of Shipment data for given programId and versionId
     * which are modified after given date
     *
     * @param programId programId that want the Shipment data for
     * @param versionId versionId that want the Shipment data for
     * @param userId userId need this back in response
     * @param lastSyncDate lastSyncDate that you want a Shipment data list
     * modified after the date
     * @param auth
     * @return returns a complete list of Shipment data for given programId and
     * versionId which are modified after given date
     */
    @GetMapping("/programData/shipmentSync/programId/{programId}/versionId/{versionId}/userId/{userId}/lastSyncDate/{lastSyncDate}")
    @Operation(description = "API used to get list of Shipment data for given programId and versionId which are modified after given date", summary = "Get list of Shipment data for given programId and versionId which are modified after given date", tags = ("programData"))
    @Parameters({
        @Parameter(name = "programId", description = "programId that want the Shipment data for"),
        @Parameter(name = "versionId", description = "versionId that want the Shipment data for"),
        @Parameter(name = "userId", description = "userId need this back in response"),
        @Parameter(name = "lastSyncDate", description = "lastSyncDate that you want a Shipment data list")})
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the Shipment data list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "412", description = "Returns a HttpStatus.PRECONDITION_FAILED if certain conditions to get the Shipment data list does not met")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of Shipment data list")

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
     * API to check if newer version available for list programIds and
     * versionIds Sample JSON
     * [{"programId":2535,"versionId":3},{"programId":2001,"versionId":5}]
     *
     * @param programVersionList programVersionList that you want to check newer
     * version for
     * @param auth
     * @return return true or false based on availability of newer version for
     * listed programIds and versionIds
     */
    @PostMapping("/programData/checkNewerVersions")

    @Operation(description = "API to check if newer version available for list programIds and versionIds", summary = "To check if newer version available for list programIds and versionIds", tags = ("programData"))
    @Parameters(
            @Parameter(name = "programVersionList", description = "programVersionList that you want to check newer version for"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the Newer version status")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of newer version of program")
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

    /**
     * API to get the latest version for a program
     *
     * @param programId programId that you want the latest version for
     * @return latest version of program
     */
    @GetMapping("/programData/getLatestVersionForProgram/{programId}")

    @Operation(description = "API to get the latest version for a program", summary = "Get the latest version for a program", tags = ("programData"))
    @Parameters(
            @Parameter(name = "programId", description = "programId that you want the latest version for"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the latest version of program")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of latest version of program")

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

}
