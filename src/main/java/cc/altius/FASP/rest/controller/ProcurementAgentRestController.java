/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ProcurementAgent;
import cc.altius.FASP.model.ProcurementAgentForecastingUnit;
import cc.altius.FASP.model.ProcurementAgentPlanningUnit;
import cc.altius.FASP.model.ProcurementAgentProcurementUnit;
import cc.altius.FASP.model.ProcurementAgentType;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.model.Views;
import cc.altius.FASP.service.ProcurementAgentService;
import cc.altius.FASP.service.UserService;
import com.fasterxml.jackson.annotation.JsonView;
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
public class ProcurementAgentRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProcurementAgentService procurementAgentService;
    @Autowired
    private UserService userService;

    @PostMapping(path = "/procurementAgent")
    public ResponseEntity postProcurementAgent(@RequestBody ProcurementAgent procurementAgent, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            int procurementAgentId = this.procurementAgentService.addProcurementAgent(procurementAgent, curUser);
            if (procurementAgentId > 0) {
                return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK);
            } else {
                return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to add Procurement Agent", ae);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.FORBIDDEN);
        } catch (DuplicateKeyException e) {
            logger.error("Error while trying to add Procurement Agent", e);
            return new ResponseEntity(new ResponseCode("static.message.alreadExists"), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            logger.error("Error while trying to add Procurement Agent", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping(path = "/procurementAgentType")
    public ResponseEntity postProcurementAgentType(@RequestBody ProcurementAgentType procurementAgentType, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            int procurementAgentTypeId = this.procurementAgentService.addProcurementAgentType(procurementAgentType, curUser);
            if (procurementAgentTypeId > 0) {
                return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK);
            } else {
                return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to add Procurement Agent Type", ae);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.FORBIDDEN);
        } catch (DuplicateKeyException e) {
            logger.error("Error while trying to add Procurement Agent Type", e);
            return new ResponseEntity(new ResponseCode("static.message.alreadExists"), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            logger.error("Error while trying to add Procurement Agent Type", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping(path = "/procurementAgent")
    public ResponseEntity putProcurementAgent(@RequestBody ProcurementAgent procurementAgent, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            int rows = this.procurementAgentService.updateProcurementAgent(procurementAgent, curUser);
            return new ResponseEntity(new ResponseCode("static.message.updateSuccess"), HttpStatus.OK);
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to update Procurement Agent", ae);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.FORBIDDEN);
        } catch (DuplicateKeyException e) {
            logger.error("Error while trying to update Procurement Agent", e);
            return new ResponseEntity(new ResponseCode("static.message.alreadExists"), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            logger.error("Error while trying to add Procurement Agent", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/procurementAgentType")
    public ResponseEntity putProcurementAgentType(@RequestBody ProcurementAgentType procurementAgentType, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            int rows = this.procurementAgentService.updateProcurementAgentType(procurementAgentType, curUser);
            return new ResponseEntity(new ResponseCode("static.message.updateSuccess"), HttpStatus.OK);
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to update Procurement Agent Type", ae);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.FORBIDDEN);
        } catch (DuplicateKeyException e) {
            logger.error("Error while trying to update Procurement Agent Type", e);
            return new ResponseEntity(new ResponseCode("static.message.alreadExists"), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            logger.error("Error while trying to add Procurement Agent Type", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @JsonView({Views.ReportView.class})
    @GetMapping("/procurementAgent")
    public ResponseEntity getProcurementAgent(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.procurementAgentService.getProcurementAgentList(true, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Procurement Agent", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/procurementAgentType")
    public ResponseEntity getProcurementAgentType(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.procurementAgentService.getProcurementAgentTypeList(true, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Procurement Agent Type", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @JsonView({Views.ReportView.class})
    @GetMapping("/procurementAgent/realmId/{realmId}")
    public ResponseEntity getProcurementAgentForRealm(@PathVariable("realmId") int realmId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.procurementAgentService.getProcurementAgentByRealm(realmId, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list Procurement Agent", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list Procurement Agent", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to list Procurement Agent", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/procurementAgentType/realmId/{realmId}")
    public ResponseEntity getProcurementAgentTypeForRealm(@PathVariable("realmId") int realmId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.procurementAgentService.getProcurementAgentTypeByRealm(realmId, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list Procurement Agent Type", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list Procurement Agent Type", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to list Procurement Agent Type", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @JsonView({Views.ReportView.class})
    @GetMapping("/procurementAgent/{procurementAgentId}")
    public ResponseEntity getProcurementAgent(@PathVariable("procurementAgentId") int procurementAgentId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.procurementAgentService.getProcurementAgentById(procurementAgentId, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException er) {
            logger.error("Error while trying to get Procurement Agent Id" + procurementAgentId, er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error while trying to list Procurement Agent", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/procurementAgentType/{procurementAgentTypeId}")
    public ResponseEntity getProcurementAgentType(@PathVariable("procurementAgentTypeId") int procurementAgentTypeId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.procurementAgentService.getProcurementAgentTypeById(procurementAgentTypeId, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException er) {
            logger.error("Error while trying to get Procurement Agent Type Id" + procurementAgentTypeId, er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error while trying to list Procurement Agent Type", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/procurementAgent/planningUnit")
    public ResponseEntity savePlanningUnitForProcurementAgent(@RequestBody ProcurementAgentPlanningUnit[] procurementAgentPlanningUnits, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            this.procurementAgentService.saveProcurementAgentPlanningUnit(procurementAgentPlanningUnits, curUser);
            return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to update PlanningUnit for ProcurementAgent", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error while trying to update PlanningUnit for ProcurementAgent", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @GetMapping("/procurementAgent/{procurementAgentId}/planningUnit")
//    public ResponseEntity getProcurementAgentPlanningUnitList(@PathVariable("procurementAgentId") int procurementAgentId, Authentication auth) {
//        try {
//            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
//            return new ResponseEntity(this.procurementAgentService.getProcurementAgentPlanningUnitList(procurementAgentId, true, curUser), HttpStatus.OK);
//        } catch (EmptyResultDataAccessException er) {
//            logger.error("Error while trying to get Procurement Agent Id" + procurementAgentId, er);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
//        } catch (Exception e) {
//            logger.error("Error while trying to list Procurement Agent", e);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @PostMapping("/procurementAgent/planningUnits")
//    public ResponseEntity getProcurementAgentPlanningUnitListByPlanningUnitList(@RequestBody int[] planningUnitIds, Authentication auth) {
//        try {
//            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
//            return new ResponseEntity(this.procurementAgentService.getProcurementAgentPlanningUnitListByPlanningUnitList(planningUnitIds, curUser), HttpStatus.OK);
//        } catch (EmptyResultDataAccessException er) {
//            logger.error("Error while trying to get Procurement Agent Planning Unit List", er);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
//        } catch (Exception e) {
//            logger.error("Error while trying to get Procurement Agent Planning Unit List", e);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @GetMapping("/procurementAgent/{procurementAgentId}/planningUnit/all")
    public ResponseEntity getProcurementAgentPlanningUnitListAll(@PathVariable("procurementAgentId") int procurementAgentId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.procurementAgentService.getProcurementAgentPlanningUnitList(procurementAgentId, false, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException er) {
            logger.error("Error while trying to get Procurement Agent Id" + procurementAgentId, er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error while trying to list Procurement Agent", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/procurementAgent/forecastingUnit")
    public ResponseEntity saveForecastingUnitForProcurementAgent(@RequestBody ProcurementAgentForecastingUnit[] procurementAgentForecastingUnits, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            this.procurementAgentService.saveProcurementAgentForecastingUnit(procurementAgentForecastingUnits, curUser);
            return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to update ForecastingUnit for ProcurementAgent", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error while trying to update ForecastingUnit for ProcurementAgent", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/procurementAgent/{procurementAgentId}/forecastingUnit")
    public ResponseEntity getProcurementAgentForecastingUnitList(@PathVariable("procurementAgentId") int procurementAgentId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.procurementAgentService.getProcurementAgentForecastingUnitList(procurementAgentId, true, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException er) {
            logger.error("Error while trying to get Procurement Agent Id" + procurementAgentId, er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error while trying to list Procurement Agent", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/procurementAgent/{procurementAgentId}/forecastingUnit/all")
    public ResponseEntity getProcurementAgentForecastingUnitListAll(@PathVariable("procurementAgentId") int procurementAgentId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.procurementAgentService.getProcurementAgentForecastingUnitList(procurementAgentId, false, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException er) {
            logger.error("Error while trying to get Procurement Agent Id" + procurementAgentId, er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error while trying to list Procurement Agent", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/procurementAgent/procurementUnit")
    public ResponseEntity saveProcurementUnitForProcurementAgent(@RequestBody ProcurementAgentProcurementUnit[] procurementAgentProcurementUnits, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            this.procurementAgentService.saveProcurementAgentProcurementUnit(procurementAgentProcurementUnits, curUser);
            return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to update ProcurementUnit for ProcurementAgent", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to update ProcurementUnit for ProcurementAgent", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/procurementAgent/{procurementAgentId}/procurementUnit")
    public ResponseEntity getProcurementAgentProcurementUnitList(@PathVariable("procurementAgentId") int procurementAgentId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.procurementAgentService.getProcurementAgentProcurementUnitList(procurementAgentId, true, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException er) {
            logger.error("Error while trying to get Procurement Unit for Procurement Agent" + procurementAgentId, er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error while trying to list Procurement Unit for Procurement Agent", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/procurementAgent/{procurementAgentId}/procurementUnit/all")
    public ResponseEntity getProcurementAgentProcurementUnitListAll(@PathVariable("procurementAgentId") int procurementAgentId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.procurementAgentService.getProcurementAgentProcurementUnitList(procurementAgentId, false, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException er) {
            logger.error("Error while trying to get Procurement Unit for Procurement Agent" + procurementAgentId, er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error while trying to list Procurement Unit for Procurement Agent", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/procurementAgent/getDisplayName/realmId/{realmId}/name/{name}")
    public ResponseEntity getProcurementAgentDisplayName(@PathVariable("realmId") int realmId, @PathVariable("name") String name, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.procurementAgentService.getDisplayName(realmId, name, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to get Funding source suggested display name", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
