CREATE TABLE Agenci ( licencja varchar(30) PRIMARY KEY, nazwa varchar(90),
wiek int NOT NULL CHECK (wiek >= 21), typ enum('osoba indywidualna', 'agencja', 'inny') );
CREATE TABLE Kontrakty ( ID int PRIMARY KEY AUTO_INCREMENT, agent varchar(30), aktor int,
poczatek date, koniec date NOT NULL CHECK (DATEDIFF(day, poczatek, koniec) >= 1), gaża int NOT NULL CHECK (gaża >= 0),
FOREIGN KEY (agent) REFERENCES agenci(licencja), FOREIGN KEY (aktor) REFERENCES aktorzy(aktor_id) );