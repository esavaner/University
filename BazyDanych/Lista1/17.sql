UPDATE film SET language_id = '4' WHERE title = 'WON DARES';
CREATE VIEW nfilm AS SELECT film_id FROM film_actor WHERE actor_id = 
(SELECT a.actor_id FROM actor a WHERE a.first_name = 'NICK' AND a.last_name = 'WAHLBERG');
UPDATE film f INNER JOIN nfilm n ON f.film_id = n.film_id SET f.language_id = '6';
DROP VIEW nfilm;
