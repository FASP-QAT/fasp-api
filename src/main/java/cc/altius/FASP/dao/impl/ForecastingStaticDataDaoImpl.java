/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.rowMapper.SimpleBaseModelRowMapper;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import cc.altius.FASP.dao.ForecastingStaticDataDao;
import cc.altius.FASP.model.ExtrapolationMethod;
import cc.altius.FASP.model.NodeType;
import cc.altius.FASP.model.NodeTypeRowMapper;
import cc.altius.FASP.model.NodeTypeSync;
import cc.altius.FASP.model.SimpleBaseModel;
import cc.altius.FASP.model.rowMapper.ExtrapolationMethodRowMapper;
import cc.altius.FASP.model.rowMapper.NodeTypeSyncResultSetExtractor;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author akil
 */
@Repository
public class ForecastingStaticDataDaoImpl implements ForecastingStaticDataDao {

    private DataSource dataSource;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static String forecastMethodString = "SELECT nt.FORECAST_METHOD_TYPE_ID ID, "
            + "nt.LABEL_ID, nt.LABEL_EN, nt.LABEL_FR, nt.LABEL_SP, nt.LABEL_PR, "
            + "nt.ACTIVE, nt.CREATED_DATE, cb.USER_ID CB_USER_ID, cb.USERNAME CB_USERNAME, nt.LAST_MODIFIED_DATE, lmb.USER_ID LMB_USER_ID, lmb.USERNAME LMB_USERNAME "
            + "FROM vw_forecast_method_type nt "
            + "LEFT JOIN us_user cb ON nt.CREATED_BY=cb.USER_ID "
            + "LEFT JOIN us_user lmb ON nt.LAST_MODIFIED_BY=lmb.USER_ID "
            + "WHERE 1 ";

    private static String usageTypeString = "SELECT ut.USAGE_TYPE_ID ID, "
            + "ut.LABEL_ID, ut.LABEL_EN, ut.LABEL_FR, ut.LABEL_SP, ut.LABEL_PR, "
            + "ut.ACTIVE, ut.CREATED_DATE, cb.USER_ID CB_USER_ID, cb.USERNAME CB_USERNAME, ut.LAST_MODIFIED_DATE, lmb.USER_ID LMB_USER_ID, lmb.USERNAME LMB_USERNAME "
            + "FROM vw_usage_type ut "
            + "LEFT JOIN us_user cb ON ut.CREATED_BY=cb.USER_ID "
            + "LEFT JOIN us_user lmb ON ut.LAST_MODIFIED_BY=lmb.USER_ID "
            + "WHERE 1 ";

    private static String nodeTypeString = "SELECT nt.NODE_TYPE_ID ID, nt.MODELING_ALLOWED, nt.EXTRAPOLATION_ALLOWED, nt.TREE_TEMPLATE_ALLOWED, nt.FORECAST_TREE_ALLOWED, "
            + "nt.LABEL_ID, nt.LABEL_EN, nt.LABEL_FR, nt.LABEL_SP, nt.LABEL_PR, "
            + "nt.ACTIVE, nt.CREATED_DATE, cb.USER_ID CB_USER_ID, cb.USERNAME CB_USERNAME, nt.LAST_MODIFIED_DATE, lmb.USER_ID LMB_USER_ID, lmb.USERNAME LMB_USERNAME "
            + "FROM vw_node_type nt "
            + "LEFT JOIN us_user cb ON nt.CREATED_BY=cb.USER_ID "
            + "LEFT JOIN us_user lmb ON nt.LAST_MODIFIED_BY=lmb.USER_ID "
            + "WHERE 1 ";

    private static String nodeTypeSyncString = "SELECT nt.NODE_TYPE_ID ID, nt.MODELING_ALLOWED, nt.EXTRAPOLATION_ALLOWED, nt.TREE_TEMPLATE_ALLOWED, nt.FORECAST_TREE_ALLOWED, ntr.CHILD_NODE_TYPE_ID, "
            + "nt.LABEL_ID, nt.LABEL_EN, nt.LABEL_FR, nt.LABEL_SP, nt.LABEL_PR, "
            + "nt.ACTIVE, nt.CREATED_DATE, cb.USER_ID CB_USER_ID, cb.USERNAME CB_USERNAME, nt.LAST_MODIFIED_DATE, lmb.USER_ID LMB_USER_ID, lmb.USERNAME LMB_USERNAME "
            + "FROM vw_node_type nt "
            + "LEFT JOIN ap_node_type_rule ntr ON nt.NODE_TYPE_ID=ntr.NODE_TYPE_ID "
            + "LEFT JOIN us_user cb ON nt.CREATED_BY=cb.USER_ID "
            + "LEFT JOIN us_user lmb ON nt.LAST_MODIFIED_BY=lmb.USER_ID "
            + "WHERE 1 ";

    private static String nodeTypeRuleString = "SELECT ntr.NODE_TYPE_ID ID, ntr.CHILD_NODE_TYPE_ID "
            + "FROM ap_node_type_rule ntr "
            + "WHERE 1 ";

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<SimpleBaseModel> getUsageTypeList(boolean active, CustomUserDetails curUser) {
        String sqlString = usageTypeString;
        if (active) {
            sqlString += " AND ut.ACTIVE ORDER BY ut.LABEL_EN";
        }
        return namedParameterJdbcTemplate.query(sqlString, new SimpleBaseModelRowMapper());
    }

    @Override
    public List<NodeType> getNodeTypeList(boolean active, CustomUserDetails curUser) {
        String sqlString = nodeTypeString;
        if (active) {
            sqlString += " AND nt.ACTIVE";
        }
        return namedParameterJdbcTemplate.query(sqlString, new NodeTypeRowMapper());
    }

    @Override
    public List<SimpleBaseModel> getForecastMethodTypeList(boolean active, CustomUserDetails curUser) {
        String sqlString = forecastMethodString;
        if (active) {
            sqlString += " AND nt.ACTIVE";
        }
        return namedParameterJdbcTemplate.query(sqlString, new SimpleBaseModelRowMapper());
    }

    @Override
    public List<SimpleBaseModel> getUsageTypeListForSync(String lastSyncDate, CustomUserDetails curUser) {
        String sqlString = usageTypeString + " AND ut.LAST_MODIFIED_DATE>:lastSyncDate";
        Map<String, Object> params = new HashMap<>();
        params.put("lastSyncDate", lastSyncDate);
        return namedParameterJdbcTemplate.query(sqlString, params, new SimpleBaseModelRowMapper());
    }

    @Override
    public List<NodeTypeSync> getNodeTypeListForSync(String lastSyncDate, CustomUserDetails curUser) {
        String sqlString = nodeTypeSyncString + " AND nt.LAST_MODIFIED_DATE>:lastSyncDate";
        sqlString += " ORDER BY nt.NODE_TYPE_ID, ntr.CHILD_NODE_TYPE_ID ";
        Map<String, Object> params = new HashMap<>();
        params.put("lastSyncDate", lastSyncDate);
        return namedParameterJdbcTemplate.query(sqlString, params, new NodeTypeSyncResultSetExtractor());
    }

    @Override
    public List<SimpleBaseModel> getForecastMethodTypeListForSync(String lastSyncDate, CustomUserDetails curUser) {
        String sqlString = forecastMethodString + " AND nt.LAST_MODIFIED_DATE>:lastSyncDate";
        Map<String, Object> params = new HashMap<>();
        params.put("lastSyncDate", lastSyncDate);
        return namedParameterJdbcTemplate.query(sqlString, params, new SimpleBaseModelRowMapper());
    }

    @Override
    public List<ExtrapolationMethod> getExtrapolationMethodListForSync(String lastSyncDate, CustomUserDetails curUser) {
        String sqlString = "SELECT nt.EXTRAPOLATION_METHOD_ID ID, nt.SORT_ORDER, "
                + "nt.LABEL_ID, nt.LABEL_EN, nt.LABEL_FR, nt.LABEL_SP, nt.LABEL_PR, "
                + "nt.ACTIVE, nt.CREATED_DATE, cb.USER_ID CB_USER_ID, cb.USERNAME CB_USERNAME, nt.LAST_MODIFIED_DATE, lmb.USER_ID LMB_USER_ID, lmb.USERNAME LMB_USERNAME "
                + "FROM vw_extrapolation_method nt "
                + "LEFT JOIN us_user cb ON nt.CREATED_BY=cb.USER_ID "
                + "LEFT JOIN us_user lmb ON nt.LAST_MODIFIED_BY=lmb.USER_ID "
                + "WHERE nt.LAST_MODIFIED_DATE>:lastSyncDate ORDER BY nt.SORT_ORDER";
        Map<String, Object> params = new HashMap<>();
        params.put("lastSyncDate", lastSyncDate);
        return namedParameterJdbcTemplate.query(sqlString, params, new ExtrapolationMethodRowMapper());
    }

}
