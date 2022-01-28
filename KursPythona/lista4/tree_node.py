import random

#obiek node, ktory przechowuje wartosc liscia i jego potomkow
class Node(object):
    def __init__(self, data, children):
        self.data = data
        self.children = children


def tree(n):
    if n <= 0:
        return

    #nadanie losowej wartosci liscia
    data = random.randint(-10, 10)
    if n == 1:
        return Node(data, [])

    #stworzenie losowej liczby potomkow danego liscia
    child_count = random.randint(1, 5)
    
    #dodaje child_counnt-1 nowych potomkow o losowej dlugosci, a nastepnie dodaje nowego potomka o dlugosci n-1 tak, aby drzewo mialo dlugosc n
    children = [tree(random.randint(1, n-1)) for c in range(child_count-1)]
    children.append(tree(n-1))

    return Node(data, children)


def dfs(t):
    if t is None:
        return

    #zwraca wartosc liscia
    yield t.data

    #wykonuje dfs dla kazdego potomka danego liscia
    for c in t.children:
        yield from dfs(c)


#lekka modyfikacja bfs z poprzedniego zadania
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
            yield leaf.data
            for c in leaf.children:
                next_line.append(c)

        #zamienia drzewo na nastepna linie lisci
        t = next_line.copy()

t = tree(4)
print(t)
print(list(dfs(t)))
print()
print(list(bfs(t)))