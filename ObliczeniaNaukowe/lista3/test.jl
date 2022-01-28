#Filip Rurak
#l3ztest

#import funkcji
include("./functions.jl")

#uzycie pakietu testow
using Test


#testy dla metody bisekcji
@testset "Bisekcja" begin
  @test abs(mbisekcji((x -> x), -1.0, 1.0, 10^(-3), 10^(-3))[1]) <= 10^(-3)
  @test abs(mbisekcji((x -> x^3 + 1), -10.0, 10.0, 10^(-3), 10^(-3))[1] + 1) <= 10^(-3)
end

#testy dla metody Newtona
@testset "Newton" begin
  @test abs(mstycznych((x -> x), (x -> 1), -1.0, 10^(-3), 10^(-3), 100)[1]) <= 10^(-3)
  @test abs(mstycznych((x -> x^3 + 1), (x -> 3*x^2), -10.0, 10^(-3), 10^(-3), 100)[1] + 1) <= 10^(-3)
end

#testy dla metody siecznych
@testset "Sieczne" begin
  @test abs(msiecznych((x -> x), -1.0, 1.0, 10^(-3), 10^(-3), 100)[1]) <= 10^(-3)
  @test abs(msiecznych((x -> x^3 + 1), -2.0, 0.0, 10^(-5), 10^(-5), 100)[1] + 1) <= 10^(-5)
end
