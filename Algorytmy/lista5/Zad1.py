import sys
import re
import string
import operator

class element:
    def __init__(self, x, y):
        self.value = x
        self.priority = y

class priorityQueue:
    def __init__(self):
        self.heap=[]                # an array of integers
        self.size = 0               # the size of heap

    def __len__(self):
        return self.size

    def parent(self,index):
        # index is between 1 and self.size
        # It returns the value of the parent of heap[index]
        if index<=1 or index>self.size:
            return None
        else:
            return self.heap[index//2-1].priority
 
        
    def leftChild(self,index):
        # It returns the value of the left child
        if index<1 or 2*index>self.size:
            return None
        else:
            return self.heap[index*2-1].priority
        
    def rightChild(self,index):
        # It returns the value of the right child
        if index<1 or 2*index+1>self.size:
            return None
        else:
            return self.heap[index*2].priority
    
    def isEmpty(self):
        if self.size > 0:
            return 0
        else:     
            return 1
            
    def swap(self, index1, index2):
        self.heap[index1-1], self.heap[index2-1] = self.heap[index2-1], self.heap[index1-1]
        
    def insert(self,v ,p):
        
        #>= !!!!
        if p >= 0 :
            el = element(v, p)
  
            # the funciton inserts x into the heap, satisfying the heap property
            self.size+=1
            self.heap.append(el)
            index = self.size
            while index>1 and el.priority<self.parent(index):
                parentIndex = index//2
                self.swap(index, parentIndex)
                index = parentIndex
        else:            
            print("Wrong priority")
    
    def printSelf(self):
        listElem = []
        for i in self.heap:
            listElem.append((i.value, i.priority))
                
        #elements indexes are based on heap, so elem with
        #higher priority can be afer element with lower priority
        #but PQ still works properly with priorities 
        #sort of list is only visual good looking
        listElem.sort(key = operator.itemgetter(1))
        print(listElem)     
    
    def Top(self):
        if self.size<=0:
            return None
        elif self.size>=1:            
            return self.heap[0].value, self.heap[0].priority 

    #VERY IMPORTANT INFORMATION
    #https://stackoverflow.com/questions/2372994/search-an-element-in-a-heap    
    def searchElement(self, x):
        listElem = []
        for i in self.heap:
            if i.value == x:            
                listElem.append(self.heap.index(i))
        return listElem        
    
    
    def priorityChange(self, x, p):        
        #IMPORTANT
        #method change priority is O(logN)
        #but we are force to search all elements and search
        #is O(N)
        #https://stackoverflow.com/questions/2372994/search-an-element-in-a-heap    
        if p > 0 :
            
            listIndexes = self.searchElement(x)
          
            for i in listIndexes:
                index = i
                while index>1 and p<self.parent(index):
                    parentIndex = index//2
                    self.swap(index, parentIndex)
                    index = parentIndex
                if p < self.heap[i].priority:    
                    self.heap[i].priority = p    
        else:
            print("Wrong priority")
        
    def deleteMax(self):
        # The function returns the largest integer in self.heap if exists,
        #   otherwise None
        # After the maximum is deleted from self.heap,
        #   it must satisfy the heap property.
        if self.size<=0:
            return None
        elif self.size==1:
            self.size=0
            return self.heap[0].value, self.heap[0].priority 
        #--- code the remaining -----
        maxint, maxval = self.heap[0].priority, self.heap[0].value
        self.swap(1, self.size)
        self.heap.pop()
        self.size-=1
        index = 1
        while True:
            if index*2+1>self.size and index*2>self.size:
                break
            elif index*2+1>self.size and index*2<=self.size:
                if self.size==2 and self.heap[0].priority<self.heap[1].priority:
                    break
                self.swap(index, index*2)
                index = index*2
                break
            elif self.heap[index-1].priority>self.leftChild(index) or self.heap[index-1].priority>self.rightChild(index):
                if self.leftChild(index)<self.rightChild(index):
                    self.swap(index, index*2)
                    index = index*2
                else:
                    self.swap(index, index*2+1)
                    index = index*2+1
            else:
                break
        return (maxint, maxval)


        
if __name__ == "__main__":

    h = priorityQueue()                
    
    val_ins = int(input("Insert number of operations: "))
    for i in range (val_ins):
                
        val_operation = list(input("Operation: ").split())
                    
        if val_operation[0] == "insert":
            
            print("")
            h.insert(int(val_operation[1]),int(val_operation[2]))
            print("")
                        
        elif val_operation[0] == "empty":
            
            print("")
            print(h.isEmpty())
            print("")
            
        elif val_operation[0] == "top":
            
            print("")
            print(h.Top())
            print("")
        
        elif val_operation[0] == "pop":
            
            x = h.deleteMax()    
            print("")
            print(x)
            print("")            
                    
        elif val_operation[0] == "priority":
            
            print("")
            h.priorityChange(int(val_operation[1]), int(val_operation[2]))
            print("")
                    
        elif val_operation[0] == "print":
            
            print("")
            h.printSelf()
            print("")
            
        else:
            print("Wrong input")       


