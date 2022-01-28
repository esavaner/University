#include <iostream>
#include <string>
#include <cstdlib>
#include <ctime>
#include <fstream>
#include "splaytree.h"

using namespace std;

nodeS * newNodeS(string value) {
    nodeS *node = new nodeS();
    node->value = value;
    node->left = node->right = nullptr;
    return node;
}

nodeS *splaytree::insertSplay(nodeS *&root, string s) {
    if (!root)
        return newNodeS(s);
    root = splay(root, s);
    nodeS *newnode = newNodeS(s);
    if (s.compare(root->value) < 0) {
        newnode->right = root;
        newnode->left = root->left;
        root->left = nullptr;
    } else {
        newnode->left = root;
        newnode->right = root->right;
        root->right = nullptr;
    }
    return newnode;
}

nodeS *splaytree::rot_L(nodeS *&x){
    nodeS *y = x->right;
    x->right = y->left;
    y->left = x;
    return y;
}

nodeS *splaytree::rot_R(nodeS *&x) {
    nodeS *y = x->left;
    x->left = y->right;
    y->right = x;
    return y;
}

nodeS *splaytree::splay(nodeS *&root, string s) {
    if (!root || root->value == s)
        return root;

    if (s.compare(root->value) < 0) {
        if (!root->left)
            return root;

        if (s.compare(root->left->value) < 0){
            root->left->left = splay(root->left->left, s);
            root = rot_R(root);
        }
        else if (s.compare(root->left->value) > 0) {
            root->left->right = splay(root->left->right, s);
            if (root->left->right)
                root->left = rot_L(root->left);
        }
        return (!root->left)? root: rot_R(root);
    } else {
        if (!root->right)
            return root;
        if (s.compare(root->right->value) < 0) {
            root->right->left = splay(root->right->left, s);
            if (root->right->left)
                root->right = rot_R(root->right);
        }
        else if (s.compare(root->right->value) > 0) {
            root->right->right = splay(root->right->right, s);
            root = rot_L(root);
        }
        return (!root->right)? root: rot_L(root);
    }
}

nodeS *splaytree::deleteSplay(nodeS *&root, string s) {
    nodeS *temp;
    if (!root)
        return nullptr;

    root = splay(root, s);

    if (!s.compare(root->value))
        return root;

    if (!root->left) {
        temp = root;
        root = root->right;
    } else {
        temp = root;
        root = splay(root->left, s);
        root->right = temp->right;
    }
    free(temp);
    return root;
}

nodeS *splaytree::search(nodeS *&root, string s) {
    return splay(root, s);
}

void splaytree::load(nodeS *&root, string filename) {
    string s;
    ifstream file;
    file.open(filename);
    if (!file) {
        cerr << "Unable to open file " << filename;
        return;
    }
    while(file >> s) {
        root = insertSplay(root, s);
    }
    file.close();
}

void splaytree::inorderSplay(nodeS *&p) {
    if(p) {
        inorderSplay(p->left);
        cout << p->value << " ";
        inorderSplay(p->right);
    }
}