/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ForecastingUnit;
import java.util.List;

/**
 *
 * @author akil
 */
public interface ProductInterface {

    public List<ForecastingUnit> getProductList(boolean active, CustomUserDetails curUser);

    public int addProduct(ForecastingUnit product, CustomUserDetails curUser);

    public int updateProduct(ForecastingUnit product, CustomUserDetails curUser);

    public ForecastingUnit getProductById(int productId, CustomUserDetails curUser);
}
