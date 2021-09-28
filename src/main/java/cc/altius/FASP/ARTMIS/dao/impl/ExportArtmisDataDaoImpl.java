/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.ARTMIS.dao.impl;

import cc.altius.FASP.ARTMIS.dao.ExportArtmisDataDao;
import cc.altius.FASP.model.DTO.ExportOrderDataDTO;
import cc.altius.FASP.model.DTO.ExportProgramDataDTO;
import cc.altius.FASP.model.DTO.ExportShipmentLinkingDTO;
import cc.altius.FASP.model.DTO.rowMapper.ExportOrderDataDTORowMapper;
import cc.altius.FASP.model.DTO.rowMapper.ExportProgramDataDTORowMapper;
import cc.altius.FASP.model.DTO.rowMapper.ExportShipmentLinkingDTORowMapper;
import cc.altius.utils.DateUtils;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
    public List<ExportProgramDataDTO> exportProgramData(Date lastDate) {
        String today = DateUtils.getCurrentDateString(DateUtils.EST, DateUtils.YMD) + " 21:00:00";
        String sql = "SELECT p.PROGRAM_ID, p.PROGRAM_CODE, p.LABEL_EN `PROGRAM_NAME`, c.COUNTRY_CODE2, ha.LABEL_EN `TECHNICAL_AREA_NAME`,p.`ACTIVE`,p.`LAST_MODIFIED_DATE` "
                + "FRom vw_program p LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID "
                + "LEFT JOIN vw_country c ON rc.COUNTRY_ID=c.COUNTRY_ID "
                + "LEFT JOIN vw_health_area ha ON p.HEALTH_AREA_ID=ha.HEALTH_AREA_ID "
                + "WHERE p.`LAST_MODIFIED_DATE` > ? AND p.`LAST_MODIFIED_DATE` < ?";
        System.out.println("lastDate---" + lastDate);
        System.out.println("today---" + today);
        return this.jdbcTemplate.query(sql, new ExportProgramDataDTORowMapper(), lastDate, today);
    }

    @Override
    public List<ExportOrderDataDTO> exportOrderData(Date lastDate) {
        String today = DateUtils.getCurrentDateString(DateUtils.EST, DateUtils.YMD) + " 21:00:00";
        String sql = "SELECT "
                + "    s.SHIPMENT_ID, papu.SKU_CODE, s.PROGRAM_ID, pa.PROCUREMENT_AGENT_CODE, st.SHIPMENT_QTY, "
                + "    COALESCE(st.`RECEIVED_DATE`,st.EXPECTED_DELIVERY_DATE)  AS `EXPECTED_DELIVERY_DATE`,  "
                + "    tc.`TRACER_CATEGORY_ID`, tc.`LABEL_EN` AS TRACER_CATEGORY_DESC, IF(st.SHIPMENT_STATUS_ID!=8 AND st.`ACTIVE`, 1, 0) `ACTIVE`, st.LAST_MODIFIED_DATE "
                + "FROM rm_shipment s  "
                + "LEFT JOIN rm_shipment_trans st on s.`SHIPMENT_ID`=st.`SHIPMENT_ID` AND s.`MAX_VERSION_ID`=st.`VERSION_ID` "
                + "LEFT JOIN vw_procurement_agent pa ON st.`PROCUREMENT_AGENT_ID`=pa.`PROCUREMENT_AGENT_ID`  "
                + "LEFT JOIN rm_procurement_agent_planning_unit papu ON pa.`PROCUREMENT_AGENT_ID`=papu.`PROCUREMENT_AGENT_ID` AND st.`PLANNING_UNIT_ID`=papu.`PLANNING_UNIT_ID`  "
                + "LEFT JOIN rm_planning_unit pu ON pu.`PLANNING_UNIT_ID`=papu.`PLANNING_UNIT_ID`  "
                + "LEFT JOIN rm_forecasting_unit fu ON fu.`FORECASTING_UNIT_ID`=pu.`FORECASTING_UNIT_ID`  "
                + "LEFT JOIN vw_tracer_category tc ON tc.`TRACER_CATEGORY_ID`=fu.`TRACER_CATEGORY_ID`  "
                + "WHERE s.`LAST_MODIFIED_DATE` > ? AND s.`LAST_MODIFIED_DATE` < ?";
        System.out.println("lastDate---" + lastDate);
        System.out.println("today---" + today);
        return this.jdbcTemplate.query(sql, new ExportOrderDataDTORowMapper(), lastDate, today);
    }

    @Override
    public Date getLastDate(String erpCode, String jobName) {
        String sqlString = "SELECT LAST_DATE FROM ap_export WHERE ERP_CODE=? AND JOB_NAME=?";
        return this.jdbcTemplate.queryForObject(sqlString, Date.class, erpCode, jobName);
    }

    @Override
    public boolean updateLastDate(String erpCode, String jobName, Date lastDate) {
        String sqlString = "UPDATE ap_export SET LAST_DATE = ? WHERE ERP_CODE=? AND JOB_NAME=?";
        return (this.jdbcTemplate.update(sqlString, lastDate, erpCode, jobName) == 1);
    }

    @Override
    public List<ExportShipmentLinkingDTO> exportShipmentLinkingData(Date lastDate) {
        String sql = "SELECT s.`PROGRAM_ID`,m.`MANUAL_TAGGING_ID`,l.`LABEL_EN`,m.`SHIPMENT_ID`,b.RO_NO,m.`ORDER_NO`,m.`PRIME_LINE_NO`,m.`ACTIVE`,m.`LAST_MODIFIED_DATE` FROM rm_manual_tagging m "
                + "LEFT JOIN rm_shipment s ON s.`SHIPMENT_ID`=m.`SHIPMENT_ID` "
                + "LEFT JOIN rm_program p ON p.`PROGRAM_ID`=s.`PROGRAM_ID` "
                + "LEFT JOIN ap_label l ON l.`LABEL_ID`=p.`LABEL_ID` "
                + "LEFT JOIN (SELECT a.* FROM ( "
                + "SELECT o.`ERP_ORDER_ID`,o.`RO_NO`,o.`ORDER_NO`,o.`PRIME_LINE_NO` FROM rm_erp_order o "
                + "ORDER BY o.`ERP_ORDER_ID` DESC) AS a "
                + "GROUP BY a.RO_NO,a.ORDER_NO,a.PRIME_LINE_NO ) AS b ON b.ORDER_NO=m.`ORDER_NO` AND b.PRIME_LINE_NO =m.`PRIME_LINE_NO` "
                + " WHERE m.`ACTIVE`=1;";
        System.out.println("list-------" + this.jdbcTemplate.query(sql, new ExportShipmentLinkingDTORowMapper()));
        return this.jdbcTemplate.query(sql, new ExportShipmentLinkingDTORowMapper());
    }

}
