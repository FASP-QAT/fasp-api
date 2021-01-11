-- Queries for UAT and Production to import ET-ARV and RTK
SET @dt = '2021-01-11 00:00:00';
-- First go to the Prod site and create the user 
-- User Id 35

-- Check what the Program Id is for the program you want to import
-- ET-ARV-> 2135
-- ET-RTK-> 2138
-- AZ-LAB-VIAC
UPDATE rm_program p SET p.PROGRAM_CODE='ETH-RTK-MOH', p.HEALTH_AREA_ID=6, p.ORGANISATION_ID=1, p.ACTIVE=1, p.AIR_FREIGHT_PERC=20, p.SEA_FREIGHT_PERC=15, p.PROGRAM_MANAGER_USER_ID=35, p.LAST_MODIFIED_DATE='2020-01-08 00:00:00', p.PLANNED_TO_SUBMITTED_LEAD_TIME=1, p.SUBMITTED_TO_APPROVED_LEAD_TIME=1, p.APPROVED_TO_SHIPPED_LEAD_TIME=4, p.SHIPPED_TO_ARRIVED_BY_AIR_LEAD_TIME=2, p.SHIPPED_TO_ARRIVED_BY_SEA_LEAD_TIME=2, p.ARRIVED_TO_DELIVERED_LEAD_TIME=0.5 WHERE p.PROGRAM_ID=2138;
UPDATE rm_program p SET p.PROGRAM_CODE='ETH-ARV-MOH', p.HEALTH_AREA_ID=1, p.ORGANISATION_ID=1, p.ACTIVE=1, p.AIR_FREIGHT_PERC=20, p.SEA_FREIGHT_PERC=15, p.PROGRAM_MANAGER_USER_ID=35, p.LAST_MODIFIED_DATE='2020-01-08 00:00:00', p.PLANNED_TO_SUBMITTED_LEAD_TIME=1, p.SUBMITTED_TO_APPROVED_LEAD_TIME=1, p.APPROVED_TO_SHIPPED_LEAD_TIME=4, p.SHIPPED_TO_ARRIVED_BY_AIR_LEAD_TIME=2, p.SHIPPED_TO_ARRIVED_BY_SEA_LEAD_TIME=2, p.ARRIVED_TO_DELIVERED_LEAD_TIME=0.5 WHERE p.PROGRAM_ID=2135;
UPDATE rm_program p LEFT JOIN ap_label pl ON p.LABEL_ID=pl.LABEL_ID SET p.PROGRAM_CODE='Zim-LAB-VIAC-MoHcc', pl.LABEL_EN='Zimbabwe-LAB-VIAC', p.HEALTH_AREA_ID=5, p.ORGANISATION_ID=1, p.ACTIVE=1, p.AIR_FREIGHT_PERC=38.5, p.SEA_FREIGHT_PERC=38.5, p.PROGRAM_MANAGER_USER_ID=4, p.LAST_MODIFIED_DATE=@dt, p.PLANNED_TO_SUBMITTED_LEAD_TIME=1, p.SUBMITTED_TO_APPROVED_LEAD_TIME=1, p.APPROVED_TO_SHIPPED_LEAD_TIME=3, p.SHIPPED_TO_ARRIVED_BY_AIR_LEAD_TIME=0.17, p.SHIPPED_TO_ARRIVED_BY_SEA_LEAD_TIME=2, p.ARRIVED_TO_DELIVERED_LEAD_TIME=0.17 WHERE p.PROGRAM_ID=2527;
UPDATE rm_program_region pr SET pr.ACTIVE=1, pr.LAST_MODIFIED_DATE=@dt where pr.PROGRAM_ID IN (2527, 2138, 2135);
DELETE pr.* FROM rm_program_region pr where pr.PROGRAM_ID=2527;
INSERT INTO rm_program_region VALUES (null, 2527, 53, 1, 1, @dt, 1, @dt);

-- Insert into the tr_program table 
INSERT INTO tr_program VALUES (2135, 16, "ARV", "/globalTables/20210108/ET-ARV/");
INSERT INTO tr_program VALUES (2138, 16, "RTK", "/globalTables/20210108/ET-RTK/");
INSERT INTO tr_program VALUES (2527, 51, "LAB-VIAC", "/globalTables/20210108/ZW-LAB-VIAC/");

-- Check health area country and organisation country
UPDATE rm_health_area_country hac SET ACTIVE=1, LAST_MODIFIED_DATE=@dt where hac.REALM_COUNTRY_ID=16 and (hac.HEALTH_AREA_ID=1 OR hac.HEALTH_AREA_ID=6);
UPDATE rm_health_area_country hac SET ACTIVE=1, LAST_MODIFIED_DATE=@dt where hac.REALM_COUNTRY_ID=51 and (hac.HEALTH_AREA_ID=5);
-- INSERT INTO rm_health_area_country VALUES (null, 12, 16, 1, 1, now(), 1, now());

-- SELECT * FROM rm_organisation_country oc where oc.REALM_COUNTRY_ID=16 and (oc.ORGANISATION_ID=1);
-- UPDATE rm_organisation_country oc SET ACTIVE=1, LAST_MODIFIED_DATE=@dt where oc.REALM_COUNTRY_ID=16 and (oc.ORGANISATION_ID=1);
-- INSERT INTO rm_organisation_country VALUES (null, 1, 16, 1, 1, now(), 1, now());

INSERT INTO tr_data_source VALUES ('FORCASTE00', 14, 2135);
INSERT INTO tr_data_source VALUES ('LMIS', 5, 2135);
INSERT INTO tr_data_source VALUES ('QMED', 18, 2135);
INSERT INTO tr_data_source VALUES ('QUANTIFI02', 15, 2135);
INSERT INTO tr_data_source VALUES ('QUANTIME00', 18, 2135);
INSERT INTO tr_data_source VALUES ('SDP', 8, 2135);
INSERT INTO tr_data_source VALUES ('TREND', 11, 2135);
INSERT INTO tr_data_source VALUES ('_NA00', 14, 2135);

INSERT INTO tr_data_source VALUES ('SOCKSTAT00', 20, 2135);
INSERT INTO tr_data_source VALUES ('STOCK', 2, 2135);
INSERT INTO tr_data_source VALUES ('_NA02', 16, 2135);

INSERT INTO tr_data_source VALUES ('SHIPMENT00', 17, 2135);
INSERT INTO tr_data_source VALUES ('SUPP', 10, 2135);
INSERT INTO tr_data_source VALUES ('WRHS', 12, 2135);
INSERT INTO tr_data_source VALUES ('_NA03', 17, 2135);

INSERT INTO tr_data_source VALUES ('EPHI00', 15, 2138);
INSERT INTO tr_data_source VALUES ('LMIS', 5, 2138);
INSERT INTO tr_data_source VALUES ('QUANTIFI00', 15, 2138);
INSERT INTO tr_data_source VALUES ('QUANTIFI01', 15, 2138);
INSERT INTO tr_data_source VALUES ('QUANTIFI02', 15, 2138);
INSERT INTO tr_data_source VALUES ('TREND', 11, 2138);
INSERT INTO tr_data_source VALUES ('_NA00', 14, 2138);
INSERT INTO tr_data_source VALUES ('_NA01', 15, 2138);

-- INSERT INTO tr_data_source VALUES ('SOCKSTAT00', 20, 2138);
INSERT INTO tr_data_source VALUES ('STOCKSTA00', 20, 2138);
INSERT INTO tr_data_source VALUES ('STOCK', 2, 2138);

INSERT INTO tr_data_source VALUES ('PFSA00', 10, 2138);
INSERT INTO tr_data_source VALUES ('SUPP', 10, 2138);
INSERT INTO tr_data_source VALUES ('WRHS', 12, 2138);
INSERT INTO tr_data_source VALUES ('_NA03', 17, 2138);

INSERT INTO tr_data_source VALUES ('_NA01', 14, 2527);
INSERT INTO tr_data_source VALUES ('LMIS', 5, 2527);
INSERT INTO tr_data_source VALUES ('PROCEDUR00', 5, 2527);
INSERT INTO tr_data_source VALUES ('QUANTIME00', 18, 2527);
INSERT INTO tr_data_source VALUES ('CLONE', 1, 2527);
INSERT INTO tr_data_source VALUES ('_NA00', 15, 2527);

INSERT INTO tr_data_source VALUES ('PIC', 7, 2527);
INSERT INTO tr_data_source VALUES ('_NA02', 16, 2527);

INSERT INTO tr_data_source VALUES ('SUPP', 10, 2527);
INSERT INTO tr_data_source VALUES ('_NA03', 17, 2527);
INSERT INTO tr_data_source VALUES ('WRHS', 12, 2527);

INSERT INTO tr_procurement_agent VALUES ('GHSC-PSM00', 2527, 1);
INSERT INTO tr_procurement_agent VALUES ('UNFPA', 2527, 8);
INSERT INTO tr_procurement_agent VALUES ('UNFUNDED00', 2527, 11);
INSERT INTO tr_procurement_agent VALUES ('USAID', 2527, 1);

INSERT INTO tr_funding_source VALUES ('USAID', 2527, 1);
INSERT INTO tr_funding_source VALUES ('UNFPA', 2527, 2);
INSERT INTO tr_funding_source VALUES ('UNFUNDED00', 2527, 8);


INSERT INTO tr_product VALUES ('ABACAVIR00', 2135, 6, 12, 3, 6, 1.5, 18, 7.97);
INSERT INTO tr_product VALUES ('ABACAVIR01', 2135, 6, 12, 3, 6, 1.5, 18, 7.04);
INSERT INTO tr_product VALUES ('ATAZANAV00', 2135, 6, 12, 3, 6, 1.5, 18, 11.98);
INSERT INTO tr_product VALUES ('AZT-3TC-00', 2135, 6, 12, 3, 6, 1.5, 18, 2.9);
INSERT INTO tr_product VALUES ('DARUNAVI00', 2135, 6, 12, 3, 6, 1.5, 18, null);
INSERT INTO tr_product VALUES ('DARUNAVI01', 2135, 6, 12, 3, 6, 1.5, 18, null);
INSERT INTO tr_product VALUES ('DARUNAVI02', 2135, 6, 12, 3, 6, 1.5, 18, null);
INSERT INTO tr_product VALUES ('DOLUTEGR00', 2135, 6, 12, 3, 6, 1.5, 18, 3.5);
INSERT INTO tr_product VALUES ('EFAVIREN00', 2135, 6, 12, 3, 6, 1.5, 18, 2.23);
INSERT INTO tr_product VALUES ('EFAVIREN01', 2135, 6, 12, 3, 6, 1.5, 18, 3.43);
INSERT INTO tr_product VALUES ('EFAVIREN02', 2135, 6, 12, 3, 6, 1.5, 18, 1.48);
INSERT INTO tr_product VALUES ('LAMIVUDI00', 2135, 6, 12, 3, 6, 1.5, 18, 1.63);
INSERT INTO tr_product VALUES ('LAMIVUDI01', 2135, 6, 12, 3, 6, 1.5, 18, 2.85);
INSERT INTO tr_product VALUES ('LAMIVUDI02', 2135, 6, 12, 3, 6, 1.5, 18, 1.48);
INSERT INTO tr_product VALUES ('LOPINAVI00', 2135, 6, 12, 3, 6, 1.5, 18, 16.33);
INSERT INTO tr_product VALUES ('LOPINAVI01', 2135, 6, 12, 3, 6, 1.5, 18, 10);
INSERT INTO tr_product VALUES ('LOPINAVI03', 2135, 6, 12, 3, 6, 1.5, 18, 19.78);
INSERT INTO tr_product VALUES ('NEVIRAPI00', 2135, 6, 12, 3, 6, 1.5, 18, 1.26);
INSERT INTO tr_product VALUES ('RALTEGRA00', 2135, 6, 12, 3, 6, 1.5, 18, 36);
INSERT INTO tr_product VALUES ('RITONAVI00', 2135, 6, 12, 3, 6, 1.5, 18, 6.85);
INSERT INTO tr_product VALUES ('RITONAVI01', 2135, 6, 12, 3, 6, 1.5, 18, null);
INSERT INTO tr_product VALUES ('TDF+3TC+00', 2135, 6, 12, 3, 6, 1.5, 18, 5.85);
INSERT INTO tr_product VALUES ('TDF+3TC+01', 2135, 6, 12, 3, 6, 1.5, 18, 6);
INSERT INTO tr_product VALUES ('TDF+3TC+02', 2135, 6, 12, 3, 6, 1.5, 18, 15.89);
INSERT INTO tr_product VALUES ('TDF+3TC+03', 2135, 6, 12, 3, 6, 1.5, 18, 31);
INSERT INTO tr_product VALUES ('ZIDOVUDI00', 2135, 6, 12, 3, 6, 1.5, 18, 4.72);
INSERT INTO tr_product VALUES ('ZIDOVUDI01', 2135, 6, 12, 3, 6, 1.5, 18, 1.363);
INSERT INTO tr_product VALUES ('HIV-1/2/00', 2138, 6, 12, 3, 6, 1.5, 18, 28);
INSERT INTO tr_product VALUES ('HIV-1/2R00', 2138, 6, 12, 3, 6, 1.5, 18, 22.63);
INSERT INTO tr_product VALUES ('STAT-PAK00', 2138, 6, 12, 3, 6, 1.5, 18, 11.6);
INSERT INTO tr_product VALUES ('EDTACAPI00', 2138, null, null, null, null, null, null, null);
INSERT INTO tr_product VALUES ('HIV-1/2,00', 2138, null, null, null, null, null, null, null);
INSERT INTO tr_product VALUES ('HIV-1ASA00', 2138, null, null, null, null, null, null, null);
INSERT INTO tr_product VALUES ('ACETICAC00', 2527, 8, 13, 3, 6, 4, 19, 18);
INSERT INTO tr_product VALUES ('COTTONWO00', 2527, 8, 13, 3, 6, 4, 55, 3.88);
INSERT INTO tr_product VALUES ('LATEXEXA00', 2527, 8, 13, 3, 6, 4, 55, 3.69);
INSERT INTO tr_product VALUES ('LINENSAV00', 2527, 8, 13, 3, 6, 4, 55, 3.35);
INSERT INTO tr_product VALUES ('LIQUIDSO00', 2527, 8, 13, 3, 6, 4, 67, 4.95);
INSERT INTO tr_product VALUES ('PAPERTOW00', 2527, 8, 13, 3, 6, 4, 55, 3.67);
INSERT INTO tr_product VALUES ('SANITARY00', 2527, 8, 13, 3, 6, 4, 43, 0.86);
INSERT INTO tr_product VALUES ('SODIUMHY00', 2527, 8, 13, 3, 6, 4, 19, 6.55);
INSERT INTO tr_product VALUES ('TISSUERO00', 2527, 8, 13, 3, 6, 4, 910, 0.4);

-- QAT FundingSource ID	
	INSERT INTO tr_funding_source VALUES ('_NA03', 2138, 8);
	INSERT INTO tr_funding_source VALUES ('GF00 ', 2138, 4);
	INSERT INTO tr_funding_source VALUES ('MOSTLIKE00', 2138, 1);
	INSERT INTO tr_funding_source VALUES ('MOSTLIKE01', 2138, 4);
	INSERT INTO tr_funding_source VALUES ('UNFPA ', 2138, 2);
	INSERT INTO tr_funding_source VALUES ('UNICEF ', 2138, 6);
	INSERT INTO tr_funding_source VALUES ('USAID ', 2138, 1);
	
-- QAT ProcurementAgent ID	
	INSERT INTO tr_procurement_agent VALUES ('DFID______', 2138, 10);
	INSERT INTO tr_procurement_agent VALUES ('GHSC-PSM00', 2138, 1);
	INSERT INTO tr_procurement_agent VALUES ('MACLOED00', 2138, 10);
	INSERT INTO tr_procurement_agent VALUES ('PFAS00', 2138, 6);
	INSERT INTO tr_procurement_agent VALUES ('SCMS', 2138, 10);
	INSERT INTO tr_procurement_agent VALUES ('UNFPA', 2138, 8);
	INSERT INTO tr_procurement_agent VALUES ('UNICEF', 2138, 9);
	INSERT INTO tr_procurement_agent VALUES ('USAID', 2138, 1);
	
	
	
	
-- QAT FundingSource ID	
INSERT INTO tr_funding_source VALUES ('_NA03', 2135, 8);	
INSERT INTO tr_funding_source VALUES ('GLOBALFU00', 2135, 4);	
INSERT INTO tr_funding_source VALUES ('USAID     ', 2135, 1);	
INSERT INTO tr_funding_source VALUES ('TBD00', 2135, 8);	
	
-- QAT ProcurementAgent ID	
INSERT INTO tr_procurement_agent VALUES ('EPSA00', 2135, 6);	
INSERT INTO tr_procurement_agent VALUES ('PPM00', 2135, 2);	
INSERT INTO tr_procurement_agent VALUES ('GHSC-PSM', 2135, 1);	
INSERT INTO tr_procurement_agent VALUES ('TBD00', 2135, 11);	
