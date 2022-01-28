import heapq
import os
import io
from collections import defaultdict

class Node:
	#jeden wezel drzewa, posiada symbol, jego czestotliwosc wystepowania i mozliwych potomkow
	def __init__(self, symbol, occurence):
		self.symbol = symbol
		self.occurence = occurence
		self.left = None
		self.right = None

	#nadpisanie funkcji wiekszosci
	def __gt__(self, other):
		if(other == None):
			return -1
		if(not isinstance(other, Node)):
			return -1
		return self.occurence > other.occurence


class ClassicCoding:
	#obiekt z plikiem wejsciowym, drzewem, kodami i ich odwrotnosciami do odkodowania
	def __init__(self, infile):
		self.infile = infile
		self.heap = []
		self.codes = {}
		self.reverse = {}


	#-------------------------------------------------------------------------------
	#kodowanie



	#liczenie czestotliwosci wystapienia kazdego symbolu
	def occurence(self, data):
		symbols = {}
		for d in data:
			if not d in symbols:
				symbols[d] = 0
			symbols[d] += 1
		return symbols


	#stworzenie drzewa na podstawie symboli
	def createHeap(self, symbols):
		for s in symbols:
			node = Node(s, symbols[s])
			heapq.heappush(self.heap, node)


	#laczenie poszczegolnych lisci na podstawie ich wystepowania
	def merge_nodes(self):
		while(len(self.heap)>1):
			node1 = heapq.heappop(self.heap)
			node2 = heapq.heappop(self.heap)

			merged = Node(None, node1.occurence + node2.occurence)
			merged.left = node1
			merged.right = node2

			heapq.heappush(self.heap, merged)


	#tworzenie kodow dla symboli przechodzac przez drzewo
	def createCodes(self, root, code):
		if(root == None):
			return

		if(root.symbol != None):
			self.codes[root.symbol] = code
			self.reverse[code] = root.symbol
			return

		self.createCodes(root.left, code + "0")
		self.createCodes(root.right, code + "1")


	#rozpoczecie funkcji powyzej, ale dla korzenia
	def startCodes(self):
		root = heapq.heappop(self.heap)
		code = ""
		self.createCodes(root, code)


	#funkcja zwracajaca jeden dlugi kod na podstawie wyszystkich kodow symboli
	def getEncoded(self, data):
		encoded = ""
		for d in data:
			encoded += self.codes[d]
		return encoded


	#wypelnienie w razie gdyby tekst nie byl wielokrotnoscia 8
	def pad(self, encoded):
		padding = 8 - len(encoded)%8
		for _ in range(padding):
			encoded += "0"

		info = "{0:08b}".format(padding)
		encoded = info + encoded
		return encoded


	#zamiana tekstu na bity
	def toByteArray(self, padded):
		b = bytearray()
		for i in range(0, len(padded), 8):
			byte = padded[i:i+8]
			b.append(int(byte, 2))
		return b



	#funkcja kodujaca, wykonuje nastepujace kroki:
	#	- odczutuje plik
	#	- liczy czestotliwosc wystepowania symboli
	#	- tworzy drzewo i laczy odpowiednie liscie
	#	- koduje tekst na podstawie kodow zapisanych w drzewie
	#	- zamienia tekst na bity i zapisuje do pliku
	def code(self):
		name, _ = os.path.splitext(self.infile)
		outfile = name + "_coded.bin"

		with open(self.infile, 'rb') as f, open(outfile, 'wb') as output:
			data = f.read()
			symbols = self.occurence(data)
			self.createHeap(symbols)
			self.merge_nodes()
			self.startCodes()
			encoded = self.getEncoded(data)
			padded = self.pad(encoded)
			b = self.toByteArray(padded)
			output.write(bytes(b))

		return outfile, data, symbols



	#-------------------------------------------------------------------------------
	#dekodowanie


	#na podstawie tablicy kodow odczytuje zakodowany tekst
	def decodeFromEncoded(self, encoded):
		code = ""
		decoded = ""

		for bit in encoded:
			code += bit
			if(code in self.reverse):
				symbol = self.reverse[code]
				decoded += chr(symbol)
				code = ""

		return decoded


	#funkcja usuwajaca padding
	def unpad(self, padded):
		info = padded[:8]
		padding = int(info, 2)

		padded = padded[8:] 
		encoded = padded[:-1*padding]

		return encoded


	#funkcja dekodujaca, wykonuje nastepujace kroki:
	#	- odczutuje plik
	#	- usuwa padding gdyby istnial
	#	- dekoduje symbole na podstawie tablicy
	#	- zamienia bity na tekst i zapisuje do pliku
	def decode(self, path):
		name, _ = os.path.splitext(self.infile)
		outfile = name + "_decoded.txt"
		bit_string = ""

		with open(path, 'rb') as file, open(outfile, 'w', encoding="utf-8") as output:

			byte = file.read(1)
			while(byte != b""):
				byte = ord(byte)
				bits = bin(byte)[2:].rjust(8, '0')
				bit_string += bits
				byte = file.read(1)

			encoded = self.unpad(bit_string)

			decoded = self.decodeFromEncoded(encoded)
			
			output.write(decoded)

		return bit_string