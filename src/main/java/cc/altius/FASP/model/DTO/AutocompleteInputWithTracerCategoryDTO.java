/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.DTO;

import cc.altius.FASP.model.AutoCompleteInput;

/**
 *
 * @author akil
 */
public class AutocompleteInputWithTracerCategoryDTO extends AutoCompleteInput {

    private Integer tracerCategoryId;

    public Integer getTracerCategoryId() {
        return tracerCategoryId;
    }

    public void setTracerCategoryId(Integer tracerCategoryId) {
        this.tracerCategoryId = tracerCategoryId;
    }

}
