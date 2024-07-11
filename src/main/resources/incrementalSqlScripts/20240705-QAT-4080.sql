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
set ll.LABEL_TEXT='Compare to Consumption Forecast(s)'
where l.LABEL_CODE='static.compareAndSelect.compareToConsumptionForecast' and ll.LANGUAGE_ID=1;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Compare com as previsões de consumo'
where l.LABEL_CODE='static.compareAndSelect.compareToConsumptionForecast' and ll.LANGUAGE_ID=4;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Comparer aux prévisions de consommation'
where l.LABEL_CODE='static.compareAndSelect.compareToConsumptionForecast' and ll.LANGUAGE_ID=2;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Comparar con las previsiones de consumo'
where l.LABEL_CODE='static.compareAndSelect.compareToConsumptionForecast' and ll.LANGUAGE_ID=3;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='% above highest'
where l.LABEL_CODE='static.compareAndSelect.aboveHighestConsumption' and ll.LANGUAGE_ID=1;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='% acima do mais alto'
where l.LABEL_CODE='static.compareAndSelect.aboveHighestConsumption' and ll.LANGUAGE_ID=4;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='% au-dessus du plus haut'
where l.LABEL_CODE='static.compareAndSelect.aboveHighestConsumption' and ll.LANGUAGE_ID=2;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='% por encima del más alto'
where l.LABEL_CODE='static.compareAndSelect.aboveHighestConsumption' and ll.LANGUAGE_ID=3;

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.compareAndSelect.selectForecast','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Click to select forecast.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Cliquez pour sélectionner les prévisions.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Clique para selecionar a previsão.');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Haga clic para seleccionar el pronóstico.');-- sp

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.compareAndSelect.forecastSelected','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Forecast selected.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Prévision sélectionnée.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Previsão selecionada.');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Pronóstico seleccionado.');-- sp

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.compareAndSelect.collapsePUPanel','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Click “-” to hide PU panel');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Cliquez sur « - » pour masquer le panneau PU');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Clique em “-” para ocultar o painel PU');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Haga clic en “-” para ocultar el panel PU');-- sp

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.compareAndSelect.expandPUPanel','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Expand PU panel');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Développer le panneau PU');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Expandir painel PU');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ampliar panel de PU');-- sp
