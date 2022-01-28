######
#
# uruchamiac poleceniem 'python3 jpegls.py nazwa_pliku'
#
######

import sys
from math import log
from collections import defaultdict


####################################################
#definicje schematów

def p1(n, w, nw):
    return w

def p2(n, w, nw):
    return n

def p3(n, w, nw):
    return nw

def p4(n, w, nw):
    return n + w - nw

def p5(n, w, nw):
    return n + (w - nw)/2

def p6(n, w, nw):
    return w + (n - nw)/2

def p7(n, w, nw):
    return (n + w)/2

def pn(n, w, nw):
    if nw >= max(w, n):
        return min(w, n)
    elif nw <= min(w, n):
        return max(w, n)
    
    return w + n - nw

def ns(n, w, nw):
    return Pixel(pn(n.r, w.r, nw.r), pn(n.g, w.g, nw.g), pn(n.b, w.b, nw.b))


schemes = [p1, p2, p3, p4, p5, p6, p7, ns]

####################################################


#klasa pixel zawierajaca dane RGB
class Pixel:
    def __init__(self, r=0, g=0, b=0):
        self.r = r
        self.g = g
        self.b = b

    def __mod__(self, num):
        return Pixel(self.r % num, self.g % num, self.b % num)

    def __truediv__(self, num):
        return Pixel(self.r / num, self.g / num, self.b / num)

    def __sub__(self, other):
        return Pixel(self.r - other.r, self.g - other.g, self.b - other.b)

    def __add__(self, other):
        return Pixel(self.r + other.r, self.g + other.g, self.b + other.b)


#tworzenie macierzy z tablicy jednowymiarowej
def getMap(mat, x, y):
    bmap = []
    for j in range(y):
        row = []
        for i in range(3*x*j, 3*x*(j + 1), 3):
            row.append(Pixel(mat[i], mat[i + 1], mat[i + 2]))

        bmap.insert(0, row)

    return bmap


#klasa entropy zawierajaca jeden schemat i entropie kolorow dla tego schematu
class entropy:
    def __init__(self, name, bmap):
        self.name = name
        self.bmap = bmap
        self.all = self.entr(self.bmap, 'all')
        self.r = self.entr(self.bmap, 'r')
        self.g = self.entr(self.bmap, 'g')
        self.b = self.entr(self.bmap, 'b')

    def entr(self, bmap, color):
        symbols = defaultdict(int)
        length = 0

        #tworzenie slownika symboli
        for row in bmap:
            for pixel in row:
                length += 1
                if color == 'r' or color == 'all':
                    symbols[pixel.r] += 1
                if color == 'g' or color == 'all':
                    symbols[pixel.g] += 1
                if color == 'b' or color == 'all':
                    symbols[pixel.b] += 1

        #jesli byly wliczane wszystkie kolory, to dlugosc danych musi byc 3-krotnie wieksza
        if color == 'all':
            length = 3*length


        #liczenie entropii
        entropy = 0.0
        for s in symbols.values():
            entropy += s * (log(s, 2) - log(length, 2))
        entropy /= -(length)

        return entropy


#kodowanie jpeg ls
def jpegls(bmap, scheme):
    encoded = []
    for i, row in enumerate(bmap):
        encrow = []
        for j, pixel in enumerate(row):

            #jesli jest to pierwszy rzad, to nie ma 'polnocnych' pikseli
            if i == 0:
                n = Pixel(0, 0, 0)
            else:
                n = bmap[i-1][j]

            #jesli jest to lewa kolumna, to nie ma 'zachodnich' pikseli
            if j == 0:
                w = Pixel(0, 0, 0)
            else:
                w = bmap[i][j-1]

            #jesli jest to lewa kolumna lub pierwszy rzad, to nie ma 'polnocno-zachodnich' pikseli
            if i == 0 or j == 0:
                nw = Pixel(0, 0, 0)
            else:
                nw = bmap[i-1][j-1]

            #kodowanie jako liczby 8-bitowe
            encrow.append((pixel - scheme(n, w, nw))%256)

        encoded.append(encrow)
    return encoded


#glowna funkcja programu
def test(argv):

    #otwieranie pliku
    f = open(argv[1], 'rb')
    image = f.read()

    #liczenie szerokosci i wysokosci obrazu
    x = image[12] + image[13]*256
    y = image[14] + image[15]*256

    #tworzenie bitmapy z tablicy jednowymiarowej
    bmap = getMap(image[18:-26], x, y)

    #entropia dla oryginalu
    org = entropy("original", bmap)
    print("\nScheme:", org.name, "entropy:", org.all, "R:", org.r, "G:", org.g, "B:", org.b, "\n")

    minAll, minR, minG, minB = org, org, org, org

    #entropie dla kazdego schematu
    for i, scheme in enumerate(schemes, 1):
        e = entropy(str(i), jpegls(bmap, scheme))
        print("Scheme:", e.name, "entropy:", e.all, "R:", e.r, "G:", e.g, "B:", e.b)

        #liczenie optymalnego wyniku dla kazdego koloru
        if e.all < minAll.all:
            minAll = e
        if e.r < minR.r:
            minR = e
        if e.g < minG.g:
            minG = e
        if e.b < minB.b:
            minB = e

    print("Optimal normal scheme:", minAll.name, ", ", minAll.all)
    print("Optimal red scheme:", minR.name, ", ", minR.r)
    print("Optimal green scheme:", minG.name, ", ", minG.g)
    print("Optimal blue scheme:", minB.name, ", ", minB.b)

if __name__ == "__main__":
    test(sys.argv)