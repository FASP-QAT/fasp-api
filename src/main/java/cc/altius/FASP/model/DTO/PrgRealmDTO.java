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
public class PrgRealmDTO {

    private Integer realmId;
    private String realmCode;
    private PrgLabelDTO label;
    private int monthsInPastForAMC;
    private int monthsInFutureForAMC;
    private int orderFrequency;
    private boolean defaultRealm;

    public PrgLabelDTO getLabel() {
        return label;
    }

    public void setLabel(PrgLabelDTO label) {
        this.label = label;
    }

    public int getMonthsInPastForAMC() {
        return monthsInPastForAMC;
    }

    public void setMonthsInPastForAMC(int monthsInPastForAMC) {
        this.monthsInPastForAMC = monthsInPastForAMC;
    }

    public int getMonthsInFutureForAMC() {
        return monthsInFutureForAMC;
    }

    public void setMonthsInFutureForAMC(int monthsInFutureForAMC) {
        this.monthsInFutureForAMC = monthsInFutureForAMC;
    }

    public int getOrderFrequency() {
        return orderFrequency;
    }

    public void setOrderFrequency(int orderFrequency) {
        this.orderFrequency = orderFrequency;
    }

    public boolean isDefaultRealm() {
        return defaultRealm;
    }

    public void setDefaultRealm(boolean defaultRealm) {
        this.defaultRealm = defaultRealm;
    }

    public Integer getRealmId() {
        return realmId;
    }

    public void setRealmId(Integer realmId) {
        this.realmId = realmId;
    }

    public String getRealmCode() {
        return realmCode;
    }

    public void setRealmCode(String realmCode) {
        this.realmCode = realmCode;
    }

    @Override
    public String toString() {
        return "Realm{" + "realmId=" + realmId + ", realmCode=" + realmCode + '}';
    }

}
