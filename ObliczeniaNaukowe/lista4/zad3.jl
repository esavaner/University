#Filip Rurak
#l4z3

"""
funkcja obliczajaca wspolczynniki wielomianu interpolacyjnego

dane wejsciowe:
    x - wektor dlugosci n+1 zawierajacy wezly x0...xn
    fx - wektor dlugosci n+1 zawierajacy ilorazy roznicowe

dane wyjsciowe:
    a - wektor dlugosci n+1 zawierajacy obliczone
        wspolczynniki postaci naturalnej
"""
function naturalna(x::Vector{Float64}, fx::Vector{Float64})
    l = length(x)
    a = zeros(l)
    a[l] = fx[l]
    for i in l-1:-1:1
        a[i] = fx[i] - a[i+1] * x[i]
        for j in i+1:l-1
            a[j] = a[j] - a[j+1] * x[i]
        end
    end
    return a
end
