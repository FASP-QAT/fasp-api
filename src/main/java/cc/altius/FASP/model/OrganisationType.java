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
public class OrganisationType extends BaseModel implements Serializable {

    private int organisationTypeId;
    private SimpleCodeObject realm;
    private Label label;

    public OrganisationType() {
    }

    public OrganisationType(int organisationTypeId, Label label) {
        this.organisationTypeId = organisationTypeId;
        this.label = label;
    }

    public OrganisationType(int organisationTypeId, SimpleCodeObject realm, Label label) {
        this.organisationTypeId = organisationTypeId;
        this.realm = realm;
        this.label = label;
    }

    public int getOrganisationTypeId() {
        return organisationTypeId;
    }

    public void setOrganisationTypeId(int organisationTypeId) {
        this.organisationTypeId = organisationTypeId;
    }

    public SimpleCodeObject getRealm() {
        return realm;
    }

    public void setRealm(SimpleCodeObject realm) {
        this.realm = realm;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "OrganisationType{" + "organisationTypeId=" + organisationTypeId + ", realm=" + realm + ", label=" + label + '}';
    }

}
