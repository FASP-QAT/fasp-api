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
public class SimpleUnitAndTracerObject extends SimpleUnitObject {

    private SimpleObject tracerCategory;

    public SimpleObject getTracerCategory() {
        return tracerCategory;
    }

    public void setTracerCategory(SimpleObject tracerCategory) {
        this.tracerCategory = tracerCategory;
    }

    public SimpleUnitAndTracerObject() {
    }

    public SimpleUnitAndTracerObject(SimpleObject tracerCategory, SimpleCodeObject unit, Integer id, Label label) {
        super(unit, id, label);
        this.tracerCategory = tracerCategory;
    }

}
