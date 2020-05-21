/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.ImportProductCatalogueDao;
import cc.altius.FASP.service.ImportProductCatalogueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author altius
 */
@Service
public class ImportProductCatalogueServiceImpl implements ImportProductCatalogueService {

    @Autowired
    private ImportProductCatalogueDao importProductCatalogueDao;

    @Override
    @Transactional
    public void importProductCatalogue(String filePath) {
        this.importProductCatalogueDao.importProductCatalogue(filePath);
        this.importProductCatalogueDao.pullUnitTable();
        this.importProductCatalogueDao.pullTracerCategoryFromTmpTables();
        this.importProductCatalogueDao.pullForecastingUnitFromTmpTables();
        this.importProductCatalogueDao.pullPlanningUnitFromTmpTables();
        this.importProductCatalogueDao.pullSupplierFromTmpTables();
        this.importProductCatalogueDao.pullProcurementUnitFromTmpTables();
    }

}
