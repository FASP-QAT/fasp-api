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
public class ProcurementAgentProcurementUnit extends BaseModel implements Serializable {

    private int procurementAgentProcurementUnitId;
    private SimpleObject procurementAgent;
    private SimpleObject procurementUnit;
    private String skuCode;
    private Double vendorPrice;
    private Integer approvedToShippedLeadTime;
    private String gtin;

    public ProcurementAgentProcurementUnit() {
    }

    public ProcurementAgentProcurementUnit(int procurementAgentProcurementUnitId, SimpleObject procurementAgent, SimpleObject procurementUnit, String skuCode, Double vendorPrice, Integer approvedToShippedLeadTime, String gtin) {
        this.procurementAgentProcurementUnitId = procurementAgentProcurementUnitId;
        this.procurementAgent = procurementAgent;
        this.procurementUnit = procurementUnit;
        this.skuCode = skuCode;
        this.vendorPrice = vendorPrice;
        this.approvedToShippedLeadTime = approvedToShippedLeadTime;
        this.gtin = gtin;
    }

    public int getProcurementAgentProcurementUnitId() {
        return procurementAgentProcurementUnitId;
    }

    public void setProcurementAgentProcurementUnitId(int procurementAgentProcurementUnitId) {
        this.procurementAgentProcurementUnitId = procurementAgentProcurementUnitId;
    }

    public SimpleObject getProcurementAgent() {
        return procurementAgent;
    }

    public void setProcurementAgent(SimpleObject procurementAgent) {
        this.procurementAgent = procurementAgent;
    }

    public SimpleObject getProcurementUnit() {
        return procurementUnit;
    }

    public void setProcurementUnit(SimpleObject procurementUnit) {
        this.procurementUnit = procurementUnit;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public Double getVendorPrice() {
        return vendorPrice;
    }

    public void setVendorPrice(Double vendorPrice) {
        this.vendorPrice = vendorPrice;
    }

    public Integer getApprovedToShippedLeadTime() {
        return approvedToShippedLeadTime;
    }

    public void setApprovedToShippedLeadTime(Integer approvedToShippedLeadTime) {
        this.approvedToShippedLeadTime = approvedToShippedLeadTime;
    }

    public String getGtin() {
        return gtin;
    }

    public void setGtin(String gtin) {
        this.gtin = gtin;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + this.procurementAgentProcurementUnitId;
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
        final ProcurementAgentProcurementUnit other = (ProcurementAgentProcurementUnit) obj;
        if (this.procurementAgentProcurementUnitId != other.procurementAgentProcurementUnitId) {
            return false;
        }
        return true;
    }

}
