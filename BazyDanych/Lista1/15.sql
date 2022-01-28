CREATE VIEW avam AS SELECT c.first_name, c.last_name, AVG(p.amount) AS av
FROM customer c INNER JOIN payment p ON c.customer_id = p.customer_id GROUP BY p.customer_id;
SELECT avam.first_name, avam.last_name, avam.av FROM avam WHERE avam.av >  (SELECT AVG(amount) FROM payment WHERE payment_date LIKE '%2005-07-07%');
DROP VIEW avam;