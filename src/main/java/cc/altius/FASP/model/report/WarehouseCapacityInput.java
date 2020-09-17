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

    private int realmCountryId;
    private String[] programIds;

    public int getRealmCountryId() {
        return realmCountryId;
    }

    public void setRealmCountryId(int realmCountryId) {
        this.realmCountryId = realmCountryId;
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

}
