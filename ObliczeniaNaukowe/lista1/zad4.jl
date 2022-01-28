#Filip Rurak
#l1z4

#funkcja iterujaca przez przedzial (1,2) za pomoca funkcji nextfloat,
#tak by prawdziwa byla nierownosc x*(1/x) != 1
function f(t::Type)
    x=t(1)
    while x != t(2) && x < t(2)
        x = nextfloat(x)
        if t(x*t(1/x)) != t(1)
            return x
        end
    end
    return t(2)
end

println("x = ", f(Float64))
println()
