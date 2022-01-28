#ifndef ZAD4_RBTREE_H
#define ZAD4_RBTREE_H

#include <iostream>

using namespace std;

struct nodeRB {
    nodeRB * up;
    nodeRB * left;
    nodeRB * right;
    string value;
    char color;
};

class rbtree {
private:
    nodeRB S;
public:
    nodeRB *findRBT(nodeRB *&root, string s);
    nodeRB *minRBT(nodeRB * p);
    nodeRB *succRBT(nodeRB * p);
    void rot_L(nodeRB *&root, nodeRB * A);
    void rot_R(nodeRB *&root, nodeRB * A);
    void insertRB(nodeRB *&root, string s);
    void deleteRB(nodeRB *&root, nodeRB * X);
    void search(nodeRB *&root, string s);
    void load(nodeRB *&root, string filename);
    void inorderRB(nodeRB *&p);
};
#endif //ZAD4_RBTREE_H
