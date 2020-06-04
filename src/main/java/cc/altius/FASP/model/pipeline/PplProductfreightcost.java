/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.pipeline;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author akil
 */
public class PplProductfreightcost implements Serializable {

    private String productid;
    private String supplierid;
    private Double freightcost;
    private Date dtmchanged;
    private int pipelineId;

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getSupplierid() {
        return supplierid;
    }

    public void setSupplierid(String supplierid) {
        this.supplierid = supplierid;
    }

    public Double getFreightcost() {
        return freightcost;
    }

    public void setFreightcost(Double freightcost) {
        this.freightcost = freightcost;
    }

    public Date getDtmchanged() {
        return dtmchanged;
    }

    public void setDtmchanged(Date dtmchanged) {
        this.dtmchanged = dtmchanged;
    }

    public int getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(int pipelineId) {
        this.pipelineId = pipelineId;
    }

}
