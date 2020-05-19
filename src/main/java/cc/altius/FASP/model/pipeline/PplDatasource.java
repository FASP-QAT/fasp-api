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
public class PplDatasource implements Serializable {

    private String datasourceid;
    private String datasourcename;
    private String datasourcetypeid;
    private int pipelineId;

    public String getDatasourceid() {
        return datasourceid;
    }

    public void setDatasourceid(String datasourceid) {
        this.datasourceid = datasourceid;
    }

    public String getDatasourcename() {
        return datasourcename;
    }

    public void setDatasourcename(String datasourcename) {
        this.datasourcename = datasourcename;
    }

    public String getDatasourcetypeid() {
        return datasourcetypeid;
    }

    public void setDatasourcetypeid(String datasourcetypeid) {
        this.datasourcetypeid = datasourcetypeid;
    }

    public int getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(int pipelineId) {
        this.pipelineId = pipelineId;
    }
}
