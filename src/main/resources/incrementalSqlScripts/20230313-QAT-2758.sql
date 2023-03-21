select a.STATIC_LABEL_ID into @MAX from ap_static_label a where a.LABEL_CODE = 'Can Edit All Usage templates';
delete from ap_static_label where ap_static_label.STATIC_LABEL_ID=@MAX;

select a.STATIC_LABEL_ID into @MAX from ap_static_label a where a.LABEL_CODE = 'Can Edit only own Usage templates';
delete from ap_static_label where ap_static_label.STATIC_LABEL_ID=@MAX;

select a.STATIC_LABEL_ID into @MAX from ap_static_label a where a.LABEL_CODE = 'Can Edit All Equivalency Units';
delete from ap_static_label where ap_static_label.STATIC_LABEL_ID=@MAX;

select a.STATIC_LABEL_ID into @MAX from ap_static_label a where a.LABEL_CODE = 'Can Edit only own Equivalency Units';
delete from ap_static_label where ap_static_label.STATIC_LABEL_ID=@MAX;

INSERT INTO fasp.ap_label VALUES ( NULL,'Can Edit All Usage templates',null,null,null,1,now(),1,now(),24);
SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;
update us_business_function set us_business_function.LABEL_ID=@MAX where us_business_function.BUSINESS_FUNCTION_ID='ROLE_BF_EDIT_USAGE_TEMPLATE_ALL';

INSERT INTO fasp.ap_label VALUES ( NULL,'Can Edit only own Usage templates',null,null,null,1,now(),1,now(),24);
SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;
update us_business_function set us_business_function.LABEL_ID=@MAX where us_business_function.BUSINESS_FUNCTION_ID='ROLE_BF_EDIT_USAGE_TEMPLATE_OWN';

INSERT INTO fasp.ap_label VALUES ( NULL,'Can Edit All Equivalency Units',null,null,null,1,now(),1,now(),24);
SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;
update us_business_function set us_business_function.LABEL_ID=@MAX where us_business_function.BUSINESS_FUNCTION_ID='ROLE_BF_EDIT_EQIVALENCY_UNIT_ALL';

INSERT INTO fasp.ap_label VALUES ( NULL,'Can Edit only own Equivalency Units',null,null,null,1,now(),1,now(),24);
SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;
update us_business_function set us_business_function.LABEL_ID=@MAX where us_business_function.BUSINESS_FUNCTION_ID='ROLE_BF_EDIT_EQIVALENCY_UNIT_OWN';
