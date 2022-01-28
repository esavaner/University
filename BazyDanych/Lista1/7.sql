SELECT f.title, r.rental_date FROM film f INNER JOIN rental r ON r.rental_id = f.film_id
WHERE r.rental_date >= '2005-05-25' AND r.rental_date <='2005-05-31' ORDER BY f.title ASC;