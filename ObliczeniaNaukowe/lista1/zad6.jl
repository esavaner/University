#Filip Rurak
#l1z6

#funkcja f podana w zadaniu
function f(x::Float64)
    return sqrt(x^2 + 1)-1
end

#funkcja g podana w zadaniu
function g(x::Float64)
    return (x^2) / (sqrt(x^2 + 1)+1)
end

#drukowanie wynikow dla roznych wartosci 
for i=1:50
    fx = f(Float64(8)^(-i))
    gx = g(Float64(8)^(-i))
    println("f(8^-", i, ")= ", fx, "   g(8^-", i, ")= ", gx)
end
println()
