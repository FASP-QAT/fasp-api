/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model;

import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;

/**
 *
 * @author akil
 */
public class BasicUserWithOrgAndCountry extends BasicUser implements Serializable {

    @JsonView({Views.UserListView.class})
    private String orgAndCountry;

    public BasicUserWithOrgAndCountry(String orgAndCountry, int userId, String username) {
        super(userId, username);
        this.orgAndCountry = orgAndCountry;
    }
    
    public String getOrgAndCountry() {
        return orgAndCountry;
    }

    public void setOrgAndCountry(String orgAndCountry) {
        this.orgAndCountry = orgAndCountry;
    }

}
