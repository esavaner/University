import math
from inspect import getfullargspec
from collections import defaultdict

class overload:
    #pusty slownik na funkcje i odpowiadajace im ilosci argumentow
    functions = defaultdict(dict)

    def __init__(self, f):
        #zapobieganie przeciazaniu funkcji o innych nazwach, ale takiej samej ilosci argumentow
        self.functions = overload.functions[f.__name__]

        #zapisanie w slowniku odpowiedniej funkcji dla danych argumentow
        length = len(getfullargspec(f).args)
        self.functions[length] = f

    def __call__(self, *args, **kwargs):
        #wylowanie funkcji bazujace na argumentach
        return self.functions[len(args)](*args, **kwargs)

@overload
def norm(x, y):
    return math.sqrt(x*x + y*y)

@overload
def norm(x, y, z):
    return abs(x) + abs(y) + abs(z)


print(f"norm(2,4) = {norm(2,4)}")

print(f"norm(2,3,4) = {norm(2,3,4)}")