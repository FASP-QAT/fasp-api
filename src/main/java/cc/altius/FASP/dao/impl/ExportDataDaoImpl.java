/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.ExportDataDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.SupplyPlanExportDTO;
import cc.altius.FASP.model.DTO.rowMapper.SupplyPlanExportDTORowMapper;
import cc.altius.FASP.service.AclService;
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
public class ExportDataDaoImpl implements ExportDataDao {

    private DataSource dataSource;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private AclService aclService;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<SupplyPlanExportDTO> getSupplyPlanForProgramId(int programId, int versionId, CustomUserDetails curUser) {
        StringBuilder stringBuilder = new StringBuilder("SELECT ")
                .append("    spa.TRANS_DATE, spa.PLANNING_UNIT_ID, spa.REGION_COUNT, spa.REGION_COUNT_FOR_STOCK, spa.OPENING_BALANCE_WPS, ")
                .append("    spa.ACTUAL_CONSUMPTION_QTY, spa.FORECASTED_CONSUMPTION_QTY, spa.NATIONAL_ADJUSTMENT_WPS, ")
                .append("    spa.MANUAL_PLANNED_SHIPMENT_QTY, spa.MANUAL_SUBMITTED_SHIPMENT_QTY, spa.MANUAL_APPROVED_SHIPMENT_QTY, spa.MANUAL_ONHOLD_SHIPMENT_QTY, spa.MANUAL_SHIPPED_SHIPMENT_QTY, spa.MANUAL_RECEIVED_SHIPMENT_QTY, ")
                .append("    spa.ERP_PLANNED_SHIPMENT_QTY, spa.ERP_SUBMITTED_SHIPMENT_QTY, spa.ERP_APPROVED_SHIPMENT_QTY, spa.ERP_ONHOLD_SHIPMENT_QTY, spa.ERP_SHIPPED_SHIPMENT_QTY, spa.ERP_RECEIVED_SHIPMENT_QTY, ")
                .append("    spa.EXPIRED_STOCK_WPS, spa.CLOSING_BALANCE_WPS, spa.MOS_WPS, spa.AMC, spa.AMC_COUNT, spa.UNMET_DEMAND_WPS, ")
                .append("    spa.MIN_STOCK_MOS, spa.MIN_STOCK_QTY, spa.MAX_STOCK_MOS, spa.MAX_STOCK_QTY ")
                .append("FROM rm_supply_plan_amc spa ")
                .append("LEFT JOIN vw_program p ON spa.PROGRAM_ID=p.PROGRAM_ID ")
                .append("WHERE ")
                .append("    spa.PROGRAM_ID=:programId ")
                .append("    AND spa.VERSION_ID=:versionId ")
                .append("ORDER BY spa.PLANNING_UNIT_ID, spa.TRANS_DATE ");
        Map<String, Object> params = new HashMap<>();
        params.put("programId", programId);
        params.put("versionId", versionId);
        this.aclService.addFullAclForProgram(stringBuilder, params, "p", curUser);
        return this.namedParameterJdbcTemplate.query(stringBuilder.toString(), params, new SupplyPlanExportDTORowMapper());
    }

}
