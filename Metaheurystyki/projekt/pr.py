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
    vertical = 2
    horizontal = 3
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

            if self.maze[y][x] == self.vertical and move in (Moves.U, Moves.D)
                continue

            if self.maze[y][x] == self.horizontal and move in (Moves.L, Moves.P)
                continue

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


#tworzenie sasiada aktualnej sciezki poprzez zamiane 2 pol
def neighbour(route):
    r = route.copy()
    if len(r) > 1:
        i = random.randint(0, len(r) - 1)
        j = random.randint(0, len(r) - 1)
        r[i], r[j] = r[j], r[i]

    return r 


#glowna funkcja symulowanego wyzarzania
def find(g: Grid, t, T):
    minpath = g.path()
    minimum = g.cost(minpath)
    t0 = time.time()

    #dopoki nie przekroczy czasu
    while time.time() - t0 < t:

        #zmiana sciezki
        newpath = neighbour(oldpath)
        newResult = g.cost(newpath)

        #jesli nowy winik jest mniejszy niz aktualne minimum
        if newResult < minimum:
            minpath, minimum = newpath, newResult

    return (minimum, minpath)


if __name__ == "__main__":
    line = input()
    t, n, m = map(int, line.split())
    maze = [[int(symbol) for symbol in input()] for _ in range(n)]
    g = Grid(maze, n*m)
    result, path = find(g, t, 100)
    toprint = {Moves.U: 'U', Moves.D: 'D', Moves.L: 'L', Moves.R: 'R'}
    print(result)
    print(''.join(toprint[p] for p in path[:result]), file=stderr)