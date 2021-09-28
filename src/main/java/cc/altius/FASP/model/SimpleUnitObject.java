/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

/**
 *
 * @author akil
 */
public class SimpleUnitObject extends SimpleObject {

    private SimpleCodeObject unit;

    public SimpleCodeObject getUnit() {
        return unit;
    }

    public void setUnit(SimpleCodeObject unit) {
        this.unit = unit;
    }

    public SimpleUnitObject() {
    }

    public SimpleUnitObject(SimpleCodeObject unit, Integer id, Label label) {
        super(id, label);
        this.unit = unit;
    }

}
