/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO;

/**
 *
 * @author altius
 */
public class PrgSubFundingSourceDTO {

    private int subFundingSourceId;
    private PrgFundingSourceDTO fundingSource;
    private PrgLabelDTO label;
    private boolean active;
    private int realmId;

    public int getRealmId() {
        return realmId;
    }

    public void setRealmId(int realmId) {
        this.realmId = realmId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getSubFundingSourceId() {
        return subFundingSourceId;
    }

    public void setSubFundingSourceId(int subFundingSourceId) {
        this.subFundingSourceId = subFundingSourceId;
    }

    public PrgFundingSourceDTO getFundingSource() {
        return fundingSource;
    }

    public void setFundingSource(PrgFundingSourceDTO fundingSource) {
        this.fundingSource = fundingSource;
    }

    public PrgLabelDTO getLabel() {
        return label;
    }

    public void setLabel(PrgLabelDTO label) {
        this.label = label;
    }

}
