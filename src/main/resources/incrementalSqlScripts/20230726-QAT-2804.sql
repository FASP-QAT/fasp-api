/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  altius
 * Created: 26-Jul-2023
 */
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.shipment.selectValidBudget','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Please select valid budget');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Veuillez sélectionner un budget valide');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Seleccione un presupuesto válido');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Selecione um orçamento válido');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.budget.budgetNoteForCommitingLocalVersion','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'This report uses the latest versions of supply plans. Please commit local supply plans to see shipments tagged to budgets reflected here.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Ce rapport utilise les dernières versions des plans d`approvisionnement. Veuillez valider les plans d`approvisionnement locaux pour voir les expéditions étiquetées aux budgets reflétés ici.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Este informe utiliza las últimas versiones de los planes de suministro. Confirme los planes de suministro locales para ver los envíos etiquetados en los presupuestos reflejados aquí.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Este relatório usa as versões mais recentes dos planos de fornecimento. Confirme os planos de fornecimento locais para ver as remessas marcadas para orçamentos refletidas aqui.');-- pr