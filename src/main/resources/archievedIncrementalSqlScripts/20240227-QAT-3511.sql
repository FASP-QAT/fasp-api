/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  altius
 * Created: 27-Feb-2024
 */

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='If the MIN is 3 and reorder interval is 9 and the first month is a re-order month then:'
where l.LABEL_CODE='static.supplyPlanFormula.inventoryTurns1L7' and ll.LANGUAGE_ID=1;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Si le MIN est de 3 et l`intervalle de réapprovisionnement est de 9 et que le premier mois est un mois de réapprovisionnement alors :'
where l.LABEL_CODE='static.supplyPlanFormula.inventoryTurns1L7' and ll.LANGUAGE_ID=2;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Si el MIN es 3 y el intervalo de reorden es 9 y el primer mes es un mes de reorden, entonces:'
where l.LABEL_CODE='static.supplyPlanFormula.inventoryTurns1L7' and ll.LANGUAGE_ID=3;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Se o MIN for 3 e o intervalo de novo pedido for 9 e o primeiro mês for um mês de novo pedido, então:'
where l.LABEL_CODE='static.supplyPlanFormula.inventoryTurns1L7' and ll.LANGUAGE_ID=4;


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlanFormula.inventoryTurns1L24','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Avg. MOS');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Moy. MOS');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Promedio MOS');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Média MOS');-- pr