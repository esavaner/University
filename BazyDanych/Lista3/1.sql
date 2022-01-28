USE `Laboratorium-filmoteka`;
CREATE INDEX idx_title USING BTREE ON filmy (tytul);
CREATE INDEX idx_name USING BTREE ON aktorzy (nazwisko, imie(1));
CREATE INDEX idx_actor USING BTREE ON zagrali (aktor_id);