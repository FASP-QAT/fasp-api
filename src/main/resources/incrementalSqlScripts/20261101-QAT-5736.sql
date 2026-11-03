-- ==============================================================================
-- Auto-Inactivation Feature - Complete Unified Database Script
-- Ticket: QAT-5736
-- ==============================================================================

-- ------------------------------------------------------------------------------
-- 1. Schema Alterations
-- ------------------------------------------------------------------------------

-- Add inactivity configuration to Realm table
ALTER TABLE `rm_realm`
    ADD COLUMN `NO_OF_DAYS_FOR_INACTIVITY_REMINDER` INT NOT NULL DEFAULT 60 COMMENT 'Days of inactivity before reminder email is sent',
    ADD COLUMN `NO_OF_DAYS_TO_LOCK_USER` INT NOT NULL DEFAULT 90 COMMENT 'Days of inactivity before user account is locked';

-- Add notes, reminder tracking, and auto-inactivation flags to User table
ALTER TABLE `us_user`
    ADD COLUMN `NOTES` TEXT DEFAULT NULL COMMENT 'Internal comment / approval note',
    ADD COLUMN `REMINDER_EMAIL_SENT` TINYINT(1) NOT NULL DEFAULT 0 COMMENT 'Flag indicating if reminder email was sent',
    ADD COLUMN `REMINDER_EMAIL_SENT_DATE` DATETIME DEFAULT NULL COMMENT 'Timestamp when reminder email was sent',
    ADD COLUMN `AUTO_INACTIVATED` TINYINT(1) NOT NULL DEFAULT 0 COMMENT 'Flag indicating if user was auto-inactivated due to inactivity';

-- ------------------------------------------------------------------------------
-- 2. Realm Default Values
-- ------------------------------------------------------------------------------

UPDATE `rm_realm`
SET `NO_OF_DAYS_FOR_INACTIVITY_REMINDER` = 60
WHERE `NO_OF_DAYS_FOR_INACTIVITY_REMINDER` IS NULL OR `NO_OF_DAYS_FOR_INACTIVITY_REMINDER` <= 0;

UPDATE `rm_realm`
SET `NO_OF_DAYS_TO_LOCK_USER` = 90
WHERE `NO_OF_DAYS_TO_LOCK_USER` IS NULL OR `NO_OF_DAYS_TO_LOCK_USER` <= 0;

-- Ensure consistency for any active users
UPDATE `us_user` SET `AUTO_INACTIVATED` = 0 WHERE `ACTIVE` = 1 AND `AUTO_INACTIVATED` = 1;

-- ------------------------------------------------------------------------------
-- 3. Business Function & Role Mappings
-- ------------------------------------------------------------------------------

-- Insert Label for Business Function
INSERT INTO `fasp`.`ap_label` (`LABEL_ID`, `LABEL_EN`, `LABEL_FR`, `LABEL_SP`, `LABEL_PR`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`, `SOURCE_ID`)
VALUES (NULL, 'Activate Auto-Inactivated User', 'Activer l''utilisateur désactivé automatiquement', 'Activar usuario desactivado automáticamente', 'Ativar usuário desativado automaticamente', 1, NOW(), 1, NOW(), 24);
SELECT MAX(l.LABEL_ID) INTO @BF_LABEL_ID FROM ap_label l;

-- Insert Business Function for activating auto-inactivated accounts
INSERT INTO `fasp`.`us_business_function` (`BUSINESS_FUNCTION_ID`, `LABEL_ID`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`)
VALUES ('ROLE_BF_ACTIVATE_AUTO_INACTIVATED_USER', @BF_LABEL_ID, 1, NOW(), 1, NOW());

-- Assign Business Function to Application Admin and Realm Admin roles
INSERT INTO `fasp`.`us_role_business_function` (`ROLE_BUSINESS_FUNCTION_ID`, `ROLE_ID`, `BUSINESS_FUNCTION_ID`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`)
VALUES (NULL, 'ROLE_APPLICATION_ADMIN', 'ROLE_BF_ACTIVATE_AUTO_INACTIVATED_USER', 1, NOW(), 1, NOW());

INSERT INTO `fasp`.`us_role_business_function` (`ROLE_BUSINESS_FUNCTION_ID`, `ROLE_ID`, `BUSINESS_FUNCTION_ID`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`)
VALUES (NULL, 'ROLE_REALM_ADMIN', 'ROLE_BF_ACTIVATE_AUTO_INACTIVATED_USER', 1, NOW(), 1, NOW());

-- ------------------------------------------------------------------------------
-- 4. Security API Endpoint Mapping
-- ------------------------------------------------------------------------------

INSERT IGNORE INTO `ap_security` (`METHOD`, `URL`, `BF`)
VALUES (2, '/api/user/activateAutoInactivatedUser/*', 'ROLE_BF_ACTIVATE_AUTO_INACTIVATED_USER');

-- ------------------------------------------------------------------------------
-- 5. Email Template (Inactivity Reminder)
-- ------------------------------------------------------------------------------

INSERT INTO `em_email_template` (
    `EMAIL_TEMPLATE_ID`, `EMAIL_DESC`, `SUBJECT`, `SUBJECT_PARAM`, `EMAIL_BODY`,
    `EMAIL_BODY_PARAM`, `CC_TO`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`,
    `LAST_MODIFIED_DATE`, `ACTIVE`, `TO_SEND`, `BCC`
)
SELECT 
    9,
    'Inactivity Reminder',
    'Action Required: Keep your QAT account active',
    '',
    '<!doctype html>
<html>
  <head>
    <meta name="viewport" content="width=device-width">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Account Inactivity Notice</title>
    <style>
      body { background-color: #f6f6f6; font-family: \'Roboto Slab\', \'Roboto\', \'Arial\', sans-serif; font-size: 14px; line-height: 1.4; color: #222; }
      .container { max-width: 580px; margin: 0 auto; padding: 20px; }
      .header { background-color: #bb3430; padding: 25px 20px; text-align: center; }
      .header h2 { color: #ffffff; margin: 0; font-size: 20px; font-weight: normal; }
      .content { background: #ffffff; padding: 30px 25px; border-radius: 4px; }
      .btn { display: inline-block; background-color: #007bff; color: #ffffff !important; padding: 10px 24px; text-decoration: none; border-radius: 4px; font-weight: bold; margin: 15px 0; }
    </style>
  </head>
  <body>
    <div class="container">
      <div class="header">
        <h2>Quantification Analytics Tool</h2>
      </div>
      <div class="content">
        <p>Dear <b><%USERNAME%></b>,</p>
        <p>We noticed that you have not logged in to your QAT account recently.</p>
        <p>To ensure account security and keep user lists up-to-date, your account is scheduled to be deactivated in <b><%DAYS%> days</b> unless you sign in.</p>
        <p>If you wish to keep your account active, simply log in using the link below:</p>
        <p style="text-align: center;">
          <a class="btn" href="<%LOGIN_URL%>">Log In to QAT</a>
        </p>
        <p>If you no longer need access, no action is required and your account will automatically be deactivated.</p>
        <br>
        <p>Thanks,<br>Team QAT<br><i>(This is an automated notification. Please do not reply directly to this email.)</i></p>
      </div>
    </div>
  </body>
</html>',
    'USERNAME,DAYS,LOGIN_URL',
    '',
    1,
    NOW(),
    1,
    NOW(),
    1,
    '',
    ''
WHERE NOT EXISTS (
    SELECT 1 FROM `em_email_template` WHERE `EMAIL_TEMPLATE_ID` = 9
);

-- ------------------------------------------------------------------------------
-- 6. i18n Static Labels (ap_static_label & ap_static_label_languages)
-- ------------------------------------------------------------------------------

-- static.realm.noOfDaysForInactivityReminder
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES (NULL, 'static.realm.noOfDaysForInactivityReminder', '1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l;
INSERT INTO ap_static_label_languages VALUES(NULL, @MAX, 1, 'Days of inactivity before reminder email'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL, @MAX, 2, 'Jours d''inactivité avant l''envoi de l''e-mail de rappel'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL, @MAX, 3, 'Días de inactividad antes del correo electrónico de recordatorio'); -- sp
INSERT INTO ap_static_label_languages VALUES(NULL, @MAX, 4, 'Dias de inatividade antes do e-mail de lembrete'); -- pr

-- static.realm.noOfDaysForInactivityReminderText
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES (NULL, 'static.realm.noOfDaysForInactivityReminderText', '1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l;
INSERT INTO ap_static_label_languages VALUES(NULL, @MAX, 1, 'Enter number of days of inactivity before sending reminder email'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL, @MAX, 2, 'Entrez le nombre de jours d''inactivité avant d''envoyer l''e-mail de rappel'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL, @MAX, 3, 'Ingrese el número de días de inactividad antes de enviar el correo electrónico de recordatorio'); -- sp
INSERT INTO ap_static_label_languages VALUES(NULL, @MAX, 4, 'Insira o número de dias de inatividade antes de enviar o e-mail de lembrete'); -- pr

-- static.realm.noOfDaysToLockUser
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES (NULL, 'static.realm.noOfDaysToLockUser', '1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l;
INSERT INTO ap_static_label_languages VALUES(NULL, @MAX, 1, 'Days of inactivity to lock user out'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL, @MAX, 2, 'Jours d''inactivité pour verrouiller le compte de l''utilisateur'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL, @MAX, 3, 'Días de inactividad para bloquear al usuario'); -- sp
INSERT INTO ap_static_label_languages VALUES(NULL, @MAX, 4, 'Dias de inatividade para bloquear o usuário'); -- pr

-- static.realm.noOfDaysToLockUserText
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES (NULL, 'static.realm.noOfDaysToLockUserText', '1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l;
INSERT INTO ap_static_label_languages VALUES(NULL, @MAX, 1, 'Enter number of days of inactivity before locking user out'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL, @MAX, 2, 'Entrez le nombre de jours d''inactivité avant de verrouiller le compte de l''utilisateur'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL, @MAX, 3, 'Ingrese el número de días de inactividad antes de bloquear al usuario'); -- sp
INSERT INTO ap_static_label_languages VALUES(NULL, @MAX, 4, 'Insira o número de dias de inatividade antes de bloquear o usuário'); -- pr

-- static.realm.lockDaysMustBeGreaterThanReminder
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES (NULL, 'static.realm.lockDaysMustBeGreaterThanReminder', '1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l;
INSERT INTO ap_static_label_languages VALUES(NULL, @MAX, 1, 'Lock-out days must be greater than reminder days'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL, @MAX, 2, 'Le nombre de jours de verrouillage doit être supérieur au nombre de jours de rappel'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL, @MAX, 3, 'Los días de bloqueo deben ser mayores que los días de recordatorio'); -- sp
INSERT INTO ap_static_label_languages VALUES(NULL, @MAX, 4, 'Os dias de bloqueio devem ser maiores que os dias de lembrete'); -- pr

-- static.user.notes
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES (NULL, 'static.user.notes', '1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l;
INSERT INTO ap_static_label_languages VALUES(NULL, @MAX, 1, 'Notes'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL, @MAX, 2, 'Remarques'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL, @MAX, 3, 'Notas'); -- sp
INSERT INTO ap_static_label_languages VALUES(NULL, @MAX, 4, 'Notas'); -- pr

-- static.user.notesPlaceholder
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES (NULL, 'static.user.notesPlaceholder', '1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l;
INSERT INTO ap_static_label_languages VALUES(NULL, @MAX, 1, 'Enter internal comments (e.g., who approved or requested this account)'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL, @MAX, 2, 'Entrez des commentaires internes (ex. qui a approuvé ou demandé ce compte)'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL, @MAX, 3, 'Ingrese comentarios internos (ej. quién aprobó o solicitó esta cuenta)'); -- sp
INSERT INTO ap_static_label_languages VALUES(NULL, @MAX, 4, 'Insira comentários internos (ex. quem aprovou ou solicitou esta conta)'); -- pr

-- static.user.activateAutoInactivatedUser
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES (NULL, 'static.user.activateAutoInactivatedUser', '1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l;
INSERT INTO ap_static_label_languages VALUES(NULL, @MAX, 1, 'Activate Account'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL, @MAX, 2, 'Activer le compte'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL, @MAX, 3, 'Activar cuenta'); -- sp
INSERT INTO ap_static_label_languages VALUES(NULL, @MAX, 4, 'Ativar conta'); -- pr

-- static.user.autoInactivatedStatus
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES (NULL, 'static.user.autoInactivatedStatus', '1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l;
INSERT INTO ap_static_label_languages VALUES(NULL, @MAX, 1, 'Auto-Inactivated'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL, @MAX, 2, 'Désactivé automatiquement'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL, @MAX, 3, 'Desactivado automáticamente'); -- sp
INSERT INTO ap_static_label_languages VALUES(NULL, @MAX, 4, 'Desativado automaticamente'); -- pr

-- static.message.login.autoInactivated
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES (NULL, 'static.message.login.autoInactivated', '1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l;
INSERT INTO ap_static_label_languages VALUES(NULL, @MAX, 1, 'Your account has been deactivated due to inactivity. Please contact your Realm Administrator or Application Administrator to reactivate your account.'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL, @MAX, 2, 'Votre compte a été désactivé en raison de son inactivité. Veuillez contacter l''administrateur de votre domaine ou l''administrateur de l''application pour réactiver votre compte.'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL, @MAX, 3, 'Su cuenta ha sido desactivada debido a inactividad. Póngase en contacto con su Administrador de Dominio o Administrador de Aplicación para reactivar su cuenta.'); -- sp
INSERT INTO ap_static_label_languages VALUES(NULL, @MAX, 4, 'Sua conta foi desativada devido à inatividade. Entre em contato com o Administrador do Domínio ou com o Administrador do Aplicativo para reativar sua conta.'); -- pr

-- static.user.confirmActivate
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES (NULL, 'static.user.confirmActivate', '1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l;
INSERT INTO ap_static_label_languages VALUES(NULL, @MAX, 1, 'Are you sure you want to activate this auto-inactivated account?'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL, @MAX, 2, 'Êtes-vous sûr de vouloir activer ce compte désactivé automatiquement ?'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL, @MAX, 3, '¿Está seguro de que desea activar esta cuenta desactivada automáticamente?'); -- sp
INSERT INTO ap_static_label_languages VALUES(NULL, @MAX, 4, 'Tem certeza de que deseja ativar esta conta desativada automaticamente?'); -- pr

-- static.user.activateAutoInactivatedUserSuccess
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES (NULL, 'static.user.activateAutoInactivatedUserSuccess', '1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l;
INSERT INTO ap_static_label_languages VALUES(NULL, @MAX, 1, 'User account has been activated successfully'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL, @MAX, 2, 'Le compte utilisateur a été activé avec succès'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL, @MAX, 3, 'La cuenta de usuario se ha activado correctamente'); -- sp
INSERT INTO ap_static_label_languages VALUES(NULL, @MAX, 4, 'A conta do usuário foi ativada com sucesso'); -- pr

-- static.validated.noOfDaysForInactivityReminder
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES (NULL, 'static.validated.noOfDaysForInactivityReminder', '1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l;
INSERT INTO ap_static_label_languages VALUES(NULL, @MAX, 1, 'Please enter days of inactivity before reminder email'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL, @MAX, 2, 'Veuillez entrer le nombre de jours d''inactivité avant l''e-mail de rappel'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL, @MAX, 3, 'Por favor ingrese los días de inactividad antes del correo electrónico de recordatorio'); -- sp
INSERT INTO ap_static_label_languages VALUES(NULL, @MAX, 4, 'Por favor, insira os dias de inatividade antes do e-mail de lembrete'); -- pr

-- static.validated.noOfDaysToLockUser
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES (NULL, 'static.validated.noOfDaysToLockUser', '1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l;
INSERT INTO ap_static_label_languages VALUES(NULL, @MAX, 1, 'Please enter days of inactivity before locking user out'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL, @MAX, 2, 'Veuillez entrer le nombre de jours d''inactivité avant de verrouiller le compte de l''utilisateur'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL, @MAX, 3, 'Por favor ingrese los días de inactividad antes de bloquear al usuario'); -- sp
INSERT INTO ap_static_label_languages VALUES(NULL, @MAX, 4, 'Por favor, insira os dias de inatividade antes de bloquear o usuário'); -- pr
