DROP VIEW w2;
DROP VIEW w1;

CREATE VIEW w1 AS SELECT a.imie, a.nazwisko, f.tytul FROM aktorzy a 
INNER JOIN zagrali z ON a.aktor_id = z.aktor_id 
INNER JOIN filmy f ON z.film_id = f.film_id; 
SELECT * FROM w1;

CREATE VIEW w2 AS SELECT tytul, gatunek, czas_trwania, kategoria FROM filmy;

CREATE USER 'userlab'@'localhost' IDENTIFIED BY '123';
GRANT SELECT ON w1 TO 'userlab'@'localhost';
GRANT SELECT ON w2 TO 'userlab'@'localhost';