update ap_label al set al.LABEL_EN="Funnel"
where al.LABEL_ID =(select nt.LABEL_ID from ap_node_type nt where nt.NODE_TYPE_ID=6);

update ap_label al set al.LABEL_FR="Entonnoir"
where al.LABEL_ID =(select nt.LABEL_ID from ap_node_type nt where nt.NODE_TYPE_ID=6);

update ap_label al set al.LABEL_SP="Embudo"
where al.LABEL_ID =(select nt.LABEL_ID from ap_node_type nt where nt.NODE_TYPE_ID=6);

update ap_label al set al.LABEL_PR="Funil"
where al.LABEL_ID =(select nt.LABEL_ID from ap_node_type nt where nt.NODE_TYPE_ID=6);