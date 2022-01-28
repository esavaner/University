import math
import random
import time


#funkcja Salomona
def salomon(x):
    s = math.sqrt(sum(i**2 for i in x))
    return float(1 - math.cos(2*math.pi*s) + 0.1*s)


#okreslanie sasiada aktualnego x poprzez losowe zwiekszanie lub zmniejszanie skladowych zmiennych
def neighbour(x):
    return tuple(i + random.random() if random.random() < 0.5 else i - random.random() for i in x)


#funkcja prawdopodobienstwa bazujaca na nowym i starym wyniku oraz temperaturze
def P(old, new, T):
    if new < old:
        return 1
    else:
        return math.exp(- (new - old)/T)


#funkcja zmiany temperatury
def temperature(T):
    return max(0.001, T*0.999)


#glowna funkcja symulowanego wyzarzania
def sa(x, t, T):
    minx, oldx = x, x
    minimum, oldResult = salomon(minx), salomon(oldx)
    t0 = time.time()

    #dopoki nie przekroczy czasu
    while time.time() - t0 < t:

        #zmiana temperatury i x na nowy
        T = temperature(T)
        newx = neighbour(oldx)
        newResult = salomon(newx)

        #zamiana wyniku na podstawie funkcji prawdopodobienstwa
        if P(oldResult, newResult, T) > random.random():
            oldx, oldResult = newx, newResult

        #jesli nowy winik jest mniejszy niz aktualne minimum
        if oldResult <= minimum:
            minx, minimum = oldx, oldResult

    return (*minx, minimum)

if __name__ == "__main__":
    line = input()
    t, x1, x2, x3, x4 = map(float, line.split())
    result = sa((x1, x2, x3, x4), t, 100)
    print(*result)