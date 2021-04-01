/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author altius
 */
public class StaticLabelDTO implements Serializable {

    private String labelCode;
    private int staticLabelId;
    private List<StaticLabelLanguagesDTO> staticLabelLanguages;

    public StaticLabelDTO() {
        staticLabelLanguages=new ArrayList<>();
    }
    
    

    public String getLabelCode() {
        return labelCode;
    }

    public void setLabelCode(String labelCode) {
        this.labelCode = labelCode;
    }

    public int getStaticLabelId() {
        return staticLabelId;
    }

    public void setStaticLabelId(int staticLabelId) {
        this.staticLabelId = staticLabelId;
    }

    public List<StaticLabelLanguagesDTO> getStaticLabelLanguages() {
        return staticLabelLanguages;
    }

    public void setStaticLabelLanguages(List<StaticLabelLanguagesDTO> staticLabelLanguages) {
        this.staticLabelLanguages = staticLabelLanguages;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + this.staticLabelId;
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
        final StaticLabelDTO other = (StaticLabelDTO) obj;
        if (this.staticLabelId != other.staticLabelId) {
            return false;
        }
        return true;
    }
    
    

}
