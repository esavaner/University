f zs = 1
evenr xs = foldr (+) 0 ys
 where ys = map f [x | x <- xs, x`mod`2 == 0]
