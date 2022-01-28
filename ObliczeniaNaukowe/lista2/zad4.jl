#Filip Rurak
#l2z4

#dodanie pakietu wielomianow
#Pkg.add("Polynomials")
using Polynomials

#wspolczynniki wielomianu w postaci naturalnej
a = [2432902008176640000.0, -8752948036761600000.0,
      13803759753640704000.0, -12870931245150988800.0,
      8037811822645051776.0, -3599979517947607200.0,
      1206647803780373360.0, -311333643161390640.0,
      63030812099294896.0, -10142299865511450.0,
      1307535010540395.0, -135585182899530.0,
      11310276995381.0, -756111184500.0, 40171771630.0, -1672280820.0,
      53327946.0, -1256850.0, 20615.0, -210.0-2^-23, 1]

#pierwiastki wielomianu w postaci iloczynowej
b = [20.0, 19.0, 18.0, 17.0, 16.0, 15.0, 14.0, 13.0, 12.0, 11.0, 10.0, 9.0, 8.0, 7.0, 6.0, 5.0, 4.0, 3.0, 2.0, 1.0]

#tworzenie wielomianow
P = Poly(a)
p = poly(b)

#obliczanie pierwiastkow wielomianow
rootsP = roots(P)
rootsp = roots(p)

#drukowanie wynikow
foreach(t->println("z = ", t, " P(z) = ", polyval(P, t)), rootsP)
println()
foreach(t->println("z = ", t, " p(z) = ", polyval(p, t)), rootsp)
println()

for k = 1:20
      println("k = ", k, " |zk - k| = ", abs(rootsP[k] - k))
end
println()
