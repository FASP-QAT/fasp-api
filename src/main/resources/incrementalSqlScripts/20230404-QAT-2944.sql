/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  altius
 * Created: 22-Mar-2023
 */

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.errorPage.error','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Error');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Erreur');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Error');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Erro');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.errorPage.errorMessage','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'We seem to have encountered an unexpected error. Please show this to one of our engineers so we can get someone working on this right away.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Nous semblons avoir rencontré une erreur inattendue. Veuillez le montrer à l`un de nos ingénieurs afin que nous puissions immédiatement envoyer quelqu`un travailler dessus.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Parece que hemos encontrado un error inesperado. Muéstrele esto a uno de nuestros ingenieros para que podamos hacer que alguien trabaje en esto de inmediato.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Parece que encontramos um erro inesperado. Mostre isso a um de nossos engenheiros para que possamos contratar alguém para trabalhar nisso imediatamente.');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.errorPage.errorReason','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Error reason -');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Raison de l`erreur -');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Motivo del error -');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Motivo do erro -');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.errorPage.returnToDashboard','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Return to Dashboard');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Retour au tableau de bord');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Volver al panel');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Voltar ao Painel');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.errorPage.userCommentPlaceholder','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Please describe what you were doing when this error occurred - the more details the better!');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Veuillez décrire ce que vous faisiez lorsque cette erreur s`est produite ; plus vous avez de détails, mieux c`est !');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Describa lo que estaba haciendo cuando se produjo este error. ¡Cuantos más detalles, mejor!');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Por favor, descreva o que você estava fazendo quando este erro ocorreu - quanto mais detalhes, melhor!');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.errorPage.raiseATicket','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Raise a Ticket');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Levez un ticket');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Levantar un boleto');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Levantar um Ticket');-- pr