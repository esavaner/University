phi 1 = 1
phi a = length $ filter (coprime a) [1..a-1]
    where coprime a b = gcd a b == 1
eulersum n k = sum [phi x | x <- [1..n], n `mod` k == 0, x /= n]
