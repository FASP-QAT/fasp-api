UPDATE rm_inventory_trans it
SET it.INVENTORY_DATE=date_sub(date_add(it.INVENTORY_DATE, interval 1 month), interval 1 day)
where DAY(it.INVENTORY_DATE)=1;

