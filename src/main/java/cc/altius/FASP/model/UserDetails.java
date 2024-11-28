/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model;

import java.io.Serializable;
import java.util.Map;

/**
 *
 * @author akil
 */
public class UserDetails implements Serializable {

    private User user;
    private Map<String, BfAndProgramId> bfAndProgramIdMap;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Map<String, BfAndProgramId> getBfAndProgramIdMap() {
        return bfAndProgramIdMap;
    }

    public void setBfAndProgramIdMap(Map<String, BfAndProgramId> bfAndProgramIdMap) {
        this.bfAndProgramIdMap = bfAndProgramIdMap;
    }

}
