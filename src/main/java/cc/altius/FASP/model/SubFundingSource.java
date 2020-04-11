/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author altius
 */
public class SubFundingSource extends BaseModel implements Serializable {

    private int subFundingSourceId;
    private FundingSource fundingSource;
    private Label label;

    public SubFundingSource() {
    }

    public SubFundingSource(int subFundingSourceId, Label label, FundingSource fundingSource) {
        this.subFundingSourceId = subFundingSourceId;
        this.label = label;
        this.fundingSource = fundingSource;
    }

    public int getSubFundingSourceId() {
        return subFundingSourceId;
    }

    public void setSubFundingSourceId(int subFundingSourceId) {
        this.subFundingSourceId = subFundingSourceId;
    }

    public FundingSource getFundingSource() {
        return fundingSource;
    }

    public void setFundingSource(FundingSource fundingSource) {
        this.fundingSource = fundingSource;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.subFundingSourceId);
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
        final SubFundingSource other = (SubFundingSource) obj;
        if (!Objects.equals(this.subFundingSourceId, other.subFundingSourceId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SubFundingSource{" + "subFundingSourceId=" + subFundingSourceId + ", fundingSource=" + fundingSource + ", label=" + label + '}';
    }

}
