/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model;

import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;

/**
 *
 * @author akil
 */
public class SimpleObjectWithFu extends SimpleObject implements Serializable {

    @JsonView(Views.ReportView.class)
    private int forecastingUnitId;

    public SimpleObjectWithFu() {
    }

    public SimpleObjectWithFu(Integer id, Label label, int forecastingUnitId) {
        super(id, label);
        this.forecastingUnitId = forecastingUnitId;
    }

    public int getForecastingUnitId() {
        return forecastingUnitId;
    }

    public void setForecastingUnitId(int forecastingUnitId) {
        this.forecastingUnitId = forecastingUnitId;
    }

}
