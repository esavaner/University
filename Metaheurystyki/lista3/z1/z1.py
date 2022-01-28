import math
import random
import time



population = 20



#funkcja X.S. Yang
def xsyang(x, e):
    return sum(ei * math.pow(abs(xi), 2) for i, (xi, ei) in enumerate(zip(x, e), 1))



#selekcja losowego najlepszego wyniku
def selection(results):

    #jakis poczatkowy najlepszy
    best = random.randrange(len(results))

    #5 razy szukanie lepszego wyniku
    for i in range(5):
        curr = random.randrange(len(results))
        if results[curr] < results[best]:
            best = curr
    return best



#kombinacja dwoch wynikow
def recombination(x1, x2):

    #wybieranie losowych indeksow na zamiane bitow
    i = random.randrange(len(x1))
    j = random.randrange(len(x2))

    #jesli indeks koncowy jest wiekszy od poczatkowego to nalezy je zamienic
    if j > i:
        i, j = j, i

    #zamiana bitow pierwszego na bity drugiego
    if i != j:
        for k in range(i, j):
            x1[k] = x2[k]
    return x1, x2



#mutacja bitow danego x z pewnym malym prawdopodobienstwem - tutaj 0.01
def mutation(x):
    return [position * random.gauss(1, 0.01) for i, position in enumerate(x)]



#glowna funkcja algorytmu genetycznego
#przyjmuje poczatkowy x, epsilony i maksymalnny czas
def genetic(x, e, t):

    #tworzenie poczatkowej populacji - tutaj o rozmiarze 20
    pop = []
    for _ in range(population):
        pop.append(mutation(x))

    minx = None
    minimum = float('Inf')
    t0 = time.time()

    #dopoki nie przekroczy czasu
    while time.time() - t0 < t:

        #sprawdzanie czy ktorys obiekt w populacji jest lepszy od aktualnego minimum
        results = []
        for p in pop:

            #liczenie nowego wyniku dla danego obiektu
            newResult = xsyang(p, e)
            results.append(newResult)

            #jesli nowy wynik jest lepszy to zmienia aktualne minimum
            if minx is None or newResult < minimum:
                minx = p
                minimum = newResult


        #tworzenie nowej populacji na podstawie poprzedniej
        popNew = []
        for _ in range(population//2):

            #wybieranie dwoch losowych wynikow (na podstawie ich jakosci)
            x1 = pop[selection(results)]
            x2 = pop[selection(results)]

            #przypisywanie bitow pierwszego wyniku z drugiego wyniku 
            x1p, x2p = recombination(x1, x2)

            #dodanie zmutowanych wynikow do nowej populacji
            popNew.extend([mutation(x1p), mutation(x2p)])

        #zamiana aktualnie badanej populacji na nowa
        pop = popNew

    return (*minx, minimum)



if __name__ == "__main__":
    line = list(map(float, input().split()))
    t = int(line[0])
    x = line[1:6]
    e = line[6:11]
    result = genetic(x, e, t)
    print(*result)