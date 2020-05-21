/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.ImportProductCatalogueDao;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author altius
 */
@Repository
public class ImportProductCatalogueDaoImpl implements ImportProductCatalogueDao {

    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    private static org.slf4j.Logger LOG = LoggerFactory.getLogger(ImportProductCatalogueDaoImpl.class);

    @Override
    public void importProductCatalogue(String filePath) {
        try {
            File fXmlFile = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

            NodeList nList1 = doc.getElementsByTagName("DATA_RECORD");
            MapSqlParameterSource[] batchParams = new MapSqlParameterSource[nList1.getLength()];
            Map<String, Object> map = new HashedMap<String, Object>();
            int x = 0;
            String sql;
            
            sql = "TRUNCATE TABLE `fasp`.`tmp_product_catalog`";
            this.jdbcTemplate.update(sql);
            
            sql = "INSERT INTO tmp_product_catalog VALUES(:taskOrderLongDescription,:commodityCouncilLongDesc,"
                    + ":commoditySubcatLongDesc,:productTracerCat,"
                    + ":productBuyable,:productIdNoPack,:productNameNoPack,"
                    + ":productId,:productName,:itemUom,"
                    + ":productPackSize,:productBaseUnitMult,:productBaseUnit,"
                    + ":productDataTrusteeProductIdentifier,:productUnspsc,"
                    + ":productInternationalNonproprietaryName,:productControlledItemWho,"
                    + ":productAdministrationRoute,:productDosageForm,:productQaEligibilityCategory,"
                    + ":productQaEligibilityCriteria,:productDrug1Name,:productDrug1Abbr,:productDrug1Strength,:productDrug1Measure,:productDrug1Unit,"
                    + ":productDrug2Name,:productDrug2Abbr,:productDrug2Strength,:productDrug2Measure,:productDrug2Unit,"
                    + ":productDrug3Name,:productDrug3Abbr,:productDrug3Strength,:productDrug3Measure,:productDrug3Unit,"
                    + ":productDrug4Name,:productDrug4Abbr,:productDrug4Strength,:productDrug4Measure,:productDrug4Unit,"
                    + ":usaidArvTier,:productBuyable1,:planningUnitMoq,:planningUnitPallet,:planningUnitsPerContainer,"
                    + ":planningUnitVolumeM3,:planningUnitWeightKg,:itemId,:itemName,:itemSupplierName,:itemWeightUom,"
                    + ":itemWeight,:itemSizeMeasureH,:itemHeight,:itemSizeMeasureL,:itemLength,:itemSizeMeasureW,:itemWidth,"
                    + ":itemManufacturerGtinUpc,:itemLabelLanguages,:itemBuyable,:itemUnitsPerCase,:itemNumOfUnitsPallet,"
                    + ":itemNumOfPalletsContainer,:unitsPerContainer,:wcsCataloguePrice);";
            for (int temp2 = 0; temp2 < nList1.getLength(); temp2++) {
                Node nNode1 = nList1.item(temp2);
                if (nNode1.getNodeType() == Node.ELEMENT_NODE) {
                    Element dataRecordElement = (Element) nNode1;
                    map.put("taskOrderLongDescription", dataRecordElement.getElementsByTagName("TASK_ORDER_LONG_DESCRIPTION").item(0).getTextContent());
                    map.put("commodityCouncilLongDesc", dataRecordElement.getElementsByTagName("COMMODITY_COUNCIL_LONG_DESC").item(0).getTextContent());
                    map.put("commoditySubcatLongDesc", dataRecordElement.getElementsByTagName("COMMODITY_SUBCAT_LONG_DESC").item(0).getTextContent());
                    map.put("productTracerCat", dataRecordElement.getElementsByTagName("PRODUCT_TRACER_CAT").item(0).getTextContent());
                    map.put("productBuyable", dataRecordElement.getElementsByTagName("PRODUCT_BUYABLE").item(0).getTextContent());
                    map.put("productIdNoPack", dataRecordElement.getElementsByTagName("PRODUCT_ID_NO_PACK").item(0).getTextContent());
                    map.put("productNameNoPack", dataRecordElement.getElementsByTagName("PRODUCT_NAME_NO_PACK").item(0).getTextContent());
                    map.put("productId", dataRecordElement.getElementsByTagName("PRODUCT_ID").item(0).getTextContent());
                    map.put("productName", dataRecordElement.getElementsByTagName("PRODUCT_NAME").item(0).getTextContent());
                    map.put("itemUom", dataRecordElement.getElementsByTagName("ITEM_UOM").item(0).getTextContent());
                    map.put("productPackSize", dataRecordElement.getElementsByTagName("PRODUCT_PACK_SIZE").item(0).getTextContent());
                    map.put("productBaseUnitMult", dataRecordElement.getElementsByTagName("PRODUCT_BASE_UNIT_MULT").item(0).getTextContent());
                    map.put("productBaseUnit", dataRecordElement.getElementsByTagName("PRODUCT_BASE_UNIT").item(0).getTextContent());
                    map.put("productDataTrusteeProductIdentifier", dataRecordElement.getElementsByTagName("PRODUCT_DATA_TRUSTEE_PRODUCT_IDENTIFIER").item(0).getTextContent());
                    map.put("productUnspsc", dataRecordElement.getElementsByTagName("PRODUCT_UNSPSC").item(0).getTextContent());
                    map.put("productInternationalNonproprietaryName", dataRecordElement.getElementsByTagName("PRODUCT_INTERNATIONAL_NONPROPRIETARY_NAME").item(0).getTextContent());
                    map.put("productControlledItemWho", dataRecordElement.getElementsByTagName("PRODUCT_CONTROLLED_ITEM_WHO").item(0).getTextContent());
                    map.put("productAdministrationRoute", dataRecordElement.getElementsByTagName("PRODUCT_ADMINISTRATION_ROUTE").item(0).getTextContent());
                    map.put("productDosageForm", dataRecordElement.getElementsByTagName("PRODUCT_DOSAGE_FORM").item(0).getTextContent());
                    map.put("productQaEligibilityCategory", dataRecordElement.getElementsByTagName("PRODUCT_QA_ELIGIBILITY_CATEGORY").item(0).getTextContent());
                    map.put("productQaEligibilityCriteria", dataRecordElement.getElementsByTagName("PRODUCT_QA_ELIGIBILITY_CRITERIA").item(0).getTextContent());
                    map.put("productDrug1Name", dataRecordElement.getElementsByTagName("PRODUCT_DRUG_1_NAME").item(0).getTextContent());
                    map.put("productDrug1Abbr", dataRecordElement.getElementsByTagName("PRODUCT_DRUG_1_ABBREVIATION").item(0).getTextContent());
                    map.put("productDrug1Strength", dataRecordElement.getElementsByTagName("PRODUCT_DRUG_1_STRENGTH").item(0).getTextContent());
                    map.put("productDrug1Measure", dataRecordElement.getElementsByTagName("PRODUCT_DRUG_1_MEASURE").item(0).getTextContent());
                    map.put("productDrug1Unit", dataRecordElement.getElementsByTagName("PRODUCT_DRUG_1_UNIT").item(0).getTextContent());

                    map.put("productDrug2Name", dataRecordElement.getElementsByTagName("PRODUCT_DRUG_2_NAME").item(0).getTextContent());
                    map.put("productDrug2Abbr", dataRecordElement.getElementsByTagName("PRODUCT_DRUG_2_ABBREVIATION").item(0).getTextContent());
                    map.put("productDrug2Strength", dataRecordElement.getElementsByTagName("PRODUCT_DRUG_2_STRENGTH").item(0).getTextContent());
                    map.put("productDrug2Measure", dataRecordElement.getElementsByTagName("PRODUCT_DRUG_2_MEASURE").item(0).getTextContent());
                    map.put("productDrug2Unit", dataRecordElement.getElementsByTagName("PRODUCT_DRUG_2_UNIT").item(0).getTextContent());

                    map.put("productDrug3Name", dataRecordElement.getElementsByTagName("PRODUCT_DRUG_3_NAME").item(0).getTextContent());
                    map.put("productDrug3Abbr", dataRecordElement.getElementsByTagName("PRODUCT_DRUG_3_ABBREVIATION").item(0).getTextContent());
                    map.put("productDrug3Strength", dataRecordElement.getElementsByTagName("PRODUCT_DRUG_3_STRENGTH").item(0).getTextContent());
                    map.put("productDrug3Measure", dataRecordElement.getElementsByTagName("PRODUCT_DRUG_3_MEASURE").item(0).getTextContent());
                    map.put("productDrug3Unit", dataRecordElement.getElementsByTagName("PRODUCT_DRUG_3_UNIT").item(0).getTextContent());

                    map.put("productDrug4Name", dataRecordElement.getElementsByTagName("PRODUCT_DRUG_4_NAME").item(0).getTextContent());
                    map.put("productDrug4Abbr", dataRecordElement.getElementsByTagName("PRODUCT_DRUG_4_ABBREVIATION").item(0).getTextContent());
                    map.put("productDrug4Strength", dataRecordElement.getElementsByTagName("PRODUCT_DRUG_4_STRENGTH").item(0).getTextContent());
                    map.put("productDrug4Measure", dataRecordElement.getElementsByTagName("PRODUCT_DRUG_4_MEASURE").item(0).getTextContent());
                    map.put("productDrug4Unit", dataRecordElement.getElementsByTagName("PRODUCT_DRUG_4_UNIT").item(0).getTextContent());

                    map.put("usaidArvTier", dataRecordElement.getElementsByTagName("USAID_ARV_TIER").item(0).getTextContent());
                    // below
                    map.put("productBuyable1", dataRecordElement.getElementsByTagName("PRODUCT_BUYABLE").item(1).getTextContent());
                    map.put("planningUnitMoq", dataRecordElement.getElementsByTagName("PLANNING_UNIT_MOQ").item(0).getTextContent());
                    map.put("planningUnitPallet", dataRecordElement.getElementsByTagName("PLANNING_UNIT_PALLET").item(0).getTextContent());
                    map.put("planningUnitsPerContainer", dataRecordElement.getElementsByTagName("PLANNING_UNITS_PER_CONTAINER").item(0).getTextContent());
                    map.put("planningUnitVolumeM3", dataRecordElement.getElementsByTagName("PLANNING_UNIT_VOLUME_M3").item(0).getTextContent());
                    map.put("planningUnitWeightKg", dataRecordElement.getElementsByTagName("PLANNING_UNIT_WEIGHT_KG").item(0).getTextContent());
                    map.put("itemId", dataRecordElement.getElementsByTagName("ITEM_ID").item(0).getTextContent());
                    map.put("itemName", dataRecordElement.getElementsByTagName("ITEM_NAME").item(0).getTextContent());
                    map.put("itemSupplierName", dataRecordElement.getElementsByTagName("ITEM_SUPPLIER_NAME").item(0).getTextContent());
                    map.put("itemWeightUom", dataRecordElement.getElementsByTagName("ITEM_WEIGHT_UOM").item(0).getTextContent());
                    map.put("itemWeight", dataRecordElement.getElementsByTagName("ITEM_WEIGHT").item(0).getTextContent());
                    map.put("itemSizeMeasureH", dataRecordElement.getElementsByTagName("ITEM_SIZEMEASURE").item(0).getTextContent());
                    map.put("itemHeight", dataRecordElement.getElementsByTagName("ITEM_HEIGHT").item(0).getTextContent());
                    map.put("itemSizeMeasureL", dataRecordElement.getElementsByTagName("ITEM_SIZEMEASURE").item(1).getTextContent());
                    map.put("itemLength", dataRecordElement.getElementsByTagName("ITEM_LENGTH").item(0).getTextContent());
                    map.put("itemSizeMeasureW", dataRecordElement.getElementsByTagName("ITEM_SIZEMEASURE").item(2).getTextContent());
                    map.put("itemWidth", dataRecordElement.getElementsByTagName("ITEM_WIDTH").item(0).getTextContent());
                    map.put("itemManufacturerGtinUpc", dataRecordElement.getElementsByTagName("ITEM_MANUFACTURER_GTIN_UPC").item(0).getTextContent());
                    map.put("itemLabelLanguages", dataRecordElement.getElementsByTagName("ITEM_LABEL_LANGUAGES").item(0).getTextContent());
                    map.put("itemBuyable", dataRecordElement.getElementsByTagName("ITEM_BUYABLE").item(0).getTextContent());
                    map.put("itemUnitsPerCase", dataRecordElement.getElementsByTagName("ITEM_UNITS_PER_CASE").item(0).getTextContent());
                    map.put("itemNumOfUnitsPallet", dataRecordElement.getElementsByTagName("ITEM_NUM_OF_UNITS_PALLET").item(0).getTextContent());
                    map.put("itemNumOfPalletsContainer", dataRecordElement.getElementsByTagName("ITEM_NUM_OF_PALLETS_CONTAINER").item(0).getTextContent());
                    map.put("unitsPerContainer", dataRecordElement.getElementsByTagName("UNITS_PER_CONTAINER").item(0).getTextContent());
                    map.put("wcsCataloguePrice", dataRecordElement.getElementsByTagName("WCS_CATALOG_PRICE").item(0).getTextContent());
                    batchParams[x] = new MapSqlParameterSource(map);
                    x++;
                }
            }
            namedParameterJdbcTemplate.batchUpdate(sql, batchParams);

        } catch (ParserConfigurationException ex) {
            LOG.error("Error---" + ex);
        } catch (SAXException ex) {
            LOG.error("Error---" + ex);
        } catch (IOException ex) {
            LOG.error("Error---" + ex);
        }
    }

    @Override
    public void pullUnitTable() {
        System.out.println("---1---");
        String sql = "DROP TABLE IF EXISTS `tmp_unit`";
        this.jdbcTemplate.execute(sql);
        System.out.println("---2---");
        sql = "CREATE TABLE `tmp_unit` ( "
                + " `ID` int(10) unsigned NOT NULL AUTO_INCREMENT, "
                + " `LABEL` varchar(200) COLLATE utf8_bin NOT NULL, "
                + " `UNIT_ID` int (10) unsigned DEFAULT NULL, "
                + " `LABEL_ID` int (10) unsigned DEFAULT NULL, "
                + " `DIMENSION_ID` int (10) unsigned DEFAULT NULL, "
                + " PRIMARY KEY (`ID`) "
                + " ) ENGINE = InnoDB DEFAULT CHARSET = utf8 COLLATE = utf8_bin";
        this.jdbcTemplate.execute(sql);
        System.out.println("---3---");

        sql = "ALTER TABLE `fasp`.`tmp_unit` ADD UNIQUE INDEX `index2` (`LABEL` ASC)";
        this.jdbcTemplate.execute(sql);
        System.out.println("---4---");

        sql = "INSERT INTO tmp_unit SELECT NULL, BaseUnit, NULL, NULL, NULL FROM tmp_product_catalog GROUP BY BaseUnit";
        this.jdbcTemplate.update(sql);
        System.out.println("---5---");
        sql = "INSERT IGNORE INTO tmp_unit SELECT NULL, OrderUOM, NULL, NULL, NULL FROM tmp_product_catalog GROUP BY OrderUOM";
        this.jdbcTemplate.update(sql);
        System.out.println("---6---");
        sql = "INSERT IGNORE INTO tmp_unit SELECT NULL, WeightUOM, NULL, NULL, NULL FROM tmp_product_catalog GROUP BY WeightUOM";
        this.jdbcTemplate.update(sql);
        System.out.println("---7---");
        sql = "INSERT IGNORE INTO tmp_unit SELECT NULL, HeightUOM, NULL, NULL, NULL FROM tmp_product_catalog GROUP BY HeightUOM";
        this.jdbcTemplate.update(sql);
        System.out.println("---8---");
        sql = "INSERT IGNORE INTO tmp_unit SELECT NULL, LengthUOM, NULL, NULL, NULL FROM tmp_product_catalog GROUP BY LengthUOM";
        this.jdbcTemplate.update(sql);
        System.out.println("---9---");
        sql = "INSERT IGNORE INTO tmp_unit SELECT NULL, WidthUOM, NULL, NULL, NULL FROM tmp_product_catalog GROUP BY WidthUOM";
        this.jdbcTemplate.update(sql);
        System.out.println("---10---");

        sql = "UPDATE ap_unit au LEFT JOIN ap_label al ON au.LABEL_ID = al.LABEL_ID LEFT JOIN tmp_unit tu ON al.LABEL_EN = tu.LABEL "
                + "SET tu.UNIT_ID = au.UNIT_ID WHERE tu.ID IS NOT NULL";
        this.jdbcTemplate.execute(sql);
        System.out.println("---11---");
        sql = "SELECT * FROM tmp_unit tu where tu.UNIT_ID IS NULL";
        List<Map<String, Object>> result = this.jdbcTemplate.queryForList(sql);
        Scanner s = new Scanner(System.in);
        System.out.println("---sssssss---" + s);
        boolean validData = false;
        for (Map<String, Object> u : result) {
            validData = false;
            do {
                System.out.println("---------------------------------------------------------------------------");
                System.out.println("Please select the Dimension for this Unit. (Only enter the number)");
                System.out.println("Possible Dimension values are Volume(1), Weight(2), Each(3), Distance(4)");
                System.out.println("Unit: " + u.get("LABEL"));
                String input = s.nextLine();
                System.out.println("---input---" + input);
                if (input.equals("1") || input.equals("2") || input.equals("3") || input.equals("4")) {
                    validData = true;
                    this.jdbcTemplate.update("UPDATE tmp_unit SET DIMENSION_ID=? WHERE ID=?", input, u.get("ID"));
                    System.out.println("---11---");
                }
            } while (!validData);
        }

        sql = "SELECT MAX(LABEL_ID) FROM ap_label";
        int max = this.jdbcTemplate.queryForObject(sql, Integer.class);
        System.out.println("---12---");
        sql = "INSERT INTO ap_label SELECT NULL, tu.LABEL, NULL, NULL, NULL, 1, NOW(), 1, NOW() FROM tmp_unit tu WHERE tu.UNIT_ID IS NULL";
        this.jdbcTemplate.update(sql);
        System.out.println("---13---");
        sql = "UPDATE tmp_unit tu LEFT JOIN ap_label l ON tu.LABEL=l.LABEL_EN AND l.LABEL_ID>" + max + " SET tu.LABEL_ID=l.LABEL_ID WHERE tu.UNIT_ID IS NULL";
        this.jdbcTemplate.update(sql);
        System.out.println("---14---");
        sql = "INSERT INTO ap_unit SELECT  null, tu.DIMENSION_ID, tu.LABEL_ID, tu.LABEL, true, 1, now(), 1, now() from tmp_unit tu where tu.UNIT_ID is null";
        this.jdbcTemplate.update(sql);
        System.out.println("---15---");
    }

    @Override
    public void pullTracerCategoryFromTmpTables() {
        int max;

        // Step 1 - Drop the table if it exists
        String sqlString = "DROP TABLE IF EXISTS `tmp_tracer_category`";
        this.jdbcTemplate.update(sqlString);

        // Step 2 - Create the tmp table
        sqlString = "CREATE TABLE `tmp_tracer_category` ( "
                + "  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT, "
                + "  `LABEL` varchar(200) COLLATE utf8_bin NOT NULL, "
                + "  `TRACER_CATEGORY_ID` int(10) unsigned DEFAULT NULL, "
                + "  `LABEL_ID` int (10) unsigned DEFAULT NULL, "
                + "  PRIMARY KEY (`ID`) "
                + ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin";
        this.jdbcTemplate.update(sqlString);

        // Step 3 insert into the tmp_tracer all the data that you can get from 
        //        product_catalog that you just imported
        sqlString = "INSERT INTO tmp_tracer_category "
                + "SELECT NULL, TracerCategory, NULL, NULL "
                + "FROM tmp_product_catalog GROUP BY TracerCategory";
        int rows = this.jdbcTemplate.update(sqlString);
        System.out.println(rows + " inserted into the tmp_tracer_category table");

        // Step 4 Match those records that are already present in the main tracer_category table
        sqlString = "UPDATE rm_tracer_category tc "
                + "LEFT JOIN ap_label tcl ON tc.LABEL_ID=tcl.LABEL_ID "
                + "LEFT JOIN tmp_tracer_category ttc ON tcl.LABEL_EN=ttc.LABEL "
                + "SET ttc.TRACER_CATEGORY_ID=tc.TRACER_CATEGORY_ID "
                + "WHERE ttc.ID IS NOT NULL";

        rows = this.jdbcTemplate.update(sqlString);
        System.out.println(rows + " existing labels found");

        // Step 5 Get the max count on the label table so that you can now work on data that you insert from here on
        sqlString = "SELECT MAX(LABEL_ID) m FROM ap_label";

        max = this.jdbcTemplate.queryForObject(sqlString, Integer.class
        );
        System.out.println(max + " is the current Max count for ap_label");

        // Step 6 Insert the rows that you do not have in tracer already into the label table
        sqlString = "INSERT INTO ap_label SELECT NULL, ttc.LABEL, NULL, NULL, NULL, 1, NOW(), 1, NOW() "
                + "FROM tmp_tracer_category ttc "
                + "WHERE ttc.TRACER_CATEGORY_ID IS NULL";

        rows = this.jdbcTemplate.update(sqlString);
        System.out.println(rows + " rows inserted into the ap_label table");

        // Step 7 Now find the LABEL_ID for the rows that you just inserted and update your tmp table with those
        sqlString = "UPDATE tmp_tracer_category ttc "
                + "LEFT JOIN ap_label l ON ttc.LABEL=l.LABEL_EN "
                + "AND l.LABEL_ID>? "
                + "SET ttc.LABEL_ID=l.LABEL_ID "
                + "WHERE ttc.TRACER_CATEGORY_ID IS NULL";

        rows = this.jdbcTemplate.update(sqlString, max);
        System.out.println(rows + " rows matched and LABEL_ID updated with new LABEL_ID");

        // Step 8 Finally take the Labels and 
        sqlString = "INSERT INTO rm_tracer_category "
                + "SELECT NULL, 1, ttc.LABEL_ID, 1, 1, NOW(), 1, NOW() "
                + "FROM tmp_tracer_category ttc "
                + "WHERE ttc.TRACER_CATEGORY_ID IS NULL "
                + "AND ttc.LABEL_ID IS NOT NULL";

        rows = this.jdbcTemplate.update(sqlString);
        System.out.println(rows + " new rows inserted into the rm_tracer_category table");
    }

    @Override
    public void pullForecastingUnitFromTmpTables() {
        int max;

        // Step 1 - Drop the table if it exists
        String sqlString = "DROP TABLE IF EXISTS `tmp_label`";
        this.jdbcTemplate.update(sqlString);
        sqlString = "DROP TABLE IF EXISTS `tmp_forecasting_unit`";
        this.jdbcTemplate.update(sqlString);

        // Step 2 - Create the tmp table
        sqlString = "CREATE TABLE `tmp_label` ( \n"
                + "	`ID` int(10) unsigned NOT NULL AUTO_INCREMENT, \n"
                + "    `LABEL` varchar(200) COLLATE utf8_bin NOT NULL, \n"
                + "    `LABEL_ID` int (10) unsigned DEFAULT NULL, \n"
                + "    `FOUND` tinyint(1) unsigned DEFAULT 0 NOT NULL,\n"
                + "    PRIMARY KEY (`ID`) \n"
                + ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin";
        this.jdbcTemplate.update(sqlString);

        sqlString = "ALTER TABLE `fasp`.`tmp_label` \n"
                + "ADD INDEX `tmpLabel_label` (`LABEL` ASC),\n"
                + "ADD INDEX `fk_tmpLabel_labelIdIdx` (`LABEL_ID` ASC),\n"
                + "ADD CONSTRAINT `fk_tmpLabel_labelId`\n"
                + "  FOREIGN KEY (`LABEL_ID`)\n"
                + "  REFERENCES `fasp`.`ap_label` (`LABEL_ID`)\n"
                + "  ON DELETE NO ACTION\n"
                + "  ON UPDATE NO ACTION;";
        this.jdbcTemplate.update(sqlString);

        sqlString = "CREATE TABLE `tmp_forecasting_unit` ( \n"
                + "	`ID` int(10) unsigned NOT NULL AUTO_INCREMENT, \n"
                + "    `LABEL` varchar(200) COLLATE utf8_bin NOT NULL, \n"
                + "    `LABEL_ID` int (10) unsigned DEFAULT NULL, \n"
                + "    `GENERIC_LABEL`varchar(200) COLLATE utf8_bin NULL, \n"
                + "    `GENERIC_LABEL_ID` int (10) unsigned DEFAULT NULL, \n"
                + "	`UNIT_ID` int (10) unsigned DEFAULT NULL,\n"
                + "    `PRODUCT_CATEGORY_ID` int (10) unsigned default null,\n"
                + "    `TRACER_CATEGORY_ID` int (10) unsigned default null,\n"
                + "    PRIMARY KEY (`ID`) \n"
                + ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin";
        this.jdbcTemplate.update(sqlString);

        sqlString = "ALTER TABLE `fasp`.`tmp_forecasting_unit` \n"
                + "ADD INDEX `idxLabel` (`LABEL` ASC),\n"
                + "ADD INDEX `idxGenericLabel` (`GENERIC_LABEL` ASC),\n"
                + "ADD INDEX `fk_labelId_idx` (`LABEL_ID` ASC),\n"
                + "ADD INDEX `fk_unitId_idx` (`UNIT_ID` ASC),\n"
                + "ADD INDEX `fk_genericLabelId_idx` (`GENERIC_LABEL_ID` ASC),\n"
                + "ADD INDEX `fk_productCategoryId_idx` (`PRODUCT_CATEGORY_ID` ASC),\n"
                + "ADD INDEX `fk_tracerCategoryId_idx` (`TRACER_CATEGORY_ID` ASC)";
        this.jdbcTemplate.update(sqlString);
        sqlString = "ALTER TABLE `fasp`.`tmp_forecasting_unit` \n"
                + "ADD CONSTRAINT `fk_labelId`\n"
                + "  FOREIGN KEY (`LABEL_ID`)\n"
                + "  REFERENCES `fasp`.`ap_label` (`LABEL_ID`)\n"
                + "  ON DELETE NO ACTION\n"
                + "  ON UPDATE NO ACTION,\n"
                + "ADD CONSTRAINT `fk_unitId`\n"
                + "  FOREIGN KEY (`UNIT_ID`)\n"
                + "  REFERENCES `fasp`.`ap_unit` (`UNIT_ID`)\n"
                + "  ON DELETE NO ACTION\n"
                + "  ON UPDATE NO ACTION,\n"
                + "ADD CONSTRAINT `fk_genericLabelId`\n"
                + "  FOREIGN KEY (`GENERIC_LABEL_ID`)\n"
                + "  REFERENCES `fasp`.`ap_label` (`LABEL_ID`)\n"
                + "  ON DELETE NO ACTION\n"
                + "  ON UPDATE NO ACTION,\n"
                + "ADD CONSTRAINT `fk_productCategoryId`\n"
                + "  FOREIGN KEY (`PRODUCT_CATEGORY_ID`)\n"
                + "  REFERENCES `fasp`.`rm_product_category` (`PRODUCT_CATEGORY_ID`)\n"
                + "  ON DELETE NO ACTION\n"
                + "  ON UPDATE NO ACTION,\n"
                + "ADD CONSTRAINT `fk_tracerCategoryId`\n"
                + "  FOREIGN KEY (`TRACER_CATEGORY_ID`)\n"
                + "  REFERENCES `fasp`.`rm_tracer_category` (`TRACER_CATEGORY_ID`)\n"
                + "  ON DELETE NO ACTION\n"
                + "  ON UPDATE NO ACTION";
        this.jdbcTemplate.update(sqlString);

        // Step 3 insert into the tmp_label the ProductNameNoPack
        sqlString = "insert into tmp_label SELECT null, ProductNameNoPack, null, 0 FROM tmp_product_catalog tpc group by tpc.ProductNameNoPack";
        int rows = this.jdbcTemplate.update(sqlString);
        System.out.println(rows + " inserted into the tmp_label for ProductNameNoPack");

        // Step 4 Match those records that are already present in the main forecasting_unit table
        sqlString = "update tmp_label tl LEFT JOIN (SELECT ful.LABEL_ID, ful.LABEL_EN FROM rm_forecasting_unit fu LEFT JOIN ap_label ful on fu.LABEL_ID=ful.LABEL_ID) as fuld ON tl.LABEL=fuld.LABEL_EN \n"
                + "set tl.LABEL_ID = fuld.LABEL_ID, tl.FOUND=1\n"
                + "where fuld.LABEL_ID is not null";
        rows = this.jdbcTemplate.update(sqlString);
        System.out.println(rows + " existing labels found");

        // Step 5 Get the max count on the label table so that you can now work on data that you insert from here on
        sqlString = "SELECT MAX(LABEL_ID) m FROM ap_label";
        max = this.jdbcTemplate.queryForObject(sqlString, Integer.class);
        System.out.println(max + " is the current Max count for ap_label");

        // Step 6 Insert the rows that you do not have in label table
        sqlString = "INSERT INTO ap_label select null, tl.LABEL, null, null, null, 1, now(), 1, now() from tmp_label tl where tl.FOUND=false";
        rows = this.jdbcTemplate.update(sqlString);
        System.out.println(rows + " rows inserted into the ap_label table");

        // Step 7 Now find the LABEL_ID for the rows that you just inserted and update your tmp table with those
        sqlString = "update tmp_label tl LEFT JOIN ap_label ful on ful.LABEL_EN=tl.LABEL and ful.LABEL_ID>? set tl.LABEL_ID = ful.LABEL_ID where ful.LABEL_ID is not null";
        rows = this.jdbcTemplate.update(sqlString, max);
        System.out.println(rows + " rows matched and LABEL_ID updated with new LABEL_ID");

        // Step 8 - Insert into tmp_froecasting those rows that have not been found 
        sqlString = "INSERT INTO tmp_forecasting_unit SELECT null, tl.LABEL, tl.LABEL_ID, null, null, null, null, null  from tmp_label tl where tl.FOUND=0";
        rows = this.jdbcTemplate.update(sqlString);
        System.out.println(rows + " rows inserted into tmp_forecasting_unit");

        // Step 9 - Get the ProductCategory, TracerCategory, Unit and Generic matching ID's
        sqlString = "update tmp_forecasting_unit tfu \n"
                + "LEFT JOIN tmp_product_catalog tpc ON tfu.LABEL=tpc.ProductNameNoPack \n"
                + "set \n"
                + "tfu.GENERIC_LABEL=tpc.INN";
        rows = this.jdbcTemplate.update(sqlString);
        System.out.println(rows + " matching Generic labels updated");

        sqlString = "update tmp_forecasting_unit tfu LEFT JOIN tmp_product_catalog tpc ON tfu.LABEL=tpc.ProductNameNoPack  \n"
                + "LEFT JOIN (SELECT u.*, ul.LABEL_EN FROM ap_unit u LEFT JOIN ap_label ul ON u.LABEL_ID=ul.LABEL_ID) tud ON tpc.BaseUnit=tud.LABEL_EN "
                + "set \n"
                + "tfu.UNIT_ID=tud.UNIT_ID";
        rows = this.jdbcTemplate.update(sqlString);
        System.out.println(rows + " matching Unit Id's updated");

        sqlString = "update tmp_forecasting_unit tfu LEFT JOIN tmp_product_catalog tpc ON tfu.LABEL=tpc.ProductNameNoPack \n"
                + "LEFT JOIN (SELECT pc.PRODUCT_CATEGORY_ID, pcl.LABEL_EN FROM rm_product_category pc LEFT JOIN ap_label pcl ON pc.LABEL_ID=pcl.LABEL_ID) tpcd ON COALESCE(tpc.Subcategory, tpc.CommodityCouncil)=tpcd.LABEL_EN "
                + "set \n"
                + "tfu.PRODUCT_CATEGORY_ID=tpcd.PRODUCT_CATEGORY_ID";
        rows = this.jdbcTemplate.update(sqlString);
        System.out.println(rows + " matching Product Categories updated");

        sqlString = "update tmp_forecasting_unit tfu LEFT JOIN tmp_product_catalog tpc ON tfu.LABEL=tpc.ProductNameNoPack \n"
                + "LEFT JOIN (SELECT tc.TRACER_CATEGORY_ID, tcl.LABEL_EN FROM rm_tracer_category tc LEFT JOIN ap_label tcl ON tc.LABEL_ID=tcl.LABEL_ID) ttcd ON tpc.TracerCategory=ttcd.LABEL_EN "
                + "set \n"
                + "tfu.TRACER_CATEGORY_ID=ttcd.TRACER_CATEGORY_ID";
        rows = this.jdbcTemplate.update(sqlString);
        System.out.println(rows + " matching Tracer Categories updated");

        // Step 10 Complete the Missing Generic labels
        sqlString = "truncate table tmp_label";
        this.jdbcTemplate.update(sqlString);

        // Step 11 Insert Unique Genric labels into the tmp_label table
        sqlString = "insert into tmp_label SELECT null, tfu.GENERIC_LABEL, null, 0 from tmp_forecasting_unit tfu WHERE tfu.GENERIC_LABEL IS NOT NULL and tfu.GENERIC_LABEL != '' group by tfu.GENERIC_LABEL";
        rows = this.jdbcTemplate.update(sqlString);
        System.out.println(rows + " unique Generic names put into tmp_label");

        // Step 12 Get the max count on the label table so that you can now work on data that you insert from here on
        sqlString = "SELECT MAX(LABEL_ID) m FROM ap_label";
        max = this.jdbcTemplate.queryForObject(sqlString, Integer.class);
        System.out.println(max + " is the current Max count for ap_label");

        // Step 13 Insert the labels into ap_label
        sqlString = "INSERT INTO ap_label select null, tl.LABEL, null, null, null, 1, now(), 1, now() from tmp_label tl where tl.FOUND=false";
        rows = this.jdbcTemplate.update(sqlString);
        System.out.println(rows + " rows inserted into ap_label");

        // Step 14 Now find the LABEL_ID for the rows that you just inserted and update your tmp table with those
        sqlString = "update tmp_label tl LEFT JOIN ap_label ful on ful.LABEL_EN=tl.LABEL and ful.LABEL_ID>? set tl.LABEL_ID = ful.LABEL_ID where ful.LABEL_ID is not null;";
        rows = this.jdbcTemplate.update(sqlString, max);
        System.out.println(rows + " rows matched and LABEL_ID updated with new LABEL_ID");

        // Step 15 Update the Generic Label with the new ones inserted
        sqlString = "update tmp_forecasting_unit tfu left join tmp_label tl ON tfu.GENERIC_LABEL=tl.LABEL SET tfu.GENERIC_LABEL_ID=tl.LABEL_ID";
        rows = this.jdbcTemplate.update(sqlString);
        System.out.println(rows + " rows updated with Generic Label Id");

        // Step 16 Now insert all the data into ForecastingUnit
        sqlString = "INSERT INTO rm_forecasting_unit select null, 1, tfu.PRODUCT_CATEGORY_ID, tfu.TRACER_CATEGORY_ID, tfu.GENERIC_LABEL_ID, tfu.LABEL_ID, 1, 1, now(), 1, now() from tmp_forecasting_unit tfu";
        rows = this.jdbcTemplate.update(sqlString);
        System.out.println(rows + " Inserted into the Forecasting Unit table");
    }

    @Override
    public void pullPlanningUnitFromTmpTables() {
        String sqlString = "DROP TABLE IF EXISTS tmp_planning_unit";
        this.jdbcTemplate.update(sqlString);

        sqlString = "CREATE TABLE `tmp_planning_unit` ( \n"
                + "	`ID` int(10) unsigned NOT NULL AUTO_INCREMENT, \n"
                + "    `LABEL` varchar(200) COLLATE utf8_bin NOT NULL, \n"
                + "    `LABEL_ID` int (10) unsigned DEFAULT NULL, \n"
                + "    `MULTIPLIER` double (12,2) UNSIGNED DEFAULT null,\n"
                + " 	`UNIT_ID` int (10) unsigned DEFAULT NULL,\n"
                + "	`FORECASTING_UNIT_ID` int (10) unsigned DEFAULT NULL,\n"
                + "    PRIMARY KEY (`ID`) \n"
                + ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin";
        this.jdbcTemplate.update(sqlString);

        sqlString = "ALTER TABLE `fasp`.`tmp_planning_unit` \n"
                + "ADD INDEX `tmpPlanningUnit_label` (`LABEL` ASC),\n"
                + "ADD INDEX `fk_tmp_planning_unit_unitId_idx` (`UNIT_ID` ASC),\n"
                + "ADD INDEX `fk_tmp_planning_unit_forecastingUnit_idx` (`FORECASTING_UNIT_ID` ASC),\n"
                + "ADD INDEX `fk_tmp_planning_unit_labelId_idx` (`LABEL_ID` ASC)";
        this.jdbcTemplate.update(sqlString);
        sqlString = "ALTER TABLE `fasp`.`tmp_planning_unit` \n"
                + "ADD CONSTRAINT `fk_tmp_planning_unit_unitId`\n"
                + "  FOREIGN KEY (`UNIT_ID`)\n"
                + "  REFERENCES `fasp`.`ap_unit` (`UNIT_ID`)\n"
                + "  ON DELETE NO ACTION\n"
                + "  ON UPDATE NO ACTION,\n"
                + "ADD CONSTRAINT `fk_tmp_planning_unit_forecastingUnit`\n"
                + "  FOREIGN KEY (`FORECASTING_UNIT_ID`)\n"
                + "  REFERENCES `fasp`.`rm_forecasting_unit` (`FORECASTING_UNIT_ID`)\n"
                + "  ON DELETE NO ACTION\n"
                + "  ON UPDATE NO ACTION,\n"
                + "ADD CONSTRAINT `fk_tmp_planning_unit_labelId`\n"
                + "  FOREIGN KEY (`LABEL_ID`)\n"
                + "  REFERENCES `fasp`.`ap_label` (`LABEL_ID`)\n"
                + "  ON DELETE NO ACTION\n"
                + "  ON UPDATE NO ACTION";
        this.jdbcTemplate.update(sqlString);

        sqlString = "truncate table tmp_label";
        this.jdbcTemplate.update(sqlString);

        // Step 1 Insert the PlanningUnit name into the tmpTable
        sqlString = "insert into tmp_label SELECT null, ProductName, null, 0 FROM tmp_product_catalog tpc group by tpc.ProductName";
        int rows = this.jdbcTemplate.update(sqlString);
        System.out.println(rows + " labels instered into the tmp label table");

        // Step 2 Find the matching labels that are already there
        sqlString = "update tmp_label tl LEFT JOIN (SELECT pul.LABEL_ID, pul.LABEL_EN FROM rm_planning_unit pu LEFT JOIN ap_label pul on pu.LABEL_ID=pul.LABEL_ID) as puld ON tl.LABEL=puld.LABEL_EN \n"
                + "set tl.LABEL_ID = puld.LABEL_ID, tl.FOUND=1\n"
                + "where puld.LABEL_ID is not null";
        rows = this.jdbcTemplate.update(sqlString);
        System.out.println(rows + " labels are already present");

        // Step 3 Get the max count on the label table so that you can now work on data that you insert from here on
        sqlString = "SELECT MAX(LABEL_ID) m FROM ap_label";
        int max = this.jdbcTemplate.queryForObject(sqlString, Integer.class);
        System.out.println(max + " is the current Max count for ap_label");

        // Step 4 Insert the rows that you do not have in label table
        sqlString = "INSERT INTO ap_label select null, tl.LABEL, null, null, null, 1, now(), 1, now() from tmp_label tl where tl.FOUND=false";
        rows = this.jdbcTemplate.update(sqlString);
        System.out.println(rows + " rows inserted into the ap_label table");

        // Step 5 Now find the LABEL_ID for the rows that you just inserted and update your tmp table with those
        sqlString = "update tmp_label tl LEFT JOIN ap_label pul on pul.LABEL_EN=tl.LABEL and pul.LABEL_ID>? set tl.LABEL_ID = pul.LABEL_ID where pul.LABEL_ID is not null";
        rows = this.jdbcTemplate.update(sqlString, max);
        System.out.println(rows + " rows matched and LABEL_ID updated with new LABEL_ID");

        // Step 6 Insert into tmp planning unit 
        sqlString = "INSERT INTO tmp_planning_unit SELECT null, tl.LABEL, tl.LABEL_ID, 1, null, null  from tmp_label tl where tl.FOUND=0";
        rows = this.jdbcTemplate.update(sqlString);
        System.out.println(rows + " rows inserted into tmpPlanningUnit");

        // Step 7 Update the ForecastingUnit and Unit
        sqlString = "update tmp_planning_unit tpu \n"
                + "LEFT JOIN tmp_product_catalog tpc ON tpu.LABEL=tpc.ProductName\n"
                + "LEFT JOIN (SELECT u.*, ul.LABEL_EN FROM ap_unit u LEFT JOIN ap_label ul ON u.LABEL_ID=ul.LABEL_ID) tud ON tpc.OrderUOM=tud.LABEL_EN\n"
                + "set tpu.UNIT_ID=tud.UNIT_ID";
        rows = this.jdbcTemplate.update(sqlString);
        System.out.println(rows + " UnitId's updated in tmpPlanningUnit");

        sqlString = "update tmp_planning_unit tpu \n"
                + "LEFT JOIN tmp_product_catalog tpc ON tpu.LABEL=tpc.ProductName\n"
                + "LEFT JOIN (SELECT fu.*, ful.LABEL_EN FROM rm_forecasting_unit fu LEFT JOIN ap_label ful ON fu.LABEL_ID=ful.LABEL_ID) tfud ON tpc.ProductNameNoPack=tfud.LABEL_EN\n"
                + "set tpu.FORECASTING_UNIT_ID=tfud.FORECASTING_UNIT_ID";
        rows = this.jdbcTemplate.update(sqlString);
        System.out.println(rows + " ForecastingUnits updated in tmpPlanningUnit");

        // Step 8 Insert into the main planning Unit
        sqlString = "insert into rm_planning_unit select null, tpu.FORECASTING_UNIT_ID, tpu.LABEL_ID, tpu.UNIT_ID, tpu.MULTIPLIER, 1, 1, now(), 1, now() from tmp_planning_unit tpu";
        rows = this.jdbcTemplate.update(sqlString);
        System.out.println(rows + " inserted into Planning unit");

        // Now for Procurment Agent Planning Unit
        sqlString = "drop table IF EXISTS `tmp_procurement_agent_planning_unit`";
        this.jdbcTemplate.update(sqlString);

        sqlString = "CREATE TABLE `tmp_procurement_agent_planning_unit` ( \n"
                + "	`ID` int(10) unsigned NOT NULL AUTO_INCREMENT, \n"
                + "    `PLANNING_UNIT_ID` int(10) UNSIGNED not null,\n"
                + "    `SKU_CODE` VARCHAR(50) null,\n"
                + "    `EST_PRICE` varchar(20) null,\n"
                + "    `MOQ` VARCHAR(20) null,\n"
                + "    `UNITS_PER_PALLET` VARCHAR(20) null,\n"
                + "    `UNITS_PER_CONTAINER` VARCHAR(20) null,\n"
                + "    `VOLUME` VARCHAR(20) null,\n"
                + "    `WEIGHT` VARCHAR(20) null,\n"
                + "    PRIMARY KEY (`ID`) \n"
                + ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin";
        this.jdbcTemplate.update(sqlString);

        sqlString = "insert into tmp_procurement_agent_planning_unit select null, pu.PLANNING_UNIT_ID, null, null, null, null, null, null, null from rm_planning_unit pu";
        rows = this.jdbcTemplate.update(sqlString);
        System.out.println(rows + " rowsinstered into the tmpProcurementAgentPlanningUnit table");

        sqlString = "update tmp_procurement_agent_planning_unit tpapu LEFT JOIN rm_planning_unit pu ON tpapu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID LEFT JOIN ap_label pul ON pu.LABEL_ID=pul.LABEL_ID LEFT JOIN tmp_product_catalog tpc ON pul.LABEL_EN=tpc.ProductName\n"
                + "SET \n"
                + "	tpapu.SKU_CODE=tpc.ProductID, \n"
                + "    tpapu.EST_PRICE=if(tpc.EstPrice='', null,tpc.EstPrice), \n"
                + "    tpapu.MOQ=if(tpc.PlanningUnitMOQ='', null,tpc.PlanningUnitMOQ), \n"
                + "    tpapu.UNITS_PER_PALLET=if(tpc.PlanningUnitsperPallet='',null,tpc.PlanningUnitsperPallet), \n"
                + "    tpapu.UNITS_PER_CONTAINER=if(tpc.PlanningUnitsperContainer='',null,tpc.PlanningUnitsperContainer), \n"
                + "    tpapu.VOLUME=if(tpc.PlanningUnitVolumem3='',null,tpc.PlanningUnitVolumem3), \n"
                + "    tpapu.WEIGHT=if(tpc.PlanningUnitWeightkg='',null,tpc.PlanningUnitWeightkg)";
        rows = this.jdbcTemplate.update(sqlString);
        System.out.println(rows + " rows mapped with data for planning unit");

        sqlString = "insert into rm_procurement_agent_planning_unit select null, 1, tpapu.PLANNING_UNIT_ID, tpapu.SKU_CODE, tpapu.EST_PRICE, tpapu.MOQ, tpapu.UNITS_PER_PALLET, tpapu.UNITS_PER_CONTAINER, tpapu.VOLUME, tpapu.WEIGHT, 1, 1, now(), 1, now() from tmp_procurement_agent_planning_unit tpapu "
                + "where tpapu.SKU_CODE IS NOT NULL;";
        rows = this.jdbcTemplate.update(sqlString);
        System.out.println(rows + " rows inserted into procurementAgentPlanningUnit");
    }

    @Override
    public void pullSupplierFromTmpTables() {
        String sql = "DROP TABLE IF EXISTS `tmp_supplier`";
        this.jdbcTemplate.execute(sql);

        sql = "CREATE TABLE `tmp_supplier` ( "
                + " `ID` int(10) unsigned NOT NULL AUTO_INCREMENT, "
                + " `LABEL` varchar(200) COLLATE utf8_bin NOT NULL, "
                + " `SUPPLIER_ID` int (10) unsigned DEFAULT NULL, "
                + " `LABEL_ID` int (10) unsigned DEFAULT NULL, "
                + " PRIMARY KEY (`ID`) "
                + " ) ENGINE = InnoDB DEFAULT CHARSET = utf8 COLLATE = utf8_bin";
        this.jdbcTemplate.execute(sql);

        sql = "ALTER TABLE `fasp`.`tmp_supplier` ADD UNIQUE INDEX `index3` (`LABEL` ASC)";
        this.jdbcTemplate.execute(sql);

        sql = "INSERT IGNORE INTO tmp_supplier "
                + "SELECT NULL, Supplier, NULL, NULL "
                + "FROM tmp_product_catalog tpc "
                + "WHERE Supplier IS NOT NULL AND Supplier <> '' "
                + "GROUP BY Supplier;";

        this.jdbcTemplate.update(sql);

        sql = "UPDATE rm_supplier rs "
                + "LEFT JOIN ap_label al ON rs.`LABEL_ID` = al.`LABEL_ID` "
                + "LEFT JOIN tmp_supplier ts ON al.`LABEL_EN` = ts.LABEL "
                + "SET ts.SUPPLIER_ID = rs.`SUPPLIER_ID` "
                + "WHERE ts.ID IS NOT NULL;";
        this.jdbcTemplate.execute(sql);

        sql = "SELECT MAX(LABEL_ID) FROM ap_label";
        int max = this.jdbcTemplate.queryForObject(sql, Integer.class);

        sql = "INSERT INTO ap_label SELECT NULL, ts.LABEL, NULL, NULL, NULL, 1, NOW(), 1, NOW() "
                + "FROM tmp_supplier ts WHERE ts.SUPPLIER_ID IS NULL";
        this.jdbcTemplate.update(sql);

        sql = "UPDATE tmp_supplier ts LEFT JOIN ap_label l ON ts.LABEL=l.LABEL_EN AND l.LABEL_ID>"
                + max + " SET ts.LABEL_ID=l.LABEL_ID WHERE ts.SUPPLIER_ID IS NULL";
        this.jdbcTemplate.update(sql);

        sql = "INSERT INTO rm_supplier "
                + "SELECT NULL, 1, ts.LABEL_ID, 1, 1, NOW(), 1 , NOW() "
                + "FROM tmp_supplier ts "
                + "WHERE ts.SUPPLIER_ID IS NULL";
        this.jdbcTemplate.update(sql);
    }

    @Override
    public void pullProcurementUnitFromTmpTables() {
        // Step 1 -- Create Tables
        String sqlString = "DROP TABLE IF EXISTS tmp_procurement_unit";
        this.jdbcTemplate.update(sqlString);

        sqlString = "CREATE TABLE `tmp_procurement_unit` ( \n"
                + "	`ID` int(10) unsigned NOT NULL AUTO_INCREMENT, \n"
                + "    `LABEL` varchar(200) COLLATE utf8_bin NOT NULL, \n"
                + "    `LABEL_ID` int (10) unsigned DEFAULT NULL, \n"
                + "    `MULTIPLIER` double (12,2) UNSIGNED DEFAULT null,\n"
                + " 	`UNIT_ID` int (10) unsigned DEFAULT NULL,\n"
                + "	`PLANNING_UNIT_ID` int (10) unsigned DEFAULT NULL,\n"
                + "    `SUPPLIER_ID` int (10) unsigned DEFAULT NULL,\n"
                + "    `WIDTH_UNIT_ID` int (10) unsigned DEFAULT NULL,\n"
                + "    `WIDTH` decimal (12,2) unsigned DEFAULT NULL,\n"
                + "    `LENGTH_UNIT_ID` int (10) unsigned DEFAULT NULL,\n"
                + "    `LENGTH` decimal (12,2) unsigned DEFAULT NULL,\n"
                + "    `HEIGHT_UNIT_ID` int (10) unsigned DEFAULT NULL,\n"
                + "    `HEIGHT` decimal (12,2) unsigned DEFAULT NULL,\n"
                + "    `WEIGHT_UNIT_ID` int (10) unsigned DEFAULT NULL,\n"
                + "    `WEIGHT` decimal (12,2) unsigned DEFAULT NULL,\n"
                + "    `UNITS_PER_CONTAINER` int (10) unsigned DEFAULT NULL,\n"
                + "    `LABELING` Varchar(200) DEFAULT NULL,\n"
                + "    `SKU_CODE` Varchar(200) DEFAULT NULL,\n"
                + "    `GTIN` Varchar(200) DEFAULT NULL,\n"
                + "    `VENDOR_PRICE` Varchar(200) DEFAULT NULL,\n"
                + "    `APPROVED_TO_SHIPPED_LEAD_TIME` DECIMAL(12,2) DEFAULT NULL,\n"
                + "    `FOUND` tinyint(1) unsigned default 0 not null,\n"
                + "    CONSTRAINT PRIMARY KEY (`ID`) \n"
                + ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin";
        this.jdbcTemplate.update(sqlString);

        sqlString = "ALTER TABLE `fasp`.`tmp_procurement_unit` \n"
                + "ADD INDEX `idx_procurementUnit1` (`LABEL` ASC),\n"
                + "ADD INDEX `idx_procurementUnit2` (`LABEL_ID` ASC),\n"
                + "ADD INDEX `idx_procurementUnit3` (`UNIT_ID` ASC),\n"
                + "ADD INDEX `idx_procurementUnit4` (`PLANNING_UNIT_ID` ASC),\n"
                + "ADD INDEX `idx_procurementUnit5` (`WIDTH_UNIT_ID` ASC),\n"
                + "ADD INDEX `idx_procurementUnit6` (`HEIGHT_UNIT_ID` ASC),\n"
                + "ADD INDEX `idx_procurementUnit7` (`LENGTH_UNIT_ID` ASC),\n"
                + "ADD INDEX `idx_procurementUnit8` (`WEIGHT_UNIT_ID` ASC)";
        this.jdbcTemplate.update(sqlString);

        sqlString = "ALTER TABLE `fasp`.`tmp_procurement_unit` \n"
                + "ADD CONSTRAINT `fk_forecastingUnit1`\n"
                + "  FOREIGN KEY (`LABEL_ID`)\n"
                + "  REFERENCES `fasp`.`ap_label` (`LABEL_ID`)\n"
                + "  ON DELETE NO ACTION\n"
                + "  ON UPDATE NO ACTION,\n"
                + "ADD CONSTRAINT `fk_procurementUnit2`\n"
                + "  FOREIGN KEY (`UNIT_ID`)\n"
                + "  REFERENCES `fasp`.`ap_unit` (`UNIT_ID`)\n"
                + "  ON DELETE NO ACTION\n"
                + "  ON UPDATE NO ACTION,\n"
                + "ADD CONSTRAINT `fk_procurementUnit3`\n"
                + "  FOREIGN KEY (`PLANNING_UNIT_ID`)\n"
                + "  REFERENCES `fasp`.`rm_planning_unit` (`PLANNING_UNIT_ID`)\n"
                + "  ON DELETE NO ACTION\n"
                + "  ON UPDATE NO ACTION,\n"
                + "ADD CONSTRAINT `fk_procurementUnit4`\n"
                + "  FOREIGN KEY (`SUPPLIER_ID`)\n"
                + "  REFERENCES `fasp`.`rm_supplier` (`SUPPLIER_ID`)\n"
                + "  ON DELETE NO ACTION\n"
                + "  ON UPDATE NO ACTION,\n"
                + "ADD CONSTRAINT `fk_procurementUnit5`\n"
                + "  FOREIGN KEY (`WIDTH_UNIT_ID`)\n"
                + "  REFERENCES `fasp`.`ap_unit` (`UNIT_ID`)\n"
                + "  ON DELETE NO ACTION\n"
                + "  ON UPDATE NO ACTION,\n"
                + "  ADD CONSTRAINT `fk_procurementUnit6`\n"
                + "  FOREIGN KEY (`LENGTH_UNIT_ID`)\n"
                + "  REFERENCES `fasp`.`ap_unit` (`UNIT_ID`)\n"
                + "  ON DELETE NO ACTION\n"
                + "  ON UPDATE NO ACTION,\n"
                + "  ADD CONSTRAINT `fk_procurementUnit7`\n"
                + "  FOREIGN KEY (`HEIGHT_UNIT_ID`)\n"
                + "  REFERENCES `fasp`.`ap_unit` (`UNIT_ID`)\n"
                + "  ON DELETE NO ACTION\n"
                + "  ON UPDATE NO ACTION,\n"
                + "  ADD CONSTRAINT `fk_procurementUnit8`\n"
                + "  FOREIGN KEY (`WEIGHT_UNIT_ID`)\n"
                + "  REFERENCES `fasp`.`ap_unit` (`UNIT_ID`)\n"
                + "  ON DELETE NO ACTION\n"
                + "  ON UPDATE NO ACTION";
        this.jdbcTemplate.update(sqlString);

        sqlString = "truncate table tmp_procurement_unit";

        sqlString = "truncate table tmp_label";
        this.jdbcTemplate.update(sqlString);

        // Step 2 - Insert into tmpLabel
        sqlString = "insert into tmp_label SELECT null, ItemName, null, 0 FROM tmp_product_catalog tpc group by tpc.ItemName";
        int rows = this.jdbcTemplate.update(sqlString);
        System.out.println(rows + " rows inserted into tmpLabel");

        // Step 3 - Find already existing labels
        sqlString = "update tmp_label tl LEFT JOIN (SELECT pul.LABEL_ID, pul.LABEL_EN FROM rm_procurement_unit pu LEFT JOIN ap_label pul on pu.LABEL_ID=pul.LABEL_ID) as puld ON tl.LABEL=puld.LABEL_EN  set tl.LABEL_ID = puld.LABEL_ID, tl.FOUND=1 where puld.LABEL_ID is not null";
        rows = this.jdbcTemplate.update(sqlString);
        System.out.println(rows + " labels already existed");

        // Step 4 - Find max
        sqlString = "SELECT MAX(ap_label.LABEL_ID) from ap_label";
        int max = this.jdbcTemplate.queryForObject(sqlString, Integer.class);
        System.out.println(max + " Current max");

        // Step 5 - Insert into ap_labels
        sqlString = "INSERT INTO ap_label select null, tl.LABEL, null, null, null, 1, now(), 1, now() from tmp_label tl where tl.FOUND=false";
        rows = this.jdbcTemplate.update(sqlString);
        System.out.println(rows + " rows inserted for labels");

        // Step 6 - Match the Labels inserted to find the label_id
        sqlString = "update tmp_label tl LEFT JOIN ap_label pul on pul.LABEL_EN=tl.LABEL and pul.LABEL_ID>? set tl.LABEL_ID = pul.LABEL_ID where pul.LABEL_ID is not null";
        rows = this.jdbcTemplate.update(sqlString, max);
        System.out.println(rows + " rows matched with new labels");

        // Step 7 - Insert into the tmp_procurement table
        sqlString = "INSERT INTO tmp_procurement_unit SELECT null, tl.LABEL, tl.LABEL_ID, null, null, null, null, null, null, null, null, null, null, null, null, null,null, null, null, null, null, 1  from tmp_label tl where tl.FOUND=0";
        rows = this.jdbcTemplate.update(sqlString);
        System.out.println(rows + " rows inserted into the tmp_procurement table");

        Scanner s = new Scanner(System.in);
        boolean validData = false;
        Double approvedToShipppedLeadTime = null;
        do {
            System.out.println("---------------------------------------------------------------------------");
            System.out.println("Please enter the Approved to Shipped Lead Time (in Months) that you want to use as default for all Procurement Units.");
            System.out.println("Could be a Decimal or wirte 'null' if you want to keep it as null");
            System.out.println("Approved to Shipped Lead Time (in months): ");
            String input = s.nextLine();
            if (NumberUtils.isCreatable(input) || input.equals("null")) {
                validData = true;
                if (!input.equals("null")) {
                    approvedToShipppedLeadTime = NumberUtils.toDouble(input);
                }
            }
        } while (!validData);

        // Step 8 - match and update other data
        sqlString = "update tmp_procurement_unit tpu \n"
                + "LEFT JOIN tmp_product_catalog tpc ON tpu.LABEL=tpc.ItemName\n"
                + "LEFT JOIN (SELECT u.*, ul.LABEL_EN FROM ap_unit u LEFT JOIN ap_label ul ON u.LABEL_ID=ul.LABEL_ID) tuhd ON tpc.HeightUOM=tuhd.LABEL_EN\n"
                + "LEFT JOIN (SELECT u.*, ul.LABEL_EN FROM ap_unit u LEFT JOIN ap_label ul ON u.LABEL_ID=ul.LABEL_ID) tuld ON tpc.LengthUOM=tuld.LABEL_EN\n"
                + "LEFT JOIN (SELECT u.*, ul.LABEL_EN FROM ap_unit u LEFT JOIN ap_label ul ON u.LABEL_ID=ul.LABEL_ID) tuwd ON tpc.WidthUOM=tuwd.LABEL_EN\n"
                + "LEFT JOIN (SELECT u.*, ul.LABEL_EN FROM ap_unit u LEFT JOIN ap_label ul ON u.LABEL_ID=ul.LABEL_ID) tuwed ON tpc.WeightUOM=tuwed.LABEL_EN\n"
                + "set \n"
                + "	tpu.HEIGHT=if(tpc.Height='', null, tpc.Height),\n"
                + "    tpu.LENGTH=if(tpc.Length='', null, tpc.Length),\n"
                + "    tpu.WIDTH=if(tpc.Width='', null, tpc.Width),\n"
                + "    tpu.WEIGHT=if(tpc.Weight='', null, tpc.Weight),\n"
                + "    tpu.UNITS_PER_CONTAINER=if(tpc.UnitsperContainer='', null, tpc.UnitsperContainer),\n"
                + "    tpu.LABELING=if(tpc.Labeling='', null, tpc.Labeling),\n"
                + "    tpu.SKU_CODE=if(tpc.ItemID='', null, tpc.ItemID),\n"
                + "    tpu.GTIN=if(tpc.GTIN='', null, tpc.GTIN),\n"
                + "    tpu.VENDOR_PRICE=null,\n"
                + "    tpu.APPROVED_TO_SHIPPED_LEAD_TIME=" + approvedToShipppedLeadTime + ","
                + "    tpu.HEIGHT_UNIT_ID=tuhd.UNIT_ID,\n"
                + "    tpu.LENGTH_UNIT_ID=tuld.UNIT_ID,\n"
                + "    tpu.WIDTH_UNIT_ID=tuwd.UNIT_ID,\n"
                + "    tpu.WEIGHT_UNIT_ID=tuwed.UNIT_ID";
        rows = this.jdbcTemplate.update(sqlString);
        System.out.println(rows + " rows updated with matching data from product_catalog");

        // Step 9 - Match the Planning Unit, Multiplier and UnitId
        sqlString = "update tmp_procurement_unit tpu \n"
                + "LEFT JOIN tmp_product_catalog tpc ON tpu.LABEL=tpc.ItemName\n"
                + "LEFT JOIN (SELECT pu.PLANNING_UNIT_ID, pul.LABEL_EN, pu.MULTIPLIER, pu.UNIT_ID FROM rm_planning_unit pu LEFT JOIN ap_label pul ON pu.LABEL_ID=pul.LABEL_ID) tpud ON tpc.ProductName=tpud.LABEL_EN\n"
                + "set tpu.PLANNING_UNIT_ID=tpud.PLANNING_UNIT_ID, tpu.MULTIPLIER=tpud.MULTIPLIER, tpu.UNIT_ID=tpud.UNIT_ID";
        this.jdbcTemplate.update(sqlString);
        System.out.println(rows + " rows updated with matching data from planning_unit");

        // Step 10 - MAtch the Supplier Id
        sqlString = "update tmp_procurement_unit tpu \n"
                + "LEFT JOIN tmp_product_catalog tpc ON tpu.LABEL=tpc.ItemName\n"
                + "LEFT JOIN (SELECT s.SUPPLIER_ID, sl.LABEL_EN from rm_supplier s LEFT JOIN ap_label sl ON s.LABEL_ID=sl.LABEL_ID) tsd ON tpc.Supplier=tsd.LABEL_EN\n"
                + "set tpu.SUPPLIER_ID=tsd.SUPPLIER_ID";
        rows = this.jdbcTemplate.update(sqlString);
        System.out.println(rows + " rows updated with matching data from supplier");

        // Step 10a - Find max count for procurment_unit
        sqlString = "SELECT IFNULL(MAX(PROCUREMENT_UNIT_ID),0) from rm_procurement_unit";
        max = this.jdbcTemplate.queryForObject(sqlString, Integer.class);
        System.out.println(max + " Current max for ProcurementUnit");

        // Step 11 - Insert into the procurement_unit
        sqlString = "insert into rm_procurement_unit \n"
                + "SELECT null, tpu.PLANNING_UNIT_ID, tpu.LABEL_ID, tpu.UNIT_ID, tpu.MULTIPLIER, tpu.SUPPLIER_ID, tpu.WIDTH_UNIT_ID, tpu.WIDTH, tpu.HEIGHT_UNIT_ID, tpu.HEIGHT, tpu.LENGTH_UNIT_ID, tpu.LENGTH, tpu.WEIGHT_UNIT_ID, tpu.WEIGHT, tpu.UNITS_PER_CONTAINER, tpu.LABELING, 1, 1, now(), 1, now() from tmp_procurement_unit tpu WHERE tpu.SUPPLIER_ID is not null && tpu.PLANNING_UNIT_ID is not null AND tpu.LABEL_ID is not null";
        rows = this.jdbcTemplate.update(sqlString);
        System.out.println(rows + " rows inserted into the procurement unit table");

        // Step 12 - Insert into the procurement_agent_procurement_unit
        sqlString = "insert ignore into rm_procurement_agent_procurement_unit SELECT null, pu.PROCUREMENT_UNIT_ID, 1, tpu.SKU_CODE, tpu.VENDOR_PRICE, tpu.APPROVED_TO_SHIPPED_LEAD_TIME, tpu.GTIN, 1, 1, now(), 1, now() from rm_procurement_unit pu LEFT JOIN tmp_procurement_unit tpu ON pu.LABEL_ID=tpu.LABEL_ID WHERE tpu.SKU_CODE is not null AND pu.PROCUREMENT_UNIT_ID>?";
        rows = this.jdbcTemplate.update(sqlString, max);
        System.out.println(rows + " rows inserted into the procurement_agent_procurement_unit table");
    }

}
