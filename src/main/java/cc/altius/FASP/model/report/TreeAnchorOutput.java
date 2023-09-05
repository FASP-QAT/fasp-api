/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.report;

/**
 *
 * @author akil
 */
public class TreeAnchorOutput {

    private int treeId;
    private Integer treeAnchorId;

    public TreeAnchorOutput() {
    }

    public TreeAnchorOutput(int treeId, int treeAnchorId) {
        this.treeId = treeId;
        if (treeAnchorId == 0) {
            this.treeAnchorId = null;
        } else {
            this.treeAnchorId = treeAnchorId;
        }
    }

    public int getTreeId() {
        return treeId;
    }

    public void setTreeId(int treeId) {
        this.treeId = treeId;
    }

    public int getTreeAnchorId() {
        return treeAnchorId;
    }

    public void setTreeAnchorId(int treeAnchorId) {
        if (treeAnchorId == 0) {
            this.treeAnchorId = null;
        } else {
            this.treeAnchorId = treeAnchorId;
        }
    }

}
