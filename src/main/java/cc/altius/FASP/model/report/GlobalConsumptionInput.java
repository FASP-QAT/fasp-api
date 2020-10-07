/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.framework.JsonDateDeserializer;
import cc.altius.FASP.framework.JsonDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Date;

/**
 *
 * @author akil
 */
public class GlobalConsumptionInput {

    private int realmId;
    private String[] realmCountryIds;
    private String[] programIds;
    private String[] planningUnitIds;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date startDate;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date stopDate;
    private int reportView; // 1 = Planning Unit, 2 = Forecasting Unit
    private boolean useApprovedSupplyPlanOnly;

    public int getRealmId() {
        return realmId;
    }

    public void setRealmId(int realmId) {
        this.realmId = realmId;
    }

    
    public String[] getRealmCountryIds() {
        return realmCountryIds;
    }

    public void setRealmCountryIds(String[] realmCountryIds) {
        this.realmCountryIds = realmCountryIds;
    }

    public String[] getProgramIds() {
        return programIds;
    }

    public void setProgramIds(String[] programIds) {
        this.programIds = programIds;
    }

    public String[] getPlanningUnitIds() {
        return planningUnitIds;
    }

    public void setPlanningUnitIds(String[] planningUnitIds) {
        this.planningUnitIds = planningUnitIds;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getStopDate() {
        return stopDate;
    }

    public void setStopDate(Date stopDate) {
        this.stopDate = stopDate;
    }

    public int getReportView() {
        return reportView;
    }

    public void setReportView(int reportView) {
        this.reportView = reportView;
    }

    public boolean isUseApprovedSupplyPlanOnly() {
        return useApprovedSupplyPlanOnly;
    }

    public void setUseApprovedSupplyPlanOnly(boolean useApprovedSupplyPlanOnly) {
        this.useApprovedSupplyPlanOnly = useApprovedSupplyPlanOnly;
    }

    public String getRealmCountryIdString() {
        if (this.realmCountryIds == null) {
            return "";
        } else {
            String opt = String.join("','", this.realmCountryIds);
            if (this.realmCountryIds.length > 0) {
                return "'" + opt + "'";
            } else {
                return opt;
            }
        }
    }

    public String getProgramIdString() {
        if (this.programIds == null) {
            return "";
        } else {
            String opt = String.join("','", this.programIds);
            if (this.programIds.length > 0) {
                return "'" + opt + "'";
            } else {
                return opt;
            }
        }
    }

    public String getPlanningUnitIdString() {
        if (this.planningUnitIds == null) {
            return "";
        } else {
            String opt = String.join("','", this.planningUnitIds);
            if (this.planningUnitIds.length > 0) {
                return "'" + opt + "'";
            } else {
                return opt;
            }
        }
    }

}
