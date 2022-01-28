import sys
import random
import tabu as tb
 
def check(arg):
    infile = open(arg[1], "r")
    char = infile.readline().split(' ')
    t = int(char[0])
    n = int(char[1])
    weights = []
    for _ in range(n):
        weights.append([int(w) for w in infile.readline().split(' ')])
    infile.close()

    result = tb.search(t, n, weights)

    outfile = open(arg[2], "w")
    outfile.write(' '.join(str(r) for r in result))
    outfile.close()

if __name__ == "__main__":
    check(sys.argv)