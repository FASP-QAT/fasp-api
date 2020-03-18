/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.PrgProductCategoryDTO;
import cc.altius.FASP.model.ProductCategory;
import java.util.List;

/**
 *
 * @author altius
 */
public interface ProductCategoryService {

    public List<PrgProductCategoryDTO> getProductCategoryListForSync(String lastSyncDate);

//    public int addProductCategory(List<ProductCategory> productCategoryList, CustomUserDetails curUser);
    public int saveProductCategoryList(List<ProductCategory> productCategoryList, CustomUserDetails curUser);

    public List<ProductCategory> getProductCategoryList(CustomUserDetails curUser);

    public List<ProductCategory> getProductCategoryList(CustomUserDetails curUser, int productCategoryId, boolean includeCurrentLevel, boolean includeAllChildren);
    
    public List<ProductCategory> getProductCategoryList(CustomUserDetails curUser, int realmId, int productCategoryId, boolean includeCurrentLevel, boolean includeAllChildren);

    public ProductCategory getProductCategoryById(int productCategoryId, CustomUserDetails curUser);

}
