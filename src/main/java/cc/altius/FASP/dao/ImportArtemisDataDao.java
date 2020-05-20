/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao;

/**
 *
 * @author altius
 */
public interface ImportArtemisDataDao {

    public int importOrderAndShipmentData(String orderDataFilePath, String shipmentDataFilePath);
}
