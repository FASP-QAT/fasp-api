/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.ExportDataDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.SupplyPlanExportDTO;
import cc.altius.FASP.model.DTO.rowMapper.SupplyPlanExportPuDTOResultSetExtractor;
import cc.altius.FASP.model.SimpleProgram;
import cc.altius.FASP.service.AclService;
import java.util.HashMap;
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
    public SupplyPlanExportDTO getSupplyPlanForProgramId(SimpleProgram program, int versionId, String startDate, CustomUserDetails curUser) {
        StringBuilder stringBuilder = new StringBuilder("SELECT "
                + "    spa.TRANS_DATE, spa.REGION_COUNT, spa.REGION_COUNT_FOR_STOCK, spa.OPENING_BALANCE, "
                + "    pu.PLANNING_UNIT_ID, pu.LABEL_ID `PU_LABEL_ID`, pu.LABEL_EN `PU_LABEL_EN`, pu.LABEL_FR `PU_LABEL_FR`, pu.LABEL_SP `PU_LABEL_SP`, pu.LABEL_PR `PU_LABEL_PR`, pu.MULTIPLIER, "
                + "    fu.FORECASTING_UNIT_ID, fu.LABEL_ID `FU_LABEL_ID`, fu.LABEL_EN `FU_LABEL_EN`, fu.LABEL_FR `FU_LABEL_FR`, fu.LABEL_SP `FU_LABEL_SP`, fu.LABEL_PR `FU_LABEL_PR`, "
                + "    u.UNIT_ID, u.LABEL_ID `U_LABEL_ID`, u.LABEL_EN `U_LABEL_EN`, u.LABEL_FR `U_LABEL_FR`, u.LABEL_SP `U_LABEL_SP`, u.LABEL_PR `U_LABEL_PR`, "
                + "    pc.PRODUCT_CATEGORY_ID, pc.LABEL_ID `PC_LABEL_ID`, pc.LABEL_EN `PC_LABEL_EN`, pc.LABEL_FR `PC_LABEL_FR`, pc.LABEL_SP `PC_LABEL_SP`, pc.LABEL_PR `PC_LABEL_PR`, "
                + "    spa.ACTUAL_CONSUMPTION_QTY, spa.FORECASTED_CONSUMPTION_QTY, spa.NATIONAL_ADJUSTMENT, "
                + "    spa.MANUAL_PLANNED_SHIPMENT_QTY, spa.MANUAL_SUBMITTED_SHIPMENT_QTY, spa.MANUAL_APPROVED_SHIPMENT_QTY, spa.MANUAL_ONHOLD_SHIPMENT_QTY, spa.MANUAL_SHIPPED_SHIPMENT_QTY, spa.MANUAL_RECEIVED_SHIPMENT_QTY, "
                + "    spa.ERP_PLANNED_SHIPMENT_QTY, spa.ERP_SUBMITTED_SHIPMENT_QTY, spa.ERP_APPROVED_SHIPMENT_QTY, spa.ERP_ONHOLD_SHIPMENT_QTY, spa.ERP_SHIPPED_SHIPMENT_QTY, spa.ERP_RECEIVED_SHIPMENT_QTY, "
                + "    spa.EXPIRED_STOCK, spa.CLOSING_BALANCE, spa.MOS, spa.AMC, spa.AMC_COUNT, spa.UNMET_DEMAND, "
                + "    spa.MIN_STOCK_MOS, spa.MIN_STOCK_QTY, spa.MAX_STOCK_MOS, spa.MAX_STOCK_QTY "
                + "FROM vw_program p "
                + "LEFT JOIN rm_supply_plan_amc spa  ON spa.PROGRAM_ID=p.PROGRAM_ID "
                + "LEFT JOIN vw_planning_unit pu ON spa.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID "
                + "LEFT JOIN vw_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID "
                + "LEFT JOIN vw_product_category pc ON fu.PRODUCT_CATEGORY_ID=pc.PRODUCT_CATEGORY_ID "
                + "LEFT JOIN vw_unit u ON pu.UNIT_ID=u.UNIT_ID "
                + "WHERE "
                + "    p.PROGRAM_ID=:programId "
                + "    AND spa.VERSION_ID=:versionId ");

        Map<String, Object> params = new HashMap<>();

        params.put("programId", program.getId());
        if (startDate != null) {
            params.put("startDate", startDate + "-01");
            stringBuilder.append(" AND spa.TRANS_DATE>=:startDate");
        }
        versionId = (versionId == -1 ? program.getCurrentVersionId() : versionId);
        params.put("versionId", versionId);
        this.aclService.addFullAclForProgram(stringBuilder, params, "p", curUser);

        stringBuilder.append("ORDER BY spa.PLANNING_UNIT_ID, spa.TRANS_DATE ");
        SupplyPlanExportDTO sp = new SupplyPlanExportDTO(program, versionId);
        sp.setPlanningUnitList(this.namedParameterJdbcTemplate.query(stringBuilder.toString(), params, new SupplyPlanExportPuDTOResultSetExtractor()));
        return sp;
    }

}
