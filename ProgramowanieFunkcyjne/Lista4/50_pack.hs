f [] [] acc = acc
f xs [] acc = acc ++ [xs]
f [] (x:xs) acc = f [x] xs acc

f current (x:xs) acc =
  if (head current == x) then
    f (x:current) xs acc
  else
    acc ++ (f [x] xs [current])


pack [] = []
pack (x:xs) = f [] (x:xs) []