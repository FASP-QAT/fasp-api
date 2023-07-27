/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.BasicUser;
import cc.altius.FASP.model.Country;
import cc.altius.FASP.model.Currency;
import cc.altius.FASP.model.Program;
import cc.altius.FASP.model.Realm;
import cc.altius.FASP.model.RealmCountry;
import cc.altius.FASP.model.Region;
import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.Version;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author akil
 */
public class ProgramResultSetExtractor implements ResultSetExtractor<Program> {

    @Override
    public Program extractData(ResultSet rs) throws SQLException, DataAccessException {
        Program p = new Program();
        boolean isFirst = true;
        while (rs.next()) {
            if (isFirst) {
                p.setProgramId(rs.getInt("PROGRAM_ID"));
                p.setProgramCode(rs.getString("PROGRAM_CODE"));
                p.setRealmCountry(
                        new RealmCountry(
                                rs.getInt("REALM_COUNTRY_ID"),
                                new Country(rs.getInt("COUNTRY_ID"), rs.getString("COUNTRY_CODE"), new LabelRowMapper("COUNTRY_").mapRow(rs, 1)),
                                new Realm(rs.getInt("REALM_ID"), new LabelRowMapper("REALM_").mapRow(rs, 1), rs.getString("REALM_CODE"), rs.getInt("MIN_MOS_MIN_GAURDRAIL"), rs.getInt("MIN_MOS_MAX_GAURDRAIL"), rs.getInt("MAX_MOS_MAX_GAURDRAIL"), rs.getInt("MIN_QPL_TOLERANCE"), rs.getInt("MIN_QPL_TOLERANCE_CUT_OFF"), rs.getInt("MAX_QPL_TOLERANCE"), rs.getInt("ACTUAL_CONSUMPTION_MONTHS_IN_PAST"), rs.getInt("FORECAST_CONSUMPTION_MONTH_IN_PAST"), rs.getInt("INVENTORY_MONTHS_IN_PAST"), rs.getInt("MIN_COUNT_FOR_MODE"), rs.getDouble("MIN_PERC_FOR_MODE"))
                        )
                );
                p.getRealmCountry().getCountry().setCountryCode2(rs.getString("COUNTRY_CODE2"));
                p.getRealmCountry().setDefaultCurrency(new Currency(rs.getInt("CURRENCY_ID"), rs.getString("CURRENCY_CODE"), new LabelRowMapper("CURRENCY_").mapRow(rs, 1), rs.getDouble("CONVERSION_RATE_TO_USD")));
                p.setLabel(new LabelRowMapper().mapRow(rs, 1));
                p.setOrganisation(new SimpleCodeObject(rs.getInt("ORGANISATION_ID"), new LabelRowMapper("ORGANISATION_").mapRow(rs, 1), rs.getString("ORGANISATION_CODE")));
                p.setProgramManager(new BasicUser(rs.getInt("PROGRAM_MANAGER_USER_ID"), rs.getString("PROGRAM_MANAGER_USERNAME")));
                p.setProgramNotes(rs.getString("PROGRAM_NOTES"));
                p.setAirFreightPerc(rs.getDouble("AIR_FREIGHT_PERC"));
                p.setSeaFreightPerc(rs.getDouble("SEA_FREIGHT_PERC"));
                p.setRoadFreightPerc(rs.getDouble("ROAD_FREIGHT_PERC"));
                p.setPlannedToSubmittedLeadTime(rs.getDouble("PLANNED_TO_SUBMITTED_LEAD_TIME"));
                p.setSubmittedToApprovedLeadTime(rs.getDouble("SUBMITTED_TO_APPROVED_LEAD_TIME"));
                p.setApprovedToShippedLeadTime(rs.getDouble("APPROVED_TO_SHIPPED_LEAD_TIME"));
                p.setShippedToArrivedBySeaLeadTime(rs.getDouble("SHIPPED_TO_ARRIVED_BY_SEA_LEAD_TIME"));
                p.setShippedToArrivedByAirLeadTime(rs.getDouble("SHIPPED_TO_ARRIVED_BY_AIR_LEAD_TIME"));
                p.setShippedToArrivedByRoadLeadTime(rs.getDouble("SHIPPED_TO_ARRIVED_BY_ROAD_LEAD_TIME"));
                p.setArrivedToDeliveredLeadTime(rs.getDouble("ARRIVED_TO_DELIVERED_LEAD_TIME"));
                p.setProgramTypeId(rs.getInt("PROGRAM_TYPE_ID"));
                p.setCurrentVersion(new Version(
                        rs.getInt("CV_VERSION_ID"),
                        new SimpleObject(rs.getInt("CV_VERSION_TYPE_ID"), new LabelRowMapper("CV_VERSION_TYPE_").mapRow(rs, 1)),
                        new SimpleObject(rs.getInt("CV_VERSION_STATUS_ID"), new LabelRowMapper("CV_VERSION_STATUS_").mapRow(rs, 1)),
                        rs.getString("CV_VERSION_NOTES"),
                        new BasicUser(rs.getInt("CV_CB_USER_ID"), rs.getString("CV_CB_USERNAME")),
                        rs.getTimestamp("CV_CREATED_DATE"),
                        new BasicUser(rs.getInt("CV_LMB_USER_ID"), rs.getString("CV_LMB_USERNAME")),
                        rs.getTimestamp("CV_LAST_MODIFIED_DATE")
                ));
                p.getCurrentVersion().setForecastStartDate(rs.getDate("CV_FORECAST_START_DATE"));
                p.getCurrentVersion().setForecastStopDate(rs.getDate("CV_FORECAST_STOP_DATE"));
                p.getCurrentVersion().setDaysInMonth(rs.getInt("DAYS_IN_MONTH"));
                if (rs.wasNull()) {
                    p.getCurrentVersion().setDaysInMonth(null);
                }
                p.getCurrentVersion().setFreightPerc(rs.getDouble("FREIGHT_PERC"));
                if (rs.wasNull()) {
                    p.getCurrentVersion().setFreightPerc(null);
                }
                p.getCurrentVersion().setForecastThresholdHighPerc(rs.getDouble("FORECAST_THRESHOLD_HIGH_PERC"));
                if (rs.wasNull()) {
                    p.getCurrentVersion().setForecastThresholdHighPerc(null);
                }
                p.getCurrentVersion().setForecastThresholdLowPerc(rs.getDouble("FORECAST_THRESHOLD_HIGH_PERC"));
                if (rs.wasNull()) {
                    p.getCurrentVersion().setForecastThresholdLowPerc(null);
                }
                p.setBaseModel(new BaseModelRowMapper().mapRow(rs, 1));
                p.setRegionList(new LinkedList<>());
                p.setVersionList(new LinkedList<>());
            }
            Region r = new Region(rs.getInt("REGION_ID"), new LabelRowMapper("REGION_").mapRow(rs, 0));
            r.setCapacityCbm(rs.getDouble("CAPACITY_CBM"));
            r.setGln(rs.getString("GLN"));
            if (p.getRegionList().indexOf(r) == -1) {
                p.getRegionList().add(r);
            }

            SimpleCodeObject ha = new SimpleCodeObject(rs.getInt("HEALTH_AREA_ID"), new LabelRowMapper("HEALTH_AREA_").mapRow(rs, 0), rs.getString("HEALTH_AREA_CODE"));
            if (p.getHealthAreaList().indexOf(ha) == -1) {
                p.getHealthAreaList().add(ha);
            }
            Version v = new Version(
                    rs.getInt("VT_VERSION_ID"),
                    new SimpleObject(rs.getInt("VT_VERSION_TYPE_ID"), new LabelRowMapper("VT_VERSION_TYPE_").mapRow(rs, 1)),
                    new SimpleObject(rs.getInt("VT_VERSION_STATUS_ID"), new LabelRowMapper("VT_VERSION_STATUS_").mapRow(rs, 1)),
                    rs.getString("VT_VERSION_NOTES"),
                    new BasicUser(rs.getInt("VT_CB_USER_ID"), rs.getString("VT_CB_USERNAME")),
                    rs.getTimestamp("VT_CREATED_DATE"),
                    new BasicUser(rs.getInt("VT_LMB_USER_ID"), rs.getString("VT_LMB_USERNAME")),
                    rs.getTimestamp("VT_LAST_MODIFIED_DATE")
            );
            v.setForecastStartDate(rs.getDate("VT_FORECAST_START_DATE"));
            v.setForecastStopDate(rs.getDate("VT_FORECAST_STOP_DATE"));
            v.setDaysInMonth(rs.getInt("VT_DAYS_IN_MONTH"));
            if (rs.wasNull()) {
                v.setDaysInMonth(null);
            }
            v.setFreightPerc(rs.getDouble("VT_FREIGHT_PERC"));
            if (rs.wasNull()) {
                v.setFreightPerc(null);
            }
            v.setForecastThresholdHighPerc(rs.getDouble("VT_FORECAST_THRESHOLD_HIGH_PERC"));
            if (rs.wasNull()) {
                v.setForecastThresholdHighPerc(null);
            }
            v.setForecastThresholdLowPerc(rs.getDouble("VT_FORECAST_THRESHOLD_LOW_PERC"));
            if (rs.wasNull()) {
                v.setForecastThresholdLowPerc(null);
            }
            if (p.getVersionList().indexOf(v) == -1) {
                p.getVersionList().add(v);
            }
            isFirst = false;
        }
        if (!isFirst) {
            return p;
        } else {
            return null;
        }
    }

}
