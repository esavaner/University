import sys
import random
from io_ import read, write


def szum(p, data):
    result = ''

    #dla kazdego bitu
    for d in data:
        #dla danego p zmienia bit na przeciwny
        if random.random() <= float(p):
            d = str(int(not int(d)))
        result += d

    #zapisywanie jako ciag bajtow
    return bytes(int(result[i:i+8], 2) for i in range(0, len(result), 8))


def test(argv):
    #czytanie plikow i prawdopodobienstwa
    p, infile, outfile = argv[1:]
    data = read(infile)

    #zamiana bitow
    result = szum(p, data)

    #zapisywanie
    write(outfile, result)


if __name__ == "__main__":
    test(sys.argv)