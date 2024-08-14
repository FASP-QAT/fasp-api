/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.ProgramIdAndVersionId;
import java.io.Serializable;

/**
 *
 * @author akil
 */
public class LoadProgramInput extends ProgramIdAndVersionId implements Serializable {

    private String cutOffDate;

    public String getCutOffDate() {
        return cutOffDate;
    }

    public void setCutOffDate(String cutOffDate) {
        this.cutOffDate = cutOffDate;
    }

}
