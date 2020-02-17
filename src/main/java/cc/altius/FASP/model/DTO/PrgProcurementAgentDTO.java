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
public class PrgProcurementAgentDTO {

    private int procurementAgentId;
    private PrgLabelDTO label;
    private int submittedToApprovedLeadTime;
    private PrgProcurementAgentLogisiticsUnitDTO prgProcurementAgentLogisiticsUnit;

    public PrgProcurementAgentLogisiticsUnitDTO getPrgProcurementAgentLogisiticsUnit() {
        return prgProcurementAgentLogisiticsUnit;
    }

    public void setPrgProcurementAgentLogisiticsUnit(PrgProcurementAgentLogisiticsUnitDTO prgProcurementAgentLogisiticsUnit) {
        this.prgProcurementAgentLogisiticsUnit = prgProcurementAgentLogisiticsUnit;
    }

    public int getProcurementAgentId() {
        return procurementAgentId;
    }

    public void setProcurementAgentId(int procurementAgentId) {
        this.procurementAgentId = procurementAgentId;
    }

    public PrgLabelDTO getLabel() {
        return label;
    }

    public void setLabel(PrgLabelDTO label) {
        this.label = label;
    }

    public int getSubmittedToApprovedLeadTime() {
        return submittedToApprovedLeadTime;
    }

    public void setSubmittedToApprovedLeadTime(int submittedToApprovedLeadTime) {
        this.submittedToApprovedLeadTime = submittedToApprovedLeadTime;
    }

}
