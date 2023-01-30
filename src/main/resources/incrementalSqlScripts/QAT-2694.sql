/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  altius
 * Created: 30-Jan-2023
 */

UPDATE ap_static_label_languages
LEFT JOIN ap_static_label ON ap_static_label.STATIC_LABEL_ID=ap_static_label_languages.STATIC_LABEL_ID
SET ap_static_label_languages.LABEL_TEXT='To switch scenarios, please close this window and use the Scenario dropdown on the main screen' where ap_static_label.LABEL_CODE='static.tooltip.scenario' 
and ap_static_label_languages.LANGUAGE_ID=1;

UPDATE ap_static_label_languages
LEFT JOIN ap_static_label ON ap_static_label.STATIC_LABEL_ID=ap_static_label_languages.STATIC_LABEL_ID
SET ap_static_label_languages.LABEL_TEXT='Pour changer de scénario, veuillez fermer cette fenêtre et utiliser le menu déroulant Scénario sur l`écran principal' where ap_static_label.LABEL_CODE='static.tooltip.scenario' 
and ap_static_label_languages.LANGUAGE_ID=2;

UPDATE ap_static_label_languages
LEFT JOIN ap_static_label ON ap_static_label.STATIC_LABEL_ID=ap_static_label_languages.STATIC_LABEL_ID
SET ap_static_label_languages.LABEL_TEXT='Para cambiar de escenario, cierre esta ventana y use el menú desplegable Escenario en la pantalla principal' where ap_static_label.LABEL_CODE='static.tooltip.scenario' 
and ap_static_label_languages.LANGUAGE_ID=3;

UPDATE ap_static_label_languages
LEFT JOIN ap_static_label ON ap_static_label.STATIC_LABEL_ID=ap_static_label_languages.STATIC_LABEL_ID
SET ap_static_label_languages.LABEL_TEXT='Para alternar os cenários, feche esta janela e use o menu suspenso Cenário na tela principal' where ap_static_label.LABEL_CODE='static.tooltip.scenario' 
and ap_static_label_languages.LANGUAGE_ID=4;