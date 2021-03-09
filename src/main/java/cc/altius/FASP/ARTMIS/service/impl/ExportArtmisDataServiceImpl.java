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
import cc.altius.FASP.model.DTO.ExportShipmentLinkingDTO;
import java.util.Date;
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
    public List<ExportProgramDataDTO> exportProgramData(Date lastDate) {
        return this.exportArtmisDataDao.exportProgramData(lastDate);
    }

    @Override
    public List<ExportOrderDataDTO> exportOrderData(Date lastDate) {
        return this.exportArtmisDataDao.exportOrderData(lastDate);
    }

    @Override
    public Date getLastDate(String erpCode, String jobName) {
        return this.exportArtmisDataDao.getLastDate(erpCode, jobName);
    }

    @Override
    public boolean updateLastDate(String erpCode, String jobName, Date lastDate) {
        return this.exportArtmisDataDao.updateLastDate(erpCode, jobName, lastDate);
    }

    @Override
    public List<ExportShipmentLinkingDTO> exportShipmentLinkingData(Date lastDate) {
        return this.exportArtmisDataDao.exportShipmentLinkingData(lastDate);
    }

}
