INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.shipment.shipmentCostOverviewNotes','1');

SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'No shipments found. Ensure that you have shipments within this time period. If “Report Based On Submitted Date” is selected, please ensure you have manually entered actual submitted dates by right clicking on shipment records and selecting “Show Shipment Dates”.');-- en

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Aucune expédition trouvée. Assurez-vous d’avoir des expéditions dans ce délai. Si « Rapport basé sur la date de soumission » est sélectionné, assurez-vous d\'avoir saisi manuellement les dates de soumission réelles en cliquant avec le bouton droit sur les enregistrements d\'expédition et en sélectionnant « Afficher les dates d\'expédition ».');-- fr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Nenhuma remessa encontrada. Certifique-se de ter remessas dentro deste período de tempo. Se “Relatório baseado na data de envio” for selecionado, certifique-se de ter inserido manualmente as datas reais de envio clicando com o botão direito nos registros de remessa e selecionando “Mostrar datas de remessa”.');-- pr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'No se encontraron envíos. Asegúrese de tener envíos dentro de este período de tiempo. Si se selecciona "Informe basado en la fecha de envío", asegúrese de haber ingresado manualmente las fechas de envío reales haciendo clic derecho en los registros de envío y seleccionando "Mostrar fechas de envío".');-- sp
