INSERT INTO ap_static_label VALUES (null, 'static.login.databaseBackupRunning', 1);
SELECT @label_id := MAX(STATIC_LABEL_ID) FROM ap_static_label;
INSERT INTO ap_static_label_languages VALUES (null, @label_id, 1, 'A database backup is currently in progress. The system will only run in offline mode.');
INSERT INTO ap_static_label_languages VALUES (null, @label_id, 2, 'Une sauvegarde de la base de données est en cours. Le système ne fonctionnera qu''en mode hors ligne.');
INSERT INTO ap_static_label_languages VALUES (null, @label_id, 3, 'Se está realizando una copia de seguridad de la base de datos. El sistema solo se ejecutará en modo sin conexión.');
INSERT INTO ap_static_label_languages VALUES (null, @label_id, 4, 'Um backup do banco de dados está em andamento. O sistema funcionará apenas no modo offline.');
