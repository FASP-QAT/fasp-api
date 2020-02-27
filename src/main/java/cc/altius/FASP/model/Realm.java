/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import java.io.Serializable;

/**
 *
 * @author altius
 */
public class Realm implements Serializable {

    private Integer realmId;
    private Label label;
    private String realmCode;

    public Realm(Integer realmId, Label label, String realmCode) {
        this.realmId = realmId;
        this.label = label;
        this.realmCode = realmCode;
    }

    public Realm() {
    }

    public Realm(Integer realmId) {
        this.realmId = realmId;
    }

    public Integer getRealmId() {
        return realmId;
    }

    public void setRealmId(Integer realmId) {
        this.realmId = realmId;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public String getRealmCode() {
        return realmCode;
    }

    public void setRealmCode(String realmCode) {
        this.realmCode = realmCode;
    }

    @Override
    public String toString() {
        return "Realm{" + "realmId=" + realmId + ", label=" + label + ", realmCode=" + realmCode + '}';
    }

}
