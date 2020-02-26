/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.ManufacturerDao;
import cc.altius.FASP.model.DTO.PrgManufacturerDTO;
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
    public List<PrgManufacturerDTO> getManufacturerListForSync(String lastSyncDate) {
        return this.manufacturerDao.getManufacturerListForSync(lastSyncDate);
    }

}
