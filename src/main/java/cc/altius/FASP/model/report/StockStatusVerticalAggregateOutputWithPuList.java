/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.Views;
import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author akil
 */
public class StockStatusVerticalAggregateOutputWithPuList implements Serializable {

    @JsonView(Views.ReportView.class)
    private List<StockStatusVerticalAggregateOutput> stockStatusVerticalAggregateList;
    @JsonView(Views.ReportView.class)
    private List<ProgramAndPlanningUnit> programPlanningUnitList;

    public List<StockStatusVerticalAggregateOutput> getStockStatusVerticalAggregate() {
        return stockStatusVerticalAggregateList;
    }

    public void setStockStatusVerticalAggregate(List<StockStatusVerticalAggregateOutput> stockStatusVerticalAggregate) {
        this.stockStatusVerticalAggregateList = stockStatusVerticalAggregate;
    }

    public List<ProgramAndPlanningUnit> getProgramPlanningUnitList() {
        return programPlanningUnitList;
    }

    public void setProgramPlanningUnitList(List<ProgramAndPlanningUnit> programPlanningUnitList) {
        this.programPlanningUnitList = programPlanningUnitList;
    }

}
