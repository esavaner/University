#Filip Rurak
#l2z5

#definicje zmiennych w Float32
x = Float32(40)
p0 = Float32(0.01)
r = Float32(3)
t = Float32

#funkcja f podana w zadaniu jako pn
function f(n)
    if(t(n) <= t(0))
        return t(p0)
    end
    s = f(t(n) - t(1))
    return s + t(r) * s * (t(1) - s)
end

println(f(x))
println()

#zmiana typu zmiennych na Float64
x = Float64(40)
p0 = Float64(0.01)
r = Float64(3)
t = Float64

println(f(x))
println()
