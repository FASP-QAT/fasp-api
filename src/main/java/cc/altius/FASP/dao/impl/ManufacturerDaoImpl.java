/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.LabelDao;
import cc.altius.FASP.dao.ManufacturerDao;
import cc.altius.FASP.model.DTO.PrgManufacturerDTO;
import cc.altius.FASP.model.DTO.rowMapper.PrgManufacturerDTORowMapper;
import cc.altius.FASP.model.Manufacturer;
import cc.altius.FASP.model.rowMapper.ManufacturerRowMapper;
import cc.altius.utils.DateUtils;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author altius
 */
@Repository
public class ManufacturerDaoImpl implements ManufacturerDao {

    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Autowired
    private LabelDao labelDao;

    @Override
    public List<PrgManufacturerDTO> getManufacturerListForSync(String lastSyncDate) {
        String sql = "SELECT m.`ACTIVE`,m.`MANUFACTURER_ID`,l.`LABEL_EN`,l.`LABEL_FR`,l.`LABEL_PR`,l.`LABEL_SP`\n"
                + "FROM rm_manufacturer m \n"
                + "LEFT JOIN ap_label l ON l.`LABEL_ID`=m.`LABEL_ID`";
        Map<String, Object> params = new HashMap<>();
        if (!lastSyncDate.equals("null")) {
            sql += " WHERE m.`LAST_MODIFIED_DATE`>:lastSyncDate;";
            params.put("lastSyncDate", lastSyncDate);
        }
        NamedParameterJdbcTemplate nm = new NamedParameterJdbcTemplate(jdbcTemplate);
        return nm.query(sql, params, new PrgManufacturerDTORowMapper());
    }

    @Override
    @Transactional
    public int addManufacturer(Manufacturer m, int curUser) {
        SimpleJdbcInsert si = new SimpleJdbcInsert(this.jdbcTemplate).withTableName("rm_manufacturer").usingGeneratedKeyColumns("MANUFACTURER_ID");
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params = new HashMap<>();
        params.put("REALM_ID", m.getRealm().getRealmId());
        int labelId = this.labelDao.addLabel(m.getLabel(), curUser);
        params.put("LABEL_ID", labelId);
        params.put("ACTIVE", true);
        params.put("CREATED_BY", curUser);
        params.put("CREATED_DATE", curDate);
        params.put("LAST_MODIFIED_BY", curUser);
        params.put("LAST_MODIFIED_DATE", curDate);

        int insertedRow = si.executeAndReturnKey(params).intValue();

        SimpleJdbcInsert sii = new SimpleJdbcInsert(this.jdbcTemplate).withTableName("tk_ticket").usingGeneratedKeyColumns("TICKET_ID");
        Map<String, Object> paramsTwo = new HashMap<>();
        paramsTwo.put("TICKET_TYPE_ID", 1);
        paramsTwo.put("TICKET_STATUS_ID", 1);
        paramsTwo.put("REFFERENCE_ID", insertedRow);
        paramsTwo.put("NOTES", "");
        paramsTwo.put("CREATED_BY", curUser);
        paramsTwo.put("CREATED_DATE", curDate);
        paramsTwo.put("LAST_MODIFIED_BY", curUser);
        paramsTwo.put("LAST_MODIFIED_DATE", curDate);
        sii.execute(paramsTwo);

        return insertedRow;
    }

    @Override
    public int updateManufacturer(Manufacturer m, int curUser) {
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        String sqlOne = "UPDATE ap_label al SET al.`LABEL_EN`=?,al.`LAST_MODIFIED_BY`=?,al.`LAST_MODIFIED_DATE`=? WHERE al.`LABEL_ID`=?";
        this.jdbcTemplate.update(sqlOne, m.getLabel().getLabel_en(), curUser, curDate, m.getLabel().getLabelId());

        String sqlTwo = "UPDATE rm_manufacturer dt SET  dt.`REALM_ID`=?,dt.`ACTIVE`=?,dt.`LAST_MODIFIED_BY`=?,dt.`LAST_MODIFIED_DATE`=?"
                + " WHERE dt.`MANUFACTURER_ID`=?;";
        return this.jdbcTemplate.update(sqlTwo, m.getRealm().getRealmId(), m.isActive(), curUser, curDate, m.getManufacturerId());
    }

    @Override
    public List<Manufacturer> getManufacturerList() {
        String sql = "SELECT rm.*,rr.`REALM_CODE`,rr.`MONTHS_IN_PAST_FOR_AMC`,rr.`MONTHS_IN_FUTURE_FOR_AMC`,rr.`ORDER_FREQUENCY`,rr.`DEFAULT_REALM`,"
                + "al.`LABEL_EN`,al.`LABEL_FR`,al.`LABEL_SP`,al.`LABEL_PR`,al.`LABEL_ID`,\n"
                + "rr.`REALM_ID` `RM_REALM_ID`,lr.`LABEL_ID` `RM_LABEL_ID`,\n"
                + "lr.`LABEL_EN` `RM_LABEL_EN`,lr.`LABEL_FR` `RM_LABEL_FR` ,lr.`LABEL_SP` `RM_LABEL_SP`,lr.`LABEL_PR` `RM_LABEL_PR`\n"
                + "  FROM rm_manufacturer rm \n"
                + "LEFT JOIN  ap_label al ON al.`LABEL_ID`=rm.`LABEL_ID`\n"
                + "LEFT JOIN rm_realm rr ON rr.`REALM_ID`=rm.`REALM_ID`\n"
                + "LEFT JOIN ap_label lr ON lr.`LABEL_ID`=rr.`LABEL_ID`";
        return this.jdbcTemplate.query(sql, new ManufacturerRowMapper());
    }

    @Override
    public Manufacturer getManufacturerById(int manufacturerId) {
        String sql = "SELECT rm.*,rr.`REALM_CODE`,rr.`MONTHS_IN_PAST_FOR_AMC`,rr.`MONTHS_IN_FUTURE_FOR_AMC`,rr.`ORDER_FREQUENCY`,rr.`DEFAULT_REALM`"
                + ",al.`LABEL_EN`,al.`LABEL_FR`,al.`LABEL_SP`,al.`LABEL_PR`,al.`LABEL_ID`,\n"
                + "rr.`REALM_ID` `RM_REALM_ID`,lr.`LABEL_ID` `RM_LABEL_ID`,\n"
                + "lr.`LABEL_EN` `RM_LABEL_EN`,lr.`LABEL_FR` `RM_LABEL_FR` ,lr.`LABEL_SP` `RM_LABEL_SP`,lr.`LABEL_PR` `RM_LABEL_PR`\n"
                + "  FROM rm_manufacturer rm \n"
                + "LEFT JOIN  ap_label al ON al.`LABEL_ID`=rm.`LABEL_ID`\n"
                + "LEFT JOIN rm_realm rr ON rr.`REALM_ID`=rm.`REALM_ID`\n"
                + "LEFT JOIN ap_label lr ON lr.`LABEL_ID`=rr.`LABEL_ID`"
                + "WHERE rm.`MANUFACTURER_ID`=?";
        return this.jdbcTemplate.queryForObject(sql, new ManufacturerRowMapper(), manufacturerId);
    }

}
