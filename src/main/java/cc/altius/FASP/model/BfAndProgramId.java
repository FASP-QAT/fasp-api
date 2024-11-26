/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model;

import java.util.HashSet;

/**
 *
 * @author akil
 */
public class BfAndProgramId {

    private HashSet<String> businessFunctionList;
    private HashSet<Integer> programIdList;

    public BfAndProgramId() {
        this.businessFunctionList = new HashSet<>();
        this.programIdList = new HashSet<>();
    }

    public HashSet<String> getBusinessFunctionList() {
        return businessFunctionList;
    }

    public void setBusinessFunctionList(HashSet<String> businessFunctionList) {
        this.businessFunctionList = businessFunctionList;
    }

    public HashSet<Integer> getProgramIdList() {
        return programIdList;
    }

    public void setProgramIdList(HashSet<Integer> programIdList) {
        this.programIdList = programIdList;
    }

}
