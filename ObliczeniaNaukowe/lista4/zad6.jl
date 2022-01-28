#Filip Rurak
#l4z6

#import funkcji
include("functions.jl")
using .functions
n = [5, 10, 15]

foreach(i -> rysujNnfx(x -> abs(x), -1.0, 1.0, i, "x"), n)
foreach(i -> rysujNnfx(x -> 1/(1+x^2), -5.0, 5.0, i, "1div(1+x^2)"), n)
