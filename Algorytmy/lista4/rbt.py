
#wezel z wartoscia, kolorem i wezlami z nim polaczonymi
class RbtNode():
    def __init__(self, val):
        self.val = val
        self.parent = None
        self.left = None
        self.right = None
        self.color = 1


class Rbt:
    def __init__(self):
        self.NIL = RbtNode(0)
        self.NIL.color = 0
        self.NIL.left = None
        self.NIL.right = None
        self.root = self.NIL
        self.elements = 0
        self.comparisons = 0


    def insert(self, key):
        node = RbtNode(key)
        node.parent = None
        node.val = key
        node.left = self.NIL
        node.right = self.NIL
        node.color = 1
        y = None
        x = self.root

        #powtarza az nie napotka NIL
        while x != self.NIL:
            y = x

            #jesli szukany klucz jest mniejszy od aktualnej wartosci to idzie w lewo
            if node.val < x.val:
                x = x.left

            #inczej idzie w prawo
            else:
                x = x.right
        node.parent = y

        #jesli wezel nie ma przodka to zwraca ten wezel
        if y == None:
            self.root = node

        #jesli wartosc klucza przodka jest wieksza niz klucz to bedzie to lewy potomek
        elif node.val < y.val:
            y.left = node

        #inaczej bedzie to prawy potomek
        else:
            y.right = node

        #jesli nie ma przodka to bedzie mialo kolor czarny
        if node.parent == None:
            node.color = 0
            return
        if node.parent.parent == None:
            return

        #naprawia drzewo po insercie
        self.fix_insert(node)


    def load(self, f):
        try:
            infile = open(f, 'r')
        except IOError:
            print("Plik nie istnieje")

        #dla kazdego wyrazu w pliku wykonuje insert
        data = infile.read().split()
        for d in data:
            self.elements += 1
            self.insert(d)


    def delete(self, node, key):
        z = self.NIL

        #sprawdza czy szukany klucz istnieje i zapisuje go pod z
        while node != self.NIL:
            if node.val == key:
                z = node
            if node.val <= key:
                node = node.right
            else:
                node = node.left
        if z == self.NIL:
            print("Nie mozna znalezc klucza w drzewie")
            return
        y = z

        #zapis oryginalnego koloru wezla
        y_original_color = y.color

        #jesli nie istnieje lewy potomek
        if z.left == self.NIL:
            x = z.right
            self.__rb_transplant(z, z.right)

        #jesli nie istnieje prawy potomek
        elif (z.right == self.NIL):
            x = z.left
            self.__rb_transplant(z, z.left)

        #jesli ma obu potomkow
        else:

            #szuka minimum w prawym poddrzewie i podstawia pod y
            y = self.findmin(z.right)
            y_original_color = y.color

            #pod x zapisuje prawego potomka oryginalu
            x = y.right

            #jesli y jest potomkiem wezla do usuniecia to ustawia y pod przodka x 
            if y.parent == z:
                x.parent = y
            else:

                #zamiana y i jego prawego potomka
                self.__rb_transplant(y, y.right)
                y.right = z.right
                y.right.parent = y

            #zamiana wezla do usuniecia z y
            self.__rb_transplant(z, y)
            y.left = z.left
            y.left.parent = y

            #ustawia kolor y na kolor oryginalu
            y.color = z.color

        #jesli kolor oryginalnego wezla jest czarny to naprawia drzewo
        if y_original_color == 0:
            self.delete_fix(x)


    def find(self, node, key):
        self.comparisons += 1

        #jesli wezel nie istnieje lub wartosc jest rowna kluczowi to zwraca go
        if node == None or key == node.val:
            return node

        #jesli klucz jest mniejszy od aktualnej wartosci to szuka w lewym poddrzewie
        if key < str(node.val):
            return self.find(node.left, key)

        #inaczej szuka w prawym poddrzewie
        return self.find(node.right, key)


    #szuka najbardziej lewego wezla
    def findmin(self, node):
        while node.left != self.NIL:
            node = node.left
        return node


    #szuka najbardziej prawego wezla
    def findmax(self, node):
        while node.right != self.NIL:
            node = node.right
        return node


    def successor(self, x):

        #szuka najmiejszej wartosci w prawym poddrzewie
        if x.right != self.NIL:
            return self.findmin(x.right)

        #szuka wartosci idac do gory i w prawo
        y = x.parent
        if y is not None:
            while y != self.NIL and x == y.right:
                x = y
                y = y.parent
        return y


    #drukuje drzewo od lewej do prawej
    #nie dziala dla wartosci 0
    def inorder(self, node):
        if node is not None:
            self.inorder(node.left)
            if node.val != 0:
                print(node.val, end=' ')
            self.inorder(node.right)


    def delete_fix(self, x):

        #dopoki x nie jest rootem i kolor x jest czarny
        while x != self.root and x.color == 0:

            #jesli x jest lewym potomkiem swojego przodka
            if x == x.parent.left:

                #ustawia rodzenstwo x
                s = x.parent.right

                #jesli kolor rodzenstwa jest czerwony
                if s.color == 1:

                    #ustawia kolor rodzenstwa na czarny, rodzica na czerowny i wykonuje rotacje lewostronna
                    s.color = 0
                    x.parent.color = 1
                    self.left_rotate(x.parent)
                    s = x.parent.right

                #jesli kolor obu potomkow rodzenstwa jest czarny
                if s.left.color == 0 and s.right.color == 0:

                    #ustawia kolor rodzenstwa na czerwony
                    s.color = 1
                    x = x.parent
                else:

                    #jesli kolor prawego potomka rodzenstwa jest czarny
                    if s.right.color == 0:

                        #ustawia kolor rodzenstwa na czerwony, a jego lewego potmka na czarny i wykonuje rotacje prawostronna
                        s.left.color = 0
                        s.color = 1
                        self.right_rotate(s)
                        s = x.parent.right

                    #jesli zadna z powyzszych nie jest spelnione
                    #ustawia kolor rodzenstwa x na kolor przodka x
                    s.color = x.parent.color

                    #ustawia kolor przodka i kolor prawego potomka rodzenstwa na czarny
                    x.parent.color = 0
                    s.right.color = 0

                    #wykoinuje rotacje lewostronna
                    self.left_rotate(x.parent)
                    x = self.root

            #jesli x jest prawym potomkiem swojego przodka
            #to samo co wyzej tylko lewe zamienione na prawe i w druga strone
            else:
                s = x.parent.left
                if s.color == 1:
                    s.color = 0
                    x.parent.color = 1
                    self.right_rotate(x.parent)
                    s = x.parent.left
                if s.right.color == 0 and s.right.color == 0:
                    s.color = 1
                    x = x.parent
                else:
                    if s.left.color == 0:
                        s.right.color = 0
                        s.color = 1
                        self.left_rotate(s)
                        s = x.parent.left 
                    s.color = x.parent.color
                    x.parent.color = 0
                    s.left.color = 0
                    self.right_rotate(x.parent)
                    x = self.root
        x.color = 0


    #zamiana dwoch wezlow
    def __rb_transplant(self, u, v):
        if u.parent == None:
            self.root = v
        elif u == u.parent.left:
            u.parent.left = v
        else:
            u.parent.right = v
        v.parent = u.parent


    def  fix_insert(self, k):

        #dopoki kolor przodka nie jest czerowny
        while k.parent.color == 1:

            #jesli przodek jest prawym potomkiem
            if k.parent == k.parent.parent.right:
                u = k.parent.parent.left

                #jesli kolor lewgo potomka nadprzodka jest czerwony
                if u.color == 1:

                    #ustawia kolory obu przodkow na czarny
                    u.color = 0
                    k.parent.color = 0

                    #a kolor nadprzodka na czerwony
                    k.parent.parent.color = 1
                    k = k.parent.parent
                else:

                    #jesli wezel jest lewym potomkiem przodka
                    if k == k.parent.left:
                        k = k.parent

                        #wykonuje rotacje prawostronna
                        self.right_rotate(k)
                    k.parent.color = 0
                    k.parent.parent.color = 1

                    #wykonuje rotacje lewostronna
                    self.left_rotate(k.parent.parent)

            #jesli przodek jest lewym potomkiem
            else:
                u = k.parent.parent.right

                #jesli kolor prawego potomka nadprzodka jest czerwony
                if u.color == 1:

                    #ustawia kolory obu przodkow na czarny
                    u.color = 0
                    k.parent.color = 0

                    #a kolor nadprzodka na czerwony
                    k.parent.parent.color = 1
                    k = k.parent.parent 
                else:

                    #jesli wezel jest prawym potomkiem przodka
                    if k == k.parent.right:
                        k = k.parent

                        #wykonuje rotacje prawostronna
                        self.left_rotate(k)
                    k.parent.color = 0
                    k.parent.parent.color = 1

                    #wykonuje rotacje lewostronna
                    self.right_rotate(k.parent.parent)
            if k == self.root:
                break

        #ustawia kolor roota na czarny
        self.root.color = 0


    def left_rotate(self, x):
        y = x.right
        x.right = y.left
        if y.left != self.NIL:
            y.left.parent = x
        y.parent = x.parent
        if x.parent == None:
            self.root = y
        elif x == x.parent.left:
            x.parent.left = y
        else:
            x.parent.right = y
        y.left = x
        x.parent = y


    def right_rotate(self, x):
        y = x.left
        x.left = y.right
        if y.right != self.NIL:
            y.right.parent = x
        y.parent = x.parent
        if x.parent == None:
            self.root = y
        elif x == x.parent.right:
            x.parent.right = y
        else:
            x.parent.left = y
        y.right = x
        x.parent = y