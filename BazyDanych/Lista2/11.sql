DROP TRIGGER ins;
DROP TRIGGER upd;
DROP TRIGGER del;


DELIMITER //
CREATE TRIGGER del AFTER DELETE ON zagrali FOR EACH ROW BEGIN
	UPDATE aktorzy a SET a.zagranych_filmow = a.zagranych_filmow - 1 WHERE a.aktor_id = OLD.aktor_id;
END //

DELIMITER //
CREATE TRIGGER ins AFTER INSERT ON zagrali FOR EACH ROW BEGIN
	CASE WHEN NEW.aktor_id IN (SELECT aktor_id FROM aktorzy)
    THEN UPDATE aktorzy a SET a.zagranych_filmow = a.zagranych_filmow + 1 WHERE a.aktor_id = NEW.aktor_id;
    ELSE INSERT INTO aktorzy(aktor_id, zagranych_filmow) VALUES (NEW.aktor_id, 1);
    END CASE;
END//

DELIMITER //
CREATE TRIGGER upd AFTER UPDATE ON zagrali FOR EACH ROW BEGIN 
	UPDATE aktorzy a SET a.zagranych_filmow = (SELECT COUNT(film_id) FROM zagrali GROUP BY aktor_id HAVING aktor_id = a.aktor_id); 
END//