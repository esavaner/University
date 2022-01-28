#Filip Rurak
#l3z5

#import funkcji
include("./functions.jl")

#deklaracja e
e = MathConstants.e

#funkcja f podana w zadaniu
function f(x::Float64)
    return  e^x - 3*x
end

#drukowanie wynikow dla roznych przedzialow
println(mbisekcji(f, 0.0, 1.0, 10^(-4), 10^(-4)))
println(mbisekcji(f, 1.0, 2.0, 10^(-4), 10^(-4)))
println(mbisekcji(f, 0.0, 2.0, 10^(-4), 10^(-4)))
println()
