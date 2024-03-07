/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.DTO;

import cc.altius.FASP.model.BasicUser;
import cc.altius.FASP.model.Label;
import cc.altius.FASP.model.Views;
import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author akil
 */
public class UserAclDTO implements Serializable {

    @JsonView({Views.UserListView.class})
    private BasicUser user;
    @JsonView({Views.UserListView.class})
    private Label realmCountry;
    @JsonView({Views.UserListView.class})
    private Label healthArea;
    @JsonView({Views.UserListView.class})
    private Label organisation;
    @JsonView({Views.UserListView.class})
    private Label program;
    private final List<Integer> realmCountryIdList;
    private final List<Integer> healthAreaIdList;
    private final List<Integer> organisationIdList;
    private final List<Integer> programIdList;

    public UserAclDTO() {
        this.realmCountryIdList = new LinkedList<>();
        this.healthAreaIdList = new LinkedList<>();
        this.organisationIdList = new LinkedList<>();
        this.programIdList = new LinkedList<>();
    }

    public BasicUser getUser() {
        return user;
    }

    public void setUser(BasicUser user) {
        this.user = user;
    }

    private Label getDefaultLabel() {
        return new Label(0, "All", "All", "All", "All");
    }

    public void addProperty(String propertyType, int propertyId, Label propertyLabel) {
        int idx;
        switch (propertyType) {
            case "realmCountry" -> {
                idx = this.realmCountryIdList.indexOf(propertyId);
                if (idx == -1) {
                    // Add it
                    this.realmCountryIdList.add(propertyId);
                    if (this.realmCountry == null) {
                        if (propertyLabel == null || propertyLabel.getLabelId() == null || propertyLabel.getLabelId() == 0) {
                            this.realmCountry = getDefaultLabel();
                        } else {
                            this.realmCountry = propertyLabel;
                        }
                    } else {
                        this.realmCountry.setLabel_en(this.realmCountry.getLabel_en() + "," + propertyLabel.getLabel_en());
                        this.realmCountry.setLabel_fr(this.realmCountry.getLabel_fr() + "," + propertyLabel.getLabel_fr());
                        this.realmCountry.setLabel_sp(this.realmCountry.getLabel_sp() + "," + propertyLabel.getLabel_sp());
                        this.realmCountry.setLabel_pr(this.realmCountry.getLabel_pr() + "," + propertyLabel.getLabel_pr());
                    }
                }
            }
            case "healthArea" -> {
                idx = this.healthAreaIdList.indexOf(propertyId);
                if (idx == -1) {
                    // Add it
                    this.healthAreaIdList.add(propertyId);
                    if (this.healthArea == null) {
                        if (propertyLabel == null || propertyLabel.getLabelId() == null || propertyLabel.getLabelId() == 0) {
                            this.healthArea = getDefaultLabel();
                        } else {
                            this.healthArea = propertyLabel;
                        }
                    } else {
                        this.healthArea.setLabel_en(this.healthArea.getLabel_en() + "," + propertyLabel.getLabel_en());
                        this.healthArea.setLabel_fr(this.healthArea.getLabel_fr() + "," + propertyLabel.getLabel_fr());
                        this.healthArea.setLabel_sp(this.healthArea.getLabel_sp() + "," + propertyLabel.getLabel_sp());
                        this.healthArea.setLabel_pr(this.healthArea.getLabel_pr() + "," + propertyLabel.getLabel_pr());
                    }
                }
            }
            case "organisation" -> {
                idx = this.organisationIdList.indexOf(propertyId);
                if (idx == -1) {
                    // Add it
                    this.organisationIdList.add(propertyId);
                    if (this.organisation == null) {
                        if (propertyLabel == null || propertyLabel.getLabelId() == null || propertyLabel.getLabelId() == 0) {
                            this.organisation = getDefaultLabel();
                        } else {
                            this.organisation = propertyLabel;
                        }
                    } else {
                        this.organisation.setLabel_en(this.organisation.getLabel_en() + "," + propertyLabel.getLabel_en());
                        this.organisation.setLabel_fr(this.organisation.getLabel_fr() + "," + propertyLabel.getLabel_fr());
                        this.organisation.setLabel_sp(this.organisation.getLabel_sp() + "," + propertyLabel.getLabel_sp());
                        this.organisation.setLabel_pr(this.organisation.getLabel_pr() + "," + propertyLabel.getLabel_pr());
                    }
                }
            }
            case "program" -> {
                idx = this.programIdList.indexOf(propertyId);
                if (idx == -1) {
                    // Add it
                    this.programIdList.add(propertyId);
                    if (this.program == null) {
                        if (propertyLabel == null || propertyLabel.getLabelId() == null || propertyLabel.getLabelId() == 0) {
                            this.program = getDefaultLabel();
                        } else {
                            this.program = propertyLabel;
                        }
                    } else {
                        this.program.setLabel_en(this.program.getLabel_en() + "," + propertyLabel.getLabel_en());
                        this.program.setLabel_fr(this.program.getLabel_fr() + "," + propertyLabel.getLabel_fr());
                        this.program.setLabel_sp(this.program.getLabel_sp() + "," + propertyLabel.getLabel_sp());
                        this.program.setLabel_pr(this.program.getLabel_pr() + "," + propertyLabel.getLabel_pr());
                    }
                }
            }
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.user);
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
        final UserAclDTO other = (UserAclDTO) obj;
        return Objects.equals(this.user, other.user);
    }

}
