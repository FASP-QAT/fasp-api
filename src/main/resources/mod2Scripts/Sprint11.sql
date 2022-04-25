/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  altius
 * Created: 25-Apr-2022
 */

SET FOREIGN_KEY_CHECKS=0;
DELETE FROM ap_static_label_languages WHERE STATIC_LABEL_ID IN (SELECT l.`STATIC_LABEL_ID` FROM ap_static_label l WHERE l.`LABEL_CODE` IN ('static.tooltip.scenario','static.tooltip.NodeTitle',
'static.placeholder.modelingType',
'static.placeholder.usageTemplate'
));

DELETE l.* FROM ap_static_label l WHERE l.`LABEL_CODE` IN ('static.tooltip.scenario','static.tooltip.NodeTitle',
'static.placeholder.modelingType',
'static.placeholder.usageTemplate'
);
SET FOREIGN_KEY_CHECKS=1;