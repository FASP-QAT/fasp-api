/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import java.io.Serializable;

/**
 *
 * @author akil
 */
public class Criticality extends SimpleObject implements Serializable {

    private String colorHtmlCode;

    public Criticality() {

    }

    public Criticality(Integer id, Label label, String colorHtmlCode) {
        super(id, label);
        this.colorHtmlCode = colorHtmlCode;
    }

    public String getColorHtmlCode() {
        return colorHtmlCode;
    }

    public void setColorHtmlCode(String colorHtmlCode) {
        this.colorHtmlCode = colorHtmlCode;
    }

}
