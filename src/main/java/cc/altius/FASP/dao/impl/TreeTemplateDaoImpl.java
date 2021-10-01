/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.TreeTemplateDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.TreeNode;
import cc.altius.FASP.model.TreeTemplate;
import cc.altius.FASP.model.rowMapper.TreeNodeResultSetExtractor;
import cc.altius.FASP.model.rowMapper.TreeTemplateRowMapper;
import cc.altius.utils.TreeUtils.Tree;
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
    public Tree<TreeNode> getTree(int treeTemplateId) {
        String sql = "SELECT  "
                + "    ttn.NODE_ID, ttn.TREE_TEMPLATE_ID, ttn.PARENT_NODE_ID, ttn.SORT_ORDER, ttn.LEVEL_NO, ttn.MANUAL_CHANGE_EFFECTS_FUTURE_MONTHS,  "
                + "    ttn.LABEL_ID, ttn.LABEL_EN, ttn.LABEL_FR, ttn.LABEL_SP, ttn.LABEL_PR, ttn.ACTIVE, ttn.CREATED_DATE, ttn.LAST_MODIFIED_DATE, "
                + "    nt.NODE_TYPE_ID `NODE_TYPE_ID`, nt.LABEL_ID `NT_LABEL_ID`, nt.LABEL_EN `NT_LABEL_EN`, nt.LABEL_FR `NT_LABEL_FR`, nt.LABEL_SP `NT_LABEL_SP`, nt.LABEL_PR `NT_LABEL_PR`,  "
                + "    u.UNIT_ID `U_UNIT_ID`, u.UNIT_CODE `U_UNIT_CODE`, u.LABEL_ID `U_LABEL_ID`, u.LABEL_EN `U_LABEL_EN`, u.LABEL_FR `U_LABEL_FR`, u.LABEL_SP `U_LABEL_SP`, u.LABEL_PR `U_LABEL_PR`, "
                + "    cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME` "
                + "FROM vw_tree_template_node ttn "
                + "LEFT JOIN vw_node_type nt ON ttn.NODE_TYPE_ID=nt.NODE_TYPE_ID "
                + "LEFT JOIN vw_unit u ON ttn.UNIT_ID=u.UNIT_ID "
                + "LEFT JOIN us_user cb ON ttn.CREATED_BY=cb.USER_ID "
                + "LEFT JOIN us_user lmb ON ttn.LAST_MODIFIED_BY=lmb.USER_ID "
                + "ORDER BY ttn.SORT_ORDER";
        return this.namedParameterJdbcTemplate.query(sql, new TreeNodeResultSetExtractor());
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
