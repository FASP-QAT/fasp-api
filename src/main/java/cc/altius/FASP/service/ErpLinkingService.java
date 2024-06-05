/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.ArtmisHistory;
import cc.altius.FASP.model.DTO.AutoCompletePuDTO;
import cc.altius.FASP.model.DTO.ERPNotificationDTO;
import cc.altius.FASP.model.DTO.ErpAutoCompleteDTO;
import cc.altius.FASP.model.DTO.ManualTaggingDTO;
import cc.altius.FASP.model.DTO.ManualTaggingOrderDTO;
import cc.altius.FASP.model.NotLinkedErpShipmentsInput;
import cc.altius.FASP.model.DTO.NotificationSummaryDTO;
import cc.altius.FASP.model.LinkedShipmentBatchDetails;
import cc.altius.FASP.model.NotLinkedErpShipmentsInputTab3;
import cc.altius.FASP.model.ProductCategory;
import cc.altius.FASP.model.RoAndRoPrimeLineNo;
import cc.altius.FASP.model.ShipmentLinkingOutput;
import cc.altius.FASP.model.Shipment;
import cc.altius.FASP.model.ShipmentLinkedToOtherProgramInput;
import cc.altius.FASP.model.ShipmentLinkedToOtherProgramOutput;
import cc.altius.FASP.model.ShipmentSyncInput;
import cc.altius.FASP.model.SimpleCodeObject;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

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

    public List<ManualTaggingDTO> getOrderDetailsByForNotLinkedERPShipments(String roNoOrderNo, int planningUnitId, int linkingType);

    public int createERPNotification(String orderNo, int primeLineNo, int shipmentId, int notificationTypeId);

    public List<ERPNotificationDTO> getNotificationList(int programId, int versionId);

    public int updateNotification(List<ERPNotificationDTO> eRPNotificationDTOList, CustomUserDetails curUser);

    public int getNotificationCount(CustomUserDetails curUser);

    public int checkPreviousARTMISPlanningUnitId(String orderNo, int primeLineNo);

    public List<NotificationSummaryDTO> getNotificationSummary(CustomUserDetails curUser);

    // ################################## New functions ###########################################
    public List<Shipment> getNotLinkedQatShipments(int programId, int versionId, String[] planningUnitIds, CustomUserDetails curUser);

    public List<String> autoCompleteOrder(ErpAutoCompleteDTO erpAutoCompleteDTO, CustomUserDetails curUser);

    public List<SimpleCodeObject> autoCompletePu(AutoCompletePuDTO autoCompletePuDTO, CustomUserDetails curUser);

    public List<ShipmentLinkingOutput> getNotLinkedErpShipmentsTab1AndTab3(NotLinkedErpShipmentsInput input, CustomUserDetails curUser);

    public List<ShipmentLinkingOutput> getNotLinkedErpShipmentsTab3(NotLinkedErpShipmentsInputTab3 input, CustomUserDetails curUser);

    public List<ShipmentLinkingOutput> getLinkedQatShipments(int programId, int versionId, String[] planningUnitIds, CustomUserDetails curUser);

    public Map<Integer, List<ShipmentLinkingOutput>> getShipmentListForSync(List<ShipmentSyncInput> shipmentSyncInputList, CustomUserDetails curUser) throws ParseException;

    public List<ShipmentLinkedToOtherProgramOutput> getShipmentLinkedToOtherProgram(ShipmentLinkedToOtherProgramInput shipmentInput, CustomUserDetails curUser);

    public ArtmisHistory getArtmisHistory(String roNo, int roPrimeLineNo);

    public List<LinkedShipmentBatchDetails> getBatchDetails(List<RoAndRoPrimeLineNo> roAndRoPrimeLineNoList, CustomUserDetails curUser);

    public List<ProductCategory> getProductCategoryListForRealmCountryForErpLinking(CustomUserDetails curUser, int realmCountryId);
}
