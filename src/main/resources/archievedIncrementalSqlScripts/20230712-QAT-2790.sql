/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  altius
 * Created: 12-Jul-2023
 */

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.extrapolation.confirmmsg','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Click “Extrapolate” first or you will lose your updated parameters. Any changes to your notes will be saved'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Cliquez d`abord sur "Extrapoler" ou vous perdrez vos paramètres mis à jour. Toute modification de vos notes sera enregistrée'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Haga clic en "Extrapolar" primero o perderá sus parámetros actualizados. Cualquier cambio en tus notas se guardará.'); -- sp 
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Clique em “Extrapolar” primeiro ou você perderá seus parâmetros atualizados. Quaisquer alterações nas suas notas serão salvas'); -- pr
