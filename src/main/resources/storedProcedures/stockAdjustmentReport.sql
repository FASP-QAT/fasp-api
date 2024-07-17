CREATE DEFINER=`faspUser`@`localhost` PROCEDURE `stockAdjustmentReport`(VAR_PROGRAM_ID INT(10), VAR_VERSION_ID INT(10), VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_PLANNING_UNIT_IDS TEXT)
BEGIN

 -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
 -- Report no 12
 -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
 
-- VAR_PROGRAM_ID must be a valid Program cannot be All i.e. -1
-- VAR_VERSION_ID must be a valid Version for the PROGRAM_ID or can be -1 in which case it will default to the latest Version of the Program, 
-- VAR_START_DATE AND VAR_STOP_DATE are the Date range between which the Stock Adjustment will be run. Only the month and year are considered while running the report
-- VAR_PLANNING_UNIT_IDS are the Quoted, Comma separated list of the Planning Unit Ids that you want to run the report for. If you want to run it for all Planning Units in the Program leave it empty
 
SET @programId = VAR_PROGRAM_ID;
SET @versionId = VAR_VERSION_ID;
IF @versionID = -1 THEN
	SELECT MAX(pv.VERSION_ID) INTO @versionId FROM rm_program_version pv WHERE pv.PROGRAM_ID=@programId;
END IF;
SET @startDt = LEFT(VAR_START_DATE,7);
SET @stopDt = LEFT(VAR_STOP_DATE,7);

SET @sqlString = "";

SET @sqlString = CONCAT(@sqlString, "SELECT ");
SET @sqlString = CONCAT(@sqlString, "	p.PROGRAM_ID, p.LABEL_ID `PROGRAM_LABEL_ID`, p.LABEL_EN `PROGRAM_LABEL_EN`, p.LABEL_FR `PROGRAM_LABEL_FR`, p.LABEL_SP `PROGRAM_LABEL_SP`, p.LABEL_PR `PROGRAM_LABEL_PR`,");
SET @sqlString = CONCAT(@sqlString, "   pu.PLANNING_UNIT_ID, pu.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pu.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pu.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pu.LABEL_SP `PLANNING_UNIT_LABEL_SP`, pu.LABEL_PR `PLANNING_UNIT_LABEL_PR`,");
SET @sqlString = CONCAT(@sqlString, "   ds.DATA_SOURCE_ID, ds.LABEL_ID `DATA_SOURCE_LABEL_ID`, ds.LABEL_EN `DATA_SOURCE_LABEL_EN`, ds.LABEL_FR `DATA_SOURCE_LABEL_FR`, ds.LABEL_SP `DATA_SOURCE_LABEL_SP`, ds.LABEL_PR `DATA_SOURCE_LABEL_PR`, ");
SET @sqlString = CONCAT(@sqlString, "	it.INVENTORY_DATE, it.ADJUSTMENT_QTY*IF(rcpu.CONVERSION_METHOD IS NULL OR rcpu.CONVERSION_METHOD=1, rcpu.CONVERSION_NUMBER, IF(rcpu.CONVERSION_METHOD=2,1/rcpu.CONVERSION_NUMBER,0)) `STOCK_ADJUSTMENT_QTY`, lmb.USER_ID `LAST_MODIFIED_BY_USER_ID`, lmb.USERNAME `LAST_MODIFIED_BY_USERNAME`, it.LAST_MODIFIED_DATE, it.NOTES");
SET @sqlString = CONCAT(@sqlString, " FROM ");
SET @sqlString = CONCAT(@sqlString, "	( ");
SET @sqlString = CONCAT(@sqlString, "    SELECT ");
SET @sqlString = CONCAT(@sqlString, "		i.PROGRAM_ID, i.INVENTORY_ID, MAX(it.VERSION_ID) MAX_VERSION_ID ");
SET @sqlString = CONCAT(@sqlString, "	FROM rm_inventory i ");
SET @sqlString = CONCAT(@sqlString, "	LEFT JOIN rm_inventory_trans it ON i.INVENTORY_ID=it.INVENTORY_ID ");
SET @sqlString = CONCAT(@sqlString, "	WHERE i.PROGRAM_ID=@programId AND it.VERSION_ID<=@versionId AND it.INVENTORY_TRANS_ID IS NOT NULL ");
SET @sqlString = CONCAT(@sqlString, "	GROUP BY i.INVENTORY_ID ");
SET @sqlString = CONCAT(@sqlString, ") ti ");
SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_inventory_trans it ON ti.INVENTORY_ID=it.INVENTORY_ID AND ti.MAX_VERSION_ID=it.VERSION_ID ");
SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_inventory i ON ti.INVENTORY_ID=i.INVENTORY_ID ");
SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_program p ON i.PROGRAM_ID=p.PROGRAM_ID ");
SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_realm_country_planning_unit rcpu ON it.REALM_COUNTRY_PLANNING_UNIT_ID=rcpu.REALM_COUNTRY_PLANNING_UNIT_ID ");
SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_planning_unit pu ON rcpu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID ");
SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_program_planning_unit ppu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID AND ppu.PROGRAM_ID=p.PROGRAM_ID ");
SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_data_source ds ON it.DATA_SOURCE_ID=ds.DATA_SOURCE_ID ");
SET @sqlString = CONCAT(@sqlString, "LEFT JOIN us_user lmb ON it.LAST_MODIFIED_BY=lmb.USER_ID ");
SET @sqlString = CONCAT(@sqlString, "WHERE it.ADJUSTMENT_QTY IS NOT NULL AND LEFT(it.INVENTORY_DATE,7) BETWEEN @startDt AND @stopDt AND it.ACTIVE AND ppu.ACTIVE AND pu.ACTIVE ");
IF LENGTH(VAR_PLANNING_UNIT_IDS) >0 THEN
 	SET @sqlString = CONCAT(@sqlString, " AND pu.PLANNING_UNIT_ID IN (",VAR_PLANNING_UNIT_IDS,") ");
END IF;

PREPARE s2 FROM @sqlString;
EXECUTE s2;
END