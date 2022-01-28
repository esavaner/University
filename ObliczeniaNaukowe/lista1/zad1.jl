#Filip Rurak
#l1z1

#t - typ dla jakiego ma byc policzony epsilon/eta/max

#feps - funkcja wyliczajaca epsilon
function feps(t::Type)
    macheps = t(1)
    while 1 + macheps/2 > 1
        macheps = macheps/2
    end
    return macheps
end

#feta - funkcja wyliczajaca eta
function feta(t::Type)
    eta = t(1)
    while eta/2 > 0
        eta = eta/2
    end
    return eta
end

#fmax - funkcja wyliczajaca liczbe max
function fmax(t::Type)
    max = t(1)
    while !isinf(max*2)
        max = max *2
    end
    delta = max/2
    while !isinf(max + delta)
        max += delta
        delta = delta/2
    end
    return max
end

#tablica typow
types=[Float16, Float32, Float64]

#porownanie epsilon i funkcji eps()
foreach(t->println(t, ", macheps: ", feps(t), " vs eps(func): ", eps(t)), types)
println()

#porownanie eta i funkcji nextfloat()
foreach(t->println(t, ", eta: ", feta(t), " vs nextfloat: ", nextfloat(t(0.0))), types)
println()

#porownanie MAX i funkcji floatmax()
foreach(t->println(t, ", MAX: ", fmax(t), " vs floatmax: ", floatmax(t)), types)
println()
