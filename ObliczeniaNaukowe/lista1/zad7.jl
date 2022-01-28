#Filip Rurak
#l1z7

#funkcja f podana  w zadaniu
function f(x::Float64)
    return sin(x) + cos(3x)
end

#pochodna funkci f
function fp(x::Float64)
    return cos(x) - 3sin(3x)
end

#pochodna funkci f obliczona wedlug wzoru
function fp_rd(f::Function, x::Float64, h::Float64)
    return (f(x+h)-f(x))/h
end

fp_normal = fp(Float64(1))

#drukowanie wartoci funkcji i liczenie bledu 
for n=0:54
    h = Float64(2)^(-n)
    fp_round = fp_rd(f, Float64(1), h)
    println("Dla h= 2^-",n ," wartosc wynosi ", fp_round, " blad ", abs(fp_normal - fp_round))
end

print("Wartosc pochodnej =", fp_normal)
println()
