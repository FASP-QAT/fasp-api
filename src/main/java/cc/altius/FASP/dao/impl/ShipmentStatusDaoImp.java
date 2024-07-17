/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.ShipmentStatusDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ShipmentStatus;
import cc.altius.FASP.model.rowMapper.ShipmentStatusResultSetExtractor;
import cc.altius.FASP.model.rowMapper.SupplierRowMapper;
import cc.altius.utils.DateUtils;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    @Override
    public int addShipmentStatus(ShipmentStatus shipmentStatus) {
        String curDate = DateUtils.getCurrentDateString(DateUtils.EST, DateUtils.YMDHMS);
        SimpleJdbcInsert labelInsert = new SimpleJdbcInsert(dataSource).withTableName("ap_label").usingGeneratedKeyColumns("LABEL_ID");
        Map<String, Object> params = new HashMap<>();

        //params.put("LABEL_EN", shipmentStatus.getLabel().getEngLabel());
//        params.put("LABEL_FR", shipmentStatus.getLabel().getFreLabel());
//        params.put("LABEL_SP", shipmentStatus.getLabel().getSpaLabel());//alreday scanned
//        params.put("LABEL_PR", shipmentStatus.getLabel().getPorLabel());
        params.put("LABEL_EN", shipmentStatus.getLabel().getLabel_en());
//        params.put("LABEL_FR", shipmentStatus.getLabel().getLabel_fr());
//        params.put("LABEL_SP", shipmentStatus.getLabel().getLabel_sp());//alreday scanned
//        params.put("LABEL_PR", shipmentStatus.getLabel().getLabel_pr());

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

        if (shipmentStatus.getNextShipmentStatusAllowed().length > 0) {

            SimpleJdbcInsert si = new SimpleJdbcInsert(jdbcTemplate).withTableName("ap_shipment_status_allowed");
            SqlParameterSource[] paramList = new SqlParameterSource[shipmentStatus.getNextShipmentStatusAllowed().length];
            int i = 0;
            for (String bf : shipmentStatus.getNextShipmentStatusAllowed()) {
                params = new HashMap<>();
                params.put("SHIPMENt_STATUS_ID", shipmentStatusId);
                params.put("NEXT_SHIPMENT_STATUS_ID", bf);
                params.put("CREATED_BY", 1);
                params.put("CREATED_DATE", curDate);
                params.put("LAST_MODIFIED_BY", 1);
                params.put("LAST_MODIFIED_DATE", curDate);
                paramList[i] = new MapSqlParameterSource(params);
                i++;
            }
            int result[] = si.executeBatch(paramList);
        }

        return shipmentStatusId;
    }

    @Override
    public List<ShipmentStatus> getShipmentStatusList(boolean active) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ss.* ,l.`LABEL_EN`,l.`LABEL_FR`,l.`LABEL_SP`,l.`LABEL_PR`,l.`LABEL_ID` ,sa.`NEXT_SHIPMENT_STATUS_ID`,sa.`SHIPMENT_STATUS_ALLOWED_ID`FROM ap_shipment_status ss  "
                + "LEFT JOIN ap_label l ON l.`LABEL_ID`=ss.`LABEL_ID` "
                + "LEFT JOIN ap_shipment_status_allowed sa ON sa.`SHIPMENT_STATUS_ID`=ss.`SHIPMENT_STATUS_ID` ");
        if (active) {
            sb.append(" WHERE ss.`ACTIVE` ");
        }
        sb.append("ORDER BY ss.`SHIPMENT_STATUS_ID`");
        return this.jdbcTemplate.query(sb.toString(), new ShipmentStatusResultSetExtractor());
    }

    @Override
    public int editShipmentStatus(ShipmentStatus shipmentStatus) {
        Date curDt = DateUtils.getCurrentDateObject(DateUtils.IST);
        Map<String, Object> params = new HashMap<>();

        String sqlOne = "UPDATE ap_label al SET al.`LABEL_EN`=?,al.`LAST_MODIFIED_BY`=?,al.`LAST_MODIFIED_DATE`=? WHERE al.`LABEL_ID`=?";
        this.jdbcTemplate.update(sqlOne, shipmentStatus.getLabel().getLabel_en(), 1, curDt, shipmentStatus.getLabel().getLabelId());

//        String sqlOne = "UPDATE ap_label al SET al.`LABEL_EN`=? , al.`LABEL_FR`=?,"
//                + "al.`LABEL_PR`=?,al.`LABEL_SP`=?,al.`LAST_MODIFIED_BY`=?,al.`LAST_MODIFIED_DATE`=? WHERE al.`LABEL_ID`=?";
//        this.jdbcTemplate.update(sqlOne, shipmentStatus.getLabel().getLabel_en(), shipmentStatus.getLabel().getLabel_fr(),
//                shipmentStatus.getLabel().getLabel_pr(), shipmentStatus.getLabel().getLabel_sp(), 1, curDt, shipmentStatus.getLabel().getLabelId());
        String sqlTwo = "UPDATE ap_shipment_status dt SET  dt.`ACTIVE`=?,dt.`LAST_MODIFIED_BY`=?,dt.`LAST_MODIFIED_DATE`=?"
                + " WHERE dt.`SHIPMENT_STATUS_ID`=?;";

        this.jdbcTemplate.update("DELETE rbf.* FROM ap_shipment_status_allowed rbf where rbf.SHIPMENT_STATUS_ID=?", shipmentStatus.getShipmentStatusId());
        SimpleJdbcInsert si = new SimpleJdbcInsert(jdbcTemplate).withTableName("ap_shipment_status_allowed");
        SqlParameterSource[] paramList = new SqlParameterSource[shipmentStatus.getNextShipmentStatusAllowed().length];
        int i = 0;
        for (String bf : shipmentStatus.getNextShipmentStatusAllowed()) {
            params = new HashMap<>();
            params.put("SHIPMENt_STATUS_ID", shipmentStatus.getShipmentStatusId());
            params.put("NEXT_SHIPMENT_STATUS_ID", bf);
            params.put("CREATED_BY", 1);
            params.put("CREATED_DATE", curDt);
            params.put("LAST_MODIFIED_BY", 1);
            params.put("LAST_MODIFIED_DATE", curDt);
            paramList[i] = new MapSqlParameterSource(params);
            i++;
        }
        int result[] = si.executeBatch(paramList);
        params.clear();

        return this.jdbcTemplate.update(sqlTwo, shipmentStatus.isActive(), 1, curDt, shipmentStatus.getShipmentStatusId());
    }

    @Override
    public List<ShipmentStatus> getShipmentStatusListForSync(String lastSyncDate, CustomUserDetails curUser) {
        String sqlString = "SELECT s.SHIPMENT_STATUS_ID, sl.LABEL_ID, sl.LABEL_EN, sl.LABEL_FR, sl.LABEL_SP, sl.LABEL_PR, ssa.NEXT_SHIPMENT_STATUS_ID, "
                + "	cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, s.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, s.LAST_MODIFIED_DATE, s.ACTIVE "
                + " FROM ap_shipment_status s  "
                + " LEFT JOIN ap_label sl ON s.LABEL_ID=sl.LABEL_ID  "
                + " LEFT JOIN ap_shipment_status_allowed ssa ON s.SHIPMENT_STATUS_ID=ssa.SHIPMENT_STATUS_ID "
                + " LEFT JOIN us_user cb ON s.CREATED_BY=cb.USER_ID "
                + " LEFT JOIN us_user lmb ON s.LAST_MODIFIED_BY=lmb.USER_ID "
                + " WHERE TRUE AND s.LAST_MODIFIED_DATE>:lastSyncDate AND s.ACTIVE ";
        Map<String, Object> params = new HashMap<>();
        params.put("lastSyncDate", lastSyncDate);
        return this.namedParameterJdbcTemplate.query(sqlString, params, new ShipmentStatusResultSetExtractor());

    }

}
