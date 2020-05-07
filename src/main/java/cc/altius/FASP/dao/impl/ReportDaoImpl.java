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
    public List<Map<String, Object>> getConsumptionData(int realmId, int programId, int planningUnitId,String startDate,String endDate) {
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
        if (programId > 1) {
            sql += "	AND cons.`PROGRAM_ID`=:programId";
            params.put("programId", programId);
        }
       // if (planningUnitId != 0) {
            sql += "	AND pu.`PLANNING_UNIT_ID`=:planningUnitId";
            params.put("planningUnitId", planningUnitId);
       // }
        sql += " And cons.`START_DATE`between :startDate and :endDate	GROUP BY DATE_FORMAT(cons.`START_DATE`,'%m-%Y') \n"
                + "    ORDER BY DATE_FORMAT(cons.`START_DATE`,'%Y-%m')";
         params.put("startDate", startDate);
          params.put("endDate", endDate);
            params.put("planningUnitId", planningUnitId);
        return this.namedParameterJdbcTemplate.queryForList(sql, params);
    }

    @Override
    public List<Map<String, Object>> getStockStatusMatrix(int realmId, int productcategoryId, int planningUnitId, int view,String startDate,String endDate) {
        StringBuilder sb = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
           params.put("realmId", realmId);
        if (view == 1) {
            sb.append("SELECT a.* ,\n"
                    + "\n"
                    + "IFNULL(SUM(CASE WHEN a.MONTH= 1  THEN a.qty END),0) AS 'Jan',\n"
                    + "IFNULL(   SUM(CASE WHEN a.MONTH= 2  THEN a.qty END),0) AS 'Feb',\n"
                    + "IFNULL(   SUM(CASE WHEN a.MONTH = 3  THEN a.qty END),0) AS 'Mar',\n"
                    + " IFNULL(  SUM(CASE WHEN a.MONTH = 4  THEN a.qty END) ,0)AS 'Apr',\n"
                    + " IFNULL(  SUM(CASE WHEN a.MONTH= 5  THEN a.qty END),0) AS 'May',\n"
                    + " IFNULL(  SUM(CASE WHEN a.MONTH = 6  THEN a.qty END),0) AS 'Jun',\n"
                    + "  IFNULL( SUM(CASE WHEN a.MONTH = 7  THEN a.qty END),0) AS 'Jul',\n"
                    + "  IFNULL( SUM(CASE WHEN a.MONTH = 8  THEN a.qty END),0) AS 'Aug',\n"
                    + "  IFNULL( SUM(CASE WHEN a.MONTH = 9  THEN a.qty END) ,0)AS 'Sep',\n"
                    + "  IFNULL( SUM(CASE WHEN a.MONTH = 10 THEN a.qty END),0) AS 'Oct',\n"
                    + "  IFNULL( SUM(CASE WHEN a.MONTH = 11 THEN a.qty END) ,0) AS 'Nov',\n"
                    + "  IFNULL( SUM(CASE WHEN a.MONTH = 12 THEN a.qty END),0) AS 'Dec'\n"
                    + "FROM\n"
                    + "\n"
                    + "(SELECT SUM(i.`ACTUAL_QTY`)  qty, MONTH(i.`INVENTORY_DATE`) MONTH,YEAR(i.`INVENTORY_DATE`) YEAR,irpu_label.`LABEL_EN` AS PLANNING_UNIT_LABEL_EN\n"
                    + "                                ,irpu_label.`LABEL_FR` AS PLANNING_UNIT_LABEL_FR,irpu_label.`LABEL_PR` AS PLANNING_UNIT_LABEL_PR\n"
                    + "                                ,irpu_label.`LABEL_SP` AS PLANNING_UNIT_LABEL_SP,pu.`PLANNING_UNIT_ID` AS `PLANNING_UNIT_ID`\n"
                    + "FROM rm_inventory i LEFT JOIN rm_realm_country_planning_unit rcpu ON rcpu.REALM_COUNTRY_PLANNING_UNIT_ID=i.REALM_COUNTRY_PLANNING_UNIT_ID\n"
                       + "	LEFT JOIN rm_realm_country rc ON rcpu.`REALM_COUNTRY_ID`=rcpu.`REALM_COUNTRY_ID`\n"
                    + "LEFT JOIN rm_planning_unit pu ON pu.PLANNING_UNIT_ID=rcpu.PLANNING_UNIT_ID\n"
                    + "	LEFT JOIN rm_forecasting_unit fu ON fu.`FORECASTING_UNIT_ID`=pu.`FORECASTING_UNIT_ID`\n"
                    + " LEFT JOIN ap_label irpu_label ON irpu_label.`LABEL_ID`=pu.`LABEL_ID` where rc.REALM_ID=:realmId ");
          //  if (planningUnitId > 0) {
                sb.append(" and pu.PLANNING_UNIT_ID=:planningUnitId ");
                 params.put("planningUnitId", planningUnitId);
            //}
            if (productcategoryId > 1) {
                sb.append(" and fu.PRODUCT_CATEGORY_ID=:productcategoryId ");
                 params.put("productcategoryId", productcategoryId);
            }

            sb.append(" And i.`INVENTORY_DATE`between :startDate and :endDate GROUP BY MONTH(i.`INVENTORY_DATE`),YEAR(i.`INVENTORY_DATE`),pu.`PLANNING_UNIT_ID` )a GROUP BY a.year,a.PLANNING_UNIT_ID;");
        } else {
            sb.append("SELECT a.* ,\n"
                    + "\n"
                    + " IFNULL(SUM(CASE WHEN a.QUARTER= 1  THEN a.SUM END),0) AS 'Q1',\n"
                    + " IFNULL(  SUM(CASE WHEN a.QUARTER= 2  THEN a.SUM END),0) AS 'Q2',\n"
                    + "  IFNULL( SUM(CASE WHEN a.QUARTER = 3  THEN a.SUM END),0) AS 'Q3',\n"
                    + "  IFNULL( SUM(CASE WHEN a.QUARTER = 4  THEN a.SUM END),0) AS 'Q4'\n"
                    + "FROM\n"
                    + "\n"
                    + "(SELECT SUM(i.`ACTUAL_QTY`) SUM, QUARTER(i.`INVENTORY_DATE`) QUARTER,YEAR(i.`INVENTORY_DATE`) YEAR,irpu_label.`LABEL_EN` AS PLANNING_UNIT_LABEL_EN\n"
                    + "                                ,irpu_label.`LABEL_FR` AS PLANNING_UNIT_LABEL_FR,irpu_label.`LABEL_PR` AS PLANNING_UNIT_LABEL_PR\n"
                    + "                                ,irpu_label.`LABEL_SP` AS PLANNING_UNIT_LABEL_SP,pu.`PLANNING_UNIT_ID` AS `PLANNING_UNIT_ID`\n"
                    + "FROM rm_inventory i LEFT JOIN rm_realm_country_planning_unit rcpu ON rcpu.REALM_COUNTRY_PLANNING_UNIT_ID=i.REALM_COUNTRY_PLANNING_UNIT_ID\n"
                     + "	LEFT JOIN rm_realm_country rc ON rcpu.`REALM_COUNTRY_ID`=rcpu.`REALM_COUNTRY_ID`\n"
                   + "LEFT JOIN rm_planning_unit pu ON pu.PLANNING_UNIT_ID=rcpu.PLANNING_UNIT_ID\n"
                    + "	LEFT JOIN rm_forecasting_unit fu ON fu.`FORECASTING_UNIT_ID`=pu.`FORECASTING_UNIT_ID`\n"
                    + " LEFT JOIN ap_label irpu_label ON irpu_label.`LABEL_ID`=pu.`LABEL_ID`  where rc.REALM_ID=:realmId ");
            if (planningUnitId > 0) {
                sb.append(" and pu.PLANNING_UNIT_ID=:planningUnitId ");
                 params.put("planningUnitId", planningUnitId);
            }
            if (productcategoryId > 1) {
                sb.append(" and fu.PRODUCT_CATEGORY_ID=:productcategoryId ");
                 params.put("productcategoryId", productcategoryId);
            }

            sb.append(" And i.`INVENTORY_DATE`between :startDate and :endDate GROUP BY QUARTER(i.`INVENTORY_DATE`),YEAR(i.`INVENTORY_DATE`),pu.`PLANNING_UNIT_ID` )a GROUP BY a.year,a.PLANNING_UNIT_ID");
        }
         params.put("startDate", startDate);
          params.put("endDate", endDate);
        System.out.println("param"+params);
        return this.namedParameterJdbcTemplate.queryForList(sb.toString(), params);/*
           
             sb.append("SELECT b.PLANNING_UNIT_LABEL_EN,b.PLANNING_UNIT_LABEL_FR,b.PLANNING_UNIT_LABEL_PR,b.PLANNING_UNIT_LABEL_SP, b.PLANNING_UNIT_ID ,\n" +
"	GROUP_CONCAT(b.year ORDER BY b.year ) year, GROUP_CONCAT(Q1 ORDER BY b.year) Q1,GROUP_CONCAT(Q2 ORDER BY b.year) Q2,GROUP_CONCAT(Q3 ORDER BY b.year) Q3,GROUP_CONCAT(Q4 ORDER BY b.year) Q4\n" +
"	FROM (SELECT a.* ,\n" +
"                    \n" +
"                     IFNULL(SUM(CASE WHEN a.QUARTER= 1  THEN a.SUM END),0) AS 'Q1',\n" +
"                     IFNULL(  SUM(CASE WHEN a.QUARTER= 2  THEN a.SUM END),0) AS 'Q2',\n" +
"                      IFNULL( SUM(CASE WHEN a.QUARTER = 3  THEN a.SUM END),0) AS 'Q3',\n" +
"                      IFNULL( SUM(CASE WHEN a.QUARTER = 4  THEN a.SUM END),0) AS 'Q4'\n" +
"                    FROM\n" +
"                    \n" +
"                    (SELECT SUM(i.`ACTUAL_QTY`) SUM, QUARTER(i.`INVENTORY_DATE`) QUARTER,YEAR(i.`INVENTORY_DATE`) YEAR,irpu_label.`LABEL_EN` AS PLANNING_UNIT_LABEL_EN\n" +
"                                                    ,irpu_label.`LABEL_FR` AS PLANNING_UNIT_LABEL_FR,irpu_label.`LABEL_PR` AS PLANNING_UNIT_LABEL_PR\n" +
"                                                    ,irpu_label.`LABEL_SP` AS PLANNING_UNIT_LABEL_SP,pu.`PLANNING_UNIT_ID` AS `PLANNING_UNIT_ID`\n" +
"                    FROM rm_inventory i LEFT JOIN rm_realm_country_planning_unit rcpu ON rcpu.REALM_COUNTRY_PLANNING_UNIT_ID=i.REALM_COUNTRY_PLANNING_UNIT_ID\n" +
"                     	LEFT JOIN rm_realm_country rc ON rcpu.`REALM_COUNTRY_ID`=rcpu.`REALM_COUNTRY_ID`\n" +
"                   LEFT JOIN rm_planning_unit pu ON pu.PLANNING_UNIT_ID=rcpu.PLANNING_UNIT_ID\n" +
"                    	LEFT JOIN rm_forecasting_unit fu ON fu.`FORECASTING_UNIT_ID`=pu.`FORECASTING_UNIT_ID`\n" +
"                     LEFT JOIN ap_label irpu_label ON irpu_label.`LABEL_ID`=pu.`LABEL_ID`  WHERE rc.REALM_ID=:realmId ");
            if (planningUnitId > 0) {
                sb.append(" and pu.PLANNING_UNIT_ID=:planningUnitId ");
                 params.put("planningUnitId", planningUnitId);
            }
            if (productcategoryId > 1) {
                sb.append(" and fu.PRODUCT_CATEGORY_ID=:productcategoryId ");
                 params.put("productcategoryId", productcategoryId);
            }

            sb.append("   GROUP BY QUARTER(i.`INVENTORY_DATE`),YEAR(i.`INVENTORY_DATE`),pu.`PLANNING_UNIT_ID` ORDER BY YEAR(i.`INVENTORY_DATE`) ASC  )a GROUP BY a.year,a.PLANNING_UNIT_ID) b GROUP BY b.PLANNING_UNIT_ID");
        }
        System.out.println("param"+params);
        return this.namedParameterJdbcTemplate.queryForList(sb.toString(), params);
    */
    }

}
