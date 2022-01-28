fibs = 1 : 1 : zipWith (+) fibs (tail fibs)
fib n = fibs !! n