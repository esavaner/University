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


    #znalezienie poczatkowej sciezki
    def path(self):
        route = []
        x, y = self.pos
        moves = list(Moves)
        w = 0

        #dopoki agent nie znajdzie wyjscia
        while self.maze[y][x] != self.ext:

            #sprawdza czy istnieje wyjscie w ktorymkolwiek dostepnym kierunku
            checkend = self.end(x, y)
            if checkend:
                route.append(checkend)
                break

            #nowy ruch
            dx, dy = moves[w].value
            newx = x + dx
            newy = y + dy

            #jesli napota sciane, to losowo wybiera nastepny kierunek ruchu
            if self.maze[newy][newx] == self.wall:
                w = random.randint(0, len(moves) - 1) % len(moves)
            else:
                x, y = newx, newy
                route.append(moves[w])

        return route

        
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


    #sprawdzanie czy istnieje wyjscie w ktorymkolwiek dostepnym kierunku
    def end(self, x, y):
        if self.maze[y - 1][x] == self.ext:
            return Moves.U
        elif self.maze[y + 1][x] == self.ext:
            return Moves.D
        elif self.maze[y][x + 1] == self.ext:
            return Moves.RIGHT
        elif self.maze[y][x - 1] == self.ext:
            return Moves.LEFT
        else:
            return None


#funkcja prawdopodobienstwa bazujaca na nowym i starym wyniku oraz temperaturze
def P(old, new, T):
    if new < old:
        return 1
    else:
        return math.exp(- (new - old)/T)


#funkcja zmiany temperatury
def temperature(T):
    return max(0.001, T*0.999)


#tworzenie sasiada aktualnej sciezki poprzez zamiane 2 pol
def neighbour(route):
    r = route.copy()
    if len(r) > 1:
        i = random.randint(0, len(r) - 1)
        j = random.randint(0, len(r) - 1)
        r[i], r[j] = r[j], r[i]

    return r 


#glowna funkcja symulowanego wyzarzania
def sa(g: Grid, t, T):
    p = g.path()
    minpath, oldpath = p, p
    minimum, oldResult = g.cost(minpath), g.cost(oldpath)
    t0 = time.time()

    #dopoki nie przekroczy czasu
    while time.time() - t0 < t:

        #zmiana temperatury i sciezki
        T = temperature(T)
        newpath = neighbour(oldpath)
        newResult = g.cost(newpath)

        #zamiana wyniku na podstawie funkcji prawdopodobienstwa
        if P(oldResult, newResult, T) > random.random():
            oldpath, oldResult = newpath, newResult

        #jesli nowy winik jest mniejszy niz aktualne minimum
        if oldResult < minimum:
            minpath, minimum = oldpath, oldResult

    return (minimum, minpath)


if __name__ == "__main__":
    line = input()
    t, n, m = map(int, line.split())
    maze = [[int(symbol) for symbol in input()] for _ in range(n)]
    g = Grid(maze, n*m)
    result, path = sa(g, t, 100)
    toprint = {Moves.U: 'U', Moves.D: 'D', Moves.L: 'L', Moves.R: 'R'}
    print(result)
    print(''.join(toprint[p] for p in path[:result]), file=stderr)
    #                                       ^
    #                                       |
    #liczba wykonanych krokow potrzebnych do dotarcia do celu, pozniejsze ruchy nie maja znaczenia