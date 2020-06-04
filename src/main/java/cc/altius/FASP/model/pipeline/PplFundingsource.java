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
public class PplFundingsource implements Serializable {

    private String fundingsourceid;
    private String fundingsourcename;
    private String fundingnote;
    private int pipelineId;

    public String getFundingsourceid() {
        return fundingsourceid;
    }

    public void setFundingsourceid(String fundingsourceid) {
        this.fundingsourceid = fundingsourceid;
    }

    public String getFundingsourcename() {
        return fundingsourcename;
    }

    public void setFundingsourcename(String fundingsourcename) {
        this.fundingsourcename = fundingsourcename;
    }

    public String getFundingnote() {
        return fundingnote;
    }

    public void setFundingnote(String fundingnote) {
        this.fundingnote = fundingnote;
    }

    public int getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(int pipelineId) {
        this.pipelineId = pipelineId;
    }

}
