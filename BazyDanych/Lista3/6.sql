DELIMITER //
CREATE PROCEDURE wypl(budzet int, zawod1 varchar(50))
BEGIN
	DECLARE reszta int DEFAULT 1;
    DECLARE pensja_temp int;
    DECLARE pesel_temp char(11);
    DECLARE pensja_c CURSOR FOR SELECT pensja FROM pracownicy WHERE zawod = zawod1;
    DECLARE pesel_c CURSOR FOR SELECT PESEL FROM pracownicy WHERE zawod = zawod1;
	CREATE TABLE wyplacono (PESEL char(11), wyplata enum('wyplacono', 'nie wyplacono'));
    START TRANSACTION;
    OPEN pesel_c;
    OPEN pensja_c;
	WHILE reszta = 1 DO
		FETCH pensja_c INTO pensja_temp;
        SET budzet = budzet - pensja_temp;
        IF budzet < 0 THEN 
			SET reszta = 0;
		ELSE 
			FETCH pesel_c INTO pesel_temp;
			INSERT INTO wyplacono VALUES(CONCAT('********', RIGHT(pesel_temp,3)), 1);
		END IF;
	END WHILE;
    CLOSE pesel_c;
    CLOSE pensja_c;
    COMMIT;
    SELECT * FROM wyplacono;
	DROP TABLE wyplacono;
END //

CALL wypl(100000, 'informatyk');
DROP PROCEDURE wypl;