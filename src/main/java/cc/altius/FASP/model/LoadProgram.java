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
    private SimpleCodeObject realmCountry;
    private SimpleCodeObject healthArea;
    private SimpleCodeObject organisation;
    private List<LoadVersion> versionList;
    private int currentPage;
    private int maxPages;

    public LoadProgram() {
    }

    public LoadProgram(SimpleCodeObject program, SimpleCodeObject realmCountry, SimpleCodeObject healthArea, SimpleCodeObject organisation, int maxCount) {
        this.program = program;
        this.realmCountry = realmCountry;
        this.healthArea = healthArea;
        this.organisation = organisation;
        this.currentPage = 0;
        this.maxPages = (maxCount-1)/5;
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

    public SimpleCodeObject getHealthArea() {
        return healthArea;
    }

    public void setHealthArea(SimpleCodeObject healthArea) {
        this.healthArea = healthArea;
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

}
