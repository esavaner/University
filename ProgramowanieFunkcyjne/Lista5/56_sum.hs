suml xs = foldl (+) 0 (map f (zip xs [1..]))
f (ak, k) =  ak * (-1)^(1 + k)