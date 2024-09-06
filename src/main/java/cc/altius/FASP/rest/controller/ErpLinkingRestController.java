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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author akil
 */
@RequestMapping("/api/erpLinking")
@Controller
public class ErpLinkingRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ErpLinkingService erpLinkingService;
    @Autowired
    private UserService userService;

    /**
     * Tab 1 option 
     * 
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
    @PostMapping("/notLinkedQatShipments/programId/{programId}/versionId/{versionId}")
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
     * Used to autocomplete the Ro/Po order
     * 
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
    @PostMapping("/autoCompleteOrder")
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
     * Used to autocomplete the PlanningUnit
     * 
     * The autocomplete runs only if the search term provided is more than 4
     * characters
     *
     * @param planningUnitId -- planningUnit of the Selected Shipment to match
     * the Tracer Category against
     * @param puName -- The puName or the SKUCode that we need to search against
     * @param auth
     * @return
     */
    @PostMapping("/autoCompletePu")
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
     * Not linked ERP shipments
     *
     * @param input programId -- Program Id of the Shipment that you clicked on
     * to open the popup | shipmentPlanningUnitId -- Planning Unit Id of the
     * Shipment that you clicked on to open the popup | roNo -- RO no that you
     * selected from the autocomplete | filterPlanningUnitId -- Planning Unit Id
     * that you selected from the autocomplete
     * @param auth
     * @return
     */
    @PostMapping("/notLinkedErpShipments")
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
     *Not linked ERP shipments for Tab 3
     * 
     * @param input realmCountryId -- that you want to see the ERP shipments for
     * productCategorySortOrder -- Sort order of the Product Category that you
     * want to filter on planningUnitIds -- Array of PlanningUnitIds that you
     * want to filter the results on
     * @param auth
     * @return
     */
    @PostMapping("/notLinkedErpShipments/tab3")
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
     *List of already linked QAT shipments for Program and Version
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
    @PostMapping("/linkedShipments/programId/{programId}/versionId/{versionId}")
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

    /**Used to sync Shipments for ERP Linking
     * 
     * @param shipmentSyncInputList
     * @param auth
     * @return 
     */
    @PostMapping("/shipmentSync")
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

    /**Used to check if a Shipment is linked to another program
     * 
     * @param shipmentInput
     * @param auth
     * @return 
     */
    @PostMapping("/otherProgramCheck")
    public ResponseEntity shipmentLinkedToOtherProgramCheck(@RequestBody ShipmentLinkedToOtherProgramInput shipmentInput, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.erpLinkingService.getShipmentLinkedToOtherProgram(shipmentInput, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while getting Shipments Linked to other Programs check", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**Artmist history for the RO/PO
     * 
     * @param roNo
     * @param roPrimeLineNo
     * @param auth
     * @return 
     */
    @GetMapping("/artmisHistory/{roNo}/{roPrimeLineNo}")
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

    /**Batch details for the RO/PO and Prime line no
     * 
     * @param roAndRoPrimeLineNoList
     * @param auth
     * @return 
     */
    @PostMapping("/batchDetails")
    public ResponseEntity getBatchDetails(@RequestBody List<RoAndRoPrimeLineNo> roAndRoPrimeLineNoList, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.erpLinkingService.getBatchDetails(roAndRoPrimeLineNoList, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while getting Shipments Linked to other Programs check", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**Shipment lniking notification for Program and Version
     * 
     * @param programId
     * @param versionId
     * @param auth
     * @return 
     */
    @GetMapping("/shipmentLinkingNotification/programId/{programId}/versionId/{versionId}")
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

    /**Update notification as processed
     * 
     * @param eRPNotificationDTO
     * @param auth
     * @return 
     */
    @PutMapping("/updateNotification")
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

    /**Notification count 
     * 
     * @param auth
     * @return 
     */
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

    /**Notification summary
     * 
     * @param auth
     * @return 
     */
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

    /**Product category list for RealmCountry for ERP Linking
     * 
     * @param realmCountryId
     * @param auth
     * @return 
     */
    @GetMapping("/productCategory/realmCountryId/{realmCountryId}")
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
