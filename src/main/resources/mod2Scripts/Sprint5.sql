INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.forecastValidation.editMe','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Edit me');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Modifiez-moi');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'editarme');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Edite-me');-- pr

USE `fasp`;
DROP procedure IF EXISTS `getSupplyPlanActualConsumption`;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `getSupplyPlanActualConsumption`(PROGRAM_ID INT(10), VERSION_ID INT (10), PLANNING_UNIT_LIST TEXT, REGION_LIST VARCHAR(255), START_DATE DATE, STOP_DATE DATE)
BEGIN
	SET @programId = PROGRAM_ID;
    SET @versionId = VERSION_ID;
    SET @planningUnitList = PLANNING_UNIT_LIST;
    SET @regionList = REGION_LIST;
    SET @startDate = START_DATE;
    SET @stopDate = STOP_DATE;
    
    SELECT 
        ct.CONSUMPTION_DATE, ct.CONSUMPTION_QTY, 
        pu.PLANNING_UNIT_ID, pu.LABEL_ID `PU_LABEL_ID`, pu.LABEL_EN `PU_LABEL_EN`, pu.LABEL_FR `PU_LABEL_FR`, pu.LABEL_SP `PU_LABEL_SP`, pu.LABEL_PR `PU_LABEL_PR`,
        fu.FORECASTING_UNIT_ID, fu.LABEL_ID `FU_LABEL_ID`, fu.LABEL_EN `FU_LABEL_EN`, fu.LABEL_FR `FU_LABEL_FR`, fu.LABEL_SP `FU_LABEL_SP`, fu.LABEL_PR `FU_LABEL_PR`,
        pc.PRODUCT_CATEGORY_ID, pc.LABEL_ID `PC_LABEL_ID`, pc.LABEL_EN `PC_LABEL_EN`, pc.LABEL_FR `PC_LABEL_FR`, pc.LABEL_SP `PC_LABEL_SP`, pc.LABEL_PR `PC_LABEL_PR`,
        r.REGION_ID, r.LABEL_ID `REG_LABEL_ID`, r.LABEL_EN `REG_LABEL_EN`, r.LABEL_FR `REG_LABEL_FR`, r.LABEL_SP `REG_LABEL_SP`, r.LABEL_PR `REG_LABEL_PR`
    FROM (SELECT ct.CONSUMPTION_ID, MAX(ct.VERSION_ID) MAX_VERSION_ID FROM rm_consumption c LEFT JOIN rm_consumption_trans ct ON c.CONSUMPTION_ID=ct.CONSUMPTION_ID WHERE (ct.VERSION_ID<=@versionId) AND c.PROGRAM_ID=@programId GROUP BY ct.CONSUMPTION_ID) tc 
    LEFT JOIN rm_consumption cons ON tc.CONSUMPTION_ID=cons.CONSUMPTION_ID
    LEFT JOIN rm_consumption_trans ct ON tc.CONSUMPTION_ID=ct.CONSUMPTION_ID AND tc.MAX_VERSION_ID=ct.VERSION_ID
    LEFT JOIN vw_planning_unit pu ON ct.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID
    LEFT JOIN vw_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID
    LEFT JOIN vw_product_category pc ON fu.PRODUCT_CATEGORY_ID=pc.PRODUCT_CATEGORY_ID
    LEFT JOIN vw_region r ON ct.REGION_ID=r.REGION_ID
    WHERE ct.CONSUMPTION_DATE BETWEEN @startDate AND @stopDate   
    AND FIND_IN_SET(ct.PLANNING_UNIT_ID, @planningUnitList) 
    AND ct.ACTIVE AND ct.ACTUAL_FLAG AND FIND_IN_SET(ct.REGION_ID, @regionList)
    ORDER BY ct.PLANNING_UNIT_ID, ct.REGION_ID, ct.CONSUMPTION_DATE;
END$$

DELIMITER ;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Before committing, remember to select a forecast for each planning unit in the `Compare and Select` screen'
where l.LABEL_CODE='static.commitTree.note' and ll.LANGUAGE_ID=1;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Avant de vous engager, n`oubliez pas de sélectionner une prévision pour chaque unité de planification dans l`écran `Comparer et sélectionner`'
where l.LABEL_CODE='static.commitTree.note' and ll.LANGUAGE_ID=2;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Antes de comprometerse, recuerde seleccionar un pronóstico para cada unidad de planificación en la pantalla `Comparar y seleccionar`.'
where l.LABEL_CODE='static.commitTree.note' and ll.LANGUAGE_ID=3;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Antes de confirmar, lembre-se de selecionar uma previsão para cada unidade de planejamento na tela `Compare and Select`'
where l.LABEL_CODE='static.commitTree.note' and ll.LANGUAGE_ID=4;



-- - Tooltip
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.scenario','48');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Choose scenario for which the data below applies');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Choisissez le scénario pour lequel les données ci-dessous sappliquent');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Elija el escenario para el que se aplican los datos a continuación');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Escolha o cenário para o qual os dados abaixo se aplicam');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.NodeTitle','48');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'The node title will show up on the Tree View');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Le titre du nœud apparaîtra dans larborescence');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'El título del nodo aparecerá en la vista de árbol');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'O título do nó aparecerá na visualização em árvore');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.NodeType','48');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Click on Show Guidance for explanation on the node types');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Cliquez sur Afficher le guidage pour obtenir des explications sur les types de nœuds');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Haga clic en Mostrar orientación para obtener una explicación sobre los tipos de nodos');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Clique em Mostrar orientação para explicação sobre os tipos de nós');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.NodeValue','48');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Node Value = [Parent Value] * [Node Percentage]');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Valeur du nœud = [Valeur parent] * [Pourcentage du nœud]');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Valor de nodo = [Valor principal] * [Porcentaje de nodo]');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Valor do nó = [Valor pai] * [Porcentagem do nó]');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.ModelingTransferMonth','48');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Select month for the sample calculation in the Calculated Change column');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Sélectionnez le mois pour lexemple de calcul dans la colonne Variation calculée');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Seleccione el mes para el cálculo de muestra en la columna Cambio calculado');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Selecione o mês para o cálculo de amostra na coluna Alteração calculada');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.Transfercloumn','48');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Link the changes in this node to the changes in another node in the same level Transfers must occur from left to right');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Lier les modifications de ce nœud aux modifications dun autre nœud du même niveau Les transferts doivent se faire de gauche à droite');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Vincule los cambios en este nodo a los cambios en otro nodo en el mismo nivel Las transferencias deben ocurrir de izquierda a derecha');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Vincule as mudanças neste nó às mudanças em outro nó no mesmo nível As transferências devem ocorrer da esquerda para a direita');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.ModelingType','48');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'How the node will change over time Exponential (%) Percent increase decrease based on the preceeding months value  Linear (%) Percent increase decrease based on the starting months value Linear (#) Increase decrease based on a fixed, monthly quantity Click on Show terms and logic for more');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Comment le nœud changera-t-il au fil du temps Exponentielle (%) Diminution de laugmentation en pourcentage basée sur la valeur du mois précédent Linéaire (%) Diminution de laugmentation en pourcentage basée sur la valeur du mois de départ Linéaire (#) Diminution de laugmentation basée sur une quantité mensuelle fixe Cliquez sur Afficher les termes et la logique pour plus');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cómo cambiará el nodo con el tiempo Exponencial (%) Disminución del aumento porcentual en función del valor del mes anterior Lineal (%) Disminución del aumento porcentual en función del valor del mes inicial Lineal (#) Disminución del aumento en función de una cantidad mensual fija Haga clic en Mostrar términos y logica para mas');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Como o nó mudará ao longo do tempo Exponencial (%) Aumento percentual diminuição com base no valor do mês anterior Linear (%) Aumento percentual diminuição com base no valor do mês inicial Linear (#) Aumento diminuição com base em uma quantidade fixa mensal Clique em Mostrar termos e lógica para mais');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.ModelingCalculator','48');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Use the modeling calculator to interpolate to a target or if you need help figuring out the monthly change');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Utilisez la calculatrice de modélisation pour interpoler à une cible ou si vous avez besoin daide pour déterminer la variation mensuelle');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Use la calculadora de modelado para interpolar a un objetivo o si necesita ayuda para calcular el cambio mensual');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Use a calculadora de modelagem para interpolar para um destino ou se precisar de ajuda para descobrir a mudança mensal');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.CalculatorChangeforMonth','48');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Sample calculation for one month Click View month by month data for more');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Exemple de calcul pour un mois Cliquez sur Afficher les données mois par mois pour en savoir plus');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cálculo de muestra para un mes Haga clic en Ver datos mes por mes para obtener más información');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Cálculo de amostra para um mês Clique em Ver dados mês a mês para mais');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.TargetEndingValue','48');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Ending value at the target date');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Valeur finale à la date cible');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Valor final en la fecha objetivo');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Valor final na data prevista');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.TargetChangePercent','48');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Total change positive or negative in percent for the node over the period from start date to target date');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Changement total positif ou négatif en pourcentage pour le nœud sur la période allant de la date de début à la date cible');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cambio total positivo o negativo en porcentaje para el nodo durante el período desde la fecha de inicio hasta la fecha de destino');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Mudança total positiva ou negativa em porcentagem para o nó durante o período desde a data de início até a data de destino');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.TargetChangeHash','48');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Total change positive or negative for the node over the period from start date to target date');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Changement total positif ou négatif pour le nœud sur la période allant de la date de début à la date cible');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cambio total positivo o negativo para el nodo durante el período desde la fecha de inicio hasta la fecha de destino');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Mudança total positiva ou negativa para o nó durante o período desde a data de início até a data de destino');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.CalculatedMonthOnMonthChnage','48');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Using the provided target  QAT calculates a monthly change which will be fed back into the previous screen');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Lutilisation de la cible QAT fournie calcule un changement mensuel qui sera réinjecté dans lécran précédent');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Utilizando el QAT objetivo proporcionado, se calcula un cambio mensual que se retroalimentará a la pantalla anterior');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'O uso do QAT de destino fornecido calcula uma alteração mensal que será realimentada na tela anterior');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.Parent','48');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'To change the parent use drag and drop on the Tree View');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Pour changer le parent, utilisez le glisser-déposer sur larborescence');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Para cambiar el uso principal, arrastre y suelte en la vista de árbol');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Para alterar o pai use arrastar e soltar na Vista em Árvore');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.PercentageOfParent','48');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Enter the starting percentage of the node If this value changes over time, use the Modeling/Transfer tab');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Saisissez le pourcentage de départ du nœud Si cette valeur évolue dans le temps, utilisez longlet Modélisation/Transfert');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Introduzca el porcentaje inicial del nodo. Si este valor cambia con el tiempo, utilice la pestaña Modelado/Transferencia.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Insira a porcentagem inicial do nó Se esse valor mudar ao longo do tempo, use a guia Modelagem/Transferência');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.ParentValue','48');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'To change the parent value edit the parent node directly');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Pour modifier la valeur parent, modifiez directement le nœud parent');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Para cambiar el valor principal, edite el nodo principal directamente');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Para alterar o valor pai, edite o nó pai diretamente');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.MonthlyChange','48');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Change in percentage points per month For example if the starting node value is 60% of the parent, and the Monthly Change is 5% then month 2 starts at 65% month 3 starts at 70% etc');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Changement en points de pourcentage par mois Par exemple, si la valeur du nœud de départ est de 60 % du parent et que le changement mensuel est de 5 %, le mois 2 commence à 65 %, le mois 3 commence à 70 %, etc');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cambio en puntos porcentuales por mes Por ejemplo, si el valor del nodo inicial es el 60 % del principal y el cambio mensual es del 5 %, entonces el mes 2 comienza en el 65 %, el mes 3 comienza en el 70 %, etc');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Mudança em pontos percentuais por mês Por exemplo, se o valor do nó inicial for 60% do pai, e a Mudança Mensal for 5%, então o mês 2 começa em 65% o mês 3 começa em 70% etc');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.tracercategoryModelingType','48');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Select a tracer category to narrow down the list of forecasting units to select from');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Sélectionnez une catégorie de traceur pour affiner la liste des unités de prévision à sélectionner');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Seleccione una categoría de seguimiento para reducir la lista de unidades de pronóstico para seleccionar');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Selecione uma categoria de rastreador para restringir a lista de unidades de previsão para selecionar');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.CopyFromTemplate','48');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Use a template to fill in all subsquent fields Edit these in the Program Management Usage Template screen');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Utilisez un modèle pour remplir tous les champs suivants Modifiez-les dans lécran Modèle dutilisation de la gestion des programmes');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Utilice una plantilla para completar todos los campos subsiguientes. Edítelos en la pantalla Plantilla de uso de gestión de programas.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Use um modelo para preencher todos os campos subsequentes Edite-os na tela Modelo de uso de gerenciamento de programas');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.TypeOfUse','48');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Select discrete if this product will be used for limited time and continuous if it will be used indefinitely');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Sélectionnez discret si ce produit sera utilisé pendant un temps limité et continu sil sera utilisé indéfiniment');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Seleccione discreto si este producto se usará por tiempo limitado y continuo si se usará indefinidamente');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Selecione discreto se este produto for usado por tempo limitado e contínuo se for usado indefinidamente');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.LagInMonth','48');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Use this feature for phased product usage For example if the lag is 2 the product usage will begin 2 months after the parent node dates');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Utilisez cette fonctionnalité pour une utilisation progressive du produit Par exemple, si le décalage est de 2, lutilisation du produit commencera 2 mois après les dates du nœud parent');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Use esta función para el uso del producto por etapas. Por ejemplo, si el retraso es 2, el uso del producto comenzará 2 meses después de las fechas del nodo principal.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Use esta funcionalidade para uma utilização faseada do produto Por exemplo se o atraso for 2 a utilização do produto irácomeçar 2 meses após as datas do nó pai');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.SingleUse','48');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'If yes frequency and length of time are not required');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Si oui, la fréquence et la durée ne sont pas requises');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'En caso afirmativo, no se requiere frecuencia ni duración');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Se sim, a frequência e o tempo não são necessários');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.TypeOfUsePU','48');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'To change edit from parent Forecasting Unit node');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Pour modifier la modification à partir du nœud dunité de prévision parent');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Para cambiar la edición del nodo Unidad de previsión principal');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Para alterar a edição do nó pai da unidade de previsão');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.planningUnitNode','48');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Select a planning unit that corresponds to the selected forecasting unit Edit the parent node if the forecasting unit is incorrect');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Sélectionnez une unité de planification qui correspond à lunité de prévision sélectionnée Modifiez le nœud parent si lunité de prévision est incorrecte');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Seleccione una unidad de planificación que corresponda a la unidad de previsión seleccionada. Edite el nodo principal si la unidad de previsión es incorrecta.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Selecione uma unidade de planejamento que corresponda à unidade de previsão selecionada Edite o nó pai se a unidade de previsão estiver incorreta');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.Conversionfactor','48');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Auto-populated based on conversion between forecasting unit FU and planning unit PU');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Renseigné automatiquement en fonction de la conversion entre lunité de prévision FU et lunité de planification PU');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Autocompletado basado en la conversión entre la unidad de pronóstico FU y la unidad de planificación PU');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Preenchido automaticamente com base na conversão entre a unidade de previsão FU e a unidade de planejamento PU');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.NoOfPUUsage','48');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'The number of forecasting units FU divided by the conversion factor equals the number of planning units PU required');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Le nombre dunités de prévision FU divisé par le facteur de conversion est égal au nombre dunités de planification PU nécessaires');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'El número de unidades de pronóstico FU dividido por el factor de conversión es igual al número de unidades de planificación PU requeridas');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'O número de unidades de previsão FU dividido pelo fator de conversão é igual ao número de unidades de planejamento PU necessárias');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.QATEstimateForInterval','48');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'QAT calculates an estimated consumption interval by dividing conversion factor by the quantity required monthly');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'QAT calcule un intervalle de consommation estimé en divisant le facteur de conversion par la quantité requise mensuellement');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'QAT calcula un intervalo de consumo estimado dividiendo el factor de conversión por la cantidad requerida mensualmente');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'QAT calcula um intervalo de consumo estimado dividindo o fator de conversão pela quantidade necessária mensalmente');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.ConsumptionIntervalEveryXMonths','48');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'For the purposes of your forecast, how often is the product actually consumed Consumption can be defined at different levels depending on your supply chain For example if the end user uses a product daily but only picks up the product every 2 months enter 2 to account for a multi-month consmption pattern');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Aux fins de vos prévisions, à quelle fréquence le produit est-il réellement consommé La consommation peut être définie à différents niveaux en fonction de votre chaîne dapprovisionnement Par exemple, si lutilisateur final utilise un produit quotidiennement mais ne récupère le produit que tous les 2 mois, entrez 2 pour tenir compte un modèle de consommation sur plusieurs mois');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'A los fines de su pronóstico, ¿con qué frecuencia se consume realmente el producto? El consumo se puede definir en diferentes niveles según su cadena de suministro. Por ejemplo, si el usuario final usa un producto diariamente pero solo lo recoge cada 2 meses, ingrese 2 para tener en cuenta. un patrón de consumo de varios meses');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Para os propósitos de sua previsão, com que frequência o produto é realmente consumido O consumo pode ser definido em diferentes níveis dependendo da sua cadeia de suprimentos Por exemplo, se o usuário final usa um produto diariamente, mas só pega o produto a cada 2 meses, insira 2 para contabilizar um padrão de consumo de vários meses');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.willClientsShareOnePU','48');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'If yes this means end users will be sharing a planning unit eg if PU can be split into smaller units for distribution');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Si oui, cela signifie que les utilisateurs finaux partageront une unité de planification, par exemple si PU peut être divisé en unités plus petites pour la distribution');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Si oui, cela signifie que les utilisateurs finaux partageront une unité de planification, par exemple si PU peut être divisé en unités plus petites pour la distribution');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Se sim, isso significa que os usuários finais compartilharão uma unidade de planejamento, por exemplo, se a PU puder ser dividida em unidades menores para distribuição');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.HowManyPUperIntervalPer','48');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Calculated as # of planning units PU per month * consumption interval');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Calculé en nombre dunités de planification PU par mois * intervalle de consommation');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Calculado como # de unidades de planificación PU por mes * intervalo de consumo');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Calculado como # de unidades de planejamento PU por mês * intervalo de consumo');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.planningProgramSetting','48');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Program must be loaded first as Planning Unit Settings are version specific');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Le programme doit être chargé en premier car les paramètres dunité de planification sont spécifiques à la version');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'El programa debe cargarse primero ya que la configuración de la unidad de planificación es específica de la versión');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'O programa deve ser carregado primeiro, pois as configurações da unidade de planejamento são específicas da versão');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.Stock','48');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Enter your actual or projected stock at the beginning of your forecast period This is used for calculating the procurement gap in the Forecast Summary screen');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Entrez votre stock réel ou projeté au début de votre période de prévision Ceci est utilisé pour calculer lécart dapprovisionnement dans lécran Résumé des prévisions');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ingrese su stock real o proyectado al comienzo de su período de pronóstico Esto se usa para calcular la brecha de adquisición en la pantalla Resumen de pronóstico');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Insira seu estoque real ou projetado no início do período de previsão Isso é usado para calcular a lacuna de suprimento na tela Resumo da previsão');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.ExistingShipments ','48');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Enter the total quantity of existing shipments during your forecast period This is used for calculating the procurement gap in the Forecast Summary screen Leave this blank to assume no existing shipments');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Entrez la quantité totale dexpéditions existantes au cours de votre période de prévision Ceci est utilisé pour calculer lécart dapprovisionnement dans lécran Résumé des prévisions Laissez ce champ vide pour supposer quil ny a pas dexpéditions existantes');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ingrese la cantidad total de envíos existentes durante su período de pronóstico Esto se usa para calcular la brecha de adquisición en la pantalla Resumen de pronóstico Deje esto en blanco para asumir que no hay envíos existentes');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Introduza a quantidade total de envios existentes durante o seu período de previsão Istoéusado para calcular a lacuna de aprovisionamento no ecrã Resumo da Previsão Deixe em branco para assumir que não há envios existentes');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.DesiredMonthsofStock  ','48');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Enter your desired Months of Stock at the end of your forecast period This is used for calculating the procurement gap in the Forecast Summary screen');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Entrez vos mois de stock souhaités à la fin de votre période de prévision Ceci est utilisé pour calculer lécart dapprovisionnement dans lécran Résumé des prévisions');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ingrese los Meses de existencias deseados al final de su período de pronóstico Esto se usa para calcular la brecha de adquisición en la pantalla Resumen de pronóstico');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Insira os Meses de Estoque desejados no final do período de previsão Isso é usado para calcular a lacuna de aquisição na tela Resumo da Previsão');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.PriceType  ','48');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Select a procurement agent for your price type If PA proces are available QAT will populate the price You may override QAT provided prices with your own. If you dont see the desired procurement agent, select custom and indicate the procurement agent name in the notes.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Sélectionnez un agent dapprovisionnement pour votre type de prix Si les processus PA sont disponibles, QAT remplira le prix Vous pouvez remplacer les prix fournis par QAT par les vôtres. Si vous ne voyez pas lagent dapprovisionnement souhaité, sélectionnez ""personnalisé"" et indiquez le nom de lagent dapprovisionnement dans les notes.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Seleccione un agente de adquisiciones para su tipo de precio. Si los procesos de PA están disponibles, QAT completará el precio. Puede anular los precios provistos por QAT con los suyos. Si no ve el agente de compras deseado, seleccione ""personalizado"" e indique el nombre del agente de compras en las notas.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Selecione um agente de compras para seu tipo de preço Se os processos de PA estiverem disponíveis, QAT preencherá o preço Você pode substituir os preços fornecidos pela QAT pelos seus próprios. Se você não vir o agente de compras desejado, selecione ""personalizado"" e indique o nome do agente de compras nas notas.');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.yAxisInEquivalencyUnit  ','48');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'If yes Yaxis is displayed in equivalency units which allows the forecast to be displayed in programmatic units and combined with other products that share the same equivalency units Program Admins Use the Equivalency Units screen to manage equivalency units');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Si oui, laxe Y est affiché en unités déquivalence, ce qui permet dafficher les prévisions en unités programmatiques et de les combiner avec dautres produits partageant les mêmes unités déquivalence Administrateurs du programme Utilisez lécran Unités déquivalence pour gérer les unités déquivalence');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'En caso afirmativo, Yaxis se muestra en unidades de equivalencia, lo que permite que el pronóstico se muestre en unidades programáticas y se combine con otros productos que comparten las mismas unidades de equivalencia Administradores del programa Use la pantalla Unidades de equivalencia para administrar las unidades de equivalencia');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Se sim Yaxis é exibido em unidades de equivalência o que permite que a previsão seja exibida em unidades programáticas e combinada com outros produtos que compartilham as mesmas unidades de equivalência Administradores do programa Use a tela Unidades de equivalência para gerenciar unidades de equivalência');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.version  ','48');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Only local version settings are editable Please load program if you wish to edit version settings');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Seuls les paramètres de la version locale sont modifiables Veuillez charger le programme si vous souhaitez modifier les paramètres de la version');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Solo se puede editar la configuración de la versión local Cargue el programa si desea editar la configuración de la versión');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Apenas as configurações da versão local são editáveis ​​Carregue o programa se desejar editar as configurações da versão');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.HashOfDaysInMonth  ','48');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Used in Consumption Forecasts for adjusting actual consumption against stock out days');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Utilisé dans les prévisions de consommation pour ajuster la consommation réelle par rapport aux jours de rupture de stock');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Se utiliza en las previsiones de consumo para ajustar el consumo real frente a los días de desabastecimiento');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Usado em previsões de consumo para ajustar o consumo real em relação aos dias de falta de estoque');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.FreightPercent  ','48');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Used in Forecast Summary screen to calculate freight cost');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Utilisé dans lécran Résumé des prévisions pour calculer le coût du fret');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Se usa en la pantalla Resumen de pronóstico para calcular el costo del flete');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Usado na tela Resumo da previsão para calcular o custo do frete');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.ForecastThresholdHigh  ','48');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Used in Compare and Select screen to compare tree forecasts againt consumption forecasts QAT will flag any tree forecasts that are this % above the highest consumption forecast');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Utilisé dans lécran Comparer et sélectionner pour comparer les prévisions darbre avec les prévisions de consommation QAT signalera toutes les prévisions darbre qui sont ce % au-dessus de la prévision de consommation la plus élevée');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Se usa en la pantalla Comparar y Seleccionar para comparar pronósticos de árbol con pronósticos de consumo. QAT marcará cualquier pronóstico de árbol que esté este % por encima del pronóstico de consumo más alto.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Usado na tela Comparar e Selecionar para comparar as previsões da árvore com as previsões de consumo O QAT sinalizará qualquer previsão de árvore que esteja esta % acima da previsão de consumo mais alta');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.ForecastThresholdLow ','48');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Used in Compare and Select screen to compare tree forecasts againt consumption forecasts QAT will flag any tree forecasts that are this % below the lowest consumption forecast');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Utilisé dans lécran Comparer et sélectionner pour comparer les prévisions darbre avec les prévisions de consommation QAT signalera toutes les prévisions darbre qui sont à ce % en dessous de la prévision de consommation la plus basse');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Se usa en la pantalla Comparar y seleccionar para comparar pronósticos de árbol con pronósticos de consumo. QAT marcará cualquier pronóstico de árbol que esté este % por debajo del pronóstico de consumo más bajo.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Usado na tela Comparar e Selecionar para comparar as previsões da árvore com as previsões de consumo O QAT sinalizará qualquer previsão de árvore que esteja essa % abaixo da previsão de consumo mais baixa');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.ForecastProgram ','48');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Realm Level templates are available to all users However  program admins can create program specific templates');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Les modèles de niveau de domaine sont disponibles pour tous les utilisateurs Cependant, les administrateurs du programme peuvent créer des modèles spécifiques au programme');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Las plantillas de nivel de dominio están disponibles para todos los usuarios. Sin embargo, los administradores del programa pueden crear plantillas específicas del programa.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Os modelos de nível de domínio estão disponíveis para todos os utilizadores No entanto os administradores do programa podem criar modelos específicos do programa');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.UsageName ','48');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'These Ugsage Names will appear when adding editing a forecasting tree node in the Copy from Template dropdown');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Ces noms Ugsage apparaîtront lors de lajout de la modification dun nœud darbre de prévision dans la liste déroulante Copier à partir du modèle');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Estos nombres de uso aparecerán al agregar la edición de un nodo de árbol de pronóstico en el menú desplegable Copiar de plantilla');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Esses nomes de Ugsage aparecerão ao adicionar a edição de um nó de árvore de previsão no menu suspenso Copiar do modelo');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.LagInMonth ','48');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'0 indicates immediate usage');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'0 indique une utilisation immédiate');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'0 indica uso inmediato');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'0 indica uso imediato');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.UsageType ','48');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Continuous usage product will be used for an indeterminate amount of time Discrete usage product will be used for a finite period as defined by the user');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Le produit à usage continu sera utilisé pendant une durée indéterminée Le produit à usage discret sera utilisé pendant une période déterminée définie par lutilisateur');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'El producto de uso continuo se utilizará durante un período de tiempo indeterminado El producto de uso discreto se utilizará durante un período finito definido por el usuario');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'O produto de uso contínuo será usado por um período indeterminado O produto de uso discreto será usado por um período finito conforme definido pelo usuário');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.Persons ','48');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'If product is shared indicate how many persons share this product If a product is not shared enter 1');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Si le produit est partagé, indiquez combien de personnes partagent ce produit Si un produit nest pas partagé, entrez 1');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Si el producto se comparte, indique cuántas personas comparten este producto. Si el producto no se comparte, ingrese 1.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Se o produto for compartilhado indique quantas pessoas compartilham este produto Se um produto não for compartilhado digite 1');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.PersonsUnit ','48');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'The desired unit for the # Persons column Note this is used in the Usage in Words column on this screen but when the template is used this unit does not carry over Instead the unit of the parent node is used');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Lunité souhaitée pour la colonne Nombre de personnes Notez quelle est utilisée dans la colonne Utilisation dans les mots sur cet écran, mais lorsque le modèle est utilisé, cette unité nest pas reportée Au lieu de cela, lunité du nœud parent est utilisée');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'La unidad deseada para la columna # Personas Tenga en cuenta que esto se usa en la columna Uso en palabras en esta pantalla, pero cuando se usa la plantilla, esta unidad no se transfiere. En su lugar, se usa la unidad del nodo principal.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'A unidade desejada para a coluna # Pessoas Repare que istoéusado na coluna Uso em Palavras neste ecrã mas quando o modeloéusado esta unidade não é transportada Em vez dissoéusada a unidade do nó pai');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.FUPersonTime ','48');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'How much of the forecasting unit is consumed each time');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Quelle quantité dunité de prévision est consommée à chaque fois');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cuánto de la unidad de pronóstico se consume cada vez');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Quanto da unidade de previsão é consumida a cada vez');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.OneTimeUsage ','48');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Discrete only If Yes the forecasting unit is only used once and subsequent fields will not be required');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Discrète uniquement Si Oui, lunité de prévision nest utilisée quune seule fois et les champs suivants ne seront pas requis');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Solo discreto En caso afirmativo, la unidad de pronóstico solo se usa una vez y los campos posteriores no serán obligatorios');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Apenas discreto Se Sim, a unidade de previsão é usada apenas uma vez e os campos subsequentes não serão obrigatórios');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.OfTimeFreqwency ','48');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'How often the forecasting unit is used based on the time period in Frequency Unit For example if the product is used 4 times per year enter 4 in this column and year in the next column');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'La fréquence dutilisation de lunité de prévision en fonction de la période dans Frequency Unit Par exemple, si le produit est utilisé 4 fois par an, entrez 4 dans cette colonne et lannée dans la colonne suivante');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Con qué frecuencia se usa la unidad de pronóstico según el período de tiempo en Unidad de frecuencia Por ejemplo, si el producto se usa 4 veces al año, ingrese 4 en esta columna y el año en la siguiente columna');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Com que frequência a unidade de previsão é usada com base no período de tempo em Unidade de Frequência Por exemplo se o produto for usado 4 vezes por ano insira 4 nesta coluna e ano na próxima coluna');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.Freqwency ','48');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'The time period referenced by the Frequency used in the previous column For example if the product is used 3 times per day enter 3 in previous column and day in this column');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'La période de temps référencée par la Fréquence utilisée dans la colonne précédente Par exemple si le produit est utilisé 3 fois par jour entrez 3 dans la colonne précédente et le jour dans cette colonne');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'El período de tiempo al que hace referencia la Frecuencia utilizada en la columna anterior. Por ejemplo, si el producto se usa 3 veces al día, ingrese 3 en la columna anterior y el día en esta columna.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'O período de tempo referenciado pela Frequência utilizada na coluna anterior Por exemplo se o produto for utilizado 3 vezes por dia introduza 3 na coluna anterior e o dia nesta coluna');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.UsagePeriod ','48');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'The length of the usage period For continuous usage choose indefinitely');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'La durée de la période dutilisation Pour une utilisation continue, choisissez indéfiniment');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'La duración del período de uso Para uso continuo, elija indefinidamente');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'A duração do período de uso Para uso contínuo, escolha indefinidamente');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.PeriodUnit ','48');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'The desired unit for the Period column');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Lunité souhaitée pour la colonne Période');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'La unidad deseada para la columna Período');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'A unidade desejada para a coluna Período');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.OfFuRequired ','48');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'(Discrete only) The total forecasting units required for the period. Equal to: (# of FU / Person / Time) / (# People) * (Usage Frequency, converted to frequency/month) * (Usage Period, converted to months)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'(Discrète uniquement) Le nombre total dunités de prévision requises pour la période. Égal à : (# dUF / Personne / Temps) / (# Personnes) * (Fréquence dutilisation, convertie en fréquence/mois) * (Période dutilisation, convertie en mois)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'(Solo discreto) Las unidades de pronóstico totales requeridas para el período. Igual a: (# de FU/Persona/Tiempo) / (# de Personas) * (Frecuencia de Uso, convertida a frecuencia/mes) * (Período de Uso, convertido a meses)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'(Somente discreto) O total de unidades de previsão necessárias para o período. Igual a: (# de FU / Pessoa / Tempo) / (# Pessoas) * (Frequência de uso, convertida em frequência/mês) * (Período de uso, convertido em meses)');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.UsageInWords','48');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'This column combines all user inputs provided in previous columns Use this column to ensure your inputs are correct');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Cette colonne combine toutes les entrées utilisateur fournies dans les colonnes précédentes Utilisez cette colonne pour vous assurer que vos entrées sont correctes');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Esta columna combina todas las entradas del usuario proporcionadas en las columnas anteriores Use esta columna para asegurarse de que sus entradas sean correctas');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Esta coluna combina todas as entradas do utilizador fornecidas nas colunas anteriores Use esta coluna para garantir que as suas entradas estão correctas');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.Display','48');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Select which forecasts to display in the graph and table below');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Sélectionnez les prévisions à afficher dans le graphique et le tableau ci-dessous');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Seleccione qué pronósticos mostrar en el gráfico y la tabla a continuación');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Selecione quais previsões serão exibidas no gráfico e na tabela abaixo');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.CompareandSelectType','48');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Tree forecast or a Consumption forecast');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Prévision darbre ou prévision de consommation');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Pronóstico de árbol o un pronóstico de consumo');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Previsão de árvore ou uma previsão de consumo');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.Forecst','48');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'For Tree Forecasts this field displays tree and scenario name set by the user For Consumption Forecasts this field displays the names of the consumption forecast methods selected by the user Forecasts with missing data will be gray and non editable To add or update forecast data navigate to the Consumption or Tree forecast screens using the links in the upper left of the screen or on the lefthand side menu');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Pour les prévisions arborescentes, ce champ affiche le nom de larborescence et du scénario défini par lutilisateur. Pour les prévisions de consommation, ce champ affiche les noms des méthodes de prévision de consommation sélectionnées par lutilisateur. Les prévisions avec des données manquantes seront grisées et non modifiables. Pour ajouter ou mettre à jour les données de prévision, accédez au Écrans de prévision de consommation ou darborescence en utilisant les liens en haut à gauche de lécran ou dans le menu de gauche');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Para pronósticos de árbol, este campo muestra el árbol y el nombre del escenario establecido por el usuario. Para pronósticos de consumo, este campo muestra los nombres de los métodos de pronóstico de consumo seleccionados por el usuario. Los pronósticos con datos faltantes aparecerán en gris y no se podrán editar. Pantallas de previsión de consumo o árbol utilizando los enlaces en la parte superior izquierda de la pantalla o en el menú del lado izquierdo');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Para Previsões de Árvore este campo exibe o nome da árvore e do cenário definido pelo usuário Para Previsões de Consumo este campo exibe os nomes dos métodos de previsão de consumo selecionados pelo usuário Previsões com dados ausentes serão cinza e não editáveis ​​Para adicionar ou atualizar dados de previsão navegue até o Telas de previsão de consumo ou árvore usando os links no canto superior esquerdo da tela ou no menu do lado esquerdo');
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.SelectAsForecast','48');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Select one forecast as the final forecast for each region and planning unit');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Sélectionnez une prévision comme prévision finale pour chaque région et unité de planification');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Seleccione un pronóstico como el pronóstico final para cada región y unidad de planificación');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Selecione uma previsão como a previsão final para cada região e unidade de planejamento');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.TotalForecast','48');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Total forecasted quantity during the Forecast Period.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Quantité totale prévue pendant la période de prévision.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cantidad total prevista durante el Período de previsión.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Quantidade total prevista durante o período de previsão.');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.ForecastError','48');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Forecast Error calculated using Weighted Absolute Percentage Error WAPE');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Erreur de prévision calculée à laide du pourcentage derreur absolu pondéré WAPE');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Error de pronóstico calculado utilizando el error de porcentaje absoluto ponderado WAPE');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Erro de previsão calculado usando o erro de porcentagem absoluta ponderada WAPE');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.ForecastErrorMonthUsed','48');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Retrieving data. Wait a few seconds and try to cut or copy again.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Récupération des données. Attendez quelques secondes et essayez à nouveau de couper ou de copier.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Recuperando datos. Espere unos segundos e intente cortar o copiar de nuevo.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Recuperando dados. Aguarde alguns segundos e tente recortar ou copiar novamente.');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.ComparetoConsumptionForecast','48');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'For each Tree Forecast QAT will calculate the percentage above the highest or below the lowest Consumption Forecast The percentage will be highlighted in red text if it is outside of the threshold percentages set by the user in the Version Settings screen');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Pour chaque arbre de prévision, QAT calculera le pourcentage au-dessus de la prévision de consommation la plus élevée ou en dessous de la plus basse Le pourcentage sera mis en surbrillance en rouge sil est en dehors des pourcentages de seuil définis par lutilisateur dans lécran Paramètres de version');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Para cada Pronóstico de árbol, QAT calculará el porcentaje por encima del más alto o por debajo del más bajo. Pronóstico de consumo El porcentaje se resaltará en texto rojo si está fuera de los porcentajes de umbral establecidos por el usuario en la pantalla Configuración de la versión.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Para cada Tree Forecast o QAT calculará a porcentagem acima da mais alta ou abaixo da mais baixa Previsão de Consumo A porcentagem será destacada em texto vermelho se estiver fora das porcentagens limite definidas pelo usuário na tela de Configurações da Versão');
