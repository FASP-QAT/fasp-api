/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO.rowMapper;

import cc.altius.FASP.model.DTO.PrgCountryDTO;
import cc.altius.FASP.model.DTO.PrgCurrencyDTO;
import cc.altius.FASP.model.DTO.PrgHealthAreaDTO;
import cc.altius.FASP.model.DTO.PrgLabelDTO;
import cc.altius.FASP.model.DTO.PrgLanguageDTO;
import cc.altius.FASP.model.DTO.PrgOrganisationDTO;
import cc.altius.FASP.model.DTO.PrgProgramDataDTO;
import cc.altius.FASP.model.DTO.PrgRealmCountryDTO;
import cc.altius.FASP.model.DTO.PrgRealmDTO;
import cc.altius.FASP.model.DTO.PrgUnitDTO;
import cc.altius.FASP.model.DTO.PrgUnitTypeDTO;
import cc.altius.FASP.model.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class PrgProgramDataDTORowMapper implements RowMapper<PrgProgramDataDTO> {

    @Override
    public PrgProgramDataDTO mapRow(ResultSet rs, int i) throws SQLException {
        PrgProgramDataDTO programData = new PrgProgramDataDTO();
        programData.setProgramId(rs.getInt("PROGRAM_ID"));
        programData.setAirFreightPerc(rs.getDouble("AIR_FREIGHT_PERC"));
        programData.setApprovedToShippedLeadTime(rs.getInt("APPROVED_TO_SHIPPED_LEAD_TIME"));
        programData.setDeliveredToReceivedLeadTime(rs.getInt("DELIVERED_TO_RECEIVED_LEAD_TIME"));
        programData.setDraftToSubmittedLeadTime(rs.getInt("DRAFT_TO_SUBMITTED_LEAD_TIME"));

        PrgHealthAreaDTO healthArea = new PrgHealthAreaDTO();
        healthArea.setHealthAreaId(rs.getInt("HEALTH_AREA_ID"));
        healthArea.setLabel(new PrgLabelDTORowMapper("HEALTH_AREA_").mapRow(rs, i));
        programData.setHealthArea(healthArea);

        programData.setLabel(new PrgLabelDTORowMapper("PROGRAM_").mapRow(rs, i));

        User lastModifiedBy = new User();
        lastModifiedBy.setUserId(rs.getInt("LAST_MODIFIED_BY"));
        lastModifiedBy.setUsername(rs.getString("LAST_MODIFIED_BY_USERNAME"));
        programData.setLastModifiedBy(lastModifiedBy);

        programData.setLastModifiedDate(rs.getTimestamp("LAST_MODIFIED_DATE"));
        programData.setMonthsInFutureForAMC(rs.getInt("MONTHS_IN_FUTURE_FOR_AMC"));
        programData.setMonthsInPastForAMC(rs.getInt("MONTHS_IN_PAST_FOR_AMC"));

        PrgOrganisationDTO organisation = new PrgOrganisationDTO();
        organisation.setLabel(new PrgLabelDTORowMapper("ORGANISATION_").mapRow(rs, i));
        organisation.setOrganisationId(rs.getInt("ORGANISATION_ID"));
        programData.setOrganisation(organisation);

        programData.setPlanToDraftLeadTime(rs.getInt("PLAN_TO_DRAFT_LEAD_TIME"));

        User programManager = new User();
        programManager.setUserId(rs.getInt("PROGRAM_MANAGER_USER_ID"));
        programManager.setUsername(rs.getString("PROGRAM_MANAGER_USERNAME"));
        programData.setProgramManagerUser(programManager);

        programData.setProgramNotes(rs.getString("PROGRAM_NOTES"));

        PrgRealmCountryDTO realmCountry = new PrgRealmCountryDTO();
        realmCountry.setAirFreightPerc(rs.getDouble("REALM_COUNTRY_AIR_FREIGHT_PERC"));
        realmCountry.setArrivedToDeliveredLeadTime(rs.getInt("ARRIVED_TO_DELIVERED_LEAD_TIME"));

        PrgCountryDTO country = new PrgCountryDTO();
        country.setCountryId(rs.getInt("COUNTRY_ID"));

        PrgCurrencyDTO currency = new PrgCurrencyDTO();
        currency.setConversionRateToUsd(rs.getDouble("CONVERSION_RATE_TO_USD"));
        currency.setCurrencyCode(rs.getString("CURRENCY_CODE"));
        currency.setCurrencyId(rs.getInt("CURRENCY_ID"));
        currency.setCurrencySymbol(rs.getString("CURRENCY_SYMBOL"));

        currency.setLabel(new PrgLabelDTORowMapper("CURRENCY_").mapRow(rs, i));
        country.setCurrency(currency);

        country.setLabel(new PrgLabelDTORowMapper("COUNTRY_").mapRow(rs, i));
        PrgLanguageDTO language = new PrgLanguageDTO();
        language.setLanguageId(rs.getInt("LANGUAGE_ID"));
        language.setLanguageName(rs.getString("LANGUAGE_NAME"));
        country.setLanguage(language);
        realmCountry.setCountry(country);

        PrgCurrencyDTO defaultCurrency = new PrgCurrencyDTO();
        defaultCurrency.setConversionRateToUsd(rs.getDouble("DEFAULT_CONVERSION_RATE_TO_USD"));
        defaultCurrency.setCurrencyCode(rs.getString("DEFAULT_CURRENCY_CODE"));
        defaultCurrency.setCurrencyId(rs.getInt("DEFAULT_CURRENCY_ID"));
        defaultCurrency.setCurrencySymbol(rs.getString("DEFAULT_CURRENCY_SYMBOL"));

        defaultCurrency.setLabel(new PrgLabelDTORowMapper("DEFAULT_CURRENCY_").mapRow(rs, i));
        realmCountry.setDefaultCurrency(defaultCurrency);

        PrgUnitDTO palletUnit = new PrgUnitDTO();
        palletUnit.setLabel(new PrgLabelDTORowMapper("PALLET_UNIT_").mapRow(rs, i));
        palletUnit.setUnitCode(rs.getString("PALLET_UNIT_CODE"));
        palletUnit.setUnitId(rs.getInt("PALLET_UNIT_ID"));

        PrgUnitTypeDTO palletUnitType = new PrgUnitTypeDTO();
        palletUnitType.setLabel(new PrgLabelDTORowMapper("PALLET_UNIT_TYPE_").mapRow(rs, i));
        palletUnitType.setUnitTypeId(rs.getInt("PALLET_UNIT_TYPE_ID"));
        palletUnit.setUnitType(palletUnitType);
        realmCountry.setPalletUnit(palletUnit);

        PrgRealmDTO realm = new PrgRealmDTO();
        realm.setDefaultRealm(rs.getBoolean("DEFAULT_REALM"));
        realm.setLabel(new PrgLabelDTORowMapper("REALM_").mapRow(rs, i));
        realm.setMonthsInFutureForAMC(rs.getInt("MONTHS_IN_FUTURE_FOR_AMC_REALM"));
        realm.setMonthsInPastForAMC(rs.getInt("MONTHS_IN_PAST_FOR_AMC_REALM"));
        realm.setOrderFrequency(rs.getInt("ORDER_FREQUENCY"));
        realm.setRealmCode(rs.getString("REALM_CODE"));
        realm.setRealmId(rs.getInt("REALM_ID"));
        realmCountry.setRealm(realm);
        realmCountry.setRealmCountryId(rs.getInt("REALM_COUNTRY_ID"));
        realmCountry.setSeaFreightPerc(rs.getDouble("REALM_COUNTRY_SEA_FREIGHT_PREC"));
        realmCountry.setShippedToArrivedAirLeadTime(rs.getInt("SHIPPED_TO_ARRIVED_AIR_LEAD_TIME"));
        realmCountry.setShippedToArrivedSeaLeadTime(rs.getInt("SHIPPED_TO_ARRIVED_SEA_LEAD_TIME"));

        programData.setRealmCountry(realmCountry);
        programData.setSeaFreightPerc(rs.getDouble("SEA_FREIGHT_PERC"));
        programData.setSubmittedToApprovedLeadTime(rs.getInt("SUBMITTED_TO_APPROVED_LEAD_TIME"));
        programData.setProgramVersion(rs.getInt("PROGRAM_VERSION"));
        return programData;
    }
}
