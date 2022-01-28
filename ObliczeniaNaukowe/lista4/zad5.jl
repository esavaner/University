#Filip Rurak
#l4z5

#import funkcji
include("functions.jl")
using .functions
n = [5, 10, 15]

foreach(i -> rysujNnfx(x -> exp(x), 0.0, 1.0, i, "e^x"), n)
foreach(i -> rysujNnfx(x -> x^2 * sin(x), -1.0, 1.0, i, "x^2sinx"), n)
