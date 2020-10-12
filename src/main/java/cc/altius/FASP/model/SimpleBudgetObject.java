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
public class SimpleBudgetObject extends SimpleCodeObject implements Serializable {

    @JsonView({Views.ArtmisView.class, Views.InternalView.class})
    private Currency currency;

    public SimpleBudgetObject() {
    }

    public SimpleBudgetObject(Integer id, String code, Label label, Currency currency) {
        super(id, label, code);
        this.currency = currency;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

}
