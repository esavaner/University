CREATE VIEW vie AS SELECT customer_id, COUNT(rental_id) AS quantity FROM rental GROUP BY customer_id;
SELECT c.first_name FROM customer c INNER JOIN vie ON c.customer_id = vie.customer_id 
WHERE vie.quantity > (SELECT vie.quantity FROM vie WHERE vie.customer_id = (SELECT customer_id FROM customer WHERE email = 'PETER.MENARD@sakilacustomer.org'));
DROP VIEW vie;