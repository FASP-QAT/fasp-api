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
public class EmailCategory implements Serializable {

    private int categoryId;
    private String categoryCode;
    private Label label;
    private boolean allowOptOut;
    private boolean defaultOptIn;

    public EmailCategory() {
    }

    public EmailCategory(int categoryId, String categoryCode, Label label, boolean allowOptOut, boolean defaultOptIn) {
        this.categoryId = categoryId;
        this.categoryCode = categoryCode;
        this.label = label;
        this.allowOptOut = allowOptOut;
        this.defaultOptIn = defaultOptIn;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public String getCategoryName() {
        return label != null ? label.getLabel_en() : "";
    }

    public boolean isAllowOptOut() {
        return allowOptOut;
    }

    public void setAllowOptOut(boolean allowOptOut) {
        this.allowOptOut = allowOptOut;
    }

    public boolean isDefaultOptIn() {
        return defaultOptIn;
    }

    public void setDefaultOptIn(boolean defaultOptIn) {
        this.defaultOptIn = defaultOptIn;
    }

}
