DELIMITER //
CREATE PROCEDURE findGaza(imie varchar(30)) 
BEGIN
SELECT gaża FROM kontrakty WHERE agent = imie;
END //

CALL findGaza('SHFCXG');
DROP PROCEDURE findGaza;