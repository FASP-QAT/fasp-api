/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cc.altius.FASP.model.pipeline.rowMapper;
import cc.altius.FASP.model.pipeline.QatTempFundingSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author ekta
 */
public class QatTempFundingSourceRowMapper implements RowMapper<QatTempFundingSource> {
 @Override
    public QatTempFundingSource mapRow(ResultSet rs, int arg1) throws SQLException {
QatTempFundingSource q=new QatTempFundingSource();
q.setFundingSourceId(rs.getInt("FUNDING_SOURCE_ID"));
q.setPipelineFundingSource(rs.getString("PIPELINE_FUNDING_SOURCE"));
q.setPipelineFundingSourceId(rs.getString("PIPELINE_FUNDING_SOURCE_ID"));
return q;

}

}
