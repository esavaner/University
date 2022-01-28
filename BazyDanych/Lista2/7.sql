DELIMITER //
CREATE PROCEDURE findAgent(imie varchar(30), nazwisko varchar(30)) 
BEGIN
SELECT k.agent, TIMESTAMPDIFF(DAY,CURDATE(), k.koniec) AS pozostalo_dni FROM kontrakty k INNER JOIN aktorzy a ON k.aktor = a.aktor_id WHERE imie = a.imie AND nazwisko = a.nazwisko;
END //

CALL findAgent('NICK','WAHLBERG');
DROP PROCEDURE findAgent;