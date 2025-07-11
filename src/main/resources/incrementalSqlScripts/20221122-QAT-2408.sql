/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  altius
 * Created: 11-Nov-2022
 */

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.autoCalculate','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'If QAT is slow while editing your tree, uncheck the auto-calculate box to stop calculations. Note that all calculated numbers may be wrong. To update the calculations, either re-check the ‘auto-calculate’ checkbox, or click the recalculate icon.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Si QAT est lent lors de la modification de votre arbre, décochez la case de calcul automatique pour arrêter les calculs. Notez que tous les nombres calculés peuvent être erronés. Pour mettre à jour les calculs, cochez à nouveau la case `Calcul automatique` ou cliquez sur l`icône de recalcul.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Si QAT es lento mientras edita su árbol, desmarque la casilla de cálculo automático para detener los cálculos. Tenga en cuenta que todos los números calculados pueden estar equivocados. Para actualizar los cálculos, vuelva a marcar la casilla de verificación `auto-calcular` o haga clic en el icono de recalcular.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Se o QAT estiver lento ao editar sua árvore, desmarque a caixa de cálculo automático para interromper os cálculos. Observe que todos os números calculados podem estar errados. Para atualizar os cálculos, marque novamente a caixa de seleção `calcular automaticamente` ou clique no ícone de recalcular.');-- pr