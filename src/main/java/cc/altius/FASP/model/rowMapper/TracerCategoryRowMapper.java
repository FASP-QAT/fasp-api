/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.TracerCategory;
import cc.altius.FASP.model.Realm;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class TracerCategoryRowMapper implements RowMapper<TracerCategory> {

    private String prefix;

    public TracerCategoryRowMapper() {
        this.prefix = "";
    }

    public TracerCategoryRowMapper(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public TracerCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
        TracerCategory pc = new TracerCategory(
                rs.getInt(this.prefix + "TRACER_CATEGORY_ID"),
                new Realm(rs.getInt(prefix + "REALM_ID"), new LabelRowMapper(this.prefix + "REALM_").mapRow(rs, rowNum), rs.getString(prefix + "REALM_CODE")),
                new LabelRowMapper(prefix).mapRow(rs, rowNum)
        );
        pc.setBaseModel(new BaseModelRowMapper(this.prefix).mapRow(rs, rowNum));
        return pc;
    }

}
