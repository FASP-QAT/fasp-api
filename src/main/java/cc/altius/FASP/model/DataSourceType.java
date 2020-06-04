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
public class DataSourceType extends BaseModel implements Serializable {

    private int dataSourceTypeId;
    private SimpleCodeObject realm;
    private Label label;

    public DataSourceType() {
    }

    public DataSourceType(int dataSourceTypeId, Label label) {
        this.dataSourceTypeId = dataSourceTypeId;
        this.label = label;
    }

    public DataSourceType(int dataSourceTypeId, Label label, SimpleCodeObject realm) {
        this.dataSourceTypeId = dataSourceTypeId;
        this.label = label;
        this.realm = realm;
    }

    public int getDataSourceTypeId() {
        return dataSourceTypeId;
    }

    public void setDataSourceTypeId(int dataSourceTypeId) {
        this.dataSourceTypeId = dataSourceTypeId;
    }

    public SimpleCodeObject getRealm() {
        return realm;
    }

    public void setRealm(SimpleCodeObject realm) {
        this.realm = realm;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + this.dataSourceTypeId;
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
        final DataSourceType other = (DataSourceType) obj;
        if (this.dataSourceTypeId != other.dataSourceTypeId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DataSourceType{" + "dataSourceTypeId=" + dataSourceTypeId + ", label=" + label + '}';
    }
}
