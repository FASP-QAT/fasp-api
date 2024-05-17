/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao;

import cc.altius.FASP.exception.DuplicateNameException;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ProcurementUnit;
import java.util.List;

/**
 *
 * @author akil
 */
public interface ProcurementUnitDao {

    public List<ProcurementUnit> getProcurementUnitList(boolean active, CustomUserDetails curUser);

    public List<ProcurementUnit> getProcurementUnitListForRealm(int realmId, boolean active, CustomUserDetails curUser);

    public List<ProcurementUnit> getProcurementUnitListByPlanningUnit(int planningUnitId, boolean active, CustomUserDetails curUser);

    public int addProcurementUnit(ProcurementUnit procurementUnit, CustomUserDetails curUser) throws DuplicateNameException;

    public int updateProcurementUnit(ProcurementUnit procurementUnit, CustomUserDetails curUser) throws DuplicateNameException;

    public ProcurementUnit getProcurementUnitById(int procurementUnitId, CustomUserDetails curUser);

    public List<ProcurementUnit> getProcurementUnitListForSync(String lastSyncDate, CustomUserDetails curUser);
    
    public List<ProcurementUnit> getProcurementUnitListForSyncProgram(String programIdsString, CustomUserDetails curUser);
}
