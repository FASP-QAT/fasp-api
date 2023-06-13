/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;

/**
 *
 * @author akil
 */
public class UsagePeriod extends BaseModel implements Serializable {

    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private int usagePeriodId;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private Label label;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private double convertToMonth;

    public UsagePeriod() {

    }

    public UsagePeriod(int usagePeriodId, Label label, double convertToMonth) {
        this.usagePeriodId = usagePeriodId;
        this.label = label;
        this.convertToMonth = convertToMonth;
    }

    public int getUsagePeriodId() {
        return usagePeriodId;
    }

    public void setUsagePeriodId(int usagePeriodId) {
        this.usagePeriodId = usagePeriodId;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public double getConvertToMonth() {
        return convertToMonth;
    }

    public void setConvertToMonth(double convertToMonth) {
        this.convertToMonth = convertToMonth;
    }

}
