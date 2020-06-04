/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.pipeline.rowMapper;

import cc.altius.FASP.model.pipeline.QatTempProgramPlanningUnit;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class QatTempPlanningUnitRowMapper implements RowMapper<QatTempProgramPlanningUnit> {

    @Override
    public QatTempProgramPlanningUnit mapRow(ResultSet rs, int arg1) throws SQLException {
        QatTempProgramPlanningUnit p = new QatTempProgramPlanningUnit();
//        SimpleObject s = new SimpleObject();
//        s.setId(rs.getInt("PLANNING_UNIT_ID"));
//        p.setPlanningUnit(s);
        p.setPlanningUnitId(rs.getString("PLANNING_UNIT_ID"));
        p.setMinMonthsOfStock(rs.getInt("MIN_MONTHS_OF_STOCK"));
        p.setReorderFrequencyInMonths(rs.getInt("REORDER_FREQUENCY_IN_MONTHS"));
        p.setProgramPlanningUnitId(rs.getString("PIPELINE_PRODUCT_ID"));
        p.setProductCategoryId(rs.getInt("PRODUCT_CATEGORY_ID"));
        p.setPipelineProductCategoryName(rs.getString("PIPELINE_PRODUCT_CATEGORY"));
        p.setPipelineProductName(rs.getString("PIPELINE_PRODUCT_NAME"));
        return p;
    }

}
