/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.LabelDao;
import cc.altius.FASP.dao.ProductCategoryDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.PrgProductCategoryDTO;
import cc.altius.FASP.model.DTO.rowMapper.PrgProductCategoryDTORowMapper;
import cc.altius.FASP.model.ProductCategory;
import cc.altius.FASP.model.rowMapper.ProductCategoryRowMapper;
import cc.altius.utils.DateUtils;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author altius
 */
@Repository
public class ProductCategoryDaoImpl implements ProductCategoryDao {

    @Autowired
    private LabelDao labelDao;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<PrgProductCategoryDTO> getProductCategoryListForSync(String lastSyncDate) {
        String sql = "SELECT pc.`ACTIVE`,pc.`PRODUCT_CATEGORY_ID`,l.`LABEL_EN`,l.`LABEL_FR`,l.`LABEL_PR`,l.`LABEL_SP` "
                + "FROM rm_`product_category pc "
                + "LEFT JOIN ap_label l ON l.`LABEL_ID`=pc.`LABEL_ID`";
        Map<String, Object> params = new HashMap<>();
        if (!lastSyncDate.equals("null")) {
            sql += " WHERE pc.`LAST_MODIFIED_DATE`>:lastSyncDate;";
            params.put("lastSyncDate", lastSyncDate);
        }
        return this.namedParameterJdbcTemplate.query(sql, params, new PrgProductCategoryDTORowMapper());
    }

    @Override
    @Transactional(propagation = Propagation.NESTED)
    public int addProductCategory(ProductCategory productCategory, CustomUserDetails curUser) {
        SimpleJdbcInsert si = new SimpleJdbcInsert(dataSource).withTableName("rm_product_category").usingGeneratedKeyColumns("PRODUCT_CATEGORY_ID");
        int labelId = this.labelDao.addLabel(productCategory.getLabel(), curUser.getUserId());
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params = new HashMap<>();
        params.put("REALM_ID", productCategory.getRealm().getRealmId());
        params.put("LABEL_ID", labelId);
        params.put("SORT_ORDER", productCategory.getSortOrder());
        params.put("`LEVEL`", productCategory.getLevel());
        params.put("CREATED_DATE", curDate);
        params.put("CREATED_BY", curUser.getUserId());
        params.put("LAST_MODIFIED_DATE", curDate);
        params.put("LAST_MODIFIED_BY", curUser.getUserId());
        params.put("ACTIVE", true);
        return si.executeAndReturnKey(params).intValue();
    }

    @Override
    @Transactional(propagation = Propagation.NESTED)
    public int updateProductCategory(ProductCategory productCategory, CustomUserDetails curUser) {
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        String sqlString = "UPDATE rm_product_category pc "
                + "LEFT JOIN ap_label pcl ON pc.LABEL_ID=pcl.LABEL_ID "
                + "SET "
                + "pc.SORT_ORDER=:sortOrder, "
                + "pc.`LEVEL`=:level, "
                + "pc.ACTIVE=:active, "
                + "pc.LAST_MODIFIED_BY=IF(pc.`LEVEL`!=:level OR pc.SORT_ORDER!=:sortOrder OR pc.ACTIVE!=:active =:curUser, :curUser, pc.LAST_MODIFIED_BY), "
                + "pc.LAST_MODIFIED_DATE=IF(pc.`LEVEL`!=:level OR pc.SORT_ORDER!=:sortOrder OR pc.ACTIVE!=:active =:curUser, :curDate, pc.LAST_MODIFIED_DATE), "
                + "pcl.LABEL_EN=:labelEn, "
                + "pcl.LAST_MODIFIED_BY=IF(pc;.LABEL_EN!=:labelEn, :curUser, pcl.LAST_MODIFIED_BY), "
                + "pcl.LAST_MODIFIED_DATE=IF(pc;.LABEL_EN!=:labelEn, :curDate, pcl.LAST_MODIFIED_DATE), "
                + "WHERE PRODUCT_CATEGORY_ID=:productCategoryId ";
        Map<String, Object> params = new HashMap<>();
        params.put("productCategoryId", productCategory.getProductCategoryId());
        params.put("level", productCategory.getLevel());
        params.put("sortOrder", productCategory.getSortOrder());
        params.put("labelEn", productCategory.getLabel().getLabel_en());
        params.put("lastModifiedDate", curDate);
        params.put("lastModifiedBy", curUser.getUserId());
        params.put("active", productCategory.isActive());
        params.put("curUser", curUser.getUserId());
        params.put("curDate", curDate);
        return this.namedParameterJdbcTemplate.update(sqlString, params);
    }

    @Override
    public List<ProductCategory> getProductCategoryList(CustomUserDetails curUser) {
        String sqlString = "SELECT  "
                + "    pc.PRODUCT_CATEGORY_ID, pc.SORT_ORDER,  "
                + "    pcl.LABEL_ID, pcl.LABEL_EN, pcl.LABEL_FR, pcl.LABEL_PR, pcl.LABEL_SP, "
                + "    r.REALM_ID, r.REALM_CODE,  "
                + "    rl.LABEL_ID `REALM_LABEL_ID`, rl.LABEL_EN `REALM_LABEL_EN`, rl.LABEL_FR `REALM_LABEL_FR`, rl.LABEL_PR `REALM_LABEL_PR`, rl.LABEL_SP `REALM_LABEL_SP`,"
                + "    cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, pc.ACTIVE, pc.CREATED_DATE, pc.LAST_MODIFIED_DATE "
                + "	FROM rm_product_category pc  "
                + "LEFT JOIN ap_label pcl ON pc.LABEL_ID=pcl.LABEL_ID "
                + "LEFT JOIN rm_realm r ON pc.REALM_ID=r.REALM_ID "
                + "LEFT JOIN ap_label rl ON r.LABEL_ID=rl.LABEL_ID "
                + "LEFT JOIN us_user cb ON pc.CREATED_BY=cb.USER_ID "
                + "LEFT JOIN us_user lmb ON pc.LAST_MODIFIED_BY=lmb.USER_ID "
                + "WHERE PRODUCT_CATEGORY_ID=:productCategoryId "
                + "AND pc.`ACTIVE` ";
        Map<String, Object> params = new HashMap<>();
        if (curUser.getRealm().getRealmId() != -1) {
            sqlString += "AND pc.REALM_ID=:realmId ";
            params.put("realmId", curUser.getRealm().getRealmId());
        }
        sqlString += "ORDER BY pc.SORT_ORDER";
        return this.namedParameterJdbcTemplate.query(sqlString, params, new ProductCategoryRowMapper());
    }

    @Override
    public ProductCategory getProductCategoryById(int productCategoryId, CustomUserDetails curUser) {
        String sqlString = "SELECT  "
                + "    pc.PRODUCT_CATEGORY_ID, pc.SORT_ORDER,  "
                + "    pcl.LABEL_ID, pcl.LABEL_EN, pcl.LABEL_FR, pcl.LABEL_PR, pcl.LABEL_SP, "
                + "    r.REALM_ID, r.REALM_CODE,  "
                + "    rl.LABEL_ID `REALM_LABEL_ID`, rl.LABEL_EN `REALM_LABEL_EN`, rl.LABEL_FR `REALM_LABEL_FR`, rl.LABEL_PR `REALM_LABEL_PR`, rl.LABEL_SP `REALM_LABEL_SP`,"
                + "    cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, pc.ACTIVE, pc.CREATED_DATE, pc.LAST_MODIFIED_DATE "
                + "	FROM rm_product_category pc  "
                + "LEFT JOIN ap_label pcl ON pc.LABEL_ID=pcl.LABEL_ID "
                + "LEFT JOIN rm_realm r ON pc.REALM_ID=r.REALM_ID "
                + "LEFT JOIN ap_label rl ON r.LABEL_ID=rl.LABEL_ID "
                + "LEFT JOIN us_user cb ON pc.CREATED_BY=cb.USER_ID "
                + "LEFT JOIN us_user lmb ON pc.LAST_MODIFIED_BY=lmb.USER_ID "
                + "WHERE PRODUCT_CATEGORY_ID=:productCategoryId "
                + "AND pc.`ACTIVE` "
                + "WHERE pc.PRODUCT_CATEGORY_ID=:productCategoryId ";
        Map<String, Object> params = new HashMap<>();
        if (curUser.getRealm().getRealmId() != -1) {
            sqlString += "AND pc.REALM_ID=:realmId ";
            params.put("realmId", curUser.getRealm().getRealmId());
        }
        params.put("productCategoryId", productCategoryId);
        return this.namedParameterJdbcTemplate.queryForObject(sqlString, params, new ProductCategoryRowMapper());
    }

    @Override
    public List<ProductCategory> getProductCategoryList(CustomUserDetails curUser, int realmId, int productCategoryId, boolean includeMainBranch, boolean includeAllChildren) {
        String sqlString = "SELECT  "
                + "	pc.PRODUCT_CATEGORY_ID, pc.SORT_ORDER, pc.`LEVEL`,  "
                + "    pcl.LABEL_ID, pcl.LABEL_EN, pcl.LABEL_FR, pcl.LABEL_PR, pcl.LABEL_SP, "
                + "    r.REALM_ID, r.REALM_CODE,  "
                + "    rl.LABEL_ID `REALM_LABEL_ID`, rl.LABEL_EN `REALM_LABEL_EN`, rl.LABEL_FR `REALM_LABEL_FR`, rl.LABEL_PR `REALM_LABEL_PR`, rl.LABEL_SP `REALM_LABEL_SP`, "
                + "    cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, pc.ACTIVE, pc.CREATED_DATE, pc.LAST_MODIFIED_DATE "
                + "	FROM rm_product_category pc  "
                + "LEFT JOIN ap_label pcl ON pc.LABEL_ID=pcl.LABEL_ID "
                + "LEFT JOIN rm_realm r ON pc.REALM_ID=r.REALM_ID "
                + "LEFT JOIN ap_label rl ON r.LABEL_ID=rl.LABEL_ID "
                + "LEFT JOIN us_user cb ON pc.CREATED_BY=cb.USER_ID "
                + "LEFT JOIN us_user lmb ON pc.LAST_MODIFIED_BY=lmb.USER_ID "
                + "WHERE  "
                + "	pc.ACTIVE AND pc.REALM_ID=:realmId "
                + "    AND pc.`SORT_ORDER` LIKE (SELECT CONCAT(pc2.`SORT_ORDER`,IF(:includeMainBranch,\"\",\".\"),\"%\") FROM rm_product_category pc2 WHERE pc2.`PRODUCT_CATEGORY_ID`=:productCategoryId) "
                + "    AND (:includeAllChildren OR pc.`LEVEL` = (SELECT pc3.`LEVEL` FROM rm_product_category pc3 WHERE pc3.PRODUCT_CATEGORY_ID=:productCategoryId) OR pc.`LEVEL` = (SELECT pc3.`LEVEL`+1 FROM rm_product_category pc3 WHERE pc3.PRODUCT_CATEGORY_ID=:productCategoryId)) ";
        Map<String, Object> params = new HashMap<>();
        params.put("realmId", realmId);
        if (curUser.getRealm().getRealmId() != -1) {
            sqlString += "AND pc.REALM_ID=:userRealmId ";
            params.put("userRealmId", curUser.getRealm().getRealmId());
        }
        sqlString += " ORDER BY pc.SORT_ORDER";
        System.out.println(sqlString);
        params.put("includeAllChildren", includeAllChildren);
        params.put("includeMainBranch", includeMainBranch);
        params.put("productCategoryId", productCategoryId);
        return this.namedParameterJdbcTemplate.query(sqlString, params, new ProductCategoryRowMapper());
    }

    @Override
    public List<ProductCategory> getProductCategoryList(CustomUserDetails curUser, int productCategoryId, boolean includeMainBranch, boolean includeAllChildren) {
        String sqlString = "SELECT  "
                + "	pc.PRODUCT_CATEGORY_ID, pc.SORT_ORDER, pc.`LEVEL`,  "
                + "    pcl.LABEL_ID, pcl.LABEL_EN, pcl.LABEL_FR, pcl.LABEL_PR, pcl.LABEL_SP, "
                + "    r.REALM_ID, r.REALM_CODE,  "
                + "    rl.LABEL_ID `REALM_LABEL_ID`, rl.LABEL_EN `REALM_LABEL_EN`, rl.LABEL_FR `REALM_LABEL_FR`, rl.LABEL_PR `REALM_LABEL_PR`, rl.LABEL_SP `REALM_LABEL_SP`, "
                + "    cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, pc.ACTIVE, pc.CREATED_DATE, pc.LAST_MODIFIED_DATE "
                + "	FROM rm_product_category pc  "
                + "LEFT JOIN ap_label pcl ON pc.LABEL_ID=pcl.LABEL_ID "
                + "LEFT JOIN rm_realm r ON pc.REALM_ID=r.REALM_ID "
                + "LEFT JOIN ap_label rl ON r.LABEL_ID=rl.LABEL_ID "
                + "LEFT JOIN us_user cb ON pc.CREATED_BY=cb.USER_ID "
                + "LEFT JOIN us_user lmb ON pc.LAST_MODIFIED_BY=lmb.USER_ID "
                + "WHERE  "
                + "	pc.ACTIVE  "
                + "    AND pc.`SORT_ORDER` LIKE (SELECT CONCAT(pc2.`SORT_ORDER`,IF(:includeMainBranch,\"\",\".\"),\"%\") FROM rm_product_category pc2 WHERE pc2.`PRODUCT_CATEGORY_ID`=:productCategoryId) "
                + "    AND (:includeAllChildren OR pc.`LEVEL` = (SELECT pc3.`LEVEL` FROM rm_product_category pc3 WHERE pc3.PRODUCT_CATEGORY_ID=:productCategoryId) OR pc.`LEVEL` = (SELECT pc3.`LEVEL`+1 FROM rm_product_category pc3 WHERE pc3.PRODUCT_CATEGORY_ID=:productCategoryId)) ";
        Map<String, Object> params = new HashMap<>();
        if (curUser.getRealm().getRealmId() != -1) {
            sqlString += "AND pc.REALM_ID=:realmId ";
            params.put("realmId", curUser.getRealm().getRealmId());
        }
        sqlString += " ORDER BY pc.SORT_ORDER";
        params.put("includeAllChildren", includeAllChildren);
        params.put("includeMainBranch", includeMainBranch);
        params.put("productCategoryId", productCategoryId);
        return this.namedParameterJdbcTemplate.query(sqlString, params, new ProductCategoryRowMapper());
    }

}
