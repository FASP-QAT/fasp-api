/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  altius
 * Created: 30-Jun-2023
 */

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.treeTemplate.createTreeFromTemplate','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Use this template');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Utilisez ce modèle');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Usa esta plantilla');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Use este modelo');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.treeTemplate.monthsInPastTooltip','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Choose the number of past months available in the Month dropdown for nodes in this tree template. Months will be negative, relative to the forecast start month. (e.g. if you select 2, you will see Month -1 and Month -2 available, which are the 2 months immediately before the forecast period.) When the template is used, actual months will be shown based on the user-selected forecast start month.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Choisissez le nombre de mois passés disponibles dans la liste déroulante Mois pour les nœuds de ce modèle d`arborescence. Les mois seront négatifs, par rapport au mois de début prévu. (par exemple, si vous sélectionnez 2, vous verrez Mois -1 et Mois -2 disponibles, qui sont les 2 mois précédant immédiatement la période de prévision.) Lorsque le modèle est utilisé, les mois réels seront affichés en fonction du début de prévision sélectionné par l`utilisateur. mois.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Elija la cantidad de meses anteriores disponibles en el menú desplegable Mes para nodos en esta plantilla de árbol. Los meses serán negativos, en relación con el mes de inicio del pronóstico. (por ejemplo, si selecciona 2, verá el Mes -1 y el Mes -2 disponibles, que son los 2 meses inmediatamente anteriores al período de pronóstico). Cuando se usa la plantilla, los meses reales se mostrarán según el inicio del pronóstico seleccionado por el usuario. mes.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Escolha o número de meses anteriores disponíveis no menu suspenso Mês para nós neste modelo de árvore. Os meses serão negativos em relação ao mês de início da previsão. (por exemplo, se você selecionar 2, verá Mês -1 e Mês -2 disponíveis, que são os 2 meses imediatamente anteriores ao período de previsão.) Quando o modelo for usado, os meses reais serão mostrados com base no início da previsão selecionado pelo usuário mês.');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.treeTemplate.monthsInFutureTooltip','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Choose the number of future months available in the Month dropdown for nodes in this tree template. Months will be positive, relative to the forecast start month. (e.g. if you select 2, you will see Month 1 and Month 2 available, which are first 2 months of forecast period.) When the template is used, actual months will be shown based on the user-selected forecast start month.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Choisissez le nombre de mois futurs disponibles dans la liste déroulante Mois pour les nœuds de ce modèle d`arborescence. Les mois seront positifs, par rapport au mois de début prévu. (par exemple, si vous sélectionnez 2, vous verrez Mois 1 et Mois 2 disponibles, qui sont les 2 premiers mois de la période de prévision.) Lorsque le modèle est utilisé, les mois réels seront affichés en fonction du mois de début de prévision sélectionné par l`utilisateur.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Elija la cantidad de meses futuros disponibles en el menú desplegable Mes para nodos en esta plantilla de árbol. Los meses serán positivos, en relación con el mes de inicio previsto. (por ejemplo, si selecciona 2, verá el Mes 1 y el Mes 2 disponibles, que son los primeros 2 meses del período de pronóstico). Cuando se usa la plantilla, los meses reales se mostrarán según el mes de inicio del pronóstico seleccionado por el usuario.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Escolha o número de meses futuros disponíveis no menu suspenso Mês para nós neste modelo de árvore. Os meses serão positivos, em relação ao mês de início da previsão. (por exemplo, se você selecionar 2, verá Mês 1 e Mês 2 disponíveis, que são os primeiros 2 meses do período de previsão.) Quando o modelo é usado, os meses reais serão mostrados com base no mês de início da previsão selecionado pelo usuário.');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.treeTemplate.listTreeTemplateTooltip','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Left click on any tree template to preview. Right click to create tree from template (only available for templates starting from aggregation or number nodes).');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Faites un clic gauche sur n`importe quel modèle d`arbre pour prévisualiser. Faites un clic droit pour créer une arborescence à partir d`un modèle (uniquement disponible pour les modèles commençant par des nœuds d`agrégation ou de nombre).');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Haga clic izquierdo en cualquier plantilla de árbol para obtener una vista previa. Haga clic con el botón derecho para crear un árbol a partir de una plantilla (solo disponible para plantillas a partir de nodos de agregación o numéricos).');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Clique com o botão esquerdo em qualquer modelo de árvore para visualizar. Clique com o botão direito para criar a árvore a partir do modelo (disponível apenas para modelos a partir de agregação ou nós numéricos).');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.treeTemplate.createTreeTemplateTooltip','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Create a tree first, then add this template to a node.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Créez d`abord un arbre, puis ajoutez ce modèle à un nœud.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Primero cree un árbol, luego agregue esta plantilla a un nodo.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Crie uma árvore primeiro e, em seguida, adicione este modelo a um nó.');-- pr