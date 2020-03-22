/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.ProductDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.PrgProductDTO;
import cc.altius.FASP.model.Product;
import cc.altius.FASP.service.AclService;
import cc.altius.FASP.service.ProductService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

/**
 *
 * @author altius
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductDao productDao;
    @Autowired
    private AclService aclService;

    @Override
    public List<PrgProductDTO> getProductListForSync(String lastSyncDate, int realmId) {
        return this.productDao.getProductListForSync(lastSyncDate, realmId);
    }

    @Override
    public List<Product> getProductList(boolean active, CustomUserDetails curUser) {
        return this.productDao.getProductList(active, curUser);
    }

    @Override
    public List<Product> getProductList(int realmId, boolean active, CustomUserDetails curUser) {
        if(this.aclService.checkRealmAccessForUser(curUser, realmId)) {
            return this.productDao.getProductList(realmId, active, curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }
        
    }

    @Override
    public int addProduct(Product product, CustomUserDetails curUser) {
        if (this.aclService.checkRealmAccessForUser(curUser, product.getRealm().getRealmId())) {
            return this.productDao.addProduct(product, curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public int updateProduct(Product product, CustomUserDetails curUser) {
        Product pr = this.getProductById(product.getProductId(), curUser);
        if (this.aclService.checkRealmAccessForUser(curUser, pr.getRealm().getRealmId())) {
            return this.productDao.updateProduct(product, curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public Product getProductById(int productId, CustomUserDetails curUser) {
        Product pr = this.productDao.getProductById(productId, curUser);
        if (this.aclService.checkAccessForUser(curUser, pr.getRealm().getRealmId(), 0, 0, 0, pr.getProductId())) {
            return pr;
        } else {
            throw new AccessDeniedException("Access denied");
        }

    }
}
