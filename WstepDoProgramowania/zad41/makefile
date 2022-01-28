# Makefile

all: zad41

zad41: main.o match.o ; clang -o zad41 main.o match.o

main.o: main.c ; clang -c main.c

match.o: match.c ; clang -c match.c

clear: ; rm -f main *.o *~
