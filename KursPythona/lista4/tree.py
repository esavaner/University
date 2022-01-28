import random

def tree(n):
    #jesli drzewo lub potomek nie ma nastepnych potomkow to zwraca None
    if n <= 0:
        return None

    #tworzenie losowej wartosci wierzcholka
    data = random.randint(-10, 10)

    #tworzenie potomkow dla danego liscia
    left = tree(n-1)
    right = tree(random.randint(0, n-1))

    return [str(data), left, right]


#lekka modyfikacja zadania 2 z listy 3
def dfs(t):
    if t is None:
        return
    for item in t:
        
        #jesli element nie jest pusty
        if item is not None:

            #jesli element jest lista to dla kazdego jego podelementu ponownie wykonac dfs
            if isinstance(item, list):
                for nitem in dfs(item):
                    yield nitem

            #w przeciwnym przypadku zwrocic go
            else:
                yield item

def bfs(t):
    if t is None:
        return

    #podniesienie poziomiu drzewa
    t = [t]
    while len(t) > 0:

        #tworzenie nowej linii do przejscia
        next_line = []

        #przy kazdym przejsciu przez drzewo usuwa wszystkie liscie na tym samym poziomie i tworzy poziom z ich potomkow
        for leaf in t:
            if leaf == None:
                continue
            yield leaf[0]
            next_line.append(leaf[1])
            next_line.append(leaf[2])

        #zamienia drzewo na nastepna linie lisci
        t = next_line.copy()


t = tree(4)
print(t)
print(list(dfs(t)))
print()
print(list(bfs(t)))