from pso import PSO
from tsp import TSP


if __name__ == "__main__":
    t, n, s, w, g1, g2 = map(float, input().split())
    t, n, s = map(int, [t, n, s])
    grid = [[int(c) for c in input().split()[:n]] for _ in range(n)]
    tsp = TSP(grid, n)
    pso = PSO(tsp, s, w, g1, g2)
    minPos, minimum = pso.test(t)
    print('\nNajkrotsza znaleziona sciezka:', minPos, 'odleglosc:', minimum)
