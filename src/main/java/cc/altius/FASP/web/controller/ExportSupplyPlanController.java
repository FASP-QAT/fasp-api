/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.web.controller;

import cc.altius.FASP.dao.ProgramDataDao;
import cc.altius.FASP.model.EmailTemplate;
import cc.altius.FASP.model.Emailer;
import cc.altius.FASP.model.Program;
import cc.altius.FASP.model.ProgramData;
import cc.altius.FASP.service.EmailService;
import cc.altius.FASP.service.ProgramDataService;
import cc.altius.FASP.service.ProgramService;
import cc.altius.utils.DateUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
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
    @Autowired
    private EmailService emailService;
    @Value("${exportSupplyPlanFilePath}")
    private String EXPORT_SUPPLY_PLAN_FILE_PATH;
    @Value("${versionId}")
    private int VERSION_ID;
    @Value("${realmId}")
    private int REALM_ID;
    private static org.slf4j.Logger LOG = LoggerFactory.getLogger(ExportSupplyPlanController.class);

    @RequestMapping(value = "exportSupplyPlan")
//    @Scheduled(cron = "00 */05 * * * *")
    public void exportSupplyPlan() {
        EmailTemplate emailTemplate = this.emailService.getEmailTemplateByEmailTemplateId(3);
        String[] subjectParam = new String[]{};
        String[] bodyParam = null;
        Emailer emailer = new Emailer();
        try {

            String curDate = DateUtils.getCurrentDateString(DateUtils.EST, DateUtils.YMD);
            Gson gson = new Gson();
            Type type = new TypeToken<ProgramData>() {
            }.getType();
            String path, json;
            List<Program> programList = this.programService.getProgramList(REALM_ID);
            for (Program p : programList) {
                ProgramData pd = new ProgramData(p);
                pd.setRequestedProgramVersion(VERSION_ID);
                pd.setConsumptionList(this.programDataDao.getConsumptionList(p.getProgramId(), VERSION_ID));
                pd.setInventoryList(this.programDataDao.getInventoryList(p.getProgramId(), VERSION_ID));
                pd.setShipmentList(this.programDataDao.getShipmentList(p.getProgramId(), VERSION_ID));
                json = gson.toJson(pd, type);
                path = EXPORT_SUPPLY_PLAN_FILE_PATH + "SUPPLY_PLAN_" + p.getProgramId() + "_" + curDate;
                FileWriter fileWriter = new FileWriter(path);
                fileWriter.write(json);
                fileWriter.flush();
                fileWriter.close();
            }
        } catch (FileNotFoundException e) {
            subjectParam = new String[]{"Export supply plan", "File not found"};
            bodyParam = new String[]{"Export supply plan", "02-06-2020 12:50 PM IST", "File not found", e.getMessage()};
            emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), "anchal.c@altius.cc,shubham.y@altius.cc,priti.p@altius.cc,sameer.g@altiusbpo.com", "shubham.y@altius.cc,priti.p@altius.cc,sameer.g@altiusbpo.com", subjectParam, bodyParam);
            int emailerId = this.emailService.saveEmail(emailer);
            emailer.setEmailerId(emailerId);
            this.emailService.sendMail(emailer);
            System.out.println("file not found exception occured-------------------------------------");
            e.printStackTrace();
        } catch (IOException e) {
            subjectParam = new String[]{"Export supply plan", "Input/Output error"};
            bodyParam = new String[]{"Export supply plan", "02-06-2020 12:50PM IST", "Input/Output error", e.getMessage()};
            emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), "anchal.c@altius.cc", "shubham.y@altius.cc,priti.p@altius.cc,sameer.g@altiusbpo.com", subjectParam, bodyParam);
            int emailerId = this.emailService.saveEmail(emailer);
            emailer.setEmailerId(emailerId);
            this.emailService.sendMail(emailer);
            System.out.println("IO exception occured-------------------------------------");
            e.printStackTrace();
        } catch (Exception e) {
            subjectParam = new String[]{"Export supply plan", e.getClass().toString()};
            bodyParam = new String[]{"Export supply plan", "02-06-2020 12:50PM IST", e.getClass().toString(), e.getMessage()};
            emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), "anchal.c@altius.cc", "shubham.y@altius.cc,priti.p@altius.cc,sameer.g@altiusbpo.com", subjectParam, bodyParam);
            int emailerId = this.emailService.saveEmail(emailer);
            emailer.setEmailerId(emailerId);
            this.emailService.sendMail(emailer);
            System.out.println("General exception occured-------------------------------------");
            e.printStackTrace();
        }

    }
}
