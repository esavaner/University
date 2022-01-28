CREATE VIEW vmax AS SELECT agent, aktor, SUM(TIMESTAMPDIFF(DAY, poczatek, koniec)) AS suma FROM kontrakty GROUP BY agent, aktor;
SELECT a.*, MAX(v.suma) AS dlugosc 
FROM agenci a INNER JOIN vmax v ON a.licencja = v.agent; 
DROP VIEW vmax;