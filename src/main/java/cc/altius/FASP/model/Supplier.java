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
public class Supplier extends BaseModel implements Serializable {

    private int supplierId;
    private Label label;
    private SimpleCodeObject realm;

    public Supplier(int supplierId, Label label, SimpleCodeObject realm) {
        this.supplierId = supplierId;
        this.label = label;
        this.realm = realm;
    }

    public Supplier() {
    }

    public Supplier(int supplierId, Label label) {
        this.supplierId = supplierId;
        this.label = label;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public SimpleCodeObject getRealm() {
        return realm;
    }

    public void setRealm(SimpleCodeObject realm) {
        this.realm = realm;
    }

    @Override
    public String toString() {
        return "Supplier{" + "supplierId=" + supplierId + ", label=" + label + ", realm=" + realm + '}';
    }

}
