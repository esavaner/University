f x y = (\x -> (\y -> x + 2*y)) (x * y)
f a b = (\x -> (\y -> x + 2*y)) (a * b)
f a b = (\y -> a*b + 2*y)