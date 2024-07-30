/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.DTO;

import cc.altius.FASP.model.PlanningUnit;
import cc.altius.FASP.model.Views;
import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author akil
 */
public class SupplyPlanExportPuDTO implements Serializable {

    @JsonView(Views.ExportApiView.class)
    private PlanningUnit planningUnit;
    @JsonView(Views.ExportApiView.class)
    private List<SupplyPlanExportDataDTO> supplyPlanData;

    public SupplyPlanExportPuDTO() {
        this.supplyPlanData = new LinkedList<>();
    }

    public PlanningUnit getPlanningUnit() {
        return planningUnit;
    }

    public void setPlanningUnit(PlanningUnit planningUnit) {
        this.planningUnit = planningUnit;
    }

    public List<SupplyPlanExportDataDTO> getSupplyPlanData() {
        return supplyPlanData;
    }

    public void setSupplyPlanData(List<SupplyPlanExportDataDTO> supplyPlanData) {
        this.supplyPlanData = supplyPlanData;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.planningUnit);
        hash = 53 * hash + Objects.hashCode(this.supplyPlanData);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SupplyPlanExportPuDTO other = (SupplyPlanExportPuDTO) obj;
        if (this.planningUnit.getPlanningUnitId() == 0 || other.getPlanningUnit().getPlanningUnitId() == 0) {
            return false;
        }
        return Objects.equals(this.planningUnit.getPlanningUnitId(), other.planningUnit.getPlanningUnitId());
    }

    @Override
    public String toString() {
        return "" + this.planningUnit.getPlanningUnitId();
    }

}
