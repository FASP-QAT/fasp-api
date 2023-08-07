/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import java.util.Date;

/**
 *
 * @author altius
 */
public class CoreuiVersion extends BaseModel {

    private int versionId;
    private int versionNo;

    public int getVersionId() {
        return versionId;
    }

    public void setVersionId(int versionId) {
        this.versionId = versionId;
    }

    public int getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(int versionNo) {
        this.versionNo = versionNo;
    }

    @Override
    public String toString() {
        return "CoreuiVersion{" + "versionId=" + versionId + ", versionNo=" + versionNo + '}';
    }

}
