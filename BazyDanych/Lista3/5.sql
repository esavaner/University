use `lista3`;
DELIMITER //
CREATE PROCEDURE custom_agg (agg varchar(30), kol varchar(30))
BEGIN
	CASE WHEN kol = 'PESEL' THEN CASE
		WHEN agg ='min' THEN SELECT kol, agg, MIN(PESEL) FROM ludzie;
        WHEN agg ='max' THEN SELECT kol, agg, MAX(PESEL) FROM ludzie;
        WHEN agg ='sum' THEN SELECT kol, agg, SUM(PESEL) FROM ludzie;
        WHEN agg ='avg' THEN SELECT kol, agg, AVG(PESEL) FROM ludzie;
        WHEN agg ='count' THEN SELECT kol, agg, COUNT(PESEL) FROM ludzie;
        END CASE;
        WHEN kol = 'imie' THEN CASE
		WHEN agg ='min' THEN SELECT kol, agg, MIN(imie) FROM ludzie;
        WHEN agg ='max' THEN SELECT kol, agg, MAX(imie) FROM ludzie;
        WHEN agg ='sum' THEN SELECT kol, agg, SUM(imie) FROM ludzie;
        WHEN agg ='avg' THEN SELECT kol, agg, AVG(imie) FROM ludzie;
        WHEN agg ='count' THEN SELECT kol, agg, COUNT(imie) FROM ludzie;
        END CASE;
        WHEN kol = 'nazwisko' THEN CASE
		WHEN agg ='min' THEN SELECT kol, agg, MIN(nazwisko) FROM ludzie;
        WHEN agg ='max' THEN SELECT kol, agg, MAX(nazwisko) FROM ludzie;
        WHEN agg ='sum' THEN SELECT kol, agg, SUM(nazwisko) FROM ludzie;
        WHEN agg ='avg' THEN SELECT kol, agg, AVG(nazwisko) FROM ludzie;
        WHEN agg ='count' THEN SELECT kol, agg, COUNT(nazwisko) FROM ludzie;
        END CASE;
        WHEN kol = 'data_urodzenia' THEN CASE
		WHEN agg ='min' THEN SELECT kol, agg, MIN(data_urodzenia) FROM ludzie;
        WHEN agg ='max' THEN SELECT kol, agg, MAX(data_urodzenia) FROM ludzie;
        WHEN agg ='sum' THEN SELECT kol, agg, SUM(data_urodzenia) FROM ludzie;
        WHEN agg ='avg' THEN SELECT kol, agg, AVG(data_urodzenia) FROM ludzie;
        WHEN agg ='count' THEN SELECT kol, agg, COUNT(data_urodzenia) FROM ludzie;
        END CASE;
        WHEN kol = 'wzrost' THEN CASE
		WHEN agg ='min' THEN SELECT kol, agg, MIN(wzrost) FROM ludzie;
        WHEN agg ='max' THEN SELECT kol, agg, MAX(wzrost) FROM ludzie;
        WHEN agg ='sum' THEN SELECT kol, agg, SUM(wzrost) FROM ludzie;
        WHEN agg ='avg' THEN SELECT kol, agg, AVG(wzrost) FROM ludzie;
        WHEN agg ='count' THEN SELECT kol, agg, COUNT(wzrost) FROM ludzie;
        END CASE;
        WHEN kol = 'waga' THEN CASE
		WHEN agg ='min' THEN SELECT kol, agg, MIN(waga) FROM ludzie;
        WHEN agg ='max' THEN SELECT kol, agg, MAX(waga) FROM ludzie;
        WHEN agg ='sum' THEN SELECT kol, agg, SUM(waga) FROM ludzie;
        WHEN agg ='avg' THEN SELECT kol, agg, AVG(waga) FROM ludzie;
        WHEN agg ='count' THEN SELECT kol, agg, COUNT(waga) FROM ludzie;
        END CASE;
        WHEN kol = 'rozmiar_buta' THEN CASE
		WHEN agg ='min' THEN SELECT kol, agg, MIN(rozmiar_buta) FROM ludzie;
        WHEN agg ='max' THEN SELECT kol, agg, MAX(rozmiar_buta) FROM ludzie;
        WHEN agg ='sum' THEN SELECT kol, agg, SUM(rozmiar_buta) FROM ludzie;
        WHEN agg ='avg' THEN SELECT kol, agg, AVG(rozmiar_buta) FROM ludzie;
        WHEN agg ='count' THEN SELECT kol, agg, COUNT(rozmiar_buta) FROM ludzie;
        END CASE;
        WHEN kol = 'ulubiony_kolor' THEN CASE
		WHEN agg ='min' THEN SELECT kol, agg, MIN(ulubiony_kolor) FROM ludzie;
        WHEN agg ='max' THEN SELECT kol, agg, MAX(ulubiony_kolor) FROM ludzie;
        WHEN agg ='sum' THEN SELECT kol, agg, SUM(ulubiony_kolor) FROM ludzie;
        WHEN agg ='avg' THEN SELECT kol, agg, AVG(ulubiony_kolor) FROM ludzie;
        WHEN agg ='count' THEN SELECT kol, agg, COUNT(ulubiony_kolor) FROM ludzie;
        END CASE;
	END CASE;
END //
CALL custom_agg('sum','ulubiony_kolor' );

DROP PROCEDURE custom_agg;