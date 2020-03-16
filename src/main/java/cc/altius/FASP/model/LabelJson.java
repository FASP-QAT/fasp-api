/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author akil
 */
public class LabelJson implements Serializable {

    private String labelCode;
    private String labelText;

    public LabelJson() {
    }

    public LabelJson(String labelCode, String labelText) {
        this.labelCode = labelCode;
        this.labelText = labelText;
    }

    
    public String getLabelCode() {
        return labelCode;
    }

    public void setLabelCode(String labelCode) {
        this.labelCode = labelCode;
    }

    public String getLabelText() {
        return labelText;
    }

    public void setLabelText(String labelText) {
        this.labelText = labelText;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.labelCode);
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
        final LabelJson other = (LabelJson) obj;
        if (!Objects.equals(this.labelCode, other.labelCode)) {
            return false;
        }
        return true;
    }

}
