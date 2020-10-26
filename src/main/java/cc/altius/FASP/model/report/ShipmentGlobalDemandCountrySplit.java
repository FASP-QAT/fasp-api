/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.Views;
import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import java.util.Map;

/**
 *
 * @author akil
 */
public class ShipmentGlobalDemandCountrySplit implements Serializable {

    @JsonView(Views.ReportView.class)
    private SimpleCodeObject country;
    @JsonView(Views.ReportView.class)
    private Map<String, Integer> amount;

    public SimpleCodeObject getCountry() {
        return country;
    }

    public void setCountry(SimpleCodeObject country) {
        this.country = country;
    }

    public Map<String, Integer> getAmount() {
        return amount;
    }

    public void setAmount(Map<String, Integer> amount) {
        this.amount = amount;
    }

}
