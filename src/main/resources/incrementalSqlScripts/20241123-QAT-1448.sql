INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.importIntoSP','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Import Into Supply Plan'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Importer dans le plan d`approvisionnement'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Importar al plan de suministro'); -- sp 
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Importar para o plano de fornecimento'); -- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.realm.noOfMonthsInFutureForTopDashboard','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'No of months in future for top dashboard');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Nombre de mois dans le futur pour le tableau de bord supérieur');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Número de meses en el futuro para el panel superior');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Número de meses no futuro para o painel superior');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.realm.noOfMonthsInPastForBottomDashboard','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'No of months in past for bottom dashboard');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Nombre de mois dans le passé pour le tableau de bord inférieur');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Número de meses anteriores para el panel inferior');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Número de meses anteriores para o painel inferior');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.validated.restrictionNoOfMonthsInFutureForTopDashboard','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Please enter No of months in future for top dashboard');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Veuillez saisir le nombre de mois à venir pour le tableau de bord supérieur');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Por favor, introduzca el número de meses en el futuro para el panel superior');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Por favor, insira o número de meses no futuro para o painel superior');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.validated.restrictionNoOfMonthsInPastForBottomDashboard','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Please enter No of months in past for bottom dashboard');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Veuillez saisir le nombre de mois passés pour le tableau de bord inférieur');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Por favor, introduzca el número de meses anteriores para el panel inferior');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Por favor, insira o número de meses anteriores para o painel inferior');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.ticket.manageTickets','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Manage my tickets');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Gérer mes billets');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Gestionar mis tickets');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Gerenciar meus tickets');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dashboard.localTooltip','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'If checked only downloaded versions will appear in the dropdown. If unchecked, only server versions (latest final or draft) will appear in the dropdown');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Si cette case est cochée, seules les versions téléchargées apparaîtront dans la liste déroulante. Si elle n`est pas cochée, seules les versions du serveur (dernière version finale ou brouillon) apparaîtront dans la liste déroulante.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Si está marcada, solo las versiones descargadas aparecerán en el menú desplegable. Si no está marcada, solo las versiones del servidor (última versión final o borrador) aparecerán en el menú desplegable.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Se marcado, apenas as versões baixadas aparecerão no menu suspenso. Se desmarcado, apenas as versões do servidor (última final ou rascunho) aparecerão no menu suspenso.');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dashboard.actionTooltip','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Click on trashcan icon to delete local program from QAT. Down cloud icon in red indicates there is a more updated version on the server. Up cloud icon in red indicates the downloaded program has unsaved changes to the server.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Cliquez sur l`icône de la corbeille pour supprimer le programme local de QAT. L`icône de nuage vers le bas en rouge indique qu`il existe une version plus récente sur le serveur. L`icône de nuage vers le haut en rouge indique que le programme téléchargé comporte des modifications non enregistrées sur le serveur.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Haga clic en el icono de la papelera para eliminar el programa local de QAT. El icono de la nube hacia abajo en rojo indica que hay una versión más actualizada en el servidor. El icono de la nube hacia arriba en rojo indica que el programa descargado tiene cambios sin guardar en el servidor.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Clique no ícone da lixeira para excluir o programa local do QAT. O ícone da nuvem para baixo em vermelho indica que há uma versão mais atualizada no servidor. O ícone da nuvem para cima em vermelho indica que o programa baixado tem alterações não salvas no servidor.');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dashboard.programTooltip','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Asterisk (*) indicates final and approved version');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'L`astérisque (*) indique la version finale et approuvée');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'El asterisco (*) indica la versión final y aprobada');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Asterisco (*) indica versão final e aprovada');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dashboard.stockoutTooltip','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Red value indicates planning units with at least one month of stockout in the next 18 months. Blue value indicates planning units with no stockouts in the next 18 months');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'La valeur rouge indique les unités de planification avec au moins un mois de rupture de stock dans les 18 prochains mois. La valeur bleue indique les unités de planification sans rupture de stock dans les 18 prochains mois');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'El valor rojo indica las unidades de planificación con al menos un mes de falta de existencias en los próximos 18 meses. El valor azul indica las unidades de planificación sin faltantes de existencias en los próximos 18 meses');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'O valor vermelho indica unidades de planejamento com pelo menos um mês de falta de estoque nos próximos 18 meses. O valor azul indica unidades de planejamento sem faltas de estoque nos próximos 18 meses');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dashboard.expiryTooltip','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Total cost of projected expiries in the next 18 months');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Coût total des expirations prévues dans les 18 prochains mois');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Costo total de los vencimientos proyectados en los próximos 18 meses');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Custo total dos vencimentos projetados nos próximos 18 meses');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dashboard.qatProblemTooltip','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Click on the refresh icon to re-calculate the QAT Problem List open problems for the selected program(s) below.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Cliquez sur l`icône d`actualisation pour recalculer la liste des problèmes QAT ouverts pour les programmes sélectionnés ci-dessous.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Haga clic en el icono de actualización para volver a calcular los problemas abiertos de la lista de problemas QAT para los programas seleccionados a continuación.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Clique no ícone de atualização para recalcular os problemas abertos da Lista de Problemas do QAT para os programas selecionados abaixo.');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dashboard.uploadedDateTooltip','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'The date this version (draft or final) was uploaded to the server');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'La date à laquelle cette version (brouillon ou finale) a été téléchargée sur le serveur');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'La fecha en que esta versión (borrador o final) se cargó en el servidor');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'A data em que esta versão (rascunho ou final) foi carregada no servidor');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dashboard.reviewStatusTooltip','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Status and date are based on the latest final version uploaded to the server. Click on the status to be redirected to the Supply Plan Version and Review screen. Click on the note icon to view the Notes History.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Le statut et la date sont basés sur la dernière version finale téléchargée sur le serveur. Cliquez sur le statut pour être redirigé vers l`écran Version et révision du plan d`approvisionnement. Cliquez sur l`icône de note pour afficher l`historique des notes.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'El estado y la fecha se basan en la última versión final cargada en el servidor. Haga clic en el estado para ser redirigido a la pantalla Versión y revisión del plan de suministro. Haga clic en el ícono de nota para ver el historial de notas.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'O status e a data são baseados na versão final mais recente carregada no servidor. Clique no status para ser redirecionado para a tela Supply Plan Version and Review. Clique no ícone de nota para visualizar o Notes History.');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dashboard.reportPeriodTooltip','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Default is 6 months in past, 18 months in future. Update in the Program Info screen');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'La valeur par défaut est 6 mois dans le passé, 18 mois dans le futur. Mise à jour dans l`écran Informations sur le programme');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'El valor predeterminado es 6 meses en el pasado y 18 meses en el futuro. Actualice en la pantalla Información del programa');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'O padrão é 6 meses no passado, 18 meses no futuro. Atualização na tela Informações do programa');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.accessDenied','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'You do not have access to view or edit this data or setting.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Vous n`avez pas accès pour afficher ou modifier ces données ou paramètres.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'No tienes acceso para ver o editar estos datos o configuración.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Você não tem acesso para visualizar ou editar esses dados ou configurações.');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.message.addUpdateSuccess','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'{{entityname}} added/updated successfully');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'{{entityname}} ajouté/mis à jour avec succès');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'{{entityname}} añadido/actualizado exitosamente');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'{{entityname}} adicionado/atualizado com sucesso');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.aruProgram','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'To edit, you must have editable access to all selected programs. If you lack editable access to any selected program, the screen will be view-only');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Pour pouvoir effectuer des modifications, vous devez disposer d`un accès modifiable à tous les programmes sélectionnés. Si vous ne disposez pas d`un accès modifiable à un programme sélectionné, l`écran sera en lecture seule.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Para editar, debe tener acceso editable a todos los programas seleccionados. Si no tiene acceso editable a ningún programa seleccionado, la pantalla será de solo lectura.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Para editar, você deve ter acesso editável a todos os programas selecionados. Se você não tiver acesso editável a nenhum programa selecionado, a tela será somente para visualização');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.validated.restrictionNoOfMonthsInPastForTopDashboard','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Please enter No of months in past for top dashboard');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Veuillez saisir le nombre de mois passés pour le tableau de bord supérieur');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Por favor, introduzca el número de meses anteriores para ver el panel superior');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Por favor, insira o número de meses anteriores para o painel superior');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.validated.restrictionNoOfMonthsInFutureForBottomDashboard','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Please enter No of months in future for bottom dashboard');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Veuillez saisir le nombre de mois à venir pour le tableau de bord inférieur');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Por favor, introduzca el número de meses en el futuro para el panel inferior');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Por favor, insira o número de meses no futuro para o painel inferior');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.realm.noOfMonthsInPastForTopDashboard','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'No of months in past for top dashboard');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Nombre de mois dans le passé pour le tableau de bord supérieur');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Número de meses anteriores para el panel superior');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Número de meses anteriores para o painel superior');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.realm.noOfMonthsInFutureForBottomDashboard','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'No of months in future for bottom dashboard');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Nombre de mois dans le futur pour le tableau de bord inférieur');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Número de meses en el futuro para el panel inferior');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Número de meses no futuro para o painel inferior');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dashboard.stockStatusHeaderTooltip','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'The Stock Status % is calculated based on the number of months available in the report period across all planning units. These months are used as the denominator when determining the percentage.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Le pourcentage d`état du stock est calculé en fonction du nombre de mois disponibles dans la période de rapport pour toutes les unités de planification. Ces mois sont utilisés comme dénominateur lors de la détermination du pourcentage.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'El porcentaje de estado de existencias se calcula en función de la cantidad de meses disponibles en el período del informe en todas las unidades de planificación. Estos meses se utilizan como denominador al determinar el porcentaje.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Status de Estoque % é calculado com base no número de meses disponíveis no período do relatório em todas as unidades de planejamento. Esses meses são usados como denominador ao determinar a porcentagem.');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dashboard.forecastErrorHeaderTooltip','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'The forecast error is calculated as the average percentage error over the selected report period. If the percentage is displayed in red, it indicates that the calculated forecast error is above the forecast error threshold (user entered in Update Planning Units screen).');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'L`erreur de prévision est calculée comme le pourcentage moyen d`erreur sur la période de rapport sélectionnée. Si le pourcentage est affiché en rouge, cela indique que l`erreur de prévision calculée est supérieure au seuil d`erreur de prévision (saisi par l`utilisateur dans l`écran Mettre à jour les unités de planification).');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'El error de previsión se calcula como el error porcentual promedio durante el período de informe seleccionado. Si el porcentaje se muestra en rojo, indica que el error de previsión calculado es superior al umbral de error de previsión (introducido por el usuario en la pantalla Actualizar unidades de planificación).');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'O erro de previsão é calculado como o erro percentual médio sobre o período de relatório selecionado. Se a porcentagem for exibida em vermelho, isso indica que o erro de previsão calculado está acima do limite de erro de previsão (usuário inserido na tela Update Planning Units).');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dashboard.dataQualityHeaderTooltip','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'This data does not use the Report Period for calculation; instead it uses the problems flagged in the QAT Problem List for this selected program.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Ces données n`utilisent pas la période de rapport pour le calcul ; elles utilisent plutôt les problèmes signalés dans la liste des problèmes QAT pour ce programme sélectionné.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Estos datos no utilizan el período del informe para el cálculo; en su lugar, utilizan los problemas marcados en la Lista de problemas de QAT para este programa seleccionado.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Esses dados não usam o Período do Relatório para cálculo; em vez disso, eles usam os problemas sinalizados na Lista de Problemas do QAT para este programa selecionado.');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dashboard.shipmentsHeaderTooltip','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Based on chose report period for selected program');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Basé sur la période de rapport choisie pour le programme sélectionné');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Basado en el período de informe elegido para el programa seleccionado');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Com base no período de relatório escolhido para o programa selecionado');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dashboard.expiriesHeaderTooltip','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Total Cost = expired quantity * planning unit cost');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Coût total = quantité expirée * coût unitaire de planification');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Costo total = cantidad vencida * costo unitario de planificación');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Custo total = quantidade expirada * custo unitário de planejamento');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dashboard.forecastedConsumptionTooltip','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Counts the number of PUs that have at least one month of missing forecasted consumption data in the next 18 months');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Compte le nombre d`unités de production pour lesquelles il manque au moins un mois de données de consommation prévues au cours des 18 prochains mois');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cuenta la cantidad de PU que tienen al menos un mes de datos de consumo pronosticados faltantes en los próximos 18 meses');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Conta o número de PUs que têm pelo menos um mês de dados de consumo previstos ausentes nos próximos 18 meses');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dashboard.actualInventoryTooltip','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Counts the number of PUs that have missing recent actual inventory data (within the last 3 months)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Compte le nombre de PU pour lesquelles il manque des données d`inventaire réelles récentes (au cours des 3 derniers mois)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cuenta la cantidad de PU que tienen datos de inventario real reciente faltante (dentro de los últimos 3 meses)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Conta o número de PUs que não possuem dados de inventário real recentes (nos últimos 3 meses)');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dashboard.actualConsumptionTooltip','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Counts the number of PUs that either have missing recent actual consumption data (within the last 3 months), or a gap in actual consumption data (within the last 6 months)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Compte le nombre de PU qui ont soit des données de consommation réelles récentes manquantes (au cours des 3 derniers mois), soit un écart dans les données de consommation réelles (au cours des 6 derniers mois)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cuenta la cantidad de PU que tienen datos de consumo real recientes faltantes (dentro de los últimos 3 meses) o una brecha en los datos de consumo real (dentro de los últimos 6 meses)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Conta o número de PUs que não têm dados de consumo real recentes (nos últimos 3 meses) ou uma lacuna nos dados de consumo real (nos últimos 6 meses)');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dashboard.shipmentsTooltip','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Counts the number of PUs that have shipments with receive dates in the past, or shipments that should have been “submitted” based on program lead times');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Compte le nombre de PU qui ont des expéditions avec des dates de réception dans le passé, ou des expéditions qui auraient dû être « soumises » en fonction des délais d`exécution du programme');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cuenta la cantidad de PU que tienen envíos con fechas de recepción en el pasado o envíos que deberían haberse ""enviado"" según los plazos de entrega del programa.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Conta o número de PUs que têm remessas com datas de recebimento no passado ou remessas que deveriam ter sido “enviadas” com base nos prazos de entrega do programa');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.realm.settingChangeWarning','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'One of the dashboard settings has been updated. In order for this change to reflect, please re-download or upload the program.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'L`un des paramètres du tableau de bord a été mis à jour. Pour que ce changement soit pris en compte, veuillez télécharger à nouveau le programme.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Se ha actualizado una de las configuraciones del panel de control. Para que se refleje este cambio, vuelva a descargar o cargar el programa.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Uma das configurações do painel foi atualizada. Para que essa alteração seja refletida, baixe ou carregue o programa novamente.');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.averagePer','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Average %');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Moyenne %');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Promedio %');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Média %');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dashboard.aboveThreshold','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Calculated Forecast Error is above the Forecast Error Threshold');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'L`erreur de prévision calculée est supérieure au seuil d`erreur de prévision');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'El error de pronóstico calculado es superior al umbral de error de pronóstico');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'O erro de previsão calculado está acima do limite de erro de previsão');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dashboard.noOfShipments','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'# of Shipments');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Nombre d`expéditions');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'# de Envíos');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'# de Remessas');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dashboard.noOfMonths','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'# of Months');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Nombre de mois');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'# de meses');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'# de meses');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dashboard.supplyPlanPrograms','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Supply Planning Programs');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Programmes de planification de l`approvisionnement');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Programas de planificación de suministros');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Programas de Planejamento de Suprimentos');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dashboard.forecastingPrograms','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Forecasting Programs');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Programmes de prévision');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Programas de previsión');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Programas de Previsão');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dashboard.accessiblePrograms','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Accessible Programs');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Programmes accessibles');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Programas accesibles');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Programas Acessíveis');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dashboard.downloadedPrograms','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Downloaded Programs');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Programmes téléchargés');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Programas descargados');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Programas baixados');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dashboard.erpLinkedShipments','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'ERP Linking');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Liaison ERP');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Vinculación ERP');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Vinculação ERP');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dashboard.activePlanningUnits','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Active Planning Units');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Unités de planification actives');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Unidades de Planificación Activa');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Unidades de Planejamento Ativo');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dashboard.stockoutPUs','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Planning Units With Stockouts');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Unités de planification avec ruptures de stock');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Planificación de unidades con faltantes de existencias');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Unidades de Planejamento com Rupturas de Estoque');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dashboard.totalExpiriesCost','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Total Cost of Expiries');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Coût total des expirations');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Costo total de los vencimientos');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Custo total de expirações');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dashboard.openQATProblems','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Open QAT Problems');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Problèmes QAT ouverts');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Problemas de QAT abiertos');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Problemas de QAT aberto');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dashboard.uploadedDate','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Uploaded Date');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Date de téléchargement');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Fecha de subida');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Data de upload');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dashboard.reviewStatus','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Review Status');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Statut de l`évaluation');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Estado de la revisión');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Status da revisão');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dashboard.stockedoutPlanningUnits','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Stocked out Planning Units');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Unités de planification en rupture de stock');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Unidades de planificación agotadas');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Unidades de planejamento estocadas');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dashboard.totalShipments','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Total value of Shipments');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Valeur totale des expéditions');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Valor total de los envíos');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Valor total das remessas');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dashboard.shipmentsTBD','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'# of Shipments with funding TBD');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Nombre d`expéditions avec financement à déterminer');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Número de envíos con financiación por determinar');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'# de remessas com financiamento a ser definido');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dashboard.dataQuality','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Data Quality');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Qualité des données');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Calidad de datos');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Qualidade de dados');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dashboard.totalExpiries','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Total value of Expiries');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Valeur totale des expirations');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Valor total de los vencimientos');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Valor total dos vencimentos');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dashboard.myAccess','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'My Access');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Mon accès');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Mi acceso');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Meu acesso');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dashboard.downloadedSP','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Downloaded Supply Plans');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Plans d`approvisionnement téléchargés');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Planes de suministro descargados');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Planos de Suprimento Baixados');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dashboard.accessSP','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Supply Plans I Can Access');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Plans d`approvisionnement auxquels j`ai accès');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Planes de suministro a los que puedo acceder');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Planos de Suprimentos que Posso Acessar');-- pr"
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dashboard.erpLinkingRealm','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Linked Shipments (Realm)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Expéditions liées (Royaume)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Envíos vinculados (Realm)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Remessas Vinculadas (Reino)');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dashboard.erpLinkingDownloaded','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Linked Shipments (Downloaded)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Envois liés (téléchargés)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Envíos Vinculados (Descargado)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Envios vinculados (baixados)');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dashboard.overview','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Overview');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Aperçu');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Descripción general');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Visão geral');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dashboard.programSpotlight','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Program Spotlight');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Coup de projecteur sur le programme');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Programa destacado');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Destaque do programa');-- pr