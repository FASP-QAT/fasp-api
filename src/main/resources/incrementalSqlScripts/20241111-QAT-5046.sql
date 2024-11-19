/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  altius
 * Created: 11-Nov-2024
 */

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.modelingCalculator.targetBeyondForecast','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'You have provided targets beyond the forecast period. QAT will calculate the modeling according to your inputs, but note that the display (build tree, node graph, node show data table) is limited by the forecast period. If you wish to see more months, please extend your forecast period in Program Management > Version Settings.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Vous avez fourni des objectifs au-delà de la période de prévision. QAT calculera la modélisation en fonction de vos entrées, mais notez que l'affichage (arbre de construction, graphique de nœuds, tableau de données d'affichage de nœuds) est limité par la période de prévision. Si vous souhaitez voir plus de mois, veuillez prolonger votre période de prévision dans Gestion du programme > Paramètres de version.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ha proporcionado objetivos más allá del período de pronóstico. QAT calculará el modelado de acuerdo con sus entradas, pero tenga en cuenta que la visualización (árbol de compilación, gráfico de nodos, tabla de datos de presentación de nodos) está limitada por el período de pronóstico. Si desea ver más meses, extienda el período de pronóstico en Administración de programas > Configuración de versión.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Você forneceu metas além do período de previsão. O QAT calculará a modelagem de acordo com suas entradas, mas observe que a exibição (árvore de construção, gráfico de nós, tabela de dados de exibição de nós) é limitada pelo período de previsão. Se desejar ver mais meses, estenda seu período de previsão em Gerenciamento de programas > Configurações de versão.');-- pr