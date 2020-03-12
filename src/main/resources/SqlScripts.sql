SET GLOBAL event_scheduler = ON;
DELIMITER $$

CREATE DEFINER=`faspUser`@`localhost` EVENT `tokenLogoutCleanup` ON SCHEDULE EVERY 1 DAY STARTS '2020-02-01 03:00:00' ON COMPLETION NOT PRESERVE ENABLE DO BEGIN
	DELETE tl.* FROM rm_token_logout tl WHERE timestampdiff(DAY, tl.LOGOUT_DATE,  now())>=2;
END$$

DELIMITER ;

