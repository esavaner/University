#Filip Rurak
#l3z2

"""
funkcja znajdujaca miejsce zerowe metoda Newtona

dane wejsciowe:
    f - dana funkcja
    pf - pochodna funkcji
    x0 - przyblizenie poczatkowe
    delta - dokladnosc delta
    epsilon - dokladnosc epsilon
    maxit - maksymalna dopuszczalna liczba iteracji

dane wyjsciowe:
    r - miejsce zerowe
    v - wartosc funkcji w miejscu zerowym
    it - liczba iteracji
    err - kod bledu:
        0 - metoda zbiezna
        1 - nie osiagnieto wymaganej dokladnodci w maxit iteracji
        2 - pochodna bliska zeru
"""
function mstycznych(f, pf, x0::Float64, delta::Float64, epsilon::Float64, maxit::Int)
    v = f(x0)
    if abs(v) < epsilon
        return (x0, v, 0, 2)
    end
    for i in 1:maxit
        x1 = x0 - v/pf(x0)
        v = f(x1)
        if abs(x1 - x0) < delta || abs(v) < epsilon
            return (x1, v, i, 0)
        end
        x0 = x1
    end
    return (0, 0, 0, 1)
end
