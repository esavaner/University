Program realizuje kodowania Eliasa i Fibonacciego

W programie dostepne są typy 'gamma', 'delta', 'omega' i 'fibonacci' oraz tryb kodowania 'e' / 'encode' lub dekodowania 'd' / 'decode'
Ostatnie dwa miejsca przy uruchamianiu muszą być odpowiednio plikiem wejściowym i wyjściowym.
Przykładowe uruchomienie: "python lzw.py omega e in.txt out.bin"

Przy podaniu samych plików, program domyślnie ustawiony jest na 'omega' i kodowanie.

Kodowanie generuje dwa pliki, jeden binarny, a drugi tekstowy zapisany samymi 0 i 1.
Dekodowanie dziala tylko na drugim pliku (tekstowym), ponieważ binarne źle się odczytują lub w ogóle nie można ich odczytać.