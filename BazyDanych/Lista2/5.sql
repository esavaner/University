DELIMITER //
CREATE PROCEDURE do_for_l()
BEGIN
	DECLARE id INT DEFAULT 1;
    DECLARE lic varchar(30);
    DECLARE nam varchar(90);
	WHILE id <= 1000 DO
		INSERT INTO Agenci VALUES (
        CONCAT(CHAR(ROUND(65 + (RAND()*25))), CHAR(ROUND(65 + (RAND()*25))), CHAR(ROUND(65 + (RAND()*25))), CHAR(ROUND(65 + (RAND()*25))), CHAR(ROUND(65 + (RAND()*25))), CHAR(ROUND(65 + (RAND()*25)))),
        CONCAT(CHAR(ROUND(65 + (RAND()*25))), CHAR(ROUND(65 + (RAND()*25))), CHAR(ROUND(65 + (RAND()*25))), CHAR(ROUND(65 + (RAND()*25))), CHAR(ROUND(65 + (RAND()*25))), CHAR(ROUND(65 + (RAND()*25)))),
        ROUND(RAND()*(66-21)+21), 2);
        SET id = id + 1;
    END WHILE;
END //
DELIMITER ;

CALL do_for_l();
DROP PROCEDURE do_for_l;

DELIMITER //
CREATE TABLE temp(a_id int);
CREATE PROCEDURE do_for_loopp()
BEGIN
	DECLARE num INT DEFAULT 1;
	DECLARE aid int;
	WHILE num <= 178 DO
		SET aid = (SELECT aktor_id FROM aktorzy WHERE aktor_id NOT IN (SELECT a_id FROM temp) ORDER BY RAND() LIMIT 1);
		INSERT INTO kontrakty(agent, aktor, poczatek, koniec, gaża) VALUES
        ((SELECT licencja FROM agenci ORDER BY RAND() LIMIT 1),
        aid,
        DATE_FORMAT(FROM_UNIXTIME(RAND()*(UNIX_TIMESTAMP('2007-01-01')-UNIX_TIMESTAMP('2015-12-31')) + UNIX_TIMESTAMP('2015-12-31')), '%Y-%m-%d'),
        DATE_FORMAT(FROM_UNIXTIME(RAND()*(UNIX_TIMESTAMP('2016-01-01')-UNIX_TIMESTAMP('2022-12-31')) + UNIX_TIMESTAMP('2022-12-31')), '%Y-%m-%d'),
        FLOOR(100 + RAND()*(21000 - 100)));
		INSERT INTO temp VALUES (aid);
		SET num = num + 1;
    END WHILE;
END//

CALL do_for_loopp();
DROP TABLE temp;
DROP PROCEDURE do_for_loopp;