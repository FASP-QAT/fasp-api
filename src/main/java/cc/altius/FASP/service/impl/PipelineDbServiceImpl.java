/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.PipelineDbDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.Program;
import cc.altius.FASP.model.Shipment;
import cc.altius.FASP.model.pipeline.Pipeline;
import cc.altius.FASP.model.pipeline.PplProduct;
import cc.altius.FASP.model.pipeline.PplPrograminfo;
import cc.altius.FASP.model.pipeline.PplShipment;
import cc.altius.FASP.service.PipelineDbService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author akil
 */
@Service
public class PipelineDbServiceImpl implements PipelineDbService {

    @Autowired
    private PipelineDbDao pipelineDbDao;

    @Override
    public int savePipelineDbData(Pipeline pipeline, CustomUserDetails curUser) {
        return this.pipelineDbDao.savePipelineDbData(pipeline, curUser);
    }

    @Override
    public List<Map<String, Object>> getPipelineProgramList(CustomUserDetails curUser) {
        return this.pipelineDbDao.getPipelineProgramList(curUser);
    }

    @Override
    public PplPrograminfo getPipelineProgramInfoById(int pipelineId, CustomUserDetails curUser) {
        return this.pipelineDbDao.getPipelineProgramInfoById(pipelineId, curUser);
    }

    @Override

    public int addQatTempProgram(Program p, CustomUserDetails curUser, int pipelineId) {
        return this.pipelineDbDao.addQatTempProgram(p, curUser,pipelineId);
    }

    @Override
    public Program getQatTempProgram(CustomUserDetails curUser, int pipelineId) {
        return this.pipelineDbDao.getQatTempProgram(curUser,pipelineId);
    }

    @Override
    public List<PplProduct> getPipelineProductListById(CustomUserDetails curUser, int pipelineId) {
        return this.pipelineDbDao.getPipelineProductListById(curUser,pipelineId);
    }

    @Override
    public String getPipelineShipmentdataById(int pipelineId, CustomUserDetails curUser) {
         return this.pipelineDbDao.getPipelineShipmentdataById(pipelineId,curUser);
    }

    @Override
    public int saveShipmentData(int pipelineId, Shipment[] shipments, CustomUserDetails curUser) {
        return this.pipelineDbDao.saveShipmentData(pipelineId, shipments, curUser);
    }

    

}
