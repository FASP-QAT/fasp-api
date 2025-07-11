/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  altius
 * Created: 28-Nov-2022
 */

UPDATE ap_static_label_languages
LEFT JOIN ap_static_label ON ap_static_label.STATIC_LABEL_ID=ap_static_label_languages.STATIC_LABEL_ID
SET ap_static_label_languages.LABEL_TEXT='Note that even with the minimum 13 months of data, ARIMA may not always be able to calculate. Try add more months of data, or use another extrapolation method.' where ap_static_label.LABEL_CODE='static.extrapolation.error' 
and ap_static_label_languages.LANGUAGE_ID=1;

UPDATE ap_static_label_languages
LEFT JOIN ap_static_label ON ap_static_label.STATIC_LABEL_ID=ap_static_label_languages.STATIC_LABEL_ID
SET ap_static_label_languages.LABEL_TEXT='Notez que même avec le minimum de 13 mois de données, ARIMA peut ne pas toujours être en mesure de calculer. Essayez d`ajouter plus de mois de données ou utilisez une autre méthode d`extrapolation.' where ap_static_label.LABEL_CODE='static.extrapolation.error' 
and ap_static_label_languages.LANGUAGE_ID=2;

UPDATE ap_static_label_languages
LEFT JOIN ap_static_label ON ap_static_label.STATIC_LABEL_ID=ap_static_label_languages.STATIC_LABEL_ID
SET ap_static_label_languages.LABEL_TEXT='Tenga en cuenta que incluso con el mínimo de 13 meses de datos, es posible que ARIMA no siempre pueda calcular. Intente agregar más meses de datos o use otro método de extrapolación.' where ap_static_label.LABEL_CODE='static.extrapolation.error' 
and ap_static_label_languages.LANGUAGE_ID=3;

UPDATE ap_static_label_languages
LEFT JOIN ap_static_label ON ap_static_label.STATIC_LABEL_ID=ap_static_label_languages.STATIC_LABEL_ID
SET ap_static_label_languages.LABEL_TEXT='Observe que, mesmo com o mínimo de 13 meses de dados, o ARIMA nem sempre consegue calcular. Tente adicionar mais meses de dados ou use outro método de extrapolação.' where ap_static_label.LABEL_CODE='static.extrapolation.error' 
and ap_static_label_languages.LANGUAGE_ID=4;