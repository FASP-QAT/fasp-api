ALTER TABLE `fasp`.`rm_program_version` ADD COLUMN `VERSION_READY` TINYINT(1) UNSIGNED NOT NULL DEFAULT 1 AFTER `FORECAST_THRESHOLD_LOW_PERC`;

USE `fasp`;
DROP procedure IF EXISTS `getVersionId`;

USE `fasp`;
DROP procedure IF EXISTS `fasp`.`getVersionId`;
;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`localhost` PROCEDURE `getVersionId`(PROGRAM_ID INT(10), VERSION_TYPE_ID INT(10), VERSION_STATUS_ID INT(10), NOTES TEXT, FORECAST_START_DATE DATETIME, FORECAST_STOP_DATE DATETIME, DAYS_IN_MONTH INT(10), FREIGHT_PERC DECIMAL, FORECAST_THRESHOLD_HIGH_PERC DECIMAL, FORECAST_THRESHOLD_LOW_PERC DECIMAL, CREATED_BY INT(10), CREATED_DATE DATETIME, VERSION_READY TINYINT(1))
BEGIN
	SET @programId = PROGRAM_ID;
	SET @cbUserId = CREATED_BY;
	SET @createdDate = CREATED_DATE;
	SET @versionTypeId = VERSION_TYPE_ID;
	SET @versionStatusId = VERSION_STATUS_ID;
        SET @forecastStartDate = FORECAST_START_DATE;
        SET @forecastStopDate = FORECAST_STOP_DATE;
        SET @daysInMonth = DAYS_IN_MONTH;
        SET @freightPerc = FREIGHT_PERC;
        SET @forecastThresholdHighPerc = FORECAST_THRESHOLD_HIGH_PERC;
        SET @forecastThresholdLowPerc = FORECAST_THRESHOLD_LOW_PERC;
	SET @notes = NOTES;
        SET @versionReady = VERSION_READY;
        INSERT INTO `fasp`.`rm_program_version`
            (`PROGRAM_ID`, `VERSION_ID`, `VERSION_TYPE_ID`, `VERSION_STATUS_ID`,
            `NOTES`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`,
            `SENT_TO_ARTMIS`, `FORECAST_START_DATE`, `FORECAST_STOP_DATE`, `DAYS_IN_MONTH`, `FREIGHT_PERC`,
            `FORECAST_THRESHOLD_HIGH_PERC`, `FORECAST_THRESHOLD_LOW_PERC`, `VERSION_READY`)
        SELECT
            @programId, IFNULL(MAX(pv.VERSION_ID)+1,1), @versionTypeId, @versionStatusId, 
            @notes, @cbUserId, @createdDate, @cbUserId, @createdDate, 
            0, @forecastStartDate, @forecastStopDate, @daysInMonth, @freightPerc, 
            @forecastThresholdHighPerc, @forecastThresholdLowPerc, @versionReady
        FROM rm_program_version pv WHERE pv.`PROGRAM_ID`=@programId;
        SELECT pv.VERSION_ID INTO @versionId FROM rm_program_version pv WHERE pv.`PROGRAM_VERSION_ID`= LAST_INSERT_ID();

        IF @versionReady THEN
            UPDATE rm_program p SET p.CURRENT_VERSION_ID=@versionId WHERE p.PROGRAM_ID=@programId;
        END IF;

        SELECT pv.VERSION_ID, pv.NOTES, pv.FORECAST_START_DATE, pv.FORECAST_STOP_DATE, pv.DAYS_IN_MONTH, pv.FREIGHT_PERC, pv.FORECAST_THRESHOLD_HIGH_PERC, pv.FORECAST_THRESHOLD_LOW_PERC,
            pv.LAST_MODIFIED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`,
            pv.CREATED_DATE, cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`,
            vt.VERSION_TYPE_ID, vtl.LABEL_ID `VERSION_TYPE_LABEL_ID`, vtl.LABEL_EN `VERSION_TYPE_LABEL_EN`, vtl.LABEL_FR `VERSION_TYPE_LABEL_FR`, vtl.LABEL_SP `VERSION_TYPE_LABEL_SP`, vtl.LABEL_PR `VERSION_TYPE_LABEL_PR`, 
            vs.VERSION_STATUS_ID, vsl.LABEL_ID `VERSION_STATUS_LABEL_ID`, vsl.LABEL_EN `VERSION_STATUS_LABEL_EN`, vsl.LABEL_FR `VERSION_STATUS_LABEL_FR`, vsl.LABEL_SP `VERSION_STATUS_LABEL_SP`, vsl.LABEL_PR `VERSION_STATUS_LABEL_PR` 
        FROM rm_program_version pv 
        LEFT JOIN ap_version_type vt ON pv.VERSION_TYPE_ID=vt.VERSION_TYPE_ID
        LEFT JOIN ap_label vtl ON vt.LABEL_ID=vtl.LABEL_ID 
        LEFT JOIN ap_version_status vs ON pv.VERSION_STATUS_ID=vs.VERSION_STATUS_ID
        LEFT JOIN ap_label vsl ON vs.LABEL_ID=vsl.LABEL_ID
        LEFT JOIN us_user cb ON pv.CREATED_BY=cb.USER_ID
        LEFT JOIN us_user lmb ON pv.LAST_MODIFIED_BY=lmb.USER_ID
        WHERE pv.VERSION_ID=@versionId AND pv.PROGRAM_ID=@programId;
END$$

DELIMITER ;
;

