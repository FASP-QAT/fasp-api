/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.dao.impl.DashboardBottomData;
import cc.altius.FASP.framework.JsonDateDeserializer;
import cc.altius.FASP.framework.JsonDateSerializer;
import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.Views;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author akil
 */
public class DashboardForLoadProgram {

    @JsonView(Views.ReportView.class)
    private SimpleCodeObject program;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    @JsonView(Views.ReportView.class)
    private Date curDate;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    @JsonView(Views.ReportView.class)
    private Date startDateBottom;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    @JsonView(Views.ReportView.class)
    private Date stopDateBottom;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    @JsonView(Views.ReportView.class)
    private Date startDateTop;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    @JsonView(Views.ReportView.class)
    private Date stopDateTop;
    @JsonView(Views.ReportView.class)
    Map<Integer, DashboardBottomData> bottomPuData;
    @JsonView(Views.ReportView.class)
    Map<Integer, DashboardTopData> topPuData;

    public DashboardForLoadProgram() {
        bottomPuData = new HashMap<>();
        topPuData = new HashMap<>();
    }

    public SimpleCodeObject getProgram() {
        return program;
    }

    public void setProgram(SimpleCodeObject program) {
        this.program = program;
    }

    public Date getCurDate() {
        return curDate;
    }

    public void setCurDate(Date curDate) {
        this.curDate = curDate;
    }

    public Date getStartDateBottom() {
        return startDateBottom;
    }

    public void setStartDateBottom(Date startDateBottom) {
        this.startDateBottom = startDateBottom;
    }

    public Date getStopDateBottom() {
        return stopDateBottom;
    }

    public void setStopDateBottom(Date stopDateBottom) {
        this.stopDateBottom = stopDateBottom;
    }

    public Date getStartDateTop() {
        return startDateTop;
    }

    public void setStartDateTop(Date startDateTop) {
        this.startDateTop = startDateTop;
    }

    public Date getStopDateTop() {
        return stopDateTop;
    }

    public void setStopDateTop(Date stopDateTop) {
        this.stopDateTop = stopDateTop;
    }

    // Key is PU Id
    public Map<Integer, DashboardTopData> getTopPuData() {
        return topPuData;
    }

    public void setTopPuData(Map<Integer, DashboardTopData> topPuData) {
        this.topPuData = topPuData;
    }

    // Key is PU Id
    public Map<Integer, DashboardBottomData> getBottomPuData() {
        return bottomPuData;
    }

    public void setBottomPuData(Map<Integer, DashboardBottomData> bottomPuData) {
        this.bottomPuData = bottomPuData;
    }

}
