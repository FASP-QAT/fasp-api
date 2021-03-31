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
public class IntegrationView implements Serializable {

    private int integrationViewId;
    private String integrationViewDesc;
    private String integrationViewName;

    public IntegrationView() {
    }

    public IntegrationView(int integrationViewId, String integrationViewDesc, String integrationViewName) {
        this.integrationViewId = integrationViewId;
        this.integrationViewDesc = integrationViewDesc;
        this.integrationViewName = integrationViewName;
    }

    public int getIntegrationViewId() {
        return integrationViewId;
    }

    public void setIntegrationViewId(int integrationViewId) {
        this.integrationViewId = integrationViewId;
    }

    public String getIntegrationViewDesc() {
        return integrationViewDesc;
    }

    public void setIntegrationViewDesc(String integrationViewDesc) {
        this.integrationViewDesc = integrationViewDesc;
    }

    public String getIntegrationViewName() {
        return integrationViewName;
    }

    public void setIntegrationViewName(String integrationViewName) {
        this.integrationViewName = integrationViewName;
    }
    
}
