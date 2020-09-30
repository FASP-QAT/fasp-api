/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.LabelDao;
import cc.altius.FASP.dao.ProductCategoryDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ExtendedProductCategory;
import cc.altius.FASP.model.LabelConstants;
import cc.altius.FASP.model.ProductCategory;
import cc.altius.FASP.model.rowMapper.ProductCategoryRowMapper;
import cc.altius.FASP.model.rowMapper.TreeExtendedProductCategoryResultSetExtractor;
import cc.altius.FASP.service.AclService;
import cc.altius.FASP.utils.LogUtils;
import cc.altius.utils.DateUtils;
import cc.altius.utils.TreeUtils.Node;
import cc.altius.utils.TreeUtils.Tree;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
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
    @Autowired
    private AclService aclService;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    private final String sqlListStringPart1 = "SELECT  "
            + "    pc.PRODUCT_CATEGORY_ID, pc.SORT_ORDER, pc.PARENT_PRODUCT_CATEGORY_ID, "
            + "    pcl.LABEL_ID, pcl.LABEL_EN, pcl.LABEL_FR, pcl.LABEL_PR, pcl.LABEL_SP, "
            + "    r.REALM_ID, r.REALM_CODE, rl.LABEL_ID `REALM_LABEL_ID`, rl.LABEL_EN `REALM_LABEL_EN`, rl.LABEL_FR `REALM_LABEL_FR`, rl.LABEL_PR `REALM_LABEL_PR`, rl.LABEL_SP `REALM_LABEL_SP`,"
            + "    cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, pc.ACTIVE, pc.CREATED_DATE, pc.LAST_MODIFIED_DATE ";
    private final String sqlListStringPart1WithoutActive = "SELECT  "
            + "    pc.PRODUCT_CATEGORY_ID, pc.SORT_ORDER, pc.PARENT_PRODUCT_CATEGORY_ID, "
            + "    pcl.LABEL_ID, pcl.LABEL_EN, pcl.LABEL_FR, pcl.LABEL_PR, pcl.LABEL_SP, "
            + "    r.REALM_ID, r.REALM_CODE, rl.LABEL_ID `REALM_LABEL_ID`, rl.LABEL_EN `REALM_LABEL_EN`, rl.LABEL_FR `REALM_LABEL_FR`, rl.LABEL_PR `REALM_LABEL_PR`, rl.LABEL_SP `REALM_LABEL_SP`,"
            + "    cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, pc.CREATED_DATE, pc.LAST_MODIFIED_DATE ";
    private final String sqlListStringPart2 = "	FROM rm_product_category pc  "
            + " LEFT JOIN ap_label pcl ON pc.LABEL_ID=pcl.LABEL_ID "
            + " LEFT JOIN rm_realm r ON pc.REALM_ID=r.REALM_ID "
            + " LEFT JOIN ap_label rl ON r.LABEL_ID=rl.LABEL_ID "
            + " LEFT JOIN us_user cb ON pc.CREATED_BY=cb.USER_ID "
            + " LEFT JOIN us_user lmb ON pc.LAST_MODIFIED_BY=lmb.USER_ID ";
    private final String sqlListStringPart3 = " WHERE (TRUE ";
    private final String sqlListString = this.sqlListStringPart1 + this.sqlListStringPart2 + this.sqlListStringPart3;

    @Override
    @Transactional(propagation = Propagation.NESTED)
    public int addProductCategory(Node<ProductCategory> productCategory, CustomUserDetails curUser) {
        SimpleJdbcInsert si = new SimpleJdbcInsert(dataSource).withTableName("rm_product_category").usingGeneratedKeyColumns("PRODUCT_CATEGORY_ID");
        int labelId = this.labelDao.addLabel(productCategory.getPayload().getLabel(), LabelConstants.RM_PRODUCT_CATEGORY, curUser.getUserId());
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params = new HashMap<>();
        params.put("REALM_ID", productCategory.getPayload().getRealm().getId());
        params.put("LABEL_ID", labelId);
        params.put("SORT_ORDER", productCategory.getSortOrder());
        params.put("PARENT_PRODUCT_CATEGORY_ID", productCategory.getPayload().getParentProductCategoryId());
        params.put("CREATED_DATE", curDate);
        params.put("CREATED_BY", curUser.getUserId());
        params.put("LAST_MODIFIED_DATE", curDate);
        params.put("LAST_MODIFIED_BY", curUser.getUserId());
        params.put("ACTIVE", true);
        return si.executeAndReturnKey(params).intValue();
    }

    @Override
    @Transactional
    public int updateProductCategory(Node<ProductCategory> productCategory, CustomUserDetails curUser) {
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        String sqlString = "UPDATE rm_product_category pc "
                + "LEFT JOIN ap_label pcl ON pc.LABEL_ID=pcl.LABEL_ID "
                + "SET "
                + "pc.SORT_ORDER=:sortOrder, "
                + "pc.`PARENT_PRODUCT_CATEGORY_ID`=:parent, "
                + "pc.ACTIVE=:active, "
                + "pc.LAST_MODIFIED_BY=IF(pcl.LABEL_EN!=:labelEn OR pc.`PARENT_PRODUCT_CATEGORY_ID`!=:parent OR pc.SORT_ORDER!=:sortOrder OR pc.ACTIVE!=:active, :curUser, pc.LAST_MODIFIED_BY), "
                + "pc.LAST_MODIFIED_DATE=IF(pcl.LABEL_EN!=:labelEn OR pc.`PARENT_PRODUCT_CATEGORY_ID`!=:parent OR pc.SORT_ORDER!=:sortOrder OR pc.ACTIVE!=:active, :curDate, pc.LAST_MODIFIED_DATE), "
                + "pcl.LABEL_EN=:labelEn, "
                + "pcl.LAST_MODIFIED_BY=IF(pcl.LABEL_EN!=:labelEn, :curUser, pcl.LAST_MODIFIED_BY), "
                + "pcl.LAST_MODIFIED_DATE=IF(pcl.LABEL_EN!=:labelEn, :curDate, pcl.LAST_MODIFIED_DATE) "
                + "WHERE PRODUCT_CATEGORY_ID=:productCategoryId ";
        Map<String, Object> params = new HashMap<>();
        params.put("productCategoryId", productCategory.getPayload().getProductCategoryId());
        params.put("parent", productCategory.getPayload().getParentProductCategoryId());
        params.put("sortOrder", productCategory.getSortOrder());
        params.put("labelEn", productCategory.getPayload().getLabel().getLabel_en());
        params.put("lastModifiedDate", curDate);
        params.put("lastModifiedBy", curUser.getUserId());
        params.put("active", productCategory.getPayload().isActive());
        params.put("curUser", curUser.getUserId());
        params.put("curDate", curDate);
        return this.namedParameterJdbcTemplate.update(sqlString, params);
    }

    @Override
    public List<Node<ExtendedProductCategory>> getProductCategoryListForRealm(CustomUserDetails curUser, int realmId) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString);
        Map<String, Object> params = new HashMap<>();
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "r", realmId, curUser);
        sqlStringBuilder.append(") OR pc.REALM_ID IS NULL ORDER BY pc.SORT_ORDER");
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new TreeExtendedProductCategoryResultSetExtractor()).getTreeFullList();
    }

    @Override
    public List<Node<ExtendedProductCategory>> getProductCategoryList(CustomUserDetails curUser, int realmId, int productCategoryId, boolean includeMainBranch, boolean includeAllChildren) {
        String sqlString = this.sqlListString + " AND pc.PRODUCT_CATEGORY_ID=:productCategoryId )";
        Map<String, Object> params = new HashMap<>();
        params.put("productCategoryId", productCategoryId);
        ProductCategory rootNode = this.namedParameterJdbcTemplate.queryForObject(sqlString, params, new ProductCategoryRowMapper());
        params.put("sortOrder", rootNode.getSortOrder());
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "r", curUser);
        if (!includeAllChildren) {
            sqlStringBuilder.append(" AND pc.`SORT_ORDER` = :sortOrder ");
        }
        sqlStringBuilder.append(" AND pc.`SORT_ORDER` LIKE CONCAT(:sortOrder, '%') ) ORDER BY pc.SORT_ORDER");
        List<Node<ExtendedProductCategory>> pcList = this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new TreeExtendedProductCategoryResultSetExtractor()).getTreeFullList();
        if (!includeMainBranch) {
            pcList.remove(0);
        }
        return pcList;
    }

    @Override
    public ProductCategory getProductCategoryById(int productCategoryId, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString).append(" AND pc.PRODUCT_CATEGORY_ID=:productCategoryId )");
        Map<String, Object> params = new HashMap<>();
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "pc", curUser);
        params.put("productCategoryId", productCategoryId);
        return this.namedParameterJdbcTemplate.queryForObject(sqlStringBuilder.toString(), params, new ProductCategoryRowMapper());
    }

    @Override
    public List<Node<ExtendedProductCategory>> getProductCategoryListForSync(String lastSyncDate, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString).append(" AND pc.LAST_MODIFIED_DATE>:lastSyncDate ");
        Map<String, Object> params = new HashMap<>();
        params.put("lastSyncDate", lastSyncDate);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "pc", curUser);
        sqlStringBuilder.append(" ) OR pc.REALM_ID IS NULL ORDER BY pc.SORT_ORDER");
        Tree<ExtendedProductCategory> t = this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new TreeExtendedProductCategoryResultSetExtractor());
        if (t == null) {
            return new LinkedList<Node<ExtendedProductCategory>>();
        } else {
            return t.getTreeFullList();
        }
    }

    @Override
    public List<Node<ExtendedProductCategory>> getProductCategoryListForProgram(CustomUserDetails curUser, int realmId, int programId) {
        Map<String, Object> params = new HashMap<>();
        params.put("programId", programId);
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListStringPart1)
//                .append(", IF(pc.ACTIVE && pcf.PRODUCT_CATEGORY_ID IS NOT NULL, TRUE, FALSE) `ACTIVE` ")
                .append(sqlListStringPart2)
                .append(sqlListStringPart3);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "r", curUser);
        sqlStringBuilder.append(" AND (length(pc.SORT_ORDER)<5 OR LEFT(pc.`SORT_ORDER`,5) IN (SELECT LEFT(pc.SORT_ORDER,5) SO FROM rm_program_planning_unit ppu LEFT JOIN rm_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID LEFT JOIN rm_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID LEFT JOIN rm_product_category pc ON fu.PRODUCT_CATEGORY_ID=pc.PRODUCT_CATEGORY_ID WHERE ppu.PROGRAM_ID=:programId GROUP BY LEFT(pc.SORT_ORDER,5)))) OR pc.REALM_ID IS NULL ORDER BY pc.SORT_ORDER");
        List<Node<ExtendedProductCategory>> pcList = this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new TreeExtendedProductCategoryResultSetExtractor()).getTreeFullList();
//        pcList.remove(0);
        return pcList;
    }

}
