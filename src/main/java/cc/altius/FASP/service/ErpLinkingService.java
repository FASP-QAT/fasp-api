/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.ARTMISHistoryDTO;
import cc.altius.FASP.model.DTO.ERPNotificationDTO;
import cc.altius.FASP.model.DTO.ErpOrderAutocompleteDTO;
import cc.altius.FASP.model.DTO.ManualTaggingDTO;
import cc.altius.FASP.model.DTO.ManualTaggingOrderDTO;
import cc.altius.FASP.model.DTO.NotificationSummaryDTO;
import cc.altius.FASP.model.erpLinking.ErpShipmentsOutput;
import cc.altius.FASP.model.erpLinking.QatErpLinkedShipmentsInput;
import java.util.List;

/**
 *
 * @author akil
 */
public interface ErpLinkingService {

    public ManualTaggingDTO getShipmentDetailsByParentShipmentId(int parentShipmentId);

    public List<ManualTaggingDTO> getShipmentListForManualTagging(ManualTaggingDTO manualTaggingDTO, CustomUserDetails curUser);

    public List<ManualTaggingDTO> getNotLinkedShipments(int programId, int linkingTypeId);

    public List<ManualTaggingOrderDTO> getOrderDetailsByOrderNoAndPrimeLineNo(String roNoOrderNo, int programId, int planningUnitId, int linkingType, int parentShipmentId);

    public List<Integer> linkShipmentWithARTMIS(ManualTaggingOrderDTO[] manualTaggingOrderDTO, CustomUserDetails curUser);

    public List<ManualTaggingDTO> getShipmentListForDelinking(int programId, int planningUnitId);

    public void delinkShipment(ManualTaggingOrderDTO erpOrderDTO, CustomUserDetails curUser);

    public List<ErpOrderAutocompleteDTO> getErpOrderSearchData(String term, int programId, int planningUnitId, int linkingType);

    public List<ManualTaggingDTO> getOrderDetailsByForNotLinkedERPShipments(String roNoOrderNo, int planningUnitId, int linkingType);

    public int createERPNotification(String orderNo, int primeLineNo, int shipmentId, int notificationTypeId);

    public List<ERPNotificationDTO> getNotificationList(ERPNotificationDTO eRPNotificationDTO);

    public int updateNotification(ERPNotificationDTO eRPNotificationDTO, CustomUserDetails curUser);

    public int getNotificationCount(CustomUserDetails curUser);

    public List<ARTMISHistoryDTO> getARTMISHistory(String orderNo, int primeLineNo);

    public int checkPreviousARTMISPlanningUnitId(String orderNo, int primeLineNo);

    public List<NotificationSummaryDTO> getNotificationSummary(CustomUserDetails curUser);

    // ################################## New functions ###########################################
    public List<ErpShipmentsOutput> getQatErpLinkedShipments(QatErpLinkedShipmentsInput input, CustomUserDetails curUser);
}
