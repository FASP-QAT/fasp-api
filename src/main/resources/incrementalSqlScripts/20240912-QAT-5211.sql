/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  altius
 * Created: 12-Sep-2024
 */

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.compareAndSelect.forecastErrorMonths','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Forecast Error (# Months used)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Erreur de prévision (nombre de mois utilisés)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Erro de previsão (# meses usados)');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Error de pronóstico (# meses utilizados)');-- sp