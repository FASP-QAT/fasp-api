USE `fasp`;
DROP procedure IF EXISTS `getShipmentLinkingNotifications`;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `getShipmentLinkingNotifications`(PROGRAM_ID INT(10), VERSION_ID INT (10))
BEGIN
    SET @programId = PROGRAM_ID;
    SET @procurementAgentId = 1;
    SET @versionId = VERSION_ID;
    IF @versionId = -1 THEN
        SELECT MAX(pv.VERSION_ID) INTO @versionId FROM rm_program_version pv WHERE pv.PROGRAM_ID=@programId;
    END IF;

SELECT 
	n.`NOTIFICATION_ID`, n.`ADDRESSED`,n.`SHIPMENT_LINKING_ID`,n.NOTIFICATION_TYPE_ID,nt.LABEL_ID,nt.LABEL_EN,nt.LABEL_FR,nt.LABEL_SP,nt.LABEL_PR
FROM rm_erp_notification n 
LEFT JOIN vw_notification_type nt ON n.NOTIFICATION_TYPE_ID=nt.NOTIFICATION_TYPE_ID
left join rm_shipment_linking sl on sl.SHIPMENT_LINKING_ID=n.SHIPMENT_LINKING_ID
left join rm_shipment_linking_trans slt on slt.SHIPMENT_LINKING_ID=sl.SHIPMENT_LINKING_ID and slt.VERSION_ID<=@versionId
WHERE 
	n.ACTIVE AND sl.ACTIVE  and sl.PROGRAM_ID=@programId and slt.VERSION_ID<=@versionId
GROUP BY n.NOTIFICATION_ID
ORDER BY n.ADDRESSED DESC, n.NOTIFICATION_ID;
END$$

DELIMITER ;
;