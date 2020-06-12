/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import cc.altius.FASP.framework.JsonDateDeserializer;
import cc.altius.FASP.framework.JsonDateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author akil
 */
public class SupplyPlanDate implements Serializable {

    private int planningUnitId;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date transDate;
    private int unallocatedConsumption;
    private List<SupplyPlanBatchInfo> batchList;

    private final SimpleDateFormat sdf = new SimpleDateFormat("MMM yy");

    public SupplyPlanDate() {
    }

    public SupplyPlanDate(int planningUnitId, Date transDate, List<SupplyPlanBatchInfo> batchList) {
        this.planningUnitId = planningUnitId;
        this.transDate = transDate;
        this.batchList = batchList;
        batchList = new LinkedList<>();
    }

    public SupplyPlanDate(int planningUnitId, Date transDate) {
        this.planningUnitId = planningUnitId;
        this.transDate = transDate;
        batchList = new LinkedList<>();
    }

    public int getPlanningUnitId() {
        return planningUnitId;
    }

    public void setPlanningUnitId(int planningUnitId) {
        this.planningUnitId = planningUnitId;
    }

    public Date getTransDate() {
        return transDate;
    }

    public void setTransDate(Date transDate) {
        this.transDate = transDate;
    }

    public List<SupplyPlanBatchInfo> getBatchList() {
        return batchList;
    }

    public void setBatchList(List<SupplyPlanBatchInfo> batchList) {
        this.batchList = batchList;
    }

    public String getTransDateStr() {
        return this.sdf.format(this.transDate);
    }

    @JsonIgnore
    public Date getPrevTransDate() {
        Calendar c = Calendar.getInstance();
        c.setTime(transDate);
        c.add(Calendar.MONTH, -1);
        return c.getTime();
    }

    public int getUnallocatedConsumption() {
        return unallocatedConsumption;
    }

    public void setUnallocatedConsumption(int unallocatedConsumption) {
        this.unallocatedConsumption = unallocatedConsumption;
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + this.planningUnitId;
        hash = 29 * hash + Objects.hashCode(this.transDate);
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
        final SupplyPlanDate other = (SupplyPlanDate) obj;
        if (this.planningUnitId != other.planningUnitId) {
            return false;
        }
        if (!Objects.equals(this.transDate, other.transDate)) {
            return false;
        }
        return true;
    }
    
    
}
