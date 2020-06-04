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
public class PplTblbe_version implements Serializable {

    private String sbe_version;
    private Date dtmupdated;
    private int pipelineId;

    public String getSbe_version() {
        return sbe_version;
    }

    public void setSbe_version(String sbe_version) {
        this.sbe_version = sbe_version;
    }

    public Date getDtmupdated() {
        return dtmupdated;
    }

    public void setDtmupdated(Date dtmupdated) {
        this.dtmupdated = dtmupdated;
    }

    public int getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(int pipelineId) {
        this.pipelineId = pipelineId;
    }

}
