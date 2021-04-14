/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.LabelDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.DatabaseTranslationsDTO;
import cc.altius.FASP.model.DTO.StaticLabelDTO;
import cc.altius.FASP.model.DTO.StaticLabelLanguagesDTO;
import cc.altius.FASP.model.DTO.rowMapper.DatabaseTranslationsDTORowMapper;
import cc.altius.FASP.model.DTO.rowMapper.StaticLabelResultSetExtractor;
import cc.altius.FASP.model.Label;
import cc.altius.utils.DateUtils;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class LabelDaoImpl implements LabelDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    @Transactional
    public int addLabel(Label label, int sourceId, int curUser) {
        String curDate = DateUtils.getCurrentDateString(DateUtils.EST, DateUtils.YMDHMS);
        SimpleJdbcInsert si = new SimpleJdbcInsert(this.jdbcTemplate).withTableName("ap_label").usingGeneratedKeyColumns("LABEL_ID");
        Map<String, Object> params = new HashMap<>();
        params.put("LABEL_EN", label.getLabel_en());
        params.put("LABEL_FR", label.getLabel_fr());
        params.put("LABEL_SP", label.getLabel_sp());
        params.put("LABEL_PR", label.getLabel_pr());
        params.put("SOURCE_ID", sourceId);
        params.put("CREATED_BY", curUser);
        params.put("CREATED_DATE", curDate);
        params.put("LAST_MODIFIED_BY", curUser);
        params.put("LAST_MODIFIED_DATE", curDate);
        return si.executeAndReturnKey(params).intValue();
    }

    @Override
    public List<DatabaseTranslationsDTO> getDatabaseLabelsList(int realmId) {
        String sql = "SELECT l.*,-1 As `REALM_ID`,ls.SOURCE_DESC AS `LABEL_FOR` \n"
                + "FROM fasp.ap_label l\n"
                + "left join ap_label_source ls on l.SOURCE_ID=ls.SOURCE_ID;";
        return this.jdbcTemplate.query(sql, new DatabaseTranslationsDTORowMapper());
    }

    @Override
    public boolean saveDatabaseLabels(List<String> label, CustomUserDetails curUser) {
        String curDate = DateUtils.getCurrentDateString(DateUtils.EST, DateUtils.YMDHMS);
        List<Object[]> batchInsert = new ArrayList<Object[]>();
        Gson gson = new Gson();
        for (int i = 0; i < label.size(); i++) {
            Label labelData = gson.fromJson(label.get(i), Label.class);
            if (labelData != null) {
                Object[] valuesInsert = new Object[]{
                    labelData.getLabel_en(),
                    labelData.getLabel_fr(),
                    labelData.getLabel_pr(),
                    labelData.getLabel_sp(),
                    curUser.getUserId(),
                    curDate,
                    labelData.getLabelId()
                };
                batchInsert.add(valuesInsert);
            }
        }
        String sql = " UPDATE ap_label l set l.`LABEL_EN`=?,l.`LABEL_FR`=?,l.`LABEL_PR`=?,l.`LABEL_SP`=?,l.`LAST_MODIFIED_BY`=?,l.`LAST_MODIFIED_DATE`=? where l.`LABEL_ID`=? ";
        int[] count = this.jdbcTemplate.batchUpdate(sql, batchInsert);
        return true;
    }

    @Override
    public List<StaticLabelDTO> getStaticLabelsList() {
        String sql = "	select sl.STATIC_LABEL_ID,sl.LABEL_CODE,sll.STATIC_LABEL_LANGUAGE_ID,sll.LABEL_TEXT,sll.LANGUAGE_ID from ap_static_label sl\n"
                + "left join ap_static_label_languages sll on sl.STATIC_LABEL_ID=sll.STATIC_LABEL_ID\n"
                + "ORDER BY sl.STATIC_LABEL_ID, sll.LANGUAGE_ID;";
        return this.jdbcTemplate.query(sql, new StaticLabelResultSetExtractor());
    }

    @Override
    public boolean saveStaticLabels(List<StaticLabelDTO> staticLabelList, CustomUserDetails curUser) {
        List<Object[]> batchInsert;
        for (StaticLabelDTO staticLabelDTO : staticLabelList) {
            batchInsert = new ArrayList<Object[]>();
            String sql = "DELETE l.* FROM ap_static_label_languages l WHERE l.`STATIC_LABEL_ID`=?;";
            this.jdbcTemplate.update(sql, staticLabelDTO.getStaticLabelId());
            String sql1 = "INSERT INTO ap_static_label_languages VALUES  (NULL,?,?,?)";
            for (StaticLabelLanguagesDTO staticLabelLanguagesDTO : staticLabelDTO.getStaticLabelLanguages()) {
                if (staticLabelLanguagesDTO.getLabelText() != "") {
                    Object[] valuesInsert = new Object[]{
                        staticLabelDTO.getStaticLabelId(),
                        staticLabelLanguagesDTO.getLanguageId(),
                        staticLabelLanguagesDTO.getLabelText()
                    };
                    batchInsert.add(valuesInsert);
                }
            }
            this.jdbcTemplate.batchUpdate(sql1, batchInsert);
        }
        return true;
    }

}
