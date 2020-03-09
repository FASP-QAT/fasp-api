/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.PrgProductCategoryDTO;
import cc.altius.FASP.model.ProductCategory;
import java.util.List;

/**
 *
 * @author altius
 */
public interface ProductCategoryDao {
    
    public List<PrgProductCategoryDTO> getProductCategoryListForSync(String lastSyncDate);
    
    public int addProductCategory(ProductCategory productCategory, CustomUserDetails curUser);

    public int updateProductCategory(ProductCategory productCategory, CustomUserDetails curUser);

    public List<ProductCategory> getProductCategoryList(CustomUserDetails curUser);
    
    public List<ProductCategory> getProductCategoryList(CustomUserDetails curUser, int productCategoryId, boolean includeCurrentLevel, boolean includeAllChildren);

    public ProductCategory getProductCategoryById(int productCategoryId, CustomUserDetails curUser);
    
}
