import sys
import random
import functions as fn
 
def check(arg):
    #plik wejsciowy
    infile = open(arg[1], "r")
    char = infile.read().split(' ')
    infile.close()

    #obliczanie minimum
    if int(char[1]) == 0:
        result = fn.find(int(char[0]), fn.happycat)
    else:
        result = fn.find(int(char[0]), fn.griewank)

    #plik wyjsciowy
    outfile = open(arg[2], "w")
    outfile.write(' '.join(str(r) for r in result))
    outfile.close()

if __name__ == "__main__":
    check(sys.argv)