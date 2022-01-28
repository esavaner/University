#Filip Rurak
#l4z2

"""
funkcja obliczajaca wartosci wielomianu interpolacyjnego

dane wejsciowe:
    x - wektor dlugosci n+1 zawierajacy wezly x0...xn
    fx - wektor dlugosci n+1 zawierajacy ilorazy roznicowe
    t - punkt dla ktorego liczona bedzie wartosc wielomianu

dane wyjsciowe:
    nt - wartosc wielomianu w punkcie t
"""
function warNewton(x::Vector{Float64}, fx::Vector{Float64}, t::Float64)
    l = length(x)
    nt = fx[l]
    for i in l-1:-1:1
        nt = fx[i] + (t-x[i]) * nt
    end
    return nt
end
