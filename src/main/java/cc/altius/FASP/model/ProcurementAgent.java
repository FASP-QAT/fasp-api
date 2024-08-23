/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author akil
 */
public class ProcurementAgent extends BaseModel implements Serializable {

    private int procurementAgentId;
    private SimpleCodeObject realm;
    private SimpleCodeObject procurementAgentType;
    private Label label;
    private String procurementAgentCode;
    private double submittedToApprovedLeadTime;
    private double approvedToShippedLeadTime;
    private String colorHtmlCode;
    private String colorHtmlDarkCode;
    List<SimpleObject> programList;

    public ProcurementAgent() {
        this.programList = new LinkedList<>();
    }

    public ProcurementAgent(int procurementAgentId, Label label, String procurementAgentCode) {
        this.procurementAgentId = procurementAgentId;
        this.label = label;
        this.procurementAgentCode = procurementAgentCode;
        this.programList = new LinkedList<>();
    }

    public ProcurementAgent(int procurementAgentId, SimpleCodeObject realm, SimpleCodeObject procurementAgentType, Label label, String procurementAgentCode, double submittedToApprovedLeadTime, double approvedToShippedLeadTime) {
        this.procurementAgentId = procurementAgentId;
        this.realm = realm;
        this.procurementAgentType = procurementAgentType;
        this.label = label;
        this.procurementAgentCode = procurementAgentCode;
        this.submittedToApprovedLeadTime = submittedToApprovedLeadTime;
        this.approvedToShippedLeadTime = approvedToShippedLeadTime;
        this.programList = new LinkedList<>();
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

    public SimpleCodeObject getProcurementAgentType() {
        return procurementAgentType;
    }

    public void setProcurementAgentType(SimpleCodeObject procurementAgentType) {
        this.procurementAgentType = procurementAgentType;
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

    public String getColorHtmlCode() {
        return colorHtmlCode;
    }

    public void setColorHtmlCode(String colorHtmlCode) {
        this.colorHtmlCode = colorHtmlCode;
    }

    public String getColorHtmlDarkCode() {
        return colorHtmlDarkCode;
    }

    public void setColorHtmlDarkCode(String colorHtmlDarkCode) {
        this.colorHtmlDarkCode = colorHtmlDarkCode;
    }

    public List<SimpleObject> getProgramList() {
        return programList;
    }

    public void setProgramList(List<SimpleObject> programList) {
        this.programList = programList;
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
