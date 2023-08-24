INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.tooltip.FirstMonthOfTarget','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'By default, targets begin on the start month of your forecast period, but can be changed if desired. Targets must be in 12-month periods but do not have to start in January.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Par défaut, les objectifs commencent le mois de début de votre période de prévision, mais peuvent être modifiés si vous le souhaitez. Les objectifs doivent être répartis sur des périodes de 12 mois, mais ne doivent pas nécessairement commencer en janvier.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Por padrão, as metas começam no mês inicial do período de previsão, mas podem ser alteradas, se desejado. As metas devem ser em períodos de 12 meses, mas não precisam começar em janeiro.');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'De forma predeterminada, los objetivos comienzan en el mes de inicio de su período de pronóstico, pero se pueden cambiar si lo desea. Los objetivos deben ser en períodos de 12 meses, pero no es necesario que comiencen en enero.');-- sp

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.tooltip.yearsOfTarget','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'QAT recommends providing targets for the forecast period and one year beyond the forecast period. If you provide less, the end of your forecast will have no change over time.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'QAT recommande de fournir des objectifs pour la période de prévision et un an au-delà de la période de prévision. Si vous fournissez moins, la fin de votre prévision n`aura aucun changement dans le temps.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'A QAT recomenda fornecer metas para o período de previsão e um ano além do período de previsão. Se você fornecer menos, o final da sua previsão não sofrerá alterações ao longo do tempo.');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'QAT recomienda proporcionar objetivos para el período de pronóstico y un año más allá del período de pronóstico. Si proporciona menos, el final de su pronóstico no tendrá cambios en el tiempo.');-- sp

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='QAT supone que la meta anual/12 se alcanzará en el punto medio del año objetivo y luego interpola entre esos puntos medios suponiendo una tasa de cambio lineal o exponencial.'
where l.LABEL_CODE='static.tooltip.calculatedTotal' and ll.LANGUAGE_ID=3;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='QAT assumes that the annual target/12 will be reached by the midpoint of the target year and then interpolates between those midpoints assuming either a linear or exponential rate of change.'
where l.LABEL_CODE='static.tooltip.calculatedTotal' and ll.LANGUAGE_ID=1;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='QAT suppose que l`objectif annuel/12 sera atteint au milieu de l`année cible, puis interpole entre ces points médians en supposant un taux de changement linéaire ou exponentiel.'
where l.LABEL_CODE='static.tooltip.calculatedTotal' and ll.LANGUAGE_ID=2;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='O QAT assume que a meta anual/12 será alcançada no ponto médio do ano-alvo e depois interpola entre esses pontos médios assumindo uma taxa de mudança linear ou exponencial.'
where l.LABEL_CODE='static.tooltip.calculatedTotal' and ll.LANGUAGE_ID=4;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='This calculator allows users to convert their annual targets into monthly rates directly in QAT for purposes of monthly tree forecasting. This feature is generally appropriate for products that are discrete, rather than continuous. The calculator requires actual data for the year before the forecast, the annual targets during the forecast period, and the target for the year after the forecast. QAT then calculates the change between these annual figures to generate a monthly change. The calculator will then populate the start month and value in the `Node Tab` and rows in the `Modeling & Transfer` tab.\nNote that there will always be minor differences in the final annual total forecasted and your target. However, the difference will be small if your targets change at a similar rate from year to year. The benefit of using the calculator is that it provides a dynamic rather than stairstep forecast.'
where l.LABEL_CODE='static.tree.annualTargetLabel' and ll.LANGUAGE_ID=1;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Ce calculateur permet aux utilisateurs de convertir leurs objectifs annuels en tarifs mensuels directement dans QAT à des fins de prévision mensuelle des arbres. Cette fonctionnalité est généralement appropriée pour les produits discrets plutôt que continus. Le calculateur nécessite des données réelles pour l`année précédant la prévision, les objectifs annuels pendant la période de prévision et l`objectif pour l`année suivant la prévision. QAT calcule ensuite la variation entre ces chiffres annuels pour générer une variation mensuelle. La calculatrice renseignera ensuite le mois de début et la valeur dans l`onglet "Nœud" et les lignes de l`onglet "Modélisation et transfert".\nNotez qu`il y aura toujours des différences mineures entre le total annuel final prévu et votre objectif. Toutefois, la différence sera minime si vos objectifs évoluent au même rythme d’une année à l’autre. L’avantage de l’utilisation de la calculatrice est qu’elle fournit une prévision dynamique plutôt que par escalier.'
where l.LABEL_CODE='static.tree.annualTargetLabel' and ll.LANGUAGE_ID=2;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Esta calculadora permite que os usuários convertam suas metas anuais em taxas mensais diretamente no QAT para fins de previsão mensal da árvore. Esse recurso geralmente é apropriado para produtos discretos, em vez de contínuos. A calculadora requer dados reais para o ano anterior à previsão, as metas anuais durante o período de previsão e a meta para o ano seguinte à previsão. O QAT então calcula a variação entre esses números anuais para gerar uma alteração mensal. A calculadora preencherá o mês de início e o valor na `Guia Nó` e as linhas na guia `Modelagem e Transferência`.\nObserve que sempre haverá pequenas diferenças no total anual final previsto e na sua meta. No entanto, a diferença será pequena se os seus objectivos mudarem a um ritmo semelhante de ano para ano. A vantagem de usar a calculadora é que ela fornece uma previsão dinâmica, em vez de escalonada.'
where l.LABEL_CODE='static.tree.annualTargetLabel' and ll.LANGUAGE_ID=4;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Esta calculadora permite a los usuarios convertir sus objetivos anuales en tasas mensuales directamente en QAT para realizar pronósticos mensuales de árboles. Esta característica generalmente es apropiada para productos discretos, en lugar de continuos. La calculadora requiere datos reales para el año anterior al pronóstico, los objetivos anuales durante el período del pronóstico y el objetivo para el año posterior al pronóstico. Luego, QAT calcula el cambio entre estas cifras anuales para generar un cambio mensual. Luego, la calculadora completará el mes de inicio y el valor en la "pestaña Nodo" y las filas en la pestaña "Modelado y transferencia".\nTenga en cuenta que siempre habrá pequeñas diferencias entre el total anual final pronosticado y su objetivo. Sin embargo, la diferencia será pequeña si sus objetivos cambian a un ritmo similar de un año a otro. La ventaja de utilizar la calculadora es que proporciona un pronóstico dinámico en lugar de escalonado.'
where l.LABEL_CODE='static.tree.annualTargetLabel' and ll.LANGUAGE_ID=3;
