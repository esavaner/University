import numpy
from collections import defaultdict
from math import log

def occurence(data):
    symbols = defaultdict(numpy.int64)
    pairs = defaultdict(numpy.int64)
    first = numpy.int8(0).tobytes() #ustalenie 1 symbolu na 0
    for d in data:
        second = d  #nastepny symbol
        if first + second in pairs:
            pairs[first + second] += 1  #jak istnieje to zwiekszyc o 1
        else:
            pairs[first + second] = 1   #jak pojawia sie po raz pierwszy to ustawic na 1
        if first in symbols:
            symbols[first] += 1
        else:
            symbols[first] = 1
        first = second  #poprzedni symbol
    return symbols, pairs

if __name__ == '__main__':
    files = ['pan-tadeusz-czyli-ostatni-zajazd-na-litwie.txt', 'test1.bin', 'test2.bin', 'test3.bin']
    for f in files:
        data = [e.tobytes() for e in numpy.fromfile(f, dtype = numpy.int8)]
        symbols, pairs = occurence(data)

        centropy = numpy.float64(0.0)   #entropia warunkowa
        for s, p in pairs.items():
            first = numpy.int8(s[0]).tobytes()
            centropy += p * (log(p, 2) - log(symbols[first], 2))
        centropy /= -(len(data))

        entropy = numpy.float64(0.0)    #entropia
        for s in symbols.values():
            entropy += s * (log(s, 2) - log(len(data), 2))
        entropy /= -(len(data))

        print(f)
        print("Entropia warunkowa:", centropy)
        print("Entropia:", entropy)
        print()