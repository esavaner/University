from bst import Bst
from rbt import Rbt
from hmap import Hmap

print('Bst sample.txt')
bst1 = Bst()
bst1.load('sample.txt')
print('Liczba elementow:', bst1.elements, '\n')

#srednia granica find bedzie wynosic O(logn) dla losowych danych
print('Szukanie out.        O(logn)')
bst1.find(bst1.root, 'out.')
print('Liczba porownan:', bst1.comparisons, '\n')

#pojawia sie blad rekursji, a sys.setrecursionlimit(1000000) u mnie nie dziala dlatego slownik jest ograniczony do 900 pierwszych znakow
print('Bst slownikBST.txt')
bst2 = Bst()
bst2.load('slownikBST.txt')
print('Liczba elementow:', bst2.elements, '\n')

#dolna granica find bedzie wynosic O(1), poniewaz element moze znajdowac sie w roocie
print('Szukanie A           O(1)')
bst2.find(bst2.root, 'A')
print('Liczba porownan:', bst2.comparisons, '\n')
bst2.comparisons = 0

#dla elementow posortowanych drzewo bst zamienia sie w liste jednokierunkowa
#a zeby znalezc ostatni element, musi przejsc przez wszystkie elementy
#daltego dolna granica find bedzie wynosic O(n)
print("Szukanie Angora's    O(n)")
bst2.find(bst2.root, "Angora's")
print('Liczba porownan:', bst2.comparisons, '\n')




print('\n\nRbt slownikRBT.txt')
rbt = Rbt()
rbt.load('slownikRBT.txt')
print('Liczba elementow:', rbt.elements, '\n')

#srednia granica find bedzie wynosic O(logn) dla losowych danych
print('Szukanie out         O(logn)')
rbt.find(rbt.root, 'out')
print('Liczba porownan:', rbt.comparisons, '\n')
rbt.comparisons = 0

#dolna granica find bedzie wynosic O(1), poniewaz element moze znajdowac sie w roocie
print('Szukanie bungalow    O(1)')
rbt.find(rbt.root, 'bungalow')
print('Liczba porownan:', rbt.comparisons, '\n')
rbt.comparisons = 0

#drzewo czerwono czarne zawsze samo sie sortuje i jego wysokosc nie przekorczy logn
#daltego dolna granica find bedzie wynosic O(logn)
print("Szukanie zymurgy's   O(logn)")
rbt.find(rbt.root, "zymurgy's")
print('Liczba porownan:', rbt.comparisons, '\n')

