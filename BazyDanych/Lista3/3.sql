USE `Laboratorium-filmoteka`;
SELECT imie FROM aktorzy USE INDEX (idx_name) WHERE imie LIKE 'J%';

SELECT nazwisko FROM aktorzy USE INDEX (idx_name) WHERE zagranych_filmow >= 12;

SELECT DISTINCT tytul FROM filmy f INNER JOIN zagrali z ON f.film_id = z.film_id WHERE z.aktor_id IN (
	SELECT aktor_id FROM zagrali WHERE film_id IN ( 
		SELECT z.film_id FROM zagrali z JOIN aktorzy a WHERE 
			a.aktor_id = z.aktor_id AND a.imie = 'ZERO' AND a.nazwisko = 'CAGE')) ORDER BY 1;
        
SELECT aktor FROM kontrakty  USE INDEX (idx_end) WHERE koniec = (SELECT MIN(koniec) FROM kontrakty WHERE koniec > CURDATE());

SELECT imie, MAX(suma) FROM (SELECT imie, COUNT(imie) AS suma FROM aktorzy USE INDEX (idx_name) GROUP BY imie) AS maximum;