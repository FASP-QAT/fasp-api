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
public class PplTblimportrecords implements Serializable {

    private String strproductid;
    private Date dtmperiod;
    private Integer lngconsumption;
    private Integer lngadjustment;
    private Double dbldatainterval;
    private Integer lngparentid;
    private int pipelineId;

    public String getStrproductid() {
        return strproductid;
    }

    public void setStrproductid(String strproductid) {
        this.strproductid = strproductid;
    }

    public Date getDtmperiod() {
        return dtmperiod;
    }

    public void setDtmperiod(Date dtmperiod) {
        this.dtmperiod = dtmperiod;
    }

    public Integer getLngconsumption() {
        return lngconsumption;
    }

    public void setLngconsumption(Integer lngconsumption) {
        this.lngconsumption = lngconsumption;
    }

    public Integer getLngadjustment() {
        return lngadjustment;
    }

    public void setLngadjustment(Integer lngadjustment) {
        this.lngadjustment = lngadjustment;
    }

    public Double getDbldatainterval() {
        return dbldatainterval;
    }

    public void setDbldatainterval(Double dbldatainterval) {
        this.dbldatainterval = dbldatainterval;
    }

    public Integer getLngparentid() {
        return lngparentid;
    }

    public void setLngparentid(Integer lngparentid) {
        this.lngparentid = lngparentid;
    }

    public int getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(int pipelineId) {
        this.pipelineId = pipelineId;
    }

}
