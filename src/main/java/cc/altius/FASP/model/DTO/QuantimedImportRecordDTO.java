/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO;

import java.io.Serializable;

/**
 *
 * @author altius
 */
public class QuantimedImportRecordDTO implements Serializable{
    
    private String productId;
    private String dtmPeriod;
    private String ingConsumption;
    private String ingAdjustments;
    private QuantimedImportProductDTO product;

    public QuantimedImportProductDTO getProduct() {
        return product;
    }

    public void setProduct(QuantimedImportProductDTO product) {
        this.product = product;
    }    
    
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getDtmPeriod() {
        return dtmPeriod;
    }

    public void setDtmPeriod(String dtmPeriod) {
        this.dtmPeriod = dtmPeriod;
    }

    public String getIngConsumption() {
        return ingConsumption;
    }

    public void setIngConsumption(String ingConsumption) {
        this.ingConsumption = ingConsumption;
    }

    public String getIngAdjustments() {
        return ingAdjustments;
    }

    public void setIngAdjustments(String ingAdjustments) {
        this.ingAdjustments = ingAdjustments;
    }

    @Override
    public String toString() {
        return "QuantimedImportRecordDTO{" + "productId=" + productId + ", dtmPeriod=" + dtmPeriod + ", ingConsumption=" + ingConsumption + ", ingAdjustments=" + ingAdjustments + '}';
    }        
}
