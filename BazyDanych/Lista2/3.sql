ALTER TABLE aktorzy ADD zagranych_filmow int;
CREATE VIEW vz AS SELECT aktor_id, COUNT(film_id) AS zagranych FROM zagrali GROUP BY aktor_id;
UPDATE aktorzy a INNER JOIN vz ON a.aktor_id = vz.aktor_id SET a.zagranych_filmow = vz.zagranych;
DROP VIEW vz;