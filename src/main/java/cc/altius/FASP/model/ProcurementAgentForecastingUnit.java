/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model;

import java.io.Serializable;

/**
 *
 * @author akil
 */
public class ProcurementAgentForecastingUnit extends BaseModel implements Serializable {

    private int procurementAgentForecastingUnitId;
    private SimpleCodeObject procurementAgent;
    private SimpleObject forecastingUnit;
    private String skuCode;

    public ProcurementAgentForecastingUnit() {
    }

    public ProcurementAgentForecastingUnit(int procurementAgentForecastingUnitId, SimpleCodeObject procurementAgent, SimpleObject forecastingUnit) {
        this.procurementAgentForecastingUnitId = procurementAgentForecastingUnitId;
        this.procurementAgent = procurementAgent;
        this.forecastingUnit = forecastingUnit;
    }

    public int getProcurementAgentForecastingUnitId() {
        return procurementAgentForecastingUnitId;
    }

    public void setProcurementAgentForecastingUnitId(int procurementAgentForecastingUnitId) {
        this.procurementAgentForecastingUnitId = procurementAgentForecastingUnitId;
    }

    public SimpleCodeObject getProcurementAgent() {
        return procurementAgent;
    }

    public void setProcurementAgent(SimpleCodeObject procurementAgent) {
        this.procurementAgent = procurementAgent;
    }

    public SimpleObject getForecastingUnit() {
        return forecastingUnit;
    }

    public void setForecastingUnit(SimpleObject forecastingUnit) {
        this.forecastingUnit = forecastingUnit;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

}
