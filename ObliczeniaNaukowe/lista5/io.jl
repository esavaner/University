#Filip Rurak
#lista 5

module io
    using SparseArrays, LinearAlgebra

    export readA, readB, printX

    function readA(s::String)
        open(s) do file
            first = split(readline(file))
            n = parse(Int64, first[1])
            l = parse(Int64, first[2])
            en = n*l + 3*(n-l)
            rows = Array{Int64}(undef, en)
            cols = Array{Int64}(undef, en)
            values = Array{Float64}(undef, en)
            i = 1
            while !eof(file)
                next = split(readline(file))
                rows[i] = parse(Int64, next[1])
                cols[i] = parse(Int64, next[2])
                values[i] = parse(Float64, next[3])
                i += 1
            end
            return (sparse(cols, rows, values), n, l)
        end
    end

    function readB(s) :: Vector{Float64}
        b::Array{Float64, 1} = []
        open("$s") do file
            first = parse(Int64, readline(file))
            while !eof(file)
                next = parse(Float64, readline(file))
                push!(b, next[1])
            end
        end
        return b
    end

    function printX(x, n::Int64, calculated::Bool, s)
        file = open("$s", "w")
        if calculated
            err = norm(ones(n) - x) / norm(x)
            println(file, err)
        end
        for i in x
            println(file, i)
        end
        close(file)
    end

end
