/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.Product;
import java.util.List;

/**
 *
 * @author akil
 */
public interface ProductInterface {

    public List<Product> getProductList(boolean active, CustomUserDetails curUser);

    public int addProduct(Product product, CustomUserDetails curUser);

    public int updateProduct(Product product, CustomUserDetails curUser);

    public Product getProductById(int productId, CustomUserDetails curUser);
}
