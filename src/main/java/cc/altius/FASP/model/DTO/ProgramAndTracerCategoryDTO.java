/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.DTO;

import java.io.Serializable;

/**
 *
 * @author akil
 */
public class ProgramAndTracerCategoryDTO implements Serializable {

    private int programId;
    private Integer tracerCategoryId;

    public int getProgramId() {
        return programId;
    }

    public void setProgramId(int programId) {
        this.programId = programId;
    }

    public Integer getTracerCategoryId() {
        return tracerCategoryId;
    }

    public void setTracerCategoryId(Integer tracerCategoryId) {
        this.tracerCategoryId = tracerCategoryId;
    }

}
