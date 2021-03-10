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
public class StaticLabelLanguagesDTO implements Serializable {

    private int staticLabelLanguageId;
    private String labelText;
    private int languageId;
    private int staticLabelId;

    public int getStaticLabelId() {
        return staticLabelId;
    }

    public void setStaticLabelId(int staticLabelId) {
        this.staticLabelId = staticLabelId;
    }

    public int getStaticLabelLanguageId() {
        return staticLabelLanguageId;
    }

    public void setStaticLabelLanguageId(int staticLabelLanguageId) {
        this.staticLabelLanguageId = staticLabelLanguageId;
    }

    public String getLabelText() {
        return labelText;
    }

    public void setLabelText(String labelText) {
        this.labelText = labelText;
    }

    public int getLanguageId() {
        return languageId;
    }

    public void setLanguageId(int languageId) {
        this.languageId = languageId;
    }

    @Override
    public String toString() {
        return "StaticLabelLanguagesDTO{" + "staticLabelLanguageId=" + staticLabelLanguageId + ", labelText=" + labelText + ", languageId=" + languageId + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.staticLabelLanguageId;
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
        final StaticLabelLanguagesDTO other = (StaticLabelLanguagesDTO) obj;
        if (this.staticLabelLanguageId != other.staticLabelLanguageId) {
            return false;
        }
        return true;
    }

}
