/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  altius
 * Created: 16-Aug-2022
 */

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.login.loginOnline','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Online');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'En ligne');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'En línea');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Conectadas');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.login.confirmSessionChange','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'In order to switch to online mode, you need to log in again.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Pour passer en mode en ligne, vous devez vous reconnecter.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Para cambiar al modo en línea, debe iniciar sesión nuevamente.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Para mudar para o modo online, você precisa fazer login novamente.');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.login.goToLogin','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Go to Login');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Aller à Connexion');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ir a Iniciar sesión');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Ir para Entrar');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.login.goOffline','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Go Offline');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Se déconnecter');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Salir de línea');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Desconecte');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.login.goOnline','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Go Online');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Aller en ligne');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ir en línea');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Fique online');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.login.loginAgain','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Please login again to access QAT in online mode.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Veuillez vous reconnecter pour accéder au QAT en mode en ligne.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Vuelva a iniciar sesión para acceder a QAT en modo en línea.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Faça login novamente para acessar o QAT no modo online.');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.login.successOffline','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'You have successfully switched to offline mode.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Vous avez réussi à passer en mode hors ligne.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ha cambiado con éxito al modo fuera de línea.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Você alternou com sucesso para o modo offline.');-- pr