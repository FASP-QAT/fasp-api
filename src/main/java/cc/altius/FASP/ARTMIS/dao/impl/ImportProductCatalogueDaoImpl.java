/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.ARTMIS.dao.impl;

import cc.altius.FASP.ARTMIS.dao.ImportProductCatalogueDao;
import cc.altius.FASP.model.EmailTemplate;
import cc.altius.FASP.model.Emailer;
import cc.altius.FASP.service.EmailService;
import cc.altius.utils.DateUtils;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import javax.sql.DataSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.BadSqlGrammarException;
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
    @Autowired
    private EmailService emailService;

    @Value("${catalogFilePath}")
    private String CATALOG_FILE_PATH;
    @Value("${catalogBkpFilePath}")
    private String BKP_CATALOG_FILE_PATH;

    private static org.slf4j.Logger LOG = LoggerFactory.getLogger(ImportProductCatalogueDaoImpl.class);
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    @Transactional
    public void importProductCatalogue(String filePath) throws ParserConfigurationException, SAXException, IOException, FileNotFoundException, BadSqlGrammarException {
//        try {
        EmailTemplate emailTemplate = this.emailService.getEmailTemplateByEmailTemplateId(3);
        String[] subjectParam = new String[]{};
        String[] bodyParam = null;
        Emailer emailer = new Emailer();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy hh:mm a");
        String date = simpleDateFormat.format(DateUtils.getCurrentDateObject(DateUtils.EST));
        String filepath, fileList = "";
        File dir = new File(CATALOG_FILE_PATH);
        FileFilter fileFilter = new WildcardFileFilter("item_data_*.xml");
        File[] files = dir.listFiles(fileFilter);
        logger.info("Going to start product catalogue import");
        for (int i = 0; i < files.length; i++) {
            fileList += " " + files[i];
            logger.info("File names---" + files[i]);
        }
        if (files.length > 1) {
            subjectParam = new String[]{"Product Catalogue", "Multiple files found in source folder"};
            bodyParam = new String[]{"Product Catalogue", date, "Multiple files found in source folder", fileList};
            emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), "anchal.c@altius.cc,shubham.y@altius.cc,priti.p@altius.cc,sameer.g@altiusbpo.com", "shubham.y@altius.cc,priti.p@altius.cc,sameer.g@altiusbpo.com", subjectParam, bodyParam);
            int emailerId = this.emailService.saveEmail(emailer);
            emailer.setEmailerId(emailerId);
            this.emailService.sendMail(emailer);
            logger.info("Multiple files found in source folder");
        } else if (files.length < 1) {
            subjectParam = new String[]{"Product Catalogue", "File not found"};
            bodyParam = new String[]{"Product Catalogue", date, "File not found", "File not found"};
            emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), "anchal.c@altius.cc,shubham.y@altius.cc,priti.p@altius.cc,sameer.g@altiusbpo.com", "shubham.y@altius.cc,priti.p@altius.cc,sameer.g@altiusbpo.com", subjectParam, bodyParam);
            int emailerId = this.emailService.saveEmail(emailer);
            emailer.setEmailerId(emailerId);
            this.emailService.sendMail(emailer);
            logger.info("File not found");
        } else {
            File fXmlFile = new File(fileList.trim());
            String extension = "";
            int i = fXmlFile.getName().lastIndexOf('.');
            if (i > 0) {
                extension = fXmlFile.getName().substring(i + 1);
            }
            if (!extension.equalsIgnoreCase("xml")) {
                subjectParam = new String[]{"Product Catalogue", "File is not an xml"};
                bodyParam = new String[]{"Product Catalogue", date, "File is not an xml", fileList};
                emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), "anchal.c@altius.cc,shubham.y@altius.cc,priti.p@altius.cc,sameer.g@altiusbpo.com", "shubham.y@altius.cc,priti.p@altius.cc,sameer.g@altiusbpo.com", subjectParam, bodyParam);
                int emailerId = this.emailService.saveEmail(emailer);
                emailer.setEmailerId(emailerId);
                this.emailService.sendMail(emailer);
                logger.error("File is not an xml");
            } else {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(fXmlFile);
                doc.getDocumentElement().normalize();

                NodeList nList1 = doc.getElementsByTagName("itemdata");
                MapSqlParameterSource[] batchParams = new MapSqlParameterSource[nList1.getLength()];
                Map<String, Object> map = new HashedMap<String, Object>();
                int x = 0;
                String sql;

                logger.info("Going to drop tmp_product_catalog");
//                sql = "DROP TEMPORARY TABLE IF EXISTS `tmp_product_catalog`";
                sql = "DROP TABLE IF EXISTS `tmp_product_catalog`";
                this.jdbcTemplate.execute(sql);
                logger.info("Successfully droped tmp_product_catalog");

                logger.info("Going to create tmp_product_catalog");
//                sql = "CREATE TEMPORARY TABLE `tmp_product_catalog` ( "
                sql = "CREATE TABLE `tmp_product_catalog` ( "
                        + "  `TaskOrder` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                        + "  `CommodityCouncil` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                        + "  `Subcategory` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                        + "  `TracerCategory` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                        + "  `ProductActive` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                        + "  `ProductIDNoPack` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                        + "  `ProductNameNoPack` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                        + "  `ProductID` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                        + "  `ProductName` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                        + "  `OrderUOM` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                        + "  `PackSize` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                        + "  `NoofBaseUnits` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                        + "  `BaseUnit` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                        + "  `L5DataTrusteeCode` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                        + "  `UNSPSC` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                        + "  `INN` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                        + "  `Controlled` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                        + "  `Route` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                        + "  `Form` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                        + "  `QACategory` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                        + "  `QACriteria` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                        + "  `Drug1Name` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                        + "  `Drug1Abbr` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                        + "  `Drug1Qty` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                        + "  `Drug1Meas` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                        + "  `Drug1Unit` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                        + "  `Drug2Name` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                        + "  `Drug2Abbr` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                        + "  `Drug2Qty` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                        + "  `Drug2Meas` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                        + "  `Drug2Unit` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                        + "  `Drug3Name` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                        + "  `Drug3Abbr` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                        + "  `Drug3Qty` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                        + "  `Drug3Meas` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                        + "  `Drug3Unit` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                        + "  `Drug4Name` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                        + "  `Drug4Abbr` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                        + "  `Drug4Qty` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                        + "  `Drug4Meas` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                        + "  `Drug4Unit` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                        + "  `USAIDARVTier` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
//                        + "  `ProductAvailable` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                        + "  `PlanningUnitMOQ` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                        + "  `PlanningUnitsperPallet` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                        + "  `PlanningUnitsperContainer` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                        + "  `PlanningUnitVolumem3` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                        + "  `PlanningUnitWeightkg` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                        + "  `ItemID` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                        + "  `ItemName` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                        + "  `Supplier` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                        + "  `WeightUOM` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                        + "  `Weight` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                        + "  `HeightUOM` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                        + "  `Height` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
//                        + "  `LengthUOM` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                        + "  `Length` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
//                        + "  `WidthUOM` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                        + "  `Width` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                        + "  `GTIN` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                        + "  `Labeling` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                        + "  `ItemAvailable` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                        + "  `UnitsperCase` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                        + "  `UnitsperPallet` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
//                        + "  `PalletsPerContainer` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                        + "  `UnitsperContainer` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                        + "  `EstPrice` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                        + "  KEY `idxProductNameNoPack` (`ProductNameNoPack`), "
                        + "  KEY `idxProductName` (`ProductName`), "
                        + "  KEY `idxItemName` (`ItemName`) "
                        + ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin";
                this.jdbcTemplate.execute(sql);
                logger.info("Successfully created tmp_product_catalog");

                logger.info("Going to insert into tmp_product_catalog");
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
                        + ":usaidArvTier,:planningUnitMoq,:planningUnitPallet,:planningUnitsPerContainer,"
                        + ":planningUnitVolumeM3,:planningUnitWeightKg,:itemId,:itemName,:itemSupplierName,:itemWeightUom,"
                        + ":itemWeight,:itemSizeMeasureH,:itemHeight,:itemLength,:itemWidth,"
                        + ":itemManufacturerGtinUpc,:itemLabelLanguages,:itemBuyable,:itemUnitsPerCase,:itemNumOfUnitsPallet,"
                        + ":unitsPerContainer,:wcsCataloguePrice);";
                for (int temp2 = 0; temp2 < nList1.getLength(); temp2++) {
                    Node nNode1 = nList1.item(temp2);
                    if (nNode1.getNodeType() == Node.ELEMENT_NODE) {
                        Element dataRecordElement = (Element) nNode1;
                        System.out.println("task order new---"+dataRecordElement.getElementsByTagName("task_order_long_description").item(0).getTextContent());
//                        System.out.println("task order old---"+dataRecordElement.getElementsByTagName("TASK_ORDER_LONG_DESCRIPTION").item(0).getTextContent());
                        map.put("taskOrderLongDescription", dataRecordElement.getElementsByTagName("task_order_long_description").item(0).getTextContent());
                        map.put("commodityCouncilLongDesc", dataRecordElement.getElementsByTagName("commodity_council_long_desc").item(0).getTextContent());
                        map.put("commoditySubcatLongDesc", dataRecordElement.getElementsByTagName("commodity_subcat_long_desc").item(0).getTextContent());
                        map.put("productTracerCat", dataRecordElement.getElementsByTagName("product_tracer_cat").item(0).getTextContent());
                        map.put("productBuyable", dataRecordElement.getElementsByTagName("product_buyable").item(0).getTextContent());
                        map.put("productIdNoPack", dataRecordElement.getElementsByTagName("product_id_no_pack").item(0).getTextContent());
                        map.put("productNameNoPack", dataRecordElement.getElementsByTagName("product_name_no_pack").item(0).getTextContent());
                        map.put("productId", dataRecordElement.getElementsByTagName("product_id").item(0).getTextContent());
                        map.put("productName", dataRecordElement.getElementsByTagName("product_name").item(0).getTextContent());
                        map.put("itemUom", dataRecordElement.getElementsByTagName("item_uom").item(0).getTextContent());
                        map.put("productPackSize", dataRecordElement.getElementsByTagName("product_pack_size").item(0).getTextContent());
                        map.put("productBaseUnitMult", dataRecordElement.getElementsByTagName("product_base_unit_mult").item(0).getTextContent());
                        map.put("productBaseUnit", dataRecordElement.getElementsByTagName("product_base_unit").item(0).getTextContent());
                        map.put("productDataTrusteeProductIdentifier", dataRecordElement.getElementsByTagName("product_data_trustee_product_identifier").item(0).getTextContent());
                        map.put("productUnspsc", dataRecordElement.getElementsByTagName("product_unspsc").item(0).getTextContent());
                        map.put("productInternationalNonproprietaryName", dataRecordElement.getElementsByTagName("product_international_nonproprietary_name").item(0).getTextContent());
                        map.put("productControlledItemWho", dataRecordElement.getElementsByTagName("product_controlled_item_who").item(0).getTextContent());
                        map.put("productAdministrationRoute", dataRecordElement.getElementsByTagName("product_administration_route").item(0).getTextContent());
                        map.put("productDosageForm", dataRecordElement.getElementsByTagName("product_dosage_form").item(0).getTextContent());
                        map.put("productQaEligibilityCategory", dataRecordElement.getElementsByTagName("product_qa_eligibility_category").item(0).getTextContent());
                        map.put("productQaEligibilityCriteria", dataRecordElement.getElementsByTagName("product_qa_eligibility_criteria").item(0).getTextContent());
                        map.put("productDrug1Name", dataRecordElement.getElementsByTagName("product_drug_1_name").item(0).getTextContent());
                        map.put("productDrug1Abbr", dataRecordElement.getElementsByTagName("product_drug_1_abbreviation").item(0).getTextContent());
                        map.put("productDrug1Strength", dataRecordElement.getElementsByTagName("product_drug_1_strength").item(0).getTextContent());
                        map.put("productDrug1Measure", dataRecordElement.getElementsByTagName("product_drug_1_measure").item(0).getTextContent());
                        map.put("productDrug1Unit", dataRecordElement.getElementsByTagName("product_drug_1_unit").item(0).getTextContent());

                        map.put("productDrug2Name", dataRecordElement.getElementsByTagName("product_drug_2_name").item(0).getTextContent());
                        map.put("productDrug2Abbr", dataRecordElement.getElementsByTagName("product_drug_2_abbreviation").item(0).getTextContent());
                        map.put("productDrug2Strength", dataRecordElement.getElementsByTagName("product_drug_2_strength").item(0).getTextContent());
                        map.put("productDrug2Measure", dataRecordElement.getElementsByTagName("product_drug_2_measure").item(0).getTextContent());
                        map.put("productDrug2Unit", dataRecordElement.getElementsByTagName("product_drug_2_unit").item(0).getTextContent());

                        map.put("productDrug3Name", dataRecordElement.getElementsByTagName("product_drug_3_name").item(0).getTextContent());
                        map.put("productDrug3Abbr", dataRecordElement.getElementsByTagName("product_drug_3_abbreviation").item(0).getTextContent());
                        map.put("productDrug3Strength", dataRecordElement.getElementsByTagName("product_drug_3_strength").item(0).getTextContent());
                        map.put("productDrug3Measure", dataRecordElement.getElementsByTagName("product_drug_3_measure").item(0).getTextContent());
                        map.put("productDrug3Unit", dataRecordElement.getElementsByTagName("product_drug_3_unit").item(0).getTextContent());

                        map.put("productDrug4Name", dataRecordElement.getElementsByTagName("product_drug_4_name").item(0).getTextContent());
                        map.put("productDrug4Abbr", dataRecordElement.getElementsByTagName("product_drug_4_abbreviation").item(0).getTextContent());
                        map.put("productDrug4Strength", dataRecordElement.getElementsByTagName("product_drug_4_strength").item(0).getTextContent());
                        map.put("productDrug4Measure", dataRecordElement.getElementsByTagName("product_drug_4_measure").item(0).getTextContent());
                        map.put("productDrug4Unit", dataRecordElement.getElementsByTagName("product_drug_4_unit").item(0).getTextContent());

                        map.put("usaidArvTier", dataRecordElement.getElementsByTagName("usaid_arv_tier").item(0).getTextContent());
                        // below
//                        map.put("productBuyable1", dataRecordElement.getElementsByTagName("product_buyable").item(1).getTextContent());
                        map.put("planningUnitMoq", dataRecordElement.getElementsByTagName("planning_unit_moq").item(0).getTextContent());
                        map.put("planningUnitPallet", dataRecordElement.getElementsByTagName("planning_unit_per_pallet").item(0).getTextContent());
                        map.put("planningUnitsPerContainer", dataRecordElement.getElementsByTagName("planning_unit_per_container").item(0).getTextContent());
                        map.put("planningUnitVolumeM3", dataRecordElement.getElementsByTagName("planning_unit_volume_m3").item(0).getTextContent());
                        map.put("planningUnitWeightKg", dataRecordElement.getElementsByTagName("planning_unit_weight_kg").item(0).getTextContent());
                        map.put("itemId", dataRecordElement.getElementsByTagName("item_id").item(0).getTextContent());
                        map.put("itemName", dataRecordElement.getElementsByTagName("item_name").item(0).getTextContent());
                        map.put("itemSupplierName", dataRecordElement.getElementsByTagName("item_supplier_name").item(0).getTextContent());
                        map.put("itemWeightUom", dataRecordElement.getElementsByTagName("item_weight_uom").item(0).getTextContent());
                        map.put("itemWeight", dataRecordElement.getElementsByTagName("item_weight").item(0).getTextContent());
                        map.put("itemSizeMeasureH", dataRecordElement.getElementsByTagName("item_sizemeasure").item(0).getTextContent());
                        map.put("itemHeight", dataRecordElement.getElementsByTagName("item_height").item(0).getTextContent());
//                        map.put("itemSizeMeasureL", dataRecordElement.getElementsByTagName("ITEM_SIZEMEASURE").item(1).getTextContent());
                        map.put("itemLength", dataRecordElement.getElementsByTagName("item_length").item(0).getTextContent());
//                        map.put("itemSizeMeasureW", dataRecordElement.getElementsByTagName("ITEM_SIZEMEASURE").item(2).getTextContent());
                        map.put("itemWidth", dataRecordElement.getElementsByTagName("item_width").item(0).getTextContent());
                        map.put("itemManufacturerGtinUpc", dataRecordElement.getElementsByTagName("item_manufacturer_gtin_upc").item(0).getTextContent());
                        map.put("itemLabelLanguages", dataRecordElement.getElementsByTagName("item_label_languages").item(0).getTextContent());
                        map.put("itemBuyable", dataRecordElement.getElementsByTagName("item_buyable").item(0).getTextContent());
                        map.put("itemUnitsPerCase", dataRecordElement.getElementsByTagName("item_units_per_case").item(0).getTextContent());
                        map.put("itemNumOfUnitsPallet", dataRecordElement.getElementsByTagName("item_num_of_units_pallet").item(0).getTextContent());
//                        map.put("itemNumOfPalletsContainer", dataRecordElement.getElementsByTagName("ITEM_NUM_OF_PALLETS_CONTAINER").item(0).getTextContent());
                        map.put("unitsPerContainer", dataRecordElement.getElementsByTagName("units_per_container").item(0).getTextContent());
                        map.put("wcsCataloguePrice", dataRecordElement.getElementsByTagName("wcs_catalog_price").item(0).getTextContent());
                        batchParams[x] = new MapSqlParameterSource(map);
                        x++;
                    }
                }
                int[] rows1 = namedParameterJdbcTemplate.batchUpdate(sql, batchParams);
                logger.info("Successfully inserted into tmp_product_catalog records---" + rows1.length);

                sql = "SELECT COUNT(*) FROM tmp_product_catalog tpc WHERE tpc.ProductNameNoPack IS NOT NULL AND tpc.ProductNameNoPack != '';";
                logger.info("rows with product name no pack ---" + this.jdbcTemplate.queryForObject(sql, Integer.class));

                // --------------------------Unit Table-----------------------
                logger.info("Going to drop tmp_unit");
                sql = "DROP TEMPORARY TABLE IF EXISTS `tmp_unit`";
                this.jdbcTemplate.execute(sql);

                logger.info("Successfully droped tmp_unit");
                logger.info("Going to create tmp_unit");
                sql = "CREATE TEMPORARY TABLE `tmp_unit` ( "
                        + " `ID` int(10) unsigned NOT NULL AUTO_INCREMENT, "
                        + " `LABEL` varchar(200) COLLATE utf8_bin NOT NULL, "
                        + " `UNIT_ID` int (10) unsigned DEFAULT NULL, "
                        + " `LABEL_ID` int (10) unsigned DEFAULT NULL, "
                        + " `DIMENSION_ID` int (10) unsigned DEFAULT NULL, "
                        + " PRIMARY KEY (`ID`) "
                        + " ) ENGINE = InnoDB DEFAULT CHARSET = utf8 COLLATE = utf8_bin";
                this.jdbcTemplate.execute(sql);
                logger.info("Successfully created tmp_unit");

                sql = "ALTER TABLE  `tmp_unit` ADD UNIQUE INDEX `index2` (`LABEL` ASC)";
                this.jdbcTemplate.execute(sql);

                sql = "UPDATE tmp_product_catalog pc SET pc.BaseUnit = 'Bag' WHERE pc.BaseUnit='BAG'";
                logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
                sql = "UPDATE tmp_product_catalog pc SET pc.OrderUOM = 'Bag' WHERE pc.OrderUOM='BAG'";
                logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
                sql = "UPDATE tmp_product_catalog pc SET pc.WeightUOM = 'Bag' WHERE pc.WeightUOM='BAG'";
                logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
                sql = "UPDATE tmp_product_catalog pc SET pc.HeightUOM = 'Bag' WHERE pc.HeightUOM='BAG'";
                logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
//                sql = "UPDATE tmp_product_catalog pc SET pc.LengthUOM = 'Bag' WHERE pc.LengthUOM='BAG'";
//                logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
//                sql = "UPDATE tmp_product_catalog pc SET pc.WidthUOM = 'Bag' WHERE pc.WidthUOM='BAG'";
//                logger.info(sql + " -> " + this.jdbcTemplate.update(sql));

                sql = "UPDATE tmp_product_catalog pc SET pc.BaseUnit = 'Bottle' WHERE pc.BaseUnit='BOT'";
                logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
                sql = "UPDATE tmp_product_catalog pc SET pc.OrderUOM = 'Bottle' WHERE pc.OrderUOM='BOT'";
                logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
                sql = "UPDATE tmp_product_catalog pc SET pc.WeightUOM = 'Bottle' WHERE pc.WeightUOM='BOT'";
                logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
                sql = "UPDATE tmp_product_catalog pc SET pc.HeightUOM = 'Bottle' WHERE pc.HeightUOM='BOT'";
                logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
//                sql = "UPDATE tmp_product_catalog pc SET pc.LengthUOM = 'Bottle' WHERE pc.LengthUOM='BOT'";
//                logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
//                sql = "UPDATE tmp_product_catalog pc SET pc.WidthUOM = 'Bottle' WHERE pc.WidthUOM='BOT'";
//                logger.info(sql + " -> " + this.jdbcTemplate.update(sql));

                sql = "UPDATE tmp_product_catalog pc SET pc.BaseUnit = 'Box' WHERE pc.BaseUnit='BOX'";
                logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
                sql = "UPDATE tmp_product_catalog pc SET pc.OrderUOM = 'Box' WHERE pc.OrderUOM='BOX'";
                logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
                sql = "UPDATE tmp_product_catalog pc SET pc.WeightUOM = 'Box' WHERE pc.WeightUOM='BOX'";
                logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
                sql = "UPDATE tmp_product_catalog pc SET pc.HeightUOM = 'Box' WHERE pc.HeightUOM='BOX'";
                logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
//                sql = "UPDATE tmp_product_catalog pc SET pc.LengthUOM = 'Box' WHERE pc.LengthUOM='BOX'";
//                logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
//                sql = "UPDATE tmp_product_catalog pc SET pc.WidthUOM = 'Box' WHERE pc.WidthUOM='BOX'";
//                logger.info(sql + " -> " + this.jdbcTemplate.update(sql));

                sql = "UPDATE tmp_product_catalog pc SET pc.BaseUnit = 'Cassette' WHERE pc.BaseUnit='CAS'";
                logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
                sql = "UPDATE tmp_product_catalog pc SET pc.OrderUOM = 'Cassette' WHERE pc.OrderUOM='CAS'";
                logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
                sql = "UPDATE tmp_product_catalog pc SET pc.WeightUOM = 'Cassette' WHERE pc.WeightUOM='CAS'";
                logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
                sql = "UPDATE tmp_product_catalog pc SET pc.HeightUOM = 'Cassette' WHERE pc.HeightUOM='CAS'";
                logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
//                sql = "UPDATE tmp_product_catalog pc SET pc.LengthUOM = 'Cassette' WHERE pc.LengthUOM='CAS'";
//                logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
//                sql = "UPDATE tmp_product_catalog pc SET pc.WidthUOM = 'Cassette' WHERE pc.WidthUOM='CAS'";
//                logger.info(sql + " -> " + this.jdbcTemplate.update(sql));

                sql = "UPDATE tmp_product_catalog pc SET pc.BaseUnit = 'Piece' WHERE pc.BaseUnit='PCS'";
                logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
                sql = "UPDATE tmp_product_catalog pc SET pc.OrderUOM = 'Piece' WHERE pc.OrderUOM='PCS'";
                logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
                sql = "UPDATE tmp_product_catalog pc SET pc.WeightUOM = 'Piece' WHERE pc.WeightUOM='PCS'";
                logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
                sql = "UPDATE tmp_product_catalog pc SET pc.HeightUOM = 'Piece' WHERE pc.HeightUOM='PCS'";
                logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
//                sql = "UPDATE tmp_product_catalog pc SET pc.LengthUOM = 'Piece' WHERE pc.LengthUOM='PCS'";
//                logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
//                sql = "UPDATE tmp_product_catalog pc SET pc.WidthUOM = 'Piece' WHERE pc.WidthUOM='PCS'";
//                logger.info(sql + " -> " + this.jdbcTemplate.update(sql));

                sql = "UPDATE tmp_product_catalog pc SET pc.BaseUnit = 'Roll' WHERE pc.BaseUnit='ROL'";
                logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
                sql = "UPDATE tmp_product_catalog pc SET pc.OrderUOM = 'Roll' WHERE pc.OrderUOM='ROL'";
                logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
                sql = "UPDATE tmp_product_catalog pc SET pc.WeightUOM = 'Roll' WHERE pc.WeightUOM='ROL'";
                logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
                sql = "UPDATE tmp_product_catalog pc SET pc.HeightUOM = 'Roll' WHERE pc.HeightUOM='ROL'";
                logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
//                sql = "UPDATE tmp_product_catalog pc SET pc.LengthUOM = 'Roll' WHERE pc.LengthUOM='ROL'";
//                logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
//                sql = "UPDATE tmp_product_catalog pc SET pc.WidthUOM = 'Roll' WHERE pc.WidthUOM='ROL'";
//                logger.info(sql + " -> " + this.jdbcTemplate.update(sql));

                sql = "UPDATE tmp_product_catalog pc SET pc.BaseUnit = 'Set' WHERE pc.BaseUnit='SET'";
                logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
                sql = "UPDATE tmp_product_catalog pc SET pc.OrderUOM = 'Set' WHERE pc.OrderUOM='SET'";
                logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
                sql = "UPDATE tmp_product_catalog pc SET pc.WeightUOM = 'Set' WHERE pc.WeightUOM='SET'";
                logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
                sql = "UPDATE tmp_product_catalog pc SET pc.HeightUOM = 'Set' WHERE pc.HeightUOM='SET'";
                logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
//                sql = "UPDATE tmp_product_catalog pc SET pc.LengthUOM = 'Set' WHERE pc.LengthUOM='SET'";
//                logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
//                sql = "UPDATE tmp_product_catalog pc SET pc.WidthUOM = 'Set' WHERE pc.WidthUOM='SET'";
//                logger.info(sql + " -> " + this.jdbcTemplate.update(sql));

                sql = "UPDATE tmp_product_catalog pc SET pc.BaseUnit = 'Tube' WHERE pc.BaseUnit='TUB'";
                logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
                sql = "UPDATE tmp_product_catalog pc SET pc.OrderUOM = 'Tube' WHERE pc.OrderUOM='TUB'";
                logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
                sql = "UPDATE tmp_product_catalog pc SET pc.WeightUOM = 'Tube' WHERE pc.WeightUOM='TUB'";
                logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
                sql = "UPDATE tmp_product_catalog pc SET pc.HeightUOM = 'Tube' WHERE pc.HeightUOM='TUB'";
                logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
//                sql = "UPDATE tmp_product_catalog pc SET pc.LengthUOM = 'Tube' WHERE pc.LengthUOM='TUB'";
//                logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
//                sql = "UPDATE tmp_product_catalog pc SET pc.WidthUOM = 'Tube' WHERE pc.WidthUOM='TUB'";
//                logger.info(sql + " -> " + this.jdbcTemplate.update(sql));

                sql = "UPDATE tmp_product_catalog pc SET pc.BaseUnit = 'Unit' WHERE pc.BaseUnit='UNT'";
                logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
                sql = "UPDATE tmp_product_catalog pc SET pc.OrderUOM = 'Unit' WHERE pc.OrderUOM='UNT'";
                logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
                sql = "UPDATE tmp_product_catalog pc SET pc.WeightUOM = 'Unit' WHERE pc.WeightUOM='UNT'";
                logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
                sql = "UPDATE tmp_product_catalog pc SET pc.HeightUOM = 'Unit' WHERE pc.HeightUOM='UNT'";
                logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
//                sql = "UPDATE tmp_product_catalog pc SET pc.LengthUOM = 'Unit' WHERE pc.LengthUOM='UNT'";
//                logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
//                sql = "UPDATE tmp_product_catalog pc SET pc.WidthUOM = 'Unit' WHERE pc.WidthUOM='UNT'";
//                logger.info(sql + " -> " + this.jdbcTemplate.update(sql));

                sql = "INSERT IGNORE INTO tmp_unit SELECT NULL, BaseUnit, NULL, NULL, NULL FROM tmp_product_catalog WHERE BaseUnit IS NOT NULL AND BaseUnit != '' GROUP BY BaseUnit";
                logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
                sql = "INSERT IGNORE INTO tmp_unit SELECT NULL, OrderUOM, NULL, NULL, NULL FROM tmp_product_catalog WHERE OrderUOM IS NOT NULL AND OrderUOM != '' GROUP BY OrderUOM";
                logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
                sql = "INSERT IGNORE INTO tmp_unit SELECT NULL, WeightUOM, NULL, NULL, NULL FROM tmp_product_catalog WHERE WeightUOM IS NOT NULL AND WeightUOM != '' GROUP BY WeightUOM";
                logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
                sql = "INSERT IGNORE INTO tmp_unit SELECT NULL, HeightUOM, NULL, NULL, NULL FROM tmp_product_catalog WHERE HeightUOM IS NOT NULL AND HeightUOM != '' GROUP BY HeightUOM";
                logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
//                sql = "INSERT IGNORE INTO tmp_unit SELECT NULL, LengthUOM, NULL, NULL, NULL FROM tmp_product_catalog WHERE LengthUOM IS NOT NULL AND LengthUOM != '' GROUP BY LengthUOM";
//                logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
//                sql = "INSERT IGNORE INTO tmp_unit SELECT NULL, WidthUOM, NULL, NULL, NULL FROM tmp_product_catalog WHERE WidthUOM IS NOT NULL AND WidthUOM != '' GROUP BY WidthUOM";
//                logger.info(sql + " -> " + this.jdbcTemplate.update(sql));

                sql = "SELECT COUNT(*) FROM tmp_unit;";
                logger.info("Total rows inserted in tmp_unit---" + this.jdbcTemplate.queryForObject(sql, Integer.class));

                sql = "UPDATE ap_unit au LEFT JOIN ap_label al ON au.LABEL_ID = al.LABEL_ID LEFT JOIN tmp_unit tu ON al.LABEL_EN = tu.LABEL "
                        + "SET tu.UNIT_ID = au.UNIT_ID WHERE tu.ID IS NOT NULL";
                this.jdbcTemplate.update(sql);

                sql = "SELECT * FROM tmp_unit tu where tu.UNIT_ID IS NULL";
                List<Map<String, Object>> result = this.jdbcTemplate.queryForList(sql);
                Scanner s = new Scanner(System.in);
                boolean validData = false;
                for (Map<String, Object> u : result) {
                    validData = false;
                    do {
                        logger.info("---------------------------------------------------------------------------");
                        logger.info("Please select the Dimension for this Unit. (Only enter the number)");
                        logger.info("Possible Dimension values are Volume(1), Weight(2), Each(3), Distance(4)");
                        logger.info("Unit: " + u.get("LABEL"));
                        String input = s.nextLine();
                        if (input.equals("1") || input.equals("2") || input.equals("3") || input.equals("4")) {
                            validData = true;
                            this.jdbcTemplate.update("UPDATE tmp_unit SET DIMENSION_ID=? WHERE ID=?", input, u.get("ID"));
                        }
                    } while (!validData);
                }

                sql = "SELECT MAX(LABEL_ID) FROM ap_label";
                int max = this.jdbcTemplate.queryForObject(sql, Integer.class);

                sql = "INSERT INTO ap_label SELECT NULL, tu.LABEL, NULL, NULL, NULL, 1, NOW(), 1, NOW() FROM tmp_unit tu WHERE tu.UNIT_ID IS NULL";
                this.jdbcTemplate.update(sql);

                sql = "UPDATE tmp_unit tu LEFT JOIN ap_label l ON tu.LABEL=l.LABEL_EN AND l.LABEL_ID>" + max + " SET tu.LABEL_ID=l.LABEL_ID WHERE tu.UNIT_ID IS NULL";
                this.jdbcTemplate.update(sql);

                sql = "SELECT COUNT(*) FROM ap_unit;";
                logger.info("Total rows available in ap_unit---" + this.jdbcTemplate.queryForObject(sql, Integer.class));

                sql = "INSERT INTO ap_unit SELECT  null, tu.DIMENSION_ID, tu.LABEL_ID, tu.LABEL, true, 1, now(), 1, now() from tmp_unit tu where tu.UNIT_ID is null";
                this.jdbcTemplate.update(sql);

                sql = "SELECT COUNT(*) FROM ap_unit;";
                logger.info("Total new rows inserted in ap_unit---" + this.jdbcTemplate.queryForObject(sql, Integer.class));

                sql = "SELECT COUNT(*) FROM tmp_product_catalog;";
                logger.info("Total rows inserted in tmp_unit---" + this.jdbcTemplate.queryForObject(sql, Integer.class));

                // ---------------------Tracer Category------------------
                max = 0;

                // Step 1 - Drop the table if it exists
                String sqlString = "DROP TEMPORARY TABLE IF EXISTS `tmp_tracer_category`";
                this.jdbcTemplate.update(sqlString);

                // Step 2 - Create the tmp table
                sqlString = "CREATE TEMPORARY TABLE `tmp_tracer_category` ( "
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
                        + "FROM tmp_product_catalog WHERE TracerCategory IS NOT NULL AND TracerCategory != '' GROUP BY TracerCategory";
                int rows = this.jdbcTemplate.update(sqlString);
                logger.info(rows + " inserted into the tmp_tracer_category table");

                // Step 4 Match those records that are already present in the main tracer_category table
                sqlString = "UPDATE rm_tracer_category tc "
                        + "LEFT JOIN ap_label tcl ON tc.LABEL_ID=tcl.LABEL_ID "
                        + "LEFT JOIN tmp_tracer_category ttc ON tcl.LABEL_EN=ttc.LABEL "
                        + "SET ttc.TRACER_CATEGORY_ID=tc.TRACER_CATEGORY_ID "
                        + "WHERE ttc.ID IS NOT NULL";

                rows = this.jdbcTemplate.update(sqlString);
                logger.info(rows + " existing labels found");

                // Step 5 Get the max count on the label table so that you can now work on data that you insert from here on
                sqlString = "SELECT MAX(LABEL_ID) m FROM ap_label";

                max = this.jdbcTemplate.queryForObject(sqlString, Integer.class
                );
                logger.info(max + " is the current Max count for ap_label");

                // Step 6 Insert the rows that you do not have in tracer already into the label table
                sqlString = "INSERT INTO ap_label SELECT NULL, ttc.LABEL, NULL, NULL, NULL, 1, NOW(), 1, NOW() "
                        + "FROM tmp_tracer_category ttc "
                        + "WHERE ttc.TRACER_CATEGORY_ID IS NULL AND ttc.`LABEL` !=''";

                rows = this.jdbcTemplate.update(sqlString);
                logger.info(rows + " rows inserted into the ap_label table");

                // Step 7 Now find the LABEL_ID for the rows that you just inserted and update your tmp table with those
                sqlString = "UPDATE tmp_tracer_category ttc "
                        + "LEFT JOIN ap_label l ON ttc.LABEL=l.LABEL_EN "
                        + "AND l.LABEL_ID>? "
                        + "SET ttc.LABEL_ID=l.LABEL_ID "
                        + "WHERE ttc.TRACER_CATEGORY_ID IS NULL";

                rows = this.jdbcTemplate.update(sqlString, max);
                logger.info(rows + " rows matched and LABEL_ID updated with new LABEL_ID");

                // Step 8 Finally take the Labels and 
                sql = "select count(*) from rm_tracer_category;";
                logger.info("Rows available in rm_tracer_category---" + this.jdbcTemplate.queryForObject(sql, Integer.class));

                sqlString = "INSERT INTO rm_tracer_category "
                        + "SELECT NULL, 1, ttc.LABEL_ID, 1, 1, NOW(), 1, NOW() "
                        + "FROM tmp_tracer_category ttc "
                        + "WHERE ttc.TRACER_CATEGORY_ID IS NULL "
                        + "AND ttc.LABEL_ID IS NOT NULL";

                rows = this.jdbcTemplate.update(sqlString);
                logger.info(rows + " new rows inserted into the rm_tracer_category table");

                sql = "select count(*) from rm_tracer_category;";
                logger.info("Rows available in rm_tracer_category after insert---" + this.jdbcTemplate.queryForObject(sql, Integer.class));

                //------------Forcasting Unit--------------------------
                max = 0;

                // Step 1 - Drop the table if it exists
//                sqlString = "DROP TEMPORARY TABLE IF EXISTS `tmp_label`";
                sqlString = "DROP TABLE IF EXISTS `tmp_label`";
                this.jdbcTemplate.update(sqlString);
//                sqlString = "DROP TEMPORARY TABLE IF EXISTS `tmp_forecasting_unit`";
                sqlString = "DROP TABLE IF EXISTS `tmp_forecasting_unit`";
                this.jdbcTemplate.update(sqlString);

                // Step 2 - Create the tmp table
                sqlString = "CREATE  TABLE `tmp_label` (  "
                        + "	`ID` int(10) unsigned NOT NULL AUTO_INCREMENT,  "
                        + "    `LABEL` varchar(200) COLLATE utf8_bin NOT NULL,  "
                        + "    `LABEL_ID` int (10) unsigned DEFAULT NULL,  "
                        + "    `FOUND` tinyint(1) unsigned DEFAULT 0 NOT NULL, "
                        + "    PRIMARY KEY (`ID`)  "
                        + ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin";
                this.jdbcTemplate.update(sqlString);

                // Foreign keys dont work with temporary tables
//        sqlString = "ALTER TABLE  `tmp_label`  "
//                + "ADD INDEX `tmpLabel_label` (`LABEL` ASC), "
//                + "ADD INDEX `fk_tmpLabel_labelIdIdx` (`LABEL_ID` ASC), "
//                + "ADD CONSTRAINT `fk_tmpLabel_labelId` "
//                + "  FOREIGN KEY (`LABEL_ID`) "
//                + "  REFERENCES  `ap_label` (`LABEL_ID`) "
//                + "  ON DELETE NO ACTION "
//                + "  ON UPDATE NO ACTION;";
//        this.jdbcTemplate.update(sqlString);
                sqlString = "ALTER TABLE  `tmp_label`  "
                        + "ADD INDEX `tmpLabel_label` (`LABEL` ASC), "
                        + "ADD INDEX `fk_tmpLabel_labelIdIdx` (`LABEL_ID` ASC)";
                this.jdbcTemplate.update(sqlString);

                sqlString = "CREATE TABLE `tmp_forecasting_unit` (  "
                        + "	`ID` int(10) unsigned NOT NULL AUTO_INCREMENT,  "
                        + "    `LABEL` varchar(200) COLLATE utf8_bin NOT NULL,  "
                        + "    `LABEL_ID` int (10) unsigned DEFAULT NULL,  "
                        + "    `GENERIC_LABEL`varchar(200) COLLATE utf8_bin NULL,  "
                        + "    `GENERIC_LABEL_ID` int (10) unsigned DEFAULT NULL,  "
                        + "	`UNIT_ID` int (10) unsigned DEFAULT NULL, "
                        + "    `PRODUCT_CATEGORY_ID` int (10) unsigned default null, "
                        + "    `TRACER_CATEGORY_ID` int (10) unsigned default null, "
                        + "    PRIMARY KEY (`ID`)  "
                        + ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin";
                this.jdbcTemplate.update(sqlString);

                sqlString = "ALTER TABLE  `tmp_forecasting_unit`  "
                        + "ADD INDEX `idxLabel` (`LABEL` ASC), "
                        + "ADD INDEX `idxGenericLabel` (`GENERIC_LABEL` ASC), "
                        + "ADD INDEX `fk_labelId_idx` (`LABEL_ID` ASC), "
                        + "ADD INDEX `fk_unitId_idx` (`UNIT_ID` ASC), "
                        + "ADD INDEX `fk_genericLabelId_idx` (`GENERIC_LABEL_ID` ASC), "
                        + "ADD INDEX `fk_productCategoryId_idx` (`PRODUCT_CATEGORY_ID` ASC), "
                        + "ADD INDEX `fk_tracerCategoryId_idx` (`TRACER_CATEGORY_ID` ASC)";
                this.jdbcTemplate.update(sqlString);
//        sqlString = "ALTER TABLE  `tmp_forecasting_unit`  "
//                + "ADD CONSTRAINT `fk_labelId` "
//                + "  FOREIGN KEY (`LABEL_ID`) "
//                + "  REFERENCES  `ap_label` (`LABEL_ID`) "
//                + "  ON DELETE NO ACTION "
//                + "  ON UPDATE NO ACTION, "
//                + "ADD CONSTRAINT `fk_unitId` "
//                + "  FOREIGN KEY (`UNIT_ID`) "
//                + "  REFERENCES  `ap_unit` (`UNIT_ID`) "
//                + "  ON DELETE NO ACTION "
//                + "  ON UPDATE NO ACTION, "
//                + "ADD CONSTRAINT `fk_genericLabelId` "
//                + "  FOREIGN KEY (`GENERIC_LABEL_ID`) "
//                + "  REFERENCES  `ap_label` (`LABEL_ID`) "
//                + "  ON DELETE NO ACTION "
//                + "  ON UPDATE NO ACTION, "
//                + "ADD CONSTRAINT `fk_productCategoryId` "
//                + "  FOREIGN KEY (`PRODUCT_CATEGORY_ID`) "
//                + "  REFERENCES  `rm_product_category` (`PRODUCT_CATEGORY_ID`) "
//                + "  ON DELETE NO ACTION "
//                + "  ON UPDATE NO ACTION, "
//                + "ADD CONSTRAINT `fk_tracerCategoryId` "
//                + "  FOREIGN KEY (`TRACER_CATEGORY_ID`) "
//                + "  REFERENCES  `rm_tracer_category` (`TRACER_CATEGORY_ID`) "
//                + "  ON DELETE NO ACTION "
//                + "  ON UPDATE NO ACTION";
//        this.jdbcTemplate.update(sqlString);

                // Step 3 insert into the tmp_label the ProductNameNoPack
                sqlString = "insert into tmp_label SELECT null, ProductNameNoPack, null, 0 FROM tmp_product_catalog tpc WHERE tpc.ProductNameNoPack IS NOT NULL AND tpc.ProductNameNoPack != '' group by tpc.ProductNameNoPack";
                rows = this.jdbcTemplate.update(sqlString);
                logger.info(rows + " inserted into the tmp_label for ProductNameNoPack");

                // Step 4 Match those records that are already present in the main forecasting_unit table
                sqlString = "update tmp_label tl LEFT JOIN (SELECT ful.LABEL_ID, ful.LABEL_EN FROM rm_forecasting_unit fu LEFT JOIN ap_label ful on fu.LABEL_ID=ful.LABEL_ID) as fuld ON tl.LABEL=fuld.LABEL_EN  "
                        + "set tl.LABEL_ID = fuld.LABEL_ID, tl.FOUND=1 "
                        + "where fuld.LABEL_ID is not null";
                rows = this.jdbcTemplate.update(sqlString);
                logger.info(rows + " existing labels found");

                // Step 5 Get the max count on the label table so that you can now work on data that you insert from here on
                sqlString = "SELECT MAX(LABEL_ID) m FROM ap_label";
                max = this.jdbcTemplate.queryForObject(sqlString, Integer.class);
                logger.info(max + " is the current Max count for ap_label");

                // Step 6 Insert the rows that you do not have in label table
                sqlString = "INSERT INTO ap_label select null, tl.LABEL, null, null, null, 1, now(), 1, now() from tmp_label tl where tl.FOUND=false";
                rows = this.jdbcTemplate.update(sqlString);
                logger.info(rows + " rows inserted into the ap_label table");

                // Step 7 Now find the LABEL_ID for the rows that you just inserted and update your tmp table with those
                sqlString = "update tmp_label tl LEFT JOIN ap_label ful on ful.LABEL_EN=tl.LABEL and ful.LABEL_ID>? set tl.LABEL_ID = ful.LABEL_ID where ful.LABEL_ID is not null";
                rows = this.jdbcTemplate.update(sqlString, max);
                logger.info(rows + " rows matched and LABEL_ID updated with new LABEL_ID");

                // Step 8 - Insert into tmp_froecasting those rows that have not been found 
                sqlString = "INSERT INTO tmp_forecasting_unit SELECT null, tl.LABEL, tl.LABEL_ID, null, null, null, null, null  from tmp_label tl where tl.FOUND=0";
                rows = this.jdbcTemplate.update(sqlString);
                logger.info(rows + " rows inserted into tmp_forecasting_unit");

                // Step 9 - Get the ProductCategory, TracerCategory, Unit and Generic matching ID's
                sqlString = "update tmp_forecasting_unit tfu  "
                        + "LEFT JOIN tmp_product_catalog tpc ON tfu.LABEL=tpc.ProductNameNoPack  "
                        + "set  "
                        + "tfu.GENERIC_LABEL=tpc.INN";
                rows = this.jdbcTemplate.update(sqlString);
                logger.info(rows + " matching Generic labels updated");

                sqlString = "update tmp_forecasting_unit tfu LEFT JOIN tmp_product_catalog tpc ON tfu.LABEL=tpc.ProductNameNoPack   "
                        + "LEFT JOIN (SELECT u.*, ul.LABEL_EN FROM ap_unit u LEFT JOIN ap_label ul ON u.LABEL_ID=ul.LABEL_ID) tud ON tpc.BaseUnit=tud.LABEL_EN "
                        + "set  "
                        + "tfu.UNIT_ID=tud.UNIT_ID";
                rows = this.jdbcTemplate.update(sqlString);
                logger.info(rows + " matching Unit Id's updated");

                sqlString = "update tmp_forecasting_unit tfu LEFT JOIN tmp_product_catalog tpc ON tfu.LABEL=tpc.ProductNameNoPack  "
                        + "LEFT JOIN (SELECT pc.PRODUCT_CATEGORY_ID, pcl.LABEL_EN FROM rm_product_category pc LEFT JOIN ap_label pcl ON pc.LABEL_ID=pcl.LABEL_ID) tpcd ON COALESCE(tpc.Subcategory, tpc.CommodityCouncil)=tpcd.LABEL_EN "
                        + "set  "
                        + "tfu.PRODUCT_CATEGORY_ID=tpcd.PRODUCT_CATEGORY_ID";
                rows = this.jdbcTemplate.update(sqlString);
                logger.info(rows + " matching Product Categories updated");

                sqlString = "update tmp_forecasting_unit tfu LEFT JOIN tmp_product_catalog tpc ON tfu.LABEL=tpc.ProductNameNoPack  "
                        + "LEFT JOIN (SELECT tc.TRACER_CATEGORY_ID, tcl.LABEL_EN FROM rm_tracer_category tc LEFT JOIN ap_label tcl ON tc.LABEL_ID=tcl.LABEL_ID) ttcd ON tpc.TracerCategory=ttcd.LABEL_EN "
                        + "set  "
                        + "tfu.TRACER_CATEGORY_ID=ttcd.TRACER_CATEGORY_ID";
                rows = this.jdbcTemplate.update(sqlString);
                logger.info(rows + " matching Tracer Categories updated");

                // Step 10 Complete the Missing Generic labels
                sqlString = "truncate table tmp_label";
                this.jdbcTemplate.update(sqlString);
// truncate commiting the transaction start
//        sqlString = "DROP TEMPORARY TABLE IF EXISTS `tmp_label`";
//        this.jdbcTemplate.update(sqlString);
//
//        sqlString = "CREATE TEMPORARY TABLE `tmp_label` (  "
//                + "	`ID` int(10) unsigned NOT NULL AUTO_INCREMENT,  "
//                + "    `LABEL` varchar(200) COLLATE utf8_bin NOT NULL,  "
//                + "    `LABEL_ID` int (10) unsigned DEFAULT NULL,  "
//                + "    `FOUND` tinyint(1) unsigned DEFAULT 0 NOT NULL, "
//                + "    PRIMARY KEY (`ID`)  "
//                + ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin";
//        this.jdbcTemplate.update(sqlString);
//
//        sqlString = "ALTER TABLE  `tmp_label`  "
//                + "ADD INDEX `tmpLabel_label` (`LABEL` ASC), "
//                + "ADD INDEX `fk_tmpLabel_labelIdIdx` (`LABEL_ID` ASC)";
//        this.jdbcTemplate.update(sqlString);
// end

                // Step 11 Insert Unique Genric labels into the tmp_label table
                sqlString = "insert into tmp_label SELECT null, tfu.GENERIC_LABEL, null, 0 from tmp_forecasting_unit tfu WHERE tfu.GENERIC_LABEL IS NOT NULL and tfu.GENERIC_LABEL != '' group by tfu.GENERIC_LABEL";
                rows = this.jdbcTemplate.update(sqlString);
                logger.info(rows + " unique Generic names put into tmp_label");

                // Step 12 Get the max count on the label table so that you can now work on data that you insert from here on
                sqlString = "SELECT MAX(LABEL_ID) m FROM ap_label";
                max = this.jdbcTemplate.queryForObject(sqlString, Integer.class);
                logger.info(max + " is the current Max count for ap_label");

                // Step 13 Insert the labels into ap_label
                sqlString = "INSERT INTO ap_label select null, tl.LABEL, null, null, null, 1, now(), 1, now() from tmp_label tl where tl.FOUND=false";
                rows = this.jdbcTemplate.update(sqlString);
                logger.info(rows + " rows inserted into ap_label");

                // Step 14 Now find the LABEL_ID for the rows that you just inserted and update your tmp table with those
                sqlString = "update tmp_label tl LEFT JOIN ap_label ful on ful.LABEL_EN=tl.LABEL and ful.LABEL_ID>? set tl.LABEL_ID = ful.LABEL_ID where ful.LABEL_ID is not null;";
                rows = this.jdbcTemplate.update(sqlString, max);
                logger.info(rows + " rows matched and LABEL_ID updated with new LABEL_ID");

                // Step 15 Update the Generic Label with the new ones inserted
                sqlString = "update tmp_forecasting_unit tfu left join tmp_label tl ON tfu.GENERIC_LABEL=tl.LABEL SET tfu.GENERIC_LABEL_ID=tl.LABEL_ID";
                rows = this.jdbcTemplate.update(sqlString);
                logger.info(rows + " rows updated with Generic Label Id");

                sqlString = "select count(*) from rm_forecasting_unit;";
                logger.info("Rows available in rm_forecasting_unit---" + this.jdbcTemplate.queryForObject(sql, Integer.class));

                // Step 16 Now insert all the data into ForecastingUnit
//                sqlString = "INSERT INTO rm_forecasting_unit select null, 1, tfu.PRODUCT_CATEGORY_ID, tfu.TRACER_CATEGORY_ID, tfu.GENERIC_LABEL_ID, tfu.LABEL_ID, tfu.UNIT_ID, 1, 1, now(), 1, now() from tmp_forecasting_unit tfu";
                sqlString = "INSERT INTO rm_forecasting_unit select null, 1, 1, tfu.TRACER_CATEGORY_ID, tfu.GENERIC_LABEL_ID, tfu.LABEL_ID, tfu.UNIT_ID, 1, 1, now(), 1, now() from tmp_forecasting_unit tfu";
                rows = this.jdbcTemplate.update(sqlString);
                logger.info(rows + " Inserted into the Forecasting Unit table");

                sqlString = "select count(*) from rm_forecasting_unit;";
                logger.info("Total Rows after insertion rm_forecasting_unit---" + this.jdbcTemplate.queryForObject(sql, Integer.class));

                // -----------------------Planning Unit-----------------
                sqlString = "DROP TABLE IF EXISTS tmp_planning_unit";
                this.jdbcTemplate.update(sqlString);

                sqlString = "CREATE TABLE `tmp_planning_unit` (  "
                        + "	`ID` int(10) unsigned NOT NULL AUTO_INCREMENT,  "
                        + "    `LABEL` varchar(200) COLLATE utf8_bin NOT NULL,  "
                        + "    `LABEL_ID` int (10) unsigned DEFAULT NULL,  "
                        + "    `MULTIPLIER` double (20,2) UNSIGNED DEFAULT null, "
                        + " 	`UNIT_ID` int (10) unsigned DEFAULT NULL, "
                        + "	`FORECASTING_UNIT_ID` int (10) unsigned DEFAULT NULL, "
                        + "     `SKU_CODE` VARCHAR(100) NULL, "
                        + "    PRIMARY KEY (`ID`)  "
                        + ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin";
                this.jdbcTemplate.update(sqlString);

                sqlString = "ALTER TABLE  `tmp_planning_unit`  "
                        + "ADD INDEX `tmpPlanningUnit_label` (`LABEL` ASC), "
                        + "ADD INDEX `fk_tmp_planning_unit_unitId_idx` (`UNIT_ID` ASC), "
                        + "ADD INDEX `fk_tmp_planning_unit_forecastingUnit_idx` (`FORECASTING_UNIT_ID` ASC), "
                        + "ADD INDEX `fk_tmp_planning_unit_labelId_idx` (`LABEL_ID` ASC)";
                this.jdbcTemplate.update(sqlString);
                sqlString = "ALTER TABLE  `tmp_planning_unit`  "
                        + "ADD CONSTRAINT `fk_tmp_planning_unit_unitId` "
                        + "  FOREIGN KEY (`UNIT_ID`) "
                        + "  REFERENCES  `ap_unit` (`UNIT_ID`) "
                        + "  ON DELETE NO ACTION "
                        + "  ON UPDATE NO ACTION, "
                        + "ADD CONSTRAINT `fk_tmp_planning_unit_forecastingUnit` "
                        + "  FOREIGN KEY (`FORECASTING_UNIT_ID`) "
                        + "  REFERENCES  `rm_forecasting_unit` (`FORECASTING_UNIT_ID`) "
                        + "  ON DELETE NO ACTION "
                        + "  ON UPDATE NO ACTION, "
                        + "ADD CONSTRAINT `fk_tmp_planning_unit_labelId` "
                        + "  FOREIGN KEY (`LABEL_ID`) "
                        + "  REFERENCES  `ap_label` (`LABEL_ID`) "
                        + "  ON DELETE NO ACTION "
                        + "  ON UPDATE NO ACTION";
//        this.jdbcTemplate.update(sqlString);

                sqlString = "truncate table tmp_label";
                this.jdbcTemplate.update(sqlString);

                sqlString = "ALTER TABLE `tmp_label` ADD COLUMN `PRODUCT_ID` VARCHAR(100) NOT NULL AFTER `FOUND`, ADD INDEX `idx_tmpLabel_productId` (`PRODUCT_ID` ASC)";
                this.jdbcTemplate.update(sqlString);

                // Step 1 Insert the PlanningUnit name into the tmpTable
                sqlString = "insert into tmp_label SELECT null, ProductName, null, 0, ProductID FROM tmp_product_catalog tpc WHERE tpc.ProductName IS NOT NULL AND tpc.ProductName != '' group by tpc.ProductName";
                rows = this.jdbcTemplate.update(sqlString);
                logger.info(rows + " labels instered into the tmp label table");

                // Step 2 Find the matching labels that are already there
                sqlString = "update tmp_label tl LEFT JOIN (SELECT pul.LABEL_ID, pul.LABEL_EN FROM rm_planning_unit pu LEFT JOIN ap_label pul on pu.LABEL_ID=pul.LABEL_ID) as puld ON tl.LABEL=puld.LABEL_EN  "
                        + "set tl.LABEL_ID = puld.LABEL_ID, tl.FOUND=1 "
                        + "where puld.LABEL_ID is not null";
                rows = this.jdbcTemplate.update(sqlString);
                logger.info(rows + " labels are already present");

                // Step 3 Get the max count on the label table so that you can now work on data that you insert from here on
                sqlString = "SELECT MAX(LABEL_ID) m FROM ap_label";
                max = this.jdbcTemplate.queryForObject(sqlString, Integer.class);
                logger.info(max + " is the current Max count for ap_label");

                // Step 4 Insert the rows that you do not have in label table
                sqlString = "INSERT INTO ap_label select null, tl.LABEL, null, null, null, 1, now(), 1, now() from tmp_label tl where tl.FOUND=false";
                rows = this.jdbcTemplate.update(sqlString);
                logger.info(rows + " rows inserted into the ap_label table");

                // Step 5 Now find the LABEL_ID for the rows that you just inserted and update your tmp table with those
                sqlString = "update tmp_label tl LEFT JOIN ap_label pul on pul.LABEL_EN=tl.LABEL and pul.LABEL_ID>? set tl.LABEL_ID = pul.LABEL_ID where pul.LABEL_ID is not null";
                rows = this.jdbcTemplate.update(sqlString, max);
                logger.info(rows + " rows matched and LABEL_ID updated with new LABEL_ID");

                // Step 6 Insert into tmp planning unit 
                sqlString = "INSERT INTO tmp_planning_unit SELECT null, tl.LABEL, tl.LABEL_ID, 1, null, null, tl.PRODUCT_ID from tmp_label tl where tl.FOUND=0";
                rows = this.jdbcTemplate.update(sqlString);
                logger.info(rows + " rows inserted into tmpPlanningUnit");

                // Step 7 Update the ForecastingUnit and Unit
                sqlString = "update tmp_planning_unit tpu  "
                        + "LEFT JOIN tmp_product_catalog tpc ON tpu.LABEL=tpc.ProductName "
                        + "LEFT JOIN (SELECT u.*, ul.LABEL_EN FROM ap_unit u LEFT JOIN ap_label ul ON u.LABEL_ID=ul.LABEL_ID) tud ON tpc.OrderUOM=tud.LABEL_EN "
                        + "set tpu.UNIT_ID=tud.UNIT_ID, tpu.MULTIPLIER=IF(tpc.NoofBaseUnits != '',tpc.NoofBaseUnits,0)";
                rows = this.jdbcTemplate.update(sqlString);
                logger.info(rows + " UnitId's updated in tmpPlanningUnit");

                sqlString = "update tmp_planning_unit tpu  "
                        + "LEFT JOIN tmp_product_catalog tpc ON tpu.LABEL=tpc.ProductName "
                        + "LEFT JOIN (SELECT fu.*, ful.LABEL_EN FROM rm_forecasting_unit fu LEFT JOIN ap_label ful ON fu.LABEL_ID=ful.LABEL_ID) tfud ON tpc.ProductNameNoPack=tfud.LABEL_EN "
                        + "set tpu.FORECASTING_UNIT_ID=tfud.FORECASTING_UNIT_ID";
                rows = this.jdbcTemplate.update(sqlString);
                logger.info(rows + " ForecastingUnits updated in tmpPlanningUnit");

                sqlString = "select count(*) from rm_planning_unit;";
                logger.info("No of rows before insertion in rm_planning_unit --" + this.jdbcTemplate.queryForObject(sqlString, Integer.class));

                // Forcasting unit id is null so putting dummy value 1
                // Step 8 Insert into the main planning Unit
                sqlString = "insert into rm_planning_unit select null, tpu.FORECASTING_UNIT_ID, tpu.LABEL_ID, tpu.UNIT_ID, tpu.MULTIPLIER, 1, 1, now(), 1, now() from tmp_planning_unit tpu";
//                sqlString = "insert into rm_planning_unit select null, 1, tpu.LABEL_ID, tpu.UNIT_ID, tpu.MULTIPLIER, 1, 1, now(), 1, now() from tmp_planning_unit tpu";
                rows = this.jdbcTemplate.update(sqlString);
                logger.info(rows + " inserted into Planning unit");

                sqlString = "select count(*) from rm_planning_unit;";
                logger.info("No of rows after insertion in rm_planning_unit --" + this.jdbcTemplate.queryForObject(sqlString, Integer.class));

                sqlString = "ALTER TABLE `tmp_label` DROP COLUMN `PRODUCT_ID`, DROP INDEX `idx_tmpLabel_productId`";
                this.jdbcTemplate.update(sqlString);

                // Now for Procurment Agent Planning Unit
                sqlString = "drop TEMPORARY table IF EXISTS `tmp_procurement_agent_planning_unit`";
                this.jdbcTemplate.update(sqlString);

                sqlString = "CREATE TEMPORARY TABLE `tmp_procurement_agent_planning_unit` (  "
                        + "	`ID` int(10) unsigned NOT NULL AUTO_INCREMENT,  "
                        + "    `PLANNING_UNIT_ID` int(10) UNSIGNED not null, "
                        + "    `SKU_CODE` VARCHAR(50) null, "
                        + "    `EST_PRICE` varchar(20) null, "
                        + "    `MOQ` VARCHAR(20) null, "
                        + "    `UNITS_PER_PALLET` VARCHAR(20) null, "
                        + "    `UNITS_PER_CONTAINER` VARCHAR(20) null, "
                        + "    `VOLUME` VARCHAR(20) null, "
                        + "    `WEIGHT` VARCHAR(20) null, "
                        + "    PRIMARY KEY (`ID`)  "
                        + ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin";
                this.jdbcTemplate.update(sqlString);

                sqlString = "insert into tmp_procurement_agent_planning_unit select null, pu.PLANNING_UNIT_ID, null, null, null, null, null, null, null from rm_planning_unit pu";
                rows = this.jdbcTemplate.update(sqlString);
                logger.info(rows + " rowsinstered into the tmpProcurementAgentPlanningUnit table");

                sqlString = "update tmp_procurement_agent_planning_unit tpapu LEFT JOIN rm_planning_unit pu ON tpapu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID LEFT JOIN ap_label pul ON pu.LABEL_ID=pul.LABEL_ID LEFT JOIN tmp_product_catalog tpc ON pul.LABEL_EN=tpc.ProductName "
                        + "SET  "
                        + "	tpapu.SKU_CODE=tpc.ProductID,  "
                        + "    tpapu.EST_PRICE=if(tpc.EstPrice='', null,tpc.EstPrice),  "
                        + "    tpapu.MOQ=if(tpc.PlanningUnitMOQ='', null,tpc.PlanningUnitMOQ),  "
                        + "    tpapu.UNITS_PER_PALLET=if(tpc.PlanningUnitsperPallet='',null,tpc.PlanningUnitsperPallet),  "
                        + "    tpapu.UNITS_PER_CONTAINER=if(tpc.PlanningUnitsperContainer='',null,tpc.PlanningUnitsperContainer),  "
                        + "    tpapu.VOLUME=if(tpc.PlanningUnitVolumem3='',null,tpc.PlanningUnitVolumem3),  "
                        + "    tpapu.WEIGHT=if(tpc.PlanningUnitWeightkg='',null,tpc.PlanningUnitWeightkg)";
                rows = this.jdbcTemplate.update(sqlString);
                logger.info(rows + " rows mapped with data for planning unit");

                sqlString = "select count(*) from rm_procurement_agent_planning_unit;";
                logger.info("No of rows before insertion in rm_procurement_agent_planning_unit --" + this.jdbcTemplate.queryForObject(sql, Integer.class));

                sqlString = "insert into rm_procurement_agent_planning_unit select null, 1, tpapu.PLANNING_UNIT_ID, tpapu.SKU_CODE, tpapu.EST_PRICE, tpapu.MOQ, tpapu.UNITS_PER_PALLET, tpapu.UNITS_PER_CONTAINER, tpapu.VOLUME, tpapu.WEIGHT, 1, 1, now(), 1, now() from tmp_procurement_agent_planning_unit tpapu "
                        + "where tpapu.SKU_CODE IS NOT NULL;";
                rows = this.jdbcTemplate.update(sqlString);
                logger.info(rows + " rows inserted into procurementAgentPlanningUnit");

                sqlString = "select count(*) from rm_procurement_agent_planning_unit;";
                logger.info("No of rows after insertion in rm_procurement_agent_planning_unit --" + this.jdbcTemplate.queryForObject(sql, Integer.class));

                //------------Supplier----------------
                sql = "DROP TEMPORARY TABLE IF EXISTS `tmp_supplier`";
                this.jdbcTemplate.execute(sql);

                sql = "CREATE TEMPORARY TABLE `tmp_supplier` ( "
                        + " `ID` int(10) unsigned NOT NULL AUTO_INCREMENT, "
                        + " `LABEL` varchar(200) COLLATE utf8_bin NOT NULL, "
                        + " `SUPPLIER_ID` int (10) unsigned DEFAULT NULL, "
                        + " `LABEL_ID` int (10) unsigned DEFAULT NULL, "
                        + " PRIMARY KEY (`ID`) "
                        + " ) ENGINE = InnoDB DEFAULT CHARSET = utf8 COLLATE = utf8_bin";
                this.jdbcTemplate.execute(sql);

                sql = "ALTER TABLE  `tmp_supplier` ADD UNIQUE INDEX `index3` (`LABEL` ASC)";
                this.jdbcTemplate.execute(sql);

                sql = "INSERT IGNORE INTO tmp_supplier "
                        + "SELECT NULL, Supplier, NULL, NULL "
                        + "FROM tmp_product_catalog tpc "
                        + "WHERE Supplier IS NOT NULL AND Supplier <> '' "
                        + "GROUP BY Supplier;";

                logger.info("No of rows inserted into tmp_supplier---" + this.jdbcTemplate.update(sql));

                sql = "UPDATE rm_supplier rs "
                        + "LEFT JOIN ap_label al ON rs.`LABEL_ID` = al.`LABEL_ID` "
                        + "LEFT JOIN tmp_supplier ts ON al.`LABEL_EN` = ts.LABEL "
                        + "SET ts.SUPPLIER_ID = rs.`SUPPLIER_ID` "
                        + "WHERE ts.ID IS NOT NULL;";
                this.jdbcTemplate.update(sql);

                sql = "SELECT MAX(LABEL_ID) FROM ap_label";
                max = this.jdbcTemplate.queryForObject(sql, Integer.class);

                sql = "INSERT INTO ap_label SELECT NULL, ts.LABEL, NULL, NULL, NULL, 1, NOW(), 1, NOW() "
                        + "FROM tmp_supplier ts WHERE ts.SUPPLIER_ID IS NULL";
                this.jdbcTemplate.update(sql);

                sql = "UPDATE tmp_supplier ts LEFT JOIN ap_label l ON ts.LABEL=l.LABEL_EN AND l.LABEL_ID>"
                        + max + " SET ts.LABEL_ID=l.LABEL_ID WHERE ts.SUPPLIER_ID IS NULL";
                this.jdbcTemplate.update(sql);

                sql = "select count(*) from rm_supplier;";
                logger.info("no of rows before insertion into rm_supplier---" + this.jdbcTemplate.queryForObject(sql, Integer.class));

                sql = "INSERT INTO rm_supplier "
                        + "SELECT NULL, 1, ts.LABEL_ID, 1, 1, NOW(), 1 , NOW() "
                        + "FROM tmp_supplier ts "
                        + "WHERE ts.SUPPLIER_ID IS NULL";
                this.jdbcTemplate.update(sql);

                sql = "select count(*) from rm_supplier;";
                logger.info("no of rows after insertion into rm_supplier---" + this.jdbcTemplate.queryForObject(sql, Integer.class));

                //----------Procurement Unit---------------
                sqlString = "DROP TEMPORARY TABLE IF EXISTS tmp_procurement_unit";
                this.jdbcTemplate.update(sqlString);

                sqlString = "CREATE TEMPORARY TABLE `tmp_procurement_unit` (  "
                        + "	`ID` int(10) unsigned NOT NULL AUTO_INCREMENT,  "
                        + "    `LABEL` varchar(200) COLLATE utf8_bin NOT NULL,  "
                        + "    `LABEL_ID` int (10) unsigned DEFAULT NULL,  "
                        + "    `MULTIPLIER` double (12,2) UNSIGNED DEFAULT null, "
                        + " 	`UNIT_ID` int (10) unsigned DEFAULT NULL, "
                        + "	`PLANNING_UNIT_ID` int (10) unsigned DEFAULT NULL, "
                        + "    `SUPPLIER_ID` int (10) unsigned DEFAULT NULL, "
                        + "    `WIDTH_UNIT_ID` int (10) unsigned DEFAULT NULL, "
                        + "    `WIDTH` decimal (12,2) unsigned DEFAULT NULL, "
                        + "    `LENGTH_UNIT_ID` int (10) unsigned DEFAULT NULL, "
                        + "    `LENGTH` decimal (12,2) unsigned DEFAULT NULL, "
                        + "    `HEIGHT_UNIT_ID` int (10) unsigned DEFAULT NULL, "
                        + "    `HEIGHT` decimal (12,2) unsigned DEFAULT NULL, "
                        + "    `WEIGHT_UNIT_ID` int (10) unsigned DEFAULT NULL, "
                        + "    `WEIGHT` decimal (12,2) unsigned DEFAULT NULL, "
                        + "    `UNITS_PER_CONTAINER` int (10) unsigned DEFAULT NULL, "
                        + "    `LABELING` Varchar(200) DEFAULT NULL, "
                        + "    `SKU_CODE` Varchar(200) DEFAULT NULL, "
                        + "    `GTIN` Varchar(200) DEFAULT NULL, "
                        + "    `VENDOR_PRICE` Varchar(200) DEFAULT NULL, "
                        + "    `APPROVED_TO_SHIPPED_LEAD_TIME` DECIMAL(12,2) DEFAULT NULL, "
                        + "    `FOUND` tinyint(1) unsigned default 0 not null, "
                        + "    CONSTRAINT PRIMARY KEY (`ID`)  "
                        + ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin";
                this.jdbcTemplate.update(sqlString);

                sqlString = "ALTER TABLE  `tmp_procurement_unit`  "
                        + "ADD INDEX `idx_procurementUnit1` (`LABEL` ASC), "
                        + "ADD INDEX `idx_procurementUnit2` (`LABEL_ID` ASC), "
                        + "ADD INDEX `idx_procurementUnit3` (`UNIT_ID` ASC), "
                        + "ADD INDEX `idx_procurementUnit4` (`PLANNING_UNIT_ID` ASC), "
                        + "ADD INDEX `idx_procurementUnit5` (`WIDTH_UNIT_ID` ASC), "
                        + "ADD INDEX `idx_procurementUnit6` (`HEIGHT_UNIT_ID` ASC), "
                        + "ADD INDEX `idx_procurementUnit7` (`LENGTH_UNIT_ID` ASC), "
                        + "ADD INDEX `idx_procurementUnit8` (`WEIGHT_UNIT_ID` ASC)";
                this.jdbcTemplate.update(sqlString);

                sqlString = "ALTER TABLE  `tmp_procurement_unit`  "
                        + "ADD CONSTRAINT `fk_forecastingUnit1` "
                        + "  FOREIGN KEY (`LABEL_ID`) "
                        + "  REFERENCES  `ap_label` (`LABEL_ID`) "
                        + "  ON DELETE NO ACTION "
                        + "  ON UPDATE NO ACTION, "
                        + "ADD CONSTRAINT `fk_procurementUnit2` "
                        + "  FOREIGN KEY (`UNIT_ID`) "
                        + "  REFERENCES  `ap_unit` (`UNIT_ID`) "
                        + "  ON DELETE NO ACTION "
                        + "  ON UPDATE NO ACTION, "
                        + "ADD CONSTRAINT `fk_procurementUnit3` "
                        + "  FOREIGN KEY (`PLANNING_UNIT_ID`) "
                        + "  REFERENCES  `rm_planning_unit` (`PLANNING_UNIT_ID`) "
                        + "  ON DELETE NO ACTION "
                        + "  ON UPDATE NO ACTION, "
                        + "ADD CONSTRAINT `fk_procurementUnit4` "
                        + "  FOREIGN KEY (`SUPPLIER_ID`) "
                        + "  REFERENCES  `rm_supplier` (`SUPPLIER_ID`) "
                        + "  ON DELETE NO ACTION "
                        + "  ON UPDATE NO ACTION, "
                        + "ADD CONSTRAINT `fk_procurementUnit5` "
                        + "  FOREIGN KEY (`WIDTH_UNIT_ID`) "
                        + "  REFERENCES  `ap_unit` (`UNIT_ID`) "
                        + "  ON DELETE NO ACTION "
                        + "  ON UPDATE NO ACTION, "
                        + "  ADD CONSTRAINT `fk_procurementUnit6` "
                        + "  FOREIGN KEY (`LENGTH_UNIT_ID`) "
                        + "  REFERENCES  `ap_unit` (`UNIT_ID`) "
                        + "  ON DELETE NO ACTION "
                        + "  ON UPDATE NO ACTION, "
                        + "  ADD CONSTRAINT `fk_procurementUnit7` "
                        + "  FOREIGN KEY (`HEIGHT_UNIT_ID`) "
                        + "  REFERENCES  `ap_unit` (`UNIT_ID`) "
                        + "  ON DELETE NO ACTION "
                        + "  ON UPDATE NO ACTION, "
                        + "  ADD CONSTRAINT `fk_procurementUnit8` "
                        + "  FOREIGN KEY (`WEIGHT_UNIT_ID`) "
                        + "  REFERENCES  `ap_unit` (`UNIT_ID`) "
                        + "  ON DELETE NO ACTION "
                        + "  ON UPDATE NO ACTION";
//        this.jdbcTemplate.update(sqlString);

                sqlString = "truncate table tmp_procurement_unit";

                sqlString = "truncate table tmp_label";
                this.jdbcTemplate.update(sqlString);

                // Step 2 - Insert into tmpLabel
                sqlString = "insert into tmp_label SELECT null, ItemName, null, 0 FROM tmp_product_catalog tpc WHERE tpc.ItemName IS NOT NULL AND tpc.ItemName != '' group by tpc.ItemName";
                rows = this.jdbcTemplate.update(sqlString);
                logger.info(rows + " rows inserted into tmpLabel");

                // Step 3 - Find already existing labels
                sqlString = "update tmp_label tl LEFT JOIN (SELECT pul.LABEL_ID, pul.LABEL_EN FROM rm_procurement_unit pu LEFT JOIN ap_label pul on pu.LABEL_ID=pul.LABEL_ID) as puld ON tl.LABEL=puld.LABEL_EN  set tl.LABEL_ID = puld.LABEL_ID, tl.FOUND=1 where puld.LABEL_ID is not null";
                rows = this.jdbcTemplate.update(sqlString);
                logger.info(rows + " labels already existed");

                // Step 4 - Find max
                sqlString = "SELECT MAX(ap_label.LABEL_ID) from ap_label";
                max = this.jdbcTemplate.queryForObject(sqlString, Integer.class);
                logger.info(max + " Current max");

                // Step 5 - Insert into ap_labels
                sqlString = "INSERT INTO ap_label select null, tl.LABEL, null, null, null, 1, now(), 1, now() from tmp_label tl where tl.FOUND=false";
                rows = this.jdbcTemplate.update(sqlString);
                logger.info(rows + " rows inserted for labels");

                // Step 6 - Match the Labels inserted to find the label_id
                sqlString = "update tmp_label tl LEFT JOIN ap_label pul on pul.LABEL_EN=tl.LABEL and pul.LABEL_ID>? set tl.LABEL_ID = pul.LABEL_ID where pul.LABEL_ID is not null";
                rows = this.jdbcTemplate.update(sqlString, max);
                logger.info(rows + " rows matched with new labels");

                // Step 7 - Insert into the tmp_procurement table
                sqlString = "INSERT INTO tmp_procurement_unit SELECT null, tl.LABEL, tl.LABEL_ID, null, null, null, null, null, null, null, null, null, null, null, null, null,null, null, null, null, null, 1  from tmp_label tl where tl.FOUND=0";
                rows = this.jdbcTemplate.update(sqlString);
                logger.info(rows + " rows inserted into the tmp_procurement table");

                Scanner s1 = new Scanner(System.in);
                validData = false;
                Double approvedToShipppedLeadTime = 3.0;
//        do {
//            logger.info("---------------------------------------------------------------------------");
//            logger.info("Please enter the Approved to Shipped Lead Time (in Months) that you want to use as default for all Procurement Units.");
//            logger.info("Could be a Decimal or wirte 'null' if you want to keep it as null");
//            logger.info("Approved to Shipped Lead Time (in months): ");
//            String input = s.nextLine();
//            if (NumberUtils.isCreatable(input) || input.equals("null")) {
//                validData = true;
//                if (!input.equals("null")) {
//                    approvedToShipppedLeadTime = NumberUtils.toDouble(input);
//                }
//            }
//        } while (!validData);

                // Step 8 - match and update other data
                sqlString = "update tmp_procurement_unit tpu  "
                        + "LEFT JOIN tmp_product_catalog tpc ON tpu.LABEL=tpc.ItemName "
                        + "LEFT JOIN (SELECT u.*, ul.LABEL_EN FROM ap_unit u LEFT JOIN ap_label ul ON u.LABEL_ID=ul.LABEL_ID) tuhd ON tpc.HeightUOM=tuhd.LABEL_EN "
                        + "LEFT JOIN (SELECT u.*, ul.LABEL_EN FROM ap_unit u LEFT JOIN ap_label ul ON u.LABEL_ID=ul.LABEL_ID) tuld ON tpc.LengthUOM=tuld.LABEL_EN "
                        + "LEFT JOIN (SELECT u.*, ul.LABEL_EN FROM ap_unit u LEFT JOIN ap_label ul ON u.LABEL_ID=ul.LABEL_ID) tuwd ON tpc.WidthUOM=tuwd.LABEL_EN "
                        + "LEFT JOIN (SELECT u.*, ul.LABEL_EN FROM ap_unit u LEFT JOIN ap_label ul ON u.LABEL_ID=ul.LABEL_ID) tuwed ON tpc.WeightUOM=tuwed.LABEL_EN "
                        + "set  "
                        + "	tpu.HEIGHT=if(tpc.Height='', null, tpc.Height), "
                        + "    tpu.LENGTH=if(tpc.Length='', null, tpc.Length), "
                        + "    tpu.WIDTH=if(tpc.Width='', null, tpc.Width), "
                        + "    tpu.WEIGHT=if(tpc.Weight='', null, tpc.Weight), "
                        + "    tpu.UNITS_PER_CONTAINER=if(tpc.UnitsperContainer='', null, tpc.UnitsperContainer), "
                        + "    tpu.LABELING=if(tpc.Labeling='', null, tpc.Labeling), "
                        + "    tpu.SKU_CODE=if(tpc.ItemID='', null, tpc.ItemID), "
                        + "    tpu.GTIN=if(tpc.GTIN='', null, tpc.GTIN), "
                        + "    tpu.VENDOR_PRICE=null, "
                        + "    tpu.APPROVED_TO_SHIPPED_LEAD_TIME=" + approvedToShipppedLeadTime + ","
                        + "    tpu.HEIGHT_UNIT_ID=tuhd.UNIT_ID, "
                        + "    tpu.LENGTH_UNIT_ID=tuld.UNIT_ID, "
                        + "    tpu.WIDTH_UNIT_ID=tuwd.UNIT_ID, "
                        + "    tpu.WEIGHT_UNIT_ID=tuwed.UNIT_ID";
                rows = this.jdbcTemplate.update(sqlString);
                logger.info(rows + " rows updated with matching data from product_catalog");

                // Step 9 - Match the Planning Unit, Multiplier and UnitId
                sqlString = "update tmp_procurement_unit tpu  "
                        + "LEFT JOIN tmp_product_catalog tpc ON tpu.LABEL=tpc.ItemName "
                        + "LEFT JOIN (SELECT pu.PLANNING_UNIT_ID, pul.LABEL_EN, pu.MULTIPLIER, pu.UNIT_ID FROM rm_planning_unit pu LEFT JOIN ap_label pul ON pu.LABEL_ID=pul.LABEL_ID) tpud ON tpc.ProductName=tpud.LABEL_EN "
                        + "set tpu.PLANNING_UNIT_ID=tpud.PLANNING_UNIT_ID, tpu.MULTIPLIER=tpud.MULTIPLIER, tpu.UNIT_ID=tpud.UNIT_ID";
                this.jdbcTemplate.update(sqlString);
                logger.info(rows + " rows updated with matching data from planning_unit");

                // Step 10 - MAtch the Supplier Id
                sqlString = "update tmp_procurement_unit tpu  "
                        + "LEFT JOIN tmp_product_catalog tpc ON tpu.LABEL=tpc.ItemName "
                        + "LEFT JOIN (SELECT s.SUPPLIER_ID, sl.LABEL_EN from rm_supplier s LEFT JOIN ap_label sl ON s.LABEL_ID=sl.LABEL_ID) tsd ON tpc.Supplier=tsd.LABEL_EN "
                        + "set tpu.SUPPLIER_ID=tsd.SUPPLIER_ID";
                rows = this.jdbcTemplate.update(sqlString);
                logger.info(rows + " rows updated with matching data from supplier");

                // Step 10a - Find max count for procurment_unit
                sqlString = "SELECT IFNULL(MAX(PROCUREMENT_UNIT_ID),0) from rm_procurement_unit";
                max = this.jdbcTemplate.queryForObject(sqlString, Integer.class);
                logger.info(max + " Current max for ProcurementUnit");

                // Step 11 - Insert into the procurement_unit
                sqlString = "insert into rm_procurement_unit  "
                        + "SELECT null, tpu.PLANNING_UNIT_ID, tpu.LABEL_ID, tpu.UNIT_ID, tpu.MULTIPLIER, tpu.SUPPLIER_ID, tpu.WIDTH_UNIT_ID, tpu.WIDTH, tpu.HEIGHT_UNIT_ID, tpu.HEIGHT, tpu.LENGTH_UNIT_ID, tpu.LENGTH, tpu.WEIGHT_UNIT_ID, tpu.WEIGHT, tpu.UNITS_PER_CONTAINER, tpu.LABELING, 1, 1, now(), 1, now() from tmp_procurement_unit tpu WHERE tpu.SUPPLIER_ID is not null && tpu.PLANNING_UNIT_ID is not null AND tpu.LABEL_ID is not null";
                rows = this.jdbcTemplate.update(sqlString);
                logger.info(rows + " rows inserted into the procurement unit table");

                // Step 12 - Insert into the procurement_agent_procurement_unit
                sqlString = "insert ignore into rm_procurement_agent_procurement_unit SELECT null, pu.PROCUREMENT_UNIT_ID, 1, tpu.SKU_CODE, tpu.VENDOR_PRICE, tpu.APPROVED_TO_SHIPPED_LEAD_TIME, tpu.GTIN, 1, 1, now(), 1, now() from rm_procurement_unit pu LEFT JOIN tmp_procurement_unit tpu ON pu.LABEL_ID=tpu.LABEL_ID WHERE tpu.SKU_CODE is not null AND pu.PROCUREMENT_UNIT_ID>?";
                rows = this.jdbcTemplate.update(sqlString, max);
                logger.info(rows + " rows inserted into the procurement_agent_procurement_unit table");
                logger.info("Product catalog file imported successfully");
                File directory = new File(BKP_CATALOG_FILE_PATH);
                if (directory.isDirectory()) {
                    fXmlFile.renameTo(new File(BKP_CATALOG_FILE_PATH + fXmlFile.getName()));
                    logger.info("Product catalog file moved to processed folder successfully");
                } else {
                    subjectParam = new String[]{"Product Catalogue", "Backup directory does not exists"};
                    bodyParam = new String[]{"Product Catalogue", date, "Backup directory does not exists", "Backup directory does not exists"};
                    emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), "anchal.c@altius.cc,shubham.y@altius.cc,priti.p@altius.cc,sameer.g@altiusbpo.com", "shubham.y@altius.cc,priti.p@altius.cc,sameer.g@altiusbpo.com", subjectParam, bodyParam);
                    int emailerId = this.emailService.saveEmail(emailer);
                    emailer.setEmailerId(emailerId);
                    this.emailService.sendMail(emailer);
                    logger.error("Backup directory does not exists");
                }
//        throw new NullPointerException();
            }
        }
//        } catch (ParserConfigurationException ex) {
//            LOG.error("Error occured while reading product catalogue ParserConfigurationException---" + ex);
//        } catch (SAXException ex) {
//            LOG.error("Error occured while reading product catalogue SAXException---" + ex);
//        } catch (FileNotFoundException ex) {
//            logger.info("*******File not found at location---" + filePath);
//        } catch (IOException ex) {
//            logger.info("*******Error occured while reading file from location---" + filePath);
//            LOG.error("Error occured while reading product catalogue IOException---" + ex);
//        }
    }

    @Override
    public void pullUnitTable() {
        String sql = "DROP TEMPORARY TABLE IF EXISTS `tmp_unit`";
        this.jdbcTemplate.execute(sql);

        sql = "CREATE TEMPORARY TABLE `tmp_unit` ( "
                + " `ID` int(10) unsigned NOT NULL AUTO_INCREMENT, "
                + " `LABEL` varchar(200) COLLATE utf8_bin NOT NULL, "
                + " `UNIT_ID` int (10) unsigned DEFAULT NULL, "
                + " `LABEL_ID` int (10) unsigned DEFAULT NULL, "
                + " `DIMENSION_ID` int (10) unsigned DEFAULT NULL, "
                + " PRIMARY KEY (`ID`) "
                + " ) ENGINE = InnoDB DEFAULT CHARSET = utf8 COLLATE = utf8_bin";
        this.jdbcTemplate.execute(sql);

        sql = "ALTER TABLE  `tmp_unit` ADD UNIQUE INDEX `index2` (`LABEL` ASC)";
        this.jdbcTemplate.execute(sql);

        sql = "UPDATE tmp_product_catalog pc SET pc.BaseUnit = 'Bag' WHERE pc.BaseUnit='BAG'";
        logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
        sql = "UPDATE tmp_product_catalog pc SET pc.OrderUOM = 'Bag' WHERE pc.OrderUOM='BAG'";
        logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
        sql = "UPDATE tmp_product_catalog pc SET pc.WeightUOM = 'Bag' WHERE pc.WeightUOM='BAG'";
        logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
        sql = "UPDATE tmp_product_catalog pc SET pc.HeightUOM = 'Bag' WHERE pc.HeightUOM='BAG'";
        logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
        sql = "UPDATE tmp_product_catalog pc SET pc.LengthUOM = 'Bag' WHERE pc.LengthUOM='BAG'";
        logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
        sql = "UPDATE tmp_product_catalog pc SET pc.WidthUOM = 'Bag' WHERE pc.WidthUOM='BAG'";
        logger.info(sql + " -> " + this.jdbcTemplate.update(sql));

        sql = "UPDATE tmp_product_catalog pc SET pc.BaseUnit = 'Bottle' WHERE pc.BaseUnit='BOT'";
        logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
        sql = "UPDATE tmp_product_catalog pc SET pc.OrderUOM = 'Bottle' WHERE pc.OrderUOM='BOT'";
        logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
        sql = "UPDATE tmp_product_catalog pc SET pc.WeightUOM = 'Bottle' WHERE pc.WeightUOM='BOT'";
        logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
        sql = "UPDATE tmp_product_catalog pc SET pc.HeightUOM = 'Bottle' WHERE pc.HeightUOM='BOT'";
        logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
        sql = "UPDATE tmp_product_catalog pc SET pc.LengthUOM = 'Bottle' WHERE pc.LengthUOM='BOT'";
        logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
        sql = "UPDATE tmp_product_catalog pc SET pc.WidthUOM = 'Bottle' WHERE pc.WidthUOM='BOT'";
        logger.info(sql + " -> " + this.jdbcTemplate.update(sql));

        sql = "UPDATE tmp_product_catalog pc SET pc.BaseUnit = 'Box' WHERE pc.BaseUnit='BOX'";
        logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
        sql = "UPDATE tmp_product_catalog pc SET pc.OrderUOM = 'Box' WHERE pc.OrderUOM='BOX'";
        logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
        sql = "UPDATE tmp_product_catalog pc SET pc.WeightUOM = 'Box' WHERE pc.WeightUOM='BOX'";
        logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
        sql = "UPDATE tmp_product_catalog pc SET pc.HeightUOM = 'Box' WHERE pc.HeightUOM='BOX'";
        logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
        sql = "UPDATE tmp_product_catalog pc SET pc.LengthUOM = 'Box' WHERE pc.LengthUOM='BOX'";
        logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
        sql = "UPDATE tmp_product_catalog pc SET pc.WidthUOM = 'Box' WHERE pc.WidthUOM='BOX'";
        logger.info(sql + " -> " + this.jdbcTemplate.update(sql));

        sql = "UPDATE tmp_product_catalog pc SET pc.BaseUnit = 'Cassette' WHERE pc.BaseUnit='CAS'";
        logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
        sql = "UPDATE tmp_product_catalog pc SET pc.OrderUOM = 'Cassette' WHERE pc.OrderUOM='CAS'";
        logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
        sql = "UPDATE tmp_product_catalog pc SET pc.WeightUOM = 'Cassette' WHERE pc.WeightUOM='CAS'";
        logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
        sql = "UPDATE tmp_product_catalog pc SET pc.HeightUOM = 'Cassette' WHERE pc.HeightUOM='CAS'";
        logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
        sql = "UPDATE tmp_product_catalog pc SET pc.LengthUOM = 'Cassette' WHERE pc.LengthUOM='CAS'";
        logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
        sql = "UPDATE tmp_product_catalog pc SET pc.WidthUOM = 'Cassette' WHERE pc.WidthUOM='CAS'";
        logger.info(sql + " -> " + this.jdbcTemplate.update(sql));

        sql = "UPDATE tmp_product_catalog pc SET pc.BaseUnit = 'Piece' WHERE pc.BaseUnit='PCS'";
        logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
        sql = "UPDATE tmp_product_catalog pc SET pc.OrderUOM = 'Piece' WHERE pc.OrderUOM='PCS'";
        logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
        sql = "UPDATE tmp_product_catalog pc SET pc.WeightUOM = 'Piece' WHERE pc.WeightUOM='PCS'";
        logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
        sql = "UPDATE tmp_product_catalog pc SET pc.HeightUOM = 'Piece' WHERE pc.HeightUOM='PCS'";
        logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
        sql = "UPDATE tmp_product_catalog pc SET pc.LengthUOM = 'Piece' WHERE pc.LengthUOM='PCS'";
        logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
        sql = "UPDATE tmp_product_catalog pc SET pc.WidthUOM = 'Piece' WHERE pc.WidthUOM='PCS'";
        logger.info(sql + " -> " + this.jdbcTemplate.update(sql));

        sql = "UPDATE tmp_product_catalog pc SET pc.BaseUnit = 'Roll' WHERE pc.BaseUnit='ROL'";
        logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
        sql = "UPDATE tmp_product_catalog pc SET pc.OrderUOM = 'Roll' WHERE pc.OrderUOM='ROL'";
        logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
        sql = "UPDATE tmp_product_catalog pc SET pc.WeightUOM = 'Roll' WHERE pc.WeightUOM='ROL'";
        logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
        sql = "UPDATE tmp_product_catalog pc SET pc.HeightUOM = 'Roll' WHERE pc.HeightUOM='ROL'";
        logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
        sql = "UPDATE tmp_product_catalog pc SET pc.LengthUOM = 'Roll' WHERE pc.LengthUOM='ROL'";
        logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
        sql = "UPDATE tmp_product_catalog pc SET pc.WidthUOM = 'Roll' WHERE pc.WidthUOM='ROL'";
        logger.info(sql + " -> " + this.jdbcTemplate.update(sql));

        sql = "UPDATE tmp_product_catalog pc SET pc.BaseUnit = 'Set' WHERE pc.BaseUnit='SET'";
        logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
        sql = "UPDATE tmp_product_catalog pc SET pc.OrderUOM = 'Set' WHERE pc.OrderUOM='SET'";
        logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
        sql = "UPDATE tmp_product_catalog pc SET pc.WeightUOM = 'Set' WHERE pc.WeightUOM='SET'";
        logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
        sql = "UPDATE tmp_product_catalog pc SET pc.HeightUOM = 'Set' WHERE pc.HeightUOM='SET'";
        logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
        sql = "UPDATE tmp_product_catalog pc SET pc.LengthUOM = 'Set' WHERE pc.LengthUOM='SET'";
        logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
        sql = "UPDATE tmp_product_catalog pc SET pc.WidthUOM = 'Set' WHERE pc.WidthUOM='SET'";
        logger.info(sql + " -> " + this.jdbcTemplate.update(sql));

        sql = "UPDATE tmp_product_catalog pc SET pc.BaseUnit = 'Tube' WHERE pc.BaseUnit='TUB'";
        logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
        sql = "UPDATE tmp_product_catalog pc SET pc.OrderUOM = 'Tube' WHERE pc.OrderUOM='TUB'";
        logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
        sql = "UPDATE tmp_product_catalog pc SET pc.WeightUOM = 'Tube' WHERE pc.WeightUOM='TUB'";
        logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
        sql = "UPDATE tmp_product_catalog pc SET pc.HeightUOM = 'Tube' WHERE pc.HeightUOM='TUB'";
        logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
        sql = "UPDATE tmp_product_catalog pc SET pc.LengthUOM = 'Tube' WHERE pc.LengthUOM='TUB'";
        logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
        sql = "UPDATE tmp_product_catalog pc SET pc.WidthUOM = 'Tube' WHERE pc.WidthUOM='TUB'";
        logger.info(sql + " -> " + this.jdbcTemplate.update(sql));

        sql = "UPDATE tmp_product_catalog pc SET pc.BaseUnit = 'Unit' WHERE pc.BaseUnit='UNT'";
        logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
        sql = "UPDATE tmp_product_catalog pc SET pc.OrderUOM = 'Unit' WHERE pc.OrderUOM='UNT'";
        logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
        sql = "UPDATE tmp_product_catalog pc SET pc.WeightUOM = 'Unit' WHERE pc.WeightUOM='UNT'";
        logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
        sql = "UPDATE tmp_product_catalog pc SET pc.HeightUOM = 'Unit' WHERE pc.HeightUOM='UNT'";
        logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
        sql = "UPDATE tmp_product_catalog pc SET pc.LengthUOM = 'Unit' WHERE pc.LengthUOM='UNT'";
        logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
        sql = "UPDATE tmp_product_catalog pc SET pc.WidthUOM = 'Unit' WHERE pc.WidthUOM='UNT'";
        logger.info(sql + " -> " + this.jdbcTemplate.update(sql));

        sql = "INSERT INTO tmp_unit SELECT NULL, BaseUnit, NULL, NULL, NULL FROM tmp_product_catalog WHERE BaseUnit IS NOT NULL AND BaseUnit != '' GROUP BY BaseUnit";
        logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
        sql = "INSERT IGNORE INTO tmp_unit SELECT NULL, OrderUOM, NULL, NULL, NULL FROM tmp_product_catalog WHERE OrderUOM IS NOT NULL AND OrderUOM != '' GROUP BY OrderUOM";
        logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
        sql = "INSERT IGNORE INTO tmp_unit SELECT NULL, WeightUOM, NULL, NULL, NULL FROM tmp_product_catalog WHERE WeightUOM IS NOT NULL AND WeightUOM != '' GROUP BY WeightUOM";
        logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
        sql = "INSERT IGNORE INTO tmp_unit SELECT NULL, HeightUOM, NULL, NULL, NULL FROM tmp_product_catalog WHERE HeightUOM IS NOT NULL AND HeightUOM != '' GROUP BY HeightUOM";
        logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
        sql = "INSERT IGNORE INTO tmp_unit SELECT NULL, LengthUOM, NULL, NULL, NULL FROM tmp_product_catalog WHERE LengthUOM IS NOT NULL AND LengthUOM != '' GROUP BY LengthUOM";
        logger.info(sql + " -> " + this.jdbcTemplate.update(sql));
        sql = "INSERT IGNORE INTO tmp_unit SELECT NULL, WidthUOM, NULL, NULL, NULL FROM tmp_product_catalog WHERE WidthUOM IS NOT NULL AND WidthUOM != '' GROUP BY WidthUOM";
        logger.info(sql + " -> " + this.jdbcTemplate.update(sql));

        sql = "UPDATE ap_unit au LEFT JOIN ap_label al ON au.LABEL_ID = al.LABEL_ID LEFT JOIN tmp_unit tu ON al.LABEL_EN = tu.LABEL "
                + "SET tu.UNIT_ID = au.UNIT_ID WHERE tu.ID IS NOT NULL";
        this.jdbcTemplate.update(sql);

        sql = "SELECT * FROM tmp_unit tu where tu.UNIT_ID IS NULL";
        List<Map<String, Object>> result = this.jdbcTemplate.queryForList(sql);
        Scanner s = new Scanner(System.in);
        boolean validData = false;
        for (Map<String, Object> u : result) {
            validData = false;
            do {
                logger.info("---------------------------------------------------------------------------");
                logger.info("Please select the Dimension for this Unit. (Only enter the number)");
                logger.info("Possible Dimension values are Volume(1), Weight(2), Each(3), Distance(4)");
                logger.info("Unit: " + u.get("LABEL"));
                String input = s.nextLine();
                if (input.equals("1") || input.equals("2") || input.equals("3") || input.equals("4")) {
                    validData = true;
                    this.jdbcTemplate.update("UPDATE tmp_unit SET DIMENSION_ID=? WHERE ID=?", input, u.get("ID"));
                }
            } while (!validData);
        }

        sql = "SELECT MAX(LABEL_ID) FROM ap_label";
        int max = this.jdbcTemplate.queryForObject(sql, Integer.class);

        sql = "INSERT INTO ap_label SELECT NULL, tu.LABEL, NULL, NULL, NULL, 1, NOW(), 1, NOW() FROM tmp_unit tu WHERE tu.UNIT_ID IS NULL";
        this.jdbcTemplate.update(sql);

        sql = "UPDATE tmp_unit tu LEFT JOIN ap_label l ON tu.LABEL=l.LABEL_EN AND l.LABEL_ID>" + max + " SET tu.LABEL_ID=l.LABEL_ID WHERE tu.UNIT_ID IS NULL";
        this.jdbcTemplate.update(sql);

        sql = "INSERT INTO ap_unit SELECT  null, tu.DIMENSION_ID, tu.LABEL_ID, tu.LABEL, true, 1, now(), 1, now() from tmp_unit tu where tu.UNIT_ID is null";
        this.jdbcTemplate.update(sql);
        throw new NullPointerException();
    }

    @Override
    @Transactional
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
                + "FROM tmp_product_catalog WHERE TracerCategory IS NOT NULL AND TracerCategory != '' GROUP BY TracerCategory";
        int rows = this.jdbcTemplate.update(sqlString);
        logger.info(rows + " inserted into the tmp_tracer_category table");

        // Step 4 Match those records that are already present in the main tracer_category table
        sqlString = "UPDATE rm_tracer_category tc "
                + "LEFT JOIN ap_label tcl ON tc.LABEL_ID=tcl.LABEL_ID "
                + "LEFT JOIN tmp_tracer_category ttc ON tcl.LABEL_EN=ttc.LABEL "
                + "SET ttc.TRACER_CATEGORY_ID=tc.TRACER_CATEGORY_ID "
                + "WHERE ttc.ID IS NOT NULL";

        rows = this.jdbcTemplate.update(sqlString);
        logger.info(rows + " existing labels found");

        // Step 5 Get the max count on the label table so that you can now work on data that you insert from here on
        sqlString = "SELECT MAX(LABEL_ID) m FROM ap_label";

        max = this.jdbcTemplate.queryForObject(sqlString, Integer.class
        );
        logger.info(max + " is the current Max count for ap_label");

        // Step 6 Insert the rows that you do not have in tracer already into the label table
        sqlString = "INSERT INTO ap_label SELECT NULL, ttc.LABEL, NULL, NULL, NULL, 1, NOW(), 1, NOW() "
                + "FROM tmp_tracer_category ttc "
                + "WHERE ttc.TRACER_CATEGORY_ID IS NULL AND ttc.`LABEL` !=''";

        rows = this.jdbcTemplate.update(sqlString);
        logger.info(rows + " rows inserted into the ap_label table");

        // Step 7 Now find the LABEL_ID for the rows that you just inserted and update your tmp table with those
        sqlString = "UPDATE tmp_tracer_category ttc "
                + "LEFT JOIN ap_label l ON ttc.LABEL=l.LABEL_EN "
                + "AND l.LABEL_ID>? "
                + "SET ttc.LABEL_ID=l.LABEL_ID "
                + "WHERE ttc.TRACER_CATEGORY_ID IS NULL";

        rows = this.jdbcTemplate.update(sqlString, max);
        logger.info(rows + " rows matched and LABEL_ID updated with new LABEL_ID");

        // Step 8 Finally take the Labels and 
        sqlString = "INSERT INTO rm_tracer_category "
                + "SELECT NULL, 1, ttc.LABEL_ID, 1, 1, NOW(), 1, NOW() "
                + "FROM tmp_tracer_category ttc "
                + "WHERE ttc.TRACER_CATEGORY_ID IS NULL "
                + "AND ttc.LABEL_ID IS NOT NULL";

        rows = this.jdbcTemplate.update(sqlString);
        logger.info(rows + " new rows inserted into the rm_tracer_category table");
    }

    @Override
    @Transactional
    public void pullForecastingUnitFromTmpTables() {
        int max;

        // Step 1 - Drop the table if it exists
        String sqlString = "DROP TABLE IF EXISTS `tmp_label`";
        this.jdbcTemplate.update(sqlString);
        sqlString = "DROP TABLE IF EXISTS `tmp_forecasting_unit`";
        this.jdbcTemplate.update(sqlString);

        // Step 2 - Create the tmp table
        sqlString = "CREATE TABLE `tmp_label` (  "
                + "	`ID` int(10) unsigned NOT NULL AUTO_INCREMENT,  "
                + "    `LABEL` varchar(200) COLLATE utf8_bin NOT NULL,  "
                + "    `LABEL_ID` int (10) unsigned DEFAULT NULL,  "
                + "    `FOUND` tinyint(1) unsigned DEFAULT 0 NOT NULL, "
                + "    PRIMARY KEY (`ID`)  "
                + ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin";
        this.jdbcTemplate.update(sqlString);

        sqlString = "ALTER TABLE  `tmp_label`  "
                + "ADD INDEX `tmpLabel_label` (`LABEL` ASC), "
                + "ADD INDEX `fk_tmpLabel_labelIdIdx` (`LABEL_ID` ASC), "
                + "ADD CONSTRAINT `fk_tmpLabel_labelId` "
                + "  FOREIGN KEY (`LABEL_ID`) "
                + "  REFERENCES  `ap_label` (`LABEL_ID`) "
                + "  ON DELETE NO ACTION "
                + "  ON UPDATE NO ACTION;";
        this.jdbcTemplate.update(sqlString);

        sqlString = "CREATE TABLE `tmp_forecasting_unit` (  "
                + "	`ID` int(10) unsigned NOT NULL AUTO_INCREMENT,  "
                + "    `LABEL` varchar(200) COLLATE utf8_bin NOT NULL,  "
                + "    `LABEL_ID` int (10) unsigned DEFAULT NULL,  "
                + "    `GENERIC_LABEL`varchar(200) COLLATE utf8_bin NULL,  "
                + "    `GENERIC_LABEL_ID` int (10) unsigned DEFAULT NULL,  "
                + "	`UNIT_ID` int (10) unsigned DEFAULT NULL, "
                + "    `PRODUCT_CATEGORY_ID` int (10) unsigned default null, "
                + "    `TRACER_CATEGORY_ID` int (10) unsigned default null, "
                + "    PRIMARY KEY (`ID`)  "
                + ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin";
        this.jdbcTemplate.update(sqlString);

        sqlString = "ALTER TABLE  `tmp_forecasting_unit`  "
                + "ADD INDEX `idxLabel` (`LABEL` ASC), "
                + "ADD INDEX `idxGenericLabel` (`GENERIC_LABEL` ASC), "
                + "ADD INDEX `fk_labelId_idx` (`LABEL_ID` ASC), "
                + "ADD INDEX `fk_unitId_idx` (`UNIT_ID` ASC), "
                + "ADD INDEX `fk_genericLabelId_idx` (`GENERIC_LABEL_ID` ASC), "
                + "ADD INDEX `fk_productCategoryId_idx` (`PRODUCT_CATEGORY_ID` ASC), "
                + "ADD INDEX `fk_tracerCategoryId_idx` (`TRACER_CATEGORY_ID` ASC)";
        this.jdbcTemplate.update(sqlString);
        sqlString = "ALTER TABLE  `tmp_forecasting_unit`  "
                + "ADD CONSTRAINT `fk_labelId` "
                + "  FOREIGN KEY (`LABEL_ID`) "
                + "  REFERENCES  `ap_label` (`LABEL_ID`) "
                + "  ON DELETE NO ACTION "
                + "  ON UPDATE NO ACTION, "
                + "ADD CONSTRAINT `fk_unitId` "
                + "  FOREIGN KEY (`UNIT_ID`) "
                + "  REFERENCES  `ap_unit` (`UNIT_ID`) "
                + "  ON DELETE NO ACTION "
                + "  ON UPDATE NO ACTION, "
                + "ADD CONSTRAINT `fk_genericLabelId` "
                + "  FOREIGN KEY (`GENERIC_LABEL_ID`) "
                + "  REFERENCES  `ap_label` (`LABEL_ID`) "
                + "  ON DELETE NO ACTION "
                + "  ON UPDATE NO ACTION, "
                + "ADD CONSTRAINT `fk_productCategoryId` "
                + "  FOREIGN KEY (`PRODUCT_CATEGORY_ID`) "
                + "  REFERENCES  `rm_product_category` (`PRODUCT_CATEGORY_ID`) "
                + "  ON DELETE NO ACTION "
                + "  ON UPDATE NO ACTION, "
                + "ADD CONSTRAINT `fk_tracerCategoryId` "
                + "  FOREIGN KEY (`TRACER_CATEGORY_ID`) "
                + "  REFERENCES  `rm_tracer_category` (`TRACER_CATEGORY_ID`) "
                + "  ON DELETE NO ACTION "
                + "  ON UPDATE NO ACTION";
        this.jdbcTemplate.update(sqlString);

        // Step 3 insert into the tmp_label the ProductNameNoPack
        sqlString = "insert into tmp_label SELECT null, ProductNameNoPack, null, 0 FROM tmp_product_catalog tpc WHERE tpc.ProductNameNoPack IS NOT NULL AND tpc.ProductNameNoPack != '' group by tpc.ProductNameNoPack";
        int rows = this.jdbcTemplate.update(sqlString);
        logger.info(rows + " inserted into the tmp_label for ProductNameNoPack");

        // Step 4 Match those records that are already present in the main forecasting_unit table
        sqlString = "update tmp_label tl LEFT JOIN (SELECT ful.LABEL_ID, ful.LABEL_EN FROM rm_forecasting_unit fu LEFT JOIN ap_label ful on fu.LABEL_ID=ful.LABEL_ID) as fuld ON tl.LABEL=fuld.LABEL_EN  "
                + "set tl.LABEL_ID = fuld.LABEL_ID, tl.FOUND=1 "
                + "where fuld.LABEL_ID is not null";
        rows = this.jdbcTemplate.update(sqlString);
        logger.info(rows + " existing labels found");

        // Step 5 Get the max count on the label table so that you can now work on data that you insert from here on
        sqlString = "SELECT MAX(LABEL_ID) m FROM ap_label";
        max = this.jdbcTemplate.queryForObject(sqlString, Integer.class);
        logger.info(max + " is the current Max count for ap_label");

        // Step 6 Insert the rows that you do not have in label table
        sqlString = "INSERT INTO ap_label select null, tl.LABEL, null, null, null, 1, now(), 1, now() from tmp_label tl where tl.FOUND=false";
        rows = this.jdbcTemplate.update(sqlString);
        logger.info(rows + " rows inserted into the ap_label table");

        // Step 7 Now find the LABEL_ID for the rows that you just inserted and update your tmp table with those
        sqlString = "update tmp_label tl LEFT JOIN ap_label ful on ful.LABEL_EN=tl.LABEL and ful.LABEL_ID>? set tl.LABEL_ID = ful.LABEL_ID where ful.LABEL_ID is not null";
        rows = this.jdbcTemplate.update(sqlString, max);
        logger.info(rows + " rows matched and LABEL_ID updated with new LABEL_ID");

        // Step 8 - Insert into tmp_froecasting those rows that have not been found 
        sqlString = "INSERT INTO tmp_forecasting_unit SELECT null, tl.LABEL, tl.LABEL_ID, null, null, null, null, null  from tmp_label tl where tl.FOUND=0";
        rows = this.jdbcTemplate.update(sqlString);
        logger.info(rows + " rows inserted into tmp_forecasting_unit");

        // Step 9 - Get the ProductCategory, TracerCategory, Unit and Generic matching ID's
        sqlString = "update tmp_forecasting_unit tfu  "
                + "LEFT JOIN tmp_product_catalog tpc ON tfu.LABEL=tpc.ProductNameNoPack  "
                + "set  "
                + "tfu.GENERIC_LABEL=tpc.INN";
        rows = this.jdbcTemplate.update(sqlString);
        logger.info(rows + " matching Generic labels updated");

        sqlString = "update tmp_forecasting_unit tfu LEFT JOIN tmp_product_catalog tpc ON tfu.LABEL=tpc.ProductNameNoPack   "
                + "LEFT JOIN (SELECT u.*, ul.LABEL_EN FROM ap_unit u LEFT JOIN ap_label ul ON u.LABEL_ID=ul.LABEL_ID) tud ON tpc.BaseUnit=tud.LABEL_EN "
                + "set  "
                + "tfu.UNIT_ID=tud.UNIT_ID";
        rows = this.jdbcTemplate.update(sqlString);
        logger.info(rows + " matching Unit Id's updated");

        sqlString = "update tmp_forecasting_unit tfu LEFT JOIN tmp_product_catalog tpc ON tfu.LABEL=tpc.ProductNameNoPack  "
                + "LEFT JOIN (SELECT pc.PRODUCT_CATEGORY_ID, pcl.LABEL_EN FROM rm_product_category pc LEFT JOIN ap_label pcl ON pc.LABEL_ID=pcl.LABEL_ID) tpcd ON COALESCE(tpc.Subcategory, tpc.CommodityCouncil)=tpcd.LABEL_EN "
                + "set  "
                + "tfu.PRODUCT_CATEGORY_ID=tpcd.PRODUCT_CATEGORY_ID";
        rows = this.jdbcTemplate.update(sqlString);
        logger.info(rows + " matching Product Categories updated");

        sqlString = "update tmp_forecasting_unit tfu LEFT JOIN tmp_product_catalog tpc ON tfu.LABEL=tpc.ProductNameNoPack  "
                + "LEFT JOIN (SELECT tc.TRACER_CATEGORY_ID, tcl.LABEL_EN FROM rm_tracer_category tc LEFT JOIN ap_label tcl ON tc.LABEL_ID=tcl.LABEL_ID) ttcd ON tpc.TracerCategory=ttcd.LABEL_EN "
                + "set  "
                + "tfu.TRACER_CATEGORY_ID=ttcd.TRACER_CATEGORY_ID";
        rows = this.jdbcTemplate.update(sqlString);
        logger.info(rows + " matching Tracer Categories updated");

        // Step 10 Complete the Missing Generic labels
        sqlString = "truncate table tmp_label";
        this.jdbcTemplate.update(sqlString);

        // Step 11 Insert Unique Genric labels into the tmp_label table
        sqlString = "insert into tmp_label SELECT null, tfu.GENERIC_LABEL, null, 0 from tmp_forecasting_unit tfu WHERE tfu.GENERIC_LABEL IS NOT NULL and tfu.GENERIC_LABEL != '' group by tfu.GENERIC_LABEL";
        rows = this.jdbcTemplate.update(sqlString);
        logger.info(rows + " unique Generic names put into tmp_label");

        // Step 12 Get the max count on the label table so that you can now work on data that you insert from here on
        sqlString = "SELECT MAX(LABEL_ID) m FROM ap_label";
        max = this.jdbcTemplate.queryForObject(sqlString, Integer.class);
        logger.info(max + " is the current Max count for ap_label");

        // Step 13 Insert the labels into ap_label
        sqlString = "INSERT INTO ap_label select null, tl.LABEL, null, null, null, 1, now(), 1, now() from tmp_label tl where tl.FOUND=false";
        rows = this.jdbcTemplate.update(sqlString);
        logger.info(rows + " rows inserted into ap_label");

        // Step 14 Now find the LABEL_ID for the rows that you just inserted and update your tmp table with those
        sqlString = "update tmp_label tl LEFT JOIN ap_label ful on ful.LABEL_EN=tl.LABEL and ful.LABEL_ID>? set tl.LABEL_ID = ful.LABEL_ID where ful.LABEL_ID is not null;";
        rows = this.jdbcTemplate.update(sqlString, max);
        logger.info(rows + " rows matched and LABEL_ID updated with new LABEL_ID");

        // Step 15 Update the Generic Label with the new ones inserted
        sqlString = "update tmp_forecasting_unit tfu left join tmp_label tl ON tfu.GENERIC_LABEL=tl.LABEL SET tfu.GENERIC_LABEL_ID=tl.LABEL_ID";
        rows = this.jdbcTemplate.update(sqlString);
        logger.info(rows + " rows updated with Generic Label Id");

        // Step 16 Now insert all the data into ForecastingUnit
        sqlString = "INSERT INTO rm_forecasting_unit select null, 1, tfu.PRODUCT_CATEGORY_ID, tfu.TRACER_CATEGORY_ID, tfu.GENERIC_LABEL_ID, tfu.LABEL_ID, tfu.UNIT_ID, 1, 1, now(), 1, now() from tmp_forecasting_unit tfu";
        rows = this.jdbcTemplate.update(sqlString);
        logger.info(rows + " Inserted into the Forecasting Unit table");
    }

    @Override
    @Transactional
    public void pullPlanningUnitFromTmpTables() {
        String sqlString = "DROP TABLE IF EXISTS tmp_planning_unit";
        this.jdbcTemplate.update(sqlString);

        sqlString = "CREATE TABLE `tmp_planning_unit` (  "
                + "	`ID` int(10) unsigned NOT NULL AUTO_INCREMENT,  "
                + "    `LABEL` varchar(200) COLLATE utf8_bin NOT NULL,  "
                + "    `LABEL_ID` int (10) unsigned DEFAULT NULL,  "
                + "    `MULTIPLIER` double (20,2) UNSIGNED DEFAULT null, "
                + " 	`UNIT_ID` int (10) unsigned DEFAULT NULL, "
                + "	`FORECASTING_UNIT_ID` int (10) unsigned DEFAULT NULL, "
                + "     `SKU_CODE` VARCHAR(100) NULL, "
                + "    PRIMARY KEY (`ID`)  "
                + ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin";
        this.jdbcTemplate.update(sqlString);

        sqlString = "ALTER TABLE  `tmp_planning_unit`  "
                + "ADD INDEX `tmpPlanningUnit_label` (`LABEL` ASC), "
                + "ADD INDEX `fk_tmp_planning_unit_unitId_idx` (`UNIT_ID` ASC), "
                + "ADD INDEX `fk_tmp_planning_unit_forecastingUnit_idx` (`FORECASTING_UNIT_ID` ASC), "
                + "ADD INDEX `fk_tmp_planning_unit_labelId_idx` (`LABEL_ID` ASC)";
        this.jdbcTemplate.update(sqlString);
        sqlString = "ALTER TABLE  `tmp_planning_unit`  "
                + "ADD CONSTRAINT `fk_tmp_planning_unit_unitId` "
                + "  FOREIGN KEY (`UNIT_ID`) "
                + "  REFERENCES  `ap_unit` (`UNIT_ID`) "
                + "  ON DELETE NO ACTION "
                + "  ON UPDATE NO ACTION, "
                + "ADD CONSTRAINT `fk_tmp_planning_unit_forecastingUnit` "
                + "  FOREIGN KEY (`FORECASTING_UNIT_ID`) "
                + "  REFERENCES  `rm_forecasting_unit` (`FORECASTING_UNIT_ID`) "
                + "  ON DELETE NO ACTION "
                + "  ON UPDATE NO ACTION, "
                + "ADD CONSTRAINT `fk_tmp_planning_unit_labelId` "
                + "  FOREIGN KEY (`LABEL_ID`) "
                + "  REFERENCES  `ap_label` (`LABEL_ID`) "
                + "  ON DELETE NO ACTION "
                + "  ON UPDATE NO ACTION";
        this.jdbcTemplate.update(sqlString);

        sqlString = "truncate table tmp_label";
        this.jdbcTemplate.update(sqlString);

        sqlString = "ALTER TABLE `tmp_label` ADD COLUMN `PRODUCT_ID` VARCHAR(100) NOT NULL AFTER `FOUND`, ADD INDEX `idx_tmpLabel_productId` (`PRODUCT_ID` ASC)";
        this.jdbcTemplate.update(sqlString);

        // Step 1 Insert the PlanningUnit name into the tmpTable
        sqlString = "insert into tmp_label SELECT null, ProductName, null, 0, ProductID FROM tmp_product_catalog tpc WHERE tpc.ProductName IS NOT NULL AND tpc.ProductName != '' group by tpc.ProductName";
        int rows = this.jdbcTemplate.update(sqlString);
        logger.info(rows + " labels instered into the tmp label table");

        // Step 2 Find the matching labels that are already there
        sqlString = "update tmp_label tl LEFT JOIN (SELECT pul.LABEL_ID, pul.LABEL_EN FROM rm_planning_unit pu LEFT JOIN ap_label pul on pu.LABEL_ID=pul.LABEL_ID) as puld ON tl.LABEL=puld.LABEL_EN  "
                + "set tl.LABEL_ID = puld.LABEL_ID, tl.FOUND=1 "
                + "where puld.LABEL_ID is not null";
        rows = this.jdbcTemplate.update(sqlString);
        logger.info(rows + " labels are already present");

        // Step 3 Get the max count on the label table so that you can now work on data that you insert from here on
        sqlString = "SELECT MAX(LABEL_ID) m FROM ap_label";
        int max = this.jdbcTemplate.queryForObject(sqlString, Integer.class);
        logger.info(max + " is the current Max count for ap_label");

        // Step 4 Insert the rows that you do not have in label table
        sqlString = "INSERT INTO ap_label select null, tl.LABEL, null, null, null, 1, now(), 1, now() from tmp_label tl where tl.FOUND=false";
        rows = this.jdbcTemplate.update(sqlString);
        logger.info(rows + " rows inserted into the ap_label table");

        // Step 5 Now find the LABEL_ID for the rows that you just inserted and update your tmp table with those
        sqlString = "update tmp_label tl LEFT JOIN ap_label pul on pul.LABEL_EN=tl.LABEL and pul.LABEL_ID>? set tl.LABEL_ID = pul.LABEL_ID where pul.LABEL_ID is not null";
        rows = this.jdbcTemplate.update(sqlString, max);
        logger.info(rows + " rows matched and LABEL_ID updated with new LABEL_ID");

        // Step 6 Insert into tmp planning unit 
        sqlString = "INSERT INTO tmp_planning_unit SELECT null, tl.LABEL, tl.LABEL_ID, 1, null, null, tl.PRODUCT_ID from tmp_label tl where tl.FOUND=0";
        rows = this.jdbcTemplate.update(sqlString);
        logger.info(rows + " rows inserted into tmpPlanningUnit");

        // Step 7 Update the ForecastingUnit and Unit
        sqlString = "update tmp_planning_unit tpu  "
                + "LEFT JOIN tmp_product_catalog tpc ON tpu.LABEL=tpc.ProductName "
                + "LEFT JOIN (SELECT u.*, ul.LABEL_EN FROM ap_unit u LEFT JOIN ap_label ul ON u.LABEL_ID=ul.LABEL_ID) tud ON tpc.OrderUOM=tud.LABEL_EN "
                + "set tpu.UNIT_ID=tud.UNIT_ID, tpu.MULTIPLIER=IF(tpc.NoofBaseUnits != '',tpc.NoofBaseUnits,0)";
        rows = this.jdbcTemplate.update(sqlString);
        logger.info(rows + " UnitId's updated in tmpPlanningUnit");

        sqlString = "update tmp_planning_unit tpu  "
                + "LEFT JOIN tmp_product_catalog tpc ON tpu.LABEL=tpc.ProductName "
                + "LEFT JOIN (SELECT fu.*, ful.LABEL_EN FROM rm_forecasting_unit fu LEFT JOIN ap_label ful ON fu.LABEL_ID=ful.LABEL_ID) tfud ON tpc.ProductNameNoPack=tfud.LABEL_EN "
                + "set tpu.FORECASTING_UNIT_ID=tfud.FORECASTING_UNIT_ID";
        rows = this.jdbcTemplate.update(sqlString);
        logger.info(rows + " ForecastingUnits updated in tmpPlanningUnit");

        // Step 8 Insert into the main planning Unit
        sqlString = "insert into rm_planning_unit select null, tpu.FORECASTING_UNIT_ID, tpu.LABEL_ID, tpu.UNIT_ID, tpu.MULTIPLIER, 1, 1, now(), 1, now() from tmp_planning_unit tpu";
        rows = this.jdbcTemplate.update(sqlString);
        logger.info(rows + " inserted into Planning unit");

        sqlString = "ALTER TABLE `tmp_label` DROP COLUMN `PRODUCT_ID`, DROP INDEX `idx_tmpLabel_productId`";
        this.jdbcTemplate.update(sqlString);

        // Now for Procurment Agent Planning Unit
        sqlString = "drop table IF EXISTS `tmp_procurement_agent_planning_unit`";
        this.jdbcTemplate.update(sqlString);

        sqlString = "CREATE TABLE `tmp_procurement_agent_planning_unit` (  "
                + "	`ID` int(10) unsigned NOT NULL AUTO_INCREMENT,  "
                + "    `PLANNING_UNIT_ID` int(10) UNSIGNED not null, "
                + "    `SKU_CODE` VARCHAR(50) null, "
                + "    `EST_PRICE` varchar(20) null, "
                + "    `MOQ` VARCHAR(20) null, "
                + "    `UNITS_PER_PALLET` VARCHAR(20) null, "
                + "    `UNITS_PER_CONTAINER` VARCHAR(20) null, "
                + "    `VOLUME` VARCHAR(20) null, "
                + "    `WEIGHT` VARCHAR(20) null, "
                + "    PRIMARY KEY (`ID`)  "
                + ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin";
        this.jdbcTemplate.update(sqlString);

        sqlString = "insert into tmp_procurement_agent_planning_unit select null, pu.PLANNING_UNIT_ID, null, null, null, null, null, null, null from rm_planning_unit pu";
        rows = this.jdbcTemplate.update(sqlString);
        logger.info(rows + " rowsinstered into the tmpProcurementAgentPlanningUnit table");

        sqlString = "update tmp_procurement_agent_planning_unit tpapu LEFT JOIN rm_planning_unit pu ON tpapu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID LEFT JOIN ap_label pul ON pu.LABEL_ID=pul.LABEL_ID LEFT JOIN tmp_product_catalog tpc ON pul.LABEL_EN=tpc.ProductName "
                + "SET  "
                + "	tpapu.SKU_CODE=tpc.ProductID,  "
                + "    tpapu.EST_PRICE=if(tpc.EstPrice='', null,tpc.EstPrice),  "
                + "    tpapu.MOQ=if(tpc.PlanningUnitMOQ='', null,tpc.PlanningUnitMOQ),  "
                + "    tpapu.UNITS_PER_PALLET=if(tpc.PlanningUnitsperPallet='',null,tpc.PlanningUnitsperPallet),  "
                + "    tpapu.UNITS_PER_CONTAINER=if(tpc.PlanningUnitsperContainer='',null,tpc.PlanningUnitsperContainer),  "
                + "    tpapu.VOLUME=if(tpc.PlanningUnitVolumem3='',null,tpc.PlanningUnitVolumem3),  "
                + "    tpapu.WEIGHT=if(tpc.PlanningUnitWeightkg='',null,tpc.PlanningUnitWeightkg)";
        rows = this.jdbcTemplate.update(sqlString);
        logger.info(rows + " rows mapped with data for planning unit");

        sqlString = "insert into rm_procurement_agent_planning_unit select null, 1, tpapu.PLANNING_UNIT_ID, tpapu.SKU_CODE, tpapu.EST_PRICE, tpapu.MOQ, tpapu.UNITS_PER_PALLET, tpapu.UNITS_PER_CONTAINER, tpapu.VOLUME, tpapu.WEIGHT, 1, 1, now(), 1, now() from tmp_procurement_agent_planning_unit tpapu "
                + "where tpapu.SKU_CODE IS NOT NULL;";
        rows = this.jdbcTemplate.update(sqlString);
        logger.info(rows + " rows inserted into procurementAgentPlanningUnit");
    }

    @Override
    @Transactional
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

        sql = "ALTER TABLE  `tmp_supplier` ADD UNIQUE INDEX `index3` (`LABEL` ASC)";
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
        this.jdbcTemplate.update(sql);

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
    @Transactional
    public void pullProcurementUnitFromTmpTables() {
        // Step 1 -- Create Tables
        String sqlString = "DROP TABLE IF EXISTS tmp_procurement_unit";
        this.jdbcTemplate.update(sqlString);

        sqlString = "CREATE TABLE `tmp_procurement_unit` (  "
                + "	`ID` int(10) unsigned NOT NULL AUTO_INCREMENT,  "
                + "    `LABEL` varchar(200) COLLATE utf8_bin NOT NULL,  "
                + "    `LABEL_ID` int (10) unsigned DEFAULT NULL,  "
                + "    `MULTIPLIER` double (12,2) UNSIGNED DEFAULT null, "
                + " 	`UNIT_ID` int (10) unsigned DEFAULT NULL, "
                + "	`PLANNING_UNIT_ID` int (10) unsigned DEFAULT NULL, "
                + "    `SUPPLIER_ID` int (10) unsigned DEFAULT NULL, "
                + "    `WIDTH_UNIT_ID` int (10) unsigned DEFAULT NULL, "
                + "    `WIDTH` decimal (12,2) unsigned DEFAULT NULL, "
                + "    `LENGTH_UNIT_ID` int (10) unsigned DEFAULT NULL, "
                + "    `LENGTH` decimal (12,2) unsigned DEFAULT NULL, "
                + "    `HEIGHT_UNIT_ID` int (10) unsigned DEFAULT NULL, "
                + "    `HEIGHT` decimal (12,2) unsigned DEFAULT NULL, "
                + "    `WEIGHT_UNIT_ID` int (10) unsigned DEFAULT NULL, "
                + "    `WEIGHT` decimal (12,2) unsigned DEFAULT NULL, "
                + "    `UNITS_PER_CONTAINER` int (10) unsigned DEFAULT NULL, "
                + "    `LABELING` Varchar(200) DEFAULT NULL, "
                + "    `SKU_CODE` Varchar(200) DEFAULT NULL, "
                + "    `GTIN` Varchar(200) DEFAULT NULL, "
                + "    `VENDOR_PRICE` Varchar(200) DEFAULT NULL, "
                + "    `APPROVED_TO_SHIPPED_LEAD_TIME` DECIMAL(12,2) DEFAULT NULL, "
                + "    `FOUND` tinyint(1) unsigned default 0 not null, "
                + "    CONSTRAINT PRIMARY KEY (`ID`)  "
                + ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin";
        this.jdbcTemplate.update(sqlString);

        sqlString = "ALTER TABLE  `tmp_procurement_unit`  "
                + "ADD INDEX `idx_procurementUnit1` (`LABEL` ASC), "
                + "ADD INDEX `idx_procurementUnit2` (`LABEL_ID` ASC), "
                + "ADD INDEX `idx_procurementUnit3` (`UNIT_ID` ASC), "
                + "ADD INDEX `idx_procurementUnit4` (`PLANNING_UNIT_ID` ASC), "
                + "ADD INDEX `idx_procurementUnit5` (`WIDTH_UNIT_ID` ASC), "
                + "ADD INDEX `idx_procurementUnit6` (`HEIGHT_UNIT_ID` ASC), "
                + "ADD INDEX `idx_procurementUnit7` (`LENGTH_UNIT_ID` ASC), "
                + "ADD INDEX `idx_procurementUnit8` (`WEIGHT_UNIT_ID` ASC)";
        this.jdbcTemplate.update(sqlString);

        sqlString = "ALTER TABLE  `tmp_procurement_unit`  "
                + "ADD CONSTRAINT `fk_forecastingUnit1` "
                + "  FOREIGN KEY (`LABEL_ID`) "
                + "  REFERENCES  `ap_label` (`LABEL_ID`) "
                + "  ON DELETE NO ACTION "
                + "  ON UPDATE NO ACTION, "
                + "ADD CONSTRAINT `fk_procurementUnit2` "
                + "  FOREIGN KEY (`UNIT_ID`) "
                + "  REFERENCES  `ap_unit` (`UNIT_ID`) "
                + "  ON DELETE NO ACTION "
                + "  ON UPDATE NO ACTION, "
                + "ADD CONSTRAINT `fk_procurementUnit3` "
                + "  FOREIGN KEY (`PLANNING_UNIT_ID`) "
                + "  REFERENCES  `rm_planning_unit` (`PLANNING_UNIT_ID`) "
                + "  ON DELETE NO ACTION "
                + "  ON UPDATE NO ACTION, "
                + "ADD CONSTRAINT `fk_procurementUnit4` "
                + "  FOREIGN KEY (`SUPPLIER_ID`) "
                + "  REFERENCES  `rm_supplier` (`SUPPLIER_ID`) "
                + "  ON DELETE NO ACTION "
                + "  ON UPDATE NO ACTION, "
                + "ADD CONSTRAINT `fk_procurementUnit5` "
                + "  FOREIGN KEY (`WIDTH_UNIT_ID`) "
                + "  REFERENCES  `ap_unit` (`UNIT_ID`) "
                + "  ON DELETE NO ACTION "
                + "  ON UPDATE NO ACTION, "
                + "  ADD CONSTRAINT `fk_procurementUnit6` "
                + "  FOREIGN KEY (`LENGTH_UNIT_ID`) "
                + "  REFERENCES  `ap_unit` (`UNIT_ID`) "
                + "  ON DELETE NO ACTION "
                + "  ON UPDATE NO ACTION, "
                + "  ADD CONSTRAINT `fk_procurementUnit7` "
                + "  FOREIGN KEY (`HEIGHT_UNIT_ID`) "
                + "  REFERENCES  `ap_unit` (`UNIT_ID`) "
                + "  ON DELETE NO ACTION "
                + "  ON UPDATE NO ACTION, "
                + "  ADD CONSTRAINT `fk_procurementUnit8` "
                + "  FOREIGN KEY (`WEIGHT_UNIT_ID`) "
                + "  REFERENCES  `ap_unit` (`UNIT_ID`) "
                + "  ON DELETE NO ACTION "
                + "  ON UPDATE NO ACTION";
        this.jdbcTemplate.update(sqlString);

        sqlString = "truncate table tmp_procurement_unit";

        sqlString = "truncate table tmp_label";
        this.jdbcTemplate.update(sqlString);

        // Step 2 - Insert into tmpLabel
        sqlString = "insert into tmp_label SELECT null, ItemName, null, 0 FROM tmp_product_catalog tpc WHERE tpc.ItemName IS NOT NULL AND tpc.ItemName != '' group by tpc.ItemName";
        int rows = this.jdbcTemplate.update(sqlString);
        logger.info(rows + " rows inserted into tmpLabel");

        // Step 3 - Find already existing labels
        sqlString = "update tmp_label tl LEFT JOIN (SELECT pul.LABEL_ID, pul.LABEL_EN FROM rm_procurement_unit pu LEFT JOIN ap_label pul on pu.LABEL_ID=pul.LABEL_ID) as puld ON tl.LABEL=puld.LABEL_EN  set tl.LABEL_ID = puld.LABEL_ID, tl.FOUND=1 where puld.LABEL_ID is not null";
        rows = this.jdbcTemplate.update(sqlString);
        logger.info(rows + " labels already existed");

        // Step 4 - Find max
        sqlString = "SELECT MAX(ap_label.LABEL_ID) from ap_label";
        int max = this.jdbcTemplate.queryForObject(sqlString, Integer.class);
        logger.info(max + " Current max");

        // Step 5 - Insert into ap_labels
        sqlString = "INSERT INTO ap_label select null, tl.LABEL, null, null, null, 1, now(), 1, now() from tmp_label tl where tl.FOUND=false";
        rows = this.jdbcTemplate.update(sqlString);
        logger.info(rows + " rows inserted for labels");

        // Step 6 - Match the Labels inserted to find the label_id
        sqlString = "update tmp_label tl LEFT JOIN ap_label pul on pul.LABEL_EN=tl.LABEL and pul.LABEL_ID>? set tl.LABEL_ID = pul.LABEL_ID where pul.LABEL_ID is not null";
        rows = this.jdbcTemplate.update(sqlString, max);
        logger.info(rows + " rows matched with new labels");

        // Step 7 - Insert into the tmp_procurement table
        sqlString = "INSERT INTO tmp_procurement_unit SELECT null, tl.LABEL, tl.LABEL_ID, null, null, null, null, null, null, null, null, null, null, null, null, null,null, null, null, null, null, 1  from tmp_label tl where tl.FOUND=0";
        rows = this.jdbcTemplate.update(sqlString);
        logger.info(rows + " rows inserted into the tmp_procurement table");

        Scanner s = new Scanner(System.in);
        boolean validData = false;
        Double approvedToShipppedLeadTime = 3.0;
//        do {
//            logger.info("---------------------------------------------------------------------------");
//            logger.info("Please enter the Approved to Shipped Lead Time (in Months) that you want to use as default for all Procurement Units.");
//            logger.info("Could be a Decimal or wirte 'null' if you want to keep it as null");
//            logger.info("Approved to Shipped Lead Time (in months): ");
//            String input = s.nextLine();
//            if (NumberUtils.isCreatable(input) || input.equals("null")) {
//                validData = true;
//                if (!input.equals("null")) {
//                    approvedToShipppedLeadTime = NumberUtils.toDouble(input);
//                }
//            }
//        } while (!validData);

        // Step 8 - match and update other data
        sqlString = "update tmp_procurement_unit tpu  "
                + "LEFT JOIN tmp_product_catalog tpc ON tpu.LABEL=tpc.ItemName "
                + "LEFT JOIN (SELECT u.*, ul.LABEL_EN FROM ap_unit u LEFT JOIN ap_label ul ON u.LABEL_ID=ul.LABEL_ID) tuhd ON tpc.HeightUOM=tuhd.LABEL_EN "
                + "LEFT JOIN (SELECT u.*, ul.LABEL_EN FROM ap_unit u LEFT JOIN ap_label ul ON u.LABEL_ID=ul.LABEL_ID) tuld ON tpc.LengthUOM=tuld.LABEL_EN "
                + "LEFT JOIN (SELECT u.*, ul.LABEL_EN FROM ap_unit u LEFT JOIN ap_label ul ON u.LABEL_ID=ul.LABEL_ID) tuwd ON tpc.WidthUOM=tuwd.LABEL_EN "
                + "LEFT JOIN (SELECT u.*, ul.LABEL_EN FROM ap_unit u LEFT JOIN ap_label ul ON u.LABEL_ID=ul.LABEL_ID) tuwed ON tpc.WeightUOM=tuwed.LABEL_EN "
                + "set  "
                + "	tpu.HEIGHT=if(tpc.Height='', null, tpc.Height), "
                + "    tpu.LENGTH=if(tpc.Length='', null, tpc.Length), "
                + "    tpu.WIDTH=if(tpc.Width='', null, tpc.Width), "
                + "    tpu.WEIGHT=if(tpc.Weight='', null, tpc.Weight), "
                + "    tpu.UNITS_PER_CONTAINER=if(tpc.UnitsperContainer='', null, tpc.UnitsperContainer), "
                + "    tpu.LABELING=if(tpc.Labeling='', null, tpc.Labeling), "
                + "    tpu.SKU_CODE=if(tpc.ItemID='', null, tpc.ItemID), "
                + "    tpu.GTIN=if(tpc.GTIN='', null, tpc.GTIN), "
                + "    tpu.VENDOR_PRICE=null, "
                + "    tpu.APPROVED_TO_SHIPPED_LEAD_TIME=" + approvedToShipppedLeadTime + ","
                + "    tpu.HEIGHT_UNIT_ID=tuhd.UNIT_ID, "
                + "    tpu.LENGTH_UNIT_ID=tuld.UNIT_ID, "
                + "    tpu.WIDTH_UNIT_ID=tuwd.UNIT_ID, "
                + "    tpu.WEIGHT_UNIT_ID=tuwed.UNIT_ID";
        rows = this.jdbcTemplate.update(sqlString);
        logger.info(rows + " rows updated with matching data from product_catalog");

        // Step 9 - Match the Planning Unit, Multiplier and UnitId
        sqlString = "update tmp_procurement_unit tpu  "
                + "LEFT JOIN tmp_product_catalog tpc ON tpu.LABEL=tpc.ItemName "
                + "LEFT JOIN (SELECT pu.PLANNING_UNIT_ID, pul.LABEL_EN, pu.MULTIPLIER, pu.UNIT_ID FROM rm_planning_unit pu LEFT JOIN ap_label pul ON pu.LABEL_ID=pul.LABEL_ID) tpud ON tpc.ProductName=tpud.LABEL_EN "
                + "set tpu.PLANNING_UNIT_ID=tpud.PLANNING_UNIT_ID, tpu.MULTIPLIER=tpud.MULTIPLIER, tpu.UNIT_ID=tpud.UNIT_ID";
        this.jdbcTemplate.update(sqlString);
        logger.info(rows + " rows updated with matching data from planning_unit");

        // Step 10 - MAtch the Supplier Id
        sqlString = "update tmp_procurement_unit tpu  "
                + "LEFT JOIN tmp_product_catalog tpc ON tpu.LABEL=tpc.ItemName "
                + "LEFT JOIN (SELECT s.SUPPLIER_ID, sl.LABEL_EN from rm_supplier s LEFT JOIN ap_label sl ON s.LABEL_ID=sl.LABEL_ID) tsd ON tpc.Supplier=tsd.LABEL_EN "
                + "set tpu.SUPPLIER_ID=tsd.SUPPLIER_ID";
        rows = this.jdbcTemplate.update(sqlString);
        logger.info(rows + " rows updated with matching data from supplier");

        // Step 10a - Find max count for procurment_unit
        sqlString = "SELECT IFNULL(MAX(PROCUREMENT_UNIT_ID),0) from rm_procurement_unit";
        max = this.jdbcTemplate.queryForObject(sqlString, Integer.class);
        logger.info(max + " Current max for ProcurementUnit");

        // Step 11 - Insert into the procurement_unit
        sqlString = "insert into rm_procurement_unit  "
                + "SELECT null, tpu.PLANNING_UNIT_ID, tpu.LABEL_ID, tpu.UNIT_ID, tpu.MULTIPLIER, tpu.SUPPLIER_ID, tpu.WIDTH_UNIT_ID, tpu.WIDTH, tpu.HEIGHT_UNIT_ID, tpu.HEIGHT, tpu.LENGTH_UNIT_ID, tpu.LENGTH, tpu.WEIGHT_UNIT_ID, tpu.WEIGHT, tpu.UNITS_PER_CONTAINER, tpu.LABELING, 1, 1, now(), 1, now() from tmp_procurement_unit tpu WHERE tpu.SUPPLIER_ID is not null && tpu.PLANNING_UNIT_ID is not null AND tpu.LABEL_ID is not null";
        rows = this.jdbcTemplate.update(sqlString);
        logger.info(rows + " rows inserted into the procurement unit table");

        // Step 12 - Insert into the procurement_agent_procurement_unit
        sqlString = "insert ignore into rm_procurement_agent_procurement_unit SELECT null, pu.PROCUREMENT_UNIT_ID, 1, tpu.SKU_CODE, tpu.VENDOR_PRICE, tpu.APPROVED_TO_SHIPPED_LEAD_TIME, tpu.GTIN, 1, 1, now(), 1, now() from rm_procurement_unit pu LEFT JOIN tmp_procurement_unit tpu ON pu.LABEL_ID=tpu.LABEL_ID WHERE tpu.SKU_CODE is not null AND pu.PROCUREMENT_UNIT_ID>?";
        rows = this.jdbcTemplate.update(sqlString, max);
        logger.info(rows + " rows inserted into the procurement_agent_procurement_unit table");
    }

}
