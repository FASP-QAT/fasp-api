-- ----------------------------------------------------------------------------
-- CR QAT-5755: Broadcast Messaging, Listserv & Notification Preferences
-- ----------------------------------------------------------------------------

-- 1. Master Table: Broadcast Categories
INSERT INTO `ap_label_source` (`SOURCE_DESC`, `SOURCE_TEXT`) VALUES ('ap_email_category', 'Email Category');
SELECT MAX(SOURCE_ID) INTO @email_category_source_id FROM `ap_label_source` WHERE SOURCE_DESC = 'ap_email_category';

INSERT INTO `ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`, `SOURCE_ID`)
VALUES (null, 'System Updates', 'Mises à jour du système', 'Actualizaciones del sistema', 'Atualizações do sistema', 1, NOW(), 1, NOW(), @email_category_source_id);
SET @cat_label_1 = LAST_INSERT_ID();

INSERT INTO `ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`, `SOURCE_ID`)
VALUES (null, 'Promotional', 'Promotionnel', 'Promocional', 'Promocional', 1, NOW(), 1, NOW(), @email_category_source_id);
SET @cat_label_2 = LAST_INSERT_ID();

INSERT INTO `ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`, `SOURCE_ID`)
VALUES (null, 'Transactional', 'Transactionnel', 'Transaccional', 'Transacional', 1, NOW(), 1, NOW(), @email_category_source_id);
SET @cat_label_3 = LAST_INSERT_ID();

CREATE TABLE IF NOT EXISTS `ap_email_category` (
  `CATEGORY_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `CATEGORY_CODE` varchar(30) NOT NULL UNIQUE,
  `LABEL_ID` int unsigned NOT NULL,
  `ALLOW_OPT_OUT` tinyint unsigned NOT NULL DEFAULT 1 COMMENT '1 = user can opt out, 0 = mandatory (system update)',
  `DEFAULT_OPT_IN` tinyint unsigned NOT NULL DEFAULT 1,
  PRIMARY KEY (`CATEGORY_ID`),
  KEY `fk_email_category_labelId_idx` (`LABEL_ID`),
  CONSTRAINT `fk_email_category_labelId` FOREIGN KEY (`LABEL_ID`) REFERENCES `ap_label` (`LABEL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

INSERT IGNORE INTO `ap_email_category` (`CATEGORY_ID`, `CATEGORY_CODE`, `LABEL_ID`, `ALLOW_OPT_OUT`, `DEFAULT_OPT_IN`) VALUES
(1, 'SYSTEM_UPDATE', @cat_label_1, 0, 1),
(2, 'PROMOTIONAL', @cat_label_2, 1, 1),
(3, 'TRANSACTIONAL', @cat_label_3, 1, 1);

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

-- 2. User Notification Preferences
CREATE TABLE IF NOT EXISTS `us_user_notification_preference` (
  `PREFERENCE_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `USER_ID` int unsigned NOT NULL,
  `CATEGORY_ID` int unsigned NOT NULL,
  `OPTED_OUT` tinyint unsigned NOT NULL DEFAULT 0,
  `LAST_MODIFIED_DATE` datetime NOT NULL,
  PRIMARY KEY (`PREFERENCE_ID`),
  UNIQUE KEY `unq_user_category` (`USER_ID`, `CATEGORY_ID`),
  CONSTRAINT `fk_pref_user` FOREIGN KEY (`USER_ID`) REFERENCES `us_user` (`USER_ID`),
  CONSTRAINT `fk_pref_category` FOREIGN KEY (`CATEGORY_ID`) REFERENCES `ap_email_category` (`CATEGORY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- 3. Unsubscribe Token for Direct Email Links
CREATE TABLE IF NOT EXISTS `us_user_unsubscribe_token` (
  `USER_ID` int unsigned NOT NULL,
  `TOKEN` varchar(64) NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  PRIMARY KEY (`USER_ID`),
  UNIQUE KEY `unq_unsub_token` (`TOKEN`),
  CONSTRAINT `fk_unsub_user` FOREIGN KEY (`USER_ID`) REFERENCES `us_user` (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- 4. Broadcast Message Log & Audit
CREATE TABLE IF NOT EXISTS `ap_broadcast_message` (
  `BROADCAST_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `CATEGORY_ID` int unsigned NOT NULL,
  `SUBJECT` varchar(255) NOT NULL,
  `BODY_HTML` mediumtext NOT NULL,
  `TARGET_ROLES` text NULL COMMENT 'JSON array of target ROLE_IDs',
  `TARGET_BUSINESS_FUNCTIONS` text NULL COMMENT 'JSON array of target BUSINESS_FUNCTION_IDs',
  `TARGET_LANGUAGES` text NULL COMMENT 'JSON array of target LANGUAGE_IDs',
  `TOTAL_RECIPIENTS` int unsigned NOT NULL DEFAULT 0,
  `BATCH_COUNT` int unsigned NOT NULL DEFAULT 0,
  `STATUS` varchar(20) NOT NULL DEFAULT 'COMPLETED',
  `CREATED_BY` int unsigned NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  PRIMARY KEY (`BROADCAST_ID`),
  CONSTRAINT `fk_broadcast_category` FOREIGN KEY (`CATEGORY_ID`) REFERENCES `ap_email_category` (`CATEGORY_ID`),
  CONSTRAINT `fk_broadcast_createdBy` FOREIGN KEY (`CREATED_BY`) REFERENCES `us_user` (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- 5. Label for Business Function (SOURCE_ID = 24 for us_business_function)
INSERT INTO `ap_label` 
(`LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`, `SOURCE_ID`)
VALUES 
('Broadcast Message', 'Message de diffusion', 'Mensaje de difusión', 'Mensagem de transmissão', 1, NOW(), 1, NOW(), 24);

SET @BF_LABEL_ID = LAST_INSERT_ID();

-- 6. Insert Business Function into us_business_function
INSERT IGNORE INTO `us_business_function` 
(`BUSINESS_FUNCTION_ID`, `LABEL_ID`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`)
VALUES 
('ROLE_BF_BROADCAST_MESSAGE', @BF_LABEL_ID, 1, NOW(), 1, NOW());

-- 7. Role Business Function Mappings (Application Admin & Realm Admin)
INSERT IGNORE INTO `us_role_business_function` 
(`ROLE_ID`, `BUSINESS_FUNCTION_ID`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`)
VALUES 
('ROLE_APPLICATION_ADMIN', 'ROLE_BF_BROADCAST_MESSAGE', 1, NOW(), 1, NOW()),
('ROLE_INTERNAL_USER', 'ROLE_BF_BROADCAST_MESSAGE', 1, NOW(), 1, NOW()),
('ROLE_REALM_ADMIN', 'ROLE_BF_BROADCAST_MESSAGE', 1, NOW(), 1, NOW());

-- 8. API Security Mappings (METHOD: 1 = GET, 2 = POST)
INSERT IGNORE INTO `ap_security` (`METHOD`, `URL`, `BF`) VALUES 
(1, '/api/broadcast/**', 'ROLE_BF_BROADCAST_MESSAGE'),
(2, '/api/broadcast/**', 'ROLE_BF_BROADCAST_MESSAGE'),
(1, '/api/user/notificationPreference', 'ROLE_BF_LOGGED_IN'),
(2, '/api/user/notificationPreference', 'ROLE_BF_LOGGED_IN');

-- 9. Static Labels
INSERT INTO `ap_static_label` VALUES (null, 'static.broadcast.broadcastMessage', 1);
SELECT @label_id := MAX(STATIC_LABEL_ID) FROM ap_static_label;
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 1, 'Broadcast Message');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 2, 'Message de diffusion');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 3, 'Mensaje de difusión');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 4, 'Mensagem de transmissão');

INSERT INTO `ap_static_label` VALUES (null, 'static.broadcast.compose', 1);
SELECT @label_id := MAX(STATIC_LABEL_ID) FROM ap_static_label;
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 1, 'Compose');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 2, 'Rédiger');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 3, 'Redactar');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 4, 'Compor');

INSERT INTO `ap_static_label` VALUES (null, 'static.broadcast.history', 1);
SELECT @label_id := MAX(STATIC_LABEL_ID) FROM ap_static_label;
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 1, 'Broadcast History');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 2, 'Historique des diffusions');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 3, 'Historial de difusiones');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 4, 'Histórico de transmissões');

INSERT INTO `ap_static_label` VALUES (null, 'static.broadcast.category', 1);
SELECT @label_id := MAX(STATIC_LABEL_ID) FROM ap_static_label;
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 1, 'Category');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 2, 'Catégorie');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 3, 'Categoría');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 4, 'Categoria');

INSERT INTO `ap_static_label` VALUES (null, 'static.broadcast.targetAudience', 1);
SELECT @label_id := MAX(STATIC_LABEL_ID) FROM ap_static_label;
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 1, 'Target Audience');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 2, 'Public cible');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 3, 'Público objetivo');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 4, 'Público-alvo');

INSERT INTO `ap_static_label` VALUES (null, 'static.broadcast.preferredLanguage', 1);
SELECT @label_id := MAX(STATIC_LABEL_ID) FROM ap_static_label;
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 1, 'Preferred Language');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 2, 'Langue préférée');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 3, 'Idioma preferido');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 4, 'Idioma preferido');

INSERT INTO `ap_static_label` VALUES (null, 'static.broadcast.estimatedRecipients', 1);
SELECT @label_id := MAX(STATIC_LABEL_ID) FROM ap_static_label;
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 1, 'Estimated Recipients');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 2, 'Destinataires estimés');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 3, 'Destinatarios estimados');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 4, 'Destinatários estimados');

INSERT INTO `ap_static_label` VALUES (null, 'static.broadcast.subject', 1);
SELECT @label_id := MAX(STATIC_LABEL_ID) FROM ap_static_label;
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 1, 'Subject');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 2, 'Objet');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 3, 'Asunto');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 4, 'Assunto');

INSERT INTO `ap_static_label` VALUES (null, 'static.broadcast.body', 1);
SELECT @label_id := MAX(STATIC_LABEL_ID) FROM ap_static_label;
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 1, 'Message Body');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 2, 'Corps du message');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 3, 'Cuerpo del mensaje');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 4, 'Corpo da mensagem');

INSERT INTO `ap_static_label` VALUES (null, 'static.broadcast.send', 1);
SELECT @label_id := MAX(STATIC_LABEL_ID) FROM ap_static_label;
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 1, 'Send Broadcast');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 2, 'Envoyer la diffusion');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 3, 'Enviar difusión');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 4, 'Enviar transmissão');

INSERT INTO `ap_static_label` VALUES (null, 'static.broadcast.success', 1);
SELECT @label_id := MAX(STATIC_LABEL_ID) FROM ap_static_label;
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 1, 'Broadcast message sent successfully.');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 2, 'Message de diffusion envoyé avec succès.');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 3, 'Mensaje de difusión enviado con éxito.');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 4, 'Mensagem de transmissão enviada com sucesso.');

INSERT INTO `ap_static_label` VALUES (null, 'static.broadcast.mandatory', 1);
SELECT @label_id := MAX(STATIC_LABEL_ID) FROM ap_static_label;
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 1, 'Mandatory system announcement (no opt-out)');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 2, 'Annonce système obligatoire (sans désinscription)');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 3, 'Anuncio obligatorio del sistema (sin exclusión voluntaria)');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 4, 'Anúncio obrigatório do sistema (sem cancelamento)');

INSERT INTO `ap_static_label` VALUES (null, 'static.broadcast.notificationPreferences', 1);
SELECT @label_id := MAX(STATIC_LABEL_ID) FROM ap_static_label;
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 1, 'Notification Preferences');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 2, 'Préférences de notification');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 3, 'Preferencias de notificación');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 4, 'Preferências de notificação');

INSERT INTO `ap_static_label` VALUES (null, 'static.broadcast.systemUpdateDesc', 1);
SELECT @label_id := MAX(STATIC_LABEL_ID) FROM ap_static_label;
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 1, 'System updates, maintenance downtime, and mandatory deadlines (e.g., you must upload by X date). No opt out.');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 2, 'Mises à jour du système, interruptions de maintenance et échéances obligatoires. Sans désinscription.');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 3, 'Actualizaciones del sistema, mantenimiento y plazos obligatorios. Sin exclusión voluntaria.');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 4, 'Atualizações do sistema, manutenção e prazos obrigatórios. Sem cancelamento.');

INSERT INTO `ap_static_label` VALUES (null, 'static.broadcast.promotionalDesc', 1);
SELECT @label_id := MAX(STATIC_LABEL_ID) FROM ap_static_label;
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 1, 'Newsletters and general announcements. Default is to receive; user can opt out.');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 2, 'Bulletins d''information et annonces générales. Réception par défaut ; désinscription possible.');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 3, 'Boletines y anuncios generales. Predeterminado recibir; exclusión opcional.');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 4, 'Boletins informativos e anúncios gerais. Padrão receber; cancelamento opcional.');

INSERT INTO `ap_static_label` VALUES (null, 'static.broadcast.transactionalDesc', 1);
SELECT @label_id := MAX(STATIC_LABEL_ID) FROM ap_static_label;
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 1, 'Workflow notifications (e.g., supply plan approved, sent back, or upload notifications). Default is to receive; user can opt out.');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 2, 'Notifications de flux de travail (ex. plan approuvé, renvoyé). Réception par défaut ; désinscription possible.');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 3, 'Notificaciones de flujo de trabajo (p. ej., plan aprobado, devuelto). Predeterminado recibir; exclusión opcional.');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 4, 'Notificações de fluxo de trabalho (ex.: plano aprovado, reenviado). Padrão receber; cancelamento opcional.');

INSERT INTO `ap_static_label` VALUES (null, 'static.broadcast.receiveEmails', 1);
SELECT @label_id := MAX(STATIC_LABEL_ID) FROM ap_static_label;
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 1, 'Receive Emails');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 2, 'Recevoir les e-mails');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 3, 'Recibir correos');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 4, 'Receber e-mails');

INSERT INTO `ap_static_label` VALUES (null, 'static.broadcast.subscribed', 1);
SELECT @label_id := MAX(STATIC_LABEL_ID) FROM ap_static_label;
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 1, 'Subscribed');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 2, 'Abonné');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 3, 'Suscrito');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 4, 'Inscrito');

INSERT INTO `ap_static_label` VALUES (null, 'static.broadcast.optedOut', 1);
SELECT @label_id := MAX(STATIC_LABEL_ID) FROM ap_static_label;
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 1, 'Opted Out');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 2, 'Désinscrit');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 3, 'Excluido');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 4, 'Cancelado');

INSERT INTO `ap_static_label` VALUES (null, 'static.broadcast.addedSuccess', 1);
SELECT @label_id := MAX(STATIC_LABEL_ID) FROM ap_static_label;
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 1, 'Broadcast message added successfully.');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 2, 'Message de diffusion ajouté avec succès.');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 3, 'Mensaje de difusión agregado con éxito.');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 4, 'Mensagem de transmissão adicionada com sucesso.');

INSERT INTO `ap_static_label` VALUES (null, 'static.broadcast.queuedSuccess', 1);
SELECT @label_id := MAX(STATIC_LABEL_ID) FROM ap_static_label;
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 1, 'Broadcast message submitted successfully and queued for delivery.');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 2, 'Message de diffusion soumis avec succès et mis en file d\'attente pour livraison.');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 3, 'Mensaje de difusión enviado con éxito y puesto en cola para su entrega.');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 4, 'Mensagem de transmissão enviada com sucesso e colocada na fila para entrega.');

INSERT INTO `ap_static_label` VALUES (null, 'static.broadcast.statusQueued', 1);
SELECT @label_id := MAX(STATIC_LABEL_ID) FROM ap_static_label;
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 1, 'Queued');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 2, 'En file d\'attente');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 3, 'En cola');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 4, 'Na fila');

INSERT INTO `ap_static_label` VALUES (null, 'static.broadcast.statusCompleted', 1);
SELECT @label_id := MAX(STATIC_LABEL_ID) FROM ap_static_label;
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 1, 'Completed');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 2, 'Terminé');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 3, 'Completado');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 4, 'Concluído');

INSERT INTO `ap_static_label` VALUES (null, 'static.broadcast.notificationPrefHelp', 1);
SELECT @label_id := MAX(STATIC_LABEL_ID) FROM ap_static_label;
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 1, 'Manage your email subscription preferences for notifications and announcements sent by QAT.');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 2, 'Gérez vos préférences d\'abonnement aux notifications et annonces envoyées par QAT.');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 3, 'Administre sus preferencias de suscripción para las notificaciones y comunicados enviados por QAT.');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 4, 'Gerencie suas preferências de assinatura de notificações e comunicados do QAT.');

INSERT INTO `ap_static_label` VALUES (null, 'static.broadcast.activeUsers', 1);
SELECT @label_id := MAX(STATIC_LABEL_ID) FROM ap_static_label;
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 1, 'Active Users');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 2, 'Utilisateurs actifs');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 3, 'Usuarios activos');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 4, 'Utilizadores ativos');

INSERT INTO `ap_static_label` VALUES (null, 'static.broadcast.allRoles', 1);
SELECT @label_id := MAX(STATIC_LABEL_ID) FROM ap_static_label;
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 1, 'All Roles');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 2, 'Tous les rôles');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 3, 'Todos los roles');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 4, 'Todos os papéis');

INSERT INTO `ap_static_label` VALUES (null, 'static.broadcast.bccNote', 1);
SELECT @label_id := MAX(STATIC_LABEL_ID) FROM ap_static_label;
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 1, 'Delivered via BCC chunks (≤ 200 recipients per email).');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 2, 'Envoyé par lots CCI (≤ 200 destinataires par e-mail).');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 3, 'Enviado en lotes CCO (≤ 200 destinatarios por correo).');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 4, 'Enviado em lotes CCO (≤ 200 destinatários por e-mail).');

INSERT INTO `ap_static_label` VALUES (null, 'static.broadcast.targetAudienceHelp', 1);
SELECT @label_id := MAX(STATIC_LABEL_ID) FROM ap_static_label;
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 1, 'Select specific roles or leave empty to reach all active users.');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 2, 'Sélectionnez des rôles spécifiques ou laissez vide pour tous les utilisateurs actifs.');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 3, 'Seleccione roles específicos o deje en blanco para llegar a todos los usuarios activos.');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 4, 'Selecione funções específicas ou deixe em branco para alcançar todos os utilizadores ativos.');

INSERT INTO `ap_static_label` VALUES (null, 'static.broadcast.bodyHelp', 1);
SELECT @label_id := MAX(STATIC_LABEL_ID) FROM ap_static_label;
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 1, 'Replies to broadcast emails are not monitored. Standard QAT support footer with support@quantificationanalytics.org will be included automatically.');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 2, 'Les réponses ne sont pas surveillées. Le pied de page officiel du support sera ajouté automatiquement.');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 3, 'Las respuestas no son monitoreadas. El pie de página estándar de soporte de QAT se incluirá automáticamente.');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 4, 'As respostas não são monitoradas. O rodapé padrão de suporte do QAT será incluído automaticamente.');

INSERT INTO `ap_static_label` VALUES (null, 'static.broadcast.newMessage', 1);
SELECT @label_id := MAX(STATIC_LABEL_ID) FROM ap_static_label;
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 1, 'New Message');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 2, 'Nouveau message');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 3, 'Nuevo mensaje');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 4, 'Nova mensagem');

INSERT INTO `ap_static_label` VALUES (null, 'static.broadcast.saveDraft', 1);
SELECT @label_id := MAX(STATIC_LABEL_ID) FROM ap_static_label;
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 1, 'Save as Draft');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 2, 'Enregistrer comme brouillon');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 3, 'Guardar como borrador');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 4, 'Salvar como rascunho');

INSERT INTO `ap_static_label` VALUES (null, 'static.broadcast.draftSaved', 1);
SELECT @label_id := MAX(STATIC_LABEL_ID) FROM ap_static_label;
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 1, 'Broadcast message saved as draft.');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 2, 'Message de diffusion enregistré comme brouillon.');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 3, 'Mensaje de difusión guardado como borrador.');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 4, 'Mensagem de transmissão salva como rascunho.');

INSERT INTO `ap_static_label` VALUES (null, 'static.broadcast.draft', 1);
SELECT @label_id := MAX(STATIC_LABEL_ID) FROM ap_static_label;
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 1, 'Draft');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 2, 'Brouillon');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 3, 'Borrador');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 4, 'Rascunho');

INSERT INTO `ap_static_label` VALUES (null, 'static.broadcast.editDraft', 1);
SELECT @label_id := MAX(STATIC_LABEL_ID) FROM ap_static_label;
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 1, 'Edit Draft');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 2, 'Modifier le brouillon');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 3, 'Editar borrador');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 4, 'Editar rascunho');

INSERT INTO `ap_static_label` VALUES (null, 'static.broadcast.recipientsList', 1);
SELECT @label_id := MAX(STATIC_LABEL_ID) FROM ap_static_label;
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 1, 'Recipients List');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 2, 'Liste des destinataires');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 3, 'Lista de destinatarios');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 4, 'Lista de destinatários');

INSERT INTO `ap_static_label` VALUES (null, 'static.broadcast.backToList', 1);
SELECT @label_id := MAX(STATIC_LABEL_ID) FROM ap_static_label;
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 1, 'Back to Messages');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 2, 'Retour aux messages');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 3, 'Volver a los mensajes');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 4, 'Voltar para as mensagens');

INSERT INTO `ap_static_label` VALUES (null, 'static.broadcast.time', 1);
SELECT @label_id := MAX(STATIC_LABEL_ID) FROM ap_static_label;
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 1, 'Time');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 2, 'Heure');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 3, 'Hora');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 4, 'Hora');

INSERT INTO `ap_static_label` VALUES (null, 'static.broadcast.sentSuccess', 1);
SELECT @label_id := MAX(STATIC_LABEL_ID) FROM ap_static_label;
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 1, 'Broadcast message sent successfully');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 2, 'Message de diffusion envoyé avec succès');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 3, 'Mensaje de difusión enviado con éxito');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 4, 'Mensagem de transmissão enviada com sucesso');

INSERT INTO `ap_static_label` VALUES (null, 'static.broadcast.normalText', 1);
SELECT @label_id := MAX(STATIC_LABEL_ID) FROM ap_static_label;
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 1, 'Normal');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 2, 'Normal');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 3, 'Normal');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 4, 'Normal');

INSERT INTO `ap_static_label` VALUES (null, 'static.broadcast.heading', 1);
SELECT @label_id := MAX(STATIC_LABEL_ID) FROM ap_static_label;
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 1, 'Heading');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 2, 'Titre');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 3, 'Encabezado');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 4, 'Título');

INSERT INTO `ap_static_label` VALUES (null, 'static.broadcast.indent', 1);
SELECT @label_id := MAX(STATIC_LABEL_ID) FROM ap_static_label;
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 1, 'Indent');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 2, 'Indenter');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 3, 'Aumentar sangría');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 4, 'Aumentar recuo');

INSERT INTO `ap_static_label` VALUES (null, 'static.broadcast.outdent', 1);
SELECT @label_id := MAX(STATIC_LABEL_ID) FROM ap_static_label;
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 1, 'Outdent');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 2, 'Désindenter');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 3, 'Reducir sangría');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 4, 'Diminuir recuo');

INSERT INTO `ap_static_label` VALUES (null, 'static.broadcast.clearFormatting', 1);
SELECT @label_id := MAX(STATIC_LABEL_ID) FROM ap_static_label;
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 1, 'Clear Formatting');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 2, 'Effacer la mise en forme');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 3, 'Borrar formato');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 4, 'Limpar formatação');

INSERT INTO `ap_static_label` VALUES (null, 'static.broadcast.textColor', 1);
SELECT @label_id := MAX(STATIC_LABEL_ID) FROM ap_static_label;
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 1, 'Text Color');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 2, 'Couleur du texte');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 3, 'Color del texto');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 4, 'Cor do texto');

INSERT INTO `ap_static_label` VALUES (null, 'static.broadcast.highlightColor', 1);
SELECT @label_id := MAX(STATIC_LABEL_ID) FROM ap_static_label;
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 1, 'Highlight Color');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 2, 'Couleur de surbrillance');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 3, 'Color de resaltado');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 4, 'Cor de realce');

INSERT INTO `ap_static_label` VALUES (null, 'static.broadcast.fontSize', 1);
SELECT @label_id := MAX(STATIC_LABEL_ID) FROM ap_static_label;
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 1, 'Font Size');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 2, 'Taille de police');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 3, 'Tamaño de fuente');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 4, 'Tamanho da fonte');

INSERT INTO `ap_static_label` VALUES (null, 'static.broadcast.alignLeft', 1);
SELECT @label_id := MAX(STATIC_LABEL_ID) FROM ap_static_label;
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 1, 'Align Left');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 2, 'Aligner à gauche');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 3, 'Alinear a la izquierda');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 4, 'Alinhar à esquerda');

INSERT INTO `ap_static_label` VALUES (null, 'static.broadcast.alignCenter', 1);
SELECT @label_id := MAX(STATIC_LABEL_ID) FROM ap_static_label;
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 1, 'Align Center');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 2, 'Centrer');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 3, 'Centrar');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 4, 'Centralizar');

INSERT INTO `ap_static_label` VALUES (null, 'static.broadcast.alignRight', 1);
SELECT @label_id := MAX(STATIC_LABEL_ID) FROM ap_static_label;
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 1, 'Align Right');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 2, 'Aligner à droite');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 3, 'Alinear a la derecha');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 4, 'Alinhar à direita');

INSERT INTO `ap_static_label` VALUES (null, 'static.broadcast.alignJustify', 1);
SELECT @label_id := MAX(STATIC_LABEL_ID) FROM ap_static_label;
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 1, 'Justify');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 2, 'Justifier');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 3, 'Justificar');
INSERT INTO `ap_static_label_languages` VALUES (null, @label_id, 4, 'Justificar');



