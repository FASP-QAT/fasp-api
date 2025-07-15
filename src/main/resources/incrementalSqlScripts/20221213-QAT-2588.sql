/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  altius
 * Created: 06-Dec-2022
 */

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Closing Balance for 2nd month = 6,500'
where l.LABEL_CODE='static.supplyPlan.whenToSuggestQty5' and ll.LANGUAGE_ID=1;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Solde de clôture pour le 2ème mois = 6,500'
where l.LABEL_CODE='static.supplyPlan.whenToSuggestQty5' and ll.LANGUAGE_ID=2;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Saldo de cierre del segundo mes = 6,500'
where l.LABEL_CODE='static.supplyPlan.whenToSuggestQty5' and ll.LANGUAGE_ID=3;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Saldo final do 2º mês = 6,500'
where l.LABEL_CODE='static.supplyPlan.whenToSuggestQty5' and ll.LANGUAGE_ID=4;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Closing Balance for 1st month = 6,000'
where l.LABEL_CODE='static.supplyPlan.whenToSuggestQty4' and ll.LANGUAGE_ID=1;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Solde de clôture pour le 1er mois = 6,000'
where l.LABEL_CODE='static.supplyPlan.whenToSuggestQty4' and ll.LANGUAGE_ID=2;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Saldo de cierre del primer mes = 6,000'
where l.LABEL_CODE='static.supplyPlan.whenToSuggestQty4' and ll.LANGUAGE_ID=3;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Saldo final do 1º mês = 6,000'
where l.LABEL_CODE='static.supplyPlan.whenToSuggestQty4' and ll.LANGUAGE_ID=4;

