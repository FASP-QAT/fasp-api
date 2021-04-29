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
public class Integration implements Serializable {

    private int integrationId;
    private String integrationName;
    private SimpleObject realm;
    private String folderLocation;
    private String fileName;
    private IntegrationView integrationView;

    public int getIntegrationId() {
        return integrationId;
    }

    public void setIntegrationId(int integrationId) {
        this.integrationId = integrationId;
    }

    public String getIntegrationName() {
        return integrationName;
    }

    public void setIntegrationName(String integrationName) {
        this.integrationName = integrationName;
    }

    public SimpleObject getRealm() {
        return realm;
    }

    public void setRealm(SimpleObject realm) {
        this.realm = realm;
    }

    public String getFolderLocation() {
        return folderLocation;
    }

    public void setFolderLocation(String folderLocation) {
        this.folderLocation = folderLocation;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public IntegrationView getIntegrationView() {
        return integrationView;
    }

    public void setIntegrationView(IntegrationView integrationView) {
        this.integrationView = integrationView;
    }

}
