/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO;

import cc.altius.FASP.model.Label;

/**
 *
 * @author altius
 */
public class DatabaseTranslationsDTO extends Label {

    private int realmId;
    private String labelFor;

    public int getRealmId() {
        return realmId;
    }

    public void setRealmId(int realmId) {
        this.realmId = realmId;
    }

    public String getLabelFor() {
        return labelFor;
    }

    public void setLabelFor(String labelFor) {
        this.labelFor = labelFor;
    }

}
