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

    private String label_en;
    private String label_fr;
    private String label_pr;
    private String label_sp;

    public PrgLabelDTO() {
    }

    public PrgLabelDTO(String label_en, String label_fr, String label_pr, String label_sp) {
        this.label_en = label_en;
        this.label_fr = label_fr;
        this.label_pr = label_pr;
        this.label_sp = label_sp;
    }

    public String getLabel_en() {
        return label_en;
    }

    public void setLabel_en(String label_en) {
        this.label_en = label_en;
    }

    public String getLabel_fr() {
        return label_fr;
    }

    public void setLabel_fr(String label_fr) {
        this.label_fr = label_fr;
    }

    public String getLabel_pr() {
        return label_pr;
    }

    public void setLabel_pr(String label_pr) {
        this.label_pr = label_pr;
    }

    public String getLabel_sp() {
        return label_sp;
    }

    public void setLabel_sp(String label_sp) {
        this.label_sp = label_sp;
    }

}
