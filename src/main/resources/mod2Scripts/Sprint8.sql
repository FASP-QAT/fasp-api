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