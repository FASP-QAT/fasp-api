/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  altius
 * Created: 05-Jul-2024
 */

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Forecast (Months)'
where l.LABEL_CODE='static.compareAndSelect.forecastErrorMonths' and ll.LANGUAGE_ID=1;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Previsão (meses)'
where l.LABEL_CODE='static.compareAndSelect.forecastErrorMonths' and ll.LANGUAGE_ID=4;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Prévisions (mois)'
where l.LABEL_CODE='static.compareAndSelect.forecastErrorMonths' and ll.LANGUAGE_ID=2;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Previsión (meses)'
where l.LABEL_CODE='static.compareAndSelect.forecastErrorMonths' and ll.LANGUAGE_ID=3;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='% below forecast.'
where l.LABEL_CODE='static.compareAndSelect.belowLowestConsumption' and ll.LANGUAGE_ID=1;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='% abaixo do previsto.'
where l.LABEL_CODE='static.compareAndSelect.belowLowestConsumption' and ll.LANGUAGE_ID=4;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='% inférieur aux prévisions.'
where l.LABEL_CODE='static.compareAndSelect.belowLowestConsumption' and ll.LANGUAGE_ID=2;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='% por debajo del pronóstico.'
where l.LABEL_CODE='static.compareAndSelect.belowLowestConsumption' and ll.LANGUAGE_ID=3;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Compare to Forecast'
where l.LABEL_CODE='static.compareAndSelect.compareToConsumptionForecast' and ll.LANGUAGE_ID=1;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Comparar com a previsão'
where l.LABEL_CODE='static.compareAndSelect.compareToConsumptionForecast' and ll.LANGUAGE_ID=4;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Comparer aux prévisions'
where l.LABEL_CODE='static.compareAndSelect.compareToConsumptionForecast' and ll.LANGUAGE_ID=2;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Comparar con el pronóstico'
where l.LABEL_CODE='static.compareAndSelect.compareToConsumptionForecast' and ll.LANGUAGE_ID=3;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='% above the highest forecast.'
where l.LABEL_CODE='static.compareAndSelect.aboveHighestConsumption' and ll.LANGUAGE_ID=1;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='% acima da previsão mais alta.'
where l.LABEL_CODE='static.compareAndSelect.aboveHighestConsumption' and ll.LANGUAGE_ID=4;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='% au-dessus de la prévision la plus élevée.'
where l.LABEL_CODE='static.compareAndSelect.aboveHighestConsumption' and ll.LANGUAGE_ID=2;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='% por encima de la previsión más alta.'
where l.LABEL_CODE='static.compareAndSelect.aboveHighestConsumption' and ll.LANGUAGE_ID=3;