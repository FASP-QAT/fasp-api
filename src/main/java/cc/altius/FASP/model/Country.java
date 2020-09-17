/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import java.io.Serializable;

/**
 *
 * @author altius
 */
public class Country extends BaseModel implements Serializable {

    private int countryId;
    private String countryCode;
    private String countryCode2;
    private Label label;
    private SimpleObject currency;

    public Country() {
    }

    public Country(int countryId, Label label) {
        this.countryId = countryId;
        this.label = label;
    }

    public Country(int countryId, String countryCode, Label label) {
        this.countryId = countryId;
        this.countryCode = countryCode;
        this.label = label;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryCode2() {
        return countryCode2;
    }

    public void setCountryCode2(String countryCode2) {
        this.countryCode2 = countryCode2;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public SimpleObject getCurrency() {
        return currency;
    }

    public void setCurrency(SimpleObject currency) {
        this.currency = currency;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    @Override
    public String toString() {
        return "Country{" + "countryId=" + countryId + ", countryCode=" + countryCode + ", label=" + label + ", currency=" + currency + '}';
    }
}
