/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.Views;
import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;

/**
 *
 * @author akil
 */
public class DashboardQpl implements Serializable {

    @JsonView(Views.ReportView.class)
    private int puCount;
    @JsonView(Views.ReportView.class)
    private int correctCount;

    public DashboardQpl(int puCount, int correctCount) {
        this.puCount = puCount;
        this.correctCount = correctCount;
    }

    public DashboardQpl() {
    }

    public int getPuCount() {
        return puCount;
    }

    public void setPuCount(int puCount) {
        this.puCount = puCount;
    }

    public int getCorrectCount() {
        return correctCount;
    }

    public void setCorrectCount(int correctCount) {
        this.correctCount = correctCount;
    }

}
