#Filip Rurak
#l2z6

#importowanie biblioteki do rysowania funkcji
import Pkg
Pkg.add("Plots")

#definicje zmiennych podanych w zadaniu
#a bedzie tablica wynikow do rysowania funkcji
x0 = 1
c = -5
x = 40
a = []

#funkcja f podana w zadaniu jako xn
function f(n)
    if (n <= 0)
        return x0
    end
    return f(n-1)^2 + c
end

#generowanie tablicy wynikow funkcji f, dla ktorej ma byc narysowany wykres
for i = 1:x
    push!(a, f(i))
end

#wypisanie wyniku i rysowanie funkcji
println(f(x))
using Plots
plot(a)
