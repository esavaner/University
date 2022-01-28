DROP TRIGGER new_k;

DELIMITER //
CREATE TRIGGER new_k BEFORE INSERT ON kontrakty FOR EACH ROW BEGIN
	CASE WHEN NEW.agent NOT IN (SELECT licencja FROM agenci)
    THEN INSERT INTO agenci(licencja, nazwa, wiek, typ) VALUES (NEW.agent, 'nowy',25, 2);
    END CASE;
	IF ((SELECT * FROM kontrakty WHERE NEW.aktor = aktor AND koniec > CURDATE() AND poczatek < CURDATE()) IS NULL)
	THEN SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'error';
    END IF;
END//