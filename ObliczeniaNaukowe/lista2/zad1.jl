#Filip Rurak
#l2z1

#tablice x i y
x = [2.718281828, -3.141592654, 1.414213562, 0.5772156649, 0.3010299957]
y = [1486.2497, 878366.9879, -22.37492, 4773714.647, 0.000185049]

#nowy x2 podany w zadaniu
x2 = [2.718281828, -3.141592654, 1.414213562, 0.577215664, 0.301029995]
x2f32 = map(x2 -> Float32(x2), x2)
x2f64 = map(x2 -> Float32(x2), x2)

#mapowanie x i y na float32
xf32 = map(x -> Float32(x), x)
yf32 = map(x -> Float32(x), y)

#mapowanie x i y na float64
xf64 = map(x -> Float64(x), x)
yf64 = map(x -> Float64(x), y)

#funkcja sumujaca w przod
function f1(x::Vector, y::Vector)
    s=0
    for i=1:length(x)
        s+= x[i] * y[i]
    end
    return s
end

#funkcja sumujaca w tyl
function f2(x::Vector, y::Vector)
    s=0
    for i=length(x):-1:1
        s+= x[i] * y[i]
    end
    return s
end

#funkcja sumujaca od najwiekszego do najmniejszego
function f3(x::Vector, y::Vector)
    xy=[]
    for i=length(x):-1:1
        push!(xy, x[i] * y[i])
    end
    positive = sum(sort(filter(x -> x>=0, xy), rev=true))
    negative = sum(sort(filter(x -> x<0, xy)))
    return positive + negative
end

#funkcja sumujaca od najmniejszego do najwiekszego
function f4(x::Vector, y::Vector)
    xy=[]
    for i=length(x):-1:1
        push!(xy, x[i] * y[i])
    end
    positive = sum(sort(filter(x -> x>=0, xy)))
    negative = sum(sort(filter(x -> x<0, xy), rev=true))
    return positive + negative
end

#lista funkcji
flist = [f1, f2, f3, f4]

#drukowanie wynikow funkcji dla roznych typow
foreach(f -> println("Float32 ", f, ": ", f(xf32, yf32)), flist)
println()
foreach(f -> println("Float64 ", f, ": ", f(xf64, yf64)), flist)
println()

#drukowanie funkcji dla nowego x2
foreach(f -> println("Float32 (x2) ", f, ": ", f(x2f32, yf32)), flist)
println()
foreach(f -> println("Float64 (x2) ", f, ": ", f(x2f64, yf64)), flist)
println()
