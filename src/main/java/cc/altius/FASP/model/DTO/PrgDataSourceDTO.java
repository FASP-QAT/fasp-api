/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO;

/**
 *
 * @author altius
 */
public class PrgDataSourceDTO {

    private int dataSourceId;
    private PrgDataSourceTypeDTO dataSourceType;
    private PrgLabelDTO label;
    private boolean active;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public PrgLabelDTO getLabel() {
        return label;
    }

    public void setLabel(PrgLabelDTO label) {
        this.label = label;
    }

    public int getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(int dataSourceId) {
        this.dataSourceId = dataSourceId;
    }

    public PrgDataSourceTypeDTO getDataSourceType() {
        return dataSourceType;
    }

    public void setDataSourceType(PrgDataSourceTypeDTO dataSourceType) {
        this.dataSourceType = dataSourceType;
    }

}
