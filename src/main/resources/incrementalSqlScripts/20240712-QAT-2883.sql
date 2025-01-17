

UPDATE rm_funding_source_type fst LEFT JOIN ap_label l ON fst.LABEL_ID=l.LABEL_ID SET fst.FUNDING_SOURCE_TYPE_CODE='Bilateral', l.LABEL_EN='Bilateral Donor' WHERE fst.FUNDING_SOURCE_TYPE_ID=1;	
INSERT INTO ap_label values (null, 'Multilateral Donor' , null, null, null, 9, now(), 9, now(), 56);	INSERT INTO rm_funding_source_type VALUES (null, 1, 'Multilat', LAST_INSERT_ID(), 1, 9, now(), 9, now());
INSERT INTO ap_label values (null, 'Non-Government Organization' , null, null, null, 9, now(), 9, now(), 56);	INSERT INTO rm_funding_source_type VALUES (null, 1, 'NGO', LAST_INSERT_ID(), 1, 9, now(), 9, now());
INSERT INTO ap_label values (null, 'Government' , null, null, null, 9, now(), 9, now(), 56);	INSERT INTO rm_funding_source_type VALUES (null, 1, 'Gov', LAST_INSERT_ID(), 1, 9, now(), 9, now());
INSERT INTO ap_label values (null, 'TBD' , null, null, null, 9, now(), 9, now(), 56);	INSERT INTO rm_funding_source_type VALUES (null, 1, 'TBD', LAST_INSERT_ID(), 1, 9, now(), 9, now());
INSERT INTO ap_label values (null, 'Other' , null, null, null, 9, now(), 9, now(), 56);	INSERT INTO rm_funding_source_type VALUES (null, 1, 'Other', LAST_INSERT_ID(), 1, 9, now(), 9, now());

UPDATE rm_funding_source fs SET fs.FUNDING_SOURCE_TYPE_ID=1, fs.LAST_MODIFIED_DATE=now(), fs.LAST_MODIFIED_BY=9 WHERE fs.FUNDING_SOURCE_CODE='USAID';
UPDATE rm_funding_source fs SET fs.FUNDING_SOURCE_TYPE_ID=2, fs.LAST_MODIFIED_DATE=now(), fs.LAST_MODIFIED_BY=9 WHERE fs.FUNDING_SOURCE_CODE='UNFPA';
UPDATE rm_funding_source fs SET fs.FUNDING_SOURCE_TYPE_ID=3, fs.LAST_MODIFIED_DATE=now(), fs.LAST_MODIFIED_BY=9 WHERE fs.FUNDING_SOURCE_CODE='IPPF';
UPDATE rm_funding_source fs SET fs.FUNDING_SOURCE_TYPE_ID=2, fs.LAST_MODIFIED_DATE=now(), fs.LAST_MODIFIED_BY=9 WHERE fs.FUNDING_SOURCE_CODE='GFATM';
UPDATE rm_funding_source fs SET fs.FUNDING_SOURCE_TYPE_ID=4, fs.LAST_MODIFIED_DATE=now(), fs.LAST_MODIFIED_BY=9 WHERE fs.FUNDING_SOURCE_CODE='Govt';
UPDATE rm_funding_source fs SET fs.FUNDING_SOURCE_TYPE_ID=2, fs.LAST_MODIFIED_DATE=now(), fs.LAST_MODIFIED_BY=9 WHERE fs.FUNDING_SOURCE_CODE='UNICEF';
UPDATE rm_funding_source fs SET fs.FUNDING_SOURCE_TYPE_ID=2, fs.LAST_MODIFIED_DATE=now(), fs.LAST_MODIFIED_BY=9 WHERE fs.FUNDING_SOURCE_CODE='PAHO-SF';
UPDATE rm_funding_source fs SET fs.FUNDING_SOURCE_TYPE_ID=5, fs.LAST_MODIFIED_DATE=now(), fs.LAST_MODIFIED_BY=9 WHERE fs.FUNDING_SOURCE_CODE='TBD';
UPDATE rm_funding_source fs SET fs.FUNDING_SOURCE_TYPE_ID=2, fs.LAST_MODIFIED_DATE=now(), fs.LAST_MODIFIED_BY=9 WHERE fs.FUNDING_SOURCE_CODE='Unitaid';
UPDATE rm_funding_source fs SET fs.FUNDING_SOURCE_TYPE_ID=1, fs.LAST_MODIFIED_DATE=now(), fs.LAST_MODIFIED_BY=9 WHERE fs.FUNDING_SOURCE_CODE='CDC';
UPDATE rm_funding_source fs SET fs.FUNDING_SOURCE_TYPE_ID=2, fs.LAST_MODIFIED_DATE=now(), fs.LAST_MODIFIED_BY=9 WHERE fs.FUNDING_SOURCE_CODE='WHO';
UPDATE rm_funding_source fs SET fs.FUNDING_SOURCE_TYPE_ID=6, fs.LAST_MODIFIED_DATE=now(), fs.LAST_MODIFIED_BY=9 WHERE fs.FUNDING_SOURCE_CODE='DONATIO';
UPDATE rm_funding_source fs SET fs.FUNDING_SOURCE_TYPE_ID=3, fs.LAST_MODIFIED_DATE=now(), fs.LAST_MODIFIED_BY=9 WHERE fs.FUNDING_SOURCE_CODE='NEWHOR';
UPDATE rm_funding_source fs SET fs.FUNDING_SOURCE_TYPE_ID=2, fs.LAST_MODIFIED_DATE=now(), fs.LAST_MODIFIED_BY=9 WHERE fs.FUNDING_SOURCE_CODE='UNDP';
UPDATE rm_funding_source fs SET fs.FUNDING_SOURCE_TYPE_ID=1, fs.LAST_MODIFIED_DATE=now(), fs.LAST_MODIFIED_BY=9 WHERE fs.FUNDING_SOURCE_CODE='FCDO';
UPDATE rm_funding_source fs SET fs.FUNDING_SOURCE_TYPE_ID=3, fs.LAST_MODIFIED_DATE=now(), fs.LAST_MODIFIED_BY=9 WHERE fs.FUNDING_SOURCE_CODE='GATES';
UPDATE rm_funding_source fs SET fs.FUNDING_SOURCE_TYPE_ID=1, fs.LAST_MODIFIED_DATE=now(), fs.LAST_MODIFIED_BY=9 WHERE fs.FUNDING_SOURCE_CODE='PEPFAR';
UPDATE rm_funding_source fs SET fs.FUNDING_SOURCE_TYPE_ID=1, fs.LAST_MODIFIED_DATE=now(), fs.LAST_MODIFIED_BY=9 WHERE fs.FUNDING_SOURCE_CODE='PMI';
UPDATE rm_funding_source fs SET fs.FUNDING_SOURCE_TYPE_ID=2, fs.LAST_MODIFIED_DATE=now(), fs.LAST_MODIFIED_BY=9 WHERE fs.FUNDING_SOURCE_CODE='WB';
UPDATE rm_funding_source fs SET fs.FUNDING_SOURCE_TYPE_ID=3, fs.LAST_MODIFIED_DATE=now(), fs.LAST_MODIFIED_BY=9 WHERE fs.FUNDING_SOURCE_CODE='MSF';
UPDATE rm_funding_source fs SET fs.FUNDING_SOURCE_TYPE_ID=2, fs.LAST_MODIFIED_DATE=now(), fs.LAST_MODIFIED_BY=9 WHERE fs.FUNDING_SOURCE_CODE='ECSA';
UPDATE rm_funding_source fs SET fs.FUNDING_SOURCE_TYPE_ID=3, fs.LAST_MODIFIED_DATE=now(), fs.LAST_MODIFIED_BY=9 WHERE fs.FUNDING_SOURCE_CODE='WVI';
UPDATE rm_funding_source fs SET fs.FUNDING_SOURCE_TYPE_ID=1, fs.LAST_MODIFIED_DATE=now(), fs.LAST_MODIFIED_BY=9 WHERE fs.FUNDING_SOURCE_CODE='NETHERL';
UPDATE rm_funding_source fs SET fs.FUNDING_SOURCE_TYPE_ID=2, fs.LAST_MODIFIED_DATE=now(), fs.LAST_MODIFIED_BY=9 WHERE fs.FUNDING_SOURCE_CODE='ISDB';
UPDATE rm_funding_source fs SET fs.FUNDING_SOURCE_TYPE_ID=2, fs.LAST_MODIFIED_DATE=now(), fs.LAST_MODIFIED_BY=9 WHERE fs.FUNDING_SOURCE_CODE='WAHO';
UPDATE rm_funding_source fs SET fs.FUNDING_SOURCE_TYPE_ID=2, fs.LAST_MODIFIED_DATE=now(), fs.LAST_MODIFIED_BY=9 WHERE fs.FUNDING_SOURCE_CODE='WFP';
UPDATE rm_funding_source fs SET fs.FUNDING_SOURCE_TYPE_ID=3, fs.LAST_MODIFIED_DATE=now(), fs.LAST_MODIFIED_BY=9 WHERE fs.FUNDING_SOURCE_CODE='FH';
UPDATE rm_funding_source fs SET fs.FUNDING_SOURCE_TYPE_ID=3, fs.LAST_MODIFIED_DATE=now(), fs.LAST_MODIFIED_BY=9 WHERE fs.FUNDING_SOURCE_CODE='LMH';

