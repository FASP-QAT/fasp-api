/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ShipmentStatus;
import java.util.List;

/**
 *
 * @author palash
 */
public interface ShipmentStatusService {

    public int addShipmentStatus(ShipmentStatus shipmentStatus);

    public List<ShipmentStatus> getShipmentStatusList(boolean active);

    public int editShipmentStatus(ShipmentStatus shipmentStatus);
    
    public List<ShipmentStatus> getShipmentStatusListForSync(String lastSyncDate, CustomUserDetails curUser);
//    
//    public List<PrgShipmentStatusAllowedDTO> getShipmentStatusAllowedListForSync(String lastSyncDate);
}
