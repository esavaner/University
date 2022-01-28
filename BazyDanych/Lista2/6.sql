DELIMITER //
CREATE PROCEDURE do_for_loop()
BEGIN
	DECLARE num INT DEFAULT 1;
	WHILE num <= 30 DO
		INSERT INTO kontrakty(agent, aktor, poczatek, koniec, gaża) VALUES
        ((SELECT licencja FROM agenci ORDER BY RAND() LIMIT 1),
        (SELECT aktor_id FROM aktorzy ORDER BY RAND() LIMIT 1),
        DATE_FORMAT(FROM_UNIXTIME(RAND()*(UNIX_TIMESTAMP('2007-01-01')-UNIX_TIMESTAMP('2015-12-31')) + UNIX_TIMESTAMP('2015-12-31')), '%Y-%m-%d'),
        DATE_FORMAT(FROM_UNIXTIME(RAND()*(UNIX_TIMESTAMP('2016-01-01')-UNIX_TIMESTAMP('2018-11-28')) + UNIX_TIMESTAMP('2018-11-28')), '%Y-%m-%d'),
        FLOOR(100 + RAND()*(21000 - 100)));
		SET num = num + 1;
    END WHILE;
END//

CALL do_for_loop();
DROP PROCEDURE do_for_loop;