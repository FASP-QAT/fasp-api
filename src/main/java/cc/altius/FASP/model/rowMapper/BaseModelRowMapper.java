/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.BaseModel;
import cc.altius.FASP.model.BasicUser;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class BaseModelRowMapper implements RowMapper<BaseModel> {

    private String prefix;

    public BaseModelRowMapper() {
        this.prefix = "";
    }

    public BaseModelRowMapper(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public BaseModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        BaseModel b = new BaseModel();
        b.setActive(rs.getBoolean(prefix + "ACTIVE"));
        b.setCreatedDate(rs.getTimestamp(prefix + "CREATED_DATE"));
        b.setLastModifiedDate(rs.getTimestamp(prefix + "LAST_MODIFIED_DATE"));
        b.setCreatedBy(new BasicUser(rs.getInt(prefix + "CB_USER_ID"), rs.getString(prefix + "CB_USERNAME")));
        b.setLastModifiedBy(new BasicUser(rs.getInt(prefix + "LMB_USER_ID"), rs.getString(prefix + "LMB_USERNAME")));
        return b;
    }

}
