/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author akil
 */
public class RoAndRoPrimeLineNo implements Serializable {

    private String roNo;
    private String roPrimeLineNo;

    public String getRoNo() {
        return roNo;
    }

    public void setRoNo(String roNo) {
        this.roNo = roNo;
    }

    public String getRoPrimeLineNo() {
        return roPrimeLineNo;
    }

    public void setRoPrimeLineNo(String roPrimeLineNo) {
        this.roPrimeLineNo = roPrimeLineNo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.roNo);
        hash = 67 * hash + Objects.hashCode(this.roPrimeLineNo);
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
        final RoAndRoPrimeLineNo other = (RoAndRoPrimeLineNo) obj;
        if (!Objects.equals(this.roNo, other.roNo)) {
            return false;
        }
        if (!Objects.equals(this.roPrimeLineNo, other.roPrimeLineNo)) {
            return false;
        }
        return true;
    }

}