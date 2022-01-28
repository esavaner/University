import numpy as np


#miasto startowe ustawione na 0
START = 0


class TSP:
    def __init__(self, grid, size):
        self.grid = grid
        self.size = size


    #koszt dla danej sciezki
    def cost(self, path):
        c = 0
        prev = path[0]
        for city in path[1:]:
            if self.grid[prev][city] == -1:
                return float('Inf')
            c += self.grid[prev][city]
            prev = city
        return c


    #poczatkowa losowa sciezka
    def randomPath(self):
        arr = [i for i in range(self.size) if i != START]
        np.random.shuffle(arr)
        return [START] + arr + [START]
