/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao;

import cc.altius.FASP.exception.AccessControlFailedException;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.ErpOrderAutocompleteDTO;
import cc.altius.FASP.model.DTO.HealthAreaAndRealmCountryDTO;
import cc.altius.FASP.model.DTO.ManualTaggingDTO;
import cc.altius.FASP.model.DTO.ManualTaggingOrderDTO;
import cc.altius.FASP.model.DTO.NotificationSummaryDTO;
import cc.altius.FASP.model.DTO.ProgramPlanningUnitProcurementAgentInput;
import cc.altius.FASP.model.DatasetPlanningUnit;
import cc.altius.FASP.model.LoadProgram;
import cc.altius.FASP.model.Program;
import cc.altius.FASP.model.ProgramIdAndVersionId;
import cc.altius.FASP.model.ProgramInitialize;
import cc.altius.FASP.model.ProgramPlanningUnit;
import cc.altius.FASP.model.ProgramPlanningUnitProcurementAgentPrice;
import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleProgram;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.SimpleObjectWithFu;
import cc.altius.FASP.model.SimpleObjectWithType;
import cc.altius.FASP.model.SimplePlanningUnitObject;
import cc.altius.FASP.model.report.StockStatusVerticalDropdownInput;
import cc.altius.FASP.model.report.RealmCountryIdsAndHealthAreaIds;
import cc.altius.FASP.model.report.TreeAnchorInput;
import cc.altius.FASP.model.report.TreeAnchorOutput;
import java.util.List;

/**
 *
 * @author altius
 */
public interface ProgramDao {

    public List<SimpleProgram> getProgramListForDropdown(int realmId, int programTypeId, boolean aclFilter, CustomUserDetails curUser, boolean active);

    public List<SimpleCodeObject> getProgramListByVersionStatusAndVersionType(String versionStatusIdList, String versionTypeIdList, CustomUserDetails curUser);

    public List<SimpleProgram> getProgramWithFilterForHealthAreaAndRealmCountryListForDropdown(int realmId, int programTypeId, HealthAreaAndRealmCountryDTO input, CustomUserDetails curUser);

    public List<SimpleProgram> getProgramWithFilterForMultipleRealmCountryListForDropdown(int programTypeId, String realmCountryIdsStr, CustomUserDetails curUser);

    public int addProgram(ProgramInitialize p, int realmId, CustomUserDetails curUser);

    public int updateProgram(ProgramInitialize p, CustomUserDetails curUser);

    public List<Program> getProgramList(int programTypeId, CustomUserDetails curUser, boolean active);

    public List<Program> getProgramListForProgramIds(String[] programIds, CustomUserDetails curUser);

    public List<Program> getProgramListForRealmId(int realmId, int programTypeId, CustomUserDetails curUser);

    public List<ProgramPlanningUnit> getPlanningUnitListForProgramId(int programId, boolean active, CustomUserDetails curUser);

    public List<ProgramPlanningUnit> getPlanningUnitListForProgramIdAndTracerCategoryIds(int programId, boolean active, String[] tracerCategoryIds, CustomUserDetails curUser);

    public List<SimplePlanningUnitObject> getSimplePlanningUnitListForProgramIdAndTracerCategoryIds(int programId, boolean active, String[] tracerCategoryIds, CustomUserDetails curUser);

    public List<SimpleObject> getPlanningUnitListForProgramIds(String programIds, CustomUserDetails curUser);

    public List<SimpleObjectWithType> getProgramAndPlanningUnitListForProgramIds(String programIds, CustomUserDetails curUser);

    public int saveProgramPlanningUnit(ProgramPlanningUnit[] programPlanningUnits, CustomUserDetails curUser) throws AccessControlFailedException;

    public List<ProgramPlanningUnitProcurementAgentPrice> getProgramPlanningUnitProcurementAgentList(ProgramPlanningUnitProcurementAgentInput ppupa, boolean active, CustomUserDetails curUser);

    public int saveProgramPlanningUnitProcurementAgentPrice(ProgramPlanningUnitProcurementAgentPrice[] programPlanningUnitProcurementAgentPrices, CustomUserDetails curUser);

    public List<Program> getProgramListForSync(String lastSyncDate, CustomUserDetails curUser);

    public List<ProgramPlanningUnit> getProgramPlanningUnitListForSync(String lastSyncDate, CustomUserDetails curUser);

    public List<ProgramPlanningUnit> getPlanningUnitListForProgramAndCategoryId(int programId, int productCategory, boolean active, CustomUserDetails curUser);

    public Program getProgramList(int realmId, int programId, int versionId);

    public List<ManualTaggingDTO> getShipmentListForManualTagging(ManualTaggingDTO manualTaggingDTO, CustomUserDetails curUser);

    public List<ManualTaggingOrderDTO> getOrderDetailsByOrderNoAndPrimeLineNo(String roNoOrderNo, int programId, int planningUnitId, int linkingType, int parentShipmentId);

    public List<ManualTaggingDTO> getOrderDetailsByForNotLinkedERPShipments(String roNoOrderNo, int planningUnitId, int linkingType);

    public int linkShipmentWithARTMIS(ManualTaggingOrderDTO manualTaggingOrderDTO, CustomUserDetails curUser);

    public int linkShipmentWithARTMISWithoutShipmentid(ManualTaggingOrderDTO manualTaggingOrderDTO, CustomUserDetails curUser);

    public List<ManualTaggingDTO> getShipmentListForDelinking(int programId, int planningUnitId);

    public List<ManualTaggingDTO> getNotLinkedShipments(int programId, int linkingTypeId);

    public void delinkShipment(ManualTaggingOrderDTO erpOrderDTO, CustomUserDetails curUser);

    public List<LoadProgram> getLoadProgram(int programTypeId, CustomUserDetails curUser);

    public LoadProgram getLoadProgram(int programId, int page, int programTypeId, CustomUserDetails curUser);

    public boolean validateProgramCode(int realmId, int programId, String programCode, CustomUserDetails curUser);

    public List<Program> getProgramListForSyncProgram(String programIdsString, CustomUserDetails curUser);

    public List<ProgramPlanningUnit> getProgramPlanningUnitListForSyncProgram(String programIdsString, CustomUserDetails curUser);

    public List<ErpOrderAutocompleteDTO> getErpOrderSearchData(String term, int programId, int planningUnitId, int linkingType);

    public String getSupplyPlanReviewerList(int programId, CustomUserDetails curUser) throws AccessControlFailedException;

    public ManualTaggingDTO getShipmentDetailsByParentShipmentId(int parentShipmentId);

    public int checkPreviousARTMISPlanningUnitId(String orderNo, int primeLineNo);

    public List<NotificationSummaryDTO> getNotificationSummary(CustomUserDetails curUser);

    public int tab3ShipmentCreation(int shipmentId, CustomUserDetails curUser);

    public int checkIfOrderNoAlreadyTagged(String orderNo, int primeLineNo);

    public int updateERPLinking(ManualTaggingOrderDTO manualTaggingOrderDTO, CustomUserDetails curUser);

    public List<DatasetPlanningUnit> getDatasetPlanningUnitList(int programId, int versionId);

    public List<ProgramIdAndVersionId> getLatestVersionForPrograms(String programIds);

    public List<SimpleCodeObject> getSimpleProgramListByRealmCountryIdList(String[] realmCountryIds, CustomUserDetails curUser);

    public List<SimpleCodeObject> getSimpleProgramListByRealmCountryIdsAndHealthAreaIds(RealmCountryIdsAndHealthAreaIds realmCountryIdsAndHealthAreaIds, CustomUserDetails curUser);

    public List<SimpleCodeObject> getSimpleProgramListByProductCategoryIdList(String[] productCategoryIds, CustomUserDetails curUser);

    public List<TreeAnchorOutput> getTreeAnchorForSync(TreeAnchorInput ta, CustomUserDetails curUser);

    public List<SimpleObjectWithFu> getSimplePlanningUnitAndForecastingUnits(StockStatusVerticalDropdownInput ssvdi, CustomUserDetails curUser);

    public List<Integer> getProcurementAgentIdsForProgramId(int programId, CustomUserDetails curUser);

    public List<Integer> getFundingSourceIdsForProgramId(int programId, CustomUserDetails curUser);

    public ProgramPlanningUnit getPlanningUnitForProgramIdAndPlanningUnitId(int programId, int planningUnitId, CustomUserDetails curUser) throws AccessControlFailedException;
}
