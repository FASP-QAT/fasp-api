/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO;

import java.io.Serializable;

/**
 *
 * @author akil
 */
public class MultipleProgramAndTracerCategoryDTO implements Serializable {

    private String[] tracerCategoryIds;
    private String[] programIds;

    public String[] getTracerCategoryIds() {
        return tracerCategoryIds;
    }

    public void setTracerCategoryIds(String[] tracerCategoryIds) {
        this.tracerCategoryIds = tracerCategoryIds;
    }

    public String getTracerCategoryIdsString() {
        if (this.tracerCategoryIds == null) {
            return "";
        } else {
            return String.join(",", this.tracerCategoryIds);
        }
    }

    public String[] getProgramIds() {
        return programIds;
    }

    public void setProgramIds(String[] programIds) {
        this.programIds = programIds;
    }

    public String getProgramIdsString() {
        if (this.programIds == null) {
            return "";
        } else {
            return String.join(",", this.programIds);
        }
    }

}
