/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  altius
 * Created: 12-Jun-2021
 */

insert into ap_label values(null,"Internal users","","","",1,now(),1,now(),20);
select max(l.LABEL_ID) into @max from ap_label l ;

insert into us_role values('ROLE_INTERNAL_USER',@max,1,now(),1,now());

insert into us_role_business_function select null,'ROLE_INTERNAL_USER',u.BUSINESS_FUNCTION_ID,1,now(),1,now() from us_role_business_function u where u.ROLE_ID='ROLE_REALM_ADMIN' and u.BUSINESS_FUNCTION_ID not like '%NOTIFICATION%';

update us_user_role u set u.ROLE_ID='ROLE_INTERNAL_USER',u.LAST_MODIFIED_DATE=now() where u.USER_ID in (select u.USER_ID 
from us_user u 
left join us_user_role ur on u.USER_ID=ur.USER_ID
where (u.EMAIL_ID like '%altius%' or u.EMAIL_ID like '%fhi%') and u.USER_ID!=1 and ur.ROLE_ID='ROLE_REALM_ADMIN');