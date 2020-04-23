/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.ReportDao;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ekta
 */
@Repository
public class ReportDaoImpl implements ReportDao {

    private DataSource dataSource;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<Map<String, Object>> getConsumptionData(int realmId, int productcategoryId, int planningUnitId) {
        Map<String, Object> params = new HashMap<>();

        String sql = "	SELECT \n"
                + "		DATE_FORMAT(cons.`START_DATE`,'%m-%Y') consumption_date,SUM(IF(cons.`ACTUAL_FLAG`=1,cons.`CONSUMPTION_QTY`,0)) Actual,SUM(IF(cons.`ACTUAL_FLAG`=0,cons.`CONSUMPTION_QTY`,0)) forcast	FROM  rm_consumption cons \n"
                + "	LEFT JOIN rm_program p ON cons.PROGRAM_ID=p.PROGRAM_ID\n"
                + "	LEFT JOIN rm_realm_country rc ON rc.`REALM_COUNTRY_ID`=p.`REALM_COUNTRY_ID`\n"
                + "	LEFT JOIN rm_region r ON cons.REGION_ID=r.REGION_ID\n"
                + "	LEFT JOIN rm_planning_unit pu ON cons.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID\n"
                + "	LEFT JOIN rm_forecasting_unit fu ON fu.`FORECASTING_UNIT_ID`=pu.`FORECASTING_UNIT_ID`\n"
                + "	LEFT JOIN rm_data_source ds ON cons.DATA_SOURCE_ID=ds.DATA_SOURCE_ID\n"
                + "	LEFT JOIN us_user cb ON cons.CREATED_BY=cb.USER_ID\n"
                + "	LEFT JOIN us_user lmb ON cons.LAST_MODIFIED_BY=lmb.USER_ID\n"
                + "	WHERE rc.`REALM_ID`=:realmId\n";
        params.put("realmId", realmId);
        if (productcategoryId != 0) {
            sql += "	AND fu.`PRODUCT_CATEGORY_ID`=:productcategoryId";
            params.put("productcategoryId", productcategoryId);
        }
        if (planningUnitId != 0) {
            sql += "	AND pu.`PLANNING_UNIT_ID`=:planningUnitId";
            params.put("planningUnitId", planningUnitId);
        }
        sql += "	GROUP BY DATE_FORMAT(cons.`START_DATE`,'%m-%Y')\n"
                + "    ORDER BY DATE_FORMAT(cons.`START_DATE`,'%m-%Y')";
        return this.namedParameterJdbcTemplate.queryForList(sql, params);
    }

}
