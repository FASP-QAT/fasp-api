/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO;

import cc.altius.FASP.model.Label;
import cc.altius.FASP.model.Views;
import com.fasterxml.jackson.annotation.JsonView;

/**
 *
 * @author altius
 */
public class DatabaseTranslationsDTO extends Label {

    @JsonView(Views.InternalView.class)
    private String labelFor;
    @JsonView(Views.InternalView.class)
    private Label relatedTo;

    public String getLabelFor() {
        return labelFor;
    }

    public void setLabelFor(String labelFor) {
        this.labelFor = labelFor;
    }

    public Label getRelatedTo() {
        return relatedTo;
    }

    public void setRelatedTo(Label relatedTo) {
        this.relatedTo = relatedTo;
    }

}
