import sys
import argparse
import fileinput
import time
import math
import itertools
import operator
from collections import defaultdict 


#for prim

class Heap(): 
  
    def __init__(self): 
        self.array = [] 
        self.size = 0
        self.pos = [] 
  
    def newMinHeapNode(self, v, dist): 
        minHeapNode = [v, dist] 
        return minHeapNode 
  
    # A utility function to swap two nodes of  
    # min heap. Needed for min heapify 
    def swapMinHeapNode(self, a, b): 
        t = self.array[a] 
        self.array[a] = self.array[b] 
        self.array[b] = t 
  
    # A standard function to heapify at given idx 
    # This function also updates position of nodes  
    # when they are swapped. Position is needed  
    # for decreaseKey() 
    def minHeapify(self, idx): 
        smallest = idx 
        left = 2 * idx + 1
        right = 2 * idx + 2
  
        if left < self.size and self.array[left][1] < self.array[smallest][1]: 
            smallest = left 
  
        if right < self.size and self.array[right][1] < self.array[smallest][1]: 
            smallest = right 
  
        # The nodes to be swapped in min heap  
        # if idx is not smallest 
        if smallest != idx: 
  
            # Swap positions 
            self.pos[ self.array[smallest][0] ] = idx 
            self.pos[ self.array[idx][0] ] = smallest 
  
            # Swap nodes 
            self.swapMinHeapNode(smallest, idx) 
  
            self.minHeapify(smallest) 
  
    # Standard function to extract minimum node from heap 
    def extractMin(self): 
  
        # Return NULL wif heap is empty 
        if self.isEmpty() == True: 
            return
  
        # Store the root node 
        root = self.array[0] 
  
        # Replace root node with last node 
        lastNode = self.array[self.size - 1] 
        self.array[0] = lastNode 
  
        # Update position of last node 
        self.pos[lastNode[0]] = 0
        self.pos[root[0]] = self.size - 1
  
        # Reduce heap size and heapify root 
        self.size -= 1
        self.minHeapify(0) 
  
        return root 
  
    def isEmpty(self): 
        return True if self.size == 0 else False
  
    def decreaseKey(self, v, dist): 
  
        # Get the index of v in  heap array 
  
        i = self.pos[v] 
  
        # Get the node and update its dist value 
        self.array[i][1] = dist 
  
        # Travel up while the complete tree is not  
        # hepified. This is a O(Logn) loop 
        while i > 0 and self.array[i][1] < self.array[int((i - 1) / 2)][1]: 
  
            # Swap this node with its parent 
            self.pos[ self.array[i][0] ] = int((i-1)/2)
            self.pos[ self.array[int((i-1)/2)][0] ] = i 
            self.swapMinHeapNode(i, int((i - 1)/2) ) 
  
            # move to parent index 
            i = int((i - 1) / 2); 
  
    # A utility function to check if a given vertex 
    # 'v' is in min heap or not 
    def isInMinHeap(self, v): 
  
        if self.pos[v] < self.size: 
            return True
        return False
        
def printArr(parent,key, n):
    cost = 0 
    for i in range(1, n): 
        print("v", parent[i]+1, i+1, key[i]) 
        cost += key[i]
    print(cost)
        
class Graph(): 
  
    def __init__(self, V): 
        self.V = V 
        self.graph = defaultdict(list) 
  
    # Adds an edge to an undirected graph 
    def addEdge(self, src, dest, weight): 
  
        # Add an edge from src to dest.  A new node is 
        # added to the adjacency list of src. The node  
        # is added at the begining. The first element of 
        # the node has the destination and the second  
        # elements has the weight 
        newNode = [dest, weight] 
        self.graph[src].insert(0, newNode) 
  
        # Since graph is undirected, add an edge from  
        # dest to src also 
        newNode = [src, weight] 
        self.graph[dest].insert(0, newNode) 
  
    # The main function that prints the Minimum  
    # Spanning Tree(MST) using the Prim's Algorithm.  
    # It is a O(ELogV) function 
    def PrimMST(self): 
        # Get the number of vertices in graph 
        V = self.V   
          
        # key values used to pick minimum weight edge in cut 
        key = []    
          
        # List to store contructed MST 
        parent = []  
  
        # minHeap represents set E 
        minHeap = Heap() 
  
        # Initialize min heap with all vertices. Key values of all 
        # vertices (except the 0th vertex) is is initially infinite 
        for v in range(V): 
            parent.append(-1) 
            key.append(99999)#max int was removed 
            minHeap.array.append( minHeap.newMinHeapNode(v, key[v]) ) 
            minHeap.pos.append(v) 
  
        # Make key value of 0th vertex as 0 so  
        # that it is extracted first 
        minHeap.pos[0] = 0
        key[0] = 0
        minHeap.decreaseKey(0, key[0]) 
  
        # Initially size of min heap is equal to V 
        minHeap.size = V; 
  
        # In the following loop, min heap contains all nodes 
        # not yet added in the MST. 
        while minHeap.isEmpty() == False: 
  
            # Extract the vertex with minimum distance value 
            newHeapNode = minHeap.extractMin() 
            u = newHeapNode[0] 
  
            # Traverse through all adjacent vertices of u  
            # (the extracted vertex) and update their  
            # distance values 
            for pCrawl in self.graph[u]: 
  
                v = pCrawl[0] 
  
                # If shortest distance to v is not finalized  
                # yet, and distance to v through u is less than 
                # its previously calculated distance 
                if minHeap.isInMinHeap(v) and pCrawl[1] < key[v]: 
                    key[v] = pCrawl[1] 
                    parent[v] = u 
  
                    # update distance value in min heap also 
                    minHeap.decreaseKey(v, key[v]) 
  
        printArr(parent, key, V)


 
 
 
 

#for kruskal
class Edge:
    def __init__(self, b, e, w):
        self.u = b
        self.v  = e
        self.weight = w
    def __repr__(self):
        return "e "+ str(self.u)+" "+str(self.v)+" "+ str(self.weight)
        

class Node:
    def __init__(self, val):
        self.value = val
        self.parent = self
        self.rank = 0
    def __repr__(self):
        return "n "+str(self.value)+" r "+ str(self.rank) + " p "+ str(self.parent.value)



class Heapq:
    def __init__(self, d):
        self.data = None
        self.d = d

    def make_heap(self, lst):
        self.data = lst
        k = self.size() -1
        while k >=0:
            self.siftdown(k)
            k = k-1

    def find_min(self):
        return self.data[0]

    def delete(self, k):
        if(k == self.size() - 1):
            del self.data[self.size() -1]
            return

        last = self.data[self.size() - 1]
        del self.data[self.size() -1]
        item = self.data[k]
        self.data[k] = last

        if(last.weight < item.weight):
            self.siftup(k)
        else:
            self.siftdown(k)
    
    def find_min(self):
        return self.data[0]

            
    def delete_min(self):
        if self.size() == 0: return
        self.delete(0)

    def size(self):
        return len(self.data)

    def siftup(self, k):
        if k > self.size() - 1:
            return #throw error
        p = int((k-1)/self.d)
        while(k > 0 and self.data[p].weight > self.data[k].weight):
            #swap
            temp = self.data[k]
            parent = self.data[p]
            self.data[k] = parent
            self.data[p] = temp
            k = p #go up one
            p = int((k-1)/self.d)



    def siftdown(self, k):
        j = self.minchild(k)
        while(j != None and self.data[j].weight < self.data[k].weight):
            #swap
            temp = self.data[k]
            child = self.data[j]
            self.data[k] = child
            self.data[j] = temp
            k = j
            j = self.minchild(k)


    def minchild(self, k):
        i = (self.d * k) + 1
        end = (self.d * k) + self.d
        if(i>=self.size()):
            return None
        min_idx = i
        minValue = self.data[i].weight
        while(i<=end and i < self.size()):
            if(self.data[i].weight <= minValue  ):
                minValue = self.data[i].weight
                min_idx = i
            i = i+1
        return min_idx


    def __repr__(self):
        return str(self.data)        




class TreeSet:
    def __init__(self):
        self.data = {}
    #create set from single node
    def make_set(self, n):
        self.data[n] = Node(n)
    #find set of node n
    def find(self, n):
        gp = self.data[n].parent.parent
        while(gp != self.data[n].parent):
            self.data[n].parent = gp
            n = gp.value
            gp = self.data[n].parent.parent
        return gp.value
    #link 2 nodes
    def union(self, u, v):
        ptr = None
        if self.data[u].rank > self.data[v].rank:
            self.data[v].parent = self.data[u]
            ptr = self.data[u]
        elif self.data[u].rank < self.data[v].rank:
            self.data[u].parent = self.data[v]
            ptr = self.data[v]
        else:
            self.data[u].parent = self.data[v]
            self.data[v].rank = self.data[v].rank + 1
            ptr = self.data[v]
        return ptr
    #for testing purpose
    def printd(self):
        print(self.data)


if __name__ == '__main__':

    if len(sys.argv) > 1:
 
        if sys.argv[1] == "-p":

            #prim tree
            
            edges = []
            vertices = []

            val_vert = int(input("Insert number of vertices: "))
            val_edge = int(input("Insert number of edges: "))
            
            
            graph = Graph(val_vert)
            
            for i in range (int(val_edge)):
                        
                edge_list = list(input("Edge: ").split())
                    
                if int(edge_list[0]) > 0 and int(edge_list[1]) > 0 and float(edge_list[2]) > 0:
                    #input values  from 0 in output plus 1
                    graph.addEdge(int(edge_list[0])-1, int(edge_list[1])-1, float(edge_list[2]))

                    if int(edge_list[0]) not in vertices:
                        vertices.append(int(edge_list[0]))
                    if int(edge_list[1]) not in vertices:
                        vertices.append(int(edge_list[1]))
                    
                else:
                    print("Wrong input")
            #check for invalid number of nodes and edges
 
            graph.PrimMST()    
            
        
        #kruskal is ready

        if sys.argv[1] == "-k":



            edges = []
            vertices = []
            blue_edges =[]

            val_vert = int(input("Insert number of vertices: "))
            val_edge = int(input("Insert number of edges: "))
            
            d = int(val_edge)
            p_queue = Heapq(2)

            
            for i in range (int(val_edge)):
                        
                edge_list = list(input("Edge: ").split())
                    
                if int(edge_list[0]) > 0 and int(edge_list[1]) > 0 and float(edge_list[2]) >= 0:
                    #input values  
                    edge = Edge(int(edge_list[0]), int(edge_list[1]), float(edge_list[2]))
                    edges.append(edge)
                    if int(edge_list[0]) not in vertices:
                        vertices.append(int(edge_list[0]))
                    if int(edge_list[1]) not in vertices:
                        vertices.append(int(edge_list[1]))
                    
                else:
                    print("Wrong input")
            #check for invalid number of nodes and edges
            if(len(vertices) <= 0):
                print("Not enough number of nodes")
                exit(0)
            if(len(edges) <= 0):
                print("Not enough number of edges")
                exit(0)

            #here is magic
            #create priority (d-heap) from edges
            p_queue.make_heap(edges)
            
            #p_queue.__repr__()
            #create nodes with rooted tree set
            treeset = TreeSet()
            for i in vertices:
                treeset.make_set(i)

            #compute min spanning tree starting from smallest edge weight
            min_cost = 0
            while p_queue.size() != 0:
                edge_i = p_queue.find_min()
                if treeset.find(edge_i.u) != treeset.find(edge_i.v):
                    treeset.union(treeset.find(edge_i.u),treeset.find(edge_i.v))
                    blue_edges.append(edge_i)
                    min_cost = edge_i.weight + min_cost
                p_queue.delete_min()

            #here ends and starts normal life 
            
            print("c Total cost of tree ", min_cost)
            for i in blue_edges:
                print (i)
        #kruskal is ready




'''
    val_vert = int(input("Insert number of vertices: "))
    val_edge = int(input("Insert number of edges: "))
    
    list_vert = []
    list_edges = []
    
    for i in range (int(val_edge)):
                
        edge = list(input("Edge: ").split())
            
        if int(edge[0]) > 0 and int(edge[1]) > 0 and int(edge[2]) > 0:
            list_edges.append([int(edge[0]),int(edge[1]),int(edge[2])])
            list_vert.append([int(edge[0])])
        else:
            print("Wrong input")
            
    vert_start = int(input("Start vertex: "))
    
    start = time.time()
    output, ways = generate_output(list_edges, int(val_vert), int(vert_start))
    end = time.time()
    
    time_of_program = (end - start)
    
    for item in output:
        print(item)
        
    print("Exact ways to all vertex from vertex: "+ str(vert_start))
    
    for i in ways:
        print(i)
        
    print("Program time: ", time_of_program*1000, "ms")   
'''    
    
