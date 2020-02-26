/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.ProductCategoryDao;
import cc.altius.FASP.model.DTO.PrgProductCategoryDTO;
import cc.altius.FASP.service.ProductCategoryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author altius
 */
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    ProductCategoryDao productCategoryDao;

    @Override
    public List<PrgProductCategoryDTO> getProductCategoryListForSync(String lastSyncDate) {
        return this.productCategoryDao.getProductCategoryListForSync(lastSyncDate);
    }

}
