/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.ProductDao;
import cc.altius.FASP.model.DTO.PrgProductDTO;
import cc.altius.FASP.model.DTO.rowMapper.PrgProductDTORowMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author altius
 */
@Repository
public class ProductDaoImpl implements ProductDao {

    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<PrgProductDTO> getProductListForSync(String lastSyncDate) {
        String sql = "SELECT p.`ACTIVE`,p.`FORECASTING_UNIT_ID`,p.`GENERIC_LABEL_ID`,p.`PRODUCT_CATEGORY_ID`,p.`PRODUCT_ID`,\n"
                + "l.`LABEL_EN`,l.`LABEL_FR`,l.`LABEL_PR`,l.`LABEL_SP`,\n"
                + "gl.`LABEL_EN` AS 'GL_LABEL_EN',gl.`LABEL_PR` AS 'GL_LABEL_PR',gl.`LABEL_FR` AS 'GL_LABEL_FR',gl.`LABEL_SP` AS 'GL_LABEL_SP'\n"
                + "FROM rm_product p\n"
                + "LEFT JOIN ap_label l ON l.`LABEL_ID`=p.`LABEL_ID`\n"
                + "LEFT JOIN ap_label gl ON gl.`LABEL_ID`=p.`GENERIC_LABEL_ID`";
        Map<String, Object> params = new HashMap<>();
        if (!lastSyncDate.equals("null")) {
            sql += " WHERE p.`LAST_MODIFIED_DATE`>:lastSyncDate;";
            params.put("lastSyncDate", lastSyncDate);
        }
        NamedParameterJdbcTemplate nm = new NamedParameterJdbcTemplate(jdbcTemplate);
        return nm.query(sql, params, new PrgProductDTORowMapper());
    }

}
