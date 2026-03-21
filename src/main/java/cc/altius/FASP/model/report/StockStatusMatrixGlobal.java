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
import java.util.Objects;

/**
 *
 * @author akil
 */
public class StockStatusMatrixGlobal implements Serializable {

    @JsonView(Views.ReportView.class)
    private SimpleCodeObject programOrCountry;
    @JsonView(Views.ReportView.class)
    Map<String, AmcAndQty> dataMap;


    public StockStatusMatrixGlobal(SimpleCodeObject programOrCountry) {
        this.programOrCountry = programOrCountry;
    }

    public SimpleCodeObject getProgramOrCountry() {
        return programOrCountry;
    }

    public void setProgramOrCountry(SimpleCodeObject programOrCountry) {
        this.programOrCountry = programOrCountry;
    }

    public Map<String, AmcAndQty> getDataMap() {
        return dataMap;
    }

    public void setDataMap(Map<String, AmcAndQty> dataMap) {
        this.dataMap = dataMap;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.programOrCountry);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final StockStatusMatrixGlobal other = (StockStatusMatrixGlobal) obj;
        if (this.getProgramOrCountry().getId() == null || other.getProgramOrCountry().getId() == null) {
            return false;
        }
        return Objects.equals(this.programOrCountry.getId(), other.programOrCountry.getId());
    }

}
