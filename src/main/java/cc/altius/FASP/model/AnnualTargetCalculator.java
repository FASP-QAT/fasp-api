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
public class AnnualTargetCalculator extends BaseModel implements Serializable {

    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private int nodeDataAnnualTargetCalculatorId;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private String firstMonthOfTarget;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private int yearsOfTarget;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    List<Integer> actualOrTargetValueList;

    public AnnualTargetCalculator() {
        this.actualOrTargetValueList = new LinkedList<>();
    }

    public int getNodeDataAnnualTargetCalculatorId() {
        return nodeDataAnnualTargetCalculatorId;
    }

    public void setNodeDataAnnualTargetCalculatorId(int nodeDataAnnualTargetCalculatorId) {
        this.nodeDataAnnualTargetCalculatorId = nodeDataAnnualTargetCalculatorId;
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

    public List<Integer> getActualOrTargetValueList() {
        return actualOrTargetValueList;
    }

    public void setActualOrTargetValueList(List<Integer> actualOrTargetValueList) {
        this.actualOrTargetValueList = actualOrTargetValueList;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.nodeDataAnnualTargetCalculatorId;
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
        final AnnualTargetCalculator other = (AnnualTargetCalculator) obj;
        return this.nodeDataAnnualTargetCalculatorId == other.nodeDataAnnualTargetCalculatorId;
    }

}
