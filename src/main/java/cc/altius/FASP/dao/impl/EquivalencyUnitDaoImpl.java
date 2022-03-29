/*  
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.LabelDao;
import cc.altius.FASP.dao.EquivalencyUnitDao;
import cc.altius.FASP.exception.CouldNotSaveException;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.EquivalencyUnit;
import cc.altius.FASP.model.EquivalencyUnitMapping;
import cc.altius.FASP.model.LabelConstants;
import cc.altius.FASP.model.rowMapper.EquivalencyUnitMappingResultSetExtractor;
import cc.altius.FASP.model.rowMapper.EquivalencyUnitListResultSetExtractor;
import cc.altius.FASP.model.rowMapper.EquivalencyUnitResultSetExtractor;
import cc.altius.FASP.service.AclService;
import cc.altius.utils.DateUtils;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author akil
 */
@Repository
public class EquivalencyUnitDaoImpl implements EquivalencyUnitDao {

    private DataSource dataSource;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private LabelDao labelDao;
    @Autowired
    private AclService aclService;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }
    private static final String EQUVALENCY_UNIT_SELECT = "SELECT  "
            + "    eu.EQUIVALENCY_UNIT_ID,  "
            + "    eu.LABEL_ID, eu.LABEL_EN, eu.LABEL_FR, eu.LABEL_SP, eu.LABEL_PR, "
            + "    eu.ACTIVE, eu.CREATED_DATE, cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, eu.`LAST_MODIFIED_DATE`, eu.LAST_MODIFIED_BY, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, "
            + "    r.REALM_ID, r.LABEL_ID `REALM_LABEL_ID`, r.LABEL_EN `REALM_LABEL_EN`, r.LABEL_FR `REALM_LABEL_FR`, r.LABEL_SP `REALM_LABEL_SP`, r.LABEL_PR `REALM_LABEL_PR`, r.REALM_CODE, "
            + "    ha.HEALTH_AREA_ID, ha.LABEL_ID `HA_LABEL_ID`, ha.LABEL_EN `HA_LABEL_EN`, ha.LABEL_FR `HA_LABEL_FR`, ha.LABEL_SP `HA_LABEL_SP`, ha.LABEL_PR `HA_LABEL_PR`, ha.HEALTH_AREA_CODE "
            + "FROM vw_equivalency_unit eu "
            + "LEFT JOIN vw_realm r ON eu.REALM_ID=r.REALM_ID "
            + "LEFT JOIN vw_health_area ha on FIND_IN_SET(ha.HEALTH_AREA_ID,eu.HEALTH_AREA_ID) "
            + "LEFT JOIN us_user cb ON cb.USER_ID=eu.CREATED_BY "
            + "LEFT JOIN us_user lmb ON lmb.USER_ID=eu.LAST_MODIFIED_BY ";
    private static final String EQUIVALENCY_UNIT_MAPPING_SELECT = "SELECT  "
            + "    eum.EQUIVALENCY_UNIT_MAPPING_ID, eum.CONVERT_TO_EU, eum.NOTES,  "
            + "    eum.ACTIVE, eum.CREATED_DATE, cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, eum.`LAST_MODIFIED_DATE`, eum.LAST_MODIFIED_BY, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, "
            + "    eu.EQUIVALENCY_UNIT_ID, eu.LABEL_ID, eu.LABEL_EN, eu.LABEL_FR, eu.LABEL_SP, eu.LABEL_PR, "
            + "    eu.ACTIVE `EU_ACTIVE`, eu.CREATED_DATE `EU_CREATED_DATE`, eucb.USER_ID `EU_CB_USER_ID`, eucb.USERNAME `EU_CB_USERNAME`, eu.`LAST_MODIFIED_DATE` `EU_LAST_MODIFIED_DATE`, eu.LAST_MODIFIED_BY `EU_LAST_MODIFIED_BY`, eulmb.USER_ID `EU_LMB_USER_ID`, eulmb.USERNAME `EU_LMB_USERNAME`, "
            + "    r.REALM_ID, r.LABEL_ID `REALM_LABEL_ID`, r.LABEL_EN `REALM_LABEL_EN`, r.LABEL_FR `REALM_LABEL_FR`, r.LABEL_SP `REALM_LABEL_SP`, r.LABEL_PR `REALM_LABEL_PR`, r.REALM_CODE, "
            + "    ha.HEALTH_AREA_ID, ha.LABEL_ID `HA_LABEL_ID`, ha.LABEL_EN `HA_LABEL_EN`, ha.LABEL_FR `HA_LABEL_FR`, ha.LABEL_SP `HA_LABEL_SP`, ha.LABEL_PR `HA_LABEL_PR`, ha.HEALTH_AREA_CODE, "
            + "    fu.FORECASTING_UNIT_ID, fu.LABEL_ID `FU_LABEL_ID`, fu.LABEL_EN `FU_LABEL_EN`, fu.LABEL_FR `FU_LABEL_FR`, fu.LABEL_SP `FU_LABEL_SP`, fu.LABEL_PR `FU_LABEL_PR`, "
            + "    u.UNIT_ID, u.LABEL_ID `U_LABEL_ID`, u.LABEL_EN `U_LABEL_EN`, u.LABEL_FR `U_LABEL_FR`, u.LABEL_SP `U_LABEL_SP`, u.LABEL_PR `U_LABEL_PR`, u.UNIT_CODE, "
            + "    tc.TRACER_CATEGORY_ID, tc.LABEL_ID `TC_LABEL_ID`, tc.LABEL_EN `TC_LABEL_EN`, tc.LABEL_FR `TC_LABEL_FR`, tc.LABEL_SP `TC_LABEL_SP`, tc.LABEL_PR `TC_LABEL_PR`, "
            + "    p.PROGRAM_ID, p.LABEL_ID `P_LABEL_ID`, p.LABEL_EN `P_LABEL_EN`, p.LABEL_FR `P_LABEL_FR`, p.LABEL_SP `P_LABEL_SP`, p.LABEL_PR `P_LABEL_PR`, p.PROGRAM_CODE "
            + "FROM rm_equivalency_unit_mapping eum "
            + "LEFT JOIN vw_equivalency_unit eu ON eum.EQUIVALENCY_UNIT_ID=eu.EQUIVALENCY_UNIT_ID "
            + "LEFT JOIN vw_health_area ha ON FIND_IN_SET(ha.HEALTH_AREA_ID, eu.HEALTH_AREA_ID) "
            + "LEFT JOIN vw_realm r ON eu.REALM_ID=r.REALM_ID "
            + "LEFT JOIN us_user eucb ON eucb.USER_ID=eu.CREATED_BY "
            + "LEFT JOIN us_user eulmb ON eulmb.USER_ID=eu.LAST_MODIFIED_BY "
            + "LEFT JOIN us_user cb ON cb.USER_ID=eum.CREATED_BY "
            + "LEFT JOIN us_user lmb ON lmb.USER_ID=eum.LAST_MODIFIED_BY "
            + "LEFT JOIN vw_forecasting_unit fu ON eum.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID "
            + "LEFT JOIN vw_unit u ON fu.UNIT_ID=u.UNIT_ID "
            + "LEFT JOIN vw_tracer_category tc ON fu.TRACER_CATEGORY_ID=tc.TRACER_CATEGORY_ID "
            + "LEFT JOIN vw_dataset p ON p.PROGRAM_ID=eum.PROGRAM_ID "
            + "WHERE TRUE ";

    @Override
    public List<EquivalencyUnit> getEquivalencyUnitList(boolean active, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(EQUVALENCY_UNIT_SELECT);
        if (active) {
            sqlStringBuilder.append(" AND eu.ACTIVE ");
        }
        sqlStringBuilder.append("ORDER BY eu.LABEL_EN");
        Map<String, Object> params = new HashMap<>();
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "r", curUser);
        return namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new EquivalencyUnitListResultSetExtractor());
    }

    @Override
    @Transactional
    public int addAndUpdateEquivalencyUnit(List<EquivalencyUnit> equivalencyUnitList, CustomUserDetails curUser) {
        SimpleJdbcInsert si = new SimpleJdbcInsert(dataSource).withTableName("rm_equivalency_unit").usingGeneratedKeyColumns("EQUIVALENCY_UNIT_ID");
        SimpleJdbcInsert siHa = new SimpleJdbcInsert(dataSource).withTableName("rm_equivalency_unit_health_area");
        Date dt = DateUtils.getCurrentDateObject(DateUtils.EST);
        int rows = 0;
        String sql = "UPDATE rm_equivalency_unit eu "
                + "LEFT JOIN ap_label l ON eu.LABEL_ID=l.LABEL_ID "
                + "SET "
                + "eu.ACTIVE=:active, "
                + "eu.LAST_MODIFIED_DATE=:dt, "
                + "eu.LAST_MODIFIED_BY=:curUser, "
                + "l.LABEL_EN=:labelEn, "
                + "l.LAST_MODIFIED_DATE=:dt, "
                + "l.LAST_MODIFIED_BY=:curUser "
                + "WHERE "
                + "     eu.EQUIVALENCY_UNIT_ID=:equivalencyUnitId AND "
                + "     ( "
                + "         l.LABEL_EN!=:labelEn OR "
                + "         eu.ACTIVE!=:active OR "
                + "         :rowsEffected > 0 "
                + "     )";
        for (EquivalencyUnit ut : equivalencyUnitList) {
            if (ut.getEquivalencyUnitId() == 0) {
                MapSqlParameterSource param = new MapSqlParameterSource();
                param.addValue("LABEL_ID", this.labelDao.addLabel(ut.getLabel(), LabelConstants.RM_EQUIVALENCY_UNIT, curUser.getUserId()));
                param.addValue("REALM_ID", curUser.getRealm().getRealmId());
                param.addValue("ACTIVE", 1);
                param.addValue("CREATED_BY", curUser.getUserId());
                param.addValue("CREATED_DATE", dt);
                param.addValue("LAST_MODIFIED_BY", curUser.getUserId());
                param.addValue("LAST_MODIFIED_DATE", dt);
                ut.setEquivalencyUnitId(si.executeAndReturnKey(param).intValue());
                rows++;
                ut.getHealthAreaList().forEach(ha -> {
                    MapSqlParameterSource haParam = new MapSqlParameterSource();
                    haParam.addValue("EQUIVALENCY_UNIT_ID", ut.getEquivalencyUnitId());
                    haParam.addValue("HEALTH_AREA_ID", ha.getId());
                    siHa.execute(haParam);
                });
            } else {
                Map<String, Object> param = new HashMap<>();
                param.put("equivalencyUnitId", ut.getEquivalencyUnitId());
                String haIdList = "0";
                if (ut.getHealthAreaList().size() > 0) {
                    haIdList = String.join(",", ut.getHealthAreaList().stream().map(h -> h.getIdString()).toArray(String[]::new));
                }
                int rowsEffected = namedParameterJdbcTemplate.update("DELETE euha.* FROM rm_equivalency_unit_health_area euha where euha.EQUIVALENCY_UNIT_ID=:equivalencyUnitId and euha.HEALTH_AREA_ID not in (" + haIdList + ");", param);
                List<Integer> curHaList = namedParameterJdbcTemplate.queryForList("SELECT euha.HEALTH_AREA_ID FROM rm_equivalency_unit_health_area euha WHERE euha.EQUIVALENCY_UNIT_ID=:equivalencyUnitId", param, Integer.class);
                List<MapSqlParameterSource> paramList = new LinkedList<>();
                ut.getHealthAreaList().forEach(ha -> {
                    if (!curHaList.contains(ha.getId())) {
                        MapSqlParameterSource haParam = new MapSqlParameterSource();
                        haParam.addValue("EQUIVALENCY_UNIT_ID", ut.getEquivalencyUnitId());
                        haParam.addValue("HEALTH_AREA_ID", ha.getId());
                        paramList.add(haParam);
                    }
                });
                int[] updatedRows;
                if (paramList.size() > 0) {
                    updatedRows = siHa.executeBatch(paramList.toArray(new MapSqlParameterSource[paramList.size()]));
                    rowsEffected += Arrays.stream(updatedRows).filter(i -> i == 1).count();
                }
                param.clear();
                param.put("equivalencyUnitId", ut.getEquivalencyUnitId());
                param.put("labelEn", ut.getLabel().getLabel_en());
                param.put("active", ut.isActive());
                param.put("curUser", curUser.getUserId());
                param.put("dt", dt);
                param.put("rowsEffected", rowsEffected);
                if (namedParameterJdbcTemplate.update(sql, param) > 0) {
                    rows++;
                }
            }
        }
        return rows;
    }

    @Override
    public List<EquivalencyUnitMapping> getEquivalencyUnitMappingList(boolean active, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(EQUIVALENCY_UNIT_MAPPING_SELECT);
        if (active) {
            sqlStringBuilder.append(" AND eum.ACTIVE ");
        }
        sqlStringBuilder.append("ORDER BY eu.LABEL_EN");
        Map<String, Object> params = new HashMap<>();
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "r", curUser);
        return namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new EquivalencyUnitMappingResultSetExtractor());
    }

    @Override
    @Transactional
    public int addAndUpdateEquivalencyUnitMapping(List<EquivalencyUnitMapping> equivalencyUnitMappingList, CustomUserDetails curUser) throws CouldNotSaveException {
        SimpleJdbcInsert si = new SimpleJdbcInsert(dataSource).withTableName("rm_equivalency_unit_mapping");
        Date dt = DateUtils.getCurrentDateObject(DateUtils.EST);
        String sql = "UPDATE rm_equivalency_unit_mapping eum "
                + "SET "
                + "eum.EQUIVALENCY_UNIT_ID=:equivalencyUnitId, "
                + "eum.FORECASTING_UNIT_ID=:forecastingUnitId, "
                + "eum.ACTIVE=:active, "
                + "eum.CONVERT_TO_EU=:convertToEu, "
                + "eum.PROGRAM_ID=:programId, "
                + "eum.NOTES=:notes, "
                + "eum.LAST_MODIFIED_DATE=:dt, "
                + "eum.LAST_MODIFIED_BY=:curUser "
                + "WHERE "
                + "     eum.EQUIVALENCY_UNIT_MAPPING_ID=:equivalencyUnitMappingId AND "
                + "     ("
                + "         eum.EQUIVALENCY_UNIT_ID!=:equivalencyUnitId OR "
                + "         eum.FORECASTING_UNIT_ID!=:forecastingUnitId OR "
                + "         eum.ACTIVE!=:active OR "
                + "         eum.CONVERT_TO_EU!=:convertToEu OR "
                + "         eum.NOTES!=:notes OR "
                + "         eum.PROGRAM_ID!=:programId OR "
                + "         (eum.PROGRAM_ID IS NULL AND :programId IS NOT NULL) OR "
                + "         (eum.PROGRAM_ID IS NOT NULL AND :programId IS NULL) "
                + "     )";
        int updatedRows = 0;

        String sqlDuplicateCheck = "SELECT COUNT(*) FROM rm_equivalency_unit_mapping eum WHERE eum.EQUIVALENCY_UNIT_ID=:equivalencyUnitId AND eum.FORECASTING_UNIT_ID=:forecastingUnitId AND eum.REALM_ID=:realmId";
        for (EquivalencyUnitMapping eum : equivalencyUnitMappingList) {
            // Check if the Equivalency Unit is duplicate across the Realm
            if ((eum.getProgram() == null || eum.getProgram().getId() == null || eum.getProgram().getId() == 0) && eum.getEquivalencyUnitMappingId() == 0) {
                MapSqlParameterSource params = new MapSqlParameterSource();
                params.addValue("equivalencyUnitId", eum.getEquivalencyUnit().getEquivalencyUnitId());
                params.addValue("forecastingUnitId", eum.getForecastingUnit().getId());
                params.addValue("realmId", curUser.getRealm().getRealmId());
                if (this.namedParameterJdbcTemplate.queryForObject(sqlDuplicateCheck, params, Integer.class) > 0) {
                    throw new CouldNotSaveException("Duplicate Key " + eum.getEquivalencyUnit().getEquivalencyUnitId() + "-" + eum.getForecastingUnit().getId() + "-" + curUser.getRealm().getRealmId());
                }

            }
            if (eum.getEquivalencyUnitMappingId() == 0) {
                MapSqlParameterSource params = new MapSqlParameterSource();
                params.addValue("REALM_ID", curUser.getRealm().getRealmId());
                params.addValue("EQUIVALENCY_UNIT_ID", eum.getEquivalencyUnit().getEquivalencyUnitId());
                params.addValue("FORECASTING_UNIT_ID", eum.getForecastingUnit().getId());
                params.addValue("PROGRAM_ID", (eum.getProgram() == null ? null : (eum.getProgram().getId() == 0 ? null : eum.getProgram().getId())));
                params.addValue("NOTES", eum.getNotes());
                params.addValue("CONVERT_TO_EU", eum.getConvertToEu());
                params.addValue("ACTIVE", 1);
                params.addValue("CREATED_BY", curUser.getUserId());
                params.addValue("CREATED_DATE", dt);
                params.addValue("LAST_MODIFIED_BY", curUser.getUserId());
                params.addValue("LAST_MODIFIED_DATE", dt);
                updatedRows += si.execute(params);
            } else {
                MapSqlParameterSource params = new MapSqlParameterSource();
                params.addValue("equivalencyUnitMappingId", eum.getEquivalencyUnitMappingId());
                
                params.addValue("programId", (eum.getProgram() == null ? null : (eum.getProgram().getId() == 0 ? null : eum.getProgram().getId())));
                params.addValue("equivalencyUnitId", eum.getEquivalencyUnit().getEquivalencyUnitId());
                params.addValue("forecastingUnitId", eum.getForecastingUnit().getId());
                params.addValue("active", eum.isActive());
                params.addValue("notes", eum.getNotes());
                params.addValue("convertToEu", eum.getConvertToEu());
                params.addValue("curUser", curUser.getUserId());
                params.addValue("dt", dt);
                updatedRows += namedParameterJdbcTemplate.update(sql, params);
            }
        }
        return updatedRows;
    }

    @Override
    public List<EquivalencyUnitMapping> getEquivalencyUnitMappingListForSync(String programIdsString, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(EQUIVALENCY_UNIT_MAPPING_SELECT).append(" AND eum.ACTIVE ");
        sqlStringBuilder.append(" AND (eum.PROGRAM_ID IS NULL OR eum.PROGRAM_ID in (").append(programIdsString).append("))");
        Map<String, Object> params = new HashMap<>();
        params.put("programIdsString", programIdsString);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "r", curUser);
        return namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new EquivalencyUnitMappingResultSetExtractor());
    }

    @Override
    public EquivalencyUnit getEquivalencyUnitById(int equivalencyUnitId, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(EQUVALENCY_UNIT_SELECT).append(" WHERE eu.EQUIVALENCY_UNIT_ID=:equivalencyUnitId");
        Map<String, Object> params = new HashMap<>();
        params.put("equivalencyUnitId", equivalencyUnitId);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "r", curUser);
        return namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new EquivalencyUnitResultSetExtractor());
    }

}
