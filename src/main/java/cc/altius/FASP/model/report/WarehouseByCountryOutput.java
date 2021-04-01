/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.SimpleRealmCountryObject;
import cc.altius.FASP.model.Views;
import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;

/**
 *
 * @author akil
 */
public class WarehouseByCountryOutput implements Serializable {

    @JsonView(Views.ReportView.class)
    private SimpleRealmCountryObject realmCountry;
    @JsonView(Views.ReportView.class)
    private SimpleObject region;
    @JsonView(Views.ReportView.class)
    private String gln;
    @JsonView(Views.ReportView.class)
    private Double capacityCbm;
    @JsonView(Views.ReportView.class)
    private boolean active;

    public SimpleRealmCountryObject getRealmCountry() {
        return realmCountry;
    }

    public void setRealmCountry(SimpleRealmCountryObject realmCountry) {
        this.realmCountry = realmCountry;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
    
}
