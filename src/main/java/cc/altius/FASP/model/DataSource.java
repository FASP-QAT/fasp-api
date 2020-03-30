/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import java.io.Serializable;

/**
 *
 * @author palash
 */
public class DataSource extends BaseModel implements Serializable {
    
    public int dataSourceId;
    public Realm realm;
    public Label label;
    public DataSourceType dataSourceType;

    public DataSource(int dataSourceId, Realm realm, Label label) {
        this.dataSourceId = dataSourceId;
        this.realm = realm;
        this.label = label;
    }

    public DataSource(int dataSourceId, Realm realm, Label label, DataSourceType dataSourceType) {
        this.dataSourceId = dataSourceId;
        this.realm = realm;
        this.label = label;
        this.dataSourceType = dataSourceType;
    }

    public DataSource() {
    }

    public int getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(int dataSourceId) {
        this.dataSourceId = dataSourceId;
    }

    public Realm getRealm() {
        return realm;
    }

    public void setRealm(Realm realm) {
        this.realm = realm;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public DataSourceType getDataSourceType() {
        return dataSourceType;
    }

    public void setDataSourceType(DataSourceType dataSourceType) {
        this.dataSourceType = dataSourceType;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.dataSourceId;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DataSource other = (DataSource) obj;
        if (this.dataSourceId != other.dataSourceId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DataSource{" + "dataSourceId=" + dataSourceId + ", label=" + label + ", dataSourceType=" + dataSourceType + '}';
    }

  }
