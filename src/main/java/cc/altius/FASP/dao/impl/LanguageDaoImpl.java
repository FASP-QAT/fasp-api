/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.LanguageDao;
import cc.altius.FASP.model.DTO.PrgLanguageDTO;
import cc.altius.FASP.model.DTO.rowMapper.PrgLanguageDTORowMapper;
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
import org.springframework.jdbc.core.JdbcTemplate;
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

    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<Language> getLanguageList(boolean active) {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT * FROM ap_language l ");
        if (active) {
            sb.append(" WHERE l.`ACTIVE` ");
        }
        return this.jdbcTemplate.query(sb.toString(), new LanguageRowMapper());
    }

    @Override
    public int addLanguage(Language language) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(dataSource).withTableName("ap_language").usingGeneratedKeyColumns("LANGUAGE_ID");
        String curDate = DateUtils.getCurrentDateString(DateUtils.EST, DateUtils.YMDHMS);
        Map<String, Object> map = new HashedMap<>();
        map.put("LANGUAGE_NAME", language.getLanguageName());
        map.put("LANGUAGE_CODE", language.getLanguageCode());
        map.put("ACTIVE", 1);
        map.put("CREATED_BY", 1);
        map.put("CREATED_DATE", curDate);
        map.put("LAST_MODIFIED_BY", 1);
        map.put("LAST_MODIFIED_DATE", curDate);
        int languageId = insert.executeAndReturnKey(map).intValue();
        return languageId;
    }

    @Override
    public int editLanguage(Language language) {
        String curDate = DateUtils.getCurrentDateString(DateUtils.EST, DateUtils.YMDHMS);
        String sql = "Update ap_language l set l.`LANGUAGE_NAME`=:languageName, l.`LANGUAGE_CODE`=:languageCode, l.`ACTIVE`=:active,"
                + " l.`LAST_MODIFIED_BY`=:lastModifiedBy,l.`LAST_MODIFIED_DATE`=:lastModifiedDate"
                + " WHERE l.`LANGUAGE_ID`=:languageId";
        Map<String, Object> map = new HashMap<>();
        map.put("languageName", language.getLanguageName());
        map.put("languageCode", language.getLanguageCode());
        map.put("active", language.isActive());
        map.put("languageId", language.getLanguageId());
        map.put("lastModifiedBy", 1);
        map.put("lastModifiedDate", curDate);
        int updatedRow = namedParameterJdbcTemplate.update(sql, map);
        return updatedRow;
    }

    @Override
    public List<PrgLanguageDTO> getLanguageListForSync(String lastSyncDate) {
        String sql = "SELECT l.`LANGUAGE_ID`,l.`LANGUAGE_NAME`,l.`ACTIVE` FROM ap_language l";
        Map<String, Object> params = new HashMap<>();
        if (!lastSyncDate.equals("null")) {
            sql += " WHERE l.`LAST_MODIFIED_DATE`>:lastSyncDate;";
            params.put("lastSyncDate", lastSyncDate);
        }
        NamedParameterJdbcTemplate nm = new NamedParameterJdbcTemplate(jdbcTemplate);
        return nm.query(sql, params, new PrgLanguageDTORowMapper());
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
        return result1;
    }

}
