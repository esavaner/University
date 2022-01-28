#Filip Rurak
#lista 5

module blocksys
    include("io.jl")
    include("matrixgen.jl")
    import SparseArrays
    import LinearAlgebra

    export getB, doGauss, doGaussChoice, doGaussLU, doGaussChoiceLU, LU, ChoiceLU

    function getB(A::SparseArrays.SparseMatrixCSC{Float64, Int64}, n::Int64, l::Int64) :: Vector{Float64}
        b = zeros(Float64, n)
        for i = 1:n
			from_col = convert(Int64, max(l * floor((i-1) / l) - 1, 1))
			last_col = convert(Int64, min(l + l * floor((i-1) / l), n))
			for j in from_col : last_col
				b[i] += A[j, i]
			end
            if (i + l <= n)
				b[i] += A[i + l, i]
			end
        end
        return b
    end

    function doGauss(A::SparseArrays.SparseMatrixCSC{Float64, Int64}, b::Vector{Float64}, n::Int64, l::Int64)
		Ap = copy(A)
		bp = copy(b)
		for k = 1:n-1
			row = convert(Int64, min(l + l * floor((k+1) / l), n))
			col = convert(Int64, min(k + l, n))
			for i = k+1:row
				z = Ap[k, i] / Ap[k, k]
				Ap[k, i] = 0
				for j = k+1:col
					Ap[j, i] -= z * Ap[j, k]
				end
				bp[i] -= z * bp[k]
			end
		end
		x = Array{Float64}(undef, n)
		for i = n:-1:1
			sum = 0.0
			for j = i+1:min(n, i+l)
				sum += Ap[j, i] * x[j]
			end
			x[i] = (bp[i]-sum) / Ap[i, i]
		end
		return x
    end

    function doGaussChoice(A::SparseArrays.SparseMatrixCSC{Float64, Int64}, b::Vector{Float64}, n::Int64, l::Int64)
		Ap = copy(A)
		bp = copy(b)
		p = collect(1:n)
		for k = 1:n-1
			row = convert(Int64, min(l + l*floor((k+1) / l), n))
			col = convert(Int64, min(2*l + l*floor((k+1) / l), n))
			for i = k+1:row
				maxIndex = k
				maxColVal = abs(Ap[k, p[k]])
				for x = i:row
					if (abs(Ap[k, p[x]]) > maxColVal)
						maxIndex = x;
						maxColVal = abs(Ap[k, p[x]])
					end
				end
				p[maxIndex], p[k] = p[k], p[maxIndex]
				z = Ap[k, p[i]] / Ap[k, p[k]]
				Ap[k, p[i]] = 0
				for j = k+1:col
					Ap[j, p[i]] -= z * Ap[j, p[k]]
				end
				bp[p[i]] -= z * bp[p[k]]
			end
		end
		x = Array{Float64}(undef, n)
		for i = n:-1:1
			sum = 0.0
			col = convert(Int64, min(2*l + l*floor((p[i]+1) / l), n))
			for j = i+1:col
				sum += Ap[j, p[i]] * x[j]
			end
			x[i] = (bp[p[i]] - sum) / Ap[i, p[i]]
		end
		return x
    end

    function LU(A::SparseArrays.SparseMatrixCSC{Float64, Int64}, n::Int64, l::Int64)
		Ap = copy(A)
		for k = 1:n-1
			row = convert(Int64, min(l + l * floor((k+1) / l), n))
			col = convert(Int64, min(k + l, n))
			for i = k+1:row
				z = Ap[k, i] / Ap[k, k]
				Ap[k, i] = z
				for j = k+1:col
					Ap[j, i] -= z * Ap[j, k]
				end
			end
		end
		return Ap
    end

	function doGaussLU(A::SparseArrays.SparseMatrixCSC{Float64, Int64}, b::Vector{Float64}, n::Int64, l::Int64)
		Ap = LU(A, n, l)
		bp = copy(b)
		z = Array{Float64}(undef, n)
		for i = 1:n
			sum = 0.0
			col = convert(Int64, max(l * floor((i-1) / l) - 1, 1))
			for j = col:i-1
				sum += Ap[j, i] * z[j]
			end
			z[i] = bp[i] - sum
		end
		x = Array{Float64}(undef, n)
		for i = n:-1:1
			sum = 0.0
			col = min(n, i+l)
			for j = i+1:col
				sum += Ap[j, i] * x[j]
			end
			x[i] = (z[i] - sum) / Ap[i, i]
		end
		return x
	end

    function ChoiceLU(A::SparseArrays.SparseMatrixCSC{Float64, Int64}, n::Int64, l::Int64)
		Ap = copy(A)
		p = collect(1:n)
		for k = 1:n-1
			row = convert(Int64, min(l + l * floor((k+1) / l), n))
			col = convert(Int64, min(2*l + l*floor((k+1) / l), n))
			for i = k+1:row
				maxIndex = k
				maxColVal = abs(Ap[k, p[k]])
				for x = i:row
					if (abs(Ap[k, p[x]]) > maxColVal)
						maxIndex = x;
						maxColVal = abs(Ap[k, p[x]])
					end
				end
				p[maxIndex], p[k] = p[k], p[maxIndex]
				z = Ap[k, p[i]] / Ap[k, p[k]]
				Ap[k, p[i]] = z
				for j = k+1:col
					Ap[j, p[i]] -= z * Ap[j, p[k]]
				end
			end
		end
		return Ap, p
    end

    function doGaussChoiceLU(A::SparseArrays.SparseMatrixCSC{Float64, Int64}, b::Vector{Float64}, n::Int64, l::Int64)
		LU = ChoiceLU(A, n ,l)
		Ap = LU[1]
		p = LU[2]
		bp = copy(b)
		z = Array{Float64}(undef, n)
		for i = 1:n
			sum = 0.0
			col = convert(Int64, max(l * floor((p[i]-1) / l) - 1, 1))
			for j = col:i-1
				sum += Ap[j, p[i]] * z[j]
			end
			z[i] = bp[p[i]] - sum
		end
		x = Array{Float64}(undef, n)
		for i = n:-1:1
			sum = 0.0
			col = convert(Int64, min(2*l + l*floor((p[i]+1) / l), n))
			for j = i+1:col
				sum += Ap[j, p[i]] * x[j]
			end
			x[i] = (z[i] - sum) / Ap[i, p[i]]
		end
		return x
    end

end
