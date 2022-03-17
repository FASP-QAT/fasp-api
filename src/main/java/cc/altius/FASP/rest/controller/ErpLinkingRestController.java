/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.ERPNotificationDTO;
import cc.altius.FASP.model.DTO.ManualTaggingDTO;
import cc.altius.FASP.model.DTO.ManualTaggingOrderDTO;
import cc.altius.FASP.model.erpLinking.QatErpLinkedShipmentsInput;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SupplyPlanCommitRequest;
import cc.altius.FASP.service.ErpLinkingService;
import cc.altius.FASP.service.UserService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author akil
 */
@Controller
public class ErpLinkingRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ErpLinkingService erpLinkingService;
    @Autowired
    private UserService userService;

    @PostMapping("/getShipmentDetailsByParentShipmentId")
    public ResponseEntity getShipmentDetailsByParentShipmentId(@RequestBody ManualTaggingDTO manualTaggingDTO, Authentication auth) {
//        System.out.println("parentShipmentId--------" + manualTaggingDTO);
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.erpLinkingService.getShipmentDetailsByParentShipmentId(manualTaggingDTO.getParentShipmentId()), HttpStatus.OK);
//            return new ResponseEntity("", HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list Shipment list for Manual Tagging", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list Shipment list for Manual Tagging", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to list Shipment list for Manual Tagging", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*
    * This is called for All 3 of the Radio buttons with 
    * LinkingType = 1 --> // QAT Shipments that can be linked
    * LinkingType = 2 --> // QAT Shipments that are already linked
    * LinkingType = 3 --> // ERP Shipments that are not currently linked
     */
    @PostMapping("/manualTagging")
    public ResponseEntity getShipmentListForManualTagging(@RequestBody ManualTaggingDTO manualTaggingDTO, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.erpLinkingService.getShipmentListForManualTagging(manualTaggingDTO, curUser), HttpStatus.OK);
//            return new ResponseEntity("", HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("ERP Linking : Error while trying to list Shipment list for Manual Tagging 1---", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            logger.error("ERP Linking : Error while trying to list Shipment list for Manual Tagging 2---", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("ERP Linking : Error while trying to list Shipment list for Manual Tagging 3---", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/manualTagging/notLinkedShipments/{programId}/{linkingType}")
    public ResponseEntity getNotLinkedShipmentListForManualTagging(@PathVariable("programId") int programId, @PathVariable("linkingType") int linkingType, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.erpLinkingService.getNotLinkedShipments(programId, linkingType), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list Shipment list for Manual Tagging", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list Shipment list for Manual Tagging", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to list Shipment list for Manual Tagging", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/shipmentLinkingNotification")
    public ResponseEntity shipmentLinkingNotification(@RequestBody ERPNotificationDTO eRPNotificationDTO, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.erpLinkingService.getNotificationList(eRPNotificationDTO), HttpStatus.OK);
//            return new ResponseEntity("", HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list Shipment list for Manual Tagging", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list Shipment list for Manual Tagging", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to list Shipment list for Manual Tagging", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/updateNotification")
    public ResponseEntity updateNotification(@RequestBody ERPNotificationDTO[] eRPNotificationDTO, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            for (ERPNotificationDTO e : eRPNotificationDTO) {
//                System.out.println("e-------------------*********************" + e);
                this.erpLinkingService.updateNotification(e, curUser);
            }
            // Do a rebuild of the Supply Plan
            // this.programDataService.getNewSupplyPlanList(eRPNotificationDTO[0].getProgramId(), -1, true, false);
            return new ResponseEntity(true, HttpStatus.OK);
//            return new ResponseEntity("", HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            logger.error("Error while trying to list Shipment list for Manual Tagging", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            e.printStackTrace();
            logger.error("Error while trying to list Shipment list for Manual Tagging", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error while trying to list Shipment list for Manual Tagging", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getNotificationCount")
    public ResponseEntity getNotificationCount(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.erpLinkingService.getNotificationCount(curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getNotificationSummary")
    public ResponseEntity getNotificationSummary(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.erpLinkingService.getNotificationSummary(curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/artmisHistory/{orderNo}/{primeLineNo}")
    public ResponseEntity artmisHistory(@PathVariable("orderNo") String orderNo, @PathVariable("primeLineNo") int primeLineNo, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.erpLinkingService.getARTMISHistory(orderNo, primeLineNo), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list Shipment list for Manual Tagging", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list Shipment list for Manual Tagging", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to list Shipment list for Manual Tagging", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/searchErpOrderData/{term}/{programId}/{erpPlanningUnitId}/{linkingType}")
    public ResponseEntity searchErpOrderData(@PathVariable("term") String term, @PathVariable("programId") int programId, @PathVariable("erpPlanningUnitId") int planningUnitId, @PathVariable("linkingType") int linkingType, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.erpLinkingService.getErpOrderSearchData(term, programId, planningUnitId, linkingType), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list Shipment list for Manual Tagging", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list Shipment list for Manual Tagging", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to list Shipment list for Manual Tagging", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/orderDetails/{roNoOrderNo}/{programId}/{erpPlanningUnitId}/{linkingType}/{parentShipmentId}")
    public ResponseEntity getOrderDetailsByOrderNoAndPrimeLineNo(@PathVariable("roNoOrderNo") String roNoOrderNo, @PathVariable("programId") int programId, @PathVariable("erpPlanningUnitId") int planningUnitId, @PathVariable("linkingType") int linkingType, @PathVariable("parentShipmentId") int parentShipmentId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            if (linkingType == 3) {
                return new ResponseEntity(this.erpLinkingService.getOrderDetailsByForNotLinkedERPShipments(roNoOrderNo, planningUnitId, linkingType), HttpStatus.OK);
            } else {
                return new ResponseEntity(this.erpLinkingService.getOrderDetailsByOrderNoAndPrimeLineNo(roNoOrderNo, programId, planningUnitId, linkingType, parentShipmentId), HttpStatus.OK);
            }
        } catch (EmptyResultDataAccessException e) {
            logger.error("ERP Linking : Error while trying to get order details for Manual Tagging 1---", e);
            return new ResponseEntity(new ResponseCode("static.mt.noDetailsFound"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            logger.error("ERP Linking : Error while trying to get order details for Manual Tagging 2---", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("ERP Linking : Error while trying to  get order details for Manual Tagging 3---", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/linkShipmentWithARTMIS/")
    @Transactional
    public ResponseEntity linkShipmentWithARTMIS(@RequestBody ManualTaggingOrderDTO[] erpOrderDTO, Authentication auth) {
        try {
//            System.out.println("erpOrderDTO%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%" + Arrays.toString(erpOrderDTO));
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            List<Integer> result = this.erpLinkingService.linkShipmentWithARTMIS(erpOrderDTO, curUser);
            logger.info("ERP Linking : Going to get new supply plan list ");
            SupplyPlanCommitRequest s = new SupplyPlanCommitRequest();
            SimpleCodeObject program = new SimpleCodeObject();
            program.setId(erpOrderDTO[0].getProgramId());
            s.setProgram(program);
            s.setCommittedVersionId(-1);
            s.setSaveData(false);
            s.setNotes("ERP Linking Supply Plan Rebuild");
            // Add to Commit Request so SupplyPlan can be rebuilt
//            int commitRequestId = this.programDataService.addSupplyPlanCommitRequest(s, curUser);
//            this.programDataService.getNewSupplyPlanList(erpOrderDTO[0].getProgramId(), -1, true, false);
            logger.info("ERP Linking : supply plan rebuild done ");
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("ERP Linking : Linking error 1---", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            logger.error("ERP Linking : Linking error 2---", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("ERP Linking : Linking error 3---", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/shipmentListForDelinking/{programId}/{planningUnitId}")
    public ResponseEntity getShipmentListForDelinking(@PathVariable("programId") int programId, @PathVariable("planningUnitId") int planningUnitId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.erpLinkingService.getShipmentListForDelinking(programId, planningUnitId), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list Shipment list for Manual Tagging", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list Shipment list for Manual Tagging", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to list Shipment list for Manual Tagging", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/delinkShipment/")
    public ResponseEntity delinkShipment(@RequestBody ManualTaggingOrderDTO erpOrderDTO, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            this.erpLinkingService.delinkShipment(erpOrderDTO, curUser);
            logger.info("ERP Linking : Going to get new supply plan list ");
            // Rebuild Supply plan after de-linking
            // this.programDataService.getNewSupplyPlanList(erpOrderDTO.getProgramId(), -1, true, false);
            logger.info("ERP Linking : new supply plan rebuild done ");
            return new ResponseEntity(new ResponseCode("success"), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("ERP Linking : Delinking error 1---", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            logger.error("ERP Linking : Delinking error 2---", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("ERP Linking : Delinking error 3---", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ################################## New functions ###########################################
    @PostMapping("/qatErpLinkedShipments")
    public ResponseEntity getQatErpLinkedShipments(@RequestBody QatErpLinkedShipmentsInput input, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.erpLinkingService.getQatErpLinkedShipments(input, curUser), HttpStatus.OK);
//            return new ResponseEntity("", HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("ERP Linking : Error while trying to list Shipment list for Manual Tagging 1---", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            logger.error("ERP Linking : Error while trying to list Shipment list for Manual Tagging 2---", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("ERP Linking : Error while trying to list Shipment list for Manual Tagging 3---", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
