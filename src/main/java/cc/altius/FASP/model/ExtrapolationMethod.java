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
public class ExtrapolationMethod extends SimpleBaseModel implements Serializable {

    @JsonView(Views.IgnoreView.class)
    private int sortOrder;

    public ExtrapolationMethod() {
    }

    public ExtrapolationMethod(Integer id, Label label, int sortOrder) {
        super(id, label);
        this.sortOrder = sortOrder;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

}
