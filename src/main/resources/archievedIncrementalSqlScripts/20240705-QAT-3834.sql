INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.module','1');

SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Module');-- en

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Module');-- fr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Módulo');-- pr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Módulo');-- sp


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.program.noOfProgramsUsingPU','1');

SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'# Programs using PU');-- en

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'# Programmes utilisant PU');-- fr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'# Programas usando PU');-- pr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'# Programas que usan PU');-- sp


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.program.noOfProgramsUsingFU','1');

SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'# Programs using FU');-- en

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'# Programmes utilisant FU');-- fr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'# Programas usando FU');-- pr

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'# Programas que usan FU');-- sp