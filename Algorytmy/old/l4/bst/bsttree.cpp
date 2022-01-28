#include <iostream>
#include <algorithm>
#include <fstream>
#include "bsttree.h"

using namespace std;

void bsttree::insertBST(node *&root, string s) {
    node *prev, *newN = new node;
    newN->left = newN->right = nullptr;
    newN->value = s;
    prev = root;
    if(!prev)
        root = newN;
    else
        while(true) {
            if (s.compare(prev->value) < 0) {
                if (!prev->left) {
                    prev->left = newN;
                    break;
                } else prev = prev->left;
            } else {
                if (!prev->right) {
                    prev->right = newN;
                    break;
                } else prev = prev->right;
            }
        }
    newN->up  = prev;
}

void bsttree::deleteBST(node *&root, string s) {
    node *y, *z, *x = searchBST(root, s);
    if(x) {
        if(!x->right || !x->left)
            y = x;
        else
            y = minBST(x);

        if(y->left)
            z = y->left;
        else
            z = y->right;

        if(z)
            z->up = y->up;

        if(!y->up)
            root = z;
        else if(y == y->up->left)
            y->up->left = z;
        else
            y->up->right = z;
    }
}

node *bsttree::searchBST(node *&root, string s) {
    node *next = root;
    while(next && !s.compare(next->value)) {
        if(s.compare(next->value) < 0) {
            cout << next->value;
            next = next->left;
        }
        else if (s.compare(next->value) > 0) {
            cout << next->value;
            next = next->right;
        }
    }
    return next;
}

void bsttree::load(node *&root, string filename) {
    string s;
    ifstream file;
    file.open(filename);
    if (!file) {
        cerr << "Unable to open file " << filename;
        return;
    }
    while(file >> s) {
        insertBST(root, s);
    }
    file.close();
}

void bsttree::inorderBST(node *&p) {
    if(!p)
        return;
    inorderBST(p->left);
    cout << p->value << " ";
    inorderBST(p->right);
}

node *bsttree::minBST(node *&p) {
    if(p)
        while(p->left)
            p = p->left;
    return p;
}
