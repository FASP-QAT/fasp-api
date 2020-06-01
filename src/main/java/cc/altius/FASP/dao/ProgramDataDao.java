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
import cc.altius.FASP.model.Shipment;
import cc.altius.FASP.model.Version;
import java.util.List;

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

}
