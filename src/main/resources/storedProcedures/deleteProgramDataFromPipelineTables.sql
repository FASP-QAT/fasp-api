USE `fasp`;
DROP procedure IF EXISTS `deleteProgramDataFromPipelineTables`;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `deleteProgramDataFromPipelineTables`(PIPELINE_ID INT(10))
BEGIN
SET @pipelineId = PIPELINE_ID;
 
delete from qat_temp_shipment where qat_temp_shipment.PIPELINE_ID=@pipelineId;
delete from qat_temp_inventory where qat_temp_inventory.PIPELINE_ID=@pipelineId;
delete from qat_temp_consumption where qat_temp_consumption.PIPELINE_ID=@pipelineId;

delete from qat_temp_data_source where qat_temp_data_source.PIPELINE_ID=@pipelineId;
delete from qat_temp_funding_source where qat_temp_funding_source.PIPELINE_ID=@pipelineId;
delete from qat_temp_procurement_agent where qat_temp_procurement_agent.PIPELINE_ID=@pipelineId;

delete from qat_temp_program_region where qat_temp_program_region.PIPELINE_ID=@pipelineId;
delete from qat_temp_program_planning_unit where qat_temp_program_planning_unit.PIPELINE_ID=@pipelineId;
delete from qat_temp_program where qat_temp_program.PIPELINE_ID=@pipelineId; 

delete from adb_shipment where adb_shipment.PIPELINE_ID=@pipelineId;
delete from adb_inventory where adb_inventory.PIPELINE_ID=@pipelineId;
delete from adb_consumption where adb_consumption.PIPELINE_ID=@pipelineId;

delete from adb_tblbe_version where adb_tblbe_version.PIPELINE_ID=@pipelineId;
delete from adb_tblimportproducts where adb_tblimportproducts.PIPELINE_ID=@pipelineId;
delete from adb_tblimportrecords where adb_tblimportrecords.PIPELINE_ID=@pipelineId;

delete from adb_paste_errors where adb_paste_errors.PIPELINE_ID=@pipelineId;
delete from adb_monthlystockarchive where adb_monthlystockarchive.PIPELINE_ID=@pipelineId;
delete from adb_productsuppliercasesize where adb_productsuppliercasesize.PIPELINE_ID=@pipelineId;

-- delete from adb_shipmentstatus where adb_shipmentstatus.PIPELINE_ID=@pipelineId;
delete from adb_commodityprice where adb_commodityprice.PIPELINE_ID=@pipelineId;
delete from adb_productfreightcost where adb_productfreightcost.PIPELINE_ID=@pipelineId;

delete from adb_source where adb_source.PIPELINE_ID=@pipelineId;
delete from adb_method where adb_method.PIPELINE_ID=@pipelineId;
delete from adb_fundingsource where adb_fundingsource.PIPELINE_ID=@pipelineId;

delete from adb_datasource where adb_datasource.PIPELINE_ID=@pipelineId;
delete from adb_product where adb_product.PIPELINE_ID=@pipelineId;
delete from adb_programinfo where adb_programinfo.PIPELINE_ID=@pipelineId;

delete from adb_pipeline where adb_pipeline.PIPELINE_ID=@pipelineId;

END$$

DELIMITER ;

