# Makefile

all: zad42

zad42: main.o guess.o printAnswer.o remove.o match.o pozycja.o ; clang -o zad42 main.o guess.o printAnswer.o remove.o match.o pozycja.o

main.o: main.c ; clang -c main.c

guess.o: guess.c ; clang -c guess.c

printAnswer.o: printAnswer.c ; clang -c printAnswer.c

remove.o: remove.c ; clang -c remove.c

match.o: match.c ; clang -c match.c

pozycja.o: pozycja.c ; clang -c pozycja.c

clear: ; rm -f main *.o *~
