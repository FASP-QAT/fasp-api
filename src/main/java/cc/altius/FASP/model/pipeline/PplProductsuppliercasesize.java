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
public class PplProductsuppliercasesize implements Serializable {

    private String productid;
    private String supplierid;
    private Date dtmeffective;
    private Integer intcasesize;
    private Date dtmchanged;
    private String user;
    private String note;
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

    public Date getDtmeffective() {
        return dtmeffective;
    }

    public void setDtmeffective(Date dtmeffective) {
        this.dtmeffective = dtmeffective;
    }

    public Integer getIntcasesize() {
        return intcasesize;
    }

    public void setIntcasesize(Integer intcasesize) {
        this.intcasesize = intcasesize;
    }

    public Date getDtmchanged() {
        return dtmchanged;
    }

    public void setDtmchanged(Date dtmchanged) {
        this.dtmchanged = dtmchanged;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(int pipelineId) {
        this.pipelineId = pipelineId;
    }

}
