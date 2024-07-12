/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.DTO;

import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleProgram;
import cc.altius.FASP.model.Views;
import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author akil
 */
public class SupplyPlanExportDTO implements Serializable {

    @JsonView(Views.ExportApiView.class)
    private final int programId;
    @JsonView(Views.ExportApiView.class)
    private final int versionId;
    @JsonView(Views.ExportApiView.class)
    private final SimpleProgram program;
    @JsonView(Views.ExportApiView.class)
    private List<SupplyPlanExportPuDTO> planningUnitList;

    public SupplyPlanExportDTO(SimpleProgram program, int versionId) {
        this.program = program;
        this.programId = program.getId();
        this.versionId = versionId;
        this.planningUnitList = new LinkedList<>();
    }

    public int getProgramId() {
        return programId;
    }

    public SimpleCodeObject getProgram() {
        return program;
    }

    public int getVersionId() {
        return versionId;
    }

    public List<SupplyPlanExportPuDTO> getPlanningUnitList() {
        return planningUnitList;
    }

    public void setPlanningUnitList(List<SupplyPlanExportPuDTO> planningUnitList) {
        this.planningUnitList = planningUnitList;
    }
}
