/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import cc.altius.FASP.utils.ArrayUtils;

/**
 *
 * @author akil
 */
public class NotLinkedErpShipmentsInputTab3 {

    private int realmCountryId;
    private String[] productCategorySortOrders;
    private String[] planningUnitIds;

    public int getRealmCountryId() {
        return realmCountryId;
    }

    public void setRealmCountryId(int realmCountryId) {
        this.realmCountryId = realmCountryId;
    }

    public String[] getProductCategorySortOrders() {
        return productCategorySortOrders;
    }

    public void setProductCategorySortOrder(String[] productCategorySortOrders) {
        this.productCategorySortOrders = productCategorySortOrders;
    }

    public String[] getPlanningUnitIds() {
        return planningUnitIds;
    }

    public void setPlanningUnitIds(String[] planningUnitIds) {
        this.planningUnitIds = planningUnitIds;
    }

    @Override
    public String toString() {
        return "NotLinkedErpShipmentsInputTab3{" + "realmCountryId=" + realmCountryId + ", productCategorySortOrders=" + ArrayUtils.convertArrayToString(productCategorySortOrders) + ", planningUnitIds=" + ArrayUtils.convertArrayToString(planningUnitIds) + '}';
    }

}
