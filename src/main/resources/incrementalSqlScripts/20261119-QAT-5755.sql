-- ----------------------------------------------------------------------------
-- CR QAT-5755: Incremental Updates for Client Feedback (2026-11-19)
-- ----------------------------------------------------------------------------
USE `fasp`;

DROP PROCEDURE IF EXISTS `insert_static_label_if_missing`;
DELIMITER $$
CREATE PROCEDURE `insert_static_label_if_missing`(
    IN p_label_code VARCHAR(255),
    IN p_en TEXT,
    IN p_fr TEXT,
    IN p_sp TEXT,
    IN p_pr TEXT
)
BEGIN
    DECLARE v_id INT;
    SELECT STATIC_LABEL_ID INTO v_id FROM `ap_static_label` WHERE `LABEL_CODE` = p_label_code LIMIT 1;
    IF v_id IS NULL THEN
        INSERT INTO `ap_static_label` (`STATIC_LABEL_ID`, `LABEL_CODE`, `ACTIVE`) VALUES (NULL, p_label_code, 1);
        SET v_id = LAST_INSERT_ID();
        INSERT INTO `ap_static_label_languages` VALUES (NULL, v_id, 1, p_en);
        INSERT INTO `ap_static_label_languages` VALUES (NULL, v_id, 2, p_fr);
        INSERT INTO `ap_static_label_languages` VALUES (NULL, v_id, 3, p_sp);
        INSERT INTO `ap_static_label_languages` VALUES (NULL, v_id, 4, p_pr);
    ELSE
        UPDATE `ap_static_label_languages` SET `LABEL_TEXT` = p_en WHERE `STATIC_LABEL_ID` = v_id AND `LANGUAGE_ID` = 1;
        UPDATE `ap_static_label_languages` SET `LABEL_TEXT` = p_fr WHERE `STATIC_LABEL_ID` = v_id AND `LANGUAGE_ID` = 2;
        UPDATE `ap_static_label_languages` SET `LABEL_TEXT` = p_sp WHERE `STATIC_LABEL_ID` = v_id AND `LANGUAGE_ID` = 3;
        UPDATE `ap_static_label_languages` SET `LABEL_TEXT` = p_pr WHERE `STATIC_LABEL_ID` = v_id AND `LANGUAGE_ID` = 4;
    END IF;
END$$
DELIMITER ;

-- Update success message per client feedback (Point 5)
CALL insert_static_label_if_missing('static.broadcast.queuedSuccess', 'Successfully queued for delivery', 'Mis en file d\'attente pour livraison avec succès', 'Puesto en cola para entrega con éxito', 'Colocado na fila para entrega com sucesso');

-- Add Recipients column header label (Point 16)
CALL insert_static_label_if_missing('static.broadcast.recipients', 'Recipients', 'Destinataires', 'Destinatarios', 'Destinatários');

DROP PROCEDURE IF EXISTS `insert_static_label_if_missing`;
