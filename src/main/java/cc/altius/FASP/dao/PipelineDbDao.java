/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.pipeline.Pipeline;
import cc.altius.FASP.model.pipeline.PplPrograminfo;
import cc.altius.FASP.model.pipeline.PplShipment;
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
    
     public List<PplShipment> getPipelineShipmentdataById(int pipelineId, CustomUserDetails curUser);
}
