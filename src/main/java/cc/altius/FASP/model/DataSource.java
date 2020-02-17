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
public class DataSource {
    
    public int dataSourceId;
    public Label label;
    public DataSourceType dataSourceType;
    public boolean active;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    

    public DataSourceType getDataSourceType() {
        return dataSourceType;
    }

    public void setDataSourceType(DataSourceType dataSourceType) {
        this.dataSourceType = dataSourceType;
    }
    

    public int getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(int dataSourceId) {
        this.dataSourceId = dataSourceId;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "DataSource{" + "dataSourceId=" + dataSourceId + ", label=" + label + ", dataSourceType=" + dataSourceType + '}';
    }

    
    
  }
