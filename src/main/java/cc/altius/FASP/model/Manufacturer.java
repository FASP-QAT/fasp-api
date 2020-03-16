/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import java.io.Serializable;

/**
 *
 * @author palash
 */
public class Manufacturer extends BaseModel implements Serializable {

    private int manufacturerId;
    private Label label;
    private Realm realm;

    public Manufacturer(int manufacturerId, Label label, Realm realm) {
        this.manufacturerId = manufacturerId;
        this.label = label;
        this.realm = realm;
    }

    public Manufacturer() {
    }

    public int getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(int manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public Realm getRealm() {
        return realm;
    }

    public void setRealm(Realm realm) {
        this.realm = realm;
    }

    @Override
    public String toString() {
        return "Manufacturer{" + "manufacturerId=" + manufacturerId + ", label=" + label + ", realm=" + realm + '}';
    }

}
