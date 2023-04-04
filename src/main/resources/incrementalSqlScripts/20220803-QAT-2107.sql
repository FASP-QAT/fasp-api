update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='# of frequency'
where l.LABEL_CODE='static.usageTemplate.timesPerFrequency' and ll.LANGUAGE_ID=1;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='# de fréquence'
where l.LABEL_CODE='static.usageTemplate.timesPerFrequency' and ll.LANGUAGE_ID=2;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='# de la frecuencia'
where l.LABEL_CODE='static.usageTemplate.timesPerFrequency' and ll.LANGUAGE_ID=3;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='# de frequência'
where l.LABEL_CODE='static.usageTemplate.timesPerFrequency' and ll.LANGUAGE_ID=4;



update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='How often the forecasting unit is used based on the time period in Frequency Unit. For example, if the product is used every 3 months, enter 3 in this column and month in the next column.'
where l.LABEL_CODE='static.tooltip.OfTimeFreqwency' and ll.LANGUAGE_ID=1;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Fréquence dutilisation de lunité de prévision en fonction de la période dans lunité de fréquence. Par exemple, si le produit est utilisé tous les 3 mois, entrez 3 dans cette colonne et mois dans la colonne suivante.'
where l.LABEL_CODE='static.tooltip.OfTimeFreqwency' and ll.LANGUAGE_ID=2;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Con qué frecuencia se usa la unidad de pronóstico según el período de tiempo en Unidad de frecuencia. Por ejemplo, si el producto se usa cada 3 meses, ingrese 3 en esta columna y el mes en la columna siguiente.'
where l.LABEL_CODE='static.tooltip.OfTimeFreqwency' and ll.LANGUAGE_ID=3;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Com que frequência a unidade de previsão é usada com base no período de tempo em Unidade de frequência. Por exemplo, se o produto for usado a cada 3 meses, insira 3 nesta coluna e mês na próxima coluna.'
where l.LABEL_CODE='static.tooltip.OfTimeFreqwency' and ll.LANGUAGE_ID=4;