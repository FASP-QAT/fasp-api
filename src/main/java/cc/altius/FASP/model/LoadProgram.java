/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author akil
 */
public class LoadProgram implements Serializable {

    private SimpleCodeObject program;
    private List<LoadVersion> versionList;
    private int pagination;

    public LoadProgram() {
    }

    public LoadProgram(SimpleCodeObject program) {
        this.program = program;
        this.pagination = 0;
    }

    public SimpleCodeObject getProgram() {
        return program;
    }

    public void setProgram(SimpleCodeObject program) {
        this.program = program;
    }

    public List<LoadVersion> getVersionList() {
        return versionList;
    }

    public void setVersionList(List<LoadVersion> versionList) {
        this.versionList = versionList;
    }

    public int getPagination() {
        return pagination;
    }

    public void setPagination(int pagination) {
        this.pagination = pagination;
    }

}
