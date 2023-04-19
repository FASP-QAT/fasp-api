/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.ProgramCommonDao;
import cc.altius.FASP.framework.GlobalConstants;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.Program;
import cc.altius.FASP.model.rowMapper.ProgramBasicResultSetExtractor;
import cc.altius.FASP.model.rowMapper.ProgramResultSetExtractor;
import cc.altius.FASP.service.AclService;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author akil
 */
@Repository
public class ProgramCommonDaoImpl implements ProgramCommonDao {

    @Autowired
    private AclService aclService;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Program getProgramById(int programId, int programTypeId, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder;
        if (programTypeId == -1) {
            Map<String, Object> param = new HashMap<>();
            param.put("programId", programId);
            try {
                programTypeId = this.namedParameterJdbcTemplate.queryForObject("SELECT p.PROGRAM_TYPE_ID FROM rm_program p WHERE p.PROGRAM_ID=:programId", param, Integer.class).intValue();
            } catch (EmptyResultDataAccessException erda) {
                return null;
            }
        }
        if (programTypeId == GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN) {
            sqlStringBuilder = new StringBuilder(ProgramDaoImpl.sqlProgramListString);
        } else if (programTypeId == GlobalConstants.PROGRAM_TYPE_DATASET) {
            sqlStringBuilder = new StringBuilder(ProgramDaoImpl.sqlDatasetListString);
        } else {
            return null;
        }
        sqlStringBuilder.append(" AND p.PROGRAM_ID=:programId");
        Map<String, Object> params = new HashMap<>();
        params.put("programId", programId);
        sqlStringBuilder.append(ProgramDaoImpl.sqlOrderBy);
        Program p = this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new ProgramResultSetExtractor());
        if (p == null) {
            return null;
        }
        if (this.aclService.checkProgramAccessForUser(curUser, p.getRealmCountry().getRealm().getRealmId(), p.getProgramId(), p.getHealthAreaIdList(), p.getOrganisation().getId())) {
            return p;
        } else {
            return null;
        }
    }

    @Override
    public Program getBasicProgramById(int programId, int programTypeId, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder;
        if (programTypeId == GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN) {
            sqlStringBuilder = new StringBuilder(ProgramDaoImpl.sqlProgramListBasicString);
        } else if (programTypeId == GlobalConstants.PROGRAM_TYPE_DATASET) {
            sqlStringBuilder = new StringBuilder(ProgramDaoImpl.sqlDatasetListBasicString);
        } else {
            sqlStringBuilder = new StringBuilder(ProgramDaoImpl.sqlAllProgramListBasicString);
        }
        sqlStringBuilder.append(" AND p.PROGRAM_ID=:programId");
        Map<String, Object> params = new HashMap<>();
        params.put("programId", programId);
        sqlStringBuilder.append(ProgramDaoImpl.sqlOrderByBasic);
        System.out.println(sqlStringBuilder.toString());
        System.out.println(params);
        Program p = this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new ProgramBasicResultSetExtractor());
        if (p == null) {
            throw new EmptyResultDataAccessException(1);
        }
        if (this.aclService.checkProgramAccessForUser(curUser, p.getRealmCountry().getRealm().getRealmId(), p.getProgramId(), p.getHealthAreaIdList(), p.getOrganisation().getId())) {
            return p;
        } else {
            return null;
        }
    }

}
