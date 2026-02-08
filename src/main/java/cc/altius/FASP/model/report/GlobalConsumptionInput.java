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
    private int versionId; // If a Single Program is selected then versionId is relevant. When Multiple Programs are selected this should be 0. If single program is selected then cannot be 0. -1 for latest versionId
    private int equivalencyUnitId; // 0 if not selected or then value if selected
    private String[] planningUnitIds; // Use list of PU's if an EquivalencyUnit is provided, if no equivalencyUnitId is selected then only use the first PU from the list
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date startDate;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date stopDate;
    private int viewBy; // View by Country = 1, View by Program = 2

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

    public int getVersionId() {
        return versionId;
    }

    public void setVersionId(int versionId) {
        this.versionId = versionId;
    }

    public int getEquivalencyUnitId() {
        return equivalencyUnitId;
    }

    public void setEquivalencyUnitId(int equivalencyUnitId) {
        this.equivalencyUnitId = equivalencyUnitId;
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

    public int getViewBy() {
        return viewBy;
    }

    public void setViewBy(int viewBy) {
        this.viewBy = viewBy;
    }

    public String getRealmCountryIdString() {
        if (this.realmCountryIds == null) {
            return "";
        } else {
            return String.join(",", this.realmCountryIds);
        }
    }

    public String getProgramIdString() {
        if (this.programIds == null) {
            return "";
        } else {
            return String.join(",", this.programIds);
        }
    }

    public String getPlanningUnitIdString() {
        if (this.planningUnitIds == null) {
            return "";
        } else {
            if (isEquivalencyUnitSelected()) {
                return String.join(",", this.planningUnitIds);
            } else {
                return this.planningUnitIds[0].toString();
            }
        }
    }

    public boolean isEquivalencyUnitSelected() {
        return this.equivalencyUnitId != 0;
    }

    @Override
    public String toString() {
        return "GlobalConsumptionInput{" + "realmId=" + realmId + ", realmCountryIds=" + getRealmCountryIdString() + ", programIds=" + getProgramIdString() + ", equivalencyUnitId=" + equivalencyUnitId + ", planningUnitIds=" + getPlanningUnitIdString() + ", startDate=" + startDate + ", stopDate=" + stopDate + ", viewBy=" + viewBy + '}';
    }

}
