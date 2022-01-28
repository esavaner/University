import sys
import re
import string
import operator


class Element:
    def __init__(self, x, prio):
        self.x = x
        self.prio = prio

class PrQueue:
    def __init__(self):
        self.heap= []          
        self.size = 0               

    def __len__(self):
        return self.size

    def parent(self,index):
        if index<=1 or index>self.size:
            return None
        else:
            return self.heap[index//2 - 1].prio
 
    def left(self,index):
        if index<1 or 2*index>self.size:
            return None
        else:
            return self.heap[index*2 - 1].prio
        
    def right(self,index):
        if index < 1 or 2*index + 1 > self.size:
            return None
        else:
            return self.heap[index*2].prio
    
    def empty(self):
        if self.size > 0:
            return 0
        else:     
            return 1
            
    def swap(self, x1, x2):
        self.heap[x1 - 1], self.heap[x2 - 1] = self.heap[x2 - 1], self.heap[x1 - 1]
        
    def insert(self, v ,p):
        if p >= 0:
            e = Element(v, p)
            self.size += 1
            self.heap.append(e)
            index = self.size
            while index > 1 and e.prio < self.parent(index):
                parentIndex = index//2
                self.swap(index, parentIndex)
                index = parentIndex
        else:            
            print('Zly priorytet')
    
    def show(self):
        result = []
        for i in self.heap:
            result.append((i.x, i.prio))
        result.sort(key = operator.itemgetter(1))
        print(result)
    
    def top(self):
        if self.size <= 0:
            return None
        elif self.size >= 1:          
            return self.heap[0].x, self.heap[0].prio 
   
    def search(self, x):
        result = []
        for i in self.heap:
            if i.x == x:            
                result.append(self.heap.index(i))
        return result        
    
    def prio(self, x, p):           
        if p > 0 :
            ids = self.search(x)
            print(ids)
            for i in ids:
                index = i
                while index > 1 and p < self.parent(index):
                    parentIndex = index//2
                    self.swap(index, parentIndex)
                    index = parentIndex
                if p < self.heap[i].prio:    
                    self.heap[i].prio = p    
        else:
            print('Zly priorytet')
        
    def pop(self):
        if self.size <= 0:
            return None
        elif self.size == 1:
            self.size = 0
            return self.heap[0].x, self.heap[0].prio 

        maxint, maxval = self.heap[0].prio, self.heap[0].x
        self.swap(1, self.size)
        self.heap.pop()
        self.size -= 1
        index = 1
        while True:
            if index*2 + 1 > self.size and index*2 > self.size:
                break
            elif index*2 + 1 > self.size and index*2 <= self.size:
                if self.size == 2 and self.heap[0].prio < self.heap[1].prio:
                    break
                self.swap(index, index*2)
                index *= 2
                break
            elif self.heap[index - 1].prio > self.left(index) or self.heap[index - 1].prio > self.right(index):
                if self.left(index) < self.right(index):
                    self.swap(index, index*2)
                    index *= 2
                else:
                    self.swap(index, index*2 + 1)
                    index = index*2 + 1
            else:
                break
        return (maxint, maxval)


if __name__ == "__main__":
    m = int(input('M: '))
    q = PrQueue()
    for _ in range(m):
        try:
            op = input().split()
            if op[0] == 'insert':
                q.insert(float(op[1]), float(op[2]))
            elif op[0] == 'empty':
                print(q.empty())
            elif op[0] == 'top':
                print(q.top())
            elif op[0] == 'pop':
                q.pop()
            elif op[0] == 'priority':
                q.prio(float(op[1]), float(op[2]))
            elif op[0] == 'print':
                q.show()
            else:
                print('Nieznana komenda')

        except IndexError:
            print('Za malo argumentow')
