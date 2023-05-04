/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.framework.JsonDateDeserializer;
import cc.altius.FASP.framework.JsonDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author akil
 */
public class ManualJsonPushReportInput implements Serializable {

    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date startDate;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date stopDate;
    private String[] realmCountryIds;
    private String[] programIds;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public Date getStartDate() {
        return startDate;
    }

    public String getStartDateString() {
        if (startDate == null) {
            return null;
        } else {
            return sdf.format(startDate);
        }
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getStopDate() {
        return stopDate;
    }

    public String getStopDateString() {
        if (stopDate == null) {
            return null;
        } else {
            return sdf.format(stopDate);
        }
    }

    public void setStopDate(Date stopDate) {
        this.stopDate = stopDate;
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

    public String getRealmCountryIdsString() {
        if (this.realmCountryIds == null) {
            return "";
        } else {
            String opt = String.join(",", this.realmCountryIds);
            return opt;
        }
    }

    public String getProgramIdsString() {
        if (this.programIds == null) {
            return "";
        } else {
            String opt = String.join(",", this.programIds);
            return opt;
        }
    }
}
