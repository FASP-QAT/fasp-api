/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.Views;
import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author akil
 */
public class ProgramAndReportingUnit implements Serializable {

    @JsonView(Views.ReportView.class)
    private SimpleCodeObject program;
    @JsonView(Views.ReportView.class)
    private SimpleObject reportingUnit;

    public ProgramAndReportingUnit() {
    }

    public ProgramAndReportingUnit(SimpleCodeObject program, SimpleObject reportingUnit) {
        this.program = program;
        this.reportingUnit = reportingUnit;
    }

    public SimpleCodeObject getProgram() {
        return program;
    }

    public void setProgram(SimpleCodeObject program) {
        this.program = program;
    }

    public SimpleObject getReportingUnit() {
        return reportingUnit;
    }

    public void setReportingUnit(SimpleObject reportingUnit) {
        this.reportingUnit = reportingUnit;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.program);
        hash = 97 * hash + Objects.hashCode(this.reportingUnit);
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
        final ProgramAndReportingUnit other = (ProgramAndReportingUnit) obj;
        if (!Objects.equals(this.program.getCode(), other.program.getCode())) {
            return false;
        }
        return Objects.equals(this.reportingUnit.getId(), other.reportingUnit.getId());
    }

}
