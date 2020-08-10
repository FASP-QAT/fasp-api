/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.ARTMIS.service.impl;

import cc.altius.FASP.ARTMIS.dao.ExportArtmisDataDao;
import cc.altius.FASP.ARTMIS.service.ExportArtmisDataService;
import cc.altius.FASP.model.DTO.ExportOrderDataDTO;
import cc.altius.FASP.model.DTO.ExportProgramDataDTO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author altius
 */
@Service
public class ExportArtmisDataServiceImpl implements ExportArtmisDataService {

    @Autowired
    private ExportArtmisDataDao exportArtmisDataDao;

    @Override
    public List<ExportProgramDataDTO> exportProgramData() {
        return this.exportArtmisDataDao.exportProgramData();
    }

    @Override
    public List<ExportOrderDataDTO> exportOrderData() {
        return this.exportArtmisDataDao.exportOrderData();
    }

}
