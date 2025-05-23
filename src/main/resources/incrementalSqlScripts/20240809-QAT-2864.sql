INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.recommendToChangeFreightCost','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Recommended to change the freight cost'); --en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Il est recommandé de modifier le coût du fret'); --fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Se recomienda cambiar el costo del flete'); --sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Recomendado para alterar o custo do frete'); --pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dataentry.autogeneratedFreight','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Auto-calculated Freight Cost'); --en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Coût de transport calculé automatiquement'); --fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Costo de flete calculado automáticamente'); --sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Custo de frete calculado automaticamente'); --pr

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Depending on the status in Auto-calculated Freight Cost column, this value is either auto-calculated (PU Cost * Freight %) based on Program/PU settings or manually entered'
where l.LABEL_CODE='static.shipmentTooltip.freightCost' and ll.LANGUAGE_ID=1;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='En fonction du statut dans la colonne Coût de fret calculé automatiquement, cette valeur est soit calculée automatiquement (Coût PU * % de fret) en fonction des paramètres du programme/PU, soit saisie manuellement'
where l.LABEL_CODE='static.shipmentTooltip.freightCost' and ll.LANGUAGE_ID=2;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Dependiendo del estado en la columna Costo de flete calculado automáticamente, este valor se calcula automáticamente (Costo de PU * % de flete) según la configuración del Programa/PU o se ingresa manualmente.'
where l.LABEL_CODE='static.shipmentTooltip.freightCost' and ll.LANGUAGE_ID=3;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Dependendo do status na coluna Custo de frete calculado automaticamente, esse valor é calculado automaticamente (Custo PU * Frete%) com base nas configurações do Programa/PU ou inserido manualmente'
where l.LABEL_CODE='static.shipmentTooltip.freightCost' and ll.LANGUAGE_ID=4;

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dataentry.autogeneratedTooltip','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'If checked, QAT will auto-calculate freight cost based on Program/PU settings. If unchecked, freight cost will need to be manually updated'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Si cette case est cochée, QAT calculera automatiquement le coût du fret en fonction des paramètres du programme/de l`unité de commande. Si cette case n`est pas cochée, le coût du fret devra être mis à jour manuellement'); --fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Si se marca, QAT calculará automáticamente el costo del flete en función de la configuración del programa o de la PU. Si no se marca, el costo del flete deberá actualizarse manualmente.'); --sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Se marcado, o QAT calculará automaticamente o custo do frete com base nas configurações do Programa/PU. Se desmarcado, o custo do frete precisará ser atualizado manualmente'); --pr