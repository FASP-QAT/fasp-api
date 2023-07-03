/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model;

import java.io.Serializable;

/**
 *
 * @author akil
 */
public class ForecastingUnitWithSku extends ForecastingUnit implements Serializable {

    private String skuCode;

    public ForecastingUnitWithSku() {
    }

    public ForecastingUnitWithSku(int productId, SimpleCodeObject realm, Label genericLabel, Label label, SimpleCodeObject unit, SimpleObject productCategory, SimpleObject tracerCategory, String skuCode) {
        super(productId, realm, genericLabel, label, unit, productCategory, tracerCategory);
        this.skuCode = skuCode;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
