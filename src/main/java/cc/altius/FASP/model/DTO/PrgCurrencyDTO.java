/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO;

/**
 *
 * @author altius
 */
public class PrgCurrencyDTO {

    private int currencyId;
    private String currencyCode;
    private String currencySymbol;
    private PrgLabelDTO label;
    private double conversionRateToUsd;

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

    public PrgLabelDTO getLabel() {
        return label;
    }

    public void setLabel(PrgLabelDTO label) {
        this.label = label;
    }

    public double getConversionRateToUsd() {
        return conversionRateToUsd;
    }

    public void setConversionRateToUsd(double conversionRateToUsd) {
        this.conversionRateToUsd = conversionRateToUsd;
    }

}
