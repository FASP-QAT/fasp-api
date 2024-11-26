/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import com.fasterxml.jackson.annotation.JsonView;
import java.util.Objects;

/**
 *
 * @author altius
 */
public class BusinessFunction extends BaseModel {

    @JsonView(Views.ReportView.class)
    private String businessFunctionId;
    @JsonView(Views.ReportView.class)
    private Label label;

    public BusinessFunction() {
    }

    public BusinessFunction(String businessFunctionId) {
        this.businessFunctionId = businessFunctionId;
    }

    public String getBusinessFunctionId() {
        return businessFunctionId;
    }

    public void setBusinessFunctionId(String businessFunctionId) {
        this.businessFunctionId = businessFunctionId;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.businessFunctionId);
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
        final BusinessFunction other = (BusinessFunction) obj;
        if (!Objects.equals(this.businessFunctionId, other.businessFunctionId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BusinessFunction{" + "businessFunctionId=" + businessFunctionId + ", label=" + label + '}';
    }

}
