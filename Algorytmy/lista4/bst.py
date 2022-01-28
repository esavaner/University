
#clasa node z wartoscia oraz lewym i prawym poddrzewem
class BstNode:
    def __init__(self, val):
        self.val = val
        self.left = None
        self.right = None

class Bst:
    def __init__(self):
        self.root = None
        self.elements = 0
        self.comparisons = 0


    def insert(self, node, s):
        #jesli dany wezel nie istnieje
        if node is None:
            self.elements += 1
            return BstNode(s)

        #jesli wartosc klucza jest mniejsza to insert z lewej
        if s < node.val:
            node.left = self.insert(node.left, s)

        #w przeciwnym przypadku insert z prawej
        else:
            node.right = self.insert(node.right, s)
        return node


    def load(self, f):
        try:
            infile = open(f, 'r')
        except IOError:
            print("Plik nie istnieje")

        #dla kazdego wyrazu w pliku wykonuje insert
        data = infile.read().split()
        for d in data:
            self.root = self.insert(self.root, d)


    def delete(self, node, s):
        if node is None: 
            return node  

        #jesli szukany klucz jest w mniejszy od aktualnej wartosci to szuka w lewym poddrzewie
        if s < node.val: 
            node.left = self.delete(node.left, s) 

        #jesli jest wiekszy to w prawym
        elif(s > node.val): 
            node.right = self.delete(node.right, s) 

        #jesli to jest szukany wezel do usuniecia
        else: 

            #jesli wezel ma 0 lub 1 potomka
            if node.left is None : 
                temp = node.right  
                node = None 
                return temp  
                
            elif node.right is None : 
                temp = node.left  
                node = None
                return temp 

            #jesli ma 2 potomkow to szuka najmniejszego wezla w prawym poddrzewie
            temp = self.findmin(node.right) 

            #kopiuje wartosc nastepnika do tego wezla
            node.val = temp.val

            #usuwa nastepnika
            node.right = self.delete(node.right, temp.val) 
        return node 


    #zaczynajac od roota sprawdza czy szukana wartosc jest mniejsza czy wieksza od aktualnej wartosci wezla
    def find(self, node, s):

        self.comparisons += 1

        #jesli jest rowna lub nie istnieje
        if node is None or node.val == s:
            return node

        #jesli wartosc jest mniejsza to szuka w lewym poddrzewie
        elif s < node.val:
            return self.find(node.left, s)

        #w przeciwnym przypadku w prawym
        else:
            return self.find(node.right, s)


    #szuka najbardziej lewego wezla
    def findmin(self, node):
        if node is None:
            return None

        curr = node
        while (curr.left is not None):
            curr = curr.left
        return curr


    #szuka najbardziej prawego wezla
    def findmax(self, node):
        if node is None:
            return None
        curr = node
        while (curr.right is not None):
            curr = curr.right
        return curr


    #zwraca wezel najbardziej po szukanym lub nic jesli taki nie istnieje
    def successor(self, root, node, k):
        if root is None:
            return None

        #szukanie minimalnej wartosci w prawym poddrzewie
        if root.val == k:
            if root.right:
                return self.findmin(root.right)

        #jesli wartosc wezla jest mniejsza od szukanej to zamienia roota na lewe poddrzewo
        elif root.val > k:
            node = self.successor(root.left, root, k)
        else:
            node = self.successor(root.right, node, k)
        return node


    #drukuje drzewo od lewej do prawej
    def inorder(self, node):
        if node is not None:
            self.inorder(node.left)
            print(node.val, end=' ')
            self.inorder(node.right)