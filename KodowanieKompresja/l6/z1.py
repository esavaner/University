import sys


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



def decode(bmap):

    #pierwszy wyraz bedzie taki sam
    result = [bmap[0]]
    for i, pixel in enumerate(bmap[1:]):

        #tworzenie piksela przez dodawanie aktualnej wartosci do poprzedniego wyrazu + zabezpieczenia
        r = max(min(pixel[0] + result[i][0], 255), 0)
        g = max(min(pixel[1] + result[i][1], 255), 0)
        b = max(min(pixel[2] + result[i][2], 255), 0)
        result.append([r, g, b])

    return result



def encode(bmap, k):

    #tworzenie zbiorow kwantyzacji
    interval = getIntervals(k)

    #pierwszy wyraz bedzie taki sam
    result = [bmap[0]]
    for i, pixel in enumerate(bmap[1:]):

        #kwantyzacja poprzedniego wyrazu
        dr, dg, db = bmap[i][0], bmap[i][1], bmap[i][2]
        for intv in interval:
            if dr >= intv[0] and dr <= intv[-1]:
                dr = int((intv[0] + intv[-1])/2)

            if dg >= intv[0] and dg <= intv[-1]:
                dg = int((intv[0] + intv[-1])/2)

            if db >= intv[0] and db <= intv[-1]:
                db = int((intv[0] + intv[-1])/2)

        #zapisywanie roznicy pomiedzy aktualnym pikselem a skwantyfikowanym poprzednim wyrazem
        r = pixel[0] - dr 
        g = pixel[1] - dg 
        b = pixel[2] - db
        result.append([r, g, b])


    return result


def test(argv):
    infile, outfile, k = argv[1:]
    with open(infile, 'rb') as f1:
        image = f1.read()

    #liczenie szerokosci i wysokosci obrazu
    x = image[12] + image[13]*256
    y = image[14] + image[15]*256

    #tworzenie bitmapy z tablicy jednowymiarowej
    old = getMap(image[18:-26], x, y)


    #kodowanie i dekodowanie
    new = decode(encode(old, k))

    #zapisywanie nowego pliku
    mat  = getImage(new)
    ext = image[:18] + bytearray(mat) + image[-26:]
    with open(outfile, 'wb') as f2:
        f2.write(ext)

if __name__ == "__main__":
    test(sys.argv)