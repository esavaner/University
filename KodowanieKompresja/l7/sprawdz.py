import sys
from io_ import read, write


def test(argv):
    #czytanie plikow
    infile1, infile2 = argv[1:]
    data1 = read(infile1)
    data2 = read(infile2)

    #roznica w wielkosci plikow
    diff = abs(len(data2) - len(data1)) // 4


    #sprawdzanie czy 4-bitowe bloki sie nie roznia
    counter = 0
    for i in range(0, min(len(data1), len(data2)), 4):
        if data1[i:i+4] != data2[i:i+4]:
            counter += 1
    
    #drukowanie roznic jako roznicy wielkosci i ilosci roznych blokow
    print(diff + counter)


if __name__ == "__main__":
    test(sys.argv)