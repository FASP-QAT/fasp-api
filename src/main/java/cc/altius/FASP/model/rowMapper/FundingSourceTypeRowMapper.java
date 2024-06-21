/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.FundingSourceType;
import cc.altius.FASP.model.SimpleCodeObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class FundingSourceTypeRowMapper implements RowMapper<FundingSourceType> {

    @Override
    public FundingSourceType mapRow(ResultSet rs, int rowNum) throws SQLException {
        FundingSourceType pa = new FundingSourceType(
                rs.getInt("FUNDING_SOURCE_TYPE_ID"),
                new SimpleCodeObject(rs.getInt("REALM_ID"), new LabelRowMapper("REALM_").mapRow(rs, rowNum), rs.getString("REALM_CODE")),
                new LabelRowMapper().mapRow(rs, rowNum),
                rs.getString("FUNDING_SOURCE_TYPE_CODE")
        );
        pa.setBaseModel(new BaseModelRowMapper().mapRow(rs, rowNum));
        return pa;
    }
}
