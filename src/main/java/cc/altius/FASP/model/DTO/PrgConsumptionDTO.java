/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author altius
 */
public class PrgConsumptionDTO implements Serializable {

    private int consumptionId;
    private PrgRegionDTO region;
    private PrgLogisticsUnitDTO logisticsUnit;
    private PrgPlanningUnitDTO planningUnit;
    private PrgUnitDTO unit;
    private double packSize;
    private double consumptionQty;
    private Date startDate;
    private Date stopDate;
    private int daysOfStockOut;
    private PrgDataSourceDTO dataSource;

    public PrgPlanningUnitDTO getPlanningUnit() {
        return planningUnit;
    }

    public void setPlanningUnit(PrgPlanningUnitDTO planningUnit) {
        this.planningUnit = planningUnit;
    }

    public int getConsumptionId() {
        return consumptionId;
    }

    public void setConsumptionId(int consumptionId) {
        this.consumptionId = consumptionId;
    }

    public PrgRegionDTO getRegion() {
        return region;
    }

    public void setRegion(PrgRegionDTO region) {
        this.region = region;
    }

    public PrgLogisticsUnitDTO getLogisticsUnit() {
        return logisticsUnit;
    }

    public void setLogisticsUnit(PrgLogisticsUnitDTO logisticsUnit) {
        this.logisticsUnit = logisticsUnit;
    }

    public PrgUnitDTO getUnit() {
        return unit;
    }

    public void setUnit(PrgUnitDTO unit) {
        this.unit = unit;
    }

    public double getPackSize() {
        return packSize;
    }

    public void setPackSize(double packSize) {
        this.packSize = packSize;
    }

    public double getConsumptionQty() {
        return consumptionQty;
    }

    public void setConsumptionQty(double consumptionQty) {
        this.consumptionQty = consumptionQty;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getStopDate() {
        return stopDate;
    }

    public void setStopDate(Date stopDate) {
        this.stopDate = stopDate;
    }

    public int getDaysOfStockOut() {
        return daysOfStockOut;
    }

    public void setDaysOfStockOut(int daysOfStockOut) {
        this.daysOfStockOut = daysOfStockOut;
    }

    public PrgDataSourceDTO getDataSource() {
        return dataSource;
    }

    public void setDataSource(PrgDataSourceDTO dataSource) {
        this.dataSource = dataSource;
    }

}
