/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import cc.altius.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author akil
 */
public class SupplyPlanDate implements Serializable {

    private int planningUnitId;
    private String transDate;
    private int unallocatedConsumption;
    private int unallocatedConsumptionWps;
    private List<SupplyPlanBatchInfo> batchList;

    private final SimpleDateFormat sdf = new SimpleDateFormat("MMM yy");

    public SupplyPlanDate() {
    }

    public SupplyPlanDate(int planningUnitId, String transDate, List<SupplyPlanBatchInfo> batchList) {
        this.planningUnitId = planningUnitId;
        this.transDate = transDate;
        this.batchList = batchList;
        batchList = new LinkedList<>();
    }

    public SupplyPlanDate(int planningUnitId, String transDate) {
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

    public String getTransDate() {
        return transDate;
    }

    public void setTransDate(String transDate) {
        this.transDate = transDate;
    }

    public List<SupplyPlanBatchInfo> getBatchList() {
        return batchList;
    }

    public void setBatchList(List<SupplyPlanBatchInfo> batchList) {
        this.batchList = batchList;
    }

    @JsonIgnore
    public String getPrevTransDate() {
        Date dt;
        try {
            dt = DateUtils.getDateFromString(this.transDate, DateUtils.YMD);
            Calendar c = Calendar.getInstance();
            c.setTime(dt);
            c.add(Calendar.MONTH, -1);
            return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        } catch (ParseException ex) {
            Logger.getLogger(SupplyPlanDate.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    public int getUnallocatedConsumption() {
        return unallocatedConsumption;
    }

    public void setUnallocatedConsumption(int unallocatedConsumption) {
        this.unallocatedConsumption = unallocatedConsumption;
    }

    public int getUnallocatedConsumptionWps() {
        return unallocatedConsumptionWps;
    }

    public void setUnallocatedConsumptionWps(int unallocatedConsumptionWps) {
        this.unallocatedConsumptionWps = unallocatedConsumptionWps;
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
