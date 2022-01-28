CREATE VIEW deleted AS SELECT film_id FROM film WHERE special_features LIKE '%Deleted%';
CREATE VIEW v2 AS SELECT f.actor_id FROM film_actor f INNER JOIN deleted ON f.film_id = deleted.film_id;
SELECT a.first_name, a.last_name FROM actor a INNER JOIN v2 ON a.actor_id = v2.actor_id;
DROP VIEW deleted;
DROP VIEW v2;