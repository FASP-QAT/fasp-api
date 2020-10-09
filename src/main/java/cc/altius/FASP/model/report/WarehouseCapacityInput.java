/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import java.io.Serializable;

/**
 *
 * @author akil
 */
public class WarehouseCapacityInput implements Serializable {

    private String[] realmCountryIds;
    private String[] programIds;

    public String[] getRealmCountryIds() {
        return realmCountryIds;
    }

    public void setRealmCountryIds(String[] realmCountryIds) {
        this.realmCountryIds = realmCountryIds;
    }

    public String[] getProgramIds() {
        return programIds;
    }

    public void setProgramIds(String[] programIds) {
        this.programIds = programIds;
    }

    public String getProgramIdString() {
        if (this.programIds == null) {
            return "";
        } else {
            String opt = String.join("','", this.programIds);
            if (this.programIds.length > 0) {
                return "'" + opt + "'";
            } else {
                return opt;
            }
        }
    }

    public String getRealmCountryIdString() {
        if (this.realmCountryIds == null) {
            return "";
        } else {
            String opt = String.join("','", this.realmCountryIds);
            if (this.realmCountryIds.length > 0) {
                return "'" + opt + "'";
            } else {
                return opt;
            }
        }
    }

}
