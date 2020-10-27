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
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author akil
 */
public class StockStatusForProgramInput implements Serializable {

    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date dt;
    private int programId;
    private int versionId;
    private String[] tracerCategoryIds;
    private boolean includePlannedShipments;

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public int getProgramId() {
        return programId;
    }

    public void setProgramId(int programId) {
        this.programId = programId;
    }

    public int getVersionId() {
        return versionId;
    }

    public void setVersionId(int versionId) {
        this.versionId = versionId;
    }

    public boolean isIncludePlannedShipments() {
        return includePlannedShipments;
    }

    public void setIncludePlannedShipments(boolean includePlannedShipments) {
        this.includePlannedShipments = includePlannedShipments;
    }

    public String[] getTracerCategoryIds() {
        return tracerCategoryIds;
    }

    public void setTracerCategoryIds(String[] tracerCategoryIds) {
        this.tracerCategoryIds = tracerCategoryIds;
    }
    
    public String getTracerCategoryIdString() {
        if (this.tracerCategoryIds == null) {
            return "";
        } else {
            String opt = String.join("','", this.tracerCategoryIds);
            if (this.tracerCategoryIds.length > 0) {
                return "'" + opt + "'";
            } else {
                return opt;
            }
        }
    }
    
}
