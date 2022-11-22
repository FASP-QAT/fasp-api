/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  altius
 * Created: 11-Nov-2022
 */

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Master data sync failed. Please ensure your computer has access to internet and try again.'
where l.LABEL_CODE='static.masterDataSync.masterDataSyncFailed' and ll.LANGUAGE_ID=1;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='La synchronisation des données de base a échoué. Assurez-vous que votre ordinateur a accès à Internet et réessayez.'
where l.LABEL_CODE='static.masterDataSync.masterDataSyncFailed' and ll.LANGUAGE_ID=2;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='La sincronización de datos maestros falló. Asegúrese de que su computadora tenga acceso a Internet y vuelva a intentarlo.'
where l.LABEL_CODE='static.masterDataSync.masterDataSyncFailed' and ll.LANGUAGE_ID=3;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Falha na sincronização de dados mestre. Certifique-se de que seu computador tenha acesso à Internet e tente novamente.'
where l.LABEL_CODE='static.masterDataSync.masterDataSyncFailed' and ll.LANGUAGE_ID=4;