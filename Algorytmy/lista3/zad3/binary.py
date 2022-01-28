import random
import time


comparisons = 0

def binarySearch(arr, l, r, v):
    global comparisons
    if r >= l: 
        mid = l + (r - l) // 2

        #jesli element jest w srodku to zwraca 1
        if arr[mid] == v:
            return 1 

        #jesli szukany element jest mniejszy od elementu w srodku
        elif arr[mid] > v: 
            comparisons += 1
            return binarySearch(arr, l, mid-1, v) 

        #jesli element jest wiekszy od elementu w srodku
        else:
            comparisons += 1
            return binarySearch(arr, mid + 1, r, v)

    #jak nie znaleziono elementu to zwraca 0
    else: 
        return 0


def test():
    for n in range(1000, 100000, 1000):

        #zerowanie porownan i tworzenie posortowanej tablicy od 1 do n+1
        global comparisons
        comparisons = 0
        arr = [i for i in range(1, n+1)]
        
        #losowanie zmiennej do szukania
        v = random.randint(1, n+1)

        #start mierzenia czasu i uruchomienie funkcji
        start = time.time()
        result = binarySearch(arr, 0, n-1, v)
        end = time.time()

        #drukowanie wynikow dla kazdego n
        #z ilosci porownan mozna wnioskowac ze algorytm dziala w czasie O(logn)
        #funkcja dziala tak krotko ze nie praktycznie nie ma roznicy w czasie
        print(n, result, comparisons, end - start)

        
print("N | wynik | porownania | czas")
test()
print("N | wynik | porownania | czas")