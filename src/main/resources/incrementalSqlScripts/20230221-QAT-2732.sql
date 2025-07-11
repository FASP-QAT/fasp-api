/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  altius
 * Created: 08-Feb-2023
 */

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='# of FU required for period per'
where l.LABEL_CODE='static.tree.#OfFURequiredForPeriodPerPatient' and ll.LANGUAGE_ID=1;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='# d`F requis pour la période par'
where l.LABEL_CODE='static.tree.#OfFURequiredForPeriodPerPatient' and ll.LANGUAGE_ID=2;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='# de FU requeridas por período por'
where l.LABEL_CODE='static.tree.#OfFURequiredForPeriodPerPatient' and ll.LANGUAGE_ID=3;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='# de FU necessário para o período por'
where l.LABEL_CODE='static.tree.#OfFURequiredForPeriodPerPatient' and ll.LANGUAGE_ID=4;