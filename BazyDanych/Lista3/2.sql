CREATE INDEX idx_end USING BTREE ON kontrakty (koniec);
SELECT aktor FROM kontrakty WHERE MONTH(koniec) LIKE MONTH(DATE_ADD(CURDATE(), INTERVAL 1 MONTH))