#Filip Rurak
#l2z3

#importowanie biblioteki z funkcjami do obliczen na macierzach
Pkg.add("LinearAlgebra")
using LinearAlgebra

#deklaracje zmiennych podanych w zadaniu
n1 = 10
n = [5, 10, 20]
c = [1, 10, 10^3, 10^7, 10^12, 10^16]

#tworzenie macierzy o rozmiarze 1 na N zlozonej z samych jedynek
function matx(n)
    return [1 for i in 1:n]
end

#liczenie bledu wzglednego
function err(x, nx)
    a = []
    for i in x
        push!(a, abs(x[i] - nx[i]) / abs(x[i]))
    end
    return a
end

#wypisywanie wynikow dla macierzy losowej
function err_matcond()
    for j in c
        for i in n
            A = matcond(i, Float64(j))
            x = matx(i)
            B = A * x
            x1 = A \ B
            x2 = inv(A) * B
            println("n = ", i, " c = ", j, " gauss = ", err(x, x1)[1], " inv = ", err(x, x2)[1])
        end
    end
end

#wypisywanie wyniow dla macierzy hilberta
function err_hilb()
    for i = 1:n1
        A = hilb(i)
        x = matx(i)
        B = A * x
        x1 = A \ B
        x2 = inv(A) * B
        println("n = ", i, " gauss = ", err(x, x1)[1], " inv = ", err(x, x2)[1])
    end
end

println()
err_matcond()
println()
err_hilb()
println()


function matcond(n::Int, c::Float64)
# Function generates a random square matrix A of size n with
# a given condition number c.
# Inputs:
#	n: size of matrix A, n>1
#	c: condition of matrix A, c>= 1.0
#
# Usage: matcond(10, 100.0)
#
# Pawel Zielinski
        if n < 2
         error("size n should be > 1")
        end
        if c< 1.0
         error("condition number  c of a matrix  should be >= 1.0")
        end
        (U,S,V)=svd(rand(n,n))
        return U*diagm(0 =>[LinRange(1.0,c,n);])*V'
end

function hilb(n::Int)
# Function generates the Hilbert matrix  A of size n,
#  A (i, j) = 1 / (i + j - 1)
# Inputs:
#	n: size of matrix A, n>=1
#
#
# Usage: hilb(10)
#
# Pawel Zielinski
        if n < 1
         error("size n should be >= 1")
        end
        return [1 / (i + j - 1) for i in 1:n, j in 1:n]
end
