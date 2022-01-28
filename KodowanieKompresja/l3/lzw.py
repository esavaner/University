  
import sys
from math import log


class Gamma:
    def encode(self, x):

        #liczba x zostaje zapisana w naturalnym kodzie binarnym
        binary = bin(x)[2:]

        #n - liczba cyfr znaczacych
        n = len(binary)

        #slowo kodowe sklada sie z bitu 0 powtorzonego n-1 razy oraz liczby dwojkowej
        return (n - 1)*'0' + binary

    def decode(self, data):

        while data != "":

            #liczenie liczby zer na poczatku kodu
            zeros = 0
            while data[zeros] == '0':
                zeros += 1
                if zeros >= len(data):
                    return

            #zwrocenie liczby dziesietnej
            yield int(data[:zeros + zeros+1], base=2)

            #usuniecie zakodowanej liczby z ciagu wejsciowego
            data = data[zeros + zeros+1:]


class Delta:
    def encode(self, x):

        #liczba x zostaje zapisana w naturalnym kodzie binarnym
        binary = bin(x)[2:]

        #n - liczba cyfr znaczacych
        n = len(binary)

        #przedstawienie n w kodzie gamma
        kn = Gamma().encode(n)

        #zapisanie slowa jako k + n + x(bez pierwszego bitu)
        return kn + binary[1:]

    def decode(self, data):

        while data != "":

            #liczenie liczby zer na poczatku kodu
            zeros = 0
            while data[zeros] == '0':
                zeros += 1
                if zeros >= len(data):
                    return

            #kn pierwszych bitow nie jest czescia liczby
            kn = zeros + zeros+1

            #tym razem to n jest przedstawiane dziesietnie
            n = int(data[:kn], base=2)

            #zamienienie liczby x na dziesietna
            yield int('1' + data[kn:kn + n-1], base=2)

            #usuniecie zakodowanej liczby z ciagu wejsciowego
            data = data[kn + n-1:]


class Omega:
    def encode(self, x):

        #zapisanie bitu 0 i podstawienie x pod k
        code = '0'
        k = x

        #dopoki k jest wieksze od 1:
        while k > 1:

            #zapisanie k binarnie
            binary = bin(k)[2:]

            #dodanie nowego kodu na poczatek slowa kodowanego
            code = binary + code

            #podstawienie pod k liczby bitow k pomniejszonej o 1
            k = len(binary) - 1

        return code

    def decode(self, data):

        while data != "":

            #podstawienie pod n liczby 1
            n = 1

            #jesli pierwszy bit jest rowny 0 to nie mozna odkodowac liczby
            if data[0] == '0':
                return

            #dopoki kolejny bit nie ma wartosci 0
            while data[0] != '0':
                np = n

                #podstawienie pod n wartosci zapisanej na n+1 kolejnych bitach
                n = int(data[:n+1], base=2)

                #usuniecie poprzedniego n z ciagu
                data = data[np+1:]
                if data == "":
                    return

            #zwrocenie ostatniej odczytanej wartosci
            yield n
            data = data[1:]


class Fibonacci:
    def __init__(self):
        self.seq = [0, 1]
        self.last = 1

    #tworzenie ciagu Fibonacciego
    def createSeq(self, x):

        #jesli x jest wiekszy niz ostatnia liczba w ciagu
        if x > self.last:

            #dopoki suma ostatnich 2 jest mniejsza rowna x
            while self.seq[-1] + self.seq[-2] <= x:

                #dodaj ta sume na koniec ciagu
                self.seq.append(self.seq[-1] + self.seq[-2])

            #ustaw ostatnia najwieksza liczbe na x i zwroc ciag
            self.last = x
            return self.seq.copy()

        #w przeciwnym wypadku zwroci liczby mniejsze od x z juz istniejacego ciagu
        else:
            return [num for num in self.seq if num <= x]


    def encode(self, x):

        #ustalene kodu na 1 i utworzenie ciagu Fibonacciego, gdzie ostatania liczba bedzie mniejsza od x
        code = '1'
        sequence = self.createSeq(x)

        #dopoki x jest wiekszy od 0
        while x != 0:

            #dodanie 1 na poczatku kodui odjecie od x ostanie miejsce ciagu
            #mozna to zrobic, poniewaz x zawsze bedzie wiekszy
            code = '1' + code
            x -= sequence[-1]
            sequence.pop()

            #dodaje 0 na poczatku dopoki x jest mniejszy od ostatniego miejsca
            while sequence[-1] > x:
                code = '0' + code
                sequence.pop()

        #usuniecie 1 na poczatku kodu
        return code[1:]


    def decode(self, data):

        #rozpoczecie ciagu Fibonacciego, ustawienie wyniku na 0 oraz ostaniego bitu na nic
        sequence = [1, 1]
        result = 0
        last = ''


        for d in data:

            #jesli dwa bity pod rzad rownaja sie 1
            if d == '1' and last == '1':

                #zwrocenie wyniku
                yield result

                #reset wszystkich wartosci
                sequence = [1, 1]
                result = 0
                last = ''

            #w przeciwnym wypadku zwiekszenie wyniku o wartosc bita razy ostatni wyraz w ciagu Fibonacciego
            else:
                result += int(d) * sequence[-1]

                #obliczenie nastepnego wyrazu ciągu
                sequence.append(sequence[-1] + sequence[-2])

                #zamiana wartosci ostatniego bita na aktualna wartosc
                last = d


class LZW:
    def __init__(self, coding):
        self.coding = coding


    def encodeLZW(self, data: str):
        #wypelnienie slownika poczatkowym alfabetem
        dictionary = {chr(i): i for i in range(256)}

        #indeks na pierwsze wolne miejsce w slowniku
        latest = 256

        #c jako pierwszy symbol wejsciowy
        c = data[0]

        #wczytywanie kolejnych znakow jako s
        for s in data[1:]:

            #jesli c+s znajduje sie w slowniku, to przedluza ciag c
            if (c+s) in dictionary:
                c += s

            #jesli c+s nie ma w slowniku, to wypisuje kod dla c
            #nastepnie dodaje ciag c+s do slownika i przypisuje c = s
            else:
                yield dictionary[c]
                dictionary[c+s] = latest
                c = s
                latest += 1

        #na koncu wypisuje kod zwiazany z c
        yield dictionary[c]


    def decodeLZW(self, data):
        #wypelnienie slownika poczatkowym alfabetem, tylko teraz w odwrotny sposob
        dictionary = {i: chr(i) for i in range(256)}

        #indeks na pierwsze wolne miejsce w slowniku
        latest = 256

        #pierwszy kod skompresowanych danych
        pk = data[0]

        #wypisanie ciagu kodu pk ze slownika
        yield dictionary[pk]

        #wczytywanie kodu k
        for k in data[1:]:

            #ciag skojarzony z poprzednim kodem
            pc = dictionary[pk]

            #jesli k jest w slowniku, to dodaje do slownika ciag (pc + pierwszy symbol slownik[k])
            #nastepnie wypisuje slownik [k]
            if k in dictionary:
                dictionary[latest] = pc + dictionary[k][0]
                latest += 1
                yield dictionary[k]

            #jesli k nie ma w slowniku, to dodaje do slownika ciag (pc + pierwszys symbol pc)
            #nastepnie wypisuje ten ciag
            else:
                dictionary[latest] = pc + pc[0]
                latest += 1
                yield dictionary[latest-1]

            pk = k

    #koduje ciag wejsciowy na numery, a potem numery na kody w zaleznosci od wybranej metody
    def encode(self, data):
        return ''.join(self.coding.encode(number) for number in self.encodeLZW(data))

    #dekoduje ciag wejsciowy na numery, a potem numery na odpowiednie znaki
    def decode(self, data):
        return ''.join(self.decodeLZW(list(self.coding.decode(data))))


def pad(data):
    padding = len(data)%8
    bits = format(padding, '08b') + data + (8-padding)*'0'
    n = [bits[i:i+8] for i in range(0, len(bits), 8)]
    result = [chr(int(x, 2)) for x in n]
    return ''.join(result)


def test(argv):

    #domyslne kodowanie to omega
    coding = Omega()
    encode = True

    #zamiana kodowania w zaleznosci od argumentow
    for a in argv:
        if a == 'omega':
            coding = Omega()
        elif a == 'gamma':
            coding = Gamma()
        elif a == 'delta':
            coding = Delta()
        elif a == 'fibonacci':
            coding = Fibonacci()
        elif a == 'e' or a == 'encode':
            encode = True
        elif a == 'd' or a == 'decode':
            encode = False

    #ostatnie 2 argumenty na wejsciu to pliki
    infile = argv[-2]
    outfile = argv[-1]
    encoding = LZW(coding)

    #jesli wybrane zostalo kodowanie
    if encode:
        before = None
        with open(infile, 'r') as f:
            before = f.read()
        after = encoding.encode(before)
        padded = pad(after)
        with open(outfile, 'w', encoding="utf-8") as f1, open(outfile + ".txt", 'w') as f2:
            f1.write(padded)
            f2.write(after)

        #entropia dla wejscia
        symbols = {}
        for d in before:
            if not d in symbols:
                symbols[d] = 0
            symbols[d] += 1
            
        entropy = 0.0
        for s in symbols.values():
            entropy += s * (log(s, 2) - log(len(before), 2))
        entropy /= -(len(before))


        #entropia dla wyjscia
        symbols = {}
        for d in padded:
            if not d in symbols:
                symbols[d] = 0
            symbols[d] += 1
            
        entropy2 = 0.0
        for s in symbols.values():
            entropy2 += s * (log(s, 2) - log(len(padded), 2))
        entropy2 /= -(len(padded))

        print("Dlugosc pliku:", len(before*8))
        print("Dlugosc kodu:", len(after))
        print("Stopien kompresji:", len(before)*8/len(after))
        print("Entropia:", entropy)
        print("Entropia kodu:", entropy2)

        print()

    #jesli wybrane zostalo dekodowanie
    else:
        with open(infile, 'r') as f:
            before = f.read()
        after = encoding.decode(before)
        with open(outfile, 'w', encoding="utf-8") as f:
            f.write(after)


if __name__ == "__main__":
    test(sys.argv)