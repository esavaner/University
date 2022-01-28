#ifndef ZAD3_3_TREE_H
#define ZAD3_3_TREE_H

#include "queue.h"
struct treenode {
    treenode * next;
    int v,weight;
};

class tree {
private:
    treenode **treeList;
    int treeLength;
    int weight;
public:
    tree(int n);
    ~tree();
    void addEdge(edge e);
    void print();
    treenode *getNode(int n);
};

#endif //ZAD3_3_TREE_H