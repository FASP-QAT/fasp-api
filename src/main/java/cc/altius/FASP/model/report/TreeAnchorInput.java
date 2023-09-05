/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.report;

import java.io.Serializable;

/**
 *
 * @author akil
 */
public class TreeAnchorInput implements Serializable {

    private int programId;
    private String[] treeIds;

    public int getProgramId() {
        return programId;
    }

    public void setProgramId(int programId) {
        this.programId = programId;
    }

    public String[] getTreeIds() {
        return treeIds;
    }

    public void setTreeIds(String[] treeIds) {
        this.treeIds = treeIds;
    }

}
