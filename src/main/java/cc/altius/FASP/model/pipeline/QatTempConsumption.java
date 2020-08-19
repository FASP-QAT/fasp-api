/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.pipeline;

/**
 *
 * @author altius
 */
public class QatTempConsumption {

    private int consumptionId;
    private String regionId;
    private String planningUnitId;
//    @JsonDeserialize(using = JsonDateDeserializer.class)
//    @JsonSerialize(using = JsonDateSerializer.class)
    private String consumptionDate;
    private boolean actualFlag;
    private double consumptionQty;
    private int dayOfStockOut;
    private String dataSourceId;
    private int consNumMonth;
    private String notes;
    private int realmCountryPlanningUnitId;
    private double multiplier;

    
    
    public QatTempConsumption() {
    }

    public QatTempConsumption(int consumptionId, String regionId, String planningUnitId, String consumptionDate, boolean actualFlag, double consumptionQty, int dayOfStockOut, String dataSourceId, String notes,int consNumMonth,int realmCountryPlanningUnitId,double  multiplier) {
        this.consumptionId = consumptionId;
        this.regionId = regionId;
        this.planningUnitId = planningUnitId;
        this.consumptionDate = consumptionDate;
        this.actualFlag = actualFlag;
        this.consumptionQty = consumptionQty;
        this.dayOfStockOut = dayOfStockOut;
        this.dataSourceId = dataSourceId;
        this.notes = notes;
        this.consNumMonth=consNumMonth;
        this.realmCountryPlanningUnitId=realmCountryPlanningUnitId;
        this.multiplier=multiplier;
    }

    public int getConsNumMonth() {
        return consNumMonth;
    }

    public void setConsNumMonth(int consNumMonth) {
        this.consNumMonth = consNumMonth;
    }

    
    public int getConsumptionId() {
        return consumptionId;
    }

    public void setConsumptionId(int consumptionId) {
        this.consumptionId = consumptionId;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getPlanningUnitId() {
        return planningUnitId;
    }

    public void setPlanningUnitId(String planningUnitId) {
        this.planningUnitId = planningUnitId;
    }

    public String getConsumptionDate() {
        return consumptionDate;
    }

    public void setConsumptionDate(String consumptionDate) {
        this.consumptionDate = consumptionDate;
    }

    public boolean isActualFlag() {
        return actualFlag;
    }

    public void setActualFlag(boolean actualFlag) {
        this.actualFlag = actualFlag;
    }

    public double getConsumptionQty() {
        return consumptionQty;
    }

    public void setConsumptionQty(double consumptionQty) {
        this.consumptionQty = consumptionQty;
    }

    public int getDayOfStockOut() {
        return dayOfStockOut;
    }

    public void setDayOfStockOut(int dayOfStockOut) {
        this.dayOfStockOut = dayOfStockOut;
    }

    public String getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(String dataSourceId) {
        this.dataSourceId = dataSourceId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
     public int getRealmCountryPlanningUnitId() {
        return realmCountryPlanningUnitId;
    }

    public void setRealmCountryPlanningUnitId(int realmCountryPlanningUnitId) {
        this.realmCountryPlanningUnitId = realmCountryPlanningUnitId;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(double multiplier) {
        this.multiplier = multiplier;
    }
    
}
