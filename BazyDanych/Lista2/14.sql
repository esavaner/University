CREATE VIEW info AS SELECT a.imie, a.nazwisko, k.agent, TIMESTAMPDIFF(DAY, CURDATE(), k.koniec) AS pozostalo_dni
FROM aktorzy a INNER JOIN kontrakty k ON a.aktor_id = k.aktor;
SELECT * FROM info;

DROP VIEW info;