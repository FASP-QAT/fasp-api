CREATE DEFINER=`faspUser`@`%` PROCEDURE `getBatchInventoryData`(PROGRAM_ID INT(10), VERSION_ID INT (10), PLANNING_UNIT_ACTIVE TINYINT(1), CUT_OFF_DATE DATE)
BEGIN
    SET @programId = PROGRAM_ID;
    SET @versionId = VERSION_ID;
    SET @planningUmitActive= PLANNING_UNIT_ACTIVE;
    IF @versionId = -1 THEN 
        SELECT MAX(pv.VERSION_ID) into @versionId FROM rm_program_version pv where pv.PROGRAM_ID=@programId;
    END IF;
    SET @cutOffDate = CUT_OFF_DATE;
    SET @useCutOff = false;
    IF @cutOffDate is not null && LENGTH(@cutOffDate)!=0 THEN
        SET @useCutOff = true;
    END IF;
    
    SELECT bi.BATCH_INVENTORY_ID, bi.PROGRAM_ID, bt.VERSION_ID, bi.PLANNING_UNIT_ID, pu.LABEL_ID `PU_LABEL_ID`, pu.LABEL_EN `PU_LABEL_EN`, pu.LABEL_FR `PU_LABEL_FR`, pu.LABEL_SP `PU_LABEL_SP`, pu.LABEL_PR `PU_LABEL_PR`, bi.INVENTORY_DATE, bt.BATCH_INVENTORY_TRANS_ID, bt.BATCH_ID, bt.QTY, b.BATCH_NO, b.AUTO_GENERATED, b.EXPIRY_DATE, b.CREATED_DATE `BATCH_CREATED_DATE`, bi.CREATED_DATE, bt.LAST_MODIFIED_DATE, cb.USER_ID `CB_USER_ID`, cb.`USERNAME` `CB_USERNAME`, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`
    FROM (SELECT bi.BATCH_INVENTORY_ID, MAX(bt.VERSION_ID) MAX_VERSION_ID FROM rm_batch_inventory bi LEFT JOIN rm_batch_inventory_trans bt ON bi.BATCH_INVENTORY_ID=bt.BATCH_INVENTORY_ID WHERE (@versionId=-1 OR bt.VERSION_ID<=@versionId) AND bi.PROGRAM_ID=@programId GROUP BY bt.BATCH_INVENTORY_ID) tb
    LEFT JOIN rm_batch_inventory bi ON tb.BATCH_INVENTORY_ID=bi.BATCH_INVENTORY_ID
    LEFT JOIN rm_batch_inventory_trans bt ON bi.BATCH_INVENTORY_ID=bt.BATCH_INVENTORY_ID AND bt.ACTIVE
    LEFT JOIN rm_program_planning_unit ppu ON bi.PROGRAM_ID=ppu.PROGRAM_ID AND bi.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID
    LEFT JOIN vw_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID
    LEFT JOIN rm_batch_info b ON bt.BATCH_ID=b.BATCH_ID
    LEFT JOIN us_user cb ON bi.CREATED_BY=cb.USER_ID
    LEFT JOIN us_user lmb ON bt.LAST_MODIFIED_BY=lmb.USER_ID
    WHERE (@planningUnitActive = FALSE OR ppu.ACTIVE) AND (@useCutOff = FALSE OR (@useCutOff = TRUE AND bi.INVENTORY_DATE>=DATE_SUB(@cutOffDate,INTERVAL ppu.MONTHS_IN_PAST_FOR_AMC MONTH)))
    ORDER BY bi.PLANNING_UNIT_ID, bi.INVENTORY_DATE, bt.BATCH_ID;
    
END