
def allsubsets(s):
    #jesli dlugosc listy jest rowana 0 to zwroci pusty zbior
    if len(s) == 0:
        return [[]]

    #ustawienie pierwszego elementu listy i reszty
    a = s[0]
    t = s[1:]

    #tworzenie podzbiorow reszty listy
    res = allsubsets(t)

    #mapowanie kazdego elementu z reszty na [pierwszy, kazdy element z reszty] i dodwanie tego do poprzednich podzbiorow
    return list(map(lambda b: [a, *b], res)) + res

if __name__ == "__main__":
    print(allsubsets([1, 2, 3]))