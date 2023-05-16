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
public class PlanningUnitAndTracerCategoryDTO implements Serializable {

    private Integer planningUnitId;
    private Integer tracerCategoryId;

    public PlanningUnitAndTracerCategoryDTO() {
    }

    public PlanningUnitAndTracerCategoryDTO(Integer planningUnitId, Integer tracerCategoryId) {
        this.planningUnitId = planningUnitId;
        this.tracerCategoryId = tracerCategoryId;
    }

    public Integer getPlanningUnitId() {
        return planningUnitId;
    }

    public void setPlanningUnitId(Integer planningUnitId) {
        this.planningUnitId = planningUnitId;
    }

    public Integer getTracerCategoryId() {
        return tracerCategoryId;
    }

    public void setTracerCategoryId(Integer tracerCategoryId) {
        this.tracerCategoryId = tracerCategoryId;
    }

}
