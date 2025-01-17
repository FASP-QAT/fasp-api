/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model;

import cc.altius.FASP.framework.JsonDateDeserializer;
import cc.altius.FASP.framework.JsonDateSerializer;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 *
 * @author akil
 */
public class SimpleProgram extends SimpleCodeObject {

    private int realmId;
    @JsonView({Views.DropDown2View.class})
    private SimpleCodeObject realmCountry;
    @JsonView({Views.DropDown2View.class, Views.ExportApiView.class})
    private List<SimpleCodeObject> healthAreaList;
    @JsonView({Views.DropDown2View.class, Views.ExportApiView.class})
    private SimpleCodeObject organisation;
    @JsonView({Views.DropDown2View.class, Views.ExportApiView.class})
    private List<SimpleObject> regionList;
    @JsonView({Views.DropDownView.class, Views.DropDown2View.class, Views.ExportApiView.class})
    private int currentVersionId;
    @JsonView({Views.DropDownView.class, Views.DropDown2View.class})
    private int programTypeId;
    @JsonView({Views.DropDownView.class, Views.DropDown2View.class})
    private boolean active;
    @JsonView({Views.DropDown2View.class})
    private int noOfMonthsInPastForBottomDashboard;
    @JsonView({Views.DropDown2View.class})
    private int noOfMonthsInFutureForBottomDashboard;

    public SimpleProgram() {
        super();
        this.healthAreaList = new LinkedList<>();
        this.regionList = new LinkedList<>();
    }

    public SimpleProgram(int id) {
        super();
        this.setId(id);
        this.healthAreaList = new LinkedList<>();
        this.regionList = new LinkedList<>();
    }

    public SimpleProgram(Integer id, Label label, String code, int currentVersionId, int programTypeId) {
        super(id, label, code);
        this.currentVersionId = currentVersionId;
        this.programTypeId = programTypeId;
        this.healthAreaList = new LinkedList<>();
        this.regionList = new LinkedList<>();
    }

    public int getRealmId() {
        return realmId;
    }

    public void setRealmId(int realmId) {
        this.realmId = realmId;
    }

    public int getCurrentVersionId() {
        return currentVersionId;
    }

    public void setCurrentVersionId(int currentVersionId) {
        this.currentVersionId = currentVersionId;
    }

    public int getProgramTypeId() {
        return programTypeId;
    }

    public void setProgramTypeId(int programTypeId) {
        this.programTypeId = programTypeId;
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

    public void setHealthAreaList(List<SimpleCodeObject> healthAreaList) {
        this.healthAreaList = healthAreaList;
    }

    public List<Integer> getHealthAreaIdList() {
        return healthAreaList.stream().map(SimpleCodeObject::getId).collect(Collectors.toList());
    }

    public SimpleCodeObject getOrganisation() {
        return organisation;
    }

    public void setOrganisation(SimpleCodeObject organisation) {
        this.organisation = organisation;
    }

    public List<SimpleObject> getRegionList() {
        return regionList;
    }

    public void setRegionList(List<SimpleObject> regionList) {
        this.regionList = regionList;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getNoOfMonthsInPastForBottomDashboard() {
        return noOfMonthsInPastForBottomDashboard;
    }

    public void setNoOfMonthsInPastForBottomDashboard(int noOfMonthsInPastForBottomDashboard) {
        this.noOfMonthsInPastForBottomDashboard = noOfMonthsInPastForBottomDashboard;
    }

    public int getNoOfMonthsInFutureForBottomDashboard() {
        return noOfMonthsInFutureForBottomDashboard;
    }

    public void setNoOfMonthsInFutureForBottomDashboard(int noOfMonthsInFutureForBottomDashboard) {
        this.noOfMonthsInFutureForBottomDashboard = noOfMonthsInFutureForBottomDashboard;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.realmCountry);
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
        final SimpleProgram other = (SimpleProgram) obj;
        return Objects.equals(this.getId(), other.getId());
    }

    @Override
    public String toString() {
        return "SimpleProgram{" + "realmCountry=" + realmCountry + ", healthAreaList=" + healthAreaList + ", organisation=" + organisation + ", regionList=" + regionList + ", currentVersionId=" + currentVersionId + ", programTypeId=" + programTypeId + '}';
    }

}
