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
public class QuantimedImportProductDTO implements Serializable {
    
    private String productName;
    private String productId;
    private String source;
    private String userDefined;
    private String productGroup;
    private String innovatorName;
    private String lowestUnitQuantity;
    private String lowestUnitMeasure;
    private String quantificationFactor;
    private String programPlanningUnitId;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUserDefined() {
        return userDefined;
    }

    public void setUserDefined(String userDefined) {
        this.userDefined = userDefined;
    }

    public String getProductGroup() {
        return productGroup;
    }

    public void setProductGroup(String productGroup) {
        this.productGroup = productGroup;
    }

    public String getInnovatorName() {
        return innovatorName;
    }

    public void setInnovatorName(String innovatorName) {
        this.innovatorName = innovatorName;
    }

    public String getLowestUnitQuantity() {
        return lowestUnitQuantity;
    }

    public void setLowestUnitQuantity(String lowestUnitQuantity) {
        this.lowestUnitQuantity = lowestUnitQuantity;
    }

    public String getLowestUnitMeasure() {
        return lowestUnitMeasure;
    }

    public void setLowestUnitMeasure(String lowestUnitMeasure) {
        this.lowestUnitMeasure = lowestUnitMeasure;
    }

    public String getQuantificationFactor() {
        return quantificationFactor;
    }

    public void setQuantificationFactor(String quantificationFactor) {
        this.quantificationFactor = quantificationFactor;
    }

    public String getProgramPlanningUnitId() {
        return programPlanningUnitId;
    }

    public void setProgramPlanningUnitId(String programPlanningUnitId) {
        this.programPlanningUnitId = programPlanningUnitId;
    }

    @Override
    public String toString() {
        return "QuantimedImportProductDTO{" + "productName=" + productName + ", productId=" + productId + ", source=" + source + ", userDefined=" + userDefined + ", productGroup=" + productGroup + ", innovatorName=" + innovatorName + ", lowestUnitQuantity=" + lowestUnitQuantity + ", lowestUnitMeasure=" + lowestUnitMeasure + ", quantificationFactor=" + quantificationFactor + ", programPlanningUnitId=" + programPlanningUnitId + '}';
    }

    
}
