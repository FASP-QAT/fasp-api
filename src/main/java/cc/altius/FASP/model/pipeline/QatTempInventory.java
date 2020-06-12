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
public class QatTempInventory {

    private String planningUnitId;
    private String dataSourceId;
    private String regionId;
    private String inventoryDate;
    private int manualAdjustment;
    private String notes;
    private boolean active;
    private int realmCountryPlanningUnitId;
    private double multiplier;

    public QatTempInventory() {

    }

    public QatTempInventory(String planningUnitId, String dataSourceId, String regionId, String inventoryDate, int manualAdjustment, String notes, boolean active,int realmCountryPlanningUnitId,double  multiplier) {
        this.planningUnitId = planningUnitId;
        this.dataSourceId = dataSourceId;
        this.regionId = regionId;
        this.inventoryDate = inventoryDate;
        this.manualAdjustment = manualAdjustment;
        this.notes = notes;
        this.active = active;
        this.realmCountryPlanningUnitId=realmCountryPlanningUnitId;
        this.multiplier=multiplier;
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
    
    

    public String getPlanningUnitId() {
        return planningUnitId;
    }

    public void setPlanningUnitId(String planningUnitId) {
        this.planningUnitId = planningUnitId;
    }

    public String getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(String dataSourceId) {
        this.dataSourceId = dataSourceId;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getInventoryDate() {
        return inventoryDate;
    }

    public void setInventoryDate(String inventoryDate) {
        this.inventoryDate = inventoryDate;
    }

    public int getManualAdjustment() {
        return manualAdjustment;
    }

    public void setManualAdjustment(int manualAdjustment) {
        this.manualAdjustment = manualAdjustment;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    
}
