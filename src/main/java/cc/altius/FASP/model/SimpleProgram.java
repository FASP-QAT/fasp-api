/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model;

import com.fasterxml.jackson.annotation.JsonView;

/**
 *
 * @author akil
 */
public class SimpleProgram extends SimpleCodeObject {

    @JsonView({Views.DropDownView.class})
    private int currentVersionId;

    public SimpleProgram() {
        super();
    }

    public SimpleProgram(Integer id, Label label, String code, int currentVersionId) {
        super(id, label, code);
        this.currentVersionId = currentVersionId;
    }

    public int getCurrentVersionId() {
        return currentVersionId;
    }

    public void setCurrentVersionId(int currentVersionId) {
        this.currentVersionId = currentVersionId;
    }

    @Override
    public String toString() {
        return "SimpleProgram{" + "id=" + this.getId() + ", label=" + this.getLabel() + ", code=" + this.getCode() + ", currentVersionId=" + currentVersionId + '}';
    }

}
