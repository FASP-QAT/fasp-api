INSERT INTO ap_label values (null, 'Tanzania Condoms & ARV', null, null, null, 1, @dt, 1, @dt, 45);
SELECT last_insert_id() into @labelId;
INSERT INTO rm_program values (null, "TZA-CON/ARV-MOH", 1, 44 , 1, @labelId, 1, "Testing for Condoms & ARV", null, null, null, null, null, null, null, null, 1, 1, 9, @dt, 9, @dt, 2);
SELECT last_insert_id() into @programId;
INSERT INTO rm_program_version values (null, @programId, 1, 1, 1, "Loaded during testing", 9, @dt, 9, @dt, 1, "2020-01-01", "2024-12-31");
INSERT INTO rm_program_health_area values (null, @programId, 8);
INSERT INTO rm_program_region VALUES (null, @programId, 70, 1, 1, @dt, 1, @dt);

INSERT INTO rm_forecast_tree SELECT null, @programId, 1, tt.LABEL_ID, tt.FORECAST_METHOD_ID, tt.CREATED_BY, tt.CREATED_DATE, tt.LAST_MODIFIED_BY, tt.LAST_MODIFIED_DATE, 1 FROM rm_tree_template tt WHERE tt.TREE_TEMPLATE_ID=1;
SELECT last_insert_id() into @treeId;
INSERT INTO ap_label values (null, 'Default scenario', null, null, null, 1, @dt, 1, @dt, 50);
SELECT last_insert_id() into @labelId;
INSERT INTO rm_scenario VALUES (null, @treeId, @labelId, 1, @dt, 1, @dt, 1);
SELECT last_insert_id() into @scenarioId1;
INSERT INTO ap_label values (null, 'High scenario', null, null, null, 1, @dt, 1, @dt, 50);
SELECT last_insert_id() into @labelId;
INSERT INTO rm_scenario VALUES (null, @treeId, @labelId, 1, @dt, 1, @dt, 1);
SELECT last_insert_id() into @scenarioId2;
INSERT INTO rm_forecast_tree_node SELECT null, 1, ttn.PARENT_NODE_ID, ttn.SORT_ORDER, ttn.LEVEL_NO, ttn.NODE_TYPE_ID, ttn.UNIT_ID, ttn.MANUAL_CHANGE_EFFECTS_FUTURE_MONTHS, ttn.LABEL_ID, ttn.CREATED_BY, ttn.CREATED_DATE, ttn.LAST_MODIFIED_BY, ttn.LAST_MODIFIED_DATE, ttn.ACTIVE FROM rm_tree_template_node ttn where ttn.TREE_TEMPLATE_ID=1;
INSERT INTO rm_forecast_tree_node_data_fu SELECT null, tnd.FORECASTING_UNIT_ID, tnd.LAG_IN_MONTHS, tnd.USAGE_TYPE_ID, tnd.NO_OF_PERSONS, tnd.FORECASTING_UNITS_PER_PERSON, tnd.ONE_TIME_USAGE, tnd.USAGE_FREQUENCY, tnd.USAGE_FREQUENCY_USAGE_PERIOD_ID, tnd.REPEAT_COUNT, tnd.REPEAT_USAGE_PERIOD_ID, tnd.CREATED_BY, tnd.CREATED_DATE, tnd.LAST_MODIFIED_BY, tnd.LAST_MODIFIED_DATE, tnd.ACTIVE FROM rm_tree_template_node_data_fu tnd;
INSERT INTO rm_forecast_tree_node_data_fu SELECT null, tnd.FORECASTING_UNIT_ID, tnd.LAG_IN_MONTHS, tnd.USAGE_TYPE_ID, tnd.NO_OF_PERSONS, tnd.FORECASTING_UNITS_PER_PERSON, tnd.ONE_TIME_USAGE, tnd.USAGE_FREQUENCY, tnd.USAGE_FREQUENCY_USAGE_PERIOD_ID, tnd.REPEAT_COUNT, tnd.REPEAT_USAGE_PERIOD_ID, tnd.CREATED_BY, tnd.CREATED_DATE, tnd.LAST_MODIFIED_BY, tnd.LAST_MODIFIED_DATE, tnd.ACTIVE FROM rm_tree_template_node_data_fu tnd;
INSERT INTO rm_forecast_tree_node_data_pu SELECT null, tnd.PLANNING_UNIT_ID, tnd.SHARE_PLANNING_UNIT, tnd.REFILL_MONTHS, tnd.CREATED_BY, tnd.CREATED_DATE, tnd.LAST_MODIFIED_BY, tnd.LAST_MODIFIED_DATE, tnd.ACTIVE FROM rm_tree_template_node_data_pu tnd;
INSERT INTO rm_forecast_tree_node_data_pu SELECT null, tnd.PLANNING_UNIT_ID, tnd.SHARE_PLANNING_UNIT, tnd.REFILL_MONTHS, tnd.CREATED_BY, tnd.CREATED_DATE, tnd.LAST_MODIFIED_BY, tnd.LAST_MODIFIED_DATE, tnd.ACTIVE FROM rm_tree_template_node_data_pu tnd;
INSERT INTO rm_forecast_tree_node_data SELECT null, tnd.NODE_ID, @scenarioId1, tnd.MONTH, tnd.DATA_VALUE, tnd.NODE_DATA_FU_ID, tnd.NODE_DATA_PU_ID, tnd.NOTES, tnd.CREATED_BY, tnd.CREATED_DATE, tnd.LAST_MODIFIED_BY, tnd.LAST_MODIFIED_DATE, tnd.ACTIVE FROM rm_tree_template_node_data tnd;
INSERT INTO rm_forecast_tree_node_data SELECT null, tnd.NODE_ID, @scenarioId2, tnd.MONTH, tnd.DATA_VALUE*1.1, tnd.NODE_DATA_FU_ID, tnd.NODE_DATA_PU_ID, tnd.NOTES, tnd.CREATED_BY, tnd.CREATED_DATE, tnd.LAST_MODIFIED_BY, tnd.LAST_MODIFIED_DATE, tnd.ACTIVE FROM rm_tree_template_node_data tnd;
UPDATE `fasp`.`rm_forecast_tree_node_data` SET `DATA_VALUE`='100' WHERE `NODE_DATA_ID`='23';
UPDATE `fasp`.`rm_forecast_tree_node_data` SET `DATA_VALUE`='100' WHERE `NODE_DATA_ID`='24';


UPDATE rm_forecast_tree_node_data tnd SET tnd.NODE_DATA_FU_ID=tnd.NODE_DATA_FU_ID+3 WHERE tnd.SCENARIO_ID=@scenarioId2 AND tnd.NODE_DATA_FU_ID IS NOT NULL;
UPDATE rm_forecast_tree_node_data tnd SET tnd.NODE_DATA_PU_ID=tnd.NODE_DATA_PU_ID+3 WHERE tnd.SCENARIO_ID=@scenarioId2 AND tnd.NODE_DATA_PU_ID IS NOT NULL;
INSERT INTO rm_organisation_country VALUES (null, 1, 44, 1, 1, @dt, 1, @dt);

INSERT INTO rm_forecast_tree_region VALUES (null, @treeId, 70);
INSERT INTO ap_label values (null, 'ARV Tree', null, null, null, 1, @dt, 1, @dt, 48);
SELECT last_insert_id() into @labelId;
INSERT INTO rm_forecast_tree VALUES (null, @programId, 1, @labelId, 1, 1, @dt, 1, @dt, 1);

SELECT last_insert_id() into @treeId;
INSERT INTO ap_label values (null, 'Most likely scenario', null, null, null, 1, @dt, 1, @dt, 50);
SELECT last_insert_id() into @labelId;
INSERT INTO rm_scenario VALUES (null, @treeId, @labelId, 1, @dt, 1, @dt, 1);
SELECT last_insert_id() into @scenarioId1;
INSERT INTO ap_label values (null, 'Over estimation', null, null, null, 1, @dt, 1, @dt, 50);
SELECT last_insert_id() into @labelId;
INSERT INTO rm_scenario VALUES (null, @treeId, @labelId, 1, @dt, 1, @dt, 1);
SELECT last_insert_id() into @scenarioId2;
INSERT INTO ap_label values (null, 'Under estimation', null, null, null, 1, @dt, 1, @dt, 50);
SELECT last_insert_id() into @labelId;
INSERT INTO rm_scenario VALUES (null, @treeId, @labelId, 1, @dt, 1, @dt, 1);
SELECT last_insert_id() into @scenarioId3;

INSERT INTO ap_label values (null, 'ARV Patients', null, null, null, 1, @dt, 1, @dt, 49);
SELECT last_insert_id() into @labelId;
insert into rm_forecast_tree_node values (null, @treeId, null, "00", 1, 1, @unitId, 1, @labelId, 1, @dt, 1, @dt, 1);
SELECT last_insert_id() into @nodeId;
insert into rm_forecast_tree_node_data values (null, @nodeId, @scenarioId1, "2021-01-01", null, null, null, "", 1, @dt, 1, @dt, 1);
insert into rm_forecast_tree_node_data values (null, @nodeId, @scenarioId2, "2021-01-01", null, null, null, "", 1, @dt, 1, @dt, 1);
insert into rm_forecast_tree_node_data values (null, @nodeId, @scenarioId3, "2021-01-01", null, null, null, "", 1, @dt, 1, @dt, 1);

SET @parentNodeId = @nodeId;
INSERT INTO ap_label values (null, 'Adults', null, null, null, 1, @dt, 1, @dt, 49);
SELECT last_insert_id() into @labelId;
insert into rm_forecast_tree_node values (null, @treeId, @parentNodeId, "00.01", 2, 1, @unitId, 1, @labelId, 1, @dt, 1, @dt, 1);
SELECT last_insert_id() into @nodeId;
insert into rm_forecast_tree_node_data values (null, @nodeId, @scenarioId1, "2021-01-01", null, null, null, "", 1, @dt, 1, @dt, 1);
insert into rm_forecast_tree_node_data values (null, @nodeId, @scenarioId2, "2021-01-01", null, null, null, "", 1, @dt, 1, @dt, 1);
insert into rm_forecast_tree_node_data values (null, @nodeId, @scenarioId3, "2021-01-01", null, null, null, "", 1, @dt, 1, @dt, 1);

INSERT INTO ap_label values (null, 'Children', null, null, null, 1, @dt, 1, @dt, 49);
SELECT last_insert_id() into @labelId;
insert into rm_forecast_tree_node values (null, @treeId, @parentNodeId, "00.02", 2, 1, @unitId, 1, @labelId, 1, @dt, 1, @dt, 1);
SELECT last_insert_id() into @nodeId;
insert into rm_forecast_tree_node_data values (null, @nodeId, @scenarioId1, "2021-01-01", null, null, null, "", 1, @dt, 1, @dt, 1);
insert into rm_forecast_tree_node_data values (null, @nodeId, @scenarioId2, "2021-01-01", null, null, null, "", 1, @dt, 1, @dt, 1);
insert into rm_forecast_tree_node_data values (null, @nodeId, @scenarioId3, "2021-01-01", null, null, null, "", 1, @dt, 1, @dt, 1);

SET @parentNodeId = @nodeId-1;

INSERT INTO ap_label values (null, 'Adults 1st Line', null, null, null, 1, @dt, 1, @dt, 49);
SELECT last_insert_id() into @labelId;
insert into rm_forecast_tree_node values (null, @treeId, @parentNodeId, "00.01.01", 3, 2, @unitId, 1, @labelId, 1, @dt, 1, @dt, 1);
SELECT last_insert_id() into @nodeId;
insert into rm_forecast_tree_node_data values (null, @nodeId, @scenarioId1, "2021-01-01", 45386964, null, null, "", 1, @dt, 1, @dt, 1);
insert into rm_forecast_tree_node_data values (null, @nodeId, @scenarioId2, "2021-01-01", 53820401, null, null, "", 1, @dt, 1, @dt, 1);
insert into rm_forecast_tree_node_data values (null, @nodeId, @scenarioId3, "2021-01-01", 39102354, null, null, "", 1, @dt, 1, @dt, 1);

INSERT INTO ap_label values (null, 'Adults 2nd Line', null, null, null, 1, @dt, 1, @dt, 49);
SELECT last_insert_id() into @labelId;
insert into rm_forecast_tree_node values (null, @treeId, @parentNodeId, "00.01.02", 3, 2, @unitId, 1, @labelId, 1, @dt, 1, @dt, 1);
SELECT last_insert_id() into @nodeId;
insert into rm_forecast_tree_node_data values (null, @nodeId, @scenarioId1, "2021-01-01", 55442475, null, null, "", 1, @dt, 1, @dt, 1);
insert into rm_forecast_tree_node_data values (null, @nodeId, @scenarioId2, "2021-01-01", 60420158, null, null, "", 1, @dt, 1, @dt, 1);
insert into rm_forecast_tree_node_data values (null, @nodeId, @scenarioId3, "2021-01-01", 48391200, null, null, "", 1, @dt, 1, @dt, 1);

SET @parentNodeId = @nodeId - 1;
INSERT INTO ap_label values (null, 'TLD', null, null, null, 1, @dt, 1, @dt, 49);
SELECT last_insert_id() into @labelId;
insert into rm_forecast_tree_node values (null, @treeId, @parentNodeId, "00.01.01.01", 4, 3, @unitId, 1, @labelId, 1, @dt, 1, @dt, 1);
SELECT last_insert_id() into @nodeId;
insert into rm_forecast_tree_node_data values (null, @nodeId, @scenarioId1, "2021-01-01", 36.8, null, null, "", 1, @dt, 1, @dt, 1);
insert into rm_forecast_tree_node_data values (null, @nodeId, @scenarioId2, "2021-01-01", 33.5, null, null, "", 1, @dt, 1, @dt, 1);
insert into rm_forecast_tree_node_data values (null, @nodeId, @scenarioId3, "2021-01-01", 45.9, null, null, "", 1, @dt, 1, @dt, 1);
INSERT INTO rm_forecast_tree_region values (null, @treeId, 70);


INSERT INTO rm_forecast_tree_node_data_modeling VALUES (null, 1, '2021-01-01', '2021-12-31', 3, 0.0875, null, 'An increase of 0.0875% every month which equates to a 1.05% increase every year', 9, now(), 9, now(), 1);
INSERT INTO rm_forecast_tree_node_data_modeling VALUES (null, 1, '2022-01-01', '2025-12-31', 3, 0.0975, null, 'An increase of 0.0975% every month which equates to a 1.05% increase every year', 9, now(), 9, now(), 1);
INSERT INTO rm_forecast_tree_node_data_modeling VALUES (null, 4, '2021-01-01', '2021-12-31', 4, 0.05, null, 'An increase of 0.05% of sexually active men every month which equates to a 1.05% increase every year', 9, now(), 9, now(), 1);
INSERT INTO rm_forecast_tree_node_data_modeling VALUES (null, 4, '2022-01-01', '2025-12-31', 4, 0.073, null, 'An increase of 0.073% of sexually active men every month which equates to a 1.05% increase every year', 9, now(), 9, now(), 1);
INSERT INTO rm_forecast_tree_node_data_modeling VALUES (null, 6, '2021-01-01', '2025-12-31', 2, -8750, 7, '8750 men move over from No logo to Strawberry condoms every month', 9, now(), 9, now(), 1);

INSERT INTO rm_forecast_tree_node_data_modeling VALUES (null, 42, '2021-01-01', '2025-12-31', 2, 25500, null, 'An increase of 25500 number of Patients every month', 9, now(), 9, now(), 1);
INSERT INTO rm_forecast_tree_node_data_modeling VALUES (null, 42, '2021-01-01', '2021-12-31', 2, -4760, 45, '4760 Patients move from Line 1 to Line 2 every month', 9, now(), 9, now(), 1);
INSERT INTO rm_forecast_tree_node_data_modeling VALUES (null, 42, '2022-01-01', '2022-12-31', 2, -5500, 45, '5500 Patients move from Line 1 to Line 2 every month', 9, now(), 9, now(), 1);
INSERT INTO rm_forecast_tree_node_data_modeling VALUES (null, 42, '2023-01-01', '2023-12-31', 2, -6340, 45, '6340 Patients move from Line 1 to Line 2 every month', 9, now(), 9, now(), 1);
INSERT INTO rm_forecast_tree_node_data_modeling VALUES (null, 42, '2024-01-01', '2024-12-31', 2, -7120, 45, '7120 Patients move from Line 1 to Line 2 every month', 9, now(), 9, now(), 1);
INSERT INTO rm_forecast_tree_node_data_modeling VALUES (null, 42, '2025-01-01', '2025-12-31', 2, -8030, 45, '8030 Patients move from Line 1 to Line 2 every month', 9, now(), 9, now(), 1);

INSERT INTO ap_label_source VALUES (null, 'rm_forecast_consumption_unit');
INSERT INTO `fasp`.`rm_forecast_consumption_unit` (`PROGRAM_ID`, `FORECASTING_UNIT_ID`, `DATA_TYPE`, `PLANNING_UNIT_ID`, `OTHER_UNIT_LABEL_ID`, `OTHER_UNIT_MULTIPLIER_FOR_FU`, `VERSION_ID`, `CREATED_BY`, `CREATED_DATE`) VALUES (@programId, '911', '1', NULL, NULL, NULL, '1', '1', '2021-10-27 00:00:00');
INSERT INTO `fasp`.`rm_forecast_consumption_unit` (`PROGRAM_ID`, `FORECASTING_UNIT_ID`, `DATA_TYPE`, `PLANNING_UNIT_ID`, `OTHER_UNIT_LABEL_ID`, `OTHER_UNIT_MULTIPLIER_FOR_FU`, `VERSION_ID`, `CREATED_BY`, `CREATED_DATE`) VALUES (@programId, '915', '2', '4148', NULL, NULL, '1', '1', '2021-10-27 00:00:00');
INSERT INTO ap_label values (null, "10 Bottles of TLD 90", null, null, null, 1, now(), 1, now(), 51);
SELECT LAST_INSERT_ID() into @labelId;
INSERT INTO `fasp`.`rm_forecast_consumption_unit` (`PROGRAM_ID`, `FORECASTING_UNIT_ID`, `DATA_TYPE`, `PLANNING_UNIT_ID`, `OTHER_UNIT_LABEL_ID`, `OTHER_UNIT_MULTIPLIER_FOR_FU`, `VERSION_ID`, `CREATED_BY`, `CREATED_DATE`) VALUES (@programId, '2665', '3', NULL, @labelId, 900, '1', '1', '2021-10-27 00:00:00');

INSERT INTO ap_label values (null, "North", null, null, null, 1, now(), 1, now(), 11);
SELECT LAST_INSERT_ID() into @labelId;
INSERT INTO rm_region values (null, 1, 44, @labelId, null, null, 1, 1, now(), 1, now());
SELECT LAST_INSERT_ID() into @northId;
INSERT INTO rm_program_region values (null, @programId, @northId, 1, 1, now(), 1, now());

INSERT INTO ap_label values (null, "South", null, null, null, 1, now(), 1, now(), 11);
SELECT LAST_INSERT_ID() into @labelId;
INSERT INTO rm_region values (null, 1, 44, @labelId, null, null, 1, 1, now(), 1, now());
SELECT LAST_INSERT_ID() into @southId;
INSERT INTO rm_program_region values (null, @programId, @southId, 1, 1, now(), 1, now());

INSERT INTO rm_forecast_consumption values (null, @programId, 1, 70, '2020-01-01', 5000, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 1, 70, '2020-02-01', 6500, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 1, 70, '2020-03-01', 6200, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 1, 70, '2020-04-01', 7000, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 1, 70, '2020-05-01', 6800, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 1, 70, '2020-06-01', 6400, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 1, 70, '2020-07-01', 5800, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 1, 70, '2020-08-01', 5900, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 1, 70, '2020-09-01', 6300, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 1, 70, '2020-10-01', 6900, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 1, 70, '2020-11-01', 7500, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 1, 70, '2020-12-01', 7000, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 1, 70, '2021-01-01', 7100, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 1, 70, '2021-02-01', 8400, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 1, 70, '2021-03-01', 8300, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 1, 70, '2021-04-01', 9000, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 1, 70, '2021-05-01', 7600, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 1, 70, '2021-06-01', 7000, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 1, 70, '2021-07-01', 6700, null, null, 0, 1, 1, now());

INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2020-01-01', 3600, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2020-02-01', 3800, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2020-03-01', 3500, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2020-04-01', 3650, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2020-05-01', 3700, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2020-06-01', 3200, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2020-07-01', 3780, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2020-08-01', 3900, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2020-09-01', 3450, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2020-10-01', 3280, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2020-11-01', 3450, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2020-12-01', 3600, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2021-01-01', 3730, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2021-02-01', 3500, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2021-03-01', 3380, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2021-04-01', 3840, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2021-05-01', 3480, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2021-06-01', 3370, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2021-07-01', 3600, null, null, 0, 1, 1, now());

INSERT INTO rm_forecast_consumption values (null, @programId, 2, @southId, '2020-01-01', 5292, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2020-02-01', 5586, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2020-03-01', 5145, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2020-04-01', 5365, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2020-05-01', 5439, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2020-06-01', 4704, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2020-07-01', 5556, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2020-08-01', 5733, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2020-09-01', 5071, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2020-10-01', 4821, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2020-11-01', 5071, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2020-12-01', 5292, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2021-01-01', 5483, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2021-02-01', 5145, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2021-03-01', 4968, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2021-04-01', 5644, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2021-05-01', 5115, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2021-06-01', 4953, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 2, @northId, '2021-07-01', 5292, null, null, 0, 1, 1, now());

INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2020-01-01', 540, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2020-02-01', 480, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2020-03-01', 580, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2020-04-01', 500, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2020-05-01', 560, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2020-06-01', 590, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2020-07-01', 570, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2020-08-01', 610, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2020-09-01', 570, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2020-10-01', 590, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2020-11-01', 630, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2020-12-01', 610, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2021-01-01', 600, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2021-02-01', 620, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2021-03-01', 610, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2021-04-01', 650, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2021-05-01', 620, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2021-06-01', 630, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2021-07-01', 680, null, null, 0, 1, 1, now());

INSERT INTO rm_forecast_consumption values (null, @programId, 3, @southId, '2020-01-01', 1274, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2020-02-01', 1132, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2020-03-01', 1368, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2020-04-01', 1180, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2020-05-01', 1321, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2020-06-01', 1392, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2020-07-01', 1345, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2020-08-01', 1439, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2020-09-01', 1345, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2020-10-01', 1392, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2020-11-01', 1486, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2020-12-01', 1439, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2021-01-01', 1416, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2021-02-01', 1463, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2021-03-01', 1439, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2021-04-01', 1534, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2021-05-01', 1463, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2021-06-01', 1486, null, null, 0, 1, 1, now());
INSERT INTO rm_forecast_consumption values (null, @programId, 3, @northId, '2021-07-01', 1604, null, null, 0, 1, 1, now());

INSERT INTO rm_tree_template_node_data_modeling values (null, 1, 0, 11, 3, 0.0875, null, ' An increase of 0.0875% every month which equates to a 1.05% increase every year', 1, now(), 1, now(), 1);
INSERT INTO rm_tree_template_node_data_modeling values (null, 1, 12, 48, 3, 0.0975, null, ' An increase of 0.0975% every month which equates to a 1.05% increase every year', 1, now(), 1, now(), 1);
INSERT INTO rm_tree_template_node_data_modeling values (null, 4, 0, 11, 4, 0.05, null, ' An increase of 0.05% of sexually active men every month which equates to a 1.05% increase every year', 1, now(), 1, now(), 1);
INSERT INTO rm_tree_template_node_data_modeling values (null, 4, 12, 48, 4, 0.073, null, ' An increase of 0.073% of sexually active men every month which equates to a 1.05% increase every year', 1, now(), 1, now(), 1);
INSERT INTO rm_tree_template_node_data_modeling values (null, 6, 0, 48, 2, -8750, 7, ' 8750 men move over from No logo to Strawberry condoms every month', 1, now(), 1, now(), 1);

UPDATE `fasp`.`rm_forecast_consumption_unit` SET `PLANNING_UNIT_ID`='2733' WHERE `CONSUMPTION_UNIT_ID`='3';
UPDATE `fasp`.`rm_forecast_consumption_unit` SET `PLANNING_UNIT_ID`='4159' WHERE `CONSUMPTION_UNIT_ID`='1';

UPDATE `fasp`.`rm_forecast_tree_node_data_modeling` SET `MODELING_TYPE_ID`='5', `DATA_VALUE`='-0.5' WHERE `NODE_DATA_MODELING_ID`='5';
UPDATE `fasp`.`rm_forecast_tree_node_data_modeling` SET `MODELING_TYPE_ID`='5' WHERE `NODE_DATA_MODELING_ID`='3';
UPDATE `fasp`.`rm_forecast_tree_node_data_modeling` SET `MODELING_TYPE_ID`='5' WHERE `NODE_DATA_MODELING_ID`='4';

INSERT INTO `fasp`.`rm_dataset_planning_unit` (`PROGRAM_ID`, `VERSION_ID`, `PLANNING_UNIT_ID`, `CONSUMPTION_FORECAST`, `TREE_FORECAST`, `PROCUREMENT_AGENT_ID`, `PRICE`) VALUES (@programId, '1', '4148', '1', '1', '1', '0.03');
INSERT INTO `fasp`.`rm_dataset_planning_unit` (`PROGRAM_ID`, `VERSION_ID`, `PLANNING_UNIT_ID`, `CONSUMPTION_FORECAST`, `TREE_FORECAST`, `PRICE`) VALUES (@programId, '1', '4149', '1', '1', '0.045');
INSERT INTO `fasp`.`rm_dataset_planning_unit` (`PROGRAM_ID`, `VERSION_ID`, `PLANNING_UNIT_ID`, `CONSUMPTION_FORECAST`, `TREE_FORECAST`, `PROCUREMENT_AGENT_ID`, `PRICE`) VALUES (@programId, '1', '2733', '1', '0', '1', '5.49');

INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation_list` (`PROGRAM_ID`, `PLANNING_UNIT_ID`, `REGION_ID`, `VERSION_ID`) VALUES ('2557', '4149', '70', '1');
INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation_list` (`PROGRAM_ID`, `PLANNING_UNIT_ID`, `REGION_ID`, `VERSION_ID`) VALUES ('2557', '4149', '73', '1');
INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation_list` (`PROGRAM_ID`, `PLANNING_UNIT_ID`, `REGION_ID`, `VERSION_ID`) VALUES ('2557', '4148', '70', '1');
INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation_list` (`PROGRAM_ID`, `PLANNING_UNIT_ID`, `REGION_ID`, `VERSION_ID`) VALUES ('2557', '2733', '70', '1');

INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation_settings` (`CONSUMPTION_EXTRAPOLATION_LIST_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`) VALUES ('1', '1', '{"confidenceLevel":"95","seasonality":"12","alpha":"1","beta":"1","gamma":"1","phi":"1"}');
INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation_settings` (`CONSUMPTION_EXTRAPOLATION_LIST_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`) VALUES ('1', '2', '{"confidenceLevel":"95","seasonality":"12","alpha":"1","beta":"1","gamma":"1","phi":"1"}');
INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation_settings` (`CONSUMPTION_EXTRAPOLATION_LIST_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`) VALUES ('1', '3', '{"confidenceLevel":"95","seasonality":"12","alpha":"1","beta":"1","gamma":"1","phi":"1"}');
INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation_settings` (`CONSUMPTION_EXTRAPOLATION_LIST_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`) VALUES ('1', '4', '{"p":"95","d":"12","q":"12"}');
INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation_settings` (`CONSUMPTION_EXTRAPOLATION_LIST_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`) VALUES ('1', '5', null);
INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation_settings` (`CONSUMPTION_EXTRAPOLATION_LIST_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`) VALUES ('1', '6', null);
INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation_settings` (`CONSUMPTION_EXTRAPOLATION_LIST_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`) VALUES ('1', '7', '{"months":"5"}');

INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation_settings` (`CONSUMPTION_EXTRAPOLATION_LIST_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`) VALUES ('2', '1', '{"confidenceLevel":"95","seasonality":"12","alpha":"1","beta":"1","gamma":"1","phi":"1"}');
INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation_settings` (`CONSUMPTION_EXTRAPOLATION_LIST_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`) VALUES ('2', '2', '{"confidenceLevel":"95","seasonality":"12","alpha":"1","beta":"1","gamma":"1","phi":"1"}');
INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation_settings` (`CONSUMPTION_EXTRAPOLATION_LIST_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`) VALUES ('2', '3', '{"confidenceLevel":"95","seasonality":"12","alpha":"1","beta":"1","gamma":"1","phi":"1"}');
INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation_settings` (`CONSUMPTION_EXTRAPOLATION_LIST_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`) VALUES ('2', '4', '{"p":"95","d":"12","q":"12"}');
INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation_settings` (`CONSUMPTION_EXTRAPOLATION_LIST_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`) VALUES ('2', '5', null);
INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation_settings` (`CONSUMPTION_EXTRAPOLATION_LIST_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`) VALUES ('2', '6', null);
INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation_settings` (`CONSUMPTION_EXTRAPOLATION_LIST_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`) VALUES ('2', '7', '{"months":"5"}');

INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation_settings` (`CONSUMPTION_EXTRAPOLATION_LIST_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`) VALUES ('3', '4', '{"p":"95","d":"12","q":"12"}');
INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation_settings` (`CONSUMPTION_EXTRAPOLATION_LIST_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`) VALUES ('3', '5', null);
INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation_settings` (`CONSUMPTION_EXTRAPOLATION_LIST_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`) VALUES ('3', '6', null);
INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation_settings` (`CONSUMPTION_EXTRAPOLATION_LIST_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`) VALUES ('3', '7', '{"months":"5"}');

INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation_settings` (`CONSUMPTION_EXTRAPOLATION_LIST_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`) VALUES ('4', '5', null);
INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation_settings` (`CONSUMPTION_EXTRAPOLATION_LIST_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`) VALUES ('4', '6', null);

INSERT INTO `fasp`.`rm_dataset_planning_unit_selected` (`PROGRAM_PLANNING_UNIT_ID`, `REGION_ID`, `SCENARIO_ID`) VALUES ('1', '70', '1');
INSERT INTO `fasp`.`rm_dataset_planning_unit_selected` (`PROGRAM_PLANNING_UNIT_ID`, `REGION_ID`, `SCENARIO_ID`) VALUES ('2', '70', '2');
INSERT INTO `fasp`.`rm_dataset_planning_unit_selected` (`PROGRAM_PLANNING_UNIT_ID`, `REGION_ID`, `EXTRAPOLATION_SETTINGS_ID`) VALUES ('3', '70', '19');

UPDATE fasp.rm_dataset_planning_unit dpu LEFT JOIN rm_forecast_consumption_unit fcu ON dpu.PROGRAM_ID=fcu.PROGRAM_ID AND dpu.PLANNING_UNIT_ID=fcu.PLANNING_UNIT_ID 
SET 
dpu.CONSUMPTION_DATA_TYPE_ID=fcu.DATA_TYPE,
dpu.OTHER_LABEL_ID=fcu.OTHER_UNIT_LABEL_ID,
dpu.OTHER_MULTIPLIER=fcu.OTHER_UNIT_MULTIPLIER_FOR_FU,
dpu.CREATED_BY=COALESCE(fcu.CREATED_BY,1),
dpu.CREATED_DATE=COALESCE(fcu.CREATED_DATE,now());

TRUNCATE TABLE rm_forecast_consumption_extrapolation;

INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation` (`PROGRAM_ID`, `PLANNING_UNIT_ID`, `REGION_ID`, `VERSION_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`, `CREATED_DATE`, `CREATED_BY`) VALUES ('2557', '4149', '70', '1', '1', '{"confidenceLevel":"95","seasonality":"12","alpha":"1","beta":"1","gamma":"1","phi":"1"}', now(), 1);
INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation` (`PROGRAM_ID`, `PLANNING_UNIT_ID`, `REGION_ID`, `VERSION_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`, `CREATED_DATE`, `CREATED_BY`) VALUES ('2557', '4149', '70', '1', '2', '{"confidenceLevel":"95","seasonality":"12","alpha":"1","beta":"1","gamma":"1","phi":"1"}', now(), 1);
INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation` (`PROGRAM_ID`, `PLANNING_UNIT_ID`, `REGION_ID`, `VERSION_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`, `CREATED_DATE`, `CREATED_BY`) VALUES ('2557', '4149', '70', '1', '3', '{"confidenceLevel":"95","seasonality":"12","alpha":"1","beta":"1","gamma":"1","phi":"1"}', now(), 1);
INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation` (`PROGRAM_ID`, `PLANNING_UNIT_ID`, `REGION_ID`, `VERSION_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`, `CREATED_DATE`, `CREATED_BY`) VALUES ('2557', '4149', '70', '1', '4', '{"p":"95","d":"12","q":"12"}', now(), 1);
INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation` (`PROGRAM_ID`, `PLANNING_UNIT_ID`, `REGION_ID`, `VERSION_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`, `CREATED_DATE`, `CREATED_BY`) VALUES ('2557', '4149', '70', '1', '5', null, now(), 1);
INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation` (`PROGRAM_ID`, `PLANNING_UNIT_ID`, `REGION_ID`, `VERSION_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`, `CREATED_DATE`, `CREATED_BY`) VALUES ('2557', '4149', '70', '1', '6', null, now(), 1);
INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation` (`PROGRAM_ID`, `PLANNING_UNIT_ID`, `REGION_ID`, `VERSION_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`, `CREATED_DATE`, `CREATED_BY`) VALUES ('2557', '4149', '70', '1', '7', '{"months":"5"}', now(), 1);

INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation` (`PROGRAM_ID`, `PLANNING_UNIT_ID`, `REGION_ID`, `VERSION_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`, `CREATED_DATE`, `CREATED_BY`) VALUES ('2557', '4149', '73', '1', '1', '{"confidenceLevel":"95","seasonality":"12","alpha":"1","beta":"1","gamma":"1","phi":"1"}', now(), 1);
INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation` (`PROGRAM_ID`, `PLANNING_UNIT_ID`, `REGION_ID`, `VERSION_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`, `CREATED_DATE`, `CREATED_BY`) VALUES ('2557', '4149', '73', '1', '2', '{"confidenceLevel":"95","seasonality":"12","alpha":"1","beta":"1","gamma":"1","phi":"1"}', now(), 1);
INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation` (`PROGRAM_ID`, `PLANNING_UNIT_ID`, `REGION_ID`, `VERSION_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`, `CREATED_DATE`, `CREATED_BY`) VALUES ('2557', '4149', '73', '1', '3', '{"confidenceLevel":"95","seasonality":"12","alpha":"1","beta":"1","gamma":"1","phi":"1"}', now(), 1);
INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation` (`PROGRAM_ID`, `PLANNING_UNIT_ID`, `REGION_ID`, `VERSION_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`, `CREATED_DATE`, `CREATED_BY`) VALUES ('2557', '4149', '73', '1', '4', '{"p":"95","d":"12","q":"12"}', now(), 1);
INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation` (`PROGRAM_ID`, `PLANNING_UNIT_ID`, `REGION_ID`, `VERSION_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`, `CREATED_DATE`, `CREATED_BY`) VALUES ('2557', '4149', '73', '1', '5', null, now(), 1);
INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation` (`PROGRAM_ID`, `PLANNING_UNIT_ID`, `REGION_ID`, `VERSION_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`, `CREATED_DATE`, `CREATED_BY`) VALUES ('2557', '4149', '73', '1', '6', null, now(), 1);
INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation` (`PROGRAM_ID`, `PLANNING_UNIT_ID`, `REGION_ID`, `VERSION_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`, `CREATED_DATE`, `CREATED_BY`) VALUES ('2557', '4149', '73', '1', '7', '{"months":"5"}', now(), 1);

INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation` (`PROGRAM_ID`, `PLANNING_UNIT_ID`, `REGION_ID`, `VERSION_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`, `CREATED_DATE`, `CREATED_BY`) VALUES ('2557', '4148', '70', '1', '4', '{"p":"95","d":"12","q":"12"}', now(), 1);
INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation` (`PROGRAM_ID`, `PLANNING_UNIT_ID`, `REGION_ID`, `VERSION_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`, `CREATED_DATE`, `CREATED_BY`) VALUES ('2557', '4148', '70', '1', '5', null, now(), 1);
INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation` (`PROGRAM_ID`, `PLANNING_UNIT_ID`, `REGION_ID`, `VERSION_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`, `CREATED_DATE`, `CREATED_BY`) VALUES ('2557', '4148', '70', '1', '6', null, now(), 1);
INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation` (`PROGRAM_ID`, `PLANNING_UNIT_ID`, `REGION_ID`, `VERSION_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`, `CREATED_DATE`, `CREATED_BY`) VALUES ('2557', '4148', '70', '1', '7', '{"months":"5"}', now(), 1);

INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation` (`PROGRAM_ID`, `PLANNING_UNIT_ID`, `REGION_ID`, `VERSION_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`, `CREATED_DATE`, `CREATED_BY`) VALUES ('2557', '2733', '70', '1', '5', null, now(), 1);
INSERT INTO `fasp`.`rm_forecast_consumption_extrapolation` (`PROGRAM_ID`, `PLANNING_UNIT_ID`, `REGION_ID`, `VERSION_ID`, `EXTRAPOLATION_METHOD_ID`, `JSON_PROPERTIES`, `CREATED_DATE`, `CREATED_BY`) VALUES ('2557', '2733', '70', '1', '6', null, now(), 1);

DROP TABLE rm_forecast_consumption_extrapolation_settings;
DROP TABLE rm_forecast_consumption_unit;
DROP VIEW `fasp`.`vw_forecast_consumption_unit`;

UPDATE `fasp`.`rm_dataset_planning_unit` SET `CONSUMPTION_DATA_TYPE_ID` = '1' WHERE (`PROGRAM_PLANNING_UNIT_ID` = '2');

insert into rm_tree_template_node SELECT ftn.NODE_ID, 2, ftn.PARENT_NODE_ID, ftn.SORT_ORDER, ftn.LEVEL_NO, ftn.NODE_TYPE_ID, ftn.UNIT_ID, ftn.LABEL_ID+@labelId-@oldLabelId, ftn.CREATED_BY, ftn.CREATED_DATE, ftn.LAST_MODIFIED_BY, ftn.LAST_MODIFIED_DATE, ftn.ACTIVE from rm_forecast_tree_node ftn where ftn.TREE_ID=2;
INSERT INTO rm_tree_template_node_data SELECT NODE_DATA_ID, NODE_ID, 0, DATA_VALUE, NODE_DATA_FU_ID, NODE_DATA_PU_ID, NOTES, MANUAL_CHANGES_EFFECT_FUTURE, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, ACTIVE FROM rm_forecast_tree_node_data ftnd where ftnd.NODE_ID between 16 and 21 AND ftnd.SCENARIO_ID=3;

update rm_forecast_actual_consumption a 
set a.PLANNING_UNIT_ID = '4148'
where a.PLANNING_UNIT_ID =1;

UPDATE rm_forecast_actual_consumption a 
SET a.PLANNING_UNIT_ID = '4149'
WHERE a.PLANNING_UNIT_ID =2;

UPDATE rm_forecast_actual_consumption a 
SET a.PLANNING_UNIT_ID = '2733'
WHERE a.PLANNING_UNIT_ID =3;


DELETE ntr.* FROM ap_node_type_rule ntr where ntr.NODE_TYPE_ID=6;
DELETE ntr.* FROM ap_node_type_rule ntr where ntr.CHILD_NODE_TYPE_ID=6;
DELETE nt.* FROM ap_node_type nt WHERE nt.NODE_TYPE_ID=6;
SET @treeId = 1;
INSERT INTO ap_label values (null, "Country level", null, null, null, 1, now(), 1, now(), 53);
SELECT LAST_INSERT_ID() into @labelId;
INSERT INTO rm_forecast_tree_level VALUES (null, @treeId, 0, @labelId, 94);
INSERT INTO ap_label values (null, "Gender level", null, null, null, 1, now(), 1, now(), 53);
SELECT LAST_INSERT_ID() into @labelId;
INSERT INTO rm_forecast_tree_level VALUES (null, @treeId, 1, @labelId, 94);
INSERT INTO ap_label values (null, "Sexually active level", null, null, null, 1, now(), 1, now(), 53);
SELECT LAST_INSERT_ID() into @labelId;
INSERT INTO rm_forecast_tree_level VALUES (null, @treeId, 2, @labelId, 94);
INSERT INTO ap_label values (null, "Contraceptive level", null, null, null, 1, now(), 1, now(), 53);
SELECT LAST_INSERT_ID() into @labelId;
INSERT INTO rm_forecast_tree_level VALUES (null, @treeId, 3, @labelId, 94);
INSERT INTO ap_label values (null, "FU level", null, null, null, 1, now(), 1, now(), 53);
SELECT LAST_INSERT_ID() into @labelId;
INSERT INTO rm_forecast_tree_level VALUES (null, @treeId, 4, @labelId, null);
INSERT INTO ap_label values (null, "PU level", null, null, null, 1, now(), 1, now(), 53);
SELECT LAST_INSERT_ID() into @labelId;
INSERT INTO rm_forecast_tree_level VALUES (null, @treeId, 5, @labelId, null);

UPDATE rm_dataset_planning_unit_selected pus SET pus.TREE_ID=1 where pus.SCENARIO_ID between 1 AND 2;


UPDATE rm_tree_template_node_data_pu SET PU_PER_VISIT = 1;
UPDATE rm_forecast_tree_node_data_pu SET PU_PER_VISIT = 1;

UPDATE rm_dataset_planning_unit dpu SET dpu.ACTIVE=1;