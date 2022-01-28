#Filip Rurak
#l3z6

#import funkcji
include("./functions.jl")

#deklaracja zmiennych
e = MathConstants.e
delta = 10^(-5)

#funkcja f1 podana w zadaniu
function f1(x::Float64)
    return e^(1-x) - 1
end

#funkcja f2 podana w zadaniu
function f2(x::Float64)
    return x*e^(-x)
end

#pochodna funkcji f1
function pf1(x::Float64)
    return -e^(1-x)
end

#pochodna funkcji f2
function pf2(x::Float64)
    return (-e^(-x))*(x-1)
end

#drukowanie wynikow dla f1
println(mbisekcji(f1, 0.0, 3.0, delta, delta))
println(mstycznych(f1, pf1, 0.1, delta, delta, 100))
println(msiecznych(f1, 0.1, 0.2, delta, delta, 100))
println()

#drukowanie wynikow dla f2
println(mbisekcji(f2, -1.0, 2.0, delta, delta))
println(mstycznych(f2, pf2, -0.1, delta, delta, 100))
println(msiecznych(f2, -0.2, -0.1, delta, delta, 100))
println()

#drukowanie wynikow dla podpunktow zadania
println(mstycznych(f1, pf1, 7.0, delta, delta, 1000))
println(mstycznych(f2, pf2, 1.0, delta, delta, 1000))
println(mstycznych(f2, pf2, 14.0, delta, delta, 1000))
println(mstycznych(f2, pf2, 15.0, delta, delta, 1000))
println(mstycznych(f2, pf2, 500.0, delta, delta, 1000))
println()
