/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;

/**
 *
 * @author akil
 */
public class NodeType implements Serializable {

    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private int id;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private Label label;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private boolean modelingAllowed;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private boolean extrapolationAllowed;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private boolean treeTemplateAllowed;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private boolean forecastTreeAllowed;

    public NodeType() {
    }

    public NodeType(int id, Label label, boolean modelingAllowed, boolean extrapolationAllowed, boolean treeTemplateAllowed, boolean forecastTreeAllowed) {
        this.id = id;
        this.label = label;
        this.modelingAllowed = modelingAllowed;
        this.extrapolationAllowed = extrapolationAllowed;
        this.treeTemplateAllowed = treeTemplateAllowed;
        this.forecastTreeAllowed = forecastTreeAllowed;
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

    public boolean isExtrapolationAllowed() {
        return extrapolationAllowed;
    }

    public void setExtrapolationAllowed(boolean extrapolationAllowed) {
        this.extrapolationAllowed = extrapolationAllowed;
    }

    public boolean isTreeTemplateAllowed() {
        return treeTemplateAllowed;
    }

    public void setTreeTemplateAllowed(boolean treeTemplateAllowed) {
        this.treeTemplateAllowed = treeTemplateAllowed;
    }

    public boolean isForecastTreeAllowed() {
        return forecastTreeAllowed;
    }

    public void setForecastTreeAllowed(boolean forecastTreeAllowed) {
        this.forecastTreeAllowed = forecastTreeAllowed;
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
