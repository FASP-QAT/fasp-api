/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model;

import com.fasterxml.jackson.annotation.JsonView;
import java.util.Objects;

/**
 *
 * @author akil
 */
public class SimpleObjectStringId {

    @JsonView({Views.UserListView.class})
    private String id;
    @JsonView({Views.UserListView.class})
    private Label label;

    public SimpleObjectStringId() {
    }

    public SimpleObjectStringId(String id, Label label) {
        this.id = id;
        this.label = label;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + Objects.hashCode(this.id);
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
        final SimpleObjectStringId other = (SimpleObjectStringId) obj;
        return Objects.equals(this.id, other.id);
    }

}
