/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.exception.CouldNotSaveException;
import cc.altius.FASP.model.CommitRequest;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DatasetDataJson;
import cc.altius.FASP.model.EmptyDoubleTypeAdapter;
import cc.altius.FASP.model.EmptyIntegerTypeAdapter;
import cc.altius.FASP.model.ProgramData;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.model.report.CommitRequestInput;
import cc.altius.FASP.service.CommitRequestService;
import cc.altius.FASP.service.ProgramService;
import cc.altius.FASP.service.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.IOUtils;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author akil
 */
@RestController
@RequestMapping("/api")
public class CommitRequestRestController {

    @Autowired
    private UserService userService;
    @Autowired
    private CommitRequestService commitRequestService;
    @Autowired
    private ProgramService programService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // Part 1 of the Commit Request for Supply Plan
    @PutMapping("/programData/{comparedVersionId}")
    public ResponseEntity putProgramData(@PathVariable(value = "comparedVersionId", required = true) int comparedVersionId, @RequestBody ProgramData programData, Authentication auth) {
        try {
//            String json = IOUtils.toString(request.getReader());
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Double.class, new EmptyDoubleTypeAdapter())
                    .registerTypeAdapter(Integer.class, new EmptyIntegerTypeAdapter())
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .setLenient()
                    .create();
//            ProgramData programData = gson.fromJson(json, new TypeToken<ProgramData>() {
//            }.getType());
            int latestVersion = this.programService.getLatestVersionForPrograms("" + programData.getProgramId()).get(0).getVersionId();
            if (latestVersion == comparedVersionId) {
                boolean checkIfRequestExists = this.commitRequestService.checkIfCommitRequestExistsForProgram(programData.getProgramId());
                if (!checkIfRequestExists) {
                    CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
                    int commitRequestId = this.commitRequestService.saveProgramData(programData, gson.toJson(programData), curUser);
                    return new ResponseEntity(commitRequestId, HttpStatus.OK);
                } else {
                    logger.error("Request already exists");
                    return new ResponseEntity(new ResponseCode("static.commitVersion.requestAlreadyExists"), HttpStatus.NOT_ACCEPTABLE);
                }
            } else {
                logger.error("Compared version is not latest");
                return new ResponseEntity(new ResponseCode("static.commitVersion.versionIsOutDated"), HttpStatus.NOT_ACCEPTABLE);
            }
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

    // Part 1 of the Commit Request for Dataset
    @PutMapping("/datasetData/{comparedVersionId}")
    public ResponseEntity putDatasetData(@PathVariable(value = "comparedVersionId", required = true) int comparedVersionId, HttpServletRequest request, Authentication auth) {
        String json = null;
        try {
            json = IOUtils.toString(request.getReader());
            String emptyFuNodeString1 = "\"fuNode\":{\"noOfForecastingUnitsPerPerson\":\"\",\"usageFrequency\":\"\",\"forecastingUnit\":{\"label\":{\"label_en\":\"\"},\"tracerCategory\":{},\"unit\":{\"id\":\"\"}},\"usageType\":{\"id\":\"\"},\"usagePeriod\":{\"usagePeriodId\":\"\"},\"repeatUsagePeriod\":{\"usagePeriodId\":\"\"},\"noOfPersons\":\"\"}";
            String emptyFuNodeString2 = "\"fuNode\":{\"lagInMonths\":0,\"noOfForecastingUnitsPerPerson\":\"\",\"usageFrequency\":\"\",\"forecastingUnit\":{\"label\":{\"label_en\":\"\"},\"tracerCategory\":{},\"unit\":{\"id\":\"\"}},\"usageType\":{\"id\":\"\"},\"usagePeriod\":{\"usagePeriodId\":1},\"repeatUsagePeriod\":{\"usagePeriodId\":1},\"noOfPersons\":\"\"}";
            String emptyFuNodeString3 = "\"fuNode\":{\"lagInMonths\":0,\"noOfForecastingUnitsPerPerson\":\"\",\"usageFrequency\":\"\",\"forecastingUnit\":{\"label\":{\"label_en\":\"\"},\"tracerCategory\":{\"id\":3},\"unit\":{\"id\":\"\"}},\"usageType\":{\"id\":\"\"},\"usagePeriod\":{\"usagePeriodId\":1},\"repeatUsagePeriod\":{\"usagePeriodId\":1},\"noOfPersons\":\"\"}";
            json = json.replace(emptyFuNodeString1, "\"fuNode\": null");
            json = json.replace(emptyFuNodeString2, "\"fuNode\": null");
            json = json.replace(emptyFuNodeString3, "\"fuNode\": null");
            json = json.replace(",,", ",");
            String emptyPuNodeString1 = "\"puNode\":{\"planningUnit\":{\"unit\":{}},\"refillMonths\":\"\"}";
            json = json.replace(emptyPuNodeString1, "\"puNode\": null");
            String emptyPuNodeString2 = "\"puNode\":{\"planningUnit\":{\"id\":\"\",\"unit\":{},\"multiplier\":\"\"},\"refillMonths\":\"\"}";
            json = json.replace(emptyPuNodeString2, "\"puNode\": null");
            json = json.replace(",,", ",");
            String emptyUsageFrequency="\"usageFrequency\":\"\",";
            json = json.replace(emptyUsageFrequency, "\"usageFrequency\":null,");
            String emptyUsagePeriod="\"usagePeriod\":{\"usagePeriodId\":\"\"},";
            json = json.replace(emptyUsagePeriod, "\"usagePeriod\":null,");
            String emptyRepeatUsagePeriod="\"repeatUsagePeriod\":{\"usagePeriodId\":\"\"},";
            json = json.replace(emptyRepeatUsagePeriod, "\"repeatUsagePeriod\":null,");
                    
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Double.class, new EmptyDoubleTypeAdapter())
                    .registerTypeAdapter(Integer.class, new EmptyIntegerTypeAdapter())
                    .setDateFormat("yyyy-MM-dd HH:mm:ss").setLenient()
                    .create();
            DatasetDataJson datasetData = gson.fromJson(json, new TypeToken<DatasetDataJson>() {
            }.getType());
            int latestVersion = this.programService.getLatestVersionForPrograms("" + datasetData.getProgramId()).get(0).getVersionId();
            if (latestVersion == comparedVersionId) {
                boolean checkIfRequestExists = this.commitRequestService.checkIfCommitRequestExistsForProgram(datasetData.getProgramId());
                if (!checkIfRequestExists) {
                    CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
                    int commitRequestId = this.commitRequestService.saveDatasetData(datasetData, json, curUser);
                    logger.info("Commit request received and stored in the db commitRequestId=" + commitRequestId);
                    return new ResponseEntity(commitRequestId, HttpStatus.OK);
                } else {
                    logger.error("Request already exists");
                    return new ResponseEntity(new ResponseCode("static.commitVersion.requestAlreadyExists"), HttpStatus.NOT_ACCEPTABLE);
                }
            } else {
                logger.error("Compared version is not latest");
                return new ResponseEntity(new ResponseCode("static.commitVersion.versionIsOutDated"), HttpStatus.NOT_ACCEPTABLE);
            }
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
            logger.error(json);
            logger.error("Error while trying to update ProgramData", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Part 2 of the Commit Request
    @GetMapping("/processCommitRequest")
    //sec min hour day_of_month month day_of_week
    @Scheduled(cron = "00 */1 * * * *")
    public ResponseEntity processCommitRequest() {
        try {
            logger.info("Starting the Commit request scheduler");
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(1);
            this.commitRequestService.processCommitRequest(curUser);
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
    public ResponseEntity getProgramDataCommitRequest(@RequestBody CommitRequestInput spcr, @PathVariable(value = "requestStatus", required = true) int requestStatus, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            List<CommitRequest> spcrList = this.commitRequestService.getCommitRequestList(spcr, requestStatus, curUser);
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

    /**
     * Asynchronous API used to get the commit status
     *
     *
     * @return returns the commit status
     */
    @Operation(description = "Asynchronous API used to get the commit status.", summary = "Asynchronous API to get commit status", tags = ("commitStatus"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the Integration Program list")
    @GetMapping("sendNotification/{commitRequestId}")
    public @ResponseBody
    CompletableFuture<ResponseEntity> sendNotification(@PathVariable("commitRequestId") int commitRequestId) throws InterruptedException {
        return this.commitRequestService.getCommitRequestStatusByCommitRequestId(commitRequestId).thenApplyAsync(ResponseEntity -> {
            return new ResponseEntity(ResponseEntity, HttpStatus.OK);
        });
    }
}
