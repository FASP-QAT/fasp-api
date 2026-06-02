/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  altius
 * Created: 21-May-2026
 */

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Please enter a valid number having max 10 digits before decimal and max 4 digits after decimal.'
where l.LABEL_CODE='static.tree.decimalValidation10&2' and ll.LANGUAGE_ID=1;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Veuillez saisir un nombre valide comportant au maximum 10 chiffres entiers et 4 chiffres décimaux.'
where l.LABEL_CODE='static.tree.decimalValidation10&2' and ll.LANGUAGE_ID=2;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Ingrese un número válido que tenga un máximo de 10 dígitos antes del decimal y un máximo de 4 dígitos después del decimal.'
where l.LABEL_CODE='static.tree.decimalValidation10&2' and ll.LANGUAGE_ID=3;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Insira um número válido com no máximo 10 dígitos antes do decimal e no máximo 4 dígitos após o decimal.'
where l.LABEL_CODE='static.tree.decimalValidation10&2' and ll.LANGUAGE_ID=4;
