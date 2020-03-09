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
public class PrgLabelDTO {

    private String labelEn;
    private String labelFr;
    private String labelPr;
    private String labelSp;

    public PrgLabelDTO() {
    }

    public PrgLabelDTO(String labelEn, String labelFr, String labelPr, String labelSp) {
        this.labelEn = labelEn;
        this.labelFr = labelFr;
        this.labelPr = labelPr;
        this.labelSp = labelSp;
    }

    public String getLabelEn() {
        return labelEn;
    }

    public void setLabelEn(String labelEn) {
        this.labelEn = labelEn;
    }

    public String getLabelFr() {
        return labelFr;
    }

    public void setLabelFr(String labelFr) {
        this.labelFr = labelFr;
    }

    public String getLabelPr() {
        return labelPr;
    }

    public void setLabelPr(String labelPr) {
        this.labelPr = labelPr;
    }

    public String getLabelSp() {
        return labelSp;
    }

    public void setLabelSp(String labelSp) {
        this.labelSp = labelSp;
    }

}
