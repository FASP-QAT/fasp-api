/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  altius
 * Created: 28-Mar-2022
 */

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.placeholder.placeholder','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'[Placeholder]');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'[Espace réservé]');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'[Marcador de posición]');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'[Espaço reservado]');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.placeholder.monthlyForecastReport','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'This report aggregates regional forecasts. For disaggregated regional forecasts, export CSV.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Ce rapport regroupe les prévisions régionales. Pour les prévisions régionales désagrégées, exportez CSV.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Este informe agrega pronósticos regionales. Para pronósticos regionales desagregados, exporte CSV.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Este relatório agrega previsões regionais. Para previsões regionais desagregadas, exporte CSV.');-- pr

