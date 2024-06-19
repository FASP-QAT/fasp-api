/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.DTO;

/**
 *
 * @author akil
 */
public class ProductCategoryTracerCategoryAndForecastingUnitDTO extends ProductCategoryAndTracerCategoryDTO {

    private String forecastingUnitId;

    public String getForecastingUnitId() {
        return forecastingUnitId;
    }

    public void setForecastingUnitId(String forecastingUnitId) {
        this.forecastingUnitId = forecastingUnitId;
    }

}
