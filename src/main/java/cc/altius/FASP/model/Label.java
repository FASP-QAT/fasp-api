/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

/**
 *
 * @author altius
 */
public class Label {

    private Integer labelId;
    private String engLabel;
    private String spaLabel;
    private String freLabel;
    private String porLabel;

    public Label(Integer labelId, String engLabel, String spaLabel, String freLabel, String porLabel) {
        this.labelId = labelId;
        this.engLabel = engLabel;
        this.spaLabel = spaLabel;
        this.freLabel = freLabel;
        this.porLabel = porLabel;
    }

    public Label(Integer labelId) {
        this.labelId = labelId;
    }

    public Label() {
    }

    public Integer getLabelId() {
        return labelId;
    }

    public void setLabelId(Integer labelId) {
        this.labelId = labelId;
    }

    public String getEngLabel() {
        return engLabel;
    }

    public void setEngLabel(String engLabel) {
        this.engLabel = engLabel;
    }

    public String getSpaLabel() {
        return spaLabel;
    }

    public void setSpaLabel(String spaLabel) {
        this.spaLabel = spaLabel;
    }

    public String getFreLabel() {
        return freLabel;
    }

    public void setFreLabel(String freLabel) {
        this.freLabel = freLabel;
    }

    public String getPorLabel() {
        return porLabel;
    }

    public void setPorLabel(String porLabel) {
        this.porLabel = porLabel;
    }

    @Override
    public String toString() {
        return "Label{" + "labelId=" + labelId + ", engLabel=" + engLabel + ", spaLabel=" + spaLabel + ", freLabel=" + freLabel + ", porLabel=" + porLabel + '}';
    }

    public String getLabel() {
        return this.engLabel;
    }

}
