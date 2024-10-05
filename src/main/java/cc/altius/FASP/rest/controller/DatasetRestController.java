/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.framework.GlobalConstants;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DatasetData;
import cc.altius.FASP.model.DatasetVersionListInput;
import cc.altius.FASP.model.LoadProgram;
import cc.altius.FASP.model.ProgramIdAndVersionId;
import cc.altius.FASP.model.ProgramInitialize;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.model.Views;
import cc.altius.FASP.service.ProgramDataService;
import cc.altius.FASP.service.ProgramService;
import cc.altius.FASP.service.RealmCountryService;
import cc.altius.FASP.service.UserService;
import static cc.altius.FASP.utils.CompressUtils.compress;
import static cc.altius.FASP.utils.CompressUtils.isCompress;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 *
 * @author akil
 */
@RestController
@RequestMapping("/api")
public class DatasetRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProgramService programService;
    @Autowired
    private ProgramDataService programDataService;
    @Autowired
    private UserService userService;
    @Autowired
    private RealmCountryService realmCountryService;

    /**
     * Get list of active Dataset Programs
     *
     * @param auth
     * @return
     */
    @GetMapping("/dataset")
    public ResponseEntity getDataset(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.programService.getProgramList(GlobalConstants.PROGRAM_TYPE_DATASET, curUser, true), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Dataset", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get Dataset by Id
     *
     * @param programId
     * @param auth
     * @return
     */
    @GetMapping("/dataset/{programId}")
    public ResponseEntity getDataset(@PathVariable("programId") int programId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.programService.getFullProgramById(programId, GlobalConstants.PROGRAM_TYPE_DATASET, curUser), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list Dataset", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list Dataset", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error while trying to list Dataset", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get Dataset Program Data based on Program and Version Id list but without
     * the Tree data
     *
     * @param programId
     * @param versionId
     * @param includeTreeData
     * @param auth
     * @return
     */
    @JsonView(Views.InternalView.class)
    @GetMapping("/datasetData/programId/{programId}/versionId/{versionId}/withoutTree")
    public ResponseEntity getDatasetData(@PathVariable("programId") int programId, @PathVariable("versionId") int versionId, @PathVariable(name = "includeTreeData", required = false) boolean includeTreeData, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.programDataService.getDatasetData(programId, versionId, false, curUser), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list Dataset", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list Dataset", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error while trying to list Dataset", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get Dataset Program Data based on Program and Version Id list
     *
     * @param programId
     * @param versionId
     * @param auth
     * @return
     */
    @JsonView(Views.InternalView.class)
    @GetMapping("/datasetData/programId/{programId}/versionId/{versionId}")
    public ResponseEntity getDatasetData(@PathVariable("programId") int programId, @PathVariable("versionId") int versionId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.programDataService.getDatasetData(programId, versionId, true, curUser), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list Dataset", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list Dataset", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error while trying to list Dataset", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get Planning Unit list for Dataset Program
     *
     * @param programId
     * @param versionId
     * @param auth
     * @return
     */
    @GetMapping(value = "/planningUnit/programId/{programId}/versionId/{versionId}")
    public ResponseEntity getPlanningUnitForDataset(@PathVariable("programId") int programId, @PathVariable("versionId") int versionId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.programDataService.getDatasetPlanningUnit(programId, versionId, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException er) {
            logger.error("Error while trying to list PlanningUnit", er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get list of Dataset Programs based on Program and Version Id list
     *
     * @param programVersionList
     * @param auth
     * @return
     */
    @JsonView(Views.InternalView.class)
    @PostMapping("/datasetData")
    public ResponseEntity getDatasetData(@RequestBody List<ProgramIdAndVersionId> programVersionList, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            List<DatasetData> masters = this.programDataService.getDatasetData(programVersionList, curUser);
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(masters);
            if (isCompress(jsonString)) {
                return new ResponseEntity(compress(jsonString), HttpStatus.OK);
            }
            return new ResponseEntity(masters, HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list Dataset", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list Dataset", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error while trying to list Dataset", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Add new Dataset Program
     *
     * @param dataset
     * @param auth
     * @return
     */
    @PostMapping(path = "/dataset")
    public ResponseEntity postDataset(@RequestBody ProgramInitialize dataset, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            dataset.setProgramTypeId(GlobalConstants.PROGRAM_TYPE_DATASET); // Dataset Program
            this.programService.addProgram(dataset, curUser);
            return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK);
        } catch (DuplicateKeyException d) {
            logger.error("Error while trying to add Program", d);
            return new ResponseEntity(new ResponseCode("static.message.alreadExists"), HttpStatus.NOT_ACCEPTABLE);
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to add Program", ae);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to add Program", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Update Dataset Program
     *
     * @param dataset
     * @param auth
     * @return
     */
    @PutMapping(path = "/dataset")
    public ResponseEntity putDataset(@RequestBody ProgramInitialize dataset, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            dataset.setProgramTypeId(GlobalConstants.PROGRAM_TYPE_DATASET); // Dataset Program
            this.programService.updateProgram(dataset, curUser);
            return new ResponseEntity(new ResponseCode("static.message.updateSuccess"), HttpStatus.OK);
        } catch (EmptyResultDataAccessException ae) {
            logger.error("Error while trying to update Program", ae);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to update Program", ae);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to update Program", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Used in the Load Dataset Page
     *
     * @param auth
     * @return
     */
    @GetMapping("/loadDataset")
    public ResponseEntity getLoadDataset(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            Map<String, Object> params = new HashMap<>();
            params.put("realmCountryList", this.realmCountryService.getRealmCountryListByRealmIdForActivePrograms(curUser.getRealm().getRealmId(), GlobalConstants.PROGRAM_TYPE_DATASET, curUser));
            params.put("programList", this.programService.getLoadProgram(GlobalConstants.PROGRAM_TYPE_DATASET, curUser));
            return new ResponseEntity(params, HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list Datasets", e);
            return new ResponseEntity(new LinkedList<LoadProgram>(), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list Datasets", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to list Datasets", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Used in the Load Dataset Page to get the Version history of a specific
     * Program
     *
     * @param programId
     * @param page
     * @param auth
     * @return
     */
    @GetMapping("/loadDataset/programId/{programId}/page/{page}")
    public ResponseEntity getLoadDataset(@PathVariable("programId") int programId, @PathVariable("page") int page, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.programService.getLoadProgram(programId, page, GlobalConstants.PROGRAM_TYPE_DATASET, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity(new LinkedList<LoadProgram>(), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list Dtasets", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to list Datasets", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Gets the list of all Dataset Programs based on a list of ProgramIds,
     * Version Type, Start date and Stop date
     *
     * @param dvli
     * @param auth
     * @return
     */
    @JsonView({Views.ReportView.class})
    @PostMapping("/dataset/versions")
    public ResponseEntity getDatasetVersionList(@RequestBody DatasetVersionListInput dvli, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.programDataService.getDatasetVersionList(dvli, curUser), HttpStatus.OK);
        } catch (DuplicateKeyException d) {
            logger.error("Error while get ProgramVersion List", d);
            return new ResponseEntity(new ResponseCode("static.message.alreadExists"), HttpStatus.NOT_ACCEPTABLE);
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to get ProgramVersion List", ae);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to get ProgramVersion List", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
