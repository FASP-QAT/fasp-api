/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.framework.JsonDateDeserializer;
import cc.altius.FASP.framework.JsonDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author akil
 */
public class StockStatusAcrossProductsInput implements Serializable {

    private int realmId;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date dt;
    private int tracerCategoryId;
    private String[] realmCountryIds;

    public int getRealmId() {
        return realmId;
    }

    public void setRealmId(int realmId) {
        this.realmId = realmId;
    }

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public int getTracerCategoryId() {
        return tracerCategoryId;
    }

    public void setTracerCategoryId(int tracerCategoryId) {
        this.tracerCategoryId = tracerCategoryId;
    }

    public String[] getRealmCountryIds() {
        return realmCountryIds;
    }

    public void setRealmCountryIds(String[] realmCountryIds) {
        this.realmCountryIds = realmCountryIds;
    }

    public String getRealmCountryIdsString() {
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
