delete x z = [a | a <- z, a /= x]
perm [] = [[]]
perm z = [x:ys | x <-z, ys <- perm(delete x z)]


