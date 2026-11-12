/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.BroadcastDao;
import cc.altius.FASP.model.BasicUser;
import cc.altius.FASP.model.BroadcastMessage;
import cc.altius.FASP.model.BroadcastRecipient;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.EmailCategory;
import cc.altius.FASP.model.UserNotificationPreference;
import cc.altius.FASP.model.rowMapper.BasicUserRowMapper;
import cc.altius.FASP.model.rowMapper.LabelRowMapper;
import cc.altius.utils.DateUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
public class BroadcastDaoImpl implements BroadcastDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<EmailCategory> getEmailCategoryList() {
        String sql = "SELECT c.CATEGORY_ID, c.CATEGORY_CODE, c.ALLOW_OPT_OUT, c.DEFAULT_OPT_IN, "
                + "c.LABEL_ID, c.LABEL_EN, c.LABEL_FR, c.LABEL_SP, c.LABEL_PR "
                + "FROM vw_email_category c ORDER BY c.CATEGORY_ID";
        return this.jdbcTemplate.query(sql, new RowMapper<EmailCategory>() {
            @Override
            public EmailCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new EmailCategory(
                        rs.getInt("CATEGORY_ID"),
                        rs.getString("CATEGORY_CODE"),
                        new LabelRowMapper("").mapRow(rs, rowNum),
                        rs.getBoolean("ALLOW_OPT_OUT"),
                        rs.getBoolean("DEFAULT_OPT_IN")
                );
            }
        });
    }

    @Override
    public EmailCategory getEmailCategoryById(int categoryId) {
        String sql = "SELECT c.CATEGORY_ID, c.CATEGORY_CODE, c.ALLOW_OPT_OUT, c.DEFAULT_OPT_IN, "
                + "c.LABEL_ID, c.LABEL_EN, c.LABEL_FR, c.LABEL_SP, c.LABEL_PR "
                + "FROM vw_email_category c WHERE c.CATEGORY_ID = ?";
        try {
            return this.jdbcTemplate.queryForObject(sql, new RowMapper<EmailCategory>() {
                @Override
                public EmailCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return new EmailCategory(
                            rs.getInt("CATEGORY_ID"),
                            rs.getString("CATEGORY_CODE"),
                            new LabelRowMapper("").mapRow(rs, rowNum),
                            rs.getBoolean("ALLOW_OPT_OUT"),
                            rs.getBoolean("DEFAULT_OPT_IN")
                    );
                }
            }, categoryId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<UserNotificationPreference> getNotificationPreferencesByUserId(int userId) {
        String sql = "SELECT c.CATEGORY_ID, c.CATEGORY_CODE, c.ALLOW_OPT_OUT, "
                + "c.LABEL_ID, c.LABEL_EN, c.LABEL_FR, c.LABEL_SP, c.LABEL_PR, "
                + "p.PREFERENCE_ID, IFNULL(p.OPTED_OUT, 0) `OPTED_OUT`, p.LAST_MODIFIED_DATE "
                + "FROM vw_email_category c "
                + "LEFT JOIN us_user_notification_preference p ON c.CATEGORY_ID = p.CATEGORY_ID AND p.USER_ID = ? "
                + "ORDER BY c.CATEGORY_ID";
        return this.jdbcTemplate.query(sql, new RowMapper<UserNotificationPreference>() {
            @Override
            public UserNotificationPreference mapRow(ResultSet rs, int rowNum) throws SQLException {
                UserNotificationPreference pref = new UserNotificationPreference();
                pref.setUserId(userId);
                pref.setCategoryId(rs.getInt("CATEGORY_ID"));
                pref.setCategoryCode(rs.getString("CATEGORY_CODE"));
                pref.setLabel(new LabelRowMapper("").mapRow(rs, rowNum));
                pref.setCategoryName(rs.getString("LABEL_EN"));
                pref.setAllowOptOut(rs.getBoolean("ALLOW_OPT_OUT"));
                pref.setPreferenceId(rs.getInt("PREFERENCE_ID"));
                pref.setOptedOut(rs.getBoolean("OPTED_OUT"));
                pref.setLastModifiedDate(rs.getTimestamp("LAST_MODIFIED_DATE"));
                return pref;
            }
        }, userId);
    }

    @Override
    public int saveNotificationPreferences(int userId, List<UserNotificationPreference> preferences) {
        String curDate = DateUtils.getCurrentDateString(DateUtils.EST, DateUtils.YMDHMS);
        int rows = 0;
        String sql = "INSERT INTO us_user_notification_preference (USER_ID, CATEGORY_ID, OPTED_OUT, LAST_MODIFIED_DATE) "
                + "VALUES (?, ?, ?, ?) "
                + "ON DUPLICATE KEY UPDATE OPTED_OUT = VALUES(OPTED_OUT), LAST_MODIFIED_DATE = VALUES(LAST_MODIFIED_DATE)";
        for (UserNotificationPreference pref : preferences) {
            // Mandatory categories cannot be opted out
            EmailCategory cat = getEmailCategoryById(pref.getCategoryId());
            boolean optedOut = (cat != null && cat.isAllowOptOut()) ? pref.isOptedOut() : false;
            rows += this.jdbcTemplate.update(sql, userId, pref.getCategoryId(), optedOut ? 1 : 0, curDate);
        }
        return rows;
    }

    @Override
    public List<BroadcastRecipient> getBroadcastRecipients(int categoryId, List<String> businessFunctionIds, List<Integer> languageIds) {
        return getBroadcastRecipients(categoryId, null, businessFunctionIds, languageIds);
    }

    @Override
    public List<BroadcastRecipient> getBroadcastRecipients(int categoryId, List<String> targetRoles, List<String> businessFunctionIds, List<Integer> languageIds) {
        boolean rolesEmpty = (targetRoles == null || targetRoles.isEmpty() || (targetRoles.size() == 1 && (targetRoles.get(0).equals("ALL") || targetRoles.get(0).equals("-1"))));
        boolean bfEmpty = (businessFunctionIds == null || businessFunctionIds.isEmpty() || (businessFunctionIds.size() == 1 && (businessFunctionIds.get(0).equals("ALL") || businessFunctionIds.get(0).equals("-1"))));
        boolean langEmpty = (languageIds == null || languageIds.isEmpty());

        String sql = "SELECT DISTINCT u.USER_ID, u.EMAIL_ID, u.LANGUAGE_ID, u.USERNAME "
                + "FROM us_user u "
                + "LEFT JOIN us_user_role ur ON u.USER_ID = ur.USER_ID "
                + "LEFT JOIN us_role_business_function rbf ON ur.ROLE_ID = rbf.ROLE_ID "
                + "LEFT JOIN us_user_notification_preference pref "
                + "       ON u.USER_ID = pref.USER_ID AND pref.CATEGORY_ID = :categoryId "
                + "WHERE u.ACTIVE = 1 "
                + "  AND u.EMAIL_ID IS NOT NULL "
                + "  AND TRIM(u.EMAIL_ID) != '' "
                + "  AND (pref.OPTED_OUT IS NULL OR pref.OPTED_OUT = 0) ";

        if (!rolesEmpty) {
            sql += "  AND ur.ROLE_ID IN (:roleList) ";
        }
        if (!bfEmpty) {
            sql += "  AND rbf.BUSINESS_FUNCTION_ID IN (:bfList) ";
        }
        if (!langEmpty) {
            sql += "  AND u.LANGUAGE_ID IN (:langList) ";
        }

        Map<String, Object> params = new HashMap<>();
        params.put("categoryId", categoryId);
        if (!rolesEmpty) {
            params.put("roleList", targetRoles);
        }
        if (!bfEmpty) {
            params.put("bfList", businessFunctionIds);
        }
        if (!langEmpty) {
            params.put("langList", languageIds);
        }

        return this.namedParameterJdbcTemplate.query(sql, params, new RowMapper<BroadcastRecipient>() {
            @Override
            public BroadcastRecipient mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new BroadcastRecipient(
                        rs.getInt("USER_ID"),
                        rs.getString("EMAIL_ID"),
                        rs.getInt("LANGUAGE_ID"),
                        rs.getString("USERNAME")
                );
            }
        });
    }

    @Override
    public int saveBroadcastMessage(BroadcastMessage broadcastMessage, CustomUserDetails curUser) {
        String curDate = DateUtils.getCurrentDateString(DateUtils.EST, DateUtils.YMDHMS);
        SimpleJdbcInsert insert = new SimpleJdbcInsert(this.dataSource)
                .withTableName("ap_broadcast_message")
                .usingGeneratedKeyColumns("BROADCAST_ID");

        Map<String, Object> params = new HashMap<>();
        params.put("CATEGORY_ID", broadcastMessage.getCategoryId());
        params.put("SUBJECT", broadcastMessage.getSubject());
        params.put("BODY_HTML", broadcastMessage.getBodyHtml());

        String rolesJson = null;
        String bfJson = null;
        String langJson = null;
        try {
            if (broadcastMessage.getTargetRoles() != null) {
                rolesJson = objectMapper.writeValueAsString(broadcastMessage.getTargetRoles());
            }
            if (broadcastMessage.getTargetBusinessFunctions() != null) {
                bfJson = objectMapper.writeValueAsString(broadcastMessage.getTargetBusinessFunctions());
            }
            if (broadcastMessage.getTargetLanguages() != null) {
                langJson = objectMapper.writeValueAsString(broadcastMessage.getTargetLanguages());
            }
        } catch (Exception e) {
            logger.warn("Error serializing broadcast filter JSON", e);
        }

        params.put("TARGET_ROLES", rolesJson);
        params.put("TARGET_BUSINESS_FUNCTIONS", bfJson);
        params.put("TARGET_LANGUAGES", langJson);
        params.put("TOTAL_RECIPIENTS", broadcastMessage.getTotalRecipients());
        params.put("BATCH_COUNT", broadcastMessage.getBatchCount());
        params.put("STATUS", broadcastMessage.getStatus() != null ? broadcastMessage.getStatus() : "PENDING");
        params.put("CREATED_BY", curUser.getUserId());
        params.put("CREATED_DATE", curDate);

        Number id = insert.executeAndReturnKey(params);
        return id.intValue();
    }

    @Override
    public int updateBroadcastDraft(BroadcastMessage broadcastMessage, CustomUserDetails curUser) {
        String sql = "UPDATE ap_broadcast_message SET CATEGORY_ID = ?, SUBJECT = ?, BODY_HTML = ?, "
                + "TARGET_ROLES = ?, TARGET_BUSINESS_FUNCTIONS = ?, TARGET_LANGUAGES = ?, "
                + "TOTAL_RECIPIENTS = ?, STATUS = ? WHERE BROADCAST_ID = ?";
        String rolesJson = null;
        String bfJson = null;
        String langJson = null;
        try {
            if (broadcastMessage.getTargetRoles() != null) {
                rolesJson = objectMapper.writeValueAsString(broadcastMessage.getTargetRoles());
            }
            if (broadcastMessage.getTargetBusinessFunctions() != null) {
                bfJson = objectMapper.writeValueAsString(broadcastMessage.getTargetBusinessFunctions());
            }
            if (broadcastMessage.getTargetLanguages() != null) {
                langJson = objectMapper.writeValueAsString(broadcastMessage.getTargetLanguages());
            }
        } catch (Exception e) {
            logger.warn("Error serializing broadcast filter JSON", e);
        }
        return this.jdbcTemplate.update(sql,
                broadcastMessage.getCategoryId(),
                broadcastMessage.getSubject(),
                broadcastMessage.getBodyHtml(),
                rolesJson,
                bfJson,
                langJson,
                broadcastMessage.getTotalRecipients(),
                broadcastMessage.getStatus() != null ? broadcastMessage.getStatus() : "DRAFT",
                broadcastMessage.getBroadcastId()
        );
    }

    @Override
    public BroadcastMessage getBroadcastMessageById(int broadcastId) {
        String sql = "SELECT b.BROADCAST_ID, b.CATEGORY_ID, c.CATEGORY_CODE, "
                + "c.LABEL_ID, c.LABEL_EN, c.LABEL_FR, c.LABEL_SP, c.LABEL_PR, "
                + "b.SUBJECT, b.BODY_HTML, b.TARGET_ROLES, b.TARGET_BUSINESS_FUNCTIONS, b.TARGET_LANGUAGES, "
                + "b.TOTAL_RECIPIENTS, b.BATCH_COUNT, b.STATUS, b.CREATED_DATE, "
                + "u.USER_ID `CB_USER_ID`, u.USERNAME `CB_USERNAME` "
                + "FROM ap_broadcast_message b "
                + "LEFT JOIN vw_email_category c ON b.CATEGORY_ID = c.CATEGORY_ID "
                + "LEFT JOIN us_user u ON b.CREATED_BY = u.USER_ID "
                + "WHERE b.BROADCAST_ID = ?";

        try {
            return this.jdbcTemplate.queryForObject(sql, new RowMapper<BroadcastMessage>() {
                @Override
                public BroadcastMessage mapRow(ResultSet rs, int rowNum) throws SQLException {
                    BroadcastMessage bm = new BroadcastMessage();
                    bm.setBroadcastId(rs.getInt("BROADCAST_ID"));
                    bm.setCategoryId(rs.getInt("CATEGORY_ID"));
                    bm.setLabel(new LabelRowMapper("").mapRow(rs, rowNum));
                    bm.setCategoryName(rs.getString("LABEL_EN"));
                    bm.setCategoryCode(rs.getString("CATEGORY_CODE"));
                    bm.setSubject(rs.getString("SUBJECT"));
                    bm.setBodyHtml(rs.getString("BODY_HTML"));
                    bm.setTotalRecipients(rs.getInt("TOTAL_RECIPIENTS"));
                    bm.setBatchCount(rs.getInt("BATCH_COUNT"));
                    bm.setStatus(rs.getString("STATUS"));
                    bm.setCreatedDate(rs.getTimestamp("CREATED_DATE"));
                    bm.setCreatedBy(new BasicUserRowMapper("CB_").mapRow(rs, rowNum));

                    String rolesJson = rs.getString("TARGET_ROLES");
                    String bfJson = rs.getString("TARGET_BUSINESS_FUNCTIONS");
                    String langJson = rs.getString("TARGET_LANGUAGES");
                    try {
                        if (rolesJson != null && !rolesJson.isEmpty()) {
                            bm.setTargetRoles(objectMapper.readValue(rolesJson, new TypeReference<List<String>>() {}));
                        }
                        if (bfJson != null && !bfJson.isEmpty()) {
                            bm.setTargetBusinessFunctions(objectMapper.readValue(bfJson, new TypeReference<List<String>>() {}));
                        }
                        if (langJson != null && !langJson.isEmpty()) {
                            bm.setTargetLanguages(objectMapper.readValue(langJson, new TypeReference<List<Integer>>() {}));
                        }
                    } catch (Exception e) {
                        logger.warn("Error deserializing broadcast filters", e);
                    }
                    return bm;
                }
            }, broadcastId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<BroadcastRecipient> getRecipientsByBroadcastId(int broadcastId) {
        BroadcastMessage bm = getBroadcastMessageById(broadcastId);
        if (bm == null) {
            return new ArrayList<>();
        }
        return getBroadcastRecipients(bm.getCategoryId(), bm.getTargetRoles(), bm.getTargetBusinessFunctions(), bm.getTargetLanguages());
    }

    @Override
    public int updateBroadcastStatus(int broadcastId, String status, int totalRecipients, int batchCount) {
        String sql = "UPDATE ap_broadcast_message SET STATUS = ?, TOTAL_RECIPIENTS = ?, BATCH_COUNT = ? WHERE BROADCAST_ID = ?";
        return this.jdbcTemplate.update(sql, status, totalRecipients, batchCount, broadcastId);
    }

    @Override
    public List<BroadcastMessage> getBroadcastHistory() {
        String sql = "SELECT b.BROADCAST_ID, b.CATEGORY_ID, c.CATEGORY_CODE, "
                + "c.LABEL_ID, c.LABEL_EN, c.LABEL_FR, c.LABEL_SP, c.LABEL_PR, "
                + "b.SUBJECT, b.BODY_HTML, b.TARGET_ROLES, b.TARGET_BUSINESS_FUNCTIONS, b.TARGET_LANGUAGES, "
                + "b.TOTAL_RECIPIENTS, b.BATCH_COUNT, b.STATUS, b.CREATED_DATE, "
                + "u.USER_ID `CB_USER_ID`, u.USERNAME `CB_USERNAME` "
                + "FROM ap_broadcast_message b "
                + "LEFT JOIN vw_email_category c ON b.CATEGORY_ID = c.CATEGORY_ID "
                + "LEFT JOIN us_user u ON b.CREATED_BY = u.USER_ID "
                + "ORDER BY b.BROADCAST_ID DESC LIMIT 100";

        return this.jdbcTemplate.query(sql, new RowMapper<BroadcastMessage>() {
            @Override
            public BroadcastMessage mapRow(ResultSet rs, int rowNum) throws SQLException {
                BroadcastMessage bm = new BroadcastMessage();
                bm.setBroadcastId(rs.getInt("BROADCAST_ID"));
                bm.setCategoryId(rs.getInt("CATEGORY_ID"));
                bm.setLabel(new LabelRowMapper("").mapRow(rs, rowNum));
                bm.setCategoryName(rs.getString("LABEL_EN"));
                bm.setCategoryCode(rs.getString("CATEGORY_CODE"));
                bm.setSubject(rs.getString("SUBJECT"));
                bm.setBodyHtml(rs.getString("BODY_HTML"));
                bm.setTotalRecipients(rs.getInt("TOTAL_RECIPIENTS"));
                bm.setBatchCount(rs.getInt("BATCH_COUNT"));
                bm.setStatus(rs.getString("STATUS"));
                bm.setCreatedDate(rs.getTimestamp("CREATED_DATE"));
                bm.setCreatedBy(new BasicUserRowMapper("CB_").mapRow(rs, rowNum));

                String rolesJson = rs.getString("TARGET_ROLES");
                String bfJson = rs.getString("TARGET_BUSINESS_FUNCTIONS");
                String langJson = rs.getString("TARGET_LANGUAGES");
                try {
                    if (rolesJson != null && !rolesJson.isEmpty()) {
                        bm.setTargetRoles(objectMapper.readValue(rolesJson, new TypeReference<List<String>>() {}));
                    }
                    if (bfJson != null && !bfJson.isEmpty()) {
                        bm.setTargetBusinessFunctions(objectMapper.readValue(bfJson, new TypeReference<List<String>>() {}));
                    }
                    if (langJson != null && !langJson.isEmpty()) {
                        bm.setTargetLanguages(objectMapper.readValue(langJson, new TypeReference<List<Integer>>() {}));
                    }
                } catch (Exception e) {
                    logger.warn("Error deserializing broadcast filters", e);
                }
                return bm;
            }
        });
    }

    @Override
    public String getOrCreateUnsubscribeToken(int userId) {
        String selectSql = "SELECT TOKEN FROM us_user_unsubscribe_token WHERE USER_ID = ?";
        try {
            return this.jdbcTemplate.queryForObject(selectSql, String.class, userId);
        } catch (EmptyResultDataAccessException e) {
            String token = UUID.randomUUID().toString().replace("-", "") + Long.toHexString(System.currentTimeMillis());
            String insertSql = "INSERT INTO us_user_unsubscribe_token (USER_ID, TOKEN, CREATED_DATE) VALUES (?, ?, NOW())";
            this.jdbcTemplate.update(insertSql, userId, token);
            return token;
        }
    }

    @Override
    public Integer getUserIdByUnsubscribeToken(String token) {
        String sql = "SELECT USER_ID FROM us_user_unsubscribe_token WHERE TOKEN = ?";
        try {
            return this.jdbcTemplate.queryForObject(sql, Integer.class, token);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public int unsubscribeByToken(String token, int categoryId) {
        Integer userId = getUserIdByUnsubscribeToken(token);
        if (userId == null) {
            return 0;
        }
        EmailCategory cat = getEmailCategoryById(categoryId);
        if (cat == null || !cat.isAllowOptOut()) {
            return -1; // Cannot opt out of mandatory categories
        }
        String sql = "INSERT INTO us_user_notification_preference (USER_ID, CATEGORY_ID, OPTED_OUT, LAST_MODIFIED_DATE) "
                + "VALUES (?, ?, 1, NOW()) "
                + "ON DUPLICATE KEY UPDATE OPTED_OUT = 1, LAST_MODIFIED_DATE = NOW()";
        return this.jdbcTemplate.update(sql, userId, categoryId);
    }

}
