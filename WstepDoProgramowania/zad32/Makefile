# Makefile

all: zad32

zad32: main.o cieciwa.o f.o ; clang -o zad32 main.o cieciwa.o f.o -lm

main.o: main.c ; clang -c main.c

cieciwa.o: cieciwa.c ; clang -c cieciwa.c

f.o: f.c ; clang -c f.c 

clear: ; rm -f main *.o *~
