import sys
import time
from bst import Bst
from rbt import Rbt
from hmap import Hmap


def test(inp):
    if inp == 'bst':
        bst = Bst()
        maxelements = 0
        n = int(input())
        for _ in range(n):
            try:
                c = input().split()
                start = time.time()
                if c[0] == 'insert':
                    bst.root = bst.insert(bst.root, c[1])

                elif c[0] == 'load':
                    bst.load(c[1])

                elif c[0] == 'delete':
                    bst.root = bst.delete(bst.root, c[1])
                    bst.elements -= 1

                elif c[0] == 'find':
                    node = bst.find(bst.root, c[1])
                    if node is not None and node.val == c[1]:
                        print(1)
                    else:
                        print(0)

                elif c[0] == 'min':
                    node = bst.findmin(bst.root)
                    if node.val is not None:
                        print(node.val)
                    else:
                        print()

                elif c[0] == 'max':
                    node = bst.findmax(bst.root)
                    if node is not None:
                        print(node.val)
                    else:
                        print()

                elif c[0] == 'successor':
                    k = c[1]
                    node = bst.find(bst.root, k)
                    succ = bst.successor(bst.root, node, k)
                    if succ is not None and succ.val != k:
                        print(succ.val)
                    else:
                        print()

                elif c[0] == 'inorder':
                    bst.inorder(bst.root)
                    print()

                else:
                    print('Nieznana komenda')

                end = time.time()
                if maxelements < bst.elements:
                        maxelements = bst.elements

                print("Czas dzialania:", end - start, file=sys.stderr)
                print("Maksymalna liczba elementow:", maxelements, file=sys.stderr)
                print("Koncowa liczba elementow:", bst.elements, file=sys.stderr)

            except IndexError:
                print('Za malo argumentow dla komendy')

    elif inp == 'rbt':
        rbt = Rbt()
        maxelements = 0
        n = int(input())
        for _ in range(n):
            try:
                c = input().split()

                start = time.time()
                if c[0] == 'insert':
                    rbt.insert(c[1])
                    rbt.elements += 1

                elif c[0] == 'load':
                    rbt.load(c[1])

                elif c[0] == 'delete':
                    rbt.delete(rbt.root, c[1])
                    rbt.elements -= 1

                elif c[0] == 'find':
                    node = rbt.find(rbt.root, c[1])
                    if node is not None and node.val == c[1]:
                        print(1)
                    else:
                        print(0)

                elif c[0] == 'min':
                    node = rbt.findmin(rbt.root)
                    if node.val is not None:
                        print(node.val)
                    else:
                        print()

                elif c[0] == 'max':
                    node = rbt.findmax(rbt.root)
                    if node is not None:
                        print(node.val)
                    else:
                        print()

                elif c[0] == 'successor':
                    k = c[1]
                    node = rbt.find(rbt.root, k)
                    succ = rbt.successor(node)
                    if succ is not None and succ.val != k:
                        print(succ.val)
                    else:
                        print()

                elif c[0] == 'inorder':
                    rbt.inorder(rbt.root)
                    print()

                else:
                    print('Nieznana komenda')

                end = time.time()
                if maxelements < rbt.elements:
                        maxelements = rbt.elements

                print("Czas dzialania:", end - start, file=sys.stderr)
                print("Maksymalna liczba elementow:", maxelements, file=sys.stderr)
                print("Koncowa liczba elementow:", rbt.elements, file=sys.stderr)

            except IndexError:
                print('Za malo argumentow dla komendy')

    elif inp == 'hmap':
        hmap = Hmap()
        htree = Rbt()
        n = int(input())
        for _ in range(n):
            try:
                c = input().split()
                if c[0] == 'insert':
                    pass
                    #hmap.insert(c[1])

                elif c[0] == 'load':
                    pass
                    #hmap.load(c[1])

                elif c[0] == 'delete':
                    hmap.delete(c[1])

                elif c[0] == 'find':
                    val = hmap.find(c[1])
                    if val is not None and val == c[1]:
                        print(1)
                    else:
                        print(0)

                elif c[0] == 'min':
                    print()

                elif c[0] == 'max':
                    print()

                elif c[0] == 'successor':
                    print()

                elif c[0] == 'inorder':
                    print()

                else:
                    print('Nieznana komenda')

            except IndexError:
                print('Za malo argumentow dla komendy')


if __name__ == "__main__":
    inp = str(sys.argv[2])
    test(inp)