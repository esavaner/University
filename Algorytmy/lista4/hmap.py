import re


class HNode:
    def __init__(self, key, value):
        self.key = key
        self.value = value
        self.next = None
        

class Hmap:
    def __init__(self):
        self.capacity = 1000
        self.size = 0
        self.buckets = [None] * self.capacity
		

    def hash(self, key):
	    hashsum = 0

        #dla kazdego znaku w kluczu
	    for idx, c in enumerate(key):

            #dodaje (index klucza + dlugosc klucza) ^ kod znaku
		    hashsum += (idx + len(key)) ** ord(c)

            #operacja mod aby hash byl w [0, pojemnosc - 1]
		    hashsum = hashsum % self.capacity
	    return hashsum
	

    def insert(self, key, value):
	    key = re.sub('[^a-zA-Z]+', '', key)

        #zwieksza rozmiar
	    self.size += 1

        #oblicza hash klucza
	    index = self.hash(key)

        #bierze wezel z danym hashem
	    node = self.buckets[index]

        #jesli ten wezle nie istnieje to towrzy go i dodaje do tablicy
	    if node is None:
		    self.buckets[index] = HNode(key, value)
		    return

        #znajduje ostatni wezel w liscie
	    prev = node
	    while node is not None:
		    prev = node
		    node = node.next

        #dodaje stworzony wezel na koniec listy
	    prev.next = HNode(key, value)


    def find(self, key):

        #znajduje hash
	    index = self.hash(key)

        #znajduje wezel z tym hashem
	    node = self.buckets[index]

        #znajduje ostatni wezel przed wartoscia klucza
	    while node is not None and node.key != key:
		    node = node.next

        #zwraca jesli taki wezel istnieje
	    if node is None:
		    return None
	    else:
		    return node.value	


    def print_self(self):
        for node in self.buckets:
            if node != None :
                print("Key: ", node.key, "Value: ", node.value)
		    	

    def delete(self, key):

        #znajduje hash i wezel z tym hashem
        index = self.hash(key)
        node = self.buckets[index]
        prev = None

        #szuka wezla i jego poprzednika dla danej wartosci
        while node is not None and node.key != key:
            prev = node
            node = node.next

        #nie znalezino takiego wezla
        if node is None:
            return 0

        else:

            #zmniejsza rozmiar o 1
            self.size -= 1
            result = node.value

            #usuwa wezel
            if prev is None:
                node = None
            else:
                prev.next = prev.next.next	          
            self.buckets[index] = None        

            #zwraca wartosc usunietego wezla    
            return key, result

    
    def load(self, f):
        try:
            infile = open(f, 'r')
        except IOError:
            print("Plik nie istnieje")

        #dla kazdego wyrazu w pliku wykonuje insert
        data = infile.read().split()
        for d in data:
            pass
            #self.insert(self.root, d)