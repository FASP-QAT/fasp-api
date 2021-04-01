CREATE DEFINER=`faspUser`@`localhost` PROCEDURE `generateForgotPasswordToken`(`VAR_USER_ID` INT(10), `VAR_TOKEN_DATE` DATETIME)
BEGIN
	SET @userId = null;
	SELECT USER_ID INTO @userId FROM us_user WHERE USER_ID=VAR_USER_ID;
    IF @userId IS NOT NULL THEN 
		SET @rowCnt = 1;
		SET @token = '';
		WHILE (@rowCnt != 0) DO
			SET @token = '';
			SET @allowedChars = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789';
			SET @allowedCharsLen = LENGTH(@allowedChars);
			SET @tokenLen = 25;
			SET @i = 0;
			WHILE (@i < @tokenLen) DO
				SET @token = CONCAT(@token, substring(@allowedChars, FLOOR(RAND() * @allowedCharsLen + 1), 1));
				SET @i = @i + 1;
			END WHILE;
			SELECT count(*) INTO @rowCnt FROM us_forgot_password_token WHERE TOKEN=@token;
		END WHILE;
		INSERT INTO us_forgot_password_token (USER_ID, TOKEN, TOKEN_GENERATION_DATE) VALUES (@userId, @token, `VAR_TOKEN_DATE`);
        SELECT @token;
	ELSE 
		select null;
    END IF;
END
