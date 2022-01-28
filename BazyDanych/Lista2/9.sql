PREPARE st1 FROM 'SELECT agent, COUNT(aktor) AS liczba_aktorow FROM kontrakty WHERE agent = ? GROUP BY agent';
SET @ag = 'SHFCXG';
EXECUTE st1 USING @ag;
DEALLOCATE PREPARE st1;