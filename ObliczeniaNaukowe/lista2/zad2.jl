#Filip Rurak
#l2z2

#importowanie bibliotek do rysowania funkcji
import Pkg
Pkg.add("PyPlot")
Pkg.add("Plots")

#rysowanie funkcji dla pakietu PyPlot
using PyPlot
x = range(-10,stop=50,length=1000); y = (ℯ.^x) .* log.(1 .+ ℯ.^-x);
plot(x, y)

#rysowanie funkcji dla pakietu Plots
using Plots
x = range(-10,stop=50,length=1000); y = (ℯ.^x) .* log.(1 .+ ℯ.^-x);
plot(x, y)
