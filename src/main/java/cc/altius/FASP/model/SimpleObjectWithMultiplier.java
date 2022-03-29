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
public class SimpleObjectWithMultiplier extends SimpleObject implements Serializable {

    @JsonView({Views.InternalView.class, Views.ReportView.class})
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
