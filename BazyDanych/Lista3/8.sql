CREATE DATABASE `Logs_pensja`;
USE `Logs_pensja`;
CREATE TABLE log_pensja (PESEL char(11), stara_pensja int, nowa_pensja int, czas datetime, uzytkownik varchar(90));

USE `Lista3`;

DELIMITER //
CREATE TRIGGER zmina_pensji AFTER UPDATE ON pracownicy FOR EACH ROW
BEGIN
	IF NEW.pensja <> OLD.pensja THEN INSERT INTO `Logs_pensja`.log_pensja VALUES(
		OLD.PESEL,
		OLD.pensja,
		NEW.pensja,
		NOW(),
		CURRENT_USER());
	END IF;
END//