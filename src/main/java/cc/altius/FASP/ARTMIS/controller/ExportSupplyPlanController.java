/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.ARTMIS.controller;

import cc.altius.FASP.dao.ProgramDataDao;
import cc.altius.FASP.model.EmailTemplate;
import cc.altius.FASP.model.Emailer;
import cc.altius.FASP.model.Program;
import cc.altius.FASP.model.ProgramData;
import cc.altius.FASP.model.ProgramVersion;
import cc.altius.FASP.service.EmailService;
import cc.altius.FASP.service.ProgramDataService;
import cc.altius.FASP.service.ProgramService;
import cc.altius.utils.DateUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.BadSqlGrammarException;
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
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "exportSupplyPlan")
//    @Scheduled(cron = "00 */05 * * * *")
    public void exportSupplyPlan() {
        EmailTemplate emailTemplate = this.emailService.getEmailTemplateByEmailTemplateId(4);
        String[] subjectParam = new String[]{};
        String[] bodyParam = null;
        Emailer emailer = new Emailer();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy hh:mm a");
        String date = simpleDateFormat.format(DateUtils.getCurrentDateObject(DateUtils.EST));
        try {

            String curDate = DateUtils.getCurrentDateString(DateUtils.EST, DateUtils.YMD);
            String path, json;
            List<ProgramVersion> programVersionForARTMIS = this.programDataDao.getProgramVersionForARTMIS(REALM_ID);
            System.out.println("programVersionForARTMIS---"+programVersionForARTMIS);
//            List<Program> programList = this.programService.getProgramList(REALM_ID);
            List<ProgramData> programDatas = new ArrayList<>();
            File directory = new File(EXPORT_SUPPLY_PLAN_FILE_PATH);
            String programVersionIds = "";
            if (directory.isDirectory()) {
                for (ProgramVersion p : programVersionForARTMIS) {
                    System.out.println("REALM_ID---"+REALM_ID);
                    System.out.println("program id---"+p.getProgram().getId());
                    System.out.println("version id---"+p.getVersionId());
                    Program program = this.programService.getProgramList(REALM_ID, p.getProgram().getId(), p.getVersionId());
                    System.out.println("program---"+program);
                    ProgramData pd = new ProgramData(program);
                    pd.setRequestedProgramVersion(p.getVersionId());
                    pd.setConsumptionList(this.programDataDao.getConsumptionList(program.getProgramId(), p.getVersionId()));
                    pd.setInventoryList(this.programDataDao.getInventoryList(program.getProgramId(), p.getVersionId()));
                    pd.setShipmentList(this.programDataDao.getShipmentList(program.getProgramId(), p.getVersionId()));
//                    if(p.getProgramId()==1){
                    programDatas.add(pd);
                    programVersionIds += "," + p.getProgramVersionId();
//                    }
//                json = gson.toJson(pd, type);
//                path = EXPORT_SUPPLY_PLAN_FILE_PATH + "SUPPLY_PLAN_" + p.getProgramId() + "_" + curDate + ".json";
//                FileWriter fileWriter = new FileWriter(path);
//                fileWriter.write(json);
//                fileWriter.flush();
//                fileWriter.close();
                }
                Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                Type typeList = new TypeToken<List<ProgramData>>() {
                }.getType();
                json = gson.toJson(programDatas, typeList);
                path = EXPORT_SUPPLY_PLAN_FILE_PATH + "SUPPLY_PLAN_" + curDate + ".json";
                FileWriter fileWriter = new FileWriter(path);
                fileWriter.write(json);
                fileWriter.flush();
                fileWriter.close();
                System.out.println("programVersionIds---"+programVersionIds);
                int ids = this.programDataService.updateSentToARTMISFlag(programVersionIds);
                logger.info(ids + " program version updated");
                logger.info("Export supply plan successful");
            } else {
                subjectParam = new String[]{"supply plan", "Directory does not exists"};
                bodyParam = new String[]{"supply plan", date, "Directory does not exists", "Directory does not exists"};
                emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), "anchal.c@altius.cc,shubham.y@altius.cc,priti.p@altius.cc,sameer.g@altiusbpo.com", "shubham.y@altius.cc,priti.p@altius.cc,sameer.g@altiusbpo.com", subjectParam, bodyParam);
                int emailerId = this.emailService.saveEmail(emailer);
                emailer.setEmailerId(emailerId);
                this.emailService.sendMail(emailer);
                logger.error("Directory does not exists");
            }
        } catch (FileNotFoundException e) {
            subjectParam = new String[]{"supply plan", "File not found"};
            bodyParam = new String[]{"supply plan", date, "File not found", e.getMessage()};
            emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), "anchal.c@altius.cc,shubham.y@altius.cc,priti.p@altius.cc,sameer.g@altiusbpo.com", "shubham.y@altius.cc,priti.p@altius.cc,sameer.g@altiusbpo.com", subjectParam, bodyParam);
            int emailerId = this.emailService.saveEmail(emailer);
            emailer.setEmailerId(emailerId);
            this.emailService.sendMail(emailer);
            logger.error("File not found exception occured", e);
        } catch (IOException e) {
            subjectParam = new String[]{"supply plan", "Input/Output error"};
            bodyParam = new String[]{"supply plan", date, "Input/Output error", e.getMessage()};
            emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), "anchal.c@altius.cc", "shubham.y@altius.cc,priti.p@altius.cc,sameer.g@altiusbpo.com", subjectParam, bodyParam);
            int emailerId = this.emailService.saveEmail(emailer);
            emailer.setEmailerId(emailerId);
            this.emailService.sendMail(emailer);
            logger.error("IO exception occured", e);
        } catch (BadSqlGrammarException e) {
            subjectParam = new String[]{"supply plan", "SQL Exception"};
            bodyParam = new String[]{"supply plan", date, "SQL Exception", e.getMessage()};
            emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), "anchal.c@altius.cc", "shubham.y@altius.cc,priti.p@altius.cc,sameer.g@altiusbpo.com", subjectParam, bodyParam);
            int emailerId = this.emailService.saveEmail(emailer);
            emailer.setEmailerId(emailerId);
            this.emailService.sendMail(emailer);
            logger.error("SQL exception occured", e);
        } catch (Exception e) {
            subjectParam = new String[]{"supply plan", e.getClass().getName().toString()};
            bodyParam = new String[]{"supply plan", date, e.getClass().getName().toString(), e.getMessage()};
            emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), "anchal.c@altius.cc,shubham.y@altius.cc,priti.p@altius.cc,sameer.g@altiusbpo.com", "shubham.y@altius.cc,priti.p@altius.cc,sameer.g@altiusbpo.com", subjectParam, bodyParam);
            int emailerId = this.emailService.saveEmail(emailer);
            emailer.setEmailerId(emailerId);
            this.emailService.sendMail(emailer);
            logger.error("Export supply plan exception occured", e);
        }

    }
}
