CREATE DATABASE `Laboratorium-Filmoteka`;
USE `Laboratorium-Filmoteka`;
CREATE USER '244960'@'localhost' IDENTIFIED BY 'filip960';
GRANT SELECT ON `Laboratorium-Filmoteka`.* TO '244960'@'localhost';
GRANT INSERT ON `Laboratorium-Filmoteka`.* TO '244960'@'localhost';
GRANT UPDATE ON `Laboratorium-Filmoteka`.* TO '244960'@'localhost';