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
 * @author altius
 */
public class Language extends BaseModel implements Serializable {

    @JsonView(Views.UserListView.class)
    private Integer languageId;
    @JsonView(Views.UserListView.class)
    private Label label;
    @JsonView(Views.UserListView.class)
    private String languageCode;
    @JsonView(Views.UserListView.class)
    private String countryCode;

    public Language() {
    }

    public Language(Integer languageId) {
        this.languageId = languageId;
    }

    public Language(Integer languageId, Label label, String languageCode, String countryCode) {
        this.languageId = languageId;
        this.label = label;
        this.languageCode = languageCode;
        this.countryCode = countryCode;
    }

    public Integer getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Integer languageId) {
        this.languageId = languageId;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @Override
    public String toString() {
        return "Language{" + "languageId=" + languageId + ", label=" + label + ", languageCode=" + languageCode + ", countryCode=" + countryCode + '}';
    }

}
