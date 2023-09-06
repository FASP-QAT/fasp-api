/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model;

import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;

/**
 *
 * @author akil
 */
public class SimpleObjectWithMultiplierAndActive extends SimpleObjectWithMultiplier implements Serializable {

    @JsonView({Views.InternalView.class, Views.ReportView.class})
    private boolean active;

    public SimpleObjectWithMultiplierAndActive() {
    }

    public SimpleObjectWithMultiplierAndActive(Integer id, Label label, double multiplier, boolean active) {
        super(id, label, multiplier);
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
