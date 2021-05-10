/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.ARTMIS.service;

import cc.altius.FASP.model.DTO.ExportOrderDataDTO;
import cc.altius.FASP.model.DTO.ExportProgramDataDTO;
import cc.altius.FASP.model.DTO.ExportShipmentLinkingDTO;
import java.util.Date;
import java.util.List;

/**
 *
 * @author altius
 */
public interface ExportArtmisDataService {

    public List<ExportProgramDataDTO> exportProgramData(Date lastDate);

    public List<ExportOrderDataDTO> exportOrderData(Date lastDate);

    public Date getLastDate(String erpCode, String jobName);

    public boolean updateLastDate(String erpCode, String jobName, Date lastDate);

    public List<ExportShipmentLinkingDTO> exportShipmentLinkingData(Date lastDate);
}
