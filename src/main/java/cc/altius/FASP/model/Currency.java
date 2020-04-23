/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

/**
 *
 * @author palash
 */
public class Currency extends BaseModel {
    
    private int currencyId;
    private String currencyCode;
    private String currencySymbol;
    private Label label;
    private double conversionRateToUsd;
    private boolean isSync;

    public Currency() {
    }

    public Currency(int currencyId, String currencyCode, String currencySymbol, Label label, double conversionRateToUsd) {
        this.currencyId = currencyId;
        this.currencyCode = currencyCode;
        this.currencySymbol = currencySymbol;
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

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
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
        return "Currency{" + "currencyId=" + currencyId + ", currencyCode=" + currencyCode + ", currencySymbol=" + currencySymbol + ", label=" + label + ", conversionRateToUsd=" + conversionRateToUsd + '}';
    }
 
}
