/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.pipeline;

import java.io.Serializable;

/**
 *
 * @author akil
 */
public class PplMonthlystockarchive implements Serializable {

    private String productid;
    private Double eoybalance;
    private Integer stockyear;
    private Integer stockmonth;
    private int pipelineId;

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public Double getEoybalance() {
        return eoybalance;
    }

    public void setEoybalance(Double eoybalance) {
        this.eoybalance = eoybalance;
    }

    public Integer getStockyear() {
        return stockyear;
    }

    public void setStockyear(Integer stockyear) {
        this.stockyear = stockyear;
    }

    public Integer getStockmonth() {
        return stockmonth;
    }

    public void setStockmonth(Integer stockmonth) {
        this.stockmonth = stockmonth;
    }

    public int getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(int pipelineId) {
        this.pipelineId = pipelineId;
    }

}
