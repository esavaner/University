from math import log
from huffman import ClassicCoding



#tablica plikow do uruchomiena programu

#files = ['pan-tadeusz-czyli-ostatni-zajazd-na-litwie.txt', 'test1.bin', 'test2.bin', 'test3.bin']
files = []




for f in files:
    #kodowanie i jednoczesne dekodowanie
    H = ClassicCoding(f)
    out, before, symbols = H.code()
    after = H.decode(out)

    #liczenie entropii
    entropy = 0.0
    for s in symbols.values():
        entropy += s * (log(s, 2) - log(len(before), 2))
    entropy /= -(len(before))

    print(f)
    print("Entropia:", entropy)
    print("Srednia dlugosc kodowania:", len(after)/len(before))
    print("Stopien kompresji:", len(before)*8/len(after))
    print()