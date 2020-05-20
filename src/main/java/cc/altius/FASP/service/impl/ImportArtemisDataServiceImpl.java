/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.ImportArtemisDataDao;
import cc.altius.FASP.service.ImportArtemisDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author altius
 */
@Service
public class ImportArtemisDataServiceImpl implements ImportArtemisDataService {

    @Autowired
    private ImportArtemisDataDao importArtemisDataDao;

    @Override
    public int importOrderAndShipmentData(String orderDataFilePath, String shipmentDataFilePath) {
        return this.importArtemisDataDao.importOrderAndShipmentData(orderDataFilePath, shipmentDataFilePath);
    }

}
