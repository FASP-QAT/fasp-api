update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Duplicate Budget. Please choose another Program, Budget Display Name, or both.'
where l.LABEL_CODE='static.budget.duplicateBudget' and ll.LANGUAGE_ID=1;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Budget en double. Veuillez choisir un autre programme, un autre nom d\'affichage du budget, ou les deux.'
where l.LABEL_CODE='static.budget.duplicateBudget' and ll.LANGUAGE_ID=2;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Presupuesto duplicado. Por favor, elija otro programa, nombre de visualización del presupuesto o ambos.'
where l.LABEL_CODE='static.budget.duplicateBudget' and ll.LANGUAGE_ID=3;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Orçamento duplicado. Por favor, escolha outro programa, nome de exibição do orçamento ou ambos.'
where l.LABEL_CODE='static.budget.duplicateBudget' and ll.LANGUAGE_ID=4;