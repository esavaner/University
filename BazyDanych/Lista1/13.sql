CREATE VIEW nb AS SELECT DISTINCT fa.actor_id FROM film_actor fa INNER JOIN film f ON f.film_id = fa.film_id WHERE f.title NOT LIKE 'B%';
SELECT a.last_name FROM actor a INNER JOIN nb ON a.actor_id = nb.actor_id;
DROP VIEW nb;