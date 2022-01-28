#Filip Rurak
#l1z3

#funkcja f porownujaca cechy liczb oraz sprawdzajaca delte pomiedzy liczbami
function f(x, y, delta)
    y1 = prevfloat(y)
    x1 = nextfloat(x)
    xexp = SubString(bitstring(x1), 2:12)
    yexp = SubString(bitstring(y1), 2:12)
    if xexp != yexp
        return false
    end
    if y1 + delta != y
        return false
    end
    return true
end

#drukowanie wyniku dla przedzialu (1; 2)
println(f(Float64(1), Float64(2), 2^(-52)))

#sprawdzenie funkcji dla przedzialu (0; 1)
println(f(Float64(0), Float64(1), 2^(-52)))
println()
