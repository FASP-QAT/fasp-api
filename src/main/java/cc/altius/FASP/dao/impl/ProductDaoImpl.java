/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.LabelDao;
import cc.altius.FASP.dao.ProductDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.PrgProductDTO;
import cc.altius.FASP.model.DTO.rowMapper.PrgProductDTORowMapper;
import cc.altius.FASP.model.Product;
import cc.altius.FASP.model.rowMapper.ProductRowMapper;
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

/**
 *
 * @author altius
 */
@Repository
public class ProductDaoImpl implements ProductDao {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Autowired
    private LabelDao labelDao;

    @Override
    public List<PrgProductDTO> getProductListForSync(String lastSyncDate) {
        String sql = "SELECT p.`ACTIVE`,p.`FORECASTING_UNIT_ID`,p.`GENERIC_LABEL_ID`,p.`PRODUCT_CATEGORY_ID`,p.`PRODUCT_ID`, "
                + "l.`LABEL_EN`,l.`LABEL_FR`,l.`LABEL_PR`,l.`LABEL_SP`, "
                + "gl.`LABEL_EN` AS 'GL_LABEL_EN',gl.`LABEL_PR` AS 'GL_LABEL_PR',gl.`LABEL_FR` AS 'GL_LABEL_FR',gl.`LABEL_SP` AS 'GL_LABEL_SP' "
                + "FROM rm_product p "
                + "LEFT JOIN ap_label l ON l.`LABEL_ID`=p.`LABEL_ID` "
                + "LEFT JOIN ap_label gl ON gl.`LABEL_ID`=p.`GENERIC_LABEL_ID`";
        Map<String, Object> params = new HashMap<>();
        if (!lastSyncDate.equals("null")) {
            sql += " WHERE p.`LAST_MODIFIED_DATE`>:lastSyncDate;";
            params.put("lastSyncDate", lastSyncDate);
        }
        return this.namedParameterJdbcTemplate.query(sql, params, new PrgProductDTORowMapper());
    }

    @Override
    public List<Product> getProductList(boolean active, CustomUserDetails curUser) {
        String sqlString = "SELECT p.PRODUCT_ID,  "
                + "	pl.LABEL_ID, pl.LABEL_EN, pl.LABEL_FR, pl.LABEL_PR, pl.LABEL_SP, "
                + "    pgl.LABEL_ID `GENERIC_LABEL_ID`, pgl.LABEL_EN `GENERIC_LABEL_EN`, pgl.LABEL_FR `GENERIC_LABEL_FR`, pgl.LABEL_PR `GENERIC_LABEL_PR`, pgl.LABEL_SP `GENERIC_LABEL_SP`, "
                + "    r.REALM_ID, r.REALM_CODE, rl.LABEL_ID `REALM_LABEL_ID`, rl.LABEL_EN `REALM_LABEL_EN`, rl.LABEL_FR `REALM_LABEL_FR`, rl.LABEL_PR `REALM_LABEL_PR`, rl.LABEL_SP `REALM_LABEL_SP`, "
                + "    u.UNIT_ID, u.UNIT_CODE, ul.LABEL_ID `FORECASTING_UNIT_LABEL_ID`, ul.LABEL_EN `FORECASTING_UNIT_LABEL_EN`, ul.LABEL_FR `FORECASTING_UNIT_LABEL_FR`, ul.LABEL_PR `FORECASTING_UNIT_LABEL_PR`, ul.LABEL_SP `FORECASTING_UNIT_LABEL_SP`, "
                + "    pc.PRODUCT_CATEGORY_ID, pcl.LABEL_ID `PRODUCT_CATEGORY_LABEL_ID`, pcl.LABEL_EN `PRODUCT_CATEGORY_LABEL_EN`, pcl.LABEL_FR `PRODUCT_CATEGORY_LABEL_FR`, pcl.LABEL_PR `PRODUCT_CATEGORY_LABEL_PR`, pcl.LABEL_SP `PRODUCT_CATEGORY_LABEL_SP`, "
                + "    cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, p.ACTIVE, p.CREATED_DATE, p.LAST_MODIFIED_DATE "
                + "FROM rm_product p  "
                + "LEFT JOIN ap_label pl ON p.LABEL_ID=pl.LABEL_ID "
                + "LEFT JOIN ap_label pgl ON p.GENERIC_LABEL_ID=pgl.LABEL_ID "
                + "LEFT JOIN rm_realm r ON p.REALM_ID=r.REALM_ID "
                + "LEFT JOIN ap_label rl ON r.LABEL_ID=rl.LABEL_ID "
                + "LEFT JOIN ap_unit u ON p.FORECASTING_UNIT_ID=u.UNIT_ID "
                + "LEFT JOIN ap_label ul ON u.LABEL_ID=ul.LABEL_ID "
                + "LEFT JOIN rm_product_category pc ON p.PRODUCT_CATEGORY_ID=pc.PRODUCT_CATEGORY_ID "
                + "LEFT JOIN ap_label pcl ON pc.LABEL_ID=pcl.LABEL_ID "
                + "LEFT JOIN us_user cb ON p.CREATED_BY=cb.USER_ID "
                + "LEFT JOIN us_user lmb ON p.LAST_MODIFIED_BY=lmb.USER_ID "
                + "WHERE p.ACTIVE=:active ";

        Map<String, Object> params = new HashMap<>();
        params.put("active", active);
        if (curUser.getRealm().getRealmId() != -1) {
            sqlString += "AND p.REALM_ID=:realmId ";
            params.put("realmId", curUser.getRealm().getRealmId());
        }
        return this.namedParameterJdbcTemplate.query(sqlString, params, new ProductRowMapper());
    }

    @Override
    public List<Product> getProductList(int realmId, boolean active, CustomUserDetails curUser) {
        String sqlString = "SELECT p.PRODUCT_ID,  "
                + "	pl.LABEL_ID, pl.LABEL_EN, pl.LABEL_FR, pl.LABEL_PR, pl.LABEL_SP, "
                + "    pgl.LABEL_ID `GENERIC_LABEL_ID`, pgl.LABEL_EN `GENERIC_LABEL_EN`, pgl.LABEL_FR `GENERIC_LABEL_FR`, pgl.LABEL_PR `GENERIC_LABEL_PR`, pgl.LABEL_SP `GENERIC_LABEL_SP`, "
                + "    r.REALM_ID, r.REALM_CODE, rl.LABEL_ID `REALM_LABEL_ID`, rl.LABEL_EN `REALM_LABEL_EN`, rl.LABEL_FR `REALM_LABEL_FR`, rl.LABEL_PR `REALM_LABEL_PR`, rl.LABEL_SP `REALM_LABEL_SP`, "
                + "    u.UNIT_ID, u.UNIT_CODE, ul.LABEL_ID `FORECASTING_UNIT_LABEL_ID`, ul.LABEL_EN `FORECASTING_UNIT_LABEL_EN`, ul.LABEL_FR `FORECASTING_UNIT_LABEL_FR`, ul.LABEL_PR `FORECASTING_UNIT_LABEL_PR`, ul.LABEL_SP `FORECASTING_UNIT_LABEL_SP`, "
                + "    pc.PRODUCT_CATEGORY_ID, pcl.LABEL_ID `PRODUCT_CATEGORY_LABEL_ID`, pcl.LABEL_EN `PRODUCT_CATEGORY_LABEL_EN`, pcl.LABEL_FR `PRODUCT_CATEGORY_LABEL_FR`, pcl.LABEL_PR `PRODUCT_CATEGORY_LABEL_PR`, pcl.LABEL_SP `PRODUCT_CATEGORY_LABEL_SP`, "
                + "    cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, p.ACTIVE, p.CREATED_DATE, p.LAST_MODIFIED_DATE "
                + "FROM rm_product p  "
                + "LEFT JOIN ap_label pl ON p.LABEL_ID=pl.LABEL_ID "
                + "LEFT JOIN ap_label pgl ON p.GENERIC_LABEL_ID=pgl.LABEL_ID "
                + "LEFT JOIN rm_realm r ON p.REALM_ID=r.REALM_ID "
                + "LEFT JOIN ap_label rl ON r.LABEL_ID=rl.LABEL_ID "
                + "LEFT JOIN ap_unit u ON p.FORECASTING_UNIT_ID=u.UNIT_ID "
                + "LEFT JOIN ap_label ul ON u.LABEL_ID=ul.LABEL_ID "
                + "LEFT JOIN rm_product_category pc ON p.PRODUCT_CATEGORY_ID=pc.PRODUCT_CATEGORY_ID "
                + "LEFT JOIN ap_label pcl ON pc.LABEL_ID=pcl.LABEL_ID "
                + "LEFT JOIN us_user cb ON p.CREATED_BY=cb.USER_ID "
                + "LEFT JOIN us_user lmb ON p.LAST_MODIFIED_BY=lmb.USER_ID "
                + "WHERE p.ACTIVE=:active AND p.REALM_ID=:userRealmId ";

        Map<String, Object> params = new HashMap<>();
        params.put("active", active);
        params.put("userRealmId", realmId);
        if (curUser.getRealm().getRealmId() != -1) {
            sqlString += "AND p.REALM_ID=:realmId ";
            params.put("realmId", curUser.getRealm().getRealmId());
        }
        return this.namedParameterJdbcTemplate.query(sqlString, params, new ProductRowMapper());
    }

    @Override
    public int addProduct(Product product, CustomUserDetails curUser) {
        SimpleJdbcInsert si = new SimpleJdbcInsert(this.dataSource).withTableName("rm_product").usingGeneratedKeyColumns("PRODUCT_ID");
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params = new HashMap<>();
        int labelId = this.labelDao.addLabel(product.getLabel(), curUser.getUserId());
        params.put("LABEL_ID", labelId);
        int genericLabelId = this.labelDao.addLabel(product.getGenericLabel(), curUser.getUserId());
        params.put("GENERIC_LABEL_ID", genericLabelId);
        params.put("FORECASTING_UNIT_ID", product.getForecastingUnit().getUnitId());
        params.put("REALM_ID", product.getRealm().getRealmId());
        params.put("PRODUCT_CATEGORY_ID", product.getProductCategory().getProductCategoryId());
        params.put("ACTIVE", true);
        params.put("CREATED_BY", curUser.getUserId());
        params.put("CREATED_DATE", curDate);
        params.put("LAST_MODIFIED_BY", curUser.getUserId());
        params.put("LAST_MODIFIED_DATE", curDate);
        return si.executeAndReturnKey(params).intValue();
    }

    @Override
    public int updateProduct(Product product, CustomUserDetails curUser) {
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        String sqlString = "UPDATE rm_product p LEFT JOIN ap_label pl ON p.LABEL_ID=pl.LABEL_ID LEFT JOIN ap_label pgl ON p.GENERIC_LABEL_ID=pgl.LABEL_ID "
                + "SET  "
                + "    p.FORECASTING_UNIT_ID=:forecastingUnitId, "
                + "    pc.PRODUCT_CATEGORY_ID=:productCategoryId, "
                + "    p.ACTIVE=:active, "
                + "    p.LAST_MODIFIED_BY=IF(p.FORECASTING_UNIT_ID!=:forecastingUnitId OR pc.PRODUCT_CATEGORY_ID!=:productCategoryId OR p.ACTIVE!=:active,:curUser, p.LAST_MODIFIED_BY), "
                + "    p.LAST_MODIFIED_DATE=IF(p.FORECASTING_UNIT_ID!=:forecastingUnitId OR pc.PRODUCT_CATEGORY_ID!=:productCategoryId OR p.ACTIVE!=:active,:curDate, p.LAST_MODIFIED_DATE), "
                + "    pl.LABEL_EN=:labelEn, "
                + "    pl.LAST_MODIFIED_BY=IF(pl.LABEL_EN=:labelEn,:curUser, p.LAST_MODIFIED_BY), "
                + "    pl.LAST_MODIFIED_DATE=IF(pl.LABEL_EN=:labelEn,:curDate, p.LAST_MODIFIED_DATE), "
                + "    pgl.LABEL_EN=:genericLabelEn, "
                + "    pgl.LAST_MODIFIED_BY=IF(pgl.LABEL_EN=:genericLabelEn,:curUser, p.LAST_MODIFIED_BY), "
                + "    pgl.LAST_MODIFIED_DATE=IF(pgl.LABEL_EN=:genericLabelEn,:curDate, p.LAST_MODIFIED_DATE) "
                + "WHERE p.PRODUCT_ID=:productId";
        Map<String, Object> params = new HashMap<>();
        params.put("forecastingUnitId", product.getForecastingUnit().getUnitId());
        params.put("productId", product.getProductId());
        params.put("productCategoryId", product.getProductCategory().getProductCategoryId());
        params.put("active", product.isActive());
        params.put("labelEn", product.getLabel().getLabel_en());
        params.put("genericLabelEn", product.getGenericLabel().getLabel_en());
        params.put("curUser", curUser.getUserId());
        params.put("curDate", curDate);
        return this.namedParameterJdbcTemplate.update(sqlString, params);
    }

    @Override
    public Product getProductById(int productId, CustomUserDetails curUser) {
        String sqlString = "SELECT p.PRODUCT_ID,  "
                + "	pl.LABEL_ID, pl.LABEL_EN, pl.LABEL_FR, pl.LABEL_PR, pl.LABEL_SP, "
                + "    pgl.LABEL_ID `GENERIC_LABEL_ID`, pgl.LABEL_EN `GENERIC_LABEL_EN`, pgl.LABEL_FR `GENERIC_LABEL_FR`, pgl.LABEL_PR `GENERIC_LABEL_PR`, pgl.LABEL_SP `GENERIC_LABEL_SP`, "
                + "    r.REALM_ID, r.REALM_CODE, rl.LABEL_ID `REALM_LABEL_ID`, rl.LABEL_EN `REALM_LABEL_EN`, rl.LABEL_FR `REALM_LABEL_FR`, rl.LABEL_PR `REALM_LABEL_PR`, rl.LABEL_SP `REALM_LABEL_SP`, "
                + "    u.UNIT_ID, u.UNIT_CODE, ul.LABEL_ID `FORECASTING_UNIT_LABEL_ID`, ul.LABEL_EN `FORECASTING_UNIT_LABEL_EN`, ul.LABEL_FR `FORECASTING_UNIT_LABEL_FR`, ul.LABEL_PR `FORECASTING_UNIT_LABEL_PR`, ul.LABEL_SP `FORECASTING_UNIT_LABEL_SP`, "
                + "    pc.PRODUCT_CATEGORY_ID, pcl.LABEL_ID `PRODUCT_CATEGORY_LABEL_ID`, pcl.LABEL_EN `PRODUCT_CATEGORY_LABEL_EN`, pcl.LABEL_FR `PRODUCT_CATEGORY_LABEL_FR`, pcl.LABEL_PR `PRODUCT_CATEGORY_LABEL_PR`, pcl.LABEL_SP `PRODUCT_CATEGORY_LABEL_SP`, "
                + "    cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, p.ACTIVE, p.CREATED_DATE, p.LAST_MODIFIED_DATE "
                + "FROM rm_product p  "
                + "LEFT JOIN ap_label pl ON p.LABEL_ID=pl.LABEL_ID "
                + "LEFT JOIN ap_label pgl ON p.GENERIC_LABEL_ID=pgl.LABEL_ID "
                + "LEFT JOIN rm_realm r ON p.REALM_ID=r.REALM_ID "
                + "LEFT JOIN ap_label rl ON r.LABEL_ID=rl.LABEL_ID "
                + "LEFT JOIN ap_unit u ON p.FORECASTING_UNIT_ID=u.UNIT_ID "
                + "LEFT JOIN ap_label ul ON u.LABEL_ID=ul.LABEL_ID "
                + "LEFT JOIN rm_product_category pc ON p.PRODUCT_CATEGORY_ID=pc.PRODUCT_CATEGORY_ID "
                + "LEFT JOIN ap_label pcl ON pc.LABEL_ID=pcl.LABEL_ID "
                + "LEFT JOIN us_user cb ON p.CREATED_BY=cb.USER_ID "
                + "LEFT JOIN us_user lmb ON p.LAST_MODIFIED_BY=lmb.USER_ID "
                + "WHERE p.PRODUCT_ID=:productId ";

        Map<String, Object> params = new HashMap<>();
        params.put("productId", productId);
        if (curUser.getRealm().getRealmId() != -1) {
            sqlString += "AND p.REALM_ID=:realmId ";
            params.put("realmId", curUser.getRealm().getRealmId());
        }
        return this.namedParameterJdbcTemplate.queryForObject(sqlString, params, new ProductRowMapper());
    }

}
