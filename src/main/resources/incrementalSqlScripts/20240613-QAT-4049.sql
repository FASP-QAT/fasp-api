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