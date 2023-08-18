DROP TABLE IF EXISTS `rm_forecast_tree_node_data_annual_target_calculator`;
CREATE TABLE `fasp`.`rm_forecast_tree_node_data_annual_target_calculator` (
  `NODE_DATA_ANNUAL_TARGET_CALCULATOR_ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `NODE_DATA_ID` INT UNSIGNED NOT NULL,
  `CALCULATOR_FIRST_MONTH` VARCHAR(7) NOT NULL,
  `CALCULATOR_YEARS_OF_TARGET` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`NODE_DATA_ANNUAL_TARGET_CALCULATOR_ID`),
  INDEX `fk_rm_ftnd_annual_target_calculator_idx` (`NODE_DATA_ID` ASC) ,
  CONSTRAINT `fk_rm_ftnd_annual_target_calculator_nodeDataId`
    FOREIGN KEY (`NODE_DATA_ID`)
    REFERENCES `fasp`.`rm_forecast_tree_node_data` (`NODE_DATA_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

DROP TABLE IF EXISTS `rm_forecast_tree_node_data_annual_target_calculator_data`;
CREATE TABLE `fasp`.`rm_forecast_tree_node_data_annual_target_calculator_data` (
  `NODE_DATA_ANNUAL_TARGET_CALCULATOR_DATA_ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `NODE_DATA_ANNUAL_TARGET_CALCULATOR_ID` INT UNSIGNED NOT NULL,
  `ACTUAL_OR_TARGET_VALUE` int unsigned NOT NULL,
  PRIMARY KEY (`NODE_DATA_ANNUAL_TARGET_CALCULATOR_DATA_ID`),
  INDEX `fk_rm_ftnd_annual_target_calculator_data_idx` (`NODE_DATA_ANNUAL_TARGET_CALCULATOR_ID` ASC) ,
  CONSTRAINT `fk_rm_ftnd_annual_target_calculator_annualTargetCalculatorId`
    FOREIGN KEY (`NODE_DATA_ANNUAL_TARGET_CALCULATOR_ID`)
    REFERENCES `fasp`.`rm_forecast_tree_node_data_annual_target_calculator` (`NODE_DATA_ANNUAL_TARGET_CALCULATOR_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


DROP TABLE IF EXISTS `rm_tree_template_node_data_annual_target_calculator`;
CREATE TABLE `fasp`.`rm_tree_template_node_data_annual_target_calculator` (
  `NODE_DATA_ANNUAL_TARGET_CALCULATOR_ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `NODE_DATA_ID` INT UNSIGNED NOT NULL,
  `CALCULATOR_FIRST_MONTH` VARCHAR(7) NOT NULL,
  `CALCULATOR_YEARS_OF_TARGET` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`NODE_DATA_ANNUAL_TARGET_CALCULATOR_ID`),
  INDEX `fk_rm_ttnd_annual_target_calculator_idx` (`NODE_DATA_ID` ASC) ,
  CONSTRAINT `fk_rm_ttnd_annual_target_calculator_nodeDataId`
    FOREIGN KEY (`NODE_DATA_ID`)
    REFERENCES `fasp`.`rm_tree_template_node_data` (`NODE_DATA_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

DROP TABLE IF EXISTS `rm_tree_template_node_data_annual_target_calculator_data`;
CREATE TABLE `fasp`.`rm_tree_template_node_data_annual_target_calculator_data` (
  `NODE_DATA_ANNUAL_TARGET_CALCULATOR_DATA_ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `NODE_DATA_ANNUAL_TARGET_CALCULATOR_ID` INT UNSIGNED NOT NULL,
  `ACTUAL_OR_TARGET_VALUE` int unsigned NOT NULL,
  PRIMARY KEY (`NODE_DATA_ANNUAL_TARGET_CALCULATOR_DATA_ID`),
  INDEX `fk_rm_ttnd_annual_target_calculator_data_idx` (`NODE_DATA_ANNUAL_TARGET_CALCULATOR_ID` ASC) ,
  CONSTRAINT `fk_rm_ttnd_annual_target_calculator_annualTargetCalculatorId`
    FOREIGN KEY (`NODE_DATA_ANNUAL_TARGET_CALCULATOR_ID`)
    REFERENCES `fasp`.`rm_tree_template_node_data_annual_target_calculator` (`NODE_DATA_ANNUAL_TARGET_CALCULATOR_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
ALTER TABLE `fasp`.`rm_forecast_tree_node_data_annual_target_calculator` 
ADD COLUMN `CREATED_BY` INT(10) UNSIGNED NULL AFTER `CALCULATOR_YEARS_OF_TARGET`,
ADD COLUMN `CREATED_DATE` VARCHAR(45) NULL AFTER `CREATED_BY`,
ADD COLUMN `LAST_MODIFIED_BY` INT(10) UNSIGNED NULL AFTER `CREATED_DATE`,
ADD COLUMN `LAST_MODIFIED_DATE` VARCHAR(45) NULL AFTER `LAST_MODIFIED_BY`,
ADD COLUMN `ACTIVE` TINYINT(1) UNSIGNED NULL AFTER `LAST_MODIFIED_DATE`;

ALTER TABLE `fasp`.`rm_forecast_tree_node_data_annual_target_calculator` 
CHANGE COLUMN `ACTIVE` `ACTIVE` TINYINT(1) UNSIGNED NULL DEFAULT 1 ;

ALTER TABLE `fasp`.`rm_tree_template_node_data_annual_target_calculator` 
ADD COLUMN `CREATED_BY` INT(10) UNSIGNED NULL AFTER `CALCULATOR_YEARS_OF_TARGET`,
ADD COLUMN `CREATED_DATE` VARCHAR(45) NULL AFTER `CREATED_BY`,
ADD COLUMN `LAST_MODIFIED_BY` INT(10) UNSIGNED NULL AFTER `CREATED_DATE`,
ADD COLUMN `LAST_MODIFIED_DATE` VARCHAR(45) NULL AFTER `LAST_MODIFIED_BY`,
ADD COLUMN `ACTIVE` TINYINT(1) UNSIGNED NULL AFTER `LAST_MODIFIED_DATE`;

ALTER TABLE `fasp`.`rm_tree_template_node_data_annual_target_calculator` 
CHANGE COLUMN `ACTIVE` `ACTIVE` TINYINT(1) UNSIGNED NULL DEFAULT 1 ;

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.tree.firstMonthOfTarget','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'First Month of Target');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Premier mois de cible');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Primeiro Mês da Meta');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Primer mes del objetivo');-- sp

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.tree.annualTargetLabel','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'This calculator is reaching annual targets (sum across 12 months) and is generally appropriate for products are discrete, rather than continuous.  QAT uses your targets and the change between your targets to generate a monthly % change. Using this calculator provides a dynamic rather than stairstep forecast. Note that will always be minor differences in the final annual total and your target. However, the difference will be small if your targets change at a similar rate from year to year. See show guidance for more detail on the calculation.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Ce calculateur atteint les objectifs annuels (somme sur 12 mois) et est généralement approprié pour les produits discrets plutôt que continus. QAT utilise vos objectifs et le changement entre vos objectifs pour générer un % de changement mensuel. L`utilisation de ce calculateur fournit une prévision dynamique plutôt qu`en escalier. Notez qu`il y aura toujours des différences mineures entre le total annuel final et votre objectif. Cependant, la différence sera minime si vos objectifs changent à un rythme similaire d`une année à l`autre. Voir les instructions du spectacle pour plus de détails sur le calcul.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Esta calculadora está atingindo metas anuais (soma em 12 meses) e geralmente é apropriada para produtos discretos, em vez de contínuos. QAT usa seus alvos e a mudança entre seus alvos para gerar uma % de mudança mensal. O uso desta calculadora fornece uma previsão dinâmica em vez de escalonada. Observe que sempre haverá pequenas diferenças no total anual final e na sua meta. No entanto, a diferença será pequena se suas metas mudarem em uma taxa semelhante de ano para ano. Consulte mostrar orientação para obter mais detalhes sobre o cálculo.');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Esta calculadora está alcanzando objetivos anuales (suma de 12 meses) y, en general, es adecuada para productos discretos, en lugar de continuos. QAT utiliza sus objetivos y el cambio entre sus objetivos para generar un cambio porcentual mensual. El uso de esta calculadora proporciona un pronóstico dinámico en lugar de escalonado. Tenga en cuenta que siempre habrá pequeñas diferencias en el total anual final y su objetivo. Sin embargo, la diferencia será pequeña si sus objetivos cambian a un ritmo similar de un año a otro. Consulte Mostrar orientación para obtener más detalles sobre el cálculo.');-- sp

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.tree.targetYears','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'How many years of targets?');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Combien d`années d`objectifs ?');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Quantos anos de metas?');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'¿Cuántos años de objetivos?');-- sp

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.tree.actualOrTarget','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Actual / Target');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Réel / Cible');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Real / Alvo');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Real/Objetivo');-- sp

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.tree.annualChangePer','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Annual Change (%)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Variation annuelle (%)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Variação anual (%)');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cambio Anual (%)');-- sp

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.tree.calculatedTotal','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Calculated Total');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Total calculé');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Total Calculado');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Total calculada');-- sp

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.tree.differenceTargetVsCalculatedNumber','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Difference (Target vs Calculated, #)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Différence (cible vs calculée, #)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Diferença (Meta x Calculada, #)');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Diferencia (Objetivo vs Calculado, #)');-- sp

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.tree.differenceTargetVsCalculatedPer','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Difference (Target vs Calculated, %)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Différence (cible vs calculée, %)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Diferença (Meta x Calculada, %)');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Diferencia (objetivo frente a calculado, %)');-- sp

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.tooltip.actualOrTarget','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Sum of 12 months. You must provide the total for one year before the first target, and 1 year after after the last target.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Somme de 12 mois. Vous devez fournir le total pour un an avant le premier objectif et 1 an après le dernier objectif.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Soma de 12 meses. Você deve fornecer o total de um ano antes da primeira meta e 1 ano após a última meta.');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Suma de 12 meses. Debe proporcionar el total de un año antes del primer objetivo y 1 año después del último objetivo.');-- sp

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.tooltip.annualChangePer','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Change from previous year. Annual Change (%) = (Annual Target - Previous Year Actual or Target) / (Previous Year Actual or Target)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Changement par rapport à l`année précédente. Variation annuelle (%) = (Cible annuelle - Réel ou cible de l`année précédente) / (Réel ou cible de l`année précédente)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Alteração em relação ao ano anterior. Mudança anual (%) = (Meta anual - Real ou meta do ano anterior) / (Real ou meta do ano anterior)');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cambio respecto al año anterior. Cambio anual (%) = (objetivo anual - objetivo o real del año anterior) / (objetivo o real del año anterior)');-- sp

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.tooltip.diffTargetVsCalculatedNo','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Difference = Calculated Total - Target. This difference will be large if the annual change varies significantly between years');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Différence = Total calculé - Cible. Cette différence sera importante si la variation annuelle varie considérablement d`une année à l`autre');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Diferença = Total Calculado - Alvo. Essa diferença será grande se a variação anual variar significativamente entre os anos');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Diferencia = Total calculado - Objetivo. Esta diferencia será grande si el cambio anual varía significativamente entre años.');-- sp

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.tooltip.diffTargetVsCalculatedPer','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'(Calculated Total - Target) / Target. This difference will be large if the annual change varies significantly between years');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'(Total calculé - Cible) / Cible. Cette différence sera importante si la variation annuelle varie considérablement d`une année à l`autre');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'(Total Calculado - Alvo) / Alvo. Essa diferença será grande se a variação anual variar significativamente entre os anos');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'(Total calculado - Objetivo) / Objetivo. Esta diferencia será grande si el cambio anual varía significativamente entre años.');-- sp

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.modelingCalculator.confirmAlert','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Do you want to replace the existing table ?');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Vous souhaitez remplacer la table existante ?');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Deseja substituir a tabela existente?');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'¿Quieres reemplazar la mesa existente?');-- sp    
