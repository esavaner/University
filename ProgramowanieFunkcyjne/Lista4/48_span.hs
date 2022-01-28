sp :: (a -> Bool) -> [a] -> ([a], [a])
sp p (xs) = 
  let
    y = takeWhile p xs
    z = dropWhile p xs
  in (y, z)