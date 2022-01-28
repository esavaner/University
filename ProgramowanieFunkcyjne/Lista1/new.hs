new _ 0 = 0
new n 1 = n
new 1 _ = 0
new n k = (new (n-1) k) + (new (n-1) (k-1))