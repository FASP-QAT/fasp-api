/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.report;

/**
 *
 * @author akil
 */
public class StockStatusVerticalDropdownInput {

    private String[] programIds;
    private boolean onlyAllowPuPresentAcrossAllPrograms;

    public String[] getProgramIds() {
        return programIds;
    }

    public void setProgramIds(String[] programIds) {
        this.programIds = programIds;
    }

    public boolean isOnlyAllowPuPresentAcrossAllPrograms() {
        return onlyAllowPuPresentAcrossAllPrograms;
    }

    public void setOnlyAllowPuPresentAcrossAllPrograms(boolean onlyAllowPuPresentAcrossAllPrograms) {
        this.onlyAllowPuPresentAcrossAllPrograms = onlyAllowPuPresentAcrossAllPrograms;
    }

}
