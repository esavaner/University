
def sumcol(fname):
    #otwieranie pliku
    f = open(fname, 'r')

    #dzielenie kazdej lini w pliku spacjami i sumowanie ostatniej wartosci
    return sum(int(line.split(' ')[-1]) for line in f)

if __name__ == "__main__":
    print("Calkowita liczba bajtow:", sumcol("test.txt"))