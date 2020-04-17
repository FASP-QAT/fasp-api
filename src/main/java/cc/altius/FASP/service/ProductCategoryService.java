/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ExtendedProductCategory;
import cc.altius.FASP.model.ProductCategory;
import cc.altius.utils.TreeUtils.Node;
import cc.altius.utils.TreeUtils.Tree;

/**
 *
 * @author altius
 */
public interface ProductCategoryService {

    public int saveProductCategoryList(Node<ProductCategory>[] productCategorys, CustomUserDetails curUser) throws Exception;

    public Tree<ExtendedProductCategory> getProductCategoryListForRealm(CustomUserDetails curUser, int realmId);

    public Tree<ExtendedProductCategory> getProductCategoryList(CustomUserDetails curUser, int realmId, int productCategoryId, boolean includeCurrentLevel, boolean includeAllChildren);

//    public ProductCategory getProductCategoryById(int productCategoryId, CustomUserDetails curUser);

    public Tree<ExtendedProductCategory> getProductCategoryListForSync(String lastSyncDate, CustomUserDetails curUser);

}
