import time
import sys
import math


class FibonacciHeap:
    """
    constructor of the class to initialize
    root_list, a pointer to the min in the root_list, and
    total number of nodes, in the heap as node_count
    """
    root_list = None
    min = None
    node_count = 0

    class FibonacciNode:
        """
        constructor of the FibonacciNode class
        to initialize the node properties
        """
        def __init__(self, key, value=None):
            self.key = key
            self.value = value
            self.degree = 0
            self.mark = False
            self.parent = None
            self.child = None
            self.left = None
            self.right = None

    def find_min(self):
        """
        O(1) pointer to minimum element of the heap, return it
        :return: the minimum element
        """
        return self.min

    def insert(self, key, value=None):
        """
        :param key: The Key of the node
        :param value: The Payload of the node
        :return: The new node
        Operated in O(1) time.
        creates a node, add it to the root_list.
        checks and updates Min in the root_list if necessary
        """
        new_node = self.FibonacciNode(key, value)
        new_node.left = new_node
        new_node.right = new_node
        if self.root_list is None:
            self.root_list = new_node
        else:
            new_node.right = self.root_list.right
            new_node.left = self.root_list
            self.root_list.right.left = new_node
            self.root_list.right = new_node
        if self.min is None:
            self.min = new_node
        elif new_node.key < self.min.key:
            self.min = new_node
        self.node_count += 1
        return new_node

    def update_cut(self, node):
        """
        updating the parent node promoting
        :param node:
        :return:
        """
        par = node.parent
        if par:
            if node.mark:
                self.cut(node, par)
                self.update_cut(par)
            else:
                node.mark = True

    def cut(self, node, p):
        """
        cut the subtree and promote it to the root_list
        if necessary, update the min in O(1)
        :param node: node
        :param p: parent
        :return:
        """
        if p.child == p.child.right:
            p.child = None
        elif p.child == node:
            p.child = node.right
            node.right.parent = p
        node.left.right = node.right
        node.right.left = node.left
        p.degree -= 1
        self.node_join_root_list(node)
        node.parent = None
        node.mark = False

    def extract_min(self):
        """
        extract and delete the min element in
        O(log n) time.
        also attach the children of the extracted node
        to the root_list
        :return: the min value
        """
        min_value = self.find_min()
        if min_value:
            if min_value.child:
                children_list = [_ for _ in root_list_iter(min_value.child)]
                for i in range(len(children_list)):
                    children_list[i].parent = None
                    self.node_join_root_list(children_list[i])
	     # to remove min from root
            if min_value == self.root_list: 
                self.root_list = min_value.right
            min_value.left.right = min_value.right
            min_value.right.left = min_value.left
            if min_value != min_value.right:
                self.min = min_value.right
                self.consolidate()
            else:
                self.min = self.root_list = None
            self.node_count -= 1
        return min_value

    def decrease_key(self, node, new_key):
        """
        to decrease the key of an element in the heap
        if it does not violate the heap property,
        leave it as it is,
        otherwise, cut the node
        and promote it
        :param node:
        :param new_key:
        :return:
        """
        if new_key <= node.key:
            node.key = new_key
            par = node.parent
            if par and node.key < par.key:
                self.cut(node, par)
                self.update_cut(par)
            if node.key < self.min.key:
                self.min = node
        else:
            return None

    def consolidate(self):
        """
        Fibonacci heaps run a consolidate operation only after a call to
        extract-min.
        its time complexity is O(log n) amortized.
        fibonacci_heap cannot have more than 1 of
        same degree binomial_tree in the root_list, so it combines the
        root nodes of equal degree by creating a list
        :return:
        """
        node_list = [_ for _ in root_list_iter(self.root_list)]
        consolidate_list = [None] * self.node_count
        for _ in range(len(node_list)):
            current = node_list[_]
            degree = current.degree
            while consolidate_list[degree]:
                node = consolidate_list[degree]
                if current.key > node.key:
                    my_var = current
                    current = node
                    node = my_var
                # to remove from the root
                if node == self.root_list:
                    self.root_list = node.right
                # to link the node in the root_list, update node.child
                node.left.right = node.right
                node.right.left = node.left
                node.left = node.right = node
                # to join a node
                # with the child list of root node
                if current.child:
                    node.right = current.child.right
                    node.left = current.child
                    current.child.right.left = node
                    current.child.right = node
                else:
                    current.child = node
                current.degree += 1
                node.parent = current
                node.mark = False
                consolidate_list[degree] = None
                degree += 1
            consolidate_list[degree] = current
        for i in range(len(consolidate_list)):
            # update the min  while we are iterating
            if consolidate_list[i] and self.min.key > consolidate_list[i].key:
                self.min = consolidate_list[i]

    def node_join_root_list(self, node):
        """
        to join a node to the root_list, which is a
        doubly linked list in a constant time
        :param node: a Fibonacci Node
        :return:
        """
        if self.root_list:
            node.left = self.root_list
            node.right = self.root_list.right
            self.root_list.right.left = node
            self.root_list.right = node
        else:
            self.root_list = node


def root_list_iter(start):
    """
    to go through the doubly linked list and
    stop when it reaches the start point
    :param start:
    :return:
    """
    my_arr = []
    current = start
    end = start
    flag = False
    while True:
        if current == end and flag is True:
            break
        elif current == end:
            flag = True
        my_arr.append(current)
        current = current.right
    return my_arr


def read_edges_file(input_edges, vertices):
    """
    read the text file into an array which is our graph
    according to the assignment,
    An input  file containing the (non-negative) weighted
    undirected graph information. so there is no need for
    error handling such as checking if the edges are non-negative or
    the graph is a connected graph or not
    :param input_file: a text file
    :return:
    """
    number_of_vertices = vertices + 1
    graph = [[] for _ in range(number_of_vertices)]
    for k in input_edges:        
        graph[int(k[0])].append((int(k[1]), float(k[2])))
        
    return graph


def dijkstra(my_graph, start_vert):
    """
    find the shortest path from node 1 which is the source
    to each vertices in O(log n) using greedy algorithm
    :param my_graph:
    :return: a list, each position is the distance of a node
            from the source
    """
    # initialize: make a heap, each node's distance
    # is set to infinite initially
    # then visited is false for each node
    fibonacci_heap = FibonacciHeap()
    nodes_counts = len(my_graph)
    node_list = [None] * nodes_counts#!!!!!
    visited = [-1] * nodes_counts
    shortest_from_source = [math.inf] * nodes_counts
    shortest_from_source[start_vert] = 0
    list_of_way = []
    list_of_ways = []
    counter = 1
    for j in range(1, nodes_counts):
        if j == start_vert:
            node_list[j] = fibonacci_heap.insert(0, j)
        else:
            node_list[j] = fibonacci_heap.insert(j, j)    
    # for each node in the fibonacci heap: while there is any
    while fibonacci_heap.node_count:
        current = fibonacci_heap.extract_min().value
        
        visited[current] = 1
        for (vertex, edge) in my_graph[current]:
            if visited[vertex]  == -1 and shortest_from_source[current] + edge < shortest_from_source[vertex]:
                shortest_from_source[vertex] = shortest_from_source[current] + edge
                
                #if counter == 1:
                #    list_of_way.append([[current, vertex], edge])
                #elif current != list_of_way[-1][0][0]:
                list_of_way.append([[current, vertex], edge])
                
                if counter == 1:
                    list_of_ways.append([[current, vertex], edge])
                
                for ite in list_of_ways:
                    if ite[0][1] == current:
                        if [[current, vertex], edge] not in list_of_ways:
                            list_of_ways.append([[current, vertex], edge])
                    else:
                        if [[current, vertex], edge] not in list_of_ways:
                            list_of_ways.append([[current, vertex], edge])
                counter += 1
                
                fibonacci_heap.decrease_key(node_list[vertex], shortest_from_source[vertex])
    return shortest_from_source, list_of_ways


def generate_output(edges_list, vertices, start_vert):
    """
    to generate the output
    :param input_file:
    :return:
    """
    the_graph = read_edges_file(edges_list, vertices)    
    distance_list, way_list = dijkstra(the_graph, start_vert)
    output = []
    for i in range(1, len(distance_list)):
        output.append(str(str(i) + " " + str(distance_list[i])))
    return output, way_list


if __name__ == '__main__':

    val_vert = int(input("Insert number of vertices: "))
    val_edge = int(input("Insert number of edges: "))
    
    list_vert = []
    list_edges = []
    
    for i in range (int(val_edge)):
                
        edge = list(input("Edge: ").split())
            
        if int(edge[0]) > 0 and int(edge[1]) > 0 and float(edge[2]) > 0:
            list_edges.append([int(edge[0]),int(edge[1]),float(edge[2])])
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
