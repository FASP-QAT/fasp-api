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
public class PplInventory implements Serializable {

    private String productid;
    private Date period;
    private Double invamount;
    private Boolean invtransferflag;
    private String invnote;
    private Date invdatechanged;
    private Integer ctrindex;
    private Boolean invdisplaynote;
    private String invdatasourceid;
    private Boolean fimported;
    private Double old_inventory;
    private int pipelineId;

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public Date getPeriod() {
        return period;
    }

    public void setPeriod(Date period) {
        this.period = period;
    }

    public Double getInvamount() {
        return invamount;
    }

    public void setInvamount(Double invamount) {
        this.invamount = invamount;
    }

    public Boolean getInvtransferflag() {
        return invtransferflag;
    }

    public void setInvtransferflag(Boolean invtransferflag) {
        this.invtransferflag = invtransferflag;
    }

    public String getInvnote() {
        return invnote;
    }

    public void setInvnote(String invnote) {
        this.invnote = invnote;
    }

    public Date getInvdatechanged() {
        return invdatechanged;
    }

    public void setInvdatechanged(Date invdatechanged) {
        this.invdatechanged = invdatechanged;
    }

    public Integer getCtrindex() {
        return ctrindex;
    }

    public void setCtrindex(Integer ctrindex) {
        this.ctrindex = ctrindex;
    }

    public Boolean getInvdisplaynote() {
        return invdisplaynote;
    }

    public void setInvdisplaynote(Boolean invdisplaynote) {
        this.invdisplaynote = invdisplaynote;
    }

    public String getInvdatasourceid() {
        return invdatasourceid;
    }

    public void setInvdatasourceid(String invdatasourceid) {
        this.invdatasourceid = invdatasourceid;
    }

    public Boolean getFimported() {
        return fimported;
    }

    public void setFimported(Boolean fimported) {
        this.fimported = fimported;
    }

    public Double getOld_inventory() {
        return old_inventory;
    }

    public void setOld_inventory(Double old_inventory) {
        this.old_inventory = old_inventory;
    }

    public int getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(int pipelineId) {
        this.pipelineId = pipelineId;
    }

}
