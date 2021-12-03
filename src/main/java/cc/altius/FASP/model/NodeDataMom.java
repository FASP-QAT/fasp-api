/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import cc.altius.FASP.framework.JsonDateDeserializer;
import cc.altius.FASP.framework.JsonDateSerializer;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author akil
 */
public class NodeDataMom implements Serializable {

    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private int nodeDataMomId;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date month;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private Double startValue;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private Double endValue;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private Double calculatedValue;

    public NodeDataMom() {
    }

    public NodeDataMom(int nodeDataMomId) {
        this.nodeDataMomId = nodeDataMomId;
    }

    public int getNodeDataMomId() {
        return nodeDataMomId;
    }

    public void setNodeDataMomId(int nodeDataMomId) {
        this.nodeDataMomId = nodeDataMomId;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public Double getStartValue() {
        return startValue;
    }

    public void setStartValue(Double startValue) {
        this.startValue = startValue;
    }

    public Double getEndValue() {
        return endValue;
    }

    public void setEndValue(Double endValue) {
        this.endValue = endValue;
    }

    public Double getCalculatedValue() {
        return calculatedValue;
    }

    public void setCalculatedValue(Double calculatedValue) {
        this.calculatedValue = calculatedValue;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.nodeDataMomId;
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
        final NodeDataMom other = (NodeDataMom) obj;
        if (this.nodeDataMomId != other.nodeDataMomId) {
            return false;
        }
        return true;
    }

}
