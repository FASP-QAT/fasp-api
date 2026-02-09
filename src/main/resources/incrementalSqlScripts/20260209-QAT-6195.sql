INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.mt.reminders2D1a1A','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'If the ERP unit is different from your QAT planning unit, you can add an ARU with the matching ERP unit. After Master Data Syncing, the new ARU will be available in the dropdown selection when linking a new shipment.'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Si l\'unité de gestion de votre système ERP est différente de votre unité de planification QAT, vous pouvez ajouter une unité ARU correspondant à l\'unité ERP. Après la synchronisation des données de base, la nouvelle unité ARU sera disponible dans la liste déroulante lors de la création d\'une nouvelle expédition.'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Si la unidad de ERP es diferente de la unidad de planificación de QAT, puede agregar una unidad ARU con la unidad de ERP correspondiente. Después de la sincronización de los datos maestros, la nueva unidad ARU estará disponible en el menú desplegable al vincular un nuevo envío.'); -- sp 
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Se a unidade do sistema ERP for diferente da sua unidade de planejamento QAT, você poderá adicionar uma ARU com a unidade do ERP correspondente. Após a sincronização dos dados mestre, a nova ARU estará disponível na lista suspensa ao vincular um novo envio.'); -- pr



update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT = 'Your order in the ERP system is 20 packs (10 chocolate bars per pack), but your supply plan is in packs of 100 chocolate bars. To calculate the conversion factor and add it as a new ARU, consider how many single units you have in the ERP pack size (10) and divide by how many single units you have in your QAT planning unit (100). In this example, the conversion factor is 10 bars / 100 bars = 0.1. Multiplying the ERP shipment quantity by 0.1 (or dividing by 10) results in the converted QAT shipment quantity of 2, which is the number that will be used in your supply plan.'
where l.LABEL_CODE='static.mt.reminders2D3' and ll.LANGUAGE_ID=1;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Votre commande dans le système ERP est de 20 paquets (10 barres de chocolat par paquet), mais votre plan d\'approvisionnement est basé sur des paquets de 100 barres de chocolat. Pour calculer le facteur de conversion et l\'ajouter comme nouvelle unité de référence, déterminez le nombre d\'unités individuelles dans la taille de paquet ERP (10) et divisez-le par le nombre d\'unités individuelles dans votre unité de planification QAT (100). Dans cet exemple, le facteur de conversion est de 10 barres / 100 barres = 0,1. En multipliant la quantité d\'expédition ERP par 0,1 (ou en la divisant par 10), on obtient la quantité d\'expédition QAT convertie de 2, qui est le nombre qui sera utilisé dans votre plan d\'approvisionnement.'
where l.LABEL_CODE='static.mt.reminders2D3' and ll.LANGUAGE_ID=2;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Su pedido en el sistema ERP es de 20 paquetes (10 tabletas de chocolate por paquete), pero su plan de suministro se basa en paquetes de 100 tabletas de chocolate. Para calcular el factor de conversión y agregarlo como una nueva unidad de referencia, considere cuántas unidades individuales hay en el tamaño del paquete del ERP (10) y divídalo entre la cantidad de unidades individuales que hay en su unidad de planificación QAT (100). En este ejemplo, el factor de conversión es 10 tabletas / 100 tabletas = 0,1. Al multiplicar la cantidad de envío del ERP por 0,1 (o dividirla entre 10), se obtiene la cantidad de envío convertida para QAT, que es 2, el número que se utilizará en su plan de suministro.'
where l.LABEL_CODE='static.mt.reminders2D3' and ll.LANGUAGE_ID=3;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Seu pedido no sistema ERP é de 20 pacotes (10 barras de chocolate por pacote), mas seu plano de suprimentos utiliza pacotes de 100 barras de chocolate. Para calcular o fator de conversão e adicioná-lo como uma nova unidade de medida, considere quantas unidades individuais você tem no tamanho do pacote do ERP (10) e divida pelo número de unidades individuais na unidade de planejamento do QAT (100). Neste exemplo, o fator de conversão é 10 barras / 100 barras = 0,1. Multiplicando a quantidade de envio do ERP por 0,1 (ou dividindo por 10), obtemos a quantidade de envio convertida para o QAT, que é 2, o número que será usado em seu plano de suprimentos.'
where l.LABEL_CODE='static.mt.reminders2D3' and ll.LANGUAGE_ID=4;


update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT = 'Once linked, shipments will be non-editable other than the notes field and ARU field, which are editable only on the ERP linking screens.'
where l.LABEL_CODE='static.mt.reminders3A' and ll.LANGUAGE_ID=1;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Une fois les expéditions liées, elles ne seront plus modifiables, à l\'exception du champ des notes et du champ ARU, qui ne sont modifiables que sur les écrans de liaison du système ERP.'
where l.LABEL_CODE='static.mt.reminders3A' and ll.LANGUAGE_ID=2;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Una vez vinculados, los envíos no se podrán modificar, excepto los campos de notas y el campo ARU, que solo se pueden editar en las pantallas de vinculación del sistema ERP.'
where l.LABEL_CODE='static.mt.reminders3A' and ll.LANGUAGE_ID=3;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Uma vez vinculados, os envios não poderão ser editados, exceto os campos de notas e ARU, que são editáveis ​​apenas nas telas de vinculação do sistema ERP.'
where l.LABEL_CODE='static.mt.reminders3A' and ll.LANGUAGE_ID=4;
