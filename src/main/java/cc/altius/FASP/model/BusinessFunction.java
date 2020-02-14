/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

/**
 *
 * @author altius
 */
public class BusinessFunction {

    private String businessFunctionId;
    private String businessFunctionDesc;
    private boolean active;

    public String getBusinessFunctionId() {
        return businessFunctionId;
    }

    public void setBusinessFunctionId(String businessFunctionId) {
        this.businessFunctionId = businessFunctionId;
    }

    public String getBusinessFunctionDesc() {
        return businessFunctionDesc;
    }

    public void setBusinessFunctionDesc(String businessFunctionDesc) {
        this.businessFunctionDesc = businessFunctionDesc;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
