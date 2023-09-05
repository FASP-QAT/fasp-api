/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  altius
 * Created: 25-Aug-2023
 */

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Cannot deactivate ARU with conversion factor = 1, please reactivate to continue'
where l.LABEL_CODE='static.realmCountryPlanningUnit.failedToUpdate' and ll.LANGUAGE_ID=1;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Impossible de désactiver ARU avec un facteur de conversion = 1, veuillez le réactiver pour continuer'
where l.LABEL_CODE='static.realmCountryPlanningUnit.failedToUpdate' and ll.LANGUAGE_ID=2;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='No se puede desactivar ARU con factor de conversión = 1, vuelva a activarlo para continuar'
where l.LABEL_CODE='static.realmCountryPlanningUnit.failedToUpdate' and ll.LANGUAGE_ID=3;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Não é possível desativar ARU com fator de conversão = 1. Reative para continuar'
where l.LABEL_CODE='static.realmCountryPlanningUnit.failedToUpdate' and ll.LANGUAGE_ID=4;