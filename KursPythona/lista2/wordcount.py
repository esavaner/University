import sys
import os

def readFile(path):
    try:
        print("liczba bajtow:", os.path.getsize(path))
        print("liczba slow:", len(open(path).read().split()))
        print("liczba linii:", sum(1 for line in open(path)))
        try:
            print("maksymalna dlugosc linii:", len(max(open(path), key = len)))
        except ValueError:
            print("wszystkie line sa puste")
    except IOError:
        print("Nie mozna otworzyc pliku")

if __name__ == "__main__":
    if len(sys.argv) < 2:
        print("Brak pliku wejsciowego")
    else:
        readFile(sys.argv[1])