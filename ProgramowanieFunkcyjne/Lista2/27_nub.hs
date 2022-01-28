nub [] = []
nub [x] = [x]
nub (x:xs) = x : nub [a | a <-xs, a /= x]