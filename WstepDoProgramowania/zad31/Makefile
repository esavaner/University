# Makefile

all: zad31

zad31: main.o pal.o ; clang -o zad31 main.o pal.o

main.o: main.c ; clang -c main.c

pal.o: pal.c ; clang -c pal.c

clean: ; rm -f main *.o *~
