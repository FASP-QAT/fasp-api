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
public class PplTblimportproducts implements Serializable {

    private String strproductid;
    private String strname;
    private String strdose;
    private Double lngcyp;
    private Date dtmexport;
    private Boolean fprocessed;
    private Integer lngid;
    private String strsource;
    private String strmapping;
    private int pipelineId;

    public String getStrproductid() {
        return strproductid;
    }

    public void setStrproductid(String strproductid) {
        this.strproductid = strproductid;
    }

    public String getStrname() {
        return strname;
    }

    public void setStrname(String strname) {
        this.strname = strname;
    }

    public String getStrdose() {
        return strdose;
    }

    public void setStrdose(String strdose) {
        this.strdose = strdose;
    }

    public Double getLngcyp() {
        return lngcyp;
    }

    public void setLngcyp(Double lngcyp) {
        this.lngcyp = lngcyp;
    }

    public Date getDtmexport() {
        return dtmexport;
    }

    public void setDtmexport(Date dtmexport) {
        this.dtmexport = dtmexport;
    }

    public Boolean getFprocessed() {
        return fprocessed;
    }

    public void setFprocessed(Boolean fprocessed) {
        this.fprocessed = fprocessed;
    }

    public Integer getLngid() {
        return lngid;
    }

    public void setLngid(Integer lngid) {
        this.lngid = lngid;
    }

    public String getStrsource() {
        return strsource;
    }

    public void setStrsource(String strsource) {
        this.strsource = strsource;
    }

    public String getStrmapping() {
        return strmapping;
    }

    public void setStrmapping(String strmapping) {
        this.strmapping = strmapping;
    }

    public int getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(int pipelineId) {
        this.pipelineId = pipelineId;
    }

}
