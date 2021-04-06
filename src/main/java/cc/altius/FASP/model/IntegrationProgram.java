/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import java.io.Serializable;

/**
 *
 * @author akil
 */
public class IntegrationProgram extends BaseModel implements Serializable {

    private int integrationProgramId;
    private Integration integration;
    private SimpleObject program;
    private SimpleObject versionType;
    private SimpleObject versionStatus;

    public int getIntegrationProgramId() {
        return integrationProgramId;
    }

    public void setIntegrationProgramId(int integrationProgramId) {
        this.integrationProgramId = integrationProgramId;
    }

    public Integration getIntegration() {
        return integration;
    }

    public void setIntegration(Integration integration) {
        this.integration = integration;
    }

    public SimpleObject getProgram() {
        return program;
    }

    public void setProgram(SimpleObject program) {
        this.program = program;
    }

    public SimpleObject getVersionType() {
        return versionType;
    }

    public void setVersionType(SimpleObject versionType) {
        this.versionType = versionType;
    }

    public SimpleObject getVersionStatus() {
        return versionStatus;
    }

    public void setVersionStatus(SimpleObject versionStatus) {
        this.versionStatus = versionStatus;
    }

}
