ALTER TABLE language ADD film_no CHAR(25);
UPDATE language SET film_no = 0;
CREATE VIEW vlang AS SELECT language_id, COUNT(film_id) AS film_no FROM film GROUP BY language_id;
UPDATE language l INNER JOIN vlang v ON l.language_id = v.language_id SET l.film_no = v.film_no;
DROP VIEW vlang;