import Data.List
rleE xs = map (\x -> (length x, head x)) (group xs)

rleD :: Eq a => [(Int,a)] -> [a]
rleD xs = foldr f [] xs
  where
    f (1, x) r = x : r
    f (k, x) r = x : f (k-1, x) r