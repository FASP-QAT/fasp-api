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

    private Integer forecastingUnitId;

    public Integer getForecastingUnitId() {
        return forecastingUnitId;
    }

    public void setForecastingUnitId(Integer forecastingUnitId) {
        this.forecastingUnitId = forecastingUnitId;
    }

}
