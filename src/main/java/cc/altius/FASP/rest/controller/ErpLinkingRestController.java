/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.DTO.AutoCompletePuDTO;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.ERPNotificationDTO;
import cc.altius.FASP.model.DTO.ErpAutoCompleteDTO;
import cc.altius.FASP.model.DTO.ManualTaggingDTO;
import cc.altius.FASP.model.DTO.ManualTaggingOrderDTO;
import cc.altius.FASP.model.NotLinkedErpShipmentsInput;
import cc.altius.FASP.model.NotLinkedErpShipmentsInputTab3;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.model.RoAndRoPrimeLineNo;
import cc.altius.FASP.model.ShipmentLinkedToOtherProgramInput;
import cc.altius.FASP.model.ShipmentSyncInput;
import cc.altius.FASP.service.ErpLinkingService;
import cc.altius.FASP.service.UserService;
import java.text.ParseException;
import java.util.LinkedList;
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
import org.springframework.web.bind.annotation.PutMapping;
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
//            SupplyPlanCommitRequest s = new SupplyPlanCommitRequest();
//            SimpleCodeObject program = new SimpleCodeObject();
//            program.setId(erpOrderDTO[0].getProgramId());
//            s.setProgram(program);
//            s.setCommittedVersionId(-1);
//            s.setSaveData(false);
//            s.setNotes("ERP Linking Supply Plan Rebuild");
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

// ############################################################################################    
// ################################## New functions ###########################################
// ############################################################################################
    /**
     * This function is called when the user clicks on Tab1 option in the
     * Linking screen. Current data is taken from the local but this API serves
     * historical data.
     *
     * @param programId -- ProgramId that you want to pull the Shipments for
     * @param versionId -- VersionId that you want to pull the Shipments for
     * @param planningUnitIds -- List of PlanningUnits that you want to pull the
     * Shipments for
     * @param auth
     * @return -- List of Shipment object that matches the criteria
     */
    @PostMapping("/api/erpLinking/notLinkedQatShipments/programId/{programId}/versionId/{versionId}")
    public ResponseEntity getNotLinkedQatShipments(@PathVariable("programId") int programId, @PathVariable("versionId") int versionId, @RequestBody String[] planningUnitIds, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.erpLinkingService.getNotLinkedQatShipments(programId, versionId, planningUnitIds, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("ERP Linking : Error while trying to list Shipment list Not Linked Qat Shipments", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            logger.error("ERP Linking : Error while trying to list Shipment list Not Linked Qat Shipments", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("ERP Linking : Error while trying to list Shipment list Not Linked Qat Shipments", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * The autocomplete runs only if the roPo provided is 4 or more characters
     *
     * @param roPo -- PO Number or RO Number that you want to search for. 0 if
     * you do not want to filter on this
     * @param programId -- Program Id that you want to filter for
     * @param planningUnitId -- Planning Unit Id in case you want to filter. 0
     * if you do not want to filter on that
     * @param auth
     * @return
     */
    @PostMapping("/api/erpLinking/autoCompleteOrder")
    public ResponseEntity autoCompleteOrder(@RequestBody ErpAutoCompleteDTO erpAutoCompleteDto, Authentication auth) {
        try {
            if (erpAutoCompleteDto.getRoPo().equals("0")) {
                erpAutoCompleteDto.setRoPo(null);
            }
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            if (erpAutoCompleteDto.getRoPo() == null || erpAutoCompleteDto.getRoPo().length() >= 4) {
                return new ResponseEntity(this.erpLinkingService.autoCompleteOrder(erpAutoCompleteDto, curUser), HttpStatus.OK);
            } else {
                return new ResponseEntity(new LinkedList<String>(), HttpStatus.OK);
            }
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error linking : Error while trying to autoCompleteOrder", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            logger.error("Error linking : Error while trying to autoCompleteOrder", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error linking : Error while trying to autoCompleteOrder", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * The autocomplete runs only if the search term provided is more than 4
     * characters
     *
     * @param planningUnitId -- planningUnit of the Selected Shipment to match
     * the Tracer Category against
     * @param puName -- The puName or the SKUCode that we need to search against
     * @param auth
     * @return
     */
    @PostMapping("/api/erpLinking/autoCompletePu")
    public ResponseEntity autoCompletePu(@RequestBody AutoCompletePuDTO autoCompletePuDTO, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            if (autoCompletePuDTO.getPuName() != null && autoCompletePuDTO.getPuName().length() >= 4) {
                return new ResponseEntity(this.erpLinkingService.autoCompletePu(autoCompletePuDTO, curUser), HttpStatus.OK);
            } else {
                return new ResponseEntity(new LinkedList<>(), HttpStatus.OK);
            }
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error linking : Error while trying to autoCompletePu", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            logger.error("Error linking : Error while trying to autoCompletePu", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error linking : Error while trying to autoCompletePu", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     *
     * @param input programId -- Program Id of the Shipment that you clicked on
     * to open the popup | shipmentPlanningUnitId -- Planning Unit Id of the
     * Shipment that you clicked on to open the popup | roNo -- RO no that you
     * selected from the autocomplete | filterPlanningUnitId -- Planning Unit Id
     * that you selected from the autocomplete
     * @param auth
     * @return
     */
    @PostMapping("/api/erpLinking/notLinkedErpShipments")
    public ResponseEntity getNonLinkedErpShipments(@RequestBody NotLinkedErpShipmentsInput input, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.erpLinkingService.getNotLinkedErpShipmentsTab1AndTab3(input, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error linking : Error while trying to get list of not linked ERP shipments", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            logger.error("Error linking : Error while trying to get list of not linked ERP shipments", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error linking : Error while trying to get list of not linked ERP shipments", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     *
     * @param input realmCountryId -- that you want to see the ERP shipments for
     * productCategorySortOrder -- Sort order of the Product Category that you
     * want to filter on planningUnitIds -- Array of PlanningUnitIds that you
     * want to filter the results on
     * @param auth
     * @return
     */
    @PostMapping("/api/erpLinking/notLinkedErpShipments/tab3")
    public ResponseEntity getNonLinkedErpShipmentsTab3(@RequestBody NotLinkedErpShipmentsInputTab3 input, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.erpLinkingService.getNotLinkedErpShipmentsTab3(input, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error linking : Error while trying to get list of QAT Linked shipments", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            logger.error("Error linking : Error while trying to get list of QAT Linked shipments", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error linking : Error while trying to get list of QAT Linked shipments", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     *
     * @param programId -- Program Id that you want to see the linked Shipments
     * for
     * @param versionId -- Version Id that you want to see the linked Shipments
     * for
     * @param planningUnitIds -- List of Planning Units that the list is
     * filtered on
     * @param auth
     * @return
     */
    @PostMapping("/api/erpLinking/linkedShipments/programId/{programId}/versionId/{versionId}")
    public ResponseEntity getLinkedQatShipments(@PathVariable("programId") int programId, @PathVariable("versionId") int versionId, @RequestBody String[] planningUnitIds, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.erpLinkingService.getLinkedQatShipments(programId, versionId, planningUnitIds, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error linking : Error while trying to autoCompleteOrder", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            logger.error("Error linking : Error while trying to autoCompleteOrder", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error linking : Error while trying to autoCompleteOrder", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/api/erpLinking/shipmentSync")
    public ResponseEntity shipmentSync(@RequestBody List<ShipmentSyncInput> shipmentSyncInputList, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.erpLinkingService.getShipmentListForSync(shipmentSyncInputList, curUser), HttpStatus.OK);
        } catch (ParseException p) {
            logger.error("Error while getting Sync list for Shipments", p);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.PRECONDITION_FAILED);
        } catch (Exception e) {
            logger.error("Error while getting Sync list for Shipments", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/api/erpLinking/otherProgramCheck")
    public ResponseEntity shipmentLinkedToOtherProgramCheck(@RequestBody ShipmentLinkedToOtherProgramInput shipmentInput, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.erpLinkingService.getShipmentLinkedToOtherProgram(shipmentInput, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while getting Shipments Linked to other Programs check", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/api/erpLinking/artmisHistory/{roNo}/{roPrimeLineNo}")
    public ResponseEntity artmisHistory(@PathVariable("roNo") String roNo, @PathVariable("roPrimeLineNo") int roPrimeLineNo, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.erpLinkingService.getArtmisHistory(roNo, roPrimeLineNo), HttpStatus.OK);
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

    @PostMapping("/api/erpLinking/batchDetails")
    public ResponseEntity getBatchDetails(@RequestBody List<RoAndRoPrimeLineNo> roAndRoPrimeLineNoList, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.erpLinkingService.getBatchDetails(roAndRoPrimeLineNoList, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while getting Shipments Linked to other Programs check", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/api/erpLinking/shipmentLinkingNotification/programId/{programId}/versionId/{versionId}")
    public ResponseEntity shipmentLinkingNotification(@PathVariable("programId") int programId, @PathVariable("versionId") int versionId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.erpLinkingService.getNotificationList(programId, versionId), HttpStatus.OK);
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

    @PutMapping("/api/erpLinking/updateNotification")
    public ResponseEntity updateNotification(@RequestBody List<ERPNotificationDTO> eRPNotificationDTO, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            this.erpLinkingService.updateNotification(eRPNotificationDTO, curUser);
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

    @GetMapping("/api/erpLinking/getNotificationCount")
    public ResponseEntity getNotificationCount(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.erpLinkingService.getNotificationCount(curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/api/erpLinking/getNotificationSummary")
    public ResponseEntity getNotificationSummary(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.erpLinkingService.getNotificationSummary(curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/api/erpLinking/productCategory/realmCountryId/{realmCountryId}")
    public ResponseEntity getProductCategoryListForRealmCountryForErpLinking(@PathVariable(value = "realmCountryId", required = true) int realmCountryId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.erpLinkingService.getProductCategoryListForRealmCountryForErpLinking(curUser, realmCountryId), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Product Category", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
