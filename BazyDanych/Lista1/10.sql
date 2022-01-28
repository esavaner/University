CREATE VIEW view3 AS SELECT country_id, COUNT(city_id) AS quantity FROM city GROUP BY country_id;
SELECT c.country FROM country c INNER JOIN view3 ON c.country_id = view3.country_id WHERE view3.quantity >= 7;
DROP VIEW view3;