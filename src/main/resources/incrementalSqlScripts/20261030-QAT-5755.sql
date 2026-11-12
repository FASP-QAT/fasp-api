-- ----------------------------------------------------------------------------
-- CR QAT-5755: Incremental Updates for Broadcast Messaging & Notification Preferences
-- Safe to execute on databases where 20261028-QAT-5755.sql has already been run
-- ----------------------------------------------------------------------------
USE `fasp`;

-- 1. Safely add TARGET_ROLES column to ap_broadcast_message if it does not exist
DROP PROCEDURE IF EXISTS `add_broadcast_target_roles_col`;
DELIMITER $$
CREATE PROCEDURE `add_broadcast_target_roles_col`()
BEGIN
    IF NOT EXISTS (
        SELECT * FROM information_schema.COLUMNS 
        WHERE TABLE_SCHEMA = DATABASE() 
        AND TABLE_NAME = 'ap_broadcast_message' 
        AND COLUMN_NAME = 'TARGET_ROLES'
    ) THEN
        ALTER TABLE `ap_broadcast_message` 
        ADD COLUMN `TARGET_ROLES` text NULL COMMENT 'JSON array of target ROLE_IDs' AFTER `BODY_HTML`;
    END IF;
END$$
DELIMITER ;
CALL `add_broadcast_target_roles_col`();
DROP PROCEDURE `add_broadcast_target_roles_col`;

-- 1b. Safely add LAST_MODIFIED_DATE and LAST_MODIFIED_BY to ap_broadcast_message if missing
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

-- 2. Ensure ap_label_source exists for ap_email_category
INSERT IGNORE INTO `ap_label_source` (`SOURCE_DESC`, `SOURCE_TEXT`) VALUES ('ap_email_category', 'Email Category');
SELECT MAX(SOURCE_ID) INTO @email_category_source_id FROM `ap_label_source` WHERE SOURCE_DESC = 'ap_email_category';

-- 3. Procedure to guarantee that all 3 categories (System Updates, Promotional, Transactional) exist and have proper labels
DROP PROCEDURE IF EXISTS `ensure_email_category`;
DELIMITER $$
CREATE PROCEDURE `ensure_email_category`(
    IN p_cat_id INT,
    IN p_code VARCHAR(30),
    IN p_en VARCHAR(255),
    IN p_fr VARCHAR(255),
    IN p_sp VARCHAR(255),
    IN p_pr VARCHAR(255),
    IN p_allow_opt_out TINYINT,
    IN p_default_opt_in TINYINT
)
BEGIN
    DECLARE v_cnt INT;
    DECLARE v_label_id INT;
    
    SELECT COUNT(*) INTO v_cnt FROM `ap_email_category` WHERE `CATEGORY_ID` = p_cat_id;
    
    IF v_cnt = 0 THEN
        -- Insert new label into ap_label
        INSERT INTO `ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`, `SOURCE_ID`)
        VALUES (null, p_en, p_fr, p_sp, p_pr, 1, NOW(), 1, NOW(), @email_category_source_id);
        SET v_label_id = LAST_INSERT_ID();
        
        -- Insert category with explicit ID
        INSERT INTO `ap_email_category` (`CATEGORY_ID`, `CATEGORY_CODE`, `LABEL_ID`, `ALLOW_OPT_OUT`, `DEFAULT_OPT_IN`)
        VALUES (p_cat_id, p_code, v_label_id, p_allow_opt_out, p_default_opt_in);
    ELSE
        -- Update existing category and its label
        SELECT `LABEL_ID` INTO v_label_id FROM `ap_email_category` WHERE `CATEGORY_ID` = p_cat_id;
        
        UPDATE `ap_label` 
        SET `LABEL_EN` = p_en, `LABEL_FR` = p_fr, `LABEL_SP` = p_sp, `LABEL_PR` = p_pr, `LAST_MODIFIED_DATE` = NOW()
        WHERE `LABEL_ID` = v_label_id;
        
        UPDATE `ap_email_category` 
        SET `CATEGORY_CODE` = p_code, `ALLOW_OPT_OUT` = p_allow_opt_out, `DEFAULT_OPT_IN` = p_default_opt_in
        WHERE `CATEGORY_ID` = p_cat_id;
    END IF;
END$$
DELIMITER ;

CALL ensure_email_category(1, 'SYSTEM_UPDATE', 'System Updates', 'Mises à jour du système', 'Actualizaciones del sistema', 'Atualizações do sistema', 0, 1);
CALL ensure_email_category(2, 'PROMOTIONAL', 'Promotional', 'Promotionnel', 'Promocional', 'Promocional', 1, 1);
CALL ensure_email_category(3, 'TRANSACTIONAL', 'Transactional', 'Transactionnel', 'Transaccional', 'Transacional', 1, 1);

DROP PROCEDURE `ensure_email_category`;

-- Re-create view to ensure fresh mappings
CREATE OR REPLACE VIEW `vw_email_category` AS
    SELECT 
        `c`.`CATEGORY_ID` AS `CATEGORY_ID`,
        `c`.`CATEGORY_CODE` AS `CATEGORY_CODE`,
        `c`.`LABEL_ID` AS `LABEL_ID`,
        `c`.`ALLOW_OPT_OUT` AS `ALLOW_OPT_OUT`,
        `c`.`DEFAULT_OPT_IN` AS `DEFAULT_OPT_IN`,
        `l`.`LABEL_EN` AS `LABEL_EN`,
        `l`.`LABEL_FR` AS `LABEL_FR`,
        `l`.`LABEL_SP` AS `LABEL_SP`,
        `l`.`LABEL_PR` AS `LABEL_PR`
    FROM
        (`ap_email_category` `c`
        LEFT JOIN `ap_label` `l` ON ((`c`.`LABEL_ID` = `l`.`LABEL_ID`)));

-- 4. Safely insert or update static labels for notification preferences & broadcast UI
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

CALL insert_static_label_if_missing('static.broadcast.broadcastMessage', 'Broadcast Message', 'Message de diffusion', 'Mensaje de difusión', 'Mensagem de transmissão');
CALL insert_static_label_if_missing('static.broadcast.compose', 'Compose', 'Rédiger', 'Redactar', 'Compor');
CALL insert_static_label_if_missing('static.broadcast.history', 'Broadcast History', 'Historique des diffusions', 'Historial de difusiones', 'Histórico de transmissões');
CALL insert_static_label_if_missing('static.broadcast.category', 'Category', 'Catégorie', 'Categoría', 'Categoria');
CALL insert_static_label_if_missing('static.broadcast.targetAudience', 'Target Audience', 'Public cible', 'Público objetivo', 'Público-alvo');
CALL insert_static_label_if_missing('static.broadcast.preferredLanguage', 'Preferred Language', 'Langue préférée', 'Idioma preferido', 'Idioma preferido');
CALL insert_static_label_if_missing('static.broadcast.estimatedRecipients', 'Estimated Recipients', 'Destinataires estimés', 'Destinatarios estimados', 'Destinatários estimados');
CALL insert_static_label_if_missing('static.broadcast.subject', 'Subject', 'Objet', 'Asunto', 'Assunto');
CALL insert_static_label_if_missing('static.broadcast.body', 'Message Body', 'Corps du message', 'Cuerpo del mensaje', 'Corpo da mensagem');
CALL insert_static_label_if_missing('static.broadcast.send', 'Send Broadcast', 'Envoyer la diffusion', 'Enviar difusión', 'Enviar transmissão');
CALL insert_static_label_if_missing('static.broadcast.success', 'Broadcast message sent successfully.', 'Message de diffusion envoyé avec succès.', 'Mensaje de difusión enviado con éxito.', 'Mensagem de transmissão enviada com sucesso.');
CALL insert_static_label_if_missing('static.broadcast.addedSuccess', 'Broadcast message added successfully.', 'Message de diffusion ajouté avec succès.', 'Mensaje de difusión agregado con éxito.', 'Mensagem de transmissão adicionada com sucesso.');
CALL insert_static_label_if_missing('static.broadcast.queuedSuccess', 'Broadcast message submitted successfully and queued for delivery.', 'Message de diffusion soumis avec succès et mis en file d''attente pour livraison.', 'Mensaje de difusión enviado con éxito y puesto en cola para su entrega.', 'Mensagem de transmissão enviada com sucesso e colocada na fila para entrega.');
CALL insert_static_label_if_missing('static.broadcast.statusQueued', 'Queued', 'En file d''attente', 'En cola', 'Na fila');
CALL insert_static_label_if_missing('static.broadcast.statusCompleted', 'Completed', 'Terminé', 'Completado', 'Concluído');
CALL insert_static_label_if_missing('static.broadcast.mandatory', 'Mandatory system announcement (no opt-out)', 'Annonce système obligatoire (sans désinscription)', 'Anuncio obligatorio del sistema (sin exclusión voluntaria)', 'Anúncio obrigatório do sistema (sem cancelamento)');
CALL insert_static_label_if_missing('static.broadcast.notificationPreferences', 'Notification Preferences', 'Préférences de notification', 'Preferencias de notificación', 'Preferências de notificação');
CALL insert_static_label_if_missing('static.broadcast.notificationPrefHelp', 'Manage your email subscription preferences for notifications and announcements sent by QAT.', 'Gérez vos préférences d''abonnement aux notifications et annonces envoyées par QAT.', 'Administre sus preferencias de suscripción para las notificaciones y comunicados enviados por QAT.', 'Gerencie suas preferências de assinatura de notificações e comunicados do QAT.');
CALL insert_static_label_if_missing('static.broadcast.systemUpdateDesc', 'System updates, maintenance downtime, and mandatory deadlines (e.g., you must upload by X date). No opt out.', 'Mises à jour du système, interruptions de maintenance et échéances obligatoires. Sans désinscription.', 'Actualizaciones del sistema, mantenimiento y plazos obligatorios. Sin exclusión voluntaria.', 'Atualizações do sistema, manutenção e prazos obrigatórios. Sem cancelamento.');
CALL insert_static_label_if_missing('static.broadcast.promotionalDesc', 'Newsletters and general announcements. Default is to receive; user can opt out.', 'Bulletins d''information et annonces générales. Réception par défaut ; désinscription possible.', 'Boletines y anuncios generales. Predeterminado recibir; exclusión opcional.', 'Boletins informativos e anúncios gerais. Padrão receber; cancelamento opcional.');
CALL insert_static_label_if_missing('static.broadcast.transactionalDesc', 'Workflow notifications (e.g., supply plan approved, sent back, or upload notifications). Default is to receive; user can opt out.', 'Notifications de flux de travail (ex. plan approuvé, renvoyé). Réception par défaut ; désinscription possible.', 'Notificaciones de flujo de trabajo (p. ej., plan aprobado, devuelto). Predeterminado recibir; exclusión opcional.', 'Notificações de fluxo de trabalho (ex.: plano aprovado, reenviado). Padrão receber; cancelamento opcional.');
CALL insert_static_label_if_missing('static.broadcast.receiveEmails', 'Receive Emails', 'Recevoir les e-mails', 'Recibir correos', 'Receber e-mails');
CALL insert_static_label_if_missing('static.broadcast.subscribed', 'Subscribed', 'Abonné', 'Suscrito', 'Inscrito');
CALL insert_static_label_if_missing('static.broadcast.optedOut', 'Opted Out', 'Désinscrit', 'Excluido', 'Cancelado');
CALL insert_static_label_if_missing('static.broadcast.activeUsers', 'Active Users', 'Utilisateurs actifs', 'Usuarios activos', 'Utilizadores ativos');
CALL insert_static_label_if_missing('static.broadcast.allRoles', 'All Roles', 'Tous les rôles', 'Todos los roles', 'Todos os papéis');
CALL insert_static_label_if_missing('static.broadcast.bccNote', 'Delivered via BCC chunks (≤ 200 recipients per email).', 'Envoyé par lots CCI (≤ 200 destinataires par e-mail).', 'Enviado en lotes CCO (≤ 200 destinatarios por correo).', 'Enviado em lotes CCO (≤ 200 destinatários por e-mail).');
CALL insert_static_label_if_missing('static.broadcast.targetAudienceHelp', 'Select specific roles or leave empty to reach all active users.', 'Sélectionnez des rôles spécifiques ou laissez vide pour tous les utilisateurs actifs.', 'Seleccione roles específicos o deje en blanco para llegar a todos los usuarios activos.', 'Selecione funções específicas ou deixe em branco para alcançar todos os utilizadores ativos.');
CALL insert_static_label_if_missing('static.broadcast.bodyHelp', 'Replies to broadcast emails are not monitored. Standard QAT support footer with support@quantificationanalytics.org will be included automatically.', 'Les réponses ne sont pas surveillées. Le pied de page officiel du support sera ajouté automatiquement.', 'Las respuestas no son monitoreadas. El pie de página estándar de soporte de QAT se incluirá automáticamente.', 'As respostas não são monitoradas. O rodapé padrão de suporte do QAT será incluído automaticamente.');
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

