/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  altius
 * Created: 10-Feb-2022
 */

UPDATE `fasp`.`em_email_template` SET `EMAIL_BODY`='Hello,<br><br>\r\nGreetings from QAT!!!<br>\r\nThe following error has occured while importing <%IMPORT_TYPE%> Data.<br>\r\n<b>Error </b>: <%ERROR_SHORT_DESC%><br>\r\n<b>Error Details</b> : <%ERROR_DETAILS%><br><br>\r\n\r\nThis needs urgent attention.<br><br>\r\n\r\nThanks<br>\r\nTeam-QAT\r\n\r\n' WHERE `EMAIL_TEMPLATE_ID`='3'; 
UPDATE `fasp`.`em_email_template` SET `EMAIL_BODY_PARAM`='IMPORT_TYPE,ERROR_SHORT_DESC,ERROR_DETAILS' WHERE `EMAIL_TEMPLATE_ID`='3'; 