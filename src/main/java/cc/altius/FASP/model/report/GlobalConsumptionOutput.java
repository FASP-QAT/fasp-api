/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.framework.JsonDateDeserializer;
import cc.altius.FASP.framework.JsonDateSerializer;
import cc.altius.FASP.model.SimpleCodeObject;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author akil
 */
public class GlobalConsumptionOutput implements Serializable {

    SimpleCodeObject realmCountry;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    Date consumptionDate;
    Double planningUnitQty;
    Double forecastingUnitQty;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("MMM-yy");

    public SimpleCodeObject getRealmCountry() {
        return realmCountry;
    }

    public void setRealmCountry(SimpleCodeObject realmCountry) {
        this.realmCountry = realmCountry;
    }

    public Date getConsumptionDate() {
        return consumptionDate;
    }

    public void setConsumptionDate(Date consumptionDate) {
        this.consumptionDate = consumptionDate;
    }

    public Double getPlanningUnitQty() {
        return planningUnitQty;
    }

    public void setPlanningUnitQty(Double planningUnitQty) {
        this.planningUnitQty = planningUnitQty;
    }

    public Double getForecastingUnitQty() {
        return forecastingUnitQty;
    }

    public void setForecastingUnitQty(Double forecastingUnitQty) {
        this.forecastingUnitQty = forecastingUnitQty;
    }

    public String getConsumptionDateString() {
        return sdf.format(this.consumptionDate);
    }
}
