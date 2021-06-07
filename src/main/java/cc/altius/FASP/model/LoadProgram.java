/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author akil
 */
public class LoadProgram implements Serializable {

    private SimpleCodeObject program;
    private SimpleCodeObject realmCountry;
    private List<SimpleCodeObject> healthAreaList;
    private SimpleCodeObject organisation;
    private List<LoadVersion> versionList;
    private int currentPage;
    private int maxPages;

    public LoadProgram() {
    }

    public LoadProgram(SimpleCodeObject program, SimpleCodeObject realmCountry, SimpleCodeObject healthArea, SimpleCodeObject organisation, int maxCount) {
        this.program = program;
        this.realmCountry = realmCountry;
        this.healthAreaList = new LinkedList<>();
        this.healthAreaList.add(healthArea);
        this.organisation = organisation;
        this.currentPage = 0;
        this.maxPages = (maxCount - 1) / 5;
    }

    public SimpleCodeObject getProgram() {
        return program;
    }

    public void setProgram(SimpleCodeObject program) {
        this.program = program;
    }

    public SimpleCodeObject getRealmCountry() {
        return realmCountry;
    }

    public void setRealmCountry(SimpleCodeObject realmCountry) {
        this.realmCountry = realmCountry;
    }

    public List<SimpleCodeObject> getHealthAreaList() {
        return healthAreaList;
    }

    public void setHealthArea(List<SimpleCodeObject> healthAreaList) {
        this.healthAreaList = healthAreaList;
    }

    public void addHealthArea(SimpleCodeObject healthArea) {
        int idx = this.healthAreaList.indexOf(healthArea);
        if (idx == -1) {
            this.healthAreaList.add(healthArea);
        }
    }

    public SimpleCodeObject getOrganisation() {
        return organisation;
    }

    public void setOrganisation(SimpleCodeObject organisation) {
        this.organisation = organisation;
    }

    public List<LoadVersion> getVersionList() {
        return versionList;
    }

    public void setVersionList(List<LoadVersion> versionList) {
        this.versionList = versionList;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getMaxPages() {
        return maxPages;
    }

    public void setMaxPages(int maxPages) {
        this.maxPages = maxPages;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.program);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LoadProgram other = (LoadProgram) obj;
        if (!Objects.equals(this.program, other.program)) {
            return false;
        }
        return true;
    }
    

}
