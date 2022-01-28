CREATE VIEW st1 AS SELECT DISTINCT customer_id, staff_id FROM payment WHERE staff_id = 1;
CREATE VIEW st2 AS SELECT DISTINCT customer_id, staff_id FROM payment WHERE staff_id = 2;
SELECT c.first_name, c.last_name FROM st1 s1 INNER JOIN customer c ON s1.customer_id = c.customer_id
INNER JOIN st2 s2 ON s1.customer_id = s2.customer_id WHERE s1.staff_id != s2.staff_id;
DROP VIEW st1;
DROP VIEW st2;