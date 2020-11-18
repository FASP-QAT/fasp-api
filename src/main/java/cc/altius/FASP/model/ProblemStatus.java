/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

/**
 *
 * @author akil
 */
public class ProblemStatus extends SimpleObject {

    private boolean userManaged;

    public ProblemStatus() {
    }

    public ProblemStatus(boolean userManaged, Integer id, Label label) {
        super(id, label);
        this.userManaged = userManaged;
    }

    public boolean isUserManaged() {
        return userManaged;
    }

    public void setUserManaged(boolean userManaged) {
        this.userManaged = userManaged;
    }

}
