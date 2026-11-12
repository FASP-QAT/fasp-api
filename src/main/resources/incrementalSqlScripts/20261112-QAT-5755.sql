-- ----------------------------------------------------------------------------
-- CR QAT-5755: Incremental Updates for Client Feedback (2026-11-12)
-- Safe to execute on databases where previous QAT-5755 scripts have already run
-- ----------------------------------------------------------------------------
USE `fasp`;

-- 1. Safely add LAST_MODIFIED_DATE and LAST_MODIFIED_BY to ap_broadcast_message if missing
DROP PROCEDURE IF EXISTS `add_broadcast_mod_cols`;
DELIMITER $$
CREATE PROCEDURE `add_broadcast_mod_cols`()
BEGIN
    IF NOT EXISTS (
        SELECT * FROM information_schema.COLUMNS 
        WHERE TABLE_SCHEMA = DATABASE() 
        AND TABLE_NAME = 'ap_broadcast_message' 
        AND COLUMN_NAME = 'LAST_MODIFIED_DATE'
    ) THEN
        ALTER TABLE `ap_broadcast_message` 
        ADD COLUMN `LAST_MODIFIED_DATE` datetime NULL AFTER `CREATED_DATE`,
        ADD COLUMN `LAST_MODIFIED_BY` int unsigned NULL AFTER `LAST_MODIFIED_DATE`;
    END IF;
END$$
DELIMITER ;
CALL `add_broadcast_mod_cols`();
DROP PROCEDURE `add_broadcast_mod_cols`;

-- 2. Safely insert or update newly added static labels for expanded ribbon & draft UI
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

CALL insert_static_label_if_missing('static.broadcast.newMessage', 'New Message', 'Nouveau message', 'Nuevo mensaje', 'Nova mensagem');
CALL insert_static_label_if_missing('static.broadcast.saveDraft', 'Save as Draft', 'Enregistrer comme brouillon', 'Guardar como borrador', 'Salvar como rascunho');
CALL insert_static_label_if_missing('static.broadcast.draftSaved', 'Broadcast message saved as draft.', 'Message de diffusion enregistré comme brouillon.', 'Mensaje de difusión guardado como borrador.', 'Mensagem de transmissão salva como rascunho.');
CALL insert_static_label_if_missing('static.broadcast.draft', 'Draft', 'Brouillon', 'Borrador', 'Rascunho');
CALL insert_static_label_if_missing('static.broadcast.editDraft', 'Edit Draft', 'Modifier le brouillon', 'Editar borrador', 'Editar rascunho');
CALL insert_static_label_if_missing('static.broadcast.recipientsList', 'Recipients List', 'Liste des destinataires', 'Lista de destinatarios', 'Lista de destinatários');
CALL insert_static_label_if_missing('static.broadcast.backToList', 'Back to Messages', 'Retour aux messages', 'Volver a los mensajes', 'Voltar para as mensagens');
CALL insert_static_label_if_missing('static.broadcast.time', 'Time', 'Heure', 'Hora', 'Hora');
CALL insert_static_label_if_missing('static.broadcast.sentSuccess', 'Broadcast message sent successfully', 'Message de diffusion envoyé avec succès', 'Mensaje de difusión enviado con éxito', 'Mensagem de transmissão enviada com sucesso');
CALL insert_static_label_if_missing('static.broadcast.normalText', 'Normal', 'Normal', 'Normal', 'Normal');
CALL insert_static_label_if_missing('static.broadcast.heading', 'Heading', 'Titre', 'Encabezado', 'Título');
CALL insert_static_label_if_missing('static.broadcast.indent', 'Indent', 'Indenter', 'Aumentar sangría', 'Aumentar recuo');
CALL insert_static_label_if_missing('static.broadcast.outdent', 'Outdent', 'Désindenter', 'Reducir sangría', 'Diminuir recuo');
CALL insert_static_label_if_missing('static.broadcast.clearFormatting', 'Clear Formatting', 'Effacer la mise en forme', 'Borrar formato', 'Limpar formatação');
CALL insert_static_label_if_missing('static.broadcast.textColor', 'Text Color', 'Couleur du texte', 'Color del texto', 'Cor do texto');
CALL insert_static_label_if_missing('static.broadcast.highlightColor', 'Highlight Color', 'Couleur de surbrillance', 'Color de resaltado', 'Cor de realce');
CALL insert_static_label_if_missing('static.broadcast.fontSize', 'Font Size', 'Taille de police', 'Tamaño de fuente', 'Tamanho da fonte');
CALL insert_static_label_if_missing('static.broadcast.alignLeft', 'Align Left', 'Aligner à gauche', 'Alinear a la izquierda', 'Alinhar à esquerda');
CALL insert_static_label_if_missing('static.broadcast.alignCenter', 'Align Center', 'Centrer', 'Centrar', 'Centralizar');
CALL insert_static_label_if_missing('static.broadcast.alignRight', 'Align Right', 'Aligner à droite', 'Alinear a la derecha', 'Alinhar à direita');
CALL insert_static_label_if_missing('static.broadcast.alignJustify', 'Justify', 'Justifier', 'Justificar', 'Justificar');

DROP PROCEDURE IF EXISTS `insert_static_label_if_missing`;

