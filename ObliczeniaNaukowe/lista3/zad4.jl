#Filip Rurak
#l3z4

#import funkcji
include("./functions.jl")

#funkcja f podana w zadaniu
function f(x::Float64)
    return sin(x) -(x/2.0)^2.0
end

#pochodna funkcji f
function pf(x::Float64)
    return cos(x) - x/2.0
end

#drukowanie wynikow dla roznych metod
for i in 1:100000000
    mbisekcji(f, 1.5, 2.0, 10^(-5)/2, 10^(-5)/2)
end
println("Bisekcji ", mbisekcji(f, 1.5, 2.0, 10^(-5)/2, 10^(-5)/2))

for i in 1:100000000
    mstycznych(f, pf,  1.5, 10^(-5)/2, 10^(-5)/2, 100)
end
println("Stycznych ", mstycznych(f, pf,  1.5, 10^(-5)/2, 10^(-5)/2, 100))

for i in 1:100000000
    msiecznych(f, 1.0, 2.0, 10^(-5)/2, 10^(-5)/2, 100)
end
println("Siecznych ", msiecznych(f, 1.0, 2.0, 10^(-5)/2, 10^(-5)/2, 100))
