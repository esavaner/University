CREATE DATABASE `Lista3`;
USE `Lista3`;
CREATE TABLE Ludzie (PESEL char(11) PRIMARY KEY NOT NULL CHECK (PESEL > 0), imie varchar(30), nazwisko varchar(30),
data_urodzenia date, wzrost float, waga float, rozmiar_buta int, 
ulubiony_kolor enum('czarny', 'czerwony', 'zielony', 'niebieski', 'bialy'));

CREATE TABLE Pracownicy (PESEL char(11), zawod varchar(50), pensja float, FOREIGN KEY (PESEL) REFERENCES Ludzie(PESEL));

DELIMITER //
CREATE PROCEDURE fillpeople()
BEGIN
	DECLARE id INT DEFAULT 1;
    DECLARE dateR DATE;
	WHILE id <= 200 DO
		SET dateR=DATE_FORMAT(FROM_UNIXTIME(RAND()*(UNIX_TIMESTAMP('1971-01-01')-UNIX_TIMESTAMP('2002-12-31')) + UNIX_TIMESTAMP('2002-12-31')), '%Y-%m-%d');
		INSERT INTO Ludzie VALUES (
        ROUND(1 + RAND()*100000000000),
        CONCAT(CHAR(ROUND(65 + (RAND()*25))), CHAR(ROUND(65 + (RAND()*25))), CHAR(ROUND(65 + (RAND()*25))), CHAR(ROUND(65 + (RAND()*25))), CHAR(ROUND(65 + (RAND()*25))), CHAR(ROUND(65 + (RAND()*25)))),
        CONCAT(CHAR(ROUND(65 + (RAND()*25))), CHAR(ROUND(65 + (RAND()*25))), CHAR(ROUND(65 + (RAND()*25))), CHAR(ROUND(65 + (RAND()*25))), CHAR(ROUND(65 + (RAND()*25))), CHAR(ROUND(65 + (RAND()*25)))),
        dateR,
        ROUND(150+RAND()*50),
        ROUND(50+RAND()*50),
        ROUND(30+RAND()*15),
        ROUND(RAND()*(5-1)+1));
        SET id = id + 1;
    END WHILE;
END //
DELIMITER ;

CALL fillpeople();
DROP PROCEDURE fillpeople;

DELIMITER //
CREATE TABLE temp(a_temp char(11));
CREATE PROCEDURE fillwork()
BEGIN
	DECLARE num INT DEFAULT 1;
	DECLARE aid char(11);
	WHILE num <= 50 DO
		SET aid = (SELECT PESEL FROM ludzie WHERE PESEL NOT IN (SELECT a_temp FROM temp) 
        AND TIMESTAMPDIFF(YEAR, data_urodzenia, CURDATE()) >= 18 ORDER BY RAND() LIMIT 1);
		INSERT INTO pracownicy VALUES (
        aid,
        'aktor',
        ROUND(RAND()*(15000-5000)+5000));
		INSERT INTO temp VALUES (aid);
		SET num = num + 1;
    END WHILE;
    SET num = 1;
    WHILE num <= 33 DO
		SET aid = (SELECT PESEL FROM ludzie WHERE PESEL NOT IN (SELECT a_temp FROM temp) 
        AND TIMESTAMPDIFF(YEAR, data_urodzenia, CURDATE()) >= 18 ORDER BY RAND() LIMIT 1);
		INSERT INTO pracownicy VALUES (
        aid,
        'agent',
        ROUND(RAND()*(15000-5000)+5000));
		INSERT INTO temp VALUES (aid);
		SET num = num + 1;
    END WHILE;
    SET num = 1;
    WHILE num <= 13 DO
		SET aid = (SELECT PESEL FROM ludzie WHERE PESEL NOT IN (SELECT a_temp FROM temp) 
        AND TIMESTAMPDIFF(YEAR, data_urodzenia, CURDATE()) >= 18 ORDER BY RAND() LIMIT 1);
		INSERT INTO pracownicy VALUES (
        aid,
        'informatyk',
        ROUND(RAND()*(15000-5000)+5000));
		INSERT INTO temp VALUES (aid);
		SET num = num + 1;
    END WHILE;
    SET num = 1;
    WHILE num <= 2 DO
		SET aid = (SELECT PESEL FROM ludzie WHERE PESEL NOT IN (SELECT a_temp FROM temp) 
        AND TIMESTAMPDIFF(YEAR, data_urodzenia, CURDATE()) >= 18 ORDER BY RAND() LIMIT 1);
		INSERT INTO pracownicy VALUES (
        aid,
        'reporter',
        ROUND(RAND()*(15000-5000)+5000));
		INSERT INTO temp VALUES (aid);
		SET num = num + 1;
    END WHILE;
    SET num = 1;
    WHILE num <= 77 DO
		SET aid = (SELECT PESEL FROM ludzie WHERE PESEL NOT IN (SELECT a_temp FROM temp) 
        AND TIMESTAMPDIFF(YEAR, data_urodzenia, CURDATE()) >= 18 ORDER BY RAND() LIMIT 1);
		INSERT INTO pracownicy VALUES (
        aid,
        'sprzedawca',
        ROUND(RAND()*(15000-5000)+5000));
		INSERT INTO temp VALUES (aid);
		SET num = num + 1;
    END WHILE;
END//

CALL fillwork();
DROP TABLE temp;
DROP PROCEDURE fillwork;