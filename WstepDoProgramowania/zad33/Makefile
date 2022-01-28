# Makefile

all: zad33

zad33: main.o eu.o ; clang -o zad33 main.o eu.o

main.o: main.c ; clang -c main.c

eu.o: eu.c ; clang -c eu.c

clear: ; rm -f main *.o *~
