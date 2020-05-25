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
public class PplConsumption implements Serializable {

    private String productid;
    private Integer consstartyear;
    private Integer consstartmonth;
    private Boolean consactualflag;
    private Integer consnummonths;
    private Double consamount;
    private String consdatasourceid;
    private Double consiflator;
    private String consnote;
    private Date consdatechanged;
    private Integer consid;
    private Boolean consdisplaynote;
    private Double old_consumption;
    private int pipelineId;
    private String conDate;

    public String getConDate() {
        return conDate;
    }

    public void setConDate(String conDate) {
        this.conDate = conDate;
    }
    
    

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public Integer getConsstartyear() {
        return consstartyear;
    }

    public void setConsstartyear(Integer consstartyear) {
        this.consstartyear = consstartyear;
    }

    public Integer getConsstartmonth() {
        return consstartmonth;
    }

    public void setConsstartmonth(Integer consstartmonth) {
        this.consstartmonth = consstartmonth;
    }

    public Boolean getConsactualflag() {
        return consactualflag;
    }

    public void setConsactualflag(Boolean consactualflag) {
        this.consactualflag = consactualflag;
    }

    public Integer getConsnummonths() {
        return consnummonths;
    }

    public void setConsnummonths(Integer consnummonths) {
        this.consnummonths = consnummonths;
    }

    public Double getConsamount() {
        return consamount;
    }

    public void setConsamount(Double consamount) {
        this.consamount = consamount;
    }

    public String getConsdatasourceid() {
        return consdatasourceid;
    }

    public void setConsdatasourceid(String consdatasourceid) {
        this.consdatasourceid = consdatasourceid;
    }

    public Double getConsiflator() {
        return consiflator;
    }

    public void setConsiflator(Double consiflator) {
        this.consiflator = consiflator;
    }

    public String getConsnote() {
        return consnote;
    }

    public void setConsnote(String consnote) {
        this.consnote = consnote;
    }

    public Date getConsdatechanged() {
        return consdatechanged;
    }

    public void setConsdatechanged(Date consdatechanged) {
        this.consdatechanged = consdatechanged;
    }

    public Integer getConsid() {
        return consid;
    }

    public void setConsid(Integer consid) {
        this.consid = consid;
    }

    public Boolean getConsdisplaynote() {
        return consdisplaynote;
    }

    public void setConsdisplaynote(Boolean consdisplaynote) {
        this.consdisplaynote = consdisplaynote;
    }

    public Double getOld_consumption() {
        return old_consumption;
    }

    public void setOld_consumption(Double old_consumption) {
        this.old_consumption = old_consumption;
    }

    public int getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(int pipelineId) {
        this.pipelineId = pipelineId;
    }
}
