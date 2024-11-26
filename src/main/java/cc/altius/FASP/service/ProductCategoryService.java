/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service;

import cc.altius.FASP.exception.AccessControlFailedException;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ExtendedProductCategory;
import cc.altius.FASP.model.ProductCategory;
import cc.altius.utils.TreeUtils.Node;
import java.util.List;

/**
 *
 * @author altius
 */
public interface ProductCategoryService {

    public int saveProductCategoryList(Node<ProductCategory>[] productCategorys, CustomUserDetails curUser) throws Exception;

    public List<Node<ExtendedProductCategory>> getProductCategoryListForRealm(CustomUserDetails curUser, int realmId);

    public List<Node<ExtendedProductCategory>> getProductCategoryList(CustomUserDetails curUser, int realmId, int productCategoryId, boolean includeCurrentLevel, boolean includeAllChildren);

    public List<Node<ExtendedProductCategory>> getProductCategoryListForSync(String lastSyncDate, CustomUserDetails curUser);

    public List<Node<ExtendedProductCategory>> getProductCategoryListForProgram(CustomUserDetails curUser, int realmId, int programId) throws AccessControlFailedException;

}
