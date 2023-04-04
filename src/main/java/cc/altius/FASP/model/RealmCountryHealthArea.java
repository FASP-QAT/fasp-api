/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author akil
 */
public class RealmCountryHealthArea implements Serializable {

    @JsonView({Views.ReportView.class, Views.InternalView.class})
    SimpleCodeObject realmCountry;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    List<SimpleCodeObject> healthAreaList;

    public RealmCountryHealthArea() {
        healthAreaList = new LinkedList<>();
    }

    public SimpleCodeObject getRealmCountry() {
        return realmCountry;
    }

    public void setRealmCountry(SimpleCodeObject realmCountry) {
        this.realmCountry = realmCountry;
    }

    public List<SimpleCodeObject> getHealthAreaList() {
        return healthAreaList;
    }

    public void setHealthAreaList(List<SimpleCodeObject> healthAreaList) {
        this.healthAreaList = healthAreaList;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.realmCountry);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RealmCountryHealthArea other = (RealmCountryHealthArea) obj;
        if (!Objects.equals(this.realmCountry, other.realmCountry)) {
            return false;
        }
        return true;
    }

}
