/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;

/**
 *
 * @author palash
 */
public class FundingSource extends BaseModel implements Serializable {

    @JsonView({Views.ReportView.class})
    private int fundingSourceId;
    @JsonView({Views.ReportView.class})
    private String fundingSourceCode;
    @JsonView({Views.ReportView.class})
    private Label label;
    private SimpleCodeObject realm;
    @JsonView({Views.ReportView.class})
    private boolean allowedInBudget;

    public FundingSource() {
    }

    public FundingSource(int fundingSourceId, String fundingSourceCode, Label label, SimpleCodeObject realm) {
        this.fundingSourceId = fundingSourceId;
        this.fundingSourceCode = fundingSourceCode;
        this.label = label;
        this.realm = realm;
    }

    public FundingSource(int fundingSourceId, String fundingSourceCode, Label label) {
        this.fundingSourceId = fundingSourceId;
        this.fundingSourceCode = fundingSourceCode;
        this.label = label;
    }

    public int getFundingSourceId() {
        return fundingSourceId;
    }

    public void setFundingSourceId(int fundingSourceId) {
        this.fundingSourceId = fundingSourceId;
    }

    public String getFundingSourceCode() {
        return fundingSourceCode;
    }

    public void setFundingSourceCode(String fundingSourceCode) {
        this.fundingSourceCode = fundingSourceCode;
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

    public boolean isAllowedInBudget() {
        return allowedInBudget;
    }

    public void setAllowedInBudget(boolean allowedInBudget) {
        this.allowedInBudget = allowedInBudget;
    }

    @Override
    public String toString() {
        return "FundingSource{" + "fundingSourceId=" + fundingSourceId + ", label=" + label + ", realm=" + realm + '}';
    }

}
