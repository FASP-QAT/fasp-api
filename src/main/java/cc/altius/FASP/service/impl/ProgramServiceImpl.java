/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.HealthAreaDao;
import cc.altius.FASP.dao.OrganisationDao;
import cc.altius.FASP.dao.ProcurementAgentDao;
import cc.altius.FASP.dao.ProgramDao;
import cc.altius.FASP.dao.RealmDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.ErpOrderAutocompleteDTO;
import cc.altius.FASP.model.DTO.ManualTaggingDTO;
import cc.altius.FASP.model.DTO.ManualTaggingOrderDTO;
import cc.altius.FASP.model.DTO.ProgramDTO;
import cc.altius.FASP.model.LoadProgram;
import cc.altius.FASP.model.ProcurementAgent;
import cc.altius.FASP.model.Program;
import cc.altius.FASP.model.ProgramInitialize;
import cc.altius.FASP.model.ProgramPlanningUnit;
import cc.altius.FASP.model.ProgramPlanningUnitProcurementAgentPrice;
import cc.altius.FASP.model.Realm;
import cc.altius.FASP.model.RealmCountry;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.service.AclService;
import cc.altius.FASP.service.ProgramService;
import cc.altius.FASP.service.RealmCountryService;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author altius
 */
@Service
public class ProgramServiceImpl implements ProgramService {

    @Autowired
    private ProgramDao programDao;
    @Autowired
    private RealmCountryService realmCountryService;
    @Autowired
    private RealmDao realmDao;
    @Autowired
    private HealthAreaDao healthAreaDao;
    @Autowired
    private OrganisationDao organisationDao;
    @Autowired
    private ProcurementAgentDao procurementAgentDao;
    @Autowired
    private AclService aclService;

    @Override
    public List<ProgramDTO> getProgramListForDropdown(CustomUserDetails curUser) {
        return this.programDao.getProgramListForDropdown(curUser);
    }

    @Override
    public int addProgram(Program p, CustomUserDetails curUser) {
        p.setRealmCountry(this.realmCountryService.getRealmCountryById(p.getRealmCountry().getRealmCountryId(), curUser));
        if (this.aclService.checkAccessForUser(
                curUser,
                p.getRealmCountry().getRealm().getRealmId(),
                p.getRealmCountry().getRealmCountryId(),
                p.getHealthArea().getId(),
                p.getOrganisation().getId(),
                0)) {
            RealmCountry rc = this.realmCountryService.getRealmCountryById(p.getRealmCountry().getRealmCountryId(), curUser);
            String programCode = rc.getCountry().getCountryCode() + "-" + this.healthAreaDao.getHealthAreaById(p.getHealthArea().getId(), curUser).getHealthAreaCode() + "-" + this.organisationDao.getOrganisationById(p.getOrganisation().getId(), curUser).getOrganisationCode();
            if (p.getProgramCode() != null && !p.getProgramCode().isBlank()) {
                programCode += "-" + p.getProgramCode();
            }
            p.setProgramCode(programCode);
            return this.programDao.addProgram(p, rc.getRealm().getRealmId(), curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public int updateProgram(Program p, CustomUserDetails curUser) {
        Program curProg = this.getProgramById(p.getProgramId(), curUser);
        if (curProg == null) {
            throw new EmptyResultDataAccessException(1);
        }
        if (this.aclService.checkAccessForUser(
                curUser,
                curProg.getRealmCountry().getRealm().getRealmId(),
                curProg.getRealmCountry().getRealmCountryId(),
                curProg.getHealthArea().getId(),
                curProg.getOrganisation().getId(),
                curProg.getProgramId())) {
            return this.programDao.updateProgram(p, curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public List<Program> getProgramListForProgramIds(String[] programIds, CustomUserDetails curUser) {
        return this.programDao.getProgramListForProgramIds(programIds, curUser);
    }

    @Override
    public List<Program> getProgramList(CustomUserDetails curUser, boolean active) {
        return this.programDao.getProgramList(curUser, active);
    }

    @Override
    public List<Program> getProgramList(int realmId, CustomUserDetails curUser) {
        Realm r = this.realmDao.getRealmById(realmId, curUser);
        if (r == null) {
            throw new EmptyResultDataAccessException(1);
        }
        if (this.aclService.checkRealmAccessForUser(curUser, realmId)) {
            return this.programDao.getProgramList(realmId, curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public Program getProgramById(int programId, CustomUserDetails curUser) {
        Program p = this.programDao.getProgramById(programId, curUser);
        if (p == null) {
            throw new AccessDeniedException("Access denied");
        }
        if (this.aclService.checkProgramAccessForUser(curUser, p.getRealmCountry().getRealm().getRealmId(), p.getProgramId(), p.getHealthArea().getId(), p.getOrganisation().getId())) {
            return p;
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public List<ProgramPlanningUnit> getPlanningUnitListForProgramId(int programId, boolean active, CustomUserDetails curUser) {
        Program p = this.programDao.getProgramById(programId, curUser);
        if (this.aclService.checkProgramAccessForUser(curUser, p.getRealmCountry().getRealm().getRealmId(), programId, p.getHealthArea().getId(), p.getOrganisation().getId())) {
            return this.programDao.getPlanningUnitListForProgramId(programId, active, curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public List<SimpleObject> getPlanningUnitListForProgramIds(Integer[] programIds, CustomUserDetails curUser) {
        StringBuilder programList = new StringBuilder();
        for (int programId : programIds) {
            Program p = this.programDao.getProgramById(programId, curUser);
            if (this.aclService.checkProgramAccessForUser(curUser, p.getRealmCountry().getRealm().getRealmId(), programId, p.getHealthArea().getId(), p.getOrganisation().getId())) {
                programList.append("'").append(programId).append("',");
            } else {
                throw new AccessDeniedException("Access denied");
            }
        }
        if (programList.length() > 0) {
            programList.setLength(programList.length() - 1);
            return this.programDao.getPlanningUnitListForProgramIds(programList.toString(), curUser);
        } else {
            return new LinkedList<>();
        }

    }

    @Override
    public int saveProgramPlanningUnit(ProgramPlanningUnit[] programPlanningUnits, CustomUserDetails curUser) {
        for (ProgramPlanningUnit ppu : programPlanningUnits) {
            Program p = this.programDao.getProgramById(ppu.getProgram().getId(), curUser);
            if (!this.aclService.checkProgramAccessForUser(curUser, p.getRealmCountry().getRealm().getRealmId(), p.getProgramId(), p.getHealthArea().getId(), p.getOrganisation().getId())) {
                throw new AccessDeniedException("Access denied");
            }
        }
        return this.programDao.saveProgramPlanningUnit(programPlanningUnits, curUser);
    }

    @Override
    public List<ProgramPlanningUnitProcurementAgentPrice> getProgramPlanningUnitProcurementAgentList(int programPlanningUnitId, boolean active, CustomUserDetails curUser) {
        return this.programDao.getProgramPlanningUnitProcurementAgentList(programPlanningUnitId, active, curUser);
    }

    @Override
    public int saveProgramPlanningUnitProcurementAgentPrice(ProgramPlanningUnitProcurementAgentPrice[] programPlanningUnitProcurementAgentPrices, CustomUserDetails curUser) {
        for (ProgramPlanningUnitProcurementAgentPrice papup : programPlanningUnitProcurementAgentPrices) {
            ProcurementAgent pa = this.procurementAgentDao.getProcurementAgentById(papup.getProcurementAgent().getId(), curUser);
            if (!this.aclService.checkRealmAccessForUser(curUser, pa.getRealm().getId())) {
                throw new AccessDeniedException("Access denied");
            }
        }
        return this.programDao.saveProgramPlanningUnitProcurementAgentPrice(programPlanningUnitProcurementAgentPrices, curUser);
    }

    @Override
    public List<Program> getProgramListForSync(String lastSyncDate, CustomUserDetails curUser) {
        return this.programDao.getProgramListForSync(lastSyncDate, curUser);
    }

    @Override
    public List<ProgramPlanningUnit> getProgramPlanningUnitListForSync(String lastSyncDate, CustomUserDetails curUser) {
        return this.programDao.getProgramPlanningUnitListForSync(lastSyncDate, curUser);
    }

    @Override
    public List<ProgramPlanningUnit> getPlanningUnitListForProgramAndCategoryId(int programId, int productCategoryId, boolean active, CustomUserDetails curUser) {
        Program p = this.programDao.getProgramById(programId, curUser);
        if (this.aclService.checkProgramAccessForUser(curUser, p.getRealmCountry().getRealm().getRealmId(), programId, p.getHealthArea().getId(), p.getOrganisation().getId())) {
            return this.programDao.getPlanningUnitListForProgramAndCategoryId(programId, productCategoryId, active, curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    @Transactional
    public int addProgramInitialize(ProgramInitialize program, CustomUserDetails curUser) {
        RealmCountry rc = this.realmCountryService.getRealmCountryById(program.getRealmCountry().getRealmCountryId(), curUser);
        String programCode = rc.getCountry().getCountryCode() + "-" + this.healthAreaDao.getHealthAreaById(program.getHealthArea().getId(), curUser).getHealthAreaCode() + "-" + this.organisationDao.getOrganisationById(program.getOrganisation().getId(), curUser).getOrganisationCode();
        if (program.getProgramCode() != null && !program.getProgramCode().isBlank()) {
            programCode += "-" + program.getProgramCode();
        }
        program.setProgramCode(programCode);
        int programId = this.programDao.addProgram(program, rc.getRealm().getRealmId(), curUser);
        for (ProgramPlanningUnit ppu : program.getProgramPlanningUnits()) {
            ppu.getProgram().setId(programId);
        }
        this.programDao.saveProgramPlanningUnit(program.getProgramPlanningUnits(), curUser);
        return programId;
    }

    @Override
    public Program getProgramList(int realmId, int programId, int versionId) {
        return this.programDao.getProgramList(realmId, programId, versionId);
    }

    @Override
    public List<ManualTaggingDTO> getShipmentListForManualTagging(int programId, int planningUnitId) {
        return this.programDao.getShipmentListForManualTagging(programId, planningUnitId);
    }

    @Override
    public List<ManualTaggingOrderDTO> getOrderDetailsByOrderNoAndPrimeLineNo(String roNoOrderNo, int searchId, int programId, int planningUnitId) {
        return this.programDao.getOrderDetailsByOrderNoAndPrimeLineNo(roNoOrderNo, searchId, programId, planningUnitId);
    }

    @Override
    public int linkShipmentWithARTMIS(ManualTaggingOrderDTO manualTaggingOrderDTO, CustomUserDetails curUser) {
        try {
            return this.programDao.linkShipmentWithARTMIS(manualTaggingOrderDTO, curUser);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<ManualTaggingDTO> getShipmentListForDelinking(int programId, int planningUnitId) {
        return this.programDao.getShipmentListForDelinking(programId, planningUnitId);
    }

    @Override
    public void delinkShipment(ManualTaggingOrderDTO erpOrderDTO, CustomUserDetails curUser) {
        this.programDao.delinkShipment(erpOrderDTO, curUser);
    }

    @Override
    public List<LoadProgram> getLoadProgram(CustomUserDetails curUser) {
        return this.programDao.getLoadProgram(curUser);
    }

    @Override
    public LoadProgram getLoadProgram(int programId, int page, CustomUserDetails curUser) {
        return this.programDao.getLoadProgram(programId, page, curUser);
    }

    @Override
    public boolean validateProgramCode(int realmId, int programId, String programCode, CustomUserDetails curUser) {
        if (curUser.getRealm().getRealmId() != realmId) {
            throw new AccessDeniedException("Access denied");
        }
        return this.programDao.validateProgramCode(realmId, programId, programCode, curUser);
    }

    @Override
    public List<Program> getProgramListForSyncProgram(String programIdsString, CustomUserDetails curUser) {
        if (programIdsString.length() > 0) {
            return this.programDao.getProgramListForSyncProgram(programIdsString, curUser);
        } else {
            return new LinkedList<>();
        }
    }

    @Override
    public List<ProgramPlanningUnit> getProgramPlanningUnitListForSyncProgram(String programIdsString, CustomUserDetails curUser) {
        if (programIdsString.length() > 0) {
            return this.programDao.getProgramPlanningUnitListForSyncProgram(programIdsString, curUser);
        } else {
            return new LinkedList<>();
        }
    }

    @Override
    public List<ErpOrderAutocompleteDTO> getErpOrderSearchData(String term, int searchId, int programId, int planningUnitId) {
        return this.programDao.getErpOrderSearchData(term, searchId, programId, planningUnitId);
    }

    @Override
    public String getSupplyPlanReviewerList(int programId, CustomUserDetails curUser) {
        return this.programDao.getSupplyPlanReviewerList(programId, curUser);
    }

}
