/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.ErpLinkingDao;
import cc.altius.FASP.framework.GlobalConstants;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.ArtmisHistory;
import cc.altius.FASP.model.DTO.AutoCompletePuDTO;
import cc.altius.FASP.model.DTO.ERPNotificationDTO;
import cc.altius.FASP.model.DTO.ErpAutoCompleteDTO;
import cc.altius.FASP.model.DTO.ManualTaggingDTO;
import cc.altius.FASP.model.DTO.ManualTaggingOrderDTO;
import cc.altius.FASP.model.NotLinkedErpShipmentsInput;
import cc.altius.FASP.model.DTO.NotificationSummaryDTO;
import cc.altius.FASP.model.ExtendedProductCategory;
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
import cc.altius.FASP.service.AclService;
import cc.altius.FASP.service.ErpLinkingService;
import cc.altius.FASP.service.ProgramService;
import cc.altius.utils.TreeUtils.Node;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author akil
 */
@Service
public class ErpLinkingServiceImpl implements ErpLinkingService {

    @Autowired
    private AclService aclService;
    @Autowired
    private ProgramService programService;

    @Autowired
    private ErpLinkingDao erpLinkingDao;

    @Override
    public ManualTaggingDTO getShipmentDetailsByParentShipmentId(int parentShipmentId) {
        return this.erpLinkingDao.getShipmentDetailsByParentShipmentId(parentShipmentId);
    }

    @Override
    public List<ManualTaggingDTO> getOrderDetailsByForNotLinkedERPShipments(String roNoOrderNo, int planningUnitId, int linkingType) {
        return this.erpLinkingDao.getOrderDetailsByForNotLinkedERPShipments(roNoOrderNo, planningUnitId, linkingType);
    }

    @Override
    public int createERPNotification(String orderNo, int primeLineNo, int shipmentId, int notificationTypeId) {
        return this.erpLinkingDao.createERPNotification(orderNo, primeLineNo, shipmentId, notificationTypeId);
    }

    @Override
    public List<ERPNotificationDTO> getNotificationList(int programId, int versionId) {
        return this.erpLinkingDao.getNotificationList(programId, versionId);
    }

    @Override
    public int updateNotification(List<ERPNotificationDTO> eRPNotificationDTOList, CustomUserDetails curUser) {
        int count = 0;
        for (ERPNotificationDTO eRPNotificationDTO : eRPNotificationDTOList) {
            count += this.erpLinkingDao.updateNotification(eRPNotificationDTO, curUser);
        }
        return count;
    }

    @Override
    public int getNotificationCount(CustomUserDetails curUser) {
        return this.erpLinkingDao.getNotificationCount(curUser);
    }

    @Override
    public int checkPreviousARTMISPlanningUnitId(String orderNo, int primeLineNo) {
        return this.erpLinkingDao.checkPreviousARTMISPlanningUnitId(orderNo, primeLineNo);
    }

    @Override
    public List<NotificationSummaryDTO> getNotificationSummary(CustomUserDetails curUser) {
        return this.erpLinkingDao.getNotificationSummary(curUser);
    }

    @Override
    public List<ManualTaggingDTO> getShipmentListForManualTagging(ManualTaggingDTO manualTaggingDTO, CustomUserDetails curUser) {
        return this.erpLinkingDao.getShipmentListForManualTagging(manualTaggingDTO, curUser);
    }

    @Override
    public List<ManualTaggingOrderDTO> getOrderDetailsByOrderNoAndPrimeLineNo(String roNoOrderNo, int programId, int planningUnitId, int linkingType, int parentShipmentId) {
        return this.erpLinkingDao.getOrderDetailsByOrderNoAndPrimeLineNo(roNoOrderNo, programId, planningUnitId, linkingType, parentShipmentId);
    }

    @Override
    public List<Integer> linkShipmentWithARTMIS(ManualTaggingOrderDTO[] manualTaggingOrderDTO, CustomUserDetails curUser) {
        try {
            List<Integer> result = new ArrayList<>();
            for (int i = 0; i < manualTaggingOrderDTO.length; i++) {
                if (manualTaggingOrderDTO[i].isActive()) {
                    int id = 0;
                    int count = this.erpLinkingDao.checkIfOrderNoAlreadyTagged(manualTaggingOrderDTO[i].getOrderNo(), manualTaggingOrderDTO[i].getPrimeLineNo());
                    if (manualTaggingOrderDTO[i].getShipmentId() != 0) {
                        if (count != 0) {
                            id = this.erpLinkingDao.updateERPLinking(manualTaggingOrderDTO[i], curUser);
                        } else {
                            id = this.erpLinkingDao.linkShipmentWithARTMIS(manualTaggingOrderDTO[i], curUser);
                        }
                    } else {
                        if (count == 0) {
                            id = this.erpLinkingDao.linkShipmentWithARTMISWithoutShipmentid(manualTaggingOrderDTO[i], curUser);
                        }
                    }
                    result.add(id);
                } else if (!manualTaggingOrderDTO[i].isActive()) {
                    this.erpLinkingDao.delinkShipment(manualTaggingOrderDTO[i], curUser);
                }
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ManualTaggingDTO> getShipmentListForDelinking(int programId, int planningUnitId) {
        return this.erpLinkingDao.getShipmentListForDelinking(programId, planningUnitId);
    }

    @Override
    public List<ManualTaggingDTO> getNotLinkedShipments(int programId, int linkingTypeId) {
        return this.erpLinkingDao.getNotLinkedShipments(programId, linkingTypeId);
    }

    @Override
    public void delinkShipment(ManualTaggingOrderDTO erpOrderDTO, CustomUserDetails curUser) {
        this.erpLinkingDao.delinkShipment(erpOrderDTO, curUser);
    }

    // ################################## New functions ###########################################
    @Override
    public List<Shipment> getNotLinkedQatShipments(int programId, int versionId, String[] planningUnitIds, CustomUserDetails curUser) {
        this.programService.getSimpleProgramById(programId, GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, curUser);
        return this.erpLinkingDao.getNotLinkedQatShipments(programId, versionId, planningUnitIds, curUser);
    }

    @Override
    public List<String> autoCompleteOrder(ErpAutoCompleteDTO erpAutoCompleteDTO, CustomUserDetails curUser) {
        this.programService.getSimpleProgramById(erpAutoCompleteDTO.getProgramId(), GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, curUser);
        return this.erpLinkingDao.autoCompleteOrder(erpAutoCompleteDTO, curUser);
    }

    @Override
    public List<SimpleCodeObject> autoCompletePu(AutoCompletePuDTO autoCompletePuDTO, CustomUserDetails curUser) {
        return this.erpLinkingDao.autoCompletePu(autoCompletePuDTO, curUser);
    }

    @Override
    public List<ShipmentLinkingOutput> getNotLinkedErpShipmentsTab1AndTab3(NotLinkedErpShipmentsInput input, CustomUserDetails curUser) {
        if (input.getProgramId() != 0) {
            this.programService.getSimpleProgramById(input.getProgramId(), GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, curUser);
        }
        return this.erpLinkingDao.getNotLinkedErpShipmentsTab1AndTab3(input, curUser);
    }

    @Override
    public List<ShipmentLinkingOutput> getNotLinkedErpShipmentsTab3(NotLinkedErpShipmentsInputTab3 input, CustomUserDetails curUser) {
        return this.erpLinkingDao.getNotLinkedErpShipmentsTab3(input, curUser);
    }

    @Override
    public List<ShipmentLinkingOutput> getLinkedQatShipments(int programId, int versionId, String[] planningUnitIds, CustomUserDetails curUser) {
        this.programService.getSimpleProgramById(programId, GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, curUser);
        return this.erpLinkingDao.getLinkedQatShipments(programId, versionId, planningUnitIds, curUser);
    }

    @Override
    public Map<Integer, List<ShipmentLinkingOutput>> getShipmentListForSync(List<ShipmentSyncInput> shipmentSyncInputList, CustomUserDetails curUser) throws ParseException {
        Map<Integer, List<ShipmentLinkingOutput>> result = new HashMap<>();
        for (ShipmentSyncInput ssi : shipmentSyncInputList) {
            try {
                this.programService.getSimpleProgramById(ssi.getProgramId(), GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, curUser);
                // In case the lastSyncDate is not a valid format check and throw an exception now
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                sdf.parse(ssi.getLastSyncDate());
                result.put(ssi.getProgramId(), this.erpLinkingDao.getShipmentListForSync(ssi, curUser));
            } catch (ParseException pe) {
                throw new ParseException(pe.getMessage(), pe.getErrorOffset());
            } catch (Exception e) {
                // Since the user does not have the rights to this program we need to decied what to do, throw and exception or just skip the program and go ahead
            }
        }
        return result;
    }

    @Override
    public List<ShipmentLinkedToOtherProgramOutput> getShipmentLinkedToOtherProgram(ShipmentLinkedToOtherProgramInput shipmentInput, CustomUserDetails curUser) {
        return this.erpLinkingDao.getShipmentLinkedToOtherProgram(shipmentInput, curUser);
    }

    @Override
    public ArtmisHistory getArtmisHistory(String roNo, int roPrimeLineNo) {
        return this.erpLinkingDao.getArtmisHistory(roNo, roPrimeLineNo);
    }

    @Override
    public List<LinkedShipmentBatchDetails> getBatchDetails(List<RoAndRoPrimeLineNo> roAndRoPrimeLineNoList, CustomUserDetails curUser) {
        return this.erpLinkingDao.getBatchDetails(roAndRoPrimeLineNoList, curUser);
    }

    @Override
    public List<ProductCategory> getProductCategoryListForRealmCountryForErpLinking(CustomUserDetails curUser, int realmCountryId) {
        return this.erpLinkingDao.getProductCategoryListForRealmCountryForErpLinking(curUser, realmCountryId);
    }

}
