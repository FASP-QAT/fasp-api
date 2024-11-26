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
import cc.altius.FASP.model.DatasetPlanningUnit;
import cc.altius.FASP.model.LoadProgram;
import cc.altius.FASP.model.ProgramIdAndVersionId;
import cc.altius.FASP.model.ProgramInitialize;
import cc.altius.FASP.model.ProgramVersion;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.model.Views;
import cc.altius.FASP.model.Program;
import cc.altius.FASP.service.ProgramDataService;
import cc.altius.FASP.service.ProgramService;
import cc.altius.FASP.service.RealmCountryService;
import cc.altius.FASP.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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

/**
 *
 * @author akil
 */
@RestController
@RequestMapping("/api")
@Tag(
    name = "Dataset",
    description = "Manage datasets and their versions"
)
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

    @GetMapping("/dataset")
    @Operation(
        summary = "Get Active Dataset List",
        description = "Retrieve a list of all active datasets"
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = Program.class))), responseCode = "200", description = "Returns the list of active datasets")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retrieval of the dataset list")
    public ResponseEntity getDataset(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.programService.getProgramList(GlobalConstants.PROGRAM_TYPE_DATASET, curUser, true), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Dataset", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); //500
        }
    }

    @GetMapping("/dataset/all")
    @Operation(
        summary = "Get All Dataset List",
        description = "Retrieve a complete list of all datasets (active and disabled)"
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = Program.class))), responseCode = "200", description = "Returns the list of all datasets")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retrieval of the dataset list")
    public ResponseEntity getDatasetAll(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.programService.getProgramList(GlobalConstants.PROGRAM_TYPE_DATASET, curUser, false), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Dataset", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); //500
        }
    }

    @GetMapping("/dataset/realmId/{realmId}")
    @Operation(
        summary = "Get Dataset for Realm",
        description = "Retrieve a list of datasets for a specific realm"
    )
    @Parameter(name = "realmId", description = "The ID of the realm to retrieve datasets for")
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = Program.class))), responseCode = "200", description = "Returns the list of datasets for the specified realm")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "The realm with the given ID was not found")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retrieval of the dataset list")
    public ResponseEntity getDatasetForRealm(@PathVariable(value = "realmId", required = true) int realmId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.programService.getProgramListForRealmId(realmId, GlobalConstants.PROGRAM_TYPE_DATASET, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list Dataset", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); //404
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list Dataset", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN); //403
        } catch (Exception e) {
            logger.error("Error while trying to update Dataset", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); //500
        }
    }

    @GetMapping("/dataset/{programId}")
    @Operation(
        summary = "Get Dataset by Program Id",
        description = "Retrieve a dataset by its Program ID"
    )
    @Parameter(name = "programId", description = "The ID of the program to retrieve a dataset for")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = Program.class)), responseCode = "200", description = "Returns the dataset with the specified program ID")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "The user does not have permission to access this dataset")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "The dataset with the given program ID was not found")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retrieval of the dataset")
    public ResponseEntity getDataset(@PathVariable("programId") int programId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.programService.getFullProgramById(programId, GlobalConstants.PROGRAM_TYPE_DATASET, curUser), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list Dataset", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN); //403
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list Dataset", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); //404
        } catch (Exception e) {
            logger.error("Error while trying to list Dataset", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); //500
        }
    }

    @JsonView(Views.InternalView.class)
    @GetMapping("/datasetData/programId/{programId}/versionId/{versionId}/withoutTree")
    @Operation(
        summary = "Get Dataset Data",
        description = "Retrieve dataset data for a specific program and version ID with or without tree data"
    )
    @Parameter(name = "programId", description = "The ID of the program to retrieve dataset data for")
    @Parameter(name = "versionId", description = "The ID of the version to retrieve dataset data for")
    @Parameter(name = "includeTreeData", description = "Whether to include tree data in the dataset data")
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = DatasetData.class))), responseCode = "200", description = "Returns the dataset data for the specified program and version ID without tree data")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "The user does not have permission to access this dataset")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "The dataset with the given program ID was not found")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retrieval of the dataset data")
    public ResponseEntity getDatasetData(@PathVariable("programId") int programId, @PathVariable("versionId") int versionId, @PathVariable(name = "includeTreeData", required = false) boolean includeTreeData, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.programDataService.getDatasetData(programId, versionId, false, curUser), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list Dataset", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN); //403
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list Dataset", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); //404
        } catch (Exception e) {
            logger.error("Error while trying to list Dataset", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); //500
        }
    }


    @JsonView(Views.InternalView.class)
    @GetMapping("/datasetData/programId/{programId}/versionId/{versionId}")
    @Operation(
        summary = "Get Dataset Data with Tree",
        description = "Retrieve dataset data for a specific program and version ID with tree data"
    )
    @Parameter(name = "programId", description = "The ID of the program to retrieve dataset data for")
    @Parameter(name = "versionId", description = "The ID of the version to retrieve dataset data for")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = DatasetData.class)), responseCode = "200", description = "Returns the dataset data for the specified program and version ID with tree data")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "The user does not have permission to access this dataset")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "The dataset with the given program ID was not found")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retrieval of the dataset data")
    public ResponseEntity getDatasetData(@PathVariable("programId") int programId, @PathVariable("versionId") int versionId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.programDataService.getDatasetData(programId, versionId, true, curUser), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list Dataset", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN); //403
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list Dataset", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); //404
        } catch (Exception e) {
            logger.error("Error while trying to list Dataset", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); //500
        }
    }

    @GetMapping("/planningUnit/programId/{programId}/versionId/{versionId}")
    @Operation(
        summary = "Get Planning Unit for Dataset",
        description = "Retrieve planning unit data for a specific program and version ID"
    )
    @Parameter(name = "programId", description = "The ID of the program to retrieve planning unit data for")
    @Parameter(name = "versionId", description = "The ID of the version to retrieve planning unit data for")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = DatasetPlanningUnit.class)), responseCode = "200", description = "Returns the planning unit data for the specified program and version ID")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "The dataset with the given program ID was not found")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retrieval of the planning unit data")
    public ResponseEntity getPlanningUnitForDataset(@PathVariable("programId") int programId, @PathVariable("versionId") int versionId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.programDataService.getDatasetPlanningUnit(programId, versionId, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException er) {
            logger.error("Error while trying to list PlanningUnit", er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); //404
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); //500
        }
    }

    @JsonView(Views.InternalView.class)
    @PostMapping("/datasetData")
    @Operation(
        summary = "Get Dataset Data List",
        description = "Retrieve dataset data for a list of program and version IDs"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The list of program and version IDs to retrieve dataset data for",
        required = true,
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = DatasetData.class))), responseCode = "200", description = "Returns the dataset data for the specified list of program and version IDs")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "The user does not have permission to access this dataset")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "The dataset with the given program ID was not found")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retrieval of the dataset data")
    public ResponseEntity getDatasetData(@RequestBody List<ProgramIdAndVersionId> programVersionList, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            List<DatasetData> masters = this.programDataService.getDatasetData(programVersionList, curUser);
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(masters);
            if(isCompress(jsonString)){
                return new ResponseEntity(compress(jsonString), HttpStatus.OK);
            }
            return new ResponseEntity(masters, HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list Dataset", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN); //403
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list Dataset", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); //404
        } catch (Exception e) {
            logger.error("Error while trying to list Dataset", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); //500
        }
    }

    @PostMapping("/dataset")
    @Operation(
        summary = "Add Dataset",
        description = "Create a new dataset"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The dataset to create",
        required = true,
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProgramInitialize.class))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "200", description = "Returns a success message")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "406", description = "The dataset with the given program ID already exists")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "The user does not have permission to access this dataset")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retrieval of the dataset")
    public ResponseEntity postDataset(@RequestBody ProgramInitialize dataset, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            dataset.setProgramTypeId(GlobalConstants.PROGRAM_TYPE_DATASET); // Dataset Program
            this.programService.addProgram(dataset, curUser);
            return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK);
        } catch (DuplicateKeyException d) {
            logger.error("Error while trying to add Program", d);
            return new ResponseEntity(new ResponseCode("static.message.alreadExists"), HttpStatus.NOT_ACCEPTABLE); //406
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to add Program", ae);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.FORBIDDEN); //403
        } catch (Exception e) {
            logger.error("Error while trying to add Program", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR); //500
        }
    }

    @PutMapping("/dataset")
    @Operation(
        summary = "Update Dataset",
        description = "Update an existing dataset"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The dataset to update",
        required = true,
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProgramInitialize.class))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "200", description = "Returns a success message")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "The dataset with the given program ID was not found")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "The user does not have permission to access this dataset")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retrieval of the dataset")
    public ResponseEntity putDataset(@RequestBody ProgramInitialize dataset, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            dataset.setProgramTypeId(GlobalConstants.PROGRAM_TYPE_DATASET); // Dataset Program
            this.programService.updateProgram(dataset, curUser);
            return new ResponseEntity(new ResponseCode("static.message.updateSuccess"), HttpStatus.OK);
        } catch (EmptyResultDataAccessException ae) {
            logger.error("Error while trying to update Program", ae);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.NOT_FOUND); //404
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to update Program", ae);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.FORBIDDEN); //403
        } catch (Exception e) {
            logger.error("Error while trying to update Program", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR); //500
        }
    }

    @GetMapping("/loadDataset")
    @Operation(
        summary = "Load Dataset",
        description = "Retrieve the complete dataset, including realm and program data"
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = Map.class)), responseCode = "200", description = "Returns the complete dataset, including realm and program data")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "The dataset with the given program ID was not found")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retrieval of the dataset")
    public ResponseEntity getLoadDataset(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            Map<String, Object> params = new HashMap<>();
            params.put("realmCountryList", this.realmCountryService.getRealmCountryListByRealmIdForActivePrograms(curUser.getRealm().getRealmId(), GlobalConstants.PROGRAM_TYPE_DATASET, curUser));
            params.put("programList", this.programService.getLoadProgram(GlobalConstants.PROGRAM_TYPE_DATASET, curUser));
            return new ResponseEntity(params, HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list Datasets", e);
            return new ResponseEntity(new LinkedList<LoadProgram>(), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list Datasets", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN); //403
        } catch (Exception e) {
            logger.error("Error while trying to list Datasets", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); //500
        }
    }

    @GetMapping("/loadDataset/programId/{programId}/page/{page}")
    @Operation(
        summary = "Load Program (paginated)",
        description = "Retrieve a paginated list of programs for a specific program ID"
    )
    @Parameter(name = "programId", description = "The ID of the program to retrieve a paginated list of programs for")
    @Parameter(name = "page", description = "The page number to retrieve")
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = LoadProgram.class))), responseCode = "200", description = "Returns the paginated list of programs for the specified program ID")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "The dataset with the given program ID was not found")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retrieval of the dataset")
    public ResponseEntity getLoadDataset(@PathVariable("programId") int programId, @PathVariable("page") int page, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.programService.getLoadProgram(programId, page, GlobalConstants.PROGRAM_TYPE_DATASET, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity(new LinkedList<LoadProgram>(), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list Dtasets", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN); //403
        } catch (Exception e) {
            logger.error("Error while trying to list Datasets", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); //500
        }
    }

    @JsonView({Views.ReportView.class})
    @PostMapping("/dataset/versions")
    @Operation(
        summary = "Get Dataset Version List",
        description = "Retrieve a list of dataset versions for a specific program"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The input for the dataset version list",
        required = true,
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = DatasetVersionListInput.class))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = ProgramVersion.class))), responseCode = "200", description = "Returns the list of dataset versions for the specified program")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "406", description = "The dataset with the given program ID already exists")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "The user does not have permission to access this dataset")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retrieval of the dataset")
    public ResponseEntity getDatasetVersionList(@RequestBody DatasetVersionListInput dvli, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.programDataService.getDatasetVersionList(dvli, curUser), HttpStatus.OK);
        } catch (DuplicateKeyException d) {
            logger.error("Error while get ProgramVersion List", d);
            return new ResponseEntity(new ResponseCode("static.message.alreadExists"), HttpStatus.NOT_ACCEPTABLE); //406
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to get ProgramVersion List", ae);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN); //403
        } catch (Exception e) {
            logger.error("Error while trying to get ProgramVersion List", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); //500
        }
    }
}
