/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

/**
 *
 * @author palash
 */
public class DataSourceType {
    
    private int dataSourceTypeId;
    private Label label;
    private boolean active;

    public DataSourceType() {
        
        this.label=new Label();
    }
    
    public int getDataSourceTypeId() {
        return dataSourceTypeId;
    }

    public void setDataSourceTypeId(int dataSourceTypeId) {
        this.dataSourceTypeId = dataSourceTypeId;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "DataSourceType{" + "dataSourceTypeId=" + dataSourceTypeId + ", label=" + label + ", active=" + active + '}';
    }

 

}
