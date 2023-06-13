/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import cc.altius.FASP.framework.JsonDateTimeDeserializer;
import cc.altius.FASP.framework.JsonDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author akil
 */
public class ForecastConsumptionExtrapolation implements Serializable {

    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private int consumptionExtrapolationId;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private SimpleObject planningUnit;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private SimpleObject region;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private SimpleObject extrapolationMethod;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private Map<String, Object> jsonProperties;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private BasicUser createdBy;
    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
    @JsonSerialize(using = JsonDateTimeSerializer.class)
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private Date createdDate;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private List<ExtrapolationData> extrapolationDataList;
    private final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    public ForecastConsumptionExtrapolation() {
        this.extrapolationDataList = new LinkedList<>();
    }

    public ForecastConsumptionExtrapolation(int consumptionExtrapolationId) {
        this.consumptionExtrapolationId = consumptionExtrapolationId;
        this.extrapolationDataList = new LinkedList<>();
    }

    public int getConsumptionExtrapolationId() {
        return consumptionExtrapolationId;
    }

    public void setConsumptionExtrapolationId(int consumptionExtrapolationId) {
        this.consumptionExtrapolationId = consumptionExtrapolationId;
    }

    public SimpleObject getPlanningUnit() {
        return planningUnit;
    }

    public void setPlanningUnit(SimpleObject planningUnit) {
        this.planningUnit = planningUnit;
    }

    public SimpleObject getRegion() {
        return region;
    }

    public void setRegion(SimpleObject region) {
        this.region = region;
    }

    public SimpleObject getExtrapolationMethod() {
        return extrapolationMethod;
    }

    public void setExtrapolationMethod(SimpleObject extrapolationMethod) {
        this.extrapolationMethod = extrapolationMethod;
    }

    public Map<String, Object> getJsonProperties() {
        return jsonProperties;
    }

    public void setJsonProperties(Map<String, Object> jsonProperties) {
        this.jsonProperties = jsonProperties;
    }

    public void setJsonProperties(String json) {
        this.jsonProperties = null;
        if (json != null) {
            this.jsonProperties = gson.fromJson(json, new TypeToken<HashMap<String, Object>>() {
            }.getType());
        }
    }

    public BasicUser getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(BasicUser createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public List<ExtrapolationData> getExtrapolationDataList() {
        return extrapolationDataList;
    }

    public void setExtrapolationDataList(List<ExtrapolationData> extrapolationDataList) {
        this.extrapolationDataList = extrapolationDataList;
    }

    public String getJsonPropertiesString() {
        if (this.jsonProperties != null) {
            return gson.toJson(this.jsonProperties);
        } else {
            return null;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + this.consumptionExtrapolationId;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ForecastConsumptionExtrapolation other = (ForecastConsumptionExtrapolation) obj;
        if (this.consumptionExtrapolationId != other.consumptionExtrapolationId) {
            return false;
        }
        return true;
    }

}
