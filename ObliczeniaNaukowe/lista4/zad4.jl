#Filip Rurak
#l4z4

"""
funkcja obliczajaca wspolczynniki wielomianu interpolacyjnego

dane wejsciowe:
    f - zadana funkcja f
    a,b - przedzial interpolacji
    n - stopien wielomianu interpolacyjnego

dane wyjsciowe:
    - wykres wielomianu interpolacyjnego i interpolowanej funkcji
"""

using PyPlot

function rysujNnfx(f, a::Float64, b::Float64, n::Int, name="")
    s = 20

    nodes_limit = n + 1
    h = (b - a) / n
    kh = Float64(0.0)

    x = Vector{Float64}(undef, n + 1)
    y = Vector{Float64}(undef, n + 1)
    f_x = Vector{Float64}(undef, n + 1)
    args = Vector{Float64}(undef, s * nodes_limit)
    f_plot = Vector{Float64}(undef, s * nodes_limit)
    w_plot = Vector{Float64}(undef, s * nodes_limit)

    for i = 1:nodes_limit
        x[i] = a + kh
        y[i] = f(x[i])
        kh += h
    end

    f_x = ilorazyRoznicowe(x, y)
    kh = Float64(0.0)
    nodes_limit *= s
    h = (b - a) / (nodes_limit - 1)

    for i = 1:nodes_limit
        args[i] = a + kh
        w_plot[i] = warNewton(x, f_x, args[i])
        f_plot[i] = f(args[i])
        kh += h
    end

    clf()
    plot(args, f_plot, label="f(x)", linewidth=1.0)
    plot(args, w_plot, label="w(x)", linewidth=1.0)
    grid(true)
    legend(loc=2, borderaxespad=0)
    title("$name; n = $n")
    savefig("./lista4\\/plots\\plot_$(name)_$n.png")
end
