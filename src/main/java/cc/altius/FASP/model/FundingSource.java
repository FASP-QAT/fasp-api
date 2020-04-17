/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import java.io.Serializable;

/**
 *
 * @author palash
 */
public class FundingSource extends BaseModel implements Serializable {

    private int fundingSourceId;
    private Label label;
    private SimpleCodeObject realm;

    public FundingSource() {
    }

    public FundingSource(int fundingSourceId, Label label, SimpleCodeObject realm) {
        this.fundingSourceId = fundingSourceId;
        this.label = label;
        this.realm = realm;
    }

    public int getFundingSourceId() {
        return fundingSourceId;
    }

    public void setFundingSourceId(int fundingSourceId) {
        this.fundingSourceId = fundingSourceId;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public SimpleCodeObject getRealm() {
        return realm;
    }

    public void setRealm(SimpleCodeObject realm) {
        this.realm = realm;
    }

    @Override
    public String toString() {
        return "FundingSource{" + "fundingSourceId=" + fundingSourceId + ", label=" + label + ", realm=" + realm + '}';
    }

}
