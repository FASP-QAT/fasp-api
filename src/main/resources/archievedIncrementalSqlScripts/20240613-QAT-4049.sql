/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  altius
 * Created: 13-Jun-2024
 */

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Display only Forecast Period'
where l.LABEL_CODE='static.compareAndSelect.showOnlyForecastPeriod' and ll.LANGUAGE_ID=1;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Mostrar sólo Período de pronóstico'
where l.LABEL_CODE='static.compareAndSelect.showOnlyForecastPeriod' and ll.LANGUAGE_ID=3;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Exibir apenas o período de previsão'
where l.LABEL_CODE='static.compareAndSelect.showOnlyForecastPeriod' and ll.LANGUAGE_ID=4;

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.extrapolations.showFits','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Display forecasts in months with actual consumption');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Afficher mensuellement les prévisions ainsi que les consommations réelles');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Exiba previsões em meses com consumo real');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Mostrar estimaciones en meses con consumo real');-- sp
