/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.ARTMISHistoryDTO;
import cc.altius.FASP.model.DTO.ERPNotificationDTO;
import cc.altius.FASP.model.DTO.ErpOrderAutocompleteDTO;
import cc.altius.FASP.model.DTO.ManualTaggingDTO;
import cc.altius.FASP.model.DTO.ManualTaggingOrderDTO;
import cc.altius.FASP.model.DTO.NotificationSummaryDTO;
import cc.altius.FASP.model.Shipment;
import cc.altius.FASP.model.SimpleCodeObject;
import java.util.List;

/**
 *
 * @author akil
 */
public interface ErpLinkingDao {

    public ManualTaggingDTO getShipmentDetailsByParentShipmentId(int parentShipmentId);

    public List<ManualTaggingDTO> getShipmentListForManualTagging(ManualTaggingDTO manualTaggingDTO, CustomUserDetails curUser);

    public List<ManualTaggingDTO> getNotLinkedShipments(int programId, int linkingTypeId);

    public List<ManualTaggingOrderDTO> getOrderDetailsByOrderNoAndPrimeLineNo(String roNoOrderNo, int programId, int planningUnitId, int linkingType, int parentShipmentId);

    public List<ManualTaggingDTO> getOrderDetailsByForNotLinkedERPShipments(String roNoOrderNo, int planningUnitId, int linkingType);

    public int linkShipmentWithARTMIS(ManualTaggingOrderDTO manualTaggingOrderDTO, CustomUserDetails curUser);

    public int linkShipmentWithARTMISWithoutShipmentid(ManualTaggingOrderDTO manualTaggingOrderDTO, CustomUserDetails curUser);

    public List<ManualTaggingDTO> getShipmentListForDelinking(int programId, int planningUnitId);

    public void delinkShipment(ManualTaggingOrderDTO erpOrderDTO, CustomUserDetails curUser);

    public int createERPNotification(String orderNo, int primeLineNo, int shipmentId, int notificationTypeId);

    public List<ERPNotificationDTO> getNotificationList(ERPNotificationDTO eRPNotificationDTO);

    public int updateNotification(ERPNotificationDTO eRPNotificationDTO, CustomUserDetails curUser);

    public int getNotificationCount(CustomUserDetails curUser);

    public List<ARTMISHistoryDTO> getARTMISHistory(String orderNo, int primeLineNo);

    public int checkPreviousARTMISPlanningUnitId(String orderNo, int primeLineNo);

    public List<NotificationSummaryDTO> getNotificationSummary(CustomUserDetails curUser);

    public int tab3ShipmentCreation(int shipmentId, CustomUserDetails curUser);

    public int checkIfOrderNoAlreadyTagged(String orderNo, int primeLineNo);

    public int updateERPLinking(ManualTaggingOrderDTO manualTaggingOrderDTO, CustomUserDetails curUser);

    // ################################## New functions ###########################################
    public List<Shipment> getNotLinkedQatShipments(int programId, int versionId, String[] planningUnitIds, CustomUserDetails curUser);

    public List<String> autoCompleteOrder(String roPo, int programId, int planningUnitId, CustomUserDetails curUser);
    
    public List<SimpleCodeObject> autoCompletePu(int planningUnitId, String puName, CustomUserDetails curUser);
}
