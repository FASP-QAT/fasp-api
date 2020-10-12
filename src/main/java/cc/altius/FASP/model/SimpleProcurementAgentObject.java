/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import com.fasterxml.jackson.annotation.JsonView;

/**
 *
 * @author akil
 */
public class SimpleProcurementAgentObject extends SimpleCodeObject {

    @JsonView(Views.InternalView.class)
    private String colorHtmlCode;

    public SimpleProcurementAgentObject(Integer id, Label label, String code, String colorHtmlCode) {
        super(id, label, code);
        this.colorHtmlCode = colorHtmlCode;
    }

    public SimpleProcurementAgentObject() {
    }

    public String getColorHtmlCode() {
        return colorHtmlCode;
    }

    public void setColorHtmlCode(String colorHtmlCode) {
        this.colorHtmlCode = colorHtmlCode;
    }

}
