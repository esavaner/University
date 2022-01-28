import sys
import random
import math
import numpy as np

comparisons = 0

#-----------------------------------------------------------------------------------
#randomized select


#domyslna procedura partition jak w quickosorcie
def partition(A, left, right):
    global comparisons
    i = (left-1)
    pivot = A[right]
    for j in range(left, right):
        if A[j] < pivot:
            i += 1
            A[i], A[j] = A[j], A[i]
    A[i+1], A[right] = A[right], A[i+1]
    return (i+1)


#zamiana lewego elmentu z losowym elementem ze zbioru (lewy, prawy)
def randomizedPartition(A, left, right):
    i = random.randint(left, right)
    A[left], A[i] = A[i], A[left]
    return partition(A, left, right)


#wybiera losowo dzielnik i w zaleznosci od warosci k schodzi do kolejnych podgrup
def randomizedSelect(A, left, right, k):
    global comparisons
    if left == right:
        return A[left]
    q = randomizedPartition(A, left, right)
    i = q-left+1
    comparisons += 1
    if k <= i:
        return randomizedSelect(A, left, q, k)
    else:
        return randomizedSelect(A, q+1, right, k-i)


#-----------------------------------------------------------------------------------
#select


#wybranie mediany z grupy 5 elementow za pomoca inseretionSort
def partition5(A, left, right):
    global comparisons
    i = left + 1
    while i <= right:
        j = i
        while j > left and A[j-1] > A[j]:
            A[j-1], A[j] = A[j], A[j-1]
            j -= 1
        i += 1
    return math.floor((left + right)/2)


def pivot(A, left, right):
    global comparisons
    
    #mediana dla grup mniejszych niz 5 elementow
    if right - left < 5:
        return partition5(A, left, right)

    #inaczej przenosi mediane w podgrupach na pierwsze n/5 pozycji
    for i in range(left, right, 5):
        subRight = i + 4
        if subRight > right:
            subRight = right
        median5 = partition5(A, i, subRight)
        A[median5], A[left + math.floor((i - left)/5)] = A[left + math.floor((i - left)/5)], A[median5]
    
    #obliczanie mediany median
    mid = (right - left)/10 + left + 1
    return select(A, left, left + math.floor((right - left)/5), mid)


def modifiedPartition(A, left, right, q, k):
    global comparisons
    v = A[q]

    #przeniesienie dzielnika na koniec
    A[q], A[right] = A[right], A[q]
    storeQ = left

    #przeniesienie wszystkich mniejszych elementow od dzielnika na lewo od niego
    for i in range(left, right-1):
        if A[i] < v:
            comparisons += 1
            A[storeQ], A[i] = A[i], A[storeQ]
            storeQ += 1

    #przeniesienie wszystkich elementow rowych dzielnikowi na prawo od mniejszych elementow
    storeQEq = storeQ
    for i in range(storeQ, right-1):
        if A[i] == v:
            comparisons += 1
            A[storeQEq], A[i] = A[i], A[storeQEq]
            storeQEq += 1
    
    #przeniesienie dzielnika na jego docelowe miejsce
    A[right], A[storeQEq] = A[storeQEq], A[right]

    comparisons += 1
    #zwrocenie lokacji dzielnika w zaleznosci od k
    #k jest w grupie mniejszych elementow
    if k < storeQ:
        return storeQ

    #k jest w grupie rownych elementow
    if k <= storeQEq:
        return k

    #k jest w grupie wiekszych elementow
    return storeQEq


#dziala podobnie jak randomized select, ale zamiast losowego dzielnika wybiera mediane median
def select(A, left, right, k):
    global comparisons
    while True:
        comparisons += 1
        if left == right:
            return left
        q = pivot(A, left, right)
        q = modifiedPartition(A, left, right, q, k)
        if k == q:
            return k
        elif k < q:
            right = q - 1
        else:
            left = q + 1


#-----------------------------------------------------------------------------------
#glowna petla

if __name__ == "__main__":
    n = int(input("n: "))
    k = int(input("k: "))
    arr = []

    #tworzenie tablicy w zaleznosci od parametru
    if sys.argv[1] == "-p":
        arr = np.random.permutation([i for i in range(1, n+1)])
    elif sys.argv[1] == "-r":
        arr = [random.randint(1, 2*n) for i in range(1, n+1)]

    #stworzenie kopii tablicy
    arr2 = arr.copy()

    #drukowanie wynikow dla randomized select
    print("\nRandomizedSelect:")
    result = randomizedSelect(arr, 0, n-1, k)
    for i in arr:
        if i == result:
            print("[" + str(i) + "]", end=" ")
        else:
            print(i, end=" ")

    #drukowanie wynikow dla select
    print("\nPorownania:", comparisons, "\n\nSelect:")
    comparisons = 0

    result2 = select(arr2, 0, n-1, k)
    for i in arr2:
        if i == result:
            print("[" + str(i) + "]", end=" ")
        else:
            print(i, end=" ")

    print("\nPorownania:", comparisons)
