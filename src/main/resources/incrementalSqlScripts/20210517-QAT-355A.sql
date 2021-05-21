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