/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.ShipmentStatusDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ShipmentStatus;
import cc.altius.FASP.service.ShipmentStatusService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author palash
 */
@Service
public class ShipmentStatusServiceImpl implements ShipmentStatusService {

    @Autowired
    private ShipmentStatusDao shipmentStatusDao;

    @Override
    public int addShipmentStatus(ShipmentStatus shipmentStatus) {
        return this.shipmentStatusDao.addShipmentStatus(shipmentStatus);
    }

    @Override
    public List<ShipmentStatus> getShipmentStatusList(boolean active) {
        return this.shipmentStatusDao.getShipmentStatusList(active);
    }

    @Override
    public int editShipmentStatus(ShipmentStatus shipmentStatus) {
        return this.shipmentStatusDao.editShipmentStatus(shipmentStatus);
    }

//
//    @Override
//    public List<PrgShipmentStatusAllowedDTO> getShipmentStatusAllowedListForSync(String lastSyncDate) {
//        return this.shipmentStatusDao.getShipmentStatusAllowedListForSync(lastSyncDate);
//    }

    @Override
    public List<ShipmentStatus> getShipmentStatusListForSync(String lastSyncDate, CustomUserDetails curUser) {
        return this.shipmentStatusDao.getShipmentStatusListForSync(lastSyncDate, curUser);
    }

}
