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



def test(argv):
    infile, outfile = argv[1:]
    with open(infile, 'rb') as f1:
        image1 = f1.read()

    with open(outfile, 'rb') as f2:
        image2 = f2.read()

    #liczenie szerokosci i wysokosci obrazu
    x = image1[12] + image1[13]*256
    y = image1[14] + image1[15]*256

    #tworzenie bitmapy z tablicy jednowymiarowej
    old = getMap(image1[18:-26], x, y)
    new = getMap(image2[18:-26], x, y)

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


if __name__ == "__main__":
    test(sys.argv)