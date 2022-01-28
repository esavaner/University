import sys
from io_ import read, write


#sprawdzanie parzystosci
def parity(data, idx):
    counter = 0
    for i in idx:
        #jesli bit jest rowny 1 to zwieksza licznik o 1
        if data[i] == '1':
            counter += 1
    return str(counter%2)


def hamming(data):
    #sprawdzanie parzystosci dla roznych blokow
    p1 = parity(data, [0, 1, 3])
    p2 = parity(data, [0, 2, 3])
    p3 = parity(data, [1, 2, 3])
    p4 = parity(p1 + p2 + data[0] + p3 + data[1:], [0, 1, 2, 3, 4, 5, 6])

    #zwracenie sumy parzystosci
    return p1 + p2 + data[0] + p3 + data[1:] + p4


def test(argv):
    #czytanie pliku
    infile, outfile = argv[1:]
    data = read(infile)

    #kodowanie i zmiana na bajty
    result = bytes(int(hamming(data[i:i+4]), 2) for i in range(0, len(data), 4))

    #zapisywanie do pliku
    write(outfile, result)


if __name__ == "__main__":
    test(sys.argv)