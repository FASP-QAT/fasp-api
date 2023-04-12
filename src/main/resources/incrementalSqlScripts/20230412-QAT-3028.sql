DELETE r.* FROM rm_region r LEFT JOIN ap_label l ON r.LABEL_ID=l.LABEL_ID where r.REALM_COUNTRY_ID in (11, 46) AND r.REGION_ID not IN (12, 47, 81,82,88,91);
DELETE l.* FROM ap_label l where l.LABEL_ID IN (36267,36274,36293,36315,36334,36373);
UPDATE rm_program_region pr SET pr.REGION_ID=82 WHERE pr.REGION_ID=88;
UPDATE rm_program_region pr SET pr.REGION_ID=81 WHERE pr.REGION_ID=91;
UPDATE ct_supply_plan_consumption pr SET pr.REGION_ID=82 WHERE pr.REGION_ID=88;
UPDATE ct_supply_plan_consumption pr SET pr.REGION_ID=81 WHERE pr.REGION_ID=91;
UPDATE ct_supply_plan_inventory pr SET pr.REGION_ID=82 WHERE pr.REGION_ID=88;
UPDATE ct_supply_plan_inventory pr SET pr.REGION_ID=81 WHERE pr.REGION_ID=91;
UPDATE rm_consumption_trans pr SET pr.REGION_ID=82 WHERE pr.REGION_ID=88;
UPDATE rm_consumption_trans pr SET pr.REGION_ID=81 WHERE pr.REGION_ID=91;
UPDATE rm_dataset_planning_unit_selected pr SET pr.REGION_ID=82 WHERE pr.REGION_ID=88; -- 1 row
UPDATE rm_dataset_planning_unit_selected pr SET pr.REGION_ID=81 WHERE pr.REGION_ID=91;
UPDATE rm_forecast_actual_consumption pr SET pr.REGION_ID=82 WHERE pr.REGION_ID=88; -- 18 rows
UPDATE rm_forecast_actual_consumption pr SET pr.REGION_ID=81 WHERE pr.REGION_ID=91;
UPDATE rm_forecast_consumption_extrapolation pr SET pr.REGION_ID=82 WHERE pr.REGION_ID=88; -- 5 rows
UPDATE rm_forecast_consumption_extrapolation pr SET pr.REGION_ID=81 WHERE pr.REGION_ID=91;
UPDATE rm_forecast_tree_region pr SET pr.REGION_ID=82 WHERE pr.REGION_ID=88; -- 36 rows
UPDATE rm_forecast_tree_region pr SET pr.REGION_ID=81 WHERE pr.REGION_ID=91; 
UPDATE rm_inventory_trans pr SET pr.REGION_ID=82 WHERE pr.REGION_ID=88;
UPDATE rm_inventory_trans pr SET pr.REGION_ID=81 WHERE pr.REGION_ID=91;
DELETE r.* FROM rm_region r where r.REGION_ID=88;
DELETE l.* FROM rm_region r LEFT JOIN ap_label l ON r.LABEL_ID=l.LABEL_ID where r.REGION_ID=88;
DELETE r.* FROM rm_region r where r.REGION_ID=91;
DELETE l.* FROM ap_label l where l.LABEL_ID in (36424, 36335, 36398, 37338, 37362); -- 5 rows