/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author akil
 */
public class ArtmisHistory implements Serializable {

    private List<ArtmisHistoryErpOrder> erpOrderList;
    private List<ArtmisHistoryErpShipment> erpShipmentList;

    public List<ArtmisHistoryErpOrder> getErpOrderList() {
        return erpOrderList;
    }

    public void setErpOrderList(List<ArtmisHistoryErpOrder> erpOrderList) {
        this.erpOrderList = erpOrderList;
    }

    public List<ArtmisHistoryErpShipment> getErpShipmentList() {
        return erpShipmentList;
    }

    public void setErpShipmentList(List<ArtmisHistoryErpShipment> erpShipmentList) {
        this.erpShipmentList = erpShipmentList;
    }

}
