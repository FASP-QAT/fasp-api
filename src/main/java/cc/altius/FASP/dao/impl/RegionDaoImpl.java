/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.RegionDao;
import cc.altius.FASP.model.DTO.PrgRegionDTO;
import cc.altius.FASP.model.DTO.rowMapper.PrgRegionDTORowMapper;
import cc.altius.FASP.model.Region;
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
public class RegionDaoImpl implements RegionDao {

    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public int addRegion(Region region) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int editRegion(Region region) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getRegionList(boolean active) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<PrgRegionDTO> getRegionListForSync(String lastSyncDate) {
        String sql = "SELECT r.`ACTIVE`,r.`CAPACITY_CBM`,r.`LABEL_ID`,r.`REGION_ID`,l.`LABEL_EN` AS `REGION_NAME_EN`,l.`LABEL_FR` AS `REGION_NAME_FR`,l.`LABEL_PR` AS `REGION_NAME_PR`,l.`LABEL_SP` AS `REGION_NAME_SP`\n"
                + "FROM rm_region r\n"
                + "LEFT JOIN ap_label l ON l.`LABEL_ID`=r.`LABEL_ID`";
        Map<String, Object> params = new HashMap<>();
        if (!lastSyncDate.equals("null")) {
            sql += " WHERE r.`LAST_MODIFIED_DATE`>:lastSyncDate;";
            params.put("lastSyncDate", lastSyncDate);
        }
        NamedParameterJdbcTemplate nm = new NamedParameterJdbcTemplate(jdbcTemplate);
        return nm.query(sql, params, new PrgRegionDTORowMapper());
    }

}
