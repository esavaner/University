import hashlib
import sys
import os

#zbieranie plikow i informacji o nich
def info(path):
    hashlist = []
    for root, dirs, files in os.walk(path, topdown=True):
        for d in dirs:
            os.path.join(root, d)
        for f in files:
            #do listy dodaje sciezke pliku i jego hash
            hashlist.append((os.path.join(root, f), hashlib.md5(f.encode()).hexdigest()))
    return hashlist

#sprawdza kopie dla listy plikow w danej sciezce
def check(path):
    hashlist = info(path)
    print("---------------------------------")
    for f1, h1 in hashlist:
        #sprawdza czy pliki maja takie same hashe
        repeats = [(f2, h2) for f2, h2 in hashlist if h1 == h2]
        if len(repeats) > 1:
            for r in repeats:
                #drukuje dana sciezke i usuwa plik z listy, alby nie bylo powtorek
                hashlist.remove(r)
                print(r[0])
            print("---------------------------------")


if __name__ == "__main__":
    if len(sys.argv) < 2:
        print("Brak sciezki wejsciowjej")
    else:
        check(sys.argv[1])