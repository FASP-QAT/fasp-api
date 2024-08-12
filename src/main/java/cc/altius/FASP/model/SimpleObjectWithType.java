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
public class SimpleObjectWithType extends SimpleObject {

    @JsonView({Views.ArtmisView.class,Views.GfpVanView.class, Views.InternalView.class, Views.ReportView.class, Views.DropDownView.class})
    private int typeId;

    public SimpleObjectWithType() {
    }

    public SimpleObjectWithType(Integer id, Label label, int typeId) {
        super(id, label);
        this.typeId = typeId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

}
