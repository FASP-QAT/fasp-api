/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.Label;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class LabelRowMapper implements RowMapper<Label> {

    private String prefix;

    public LabelRowMapper(String prefix) {
        this.prefix = prefix;
    }

    public LabelRowMapper() {
        this.prefix = "";
    }

    @Override
    public Label mapRow(ResultSet rs, int rowNum) throws SQLException {
        Label l = new Label();
        l.setLabelId(rs.getInt(prefix + "LABEL_ID"));
        l.setEngLabel(rs.getString(prefix + "LABEL_EN"));
        l.setFreLabel(rs.getString(prefix + "LABEL_FR"));
        l.setSpaLabel(rs.getString(prefix + "LABEL_SP"));
        l.setPorLabel(rs.getString(prefix + "LABEL_PR"));
        return l;
    }

}
