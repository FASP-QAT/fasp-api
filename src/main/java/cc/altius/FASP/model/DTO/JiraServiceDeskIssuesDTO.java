/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO;

import java.io.Serializable;

/**
 *
 * @author altius
 */
public class JiraServiceDeskIssuesDTO implements Serializable {
    
    private int openIssues;
    private int addressedIssues;

    public int getOpenIssues() {
        return openIssues;
    }

    public void setOpenIssues(int openIssues) {
        this.openIssues = openIssues;
    }

    public int getAddressedIssues() {
        return addressedIssues;
    }

    public void setAddressedIssues(int addressedIssues) {
        this.addressedIssues = addressedIssues;
    }
    
    
}
