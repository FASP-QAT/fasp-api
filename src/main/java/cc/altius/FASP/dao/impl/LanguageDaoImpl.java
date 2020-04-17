/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.LanguageDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.Language;
import cc.altius.FASP.model.LabelJson;
import cc.altius.FASP.model.rowMapper.LanguageRowMapper;
import cc.altius.utils.DateUtils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

/**
 *
 * @author altius
 */
@Repository
public class LanguageDaoImpl implements LanguageDao {

    private DataSource dataSource;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    private final String sqlListString = "SELECT la.LANGUAGE_ID, la.LANGUAGE_CODE, la.LANGUAGE_NAME, "
            + "cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, la.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, la.LAST_MODIFIED_DATE, la.ACTIVE  "
            + "FROM ap_language la  "
            + "LEFT JOIN us_user cb ON la.CREATED_BY=cb.USER_ID "
            + "LEFT JOIN us_user lmb ON la.LAST_MODIFIED_BY=lmb.USER_ID "
            + "WHERE TRUE ";

    @Override
    public int addLanguage(Language language, CustomUserDetails curUser) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(dataSource).withTableName("ap_language").usingGeneratedKeyColumns("LANGUAGE_ID");
        String curDate = DateUtils.getCurrentDateString(DateUtils.EST, DateUtils.YMDHMS);
        Map<String, Object> map = new HashedMap<>();
        map.put("LANGUAGE_NAME", language.getLanguageName());
        map.put("LANGUAGE_CODE", language.getLanguageCode());
        map.put("ACTIVE", 1);
        map.put("CREATED_BY", curUser.getUserId());
        map.put("CREATED_DATE", curDate);
        map.put("LAST_MODIFIED_BY", curUser.getUserId());
        map.put("LAST_MODIFIED_DATE", curDate);
        int languageId = insert.executeAndReturnKey(map).intValue();
        return languageId;
    }

    @Override
    public int editLanguage(Language language, CustomUserDetails curUser) {
        String curDate = DateUtils.getCurrentDateString(DateUtils.EST, DateUtils.YMDHMS);
        String sql = "UPDATE ap_language la SET la.`LANGUAGE_NAME`=:languageName, la.`LANGUAGE_CODE`=:languageCode, la.`ACTIVE`=:active,"
                + " la.`LAST_MODIFIED_BY`=:lastModifiedBy,la.`LAST_MODIFIED_DATE`=:lastModifiedDate"
                + " WHERE la.`LANGUAGE_ID`=:languageId";
        Map<String, Object> map = new HashMap<>();
        map.put("languageName", language.getLanguageName());
        map.put("languageCode", language.getLanguageCode());
        map.put("active", language.isActive());
        map.put("languageId", language.getLanguageId());
        map.put("lastModifiedBy", curUser.getUserId());
        map.put("lastModifiedDate", curDate);
        int updatedRow = namedParameterJdbcTemplate.update(sql, map);
        return updatedRow;
    }

    @Override
    public List<Language> getLanguageListForSync(String lastSyncDate) {
        String sqlString = this.sqlListString + " AND la.LAST_MODIFIED_DATE>=:lastSyncDate ";
        Map<String, Object> params = new HashMap<>();
        params.put("lastSyncDate", lastSyncDate);
        return this.namedParameterJdbcTemplate.query(sqlString, params, new LanguageRowMapper());
    }

    @Override
    public List<Language> getLanguageList(boolean active, CustomUserDetails curUser) {
        String sqlString = this.sqlListString ;
        if (active) {
            sqlString += " AND la.`ACTIVE` ";
        }
        return this.namedParameterJdbcTemplate.query(sqlString, new LanguageRowMapper());
    }

    @Override
    public Language getLanguageById(int languageId, CustomUserDetails curUser) {
        String sqlString = this.sqlListString + " AND la.LANGUAGE_ID=:languageId ";
        Map<String, Object> params = new HashMap<>();
        params.put("languageId", languageId);
        return this.namedParameterJdbcTemplate.queryForObject(sqlString, params, new LanguageRowMapper());
    }

    @Override
    public Map<String, String> getLanguageJsonForStaticLabels(String languageCode) {
        String sqlString = "SELECT "
                + "	sl.LABEL_CODE, sll.LABEL_TEXT "
                + "FROM ap_static_label sl  "
                + "LEFT JOIN ap_static_label_languages sll ON sll.STATIC_LABEL_ID=sl.STATIC_LABEL_ID "
                + "LEFT JOIN ap_language l ON sll.LANGUAGE_ID=l.LANGUAGE_ID "
                + "WHERE l.LANGUAGE_CODE=:languageCode";
        Map<String, Object> params = new HashMap<>();
        params.put("languageCode", languageCode);
        List<LabelJson> result = this.namedParameterJdbcTemplate.query(sqlString, params, new RowMapper<LabelJson>() {
            @Override
            public LabelJson mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new LabelJson(rs.getString("LABEL_CODE"), rs.getString("LABEL_TEXT"));
            }
        });
        Map<String, String> result1 = result.stream().collect(Collectors.toMap(LabelJson::getLabelCode, LabelJson::getLabelText));
        System.out.println("result" + result1);
        return result1;
    }

}
