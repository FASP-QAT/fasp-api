update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='How many months should be used for calculating the average monthly consumption (AMC)? Include more months in the past if the forecast data is better basis for stock calculations, such as in the case of seasonal products.'
where l.LABEL_CODE='static.programPU.monthsInPastTooltip' and ll.LANGUAGE_ID=1;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Combien de mois faut-il prendre en compte pour calculer la consommation mensuelle moyenne (CMA) ? Incluez davantage de mois dans le passé si les données prévisionnelles constituent une meilleure base pour les calculs de stocks, comme dans le cas de produits saisonniers.'
where l.LABEL_CODE='static.programPU.monthsInPastTooltip' and ll.LANGUAGE_ID=2;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='¿Cuántos meses se deben utilizar para calcular el consumo medio mensual (AMC)? Incluya más meses en el pasado si los datos del pronóstico son una mejor base para los cálculos de existencias, como en el caso de productos de temporada.'
where l.LABEL_CODE='static.programPU.monthsInPastTooltip' and ll.LANGUAGE_ID=3;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Quantos meses devem ser utilizados para o cálculo do consumo médio mensal (AMC)? Inclua mais meses no passado se os dados de previsão servirem de base melhor para cálculos de estoque, como no caso de produtos sazonais.'
where l.LABEL_CODE='static.programPU.monthsInPastTooltip' and ll.LANGUAGE_ID=4;


update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='How many months should be used for calculating the average monthly consumption (AMC)? Include more months in the future if the future data is more reliable and predicts the future.'
where l.LABEL_CODE='static.programPU.monthsInFutureTooltip' and ll.LANGUAGE_ID=1;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Combien de mois faut-il prendre en compte pour calculer la consommation mensuelle moyenne (CMA) ? Incluez plus de mois dans le futur si les données futures sont plus fiables et prédisent l’avenir.'
where l.LABEL_CODE='static.programPU.monthsInFutureTooltip' and ll.LANGUAGE_ID=2;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='¿Cuántos meses se deben utilizar para calcular el consumo medio mensual (AMC)? Incluya más meses en el futuro si los datos futuros son más confiables y predicen el futuro.'
where l.LABEL_CODE='static.programPU.monthsInFutureTooltip' and ll.LANGUAGE_ID=3;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Quantos meses devem ser utilizados para o cálculo do consumo médio mensal (AMC)? Inclua mais meses no futuro se os dados futuros forem mais confiáveis ​​e preverem o futuro.'
where l.LABEL_CODE='static.programPU.monthsInFutureTooltip' and ll.LANGUAGE_ID=4;