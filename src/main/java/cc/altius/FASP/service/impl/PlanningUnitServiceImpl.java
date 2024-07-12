/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.ForecastingUnitDao;
import cc.altius.FASP.dao.PlanningUnitDao;
import cc.altius.FASP.dao.ProductCategoryDao;
import cc.altius.FASP.dao.RealmDao;
import cc.altius.FASP.exception.DuplicateNameException;
import cc.altius.FASP.model.AutoCompleteInput;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.AutocompleteInputWithProductCategoryDTO;
import cc.altius.FASP.model.DTO.MultipleProgramAndTracerCategoryDTO;
import cc.altius.FASP.model.DTO.ProductCategoryTracerCategoryAndForecastingUnitDTO;
import cc.altius.FASP.model.DTO.ProgramAndVersionDTO;
import cc.altius.FASP.model.ForecastingUnit;
import cc.altius.FASP.model.PlanningUnit;
import cc.altius.FASP.model.PlanningUnitCapacity;
import cc.altius.FASP.model.PlanningUnitWithPrices;
import cc.altius.FASP.model.ProductCategory;
import cc.altius.FASP.model.Realm;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.SimplePlanningUnitForAdjustPlanningUnit;
import cc.altius.FASP.model.SimplePlanningUnitWithPrices;
import cc.altius.FASP.service.AclService;
import cc.altius.FASP.service.PlanningUnitService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

/**
 *
 * @author altius
 */
@Service
public class PlanningUnitServiceImpl implements PlanningUnitService {

    @Autowired
    private PlanningUnitDao planningUnitDao;
    @Autowired
    private ForecastingUnitDao forecastingUnitDao;
    @Autowired
    private RealmDao realmDao;
    @Autowired
    private AclService aclService;
    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Override
    public List<PlanningUnit> getPlanningUnitList(boolean active, CustomUserDetails curUser) {
        return this.planningUnitDao.getPlanningUnitList(active, curUser);
    }

    @Override
    public List<PlanningUnit> getPlanningUnitList(int realmId, boolean active, CustomUserDetails curUser) {
        Realm r = this.realmDao.getRealmById(realmId, curUser);
        if (this.aclService.checkRealmAccessForUser(curUser, realmId)) {
            return this.planningUnitDao.getPlanningUnitList(realmId, active, curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public List<PlanningUnit> getPlanningUnitListByIds(List<String> planningUnitIdList, CustomUserDetails curUser) {
        return this.planningUnitDao.getPlanningUnitListByIds(planningUnitIdList, curUser);
    }

    @Override
    public List<PlanningUnitWithPrices> getPlanningUnitListWithPricesByIds(List<String> planningUnitIdList, CustomUserDetails curUser) {
        return this.planningUnitDao.getPlanningUnitListWithPricesByIds(planningUnitIdList, curUser);
    }

    @Override
    public List<SimplePlanningUnitForAdjustPlanningUnit> getPlanningUnitListBasic(CustomUserDetails curUser) {
        return this.planningUnitDao.getPlanningUnitListBasic(curUser);
    }

    @Override
    public List<PlanningUnit> getPlanningUnitListByForecastingUnit(int forecastingUnitId, boolean active, CustomUserDetails curUser) {
        ForecastingUnit fu = this.forecastingUnitDao.getForecastingUnitById(forecastingUnitId, curUser);
        if (fu == null) {
            throw new EmptyResultDataAccessException(1);
        }
        return this.planningUnitDao.getPlanningUnitListByForecastingUnit(fu.getForecastingUnitId(), active, curUser);
    }

    @Override
    public int addPlanningUnit(PlanningUnit planningUnit, CustomUserDetails curUser) throws DuplicateNameException {
        ForecastingUnit fu = this.forecastingUnitDao.getForecastingUnitById(planningUnit.getForecastingUnit().getForecastingUnitId(), curUser);
        if (this.aclService.checkRealmAccessForUser(curUser, fu.getRealm().getId())) {
            return this.planningUnitDao.addPlanningUnit(planningUnit, curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public int updatePlanningUnit(PlanningUnit planningUnit, CustomUserDetails curUser) throws DuplicateNameException {
        PlanningUnit pr = this.getPlanningUnitById(planningUnit.getPlanningUnitId(), curUser);
        if (this.aclService.checkRealmAccessForUser(curUser, pr.getForecastingUnit().getRealm().getId())) {
            return this.planningUnitDao.updatePlanningUnit(planningUnit, curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public PlanningUnit getPlanningUnitById(int planningUnitId, CustomUserDetails curUser) {
        return this.planningUnitDao.getPlanningUnitById(planningUnitId, curUser);
    }

    @Override
    public List<PlanningUnitCapacity> getPlanningUnitCapacityForRealm(int realmId, String startDate, String stopDate, CustomUserDetails curUser) throws ParseException {
        Realm r = this.realmDao.getRealmById(realmId, curUser);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dtStartDate = (startDate == null ? null : sdf.parse(startDate));
        Date dtStopDate = (stopDate == null ? null : sdf.parse(stopDate));
        if (this.aclService.checkRealmAccessForUser(curUser, realmId)) {
            if ((dtStartDate != null && dtStopDate == null) || (dtStopDate != null && dtStartDate == null)) {
                throw new ParseException("One date cannot be null", 1);
            }
            return this.planningUnitDao.getPlanningUnitCapacityForRealm(realmId, startDate, stopDate, curUser);

        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public List<PlanningUnitCapacity> getPlanningUnitCapacityForId(int planningUnitId, String startDate, String stopDate, CustomUserDetails curUser) throws ParseException {
        PlanningUnit pu = this.planningUnitDao.getPlanningUnitById(planningUnitId, curUser);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dtStartDate = (startDate != null ? sdf.parse(startDate) : null);
        Date dtStopDate = (stopDate != null ? sdf.parse(stopDate) : null);
        if (this.aclService.checkRealmAccessForUser(curUser, pu.getForecastingUnit().getRealm().getId())) {
            if ((dtStartDate != null && dtStopDate == null) || (dtStopDate != null && dtStartDate == null)) {
                throw new ParseException("One date cannot be null", 1);
            }
            return this.planningUnitDao.getPlanningUnitCapacityForId(planningUnitId, startDate, stopDate, curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public List<PlanningUnitCapacity> getPlanningUnitCapacityList(CustomUserDetails curUser) {
        if (this.aclService.checkRealmAccessForUser(curUser, curUser.getRealm().getRealmId())) {
            return this.planningUnitDao.getPlanningUnitCapacityList(curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public int savePlanningUnitCapacity(PlanningUnitCapacity[] planningUnitCapacitys, CustomUserDetails curUser) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (PlanningUnitCapacity puc : planningUnitCapacitys) {
            try {
                PlanningUnit pu = this.planningUnitDao.getPlanningUnitById(puc.getPlanningUnit().getId(), curUser);
                if (!this.aclService.checkRealmAccessForUser(curUser, pu.getForecastingUnit().getRealm().getId())) {
                    throw new AccessDeniedException("Access denied");
                }
            } catch (Exception e) {
                throw new EmptyResultDataAccessException(1);
            }
            sdf.parse(puc.getStartDate());
            sdf.parse(puc.getStopDate());
        }
        return this.planningUnitDao.savePlanningUnitCapacity(planningUnitCapacitys, curUser);
    }

    @Override
    public List<PlanningUnit> getPlanningUnitListForSync(String lastSyncDate, CustomUserDetails curUser) {
        return this.planningUnitDao.getPlanningUnitListForSync(lastSyncDate, curUser);
    }

    @Override
    public List<PlanningUnit> getPlanningUnitListForSyncProgram(String programIdsString, CustomUserDetails curUser) {
        if (programIdsString.length() > 0) {
            return this.planningUnitDao.getPlanningUnitListForSyncProgram(programIdsString, curUser);
        } else {
            return new LinkedList<>();
        }
    }

    @Override
    public List<PlanningUnit> getPlanningUnitListForProductCategory(int productCategoryId, boolean active, CustomUserDetails curUser) {
        if (productCategoryId != 0) {
            ProductCategory pc = this.productCategoryDao.getProductCategoryById(productCategoryId, curUser);
            return this.planningUnitDao.getPlanningUnitListForProductCategory(pc.getSortOrder(), active, curUser);
        } else {
            return this.planningUnitDao.getPlanningUnitListForProductCategory("00", active, curUser);
        }
    }

    @Override
    public List<SimpleObject> getPlanningUnitListForProductCategoryList(String[] productCategoryIds,int realmCountryId, boolean active, CustomUserDetails curUser) {
        if (productCategoryIds != null && productCategoryIds.length != 0) {
            return this.planningUnitDao.getPlanningUnitListForProductCategoryList(productCategoryIds,realmCountryId, active, curUser);
        } else {
            return null;
        }
    }

    @Override
    public List<SimpleObject> getPlanningUnitListByRealmCountryId(int realmCountryId, CustomUserDetails curUser) {
        return this.planningUnitDao.getPlanningUnitListByRealmCountryId(realmCountryId, curUser);
    }

    @Override
    public List<SimpleObject> getPlanningUnitByProgramAndTracerCategory(MultipleProgramAndTracerCategoryDTO programAndTracerCategory, CustomUserDetails curUser) {
        return this.planningUnitDao.getPlanningUnitByProgramAndTracerCategory(programAndTracerCategory, curUser);
    }

    @Override
    public List<SimpleObject> getPlanningUnitListByTracerCategory(int tracerCategoryId, boolean active, CustomUserDetails curUser) {
        return this.planningUnitDao.getPlanningUnitListByTracerCategory(tracerCategoryId, active, curUser);
    }

    @Override
    public List<PlanningUnit> getPlanningUnitListByTracerCategoryIds(String[] tracerCategoryIds, boolean active, CustomUserDetails curUser) {
        return this.planningUnitDao.getPlanningUnitListByTracerCategoryIds(tracerCategoryIds, active, curUser);
    }

    @Override
    public List<SimplePlanningUnitWithPrices> getPlanningUnitListWithPricesForProductCategory(int productCategoryId, CustomUserDetails curUser) {
        return this.planningUnitDao.getPlanningUnitListWithPricesForProductCategory(productCategoryId, curUser);
    }

    @Override
    public List<SimpleObject> getPlanningUnitListForAutoComplete(AutoCompleteInput autoCompleteInput, CustomUserDetails curUser) {
        return this.planningUnitDao.getPlanningUnitListForAutoComplete(autoCompleteInput, curUser);
    }

    @Override
    public List<SimpleObject> getPlanningUnitListForAutoCompleteFilterForProductCategory(AutocompleteInputWithProductCategoryDTO autoCompleteInput, CustomUserDetails curUser) {
        return this.planningUnitDao.getPlanningUnitListForAutoCompleteFilterForProductCategory(autoCompleteInput, curUser);
    }

    @Override
    public List<SimpleObject> getPlanningUnitDropDownList(CustomUserDetails curUser) {
        return this.planningUnitDao.getPlanningUnitDropDownList(curUser);
    }

    @Override
    public List<SimpleObject> getPlanningUnitDropDownListFilterProductCategory(String productCategorySortOrder, CustomUserDetails curUser) {
        return this.planningUnitDao.getPlanningUnitDropDownListFilterProductCategory(productCategorySortOrder, curUser);
    }

    @Override
    public List<SimpleObject> getPlanningUnitForDatasetByProgramAndVersion(ProgramAndVersionDTO input, CustomUserDetails curUser) {
        return this.planningUnitDao.getPlanningUnitForDatasetByProgramAndVersion(input, curUser);
    }

    @Override
    public List<PlanningUnit> getPlanningUnitByTracerCategoryProductCategoryAndForecastingUnit(ProductCategoryTracerCategoryAndForecastingUnitDTO input, CustomUserDetails curUser) {
        return this.planningUnitDao.getPlanningUnitByTracerCategoryProductCategoryAndForecastingUnit(input, curUser);
    }

}
