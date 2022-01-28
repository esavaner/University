import time
import random
from tsp import TSP

class Particle:
    #definicja czastki z jej polozeniem, predkoscia oraz koszem dla jej polozenia
    def __init__(self, position, cost):
        self.pos = position
        self.bestPos = position

        self.cost = cost
        self.bestCost = cost
        self.v = self.randomVelocity()


    #ruch czastki dla zadanej nowej pozycji
    def move(self, position, cost):
        self.pos = position
        self.cost = cost
        if self.cost <= self.bestCost:
            self.bestPos = self.pos
            self.bestCost = self.cost


    #losowa predkosc poczatowa
    def randomVelocity(self):
        l = len(self.pos) - 2
        vel = []
        for _ in range(len(self.pos)//3):
            if random.random() < 0.5:
                vel.append([random.randint(1, l), random.randint(1, l)])
        return vel



class PSO:
    def __init__(self, tsp, s, w, g1, g2):
        self.tsp = tsp
        self.cost = self.tsp.cost
        self.swarmSize = s
        self.w = w
        self.g1 = g1
        self.g2 = g2
        self.swarm = []
        #tworzenie roju
        for _ in range(self.swarmSize):
            path = self.tsp.randomPath()
            cost = self.cost(path)
            p = Particle(path, cost)
            self.swarm.append(p)


    #eliminacja wyrazow predkosci z danym prawdopodobienstwem p
    def elim(self, p, seq):
        return [s for s in seq if random.random() <= p]


    #tworzenie predkosci z jaka musi sie poruszac czastka pa2, aby znalezc sie w pozycji czastki pa1 
    def sequence(self, pa1, pa2):
        path1 = pa1.copy()
        path2 = pa2.copy()
        result = []
        for i, p1 in enumerate(path1[:-1]):
            if path1 == path2:
                return result
            for j, p2 in enumerate(path2[:-1]):
                if p1 == p2 and i != j:
                    result.append([i, j])
                    path2[i], path2[j] = path2[j], path2[i]
                    break
                    
        return result


    #zmiana pozycji dla danej predkosci
    def addSeq(self, path, seq):
        result = path.copy()
        for s in seq:
            result[s[0]], result[s[1]] = result[s[1]], result[s[0]]
        return result



    #glowna funkcja testowania    
    def test(self, t):

        #znajdywanie minimum dla poczatkowych stanow czastek 
        minPos = self.tsp.randomPath()
        minimum = self.cost(minPos)
        for p in self.swarm:
            if p.cost < minimum:
                minPos = p.pos
                minimum = p.cost

        t0 = time.time()
        i = 0
        while time.time() - t0 < t:
            i += 1
            for p in self.swarm:

                #tworzenie nowej predkosci dla czastki
                seq1 = self.elim(self.g1, self.sequence(p.bestPos, p.pos))
                seq2 = self.elim(self.g2, self.sequence(minPos, p.pos))
                seq3 = self.elim(self.w, p.v)
                p.v = seq3 + seq2 + seq1

                #obliczanie nowej pozycji dla zmienionej predkosci
                newPos = self.addSeq(p.pos, p.v)

                #zmiana pozycji i kosztu czastki
                p.move(newPos, self.cost(newPos))

                #aktualzacja globalnego minimum
                if p.cost < minimum:
                    minPos = p.pos
                    minimum = p.cost
                    print('Minimalna sciezka:', minPos, 'odleglosc:', minimum, 'iteracje:', i)

        return (minPos, minimum)
