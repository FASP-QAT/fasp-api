update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='= Opening Balance - Projected Expired Stock + Shipments - Consumption + Adjustments'
where l.LABEL_CODE='static.showFormula.endingBalance1' and ll.LANGUAGE_ID=1;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='= Solde d`ouverture - Stock expiré projeté + Expéditions - Consommation + Ajustements'
where l.LABEL_CODE='static.showFormula.endingBalance1' and ll.LANGUAGE_ID=2;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='= Saldo de Apertura - Stock Vencido Proyectado + Envíos - Consumo + Ajustes'
where l.LABEL_CODE='static.showFormula.endingBalance1' and ll.LANGUAGE_ID=3;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='= Saldo inicial - Estoque expirado projetado + Remessas - Consumo + Ajustes'
where l.LABEL_CODE='static.showFormula.endingBalance1' and ll.LANGUAGE_ID=4;