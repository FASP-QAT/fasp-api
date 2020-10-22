/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.SimpleRealmCountryObject;
import cc.altius.FASP.model.Views;
import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author akil
 */
public class WarehouseCapacityOutput implements Serializable {

    @JsonView(Views.ReportView.class)
    private SimpleRealmCountryObject realmCountry;
    @JsonView(Views.ReportView.class)
    private List<SimpleCodeObject> programList;
    @JsonView(Views.ReportView.class)
    private SimpleObject region;
    @JsonView(Views.ReportView.class)
    private String gln;
    @JsonView(Views.ReportView.class)
    private Double capacityCbm;

    public WarehouseCapacityOutput() {
        this.programList = new LinkedList<>();
    }

    public SimpleRealmCountryObject getRealmCountry() {
        return realmCountry;
    }

    public void setRealmCountry(SimpleRealmCountryObject realmCountry) {
        this.realmCountry = realmCountry;
    }

    public List<SimpleCodeObject> getProgramList() {
        return programList;
    }

    public void setProgramList(List<SimpleCodeObject> programList) {
        this.programList = programList;
    }

    public SimpleObject getRegion() {
        return region;
    }

    public void setRegion(SimpleObject region) {
        this.region = region;
    }

    public String getGln() {
        return gln;
    }

    public void setGln(String gln) {
        this.gln = gln;
    }

    public Double getCapacityCbm() {
        return capacityCbm;
    }

    public void setCapacityCbm(Double capacityCbm) {
        this.capacityCbm = capacityCbm;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + Objects.hashCode(this.realmCountry);
        hash = 31 * hash + Objects.hashCode(this.region);
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
        final WarehouseCapacityOutput other = (WarehouseCapacityOutput) obj;
        if (!Objects.equals(this.realmCountry, other.realmCountry)) {
            return false;
        }
        if (!Objects.equals(this.region, other.region)) {
            return false;
        }
        return true;
    }

}
