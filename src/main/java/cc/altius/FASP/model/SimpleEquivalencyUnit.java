/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model;

import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author akil
 */
public class SimpleEquivalencyUnit extends SimpleObject implements Serializable {

    @JsonView(Views.ReportView.class)
    private List<String> forecastingUnitIds;

    public SimpleEquivalencyUnit() {
    }

    public SimpleEquivalencyUnit(Integer id, Label label) {
        super(id, label);
    }

    public List<String> getForecastingUnitIds() {
        return forecastingUnitIds;
    }

    public void setForecastingUnitIds(List<String> forecastingUnitIds) {
        this.forecastingUnitIds = forecastingUnitIds;
    }

}
