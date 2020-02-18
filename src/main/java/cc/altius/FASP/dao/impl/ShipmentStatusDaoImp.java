/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.ShipmentStatusDao;
import cc.altius.FASP.model.ShipmentStatus;
import cc.altius.FASP.model.rowMapper.ShipmentStatusRowMapper;
import cc.altius.utils.DateUtils;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

/**
 *
 * @author palash
 */
@Repository
public class ShipmentStatusDaoImp implements ShipmentStatusDao {

    private JdbcTemplate jdbcTemplate;
    private javax.sql.DataSource dataSource;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setDataSource(javax.sql.DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public int addShipmentStatus(ShipmentStatus shipmentStatus) {
        String curDate = DateUtils.getCurrentDateString(DateUtils.EST, DateUtils.YMDHMS);

        SimpleJdbcInsert labelInsert = new SimpleJdbcInsert(dataSource).withTableName("ap_label").usingGeneratedKeyColumns("LABEL_ID");
        Map<String, Object> params = new HashMap<>();
        params.put("LABEL_EN", shipmentStatus.getLabel().getEngLabel());
        params.put("LABEL_FR", shipmentStatus.getLabel().getFreLabel());
        params.put("LABEL_SP", shipmentStatus.getLabel().getSpaLabel());//alreday scanned
        params.put("LABEL_PR", shipmentStatus.getLabel().getPorLabel());
        params.put("CREATED_BY", 1);
        params.put("CREATED_DATE", curDate);
        params.put("LAST_MODIFIED_BY", 1);
        params.put("LAST_MODIFIED_DATE", curDate);
        int insertedLabelRowId = labelInsert.executeAndReturnKey(params).intValue();

        SimpleJdbcInsert insert = new SimpleJdbcInsert(dataSource).withTableName("ap_shipment_status").usingGeneratedKeyColumns("SHIPMENT_STATUS_ID");
        Map<String, Object> map = new HashMap<>();
        map.put("LABEL_ID", insertedLabelRowId);
        map.put("ACTIVE", 1);
        map.put("CREATED_BY", 1);
        map.put("CREATED_DATE", curDate);
        map.put("LAST_MODIFIED_BY", 1);
        map.put("LAST_MODIFIED_DATE", curDate);
        int shipmentStatusId = insert.executeAndReturnKey(map).intValue();
        return shipmentStatusId;
    }

    @Override
    public List<ShipmentStatus> getShipmentStatusList(boolean active) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ss.* ,l.`LABEL_EN`,l.`LABEL_FR`,l.`LABEL_SP`,l.`LABEL_PR`,l.`LABEL_ID` FROM ap_shipment_status ss \n"
                + "LEFT JOIN ap_label l ON l.`LABEL_ID`=ss.`LABEL_ID`");
        if (active) {
            sb.append(" WHERE ss.`ACTIVE` ");
        }
        return this.jdbcTemplate.query(sb.toString(), new ShipmentStatusRowMapper());
    }

    @Override
    public int editShipmentStatus(ShipmentStatus shipmentStatus) {
         Date curDt = DateUtils.getCurrentDateObject(DateUtils.IST);
        
        String sqlOne = "UPDATE ap_label al SET al.`LABEL_EN`=? , al.`LABEL_FR`=?,"
                + "al.`LABEL_PR`=?,al.`LABEL_SP`=?,al.`LAST_MODIFIED_BY`=?,al.`LAST_MODIFIED_DATE`=? WHERE al.`LABEL_ID`=?";
        this.jdbcTemplate.update(sqlOne,shipmentStatus.getLabel().getEngLabel(),shipmentStatus.getLabel().getFreLabel(),
                shipmentStatus.getLabel().getPorLabel(),shipmentStatus.getLabel().getSpaLabel(),1,curDt,shipmentStatus.getLabel().getLabelId());
        
        String sqlTwo = "UPDATE ap_shipment_status dt SET  dt.`ACTIVE`=?,dt.`LAST_MODIFIED_BY`=?,dt.`LAST_MODIFIED_DATE`=?"
                        + " WHERE dt.`SHIPMENT_STATUS_ID`=?;";
        return this.jdbcTemplate.update(sqlTwo, shipmentStatus.isActive(),1,curDt,shipmentStatus.getShipmentStatusId());
    }

}
