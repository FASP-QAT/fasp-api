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
public class ModelingType extends BaseModel implements Serializable {

    private int modelingTypeId;
    private Label label;

    public ModelingType() {

    }

    public ModelingType(int usagePeriodId, Label label) {
        this.modelingTypeId = usagePeriodId;
        this.label = label;
    }

    public int getModelingTypeId() {
        return modelingTypeId;
    }

    public void setModelingTypeId(int modelingTypeId) {
        this.modelingTypeId = modelingTypeId;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

}
