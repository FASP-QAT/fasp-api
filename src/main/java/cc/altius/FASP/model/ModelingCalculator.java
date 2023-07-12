/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model;

import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author akil
 */
public class ModelingCalculator implements Serializable {

    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private int nodeDataModelingCalculatorId;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private String firstMonthOfTarget;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private int yearsOfTarget;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    List<Integer> actualOrtargetValueList;

    public ModelingCalculator() {
        this.actualOrtargetValueList = new LinkedList<>();
    }

    public int getNodeDataModelingCalculatorId() {
        return nodeDataModelingCalculatorId;
    }

    public void setNodeDataModelingCalculatorId(int nodeDataModelingCalculatorId) {
        this.nodeDataModelingCalculatorId = nodeDataModelingCalculatorId;
    }

    public String getFirstMonthOfTarget() {
        return firstMonthOfTarget;
    }

    public void setFirstMonthOfTarget(String firstMonthOfTarget) {
        this.firstMonthOfTarget = firstMonthOfTarget;
    }

    public int getYearsOfTarget() {
        return yearsOfTarget;
    }

    public void setYearsOfTarget(int yearsOfTarget) {
        this.yearsOfTarget = yearsOfTarget;
    }

    public List<Integer> getActualOrtargetValueList() {
        return actualOrtargetValueList;
    }

    public void setActualOrtargetValueList(List<Integer> actualOrtargetValueList) {
        this.actualOrtargetValueList = actualOrtargetValueList;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.nodeDataModelingCalculatorId;
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
        final ModelingCalculator other = (ModelingCalculator) obj;
        return this.nodeDataModelingCalculatorId == other.nodeDataModelingCalculatorId;
    }

}
