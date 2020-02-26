/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

/**
 *
 * @author altius
 */
public class Region extends BaseModel {

    private int regionId;
    private Label label;
    private RealmCountry realmCountry;

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public RealmCountry getRealmCountry() {
        return realmCountry;
    }

    public void setRealmCountry(RealmCountry realmCountry) {
        this.realmCountry = realmCountry;
    }

}
