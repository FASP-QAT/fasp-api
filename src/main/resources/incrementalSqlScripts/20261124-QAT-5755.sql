USE `fasp`;

-- ----------------------------------------------------------------------------
-- CR QAT-5755: Incremental Updates for Client Feedback (2026-11-24)
-- Points 17, 21, 22, 24
-- ----------------------------------------------------------------------------

-- Point 17: Create Business Function ROLE_BF_BROADCAST_BOUNCEBACK_INFO
INSERT INTO `ap_label` 
(`LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`, `SOURCE_ID`)
VALUES 
('Broadcast Bounceback Info', 'Informations sur les rebonds de diffusion', 'Información de rebotes de difusión', 'Informações de devolução de transmissão', 1, NOW(), 1, NOW(), 24);

SET @BF_BOUNCEBACK_LABEL_ID = LAST_INSERT_ID();

INSERT IGNORE INTO `us_business_function` 
(`BUSINESS_FUNCTION_ID`, `LABEL_ID`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`)
VALUES 
('ROLE_BF_BROADCAST_BOUNCEBACK_INFO', @BF_BOUNCEBACK_LABEL_ID, 1, NOW(), 1, NOW());

INSERT IGNORE INTO `us_role_business_function` 
(`ROLE_ID`, `BUSINESS_FUNCTION_ID`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`)
VALUES 
('ROLE_REALM_ADMIN', 'ROLE_BF_BROADCAST_BOUNCEBACK_INFO', 1, NOW(), 1, NOW()),
('ROLE_APPLICATION_ADMIN', 'ROLE_BF_BROADCAST_BOUNCEBACK_INFO', 1, NOW(), 1, NOW());

-- Point 22: Rename 'PROMOTIONAL' to 'COMMUNITY', update labels and descriptions
-- Update Category 2 (Promotional -> Community)
UPDATE `ap_email_category` SET `CATEGORY_CODE` = 'COMMUNITY' WHERE `CATEGORY_ID` = 2;

-- Update Labels for Category 1 ('System Update') and Category 2 ('Community')
UPDATE `ap_label` l 
JOIN `ap_email_category` c ON c.`LABEL_ID` = l.`LABEL_ID`
SET 
  l.`LABEL_EN` = 'System Update',
  l.`LABEL_FR` = 'Mise à jour du système',
  l.`LABEL_SP` = 'Actualización del sistema',
  l.`LABEL_PR` = 'Atualização do sistema'
WHERE c.`CATEGORY_ID` = 1;

UPDATE `ap_label` l 
JOIN `ap_email_category` c ON c.`LABEL_ID` = l.`LABEL_ID`
SET 
  l.`LABEL_EN` = 'Community',
  l.`LABEL_FR` = 'Communauté',
  l.`LABEL_SP` = 'Comunidad',
  l.`LABEL_PR` = 'Comunidade'
WHERE c.`CATEGORY_ID` = 2;

-- Ensure ACTIVE column exists on ap_email_category to hide Category 3 (Transactional)
SET @col_exists = (SELECT COUNT(*) FROM information_schema.COLUMNS 
    WHERE TABLE_SCHEMA = 'fasp' AND TABLE_NAME = 'ap_email_category' AND COLUMN_NAME = 'ACTIVE');

SET @stmt = IF(@col_exists = 0, 
    'ALTER TABLE `ap_email_category` ADD COLUMN `ACTIVE` tinyint(1) UNSIGNED NOT NULL DEFAULT 1', 
    'SELECT 1');
PREPARE s FROM @stmt; EXECUTE s; DEALLOCATE PREPARE s;

-- Set Active flags: System Update and Community are Active (1), Transactional is Hidden (0)
UPDATE `ap_email_category` SET `ACTIVE` = 1 WHERE `CATEGORY_ID` IN (1, 2);
UPDATE `ap_email_category` SET `ACTIVE` = 0 WHERE `CATEGORY_ID` = 3;

-- Update View vw_email_category to include ACTIVE
CREATE OR REPLACE VIEW `vw_email_category` AS
    SELECT 
        `c`.`CATEGORY_ID` AS `CATEGORY_ID`,
        `c`.`CATEGORY_CODE` AS `CATEGORY_CODE`,
        `c`.`LABEL_ID` AS `LABEL_ID`,
        `c`.`ALLOW_OPT_OUT` AS `ALLOW_OPT_OUT`,
        `c`.`DEFAULT_OPT_IN` AS `DEFAULT_OPT_IN`,
        `c`.`ACTIVE` AS `ACTIVE`,
        `l`.`LABEL_EN` AS `LABEL_EN`,
        `l`.`LABEL_FR` AS `LABEL_FR`,
        `l`.`LABEL_SP` AS `LABEL_SP`,
        `l`.`LABEL_PR` AS `LABEL_PR`
    FROM
        (`ap_email_category` `c`
        LEFT JOIN `ap_label` `l` ON ((`c`.`LABEL_ID` = `l`.`LABEL_ID`)));

-- Static Labels Procedure
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

-- Point 17: Bounceback notice label
CALL insert_static_label_if_missing(
    'static.broadcast.bouncebackNotice',
    'Bouncebacks are not tracked in QAT. Please check the noreply@quantificationanalytics.org email inbox for bounceback emails where delivery failed and update users accordingly.',
    'Les rebonds ne sont pas suivis dans QAT. Veuillez vérifier la boîte de réception noreply@quantificationanalytics.org pour les e-mails rejetés et mettre à jour les utilisateurs en conséquence.',
    'Los rebotes no se rastrean en QAT. Verifique la bandeja de entrada de noreply@quantificationanalytics.org para ver los correos no entregados y actualice los usuarios en consecuencia.',
    'As devoluções não são rastreadas no QAT. Verifique a caixa de entrada noreply@quantificationanalytics.org para e-mails devolvidos e atualize os usuários adequadamente.'
);

-- Point 22: Category Descriptions in Preferences
CALL insert_static_label_if_missing(
    'static.broadcast.systemUpdateDesc',
    'System updates, maintenance downtime, and mandatory deadlines (e.g., you must upload by X date). Auto-workflow notifications (e.g. supply plan approved, sent back, or upload notifications).',
    'Mises à jour du système, interruptions de maintenance et échéances obligatoires. Notifications de flux de travail automatiques.',
    'Actualizaciones del sistema, tiempo de inactividad por mantenimiento y fechas límite obligatorias. Notificaciones automáticas de flujo de trabajo.',
    'Atualizações do sistema, tempo de inatividade para manutenção e prazos obrigatórios. Notificações automáticas de fluxo de trabalho.'
);

CALL insert_static_label_if_missing(
    'static.broadcast.communityDesc',
    'Newsletters and general announcements (new features, routine QMT/QGC updates)',
    'Bulletins d\'information et annonces générales (nouvelles fonctionnalités, mises à jour régulières de QMT/QGC)',
    'Boletines y anuncios generales (nuevas funciones, actualizaciones periódicas de QMT/QGC)',
    'Boletins informativos e anúncios gerais (novos recursos, atualizações de rotina do QMT/QGC)'
);

DROP PROCEDURE IF EXISTS `insert_static_label_if_missing`;
