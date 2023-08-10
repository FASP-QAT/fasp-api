/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import com.fasterxml.jackson.annotation.JsonView;

/**
 *
 * @author palash
 */
public class Currency extends BaseModel {

    @JsonView({Views.InternalView.class, Views.ReportView.class})
    private int currencyId;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class, Views.ReportView.class})
    private String currencyCode;
    @JsonView({Views.InternalView.class, Views.ReportView.class})
    private Label label;
    @JsonView({Views.InternalView.class, Views.ReportView.class})
    private double conversionRateToUsd;
    @JsonView(Views.InternalView.class)
    private boolean isSync;

    public Currency() {
    }

    public Currency(int currencyId, String currencyCode, Label label, double conversionRateToUsd) {
        this.currencyId = currencyId;
        this.currencyCode = currencyCode;
        this.label = label;
        this.conversionRateToUsd = conversionRateToUsd;
        this.isSync = true;
    }

    public int getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public double getConversionRateToUsd() {
        return conversionRateToUsd;
    }

    public void setConversionRateToUsd(double conversionRateToUsd) {
        this.conversionRateToUsd = conversionRateToUsd;
    }

    public boolean isIsSync() {
        return isSync;
    }

    public void setIsSync(boolean isSync) {
        this.isSync = isSync;
    }

    @Override
    public String toString() {
        return "Currency{" + "currencyId=" + currencyId + ", currencyCode=" + currencyCode + ", label=" + label + ", conversionRateToUsd=" + conversionRateToUsd + '}';
    }

}
