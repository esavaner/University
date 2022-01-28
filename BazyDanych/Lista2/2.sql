CREATE TABLE aktorzy ( aktor_id int, imie varchar(255), nazwisko varchar(255) );
CREATE TABLE filmy ( film_id int, tytul varchar(255), gatunek varchar(255), czas_trwania int, kategoria varchar(255) );
CREATE TABLE zagrali (  aktor_id int, film_id int );

INSERT INTO `laboratorium-filmoteka`.aktorzy(aktor_id, imie, nazwisko) 
SELECT actor_id, first_name, last_name FROM sakila.actor 
WHERE first_name NOT LIKE '%v%' AND first_name NOT LIKE '%x%' AND first_name NOT LIKE '%q%'
AND last_name NOT LIKE '%v%' AND last_name NOT LIKE '%x%' AND last_name NOT LIKE '%q%';

INSERT INTO `laboratorium-filmoteka`.filmy(film_id, tytul, gatunek, czas_trwania, kategoria) 
SELECT f.film_id, f.title, c.name, f.length, f.rating FROM sakila.film f 
INNER JOIN sakila.film_category fc ON f.film_id = fc.film_id 
INNER JOIN sakila.category c ON c.category_id = fc.category_id
WHERE f.title NOT LIKE '%v%' AND f.title NOT LIKE '%x%' AND f.title NOT LIKE '%q%';

INSERT INTO `laboratorium-filmoteka`.zagrali(aktor_id, film_id)
SELECT actor_id, film_id FROM sakila.film_actor
WHERE EXISTS (SELECT aktor_id FROM aktorzy) AND EXISTS (SELECT film_id FROM filmy);