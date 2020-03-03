/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.ProductDao;
import cc.altius.FASP.model.DTO.PrgProductDTO;
import cc.altius.FASP.service.ProductService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author altius
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductDao productDao;

    @Override
    public List<PrgProductDTO> getProductListForSync(String lastSyncDate) {
        return this.productDao.getProductListForSync(lastSyncDate);
    }

}
