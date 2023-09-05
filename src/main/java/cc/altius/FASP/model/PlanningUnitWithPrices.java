/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model;

import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author akil
 */
public class PlanningUnitWithPrices extends PlanningUnit implements Serializable {

    @JsonView(Views.ReportView.class)
    private List<SimpleObjectPrice> procurementAgentPriceList;

    public PlanningUnitWithPrices() {
        super();
        this.procurementAgentPriceList = new LinkedList<>();
    }

    public List<SimpleObjectPrice> getProcurementAgentPriceList() {
        return procurementAgentPriceList;
    }

    public void setProcurementAgentPriceList(List<SimpleObjectPrice> procurementAgentPriceList) {
        this.procurementAgentPriceList = procurementAgentPriceList;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.procurementAgentPriceList);
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
        final PlanningUnit other = (PlanningUnit) obj;
        if (this.getPlanningUnitId() != other.getPlanningUnitId()) {
            return false;
        }
        return true;
    }

}
