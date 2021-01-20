DROP TABLE IF EXISTS tmp_id;
CREATE TABLE `tmp_id` (`ID` INT NOT NULL,  PRIMARY KEY (`ID`));

-- Program 2029 - Planning Unit 4248 x3000
TRUNCATE TABLE tmp_id;
SET @programId = 2029;
SET @planningUnitId = 4248;
SET @multiplier=3000;
INSERT INTO tmp_id SELECT c.CONSUMPTION_ID FROM rm_consumption c LEFT JOIN rm_consumption_trans ct ON c.CONSUMPTION_ID=ct.CONSUMPTION_ID WHERE c.PROGRAM_ID=@programId and ct.PLANNING_UNIT_ID=@planningUnitId and ct.VERSION_ID=1;
UPDATE rm_consumption c LEFT JOIN rm_consumption_trans ct ON c.CONSUMPTION_ID=ct.CONSUMPTION_ID SET ct.CONSUMPTION_RCPU_QTY=ct.CONSUMPTION_RCPU_QTY*@multiplier WHERE c.CONSUMPTION_ID IN (SELECT ID FROM tmp_id);
UPDATE rm_consumption c LEFT JOIN rm_consumption_trans ct ON c.CONSUMPTION_ID=ct.CONSUMPTION_ID LEFT JOIN rm_realm_country_planning_unit rcpu ON ct.REALM_COUNTRY_PLANNING_UNIT_ID=rcpu.REALM_COUNTRY_PLANNING_UNIT_ID SET ct.CONSUMPTION_QTY=ct.CONSUMPTION_RCPU_QTY*rcpu.MULTIPLIER WHERE c.CONSUMPTION_ID IN (SELECT ID FROM tmp_id);
UPDATE rm_consumption c LEFT JOIN rm_consumption_trans ct ON c.CONSUMPTION_ID=ct.CONSUMPTION_ID LEFT JOIN rm_consumption_trans_batch_info ctbi ON ct.CONSUMPTION_TRANS_ID=ctbi.CONSUMPTION_TRANS_ID SET ctbi.CONSUMPTION_QTY=ctbi.CONSUMPTION_QTY*@multiplier WHERE c.CONSUMPTION_ID IN (SELECT ID FROM tmp_id);
TRUNCATE TABLE tmp_id;
INSERT INTO tmp_id SELECT i.INVENTORY_ID from rm_inventory i left join rm_inventory_trans it on i.INVENTORY_ID=it.INVENTORY_ID LEFT JOIN rm_realm_country_planning_unit rcpu ON it.REALM_COUNTRY_PLANNING_UNIT_ID=rcpu.REALM_COUNTRY_PLANNING_UNIT_ID where i.PROGRAM_ID=@programId and rcpu.PLANNING_UNIT_ID=@planningUnitId;
UPDATE rm_inventory i left join rm_inventory_trans it on i.INVENTORY_ID=it.INVENTORY_ID LEFT JOIN rm_realm_country_planning_unit rcpu ON it.REALM_COUNTRY_PLANNING_UNIT_ID=rcpu.REALM_COUNTRY_PLANNING_UNIT_ID SET it.ACTUAL_QTY=it.ACTUAL_QTY*@multiplier, it.ADJUSTMENT_QTY=it.ADJUSTMENT_QTY*@multiplier WHERE i.INVENTORY_ID IN (SELECT ID FROM tmp_id);
UPDATE rm_inventory i left join rm_inventory_trans it on i.INVENTORY_ID=it.INVENTORY_ID LEFT JOIN rm_realm_country_planning_unit rcpu ON it.REALM_COUNTRY_PLANNING_UNIT_ID=rcpu.REALM_COUNTRY_PLANNING_UNIT_ID LEFT JOIN rm_inventory_trans_batch_info itbi ON it.INVENTORY_TRANS_ID=itbi.INVENTORY_TRANS_ID SET itbi.ACTUAL_QTY=itbi.ACTUAL_QTY*@multiplier, itbi.ADJUSTMENT_QTY=itbi.ADJUSTMENT_QTY*@multiplier WHERE i.INVENTORY_ID IN (SELECT ID FROM tmp_id);

-- Program 2029 - Planning Unit 4615 x100
TRUNCATE TABLE tmp_id;
SET @programId = 2029;
SET @planningUnitId = 4615;
SET @multiplier=100;
INSERT INTO tmp_id SELECT c.CONSUMPTION_ID FROM rm_consumption c LEFT JOIN rm_consumption_trans ct ON c.CONSUMPTION_ID=ct.CONSUMPTION_ID WHERE c.PROGRAM_ID=@programId and ct.PLANNING_UNIT_ID=@planningUnitId and ct.VERSION_ID=1;
UPDATE rm_consumption c LEFT JOIN rm_consumption_trans ct ON c.CONSUMPTION_ID=ct.CONSUMPTION_ID SET ct.CONSUMPTION_RCPU_QTY=ct.CONSUMPTION_RCPU_QTY*@multiplier WHERE c.CONSUMPTION_ID IN (SELECT ID FROM tmp_id);
UPDATE rm_consumption c LEFT JOIN rm_consumption_trans ct ON c.CONSUMPTION_ID=ct.CONSUMPTION_ID LEFT JOIN rm_realm_country_planning_unit rcpu ON ct.REALM_COUNTRY_PLANNING_UNIT_ID=rcpu.REALM_COUNTRY_PLANNING_UNIT_ID SET ct.CONSUMPTION_QTY=ct.CONSUMPTION_RCPU_QTY*rcpu.MULTIPLIER WHERE c.CONSUMPTION_ID IN (SELECT ID FROM tmp_id);
UPDATE rm_consumption c LEFT JOIN rm_consumption_trans ct ON c.CONSUMPTION_ID=ct.CONSUMPTION_ID LEFT JOIN rm_consumption_trans_batch_info ctbi ON ct.CONSUMPTION_TRANS_ID=ctbi.CONSUMPTION_TRANS_ID SET ctbi.CONSUMPTION_QTY=ctbi.CONSUMPTION_QTY*@multiplier WHERE c.CONSUMPTION_ID IN (SELECT ID FROM tmp_id);
TRUNCATE TABLE tmp_id;
INSERT INTO tmp_id SELECT i.INVENTORY_ID from rm_inventory i left join rm_inventory_trans it on i.INVENTORY_ID=it.INVENTORY_ID LEFT JOIN rm_realm_country_planning_unit rcpu ON it.REALM_COUNTRY_PLANNING_UNIT_ID=rcpu.REALM_COUNTRY_PLANNING_UNIT_ID where i.PROGRAM_ID=@programId and rcpu.PLANNING_UNIT_ID=@planningUnitId;
UPDATE rm_inventory i left join rm_inventory_trans it on i.INVENTORY_ID=it.INVENTORY_ID LEFT JOIN rm_realm_country_planning_unit rcpu ON it.REALM_COUNTRY_PLANNING_UNIT_ID=rcpu.REALM_COUNTRY_PLANNING_UNIT_ID SET it.ACTUAL_QTY=it.ACTUAL_QTY*@multiplier, it.ADJUSTMENT_QTY=it.ADJUSTMENT_QTY*@multiplier WHERE i.INVENTORY_ID IN (SELECT ID FROM tmp_id);
UPDATE rm_inventory i left join rm_inventory_trans it on i.INVENTORY_ID=it.INVENTORY_ID LEFT JOIN rm_realm_country_planning_unit rcpu ON it.REALM_COUNTRY_PLANNING_UNIT_ID=rcpu.REALM_COUNTRY_PLANNING_UNIT_ID LEFT JOIN rm_inventory_trans_batch_info itbi ON it.INVENTORY_TRANS_ID=itbi.INVENTORY_TRANS_ID SET itbi.ACTUAL_QTY=itbi.ACTUAL_QTY*@multiplier, itbi.ADJUSTMENT_QTY=itbi.ADJUSTMENT_QTY*@multiplier WHERE i.INVENTORY_ID IN (SELECT ID FROM tmp_id);



-- Program 2030 - Planning Unit 1354 x30
TRUNCATE TABLE tmp_id;
SET @programId = 2030;
SET @planningUnitId = 1354;
SET @multiplier=30;
INSERT INTO tmp_id SELECT c.CONSUMPTION_ID FROM rm_consumption c LEFT JOIN rm_consumption_trans ct ON c.CONSUMPTION_ID=ct.CONSUMPTION_ID WHERE c.PROGRAM_ID=@programId and ct.PLANNING_UNIT_ID=@planningUnitId and ct.VERSION_ID=1;
UPDATE rm_consumption c LEFT JOIN rm_consumption_trans ct ON c.CONSUMPTION_ID=ct.CONSUMPTION_ID SET ct.CONSUMPTION_RCPU_QTY=ct.CONSUMPTION_RCPU_QTY*@multiplier WHERE c.CONSUMPTION_ID IN (SELECT ID FROM tmp_id);
UPDATE rm_consumption c LEFT JOIN rm_consumption_trans ct ON c.CONSUMPTION_ID=ct.CONSUMPTION_ID LEFT JOIN rm_realm_country_planning_unit rcpu ON ct.REALM_COUNTRY_PLANNING_UNIT_ID=rcpu.REALM_COUNTRY_PLANNING_UNIT_ID SET ct.CONSUMPTION_QTY=ct.CONSUMPTION_RCPU_QTY*rcpu.MULTIPLIER WHERE c.CONSUMPTION_ID IN (SELECT ID FROM tmp_id);
UPDATE rm_consumption c LEFT JOIN rm_consumption_trans ct ON c.CONSUMPTION_ID=ct.CONSUMPTION_ID LEFT JOIN rm_consumption_trans_batch_info ctbi ON ct.CONSUMPTION_TRANS_ID=ctbi.CONSUMPTION_TRANS_ID SET ctbi.CONSUMPTION_QTY=ctbi.CONSUMPTION_QTY*@multiplier WHERE c.CONSUMPTION_ID IN (SELECT ID FROM tmp_id);
TRUNCATE TABLE tmp_id;
INSERT INTO tmp_id SELECT i.INVENTORY_ID from rm_inventory i left join rm_inventory_trans it on i.INVENTORY_ID=it.INVENTORY_ID LEFT JOIN rm_realm_country_planning_unit rcpu ON it.REALM_COUNTRY_PLANNING_UNIT_ID=rcpu.REALM_COUNTRY_PLANNING_UNIT_ID where i.PROGRAM_ID=@programId and rcpu.PLANNING_UNIT_ID=@planningUnitId;
UPDATE rm_inventory i left join rm_inventory_trans it on i.INVENTORY_ID=it.INVENTORY_ID LEFT JOIN rm_realm_country_planning_unit rcpu ON it.REALM_COUNTRY_PLANNING_UNIT_ID=rcpu.REALM_COUNTRY_PLANNING_UNIT_ID SET it.ACTUAL_QTY=it.ACTUAL_QTY*@multiplier, it.ADJUSTMENT_QTY=it.ADJUSTMENT_QTY*@multiplier WHERE i.INVENTORY_ID IN (SELECT ID FROM tmp_id);
UPDATE rm_inventory i left join rm_inventory_trans it on i.INVENTORY_ID=it.INVENTORY_ID LEFT JOIN rm_realm_country_planning_unit rcpu ON it.REALM_COUNTRY_PLANNING_UNIT_ID=rcpu.REALM_COUNTRY_PLANNING_UNIT_ID LEFT JOIN rm_inventory_trans_batch_info itbi ON it.INVENTORY_TRANS_ID=itbi.INVENTORY_TRANS_ID SET itbi.ACTUAL_QTY=itbi.ACTUAL_QTY*@multiplier, itbi.ADJUSTMENT_QTY=itbi.ADJUSTMENT_QTY*@multiplier WHERE i.INVENTORY_ID IN (SELECT ID FROM tmp_id);



-- Program 2030 - Planning Unit 1355 x30
TRUNCATE TABLE tmp_id;
SET @programId = 2030;
SET @planningUnitId = 1355;
SET @multiplier=30;
INSERT INTO tmp_id SELECT c.CONSUMPTION_ID FROM rm_consumption c LEFT JOIN rm_consumption_trans ct ON c.CONSUMPTION_ID=ct.CONSUMPTION_ID WHERE c.PROGRAM_ID=@programId and ct.PLANNING_UNIT_ID=@planningUnitId and ct.VERSION_ID=1;
UPDATE rm_consumption c LEFT JOIN rm_consumption_trans ct ON c.CONSUMPTION_ID=ct.CONSUMPTION_ID SET ct.CONSUMPTION_RCPU_QTY=ct.CONSUMPTION_RCPU_QTY*@multiplier WHERE c.CONSUMPTION_ID IN (SELECT ID FROM tmp_id);
UPDATE rm_consumption c LEFT JOIN rm_consumption_trans ct ON c.CONSUMPTION_ID=ct.CONSUMPTION_ID LEFT JOIN rm_realm_country_planning_unit rcpu ON ct.REALM_COUNTRY_PLANNING_UNIT_ID=rcpu.REALM_COUNTRY_PLANNING_UNIT_ID SET ct.CONSUMPTION_QTY=ct.CONSUMPTION_RCPU_QTY*rcpu.MULTIPLIER WHERE c.CONSUMPTION_ID IN (SELECT ID FROM tmp_id);
UPDATE rm_consumption c LEFT JOIN rm_consumption_trans ct ON c.CONSUMPTION_ID=ct.CONSUMPTION_ID LEFT JOIN rm_consumption_trans_batch_info ctbi ON ct.CONSUMPTION_TRANS_ID=ctbi.CONSUMPTION_TRANS_ID SET ctbi.CONSUMPTION_QTY=ctbi.CONSUMPTION_QTY*@multiplier WHERE c.CONSUMPTION_ID IN (SELECT ID FROM tmp_id);
TRUNCATE TABLE tmp_id;
INSERT INTO tmp_id SELECT i.INVENTORY_ID from rm_inventory i left join rm_inventory_trans it on i.INVENTORY_ID=it.INVENTORY_ID LEFT JOIN rm_realm_country_planning_unit rcpu ON it.REALM_COUNTRY_PLANNING_UNIT_ID=rcpu.REALM_COUNTRY_PLANNING_UNIT_ID where i.PROGRAM_ID=@programId and rcpu.PLANNING_UNIT_ID=@planningUnitId;
UPDATE rm_inventory i left join rm_inventory_trans it on i.INVENTORY_ID=it.INVENTORY_ID LEFT JOIN rm_realm_country_planning_unit rcpu ON it.REALM_COUNTRY_PLANNING_UNIT_ID=rcpu.REALM_COUNTRY_PLANNING_UNIT_ID SET it.ACTUAL_QTY=it.ACTUAL_QTY*@multiplier, it.ADJUSTMENT_QTY=it.ADJUSTMENT_QTY*@multiplier WHERE i.INVENTORY_ID IN (SELECT ID FROM tmp_id);
UPDATE rm_inventory i left join rm_inventory_trans it on i.INVENTORY_ID=it.INVENTORY_ID LEFT JOIN rm_realm_country_planning_unit rcpu ON it.REALM_COUNTRY_PLANNING_UNIT_ID=rcpu.REALM_COUNTRY_PLANNING_UNIT_ID LEFT JOIN rm_inventory_trans_batch_info itbi ON it.INVENTORY_TRANS_ID=itbi.INVENTORY_TRANS_ID SET itbi.ACTUAL_QTY=itbi.ACTUAL_QTY*@multiplier, itbi.ADJUSTMENT_QTY=itbi.ADJUSTMENT_QTY*@multiplier WHERE i.INVENTORY_ID IN (SELECT ID FROM tmp_id);



-- Program 2030 - Planning Unit 1358 x30
TRUNCATE TABLE tmp_id;
SET @programId = 2030;
SET @planningUnitId = 1358;
SET @multiplier=30;
INSERT INTO tmp_id SELECT c.CONSUMPTION_ID FROM rm_consumption c LEFT JOIN rm_consumption_trans ct ON c.CONSUMPTION_ID=ct.CONSUMPTION_ID WHERE c.PROGRAM_ID=@programId and ct.PLANNING_UNIT_ID=@planningUnitId and ct.VERSION_ID=1;
UPDATE rm_consumption c LEFT JOIN rm_consumption_trans ct ON c.CONSUMPTION_ID=ct.CONSUMPTION_ID SET ct.CONSUMPTION_RCPU_QTY=ct.CONSUMPTION_RCPU_QTY*@multiplier WHERE c.CONSUMPTION_ID IN (SELECT ID FROM tmp_id);
UPDATE rm_consumption c LEFT JOIN rm_consumption_trans ct ON c.CONSUMPTION_ID=ct.CONSUMPTION_ID LEFT JOIN rm_realm_country_planning_unit rcpu ON ct.REALM_COUNTRY_PLANNING_UNIT_ID=rcpu.REALM_COUNTRY_PLANNING_UNIT_ID SET ct.CONSUMPTION_QTY=ct.CONSUMPTION_RCPU_QTY*rcpu.MULTIPLIER WHERE c.CONSUMPTION_ID IN (SELECT ID FROM tmp_id);
UPDATE rm_consumption c LEFT JOIN rm_consumption_trans ct ON c.CONSUMPTION_ID=ct.CONSUMPTION_ID LEFT JOIN rm_consumption_trans_batch_info ctbi ON ct.CONSUMPTION_TRANS_ID=ctbi.CONSUMPTION_TRANS_ID SET ctbi.CONSUMPTION_QTY=ctbi.CONSUMPTION_QTY*@multiplier WHERE c.CONSUMPTION_ID IN (SELECT ID FROM tmp_id);
TRUNCATE TABLE tmp_id;
INSERT INTO tmp_id SELECT i.INVENTORY_ID from rm_inventory i left join rm_inventory_trans it on i.INVENTORY_ID=it.INVENTORY_ID LEFT JOIN rm_realm_country_planning_unit rcpu ON it.REALM_COUNTRY_PLANNING_UNIT_ID=rcpu.REALM_COUNTRY_PLANNING_UNIT_ID where i.PROGRAM_ID=@programId and rcpu.PLANNING_UNIT_ID=@planningUnitId;
UPDATE rm_inventory i left join rm_inventory_trans it on i.INVENTORY_ID=it.INVENTORY_ID LEFT JOIN rm_realm_country_planning_unit rcpu ON it.REALM_COUNTRY_PLANNING_UNIT_ID=rcpu.REALM_COUNTRY_PLANNING_UNIT_ID SET it.ACTUAL_QTY=it.ACTUAL_QTY*@multiplier, it.ADJUSTMENT_QTY=it.ADJUSTMENT_QTY*@multiplier WHERE i.INVENTORY_ID IN (SELECT ID FROM tmp_id);
UPDATE rm_inventory i left join rm_inventory_trans it on i.INVENTORY_ID=it.INVENTORY_ID LEFT JOIN rm_realm_country_planning_unit rcpu ON it.REALM_COUNTRY_PLANNING_UNIT_ID=rcpu.REALM_COUNTRY_PLANNING_UNIT_ID LEFT JOIN rm_inventory_trans_batch_info itbi ON it.INVENTORY_TRANS_ID=itbi.INVENTORY_TRANS_ID SET itbi.ACTUAL_QTY=itbi.ACTUAL_QTY*@multiplier, itbi.ADJUSTMENT_QTY=itbi.ADJUSTMENT_QTY*@multiplier WHERE i.INVENTORY_ID IN (SELECT ID FROM tmp_id);



-- Program 2030 - Planning Unit 1359 x30
TRUNCATE TABLE tmp_id;
SET @programId = 2030;
SET @planningUnitId = 1359;
SET @multiplier=30;
INSERT INTO tmp_id SELECT c.CONSUMPTION_ID FROM rm_consumption c LEFT JOIN rm_consumption_trans ct ON c.CONSUMPTION_ID=ct.CONSUMPTION_ID WHERE c.PROGRAM_ID=@programId and ct.PLANNING_UNIT_ID=@planningUnitId and ct.VERSION_ID=1;
UPDATE rm_consumption c LEFT JOIN rm_consumption_trans ct ON c.CONSUMPTION_ID=ct.CONSUMPTION_ID SET ct.CONSUMPTION_RCPU_QTY=ct.CONSUMPTION_RCPU_QTY*@multiplier WHERE c.CONSUMPTION_ID IN (SELECT ID FROM tmp_id);
UPDATE rm_consumption c LEFT JOIN rm_consumption_trans ct ON c.CONSUMPTION_ID=ct.CONSUMPTION_ID LEFT JOIN rm_realm_country_planning_unit rcpu ON ct.REALM_COUNTRY_PLANNING_UNIT_ID=rcpu.REALM_COUNTRY_PLANNING_UNIT_ID SET ct.CONSUMPTION_QTY=ct.CONSUMPTION_RCPU_QTY*rcpu.MULTIPLIER WHERE c.CONSUMPTION_ID IN (SELECT ID FROM tmp_id);
UPDATE rm_consumption c LEFT JOIN rm_consumption_trans ct ON c.CONSUMPTION_ID=ct.CONSUMPTION_ID LEFT JOIN rm_consumption_trans_batch_info ctbi ON ct.CONSUMPTION_TRANS_ID=ctbi.CONSUMPTION_TRANS_ID SET ctbi.CONSUMPTION_QTY=ctbi.CONSUMPTION_QTY*@multiplier WHERE c.CONSUMPTION_ID IN (SELECT ID FROM tmp_id);
TRUNCATE TABLE tmp_id;
INSERT INTO tmp_id SELECT i.INVENTORY_ID from rm_inventory i left join rm_inventory_trans it on i.INVENTORY_ID=it.INVENTORY_ID LEFT JOIN rm_realm_country_planning_unit rcpu ON it.REALM_COUNTRY_PLANNING_UNIT_ID=rcpu.REALM_COUNTRY_PLANNING_UNIT_ID where i.PROGRAM_ID=@programId and rcpu.PLANNING_UNIT_ID=@planningUnitId;
UPDATE rm_inventory i left join rm_inventory_trans it on i.INVENTORY_ID=it.INVENTORY_ID LEFT JOIN rm_realm_country_planning_unit rcpu ON it.REALM_COUNTRY_PLANNING_UNIT_ID=rcpu.REALM_COUNTRY_PLANNING_UNIT_ID SET it.ACTUAL_QTY=it.ACTUAL_QTY*@multiplier, it.ADJUSTMENT_QTY=it.ADJUSTMENT_QTY*@multiplier WHERE i.INVENTORY_ID IN (SELECT ID FROM tmp_id);
UPDATE rm_inventory i left join rm_inventory_trans it on i.INVENTORY_ID=it.INVENTORY_ID LEFT JOIN rm_realm_country_planning_unit rcpu ON it.REALM_COUNTRY_PLANNING_UNIT_ID=rcpu.REALM_COUNTRY_PLANNING_UNIT_ID LEFT JOIN rm_inventory_trans_batch_info itbi ON it.INVENTORY_TRANS_ID=itbi.INVENTORY_TRANS_ID SET itbi.ACTUAL_QTY=itbi.ACTUAL_QTY*@multiplier, itbi.ADJUSTMENT_QTY=itbi.ADJUSTMENT_QTY*@multiplier WHERE i.INVENTORY_ID IN (SELECT ID FROM tmp_id);



-- Program 2030 - Planning Unit 5651 x50
TRUNCATE TABLE tmp_id;
SET @programId = 2030;
SET @planningUnitId = 5651;
SET @multiplier=50;
INSERT INTO tmp_id SELECT c.CONSUMPTION_ID FROM rm_consumption c LEFT JOIN rm_consumption_trans ct ON c.CONSUMPTION_ID=ct.CONSUMPTION_ID WHERE c.PROGRAM_ID=@programId and ct.PLANNING_UNIT_ID=@planningUnitId and ct.VERSION_ID=1;
UPDATE rm_consumption c LEFT JOIN rm_consumption_trans ct ON c.CONSUMPTION_ID=ct.CONSUMPTION_ID SET ct.CONSUMPTION_RCPU_QTY=ct.CONSUMPTION_RCPU_QTY*@multiplier WHERE c.CONSUMPTION_ID IN (SELECT ID FROM tmp_id);
UPDATE rm_consumption c LEFT JOIN rm_consumption_trans ct ON c.CONSUMPTION_ID=ct.CONSUMPTION_ID LEFT JOIN rm_realm_country_planning_unit rcpu ON ct.REALM_COUNTRY_PLANNING_UNIT_ID=rcpu.REALM_COUNTRY_PLANNING_UNIT_ID SET ct.CONSUMPTION_QTY=ct.CONSUMPTION_RCPU_QTY*rcpu.MULTIPLIER WHERE c.CONSUMPTION_ID IN (SELECT ID FROM tmp_id);
UPDATE rm_consumption c LEFT JOIN rm_consumption_trans ct ON c.CONSUMPTION_ID=ct.CONSUMPTION_ID LEFT JOIN rm_consumption_trans_batch_info ctbi ON ct.CONSUMPTION_TRANS_ID=ctbi.CONSUMPTION_TRANS_ID SET ctbi.CONSUMPTION_QTY=ctbi.CONSUMPTION_QTY*@multiplier WHERE c.CONSUMPTION_ID IN (SELECT ID FROM tmp_id);
TRUNCATE TABLE tmp_id;
INSERT INTO tmp_id SELECT i.INVENTORY_ID from rm_inventory i left join rm_inventory_trans it on i.INVENTORY_ID=it.INVENTORY_ID LEFT JOIN rm_realm_country_planning_unit rcpu ON it.REALM_COUNTRY_PLANNING_UNIT_ID=rcpu.REALM_COUNTRY_PLANNING_UNIT_ID where i.PROGRAM_ID=@programId and rcpu.PLANNING_UNIT_ID=@planningUnitId;
UPDATE rm_inventory i left join rm_inventory_trans it on i.INVENTORY_ID=it.INVENTORY_ID LEFT JOIN rm_realm_country_planning_unit rcpu ON it.REALM_COUNTRY_PLANNING_UNIT_ID=rcpu.REALM_COUNTRY_PLANNING_UNIT_ID SET it.ACTUAL_QTY=it.ACTUAL_QTY*@multiplier, it.ADJUSTMENT_QTY=it.ADJUSTMENT_QTY*@multiplier WHERE i.INVENTORY_ID IN (SELECT ID FROM tmp_id);
UPDATE rm_inventory i left join rm_inventory_trans it on i.INVENTORY_ID=it.INVENTORY_ID LEFT JOIN rm_realm_country_planning_unit rcpu ON it.REALM_COUNTRY_PLANNING_UNIT_ID=rcpu.REALM_COUNTRY_PLANNING_UNIT_ID LEFT JOIN rm_inventory_trans_batch_info itbi ON it.INVENTORY_TRANS_ID=itbi.INVENTORY_TRANS_ID SET itbi.ACTUAL_QTY=itbi.ACTUAL_QTY*@multiplier, itbi.ADJUSTMENT_QTY=itbi.ADJUSTMENT_QTY*@multiplier WHERE i.INVENTORY_ID IN (SELECT ID FROM tmp_id);



-- Program 2020 - Planning Unit 4115 x25
TRUNCATE TABLE tmp_id;
SET @programId = 2030;
SET @planningUnitId = 4115;
SET @multiplier=25;
INSERT INTO tmp_id SELECT c.CONSUMPTION_ID FROM rm_consumption c LEFT JOIN rm_consumption_trans ct ON c.CONSUMPTION_ID=ct.CONSUMPTION_ID WHERE c.PROGRAM_ID=@programId and ct.PLANNING_UNIT_ID=@planningUnitId and ct.VERSION_ID=1;
UPDATE rm_consumption c LEFT JOIN rm_consumption_trans ct ON c.CONSUMPTION_ID=ct.CONSUMPTION_ID SET ct.CONSUMPTION_RCPU_QTY=ct.CONSUMPTION_RCPU_QTY*@multiplier WHERE c.CONSUMPTION_ID IN (SELECT ID FROM tmp_id);
UPDATE rm_consumption c LEFT JOIN rm_consumption_trans ct ON c.CONSUMPTION_ID=ct.CONSUMPTION_ID LEFT JOIN rm_realm_country_planning_unit rcpu ON ct.REALM_COUNTRY_PLANNING_UNIT_ID=rcpu.REALM_COUNTRY_PLANNING_UNIT_ID SET ct.CONSUMPTION_QTY=ct.CONSUMPTION_RCPU_QTY*rcpu.MULTIPLIER WHERE c.CONSUMPTION_ID IN (SELECT ID FROM tmp_id);
UPDATE rm_consumption c LEFT JOIN rm_consumption_trans ct ON c.CONSUMPTION_ID=ct.CONSUMPTION_ID LEFT JOIN rm_consumption_trans_batch_info ctbi ON ct.CONSUMPTION_TRANS_ID=ctbi.CONSUMPTION_TRANS_ID SET ctbi.CONSUMPTION_QTY=ctbi.CONSUMPTION_QTY*@multiplier WHERE c.CONSUMPTION_ID IN (SELECT ID FROM tmp_id);
TRUNCATE TABLE tmp_id;
INSERT INTO tmp_id SELECT i.INVENTORY_ID from rm_inventory i left join rm_inventory_trans it on i.INVENTORY_ID=it.INVENTORY_ID LEFT JOIN rm_realm_country_planning_unit rcpu ON it.REALM_COUNTRY_PLANNING_UNIT_ID=rcpu.REALM_COUNTRY_PLANNING_UNIT_ID where i.PROGRAM_ID=@programId and rcpu.PLANNING_UNIT_ID=@planningUnitId;
UPDATE rm_inventory i left join rm_inventory_trans it on i.INVENTORY_ID=it.INVENTORY_ID LEFT JOIN rm_realm_country_planning_unit rcpu ON it.REALM_COUNTRY_PLANNING_UNIT_ID=rcpu.REALM_COUNTRY_PLANNING_UNIT_ID SET it.ACTUAL_QTY=it.ACTUAL_QTY*@multiplier, it.ADJUSTMENT_QTY=it.ADJUSTMENT_QTY*@multiplier WHERE i.INVENTORY_ID IN (SELECT ID FROM tmp_id);
UPDATE rm_inventory i left join rm_inventory_trans it on i.INVENTORY_ID=it.INVENTORY_ID LEFT JOIN rm_realm_country_planning_unit rcpu ON it.REALM_COUNTRY_PLANNING_UNIT_ID=rcpu.REALM_COUNTRY_PLANNING_UNIT_ID LEFT JOIN rm_inventory_trans_batch_info itbi ON it.INVENTORY_TRANS_ID=itbi.INVENTORY_TRANS_ID SET itbi.ACTUAL_QTY=itbi.ACTUAL_QTY*@multiplier, itbi.ADJUSTMENT_QTY=itbi.ADJUSTMENT_QTY*@multiplier WHERE i.INVENTORY_ID IN (SELECT ID FROM tmp_id);

-- Program 2030 - Planning Unit 1176 x50
TRUNCATE TABLE tmp_id;
SET @programId = 2030;
SET @planningUnitId = 1176;
SET @multiplier=50;
INSERT INTO tmp_id SELECT c.CONSUMPTION_ID FROM rm_consumption c LEFT JOIN rm_consumption_trans ct ON c.CONSUMPTION_ID=ct.CONSUMPTION_ID WHERE c.PROGRAM_ID=@programId and ct.PLANNING_UNIT_ID=@planningUnitId and ct.VERSION_ID=1;
UPDATE rm_consumption c LEFT JOIN rm_consumption_trans ct ON c.CONSUMPTION_ID=ct.CONSUMPTION_ID SET ct.CONSUMPTION_RCPU_QTY=ct.CONSUMPTION_RCPU_QTY*@multiplier WHERE c.CONSUMPTION_ID IN (SELECT ID FROM tmp_id);
UPDATE rm_consumption c LEFT JOIN rm_consumption_trans ct ON c.CONSUMPTION_ID=ct.CONSUMPTION_ID LEFT JOIN rm_realm_country_planning_unit rcpu ON ct.REALM_COUNTRY_PLANNING_UNIT_ID=rcpu.REALM_COUNTRY_PLANNING_UNIT_ID SET ct.CONSUMPTION_QTY=ct.CONSUMPTION_RCPU_QTY*rcpu.MULTIPLIER WHERE c.CONSUMPTION_ID IN (SELECT ID FROM tmp_id);
UPDATE rm_consumption c LEFT JOIN rm_consumption_trans ct ON c.CONSUMPTION_ID=ct.CONSUMPTION_ID LEFT JOIN rm_consumption_trans_batch_info ctbi ON ct.CONSUMPTION_TRANS_ID=ctbi.CONSUMPTION_TRANS_ID SET ctbi.CONSUMPTION_QTY=ctbi.CONSUMPTION_QTY*@multiplier WHERE c.CONSUMPTION_ID IN (SELECT ID FROM tmp_id);
TRUNCATE TABLE tmp_id;
INSERT INTO tmp_id SELECT i.INVENTORY_ID from rm_inventory i left join rm_inventory_trans it on i.INVENTORY_ID=it.INVENTORY_ID LEFT JOIN rm_realm_country_planning_unit rcpu ON it.REALM_COUNTRY_PLANNING_UNIT_ID=rcpu.REALM_COUNTRY_PLANNING_UNIT_ID where i.PROGRAM_ID=@programId and rcpu.PLANNING_UNIT_ID=@planningUnitId;
UPDATE rm_inventory i left join rm_inventory_trans it on i.INVENTORY_ID=it.INVENTORY_ID LEFT JOIN rm_realm_country_planning_unit rcpu ON it.REALM_COUNTRY_PLANNING_UNIT_ID=rcpu.REALM_COUNTRY_PLANNING_UNIT_ID SET it.ACTUAL_QTY=it.ACTUAL_QTY*@multiplier, it.ADJUSTMENT_QTY=it.ADJUSTMENT_QTY*@multiplier WHERE i.INVENTORY_ID IN (SELECT ID FROM tmp_id);
UPDATE rm_inventory i left join rm_inventory_trans it on i.INVENTORY_ID=it.INVENTORY_ID LEFT JOIN rm_realm_country_planning_unit rcpu ON it.REALM_COUNTRY_PLANNING_UNIT_ID=rcpu.REALM_COUNTRY_PLANNING_UNIT_ID LEFT JOIN rm_inventory_trans_batch_info itbi ON it.INVENTORY_TRANS_ID=itbi.INVENTORY_TRANS_ID SET itbi.ACTUAL_QTY=itbi.ACTUAL_QTY*@multiplier, itbi.ADJUSTMENT_QTY=itbi.ADJUSTMENT_QTY*@multiplier WHERE i.INVENTORY_ID IN (SELECT ID FROM tmp_id);


-- Program 2030 - Planning Unit 1183 x50
TRUNCATE TABLE tmp_id;
SET @programId = 2030;
SET @planningUnitId = 1183;
SET @multiplier=50;
INSERT INTO tmp_id SELECT c.CONSUMPTION_ID FROM rm_consumption c LEFT JOIN rm_consumption_trans ct ON c.CONSUMPTION_ID=ct.CONSUMPTION_ID WHERE c.PROGRAM_ID=@programId and ct.PLANNING_UNIT_ID=@planningUnitId and ct.VERSION_ID=1;
UPDATE rm_consumption c LEFT JOIN rm_consumption_trans ct ON c.CONSUMPTION_ID=ct.CONSUMPTION_ID SET ct.CONSUMPTION_RCPU_QTY=ct.CONSUMPTION_RCPU_QTY*@multiplier WHERE c.CONSUMPTION_ID IN (SELECT ID FROM tmp_id);
UPDATE rm_consumption c LEFT JOIN rm_consumption_trans ct ON c.CONSUMPTION_ID=ct.CONSUMPTION_ID LEFT JOIN rm_realm_country_planning_unit rcpu ON ct.REALM_COUNTRY_PLANNING_UNIT_ID=rcpu.REALM_COUNTRY_PLANNING_UNIT_ID SET ct.CONSUMPTION_QTY=ct.CONSUMPTION_RCPU_QTY*rcpu.MULTIPLIER WHERE c.CONSUMPTION_ID IN (SELECT ID FROM tmp_id);
UPDATE rm_consumption c LEFT JOIN rm_consumption_trans ct ON c.CONSUMPTION_ID=ct.CONSUMPTION_ID LEFT JOIN rm_consumption_trans_batch_info ctbi ON ct.CONSUMPTION_TRANS_ID=ctbi.CONSUMPTION_TRANS_ID SET ctbi.CONSUMPTION_QTY=ctbi.CONSUMPTION_QTY*@multiplier WHERE c.CONSUMPTION_ID IN (SELECT ID FROM tmp_id);
TRUNCATE TABLE tmp_id;
INSERT INTO tmp_id SELECT i.INVENTORY_ID from rm_inventory i left join rm_inventory_trans it on i.INVENTORY_ID=it.INVENTORY_ID LEFT JOIN rm_realm_country_planning_unit rcpu ON it.REALM_COUNTRY_PLANNING_UNIT_ID=rcpu.REALM_COUNTRY_PLANNING_UNIT_ID where i.PROGRAM_ID=@programId and rcpu.PLANNING_UNIT_ID=@planningUnitId;
UPDATE rm_inventory i left join rm_inventory_trans it on i.INVENTORY_ID=it.INVENTORY_ID LEFT JOIN rm_realm_country_planning_unit rcpu ON it.REALM_COUNTRY_PLANNING_UNIT_ID=rcpu.REALM_COUNTRY_PLANNING_UNIT_ID SET it.ACTUAL_QTY=it.ACTUAL_QTY*@multiplier, it.ADJUSTMENT_QTY=it.ADJUSTMENT_QTY*@multiplier WHERE i.INVENTORY_ID IN (SELECT ID FROM tmp_id);
UPDATE rm_inventory i left join rm_inventory_trans it on i.INVENTORY_ID=it.INVENTORY_ID LEFT JOIN rm_realm_country_planning_unit rcpu ON it.REALM_COUNTRY_PLANNING_UNIT_ID=rcpu.REALM_COUNTRY_PLANNING_UNIT_ID LEFT JOIN rm_inventory_trans_batch_info itbi ON it.INVENTORY_TRANS_ID=itbi.INVENTORY_TRANS_ID SET itbi.ACTUAL_QTY=itbi.ACTUAL_QTY*@multiplier, itbi.ADJUSTMENT_QTY=itbi.ADJUSTMENT_QTY*@multiplier WHERE i.INVENTORY_ID IN (SELECT ID FROM tmp_id);



DROP TABLE tmp_id;