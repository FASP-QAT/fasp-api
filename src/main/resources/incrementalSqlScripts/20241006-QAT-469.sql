DELETE ccr.* FROM us_can_create_role ccr WHERE ccr.ROLE_ID='ROLE_PROGRAM_ADMIN' AND (ccr.CAN_CREATE_ROLE='ROLE_INTERNAL_USER' OR ccr.CAN_CREATE_ROLE='ROLE_GUEST_USER');

INSERT INTO ap_label VALUES (null, 'SP - View Update Planning Units', null, null, null, 1, now(), 1, now(), 1);
INSERT INTO us_business_function values ('ROLE_BF_LIST_PROGRAM_PRODUCT', last_insert_id(), 1, now(), 1, now());
insert into us_role_business_function values (null,'ROLE_PROGRAM_USER','ROLE_BF_LIST_PROGRAM_PRODUCT',1,now(),1,now());
insert into us_role_business_function values (null,'ROLE_PROGRAM_USER','ROLE_BF_LIST_PROGRAM',1,now(),1,now());