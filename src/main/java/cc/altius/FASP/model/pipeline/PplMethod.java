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
public class PplMethod implements Serializable {

    private String methodid;
    private String methodname;
    private Double cypfactor;
    private String methodnote;
    private Integer parentid;
    private Integer categoryid;
    private Boolean frollup;
    private int pipelineId;

    public String getMethodid() {
        return methodid;
    }

    public void setMethodid(String methodid) {
        this.methodid = methodid;
    }

    public String getMethodname() {
        return methodname;
    }

    public void setMethodname(String methodname) {
        this.methodname = methodname;
    }

    public Double getCypfactor() {
        return cypfactor;
    }

    public void setCypfactor(Double cypfactor) {
        this.cypfactor = cypfactor;
    }

    public String getMethodnote() {
        return methodnote;
    }

    public void setMethodnote(String methodnote) {
        this.methodnote = methodnote;
    }

    public Integer getParentid() {
        return parentid;
    }

    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

    public Integer getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(Integer categoryid) {
        this.categoryid = categoryid;
    }

    public Boolean getFrollup() {
        return frollup;
    }

    public void setFrollup(Boolean frollup) {
        this.frollup = frollup;
    }

    public int getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(int pipelineId) {
        this.pipelineId = pipelineId;
    }

}
