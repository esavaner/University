import random
import time
import math
from enum import Enum
from typing import List
from sys import stderr


#enum dostepnych ruchow
class Moves(Enum):
    U = (0, -1)
    D = (0, 1)
    R = (1, 0)
    L = (-1, 0)


#klasa krata
class Grid:
    empty = 0
    wall = 1
    agent = 5
    ext = 8

    def __init__(self, maze, limit):
        self.maze = maze
        self.limit = limit
        self.pos = self.start()


    #znalezienie pozycji startowej agenta
    def start(self):
        for i, _ in enumerate(self.maze):
            for j, tile in enumerate(self.maze[i]):
                if tile == self.agent:
                    return j, i

        
    #liczenie kosztu dla konkretnej sciezki
    def cost(self, route):
        c = 0
        x, y = self.pos
        for move in route:
            c += 1

            #jesli sciezka jest dluzsza niz limit to zwraca inf
            if c > self.limit:
                return float('Inf')

            dx, dy = move.value
            newx = x + dx
            newy = y + dy

            #jesli znajdzie wyjscie to zwraca koszt sciezki
            if self.maze[newy][newx] == self.ext:
                return c

            if self.maze[newy][newx] != self.wall:
                x, y = newx, newy

        return float('Inf')



#selekcja losowego najlepszego wyniku
def selection(results):

    #jakis poczatkowy najlepszy
    best = random.randrange(len(results))

    #5 razy szukanie lepszego wyniku
    #for i in range(5):
    #    curr = random.randrange(len(results))
     #   if results[curr] < results[best]:
     #       best = curr
    return best



#kombinacja dwoch wynikow
def recombination(x1c, x2c):
    x1 = x1c.copy()
    x2 = x2c.copy()

    #wybieranie losowych indeksow na zamiane bitow
    length = min(len(x1), len(x2))
    i = random.randrange(length)
    j = random.randrange(length)

    #jesli indeks koncowy jest wiekszy od poczatkowego to nalezy je zamienic
    if j > i:
        i, j = j, i

    #zamiana bitow pierwszego na bity drugiego
    if i != j:
        for k in range(i, j):
            x1[k] = x2[k]
    return x1, x2



#mutacja miejsc danego x z pewnym prawdopodobienstwem
def mutation(x):
    for _ in range(5):
        i = random.randrange(len(x))
        j = random.randrange(len(x))
        x[i], x[j] = x[j], x[i]
    return x



#glowna funkcja symulowanego wyzarzania
def genetic(g: Grid, t, psize, result):
    pop = result
    minpath = None
    minimum = float('Inf')
    t0 = time.time()

    #dopoki nie przekroczy czasu
    while time.time() - t0 < t:

        #sprawdzanie czy ktorys obiekt w populacji jest lepszy od aktualnego minimum
        results = []
        for p in pop:

            #liczenie nowego wyniku dla danego obiektu
            newResult = g.cost(p)
            results.append(newResult)

            #jesli nowy wynik jest lepszy to zmienia aktualne minimum
            if minpath is None or newResult < minimum:
                minpath = p
                minimum = newResult


        #tworzenie nowej populacji na podstawie poprzedniej
        popNew = []
        for _ in range(psize//2):

            #wybieranie dwoch losowych wynikow (na podstawie ich jakosci)
            x1 = pop[selection(results)]
            x2 = pop[selection(results)]

            #przypisywanie bitow pierwszego wyniku z drugiego wyniku 
            x1p, x2p = recombination(x1, x2)

            #dodanie zmutowanych wynikow do nowej populacji
            popNew.extend([mutation(x1p), mutation(x2p)])

        #zamiana aktualnie badanej populacji na nowa
        pop = popNew
        toprint = {Moves.U: 'U', Moves.D: 'D', Moves.L: 'L', Moves.R: 'R'}
        for path in pop:
            print(''.join(toprint[p] for p in path), file=stderr)
        print()

    return (minimum, minpath)



if __name__ == "__main__":
    line = input()
    t, n, m, s, p = map(int, line.split())

    maze = [[int(symbol) for symbol in input()] for _ in range(int(n))]
    g = Grid(maze, n*m)

    toget = {'U': Moves.U, 'D': Moves.D, 'L': Moves.L, 'R': Moves.R}
    results = [[toget[symbol] for symbol in input().strip()] for _ in range(s)]

    result, path = genetic(g, t, p, results)
    toprint = {Moves.U: 'U', Moves.D: 'D', Moves.L: 'L', Moves.R: 'R'}
    print(result)
    print(''.join(toprint[p] for p in path[:result]), file=stderr)
    #                                       ^
    #                                       |
    #liczba wykonanych krokow potrzebnych do dotarcia do celu, pozniejsze ruchy nie maja znaczenia