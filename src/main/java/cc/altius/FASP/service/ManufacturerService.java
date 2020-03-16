/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service;

import cc.altius.FASP.model.DTO.PrgManufacturerDTO;
import cc.altius.FASP.model.Manufacturer;
import java.util.List;

/**
 *
 * @author altius
 */
public interface ManufacturerService {

    public List<PrgManufacturerDTO> getManufacturerListForSync(String lastSyncDate,int realmId);
    
    public int addManufacturer(Manufacturer m, int curUser);

    public int updateManufacturer(Manufacturer m, int CurUser);

    public List<Manufacturer> getManufacturerList();

    public Manufacturer getManufacturerById(int unitId);

}
