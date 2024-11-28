/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;

/**
 *
 * @author akil
 */
public class BasicUser implements Serializable {

    @JsonView({Views.InternalView.class, Views.ArtmisView.class, Views.ReportView.class, Views.DropDownView.class, Views.UserListView.class})
    private int userId;
    @JsonView({Views.InternalView.class, Views.ArtmisView.class, Views.ReportView.class, Views.DropDownView.class, Views.UserListView.class})
    private String username;

    public BasicUser(int userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    public BasicUser(String username) {
        this.username = username;
    }

    public BasicUser() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
