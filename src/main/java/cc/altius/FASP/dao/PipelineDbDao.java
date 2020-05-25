/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.Label;
import cc.altius.FASP.model.Program;
import cc.altius.FASP.model.Region;
import cc.altius.FASP.model.pipeline.Pipeline;
import cc.altius.FASP.model.pipeline.PplConsumption;
import cc.altius.FASP.model.pipeline.PplProduct;
import cc.altius.FASP.model.pipeline.PplPrograminfo;
import cc.altius.FASP.model.pipeline.PplShipment;
import cc.altius.FASP.model.pipeline.QatTempConsumption;
import cc.altius.FASP.model.pipeline.QatTempProgramPlanningUnit;
import java.util.List;
import java.util.Map;

/**
 *
 * @author akil
 */
public interface PipelineDbDao {

    public int savePipelineDbData(Pipeline pipeline, CustomUserDetails curUser);

    public List<Map<String, Object>> getPipelineProgramList(CustomUserDetails curUser);

    public PplPrograminfo getPipelineProgramInfoById(int pipelineId, CustomUserDetails curUser);

    public int addQatTempProgram(Program p, CustomUserDetails curUser, int pipelineId);

    public Program getQatTempProgram(CustomUserDetails curUser, int pipelineId);

    public int addQatTempLabel(Label label, int curUser);

    public List<PplProduct> getPipelineProductListById(CustomUserDetails curUser, int pipelineId);

    public List<PplShipment> getPipelineShipmentdataById(int pipelineId, CustomUserDetails curUser);

    public int saveQatTempProgramPlanningUnit(QatTempProgramPlanningUnit[] programPlanningUnits, CustomUserDetails curUser, int pipelineId);

    public List<QatTempProgramPlanningUnit> getQatTempPlanningUnitListByPipelienId(int pipelineId, CustomUserDetails curUser);

    public List<PplConsumption> getPipelineConsumptionById(CustomUserDetails curUser, int pipelineId);

    public List<Region> getQatTempRegionsById(CustomUserDetails curUser, int pipelineId);

    public int saveQatTempConsumption(QatTempConsumption[] consumption, CustomUserDetails curUser, int pipelineId);

    public List<QatTempConsumption> getQatTempConsumptionListByPipelienId(int pipelineId, CustomUserDetails curUser);

}
