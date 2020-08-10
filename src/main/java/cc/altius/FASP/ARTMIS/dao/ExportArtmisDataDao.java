/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.ARTMIS.dao;

import cc.altius.FASP.model.DTO.ExportOrderDataDTO;
import cc.altius.FASP.model.DTO.ExportProgramDataDTO;
import java.util.List;

/**
 *
 * @author altius
 */
public interface ExportArtmisDataDao {

    public List<ExportProgramDataDTO> exportProgramData();

    public List<ExportOrderDataDTO> exportOrderData();
}
