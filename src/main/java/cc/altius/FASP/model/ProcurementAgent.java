/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import java.io.Serializable;

/**
 *
 * @author akil
 */
public class ProcurementAgent extends BaseModel implements Serializable {

    private int procurementAgentId;
    private SimpleCodeObject realm;
    private Label label;
    private String procurementAgentCode;
    private double draftToSubmittedLeadTime;
    private double submittedToApprovedLeadTime;
    private double approvedToShippedLeadTime;
    private boolean localProcurementAgent;
    private String colorHtmlCode;

    public ProcurementAgent() {
    }

    public ProcurementAgent(int procurementAgentId, Label label, String procurementAgentCode) {
        this.procurementAgentId = procurementAgentId;
        this.label = label;
        this.procurementAgentCode = procurementAgentCode;
    }

    public ProcurementAgent(int procurementAgentId, SimpleCodeObject realm, Label label, String procurementAgentCode, double draftToSubmittedLeadTime, double submittedToApprovedLeadTime, double approvedToShippedLeadTime, boolean localProcurementAgent) {
        this.procurementAgentId = procurementAgentId;
        this.realm = realm;
        this.label = label;
        this.procurementAgentCode = procurementAgentCode;
        this.draftToSubmittedLeadTime = draftToSubmittedLeadTime;
        this.submittedToApprovedLeadTime = submittedToApprovedLeadTime;
        this.approvedToShippedLeadTime = approvedToShippedLeadTime;
        this.localProcurementAgent = localProcurementAgent;
    }

    public int getProcurementAgentId() {
        return procurementAgentId;
    }

    public void setProcurementAgentId(int procurementAgentId) {
        this.procurementAgentId = procurementAgentId;
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

    public String getProcurementAgentCode() {
        return procurementAgentCode;
    }

    public void setProcurementAgentCode(String procurementAgentCode) {
        this.procurementAgentCode = procurementAgentCode;
    }

    public double getApprovedToShippedLeadTime() {
        return approvedToShippedLeadTime;
    }

    public void setApprovedToShippedLeadTime(double approvedToShippedLeadTime) {
        this.approvedToShippedLeadTime = approvedToShippedLeadTime;
    }

    public double getSubmittedToApprovedLeadTime() {
        return submittedToApprovedLeadTime;
    }

    public void setSubmittedToApprovedLeadTime(double submittedToApprovedLeadTime) {
        this.submittedToApprovedLeadTime = submittedToApprovedLeadTime;
    }

    public double getDraftToSubmittedLeadTime() {
        return draftToSubmittedLeadTime;
    }

    public void setDraftToSubmittedLeadTime(double draftToSubmittedLeadTime) {
        this.draftToSubmittedLeadTime = draftToSubmittedLeadTime;
    }

    public boolean isLocalProcurementAgent() {
        return localProcurementAgent;
    }

    public void setLocalProcurementAgent(boolean localProcurementAgent) {
        this.localProcurementAgent = localProcurementAgent;
    }

    public String getColorHtmlCode() {
        return colorHtmlCode;
    }

    public void setColorHtmlCode(String colorHtmlCode) {
        this.colorHtmlCode = colorHtmlCode;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + this.procurementAgentId;
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
        final ProcurementAgent other = (ProcurementAgent) obj;
        if (this.procurementAgentId != other.procurementAgentId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ProcurementAgent{" + "procurementAgentId=" + procurementAgentId + ", realm=" + realm + ", label=" + label + ", submittedToApprovedLeadTime=" + submittedToApprovedLeadTime + '}';
    }

}
