/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.HealthAreaDao;
import cc.altius.FASP.dao.OrganisationDao;
import cc.altius.FASP.dao.ProgramDao;
import cc.altius.FASP.dao.RealmDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.ProgramDTO;
import cc.altius.FASP.model.Program;
import cc.altius.FASP.model.ProgramInitialize;
import cc.altius.FASP.model.ProgramPlanningUnit;
import cc.altius.FASP.model.Realm;
import cc.altius.FASP.service.AclService;
import cc.altius.FASP.service.ProgramService;
import cc.altius.FASP.service.RealmCountryService;
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
            String programCode = this.realmCountryService.getRealmCountryById(p.getRealmCountry().getRealmCountryId(), curUser).getCountry().getCountryCode() + "-" + this.healthAreaDao.getHealthAreaById(p.getHealthArea().getId(), curUser).getHealthAreaCode() + "-" + this.organisationDao.getOrganisationById(p.getOrganisation().getId(), curUser).getOrganisationCode();
            p.setProgramCode(programCode);
            return this.programDao.addProgram(p, curUser);
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
    public List<Program> getProgramList(CustomUserDetails curUser) {
        return this.programDao.getProgramList(curUser);
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
            throw new EmptyResultDataAccessException(1);
        }
        if (this.aclService.checkRealmAccessForUser(curUser, p.getRealmCountry().getRealm().getRealmId())) {
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
        String programCode = this.realmCountryService.getRealmCountryById(program.getRealmCountry().getRealmCountryId(), curUser).getCountry().getCountryCode() + "-" + this.healthAreaDao.getHealthAreaById(program.getHealthArea().getId(), curUser).getHealthAreaCode() + "-" + this.organisationDao.getOrganisationById(program.getOrganisation().getId(), curUser).getOrganisationCode();
        program.setProgramCode(programCode);
        int programId = this.programDao.addProgram(program, curUser);
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

}
