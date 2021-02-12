CREATE DEFINER=`faspUser`@`localhost` PROCEDURE `buildStockBalances`(VAR_PROGRAM_ID int(10), VAR_VERSION_ID int(10))
BEGIN
	DECLARE cursor_TRANS_DATE DATE;
	DECLARE cursor_CONSUMPTION INT;
	DECLARE done INT DEFAULT FALSE;
	DECLARE cursor_i CURSOR FOR SELECT spbi.TRANS_DATE, SUM(spbi.FORECASTED_CONSUMPTION_QTY+spbi.EXPIRED_CONSUMPTION) CONSUMPTION FROM rm_supply_plan_batch_info spbi WHERE spbi.PROGRAM_ID=VAR_PROGRAM_ID AND spbi.VERSION_ID=VAR_VERSION_ID GROUP BY spbi.TRANS_DATE HAVING SUM(spbi.FORECASTED_CONSUMPTION_QTY)>0 OR SUM(spbi.EXPIRED_CONSUMPTION)>0;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
	SET @error = false;
	OPEN cursor_i;
	read_loop: LOOP
    FETCH cursor_i INTO cursor_TRANS_DATE, cursor_CONSUMPTION;
    IF done THEN
      LEAVE read_loop;
    END IF;
    -- For each loop calculate the Consumption that should have been done based on the ExpiredConsumption
    SET @unAccountedConsumption = cursor_CONSUMPTION;
    SET @transDate = cursor_TRANS_DATE;

    UPDATE (
		SELECT 
			spbi.SUPPLY_PLAN_BATCH_INFO_ID,
            spbi.BATCH_ID,
            spbi.EXPIRY_DATE,
            spbi.FINAL_CLOSING_BALANCE,
			@unAccountedConsumption `UNACCOUNTED_CONSUMPTION`,
            IF(spbi.FINAL_CLOSING_BALANCE>@unAccountedConsumption, @unAccountedConsumption, spbi.FINAL_CLOSING_BALANCE) `CALCULATED_CONSUMPTION`, 
			@unAccountedConsumption:=@unAccountedConsumption-IF(spbi.FINAL_CLOSING_BALANCE>@unAccountedConsumption, @unAccountedConsumption, spbi.FINAL_CLOSING_BALANCE)
		FROM (SELECT * FROM rm_supply_plan_batch_info WHERE PROGRAM_ID=@programId AND VERSION_ID=@versionId AND TRANS_DATE=@transDate AND CLOSING_BALANCE>0 ORDER BY EXPIRY_DATE, IF(BATCH_ID=0, 9999999999, BATCH_ID)) spbi 
	) cs 
    LEFT JOIN rm_supply_plan_batch_info spbi2 ON cs.SUPPLY_PLAN_BATCH_INFO_ID=spbi2.SUPPLY_PLAN_BATCH_INFO_ID
 	SET spbi2.CALCULATED_CONSUMPTION=cs.CALCULATED_CONSUMPTION;
	-- If unAccountedConsumption is greated that zero it means there was some consumption that it could not allocate because it ran out of stock
    -- therefore save it as unMetDemand
    UPDATE rm_supply_plan_batch_info spbi SET spbi.UNMET_DEMAND = @unAccountedConsumption WHERE spbi.TRANS_DATE=@transDate AND spbi.BATCH_ID=0 AND spbi.PROGRAM_ID=VAR_PROGRAM_ID AND spbi.VERSION_ID=VAR_VERSION_ID;
-- 	IF @unAccountedConsumption>0 THEN 
-- 		SET @error = true;
-- 		LEAVE read_loop;
-- 	END IF;
    -- Now let the new Final Opening and Closing Stock Balances percolate down
    SET @oldBatchId = -1;
	SET @oldBatchId = -1;
	UPDATE rm_supply_plan_batch_info spbi 
	LEFT JOIN (
		SELECT 
			spbi.SUPPLY_PLAN_BATCH_INFO_ID, 
			IF(@oldBatchId!=spbi.BATCH_ID, @cb:=0, @cb:=@cb) `OB`, IF(spbi.TRANS_DATE>=spbi.EXPIRY_DATE, @cb, 0) `EXPIRED_STOCK`,
			IF(spbi.TRANS_DATE>=spbi.EXPIRY_DATE,spbi.ACTUAL_CONSUMPTION_QTY,0) `EXPIRED_CONSUMPTION`, @cb:=@cb+spbi.SHIPMENT_QTY-IF(spbi.TRANS_DATE>=spbi.EXPIRY_DATE,0,spbi.ACTUAL_CONSUMPTION_QTY)+spbi.ADJUSTMENT_MULTIPLIED_QTY-IF(spbi.TRANS_DATE>=spbi.EXPIRY_DATE, @cb, 0)-IFNULL(spbi.CALCULATED_CONSUMPTION,0) `CB`, 
			@oldBatchId := spbi.BATCH_ID 
		FROM rm_supply_plan_batch_info spbi ORDER BY spbi.EXPIRY_DATE, IF(spbi.BATCH_ID=0, 9999999999,spbi.BATCH_ID), spbi.TRANS_DATE
	) spbic ON spbi.SUPPLY_PLAN_BATCH_INFO_ID=spbic.SUPPLY_PLAN_BATCH_INFO_ID
	SET 
		spbi.FINAL_OPENING_BALANCE=spbic.`OB`, 
		spbi.FINAL_CLOSING_BALANCE=spbic.`CB`,
		spbi.EXPIRED_STOCK=spbic.`EXPIRED_STOCK`;

  END LOOP;
  CLOSE cursor_i;
  IF @error = true THEN 
	SELECT @transDate, @unAccountedConsumption, @error;
  ELSE 
	SELECT null, 0, false;
  END IF;
END
