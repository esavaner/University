import Data.List

f 0 = 0
f n = 2/(2 + (f (n-1)))
zad1 n = 1 + f n



zad2 n k = [xs | xs <-subsequences [1..n], length xs == k]



d 1 = [1]
d x = 1:[n | n<-[2..(x`div`2)], x`mod`n == 0] ++ [x]
checkPrime x = d x == [1,x]
zad3 n = take n [x| x<-[2..], checkPrime x]
























