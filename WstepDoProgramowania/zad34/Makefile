# Makefile

all: zad34

zad34: main.o east.o west.o north.o south.o dist.o new.o ; clang -o zad34 main.o east.o west.o north.o south.o dist.o new.o -lm
main.o: main.c ; clang -c main.c

east.o: east.c ; clang -c east.c

north.o: north.c ; clang -c north.c

south.o: south.c ; clang -c south.c

dist.o: dist.c ; clang -c dist.c

new.o: new.c ; clang -c new.c

clear: ; rm -f *.o *~
