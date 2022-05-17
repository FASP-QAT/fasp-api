/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import java.io.Serializable;

/**
 *
 * @author akil
 */
public class ForecastMethod extends BaseModel implements Serializable {

    private int forecastMethodId;
    private int forecastMethodTypeId;
    private Label label;

    public ForecastMethod() {

    }

    public ForecastMethod(int forecastMethodId, Label label, int forecastMethodTypeId) {
        this.forecastMethodId = forecastMethodId;
        this.forecastMethodTypeId = forecastMethodTypeId;
        this.label = label;
    }

    public int getForecastMethodId() {
        return forecastMethodId;
    }

    public void setForecastMethodId(int forecastMethodId) {
        this.forecastMethodId = forecastMethodId;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public int getForecastMethodTypeId() {
        return forecastMethodTypeId;
    }

    public void setForecastMethodTypeId(int forecastMethodTypeId) {
        this.forecastMethodTypeId = forecastMethodTypeId;
    }

}
