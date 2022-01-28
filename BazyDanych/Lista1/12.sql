SELECT a.first_name, a.last_name, f.film_id, COUNT(*) FROM actor a INNER JOIN film_actor f ON f.actor_id = a.actor_id
GROUP BY a.first_name, f.film_id HAVING COUNT(*) > 1;