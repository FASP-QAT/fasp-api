/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ProcurementUnit;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.service.ProcurementUnitService;
import cc.altius.FASP.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
 * @author altius
 */
@RestController
@RequestMapping("/api/procurementUnit")
public class ProcurementUnitRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProcurementUnitService procurementUnitService;
    @Autowired
    private UserService userService;

    /**
     * API used to get the complete ProcurementUnit list. Will only return those
     * ProcurementUnits that are marked Active.
     *
     * @param auth
     * @return returns the complete list of active ProcurementUnits
     */
    @GetMapping("/")
    @Operation(description = "API used to get the complete ProcurementUnit list. Will only return those ProcurementUnits that are marked Active.", summary = "Get active ProcurementUnit list", tags = ("procurementUnit"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the ProcurementUnit list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of ProcurementUnit list")
    public ResponseEntity getProcurementUnit(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.procurementUnitService.getProcurementUnitList(true, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list ProcurementUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the complete ProcurementUnit list.
     *
     * @param auth
     * @return returns the complete list of ProcurementUnits
     */
    @GetMapping("/all")
    @Operation(description = "API used to get the complete ProcurementUnit list.", summary = "Get ProcurementUnit list", tags = ("procurementUnit"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the ProcurementUnit list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of ProcurementUnit list")
    public ResponseEntity getProcurementUnitAll(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.procurementUnitService.getProcurementUnitList(false, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list ProcurementUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the ProcurementUnit for a specific ProcurementUnitId
     *
     * @param procurementUnitId ProcurementUnitId that you want the
     * ProcurementUnit Object for
     * @param auth
     * @return returns the ProcurementUnit object based on ProcurementUnitId
     * specified
     */
    @GetMapping(value = "/{procurementUnitId}")
    @Operation(description = "API used to get the ProcurementUnit for a specific ProcurementUnitId", summary = "Get ProcurementUnit for a ProcurementUnitId", tags = ("procurementUnit"))
    @Parameters(
            @Parameter(name = "procurementUnitId", description = "ProcurementUnitId that you want to the ProcurementUnit for"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the ProcurementUnit")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the ProcurementUnitId specified does not exist")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of ProcurementUnit")
    public ResponseEntity getProcurementUnitById(@PathVariable("procurementUnitId") int procurementUnitId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.procurementUnitService.getProcurementUnitById(procurementUnitId, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException er) {
            logger.error("Error while trying to list ProcurementUnit", er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error while trying to list ProcurementUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the ProcurementUnit for a specific RealmId. Will only
     * return those ProcurementUnits that are marked active
     *
     * @param realmId RealmId that you want the ProcurementUnit List for
     * @param auth
     * @return returns the list the ProcurementUnit based on RealmId specified.
     * Will only return those ProcurementUnits that are marked active
     */
    @GetMapping(value = "realmId/{realmId}")
    @Operation(description = "API used to get all the ProcurementUnits for a specific RealmId. Will only return those ProcurementUnits that are marked active", summary = "Get ProcurementUnit for a RealmId", tags = ("procurementUnit"))
    @Parameters(
            @Parameter(name = "realmId", description = "RealmId that you want to the ProcurementUnit for"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the ProcurementUnits list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the RealmId specified does not exist")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of ProcurementUnit")
    public ResponseEntity getProcurementUnitForRealm(@PathVariable(value = "realmId", required = true) int realmId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.procurementUnitService.getProcurementUnitListForRealm(realmId, true, curUser), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list ProcurementUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list ProcurementUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error while trying to list ProcurementUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the ProcurementUnit for a specific RealmId
     *
     * @param realmId RealmId that you want the ProcurementUnit List for
     * @param auth
     * @return returns the list the ProcurementUnit list based on RealmId
     * specified.
     */
    @GetMapping(value = "/realmId/{realmId}/all")
    @Operation(description = "API used to get all the ProcurementUnits for a specific RealmId.", summary = "Get ProcurementUnit for a RealmId", tags = ("procurementUnit"))
    @Parameters(
            @Parameter(name = "realmId", description = "RealmId that you want to the ProcurementUnit for"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the ProcurementUnits list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the RealmId specified does not exist")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of ProcurementUnit")
    public ResponseEntity getProcurementUnitForRealmAll(@PathVariable(value = "realmId", required = true) int realmId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.procurementUnitService.getProcurementUnitListForRealm(realmId, false, curUser), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list ProcurementUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list ProcurementUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error while trying to list ProcurementUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the ProcurementUnit for a specific PlanningUnitId. Will
     * only return those ProcurementUnits that are marked active
     *
     * @param planningUnitId PlanningUnitId that you want the ProcurementUnit
     * List for
     * @param auth
     * @return returns the list the ProcurementUnit list based on PlanningUnitId
     * specified. Will only return those ProcurementUnits that are marked active
     */
    @GetMapping("/planningUnitId/{planningUnitId}")
    @Operation(description = "API used to get all the ProcurementUnits for a specific PlanningUnitId. Will only return those ProcurementUnits that are marked active", summary = "Get ProcurementUnit for a PlanningUnitId", tags = ("procurementUnit"))
    @Parameters(
            @Parameter(name = "planningUnitId", description = "PlanningUnitId that you want to the ProcurementUnit for"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the ProcurementUnits list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the PlanningUnitId specified does not exist")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of ProcurementUnit")
    public ResponseEntity getProcurementUnitForPlanningUnit(@PathVariable(value = "planningUnitId", required = true) int planningUnitId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.procurementUnitService.getProcurementUnitListByPlanningUnit(planningUnitId, true, curUser), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list ProcurementUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to list ProcurementUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the ProcurementUnits for a specific PlanningUnitId.
     *
     * @param planningUnitId PlanningUnitId that you want the ProcurementUnit
     * list for
     * @param auth
     * @return returns the list the ProcurementUnit list based on PlanningUnitId
     * specified.
     */
    @GetMapping("/planningUnitId/{planningUnitId}/all")
    @Operation(description = "API used to get all the ProcurementUnits for a specific PlanningUnitId.", summary = "Get ProcurementUnit for a PlanningUnitId", tags = ("procurementUnit"))
    @Parameters(
            @Parameter(name = "planningUnitId", description = "PlanningUnitId that you want to the ProcurementUnit for"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the ProcurementUnits list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the PlanningUnitId specified does not exist")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of ProcurementUnit")
    public ResponseEntity getProcurementUnitForPlanningUnitAll(@PathVariable(value = "planningUnitId", required = true) int planningUnitId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.procurementUnitService.getProcurementUnitListByPlanningUnit(planningUnitId, false, curUser), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list ProcurementUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to list ProcurementUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to add a ProcurementUnit
     *
     * @param procurementUnit ProcurementUnit object that you want to add
     * @param auth
     * @return returns a Success code if the operation was successful
     */
    @PostMapping(value = "/")
    @Operation(description = "API used to add a ProcurementUnit", summary = "Add ProcurementUnit", tags = ("procurementUnit"))
    @Parameters(
            @Parameter(name = "procurementUnit", description = "The ProcurementUnit object that you want to add"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    public ResponseEntity addProcurementUnit(@RequestBody ProcurementUnit procurementUnit, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            this.procurementUnitService.addProcurementUnit(procurementUnit, curUser);
            return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK);
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to add ProcurementUnit", ae);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to add ProcurementUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to update a ProcurementUnit
     *
     * @param procurementUnit ProcurementUnit object that you want to update
     * @param auth
     * @return returns a Success code if the operation was successful
     */
    @PutMapping(path = "/")
    @Operation(description = "API used to update a ProcurementUnit", summary = "Update ProcurementUnit", tags = ("procurementUnit"))
    @Parameters(
            @Parameter(name = "procurementUnit", description = "The ProcurementUnit object that you want to update"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    public ResponseEntity updateProcurementUnit(@RequestBody ProcurementUnit procurementUnit, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            this.procurementUnitService.updateProcurementUnit(procurementUnit, curUser);
            return new ResponseEntity(new ResponseCode("static.message.updateSuccess"), HttpStatus.OK);
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to update ProcurementUnit", ae);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to update ProcurementUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @GetMapping(value = "/sync/procurementUnit/{lastSyncDate}")
//    public ResponseEntity getProcurementUnitListForSync(@PathVariable("lastSyncDate") String lastSyncDate, Authentication auth) {
//        try {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            sdf.parse(lastSyncDate);
//            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
//            return new ResponseEntity(this.procurementUnitService.getProcurementUnitListForSync(lastSyncDate, curUser), HttpStatus.OK);
//        } catch (ParseException p) {
//            logger.error("Error while listing procurementUnit", p);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.PRECONDITION_FAILED);
//        } catch (Exception e) {
//            logger.error("Error while listing procurementUnit", e);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}
