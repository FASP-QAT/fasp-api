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
    private String productCategorySortOrder;
    private String[] planningUnitIds;

    public int getRealmCountryId() {
        return realmCountryId;
    }

    public void setRealmCountryId(int realmCountryId) {
        this.realmCountryId = realmCountryId;
    }

    public String getProductCategorySortOrder() {
        return productCategorySortOrder;
    }

    public void setProductCategorySortOrder(String productCategorySortOrder) {
        this.productCategorySortOrder = productCategorySortOrder;
    }

    public String[] getPlanningUnitIds() {
        return planningUnitIds;
    }

    public void setPlanningUnitIds(String[] planningUnitIds) {
        this.planningUnitIds = planningUnitIds;
    }

    @Override
    public String toString() {
        return "NotLinkedErpShipmentsInputTab3{" + "realmCountryId=" + realmCountryId + ", productCategorySortOrder=" + productCategorySortOrder + ", planningUnitIds=" + ArrayUtils.convertArrayToString(planningUnitIds) + '}';
    }

}
