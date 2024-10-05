/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.LabelDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.DatabaseTranslationsDTO;
import cc.altius.FASP.model.DTO.StaticLabelDTO;
import cc.altius.FASP.model.DTO.StaticLabelLanguagesDTO;
import cc.altius.FASP.model.DTO.rowMapper.DatabaseTranslationsDTORowMapper;
import cc.altius.FASP.model.DTO.rowMapper.StaticLabelResultSetExtractor;
import cc.altius.FASP.model.Label;
import cc.altius.FASP.service.AclService;
import cc.altius.utils.DateUtils;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author altius
 */
@Repository
public class LabelDaoImpl implements LabelDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private DataSource dataSource;

    @Autowired
    private AclService aclService;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    @Transactional
    public int addLabel(Label label, int sourceId, int curUser) {
        String curDate = DateUtils.getCurrentDateString(DateUtils.EST, DateUtils.YMDHMS);
        SimpleJdbcInsert si = new SimpleJdbcInsert(this.jdbcTemplate).withTableName("ap_label").usingGeneratedKeyColumns("LABEL_ID");
        Map<String, Object> params = new HashMap<>();
        params.put("LABEL_EN", label.getLabel_en());
        params.put("LABEL_FR", label.getLabel_fr());
        params.put("LABEL_SP", label.getLabel_sp());
        params.put("LABEL_PR", label.getLabel_pr());
        params.put("SOURCE_ID", sourceId);
        params.put("CREATED_BY", curUser);
        params.put("CREATED_DATE", curDate);
        params.put("LAST_MODIFIED_BY", curUser);
        params.put("LAST_MODIFIED_DATE", curDate);
        return si.executeAndReturnKey(params).intValue();
    }

    @Override
    public List<DatabaseTranslationsDTO> getDatabaseLabelsList(CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder sb = new StringBuilder("SELECT l2.LABEL_ID, l2.LABEL_EN, l2.LABEL_FR, l2.LABEL_SP, l2.LABEL_PR, l2.LABEL_FOR, l2.ID, l2.REALM_ID, l2.PROGRAM_ID, l2.RELATED_TO_LABEL_ID, l2.RELATED_TO_LABEL_EN, l2.RELATED_TO_LABEL_FR, l2.RELATED_TO_LABEL_SP, l2.RELATED_TO_LABEL_PR FROM (SELECT  "
                + "	l.LABEL_ID, l.LABEL_EN, l.LABEL_FR, l.LABEL_SP, l.LABEL_PR, "
                + "    ls.`SOURCE_DESC` AS `LABEL_FOR`, "
                + "    IFNULL(COALESCE( "
                + "         c.COUNTRY_ID, r.REALM_ID, c2.CURRENCY_ID, d.DIMENSION_ID, vt.VERSION_TYPE_ID,  "
                + "         vs.VERSION_STATUS_ID, ss.SHIPMENT_STATUS_ID, r2.ROLE_ID, ha.HEALTH_AREA_ID,  "
                + "         o.ORGANISATION_ID, r3.REGION_ID, bf.BUSINESS_FUNCTION_ID, pa.PROCUREMENT_AGENT_ID, fs.FUNDING_SOURCE_ID,  "
                + "         b.BUDGET_ID, ds.DATA_SOURCE_ID, dst.DATA_SOURCE_TYPE_ID, u.UNIT_ID, p.PROGRAM_ID, pt.PROBLEM_TYPE_ID,  "
                + "         ps.PROBLEM_STATUS_ID, pc.CRITICALITY_ID, p2.PROBLEM_ID, pc2.PRODUCT_CATEGORY_ID, tc.TRACER_CATEGORY_ID, p3.PROBLEM_ID,  "
                + "         fu.FORECASTING_UNIT_ID, fug.FORECASTING_UNIT_ID, pu.PLANNING_UNIT_ID, pu2.PROCUREMENT_UNIT_ID, rcpu.REALM_COUNTRY_PLANNING_UNIT_ID,  "
                + "         s.SUPPLIER_ID, pc3.PROBLEM_CATEGORY_ID, l2.LANGUAGE_ID, nt.NOTIFICATION_TYPE_ID, ot.ORGANISATION_TYPE_ID, "
                + "         ut.USAGE_TYPE_ID, nt2.NODE_TYPE_ID, up.USAGE_PERIOD_ID, mt.MODELING_TYPE_ID, fmt.FORECAST_METHOD_TYPE_ID, "
                + "         fm.FORECAST_METHOD_ID, eu.EQUIVALENCY_UNIT_ID, tt.TREE_TEMPLATE_ID, ttn.NODE_ID, ft.TREE_ID, "
                + "         ftn.NODE_ID, s2.SCENARIO_ID, dpu.PROGRAM_PLANNING_UNIT_ID, em.EXTRAPOLATION_METHOD_ID, ftl.TREE_LEVEL_ID, ttl.TREE_TEMPLATE_LEVEL_ID),0) `ID`, "
                + "	IFNULL(COALESCE( "
                + "         r.REALM_ID, ha.REALM_ID, o.REALM_ID, r3.REALM_ID, pa.REALM_ID, "
                + "            fs.REALM_ID, b.REALM_ID, ds.REALM_ID, dst.REALM_ID, p.REALM_ID, "
                + "            pc2.REALM_ID, tc.REALM_ID, fu.REALM_ID, fug.REALM_ID, pu.REALM_ID, "
                + "            pu2.REALM_ID, rcpu.REALM_ID, s.REALM_ID, ot.REALM_ID, fm.REALM_ID, "
                + "            eu.REALM_ID, tt.REALM_ID, ttn.REALM_ID, ft.REALM_ID, ftn.REALM_ID, "
                + "            s2.REALM_ID, dpu.REALM_ID, ftl.REALM_ID),0) `REALM_ID`, "
                + "	IFNULL(COALESCE( "
                + "		ds.PROGRAM_ID, p.PROGRAM_ID, ft.PROGRAM_ID, ftn.PROGRAM_ID, "
                + "            s2.PROGRAM_ID, dpu.PROGRAM_ID, ftl.PROGRAM_ID),0) `PROGRAM_ID`, "
                + "     COALESCE(ft2.LABEL_ID, ft3.LABEL_ID, ft4.LABEL_ID, tt2.LABEL_ID, tt3.LABEL_ID) `RELATED_TO_LABEL_ID`, "
                + "     COALESCE(ft2.LABEL_EN, ft3.LABEL_EN, ft4.LABEL_EN, tt2.LABEL_EN, tt3.LABEL_EN) `RELATED_TO_LABEL_EN`, "
                + "     COALESCE(ft2.LABEL_FR, ft3.LABEL_FR, ft4.LABEL_FR, tt2.LABEL_FR, tt3.LABEL_FR) `RELATED_TO_LABEL_FR`, "
                + "     COALESCE(ft2.LABEL_SP, ft3.LABEL_SP, ft4.LABEL_SP, tt2.LABEL_SP, tt3.LABEL_SP) `RELATED_TO_LABEL_SP`, "
                + "     COALESCE(ft2.LABEL_PR, ft3.LABEL_PR, ft4.LABEL_PR, tt2.LABEL_PR, tt3.LABEL_PR) `RELATED_TO_LABEL_PR` "
                + "              "
                + "FROM ap_label l  "
                + "LEFT JOIN ap_label_source ls ON l.SOURCE_ID=ls.SOURCE_ID "
                + "LEFT JOIN ap_country c ON l.LABEL_ID=c.LABEL_ID " //-- 1
                + "LEFT JOIN rm_realm r ON l.LABEL_ID=r.LABEL_ID " // -- 2
                + "LEFT JOIN ap_currency c2 ON l.LABEL_ID=c2.LABEL_ID " //-- 3
                + "LEFT JOIN ap_dimension d ON l.LABEL_ID=d.LABEL_ID " //-- 4 
                + "LEFT JOIN ap_version_type vt ON l.LABEL_ID=vt.LABEL_ID " //-- 5
                + "LEFT JOIN ap_version_status vs ON l.LABEL_ID=vs.LABEL_ID " //-- 6
                + "LEFT JOIN ap_shipment_status ss ON l.LABEL_ID=ss.LABEL_ID " //-- 7
                + "LEFT JOIN us_role r2 ON l.LABEL_ID=r2.LABEL_ID " //-- 8
                + "LEFT JOIN rm_health_area ha ON l.LABEL_ID=ha.LABEL_ID " //-- 9
                + "LEFT JOIN rm_organisation o ON l.LABEL_ID=o.LABEL_ID " //-- 10
                + "LEFT JOIN (SELECT r.REGION_ID, r.LABEL_ID, rc.REALM_ID FROM rm_region r LEFT JOIN rm_realm_country rc ON r.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID) as r3 ON l.LABEL_ID=r3.LABEL_ID " //-- 11
                + "LEFT JOIN rm_procurement_agent pa ON l.LABEL_ID=pa.LABEL_ID " //-- 12
                + "LEFT JOIN rm_funding_source fs ON l.LABEL_ID=fs.LABEL_ID " //-- 13
                + "LEFT JOIN (SELECT b.BUDGET_ID, p.REALM_ID, b.LABEL_ID FROM rm_budget_program bp LEFT JOIN rm_budget b ON bp.BUDGET_ID=bp.BUDGET_ID LEFT JOIN rm_program p ON bp.PROGRAM_ID=p.PROGRAM_ID GROUP BY b.BUDGET_ID) b ON l.LABEL_ID=b.LABEL_ID " //-- 14
                + "LEFT JOIN rm_data_source ds ON l.LABEL_ID=ds.LABEL_ID " //-- 15
                + "LEFT JOIN rm_data_source_type dst ON l.LABEL_ID=dst.LABEL_ID " //-- 16
                + "LEFT JOIN ap_unit u ON l.LABEL_ID=u.LABEL_ID " //-- 17 
                + "LEFT JOIN rm_program p ON l.LABEL_ID=p.LABEL_ID " //-- 18 
                + "LEFT JOIN ap_problem_type pt ON l.LABEL_ID=pt.LABEL_ID " //-- 19 
                + "LEFT JOIN ap_problem_status ps ON l.LABEL_ID=ps.LABEL_ID " //-- 20
                + "LEFT JOIN ap_problem_criticality pc ON l.LABEL_ID=pc.LABEL_ID " //-- 21 
                + "LEFT JOIN ap_problem p2 ON l.LABEL_ID=p2.LABEL_ID " //-- 22 
                + "LEFT JOIN ap_problem p3 ON l.LABEL_ID=p3.ACTION_LABEL_ID " //-- 23 
                + "LEFT JOIN us_business_function bf ON l.LABEL_ID=bf.LABEL_ID " //-- 24 
                + "LEFT JOIN rm_product_category pc2 ON l.LABEL_ID=pc2.LABEL_ID " //-- 26 
                + "LEFT JOIN rm_tracer_category tc ON l.LABEL_ID=tc.LABEL_ID " //-- 27 
                + "LEFT JOIN rm_forecasting_unit fu ON l.LABEL_ID=fu.LABEL_ID " //-- 28 
                + "LEFT JOIN rm_forecasting_unit fug ON l.LABEL_ID=fug.GENERIC_LABEL_ID " //-- 29 
                + "LEFT JOIN (SELECT pu.PLANNING_UNIT_ID, fu.REALM_ID, pu.LABEL_ID FROM rm_planning_unit pu LEFT JOIN rm_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID) pu ON l.LABEL_ID=pu.LABEL_ID " //-- 30 
                + "LEFT JOIN (SELECT pu.PROCUREMENT_UNIT_ID, fu.REALM_ID, pu.LABEL_ID FROM rm_procurement_unit pu LEFT JOIN rm_planning_unit pu2 ON pu.PLANNING_UNIT_ID=pu2.PLANNING_UNIT_ID LEFT JOIN rm_forecasting_unit fu ON pu2.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID) pu2 ON l.LABEL_ID=pu2.LABEL_ID " //-- 31 
                + "LEFT JOIN (SELECT rcpu.REALM_COUNTRY_PLANNING_UNIT_ID, rcpu.LABEL_ID, rc.REALM_ID FROM rm_realm_country_planning_unit rcpu LEFT JOIN rm_realm_country rc ON rcpu.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID) rcpu ON l.LABEL_ID=rcpu.LABEL_ID " //-- 32 
                + "LEFT JOIN rm_supplier s ON l.LABEL_ID=s.LABEL_ID " //-- 33 
                + "LEFT JOIN ap_problem_category pc3 ON l.LABEL_ID=pc3.LABEL_ID " //-- 34 
                + "LEFT JOIN ap_language l2 ON l.LABEL_ID=l2.LABEL_ID " //-- 35 
                + "LEFT JOIN ap_notification_type nt ON l.LABEL_ID=nt.LABEL_ID " //-- 36 
                + "LEFT JOIN rm_organisation_type ot ON l.LABEL_ID=ot.LABEL_ID " //-- 37 
                + "LEFT JOIN ap_usage_type ut ON l.LABEL_ID=ut.LABEL_ID " // -- 38
                + "LEFT JOIN ap_node_type nt2 ON l.LABEL_ID=nt2.LABEL_ID " // --39
                + "LEFT JOIN ap_usage_period up ON l.LABEL_ID=up.LABEL_ID " // -- 40
                + "LEFT JOIN ap_modeling_type mt ON l.LABEL_ID=mt.LABEL_ID " // -- 41
                + "LEFT JOIN ap_forecast_method_type fmt ON l.LABEL_ID=fmt.LABEL_ID " // -- 42
                + "LEFT JOIN rm_forecast_method fm ON l.LABEL_ID=fm.LABEL_ID " // -- 43
                + "LEFT JOIN rm_equivalency_unit eu ON l.LABEL_ID=eu.LABEL_ID " // -- 44
                + "LEFT JOIN rm_tree_template tt ON l.LABEL_ID=tt.LABEL_ID " // -- 45
                + "LEFT JOIN (SELECT ttn.NODE_ID, ttn.TREE_TEMPLATE_ID, ttn.LABEL_ID, tt.REALM_ID FROM rm_tree_template_node ttn LEFT JOIN rm_tree_template tt ON ttn.TREE_TEMPLATE_ID=tt.TREE_TEMPLATE_ID) ttn ON l.LABEL_ID=ttn.LABEL_ID " // -- 46
                + "LEFT JOIN vw_tree_template tt2 ON ttn.TREE_TEMPLATE_ID=tt2.TREE_TEMPLATE_ID "
                + "LEFT JOIN (SELECT ft.TREE_ID, ft.LABEL_ID, p.REALM_ID, p.PROGRAM_ID FROM rm_forecast_tree ft LEFT JOIN rm_program p ON ft.PROGRAM_ID=p.PROGRAM_ID) ft ON ft.LABEL_ID=l.LABEL_ID " // -- 47
                + "LEFT JOIN (SELECT ftn.NODE_ID, ftn.TREE_ID, ftn.LABEL_ID, p.REALM_ID, p.PROGRAM_ID FROM rm_forecast_tree_node ftn LEFT JOIN rm_forecast_tree ft ON ftn.TREE_ID=ft.TREE_ID LEFT JOIN rm_program p ON ft.PROGRAM_ID=p.PROGRAM_ID) ftn ON ftn.LABEL_ID=l.LABEL_ID " // -- 48
                + "LEFT JOIN vw_forecast_tree ft2 ON ftn.TREE_ID=ft2.TREE_ID "
                + "LEFT JOIN (SELECT s.SCENARIO_ID, s.TREE_ID, s.LABEL_ID, p.REALM_ID, p.PROGRAM_ID FROM rm_scenario s LEFT JOIN rm_forecast_tree ft ON s.TREE_ID=ft.TREE_ID LEFT JOIN rm_program p ON ft.PROGRAM_ID=p.PROGRAM_ID) s2 ON l.LABEL_ID=s2.LABEL_ID " // -- 49
                + "LEFT JOIN vw_forecast_tree ft3 ON s2.TREE_ID=ft3.TREE_ID "
                + "LEFT JOIN (SELECT dpu.PROGRAM_PLANNING_UNIT_ID, dpu.OTHER_LABEL_ID, p.REALM_ID, p.PROGRAM_ID FROM rm_dataset_planning_unit dpu LEFT JOIN rm_program p ON dpu.PROGRAM_ID=p.PROGRAM_ID) dpu ON dpu.OTHER_LABEL_ID=l.LABEL_ID " // -- 50
                + "LEFT JOIN ap_extrapolation_method em ON em.LABEL_ID=l.LABEL_ID " // 52
                + "LEFT JOIN (SELECT ftl.TREE_LEVEL_ID, ftl.TREE_ID, ftl.LABEL_ID, p.REALM_ID, p.PROGRAM_ID FROM rm_forecast_tree_level ftl LEFT JOIN rm_forecast_tree ft ON ftl.TREE_ID=ft.TREE_ID LEFT JOIN rm_program p ON ft.PROGRAM_ID=p.PROGRAM_ID) ftl ON ftl.LABEL_ID=l.LABEL_ID " // 53
                + "LEFT JOIN vw_forecast_tree ft4 ON ftl.TREE_ID=ft4.TREE_ID "
                + "LEFT JOIN (SELECT ttl.TREE_TEMPLATE_LEVEL_ID, ttl.TREE_TEMPLATE_ID, ttl.LABEL_ID, tt.REALM_ID FROM rm_tree_template_level ttl LEFT JOIN rm_tree_template tt ON ttl.TREE_TEMPLATE_ID=tt.TREE_TEMPLATE_ID) ttl ON ttl.LABEL_ID=l.LABEL_ID " // 54
                + "LEFT JOIN vw_tree_template tt3 ON ttl.TREE_TEMPLATE_ID=tt3.TREE_TEMPLATE_ID "
                + ") AS l2 "
                + "LEFT JOIN rm_program p ON l2.PROGRAM_ID=p.PROGRAM_ID "
                + "WHERE l2.ID IS NOT NULL AND l2.LABEL_FOR IS NOT NULL ");
        if (curUser.getBusinessFunction().contains(new SimpleGrantedAuthority("ROLE_BUSINESS_FUNCTION_EDIT_APPLICATION_LABELS"))) {
            sb.append(" AND l2.REALM_ID = 0 ");
        } else if (curUser.getBusinessFunction().contains(new SimpleGrantedAuthority("ROLE_BUSINESS_FUNCTION_EDIT_REALM_LABELS"))) {
            sb.append(" AND l2.REALM_ID != 0 ");
            this.aclService.addUserAclForRealm(sb, params, "l2", curUser);
        } else if (curUser.getBusinessFunction().contains(new SimpleGrantedAuthority("ROLE_BUSINESS_FUNCTION_EDIT_PROGRAM_LABELS"))) {
            sb.append(" AND l2.PROGRAM_ID != 0");
            this.aclService.addFullAclForProgram(sb, params, "p", curUser);
        } else {
            sb.append(" AND FALSE");
        }
        return this.namedParameterJdbcTemplate.query(sb.toString(), params, new DatabaseTranslationsDTORowMapper());
    }

    @Override
    public boolean saveDatabaseLabels(List<String> label, CustomUserDetails curUser
    ) {
        String curDate = DateUtils.getCurrentDateString(DateUtils.EST, DateUtils.YMDHMS);
        List<SqlParameterSource> paramList = new LinkedList<>();
        Gson gson = new Gson();
        for (int i = 0; i < label.size(); i++) {
            Label labelData = gson.fromJson(label.get(i), Label.class);
            if (labelData != null) {
                MapSqlParameterSource param = new MapSqlParameterSource();
                param.addValue("labelId", labelData.getLabelId());
                param.addValue("labelEn", labelData.getLabel_en());
                param.addValue("labelFr", labelData.getLabel_fr());
                param.addValue("labelSp", labelData.getLabel_sp());
                param.addValue("labelPr", labelData.getLabel_pr());
                param.addValue("curUser", curUser.getUserId());
                param.addValue("curDate", curDate);
                paramList.add(param);
            }
        }
        String sql = " UPDATE ap_label l "
                + "LEFT JOIN ap_label_source ls ON l.SOURCE_ID=ls.SOURCE_ID "
                + "LEFT JOIN ap_country c ON l.LABEL_ID=c.LABEL_ID " //-- 1
                + "LEFT JOIN rm_realm r ON l.LABEL_ID=r.LABEL_ID " // -- 2
                + "LEFT JOIN ap_currency c2 ON l.LABEL_ID=c2.LABEL_ID " //-- 3
                + "LEFT JOIN ap_dimension d ON l.LABEL_ID=d.LABEL_ID " //-- 4 
                + "LEFT JOIN ap_version_type vt ON l.LABEL_ID=vt.LABEL_ID " //-- 5
                + "LEFT JOIN ap_version_status vs ON l.LABEL_ID=vs.LABEL_ID " //-- 6
                + "LEFT JOIN ap_shipment_status ss ON l.LABEL_ID=ss.LABEL_ID " //-- 7
                + "LEFT JOIN us_role r2 ON l.LABEL_ID=r2.LABEL_ID " //-- 8
                + "LEFT JOIN rm_health_area ha ON l.LABEL_ID=ha.LABEL_ID " //-- 9
                + "LEFT JOIN rm_organisation o ON l.LABEL_ID=o.LABEL_ID " //-- 10
                + "LEFT JOIN rm_region r3 ON l.LABEL_ID=r3.LABEL_ID " //-- 11
                + "LEFT JOIN rm_procurement_agent pa ON l.LABEL_ID=pa.LABEL_ID " //-- 12
                + "LEFT JOIN rm_funding_source fs ON l.LABEL_ID=fs.LABEL_ID " //-- 13
                + "LEFT JOIN rm_budget b ON l.LABEL_ID=b.LABEL_ID " //-- 14
                + "LEFT JOIN rm_data_source ds ON l.LABEL_ID=ds.LABEL_ID " //-- 15
                + "LEFT JOIN rm_data_source_type dst ON l.LABEL_ID=dst.LABEL_ID " //-- 16
                + "LEFT JOIN ap_unit u ON l.LABEL_ID=u.LABEL_ID " //-- 17 
                + "LEFT JOIN rm_program p ON l.LABEL_ID=p.LABEL_ID " //-- 18 
                + "LEFT JOIN ap_problem_type pt ON l.LABEL_ID=pt.LABEL_ID " //-- 19 
                + "LEFT JOIN ap_problem_status ps ON l.LABEL_ID=ps.LABEL_ID " //-- 20
                + "LEFT JOIN ap_problem_criticality pc ON l.LABEL_ID=pc.LABEL_ID " //-- 21 
                + "LEFT JOIN ap_problem p2 ON l.LABEL_ID=p2.LABEL_ID " //-- 22 
                + "LEFT JOIN ap_problem p3 ON l.LABEL_ID=p3.ACTION_LABEL_ID " //-- 23 
                + "LEFT JOIN us_business_function bf ON l.LABEL_ID=bf.LABEL_ID " //-- 24 
                + "LEFT JOIN rm_product_category pc2 ON l.LABEL_ID=pc2.LABEL_ID " //-- 26 
                + "LEFT JOIN rm_tracer_category tc ON l.LABEL_ID=tc.LABEL_ID " //-- 27 
                + "LEFT JOIN rm_forecasting_unit fu ON l.LABEL_ID=fu.LABEL_ID " //-- 28 
                + "LEFT JOIN rm_forecasting_unit fug ON l.LABEL_ID=fug.GENERIC_LABEL_ID " //-- 29 
                + "LEFT JOIN rm_planning_unit pu ON l.LABEL_ID=pu.LABEL_ID " //-- 30 
                + "LEFT JOIN rm_procurement_unit pu2 ON l.LABEL_ID=pu2.LABEL_ID " //-- 31 
                + "LEFT JOIN rm_realm_country_planning_unit rcpu ON l.LABEL_ID=rcpu.LABEL_ID " //-- 32 
                + "LEFT JOIN rm_supplier s ON l.LABEL_ID=s.LABEL_ID " //-- 33 
                + "LEFT JOIN ap_problem_category pc3 ON l.LABEL_ID=pc3.LABEL_ID " //-- 34 
                + "LEFT JOIN ap_language l2 ON l.LABEL_ID=l2.LABEL_ID " //-- 35 
                + "LEFT JOIN ap_notification_type nt ON l.LABEL_ID=nt.LABEL_ID " //-- 36 
                + "LEFT JOIN rm_organisation_type ot ON l.LABEL_ID=ot.LABEL_ID " //-- 37 
                + "LEFT JOIN ap_usage_type ut ON l.LABEL_ID=ut.LABEL_ID " // -- 38
                + "LEFT JOIN ap_node_type nt2 ON l.LABEL_ID=nt2.LABEL_ID " // --39
                + "LEFT JOIN ap_usage_period up ON l.LABEL_ID=up.LABEL_ID " // -- 40
                + "LEFT JOIN ap_modeling_type mt ON l.LABEL_ID=mt.LABEL_ID " // -- 41
                + "LEFT JOIN ap_forecast_method_type fmt ON l.LABEL_ID=fmt.LABEL_ID " // -- 42
                + "LEFT JOIN rm_forecast_method fm ON l.LABEL_ID=fm.LABEL_ID " // -- 43
                + "LEFT JOIN rm_equivalency_unit eu ON l.LABEL_ID=eu.LABEL_ID " // -- 44
                + "LEFT JOIN rm_tree_template tt ON l.LABEL_ID=tt.LABEL_ID " // -- 45
                + "LEFT JOIN rm_tree_template_node ttn ON l.LABEL_ID=ttn.LABEL_ID " // -- 46
                + "LEFT JOIN rm_usage_template ut ON l.LABEL_ID=ut.LABEL_ID " // -- 47
                + "LEFT JOIN rm_forecast_tree ft ON l.LABEL_ID=ft.LABEL_ID " // -- 48
                + "LEFT JOIN rm_forecast_tree_node ftn ON l.LABEL_ID=ftn.LABEL_ID " // -- 49
                + "LEFT JOIN rm_scenario s2 ON l.LABEL_ID=s2.LABEL_ID " // -- 50
                + "LEFT JOIN rm_dataset_planning_unit dpu ON dpu.OTHER_LABEL_ID=l.LABEL_ID " // -- 51
                + "LEFT JOIN ap_extrapolation_method em ON em.LABEL_ID=l.LABEL_ID " // 52
                + "LEFT JOIN rm_forecast_tree_level ftl ON ftl.LABEL_ID=l.LABEL_ID " // 53
                + "LEFT JOIN rm_tree_template_level ttl ON ttl.LABEL_ID=l.LABEL_ID " // 54
                + "LEFT JOIN rm_procurement_agent_type pat ON pat.LABEL_ID=l.LABEL_ID " // 55
                + "LEFT JOIN rm_funding_source_type fst ON fst.LABEL_ID=l.LABEL_ID " // 56
                + "set l.`LABEL_EN`=:labelEn,l.`LABEL_FR`=:labelFr,l.`LABEL_PR`=:labelPr,l.`LABEL_SP`=:labelSp,l.`LAST_MODIFIED_BY`=:curUser,l.`LAST_MODIFIED_DATE`=:curDate, "
                + "c.`LAST_MODIFIED_DATE`=:curDate,r.`LAST_MODIFIED_DATE`=:curDate,c2.`LAST_MODIFIED_DATE`=:curDate,d.`LAST_MODIFIED_DATE`=:curDate, "
                + "vt.`LAST_MODIFIED_DATE`=:curDate,vs.`LAST_MODIFIED_DATE`=:curDate,ss.`LAST_MODIFIED_DATE`=:curDate,r2.`LAST_MODIFIED_DATE`=:curDate, "
                + "ha.`LAST_MODIFIED_DATE`=:curDate,o.`LAST_MODIFIED_DATE`=:curDate,r3.`LAST_MODIFIED_DATE`=:curDate,pa.`LAST_MODIFIED_DATE`=:curDate, "
                + "fs.`LAST_MODIFIED_DATE`=:curDate,b.`LAST_MODIFIED_DATE`=:curDate,ds.`LAST_MODIFIED_DATE`=:curDate,dst.`LAST_MODIFIED_DATE`=:curDate, "
                + "u.`LAST_MODIFIED_DATE`=:curDate,p.`LAST_MODIFIED_DATE`=:curDate,pt.`LAST_MODIFIED_DATE`=:curDate,ps.`LAST_MODIFIED_DATE`=:curDate, "
                + "pc.`LAST_MODIFIED_DATE`=:curDate,p2.`LAST_MODIFIED_DATE`=:curDate,p3.`LAST_MODIFIED_DATE`=:curDate,bf.`LAST_MODIFIED_DATE`=:curDate, "
                + "pc2.`LAST_MODIFIED_DATE`=:curDate,tc.`LAST_MODIFIED_DATE`=:curDate,fu.`LAST_MODIFIED_DATE`=:curDate,fug.`LAST_MODIFIED_DATE`=:curDate, "
                + "pu.`LAST_MODIFIED_DATE`=:curDate,pu2.`LAST_MODIFIED_DATE`=:curDate,rcpu.`LAST_MODIFIED_DATE`=:curDate,s.`LAST_MODIFIED_DATE`=:curDate, "
                + "pc3.`LAST_MODIFIED_DATE`=:curDate,l2.`LAST_MODIFIED_DATE`=:curDate,nt.`LAST_MODIFIED_DATE`=:curDate,ot.`LAST_MODIFIED_DATE`=:curDate, "
                + "ut.`LAST_MODIFIED_DATE`=:curDate,nt2.`LAST_MODIFIED_DATE`=:curDate,up.`LAST_MODIFIED_DATE`=:curDate,mt.`LAST_MODIFIED_DATE`=:curDate, "
                + "fmt.`LAST_MODIFIED_DATE`=:curDate,fm.`LAST_MODIFIED_DATE`=:curDate,eu.`LAST_MODIFIED_DATE`=:curDate,tt.`LAST_MODIFIED_DATE`=:curDate, "
                + "ttn.`LAST_MODIFIED_DATE`=:curDate,ut.`LAST_MODIFIED_DATE`=:curDate,ft.`LAST_MODIFIED_DATE`=:curDate,ftn.`LAST_MODIFIED_DATE`=:curDate, "
                + "s2.`LAST_MODIFIED_DATE`=:curDate,spu.`LAST_MODIFIED_DATE`=:curDate,em.`LAST_MODIFIED_DATE`=:curDate,ftl.`LAST_MODIFIED_DATE`=:curDate, "
                + "ttl.`LAST_MODIFIED_DATE`=:curDate,pat.`LAST_MODIFIED_DATE`=:curDate,fst.`LAST_MODIFIED_DATE`=:curDate, "
                + "c.`LAST_MODIFIED_BY`=:curUser,r.`LAST_MODIFIED_BY`=:curUser,c2.`LAST_MODIFIED_BY`=:curUser,d.`LAST_MODIFIED_BY`=:curUser, "
                + "vt.`LAST_MODIFIED_BY`=:curUser,vs.`LAST_MODIFIED_BY`=:curUser,ss.`LAST_MODIFIED_BY`=:curUser,r2.`LAST_MODIFIED_BY`=:curUser, "
                + "ha.`LAST_MODIFIED_BY`=:curUser,o.`LAST_MODIFIED_BY`=:curUser,r3.`LAST_MODIFIED_BY`=:curUser,pa.`LAST_MODIFIED_BY`=:curUser, "
                + "fs.`LAST_MODIFIED_BY`=:curUser,b.`LAST_MODIFIED_BY`=:curUser,ds.`LAST_MODIFIED_BY`=:curUser,dst.`LAST_MODIFIED_BY`=:curUser, "
                + "u.`LAST_MODIFIED_BY`=:curUser,p.`LAST_MODIFIED_BY`=:curUser,pt.`LAST_MODIFIED_BY`=:curUser,ps.`LAST_MODIFIED_BY`=:curUser, "
                + "pc.`LAST_MODIFIED_BY`=:curUser,p2.`LAST_MODIFIED_BY`=:curUser,p3.`LAST_MODIFIED_BY`=:curUser,bf.`LAST_MODIFIED_BY`=:curUser, "
                + "pc2.`LAST_MODIFIED_BY`=:curUser,tc.`LAST_MODIFIED_BY`=:curUser,fu.`LAST_MODIFIED_BY`=:curUser,fug.`LAST_MODIFIED_BY`=:curUser, "
                + "pu.`LAST_MODIFIED_BY`=:curUser,pu2.`LAST_MODIFIED_BY`=:curUser,rcpu.`LAST_MODIFIED_BY`=:curUser,s.`LAST_MODIFIED_BY`=:curUser, "
                + "pc3.`LAST_MODIFIED_BY`=:curUser,l2.`LAST_MODIFIED_BY`=:curUser,nt.`LAST_MODIFIED_BY`=:curUser,ot.`LAST_MODIFIED_BY`=:curUser, "
                + "ut.`LAST_MODIFIED_BY`=:curUser,nt2.`LAST_MODIFIED_BY`=:curUser,up.`LAST_MODIFIED_BY`=:curUser,mt.`LAST_MODIFIED_BY`=:curUser, "
                + "fmt.`LAST_MODIFIED_BY`=:curUser,fm.`LAST_MODIFIED_BY`=:curUser,eu.`LAST_MODIFIED_BY`=:curUser,tt.`LAST_MODIFIED_BY`=:curUser, "
                + "ttn.`LAST_MODIFIED_BY`=:curUser,ut.`LAST_MODIFIED_BY`=:curUser,ft.`LAST_MODIFIED_BY`=:curUser,ftn.`LAST_MODIFIED_BY`=:curUser, "
                + "s2.`LAST_MODIFIED_BY`=:curUser,spu.`LAST_MODIFIED_BY`=:curUser,em.`LAST_MODIFIED_BY`=:curUser,ftl.`LAST_MODIFIED_BY`=:curUser, "
                + "ttl.`LAST_MODIFIED_BY`=:curUser,pat.`LAST_MODIFIED_BY`=:curUser,fst.`LAST_MODIFIED_BY`=:curUser "
                + " where l.`LABEL_ID`=:labelId ";
        int[] count = this.namedParameterJdbcTemplate.batchUpdate(sql, paramList.toArray(new MapSqlParameterSource[paramList.size()]));
        return true;
    }

    @Override
    public List<StaticLabelDTO> getStaticLabelsList() {
        String sql = "	select sl.STATIC_LABEL_ID,sl.LABEL_CODE,sll.STATIC_LABEL_LANGUAGE_ID,sll.LABEL_TEXT,sll.LANGUAGE_ID from ap_static_label sl\n"
                + "left join ap_static_label_languages sll on sl.STATIC_LABEL_ID=sll.STATIC_LABEL_ID\n"
                + "ORDER BY sl.STATIC_LABEL_ID, sll.LANGUAGE_ID;";
        return this.jdbcTemplate.query(sql, new StaticLabelResultSetExtractor());
    }

    @Override
    public boolean saveStaticLabels(List<StaticLabelDTO> staticLabelList, CustomUserDetails curUser
    ) {
        List<Object[]> batchInsert;
        for (StaticLabelDTO staticLabelDTO : staticLabelList) {
            batchInsert = new ArrayList<Object[]>();
            String sql = "DELETE l.* FROM ap_static_label_languages l WHERE l.`STATIC_LABEL_ID`=?;";
            this.jdbcTemplate.update(sql, staticLabelDTO.getStaticLabelId());
            String sql1 = "INSERT INTO ap_static_label_languages VALUES  (NULL,?,?,?)";
            for (StaticLabelLanguagesDTO staticLabelLanguagesDTO : staticLabelDTO.getStaticLabelLanguages()) {
                if (staticLabelLanguagesDTO.getLabelText() != "") {
                    Object[] valuesInsert = new Object[]{
                        staticLabelDTO.getStaticLabelId(),
                        staticLabelLanguagesDTO.getLanguageId(),
                        staticLabelLanguagesDTO.getLabelText()
                    };
                    batchInsert.add(valuesInsert);
                }
            }
            this.jdbcTemplate.batchUpdate(sql1, batchInsert);
        }
        return true;
    }

}
