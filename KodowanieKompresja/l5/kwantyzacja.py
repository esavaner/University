import sys
from math import pow


#funkcja liczaca odleglosci dla dwoch list
def sq(x, y):
    return sum(pow(xi - yi, 2) for xi, yi in zip(x, y))


#blad sredniokwadratowy
def meanSqErr(old, new):
    return (1/len(old)/len(old[0])) * sum([sq(old[i], new[i]) for i in range(len(old))])


#stosunek sygnalu do szumu
def snr(old, merr):
    if merr == 0:
        return "none (mean squared error equals 0)"
    return ((1/len(old)/len(old[0])) * sum(sum(pow(a, 2) for a in b) for b in old)) / merr


#tworzenie listy pikseli z ciaglej tablicy
#[p1.r, p1.g, p1.b, p2.r, p2.g, p2.b, p3.r...] -> [[p1.r, p1.g, p1.b], [p2.r, p2.g, p2.b], [p3.r...]]
def getMap(mat, x, y):
    bmap = []
    for i in range(x*y):
        bmap.append([mat[i*3], mat[i*3 + 1], mat[i*3 + 2]])
    return bmap


#tworzenie ciaglej tablicy z listy pikseli
#[[p1.r, p1.g, p1.b], [p2.r, p2.g, p2.b], [p3.r...]] -> [p1.r, p1.g, p1.b, p2.r, p2.g, p2.b, p3.r...]
def getImage(bmap):
    mat = []
    for pixel in bmap:
        mat.extend([pixel[0], pixel[1], pixel[2]])
    return mat


#tworzenie przedialow na podstawie ustalonej liczby bitow na piksel
def getIntervals(bit):

    #koncowa liczba przedzialow
    rows = int(pow(2, int(bit)))
    result = []
    i = 0
    for _ in range(rows):
        row = []

        #maksymalna liczba przedzialow to 256
        for _ in range(int(256/rows)):
            row.append(i)
            i += 1

        result.append(row)
    return result


#tworzenie nowej tablicy pikseli na podstawie przedzialow kwantyzacji
def encode(bmap, bBit, gBit, rBit):

    #tworzenie przedzialow kwantyzacji dla kazdego koloru
    rInterval = getIntervals(rBit)
    gInterval = getIntervals(gBit)
    bInterval = getIntervals(bBit)

    result = []
    for pixel in bmap:

        #sprawdzanie gdzie znajduja sie kolory pikseli
        for intv in rInterval:
            if pixel[0] >= intv[0] and pixel[0] <= intv[-1]:
                r = int((intv[0] + intv[-1])/2)

        for intv in gInterval:
            if pixel[1] >= intv[0] and pixel[1] <= intv[-1]:
                g = int((intv[0] + intv[-1])/2)

        for intv in bInterval:
            if pixel[2] >= intv[0] and pixel[2] <= intv[-1]:
                b = int((intv[0] + intv[-1])/2)

        result.append([r, g, b])
    return result


def test(argv):
    infile, outfile, rBit, gBit, bBit = argv[1:]
    with open(infile, 'rb') as f1:
        image = f1.read()

    #liczenie szerokosci i wysokosci obrazu
    x = image[12] + image[13]*256
    y = image[14] + image[15]*256

    #tworzenie bitmapy z tablicy jednowymiarowej
    old = getMap(image[18:-26], x, y)
    new = encode(old, rBit, gBit, bBit)

    #liczenie osobnych tablic kolorow na potrzeby merr i snr
    rOldArr, gOldArr, bOldArr = [], [], []
    rNewArr, gNewArr, bNewArr = [], [], []
    for o, n in zip(old, new):
        rOldArr.append([o[2]])
        rNewArr.append([n[2]])
        gOldArr.append([o[1]])
        gNewArr.append([n[1]])
        bOldArr.append([o[0]])
        bNewArr.append([n[0]])

    #liczenie i wypisywanie bledow sredniokwadratowych
    merr = meanSqErr(old, new)
    merrRed = meanSqErr(rOldArr, rNewArr)
    merrGreen = meanSqErr(gOldArr, gNewArr)
    merrBlue = meanSqErr(bOldArr, bNewArr)

    print("mse   =", merr)
    print("mse(r)=", merrRed)
    print("mse(g)=", merrGreen)
    print("mse(b)=", merrBlue)


    #liczenie i wypisywanie stosunku sygnalu do szumu
    print("SNR   =", snr(old, merr))
    print("SNR(r)=", snr(rOldArr, merrRed))
    print("SNR(g)=", snr(gOldArr, merrGreen))
    print("SNR(b)=", snr(bOldArr, merrBlue))


    #zapisywanie calego obrazu do pliku
    mat  = getImage(new)
    ext = image[:18] + bytearray(mat) + image[-26:]
    with open(outfile, 'wb') as f2:
        f2.write(ext)

if __name__ == "__main__":
    test(sys.argv)