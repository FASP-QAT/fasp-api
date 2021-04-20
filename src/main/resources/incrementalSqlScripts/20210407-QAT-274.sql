USE `fasp`;
DROP procedure IF EXISTS `getSupplyPlanNotificationToList`;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `getSupplyPlanNotificationToList`( 
    VAR_PROGRAM_ID INT(10), 
    VAR_VERSION_ID INT(10), 
    VAR_STATUS_TYPE INT(10)
)
BEGIN

SET @programId = VAR_PROGRAM_ID;
SET @versionId = VAR_VERSION_ID;
SET @statusType = VAR_STATUS_TYPE;
-- statusTypeId = 1 -> Final Submitted
-- statusTypeId = 2 -> Final Approved
-- statusTypeID = 3 -> Final Rejected

IF @statusType=1 THEN
    -- Final submitted
    -- toList
    SELECT u.USER_ID, u.USERNAME, u.EMAIL_ID
    FROM rm_program p 
    LEFT JOIN us_user_acl acl ON 
        (acl.REALM_COUNTRY_ID is null OR acl.REALM_COUNTRY_ID=p.REALM_COUNTRY_ID)
        AND (acl.PROGRAM_ID is null OR acl.PROGRAM_ID=p.PROGRAM_ID)
        AND (acl.HEALTH_AREA_ID is null OR acl.HEALTH_AREA_ID=p.HEALTH_AREA_ID)
        AND (acl.ORGANISATION_ID is null OR acl.ORGANISATION_ID=p.ORGANISATION_ID)
    LEFT JOIN us_user u ON acl.USER_ID=u.USER_ID
    LEFT JOIN us_user_role ur ON u.USER_ID=ur.USER_ID 
    LEFT JOIN us_role_business_function rbf ON ur.ROLE_ID=rbf.ROLE_ID
    WHERE u.REALM_ID=1 AND rbf.BUSINESS_FUNCTION_ID='ROLE_BF_NOTIFICATION_TO_COMMIT' AND p.PROGRAM_ID=@programId AND u.ACTIVE
    GROUP BY u.USER_ID;

ELSEIF @statusType=2 OR @statusType=3 THEN
    -- Approved or Rejected
    -- toList
    SELECT u.USER_ID, u.USERNAME, u.EMAIL_ID 
    FROM rm_program_version pv 
    LEFT JOIN us_user u ON pv.CREATED_BY=u.USER_ID 
    WHERE pv.PROGRAM_ID=@programId AND pv.VERSION_ID=@versionId;
END IF; 
    
END$$

DELIMITER ;



USE `fasp`;
DROP procedure IF EXISTS `getSupplyPlanNotificationCcList`;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `getSupplyPlanNotificationCcList`( 
    VAR_PROGRAM_ID INT(10), 
    VAR_VERSION_ID INT(10), 
    VAR_STATUS_TYPE INT(10)
)
BEGIN

SET @programId = VAR_PROGRAM_ID;
SET @versionId = VAR_VERSION_ID;
SET @statusType = VAR_STATUS_TYPE;
-- statusTypeId = 1 -> Final Submitted
-- statusTypeId = 2 -> Final Approved
-- statusTypeID = 3 -> Final Rejected

IF @statusType=1 THEN
    -- Final submitted
    -- ccList
    SELECT u2.USER_ID, u2.USERNAME, u2.EMAIL_ID 
    FROM (
        SELECT u.USER_ID, u.USERNAME, u.EMAIL_ID
        FROM rm_program p 
        LEFT JOIN us_user_acl acl ON 
                (acl.REALM_COUNTRY_ID is null OR acl.REALM_COUNTRY_ID=p.REALM_COUNTRY_ID)
                AND (acl.PROGRAM_ID is null OR acl.PROGRAM_ID=p.PROGRAM_ID)
                AND (acl.HEALTH_AREA_ID is null OR acl.HEALTH_AREA_ID=p.HEALTH_AREA_ID)
                AND (acl.ORGANISATION_ID is null OR acl.ORGANISATION_ID=p.ORGANISATION_ID)
        LEFT JOIN us_user u ON acl.USER_ID=u.USER_ID
        LEFT JOIN us_user_role ur ON u.USER_ID=ur.USER_ID 
        LEFT JOIN us_role_business_function rbf ON ur.ROLE_ID=rbf.ROLE_ID
        WHERE u.REALM_ID=1 AND rbf.BUSINESS_FUNCTION_ID='ROLE_BF_NOTIFICATION_CC_COMMIT' AND p.PROGRAM_ID=@programId AND u.ACTIVE

        UNION

        SELECT u.USER_ID, u.USERNAME, u.EMAIL_ID 
        FROM rm_program_version pv 
        LEFT JOIN us_user u ON pv.CREATED_BY=u.USER_ID 
        WHERE pv.PROGRAM_ID=@programId AND pv.VERSION_ID=@versionId
    ) u2 
    LEFT JOIN (
        SELECT u.USER_ID, u.USERNAME, u.EMAIL_ID
        FROM rm_program p 
        LEFT JOIN us_user_acl acl ON 
            (acl.REALM_COUNTRY_ID is null OR acl.REALM_COUNTRY_ID=p.REALM_COUNTRY_ID)
            AND (acl.PROGRAM_ID is null OR acl.PROGRAM_ID=p.PROGRAM_ID)
            AND (acl.HEALTH_AREA_ID is null OR acl.HEALTH_AREA_ID=p.HEALTH_AREA_ID)
            AND (acl.ORGANISATION_ID is null OR acl.ORGANISATION_ID=p.ORGANISATION_ID)
        LEFT JOIN us_user u ON acl.USER_ID=u.USER_ID
        LEFT JOIN us_user_role ur ON u.USER_ID=ur.USER_ID 
        LEFT JOIN us_role_business_function rbf ON ur.ROLE_ID=rbf.ROLE_ID
        WHERE u.REALM_ID=1 AND rbf.BUSINESS_FUNCTION_ID='ROLE_BF_NOTIFICATION_TO_COMMIT' AND p.PROGRAM_ID=@programId AND u.ACTIVE
        GROUP BY u.USER_ID
        ) toList ON u2.USER_ID=toList.USER_ID
    WHERE toList.USER_ID IS NULL
    GROUP BY u2.USER_ID;

ELSEIF @statusType=3 THEN
    -- Rejected
    -- ccList
    SELECT u.USER_ID, u.USERNAME, u.EMAIL_ID
    FROM rm_program p 
    LEFT JOIN us_user_acl acl ON 
        (acl.REALM_COUNTRY_ID is null OR acl.REALM_COUNTRY_ID=p.REALM_COUNTRY_ID)
        AND (acl.PROGRAM_ID is null OR acl.PROGRAM_ID=p.PROGRAM_ID)
        AND (acl.HEALTH_AREA_ID is null OR acl.HEALTH_AREA_ID=p.HEALTH_AREA_ID)
        AND (acl.ORGANISATION_ID is null OR acl.ORGANISATION_ID=p.ORGANISATION_ID)
    LEFT JOIN us_user u ON acl.USER_ID=u.USER_ID
    LEFT JOIN us_user_role ur ON u.USER_ID=ur.USER_ID 
    LEFT JOIN us_role_business_function rbf ON ur.ROLE_ID=rbf.ROLE_ID
    WHERE u.REALM_ID=1 AND (rbf.BUSINESS_FUNCTION_ID='ROLE_BF_NOTIFICATION_CC_REJECT') AND p.PROGRAM_ID=@programId AND u.ACTIVE
    GROUP BY u.USER_ID;

ELSEIF @statusType=2 THEN
    -- Approved
    -- ccList
    SELECT u.USER_ID, u.USERNAME, u.EMAIL_ID
    FROM rm_program p 
    LEFT JOIN us_user_acl acl ON 
        (acl.REALM_COUNTRY_ID is null OR acl.REALM_COUNTRY_ID=p.REALM_COUNTRY_ID)
        AND (acl.PROGRAM_ID is null OR acl.PROGRAM_ID=p.PROGRAM_ID)
        AND (acl.HEALTH_AREA_ID is null OR acl.HEALTH_AREA_ID=p.HEALTH_AREA_ID)
        AND (acl.ORGANISATION_ID is null OR acl.ORGANISATION_ID=p.ORGANISATION_ID)
    LEFT JOIN us_user u ON acl.USER_ID=u.USER_ID
    LEFT JOIN us_user_role ur ON u.USER_ID=ur.USER_ID 
    LEFT JOIN us_role_business_function rbf ON ur.ROLE_ID=rbf.ROLE_ID
    WHERE u.REALM_ID=1 AND (rbf.BUSINESS_FUNCTION_ID='ROLE_BF_NOTIFICATION_CC_APPROVE') AND p.PROGRAM_ID=@programId AND u.ACTIVE
    GROUP BY u.USER_ID;
END IF; 
    
END$$

DELIMITER ;

-- emial template queries

UPDATE `em_email_template` SET `SUBJECT`=' [QAT] <%PROGRAM%> Supply Plan is Approved. ', `SUBJECT_PARAM`='PROGRAM', 
`EMAIL_BODY`='<!doctype html>\n<html>\n  <head>\n    <meta name=\"viewport\" content=\"width=device-width\">\n    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n    <title>Default Template</title>\n    <style>\n    @media only screen and (max-width: 620px) {\n      table[class=body] h1 {\n        font-size: 28px !important;\n        margin-bottom: 10px !important;\n      }\n      table[class=body] p,\n            table[class=body] ul,\n            table[class=body] ol,\n            table[class=body] td,\n            table[class=body] span,\n            table[class=body] a {\n        font-size: 16px !important;\n      }\n      table[class=body] .wrapper,\n            table[class=body] .article {\n        padding: 10px !important;\n      }\n      table[class=body] .content {\n        padding: 0 !important;\n      }\n      table[class=body] .container {\n        padding: 0 !important;\n        width: 100% !important;\n      }\n      table[class=body] .main {\n        border-left-width: 0 !important;\n        border-radius: 0 !important;\n        border-right-width: 0 !important;\n      }\n      table[class=body] .btn table {\n        width: 100% !important;\n      }\n      table[class=body] .btn a {\n        width: 100% !important;\n      }\n      table[class=body] .img-responsive {\n        height: auto !important;\n        max-width: 100% !important;\n        width: auto !important;\n      }\n    }\n\n    /* -------------------------------------\n        PRESERVE THESE STYLES IN THE HEAD\n    ------------------------------------- */\n    @media all {\n      .ExternalClass {\n        width: 100%;\n      }\n      .ExternalClass,\n            .ExternalClass p,\n            .ExternalClass span,\n            .ExternalClass font,\n            .ExternalClass td,\n            .ExternalClass div {\n        line-height: 100%;\n      }\n      .apple-link a {\n        color: inherit !important;\n        font-family: inherit !important;\n        font-size: inherit !important;\n        font-weight: inherit !important;\n        line-height: inherit !important;\n        text-decoration: none !important;\n      }\n      #MessageViewBody a {\n        color: inherit;\n        text-decoration: none;\n        font-size: inherit;\n        font-family: inherit;\n        font-weight: inherit;\n        line-height: inherit;\n      }\n      .btn-primary table td:hover {\n        background-color: #34495e !important;\n      }\n      .btn-primary a:hover {\n        background-color: #34495e !important;\n        border-color: #34495e !important;\n      }\n    }\n    </style>\n  </head>\n  <body class=\"\" style=\"background-color: #f6f6f6; font-family: \'Roboto Slab\', \'Roboto\', \'Arial\', sans-serif; -webkit-font-smoothing: antialiased; font-size: 14px; line-height: 1.4; margin: 0; padding: 0; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;color: #222;\">\n    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"body\" style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%; background-color: #f6f6f6;\">\n      <tr>\n        <td style=\"font-family: \'Roboto Slab\', \'Roboto\', \'Arial\', sans-serif; font-size: 14px; vertical-align: top;\">&nbsp;</td>\n        <td class=\"container\" style=\"font-family: \'Roboto Slab\', \'Roboto\', \'Arial\', sans-serif; font-size: 14px; vertical-align: top; display: block; Margin: 0 auto; max-width: 580px; padding: 10px; width: 580px;\">\n          <div class=\"content\" style=\"box-sizing: border-box; display: block; Margin: 0 auto; max-width: 580px; padding: 10px;\">\n\n            <!-- START CENTERED WHITE CONTAINER -->\n            <span class=\"preheader\" style=\"color: transparent; display: none; height: 0; max-height: 0; max-width: 0; opacity: 0; overflow: hidden; mso-hide: all; visibility: hidden; width: 0;\"><!-- This is preheader text. Some clients will show this text as a preview. --></span>\n            <table class=\"main\" style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%; background: #ffffff; border-radius: 3px;margin-top: 10px;\">\n\n              <!-- START MAIN CONTENT AREA -->\n              <tr>\n                <td class=\"wrapper\" style=\"font-family: \'Roboto Slab\', \'Roboto\', \'Arial\', sans-serif; font-size: 14px; vertical-align: top; box-sizing: border-box; padding: 20px 40px;\">\n                  <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%;\">\n                    <tr>\n                      <td align=\"center\" style=\"text-align: center;background-color: #bb3430\">\n                        <div class=\"pageicon\" style=\"overflow: hidden;padding-top:30px;\">\n                        </div>\n                        <h2 style=\"font-weight: 400;color: #fff;padding-bottom: 27px;border-bottom: 0px solid #c7c7c7;margin: 0px;\">\nSupply Plan Approved Notification\n                        </h2>\n                      </td>\n                    </tr>\n                    <tr>\n                      <td style=\"font-family: \'Roboto Slab\', \'Roboto\', \'Arial\', sans-serif; font-size: 14px; vertical-align: top;padding: 50px 20px 30px 20px;\">\n                        <p style=\"font-family: \'Roboto Slab\', \'Roboto\', \'Arial\', sans-serif; font-size: 18px; font-weight: 700; margin: 0; margin-bottom: 15px;\">Hello,</p>\n                        <p style=\"font-family: \'Roboto Slab\', \'Roboto\', \'Arial\', sans-serif; font-size: 15px; font-weight: normal; margin: 0; margin-bottom: 5px;\"></p>\n<p style=\"font-family: \'Roboto Slab\', \'Roboto\', \'Arial\', sans-serif; font-size: 15px; font-weight: normal; margin: 0; margin-bottom: 15px;\">\nThe supply plan for program <%PROGRAM%> version <%VERSION%> has been Approved. \n</p>\n<p style=\"font-family: \'Roboto Slab\', \'Roboto\', \'Arial\', sans-serif; font-size: 15px; font-weight: normal; margin: 0; margin-bottom: 15px;\">\n<b>Version comments: <%VERSION_COMMENTS%></b>\n</p>\n<p style=\"font-family: \'Roboto Slab\', \'Roboto\', \'Arial\', sans-serif; font-size: 15px; font-weight: normal; margin: 0; margin-bottom: 15px;\">\nPlease log in to <a href=\"https://www.quantificationanalytics.org/#/login\">QAT</a> to review details.\n</p>\n<p style=\"font-family: \'Roboto Slab\', \'Roboto\', \'Arial\', sans-serif; font-size: 14px; font-weight: normal; margin: 0; margin-bottom: 15px;padding-bottom: 20px;\">\n                            Thanks,<br>\n			    Quantification Analytics Tool<br>  \n			    (<b><i>I am a robot. Please do not reply to this email</i></b>) 		\n                        </p>\n                      </td>\n                    </tr>\n                  </table>\n                </td>\n              </tr>\n\n            <!-- END MAIN CONTENT AREA -->\n            </table>\n          <!-- END CENTERED WHITE CONTAINER -->\n          </div>\n        </td>\n        <td style=\"font-family: \'Roboto Slab\', \'Roboto\', \'Arial\', sans-serif; font-size: 14px; vertical-align: top;\">&nbsp;</td>\n      </tr>\n    </table>\n  </body>\n</html>' WHERE `EMAIL_TEMPLATE_ID`='5';
UPDATE `em_email_template` SET `EMAIL_BODY_PARAM`='PROGRAM,VERSION,VERSION_COMMENTS' WHERE `EMAIL_TEMPLATE_ID`='5';

UPDATE `em_email_template` SET `SUBJECT`=' [QAT] <%PROGRAM%> Supply Plan Ready for Review. ', `SUBJECT_PARAM`='PROGRAM', 
`EMAIL_BODY`='<!doctype html>\n<html>\n  <head>\n    <meta name=\"viewport\" content=\"width=device-width\">\n    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n    <title>Default Template</title>\n    <style>\n    @media only screen and (max-width: 620px) {\n      table[class=body] h1 {\n        font-size: 28px !important;\n        margin-bottom: 10px !important;\n      }\n      table[class=body] p,\n            table[class=body] ul,\n            table[class=body] ol,\n            table[class=body] td,\n            table[class=body] span,\n            table[class=body] a {\n        font-size: 16px !important;\n      }\n      table[class=body] .wrapper,\n            table[class=body] .article {\n        padding: 10px !important;\n      }\n      table[class=body] .content {\n        padding: 0 !important;\n      }\n      table[class=body] .container {\n        padding: 0 !important;\n        width: 100% !important;\n      }\n      table[class=body] .main {\n        border-left-width: 0 !important;\n        border-radius: 0 !important;\n        border-right-width: 0 !important;\n      }\n      table[class=body] .btn table {\n        width: 100% !important;\n      }\n      table[class=body] .btn a {\n        width: 100% !important;\n      }\n      table[class=body] .img-responsive {\n        height: auto !important;\n        max-width: 100% !important;\n        width: auto !important;\n      }\n    }\n\n    /* -------------------------------------\n        PRESERVE THESE STYLES IN THE HEAD\n    ------------------------------------- */\n    @media all {\n      .ExternalClass {\n        width: 100%;\n      }\n      .ExternalClass,\n            .ExternalClass p,\n            .ExternalClass span,\n            .ExternalClass font,\n            .ExternalClass td,\n            .ExternalClass div {\n        line-height: 100%;\n      }\n      .apple-link a {\n        color: inherit !important;\n        font-family: inherit !important;\n        font-size: inherit !important;\n        font-weight: inherit !important;\n        line-height: inherit !important;\n        text-decoration: none !important;\n      }\n      #MessageViewBody a {\n        color: inherit;\n        text-decoration: none;\n        font-size: inherit;\n        font-family: inherit;\n        font-weight: inherit;\n        line-height: inherit;\n      }\n      .btn-primary table td:hover {\n        background-color: #34495e !important;\n      }\n      .btn-primary a:hover {\n        background-color: #34495e !important;\n        border-color: #34495e !important;\n      }\n    }\n    </style>\n  </head>\n  <body class=\"\" style=\"background-color: #f6f6f6; font-family: \'\'Roboto Slab\'\', \'\'Roboto\'\', \'\'Arial\'\', sans-serif; -webkit-font-smoothing: antialiased; font-size: 14px; line-height: 1.4; margin: 0; padding: 0; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;color: #222;\">\n    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"body\" style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%; background-color: #f6f6f6;\">\n      <tr>\n        <td style=\"font-family: \'\'Roboto Slab\'\', \'\'Roboto\'\', \'\'Arial\'\', sans-serif; font-size: 14px; vertical-align: top;\">&nbsp;</td>\n        <td class=\"container\" style=\"font-family: \'\'Roboto Slab\'\', \'\'Roboto\'\', \'\'Arial\'\', sans-serif; font-size: 14px; vertical-align: top; display: block; Margin: 0 auto; max-width: 580px; padding: 10px; width: 580px;\">\n          <div class=\"content\" style=\"box-sizing: border-box; display: block; Margin: 0 auto; max-width: 580px; padding: 10px;\">\n\n            <!-- START CENTERED WHITE CONTAINER -->\n            <span class=\"preheader\" style=\"color: transparent; display: none; height: 0; max-height: 0; max-width: 0; opacity: 0; overflow: hidden; mso-hide: all; visibility: hidden; width: 0;\"><!-- This is preheader text. Some clients will show this text as a preview. --></span>\n            <table class=\"main\" style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%; background: #ffffff; border-radius: 3px;margin-top: 10px;\">\n\n              <!-- START MAIN CONTENT AREA -->\n              <tr>\n                <td class=\"wrapper\" style=\"font-family: \'\'Roboto Slab\'\', \'\'Roboto\'\', \'\'Arial\'\', sans-serif; font-size: 14px; vertical-align: top; box-sizing: border-box; padding: 20px 40px;\">\n                  <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%;\">\n                                        <tr>\n                      <td align=\"center\" style=\"text-align: center;background-color: #bb3430\">\n                        <div class=\"pageicon\" style=\"overflow: hidden;padding-top:30px;\">\n                        </div>\n                        <h2 style=\"font-weight: 400;color: #fff;padding-bottom: 27px;border-bottom: 0px solid #c7c7c7;margin: 0px;\">\nProgram Commited Notification.\n                        </h2>\n                      </td>\n                    </tr>\n                    <tr>\n                      <td style=\"font-family: \'\'Roboto Slab\'\', \'\'Roboto\'\', \'\'Arial\'\', sans-serif; font-size: 14px; vertical-align: top;padding: 50px 20px 30px 20px;\">\n                        <p style=\"font-family: \'\'Roboto Slab\'\', \'\'Roboto\'\', \'\'Arial\'\', sans-serif; font-size: 18px; font-weight: 700; margin: 0; margin-bottom: 15px;\">Hello,</p>\n                        <p style=\"font-family: \'\'Roboto Slab\'\', \'\'Roboto\'\', \'\'Arial\'\', sans-serif; font-size: 15px; font-weight: normal; margin: 0; margin-bottom: 5px;\"></p>\n<p style=\"font-family: \'\'Roboto Slab\'\', \'\'Roboto\'\', \'\'Arial\'\', sans-serif; font-size: 15px; font-weight: normal; margin: 0; margin-bottom: 15px;\">\nProgram <%PROGRAM%> version <%VERSION%> has been committed as a final version in QAT and is available to be reviewed.  \n</p>\n<p style=\"font-family: \'\'Roboto Slab\'\', \'\'Roboto\'\', \'\'Arial\'\', sans-serif; font-size: 15px; font-weight: normal; margin: 0; margin-bottom: 15px;\">\n<b>Version comments: <%VERSION_COMMENTS%></b>\n</p>\n<p style=\"font-family: \'\'Roboto Slab\'\', \'\'Roboto\'\', \'\'Arial\'\', sans-serif; font-size: 15px; font-weight: normal; margin: 0; margin-bottom: 15px;\">\nPlease log in to <a href=\"https://www.quantificationanalytics.org/#/login\">QAT</a> to review details.\n</p>\n<p style=\"font-family: \'\'Roboto Slab\'\', \'\'Roboto\'\', \'\'Arial\'\', sans-serif; font-size: 14px; font-weight: normal; margin: 0; margin-bottom: 15px;padding-bottom: 20px;\">\n                            Thanks,<br>\n                            Quantification Analytics Tool<br>  \n			    (<b><i>I am a robot. Please do not reply to this email</i></b>)\n                        </p>\n                      </td>\n                    </tr>\n                  </table>\n                </td>\n              </tr>\n\n            <!-- END MAIN CONTENT AREA -->\n            </table>\n          <!-- END CENTERED WHITE CONTAINER -->\n          </div>\n        </td>\n        <td style=\"font-family: \'\'Roboto Slab\'\', \'\'Roboto\'\', \'\'Arial\'\', sans-serif; font-size: 14px; vertical-align: top;\">&nbsp;</td>\n      </tr>\n    </table>\n  </body>\n</html>' WHERE `EMAIL_TEMPLATE_ID`='6';
UPDATE `em_email_template` SET `EMAIL_BODY_PARAM`='PROGRAM,VERSION,VERSION_COMMENTS' WHERE `EMAIL_TEMPLATE_ID`='6';

INSERT INTO `em_email_template` (`EMAIL_TEMPLATE_ID`, `EMAIL_DESC`, `SUBJECT`, `SUBJECT_PARAM`, `EMAIL_BODY`, `EMAIL_BODY_PARAM`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`, `ACTIVE`) VALUES (null, 'Supply plan version and review process', '[QAT] <%PROGRAM%> Supply Plan is Rejected ', 'PROGRAM', '<!doctype html>\n<html>\n  <head>\n    <meta name=\"viewport\" content=\"width=device-width\">\n    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n    <title>Default Template</title>\n    <style>\n    @media only screen and (max-width: 620px) {\n      table[class=body] h1 {\n        font-size: 28px !important;\n        margin-bottom: 10px !important;\n      }\n      table[class=body] p,\n            table[class=body] ul,\n            table[class=body] ol,\n            table[class=body] td,\n            table[class=body] span,\n            table[class=body] a {\n        font-size: 16px !important;\n      }\n      table[class=body] .wrapper,\n            table[class=body] .article {\n        padding: 10px !important;\n      }\n      table[class=body] .content {\n        padding: 0 !important;\n      }\n      table[class=body] .container {\n        padding: 0 !important;\n        width: 100% !important;\n      }\n      table[class=body] .main {\n        border-left-width: 0 !important;\n        border-radius: 0 !important;\n        border-right-width: 0 !important;\n      }\n      table[class=body] .btn table {\n        width: 100% !important;\n      }\n      table[class=body] .btn a {\n        width: 100% !important;\n      }\n      table[class=body] .img-responsive {\n        height: auto !important;\n        max-width: 100% !important;\n        width: auto !important;\n      }\n    }\n\n    /* -------------------------------------\n        PRESERVE THESE STYLES IN THE HEAD\n    ------------------------------------- */\n    @media all {\n      .ExternalClass {\n        width: 100%;\n      }\n      .ExternalClass,\n            .ExternalClass p,\n            .ExternalClass span,\n            .ExternalClass font,\n            .ExternalClass td,\n            .ExternalClass div {\n        line-height: 100%;\n      }\n      .apple-link a {\n        color: inherit !important;\n        font-family: inherit !important;\n        font-size: inherit !important;\n        font-weight: inherit !important;\n        line-height: inherit !important;\n        text-decoration: none !important;\n      }\n      #MessageViewBody a {\n        color: inherit;\n        text-decoration: none;\n        font-size: inherit;\n        font-family: inherit;\n        font-weight: inherit;\n        line-height: inherit;\n      }\n      .btn-primary table td:hover {\n        background-color: #34495e !important;\n      }\n      .btn-primary a:hover {\n        background-color: #34495e !important;\n        border-color: #34495e !important;\n      }\n    }\n    </style>\n  </head>\n  <body class=\"\" style=\"background-color: #f6f6f6; font-family: \'\'Roboto Slab\'\', \'\'Roboto\'\', \'\'Arial\'\', sans-serif; -webkit-font-smoothing: antialiased; font-size: 14px; line-height: 1.4; margin: 0; padding: 0; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;color: #222;\">\n    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"body\" style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%; background-color: #f6f6f6;\">\n      <tr>\n        <td style=\"font-family: \'\'Roboto Slab\'\', \'\'Roboto\'\', \'\'Arial\'\', sans-serif; font-size: 14px; vertical-align: top;\">&nbsp;</td>\n        <td class=\"container\" style=\"font-family: \'\'Roboto Slab\'\', \'\'Roboto\'\', \'\'Arial\'\', sans-serif; font-size: 14px; vertical-align: top; display: block; Margin: 0 auto; max-width: 580px; padding: 10px; width: 580px;\">\n          <div class=\"content\" style=\"box-sizing: border-box; display: block; Margin: 0 auto; max-width: 580px; padding: 10px;\">\n\n            <!-- START CENTERED WHITE CONTAINER -->\n            <span class=\"preheader\" style=\"color: transparent; display: none; height: 0; max-height: 0; max-width: 0; opacity: 0; overflow: hidden; mso-hide: all; visibility: hidden; width: 0;\"><!-- This is preheader text. Some clients will show this text as a preview. --></span>\n            <table class=\"main\" style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%; background: #ffffff; border-radius: 3px;margin-top: 10px;\">\n\n              <!-- START MAIN CONTENT AREA -->\n              <tr>\n                <td class=\"wrapper\" style=\"font-family: \'\'Roboto Slab\'\', \'\'Roboto\'\', \'\'Arial\'\', sans-serif; font-size: 14px; vertical-align: top; box-sizing: border-box; padding: 20px 40px;\">\n                  <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%;\">\n                    <tr>\n                      <td align=\"center\" style=\"text-align: center;background-color: #bb3430\">\n                        <div class=\"pageicon\" style=\"overflow: hidden;padding-top:30px;\">\n                        </div>\n                        <h2 style=\"font-weight: 400;color: #fff;padding-bottom: 27px;border-bottom: 0px solid #c7c7c7;margin: 0px;\">\nSupply Plan Rejected Notification\n                        </h2>\n                      </td>\n                    </tr>\n                    <tr>\n                      <td style=\"font-family: \'\'Roboto Slab\'\', \'\'Roboto\'\', \'\'Arial\'\', sans-serif; font-size: 14px; vertical-align: top;padding: 50px 20px 30px 20px;\">\n                        <p style=\"font-family: \'\'Roboto Slab\'\', \'\'Roboto\'\', \'\'Arial\'\', sans-serif; font-size: 18px; font-weight: 700; margin: 0; margin-bottom: 15px;\">Hello,</p>\n                        <p style=\"font-family: \'\'Roboto Slab\'\', \'\'Roboto\'\', \'\'Arial\'\', sans-serif; font-size: 15px; font-weight: normal; margin: 0; margin-bottom: 5px;\"></p>\n<p style=\"font-family: \'\'Roboto Slab\'\', \'\'Roboto\'\', \'\'Arial\'\', sans-serif; font-size: 15px; font-weight: normal; margin: 0; margin-bottom: 15px;\">\nThe supply plan for program <%PROGRAM%> version <%VERSION%> has been Rejected. Please update the supply plan accordingly and re-commit.   \n</p>\n<p style=\"font-family: \'\'Roboto Slab\'\', \'\'Roboto\'\', \'\'Arial\'\', sans-serif; font-size: 15px; font-weight: normal; margin: 0; margin-bottom: 15px;\">\n<b>Version comments: <%VERSION_COMMENTS%></b>\n</p>\n<p style=\"font-family: \'\'Roboto Slab\'\', \'\'Roboto\'\', \'\'Arial\'\', sans-serif; font-size: 15px; font-weight: normal; margin: 0; margin-bottom: 15px;\">\nPlease log in to <a href=\"https://www.quantificationanalytics.org/#/login\">QAT</a> to review details.\n</p>\n<p style=\"font-family: \'\'Roboto Slab\'\', \'\'Roboto\'\', \'\'Arial\'\', sans-serif; font-size: 14px; font-weight: normal; margin: 0; margin-bottom: 15px;padding-bottom: 20px;\">\n                            Thanks,<br>\n			    Quantification Analytics Tool  \n			    (<b><i>I am a robot. Please do not reply to this email</i></b>) 		\n                        </p>\n                      </td>\n                    </tr>\n                  </table>\n                </td>\n              </tr>\n\n            <!-- END MAIN CONTENT AREA -->\n            </table>\n          <!-- END CENTERED WHITE CONTAINER -->\n          </div>\n        </td>\n        <td style=\"font-family: \'\'Roboto Slab\'\', \'\'Roboto\'\', \'\'Arial\'\', sans-serif; font-size: 14px; vertical-align: top;\">&nbsp;</td>\n      </tr>\n    </table>\n  </body>\n</html>', 'PROGRAM,VERSION,STATUS', '1', '2020-12-02 12:07:55', '1', '2020-12-02 12:07:55', '1');
SELECT MAX(et.EMAIL_TEMPLATE_ID) INTO @maxTempletId FROM em_email_template et;
UPDATE `em_email_template` SET `EMAIL_BODY_PARAM`='PROGRAM,VERSION,VERSION_COMMENTS' WHERE `EMAIL_TEMPLATE_ID`=@maxTempletId;
UPDATE `em_email_template` SET `EMAIL_BODY`='<!doctype html>\n<html>\n  <head>\n    <meta name=\"viewport\" content=\"width=device-width\">\n    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n    <title>Default Template</title>\n    <style>\n    @media only screen and (max-width: 620px) {\n      table[class=body] h1 {\n        font-size: 28px !important;\n        margin-bottom: 10px !important;\n      }\n      table[class=body] p,\n            table[class=body] ul,\n            table[class=body] ol,\n            table[class=body] td,\n            table[class=body] span,\n            table[class=body] a {\n        font-size: 16px !important;\n      }\n      table[class=body] .wrapper,\n            table[class=body] .article {\n        padding: 10px !important;\n      }\n      table[class=body] .content {\n        padding: 0 !important;\n      }\n      table[class=body] .container {\n        padding: 0 !important;\n        width: 100% !important;\n      }\n      table[class=body] .main {\n        border-left-width: 0 !important;\n        border-radius: 0 !important;\n        border-right-width: 0 !important;\n      }\n      table[class=body] .btn table {\n        width: 100% !important;\n      }\n      table[class=body] .btn a {\n        width: 100% !important;\n      }\n      table[class=body] .img-responsive {\n        height: auto !important;\n        max-width: 100% !important;\n        width: auto !important;\n      }\n    }\n\n    /* -------------------------------------\n        PRESERVE THESE STYLES IN THE HEAD\n    ------------------------------------- */\n    @media all {\n      .ExternalClass {\n        width: 100%;\n      }\n      .ExternalClass,\n            .ExternalClass p,\n            .ExternalClass span,\n            .ExternalClass font,\n            .ExternalClass td,\n            .ExternalClass div {\n        line-height: 100%;\n      }\n      .apple-link a {\n        color: inherit !important;\n        font-family: inherit !important;\n        font-size: inherit !important;\n        font-weight: inherit !important;\n        line-height: inherit !important;\n        text-decoration: none !important;\n      }\n      #MessageViewBody a {\n        color: inherit;\n        text-decoration: none;\n        font-size: inherit;\n        font-family: inherit;\n        font-weight: inherit;\n        line-height: inherit;\n      }\n      .btn-primary table td:hover {\n        background-color: #34495e !important;\n      }\n      .btn-primary a:hover {\n        background-color: #34495e !important;\n        border-color: #34495e !important;\n      }\n    }\n    </style>\n  </head>\n  <body class=\"\" style=\"background-color: #f6f6f6; font-family: \'\'Roboto Slab\'\', \'\'Roboto\'\', \'\'Arial\'\', sans-serif; -webkit-font-smoothing: antialiased; font-size: 14px; line-height: 1.4; margin: 0; padding: 0; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;color: #222;\">\n    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"body\" style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%; background-color: #f6f6f6;\">\n      <tr>\n        <td style=\"font-family: \'\'Roboto Slab\'\', \'\'Roboto\'\', \'\'Arial\'\', sans-serif; font-size: 14px; vertical-align: top;\">&nbsp;</td>\n        <td class=\"container\" style=\"font-family: \'\'Roboto Slab\'\', \'\'Roboto\'\', \'\'Arial\'\', sans-serif; font-size: 14px; vertical-align: top; display: block; Margin: 0 auto; max-width: 580px; padding: 10px; width: 580px;\">\n          <div class=\"content\" style=\"box-sizing: border-box; display: block; Margin: 0 auto; max-width: 580px; padding: 10px;\">\n\n            <!-- START CENTERED WHITE CONTAINER -->\n            <span class=\"preheader\" style=\"color: transparent; display: none; height: 0; max-height: 0; max-width: 0; opacity: 0; overflow: hidden; mso-hide: all; visibility: hidden; width: 0;\"><!-- This is preheader text. Some clients will show this text as a preview. --></span>\n            <table class=\"main\" style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%; background: #ffffff; border-radius: 3px;margin-top: 10px;\">\n\n              <!-- START MAIN CONTENT AREA -->\n              <tr>\n                <td class=\"wrapper\" style=\"font-family: \'\'Roboto Slab\'\', \'\'Roboto\'\', \'\'Arial\'\', sans-serif; font-size: 14px; vertical-align: top; box-sizing: border-box; padding: 20px 40px;\">\n                  <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%;\">\n                    <tr>\n                      <td align=\"center\" style=\"text-align: center;background-color: #bb3430\">\n                        <div class=\"pageicon\" style=\"overflow: hidden;padding-top:30px;\">\n                        </div>\n                        <h2 style=\"font-weight: 400;color: #fff;padding-bottom: 27px;border-bottom: 0px solid #c7c7c7;margin: 0px;\">\nSupply Plan Rejected Notification\n                        </h2>\n                      </td>\n                    </tr>\n                    <tr>\n                      <td style=\"font-family: \'\'Roboto Slab\'\', \'\'Roboto\'\', \'\'Arial\'\', sans-serif; font-size: 14px; vertical-align: top;padding: 50px 20px 30px 20px;\">\n                        <p style=\"font-family: \'\'Roboto Slab\'\', \'\'Roboto\'\', \'\'Arial\'\', sans-serif; font-size: 18px; font-weight: 700; margin: 0; margin-bottom: 15px;\">Hello,</p>\n                        <p style=\"font-family: \'\'Roboto Slab\'\', \'\'Roboto\'\', \'\'Arial\'\', sans-serif; font-size: 15px; font-weight: normal; margin: 0; margin-bottom: 5px;\"></p>\n<p style=\"font-family: \'\'Roboto Slab\'\', \'\'Roboto\'\', \'\'Arial\'\', sans-serif; font-size: 15px; font-weight: normal; margin: 0; margin-bottom: 15px;\">\nThe supply plan for program <%PROGRAM%> version <%VERSION%> has been Rejected. Please update the supply plan accordingly and re-commit.   \n</p>\n<p style=\"font-family: \'\'Roboto Slab\'\', \'\'Roboto\'\', \'\'Arial\'\', sans-serif; font-size: 15px; font-weight: normal; margin: 0; margin-bottom: 15px;\">\n<b>Version comments: <%VERSION_COMMENTS%></b>\n</p>\n<p style=\"font-family: \'\'Roboto Slab\'\', \'\'Roboto\'\', \'\'Arial\'\', sans-serif; font-size: 15px; font-weight: normal; margin: 0; margin-bottom: 15px;\">\nPlease log in to <a href=\"https://www.quantificationanalytics.org/#/login\">QAT</a> to review details.\n</p>\n<p style=\"font-family: \'\'Roboto Slab\'\', \'\'Roboto\'\', \'\'Arial\'\', sans-serif; font-size: 14px; font-weight: normal; margin: 0; margin-bottom: 15px;padding-bottom: 20px;\">\n                            Thanks,<br>\n    Quantification Analytics Tool<br>\n    (<b><i>I am a robot. Please do not reply to this email</i></b>) \n                        </p>\n                      </td>\n                    </tr>\n                  </table>\n                </td>\n              </tr>\n\n            <!-- END MAIN CONTENT AREA -->\n            </table>\n          <!-- END CENTERED WHITE CONTAINER -->\n          </div>\n        </td>\n        <td style=\"font-family: \'\'Roboto Slab\'\', \'\'Roboto\'\', \'\'Arial\'\', sans-serif; font-size: 14px; vertical-align: top;\">&nbsp;</td>\n      </tr>\n    </table>\n  </body>\n</html>' WHERE `EMAIL_TEMPLATE_ID`=@maxTempletId;

-- business_function ,role_business_function queries

INSERT INTO `ap_label` values(null,'Notification To commit','','','',1,now(),1,now(),24);
SELECT MAX(al.LABEL_ID) INTO @maxId FROM ap_label al ;
select @maxId;
insert into us_business_function values ('ROLE_BF_NOTIFICATION_TO_COMMIT',@maxId,1,now(),1,now());


INSERT INTO `ap_label` values(null,'Notification Cc commit','','','',1,now(),1,now(),24);
SELECT MAX(al.LABEL_ID) INTO @maxId FROM ap_label al ;
select @maxId;
insert into us_business_function values ('ROLE_BF_NOTIFICATION_CC_COMMIT',@maxId,1,now(),1,now());

INSERT INTO `ap_label` values(null,'Notification To approve','','','',1,now(),1,now(),24);
SELECT MAX(al.LABEL_ID) INTO @maxId FROM ap_label al ;
select @maxId;
insert into us_business_function values ('ROLE_BF_NOTIFICATION_TO_APPROVE',@maxId,1,now(),1,now());

INSERT INTO `ap_label` values(null,'Notification Cc approve','','','',1,now(),1,now(),24);
SELECT MAX(al.LABEL_ID) INTO @maxId FROM ap_label al ;
select @maxId;
insert into us_business_function values ('ROLE_BF_NOTIFICATION_CC_APPROVE',@maxId,1,now(),1,now());


INSERT INTO `ap_label` values(null,'Notification To reject','','','',1,now(),1,now(),24);
SELECT MAX(al.LABEL_ID) INTO @maxId FROM ap_label al ;
select @maxId;
insert into us_business_function values ('ROLE_BF_NOTIFICATION_TO_REJECT',@maxId,1,now(),1,now());


INSERT INTO `ap_label` values(null,'Notification Cc reject','','','',1,now(),1,now(),24);
SELECT MAX(al.LABEL_ID) INTO @maxId FROM ap_label al ;
select @maxId;
insert into us_business_function values ('ROLE_BF_NOTIFICATION_CC_REJECT',@maxId,1,now(),1,now());


insert into us_role_business_function values (null,'ROLE_PROGRAM_ADMIN','ROLE_BF_NOTIFICATION_CC_COMMIT',1,now(),1,now());
insert into us_role_business_function values (null,'ROLE_SUPPLY_PLAN_REVIEWER','ROLE_BF_NOTIFICATION_TO_COMMIT',1,now(),1,now());
insert into us_role_business_function values (null,'ROLE_PROGRAM_ADMIN','ROLE_BF_NOTIFICATION_CC_REJECT',1,now(),1,now());
insert into us_role_business_function values (null,'ROLE_SUPPLY_PLAN_REVIEWER','ROLE_BF_NOTIFICATION_CC_REJECT',1,now(),1,now());

insert into us_role_business_function values (null,'ROLE_GUEST_USER','ROLE_BF_NOTIFICATION_CC_APPROVE',1,now(),1,now());
insert into us_role_business_function values (null,'ROLE_PROGRAM_ADMIN','ROLE_BF_NOTIFICATION_CC_APPROVE',1,now(),1,now());
insert into us_role_business_function values (null,'ROLE_PROGRAM_USER','ROLE_BF_NOTIFICATION_CC_APPROVE',1,now(),1,now());
insert into us_role_business_function values (null,'ROLE_SUPPLY_PLAN_REVIEWER','ROLE_BF_NOTIFICATION_CC_APPROVE',1,now(),1,now());
insert into us_role_business_function values (null,'ROLE_VIEW_DATA_ENTRY','ROLE_BF_NOTIFICATION_CC_APPROVE',1,now(),1,now());
insert into us_role_business_function values (null,'ROLE_REALM_ADMIN','ROLE_BF_NOTIFICATION_CC_APPROVE',1,now(),1,now());

