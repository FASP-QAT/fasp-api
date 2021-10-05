/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.TreeTemplateDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ForecastTree;
import cc.altius.FASP.model.TreeNode;
import cc.altius.FASP.model.TreeTemplate;
import cc.altius.FASP.model.rowMapper.TreeNodeResultSetExtractor;
import cc.altius.FASP.model.rowMapper.TreeTemplateRowMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author akil
 */
@Repository
public class TreeTemplateDaoImpl implements TreeTemplateDao {

    private DataSource dataSource;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    private static final String treeTemplateSql = "SELECT  "
            + "    tt.TREE_TEMPLATE_ID, tt.LABEL_ID, tt.LABEL_EN, tt.LABEL_FR, tt.LABEL_SP, tt.LABEL_PR, tt.ACTIVE, tt.CREATED_DATE, tt.LAST_MODIFIED_DATE, "
            + "    r.REALM_ID, r.REALM_CODE, r.LABEL_ID `R_LABEL_ID`,  r.LABEL_EN `R_LABEL_EN`, r.LABEL_FR `R_LABEL_FR`, r.LABEL_SP `R_LABEL_SP`, r.LABEL_PR `R_LABEL_PR`, "
            + "    fm.FORECAST_METHOD_ID, fm.LABEL_ID `FM_LABEL_ID`, fm.LABEL_EN `FM_LABEL_EN`, fm.LABEL_FR `FM_LABEL_FR`, fm.LABEL_SP `FM_LABEL_SP`, fm.LABEL_PR `FM_LABEL_PR`, fm.FORECAST_METHOD_TYPE_ID, "
            + "    cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME` "
            + "FROM vw_tree_template tt  "
            + "LEFT JOIN vw_realm r ON tt.REALM_ID=r.REALM_ID "
            + "LEFT JOIN vw_forecast_method fm ON tt.FORECAST_METHOD_ID=fm.FORECAST_METHOD_ID "
            + "LEFT JOIN us_user cb ON tt.CREATED_BY=cb.USER_ID "
            + "LEFT JOIN us_user lmb ON tt.LAST_MODIFIED_BY=lmb.USER_ID ";

    @Override
    public List<TreeTemplate> getTreeTemplateList(CustomUserDetails curUser) {
        String sql = treeTemplateSql + "ORDER BY tt.LABEL_EN";
        return this.namedParameterJdbcTemplate.query(sql, new TreeTemplateRowMapper());
    }

    @Override
    public ForecastTree<TreeNode> getTree(int treeTemplateId) {
        String sql = "SELECT "
                + "          ttn.NODE_ID, ttn.TREE_TEMPLATE_ID, ttn.PARENT_NODE_ID, ttn.MANUAL_CHANGE_EFFECTS_FUTURE_MONTHS, "
                + "          ttn.LABEL_ID, ttn.LABEL_EN, ttn.LABEL_FR, ttn.LABEL_SP, ttn.LABEL_PR, "
                + "          nt.NODE_TYPE_ID `NODE_TYPE_ID`, nt.LABEL_ID `NT_LABEL_ID`, nt.LABEL_EN `NT_LABEL_EN`, nt.LABEL_FR `NT_LABEL_FR`, nt.LABEL_SP `NT_LABEL_SP`, nt.LABEL_PR `NT_LABEL_PR`, "
                + "          u.UNIT_ID `U_UNIT_ID`, u.UNIT_CODE `U_UNIT_CODE`, u.LABEL_ID `U_LABEL_ID`, u.LABEL_EN `U_LABEL_EN`, u.LABEL_FR `U_LABEL_FR`, u.LABEL_SP `U_LABEL_SP`, u.LABEL_PR `U_LABEL_PR`, "
                + "          ttnd.NODE_DATA_ID, ttnd.MONTH, ttnd.DATA_VALUE, ttnd.NOTES, "
                + "          ttndf.NODE_DATA_FU_ID, ttndf.LAG_IN_MONTHS, ttndf.NO_OF_PERSONS, ttndf.FORECASTING_UNITS_PER_PERSON, "
                + "          fu.FORECASTING_UNIT_ID, fu.LABEL_ID `FU_LABEL_ID`, fu.LABEL_EN `FU_LABEL_EN`, fu.LABEL_FR `FU_LABEL_FR`, fu.LABEL_SP `FU_LABEL_SP`, fu.LABEL_PR `FU_LABEL_PR`, "
                + "          fuu.UNIT_ID `FUU_UNIT_ID`, fuu.UNIT_CODE `FUU_UNIT_CODE`, fuu.LABEL_ID `FUU_LABEL_ID`, fuu.LABEL_EN `FUU_LABEL_EN`, fuu.LABEL_FR `FUU_LABEL_FR`, fuu.LABEL_SP `FUU_LABEL_SP`, fuu.LABEL_PR `FUU_LABEL_PR`, "
                + "          tc.TRACER_CATEGORY_ID, tc.LABEL_ID `TC_LABEL_ID`, tc.LABEL_EN `TC_LABEL_EN`, tc.LABEL_FR `TC_LABEL_FR`, tc.LABEL_SP `TC_LABEL_SP`, tc.LABEL_PR `TC_LABEL_PR`, "
                + "          ut.USAGE_TYPE_ID, ut.LABEL_ID `UT_LABEL_ID`, ut.LABEL_EN `UT_LABEL_EN`, ut.LABEL_FR `UT_LABEL_FR`, ut.LABEL_SP `UT_LABEL_SP`, ut.LABEL_PR `UT_LABEL_PR`, "
                + "          ttndf.ONE_TIME_USAGE, ttndf.USAGE_FREQUENCY, upf.USAGE_PERIOD_ID `UPF_USAGE_PERIOD_ID`, upf.CONVERT_TO_MONTH `UPF_CONVERT_TO_MONTH`, upf.LABEL_ID `UPF_LABEL_ID`, upf.LABEL_EN `UPF_LABEL_EN`, upf.LABEL_FR `UPF_LABEL_FR`, upf.LABEL_SP `UPF_LABEL_SP`, upf.LABEL_PR `UPF_LABEL_PR`, "
                + "          ttndf.REPEAT_COUNT, upr.USAGE_PERIOD_ID `UPR_USAGE_PERIOD_ID`, upr.CONVERT_TO_MONTH `UPR_CONVERT_TO_MONTH`, upr.LABEL_ID `UPR_LABEL_ID`, upr.LABEL_EN `UPR_LABEL_EN`, upr.LABEL_FR `UPR_LABEL_FR`, upr.LABEL_SP `UPR_LABEL_SP`, upr.LABEL_PR `UPR_LABEL_PR`, "
                + "          ttndp.NODE_DATA_PU_ID, ttndp.REFILL_MONTHS, ttndp.SHARE_PLANNING_UNIT, "
                + "          pu.PLANNING_UNIT_ID, pu.LABEL_ID `PU_LABEL_ID`, pu.LABEL_EN `PU_LABEL_EN`, pu.LABEL_FR `PU_LABEL_FR`, pu.LABEL_SP `PU_LABEL_SP`, pu.LABEL_PR `PU_LABEL_PR`, "
                + "          puu.UNIT_ID `PUU_UNIT_ID`, puu.UNIT_CODE `PUU_UNIT_CODE`, puu.LABEL_ID `PUU_LABEL_ID`, puu.LABEL_EN `PUU_LABEL_EN`, puu.LABEL_FR `PUU_LABEL_FR`, puu.LABEL_SP `PUU_LABEL_SP`, puu.LABEL_PR `PUU_LABEL_PR` "
                + "      FROM vw_tree_template_node ttn "
                + "      LEFT JOIN vw_node_type nt ON ttn.NODE_TYPE_ID=nt.NODE_TYPE_ID "
                + "      LEFT JOIN vw_unit u ON ttn.UNIT_ID=u.UNIT_ID "
                + "      LEFT JOIN rm_tree_template_node_data ttnd ON ttn.NODE_ID=ttnd.NODE_ID "
                + "      LEFT JOIN rm_tree_template_node_data_fu ttndf on ttndf.NODE_DATA_FU_ID=ttnd.NODE_DATA_FU_ID "
                + "      LEFT JOIN vw_forecasting_unit fu ON ttndf.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID "
                + "      LEFT JOIN vw_unit fuu ON fu.UNIT_ID=fuu.UNIT_ID "
                + "      LEFT JOIN vw_tracer_category tc ON fu.TRACER_CATEGORY_ID=tc.TRACER_CATEGORY_ID "
                + "      LEFT JOIN vw_usage_type ut ON ttndf.USAGE_TYPE_ID=ut.USAGE_TYPE_ID "
                + "      LEFT JOIN vw_usage_period upf ON ttndf.USAGE_FREQUENCY_USAGE_PERIOD_ID=upf.USAGE_PERIOD_ID "
                + "      LEFT JOIN vw_usage_period upr ON ttndf.REPEAT_USAGE_PERIOD_ID=upr.USAGE_PERIOD_ID "
                + "      LEFT JOIN rm_tree_template_node_data_pu ttndp ON ttndp.NODE_DATA_PU_ID=ttnd.NODE_DATA_PU_ID "
                + "      LEFT JOIN vw_planning_unit pu ON ttndp.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID "
                + "      LEFT JOIN vw_unit puu ON pu.UNIT_ID=puu.UNIT_ID "
                + "      WHERE ttn.TREE_TEMPLATE_ID=:treeTemplateId "
                + "      ORDER BY ttn.SORT_ORDER, ttnd.NODE_DATA_ID";
        Map<String, Object> params = new HashMap<>();
        params.put("treeTemplateId", treeTemplateId);
        return this.namedParameterJdbcTemplate.query(sql, params, new TreeNodeResultSetExtractor());
    }

    @Override
    public Map<String, Object> getConsumption(int treeTemplateId) {
        return null;
    }

    @Override
    public TreeTemplate getTreeTemplateById(int treeTemplateId, CustomUserDetails curUser) {
        String sql = treeTemplateSql + "WHERE tt.TREE_TEMPLATE_ID=:treeTemplateId ORDER BY tt.LABEL_EN";
        Map<String, Object> params = new HashMap<>();
        params.put("treeTemplateId", treeTemplateId);
        return this.namedParameterJdbcTemplate.queryForObject(sql, params, new TreeTemplateRowMapper());
    }

}
