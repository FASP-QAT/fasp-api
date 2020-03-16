/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.ManufacturerDao;
import cc.altius.FASP.model.DTO.PrgManufacturerDTO;
import cc.altius.FASP.model.Manufacturer;
import cc.altius.FASP.service.ManufacturerService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author altius
 */
@Service
public class ManufacturerServiceImpl implements ManufacturerService {

    @Autowired
    ManufacturerDao manufacturerDao;

    @Override
    public List<PrgManufacturerDTO> getManufacturerListForSync(String lastSyncDate,int realmId) {
        return this.manufacturerDao.getManufacturerListForSync(lastSyncDate,realmId);
    }

    @Override
    public int addManufacturer(Manufacturer m, int curUser) {
       return this.manufacturerDao.addManufacturer(m,curUser);
    }

    @Override
    public int updateManufacturer(Manufacturer m, int CurUser) {
        return this.manufacturerDao.updateManufacturer(m,CurUser);
    }

    @Override
    public List<Manufacturer> getManufacturerList() {
       return this.manufacturerDao.getManufacturerList();
    }

    @Override
    public Manufacturer getManufacturerById(int manufacturerId) {
      return this.manufacturerDao.getManufacturerById(manufacturerId);
    }

}
