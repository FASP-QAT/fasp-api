update ap_label al set al.LABEL_EN="Funnel"
where al.LABEL_ID =(select nt.LABEL_ID from ap_node_type nt where nt.NODE_TYPE_ID=6);