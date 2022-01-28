include("blocksys.jl")
include("io.jl")
include("matrixgen.jl")
using SparseArrays


#A16 = io.readA("16\\A.txt")
#b16 = io.readB("16\\b.txt")

#A10t = io.readA("10000\\A.txt")
#b10t = io.readB("10000\\b.txt")

#A50t = io.readA("50000\\A.txt")
#b50t = io.readB("50000\\b.txt")

#function benchmark(n, l, repeats, func)
#    totalTime = 0
#    totalMemory = 0
#    for i = 1:repeats
#        matrixgen.blockmat(n, l, 10.0, "test\\A.txt")
#        A = io.readA("test\\A.txt")
#        b = blocksys.getB(A, n, l)
#        (_, time, memory) = @timed func(A, b, n, l)
#        totalTime += time
#        totalMemory += memory
#        io.printX(func(A, b, n, l), true, "test\\$func$n.txt")
#    end
#    println(n, ";",totalTime/repeats, ";", totalMemory/repeats)
#end

#for i in [16, 1000, 5000, 10000, 50000]
#    totalTime = 0
#    totalMemory = 0
#    for j = 1:10
#        matrixgen.blockmat(i, 4, 10.0, "test\\A.txt")
#        A = io.readA("test\\A.txt")
#        b = blocksys.getB(A, i, 4)
#        (_, time, memory) = @timed Array(A)\Array(b)
#        totalTime += time
#        totalMemory += memory
#    end
#    println("$i; $(totalTime/10); $(totalMemory/10)")
#end

functions = [blocksys.doGauss, blocksys.doGaussChoice, blocksys.doGaussLU, blocksys.doGaussChoiceLU]

#for j = 1:4
    #n = [16, 1000, 5000, 10000, 50000]
    #println("$j")
    #for k in n
    #    benchmark(k, 4, 30, functions[j])
    #end
#end

mA = io.readA("50000\\A.txt")
A = mA[1]
n = mA[2]
l = mA[3]
B = blocksys.getB(A, n, l)

for f in functions
    io.printX(f(A, B, n, l), n, true, "test\\$f$n.txt")
end
