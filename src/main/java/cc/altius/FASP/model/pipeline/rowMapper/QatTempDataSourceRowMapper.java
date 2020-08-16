/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cc.altius.FASP.model.pipeline.rowMapper;
import cc.altius.FASP.model.pipeline.QatTempDataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author ekta
 */
public class QatTempDataSourceRowMapper implements RowMapper<QatTempDataSource> {
 @Override
    public QatTempDataSource mapRow(ResultSet rs, int arg1) throws SQLException {
QatTempDataSource q=new QatTempDataSource();
q.setDataSourceId(rs.getInt("DATA_SOURCE_ID"));
q.setPipelineDataSourceType(rs.getString("PIPELINE_DATA_SOURCE_TYPE"));
q.setPipelineDataSource(rs.getString("PIPELINE_DATA_SOURCE"));
q.setPipelineDataSourceId(rs.getString("PIPELINE_DATA_SOURCE_ID"));
q.setDataSourceTypeId(rs.getInt("DATA_SOURCE_TYPE_ID"));
return q;

}

}
