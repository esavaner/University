import sys
from io_ import read, write


#lista kodow do dekodowania
codes = ['00000000', '00011110', '00101101', '00110011',
         '01001011', '01010101', '01100110', '01111000',
         '10000111', '10011001', '10101010', '10110100',
         '11001100', '11010010', '11100001', '11111111',
        ]


err = 0
def decode(data):
    #dla kazdego kodu
    for c in codes:
        diff = 0
        for b1, b2 in zip(data, c):
            #jesli aktualnie sprawdzany bajt rozni sie od ktoregos kodu
            if b1 != b2:
                diff += 1

        #jesli jest 0 lub 1 roznica
        if diff <= 1:
            return c[2] + c[4] + c[5] + c[6]

        #jesli jest 2 lub wiecej roznic
        elif diff == 2:
            global err
            err += 1
            return '0000'

    return '0000'


def test(argv):
    #czytanie pliku
    infile, outfile = argv[1:]
    data = read(infile)

    #dekodowanie
    dec = ''.join(decode(data[i:i+8]) for i in range(0, len(data), 8))

    #zamiana na bajty
    result = bytes(int(dec[i:i+8], 2) for i in range(0, len(dec), 8))

    print('Dwa bledy znaleziono', err, 'razy')

    #zapisywanie do pliku
    write(outfile, result)


if __name__ == '__main__':
    test(sys.argv)