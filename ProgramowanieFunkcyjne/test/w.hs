divisors 1 = [1]
divisors x = 1:[ y | y <- [2..(x `div` 2)], x `mod` y == 0] ++ [x]
isPrime x = divisors x == [1,x]
prime n = [x| x<-[2..n], isPrime x]


w n = length [x| x<- prime n, n `mod` x ==0]
avg xs = fromIntegral(sum xs) / fromIntegral(length xs)
count m = avg(map w [1..m])

