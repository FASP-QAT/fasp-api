/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO;

import java.io.Serializable;

/**
 *
 * @author altius
 */
public class PrgCountryDTO implements Serializable {

    private int countryId;
    private PrgLabelDTO label;
    private PrgCurrencyDTO currency;
    private PrgLanguageDTO language;

    public PrgLabelDTO getLabel() {
        return label;
    }

    public void setLabel(PrgLabelDTO label) {
        this.label = label;
    }

    public PrgCurrencyDTO getCurrency() {
        return currency;
    }

    public void setCurrency(PrgCurrencyDTO currency) {
        this.currency = currency;
    }

    public PrgLanguageDTO getLanguage() {
        return language;
    }

    public void setLanguage(PrgLanguageDTO language) {
        this.language = language;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

}
