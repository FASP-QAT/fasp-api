/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  altius
 * Created: 06-Apr-2022
 */

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.modelingTable.note','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Note: If you make a change to the data in a pre-existing row, you will need to click `Update` before the change reflects in the `Calculated change for month` column and the `Total` value.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Remarque : Si vous apportez une modification aux données d`une ligne préexistante, vous devrez cliquer sur `Mettre à jour` avant que la modification ne soit reflétée dans la colonne `Modification calculée pour le mois` et la valeur `Total`.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Nota: Si realiza un cambio en los datos de una fila preexistente, deberá hacer clic en `Actualizar` antes de que el cambio se refleje en la columna `Cambio calculado para el mes` y el valor `Total`.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Observação: se você fizer uma alteração nos dados em uma linha pré-existente, será necessário clicar em `Atualizar` antes que a alteração seja refletida na coluna `Alteração calculada para o mês` e no valor `Total`.');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.common.dataEnteredIn','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Data entered in');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Données saisies dans');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Datos ingresados ​​en');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Dados inseridos em');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.common.otherUnitName','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Other Unit Name');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Autre nom d`unité');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Otro nombre de unidad');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Outro nome da unidade');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.importFromQATSupplyPlan.multiplierTo','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Multiplier to');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Multiplicateur à');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'multiplicador a');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Multiplicador para');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.common.otherUnit','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Other Unit');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Autre unité');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'otra unidad');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Outra unidade');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.dataentry.units','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Units');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Unités');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Unidades');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Unidades');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.dataentry.multiplierToFU','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'multiplier to FU');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'multiplicateur en FU');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'multiplicador a FU');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'multiplicador para FU');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.dataentry.change','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'change');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'changement');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'cambio');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'mudança');-- pr
