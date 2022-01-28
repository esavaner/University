#Filip Rurak
#l1z2

#f - funkcja wyliczajaca epsilon wedlug wzoru
#t - typ dla jakiego ma byc policzony epsilon
#types - tablica typow

function f(t::Type)
    abs(3(t(4/3)-1)-1)
end

types=[Float16, Float32, Float64]

#porownanie wartosci wyrazenia i funkcji eps()
foreach(t->println(t, ", Kahan: ", f(t), " vs eps: ", eps(t)), types)
println()
