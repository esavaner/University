#Filip Rurak
#l3z1

"""
funkcja znajdujaca miejsce zerowe metoda bisekcji

dane wejsciowe:
    f - dana funkcja
    a - poczatek przedzialu
    b - koniec przedzialu
    delta - dokladnosc delta
    epsilon - dokladnosc epsilon

dane wyjsciowe:
    r - miejsce zerowe
    v - wartosc funkcji w miejscu zerowym
    it - liczba iteracji
    err - kod bledu:
        0 - brak
        1 - nie zmienia znaku w przedziale
"""
function mbisekcji(f, a::Float64, b::Float64, delta::Float64, epsilon::Float64)
    u = f(a)
    v = f(b)
    e = b - a
    if sign(u) == sign(v)
        return (0, 0, 0, 1)
    end
    i = 1
    while true
        e /= 2
        c = a + e
        w = f(c)
        if abs(e) < delta || abs(w) < epsilon
            return (c, w, i, 0)
        end
        if sign(w) != sign(u)
            b = c
            v = w
        else
            a = c
            u = w
        end
        i += 1
    end
end

println(mbisekcji((x -> x), -1.0, 1.0, 10^(-3), 10^(-3))[1])
