/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.ARTMIS.dao.impl;

import cc.altius.FASP.ARTMIS.dao.ExportArtmisDataDao;
import cc.altius.FASP.model.DTO.ExportOrderDataDTO;
import cc.altius.FASP.model.DTO.ExportProgramDataDTO;
import cc.altius.FASP.model.DTO.rowMapper.ExportOrderDataDTORowMapper;
import cc.altius.FASP.model.DTO.rowMapper.ExportProgramDataDTORowMapper;
import cc.altius.utils.DateUtils;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author altius
 */
@Repository
public class ExportArtmisDataDaoImpl implements ExportArtmisDataDao {

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<ExportProgramDataDTO> exportProgramData() {
        Calendar cal = Calendar.getInstance();
        TimeZone tz = TimeZone.getTimeZone("EST");
        cal.setTimeZone(tz);
        cal.add(Calendar.DATE, -1);
//        est 10 pm
        String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()) + " 21:00:00";
        String today = DateUtils.getCurrentDateString(DateUtils.EST, DateUtils.YMD) + " 21:00:00";

        String sql = "SELECT p.PROGRAM_ID, p.PROGRAM_CODE, p.LABEL_EN `PROGRAM_NAME`, c.COUNTRY_CODE2, ha.LABEL_EN `TECHNICAL_AREA_NAME`,p.`ACTIVE`   "
                + "FRom vw_program p LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID "
                + "LEFT JOIN vw_country c ON rc.COUNTRY_ID=c.COUNTRY_ID "
                + "LEFT JOIN vw_health_area ha ON p.HEALTH_AREA_ID=ha.HEALTH_AREA_ID "
                + "WHERE p.`LAST_MODIFIED_DATE` BETWEEN ? AND ?;";
        System.out.println("yesterday---" + yesterday);
        System.out.println("today---" + today);
        return this.jdbcTemplate.query(sql, new ExportProgramDataDTORowMapper(), yesterday, today);
    }

    @Override
    public List<ExportOrderDataDTO> exportOrderData() {
        Calendar cal = Calendar.getInstance();
        TimeZone tz = TimeZone.getTimeZone("EST");
        cal.setTimeZone(tz);
        cal.add(Calendar.DATE, -1);
//        est 10 pm
        String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()) + " 21:00:00";
        String today = DateUtils.getCurrentDateString(DateUtils.EST, DateUtils.YMD) + " 21:00:00";

        String sql = "SELECT "
                + "    s.SHIPMENT_ID, papu.SKU_CODE, s.PROGRAM_ID, pa.PROCUREMENT_AGENT_CODE, st.SHIPMENT_QTY, "
                + "    COALESCE(st.`RECEIVED_DATE`,st.EXPECTED_DELIVERY_DATE)  AS `EXPECTED_DELIVERY_DATE`,  "
                + "    tc.`TRACER_CATEGORY_ID`, tc.`LABEL_EN` AS TRACER_CATEGORY_DESC, st.`ACTIVE`, st.LAST_MODIFIED_DATE "
                + "FROM rm_shipment s  "
                + "LEFT JOIN rm_shipment_trans st on s.`SHIPMENT_ID`=st.`SHIPMENT_ID` AND s.`MAX_VERSION_ID`=st.`VERSION_ID` "
                + "LEFT JOIN vw_procurement_agent pa ON st.`PROCUREMENT_AGENT_ID`=pa.`PROCUREMENT_AGENT_ID`  "
                + "LEFT JOIN rm_procurement_agent_planning_unit papu ON pa.`PROCUREMENT_AGENT_ID`=papu.`PROCUREMENT_AGENT_ID` AND st.`PLANNING_UNIT_ID`=papu.`PLANNING_UNIT_ID`  "
                + "LEFT JOIN rm_planning_unit pu ON pu.`PLANNING_UNIT_ID`=papu.`PLANNING_UNIT_ID`  "
                + "LEFT JOIN rm_forecasting_unit fu ON fu.`FORECASTING_UNIT_ID`=pu.`FORECASTING_UNIT_ID`  "
                + "LEFT JOIN vw_tracer_category tc ON tc.`TRACER_CATEGORY_ID`=fu.`TRACER_CATEGORY_ID`  "
                + "WHERE s.`LAST_MODIFIED_DATE`>?";
        System.out.println("yesterday---" + yesterday);
        System.out.println("today---" + today);
        return this.jdbcTemplate.query(sql, new ExportOrderDataDTORowMapper(), yesterday);
    }

}
