/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.web.controller;

import cc.altius.FASP.dao.ProgramDataDao;
import cc.altius.FASP.dao.impl.ImportProductCatalogueDaoImpl;
import cc.altius.FASP.model.Program;
import cc.altius.FASP.model.ProgramData;
import cc.altius.FASP.service.ProgramDataService;
import cc.altius.FASP.service.ProgramService;
import cc.altius.utils.DateUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author altius
 */
@Controller
public class ExportSupplyPlanController {

    @Autowired
    private ProgramDataService programDataService;
    @Autowired
    private ProgramService programService;
    @Autowired
    private ProgramDataDao programDataDao;
    @Value("${exportSupplyPlanFilePath}")
    private String EXPORT_SUPPLY_PLAN_FILE_PATH;
    @Value("${versionId}")
    private int VERSION_ID;
    @Value("${realmId}")
    private int REALM_ID;
    private static org.slf4j.Logger LOG = LoggerFactory.getLogger(ExportSupplyPlanController.class);

    @RequestMapping(value = "export")
//    @Scheduled(cron = "00 */05 * * * *")
    public void exportSupplyPlan() {
        try {
            String curDate = DateUtils.getCurrentDateString(DateUtils.EST, DateUtils.YMD);
            List<Program> programList = this.programService.getProgramList(REALM_ID);
            List<ProgramData> programDatas = new ArrayList<>();
            for (Program p : programList) {
                ProgramData pd = new ProgramData(p);
                pd.setRequestedProgramVersion(VERSION_ID);
                pd.setConsumptionList(this.programDataDao.getConsumptionList(p.getProgramId(), VERSION_ID));
                pd.setInventoryList(this.programDataDao.getInventoryList(p.getProgramId(), VERSION_ID));
                pd.setShipmentList(this.programDataDao.getShipmentList(p.getProgramId(), VERSION_ID));
                programDatas.add(pd);
            }
            Gson gson = new Gson();
            Type typeList = new TypeToken<List>() {
            }.getType();
            String json = gson.toJson(programDatas, typeList);
            String path = EXPORT_SUPPLY_PLAN_FILE_PATH + "SUPPLY_PLAN_" + curDate;
            FileWriter fileWriter = new FileWriter(path);
            fileWriter.write(json);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException ex) {
            LOG.error("Error occured while exporting supply plan---" + ex);
        }

    }
}
