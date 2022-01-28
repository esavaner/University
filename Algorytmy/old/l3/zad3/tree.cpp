#include "tree.h"
#include <iostream>

using namespace std;

tree::tree(int n) {
    int i;
    treeList = new treenode * [n];
    for(i = 0; i < n; i++) treeList[i] = nullptr;
    treeLength = n - 1;
    weight = 0;
}

tree::~tree() {
    int i;
    treenode *p,*r;
    for(i = 0; i <= treeLength; i++) {
        p = treeList[i];
        while(p) {
            r = p;
            p = p->next;
            delete r;
        }
    }
    delete [] treeList;
}

void tree::addEdge(edge e) {
    treenode *p;
    weight += e.w;
    p = new treenode;
    p->v = e.v2;
    p->weight = e.w;
    p->next = treeList[e.v1];
    treeList[e.v1] = p;
    p = new treenode;
    p->v = e.v1;
    p->weight = e.w;
    p->next = treeList[e.v2];
    treeList[e.v2] = p;
}

void tree::print() {
    int i;
    treenode *p;
    cout << endl;
    for(i = 0; i <= treeLength; i++) {
        for(p = treeList[i]; p; p = p->next)
            cout << i << "-" << p->v << " w:" << p->weight << " ";
        cout << endl;
    }
    cout << endl << "Minimalne drzewo rozpinajace: " << weight << endl;
}

treenode * tree::getNode(int n) {
    return treeList[n];
}