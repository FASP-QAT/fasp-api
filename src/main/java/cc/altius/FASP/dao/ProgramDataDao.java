/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao;

import cc.altius.FASP.exception.CouldNotSaveException;
import cc.altius.FASP.model.Consumption;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.Inventory;
import cc.altius.FASP.model.ProgramData;
import cc.altius.FASP.model.ProgramVersion;
import cc.altius.FASP.model.Shipment;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.Version;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author altius
 */
public interface ProgramDataDao {

    public Version getVersionInfo(int programId, int versionId);

    public List<Consumption> getConsumptionList(int programId, int versionId);

    public List<Inventory> getInventoryList(int programId, int versionId);

    public List<Shipment> getShipmentList(int programId, int versionId);

    public Version saveProgramData(ProgramData programData, CustomUserDetails curUser) throws CouldNotSaveException;

    public List<SimpleObject> getVersionTypeList();

    public List<SimpleObject> getVersionStatusList();

    public List<ProgramVersion> getProgramVersionList(int programId, int versionId, int realmCountryId, int healthAreaId, int organisationId, int versionTypeId, int versionStatusId, String startDate, String stopDate, CustomUserDetails curUser);

    public Version updateProgramVersion(int programId, int versionId, int versionStatusId, CustomUserDetails curUser);

    public int checkErpOrder(String orderNo, String primeLineNo, int realmCountryId, int planningUnitId);

}
