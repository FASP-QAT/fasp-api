/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  altius
 * Created: 23-Feb-2023
 */

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText1','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Suggested Shipments - “Plan by MOS”');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Envois suggérés - Planifier par MOS');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Envíos sugeridos: Plan by MOS');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Remessas sugeridas - “Planeje por MOS”');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText2','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Note: Below is the logic for planning units that are planned by months of stock (MOS). Update the “plan by” setting in “');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Remarque : Vous trouverez ci-dessous la logique de planification des unités planifiées par mois de stock (MOS). Mettez à jour le paramètre planifier par dans ');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Nota: A continuación se muestra la lógica para las unidades de planificación planificadas por meses de existencias (MOS). Actualice la configuración planificar por en ');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Obs.: Segue abaixo a lógica de planejamento das unidades que são planejadas por meses de estoque (MOS). Atualize a configuração “planejar até” em “');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText3','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'update planning unit');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'mettre à jour l`unité de planification');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'actualización de la unidad de planificación');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'unidade de planejamento de atualização');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText4','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'” screen.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,' filtrer.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,' pantalla.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,' tela.');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText5','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Where Month N is the month QAT is calculating for:');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Où Mois N est le mois que QAT calcule pour :');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Donde Month N es el mes para el que se calcula QAT:');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Onde Mês N é o mês QAT está calculando para:');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText6','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'1. If AMC = 0 or N/A for Month N, no suggested shipment');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'1. Si AMC = 0 ou N/A pour le mois N, aucune expédition suggérée');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'1. Si AMC = 0 o N/A para el Mes N, no se sugiere envío');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'1. Se AMC = 0 ou N/A para o Mês N, nenhuma remessa sugerida');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText7','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'2. If Month N is stocked out (Ending Balance = 0), QAT will always suggest a shipment');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'2. Si le mois N est en rupture de stock (solde final = 0), QAT proposera toujours une expédition');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'2. Si el Mes N está agotado (Saldo final = 0), QAT siempre sugerirá un envío');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'2. Se o mês N estiver esgotado (saldo final = 0), o QAT sempre sugerirá uma remessa');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText8','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'If both of next 2 months (N+1, N+2) are');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Si les deux mois suivants (N+1, N+2) sont');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Si los dos próximos 2 meses (N+1, N+2) son');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Se ambos os próximos 2 meses (N+1, N+2) forem');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText9','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Min MOS, suggested shipment will bring month N to');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Min MOS, l`expédition suggérée amènera le mois N à');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Min MOS, el envío sugerido traerá el mes N a');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Min MOS, a remessa sugerida trará o mês N para');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText10','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Max MOS');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'MOS maximale');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'MOS máx.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Max MOS');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText11','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'. Suggested qty = (Max MOS for N) * (AMC for N) – (Ending Balance for N) + (Unmet Demand for N)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'. Quantité suggérée = (Max MOS pour N) * (AMC pour N) – (Solde final pour N) + (Demande non satisfaite pour N)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'. Cantidad sugerida = (MOS máx. para N) * (AMC para N) – (Saldo final para N) + (Demanda insatisfecha para N)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'. Quantidade sugerida = (Mos máximo para N) * (AMC para N) – (Saldo final para N) + (Demanda não atendida para N)');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText12','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'If 1 or both of the next 2 months (N+1 or N+2) is');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Si 1 ou les 2 mois suivants (N+1 ou N+2) est');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Si 1 o ambos de los próximos 2 meses (N+1 o N+2) es');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Se 1 ou ambos os próximos 2 meses (N+1 ou N+2) forem');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText13','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Min MOS, suggested shipment will bring month N to');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Min MOS, l`expédition suggérée amènera le mois N à');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Min MOS, el envío sugerido traerá el mes N a');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Min MOS, a remessa sugerida trará o mês N para');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText14','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Min MOS');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'MOS minimale');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'MOS mín.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'MOS mínimo');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText15','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Suggested qty = (Min MOS for N) * (AMC for N) – (Ending Balance for N) + (Unmet Demand for N)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Quantité suggérée = (Min MOS pour N) * (AMC pour N) – (Solde final pour N) + (Demande non satisfaite pour N)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cantidad sugerida = (MOS mínimo para N) * (AMC para N) – (Saldo final para N) + (Demanda insatisfecha para N)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Quantidade sugerida = (Min MOS para N) * (AMC para N) – (Saldo final para N) + (Demanda não atendida para N)');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText16','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'3. Is product is understocked (MOS');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'3. Le produit est-il en sous-stock (MOS');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'3. ¿Hay escasez de existencias del producto (MOS');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'3. O produto está com falta de estoque (MOS');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText17','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Min MOS) for 3 straight months (Month N, N+1, N+2)?');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Min MOS) pendant 3 mois consécutifs (Mois N, N+1, N+2) ?');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Min MOS) durante 3 meses consecutivos (Mes N, N+1, N+2)?');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Min MOS) por 3 meses seguidos (Mês N, N+1, N+2)?');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText18','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'If yes, suggested shipment to bring Month N to');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Si oui, envoi suggéré pour apporter le mois N au');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'En caso afirmativo, envío sugerido para llevar el Mes N a');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Se sim, envio sugerido para trazer Mês N para');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText19','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Max MOS');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'MOS maximale');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'MOS máx.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Max MOS');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText20','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Suggested qty = (Max MOS for N) * (AMC for N) – (Ending Balance for N)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Quantité suggérée = (Max MOS pour N) * (AMC pour N) – (Solde final pour N)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cantidad sugerida = (MOS máx. para N) * (AMC para N) – (Saldo final para N)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Quantidade sugerida = (Máx. MOS para N) * (AMC para N) – (Saldo final para N)');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText21','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'If no, no suggested shipment');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Si non, aucune expédition suggérée');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Si no, no hay envío sugerido');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Se não, nenhuma remessa sugerida');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText22','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Example 1 (understocked)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Exemple 1 (sous-approvisionné)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ejemplo 1 (falta de existencias)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Exemplo 1 (sem estoque)');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText23','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Min MOS = 12; Reorder Interval = 3; Max MOS = 15');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'MOS minimale = 12 ; Intervalle de commande = 3 ; MOS maximum = 15');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'MOS mínimo = 12; Intervalo de pedido = 3; MOS máx. = 15');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'MOS mínimo = 12; Intervalo de Reordenar = 3; MOS máximo = 15');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText24','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Calculating for Month N = Jan 2022');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Calcul pour le mois N = janvier 2022');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cálculo para el mes N = enero de 2022');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Calculando para o mês N = janeiro de 2022');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText25','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'AMC for N (Jan 2022) = 3,000');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'AMC pour N (janvier 2022) = 3 000');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'AMC para N (enero de 2022) = 3000');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'AMC para N (janeiro de 2022) = 3.000');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText26','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Ending Balance for N (Jan 2022) = 30,000');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Solde final pour N (janvier 2022) = 30 000');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Saldo final para N (enero de 2022) = 30 000');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Saldo final para N (janeiro de 2022) = 30.000');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText27','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Unmet Demand for N = 0');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Demande non satisfaite pour N = 0');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Demanda insatisfecha para N = 0');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Demanda não atendida para N = 0');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText28','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'MOS for N (Jan 2022) = 10; MOS for N+1 (Feb 2022) = 9.4; MOS for N+2 (March 2022) = 8.4');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'MOS pour N (janvier 2022) = 10 ; MOS pour N+1 (février 2022) = 9,4 ; MOS pour N+2 (mars 2022) = 8,4');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'MOS para N (enero de 2022) = 10; MOS para N+1 (febrero de 2022) = 9,4; MOS para N+2 (marzo de 2022) = 8,4');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'MOS para N (janeiro de 2022) = 10; MOS para N+1 (fevereiro de 2022) = 9,4; MOS para N+2 (março de 2022) = 8,4');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText29','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Suggest a shipment?');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Proposer un envoi ?');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'¿Sugerir un envío?');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Sugerir uma remessa?');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText30','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Step 1: Jan 2022 AMC is not 0 or N/A');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Étape 1 : L`AMC de janvier 2022 n`est pas 0 ou N/A');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Paso 1: Ene 2022 AMC no es 0 o N/A');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Passo 1: Jan 2022 AMC não é 0 ou N/A');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText31','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Step 2: Jan 2022 Ending Balance is not 0 (not stocked out)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Étape 2 : Le solde de fin de janvier 2022 n`est pas égal à 0 (pas de stock en rupture de stock)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Paso 2: el saldo final de enero de 2022 no es 0 (no está agotado)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Etapa 2: o saldo final de janeiro de 2022 não é 0 (sem estoque)');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText32','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Step 3: Product is understocked for 3 straight months – Jan 2022, Feb 2022, March 2022 all have MOS (10, 9.4, and 8.4) is less than Min MOS (12).');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Étape 3 : Le produit est en sous-stock pendant 3 mois consécutifs - janvier 2022, février 2022, mars 2022 ont tous un MOS (10, 9,4 et 8,4) inférieur au MOS minimum (12).');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Paso 3: el producto no tiene suficientes existencias durante 3 meses consecutivos: enero de 2022, febrero de 2022, marzo de 2022, todos tienen un MOS (10, 9,4 y 8,4) inferior al MOS mínimo (12).');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Etapa 3: o produto está com falta de estoque por 3 meses consecutivos - janeiro de 2022, fevereiro de 2022, março de 2022, todos com MOS (10, 9,4 e 8,4) menor que o mínimo de MOS (12).');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText33','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Therefore, QAT will suggest an order to bring Month N to the');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Par conséquent, QAT proposera une ordonnance pour amener le mois N au');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Por lo tanto, QAT sugerirá una orden para llevar el Mes N al');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Portanto, QAT irá sugerir uma ordem para trazer o Mês N para o');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText34','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Max MOS');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'MOS maximale');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'MOS máx.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Max MOS');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText35','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'= (Max MOS for N) * (AMC for N) – (Ending Balance for N)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'= (Max MOS pour N) * (AMC pour N) - (Solde final pour N)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'=(max mos for n) * (amc for n) – (Ending Balance for N)”');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'=(max mos for n) * (amc for n) – (Ending Balance for N)”');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText36','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Example 2 (stocked out):');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Exemple 2 (en rupture de stock) :');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ejemplo 2 (agotado):');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Exemplo 2 (esgotado):');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText37','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Min MOS = 12; Reorder Interval = 3; Max MOS = 15');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'MOS minimale = 12 ; Intervalle de commande = 3 ; MOS maximum = 15');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'MOS mínimo = 12; Intervalo de pedido = 3; MOS máx. = 15');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'MOS mínimo = 12; Intervalo de Reordenar = 3; MOS máximo = 15');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText38','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Calculating for Month N = Jan 2022');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Calcul pour le mois N = janvier 2022');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cálculo para el mes N = enero de 2022');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Calculando para o mês N = janeiro de 2022');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText39','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'AMC for N (Jan 2022) = 3,000');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'AMC pour N (janvier 2022) = 3 000');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'AMC para N (enero de 2022) = 3000');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'AMC para N (janeiro de 2022) = 3.000');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText40','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Ending Balance for N (Jan 2022) = 0');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Solde final pour N (janvier 2022) = 0');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Saldo final para N (enero de 2022) = 0');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Saldo final para N (janeiro de 2022) = 0');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText41','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Unmet Demand for N = 0');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Demande non satisfaite pour N = 0');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Demanda insatisfecha para N = 0');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Demanda não atendida para N = 0');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText42','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'MOS for N (Jan 2022) = 0; MOS for N+1 (Feb 2022) = 0; MOS for N+2 (March 2022) = 13');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'MOS pour N (janvier 2022) = 0 ; MOS pour N+1 (février 2022) = 0 ; MOS pour N+2 (mars 2022) = 13');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'MOS para N (enero de 2022) = 0; MOS para N+1 (febrero de 2022) = 0; MOS para N+2 (marzo de 2022) = 13');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'MOS para N (janeiro de 2022) = 0; MOS para N+1 (fevereiro de 2022) = 0; MOS para N+2 (março de 2022) = 13');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText43','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Suggest a shipment? :');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Proposer un envoi ? :');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'¿Sugerir un envío? :');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Sugerir uma remessa? :');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText44','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Step 1: Jan 2022 AMC is not 0 or N/A');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Étape 1 : L`AMC de janvier 2022 n`est pas 0 ou N/A');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Paso 1: Ene 2022 AMC no es 0 o N/A');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Passo 1: Jan 2022 AMC não é 0 ou N/A');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText45','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Step 2: Jan 2022 Ending Balance is 0');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Étape 2 : Le solde de fin de janvier 2022 est de 0');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Paso 2: el saldo final de enero de 2022 es 0');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Etapa 2: o saldo final de janeiro de 2022 é 0');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText46','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'1 of next 2 months is');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'1 des 2 prochains mois est');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'1 de los próximos 2 meses es');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'1 dos próximos 2 meses é');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText47','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Min MOS (Jan 2022, Feb 2022 are stocked out and March 2022 is stocked-to-plan).');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Min MOS (janvier 2022, février 2022 sont en rupture de stock et mars 2022 est stocké selon le plan).');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Min MOS (enero de 2022, febrero de 2022 están agotados y marzo de 2022 está lleno según lo planeado).');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Min MOS (janeiro de 2022, fevereiro de 2022 estão esgotados e março de 2022 estão estocados de acordo com o planejado).');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText48','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Therefore, QAT will suggest a shipment to bring Month N to Min MOS.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Par conséquent, QAT proposera une expédition pour amener le mois N à Min MOS.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Por lo tanto, QAT sugerirá un envío para llevar el Mes N al Min MOS.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Portanto, QAT irá sugerir uma remessa para trazer Mês N para Min MOS.');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText49','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'= (Min MOS for N) * (AMC for N) – (Ending Balance for N) + (Unmet Demand for N)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'= (Min MOS pour N) * (AMC pour N) - (Solde final pour N) + (Demande non satisfaite pour N)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'=(min mos for n) * (amc for n) – (Ending Balance for N) + (Unmet Demand for N)”');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'=(min mos for n) * (amc for n) – (Ending Balance for N) + (Unmet Demand for N)”');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText50','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Suggested Shipments - “Plan by Quantity”');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Envois suggérés - Planifier par quantité');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Envíos sugeridos: Planificación por cantidad');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Remessas sugeridas - “Planejar por quantidade”');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText51','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Note: Below is the logic for planning units that are planned by quantity. Update the “plan by” setting in “');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Remarque : Vous trouverez ci-dessous la logique des unités de planification qui sont planifiées par quantité. Mettez à jour le paramètre planifier par dans ');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Nota: A continuación se muestra la lógica para las unidades de planificación planificadas por cantidad. Actualice la configuración planificar por en ');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Observação: Abaixo está a lógica para unidades de planejamento que são planejadas por quantidade. Atualize a configuração “planejar até” em “');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText52','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Where Month N is the month QAT is calculating for, and X = Distribution Lead Time:');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Où Mois N est le mois pour lequel le QAT est calculé, et X = Délai de distribution :');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Donde Mes N es el mes para el que se calcula QAT y X = Plazo de entrega de distribución:');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Onde Mês N é o mês para o qual o QAT está sendo calculado e X = Tempo de entrega de distribuição:');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText53','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'1. If AMC = 0 or N/A for Month N, no suggested shipment');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'1. Si AMC = 0 ou N/A pour le mois N, aucune expédition suggérée');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'1. Si AMC = 0 o N/A para el Mes N, no se sugiere envío');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'1. Se AMC = 0 ou N/A para o Mês N, nenhuma remessa sugerida');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText54','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'2. If Month N is stocked out (Ending Balance = 0), QAT will always suggest a shipment');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'2. Si le mois N est en rupture de stock (solde final = 0), QAT proposera toujours une expédition');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'2. Si el Mes N está agotado (Saldo final = 0), QAT siempre sugerirá un envío');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'2. Se o mês N estiver esgotado (saldo final = 0), o QAT sempre sugerirá uma remessa');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText55','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'If both of next 2 months (N+1, N+2) are');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Si les deux mois suivants (N+1, N+2) sont');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Si los dos próximos 2 meses (N+1, N+2) son');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Se ambos os próximos 2 meses (N+1, N+2) forem');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText56','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Min Qty, suggested shipment is the quantity that would bring Month N to');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Quantité minimale, l`expédition suggérée est la quantité qui amènerait le mois N à');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Min Qty, el envío sugerido es la cantidad que llevaría el Mes N a');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Quantidade mínima, remessa sugerida é a quantidade que traria o Mês N para');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText57','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Max Qty');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Qté maximale');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cantidad máxima');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Quantidade máxima');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText58','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Suggested Qty = (Max Qty for N) – (Ending Balance for N) + (Unmet Demand for N)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Qté suggérée = (Qté max pour N) – (Solde final pour N) + (Demande non satisfaite pour N)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cantidad sugerida = (Cantidad máxima para N) – (Saldo final para N) + (Demanda insatisfecha para N)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Quantidade sugerida = (Quantidade máxima para N) – (Saldo final para N) + (Demanda não atendida para N)');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText59','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'If one or both of the next 2 months (N+1 or N+2) is');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Si un ou les deux mois suivants (N+1 ou N+2) est');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Si uno o ambos de los próximos 2 meses (N+1 o N+2) es');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Se um ou ambos os próximos 2 meses (N+1 ou N+2) forem');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText60','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Min Qty, suggested shipment is the quantity that would bring Month N to');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Quantité minimale, l`expédition suggérée est la quantité qui amènerait le mois N à');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Min Qty, el envío sugerido es la cantidad que llevaría el Mes N a');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Quantidade mínima, remessa sugerida é a quantidade que traria o Mês N para');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText61','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Min Qty');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Qté minimale');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cantidad mínima');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Quantidade mínima');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText62','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Suggested Qty = (Min Qty) – (Ending Balance for N) + (Unmet Demand for N)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Quantité suggérée = (Qté minimale) - (Solde final pour N) + (Demande non satisfaite pour N)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cantidad sugerida = (Cantidad mínima) – (Saldo final para N) + (Demanda insatisfecha para N)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Quantidade sugerida = (Quantidade mínima) – (Saldo final para N) + (Demanda não atendida para N)');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText63','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'3. Is product understocked (Ending Balance');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'3. Le produit est-il en sous-stock (solde final');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'3. ¿Hay escasez de existencias del producto (saldo final');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'3. O produto está com estoque insuficiente (saldo final');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText64','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Min Qty) for 3 straight months (Month N, N+1, N+2)?');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Qté Min) pendant 3 mois consécutifs (Mois N, N+1, N+2) ?');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cantidad mínima) durante 3 meses consecutivos (Mes N, N+1, N+2)?');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Min Qty) por 3 meses seguidos (Mês N, N+1, N+2)?');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText65','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'If yes, suggested shipment is the quantity that would bring Month N to');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Si oui, l`expédition suggérée est la quantité qui amènerait le mois N à');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'En caso afirmativo, el envío sugerido es la cantidad que llevaría el Mes N a');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Se sim, a remessa sugerida é a quantidade que traria o Mês N para');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText66','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Max Qty');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Qté maximale');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cantidad máxima');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Quantidade máxima');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText67','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Suggested Qty = (Max Qty for N) – (Ending Balance for N)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Qté suggérée = (Qté max pour N) – (Solde final pour N)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cantidad sugerida = (Cantidad máxima para N) – (Saldo final para N)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Quantidade sugerida = (Quantidade máxima para N) – (Saldo final para N)');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText68','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'If no, no suggested shipment');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Si non, aucune expédition suggérée');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Si no, no hay envío sugerido');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Se não, nenhuma remessa sugerida');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText69','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'QAT puts the suggested quantity in Month N-X. In other words, X months before Month N, where X is the Distribution Lead Time.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'QAT place la quantité suggérée dans Month NX. En d`autres termes, X mois avant le mois N, où X est le délai de distribution.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'QAT pone la cantidad sugerida en Mes NX. En otras palabras, X meses antes del Mes N, donde X es el Plazo de entrega de distribución.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'QAT coloca a quantidade sugerida no Mês NX. Em outras palavras, X meses antes do Mês N, onde X é o Lead Time de Distribuição.');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText70','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Example 1 (understocked):');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Exemple 1 (sous-approvisionné) :');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ejemplo 1 (falta de existencias):');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Exemplo 1 (sem estoque):');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText71','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Min Qty = 1,000; Reorder Interval = 6 months; Distribution lead time (X) = 1 month');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Qté min = 1 000 ; Intervalle de commande = 6 mois ; Délai de distribution (X) = 1 mois');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cantidad mínima = 1000; Intervalo de pedido = 6 meses; Plazo de entrega de distribución (X) = 1 mes');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Quantidade mínima = 1.000; Intervalo de Novo Pedido = 6 meses; Prazo de entrega de distribuição (X) = 1 mês');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText72','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Calculating for Month N =Nov 2022');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Calcul pour le mois N =novembre 2022');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cálculo para el mes N = noviembre de 2022');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Calculando para o mês N = novembro de 2022');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText73','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'AMC for N (Nov 2022) = 100');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'AMC pour N (novembre 2022) = 100');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'AMC para N (noviembre de 2022) = 100');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'AMC para N (novembro de 2022) = 100');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText74','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Max Qty for N (Nov 2022) = 1,600');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Qté max pour N (novembre 2022) = 1 600');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cantidad máxima para N (noviembre de 2022) = 1600');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Quantidade máxima para N (novembro de 2022) = 1.600');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText75','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Unmet Demand for N (Nov 2022) = 0');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Demande non satisfaite de N (novembre 2022) = 0');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Demanda insatisfecha de N (noviembre de 2022) = 0');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Demanda não atendida por N (novembro de 2022) = 0');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText76','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Ending Balance for N (Nov 2022) = 900; for N + 1 (Dec 2022) = 800; for N + 2 (Jan 2023) = 700');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Solde final pour N (novembre 2022) = 900 ; pour N + 1 (décembre 2022) = 800 ; pour N + 2 (janvier 2023) = 700');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Saldo final para N (noviembre de 2022) = 900; para N + 1 (diciembre de 2022) = 800; para N + 2 (ene 2023) = 700');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Saldo final para N (novembro de 2022) = 900; para N + 1 (dezembro de 2022) = 800; para N + 2 (janeiro de 2023) = 700');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText77','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Suggest a shipment? :');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Proposer un envoi ? :');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'¿Sugerir un envío? :');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Sugerir uma remessa? :');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText78','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Step 1: Nov 2022 AMC is not 0 or N/A');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Étape 1 : L`AMC de novembre 2022 n`est pas 0 ou N/A');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Paso 1: el AMC de noviembre de 2022 no es 0 o N/A');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Etapa 1: novembro de 2022 AMC não é 0 ou N/A');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText79','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Step 2: Nov 2022 Ending Balance is not 0');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Étape 2 : Le solde de fin de novembre 2022 n`est pas égal à 0');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Paso 2: el saldo final de noviembre de 2022 no es 0');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Etapa 2: o saldo final de novembro de 2022 não é 0');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText80','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Step 3: Product is understocked for 3 straight months – Nov 2022, Dec 2022, Jan 2023 all have Ending Balance (900, 800, 700) is less than Min Qty (1,000).');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Étape 3 : Le produit est en sous-stock pendant 3 mois consécutifs - novembre 2022, décembre 2022, janvier 2023 ont tous un solde final (900, 800, 700) inférieur à la quantité minimale (1 000).');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Paso 3: el producto no tiene suficientes existencias durante 3 meses consecutivos: noviembre de 2022, diciembre de 2022, enero de 2023, todos tienen un saldo final (900, 800, 700) inferior a la cantidad mínima (1000).');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Etapa 3: o produto está com falta de estoque por 3 meses consecutivos - novembro de 2022, dezembro de 2022, janeiro de 2023, todos com saldo final (900, 800, 700) menor que a quantidade mínima (1.000).');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText81','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Therefore, QAT will suggest a shipment in Month N-X (Nov 2022 – 1 = Oct 2022) to bring Month N (Nov 2022) to');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Par conséquent, QAT suggérera une expédition au mois NX (novembre 2022 - 1 = octobre 2022) pour amener le mois N (novembre 2022) à');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Por lo tanto, QAT sugerirá un envío en el mes NX (noviembre de 2022 - 1 = octubre de 2022) para llevar el mes N (noviembre de 2022) a');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Portanto, a QAT sugerirá uma remessa no mês NX (novembro de 2022 - 1 = outubro de 2022) para trazer o mês N (novembro de 2022) para');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText82','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Max Qty');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Qté maximale');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cantidad máxima');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Quantidade máxima');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText83','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'= (Max Qty for N) – (Ending Balance for N)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'= (Qté max pour N) – (Solde final pour N)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'=(max qty for n) – (Ending Balance for N)”');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'=(max qty for n) – (Ending Balance for N)”');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText84','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'= (Max Qty for Nov 22) – (Ending Balance for Nov 22)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'= (Qté max pour le 22 novembre) – (Solde final pour le 22 novembre)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'“=(max qty for nov 22) – (Ending Balance for Nov 22)”');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'=(max qty for nov 22) – (Ending Balance for Nov 22)”');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText85','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'= 700 suggested in Oct 2022');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'= 700 suggérés en octobre 2022');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'=700 suggested in oct 2022”');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'=700 suggested in oct 2022”');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText86','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Example 2 (stocked out, but future months above Min Qty):');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Exemple 2 (en rupture de stock, mais mois futurs supérieurs à la quantité minimale) :');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ejemplo 2 (agotado, pero meses futuros por encima de Min Qty):');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Exemplo 2 (estocado, mas meses futuros acima da quantidade mínima):');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText87','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Min Qty = 1,000; Reorder Interval = 6 months; Distribution Lead Time (X) = 1 month');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Qté min = 1 000 ; Intervalle de commande = 6 mois ; Délai de distribution (X) = 1 mois');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cantidad mínima = 1000; Intervalo de pedido = 6 meses; Plazo de entrega de distribución (X) = 1 mes');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Quantidade mínima = 1.000; Intervalo de Novo Pedido = 6 meses; Tempo de entrega de distribuição (X) = 1 mês');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText88','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Calculating for Month N =Aug 2023');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Calcul pour le mois N = août 2023');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cálculo para el mes N = agosto de 2023');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Calculando para o mês N = agosto de 2023');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText89','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'AMC for N (Aug 2023) = 103');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'AMC pour N (août 2023) = 103');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'AMC para N (agosto de 2023) = 103');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'AMC para N (agosto de 2023) = 103');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText90','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Max Qty for N (Nov 2022) = 1,600');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Qté max pour N (novembre 2022) = 1 600');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cantidad máxima para N (noviembre de 2022) = 1600');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Quantidade máxima para N (novembro de 2022) = 1.600');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText91','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Unmet Demand for N (Nov 2022) = 20');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Demande non satisfaite de N (novembre 2022) = 20');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Demanda insatisfecha de N (noviembre de 2022) = 20');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Demanda não atendida por N (novembro de 2022) = 20');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText92','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Ending Balance for N (Aug 2023) = 0; for N + 1 (Sep 2023) = 1,200; for N + 2 (Oct 2023) = 1,100');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Solde final pour N (août 2023) = 0 ; pour N + 1 (septembre 2023) = 1 200 ; pour N + 2 (oct 2023) = 1 100');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Saldo final para N (agosto de 2023) = 0; para N + 1 (septiembre de 2023) = 1200; para N + 2 (octubre de 2023) = 1100');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Saldo final para N (agosto de 2023) = 0; para N + 1 (setembro de 2023) = 1.200; para N + 2 (outubro de 2023) = 1.100');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText93','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Suggest a shipment? :');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Proposer un envoi ? :');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'¿Sugerir un envío? :');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Sugerir uma remessa? :');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText94','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Step 1: Aug 2023 AMC is not 0 or N/A');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Étape 1 : août 2023 AMC n`est pas 0 ou N/A');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Paso 1: agosto de 2023 AMC no es 0 o N/A');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Etapa 1: AMC de agosto de 2023 não é 0 ou N/A');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText95','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Step 2: Aug 2023 Ending Balance is 0');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Étape 2 : Le solde final d`août 2023 est de 0');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Paso 2: el saldo final de agosto de 2023 es 0');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Etapa 2: o saldo final de agosto de 2023 é 0');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText96','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'(N+1 and N+2)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'(N+1 et N+2)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'(N+1 y N+2)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'(N+1 e N+2)');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText97','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Min Qty. Both of the next two months (Sept 2023 and Oct 2023) have Ending Balance (1,200) and (1,100) are');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Qté min. Les deux prochains mois (septembre 2023 et octobre 2023) ont un solde de fin (1 200) et (1 100) sont');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cant. mín. Los dos próximos dos meses (septiembre de 2023 y octubre de 2023) tienen un saldo final (1200) y (1100) son');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Qtd. Mín. Ambos os próximos dois meses (setembro de 2023 e outubro de 2023) têm saldo final (1.200) e (1.100) são');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText98','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Min Qty (1,000)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Quantité minimale (1 000)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cantidad mínima (1,000)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Quantidade mínima (1.000)');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText99','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Therefore, QAT will suggest a shipment in Month N-X (Aug 2023 – 1 = Jul 2023) to bring Month N (Aug 2023) to');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Par conséquent, QAT suggérera une expédition au mois NX (août 2023 - 1 = juillet 2023) pour amener le mois N (août 2023) à');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Por lo tanto, QAT sugerirá un envío en el mes NX (agosto de 2023 - 1 = julio de 2023) para llevar el mes N (agosto de 2023) a');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Portanto, a QAT sugerirá uma remessa no mês NX (agosto de 2023 - 1 = julho de 2023) para trazer o mês N (agosto de 2023) para');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText100','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Min Qty');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Qté minimale');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cantidad mínima');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Quantidade mínima');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText101','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'= (Min Qty) – (Ending Balance for N) + (Unmet Demand for N)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'= (Quantité minimale) – (Solde final pour N) + (Demande non satisfaite pour N)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'=(min qty) – (Ending Balance for N) + (Unmet Demand for N)”');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'=(min qty) – (Ending Balance for N) + (Unmet Demand for N)”');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.formula.suggestedText102','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'= 1,020 in Jul 2023');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'= 1 020 en juil. 2023');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'=1,20 in jul 2023”');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'=1,20 in jul 2023”');-- pr