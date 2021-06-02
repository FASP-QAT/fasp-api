/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  altius
 * Created: 17-May-2021
 */

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.mt.showDetails','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Details');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Des détails');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Detalles');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Detalhes');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.mt.purposeOfEachScreen','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Purpose of Each Screen: ');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Objectif de chaque écran:');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Propósito de cada pantalla:');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Objetivo de cada tela:');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.mt.tab1DetailPurpose','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Use this screen to link QAT shipments with ERP orders for the first time. Users will see a list of QAT shipments, and when a user clicks on a single QAT shipment, they can then choose which ERP orders that QAT shipment should be linked with.  ');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Utilisez cet écran pour lier les envois QAT aux commandes ERP pour la première fois. Les utilisateurs verront une liste des envois QAT, et lorsquun utilisateur clique sur un seul envoi QAT, ils peuvent alors choisir les commandes ERP auxquelles lenvoi QAT doit être lié.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Utilice esta pantalla para vincular los envíos QAT con los pedidos de ERP por primera vez. Los usuarios verán una lista de envíos QAT y, cuando un usuario haga clic en un solo envío QAT, podrán elegir con qué pedidos de ERP deben vincularse los envíos QAT.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Use esta tela para vincular remessas QAT a pedidos ERP pela primeira vez. Os usuários verão uma lista de remessas QAT e, quando um usuário clicar em uma única remessa QAT, eles podem escolher a quais pedidos de ERP a remessa QAT deve ser vinculada.');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.mt.tab2DetailPurpose','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Users will see a list of currently linked shipments and they can update the conversion factor or notes of already linked shipments, add ERP lines to already linked QAT shipments, or de-link ERP lines from QAT shipment. ');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Les utilisateurs verront une liste des envois actuellement liés et pourront mettre à jour le facteur de conversion ou les notes des envois déjà liés, ajouter des lignes ERP aux envois QAT déjà liés ou dissocier les lignes ERP des envois QAT.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Los usuarios verán una lista de envíos actualmente vinculados y pueden actualizar el factor de conversión o notas de envíos ya vinculados, agregar líneas ERP a envíos QAT ya vinculados o desvincular líneas ERP del envío QAT.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Os usuários verão uma lista de remessas atualmente vinculadas e podem atualizar o fator de conversão ou notas de remessas já vinculadas, adicionar linhas ERP a remessas QAT já vinculadas ou desvincular linhas ERP da remessa QAT.');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.mt.tab3DetailPurpose','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Use this screen to link ERP orders to QAT for the first time. Users will see a list of ERP shipments, and when a user clicks on a single ERP shipment, they can then choose which QAT shipment that ERP shipment should be linked with.  For any orders that are in the ERP that are not already in QAT, you can create a new QAT shipment in this screen.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Utilisez cet écran pour lier les commandes ERP à QAT pour la première fois. Les utilisateurs verront une liste des envois ERP, et lorsquun utilisateur clique sur un envoi ERP unique, ils peuvent alors choisir à quel envoi QAT cet envoi ERP doit être lié. Pour toutes les commandes qui sont dans lERP qui ne sont pas déjà dans QAT, vous pouvez créer une nouvelle expédition QAT dans cet écran.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Utilice esta pantalla para vincular los pedidos de ERP a QAT por primera vez. Los usuarios verán una lista de envíos de ERP y, cuando un usuario haga clic en un solo envío de ERP, podrán elegir con qué envío de QAT se debe vincular ese envío de ERP. Para cualquier pedido que esté en el ERP que aún no esté en QAT, puede crear un nuevo envío QAT en esta pantalla.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Use esta tela para vincular pedidos ERP ao QAT pela primeira vez. Os usuários verão uma lista de remessas ERP e, quando um usuário clicar em uma única remessa ERP, eles podem escolher a qual remessa QAT essa remessa ERP deve ser vinculada. Para qualquer pedido que esteja no ERP que ainda não esteja no QAT, você pode criar uma nova remessa QAT nesta tela.');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.mt.reminders','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Reminders:');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Rappels');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Recordatorios');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Lembretes');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.mt.reminders1','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'If you make any changes to ERP linkages, you will need to click “master data sync” for those changes to be seen throughout the tool. ');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Si vous apportez des modifications aux liaisons ERP, vous devrez cliquer sur «synchronisation des données de base» pour que ces modifications soient visibles dans tout loutil.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Si realiza algún cambio en los vínculos de ERP, deberá hacer clic en "sincronización de datos maestros" para que esos cambios se vean en toda la herramienta.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Se você fizer alguma alteração nos vínculos do ERP, precisará clicar em “sincronização de dados mestre” para que essas alterações sejam vistas em toda a ferramenta.');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.mt.reminders2','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Linking: ');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Mise en relation:');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Enlace:');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Vinculando:');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.mt.reminders2A','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'QAT shipments available for linking:  ');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Envois QAT disponibles pour la liaison:');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Envíos QAT disponibles para vincular:');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Remessas QAT disponíveis para vinculação:');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.mt.reminders2B','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'ERP orders available for linking: ');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Commandes ERP disponibles pour liaison:');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Pedidos ERP disponibles para vincular:');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Pedidos de ERP disponíveis para vinculação:');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.mt.reminders2C','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'If there is a note in a QAT shipment, this will carry over when you link to the ERP shipment. ');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Sil y a une note dans un envoi QAT, cela sera reporté lorsque vous établissez un lien vers lenvoi ERP.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Si hay una nota en un envío QAT, esta se transferirá cuando se vincule al envío ERP.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Se houver uma nota em uma remessa QAT, ela será transferida quando você vincular à remessa ERP.');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.mt.reminders2D','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Conversion Factor –');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Facteur de conversion -');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Factor de conversión -');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Fator de conversão -');



INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.mt.reminders3','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'While linked:  ');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Pendant que vous êtes lié:');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Mientras está vinculado:');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Enquanto estiver vinculado:');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.mt.reminders4','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'De-Linking:  ');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Dissociation:');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Desvinculación:');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Desvinculação:');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.mt.reminders3A','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Once linked, shipments will be non-editable other than the notes field and conversion factor field, which are editable only on the ERP linking screens. ');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Une fois liés, les envois ne seront pas modifiables à lexception du champ des notes et du champ du facteur de conversion, qui ne sont modifiables que sur les écrans de liaison ERP.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Una vez vinculados, los envíos no serán editables excepto el campo de notas y el campo de factor de conversión, que solo se pueden editar en las pantallas de vinculación de ERP.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Uma vez vinculadas, as remessas não poderão ser editadas, exceto o campo de notas e o campo de fator de conversão, que são editáveis ​​apenas nas telas de vinculação do ERP.');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.mt.reminders4A','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'If you delink a shipment, the QAT shipment will revert to the original shipment’s note and not carry over any updates you made after linking. For this, we recommend copying the note before de-linking and pasting it into the QAT shipment after.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Si vous dissociez un envoi, l’envoi QAT reviendra à la note d’expédition originale et ne reportera pas les mises à jour que vous avez effectuées après la liaison. Pour cela, nous vous recommandons de copier la note avant de la dissocier et de la coller dans lenvoi QAT après.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Si desvincula un envío, el envío QAT volverá a la nota del envío original y no transferirá las actualizaciones que realizó después de la vinculación. Para esto, recomendamos copiar la nota antes de desvincularla y pegarla en el envío QAT después.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Se você desvincular uma remessa, a remessa QAT reverterá para a nota da remessa original e não carregará nenhuma atualização feita após a vinculação. Para isso, recomendamos copiar a nota antes de desvincular e colá-la na remessa QAT depois.');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.mt.reminders2A1','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Shipments in “Submitted”, “Approved”, “Arrived”, “Shipped” status (all dates) ');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Expéditions dans le statut «Soumis», «Approuvé», «Arrivé», «Expédié» (toutes les dates)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Envíos en estado "Enviado", "Aprobado", "Llegado", "Enviado" (todas las fechas)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Remessas em status "Enviado", "Aprovado", "Chegado", "Enviado" (todas as datas)');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.mt.reminders2A2','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Shipments in “Received” status (within last 6 months) ');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Envois avec le statut «Reçu» (au cours des 6 derniers mois)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Envíos en estado "Recibido" (en los últimos 6 meses)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Remessas no status “Recebido” (nos últimos 6 meses)');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.mt.reminders2A3','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Note: “planned” or “cancelled” are not available');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Remarque: «planifié» ou «annulé» ne sont pas disponibles');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Nota: "planeado" o "cancelado" no están disponibles');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Nota: “planejado” ou “cancelado” não estão disponíveis');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.mt.reminders2B1','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Orders after “Approved” status');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Commandes après le statut "Approuvées"');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Pedidos después del estado "Aprobado"');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Pedidos após o status “Aprovado”');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.mt.reminders2B2','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Orders in “Delivered” status (within last 6 months) ');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Commandes au statut «Livrées» (au cours des 6 derniers mois)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Pedidos en estado "Entregado" (en los últimos 6 meses)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Pedidos com status "Entregue" (nos últimos 6 meses)');



INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.mt.reminders2D1a','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'The ');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'La');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'La');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'O');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.mt.reminders2D1b','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'ERP shipment quantity');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Quantité dexpédition ERP');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cantidad de envío ERP');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Quantidade de remessa de ERP');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.mt.reminders2D1c','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,' is the number flowing from the ERP system, and is based on the ERP unit, which may be different than your QAT planning unit. ');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'est le numéro provenant du système ERP et est basé sur lunité ERP, qui peut être différente de votre unité de planification QAT.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'es el número que fluye del sistema ERP y se basa en la unidad ERP, que puede ser diferente a su unidad de planificación QAT.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'é o número que flui do sistema ERP e é baseado na unidade ERP, que pode ser diferente da unidade de planejamento QAT.');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.mt.reminders2D2a','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'The ');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'La');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'La');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'O');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.mt.reminders2D2b','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'converted QAT shipment quantity');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'quantité dexpédition QAT convertie');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'cantidad de envío QAT convertida');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'quantidade de remessa QAT convertida');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.mt.reminders2D2c','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,' is the number that will be used in your supply plan for the linked shipment.  ');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'est le numéro qui sera utilisé dans votre plan dapprovisionnement pour lenvoi lié.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'es el número que se utilizará en su plan de suministro para el envío vinculado.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'é o número que será usado em seu plano de suprimento para a remessa vinculada.');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.mt.reminders2D3','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Your order in the ERP system is 20 packs (10 chocolate bars per pack), but your supply plan is in packs of 100 chocolate bars. To calculate the conversion factor, consider how many single units you have in the ERP pack size (10) and divide by how many single units you have in your QAT planning unit (100). In this example, the conversion factor is 10 bars / 100 bars = 0.1. Multiplying the ERP shipment quantity by 0.1 results in the converted QAT shipment quantity of 2, which is the number that will be used in your supply plan.  ');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Votre commande dans le système ERP est de 20 paquets (10 barres de chocolat par paquet), mais votre plan dapprovisionnement est en paquets de 100 barres de chocolat. Pour calculer le facteur de conversion, tenez compte du nombre dunités individuelles que vous avez dans la taille du pack ERP (10) et divisez par le nombre dunités individuelles que vous avez dans votre unité de planification QAT (100). Dans cet exemple, le facteur de conversion est de 10 bars / 100 bars = 0,1. En multipliant la quantité dexpédition ERP par 0,1, la quantité dexpédition QAT convertie est de 2, qui est le nombre qui sera utilisé dans votre plan dapprovisionnement.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Su pedido en el sistema ERP es de 20 paquetes (10 barras de chocolate por paquete), pero su plan de suministro está en paquetes de 100 barras de chocolate. Para calcular el factor de conversión, considere cuántas unidades individuales tiene en el tamaño del paquete ERP (10) y divida por cuántas unidades individuales tiene en su unidad de planificación QAT (100). En este ejemplo, el factor de conversión es 10 bares / 100 bares = 0,1. Multiplicar la cantidad de envío de ERP por 0,1 da como resultado la cantidad de envío QAT convertida de 2, que es el número que se utilizará en su plan de suministro.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Seu pedido no sistema ERP é de 20 embalagens (10 barras de chocolate por embalagem), mas seu plano de abastecimento é em embalagens de 100 barras de chocolate. Para calcular o fator de conversão, considere quantas unidades individuais você tem no tamanho do pacote ERP (10) e divida por quantas unidades individuais você tem em sua unidade de planejamento QAT (100). Neste exemplo, o fator de conversão é 10 barras / 100 barras = 0,1. Multiplicando a quantidade de remessa ERP por 0,1 resulta na quantidade de remessa QAT convertida de 2, que é o número que será usado em seu plano de suprimento.');



INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.mt.reminders2D4a','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Pack of 10 chocolate bars ');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Pack de 10 tablettes de chocolat');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Paquete de 10 barras de chocolate');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Pacote de 10 barras de chocolate');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.mt.reminders2D4b','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'20');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'vingt');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'veinte');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'vinte');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.mt.reminders2D4c','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'= 10/100 = 0.1 ');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'= 10/100 = 0,1');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'= 10/100 = 0,1');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'= 10/100 = 0,1');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.mt.reminders2D4d','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'2');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'deux');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'dos');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'dois');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.mt.reminders2D4e','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Pack of 100 chocolate bars');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Pack de 100 tablettes de chocolat');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Paquete de 100 barras de chocolate');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Pacote de 100 barras de chocolate');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.mt.notificationCount','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Notification Count');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Nombre de notifications');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Recuento de notificaciones');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Contagem de Notificação');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.mt.viewBatchDetails','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'View Batch Details');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Afficher les détails du lot');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ver detalles de lote');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Exibir detalhes do lote');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.mt.erpHistoryTitle','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'ERP Order History');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Historique des commandes ERP');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Historial de pedidos de ERP');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Histórico de pedidos de ERP');


DELIMITER $$

USE `fasp`$$

DROP PROCEDURE IF EXISTS `getShipmentListForManualLinking`$$

CREATE DEFINER=`faspUser`@`%` PROCEDURE `getShipmentListForManualLinking`(PROGRAM_ID INT(10), PLANNING_UNIT_ID TEXT, VERSION_ID INT (10))
BEGIN
    SET @programId = PROGRAM_ID;
    SET @planningUnitIds = PLANNING_UNIT_ID;
    SET @procurementAgentId = 1;
    SET @versionId = VERSION_ID;
    IF @versionId = -1 THEN
        SELECT MAX(pv.VERSION_ID) INTO @versionId FROM rm_program_version pv WHERE pv.PROGRAM_ID=@programId;
    END IF;
   
SELECT
        st.SHIPMENT_ID, st.SHIPMENT_TRANS_ID, st.SHIPMENT_QTY, st.EXPECTED_DELIVERY_DATE, st.PRODUCT_COST, st.SKU_CODE,
        st.PROCUREMENT_AGENT_ID, st.PROCUREMENT_AGENT_CODE, st.`COLOR_HTML_CODE`, st.`PROCUREMENT_AGENT_LABEL_ID`, st.`PROCUREMENT_AGENT_LABEL_EN`, st.`PROCUREMENT_AGENT_LABEL_FR`, st.`PROCUREMENT_AGENT_LABEL_SP`, st.`PROCUREMENT_AGENT_LABEL_PR`,
        st.FUNDING_SOURCE_ID, st.FUNDING_SOURCE_CODE, st.`FUNDING_SOURCE_LABEL_ID`, st.`FUNDING_SOURCE_LABEL_EN`, st.`FUNDING_SOURCE_LABEL_FR`, st.`FUNDING_SOURCE_LABEL_SP`, st.`FUNDING_SOURCE_LABEL_PR`,
        st.BUDGET_ID, st.BUDGET_CODE, st.`BUDGET_LABEL_ID`, st.`BUDGET_LABEL_EN`, st.`BUDGET_LABEL_FR`, st.`BUDGET_LABEL_SP`, st.`BUDGET_LABEL_PR`,
        st.SHIPMENT_STATUS_ID, st.`SHIPMENT_STATUS_LABEL_ID`, st.`SHIPMENT_STATUS_LABEL_EN`, st.`SHIPMENT_STATUS_LABEL_FR`, st.`SHIPMENT_STATUS_LABEL_SP`, st.`SHIPMENT_STATUS_LABEL_PR`,
        st.PLANNING_UNIT_ID, st.`PLANNING_UNIT_LABEL_ID`, st.`PLANNING_UNIT_LABEL_EN`, st.`PLANNING_UNIT_LABEL_FR`, st.`PLANNING_UNIT_LABEL_SP`, st.`PLANNING_UNIT_LABEL_PR`,
        st.ORDER_NO, st.PRIME_LINE_NO,st.`NOTES`
FROM (
        SELECT
            s.SHIPMENT_ID, st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE,st.SHIPMENT_QTY, st.RATE, st.PRODUCT_COST, st.FREIGHT_COST, st.ACCOUNT_FLAG, st.SHIPMENT_TRANS_ID, papu.SKU_CODE,
            pa.`PROCUREMENT_AGENT_ID`, pa.`PROCUREMENT_AGENT_CODE`, pa.`COLOR_HTML_CODE`, pa.`LABEL_ID` `PROCUREMENT_AGENT_LABEL_ID`, pa.`LABEL_EN` `PROCUREMENT_AGENT_LABEL_EN`, pa.LABEL_FR `PROCUREMENT_AGENT_LABEL_FR`, pa.LABEL_SP `PROCUREMENT_AGENT_LABEL_SP`, pa.LABEL_PR `PROCUREMENT_AGENT_LABEL_PR`,
            fs.`FUNDING_SOURCE_ID`, fs.`FUNDING_SOURCE_CODE`, fs.LABEL_ID `FUNDING_SOURCE_LABEL_ID`, fs.LABEL_EN `FUNDING_SOURCE_LABEL_EN`, fs.LABEL_FR `FUNDING_SOURCE_LABEL_FR`, fs.LABEL_SP `FUNDING_SOURCE_LABEL_SP`, fs.LABEL_PR `FUNDING_SOURCE_LABEL_PR`,
            shs.SHIPMENT_STATUS_ID, shs.LABEL_ID `SHIPMENT_STATUS_LABEL_ID`, shs.LABEL_EN `SHIPMENT_STATUS_LABEL_EN`, shs.LABEL_FR `SHIPMENT_STATUS_LABEL_FR`, shs.LABEL_SP `SHIPMENT_STATUS_LABEL_SP`, shs.LABEL_PR `SHIPMENT_STATUS_LABEL_PR`,
            sc.CURRENCY_ID `SHIPMENT_CURRENCY_ID`, sc.`CURRENCY_CODE` `SHIPMENT_CURRENCY_CODE`, s.CONVERSION_RATE_TO_USD `SHIPMENT_CONVERSION_RATE_TO_USD`,
            sc.LABEL_ID `SHIPMENT_CURRENCY_LABEL_ID`, sc.LABEL_EN `SHIPMENT_CURRENCY_LABEL_EN`, sc.LABEL_FR `SHIPMENT_CURRENCY_LABEL_FR`, sc.LABEL_SP `SHIPMENT_CURRENCY_LABEL_SP`, sc.LABEL_PR `SHIPMENT_CURRENCY_LABEL_PR`,
            st.ACTIVE, st.`ORDER_NO`, st.`PRIME_LINE_NO`,
            b.BUDGET_ID, b.BUDGET_CODE, b.LABEL_ID `BUDGET_LABEL_ID`, b.LABEL_EN `BUDGET_LABEL_EN`, b.LABEL_FR `BUDGET_LABEL_FR`, b.LABEL_SP `BUDGET_LABEL_SP`, b.LABEL_PR `BUDGET_LABEL_PR`,
            st.PLANNING_UNIT_ID, pu.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pu.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pu.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pu.LABEL_SP `PLANNING_UNIT_LABEL_SP`, pu.LABEL_PR `PLANNING_UNIT_LABEL_PR`,st.`NOTES`
FROM (
    SELECT st.SHIPMENT_ID, MAX(st.VERSION_ID) MAX_VERSION_ID FROM rm_shipment s LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID WHERE (@versiONId=-1 OR st.VERSION_ID<=@versiONId) AND s.PROGRAM_ID=@programId GROUP BY st.SHIPMENT_ID
) ts
    LEFT JOIN rm_shipment s ON ts.SHIPMENT_ID=s.SHIPMENT_ID
    LEFT JOIN rm_shipment_trans st ON ts.SHIPMENT_ID=st.SHIPMENT_ID AND ts.MAX_VERSION_ID=st.VERSION_ID
    LEFT JOIN vw_planning_unit pu ON st.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID
    LEFT JOIN vw_procurement_agent pa ON st.PROCUREMENT_AGENT_ID=pa.PROCUREMENT_AGENT_ID
    LEFT JOIN vw_funding_source fs ON st.FUNDING_SOURCE_ID=fs.FUNDING_SOURCE_ID
    LEFT JOIN vw_shipment_status shs ON st.SHIPMENT_STATUS_ID=shs.SHIPMENT_STATUS_ID
    LEFT JOIN vw_currency sc ON s.CURRENCY_ID=sc.CURRENCY_ID
    LEFT JOIN vw_budget b ON st.BUDGET_ID=b.BUDGET_ID
    LEFT JOIN rm_manual_tagging mt ON mt.SHIPMENT_ID=ts.SHIPMENT_ID AND mt.ACTIVE
    LEFT JOIN rm_procurement_agent_planning_unit papu ON papu.PROCUREMENT_AGENT_ID=pa.PROCUREMENT_AGENT_ID AND papu.PLANNING_UNIT_ID=st.PLANNING_UNIT_ID
    WHERE st.ERP_FLAG=0 AND st.ACTIVE AND st.SHIPMENT_STATUS_ID IN (3,4,5,6,9,7) AND (LENGTH(@planningUnitIds)=0 OR FIND_IN_SET(st.PLANNING_UNIT_ID,@planningUnitIds))   AND (mt.SHIPMENT_ID IS NULL OR mt.ACTIVE=0) AND st.PROCUREMENT_AGENT_ID=@procurementAgentId
) st
WHERE
IF(COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) < CURDATE() - INTERVAL 6 MONTH, st.SHIPMENT_STATUS_ID!=7 , st.SHIPMENT_STATUS_ID IN (3,4,5,6,9,7))
ORDER BY COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) DESC;
END$$

DELIMITER ;


DELIMITER $$

USE `fasp`$$

DROP PROCEDURE IF EXISTS `getErpShipmentForNotLinked`$$

CREATE DEFINER=`faspUser`@`%` PROCEDURE `getErpShipmentForNotLinked`(
    VAR_REALM_COUNTRY_ID INT(10), 
    VAR_PRODUCT_CATEGORY_IDS TEXT, 
    VAR_PLANNING_UNIT_IDS TEXT, 
    VAR_REALM_ID INT(10)
    )
BEGIN
    SET @productCategoryIds = VAR_PRODUCT_CATEGORY_IDS;
    SET @planningUnitIds = VAR_PLANNING_UNIT_IDS;
    SET @realmId = VAR_REALM_ID;
    SET @realmCountryId = VAR_REALM_COUNTRY_ID;
    
    SELECT GROUP_CONCAT(pc2.PRODUCT_CATEGORY_ID) INTO @finalProductCategoryIds FROM rm_product_category pc LEFT JOIN rm_product_category pc2 ON pc2.SORT_ORDER LIKE CONCAT(pc.SORT_ORDER,"%") WHERE FIND_IN_SET(pc.PRODUCT_CATEGORY_ID, @productCategoryIds);
    
    SELECT GROUP_CONCAT(COALESCE(ac.RECEPIENT_NAME, c.LABEL_EN)) INTO @recepientCountryList
    FROM rm_realm_country rc
    LEFT JOIN vw_country c ON rc.COUNTRY_ID=c.COUNTRY_ID
    LEFT JOIN tr_artmis_country ac ON rc.REALM_COUNTRY_ID=ac.REALM_COUNTRY_ID
    WHERE (rc.REALM_COUNTRY_ID=@realmCountryId OR @realmCountryId=-1) AND rc.REALM_ID=@realmId;
    
    SELECT 
        st.ORDER_NO, st.PRIME_LINE_NO, st.`RO_NO`,st.`RO_PRIME_LINE_NO`,st.ERP_ORDER_ID,
        st.PLANNING_UNIT_ID, st.`PLANNING_UNIT_LABEL_ID`, st.`PLANNING_UNIT_LABEL_EN`, st.`PLANNING_UNIT_LABEL_FR`, st.`PLANNING_UNIT_LABEL_SP`, st.`PLANNING_UNIT_LABEL_PR`,
        st.`EXPECTED_DELIVERY_DATE`, st.`STATUS`, st.QTY,st.SKU_CODE
        FROM (
    SELECT 
        o.ORDER_NO, o.PRIME_LINE_NO, o.`RO_NO`,o.`RO_PRIME_LINE_NO`,o.ERP_ORDER_ID,
        pu.PLANNING_UNIT_ID, pu.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pu.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pu.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pu.LABEL_SP `PLANNING_UNIT_LABEL_SP`, pu.LABEL_PR `PLANNING_UNIT_LABEL_PR`,
        COALESCE(o.CURRENT_ESTIMATED_DELIVERY_DATE,o.AGREED_DELIVERY_DATE, o.REQ_DELIVERY_DATE) `EXPECTED_DELIVERY_DATE`, o.`STATUS`, o.QTY,papu.SKU_CODE
    FROM rm_erp_order o 
    LEFT JOIN (SELECT MAX(o.ERP_ORDER_ID) AS ERP_ORDER_ID FROM rm_erp_order o GROUP BY o.`RO_NO`,o.`RO_PRIME_LINE_NO`,o.ORDER_NO, o.PRIME_LINE_NO) o1 ON o.ERP_ORDER_ID=o1.ERP_ORDER_ID 
    LEFT JOIN rm_manual_tagging mt ON mt.ORDER_NO=o.ORDER_NO AND mt.PRIME_LINE_NO=o.PRIME_LINE_NO AND mt.ACTIVE 
    LEFT JOIN rm_procurement_agent_planning_unit papu ON o.PLANNING_UNIT_SKU_CODE=LEFT(papu.SKU_CODE,12) 
    LEFT JOIN vw_planning_unit pu ON papu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID 
    LEFT JOIN rm_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID 
    LEFT JOIN rm_shipment_status_mapping sm ON sm.`EXTERNAL_STATUS_STAGE`=o.`STATUS`
    WHERE  
        o1.ERP_ORDER_ID IS NOT NULL  
        AND mt.SHIPMENT_ID IS NULL  
        AND o.RECPIENT_COUNTRY!=''  
        AND FIND_IN_SET(o.RECPIENT_COUNTRY,@recepientCountryList)
        AND sm.`SHIPMENT_STATUS_MAPPING_ID` NOT IN (1,3,5,7,9,10,13,15)
         AND (@finalProductCategoryIds IS NULL OR LENGTH(@finalProductCategoryIds)=0 OR FIND_IN_SET(fu.PRODUCT_CATEGORY_ID, @finalProductCategoryIds))
        AND (LENGTH(@planningUnitIds)=0 OR FIND_IN_SET(papu.PLANNING_UNIT_ID, @planningUnitIds))
        ) st
        LEFT JOIN rm_shipment_status_mapping sms ON sms.`EXTERNAL_STATUS_STAGE`=st.`STATUS`
        WHERE
        IF(st.EXPECTED_DELIVERY_DATE < CURDATE() - INTERVAL 6 MONTH, sms.SHIPMENT_STATUS_MAPPING_ID!=2 , sms.SHIPMENT_STATUS_MAPPING_ID NOT IN (1,3,5,7,9,10,13,15))
        ORDER BY st.EXPECTED_DELIVERY_DATE DESC;
END$$

DELIMITER ;

-- ALTER TABLE `fasp`.`rm_erp_notification` ADD COLUMN `MANUAL_TAGGING_ID` INT NOT NULL AFTER `CHILD_SHIPMENT_ID`; 


DELIMITER $$

USE `fasp`$$

DROP PROCEDURE IF EXISTS `getShipmentLinkingNotifications`$$

CREATE DEFINER=`faspUser`@`%` PROCEDURE `getShipmentLinkingNotifications`(PROGRAM_ID INT(10), PLANNING_UNIT_ID TEXT, VERSION_ID INT (10))
BEGIN
    SET @programId = PROGRAM_ID;
    SET @planningUnitIds = PLANNING_UNIT_ID;
    SET @procurementAgentId = 1;
    SET @versionId = VERSION_ID;
    IF @versionId = -1 THEN
        SELECT MAX(pv.VERSION_ID) INTO @versionId FROM rm_program_version pv WHERE pv.PROGRAM_ID=@programId;
    END IF;
   




































SELECT n.`NOTIFICATION_ID`,n.`ADDRESSED`,n.NOTIFICATION_TYPE_ID,l.*,n.`CHILD_SHIPMENT_ID` AS SHIPMENT_ID,e.`RO_NO`,e.`RO_PRIME_LINE_NO`,e.`ORDER_NO`,e.`PRIME_LINE_NO`,
st.`EXPECTED_DELIVERY_DATE`,e.`STATUS`,st.`SHIPMENT_QTY`,n.`CONVERSION_FACTOR`,n.`NOTES`,s.`PARENT_SHIPMENT_ID`,
st.PLANNING_UNIT_ID, pu.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pu.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pu.LABEL_FR `PLANNING_UNIT_LABEL_FR`,
 pu.LABEL_SP `PLANNING_UNIT_LABEL_SP`, pu.LABEL_PR `PLANNING_UNIT_LABEL_PR`,
 papu1.PLANNING_UNIT_ID AS ERP_PLANNING_UNIT_ID, pu1.LABEL_ID `ERP_PLANNING_UNIT_LABEL_ID`, pu1.LABEL_EN `ERP_PLANNING_UNIT_LABEL_EN`, pu1.LABEL_FR `ERP_PLANNING_UNIT_LABEL_FR`,
 pu1.LABEL_SP `ERP_PLANNING_UNIT_LABEL_SP`, pu1.LABEL_PR `ERP_PLANNING_UNIT_LABEL_PR`
 FROM 
(SELECT st.SHIPMENT_ID, MAX(st.VERSION_ID) MAX_VERSION_ID 
FROM rm_shipment s LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID 
WHERE (@versiONId=-1 OR st.VERSION_ID<=@versiONId) AND s.PROGRAM_ID=@programId GROUP BY st.SHIPMENT_ID
) ts
LEFT JOIN rm_shipment s ON s.`SHIPMENT_ID`=ts.`SHIPMENT_ID`
LEFT JOIN rm_erp_notification n ON n.`CHILD_SHIPMENT_ID`=s.`SHIPMENT_ID`
LEFT JOIN ap_notification_type nt ON nt.`NOTIFICATION_TYPE_ID`=n.`NOTIFICATION_TYPE_ID`
LEFT JOIN ap_label l ON l.`LABEL_ID`=nt.`LABEL_ID`
LEFT JOIN rm_erp_order e ON e.`ERP_ORDER_ID`=n.`ERP_ORDER_ID`
LEFT JOIN rm_procurement_agent_planning_unit papu1 ON LEFT(papu1.`SKU_CODE`,12)=e.`PLANNING_UNIT_SKU_CODE`
LEFT JOIN vw_planning_unit pu1 ON pu1.`PLANNING_UNIT_ID`=papu1.`PLANNING_UNIT_ID`
LEFT JOIN rm_shipment_trans st ON ts.SHIPMENT_ID=st.SHIPMENT_ID AND ts.MAX_VERSION_ID=st.VERSION_ID
LEFT JOIN rm_procurement_agent_planning_unit papu2 ON papu2.`PLANNING_UNIT_ID`=st.`PLANNING_UNIT_ID`
LEFT JOIN vw_planning_unit pu ON pu.`PLANNING_UNIT_ID`=papu2.`PLANNING_UNIT_ID`
-- LEFT JOIN rm_manual_tagging m ON m.ORDER_NO=e.ORDER_NO AND m.PRIME_LINE_NO=e.PRIME_LINE_NO AND m.SHIPMENT_ID=s.`PARENT_SHIPMENT_ID`
WHERE n.`NOTIFICATION_ID` IS NOT NULL AND n.ACTIVE AND (LENGTH(@planningUnitIds)=0 OR FIND_IN_SET(st.PLANNING_UNIT_ID,@planningUnitIds)) 
ORDER BY n.ADDRESSED DESC;
END$$

DELIMITER ;

DELIMITER $$

USE `fasp`$$

DROP PROCEDURE IF EXISTS `getShipmentListForAlreadyLinkedShipments`$$

CREATE DEFINER=`faspUser`@`%` PROCEDURE `getShipmentListForAlreadyLinkedShipments`(PROGRAM_ID INT(10), PLANNING_UNIT_ID TEXT, VERSION_ID INT (10))
BEGIN
    SET @programId = PROGRAM_ID;
    SET @planningUnitIds = PLANNING_UNIT_ID;
    SET @procurementAgentId = 1;
    SET @versionId = VERSION_ID;
    IF @versionId = -1 THEN
        SELECT MAX(pv.VERSION_ID) INTO @versionId FROM rm_program_version pv WHERE pv.PROGRAM_ID=@programId;
    END IF;
   
SELECT
        st.SHIPMENT_ID, st.SHIPMENT_TRANS_ID, st.QTY, st.EXPECTED_DELIVERY_DATE, st.PRODUCT_COST, st.SKU_CODE,
        st.PROCUREMENT_AGENT_ID, st.PROCUREMENT_AGENT_CODE, st.`COLOR_HTML_CODE`, st.`PROCUREMENT_AGENT_LABEL_ID`, st.`PROCUREMENT_AGENT_LABEL_EN`, st.`PROCUREMENT_AGENT_LABEL_FR`, st.`PROCUREMENT_AGENT_LABEL_SP`, st.`PROCUREMENT_AGENT_LABEL_PR`,
        st.FUNDING_SOURCE_ID, st.FUNDING_SOURCE_CODE, st.`FUNDING_SOURCE_LABEL_ID`, st.`FUNDING_SOURCE_LABEL_EN`, st.`FUNDING_SOURCE_LABEL_FR`, st.`FUNDING_SOURCE_LABEL_SP`, st.`FUNDING_SOURCE_LABEL_PR`,
        st.BUDGET_ID, st.BUDGET_CODE, st.`BUDGET_LABEL_ID`, st.`BUDGET_LABEL_EN`, st.`BUDGET_LABEL_FR`, st.`BUDGET_LABEL_SP`, st.`BUDGET_LABEL_PR`,
        st.SHIPMENT_STATUS_ID, st.`SHIPMENT_STATUS_LABEL_ID`, st.`SHIPMENT_STATUS_LABEL_EN`, st.`SHIPMENT_STATUS_LABEL_FR`, st.`SHIPMENT_STATUS_LABEL_SP`, st.`SHIPMENT_STATUS_LABEL_PR`,
        st.PLANNING_UNIT_ID, st.`PLANNING_UNIT_LABEL_ID`, st.`PLANNING_UNIT_LABEL_EN`, st.`PLANNING_UNIT_LABEL_FR`, st.`PLANNING_UNIT_LABEL_SP`, st.`PLANNING_UNIT_LABEL_PR`,
        st.ORDER_NO, st.PRIME_LINE_NO,st.`NOTES`,st.CONVERSION_FACTOR,st.PARENT_SHIPMENT_ID,st.ERP_PRODUCT_LABEL_EN,st.ERP_PRODUCT_LABEL_FR,st.ERP_PRODUCT_LABEL_SP,st.ERP_PRODUCT_LABEL_PR,st.ERP_PRODUCT_ID,st.ERP_PRODUCT_LABEL_ID,st.RO_NO,st.RO_PRIME_LINE_NO,st.STATUS
FROM (
        SELECT
            s.SHIPMENT_ID, st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE,eo.QTY, st.RATE, st.PRODUCT_COST, st.FREIGHT_COST, st.ACCOUNT_FLAG, st.SHIPMENT_TRANS_ID, papu.SKU_CODE,
            pa.`PROCUREMENT_AGENT_ID`, pa.`PROCUREMENT_AGENT_CODE`, pa.`COLOR_HTML_CODE`, pa.`LABEL_ID` `PROCUREMENT_AGENT_LABEL_ID`, pa.`LABEL_EN` `PROCUREMENT_AGENT_LABEL_EN`, pa.LABEL_FR `PROCUREMENT_AGENT_LABEL_FR`, pa.LABEL_SP `PROCUREMENT_AGENT_LABEL_SP`, pa.LABEL_PR `PROCUREMENT_AGENT_LABEL_PR`,
            fs.`FUNDING_SOURCE_ID`, fs.`FUNDING_SOURCE_CODE`, fs.LABEL_ID `FUNDING_SOURCE_LABEL_ID`, fs.LABEL_EN `FUNDING_SOURCE_LABEL_EN`, fs.LABEL_FR `FUNDING_SOURCE_LABEL_FR`, fs.LABEL_SP `FUNDING_SOURCE_LABEL_SP`, fs.LABEL_PR `FUNDING_SOURCE_LABEL_PR`,
            shs.SHIPMENT_STATUS_ID, shs.LABEL_ID `SHIPMENT_STATUS_LABEL_ID`, shs.LABEL_EN `SHIPMENT_STATUS_LABEL_EN`, shs.LABEL_FR `SHIPMENT_STATUS_LABEL_FR`, shs.LABEL_SP `SHIPMENT_STATUS_LABEL_SP`, shs.LABEL_PR `SHIPMENT_STATUS_LABEL_PR`,
            sc.CURRENCY_ID `SHIPMENT_CURRENCY_ID`, sc.`CURRENCY_CODE` `SHIPMENT_CURRENCY_CODE`, s.CONVERSION_RATE_TO_USD `SHIPMENT_CONVERSION_RATE_TO_USD`,
            sc.LABEL_ID `SHIPMENT_CURRENCY_LABEL_ID`, sc.LABEL_EN `SHIPMENT_CURRENCY_LABEL_EN`, sc.LABEL_FR `SHIPMENT_CURRENCY_LABEL_FR`, sc.LABEL_SP `SHIPMENT_CURRENCY_LABEL_SP`, sc.LABEL_PR `SHIPMENT_CURRENCY_LABEL_PR`,
            mt.ACTIVE, st.`ORDER_NO`, st.`PRIME_LINE_NO`,
            b.BUDGET_ID, b.BUDGET_CODE, b.LABEL_ID `BUDGET_LABEL_ID`, b.LABEL_EN `BUDGET_LABEL_EN`, b.LABEL_FR `BUDGET_LABEL_FR`, b.LABEL_SP `BUDGET_LABEL_SP`, b.LABEL_PR `BUDGET_LABEL_PR`,
            st.PLANNING_UNIT_ID, pu.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pu.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pu.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pu.LABEL_SP `PLANNING_UNIT_LABEL_SP`, pu.LABEL_PR `PLANNING_UNIT_LABEL_PR`,mt.`NOTES`,mt.CONVERSION_FACTOR,s.PARENT_SHIPMENT_ID,pu1.LABEL_EN AS ERP_PRODUCT_LABEL_EN,pu1.LABEL_FR AS ERP_PRODUCT_LABEL_FR,pu1.LABEL_SP AS ERP_PRODUCT_LABEL_SP,pu1.LABEL_PR AS ERP_PRODUCT_LABEL_PR,pu1.PLANNING_UNIT_ID AS ERP_PRODUCT_ID,pu1.LABEL_ID AS ERP_PRODUCT_LABEL_ID,eo.RO_NO,eo.RO_PRIME_LINE_NO,eo.STATUS
FROM (
    SELECT st.SHIPMENT_ID, MAX(st.VERSION_ID) MAX_VERSION_ID FROM rm_shipment s LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID WHERE (@versiONId=-1 OR st.VERSION_ID<=@versiONId) AND s.PROGRAM_ID=@programId GROUP BY st.SHIPMENT_ID
) ts
    LEFT JOIN rm_shipment s ON ts.SHIPMENT_ID=s.SHIPMENT_ID
    LEFT JOIN rm_shipment_trans st ON ts.SHIPMENT_ID=st.SHIPMENT_ID AND ts.MAX_VERSION_ID=st.VERSION_ID
    LEFT JOIN vw_planning_unit pu ON st.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID
    LEFT JOIN vw_procurement_agent pa ON st.PROCUREMENT_AGENT_ID=pa.PROCUREMENT_AGENT_ID
    LEFT JOIN vw_funding_source fs ON st.FUNDING_SOURCE_ID=fs.FUNDING_SOURCE_ID
    LEFT JOIN vw_shipment_status shs ON st.SHIPMENT_STATUS_ID=shs.SHIPMENT_STATUS_ID
    LEFT JOIN vw_currency sc ON s.CURRENCY_ID=sc.CURRENCY_ID
    LEFT JOIN vw_budget b ON st.BUDGET_ID=b.BUDGET_ID
    LEFT JOIN rm_manual_tagging mt ON mt.ORDER_NO=st.ORDER_NO AND mt.PRIME_LINE_NO=st.PRIME_LINE_NO AND mt.ACTIVE
    LEFT JOIN rm_procurement_agent_planning_unit papu ON papu.PROCUREMENT_AGENT_ID=pa.PROCUREMENT_AGENT_ID AND papu.PLANNING_UNIT_ID=st.PLANNING_UNIT_ID
    LEFT JOIN (SELECT e.PLANNING_UNIT_SKU_CODE,e.STATUS,e.QTY,e.RO_NO,e.RO_PRIME_LINE_NO,e.ORDER_NO,e.PRIME_LINE_NO FROM rm_erp_order e WHERE e.`ERP_ORDER_ID` IN (SELECT MAX(e.`ERP_ORDER_ID`)  AS ERP_ORDER_ID FROM rm_erp_order e
                 GROUP BY e.`RO_NO`,e.`RO_PRIME_LINE_NO`,e.`ORDER_NO`,e.`PRIME_LINE_NO`)) AS eo ON eo.ORDER_NO=st.ORDER_NO AND eo.PRIME_LINE_NO=st.PRIME_LINE_NO
    LEFT JOIN rm_procurement_agent_planning_unit papu1 ON papu1.PROCUREMENT_AGENT_ID=pa.PROCUREMENT_AGENT_ID AND LEFT(papu1.SKU_CODE,12)=eo.PLANNING_UNIT_SKU_CODE
    LEFT JOIN vw_planning_unit pu1 ON papu1.PLANNING_UNIT_ID=pu1.PLANNING_UNIT_ID
    WHERE st.ERP_FLAG=1 AND st.ACTIVE AND st.SHIPMENT_STATUS_ID IN (3,4,5,6,7,9) AND (LENGTH(@planningUnitIds)=0 OR FIND_IN_SET(st.PLANNING_UNIT_ID,@planningUnitIds))   

    AND st.PROCUREMENT_AGENT_ID=@procurementAgentId
) st
ORDER BY COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) DESC;
END$$

DELIMITER ;

ALTER TABLE `fasp`.`rm_erp_notification` ADD COLUMN `MANUAL_TAGGING_ID` INT NOT NULL AFTER `CHILD_SHIPMENT_ID`; 

DELIMITER $$

USE `fasp`$$

DROP PROCEDURE IF EXISTS `getShipmentLinkingNotifications`$$

CREATE DEFINER=`faspUser`@`%` PROCEDURE `getShipmentLinkingNotifications`(PROGRAM_ID INT(10), PLANNING_UNIT_ID TEXT, VERSION_ID INT (10))
BEGIN
    SET @programId = PROGRAM_ID;
    SET @planningUnitIds = PLANNING_UNIT_ID;
    SET @procurementAgentId = 1;
    SET @versionId = VERSION_ID;
    IF @versionId = -1 THEN
        SELECT MAX(pv.VERSION_ID) INTO @versionId FROM rm_program_version pv WHERE pv.PROGRAM_ID=@programId;
    END IF;

SELECT n.`NOTIFICATION_ID`,n.`ADDRESSED`,n.NOTIFICATION_TYPE_ID,l.*,n.`CHILD_SHIPMENT_ID` AS SHIPMENT_ID,e.`RO_NO`,e.`RO_PRIME_LINE_NO`,e.`ORDER_NO`,e.`PRIME_LINE_NO`,
st.`EXPECTED_DELIVERY_DATE`,e.`STATUS`,st.`SHIPMENT_QTY`,COALESCE(m.`CONVERSION_FACTOR`,n.`CONVERSION_FACTOR`) AS CONVERSION_FACTOR,n.`NOTES`,s.`PARENT_SHIPMENT_ID`,
st.PLANNING_UNIT_ID, pu.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pu.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pu.LABEL_FR `PLANNING_UNIT_LABEL_FR`,
 pu.LABEL_SP `PLANNING_UNIT_LABEL_SP`, pu.LABEL_PR `PLANNING_UNIT_LABEL_PR`,
 papu1.PLANNING_UNIT_ID AS ERP_PLANNING_UNIT_ID, pu1.LABEL_ID `ERP_PLANNING_UNIT_LABEL_ID`, pu1.LABEL_EN `ERP_PLANNING_UNIT_LABEL_EN`, pu1.LABEL_FR `ERP_PLANNING_UNIT_LABEL_FR`,
 pu1.LABEL_SP `ERP_PLANNING_UNIT_LABEL_SP`, pu1.LABEL_PR `ERP_PLANNING_UNIT_LABEL_PR`
 FROM 
(SELECT st.SHIPMENT_ID, MAX(st.VERSION_ID) MAX_VERSION_ID 
FROM rm_shipment s LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID 
WHERE (@versiONId=-1 OR st.VERSION_ID<=@versiONId) AND s.PROGRAM_ID=@programId GROUP BY st.SHIPMENT_ID
) ts
LEFT JOIN rm_shipment s ON s.`SHIPMENT_ID`=ts.`SHIPMENT_ID`
LEFT JOIN rm_erp_notification n ON n.`CHILD_SHIPMENT_ID`=s.`SHIPMENT_ID`
LEFT JOIN ap_notification_type nt ON nt.`NOTIFICATION_TYPE_ID`=n.`NOTIFICATION_TYPE_ID`
LEFT JOIN ap_label l ON l.`LABEL_ID`=nt.`LABEL_ID`
LEFT JOIN rm_erp_order e ON e.`ERP_ORDER_ID`=n.`ERP_ORDER_ID`
LEFT JOIN rm_procurement_agent_planning_unit papu1 ON LEFT(papu1.`SKU_CODE`,12)=e.`PLANNING_UNIT_SKU_CODE`
LEFT JOIN vw_planning_unit pu1 ON pu1.`PLANNING_UNIT_ID`=papu1.`PLANNING_UNIT_ID`
LEFT JOIN rm_shipment_trans st ON ts.SHIPMENT_ID=st.SHIPMENT_ID AND ts.MAX_VERSION_ID=st.VERSION_ID
LEFT JOIN rm_procurement_agent_planning_unit papu2 ON papu2.`PLANNING_UNIT_ID`=st.`PLANNING_UNIT_ID`
LEFT JOIN vw_planning_unit pu ON pu.`PLANNING_UNIT_ID`=papu2.`PLANNING_UNIT_ID`
LEFT JOIN rm_manual_tagging m ON m.MANUAL_TAGGING_ID=n.MANUAL_TAGGING_ID
WHERE n.`NOTIFICATION_ID` IS NOT NULL AND n.ACTIVE AND (LENGTH(@planningUnitIds)=0 OR FIND_IN_SET(st.PLANNING_UNIT_ID,@planningUnitIds)) 
ORDER BY n.ADDRESSED DESC;
END$$

DELIMITER ;

UPDATE `fasp`.`ap_label` SET `LABEL_EN`='ERP Product Change' WHERE `LABEL_ID`='30311'; 

DELIMITER $$

USE `fasp`$$

DROP PROCEDURE IF EXISTS `getErpShipmentForNotLinked`$$

CREATE DEFINER=`faspUser`@`%` PROCEDURE `getErpShipmentForNotLinked`(
    VAR_REALM_COUNTRY_ID INT(10), 
    VAR_PRODUCT_CATEGORY_IDS TEXT, 
    VAR_PLANNING_UNIT_IDS TEXT, 
    VAR_REALM_ID INT(10)
    )
BEGIN
    SET @productCategoryIds = VAR_PRODUCT_CATEGORY_IDS;
    SET @planningUnitIds = VAR_PLANNING_UNIT_IDS;
    SET @realmId = VAR_REALM_ID;
    SET @realmCountryId = VAR_REALM_COUNTRY_ID;
    
    SELECT GROUP_CONCAT(pc2.PRODUCT_CATEGORY_ID) INTO @finalProductCategoryIds FROM rm_product_category pc LEFT JOIN rm_product_category pc2 ON pc2.SORT_ORDER LIKE CONCAT(pc.SORT_ORDER,"%") WHERE FIND_IN_SET(pc.PRODUCT_CATEGORY_ID, @productCategoryIds);
    
    SELECT GROUP_CONCAT(COALESCE(ac.RECEPIENT_NAME, c.LABEL_EN)) INTO @recepientCountryList
    FROM rm_realm_country rc
    LEFT JOIN vw_country c ON rc.COUNTRY_ID=c.COUNTRY_ID
    LEFT JOIN tr_artmis_country ac ON rc.REALM_COUNTRY_ID=ac.REALM_COUNTRY_ID
    WHERE (rc.REALM_COUNTRY_ID=@realmCountryId OR @realmCountryId=-1) AND rc.REALM_ID=@realmId;
    
    SELECT 
        st.ORDER_NO, st.PRIME_LINE_NO, st.`RO_NO`,st.`RO_PRIME_LINE_NO`,st.ERP_ORDER_ID,
        st.PLANNING_UNIT_ID, st.`PLANNING_UNIT_LABEL_ID`, st.`PLANNING_UNIT_LABEL_EN`, st.`PLANNING_UNIT_LABEL_FR`, st.`PLANNING_UNIT_LABEL_SP`, st.`PLANNING_UNIT_LABEL_PR`,
        st.`EXPECTED_DELIVERY_DATE`, st.`STATUS`, st.QTY,st.SKU_CODE
        FROM (
    SELECT 
        o.ORDER_NO, o.PRIME_LINE_NO, o.`RO_NO`,o.`RO_PRIME_LINE_NO`,o.ERP_ORDER_ID,
        pu.PLANNING_UNIT_ID, pu.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pu.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pu.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pu.LABEL_SP `PLANNING_UNIT_LABEL_SP`, pu.LABEL_PR `PLANNING_UNIT_LABEL_PR`,
        COALESCE(o.CURRENT_ESTIMATED_DELIVERY_DATE,o.AGREED_DELIVERY_DATE, o.REQ_DELIVERY_DATE) `EXPECTED_DELIVERY_DATE`, o.`STATUS`, o.QTY,papu.SKU_CODE
    FROM rm_erp_order o 
    LEFT JOIN (SELECT MAX(o.ERP_ORDER_ID) AS ERP_ORDER_ID FROM rm_erp_order o GROUP BY o.`RO_NO`,o.`RO_PRIME_LINE_NO`,o.ORDER_NO, o.PRIME_LINE_NO) o1 ON o.ERP_ORDER_ID=o1.ERP_ORDER_ID 
    LEFT JOIN rm_manual_tagging mt ON mt.ORDER_NO=o.ORDER_NO AND mt.PRIME_LINE_NO=o.PRIME_LINE_NO AND mt.ACTIVE 
    LEFT JOIN rm_procurement_agent_planning_unit papu ON o.PLANNING_UNIT_SKU_CODE=LEFT(papu.SKU_CODE,12) 
    LEFT JOIN vw_planning_unit pu ON papu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID 
    LEFT JOIN rm_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID 
    LEFT JOIN rm_shipment_status_mapping sm ON sm.`EXTERNAL_STATUS_STAGE`=o.`STATUS`
    WHERE  
        o1.ERP_ORDER_ID IS NOT NULL  
        AND mt.SHIPMENT_ID IS NULL  
        AND o.RECPIENT_COUNTRY!=''  
        AND o.CHANGE_CODE != 2
        AND FIND_IN_SET(o.RECPIENT_COUNTRY,@recepientCountryList)
        AND sm.`SHIPMENT_STATUS_MAPPING_ID` NOT IN (1,3,5,7,9,10,13,15)
         AND (@finalProductCategoryIds IS NULL OR LENGTH(@finalProductCategoryIds)=0 OR FIND_IN_SET(fu.PRODUCT_CATEGORY_ID, @finalProductCategoryIds))
        AND (LENGTH(@planningUnitIds)=0 OR FIND_IN_SET(papu.PLANNING_UNIT_ID, @planningUnitIds))
        ) st
        LEFT JOIN rm_shipment_status_mapping sms ON sms.`EXTERNAL_STATUS_STAGE`=st.`STATUS`
        WHERE
        IF(st.EXPECTED_DELIVERY_DATE < CURDATE() - INTERVAL 6 MONTH, sms.SHIPMENT_STATUS_MAPPING_ID!=2 , sms.SHIPMENT_STATUS_MAPPING_ID NOT IN (1,3,5,7,9,10,13,15))
        ORDER BY st.EXPECTED_DELIVERY_DATE DESC;
END$$

DELIMITER ;

DELIMITER $$

USE `fasp`$$

DROP PROCEDURE IF EXISTS `getErpShipmentForNotLinked`$$

CREATE DEFINER=`faspUser`@`%` PROCEDURE `getErpShipmentForNotLinked`(
    VAR_REALM_COUNTRY_ID INT(10), 
    VAR_PRODUCT_CATEGORY_IDS TEXT, 
    VAR_PLANNING_UNIT_IDS TEXT, 
    VAR_REALM_ID INT(10),
    VAR_CUR_DATE DATE
    )
BEGIN
    SET @productCategoryIds = VAR_PRODUCT_CATEGORY_IDS;
    SET @planningUnitIds = VAR_PLANNING_UNIT_IDS;
    SET @realmId = VAR_REALM_ID;
    SET @realmCountryId = VAR_REALM_COUNTRY_ID;
    SET @curDate = VAR_CUR_DATE;
    
    SELECT GROUP_CONCAT(pc3.`PRODUCT_CATEGORY_ID`) INTO @finalProductCategoryIds FROM (SELECT DISTINCT(pc2.PRODUCT_CATEGORY_ID) `PRODUCT_CATEGORY_ID` FROM rm_product_category pc LEFT JOIN rm_product_category pc2 ON pc2.SORT_ORDER LIKE CONCAT(pc.SORT_ORDER,"%") WHERE FIND_IN_SET(pc.PRODUCT_CATEGORY_ID, @productCategoryIds) AND pc.REALM_ID=@realmId) AS pc3;
     
    SELECT GROUP_CONCAT(COALESCE(ac.RECEPIENT_NAME, c.LABEL_EN)) INTO @recepientCountryList
    FROM rm_realm_country rc
    LEFT JOIN vw_country c ON rc.COUNTRY_ID=c.COUNTRY_ID
    LEFT JOIN tr_artmis_country ac ON rc.REALM_COUNTRY_ID=ac.REALM_COUNTRY_ID
    WHERE (rc.REALM_COUNTRY_ID=@realmCountryId) AND rc.REALM_ID=@realmId;
    
    SELECT 
        st.ORDER_NO, st.PRIME_LINE_NO, st.`RO_NO`,st.`RO_PRIME_LINE_NO`,st.ERP_ORDER_ID,
        st.PLANNING_UNIT_ID, st.`PLANNING_UNIT_LABEL_ID`, st.`PLANNING_UNIT_LABEL_EN`, st.`PLANNING_UNIT_LABEL_FR`, st.`PLANNING_UNIT_LABEL_SP`, st.`PLANNING_UNIT_LABEL_PR`,
        st.`EXPECTED_DELIVERY_DATE`, st.`STATUS`, st.QTY,st.SKU_CODE
        FROM (
    SELECT 
        o.ORDER_NO, o.PRIME_LINE_NO, o.`RO_NO`,o.`RO_PRIME_LINE_NO`,o.ERP_ORDER_ID, 
        pu.PLANNING_UNIT_ID, pu.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pu.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pu.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pu.LABEL_SP `PLANNING_UNIT_LABEL_SP`, pu.LABEL_PR `PLANNING_UNIT_LABEL_PR`,
        COALESCE(o.CURRENT_ESTIMATED_DELIVERY_DATE,o.AGREED_DELIVERY_DATE, o.REQ_DELIVERY_DATE) `EXPECTED_DELIVERY_DATE`, o.`STATUS`, o.QTY,papu.SKU_CODE
    FROM rm_erp_order o 
    LEFT JOIN (SELECT MAX(o.ERP_ORDER_ID) AS ERP_ORDER_ID FROM rm_erp_order o GROUP BY o.`RO_NO`,o.`RO_PRIME_LINE_NO`,o.ORDER_NO, o.PRIME_LINE_NO) o1 ON o.ERP_ORDER_ID=o1.ERP_ORDER_ID 
    LEFT JOIN rm_manual_tagging mt ON mt.ORDER_NO=o.ORDER_NO AND mt.PRIME_LINE_NO=o.PRIME_LINE_NO AND mt.ACTIVE 
    LEFT JOIN rm_procurement_agent_planning_unit papu ON o.PLANNING_UNIT_SKU_CODE=LEFT(papu.SKU_CODE,12) 
    LEFT JOIN vw_planning_unit pu ON papu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID 
    LEFT JOIN rm_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID 
    LEFT JOIN rm_shipment_status_mapping sm ON sm.`EXTERNAL_STATUS_STAGE`=o.`STATUS`
    WHERE  
        o1.ERP_ORDER_ID IS NOT NULL  
        AND mt.SHIPMENT_ID IS NULL  
        AND o.RECPIENT_COUNTRY!=''  
        AND o.CHANGE_CODE != 2
        AND FIND_IN_SET(o.RECPIENT_COUNTRY,@recepientCountryList)
        AND sm.`SHIPMENT_STATUS_MAPPING_ID` NOT IN (1,3,5,7,9,10,13,15)
        AND (@finalProductCategoryIds IS NULL OR LENGTH(@finalProductCategoryIds)=0 OR FIND_IN_SET(fu.PRODUCT_CATEGORY_ID, @finalProductCategoryIds))
        AND (@planningUnitIds IS NULL OR LENGTH(@planningUnitIds)=0 OR FIND_IN_SET(papu.PLANNING_UNIT_ID, @planningUnitIds))
        ) st
        LEFT JOIN rm_shipment_status_mapping sms ON sms.`EXTERNAL_STATUS_STAGE`=st.`STATUS`
        WHERE
        IF(st.EXPECTED_DELIVERY_DATE < @curDate - INTERVAL 6 MONTH, sms.SHIPMENT_STATUS_MAPPING_ID!=2 , sms.SHIPMENT_STATUS_MAPPING_ID NOT IN (1,3,5,7,9,10,13,15))
        ORDER BY st.EXPECTED_DELIVERY_DATE DESC;
END$$

DELIMITER ;

DELIMITER $$

USE `fasp`$$

DROP PROCEDURE IF EXISTS `getNotLinkedShipments`$$

CREATE DEFINER=`faspUser`@`%` PROCEDURE `getNotLinkedShipments`(PROGRAM_ID INT(10),  VERSION_ID INT (10))
BEGIN
    SET @programId = PROGRAM_ID;
    SET @procurementAgentId = 1;
    SET @versionId = VERSION_ID;
    IF @versionId = -1 THEN
        SELECT MAX(pv.VERSION_ID) INTO @versionId FROM rm_program_version pv WHERE pv.PROGRAM_ID=@programId;
    END IF;
   
SELECT
        st.SHIPMENT_ID, st.SHIPMENT_TRANS_ID, st.SHIPMENT_QTY, st.EXPECTED_DELIVERY_DATE, st.PRODUCT_COST, st.SKU_CODE,
        st.PROCUREMENT_AGENT_ID, st.PROCUREMENT_AGENT_CODE, st.`COLOR_HTML_CODE`, st.`PROCUREMENT_AGENT_LABEL_ID`, st.`PROCUREMENT_AGENT_LABEL_EN`, st.`PROCUREMENT_AGENT_LABEL_FR`, st.`PROCUREMENT_AGENT_LABEL_SP`, st.`PROCUREMENT_AGENT_LABEL_PR`,
        st.FUNDING_SOURCE_ID, st.FUNDING_SOURCE_CODE, st.`FUNDING_SOURCE_LABEL_ID`, st.`FUNDING_SOURCE_LABEL_EN`, st.`FUNDING_SOURCE_LABEL_FR`, st.`FUNDING_SOURCE_LABEL_SP`, st.`FUNDING_SOURCE_LABEL_PR`,
        st.BUDGET_ID, st.BUDGET_CODE, st.`BUDGET_LABEL_ID`, st.`BUDGET_LABEL_EN`, st.`BUDGET_LABEL_FR`, st.`BUDGET_LABEL_SP`, st.`BUDGET_LABEL_PR`,
        st.SHIPMENT_STATUS_ID, st.`SHIPMENT_STATUS_LABEL_ID`, st.`SHIPMENT_STATUS_LABEL_EN`, st.`SHIPMENT_STATUS_LABEL_FR`, st.`SHIPMENT_STATUS_LABEL_SP`, st.`SHIPMENT_STATUS_LABEL_PR`,
        st.PLANNING_UNIT_ID, st.`PLANNING_UNIT_LABEL_ID`, st.`PLANNING_UNIT_LABEL_EN`, st.`PLANNING_UNIT_LABEL_FR`, st.`PLANNING_UNIT_LABEL_SP`, st.`PLANNING_UNIT_LABEL_PR`,
        st.ORDER_NO, st.PRIME_LINE_NO,st.`NOTES`
FROM (
        SELECT
            s.SHIPMENT_ID, st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE,st.SHIPMENT_QTY, st.RATE, st.PRODUCT_COST, st.FREIGHT_COST, st.ACCOUNT_FLAG, st.SHIPMENT_TRANS_ID, papu.SKU_CODE,
            pa.`PROCUREMENT_AGENT_ID`, pa.`PROCUREMENT_AGENT_CODE`, pa.`COLOR_HTML_CODE`, pa.`LABEL_ID` `PROCUREMENT_AGENT_LABEL_ID`, pa.`LABEL_EN` `PROCUREMENT_AGENT_LABEL_EN`, pa.LABEL_FR `PROCUREMENT_AGENT_LABEL_FR`, pa.LABEL_SP `PROCUREMENT_AGENT_LABEL_SP`, pa.LABEL_PR `PROCUREMENT_AGENT_LABEL_PR`,
            fs.`FUNDING_SOURCE_ID`, fs.`FUNDING_SOURCE_CODE`, fs.LABEL_ID `FUNDING_SOURCE_LABEL_ID`, fs.LABEL_EN `FUNDING_SOURCE_LABEL_EN`, fs.LABEL_FR `FUNDING_SOURCE_LABEL_FR`, fs.LABEL_SP `FUNDING_SOURCE_LABEL_SP`, fs.LABEL_PR `FUNDING_SOURCE_LABEL_PR`,
            shs.SHIPMENT_STATUS_ID, shs.LABEL_ID `SHIPMENT_STATUS_LABEL_ID`, shs.LABEL_EN `SHIPMENT_STATUS_LABEL_EN`, shs.LABEL_FR `SHIPMENT_STATUS_LABEL_FR`, shs.LABEL_SP `SHIPMENT_STATUS_LABEL_SP`, shs.LABEL_PR `SHIPMENT_STATUS_LABEL_PR`,
            sc.CURRENCY_ID `SHIPMENT_CURRENCY_ID`, sc.`CURRENCY_CODE` `SHIPMENT_CURRENCY_CODE`, s.CONVERSION_RATE_TO_USD `SHIPMENT_CONVERSION_RATE_TO_USD`,
            sc.LABEL_ID `SHIPMENT_CURRENCY_LABEL_ID`, sc.LABEL_EN `SHIPMENT_CURRENCY_LABEL_EN`, sc.LABEL_FR `SHIPMENT_CURRENCY_LABEL_FR`, sc.LABEL_SP `SHIPMENT_CURRENCY_LABEL_SP`, sc.LABEL_PR `SHIPMENT_CURRENCY_LABEL_PR`,
            st.ACTIVE, st.`ORDER_NO`, st.`PRIME_LINE_NO`,
            b.BUDGET_ID, b.BUDGET_CODE, b.LABEL_ID `BUDGET_LABEL_ID`, b.LABEL_EN `BUDGET_LABEL_EN`, b.LABEL_FR `BUDGET_LABEL_FR`, b.LABEL_SP `BUDGET_LABEL_SP`, b.LABEL_PR `BUDGET_LABEL_PR`,
            st.PLANNING_UNIT_ID, pu.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pu.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pu.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pu.LABEL_SP `PLANNING_UNIT_LABEL_SP`, pu.LABEL_PR `PLANNING_UNIT_LABEL_PR`,st.`NOTES`
FROM (
    SELECT st.SHIPMENT_ID, MAX(st.VERSION_ID) MAX_VERSION_ID FROM rm_shipment s LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID WHERE (@versiONId=-1 OR st.VERSION_ID<=@versiONId) AND s.PROGRAM_ID=@programId GROUP BY st.SHIPMENT_ID
) ts
    LEFT JOIN rm_shipment s ON ts.SHIPMENT_ID=s.SHIPMENT_ID
    LEFT JOIN rm_shipment_trans st ON ts.SHIPMENT_ID=st.SHIPMENT_ID AND ts.MAX_VERSION_ID=st.VERSION_ID
    LEFT JOIN vw_planning_unit pu ON st.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID
    LEFT JOIN vw_procurement_agent pa ON st.PROCUREMENT_AGENT_ID=pa.PROCUREMENT_AGENT_ID
    LEFT JOIN vw_funding_source fs ON st.FUNDING_SOURCE_ID=fs.FUNDING_SOURCE_ID
    LEFT JOIN vw_shipment_status shs ON st.SHIPMENT_STATUS_ID=shs.SHIPMENT_STATUS_ID
    LEFT JOIN vw_currency sc ON s.CURRENCY_ID=sc.CURRENCY_ID
    LEFT JOIN vw_budget b ON st.BUDGET_ID=b.BUDGET_ID
    LEFT JOIN rm_manual_tagging mt ON mt.SHIPMENT_ID=ts.SHIPMENT_ID AND mt.ACTIVE
    LEFT JOIN rm_procurement_agent_planning_unit papu ON papu.PROCUREMENT_AGENT_ID=pa.PROCUREMENT_AGENT_ID AND papu.PLANNING_UNIT_ID=st.PLANNING_UNIT_ID
    WHERE st.ERP_FLAG=0 AND st.ACTIVE AND st.ACCOUNT_FLAG AND st.SHIPMENT_STATUS_ID IN (3,4,5,6,9,7)  AND (mt.SHIPMENT_ID IS NULL OR mt.ACTIVE=0) AND st.PROCUREMENT_AGENT_ID=@procurementAgentId
) st
WHERE
IF(COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) < CURDATE() - INTERVAL 6 MONTH, st.SHIPMENT_STATUS_ID!=7 , st.SHIPMENT_STATUS_ID IN (3,4,5,6,9,7))
ORDER BY COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE);
END$$

DELIMITER ;

DELIMITER $$

USE `fasp`$$

DROP PROCEDURE IF EXISTS `getShipmentListForManualLinking`$$

CREATE DEFINER=`faspUser`@`%` PROCEDURE `getShipmentListForManualLinking`(PROGRAM_ID INT(10), PLANNING_UNIT_ID TEXT, VERSION_ID INT (10))
BEGIN
    SET @programId = PROGRAM_ID;
    SET @planningUnitIds = PLANNING_UNIT_ID;
    SET @procurementAgentId = 1;
    SET @versionId = VERSION_ID;
    IF @versionId = -1 THEN
        SELECT MAX(pv.VERSION_ID) INTO @versionId FROM rm_program_version pv WHERE pv.PROGRAM_ID=@programId;
    END IF;
   
SELECT
        st.SHIPMENT_ID, st.SHIPMENT_TRANS_ID, st.SHIPMENT_QTY, st.EXPECTED_DELIVERY_DATE, st.PRODUCT_COST, st.SKU_CODE,
        st.PROCUREMENT_AGENT_ID, st.PROCUREMENT_AGENT_CODE, st.`COLOR_HTML_CODE`, st.`PROCUREMENT_AGENT_LABEL_ID`, st.`PROCUREMENT_AGENT_LABEL_EN`, st.`PROCUREMENT_AGENT_LABEL_FR`, st.`PROCUREMENT_AGENT_LABEL_SP`, st.`PROCUREMENT_AGENT_LABEL_PR`,
        st.FUNDING_SOURCE_ID, st.FUNDING_SOURCE_CODE, st.`FUNDING_SOURCE_LABEL_ID`, st.`FUNDING_SOURCE_LABEL_EN`, st.`FUNDING_SOURCE_LABEL_FR`, st.`FUNDING_SOURCE_LABEL_SP`, st.`FUNDING_SOURCE_LABEL_PR`,
        st.BUDGET_ID, st.BUDGET_CODE, st.`BUDGET_LABEL_ID`, st.`BUDGET_LABEL_EN`, st.`BUDGET_LABEL_FR`, st.`BUDGET_LABEL_SP`, st.`BUDGET_LABEL_PR`,
        st.SHIPMENT_STATUS_ID, st.`SHIPMENT_STATUS_LABEL_ID`, st.`SHIPMENT_STATUS_LABEL_EN`, st.`SHIPMENT_STATUS_LABEL_FR`, st.`SHIPMENT_STATUS_LABEL_SP`, st.`SHIPMENT_STATUS_LABEL_PR`,
        st.PLANNING_UNIT_ID, st.`PLANNING_UNIT_LABEL_ID`, st.`PLANNING_UNIT_LABEL_EN`, st.`PLANNING_UNIT_LABEL_FR`, st.`PLANNING_UNIT_LABEL_SP`, st.`PLANNING_UNIT_LABEL_PR`,
        st.ORDER_NO, st.PRIME_LINE_NO,st.`NOTES`
FROM (
        SELECT
            s.SHIPMENT_ID, st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE,st.SHIPMENT_QTY, st.RATE, st.PRODUCT_COST, st.FREIGHT_COST, st.ACCOUNT_FLAG, st.SHIPMENT_TRANS_ID, papu.SKU_CODE,
            pa.`PROCUREMENT_AGENT_ID`, pa.`PROCUREMENT_AGENT_CODE`, pa.`COLOR_HTML_CODE`, pa.`LABEL_ID` `PROCUREMENT_AGENT_LABEL_ID`, pa.`LABEL_EN` `PROCUREMENT_AGENT_LABEL_EN`, pa.LABEL_FR `PROCUREMENT_AGENT_LABEL_FR`, pa.LABEL_SP `PROCUREMENT_AGENT_LABEL_SP`, pa.LABEL_PR `PROCUREMENT_AGENT_LABEL_PR`,
            fs.`FUNDING_SOURCE_ID`, fs.`FUNDING_SOURCE_CODE`, fs.LABEL_ID `FUNDING_SOURCE_LABEL_ID`, fs.LABEL_EN `FUNDING_SOURCE_LABEL_EN`, fs.LABEL_FR `FUNDING_SOURCE_LABEL_FR`, fs.LABEL_SP `FUNDING_SOURCE_LABEL_SP`, fs.LABEL_PR `FUNDING_SOURCE_LABEL_PR`,
            shs.SHIPMENT_STATUS_ID, shs.LABEL_ID `SHIPMENT_STATUS_LABEL_ID`, shs.LABEL_EN `SHIPMENT_STATUS_LABEL_EN`, shs.LABEL_FR `SHIPMENT_STATUS_LABEL_FR`, shs.LABEL_SP `SHIPMENT_STATUS_LABEL_SP`, shs.LABEL_PR `SHIPMENT_STATUS_LABEL_PR`,
            sc.CURRENCY_ID `SHIPMENT_CURRENCY_ID`, sc.`CURRENCY_CODE` `SHIPMENT_CURRENCY_CODE`, s.CONVERSION_RATE_TO_USD `SHIPMENT_CONVERSION_RATE_TO_USD`,
            sc.LABEL_ID `SHIPMENT_CURRENCY_LABEL_ID`, sc.LABEL_EN `SHIPMENT_CURRENCY_LABEL_EN`, sc.LABEL_FR `SHIPMENT_CURRENCY_LABEL_FR`, sc.LABEL_SP `SHIPMENT_CURRENCY_LABEL_SP`, sc.LABEL_PR `SHIPMENT_CURRENCY_LABEL_PR`,
            st.ACTIVE, st.`ORDER_NO`, st.`PRIME_LINE_NO`,
            b.BUDGET_ID, b.BUDGET_CODE, b.LABEL_ID `BUDGET_LABEL_ID`, b.LABEL_EN `BUDGET_LABEL_EN`, b.LABEL_FR `BUDGET_LABEL_FR`, b.LABEL_SP `BUDGET_LABEL_SP`, b.LABEL_PR `BUDGET_LABEL_PR`,
            st.PLANNING_UNIT_ID, pu.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pu.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pu.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pu.LABEL_SP `PLANNING_UNIT_LABEL_SP`, pu.LABEL_PR `PLANNING_UNIT_LABEL_PR`,st.`NOTES`
FROM (
    SELECT st.SHIPMENT_ID, MAX(st.VERSION_ID) MAX_VERSION_ID FROM rm_shipment s LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID WHERE (@versiONId=-1 OR st.VERSION_ID<=@versiONId) AND s.PROGRAM_ID=@programId GROUP BY st.SHIPMENT_ID
) ts
    LEFT JOIN rm_shipment s ON ts.SHIPMENT_ID=s.SHIPMENT_ID
    LEFT JOIN rm_shipment_trans st ON ts.SHIPMENT_ID=st.SHIPMENT_ID AND ts.MAX_VERSION_ID=st.VERSION_ID
    LEFT JOIN vw_planning_unit pu ON st.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID
    LEFT JOIN vw_procurement_agent pa ON st.PROCUREMENT_AGENT_ID=pa.PROCUREMENT_AGENT_ID
    LEFT JOIN vw_funding_source fs ON st.FUNDING_SOURCE_ID=fs.FUNDING_SOURCE_ID
    LEFT JOIN vw_shipment_status shs ON st.SHIPMENT_STATUS_ID=shs.SHIPMENT_STATUS_ID
    LEFT JOIN vw_currency sc ON s.CURRENCY_ID=sc.CURRENCY_ID
    LEFT JOIN vw_budget b ON st.BUDGET_ID=b.BUDGET_ID
    LEFT JOIN rm_manual_tagging mt ON mt.SHIPMENT_ID=ts.SHIPMENT_ID AND mt.ACTIVE
    LEFT JOIN rm_procurement_agent_planning_unit papu ON papu.PROCUREMENT_AGENT_ID=pa.PROCUREMENT_AGENT_ID AND papu.PLANNING_UNIT_ID=st.PLANNING_UNIT_ID
    WHERE st.ERP_FLAG=0 AND st.ACTIVE AND st.ACCOUNT_FLAG AND st.SHIPMENT_STATUS_ID IN (3,4,5,6,9,7) AND (LENGTH(@planningUnitIds)=0 OR FIND_IN_SET(st.PLANNING_UNIT_ID,@planningUnitIds))   AND (mt.SHIPMENT_ID IS NULL OR mt.ACTIVE=0) AND st.PROCUREMENT_AGENT_ID=@procurementAgentId
) st
WHERE
IF(COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) < CURDATE() - INTERVAL 6 MONTH, st.SHIPMENT_STATUS_ID!=7 , st.SHIPMENT_STATUS_ID IN (3,4,5,6,9,7))
ORDER BY COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) DESC;
END$$

DELIMITER ;