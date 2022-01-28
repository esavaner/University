CC = g++
FLAGS = -std=c++11 --pedantic -Wall -Wextra
RM = rm -f
EXE = main

ifeq ($(OS),Windows_NT)
	RM = del
	EXE = main.exe
endif

all: main

main: algorithms.o main.o
		$(CC) $(FLAGS) -o main main.o algorithms.o	

main.o: main.cpp
		$(CC) $(FLAGS) -c main.cpp

algorithms.o: algorithms.cpp
		$(CC) $(FLAGS) -c algorithms.cpp

clean: 
		$(RM) *.o
		$(RM) $(EXE)