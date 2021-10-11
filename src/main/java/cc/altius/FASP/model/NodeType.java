/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import java.io.Serializable;

/**
 *
 * @author akil
 */
public class NodeType implements Serializable {

    private int id;
    private Label label;
    private boolean modelingAllowed;

    public NodeType() {
    }

    public NodeType(int id, Label label, boolean modelingAllowed) {
        this.id = id;
        this.label = label;
        this.modelingAllowed = modelingAllowed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public boolean isModelingAllowed() {
        return modelingAllowed;
    }

    public void setModelingAllowed(boolean modelingAllowed) {
        this.modelingAllowed = modelingAllowed;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.id;
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
        final NodeType other = (NodeType) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

}
