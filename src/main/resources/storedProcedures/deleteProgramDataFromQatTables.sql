USE `fasp`;
DROP procedure IF EXISTS `deleteProgramDataFromQatTables`;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `deleteProgramDataFromQatTables`(PROGRAM_ID INT(10))
BEGIN
SET @programId = PROGRAM_ID;
-- shipment tables rm_shipment,rm_shipment_trans,rm_shipment_trans_batch_info 
delete sbi.* from rm_shipment_trans_batch_info sbi
left join rm_shipment_trans st on st.SHIPMENT_TRANS_ID=sbi.SHIPMENT_TRANS_ID
left join rm_shipment s on s.SHIPMENT_ID=st.SHIPMENT_ID
where s.PROGRAM_ID=@programId;

delete st.* from rm_shipment_trans st 
left join rm_shipment s on s.SHIPMENT_ID=st.SHIPMENT_ID
where s.PROGRAM_ID=@programId;

delete from rm_shipment where rm_shipment.PROGRAM_ID=@programId;

-- inventory tables rm_inventory,rm_inventory_trans,rm_inventory_trans_batch_info
delete ibi.* from rm_inventory_trans_batch_info ibi 
left join rm_inventory_trans it on it.INVENTORY_TRANS_ID=ibi.INVENTORY_TRANS_ID
left join rm_inventory i on i.INVENTORY_ID=it.INVENTORY_ID
where i.PROGRAM_ID=@programId;

delete it.* from rm_inventory_trans it 
left join rm_inventory i on i.INVENTORY_ID=it.INVENTORY_ID
where i.PROGRAM_ID=@programId;

delete from rm_inventory where rm_inventory.PROGRAM_ID=@programId;

-- consumption tables rm_consumption,rm_consumption_trans,rm_consumption_trans_batch_info
delete cbi.* from rm_consumption_trans_batch_info cbi
left join rm_consumption_trans ct on ct.CONSUMPTION_TRANS_ID=cbi.CONSUMPTION_TRANS_ID
left join rm_consumption c on c.CONSUMPTION_ID=ct.CONSUMPTION_ID
where c.PROGRAM_ID=@programId;

delete ct.* from rm_consumption_trans ct 
left join rm_consumption c on c.CONSUMPTION_ID=ct.CONSUMPTION_ID
where c.PROGRAM_ID=@programId;

delete from rm_consumption where rm_consumption.PROGRAM_ID=@programId;

-- rm_supply_plan_batch_qty,rm_supply_plan_amc,rm_batch_info
delete from rm_supply_plan_batch_qty where rm_supply_plan_batch_qty.PROGRAM_ID=@programId;
delete from rm_supply_plan_amc where rm_supply_plan_amc.PROGRAM_ID=@programId;
delete from rm_batch_info where rm_batch_info.PROGRAM_ID=@programId;

-- rm_problem_report_trans,rm_problem_report
delete prt.* from rm_problem_report_trans prt 
left join rm_problem_report pr on pr.PROBLEM_REPORT_ID=prt.PROBLEM_REPORT_ID
where pr.PROGRAM_ID=@programId;

delete from rm_problem_report where rm_problem_report.PROGRAM_ID=@programId;

-- rm_program_planning_unit_procurement_agent,rm_program_planning_unit
delete pupa.* from rm_program_planning_unit_procurement_agent pupa
left join rm_program_planning_unit ppu on ppu.PROGRAM_PLANNING_UNIT_ID=pupa.PROGRAM_PLANNING_UNIT_ID
where ppu.PROGRAM_ID=@programId;

delete from rm_program_planning_unit where rm_program_planning_unit.PROGRAM_ID=@programId;

-- rm_integration_program_completed,rm_integration_program
delete ic.* from rm_integration_program_completed ic 
left join rm_integration_program ip on ip.INTEGRATION_ID=ic.INTEGRATION_ID
where ip.PROGRAM_ID=@programId;

delete from rm_integration_program where rm_integration_program.PROGRAM_ID=@programId;

-- rm_program_region,rm_program_version_trans,rm_program_version
delete from rm_program_region where rm_program_region.PROGRAM_ID=@programId;

delete pvt.* from rm_program_version_trans pvt 
left join rm_program_version pv on pv.PROGRAM_VERSION_ID=pvt.PROGRAM_VERSION_ID
where pv.PROGRAM_ID=@programId;

delete from rm_program_version where rm_program_version.PROGRAM_ID=@programId;

-- rm_budget,rm_data_source,us_user_acl,rm_program

delete from rm_budget where rm_budget.PROGRAM_ID=@programId;
delete from rm_data_source where rm_data_source.PROGRAM_ID=@programId;
delete from us_user_acl where us_user_acl.PROGRAM_ID=@programId;
delete from rm_program where rm_program.PROGRAM_ID=@programId;
 
END$$

DELIMITER ;



