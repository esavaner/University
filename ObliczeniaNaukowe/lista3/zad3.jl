#Filip Rurak
#l3z3

"""
funkcja znajdujaca miejsce zerowe metoda Newtona

dane wejsciowe:
    f - dana funkcja
    a - przyblizenie poczatkowe x0
    b - przyblizenie poczatkowe x1
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
"""
function msiecznych(f, a::Float64, b::Float64, delta::Float64, epsilon::Float64, maxit::Int)
    fa = f(a)
    fb = f(b)
    for i in 1:maxit
        if abs(fa) > abs(fb)
            tmp = a
            a = b
            b = tmp

            tmp = fa
            fa = fb
            fb = tmp
        end
        s = (b - a)/(fb - fa)
        b = a
        fb = fa
        a -= fa * s
        fa = f(a)
        if abs(b - a) < delta || abs(fa) < epsilon
            return (a, fa, i, 0)
        end
    end
    return (0, 0, 0, 1)
end
