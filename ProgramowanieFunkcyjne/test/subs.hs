import Data.List
subs n k = [xs | xs <-subsequences [1..n], length xs == k]
suba n = subsequences [1..n]