
def quicksort(l):
    #lista musi miec co najmniej 2 elementy aby mozna bylo wyznaczyc pierwszy i reszte
    if len(l) <= 1:
        return l

    #ustawienie pierwszego elementu listy i reszty
    x = l[0]
    xs = l[1:]

    #lewy quicksort dla mniejszych elementow i prawy dla wiekszych rownych
    left = quicksort(list(filter(lambda y: y < x, xs)))
    right = quicksort(list(filter(lambda y: y >= x, xs)))
    return left + [x] + right

    #ladniejsze rozwiazanie, ale bez funkcji filter
    #return quicksort([y for y in xs if y < x]) + [x] + quicksort([y for y in xs if y >= x])

if __name__ == "__main__":
    print(quicksort([9, 2, 7, -4, 0, 15]))