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
public class ProcurementAgentType extends BaseModel implements Serializable {

    private int procurementAgentTypeId;
    private SimpleCodeObject realm;
    private Label label;
    private String procurementAgentTypeCode;

    public ProcurementAgentType() {
    }

    public ProcurementAgentType(int procurementAgentTypeId, Label label, String procurementAgentTypeCode) {
        this.procurementAgentTypeId = procurementAgentTypeId;
        this.label = label;
        this.procurementAgentTypeCode = procurementAgentTypeCode;
    }

    public ProcurementAgentType(int procurementAgentTypeId, SimpleCodeObject realm, Label label, String procurementAgentTypeCode) {
        this.procurementAgentTypeId = procurementAgentTypeId;
        this.realm = realm;
        this.label = label;
        this.procurementAgentTypeCode = procurementAgentTypeCode;
    }

    public int getProcurementAgentTypeId() {
        return procurementAgentTypeId;
    }

    public void setProcurementAgentTypeId(int procurementAgentTypeId) {
        this.procurementAgentTypeId = procurementAgentTypeId;
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

    public String getProcurementAgentTypeCode() {
        return procurementAgentTypeCode;
    }

    public void setProcurementAgentTypeCode(String procurementAgentTypeCode) {
        this.procurementAgentTypeCode = procurementAgentTypeCode;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + this.procurementAgentTypeId;
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
        final ProcurementAgentType other = (ProcurementAgentType) obj;
        if (this.procurementAgentTypeId != other.procurementAgentTypeId) {
            return false;
        }
        return true;
    }

}
