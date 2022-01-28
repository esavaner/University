#Filip Rurak
#l3zfunkcje

#modul z wszystkimi funkcjami
module functions

using PyPlot

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

function warNewton(x::Vector{Float64}, fx::Vector{Float64}, t::Float64)
    l = length(x)
    nt = fx[l]
    for i in l-1:-1:1
        nt = fx[i] + (t-x[i]) * nt
    end
    return nt
end

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

function rysujNnfx(f, a::Float64, b::Float64, n::Int, name::String)
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

#export wszystkich funkcji
export ilorazyRoznicowe, warNewton, naturalna, rysujNnfx
end
