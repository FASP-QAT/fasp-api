/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  altius
 * Created: 09-Sep-2021
 */

UPDATE `fasp`.`em_email_template` SET `EMAIL_BODY`='Hello,<br><br>\r\nGreetings from QAT!!!<br>\r\nThe following error has occured while exporting <%IMPORT_TYPE%>.<br>\r\nDate : <%DATETIME%><br>\r\nError : <%ERROR_SHORT_DESC%><br>\r\nError Details : <%ERROR_DETAILS%><br><br>\r\n\r\nThis needs urgent attention.<br><br>\r\n\r\nThanks<br>\r\nTeam-QAT\r\n' WHERE `EMAIL_TEMPLATE_ID`='4'; 



UPDATE `fasp`.`em_email_template` SET `EMAIL_BODY`='Hello,<br><br>\r\nGreetings from QAT!!!<br>\r\nThe following error has occured while importing <%IMPORT_TYPE%> Data.<br>\r\n<b>Date</b> : <%DATETIME%><br>\r\n<b>Error </b>: <%ERROR_SHORT_DESC%><br>\r\n<b>Error Details</b> : <%ERROR_DETAILS%><br><br>\r\n\r\nThis needs urgent attention.<br><br>\r\n\r\nThanks<br>\r\nTeam-QAT\r\n\r\n' WHERE `EMAIL_TEMPLATE_ID`='3'; 