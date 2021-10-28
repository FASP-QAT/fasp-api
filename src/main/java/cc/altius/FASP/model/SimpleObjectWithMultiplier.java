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
public class SimpleObjectWithMultiplier extends SimpleObject implements Serializable {

    private double multiplier;

    public SimpleObjectWithMultiplier() {
    }

    public SimpleObjectWithMultiplier(Integer id, Label label, double multiplier) {
        super(id, label);
        this.multiplier = multiplier;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(double multiplier) {
        this.multiplier = multiplier;
    }

}
