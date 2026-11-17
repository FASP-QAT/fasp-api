-- ----------------------------------------------------------------------------
-- CR QAT-5755: Incremental Updates for Client Feedback (2026-11-17)
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

CALL insert_static_label_if_missing('static.broadcast.batches', 'Batches', 'Lots', 'Lotes', 'Lotes');
CALL insert_static_label_if_missing('static.broadcast.bodyPlaceholder', 'Compose your announcement message here...', 'Rédigez votre message d\'annonce ici...', 'Escriba su mensaje de anuncio aquí...', 'Escreva sua mensagem de anúncio aqui...');
CALL insert_static_label_if_missing('static.broadcast.bodyRequired', 'Message body cannot be empty.', 'Le corps du message ne peut pas être vide.', 'El cuerpo del mensaje no puede estar vacío.', 'O corpo da mensagem não pode ficar vazio.');
CALL insert_static_label_if_missing('static.broadcast.enterUrl', 'Enter URL (e.g. https://www.example.com):', 'Entrez l\'URL (ex. https://www.example.com) :', 'Ingrese la URL (ej. https://www.example.com):', 'Digite a URL (ex. https://www.example.com):');
CALL insert_static_label_if_missing('static.broadcast.historyDetails', 'Broadcast Details', 'Détails de la diffusion', 'Detalles de la difusión', 'Detalhes da transmissão');
CALL insert_static_label_if_missing('static.broadcast.roleRequired', 'Please select at least one role in Target Audience.', 'Veuillez sélectionner au moins un rôle dans le public cible.', 'Seleccione al menos un rol en el público objetivo.', 'Selecione pelo menos uma função no público-alvo.');
CALL insert_static_label_if_missing('static.broadcast.subjectPlaceholder', 'Enter message subject line...', 'Entrez l\'objet du message...', 'Ingrese el asunto del mensaje...', 'Digite o assunto da mensagem...');
CALL insert_static_label_if_missing('static.broadcast.subjectRequired', 'Subject line is required.', 'L\'objet est obligatoire.', 'El asunto es obligatorio.', 'O assunto é obrigatório.');
CALL insert_static_label_if_missing('static.broadcast.viewRecipientsTooltip', 'Click to view recipients list', 'Cliquez pour voir la liste des destinataires', 'Haga clic para ver la lista de destinatarios', 'Clique para ver a lista de destinatários');
CALL insert_static_label_if_missing('static.common.date', 'Date', 'Date', 'Fecha', 'Data');
CALL insert_static_label_if_missing('static.common.dateTime', 'Date / Time', 'Date / Heure', 'Fecha / Hora', 'Data / Hora');
CALL insert_static_label_if_missing('static.common.mandatory', 'Mandatory', 'Obligatoire', 'Obligatorio', 'Obrigatório');
CALL insert_static_label_if_missing('static.common.noDataFound', 'No data found', 'Aucune donnée trouvée', 'No se encontraron datos', 'Nenhum dado encontrado');
CALL insert_static_label_if_missing('static.common.sentBy', 'Sent By', 'Envoyé par', 'Enviado por', 'Enviado por');
CALL insert_static_label_if_missing('static.common.view', 'View', 'Voir', 'Ver', 'Visualizar');
CALL insert_static_label_if_missing('static.user.emailId', 'Email ID', 'Identifiant e-mail', 'Correo electrónico', 'ID de e-mail');
CALL insert_static_label_if_missing('static.broadcast.previewRecipients', 'Preview Recipients', 'Aperçu des destinataires', 'Vista previa de destinatarios', 'Pré-visualizar destinatários');
CALL insert_static_label_if_missing('static.broadcast.searchPlaceholder', 'Search by subject, message content, category, or sender...', 'Rechercher par objet, contenu, catégorie ou expéditeur...', 'Buscar por asunto, contenido, categoría o remitente...', 'Pesquisar por assunto, conteúdo, categoria ou remetente...');
CALL insert_static_label_if_missing('static.broadcast.allRoles', 'All Roles', 'Tous les rôles', 'Todos los roles', 'Todas as funções');
CALL insert_static_label_if_missing('static.broadcast.allLanguages', 'All Languages', 'Toutes les langues', 'Todos los idiomas', 'Todos os idiomas');
CALL insert_static_label_if_missing('static.broadcast.targetRoles', 'Roles', 'Rôles', 'Roles', 'Funções');
CALL insert_static_label_if_missing('static.broadcast.targetLanguages', 'Languages', 'Langues', 'Idiomas', 'Idiomas');
CALL insert_static_label_if_missing('static.broadcast.statusQueued', 'Queued', 'En file d\'attente', 'En cola', 'Na fila');
CALL insert_static_label_if_missing('static.broadcast.statusCompleted', 'Completed', 'Terminé', 'Completado', 'Concluído');
CALL insert_static_label_if_missing('static.broadcast.queuedSuccess', 'Broadcast message queued for delivery successfully', 'Message de diffusion mis en file d\'attente avec succès', 'Mensaje de difusión puesto en cola con éxito', 'Mensagem de transmissão enfileirada com sucesso');
CALL insert_static_label_if_missing('static.broadcast.languageRequired', 'Please select at least one language.', 'Veuillez sélectionner au moins une langue.', 'Seleccione al menos un idioma.', 'Selecione pelo menos um idioma.');
CALL insert_static_label_if_missing('static.broadcast.presetColors', 'Preset Colors', 'Couleurs prédéfinies', 'Colores preestablecidos', 'Cores predefinidas');
CALL insert_static_label_if_missing('static.broadcast.customColor', 'Custom', 'Personnalisé', 'Personalizado', 'Personalizado');
CALL insert_static_label_if_missing('static.broadcast.previewRecipientsTitle', 'Target Recipients Preview', 'Aperçu des destinataires cibles', 'Vista previa de destinatarios seleccionados', 'Pré-visualização de destinatários selecionados');

DROP PROCEDURE IF EXISTS `insert_static_label_if_missing`;
