sumof n = sum [x | x <- [1..n], n `mod` x == 0, x /= n]
perf n = [x | x <- [1..n], sumof x == x]