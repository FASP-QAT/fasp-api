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
import java.util.List;

/**
 *
 * @author akil
 */
public class StockOverTimeInput implements Serializable {

    List<ProgramAndPlanningUnit> programAndPlanningUnitList;
    private Integer mosPast;
    private Integer mosFuture;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date startDate;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date stopDate;

    public List<ProgramAndPlanningUnit> getProgramAndPlanningUnitList() {
        return programAndPlanningUnitList;
    }

    public void setProgramAndPlanningUnitList(List<ProgramAndPlanningUnit> programAndPlanningUnitList) {
        this.programAndPlanningUnitList = programAndPlanningUnitList;
    }

    public Integer getMosPast() {
        return mosPast;
    }

    public void setMosPast(Integer mosPast) {
        this.mosPast = mosPast;
    }

    public Integer getMosFuture() {
        return mosFuture;
    }

    public void setMosFuture(Integer mosFuture) {
        this.mosFuture = mosFuture;
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

}
