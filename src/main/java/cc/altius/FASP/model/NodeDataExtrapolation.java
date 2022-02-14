/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author akil
 */
public class NodeDataExtrapolation implements Serializable {

    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private int nodeDataExtapolationId;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private SimpleObject extrapolationMethod;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private Map<String, Object> jsonProperties;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    List<ExtrapolationData> extrapolationDataList;
    private final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    public NodeDataExtrapolation() {
    }

    public NodeDataExtrapolation(int nodeDataExtapolationId) {
        this.nodeDataExtapolationId = nodeDataExtapolationId;
    }

    public int getNodeDataExtapolationId() {
        return nodeDataExtapolationId;
    }

    public void setNodeDataExtapolationId(int nodeDataExtapolationId) {
        this.nodeDataExtapolationId = nodeDataExtapolationId;
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

    public String getJsonPropertiesString() {
        if (this.jsonProperties != null) {
            return gson.toJson(this.jsonProperties);
        } else {
            return null;
        }
    }

    public void setJsonProperties(String json) {
        this.jsonProperties = null;
        if (json != null) {
            this.jsonProperties = gson.fromJson(json, new TypeToken<HashMap<String, Object>>() {
            }.getType());
        }
    }

    public List<ExtrapolationData> getExtrapolationDataList() {
        return extrapolationDataList;
    }

    public void setExtrapolationDataList(List<ExtrapolationData> extrapolationDataList) {
        this.extrapolationDataList = extrapolationDataList;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.nodeDataExtapolationId;
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
        final NodeDataExtrapolation other = (NodeDataExtrapolation) obj;
        if (this.nodeDataExtapolationId != other.nodeDataExtapolationId) {
            return false;
        }
        return true;
    }

}
