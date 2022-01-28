f = \x -> x * x
g = \y -> f (f y)
h = g . g

h' = \x -> x ^ 16