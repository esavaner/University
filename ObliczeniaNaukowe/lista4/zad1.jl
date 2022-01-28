#Filip Rurak
#l4z1

"""
funkcja obliczajaca ilorazy roznicowe

dane wejsciowe:
    x - wektor dlugosci n+1 zawierajacy wezly x0...xn
    f - wektor dlugosci n+1 zawierajacy wartosci
        interpolowanej funkcji w wezlach

dane wyjsciowe:
    fx - wektor dlugosci n+1 zawierajacy oblicone
        ilorazy roznicowe
"""
function ilorazyRoznicowe(x::Vector{Float64}, f::Vector{Float64})
    fx = copy(f)
    l = length(f)
    for i in 2:l
        for j = l:-1:i
            fx[j] = (fx[j] - fx[j-1])/(x[j] - x[j-i+1])
        end
    end
    return fx
end
