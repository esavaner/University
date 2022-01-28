CREATE VIEW actr AS SELECT c.name, a.last_name, a.actor_id FROM category c 
INNER JOIN film_category fc ON c.category_id = fc.category_id 
INNER JOIN film_actor fa ON fc.film_id = fa.film_id
INNER JOIN actor a ON a.actor_id = fa.actor_id;
CREATE VIEW actr1 AS SELECT last_name, actor_id, COUNT(name) AS action FROM actr WHERE name = 'Action' GROUP BY actor_id;
CREATE VIEW actr2 AS SELECT last_name, actor_id, COUNT(name) AS horror FROM actr WHERE name = 'Horror' GROUP BY actor_id;
SELECT actr1.last_name FROM actr1 INNER JOIN actr2 ON actr1.actor_id = actr2.actor_id WHERE actr1.action < actr2.horror;
DROP VIEW actr;
DROP VIEW actr1;
DROP VIEW actr2;