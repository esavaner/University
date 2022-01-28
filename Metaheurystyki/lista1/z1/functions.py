import math
import random
import time


############
#funkcje


def norm(x):
    return math.sqrt(sum(xi**2 for xi in x))

def prod(x):
    result = 1
    for i, xi in enumerate(x):
        result *= math.cos(xi/math.sqrt(i+1))
    return result

def happycat(x):
    return ((norm(x)**2 - 4)**2)**(1/8) + \
        (1/4)*((1/2)*(norm(x)**2) + sum(xi for xi in x)) + (1/2)

def griewank(x):
    return 1.0 + sum((xi**2)/4000 for xi in x) - prod(x)

##########


#losowy skok
def jump(x, r):
    return tuple(xi + random.uniform(-r, r) for xi in x)


#minimaluizacja
def find(t, f):
    x0 = tuple(random.uniform(-5, 5) for i in range(4))
    t1 = time.time()
    while time.time() - t1 < t:
        x = jump(x0, 0.5)
        if f(x) < f(x0):
            x0 = x
    return (*x0, f(x0))