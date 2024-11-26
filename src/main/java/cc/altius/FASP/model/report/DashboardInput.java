/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.report;

import java.io.Serializable;

/**
 *
 * @author akil
 */
public class DashboardInput implements Serializable {

    private int programId;
    private String startDate;
    private String stopDate;
    private int displayShipmentsBy; // 1 = Funding Source, 2 = Procurement Agent, 3 = Status-

    public int getProgramId() {
        return programId;
    }

    public void setProgramId(int programId) {
        this.programId = programId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStopDate() {
        return stopDate;
    }

    public void setStopDate(String stopDate) {
        this.stopDate = stopDate;
    }

    public int getDisplayShipmentsBy() {
        return displayShipmentsBy;
    }

    public void setDisplayShipmentsBy(int displayShipmentsBy) {
        this.displayShipmentsBy = displayShipmentsBy;
    }

}
