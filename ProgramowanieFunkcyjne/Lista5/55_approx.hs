approx n = foldr (+) 0 (map f [1..n])
f xs = 1/(sil xs)

sil 0 = 1
sil n = n * sil (n - 1)