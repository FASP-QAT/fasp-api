/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import com.fasterxml.jackson.annotation.JsonView;

/**
 *
 * @author akil
 */
public class TreeLevel {

    @JsonView(Views.InternalView.class)
    private int levelId;
    @JsonView(Views.InternalView.class)
    private int levelNo;
    @JsonView(Views.InternalView.class)
    private Label label;
    @JsonView(Views.InternalView.class)
    private SimpleCodeObject unit;

    public TreeLevel() {
    }

    public TreeLevel(int levelId, int levelNo) {
        this.levelId = levelId;
        this.levelNo = levelNo;
    }

    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    public int getLevelNo() {
        return levelNo;
    }

    public void setLevelNo(int levelNo) {
        this.levelNo = levelNo;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public SimpleCodeObject getUnit() {
        return unit;
    }

    public void setUnit(SimpleCodeObject unit) {
        this.unit = unit;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.levelId;
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
        final TreeLevel other = (TreeLevel) obj;
        if (this.levelId != other.levelId) {
            return false;
        }
        return true;
    }

}
