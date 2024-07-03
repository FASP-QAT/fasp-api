/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.ARTMIS.dao.impl;

import cc.altius.FASP.ARTMIS.dao.ImportProductCatalogueDao;
import cc.altius.FASP.model.DTO.PlanningUnitArtmisPull;
import cc.altius.FASP.model.DTO.ProcurementUnitArtmisPull;
import cc.altius.FASP.model.DTO.rowMapper.PlanningUnitArtmisPullRowMapper;
import cc.altius.FASP.model.DTO.rowMapper.ProcurementUnitArtmisPullRowMapper;
import cc.altius.FASP.model.EmailTemplate;
import cc.altius.FASP.model.Emailer;
import cc.altius.FASP.model.ForecastingUnit;
import cc.altius.FASP.model.Supplier;
import cc.altius.FASP.model.TracerCategory;
import cc.altius.FASP.model.Unit;
import cc.altius.FASP.model.rowMapper.ForecastingUnitRowMapper;
import cc.altius.FASP.model.rowMapper.SupplierRowMapper;
import cc.altius.FASP.model.rowMapper.TracerCategoryRowMapper;
import cc.altius.FASP.model.rowMapper.UnitRowMapper;
import cc.altius.FASP.service.EmailService;
import cc.altius.FASP.utils.FileNameComparator;
import cc.altius.utils.DateUtils;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
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
@Primary
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

    @Value("${qat.homeFolder}")
    private String QAT_FILE_PATH;
    @Value("${qat.catalogFilePath}")
    private String CATALOG_FILE_PATH;
    @Value("${qat.catalogBkpFilePath}")
    private String BKP_CATALOG_FILE_PATH;
    @Value("${email.catalogToList}")
    private String toList;
    @Value("${email.catalogCCList}")
    private String ccList;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String br = "\n<br/>";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy hh:mm a");
    String date = simpleDateFormat.format(DateUtils.getCurrentDateObject(DateUtils.EST));

    @Override
    @Transactional
    public void importProductCatalogue(StringBuilder sb) throws ParserConfigurationException, SAXException, IOException, FileNotFoundException, BadSqlGrammarException {
        EmailTemplate emailTemplate = this.emailService.getEmailTemplateByEmailTemplateId(3);
        String[] subjectParam;
        String[] bodyParam;
        Emailer emailer;

        String fileList = "";
        File dir = new File(QAT_FILE_PATH + CATALOG_FILE_PATH);
        FileFilter fileFilter = new WildcardFileFilter("item_data_*.xml");
        File[] files = dir.listFiles(fileFilter);
        String sqlString;
        Arrays.sort(files, new FileNameComparator());
        logger.info("Going to start product catalogue import");
        sb.append("Going to start product catalogue import").append(br);
        for (int i = 0; i < files.length; i++) {
            fileList += " " + files[i];
            logger.info("File names---" + files[i]);
            sb.append("File names---" + files[i]).append(br);
        }
        if (files.length < 1) {
            subjectParam = new String[]{"Product Catalogue", "File not found"};
            bodyParam = new String[]{"Product Catalogue", "File not found", "File not found"};
            emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), toList, ccList, "", subjectParam, bodyParam);
            int emailerId = this.emailService.saveEmail(emailer);
            emailer.setEmailerId(emailerId);
            this.emailService.sendMail(emailer);
            logger.info("File not found");
            sb.append("File not found").append(br);
        } else {
            for (File fXmlFile : files) {
                String extension = "";
                int i = fXmlFile.getName().lastIndexOf('.');
                if (i > 0) {
                    extension = fXmlFile.getName().substring(i + 1);
                }
                if (!extension.equalsIgnoreCase("xml")) {
                    subjectParam = new String[]{"Product Catalogue", "File is not an xml"};
                    bodyParam = new String[]{"Product Catalogue", "File is not an xml", fileList};
                    emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), toList, ccList, "", subjectParam, bodyParam);
                    int emailerId = this.emailService.saveEmail(emailer);
                    emailer.setEmailerId(emailerId);
                    this.emailService.sendMail(emailer);
                    logger.error("File is not an xml");
                    sb.append("File is not an xml").append(br);
                } else {
                    logger.info("######################################################################");
                    sb.append("######################################################################");
                    logger.info("Starting import for file " + fXmlFile.getName());
                    sb.append("Starting import for file ").append(fXmlFile.getName()).append(br);
                    logger.info("######################################################################");
                    sb.append("######################################################################");
                    if (fXmlFile.length() == 0) {
                        sb.append("Skipping file since it is empty ").append(fXmlFile.getName()).append(br);
                        logger.info("Skipping file since it is empty " + fXmlFile.getName());
                        fXmlFile.renameTo(new File(QAT_FILE_PATH + BKP_CATALOG_FILE_PATH + fXmlFile.getName()));
                        logger.info("Product catalog file moved to processed folder successfully");
                        sb.append("Product catalog file moved to processed folder successfully").append(br);
                    } else {
                        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                        Document doc = dBuilder.parse(fXmlFile);
                        doc.getDocumentElement().normalize();

                        NodeList nList1 = doc.getElementsByTagName("itemdata");
                        MapSqlParameterSource[] batchParams = new MapSqlParameterSource[nList1.getLength()];
                        Map<String, Object> map = new HashedMap<String, Object>();
                        int x = 0;
                        logger.info("Going to drop tmp_product_catalog");
                        sb.append("Going to drop tmp_product_catalog").append(br);
                        sqlString = "DROP TEMPORARY TABLE IF EXISTS `tmp_product_catalog`";
//                        sqlString = "DROP TABLE IF EXISTS `tmp_product_catalog`";
                        this.jdbcTemplate.execute(sqlString);
                        logger.info("Successfully droped tmp_product_catalog");
                        sb.append("Successfully droped tmp_product_catalog").append(br);

                        logger.info("Going to create tmp_product_catalog");
                        sb.append("Going to create tmp_product_catalog").append(br);
                        sqlString = "CREATE TEMPORARY TABLE `tmp_product_catalog` ( "
                                //                        sqlString = "CREATE TABLE `tmp_product_catalog` ( "
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
                                + "  `Length` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                                + "  `Width` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                                + "  `GTIN` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                                + "  `Labeling` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                                + "  `ItemAvailable` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                                + "  `UnitsperCase` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                                + "  `UnitsperPallet` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                                + "  `UnitsperContainer` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                                + "  `EstPrice` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                                + "  `Euro1` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                                + "  `Euro2` varchar(250) COLLATE utf8_bin DEFAULT NULL, "
                                + "  KEY `idxProductNameNoPack` (`ProductNameNoPack`), "
                                + "  KEY `idxProductName` (`ProductName`), "
                                + "  KEY `idxItemName` (`ItemName`) "
                                + ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin";
                        this.jdbcTemplate.execute(sqlString);
                        logger.info("Successfully created tmp_product_catalog");
                        sb.append("Successfully created tmp_product_catalog").append(br);

                        logger.info("Going to insert into tmp_product_catalog");
                        sb.append("Going to insert into tmp_product_catalog").append(br);
                        sqlString = "INSERT INTO tmp_product_catalog VALUES(:taskOrderLongDescription,:commodityCouncilLongDesc,"
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
                                + ":unitsPerContainer,:wcsCataloguePrice,:euro1,:euro2);";
                        for (int temp2 = 0; temp2 < nList1.getLength(); temp2++) {
                            Node nNode1 = nList1.item(temp2);
                            if (nNode1.getNodeType() == Node.ELEMENT_NODE) {
                                Element dataRecordElement = (Element) nNode1;
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

                                map.put("planningUnitMoq", dataRecordElement.getElementsByTagName("planning_unit_moq").item(0).getTextContent());
                                String planningUnitPerPallet = dataRecordElement.getElementsByTagName("planning_unit_per_pallet").item(0).getTextContent();
                                map.put("planningUnitPallet", planningUnitPerPallet);

                                // To be used once ARTMIS confirm that they have implemented the split till then stick to Euro 1 only
                                try {
                                    String[] noOfPallets = planningUnitPerPallet.split("\\|");
                                    String euro1 = (noOfPallets[0].split("-")[1] != null && noOfPallets[0].split("-")[1] != "" ? noOfPallets[0].split("-")[1] : "0");
                                    String euro2 = (noOfPallets[1].split("-")[1] != null && noOfPallets[1].split("-")[1] != "" ? noOfPallets[1].split("-")[1] : "0");
                                    map.put("euro1", euro1);
                                    map.put("euro2", euro2);
                                } catch (Exception e) {
                                    logger.info("Planning Unit Per Pallet not found because there was an error " + e.getMessage());
                                    map.put("euro1", planningUnitPerPallet);
                                    map.put("euro2", null);
                                    logger.error("Error while pulling tracer category---" + e.getMessage());
                                }

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
                                map.put("itemLength", dataRecordElement.getElementsByTagName("item_length").item(0).getTextContent());
                                map.put("itemWidth", dataRecordElement.getElementsByTagName("item_width").item(0).getTextContent());
                                map.put("itemManufacturerGtinUpc", dataRecordElement.getElementsByTagName("item_manufacturer_gtin_upc").item(0).getTextContent());
                                map.put("itemLabelLanguages", dataRecordElement.getElementsByTagName("item_label_languages").item(0).getTextContent());
                                map.put("itemBuyable", dataRecordElement.getElementsByTagName("item_buyable").item(0).getTextContent());
                                map.put("itemUnitsPerCase", dataRecordElement.getElementsByTagName("item_units_per_case").item(0).getTextContent());
                                map.put("itemNumOfUnitsPallet", dataRecordElement.getElementsByTagName("item_num_of_units_pallet").item(0).getTextContent());
                                map.put("unitsPerContainer", dataRecordElement.getElementsByTagName("units_per_container").item(0).getTextContent());
                                map.put("wcsCataloguePrice", dataRecordElement.getElementsByTagName("wcs_catalog_price").item(0).getTextContent());
                                batchParams[x] = new MapSqlParameterSource(map);
                                x++;
                            }
                        }
                        int[] rows1 = namedParameterJdbcTemplate.batchUpdate(sqlString, batchParams);
                        logger.info("Successfully inserted into tmp_product_catalog records---" + rows1.length);
                        sb.append("Successfully inserted into tmp_product_catalog records---").append(rows1.length).append(br);
                        sqlString = "DELETE tpc.* FROM tmp_product_catalog tpc where tpc.TaskOrder ='UNKNOWN'";
                        int dRows = this.jdbcTemplate.update(sqlString);
                        logger.info("Delted rows from tmp_product_catalog because the TaskOrder was UNKNOWN ---" + dRows);
                        sb.append("Delted rows from tmp_product_catalog because the TaskOrder was UNKNOWN ---").append(dRows).append(br);
                        sqlString = "DELETE tpc.* FROM tmp_product_catalog tpc where length(trim(tpc.ProductID)) !=13";
                        dRows = this.jdbcTemplate.update(sqlString);
                        logger.info("Delted rows from tmp_product_catalog because the ProductId len is not 13 ---" + dRows);
                        sb.append("Delted rows from tmp_product_catalog because the ProductId len is not 13 ---").append(dRows).append(br);
                        sqlString = "SELECT COUNT(*) FROM tmp_product_catalog;";
                        int tmpCnt = this.jdbcTemplate.queryForObject(sqlString, Integer.class);
                        logger.info("Total rows inserted in tmp_product_catalog---" + tmpCnt);
                        sb.append("Total rows inserted in tmp_product_catalog---").append(tmpCnt).append(br);
                        try {
                            pullUnit(sb);
                        } catch (Exception e) {
                            subjectParam = new String[]{"Product Catalog", "Error while pulling units"};
                            bodyParam = new String[]{"Product Catalog", "Error while pulling units", e.getMessage()};
                            emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), toList, ccList, "", subjectParam, bodyParam);
                            int emailerId = this.emailService.saveEmail(emailer);
                            emailer.setEmailerId(emailerId);
                            this.emailService.sendMail(emailer);
                            logger.error("Error while pulling units---" + e.getMessage());
                        }
                        try {
                            pullTracerCategory(sb);
                        } catch (Exception e) {
                            subjectParam = new String[]{"Product Catalog", "Error while pulling tracer category"};
                            bodyParam = new String[]{"Product Catalog", "Error while pulling tracer category", e.getMessage()};
                            emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), toList, ccList, "", subjectParam, bodyParam);
                            int emailerId = this.emailService.saveEmail(emailer);
                            emailer.setEmailerId(emailerId);
                            this.emailService.sendMail(emailer);
                            logger.error("Error while pulling tracer category---" + e.getMessage());
                        }
                        try {
                            pullProductCategory(sb);
                        } catch (Exception e) {
                            subjectParam = new String[]{"Product Catalog", "Error while pulling product category"};
                            bodyParam = new String[]{"Product Catalog", "Error while pulling product category", e.getMessage()};
                            emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), toList, ccList, "", subjectParam, bodyParam);
                            int emailerId = this.emailService.saveEmail(emailer);
                            emailer.setEmailerId(emailerId);
                            this.emailService.sendMail(emailer);
                            logger.error("Error while pulling product category---" + e.getMessage());
                        }
                        try {
                            pullForecastingUnit(sb);
                        } catch (Exception e) {
                            subjectParam = new String[]{"Product Catalog", "Error while pulling forecasting unit"};
                            bodyParam = new String[]{"Product Catalog", "Error while pulling forecasting unit", e.getMessage()};
                            emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), toList, ccList, "", subjectParam, bodyParam);
                            int emailerId = this.emailService.saveEmail(emailer);
                            emailer.setEmailerId(emailerId);
                            this.emailService.sendMail(emailer);
                            logger.error("Error while pulling forecasting unit---" + e.getMessage());
                        }
                        try {
                            pullPlanningUnit(sb);
                        } catch (Exception e) {
                            subjectParam = new String[]{"Product Catalog", "Error while pulling planning unit"};
                            bodyParam = new String[]{"Product Catalog", "Error while pulling planning unit", e.getMessage()};
                            emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), toList, ccList, "", subjectParam, bodyParam);
                            int emailerId = this.emailService.saveEmail(emailer);
                            emailer.setEmailerId(emailerId);
                            this.emailService.sendMail(emailer);
                            logger.error("Error while pulling planning unit---" + e.getMessage());
                        }
                        try {
                            pullSupplier(sb);
                        } catch (Exception e) {
                            subjectParam = new String[]{"Product Catalog", "Error while pulling supplier"};
                            bodyParam = new String[]{"Product Catalog", "Error while pulling supplier", e.getMessage()};
                            emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), toList, ccList, "", subjectParam, bodyParam);
                            int emailerId = this.emailService.saveEmail(emailer);
                            emailer.setEmailerId(emailerId);
                            this.emailService.sendMail(emailer);
                            logger.error("Error while pulling supplier---" + e.getMessage());
                        }
                        try {
                            pullProcurementUnit(sb);
                        } catch (Exception e) {
                            subjectParam = new String[]{"Product Catalog", "Error while pulling procurement unit"};
                            bodyParam = new String[]{"Product Catalog", "Error while pulling procurement unit", e.getMessage()};
                            emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), toList, ccList, "", subjectParam, bodyParam);
                            int emailerId = this.emailService.saveEmail(emailer);
                            emailer.setEmailerId(emailerId);
                            this.emailService.sendMail(emailer);
                            logger.error("Error while pulling procurement unit---" + e.getMessage());
                        }
                        File directory = new File(QAT_FILE_PATH + BKP_CATALOG_FILE_PATH);
                        if (directory.isDirectory()) {
                            fXmlFile.renameTo(new File(QAT_FILE_PATH + BKP_CATALOG_FILE_PATH + fXmlFile.getName()));
                            logger.info("Product catalog file moved to processed folder successfully");
                            sb.append("Product catalog file moved to processed folder successfully").append(br);
                        } else {
                            subjectParam = new String[]{"Product Catalogue", "Backup directory does not exists"};
                            bodyParam = new String[]{"Product Catalogue", "Backup directory does not exists", "Backup directory does not exists"};
                            emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), toList, ccList, "", subjectParam, bodyParam);
                            int emailerId = this.emailService.saveEmail(emailer);
                            emailer.setEmailerId(emailerId);
                            this.emailService.sendMail(emailer);
                            logger.error("Backup directory does not exists");
                            sb.append("Backup directory does not exists").append(br);
                        }
                    }
                    logger.info("######################################################################");
                    sb.append("######################################################################").append(br);
                    logger.info("Completed import for file " + fXmlFile.getName());
                    sb.append("Completed import for file ").append(fXmlFile.getName()).append(br);
                    logger.info("######################################################################");
                    sb.append("######################################################################").append(br);

                }
            }
        }
    }

    @Transactional
    private void pullUnit(StringBuilder sb) {
        // --------------------------Unit Table-----------------------
        logger.info("------------------------------- Unit ------------------------------------");
        sb.append("------------------------------- Unit ------------------------------------").append(br);
        logger.info("Going to drop tmp_unit");
        sb.append("Going to drop tmp_unit").append(br);
        String sqlString = "DROP TEMPORARY TABLE IF EXISTS `tmp_unit`";
//        String sqlString = "DROP TABLE IF EXISTS `tmp_unit`";
        this.jdbcTemplate.execute(sqlString);

        logger.info("Successfully droped tmp_unit");
        sb.append("Successfully droped tmp_unit").append(br);
        logger.info("Going to create tmp_unit");
        sb.append("Going to create tmp_unit").append(br);
        sqlString = "CREATE TEMPORARY TABLE `tmp_unit` ( "
                //        sqlString = "CREATE TABLE `tmp_unit` ( "
                + " `ID` int(10) unsigned NOT NULL AUTO_INCREMENT, "
                + " `LABEL` varchar(200) COLLATE utf8_bin NOT NULL, "
                + " `UNIT_ID` int (10) unsigned DEFAULT NULL, "
                + " `LABEL_ID` int (10) unsigned DEFAULT NULL, "
                + " `DIMENSION_ID` int (10) unsigned DEFAULT NULL, "
                + " `FOUND` tinyint(1) unsigned DEFAULT 0,"
                + " PRIMARY KEY (`ID`), "
                + " UNIQUE INDEX `index2` (`LABEL` ASC)"
                + " ) ENGINE = InnoDB DEFAULT CHARSET = utf8 COLLATE = utf8_bin";
        this.jdbcTemplate.execute(sqlString);
        logger.info("Successfully created tmp_unit");
        sb.append("Successfully created tmp_unit").append(br);
        sqlString = "INSERT IGNORE INTO tmp_unit SELECT NULL, BaseUnit, NULL, NULL, NULL, 0 FROM tmp_product_catalog WHERE BaseUnit IS NOT NULL AND BaseUnit != '' GROUP BY BaseUnit";
        int cnt = this.jdbcTemplate.update(sqlString);
        logger.info(sqlString + " -> " + cnt);
        sb.append(sqlString).append(" -> ").append(cnt).append(br);

        sqlString = "INSERT IGNORE INTO tmp_unit SELECT NULL, OrderUOM, NULL, NULL, NULL, 0  FROM tmp_product_catalog WHERE OrderUOM IS NOT NULL AND OrderUOM != '' GROUP BY OrderUOM";
        cnt = this.jdbcTemplate.update(sqlString);
        logger.info(sqlString + " -> " + cnt);
        sb.append(sqlString).append(" -> ").append(cnt).append(br);
        sqlString = "INSERT IGNORE INTO tmp_unit SELECT NULL, WeightUOM, NULL, NULL, NULL, 0 FROM tmp_product_catalog WHERE WeightUOM IS NOT NULL AND WeightUOM != '' GROUP BY WeightUOM";
        cnt = this.jdbcTemplate.update(sqlString);
        logger.info(sqlString + " -> " + cnt);
        sb.append(sqlString).append(" -> ").append(cnt).append(br);
        sqlString = "INSERT IGNORE INTO tmp_unit SELECT NULL, HeightUOM, NULL, NULL, NULL, 0 FROM tmp_product_catalog WHERE HeightUOM IS NOT NULL AND HeightUOM != '' GROUP BY HeightUOM";
        cnt = this.jdbcTemplate.update(sqlString);
        logger.info(sqlString + " -> " + cnt);
        sb.append(sqlString).append(" -> ").append(cnt).append(br);

        sqlString = "SELECT COUNT(*) FROM tmp_unit;";
        cnt = this.jdbcTemplate.queryForObject(sqlString, Integer.class);
        logger.info("Total rows inserted in tmp_unit---" + cnt);
        sb.append("Total rows inserted in tmp_unit---").append(cnt).append(br);

        sqlString = "UPDATE tmp_unit tu "
                + "LEFT JOIN vw_unit u ON tu.LABEL = u.LABEL_EN OR tu.LABEL=u.UNIT_CODE "
                + "SET tu.UNIT_ID = u.UNIT_ID, tu.FOUND=1 WHERE u.UNIT_ID IS NOT NULL";
        int rows = this.jdbcTemplate.update(sqlString);
        logger.info(rows + " matched with the ap_unit table");
        sb.append(rows).append(" matched with the ap_unit table").append(br);

        logger.info("Setting the Dimension for any new rows to 3 - Eaches, rows updated:" + this.jdbcTemplate.update("UPDATE tmp_unit tu SET tu.DIMENSION_ID=3 WHERE tu.UNIT_ID IS NULL and tu.FOUND=0"));

        sqlString = "SELECT MAX(LABEL_ID) FROM ap_label";
        int max = this.jdbcTemplate.queryForObject(sqlString, Integer.class);

        sqlString = "SELECT u.UNIT_ID, u.LABEL UNIT_CODE, u.DIMENSION_ID, null `DIMENSION_LABEL_ID`, null `DIMENSION_LABEL_EN`,null `DIMENSION_LABEL_FR`,null `DIMENSION_LABEL_SP`,null `DIMENSION_LABEL_PR`, "
                + " u.LABEL_ID, u.LABEL `LABEL_EN`, null `LABEL_FR`, null `LABEL_SP`, null `LABEL_PR`, "
                + " 0 ACTIVE, null CREATED_DATE, null LAST_MODIFIED_DATE, null CB_USER_ID, null CB_USERNAME, null LMB_USER_ID, null LMB_USERNAME "
                + "FROM tmp_unit u where u.FOUND=0";
        SimpleJdbcInsert siLabel = new SimpleJdbcInsert(jdbcTemplate).withTableName("ap_label").usingGeneratedKeyColumns("LABEL_ID");
        SimpleJdbcInsert siUnit = new SimpleJdbcInsert(jdbcTemplate).withTableName("ap_unit");
        Map<String, Object> labelParams = new HashMap<>();
        Map<String, Object> unitParams = new HashMap<>();
        int curUserId = 1;
        Date curDate = DateUtils.getCurrentDateObject("EST");
        labelParams.put("CREATED_BY", curUserId);
        labelParams.put("CREATED_DATE", curDate);
        labelParams.put("LAST_MODIFIED_BY", curUserId);
        labelParams.put("LAST_MODIFIED_DATE", curDate);
        labelParams.put("ACTIVE", true);
        labelParams.put("LABEL_EN", "");
        labelParams.put("SOURCE_ID", 17);

        unitParams.put("CREATED_BY", curUserId);
        unitParams.put("CREATED_DATE", curDate);
        unitParams.put("LAST_MODIFIED_BY", curUserId);
        unitParams.put("LAST_MODIFIED_DATE", curDate);
        unitParams.put("ACTIVE", true);
        unitParams.put("UNIT_CODE", "0");
        unitParams.put("DIMENSION_ID", 0);
        unitParams.put("LABEL_ID", 0);
        for (Unit u : this.jdbcTemplate.query(sqlString, new UnitRowMapper())) {
            labelParams.replace("LABEL_EN", u.getLabel().getLabel_en());
            u.getLabel().setLabelId(siLabel.executeAndReturnKey(labelParams).intValue());
            unitParams.replace("UNIT_CODE", u.getUnitCode().length() > 20 ? u.getUnitCode().substring(0, 19) : u.getUnitCode());
            unitParams.replace("DIMENSION_ID", u.getDimension().getId());
            unitParams.replace("LABEL_ID", u.getLabel().getLabelId());
            siUnit.execute(unitParams);
        }

        sqlString = "SELECT COUNT(*) FROM ap_unit;";
        cnt = this.jdbcTemplate.queryForObject(sqlString, Integer.class);
        logger.info("Total rows available in ap_unit---" + cnt);
        sb.append("Total rows available in ap_unit---").append(cnt).append(br);
    }

    @Transactional
    private void pullTracerCategory(StringBuilder sb) {
        logger.info("------------------------------- Tracer Category ------------------------------------");
        sb.append("------------------------------- Tracer Category ------------------------------------").append(br);
        int max = 0;

        // Step 1 - Drop the table if it exists
//        String sqlString = "DROP TEMPORARY TABLE IF EXISTS `tmp_tracer_category`";
        String sqlString = "DROP TABLE IF EXISTS `tmp_tracer_category`";
        this.jdbcTemplate.update(sqlString);

        // Step 2 - Create the tmp table
        sqlString = "CREATE TEMPORARY TABLE `tmp_tracer_category` ( "
//                        sqlString = "CREATE TABLE `tmp_tracer_category` ( "
                + "  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT, "
                + "  `LABEL` varchar(255) COLLATE utf8_bin NOT NULL, "
                + "  `TRACER_CATEGORY_ID` int(10) unsigned DEFAULT NULL, "
                + "  `LABEL_ID` int (10) unsigned DEFAULT NULL, "
                + "  `FOUND` tinyint(1) unsigned default 0,"
                + "  PRIMARY KEY (`ID`) "
                + ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin";
        this.jdbcTemplate.update(sqlString);
        // Step 3 insert into the tmp_tracer all the data that you can get from 
        //        product_catalog that you just imported
        sqlString = "INSERT INTO tmp_tracer_category SELECT NULL, TracerCategory, NULL, NULL, 0 FROM tmp_product_catalog WHERE TracerCategory IS NOT NULL AND TracerCategory != '' GROUP BY TracerCategory";
        int rows = this.jdbcTemplate.update(sqlString);
        logger.info(rows + " inserted into the tmp_tracer_category table");
        sb.append(rows).append(" inserted into the tmp_tracer_category table").append(br);

        // Step 4 Match those records that are already present in the main tracer_category table
        sqlString = "UPDATE tmp_tracer_category ttc "
                + "LEFT JOIN vw_tracer_category tc ON ttc.LABEL=tc.LABEL_EN "
                + "SET ttc.TRACER_CATEGORY_ID=tc.TRACER_CATEGORY_ID, ttc.FOUND=1 "
                + "WHERE tc.TRACER_CATEGORY_ID IS NOT NULL";

        rows = this.jdbcTemplate.update(sqlString);
        logger.info(rows + " existing labels found");
        sb.append(rows).append(" existing labels found").append(br);

        // Step 5 Get the max count on the label table so that you can now work on data that you insert from here on
        sqlString = "SELECT "
                + " ttc.TRACER_CATEGORY_ID, 1 REALM_ID, null REALM_CODE, null REALM_LABEL_ID, null REALM_LABEL_EN, null REALM_LABEL_FR, null REALM_LABEL_SP, null REALM_LABEL_PR, "
                + " null LABEL_ID, ttc.LABEL LABEL_EN, ttc.LABEL LABEL_Fr, ttc.LABEL LABEL_SP, ttc.LABEL LABEL_PR, "
                + " 0 ACTIVE, null CREATED_DATE, null LAST_MODIFIED_DATE, null CB_USER_ID, null CB_USERNAME, null LMB_USER_ID, null LMB_USERNAME,null HEALTH_AREA_ID,null HA_LABEL_ID,null HA_LABEL_EN,null HA_LABEL_FR,null HA_LABEL_SP,null HA_LABEL_PR,NULL HEALTH_AREA_CODE "
                + "FROM tmp_tracer_category ttc WHERE ttc.FOUND=0";
        this.jdbcTemplate.query(sqlString, new TracerCategoryRowMapper());
        SimpleJdbcInsert siLabel = new SimpleJdbcInsert(jdbcTemplate).withTableName("ap_label").usingGeneratedKeyColumns("LABEL_ID");
        SimpleJdbcInsert siUnit = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_tracer_category");
        Map<String, Object> labelParams = new HashMap<>();
        Map<String, Object> tracerCategoryParams = new HashMap<>();
        int curUserId = 1;
        Date curDate = DateUtils.getCurrentDateObject("EST");
        labelParams.put("CREATED_BY", curUserId);
        labelParams.put("CREATED_DATE", curDate);
        labelParams.put("LAST_MODIFIED_BY", curUserId);
        labelParams.put("LAST_MODIFIED_DATE", curDate);
        labelParams.put("ACTIVE", true);
        labelParams.put("LABEL_EN", "");
        labelParams.put("SOURCE_ID", 27);

        tracerCategoryParams.put("CREATED_BY", curUserId);
        tracerCategoryParams.put("CREATED_DATE", curDate);
        tracerCategoryParams.put("LAST_MODIFIED_BY", curUserId);
        tracerCategoryParams.put("LAST_MODIFIED_DATE", curDate);
        tracerCategoryParams.put("ACTIVE", true);
        tracerCategoryParams.put("REALM_ID", 1);
        tracerCategoryParams.put("LABEL_ID", 0);
        for (TracerCategory tc : this.jdbcTemplate.query(sqlString, new TracerCategoryRowMapper())) {
            labelParams.replace("LABEL_EN", tc.getLabel().getLabel_en());
            tc.getLabel().setLabelId(siLabel.executeAndReturnKey(labelParams).intValue());
            tracerCategoryParams.replace("LABEL_ID", tc.getLabel().getLabelId());
            siUnit.execute(tracerCategoryParams);
        }

        sqlString = "SELECT COUNT(*) FROM rm_tracer_category;";
        int cnt = this.jdbcTemplate.queryForObject(sqlString, Integer.class);
        logger.info("Total rows available in rm_tracer_category ---" + cnt);
        sb.append("Total rows available in rm_tracer_category ---").append(cnt).append(br);
    }

    @Transactional
    private void pullProductCategory(StringBuilder sb) {
        //------------Product Category-------------------------
        logger.info("------------------------------- Product Category ------------------------------------");
        sb.append("------------------------------- Product Category ------------------------------------").append(br);
        // Step 1: See if there are any new Commodity Councils
        String sqlString = "SELECT tpc.CommodityCouncil FROM tmp_product_catalog tpc LEFT JOIN vw_product_category pc ON pc.REALM_ID=1 AND tpc.CommodityCouncil=right(pc.LABEL_EN, length(pc.LABEL_EN)-locate(\":\", pc.LABEL_EN)-1) WHERE pc.PRODUCT_CATEGORY_ID is null group by tpc.CommodityCouncil";
        logger.info("Checking if there are any new Commodity Councils");
        sb.append("Checking if there are any new Commodity Councils").append(br);
        List<String> newProductCategoryList = this.jdbcTemplate.queryForList(sqlString, String.class);
        if (newProductCategoryList.size() > 0) {
            logger.info("New Commodity Council found so proceeding to enter those into the Product Category table");
            sb.append("New Commodity Council found so proceeding to enter those into the Product Category table").append(br);
            for (String pc : newProductCategoryList) {
                // Step 2: Get the highest CC (Commodity Council no in Product Category Table
                sqlString = "SELECT max(cast(right(left(pc.LABEL_EN, locate(':', pc.LABEL_EN)-1), length(left(pc.LABEL_EN, locate(':', pc.LABEL_EN)))-3) as UNSIGNED)) `maxCc` FROM vw_product_category pc where pc.REALM_ID=1 AND length(pc.SORT_ORDER)=5";
                int maxCc = this.jdbcTemplate.queryForObject(sqlString, Integer.class) + 1;
                String newCc = "CC " + maxCc + ": " + pc;
                // Step 3: Insert that into the label table
                sqlString = "INSERT INTO `ap_label` (`LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`, `SOURCE_ID`) VALUES (? , null, null, null, 1, now(), 1, now(), 26)";
                this.jdbcTemplate.update(sqlString, newCc);
                int labelId = this.jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
                // Step 4: Then insert into the product_category table
                sqlString = "INSERT INTO `rm_product_category` (`REALM_ID`, `LABEL_ID`, `PARENT_PRODUCT_CATEGORY_ID`, `SORT_ORDER`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (1, ?, 0, ?, 1, 1, now(), 1, now())";
                String sortOrder = "00." + String.format("%02d", maxCc);
                this.jdbcTemplate.update(sqlString, labelId, sortOrder);
                int productCategoryId = this.jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
                logger.info("Added " + newCc + " to the Product Category table");
                sb.append("Added ").append(newCc).append(" to the Product Category table").append(br);
                // Step 5: Now find all the SubCategories that are from the new CC and insert them
                sqlString = "SELECT tpc.Subcategory FROM tmp_product_catalog tpc where tpc.CommodityCouncil=? group by tpc.Subcategory";
                List<String> subCategoryList = this.jdbcTemplate.queryForList(sqlString, String.class, pc);
                int cnt = 1;
                for (String sc : subCategoryList) {
                    // Step 6: Insert into the label table
                    sqlString = "INSERT INTO `ap_label` (`LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`, `SOURCE_ID`) VALUES (? , null, null, null, 1, now(), 1, now(), 26)";
                    this.jdbcTemplate.update(sqlString, sc);
                    labelId = this.jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
                    // Step:7 Insert into the product category table
                    sqlString = "INSERT INTO `rm_product_category` (`REALM_ID`, `LABEL_ID`, `PARENT_PRODUCT_CATEGORY_ID`, `SORT_ORDER`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (1, ?, ?, ?, 1, 1, now(), 1, now())";
                    this.jdbcTemplate.update(sqlString, labelId, productCategoryId, sortOrder + "." + String.format("%02d", cnt));
                    cnt++;
                    logger.info("Added " + sc + " under " + newCc + " to the ProductCategory table");
                    sb.append("Added ").append(sc).append(" under ").append(newCc).append(" to the ProductCategory table").append(br);
                }
            }
            // Step 9: Now find any new SubCategory that is not present in the ProductCategory table 
            logger.info("Checking if there are any new Subcategories that are not present in the Product Category table");
            sb.append("Checking if there are any new Subcategories that are not present in the Product Category table").append(br);
            sqlString = "SELECT tpc.Subcategory, pc2.SORT_ORDER, pc2.LABEL_EN, pc2.PRODUCT_CATEGORY_ID FROM tmp_product_catalog tpc LEFT JOIN vw_product_category pc1 ON pc1.REALM_ID=1 and pc1.LABEL_EN=tpc.Subcategory  LEFT JOIN vw_product_category pc2 ON pc2.REALM_ID=1 AND tpc.CommodityCouncil=right(pc2.LABEL_EN, length(pc2.LABEL_EN)-locate(':', pc2.LABEL_EN)-1) WHERE pc1.PRODUCT_CATEGORY_ID IS NULL  group by tpc.Subcategory order by pc2.PRODUCT_CATEGORY_ID, tpc.Subcategory";
            List<Map<String, Object>> subCategoryList = this.jdbcTemplate.queryForList(sqlString);
            for (Map<String, Object> sc : subCategoryList) {
                sqlString = "SELECT count(*) FROM vw_product_category pc WHERE pc.PARENT_PRODUCT_CATEGORY_ID=?";
                int maxCnt = this.jdbcTemplate.queryForObject(sqlString, Integer.class, sc.get("PRODUCT_CATEGORY_ID")) + 1;
                sqlString = "INSERT INTO `ap_label` (`LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`, `SOURCE_ID`) VALUES (? , null, null, null, 1, now(), 1, now(),26)";
                this.jdbcTemplate.update(sqlString, sc.get("Subcategory"));
                int labelId = this.jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
                // Step:7 Insert into the product category table
                sqlString = "INSERT INTO `rm_product_category` (`REALM_ID`, `LABEL_ID`, `PARENT_PRODUCT_CATEGORY_ID`, `SORT_ORDER`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (1, ?, ?, ?, 1, 1, now(), 1, now())";
                this.jdbcTemplate.update(sqlString, labelId, sc.get("PRODUCT_CATEGORY_ID"), sc.get("SORT_ORDER") + "." + String.format("%02d", maxCnt));
                logger.info("Added " + sc.get("Subcategory") + " under " + sc.get("LABEL_EN") + " to the ProductCategory table");
                sb.append("Added ").append(sc.get("Subcategory")).append(" under ").append(sc.get("LABEL_EN")).append(" to the ProductCategory table").append(br);
            }
        }
    }

    @Transactional
    private void pullForecastingUnit(StringBuilder sb) {
        EmailTemplate emailTemplate = this.emailService.getEmailTemplateByEmailTemplateId(3);
        String[] subjectParam;
        String[] bodyParam;
        Emailer emailer;
        logger.info("------------------------------- Forecasting Unit ------------------------------------");
        sb.append("------------------------------- Forecasting Unit ------------------------------------").append(br);
        //------------Forcasting Unit--------------------------
        // Step 1 - Drop the table if it exists
//        String sqlString = "DROP TEMPORARY TABLE IF EXISTS `tmp_forecasting_unit`";
        String sqlString = "DROP TABLE IF EXISTS `tmp_forecasting_unit`";
        this.jdbcTemplate.update(sqlString);

        // Step 2 - Create Temporary Table
//        sqlString = "CREATE TEMPORARY TABLE `tmp_forecasting_unit` (   "
                        sqlString = "CREATE TABLE `tmp_forecasting_unit` (   "
                + "    `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,   "
                + "    `LABEL` varchar(200) COLLATE utf8_bin NOT NULL,   "
                + "    `LABEL_ID` int (10) unsigned DEFAULT NULL,   "
                + "    `GENERIC_LABEL`varchar(200) COLLATE utf8_bin NULL,   "
                + "    `GENERIC_LABEL_ID` int (10) unsigned DEFAULT NULL,   "
                + "    `UNIT_LABEL_EN` VARCHAR (100) COLLATE utf8_bin NULL,  "
                + "    `COMMODITY_COUNCIL` VARCHAR(200) COLLATE utf8_bin NULL,  "
                + "    `SUB_CATEGORY` VARCHAR(200) COLLATE utf8_bin NULL,  "
                + "    `TRACER_CATEGORY` VARCHAR(200) COLLATE utf8_bin NULL,  "
                + "    `FORECASTING_UNIT_ID` INT(10) UNSIGNED DEFAULT NULL, "
                + "    `FOUND` TINYINT(1) UNSIGNED DEFAULT NULL, "
                + "    PRIMARY KEY (`ID`), "
                + "    INDEX `idxLabel` (`LABEL` ASC),  "
                + "    INDEX `idxGenericLabel` (`GENERIC_LABEL` ASC),  "
                + "    INDEX `idxForecastingUnit_forecastingUnitId_idx` (`FORECASTING_UNIT_ID` ASC),  "
                + "    INDEX `idxForecastingUnit_labelId_idx` (`LABEL_ID` ASC),  "
                + "    INDEX `idxForecastingUnit_unitId_idx` (`UNIT_LABEL_EN` ASC),  "
                + "    INDEX `idxForecastingUnit_genericLabelId_idx` (`GENERIC_LABEL_ID` ASC),  "
                + "    INDEX `idxForecastingUnit_commodityCouncil_idx` (`COMMODITY_COUNCIL` ASC),  "
                + "    INDEX `idxForecastingUnit_subCategory_idx` (`SUB_CATEGORY` ASC),  "
                + "    INDEX `idxForecastingUnit_tracerCategoryId_idx` (`TRACER_CATEGORY` ASC), "
                + "    INDEX `idxForecastingUnitFound` (`FOUND` ASC)"
                + ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin";
        this.jdbcTemplate.update(sqlString);

        // Step 3 insert into the tmp_label the ProductNameNoPack
        sqlString = "INSERT INTO tmp_forecasting_unit SELECT null, ProductNameNoPack, null, IF(TRIM(INN)='',null,TRIM(INN)), null, BaseUnit, CommodityCouncil, Subcategory, TracerCategory, 0, 0 FROM tmp_product_catalog tpc WHERE tpc.ProductNameNoPack IS NOT NULL AND tpc.ProductNameNoPack != '' group by tpc.ProductNameNoPack";
        int rows = this.jdbcTemplate.update(sqlString);
        logger.info(rows + " inserted into the tmp_label for ProductNameNoPack");
        sb.append(rows).append(" inserted into the tmp_label for ProductNameNoPack").append(br);

        // Step 4 Match those records that are already present in the main forecasting_unit table
        sqlString = "update tmp_forecasting_unit tfu LEFT JOIN vw_forecasting_unit fu ON tfu.LABEL=fu.LABEL_EN "
                + "set tfu.LABEL_ID = fu.LABEL_ID, tfu.FOUND=1, tfu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID  "
                + "where fu.FORECASTING_UNIT_ID is not null AND fu.REALM_ID=1";
        rows = this.jdbcTemplate.update(sqlString);
        logger.info(rows + " Forecasting units found");
        sb.append(rows).append(" Forecasting units found").append(br);

        // Step 4 Match the Generic names
        sqlString = "update tmp_forecasting_unit tfu "
                + "LEFT JOIN (SELECT fugl.LABEL_ID, fugl.LABEL_EN FROM rm_forecasting_unit fu LEFT JOIN ap_label fugl ON fu.GENERIC_LABEL_ID=fugl.LABEL_ID WHERE fu.REALM_ID=1 AND fu.GENERIC_LABEL_ID is not null group by fugl.LABEL_EN) gl ON tfu.GENERIC_LABEL=gl.LABEL_EN "
                + "set tfu.GENERIC_LABEL_ID = gl.LABEL_ID "
                + "where tfu.GENERIC_LABEL!='' AND tfu.GENERIC_LABEL IS NOT NULL";
        rows = this.jdbcTemplate.update(sqlString);
        logger.info(rows + " Generic names matched");
        sb.append(rows).append(" Generic names matched").append(br);

        sqlString = "SELECT GENERIC_LABEL FROM tmp_forecasting_unit tfu WHERE tfu.GENERIC_LABEL_ID IS NULL AND tfu.GENERIC_LABEL IS NOT NULL AND tfu.GENERIC_LABEL !=''";
        // Step 4a Create Generic names that did not match
        sb.append("generic name---" + this.jdbcTemplate.queryForList(sqlString, String.class));
        for (String genericLabel : this.jdbcTemplate.queryForList(sqlString, String.class)) {
            // Step 6: Insert into the label table
            sqlString = "INSERT INTO `ap_label` (`LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`, `SOURCE_ID`) VALUES (? , null, null, null, 1, now(), 1, now(), 29)";
            sb.append("ap_label---" + this.jdbcTemplate.update(sqlString, genericLabel));
            int labelId = this.jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
            sqlString = "update tmp_forecasting_unit tfu "
                    + "set tfu.GENERIC_LABEL_ID = ? "
                    + "where tfu.GENERIC_LABEL=?";
            this.jdbcTemplate.update(sqlString, labelId, genericLabel);
        }

        sqlString = "SELECT null FORECASTING_UNIT_ID, 1 REALM_ID, null `REALM_LABEL_ID`, null REALM_CODE, null `REALM_LABEL_EN`,null `REALM_LABEL_FR`,null `REALM_LABEL_SP`,null `REALM_LABEL_PR`, "
                + " fu.LABEL_ID, fu.LABEL `LABEL_EN`, null `LABEL_FR`, null `LABEL_SP`, null `LABEL_PR`, "
                + " fu.GENERIC_LABEL_ID, fu.GENERIC_LABEL `GENERIC_LABEL_EN`, null `GENERIC_LABEL_FR`, null `GENERIC_LABEL_SP`, null `GENERIC_LABEL_PR`, "
                + " null PRODUCT_CATEGORY_ID, null PRODUCT_CATEGORY_LABEL_ID, fu.SUB_CATEGORY PRODUCT_CATEGORY_LABEL_EN, fu.COMMODITY_COUNCIL PRODUCT_CATEGORY_LABEL_FR, null PRODUCT_CATEGORY_LABEL_SP, null PRODUCT_CATEGORY_LABEL_PR, "
                + " null TRACER_CATEGORY_ID, null TRACER_CATEGORY_LABEL_ID, fu.TRACER_CATEGORY TRACER_CATEGORY_LABEL_EN, null TRACER_CATEGORY_LABEL_FR, null TRACER_CATEGORY_LABEL_SP, null TRACER_CATEGORY_LABEL_PR, "
                + " null UNIT_ID, fu.UNIT_LABEL_EN UNIT_CODE, null UNIT_LABEL_ID, fu.UNIT_LABEL_EN UNIT_LABEL_EN, null UNIT_LABEL_FR, null UNIT_LABEL_SP, null UNIT_LABEL_PR, "
                + " 0 ACTIVE, null CREATED_DATE, null LAST_MODIFIED_DATE, null CB_USER_ID, null CB_USERNAME, null LMB_USER_ID, null LMB_USERNAME "
                + "FROM tmp_forecasting_unit fu where fu.FOUND=0";
        SimpleJdbcInsert siLabel = new SimpleJdbcInsert(jdbcTemplate).withTableName("ap_label").usingGeneratedKeyColumns("LABEL_ID");
        SimpleJdbcInsert siForecastingUnit = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_forecasting_unit");
        Map<String, Object> labelParams = new HashMap<>();
        Map<String, Object> forecastingUnitParams = new HashMap<>();
        int curUserId = 1;
        Date curDate = DateUtils.getCurrentDateObject("EST");
        labelParams.put("CREATED_BY", curUserId);
        labelParams.put("CREATED_DATE", curDate);
        labelParams.put("LAST_MODIFIED_BY", curUserId);
        labelParams.put("LAST_MODIFIED_DATE", curDate);
        labelParams.put("ACTIVE", true);
        labelParams.put("LABEL_EN", "");
        labelParams.put("SOURCE_ID", 28);

        forecastingUnitParams.put("CREATED_BY", curUserId);
        forecastingUnitParams.put("CREATED_DATE", curDate);
        forecastingUnitParams.put("LAST_MODIFIED_BY", curUserId);
        forecastingUnitParams.put("LAST_MODIFIED_DATE", curDate);
        forecastingUnitParams.put("ACTIVE", true);
        forecastingUnitParams.put("REALM_ID", 1);
        forecastingUnitParams.put("LABEL_ID", 0);
        forecastingUnitParams.put("GENERIC_LABEL_ID", 0);
        forecastingUnitParams.put("UNIT_ID", 0);
        forecastingUnitParams.put("PRODUCT_CATEGORY_ID", 0);
        forecastingUnitParams.put("TRACER_CATEGORY_ID", 0);

        for (ForecastingUnit fu : this.jdbcTemplate.query(sqlString, new ForecastingUnitRowMapper())) {
            try {
                sqlString = "SELECT UNIT_ID FROM vw_unit u WHERE u.UNIT_CODE=? OR u.LABEL_EN=? LIMIT 1";
                logger.info("unit id code---" + fu.getUnit().getCode());
                logger.info("unit id---" + fu.getUnit().getLabel().getLabel_en());
                logger.info("unit id---" + this.jdbcTemplate.queryForObject(sqlString, Integer.class, fu.getUnit().getCode(), fu.getUnit().getLabel().getLabel_en()));
                fu.getUnit().setId(this.jdbcTemplate.queryForObject(sqlString, Integer.class, fu.getUnit().getCode(), fu.getUnit().getLabel().getLabel_en()));
                forecastingUnitParams.replace("UNIT_ID", fu.getUnit().getId());
                sb.append("----------fu 1--------------").append(br);
                sqlString = "SELECT SORT_ORDER FROM vw_product_category pc WHERE pc.REALM_ID=1 AND pc.LABEL_EN LIKE '%" + fu.getProductCategory().getLabel().getLabel_fr() + "' AND length(pc.SORT_ORDER)=5";
                String sortOrder = this.jdbcTemplate.queryForObject(sqlString, String.class);
                sb.append("----------fu 2--------------").append(br);
                sqlString = "SELECT PRODUCT_CATEGORY_ID FROM vw_product_category pc WHERE pc.REALM_ID=1 AND pc.LABEL_EN=? AND pc.SORT_ORDER LIKE '" + sortOrder + "%'";
                fu.getProductCategory().setId(this.jdbcTemplate.queryForObject(sqlString, Integer.class, fu.getProductCategory().getLabel().getLabel_en()));
                forecastingUnitParams.replace("PRODUCT_CATEGORY_ID", fu.getProductCategory().getId());
                sb.append("----------fu 3--------------").append(br);
                sqlString = "SELECT TRACER_CATEGORY_ID FROM vw_tracer_category tc WHERE tc.REALM_ID=1 AND tc.LABEL_EN=?";
                fu.getTracerCategory().setId(this.jdbcTemplate.queryForObject(sqlString, Integer.class, fu.getTracerCategory().getLabel().getLabel_en()));
                forecastingUnitParams.replace("TRACER_CATEGORY_ID", fu.getTracerCategory().getId());
                sb.append("----------fu 4--------------").append(br);
                if (fu.getProductCategory().getId() != null
                        && fu.getTracerCategory().getId() != null
                        && fu.getProductCategory().getId() != 0
                        && fu.getTracerCategory().getId() != 0) {
                    labelParams.replace("LABEL_EN", fu.getLabel().getLabel_en());
                    labelParams.replace("SOURCE_ID", 28);
                    fu.getLabel().setLabelId(siLabel.executeAndReturnKey(labelParams).intValue());
                    forecastingUnitParams.replace("LABEL_ID", fu.getLabel().getLabelId());
                    if (fu.getGenericLabel().getLabel_en() != null) {
                        sb.append("----------fu 5--------------").append(br);
                        labelParams.replace("SOURCE_ID", 29);
                        labelParams.replace("LABEL_EN", fu.getGenericLabel().getLabel_en());
                        fu.getGenericLabel().setLabelId(siLabel.executeAndReturnKey(labelParams).intValue());
                        forecastingUnitParams.replace("GENERIC_LABEL_ID", fu.getGenericLabel().getLabelId());
                    } else {
                        sb.append("----------fu 6--------------").append(br);
                        forecastingUnitParams.replace("GENERIC_LABEL_ID", null);
                    }
                    sb.append("----------fu 7--------------").append(br);
                    siForecastingUnit.execute(forecastingUnitParams);
                    sb.append("----------fu 8--------------").append(br);
                } else {
                    logger.info("Skipping the Forecasting Unit " + fu.getLabel().getLabel_en() + "because either the ProductCategory or TracerCategory is not provided");
                    sb.append("Skipping the Forecasting Unit ").append(fu.getLabel().getLabel_en()).append("because either the ProductCategory or TracerCategory is not provided").append(br);
                    subjectParam = new String[]{"Product Catalog", "Skipping the Forecasting Unit " + fu.getLabel().getLabel_en()};
                    bodyParam = new String[]{"Product Catalog", "Skipping the Forecasting Unit " + fu.getLabel().getLabel_en(), "Skipping the Forecasting Unit " + fu.getLabel().getLabel_en() + "because either the ProductCategory or TracerCategory is not provided"};
                    emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), toList, ccList, "", subjectParam, bodyParam);
                    int emailerId = this.emailService.saveEmail(emailer);
                    emailer.setEmailerId(emailerId);
                    this.emailService.sendMail(emailer);
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.info("Skipping the Forecasting Unit " + fu.getLabel().getLabel_en() + " because there was an error " + e.getMessage());
                sb.append("Skipping the Forecasting Unit ").append(fu.getLabel().getLabel_en()).append(" because there was an error ").append(e.getMessage()).append(br);
                subjectParam = new String[]{"Product Catalog", "Error while pulling forecasting unit " + fu.getLabel().getLabel_en()};
                bodyParam = new String[]{"Product Catalog", "Error while pulling forecasting unit " + fu.getLabel().getLabel_en(), e.getMessage()};
                emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), toList, ccList, "", subjectParam, bodyParam);
                int emailerId = this.emailService.saveEmail(emailer);
                emailer.setEmailerId(emailerId);
                this.emailService.sendMail(emailer);
            }
        }

        // Update queries 
        String sqlStringUpdate = "UPDATE tmp_forecasting_unit tfu  "
                + "LEFT JOIN rm_forecasting_unit fu ON tfu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID  "
                + "LEFT JOIN vw_tracer_category tc ON tc.REALM_ID=1 AND tfu.TRACER_CATEGORY=tc.LABEL_EN  "
                + "LEFT JOIN vw_unit u ON tfu.UNIT_LABEL_EN=u.LABEL_EN  "
                + "LEFT JOIN vw_product_category pc ON pc.REALM_ID=1 AND tfu.SUB_CATEGORY LIKE CONCAT('%',pc.LABEL_EN)  "
                + "SET  "
                + "    fu.GENERIC_LABEL_ID=tfu.GENERIC_LABEL_ID,  "
                + "    fu.UNIT_ID=u.UNIT_ID,  "
                + "    fu.TRACER_CATEGORY_ID=tc.TRACER_CATEGORY_ID,  "
                + "    fu.PRODUCT_CATEGORY_ID=pc.PRODUCT_CATEGORY_ID,  "
                + "    fu.LAST_MODIFIED_BY= IF(fu.GENERIC_LABEL_ID!=tfu.GENERIC_LABEL_ID OR fu.UNIT_ID!=u.UNIT_ID OR fu.TRACER_CATEGORY_ID!=tc.TRACER_CATEGORY_ID OR fu.PRODUCT_CATEGORY_ID!=pc.PRODUCT_CATEGORY_ID, ?, fu.LAST_MODIFIED_BY),  "
                + "    fu.LAST_MODIFIED_DATE= IF(fu.GENERIC_LABEL_ID!=tfu.GENERIC_LABEL_ID OR fu.UNIT_ID!=u.UNIT_ID OR fu.TRACER_CATEGORY_ID!=tc.TRACER_CATEGORY_ID OR fu.PRODUCT_CATEGORY_ID!=pc.PRODUCT_CATEGORY_ID, ?, fu.LAST_MODIFIED_DATE) "
                + "WHERE tfu.FOUND=1 AND u.UNIT_ID IS NOT NULL AND tc.TRACER_CATEGORY_ID IS NOT NULL AND pc.PRODUCT_CATEGORY_ID IS NOT NULL";
        int rowCount = this.jdbcTemplate.update(sqlStringUpdate, curUserId, curDate);

        logger.info("Rows updated - " + rowCount);
        sb.append("Rows updated - ").append(rowCount).append(br);
        //Update end
        sqlString = "SELECT COUNT(*) FROM rm_forecasting_unit";
        int cnt = this.jdbcTemplate.queryForObject(sqlString, Integer.class);
        logger.info("Total rows available in rm_forecasting_unit ---" + cnt);
        sb.append("Total rows available in rm_forecasting_unit ---").append(cnt).append(br);
    }

    @Transactional
    private void pullPlanningUnit(StringBuilder sb) {
        EmailTemplate emailTemplate = this.emailService.getEmailTemplateByEmailTemplateId(3);
        String[] subjectParam;
        String[] bodyParam;
        Emailer emailer;
        logger.info("------------------------------- Planning Unit ------------------------------------");
        sb.append("------------------------------- Planning Unit ------------------------------------").append(br);
        String sqlString = "DROP TEMPORARY TABLE IF EXISTS tmp_planning_unit";
//        String sqlString = "DROP TABLE IF EXISTS tmp_planning_unit";
        this.jdbcTemplate.update(sqlString);

        sqlString = "CREATE TEMPORARY TABLE `tmp_planning_unit` (   "
                //                        sqlString = "CREATE TABLE `tmp_planning_unit` (   "
                + "     `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,   "
                + "     `PLANNING_UNIT_ID` int (10) unsigned DEFAULT NULL,   "
                + "     `LABEL` varchar(200) COLLATE utf8_bin NOT NULL,   "
                + "     `MULTIPLIER` varchar(10) DEFAULT null,  "
                + "     `UNIT` varchar(100) COLLATE utf8_bin NOT NULL,  "
                + "     `SKU_CODE` VARCHAR(13) NULL, "
                + "     `FORECASTING_UNIT` VARCHAR(200) COLLATE utf8_bin NOT NULL, "
                + "     `CATALOG_PRICE` DECIMAL(14,4) UNSIGNED DEFAULT NULL, "
                + "     `MOQ` INT(10) UNSIGNED DEFAULT NULL, "
                + "     `UNITS_PER_PALLET_EURO1` INT(10) UNSIGNED DEFAULT NULL, "
                + "     `UNITS_PER_PALLET_EURO2` INT(10) UNSIGNED DEFAULT NULL, "
                + "     `UNITS_PER_CONTAINER` INT(10) UNSIGNED DEFAULT NULL, "
                + "     `VOLUME` DECIMAL(18,6) UNSIGNED DEFAULT NULL, "
                + "     `WEIGHT` DECIMAL(18,6) UNSIGNED DEFAULT NULL, "
                + "     `FOUND` tinyint(1) unsigned default null, "
                + "     `DUPLICATE` tinyint(1) unsigned default null, "
                + "    PRIMARY KEY (`ID`), "
                + "    INDEX `idx_tmp_planning_unit_Id_idx` (`PLANNING_UNIT_ID` ASC),  "
                + "    INDEX `tmpPlanningUnit_label` (`LABEL` ASC),  "
                + "    INDEX `idx_tmp_planning_unit_unitId_idx` (`UNIT` ASC),  "
                + "    INDEX `idx_tmp_planning_unit_skuCode` (`SKU_CODE` ASC),  "
                + "    INDEX `idx_tmp_planning_unit_forecastingUnitId_idx` (`FORECASTING_UNIT` ASC),  "
                + "    INDEX `idx_tmp_planning_unit_found_idx` (`FOUND` ASC)      "
                + ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin";
        this.jdbcTemplate.update(sqlString);

        // Step 1 Insert int tmpPlanningUnit
        sqlString = "Insert into tmp_planning_unit SELECT null, null, ProductName, if(NoofBaseUnits='', null, NoofBaseUnits), OrderUOM, trim(ProductID), tpc.ProductNameNoPack, if(tpc.EstPrice='',null,tpc.EstPrice), if(PlanningUnitMOQ='', null,PlanningUnitMOQ), if(Euro1='',null,Euro1), if(Euro2='',null,Euro2), if(PlanningUnitsperContainer='',null,PlanningUnitsperContainer), if(PlanningUnitVolumem3='',null,PlanningUnitVolumem3), if(PlanningUnitWeightkg='',null,PlanningUnitWeightkg), 0, 0 FROM tmp_product_catalog tpc WHERE tpc.ProductName IS NOT NULL AND tpc.ProductName != '' and tpc.OrderUOM!='' AND tpc.OrderUOM is not null group by tpc.ProductName";
        int rows = this.jdbcTemplate.update(sqlString);
        logger.info(rows + " rows instered into the tmp planningUnit table");
        sb.append(rows).append(" rows instered into the tmp planningUnit table").append(br);

//        // Step 1.1 Find Duplicate SKU's in the same file
//        sqlString = "UPDATE tmp_planning_unit tpu LEFT JOIN (SELECT SKU_CODE, min(tpu.ID) MIN_ID FROM tmp_planning_unit tpu group by tpu.SKU_CODE HAVING count(*)>1) m ON tpu.ID=m.MIN_ID SET tpu.DUPLICATE = 1 WHERE m.MIN_ID IS NOT NULL";
//        rows = this.jdbcTemplate.update(sqlString);
//        logger.info(rows + " rows marked as duplicate in the tmp planningUnit table");
        // Step 2 Locate if the Planning Unit has already come in before and is not duplicate
        sqlString = "UPDATE tmp_planning_unit tpu LEFT JOIN rm_procurement_agent_planning_unit papu ON tpu.SKU_CODE=papu.SKU_CODE LEFT JOIN rm_planning_unit pu ON papu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID LEFT JOIN rm_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID SET tpu.PLANNING_UNIT_ID=papu.PLANNING_UNIT_ID, tpu.FOUND=1 WHERE fu.REALM_ID = 1 AND papu.PLANNING_UNIT_ID IS NOT NULL AND tpu.DUPLICATE=0";
        rows = this.jdbcTemplate.update(sqlString);
        logger.info(rows + " Planning units matched");
        sb.append(rows).append(" Planning units matched").append(br);

        sqlString = "SELECT tpu.*, u.UNIT_ID, fu.FORECASTING_UNIT_ID FROM tmp_planning_unit tpu LEFT JOIN vw_unit u ON u.UNIT_CODE=tpu.UNIT OR u.LABEL_EN=tpu.UNIT LEFT JOIN vw_forecasting_unit fu ON tpu.FORECASTING_UNIT=fu.LABEL_EN AND fu.REALM_ID=1 where tpu.FOUND=0 AND tpu.DUPLICATE=0";
        SimpleJdbcInsert siLabel = new SimpleJdbcInsert(jdbcTemplate).withTableName("ap_label").usingGeneratedKeyColumns("LABEL_ID");
        SimpleJdbcInsert siPlanningUnit = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_planning_unit").usingGeneratedKeyColumns("PLANNING_UNIT_ID");
        SimpleJdbcInsert siPapu = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_procurement_agent_planning_unit");
        Map<String, Object> labelParams = new HashMap<>();
        Map<String, Object> planningUnitParams = new HashMap<>();
        Map<String, Object> papuParams = new HashMap<>();
        int curUserId = 1;
        Date curDate = DateUtils.getCurrentDateObject("EST");
        labelParams.put("CREATED_BY", curUserId);
        labelParams.put("CREATED_DATE", curDate);
        labelParams.put("LAST_MODIFIED_BY", curUserId);
        labelParams.put("LAST_MODIFIED_DATE", curDate);
        labelParams.put("ACTIVE", true);
        labelParams.put("LABEL_EN", "");
        labelParams.put("SOURCE_ID", 30);

        planningUnitParams.put("CREATED_BY", curUserId);
        planningUnitParams.put("CREATED_DATE", curDate);
        planningUnitParams.put("LAST_MODIFIED_BY", curUserId);
        planningUnitParams.put("LAST_MODIFIED_DATE", curDate);
        planningUnitParams.put("ACTIVE", true);
        planningUnitParams.put("LABEL_ID", 0);
        planningUnitParams.put("FORECASTING_UNIT_ID", 0);
        planningUnitParams.put("UNIT_ID", 0);
        planningUnitParams.put("MULTIPLIER", 0);

        papuParams.put("PROCUREMENT_AGENT_ID", 1);
        papuParams.put("PLANNING_UNIT_ID", 0);
        papuParams.put("CREATED_BY", curUserId);
        papuParams.put("CREATED_DATE", curDate);
        papuParams.put("LAST_MODIFIED_BY", curUserId);
        papuParams.put("LAST_MODIFIED_DATE", curDate);
        papuParams.put("ACTIVE", true);
        papuParams.put("CATALOG_PRICE", 0);
        papuParams.put("SKU_CODE", "");
        papuParams.put("MOQ", 0);
        papuParams.put("UNITS_PER_PALLET_EURO1", 0);
        papuParams.put("UNITS_PER_PALLET_EURO2", 0);
        papuParams.put("UNITS_PER_CONTAINER", 0);
        papuParams.put("VOLUME", 0);
        papuParams.put("WEIGHT", 0);

        for (PlanningUnitArtmisPull pu : this.jdbcTemplate.query(sqlString, new PlanningUnitArtmisPullRowMapper())) {
            try {
                if (pu.getForecastingUnitId() != null && pu.getForecastingUnitId() != 0) {
                    labelParams.replace("LABEL_EN", pu.getLabel());
                    int labelId = siLabel.executeAndReturnKey(labelParams).intValue();
                    planningUnitParams.replace("LABEL_ID", labelId);

                    planningUnitParams.replace("UNIT_ID", pu.getUnitId());
                    planningUnitParams.replace("FORECASTING_UNIT_ID", pu.getForecastingUnitId());
                    planningUnitParams.replace("MULTIPLIER", pu.getMultiplier());
                    int planningUnitId = siPlanningUnit.executeAndReturnKey(planningUnitParams).intValue();

                    papuParams.replace("PLANNING_UNIT_ID", planningUnitId);
                    papuParams.replace("CATALOG_PRICE", pu.getCatalogPrice());
                    papuParams.replace("SKU_CODE", pu.getSkuCode());
                    papuParams.replace("MOQ", pu.getMoq());
                    papuParams.replace("UNITS_PER_PALLET_EURO1", pu.getUnitsPerPalletEuro1());
                    papuParams.replace("UNITS_PER_PALLET_EURO2", pu.getUnitsPerPalletEuro2());
                    papuParams.replace("UNITS_PER_CONTAINER", pu.getUnitsPerContainer());
                    papuParams.replace("VOLUME", pu.getVolume());
                    papuParams.replace("WEIGHT", pu.getWeight());
                    siPapu.execute(papuParams);
                } else {
                    logger.info("Skipping the Planning Unit " + pu.getLabel() + " since there is no ForecastingUnit defined");
                    sb.append("Skipping the Planning Unit ").append(pu.getLabel()).append(" since there is no ForecastingUnit defined").append(br);
                    subjectParam = new String[]{"Product Catalog", "Skipping the Planning Unit " + pu.getLabel()};
                    bodyParam = new String[]{"Product Catalog", "Skipping the Planning Unit " + pu.getLabel(), "Skipping the Planning Unit " + pu.getSkuCode() + " since there is no ForecastingUnit defined"};
                    emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), toList, ccList, "", subjectParam, bodyParam);
                    int emailerId = this.emailService.saveEmail(emailer);
                    emailer.setEmailerId(emailerId);
                    this.emailService.sendMail(emailer);
                }
            } catch (Exception e) {
                logger.info("Skipping the Planning Unit " + pu.getLabel() + " because there was an error " + e.getMessage());
                sb.append("Skipping the Planning Unit ").append(pu.getLabel()).append(" because there was an error ").append(e.getMessage()).append(br);
                subjectParam = new String[]{"Product Catalog", "Error while pulling planning unit " + pu.getLabel()};
                bodyParam = new String[]{"Product Catalog", "Error while pulling planning unit " + pu.getLabel(), e.getMessage()};
                emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), toList, ccList, "", subjectParam, bodyParam);
                int emailerId = this.emailService.saveEmail(emailer);
                emailer.setEmailerId(emailerId);
                this.emailService.sendMail(emailer);
            }
        }

        // Update planning unit start
//        sqlString = "SELECT tpu.*, null UNIT_ID, null FORECASTING_UNIT_ID FROM tmp_planning_unit tpu where tpu.FOUND=1";
//        for (PlanningUnitArtmisPull pu : this.jdbcTemplate.query(sqlString, new PlanningUnitArtmisPullRowMapper())) {
        sqlString = "UPDATE `tmp_planning_unit` tpu   "
                + "LEFT JOIN rm_planning_unit pu ON tpu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID  "
                + "LEFT JOIN ap_label pul ON pu.LABEL_ID=pul.LABEL_ID  "
                + "LEFT JOIN rm_procurement_agent_planning_unit papu ON papu.PROCUREMENT_AGENT_ID=1 AND papu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID  "
                + "LEFT JOIN vw_unit u on tpu.UNIT=u.LABEL_EN OR tpu.UNIT=u.UNIT_CODE "
                + "LEFT JOIN vw_forecasting_unit fu ON tpu.FORECASTING_UNIT=fu.LABEL_EN "
                + "SET   "
                + "    pul.LABEL_EN=tpu.`LABEL`,   "
                + "    pu.MULTIPLIER=tpu.`MULTIPLIER`,   "
                + "    pu.UNIT_ID=u.`UNIT_ID`,   "
                + "    pu.FORECASTING_UNIT_ID=fu.`FORECASTING_UNIT_ID`,   "
                + "    pu.LAST_MODIFIED_BY=IF(pul.LABEL_EN!=tpu.`LABEL` OR pu.MULTIPLIER!=tpu.`MULTIPLIER` OR pu.UNIT_ID!=u.`UNIT_ID` OR pu.FORECASTING_UNIT_ID!=fu.`FORECASTING_UNIT_ID`, ?, pu.LAST_MODIFIED_BY), "
                + "    pu.LAST_MODIFIED_DATE=IF(pul.LABEL_EN!=tpu.`LABEL` OR pu.MULTIPLIER!=tpu.`MULTIPLIER` OR pu.UNIT_ID!=u.`UNIT_ID` OR pu.FORECASTING_UNIT_ID!=fu.`FORECASTING_UNIT_ID`, ?, pu.LAST_MODIFIED_DATE), "
                + "    papu.CATALOG_PRICE=tpu.`CATALOG_PRICE`,   "
                + "    papu.MOQ=tpu.`MOQ`,   "
                + "    papu.UNITS_PER_PALLET_EURO1=tpu.`UNITS_PER_PALLET_EURO1`,   "
                + "    papu.UNITS_PER_PALLET_EURO2=tpu.`UNITS_PER_PALLET_EURO2`,   "
                + "    papu.UNITS_PER_CONTAINER=tpu.`UNITS_PER_CONTAINER`,   "
                + "    papu.VOLUME=tpu.`VOLUME`,   "
                + "    papu.WEIGHT=tpu.`WEIGHT`,  "
                + "    papu.LAST_MODIFIED_BY=IF(papu.CATALOG_PRICE!=tpu.`CATALOG_PRICE` OR papu.MOQ!=tpu.`MOQ` OR papu.UNITS_PER_PALLET_EURO1!=tpu.`UNITS_PER_PALLET_EURO1` OR papu.UNITS_PER_PALLET_EURO2!=tpu.`UNITS_PER_PALLET_EURO2` OR papu.UNITS_PER_CONTAINER!=tpu.`UNITS_PER_CONTAINER` OR papu.VOLUME!=tpu.`VOLUME` OR papu.WEIGHT!=tpu.`WEIGHT`, ?, papu.LAST_MODIFIED_BY), "
                + "    papu.LAST_MODIFIED_DATE=IF(papu.CATALOG_PRICE!=tpu.`CATALOG_PRICE` OR papu.MOQ!=tpu.`MOQ` OR papu.UNITS_PER_PALLET_EURO1!=tpu.`UNITS_PER_PALLET_EURO1` OR papu.UNITS_PER_PALLET_EURO2!=tpu.`UNITS_PER_PALLET_EURO2` OR papu.UNITS_PER_CONTAINER!=tpu.`UNITS_PER_CONTAINER` OR papu.VOLUME!=tpu.`VOLUME` OR papu.WEIGHT!=tpu.`WEIGHT`, ?, papu.LAST_MODIFIED_DATE) "
                + "WHERE tpu.FOUND=1 AND fu.FORECASTING_UNIT_ID IS NOT NULL AND u.UNIT_ID IS NOT NULL";
        rows = this.jdbcTemplate.update(sqlString, curUserId, curDate, curUserId, curDate);
        logger.info("No of rows updated in rm_planning_unit --" + rows);
        sb.append("No of rows updated in rm_planning_unit --").append(rows).append(br);
//    }
        // Update planning unit end

        sqlString = "select count(*) from rm_planning_unit;";
        int cnt = this.jdbcTemplate.queryForObject(sqlString, Integer.class
        );
        logger.info("No of rows after insertion in rm_planning_unit --" + cnt);
        sb.append("No of rows after insertion in rm_planning_unit --").append(cnt).append(br);
    }

    @Transactional
    private void pullSupplier(StringBuilder sb) {
        //------------Supplier----------------
        logger.info("------------------------------- Suppliers ------------------------------------");
        sb.append("------------------------------- Suppliers ------------------------------------").append(br);
        String sqlString = "DROP TEMPORARY TABLE IF EXISTS `tmp_supplier`";
//        String sqlString = "DROP TABLE IF EXISTS `tmp_supplier`";
        this.jdbcTemplate.execute(sqlString);

        sqlString = "CREATE TEMPORARY TABLE `tmp_supplier` ( "
                //        sqlString = "CREATE TABLE `tmp_supplier` ( "
                + " `ID` int(10) unsigned NOT NULL AUTO_INCREMENT, "
                + " `LABEL` varchar(200) COLLATE utf8_bin NOT NULL, "
                + " `SUPPLIER_ID` int (10) unsigned DEFAULT NULL, "
                + " `LABEL_ID` int (10) unsigned DEFAULT NULL, "
                + " `FOUND` tinyint(1) UNSIGNED DEFAULT NULL, "
                + " PRIMARY KEY (`ID`), "
                + " UNIQUE INDEX `index3` (`LABEL` ASC) "
                + " ) ENGINE = InnoDB DEFAULT CHARSET = utf8 COLLATE = utf8_bin";
        this.jdbcTemplate.execute(sqlString);

        sqlString = "INSERT INTO tmp_supplier "
                + "SELECT NULL, Supplier, NULL, NULL, 0 "
                + "FROM tmp_product_catalog tpc "
                + "WHERE Supplier IS NOT NULL AND Supplier != '' "
                + "GROUP BY Supplier;";
        int cnt = this.jdbcTemplate.update(sqlString);
        logger.info("No of rows inserted into tmp_supplier---" + cnt);
        sb.append("No of rows inserted into tmp_supplier---").append(cnt).append(br);

        sqlString = "UPDATE tmp_supplier ts "
                + "LEFT JOIN vw_supplier s ON ts.`LABEL` = s.`LABEL_EN` "
                + "SET ts.SUPPLIER_ID = s.`SUPPLIER_ID`,ts.FOUND=1 "
                + "WHERE s.REALM_ID=1 AND s.SUPPLIER_ID IS NOT NULL";
        this.jdbcTemplate.update(sqlString);

        sqlString = "SELECT s.SUPPLIER_ID, 1 REALM_ID, null `REALM_LABEL_ID`, null REALM_CODE, null `REALM_LABEL_EN`,null `REALM_LABEL_FR`,null `REALM_LABEL_SP`,null `REALM_LABEL_PR`, "
                + " s.LABEL_ID, s.LABEL `LABEL_EN`, null `LABEL_FR`, null `LABEL_SP`, null `LABEL_PR`, "
                + " 0 ACTIVE, null CREATED_DATE, null LAST_MODIFIED_DATE, null CB_USER_ID, null CB_USERNAME, null LMB_USER_ID, null LMB_USERNAME "
                + "FROM tmp_supplier s where s.FOUND=0";
        SimpleJdbcInsert siLabel = new SimpleJdbcInsert(jdbcTemplate).withTableName("ap_label").usingGeneratedKeyColumns("LABEL_ID");
        SimpleJdbcInsert siSupplier = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_supplier");
        Map<String, Object> labelParams = new HashMap<>();
        Map<String, Object> supplierParams = new HashMap<>();
        int curUserId = 1;
        Date curDate = DateUtils.getCurrentDateObject("EST");
        labelParams.put("CREATED_BY", curUserId);
        labelParams.put("CREATED_DATE", curDate);
        labelParams.put("LAST_MODIFIED_BY", curUserId);
        labelParams.put("LAST_MODIFIED_DATE", curDate);
        labelParams.put("ACTIVE", true);
        labelParams.put("LABEL_EN", "");
        labelParams.put("SOURCE_ID", 33);

        supplierParams.put("CREATED_BY", curUserId);
        supplierParams.put("CREATED_DATE", curDate);
        supplierParams.put("LAST_MODIFIED_BY", curUserId);
        supplierParams.put("LAST_MODIFIED_DATE", curDate);
        supplierParams.put("ACTIVE", true);
        supplierParams.put("REALM_ID", 1);
        supplierParams.put("LABEL_ID", 0);
        for (Supplier s : this.jdbcTemplate.query(sqlString, new SupplierRowMapper())) {
            labelParams.replace("LABEL_EN", s.getLabel().getLabel_en());
            s.getLabel().setLabelId(siLabel.executeAndReturnKey(labelParams).intValue());
            supplierParams.replace("LABEL_ID", s.getLabel().getLabelId());
            siSupplier.execute(supplierParams);
        }
        sqlString = "SELECT COUNT(*) FROM rm_supplier";
        cnt = this.jdbcTemplate.queryForObject(sqlString, Integer.class);
        logger.info("Total rows available in rm_supplier---" + cnt);
        sb.append("Total rows available in rm_supplier---").append(cnt).append(br);
    }

    @Transactional
    private void pullProcurementUnit(StringBuilder sb) {
        EmailTemplate emailTemplate = this.emailService.getEmailTemplateByEmailTemplateId(3);
        String[] subjectParam;
        String[] bodyParam;
        Emailer emailer;
        //----------Procurement Unit---------------
        logger.info("------------------------------- Procurement Unit ------------------------------------");
        sb.append("------------------------------- Procurement Unit ------------------------------------").append(br);
        String sqlString = "DROP TEMPORARY TABLE IF EXISTS tmp_procurement_unit";
//        String sqlString = "DROP TABLE IF EXISTS tmp_procurement_unit";
        this.jdbcTemplate.update(sqlString);

        sqlString = "CREATE TEMPORARY TABLE `tmp_procurement_unit` (  "
                //        sqlString = "CREATE TABLE `tmp_procurement_unit` (  "
                + "	`ID` int(10) unsigned NOT NULL AUTO_INCREMENT,  "
                + "    `PROCUREMENT_UNIT_ID` int(10) UNSIGNED DEFAULT NULL,  "
                + "    `LABEL` varchar(200) COLLATE utf8_bin NOT NULL,  "
                + "    `MULTIPLIER` double (12,2) UNSIGNED DEFAULT null, "
                + "    `UNIT_ID` int (10) unsigned DEFAULT NULL, "
                + "    `PLANNING_UNIT_ID` int (10) unsigned DEFAULT NULL, "
                + "    `SUPPLIER_ID` int (10) unsigned DEFAULT NULL, "
                + "    `WIDTH` decimal (16,6) unsigned DEFAULT NULL, "
                + "    `LENGTH_UNIT_ID` int (10) unsigned DEFAULT NULL, "
                + "    `LENGTH` decimal (16,6) unsigned DEFAULT NULL, "
                + "    `HEIGHT` decimal (16,6) unsigned DEFAULT NULL, "
                + "    `VOLUME_UNIT_ID` int (10) unsigned DEFAULT NULL, "
                + "    `VOLUME` decimal (16,6) unsigned DEFAULT NULL, "
                + "    `WEIGHT_UNIT_ID` int (10) unsigned DEFAULT NULL, "
                + "    `WEIGHT` decimal (16,6) unsigned DEFAULT NULL, "
                + "    `UNITS_PER_CASE` INT (10) UNSIGNED DEFAULT NULL, "
                + "    `UNITS_PER_PALLET_EURO1` INT (10) UNSIGNED DEFAULT NULL, "
                + "    `UNITS_PER_PALLET_EURO2` INT (10) UNSIGNED DEFAULT NULL, "
                + "    `UNITS_PER_CONTAINER` int (10) unsigned DEFAULT NULL, "
                + "    `LABELLING` Varchar(200) DEFAULT NULL, "
                + "    `SKU_CODE` Varchar(200) DEFAULT NULL, "
                + "    `GTIN` Varchar(200) DEFAULT NULL, "
                + "    `VENDOR_PRICE` Varchar(200) DEFAULT NULL, "
                + "    `APPROVED_TO_SHIPPED_LEAD_TIME` DECIMAL(12,2) DEFAULT NULL, "
                + "    `FOUND` TINYINT(1) UNSIGNED DEFAULT 0, "
                + "    CONSTRAINT PRIMARY KEY (`ID`),  "
                + "     INDEX `idx_procurementUnit1` (`LABEL` ASC), "
                + "     INDEX `idx_procurementUnit2` (`PROCUREMENT_UNIT_ID` ASC), "
                + "     INDEX `idx_procurementUnit3` (`UNIT_ID` ASC), "
                + "     INDEX `idx_procurementUnit4` (`PLANNING_UNIT_ID` ASC), "
                + "     INDEX `idx_procurementUnit5` (`VOLUME_UNIT_ID` ASC), "
                + "     INDEX `idx_procurementUnit7` (`LENGTH_UNIT_ID` ASC), "
                + "     INDEX `idx_procurementUnit8` (`WEIGHT_UNIT_ID` ASC), "
                + "     INDEX `idx_procurementUnit9` (`FOUND` ASC)"
                + ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin";
        this.jdbcTemplate.update(sqlString);

        Double approvedToShipppedLeadTime = 3.0;

        // Step 1 - Insert into the tmp_procurement table
        sqlString = "INSERT INTO `tmp_procurement_unit` ( "
                + "     `ID`,`LABEL`,`PROCUREMENT_UNIT_ID`,`MULTIPLIER`,`UNIT_ID`, "
                + "     `PLANNING_UNIT_ID`,`SUPPLIER_ID`,`WIDTH`,`LENGTH_UNIT_ID`, `LENGTH`,"
                + "     `HEIGHT`, `WEIGHT_UNIT_ID`, `WEIGHT`, `VOLUME_UNIT_ID`, `VOLUME`, "
                + "     `UNITS_PER_CASE`,`UNITS_PER_PALLET_EURO1`,`UNITS_PER_PALLET_EURO2`, `UNITS_PER_CONTAINER`,`LABELLING`,`SKU_CODE`, "
                + "     `GTIN`,`VENDOR_PRICE`,`APPROVED_TO_SHIPPED_LEAD_TIME`,`FOUND`) "
                + "SELECT  "
                + "     null,tpc.ItemName,null,1,u.UNIT_ID, "
                + "     papu.PLANNING_UNIT_ID, s.SUPPLIER_ID, IF(tpc.Width='', NULL, tpc.Width), uh.UNIT_ID, IF(tpc.Length='', NULL, tpc.Length), "
                + "     IF(tpc.Height='', NULL, tpc.Height), uw.UNIT_ID, IF(tpc.Weight='', NULL, tpc.Weight), null, null, "
                + "     IF(tpc.UnitsperCase='', NULL, tpc.UnitsperCase), IF(tpc.UnitsperPallet='', NULL, tpc.UnitsperPallet), null, IF(tpc.UnitsperContainer='', NULL, tpc.UnitsperContainer), IF(tpc.Labeling='', NULL, tpc.Labeling), tpc.ItemID, "
                + "     IF(tpc.GTIN='', NULL, tpc.GTIN), IF(tpc.EstPrice='',null, tpc.EstPrice), ?, 0 "
                + "FROM tmp_product_catalog tpc  "
                + "LEFT JOIN vw_unit uh ON tpc.HeightUOM=uh.LABEL_EN OR tpc.HeightUOM=uh.UNIT_CODE "
                + "LEFT JOIN vw_unit uw ON tpc.WeightUOM=uw.LABEL_EN OR tpc.WeightUOM=uw.UNIT_CODE "
                + "LEFT JOIN vw_unit u ON tpc.OrderUOM=u.LABEL_EN OR tpc.OrderUOM=u.UNIT_CODE "
                + "LEFT JOIN rm_procurement_agent_planning_unit papu  ON tpc.ProductID=papu.SKU_CODE AND papu.PROCUREMENT_AGENT_ID=1 "
                + "LEFT JOIN rm_planning_unit pu ON papu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID "
                + "LEFT JOIN vw_supplier s ON tpc.Supplier=s.LABEL_EN "
                + "WHERE tpc.ItemID!=''AND tpc.ItemID is not NULL AND length(tpc.ItemID)=15";
        int rows = this.jdbcTemplate.update(sqlString, approvedToShipppedLeadTime);
        logger.info(rows + " rows inserted into the tmp_procurement table");
        sb.append(rows).append(" rows inserted into the tmp_procurement table").append(br);

        // Step 2 - Find already existing procurement_units based on SKU_CODE
        sqlString = "update tmp_procurement_unit tpu LEFT JOIN rm_procurement_agent_procurement_unit papu ON tpu.SKU_CODE=papu.SKU_CODE and papu.PROCUREMENT_AGENT_ID=1 set tpu.PROCUREMENT_UNIT_ID=papu.PROCUREMENT_UNIT_ID, tpu.FOUND=1 where papu.PROCUREMENT_UNIT_ID is not null";
        rows = this.jdbcTemplate.update(sqlString);
        logger.info(rows + " procurment units already existed");
        sb.append(rows).append(" procurment units already existed").append(br);

        sqlString = "SELECT tpu.* FROM tmp_procurement_unit tpu WHERE tpu.FOUND=0";
        SimpleJdbcInsert siLabel = new SimpleJdbcInsert(jdbcTemplate).withTableName("ap_label").usingGeneratedKeyColumns("LABEL_ID");
        SimpleJdbcInsert siProcurementUnit = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_procurement_unit").usingGeneratedKeyColumns("PROCUREMENT_UNIT_ID");
        SimpleJdbcInsert siPapu = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_procurement_agent_procurement_unit");
        Map<String, Object> labelParams = new HashMap<>();
        Map<String, Object> procurementUnitParams = new HashMap<>();
        Map<String, Object> papuParams = new HashMap<>();
        int curUserId = 1;
        Date curDate = DateUtils.getCurrentDateObject("EST");
        labelParams.put("CREATED_BY", curUserId);
        labelParams.put("CREATED_DATE", curDate);
        labelParams.put("LAST_MODIFIED_BY", curUserId);
        labelParams.put("LAST_MODIFIED_DATE", curDate);
        labelParams.put("ACTIVE", true);
        labelParams.put("LABEL_EN", "");
        labelParams.put("SOURCE_ID", 31);

        procurementUnitParams.put("CREATED_BY", curUserId);
        procurementUnitParams.put("CREATED_DATE", curDate);
        procurementUnitParams.put("LAST_MODIFIED_BY", curUserId);
        procurementUnitParams.put("LAST_MODIFIED_DATE", curDate);
        procurementUnitParams.put("ACTIVE", true);
        procurementUnitParams.put("LABEL_ID", 0);
        procurementUnitParams.put("PLANNING_UNIT_ID", 0);
        procurementUnitParams.put("UNIT_ID", 0);
        procurementUnitParams.put("MULTIPLIER", 0);
        procurementUnitParams.put("SUPPLIER_ID", 0);
        procurementUnitParams.put("WIDTH_QTY", 0);
        procurementUnitParams.put("HEIGHT_QTY", 0);
        procurementUnitParams.put("LENGTH_QTY", 0);
        procurementUnitParams.put("LENGTH_UNIT_ID", 0);
        procurementUnitParams.put("WEIGHT_QTY", 0);
        procurementUnitParams.put("WEIGHT_UNIT_ID", 0);
        procurementUnitParams.put("VOLUME_QTY", 0);
        procurementUnitParams.put("VOLUME_UNIT_ID", 0);
        procurementUnitParams.put("UNITS_PER_CASE", 0);
        procurementUnitParams.put("UNITS_PER_PALLET_EURO1", 0);
        procurementUnitParams.put("UNITS_PER_PALLET_EURO2", 0);
        procurementUnitParams.put("UNITS_PER_CONTAINER", 0);
        procurementUnitParams.put("LABELLING", "");

        papuParams.put("CREATED_BY", curUserId);
        papuParams.put("CREATED_DATE", curDate);
        papuParams.put("LAST_MODIFIED_BY", curUserId);
        papuParams.put("LAST_MODIFIED_DATE", curDate);
        papuParams.put("ACTIVE", true);
        papuParams.put("PROCUREMENT_UNIT_ID", 0);
        papuParams.put("PROCUREMENT_AGENT_ID", 1);
        papuParams.put("SKU_CODE", "");
        papuParams.put("GTIN", "");
        papuParams.put("VENDOR_PRICE", 0);
        papuParams.put("ACTIVE", true);
        papuParams.put("APPROVED_TO_SHIPPED_LEAD_TIME", 3);

        for (ProcurementUnitArtmisPull pu : this.jdbcTemplate.query(sqlString, new ProcurementUnitArtmisPullRowMapper())) {
            try {
                if (pu.getPlanningUnitId() != null && pu.getPlanningUnitId() != 0) {
                    if (pu.getSupplierId() != null && pu.getSupplierId() != 0) {
                        labelParams.replace("LABEL_EN", pu.getLabel());
                        int labelId = siLabel.executeAndReturnKey(labelParams).intValue();
                        procurementUnitParams.replace("LABEL_ID", labelId);

                        procurementUnitParams.replace("PLANNING_UNIT_ID", pu.getPlanningUnitId());
                        procurementUnitParams.replace("UNIT_ID", pu.getUnitId());
                        procurementUnitParams.replace("MULTIPLIER", pu.getMultiplier());
                        procurementUnitParams.replace("SUPPLIER_ID", pu.getSupplierId());
                        procurementUnitParams.replace("WIDTH_QTY", pu.getWidth());
                        procurementUnitParams.replace("HEIGHT_QTY", pu.getHeight());
                        procurementUnitParams.replace("LENGTH_QTY", pu.getLength());
                        procurementUnitParams.replace("LENGTH_UNIT_ID", pu.getLengthUnitId());
                        procurementUnitParams.replace("WEIGHT_QTY", pu.getWeight());
                        procurementUnitParams.replace("WEIGHT_UNIT_ID", pu.getWeightUnitId());
                        procurementUnitParams.replace("VOLUME_QTY", pu.getVolume());
                        procurementUnitParams.replace("VOLUME_UNIT_ID", pu.getVolumeUnitId());
                        procurementUnitParams.replace("UNITS_PER_CASE", pu.getUnitsPerCase());
                        procurementUnitParams.replace("UNITS_PER_PALLET_EURO1", pu.getUnitsPerPalletEuro1());
                        procurementUnitParams.replace("UNITS_PER_PALLET_EURO2", pu.getUnitsPerPalletEuro2());
                        procurementUnitParams.replace("UNITS_PER_CONTAINER", pu.getUnitsPerContainer());
                        procurementUnitParams.replace("LABELLING", pu.getLabelling());
                        pu.setProcurementUnitId(siProcurementUnit.executeAndReturnKey(procurementUnitParams).intValue());

                        papuParams.replace("PROCUREMENT_UNIT_ID", pu.getProcurementUnitId());
                        papuParams.replace("SKU_CODE", pu.getSkuCode());
                        papuParams.replace("VENDOR_PRICE", pu.getVendorPrice());
                        papuParams.replace("GTIN", pu.getGtin());
                        papuParams.replace("APPROVED_TO_SHIPPED_LEAD_TIME", pu.getApprovedToShippedLeadTime());
                        siPapu.execute(papuParams);
                    } else {
                        logger.info("Skipping the Procurement Unit " + pu.getLabel() + " since there is no Supplier Id defined");
                        sb.append("Skipping the Procurement Unit ").append(pu.getLabel()).append(" since there is no Supplier Id defined").append(br);
                        subjectParam = new String[]{"Product Catalog", "Skipping the Procurement Unit " + pu.getLabel()};
                        bodyParam = new String[]{"Product Catalog", "Skipping the Procurement Unit " + pu.getLabel(), "Skipping the Procurement Unit " + pu.getSkuCode() + " since there is no Supplier Id defined"};
                        emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), toList, ccList, "", subjectParam, bodyParam);
                        int emailerId = this.emailService.saveEmail(emailer);
                        emailer.setEmailerId(emailerId);
                        this.emailService.sendMail(emailer);
                    }
                } else {
                    logger.info("Skipping the Procurement Unit " + pu.getLabel() + " since there is no PlanningUnit defined");
                    sb.append("Skipping the Procurement Unit ").append(pu.getLabel()).append(" since there is no PlanningUnit defined").append(br);
                    subjectParam = new String[]{"Product Catalog", "Skipping the Procurement Unit " + pu.getLabel()};
                    bodyParam = new String[]{"Product Catalog", "Skipping the Procurement Unit " + pu.getLabel(), "Skipping the Procurement Unit " + pu.getSkuCode() + " since there is no PlanningUnit defined"};
                    emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), toList, ccList, "", subjectParam, bodyParam);
                    int emailerId = this.emailService.saveEmail(emailer);
                    emailer.setEmailerId(emailerId);
                    this.emailService.sendMail(emailer);
                }
            } catch (Exception e) {
                logger.info("Skipping the Procurement Unit " + pu.getLabel() + " because there was an error " + e.getMessage());
                sb.append("Skipping the Procurement Unit ").append(pu.getLabel()).append(" because there was an error ").append(e.getMessage()).append(br);
                subjectParam = new String[]{"Product Catalog", "Error while pulling procurement unit " + pu.getLabel()};
                bodyParam = new String[]{"Product Catalog", "Error while pulling procurement unit " + pu.getLabel(), e.getMessage()};
                emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), toList, ccList, "", subjectParam, bodyParam);
                int emailerId = this.emailService.saveEmail(emailer);
                emailer.setEmailerId(emailerId);
                this.emailService.sendMail(emailer);
            }
        }

        String sqlUpdate = "UPDATE tmp_procurement_unit tpu "
                + "LEFT JOIN rm_procurement_unit pu ON tpu.PROCUREMENT_UNIT_ID=pu.PROCUREMENT_UNIT_ID "
                + "LEFT JOIN rm_procurement_agent_procurement_unit papu ON pu.PROCUREMENT_UNIT_ID=papu.PROCUREMENT_UNIT_ID AND papu.PROCUREMENT_AGENT_ID=1 "
                + "LEFT JOIN ap_label l ON pu.LABEL_ID=l.LABEL_ID "
                + "SET "
                + "l.`LABEL_EN` = tpu.`LABEL`, "
                + "l.`LAST_MODIFIED_BY` = IF(l.`LABEL_EN`!=tpu.`LABEL`, 1, l.`LAST_MODIFIED_BY`), "
                + "l.`LAST_MODIFIED_DATE` = IF(l.`LABEL_EN`!=tpu.`LABEL`, now(), l.`LAST_MODIFIED_DATE`), "
                + "pu.`UNIT_ID` = tpu.UNIT_ID, "
                + "pu.`SUPPLIER_ID` = tpu.`SUPPLIER_ID`, "
                + "pu.`WIDTH_QTY` = tpu.`WIDTH`, "
                + "pu.`HEIGHT_QTY` = tpu.`HEIGHT`, "
                + "pu.`LENGTH_UNIT_ID` = tpu.`LENGTH_UNIT_ID`, "
                + "pu.`LENGTH_QTY` = tpu.`LENGTH`, "
                + "pu.`WEIGHT_UNIT_ID` = tpu.`WEIGHT_UNIT_ID`, "
                + "pu.`WEIGHT_QTY` = tpu.`WEIGHT`, "
                + "pu.`VOLUME_UNIT_ID` = tpu.`VOLUME_UNIT_ID`, "
                + "pu.`VOLUME_QTY` = tpu.`VOLUME`, "
                + "pu.`UNITS_PER_CASE` = tpu.`UNITS_PER_CASE`, "
                + "pu.`UNITS_PER_PALLET_EURO1` = tpu.`UNITS_PER_PALLET_EURO1`, "
                + "pu.`UNITS_PER_PALLET_EURO2` = tpu.`UNITS_PER_PALLET_EURO2`, "
                + "pu.`UNITS_PER_CONTAINER` = tpu.`UNITS_PER_CONTAINER`, "
                + "pu.`LABELING` = tpu.`LABELLING`, "
                + "pu.`LAST_MODIFIED_BY` =  "
                + "	IF( "
                + "         ( "
                + "         l.`LABEL_EN`!= tpu.`LABEL` "
                + "         OR pu.`UNIT_ID` != tpu.`UNIT_ID` "
                + "         OR pu.`SUPPLIER_ID` != tpu.`SUPPLIER_ID` "
                + "         OR pu.`LENGTH_UNIT_ID` != tpu.`LENGTH_UNIT_ID` "
                + "         OR pu.`HEIGHT_QTY` != tpu.`HEIGHT` "
                + "         OR pu.`LENGTH_QTY` != tpu.`LENGTH` "
                + "         OR pu.`WIDTH_QTY` != tpu.`WIDTH` "
                + "         OR pu.`WEIGHT_UNIT_ID` != tpu.`WEIGHT_UNIT_ID` "
                + "         OR pu.`WEIGHT_QTY` != tpu.`WEIGHT` "
                + "         OR pu.`VOLUME_UNIT_ID` != tpu.`VOLUME_UNIT_ID` "
                + "         OR pu.`VOLUME_QTY` != tpu.`VOLUME` "
                + "         OR pu.`UNITS_PER_CASE` != tpu.`UNITS_PER_CASE` "
                + "         OR pu.`UNITS_PER_PALLET_EURO1` != tpu.`UNITS_PER_PALLET_EURO1` "
                + "         OR pu.`UNITS_PER_PALLET_EURO2` != tpu.`UNITS_PER_PALLET_EURO2` "
                + "         OR pu.`UNITS_PER_CONTAINER` != tpu.`UNITS_PER_CONTAINER` "
                + "         OR pu.`LABELING` != tpu.`LABELLING` "
                + "        ), 1, pu.`LAST_MODIFIED_BY`), "
                + "pu.`LAST_MODIFIED_DATE` =  "
                + "	IF( "
                + "         ( "
                + "         l.`LABEL_EN`!= tpu.`LABEL` "
                + "         OR pu.`UNIT_ID` != tpu.`UNIT_ID` "
                + "         OR pu.`SUPPLIER_ID` != tpu.`SUPPLIER_ID` "
                + "         OR pu.`LENGTH_UNIT_ID` != tpu.`LENGTH_UNIT_ID` "
                + "         OR pu.`HEIGHT_QTY` != tpu.`HEIGHT` "
                + "         OR pu.`LENGTH_QTY` != tpu.`LENGTH` "
                + "         OR pu.`WIDTH_QTY` != tpu.`WIDTH` "
                + "         OR pu.`WEIGHT_UNIT_ID` != tpu.`WEIGHT_UNIT_ID` "
                + "         OR pu.`WEIGHT_QTY` != tpu.`WEIGHT` "
                + "         OR pu.`VOLUME_UNIT_ID` != tpu.`VOLUME_UNIT_ID` "
                + "         OR pu.`VOLUME_QTY` != tpu.`VOLUME` "
                + "         OR pu.`UNITS_PER_CASE` != tpu.`UNITS_PER_CASE` "
                + "         OR pu.`UNITS_PER_PALLET_EURO1` != tpu.`UNITS_PER_PALLET_EURO1` "
                + "         OR pu.`UNITS_PER_PALLET_EURO2` != tpu.`UNITS_PER_PALLET_EURO2` "
                + "         OR pu.`UNITS_PER_CONTAINER` != tpu.`UNITS_PER_CONTAINER` "
                + "         OR pu.`LABELING` != tpu.`LABELLING` "
                + "        ), now(), pu.`LAST_MODIFIED_DATE`), "
                + "papu.`GTIN`=tpu.`GTIN`, "
                + "papu.`VENDOR_PRICE`=tpu.`VENDOR_PRICE`, "
                + "papu.LAST_MODIFIED_BY = IF(papu.`GTIN`!=tpu.`GTIN`OR papu.`VENDOR_PRICE`!=tpu.`VENDOR_PRICE`, 1, papu.LAST_MODIFIED_BY), "
                + "papu.LAST_MODIFIED_DATE = IF(papu.`GTIN`!=tpu.`GTIN`OR papu.`VENDOR_PRICE`!=tpu.`VENDOR_PRICE`, NOW(), papu.LAST_MODIFIED_DATE) WHERE tpu.FOUND=1";
        this.jdbcTemplate.update(sqlUpdate);

        sqlString = "select count(*) from rm_procurement_unit;";
        int cnt = this.jdbcTemplate.queryForObject(sqlString, Integer.class);
        logger.info("No of rows after insertion in rm_procurement_unit --" + cnt);
        sb.append("No of rows after insertion in rm_procurement_unit --").append(cnt).append(br);
    }

    @Override
    public void rollBackAutoIncrement(StringBuilder sb) {
        logger.error("Rolling back AutoIncrement");
        sb.append("Rolling back AutoIncrement").append(br);
        String sqlString = "ALTER TABLE ap_label AUTO_INCREMENT = 1";
        this.jdbcTemplate.update(sqlString);
        sqlString = "ALTER TABLE ap_unit AUTO_INCREMENT = 1";
        this.jdbcTemplate.update(sqlString);
        sqlString = "ALTER TABLE rm_supplier AUTO_INCREMENT = 1";
        this.jdbcTemplate.update(sqlString);
        sqlString = "ALTER TABLE rm_tracer_category AUTO_INCREMENT = 1";
        this.jdbcTemplate.update(sqlString);
        sqlString = "ALTER TABLE rm_product_category AUTO_INCREMENT = 1";
        this.jdbcTemplate.update(sqlString);
        sqlString = "ALTER TABLE rm_forecasting_unit AUTO_INCREMENT = 1";
        this.jdbcTemplate.update(sqlString);
        sqlString = "ALTER TABLE rm_planning_unit AUTO_INCREMENT = 1";
        this.jdbcTemplate.update(sqlString);
        sqlString = "ALTER TABLE rm_procurement_agent_planning_unit AUTO_INCREMENT = 1";
        this.jdbcTemplate.update(sqlString);
        sqlString = "ALTER TABLE rm_procurement_unit AUTO_INCREMENT = 1";
        this.jdbcTemplate.update(sqlString);
        sqlString = "ALTER TABLE rm_procurement_agent_procurement_unit AUTO_INCREMENT = 1";
        this.jdbcTemplate.update(sqlString);
    }

}
