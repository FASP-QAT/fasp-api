/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.PipelineDbDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.Program;
import cc.altius.FASP.model.Region;
import cc.altius.FASP.model.Shipment;
import cc.altius.FASP.model.pipeline.Pipeline;
import cc.altius.FASP.model.pipeline.PplConsumption;
import cc.altius.FASP.model.pipeline.PplProduct;
import cc.altius.FASP.model.pipeline.PplPrograminfo;
import cc.altius.FASP.model.pipeline.QatTempConsumption;
import cc.altius.FASP.model.pipeline.QatTempInventory;
import cc.altius.FASP.model.pipeline.QatTempPlanningUnitInventoryCount;
import cc.altius.FASP.model.pipeline.QatTempProgramPlanningUnit;
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
        return this.pipelineDbDao.addQatTempProgram(p, curUser, pipelineId);
    }

    @Override
    public Program getQatTempProgram(CustomUserDetails curUser, int pipelineId) {
        return this.pipelineDbDao.getQatTempProgram(curUser, pipelineId);
    }

    @Override
    public List<PplProduct> getPipelineProductListById(CustomUserDetails curUser, int pipelineId) {
        return this.pipelineDbDao.getPipelineProductListById(curUser, pipelineId);
    }

//    @Override
//    public List<PplShipment> getPipelineShipmentdataById(int pipelineId, CustomUserDetails curUser) {
//        return this.pipelineDbDao.getPipelineShipmentdataById(pipelineId, curUser);
//    }

    @Override
    public int saveQatTempProgramPlanningUnit(QatTempProgramPlanningUnit[] programPlanningUnits, CustomUserDetails curUser, int pipelineId) {
        return this.pipelineDbDao.saveQatTempProgramPlanningUnit(programPlanningUnits, curUser, pipelineId);
    }

    @Override
    public List<QatTempProgramPlanningUnit> getQatTempPlanningUnitListByPipelienId(int pipelineId, CustomUserDetails curUser) {
        return this.pipelineDbDao.getQatTempPlanningUnitListByPipelienId(pipelineId, curUser);
    }

    @Override
    public List<PplConsumption> getPipelineConsumptionById(CustomUserDetails curUser, int pipelineId) {
        return this.pipelineDbDao.getPipelineConsumptionById(curUser, pipelineId);
    }

    @Override
    public List<Region> getQatTempRegionsById(CustomUserDetails curUser, int pipelineId) {
        return this.pipelineDbDao.getQatTempRegionsById(curUser, pipelineId);
    }

    @Override
    public int saveQatTempConsumption(QatTempConsumption[] consumption, CustomUserDetails curUser, int pipelineId) {
        return this.pipelineDbDao.saveQatTempConsumption(consumption, curUser, pipelineId);
    }

    @Override
    public List<QatTempConsumption> getQatTempConsumptionListByPipelienId(int pipelineId, CustomUserDetails curUser) {
        return this.pipelineDbDao.getQatTempConsumptionListByPipelienId(pipelineId, curUser);
    }
    public String getPipelineShipmentdataById(int pipelineId, CustomUserDetails curUser) {
         return this.pipelineDbDao.getPipelineShipmentdataById(pipelineId,curUser);
    }

    @Override
    public int saveShipmentData(int pipelineId, Shipment[] shipments, CustomUserDetails curUser) {
        return this.pipelineDbDao.saveShipmentData(pipelineId, shipments, curUser);
    }

    @Override
    public int finalSaveProgramData(int pipelineId, CustomUserDetails curUser) {
        return this.pipelineDbDao.finalSaveProgramData(pipelineId, curUser);
    }

    @Override
    public String getPipelineInventoryById(CustomUserDetails curUser, int pipelineId) {
        return this.pipelineDbDao.getPipelineInventoryById(curUser, pipelineId);
    }

    @Override
    public int saveQatTempInventory(QatTempInventory[] inventory, CustomUserDetails curUser, int pipelineId) {
        return this.pipelineDbDao.saveQatTempInventory(inventory, curUser, pipelineId);
    }

    @Override
    public List<QatTempPlanningUnitInventoryCount> getQatTempPlanningUnitListInventoryCount(int pipelineId, CustomUserDetails curUser) {
       return this.pipelineDbDao.getQatTempPlanningUnitListInventoryCount(pipelineId, curUser);
    }

    

}
