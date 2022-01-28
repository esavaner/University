ecd [] = []
ecd [x] = [x]
ecd (x:xs) = x : ecd [a | a <- xs, a /=x]