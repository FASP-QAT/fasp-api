/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.ProgramIdAndVersionId;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author akil
 */
public class LoadProgramInput implements Serializable {

    private String cutOffDate;
    private List<ProgramIdAndVersionId> programVersionList;

    public String getCutOffDate() {
        return cutOffDate;
    }

    public void setCutOffDate(String cutOffDate) {
        this.cutOffDate = cutOffDate;
    }

    public List<ProgramIdAndVersionId> getProgramVersionList() {
        return programVersionList;
    }

    public void setProgramVersionList(List<ProgramIdAndVersionId> programVersionList) {
        this.programVersionList = programVersionList;
    }

}
