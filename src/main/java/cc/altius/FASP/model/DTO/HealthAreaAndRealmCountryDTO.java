/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.DTO;

import java.io.Serializable;

/**
 * This object is used in the drop down for Program that requires filtered
 *
 * @author akil
 *
 */
public class HealthAreaAndRealmCountryDTO implements Serializable {

    /**
     * Set to null if you do not want to filter on healthArea
     */
    private Integer healthAreaId;
    /**
     * Set to null if you do not want to filter on realmCountry
     */
    private Integer realmCountryId;

    public HealthAreaAndRealmCountryDTO() {
    }

    public HealthAreaAndRealmCountryDTO(Integer healthAreaId, Integer realmCountryId) {
        this.healthAreaId = healthAreaId;
        this.realmCountryId = realmCountryId;
    }

    public Integer getHealthAreaId() {
        return healthAreaId;
    }

    public void setHealthAreaId(Integer healthAreaId) {
        this.healthAreaId = healthAreaId;
    }

    public Integer getRealmCountryId() {
        return realmCountryId;
    }

    public void setRealmCountryId(Integer realmCountryId) {
        this.realmCountryId = realmCountryId;
    }

}
