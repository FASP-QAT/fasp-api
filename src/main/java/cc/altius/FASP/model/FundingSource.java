/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

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
    @JsonView({Views.ReportView.class})
    private SimpleCodeObject realm;
    @JsonView({Views.ReportView.class})
    private boolean allowedInBudget;
    @JsonView({Views.ReportView.class})
    private SimpleCodeObject fundingSourceType;
    @JsonView({Views.ReportView.class})
    List<SimpleObject> programList;

    public FundingSource() {
        this.programList = new LinkedList<>();
    }

    public FundingSource(int fundingSourceId, String fundingSourceCode, Label label, SimpleCodeObject realm) {
        this.fundingSourceId = fundingSourceId;
        this.fundingSourceCode = fundingSourceCode;
        this.label = label;
        this.realm = realm;
        this.programList = new LinkedList<>();
    }

    public FundingSource(int fundingSourceId, String fundingSourceCode, Label label) {
        this.fundingSourceId = fundingSourceId;
        this.fundingSourceCode = fundingSourceCode;
        this.label = label;
        this.programList = new LinkedList<>();
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

    public SimpleCodeObject getFundingSourceType() {
        return fundingSourceType;
    }

    public void setFundingSourceType(SimpleCodeObject fundingSourceType) {
        this.fundingSourceType = fundingSourceType;
    }

    public List<SimpleObject> getProgramList() {
        return programList;
    }

    public void setProgramList(List<SimpleObject> programList) {
        this.programList = programList;
    }

    @JsonView({Views.ReportView.class})
    public BasicUser getLastModifiedBy() {
        return super.getLastModifiedBy();
    }

    @JsonView({Views.ReportView.class})
    public Date getLastModifiedDate() {
        return super.getLastModifiedDate();
    }

    @JsonView({Views.ReportView.class})
    public boolean isActive() {
        return super.isActive();
    }

    @Override
    public String toString() {
        return "FundingSource{" + "fundingSourceId=" + fundingSourceId + ", label=" + label + ", realm=" + realm + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + this.fundingSourceId;
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
        final FundingSource other = (FundingSource) obj;
        return this.fundingSourceId == other.fundingSourceId;
    }

}
