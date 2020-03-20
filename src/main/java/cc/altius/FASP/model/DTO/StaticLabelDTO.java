/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO;

import cc.altius.FASP.model.Label;
import java.io.Serializable;

/**
 *
 * @author altius
 */
public class StaticLabelDTO extends Label implements Serializable {

    private String labelCode;

    public String getLabelCode() {
        return labelCode;
    }

    public void setLabelCode(String labelCode) {
        this.labelCode = labelCode;
    }

}
