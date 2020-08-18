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
import cc.altius.FASP.model.DTO.rowMapper.DatabaseTranslationsDTORowMapper;
import cc.altius.FASP.model.DTO.rowMapper.StaticLabelDTORowMapper;
import cc.altius.FASP.model.Label;
import cc.altius.utils.DateUtils;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
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
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
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
        params.put("CREATED_BY", curUser);
        params.put("CREATED_DATE", curDate);
        params.put("LAST_MODIFIED_BY", curUser);
        params.put("LAST_MODIFIED_DATE", curDate);
        params.put("SOURCE_ID", sourceId);
        return si.executeAndReturnKey(params).intValue();
    }

    @Override
    public List<DatabaseTranslationsDTO> getDatabaseLabelsList(int realmId) {
        String sql = "select * from (SELECT l.LABEL_ID,l.LABEL_EN,l.LABEL_FR,l.LABEL_PR,l.LABEL_SP, \n"
                + "                -1 as `REALM_ID`,'static.country.countryName' as `LABEL_FOR` \n"
                + "                FROM fasp.ap_country c \n"
                + "                left join ap_label l on l.LABEL_ID=c.LABEL_ID \n"
                + "                union \n"
                + "                SELECT l.LABEL_ID,l.LABEL_EN,l.LABEL_FR,l.LABEL_PR,l.LABEL_SP, \n"
                + "                -1 as `REALM_ID`,'static.currency.currencyName' as `LABEL_FOR` \n"
                + "                FROM fasp.ap_currency c \n"
                + "                left join ap_label l on l.LABEL_ID=c.LABEL_ID\n"
                + "                union \n"
                + "                SELECT l.LABEL_ID,l.LABEL_EN,l.LABEL_FR,l.LABEL_PR,l.LABEL_SP, \n"
                + "                c.REALM_ID as `REALM_ID`,'static.dataSource.dataSourceName' as `LABEL_FOR` \n"
                + "                FROM fasp.rm_data_source c \n"
                + "                left join ap_label l on l.LABEL_ID=c.LABEL_ID\n"
                + "                union \n"
                + "                SELECT l.LABEL_ID,l.LABEL_EN,l.LABEL_FR,l.LABEL_PR,l.LABEL_SP, \n"
                + "                c.`REALM_ID` as `REALM_ID`,'static.dataSourceType.dataSourceTypeName' as `LABEL_FOR` \n"
                + "                FROM fasp.rm_data_source_type c \n"
                + "                left join ap_label l on l.LABEL_ID=c.LABEL_ID\n"
                + "                union \n"
                + "                SELECT l.LABEL_ID,l.LABEL_EN,l.LABEL_FR,l.LABEL_PR,l.LABEL_SP, \n"
                + "                -1 as `REALM_ID`,'static.shipmentStatus.shipmentStatusName' as `LABEL_FOR` \n"
                + "                FROM fasp.ap_shipment_status c \n"
                + "                left join ap_label l on l.LABEL_ID=c.LABEL_ID\n"
                + "                union \n"
                + "                SELECT l.LABEL_ID,l.LABEL_EN,l.LABEL_FR,l.LABEL_PR,l.LABEL_SP, \n"
                + "                -1 as `REALM_ID`,'static.unit.unitName' as `LABEL_FOR` \n"
                + "                FROM fasp.ap_unit c \n"
                + "                left join ap_label l on l.LABEL_ID=c.LABEL_ID\n"
                + "                union \n"
                + "                SELECT l.LABEL_ID,l.LABEL_EN,l.LABEL_FR,l.LABEL_PR,l.LABEL_SP, \n"
                + "                -1 as `REALM_ID`,'static.dimension.dimensionName' as `LABEL_FOR` \n"
                + "                FROM fasp.ap_dimension c \n"
                + "                left join ap_label l on l.LABEL_ID=c.LABEL_ID\n"
                + "                union \n"
                + "                SELECT l.LABEL_ID,l.LABEL_EN,l.LABEL_FR,l.LABEL_PR,l.LABEL_SP, \n"
                + "                r.REALM_ID as `REALM_ID`,'static.budget.budgetName' as `LABEL_FOR` \n"
                + "                FROM fasp.rm_budget c\n"
                + "                left join ap_label l on l.LABEL_ID=c.LABEL_ID\n"
                + "                left join rm_program p on p.`PROGRAM_ID`=c.`PROGRAM_ID`\n"
                + "                left join rm_realm_country r on r.REALM_COUNTRY_ID=p.REALM_COUNTRY_ID\n"
                + "                union \n"
                + "                SELECT l.LABEL_ID,l.LABEL_EN,l.LABEL_FR,l.LABEL_PR,l.LABEL_SP, \n"
                + "                c.`REALM_ID` as `REALM_ID`,'static.fundingSource.fundingSourceName' as `LABEL_FOR` \n"
                + "                FROM fasp.rm_funding_source c \n"
                + "                left join ap_label l on l.LABEL_ID=c.LABEL_ID\n"
                + "                union \n"
                + "                SELECT l.LABEL_ID,l.LABEL_EN,l.LABEL_FR,l.LABEL_PR,l.LABEL_SP, \n"
                + "                c.`REALM_ID` as `REALM_ID`,'static.healthArea.healthAreaName' as `LABEL_FOR` \n"
                + "                FROM fasp.rm_health_area c \n"
                + "                left join ap_label l on l.LABEL_ID=c.LABEL_ID\n"
                + "                union \n"
                + "                SELECT l.LABEL_ID,l.LABEL_EN,l.LABEL_FR,l.LABEL_PR,l.LABEL_SP, \n"
                + "                c.`REALM_ID` as `REALM_ID`,'static.supplier.supplierName' as `LABEL_FOR` \n"
                + "                FROM fasp.rm_supplier c \n"
                + "                left join ap_label l on l.LABEL_ID=c.LABEL_ID\n"
                + "                union \n"
                + "                SELECT l.LABEL_ID,l.LABEL_EN,l.LABEL_FR,l.LABEL_PR,l.LABEL_SP, \n"
                + "                c.`REALM_ID` as `REALM_ID`,'static.organisation.organisationName' as `LABEL_FOR` \n"
                + "                FROM fasp.rm_organisation c \n"
                + "                left join ap_label l on l.LABEL_ID=c.LABEL_ID\n"
                + "                union\n"
                + "                SELECT l.LABEL_ID,l.LABEL_EN,l.LABEL_FR,l.LABEL_PR,l.LABEL_SP, \n"
                + "                f.`REALM_ID` as `REALM_ID`,'static.planningUnit.planningUnitName' as `LABEL_FOR` \n"
                + "                FROM fasp.rm_planning_unit c \n"
                + "                left join fasp.rm_forecasting_unit f on f.FORECASTING_UNIT_ID=c.FORECASTING_UNIT_ID\n"
                + "                left join ap_label l on l.LABEL_ID=c.LABEL_ID\n"
                + "                union\n"
                + "                SELECT l.LABEL_ID,l.LABEL_EN,l.LABEL_FR,l.LABEL_PR,l.LABEL_SP, \n"
                + "                c.`REALM_ID` as `REALM_ID`,'static.procurementAgent.procurementAgentName' as `LABEL_FOR` \n"
                + "                FROM fasp.rm_procurement_agent c \n"
                + "                left join ap_label l on l.LABEL_ID=c.LABEL_ID\n"
                + "                union \n"
                + "                SELECT l.LABEL_ID,l.LABEL_EN,l.LABEL_FR,l.LABEL_PR,l.LABEL_SP, \n"
                + "                fu.`REALM_ID` as `REALM_ID`,'static.procurementUnit.procurementUnitName' as `LABEL_FOR` \n"
                + "                FROM fasp.rm_procurement_unit c \n"
                + "                left join ap_label l on l.LABEL_ID=c.LABEL_ID\n"
                + "                left join rm_planning_unit p on p.PLANNING_UNIT_ID=c.PLANNING_UNIT_ID\n"
                + "                left join rm_forecasting_unit fu on fu.FORECASTING_UNIT_ID=p.FORECASTING_UNIT_ID\n"
                + "                union \n"
                + "                SELECT l.LABEL_ID,l.LABEL_EN,l.LABEL_FR,l.LABEL_PR,l.LABEL_SP, \n"
                + "                c.`REALM_ID` as `REALM_ID`,'static.forecastingUnit.forecastingUnitName' as `LABEL_FOR` \n"
                + "                FROM fasp.rm_forecasting_unit c \n"
                + "                left join ap_label l on l.LABEL_ID=c.LABEL_ID\n"
                + "                union \n"
                + "                SELECT l.LABEL_ID,l.LABEL_EN,l.LABEL_FR,l.LABEL_PR,l.LABEL_SP, \n"
                + "                c.`REALM_ID` as `REALM_ID`,'static.forecastingUnit.genericForecastingUnitName' as `LABEL_FOR` \n"
                + "                FROM fasp.rm_forecasting_unit c \n"
                + "                left join ap_label l on l.LABEL_ID=c.GENERIC_LABEL_ID\n"
                + "                union \n"
                + "                SELECT l.LABEL_ID,l.LABEL_EN,l.LABEL_FR,l.LABEL_PR,l.LABEL_SP, \n"
                + "                c.`REALM_ID` as `REALM_ID`,'static.productCategory.productCategoryName' as `LABEL_FOR` \n"
                + "                FROM fasp.rm_product_category c \n"
                + "                left join ap_label l on l.LABEL_ID=c.LABEL_ID\n"
                + "                union \n"
                + "                SELECT l.LABEL_ID,l.LABEL_EN,l.LABEL_FR,l.LABEL_PR,l.LABEL_SP, \n"
                + "                r.`REALM_ID` as `REALM_ID`,'static.program.programName' as `LABEL_FOR` \n"
                + "                FROM fasp.rm_program c \n"
                + "                left join ap_label l on l.LABEL_ID=c.LABEL_ID\n"
                + "                left join rm_realm_country r on r.REALM_COUNTRY_ID=c.REALM_COUNTRY_ID\n"
                + "                union \n"
                + "                SELECT l.LABEL_ID,l.LABEL_EN,l.LABEL_FR,l.LABEL_PR,l.LABEL_SP, \n"
                + "                c.`REALM_ID` as `REALM_ID`,'static.realm.realmName' as `LABEL_FOR` \n"
                + "                FROM fasp.rm_realm c \n"
                + "                left join ap_label l on l.LABEL_ID=c.LABEL_ID\n"
                + "                union \n"
                + "                SELECT l.LABEL_ID,l.LABEL_EN,l.LABEL_FR,l.LABEL_PR,l.LABEL_SP, \n"
                + "                r.`REALM_ID` as `REALM_ID`,'static.region.regionName' as `LABEL_FOR` \n"
                + "                FROM fasp.rm_region c \n"
                + "                left join ap_label l on l.LABEL_ID=c.LABEL_ID\n"
                + "                left join rm_realm_country r on r.REALM_COUNTRY_ID=c.REALM_COUNTRY_ID\n"
                + "                union \n"
                + "                  SELECT l.LABEL_ID,l.LABEL_EN,l.LABEL_FR,l.LABEL_PR,l.LABEL_SP, \n"
                + "                c.`REALM_ID` as `REALM_ID`,'static.tracerCategory.tracerCategoryName' as `LABEL_FOR` \n"
                + "                FROM fasp.rm_tracer_category c \n"
                + "                left join ap_label l on l.LABEL_ID=c.LABEL_ID\n"
                + "                union\n"
                + "                SELECT l.LABEL_ID,l.LABEL_EN,l.LABEL_FR,l.LABEL_PR,l.LABEL_SP, \n"
                + "                -1 as `REALM_ID`,'static.businessFunction.businessFunctionName' as `LABEL_FOR` \n"
                + "                FROM fasp.us_business_function c \n"
                + "                left join ap_label l on l.LABEL_ID=c.LABEL_ID\n"
                + "                union \n"
                + "                SELECT l.LABEL_ID,l.LABEL_EN,l.LABEL_FR,l.LABEL_PR,l.LABEL_SP, \n"
                + "                -1 as `REALM_ID`,'static.role.roleName' as `LABEL_FOR` \n"
                + "                FROM fasp.us_role c \n"
                + "                left join ap_label l on l.LABEL_ID=c.LABEL_ID\n"
                + "                ) label where label.REALM_ID=-1 OR (label.`REALM_ID`=? OR ?=-1) group by label.`LABEL_ID` order by label.`LABEL_ID`,label.`LABEL_FOR`";

        return this.jdbcTemplate.query(sql, new DatabaseTranslationsDTORowMapper(), realmId, realmId);
    }

    @Override
    public boolean saveDatabaseLabels(List<String> label, CustomUserDetails curUser) {
        String curDate = DateUtils.getCurrentDateString(DateUtils.EST, DateUtils.YMDHMS);
        List<Object[]> batchInsert = new ArrayList<Object[]>();
        Gson gson = new Gson();
        for (int i = 0; i < label.size(); i++) {
            Label labelData = gson.fromJson(label.get(i), Label.class);
            if (labelData != null) {
                Object[] valuesInsert = new Object[]{
                    labelData.getLabel_en(),
                    labelData.getLabel_fr(),
                    labelData.getLabel_pr(),
                    labelData.getLabel_sp(),
                    curUser.getUserId(),
                    curDate,
                    labelData.getLabelId()
                };
                batchInsert.add(valuesInsert);
            }
        }
        String sql = " UPDATE ap_label l set l.`LABEL_EN`=?,l.`LABEL_FR`=?,l.`LABEL_PR`=?,l.`LABEL_SP`=?,l.`LAST_MODIFIED_BY`=?,l.`LAST_MODIFIED_DATE`=? where l.`LABEL_ID`=? ";
        int[] count = this.jdbcTemplate.batchUpdate(sql, batchInsert);
        return true;
    }

    @Override
    public List<StaticLabelDTO> getStaticLabelsList() {
        String sql = "	SELECT\n"
                + "	sl.STATIC_LABEL_ID AS `LABEL_ID`, sl.LABEL_CODE, sl.ACTIVE,\n"
                + "	GROUP_CONCAT(IF(l.LANGUAGE_ID=1, sll.LABEL_TEXT,NULL)) LABEL_EN,\n"
                + "	   GROUP_CONCAT(IF(l.LANGUAGE_ID=2, sll.LABEL_TEXT,NULL)) LABEL_FR,\n"
                + "	   GROUP_CONCAT(IF(l.LANGUAGE_ID=3, sll.LABEL_TEXT,NULL)) LABEL_SP,\n"
                + "	   GROUP_CONCAT(IF(l.LANGUAGE_ID=4, sll.LABEL_TEXT,NULL)) LABEL_PR\n"
                + "	FROM ap_static_label sl\n"
                + "	JOIN ap_language l\n"
                + "	LEFT JOIN ap_static_label_languages sll ON sll.STATIC_LABEL_ID=sl.STATIC_LABEL_ID AND sll.LANGUAGE_ID=l.LANGUAGE_ID\n"
                + "	GROUP BY sl.STATIC_LABEL_ID\n"
                + "	ORDER BY sl.STATIC_LABEL_ID, l.LANGUAGE_ID;";
        return this.jdbcTemplate.query(sql, new StaticLabelDTORowMapper());
    }

    @Override
    public boolean saveStaticLabels(List<String> label, CustomUserDetails curUser) {
        String curDate = DateUtils.getCurrentDateString(DateUtils.EST, DateUtils.YMDHMS);
        List<Object[]> batchInsert = new ArrayList<Object[]>();
        Gson gson = new Gson();
        for (int i = 0; i < label.size(); i++) {
            Label labelData = gson.fromJson(label.get(i), Label.class);
            if (labelData != null) {
                String sql = "DELETE l.* FROM ap_static_label_languages l WHERE l.`STATIC_LABEL_ID`=?;";
                this.jdbcTemplate.update(sql, labelData.getLabelId());
                String sql1 = "INSERT INTO ap_static_label_languages VALUES  (NULL,:labelId,1,:labelEn)\n"
                        + ",(NULL,:labelId,2,:labelFr),(NULL,:labelId,3,:labelSp),(NULL,:labelId,4,:labelPr)";
                Map<String, Object> params = new HashMap<>();
                params.put("labelId", labelData.getLabelId());
                params.put("labelEn", labelData.getLabel_en());
                params.put("labelFr", labelData.getLabel_fr());
                params.put("labelPr", labelData.getLabel_pr());
                params.put("labelSp", labelData.getLabel_sp());
                NamedParameterJdbcTemplate nm = new NamedParameterJdbcTemplate(jdbcTemplate);
                nm.update(sql1, params);

            }
        }
        return true;
    }

}
