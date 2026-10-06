INSERT IGNORE INTO `ap_security` (`METHOD`, `URL`, `BF`) 
VALUES (1, '/api/dataset', 'ROLE_BF_SUPPLY_PLAN_IMPORT');

INSERT IGNORE INTO `ap_security` (`METHOD`, `URL`, `BF`) 
VALUES (2, '/api/dropdown/planningUnit/dataset/filter/programAndVersion', 'ROLE_BF_SUPPLY_PLAN_IMPORT');