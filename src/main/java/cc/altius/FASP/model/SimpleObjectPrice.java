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
public class SimpleObjectPrice extends SimpleCodeObject implements Serializable {

    @JsonView({Views.InternalView.class, Views.ReportView.class})
    private Double price;

    public SimpleObjectPrice() {
    }

    public SimpleObjectPrice(Integer id, Label label, String code, Double price) {
        super(id, label, code);
        this.price = price;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

}
