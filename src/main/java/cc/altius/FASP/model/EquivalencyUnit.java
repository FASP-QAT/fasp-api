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

/**
 *
 * @author altius
 */
public class EquivalencyUnit extends BaseModel implements Serializable {

    @JsonView(Views.ReportView.class)
    private int equivalencyUnitId;
    private SimpleCodeObject realm;
    private SimpleCodeObject program;
    private List<SimpleCodeObject> healthAreaList;
    private Label label;
    private String notes;

    public EquivalencyUnit() {
        healthAreaList = new LinkedList<>();
    }

    public EquivalencyUnit(int equivalencyUnitId, SimpleCodeObject realm, SimpleCodeObject program, Label label, String notes) {
        this.equivalencyUnitId = equivalencyUnitId;
        this.realm = realm;
        if (program == null || program.getId() == 0 || program.getId() == null) {
            this.program = null;
        } else {
            this.program = program;
        }
        this.label = label;
        this.notes = notes;
        healthAreaList = new LinkedList<>();
    }

    public int getEquivalencyUnitId() {
        return equivalencyUnitId;
    }

    public void setEquivalencyUnitId(int equivalencyUnitId) {
        this.equivalencyUnitId = equivalencyUnitId;
    }

    public SimpleCodeObject getRealm() {
        return realm;
    }

    public void setRealm(SimpleCodeObject realm) {
        this.realm = realm;
    }

    public SimpleCodeObject getProgram() {
        return program;
    }

    public void setProgram(SimpleCodeObject program) {
        this.program = program;
    }

    public List<SimpleCodeObject> getHealthAreaList() {
        return healthAreaList;
    }

    public void setHealthAreaList(List<SimpleCodeObject> healthAreaList) {
        this.healthAreaList = healthAreaList;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.equivalencyUnitId;
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
        final EquivalencyUnit other = (EquivalencyUnit) obj;
        if (this.equivalencyUnitId != other.equivalencyUnitId) {
            return false;
        }
        return true;
    }

}
