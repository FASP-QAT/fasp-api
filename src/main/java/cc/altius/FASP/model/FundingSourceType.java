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
public class FundingSourceType extends BaseModel implements Serializable {

    private int fundingSourceTypeId;
    private SimpleCodeObject realm;
    private Label label;
    private String fundingSourceTypeCode;

    public FundingSourceType() {
    }

    public FundingSourceType(int fundingSourceTypeId, Label label, String fundingSourceTypeCode) {
        this.fundingSourceTypeId = fundingSourceTypeId;
        this.label = label;
        this.fundingSourceTypeCode = fundingSourceTypeCode;
    }

    public FundingSourceType(int fundingSourceTypeId, SimpleCodeObject realm, Label label, String fundingSourceTypeCode) {
        this.fundingSourceTypeId = fundingSourceTypeId;
        this.realm = realm;
        this.label = label;
        this.fundingSourceTypeCode = fundingSourceTypeCode;
    }

    public int getFundingSourceTypeId() {
        return fundingSourceTypeId;
    }

    public void setFundingSourceTypeId(int fundingSourceTypeId) {
        this.fundingSourceTypeId = fundingSourceTypeId;
    }

    public SimpleCodeObject getRealm() {
        return realm;
    }

    public void setRealm(SimpleCodeObject realm) {
        this.realm = realm;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public String getFundingSourceTypeCode() {
        return fundingSourceTypeCode;
    }

    public void setFundingSourceTypeCode(String fundingSourceTypeCode) {
        this.fundingSourceTypeCode = fundingSourceTypeCode;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + this.fundingSourceTypeId;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FundingSourceType other = (FundingSourceType) obj;
        if (this.fundingSourceTypeId != other.fundingSourceTypeId) {
            return false;
        }
        return true;
    }

}
