#include "rbtree.h"
#include <iostream>
#include <string>
#include <cstdlib>
#include <ctime>
#include <fstream>
#include <queue>

using namespace std;


enum COLOR {RED, BLACK};

class Node {
public:
    int val;
    COLOR color;
    Node *left, *right, *parent;

    Node(int val) : val(val) {
        parent = left = right = nullptr;
        color = RED;
    }
    Node *uncle() {
        if (!parent or !parent->parent)
            return nullptr;

        if (parent->isOnLeft())
            return parent->parent->right;
        else
            return parent->parent->left;
    }
    bool isOnLeft() { return this == parent->left; }
    Node *sibling() {
        if (!parent)
            return nullptr;

        if (isOnLeft())
            return parent->right;

        return parent->left;
    }
    void moveDown(Node *nParent) {
        if (parent) {
            if (isOnLeft()) {
                parent->left = nParent;
            } else {
                parent->right = nParent;
            }
        }
        nParent->parent = parent;
        parent = nParent;
    }

    bool hasRedChild() {
        return (left and left->color == RED) or
               (right and right->color == RED);
    }
};

class RBTree {
    Node *root;
    void leftRotate(Node *x) {
        Node *nParent = x->right;
        if (x == root)
            root = nParent;
        x->moveDown(nParent);
        x->right = nParent->left;
        if (nParent->left)
            nParent->left->parent = x;
        nParent->left = x;
    }

    void rightRotate(Node *x) {
        Node *nParent = x->left;
        if (x == root)
            root = nParent;

        x->moveDown(nParent);
        x->left = nParent->right;
        if (nParent->right)
            nParent->right->parent = x;
        nParent->right = x;
    }

    void swapColors(Node *x1, Node *x2) {
        COLOR temp;
        temp = x1->color;
        x1->color = x2->color;
        x2->color = temp;
    }

    void swapValues(Node *u, Node *v) {
        int temp;
        temp = u->val;
        u->val = v->val;
        v->val = temp;
    }
    void fixRedRed(Node *x) {
        if (x == root) {
            x->color = BLACK;
            return;
        }
        Node *parent = x->parent, *grandparent = parent->parent,
                *uncle = x->uncle();

        if (parent->color != BLACK) {
            if (uncle && uncle->color == RED) {
                parent->color = BLACK;
                uncle->color = BLACK;
                grandparent->color = RED;
                fixRedRed(grandparent);
            } else {
                if (parent->isOnLeft()) {
                    if (x->isOnLeft()) {
                        swapColors(parent, grandparent);
                    } else {
                        leftRotate(parent);
                        swapColors(x, grandparent);
                    }
                    rightRotate(grandparent);
                } else {
                    if (x->isOnLeft()) {
                        rightRotate(parent);
                        swapColors(x, grandparent);
                    } else {
                        swapColors(parent, grandparent);
                    }
                    leftRotate(grandparent);
                }
            }
        }
    }

    Node *successor(Node *x) {
        Node *temp = x;

        while (temp->left)
            temp = temp->left;

        return temp;
    }

    Node *BSTreplace(Node *x) {
        if (x->left and x->right)
            return successor(x->right);

        if (!x->left and !x->right)
            return nullptr;

        if (x->left)
            return x->left;
        else
            return x->right;
    }

    void deleteNode(Node *v) {
        Node *u = BSTreplace(v);
        bool uvBlack = ((!u or u->color == BLACK) and (v->color == BLACK));
        Node *parent = v->parent;

        if (!u) {
            if (v == root) {
                root = NULL;
            } else {
                if (uvBlack) {
                    fixDoubleBlack(v);
                } else {
                    if (v->sibling() != NULL)
                        // sibling is not null, make it red"
                        v->sibling()->color = RED;
                }
                if (v->isOnLeft()) {
                    parent->left = NULL;
                } else {
                    parent->right = NULL;
                }
            }
            delete v;
            return;
        }

        if (!v->left or !v->right) {
            if (v == root) {
                v->val = u->val;
                v->left = v->right = nullptr;
                delete u;
            } else {
                if (v->isOnLeft())
                    parent->left = u;
                else
                    parent->right = u;
                delete v;
                u->parent = parent;
                if (uvBlack)
                    fixDoubleBlack(u);
                else
                    u->color = BLACK;
            }
            return;
        }
        swapValues(u, v);
        deleteNode(u);
    }

    void fixDoubleBlack(Node *x) {
        if (x == root)
            return;

        Node *sibling = x->sibling(), *parent = x->parent;
        if (!sibling) {
            fixDoubleBlack(parent);
        } else {
            if (sibling->color == RED) {
                parent->color = RED;
                sibling->color = BLACK;
                if (sibling->isOnLeft()) {
                    rightRotate(parent);
                } else {
                    leftRotate(parent);
                }
                fixDoubleBlack(x);
            } else {
                if (sibling->hasRedChild()) {
                    if (sibling->left and sibling->left->color == RED) {
                        if (sibling->isOnLeft()) {
                            sibling->left->color = sibling->color;
                            sibling->color = parent->color;
                            rightRotate(parent);
                        } else {
                            sibling->left->color = parent->color;
                            rightRotate(sibling);
                            leftRotate(parent);
                        }
                    } else {
                        if (sibling->isOnLeft()) {
                            sibling->right->color = parent->color;
                            leftRotate(sibling);
                            rightRotate(parent);
                        } else {
                            sibling->right->color = sibling->color;
                            sibling->color = parent->color;
                            leftRotate(parent);
                        }
                    }
                    parent->color = BLACK;
                } else {
                    sibling->color = RED;
                    if (parent->color == BLACK)
                        fixDoubleBlack(parent);
                    else
                        parent->color = BLACK;
                }
            }
        }
    }

    void levelOrder(Node *x) {
        if (!x)
            return;
        queue<Node *> q;
        Node *curr;
        q.push(x);
        while (!q.empty()) {
            curr = q.front();
            q.pop();
            cout << curr->val << " ";
            if (curr->left)
                q.push(curr->left);
            if (curr->right)
                q.push(curr->right);
        }
    }

    void inorder(Node *x) {
        if (!x)
            return;
        inorder(x->left);
        cout << x->val << " ";
        inorder(x->right);
    }

public:
    RBTree() { root = nullptr; }

    Node *getRoot() { return root; }

    Node *search(int n) {
        Node *temp = root;
        while (temp) {
            if (n < temp->val) {
                if (!temp->left)
                    break;
                else
                    temp = temp->left;
            } else if (n == temp->val) {
                break;
            } else {
                if (!temp->right)
                    break;
                else
                    temp = temp->right;
            }
        }

        return temp;
    }

    void insert(int n) {
        Node *newNode = new Node(n);
        if (!root) {
            newNode->color = BLACK;
            root = newNode;
        } else {
            Node *temp = search(n);
            if (temp->val == n)
                return;

            newNode->parent = temp;

            if (n < temp->val)
                temp->left = newNode;
            else
                temp->right = newNode;

            fixRedRed(newNode);
        }
    }

    void deleteByVal(int n) {
        if (!root)
            // Tree is empty
            return;

        Node *v = search(n), *u;

        if (v->val != n) {
            cout << "No node found to delete with value:" << n << endl;
            return;
        }

        deleteNode(v);
    }

    void printInOrder() {
        cout << "Inorder: " << endl;
        if (!root)
            cout << "Tree is empty" << endl;
        else
            inorder(root);
        cout << endl;
    }

    void printLevelOrder() {
        cout << "Level order: " << endl;
        if (!root)
            cout << "Tree is empty" << endl;
        else
            levelOrder(root);
        cout << endl;
    }
};

void rbtree::load(nodeRB *&root, string filename) {
    string s;
    ifstream file;
    file.open(filename);
    if (!file) {
        cerr << "Unable to open file " << filename;
        return;
    }
    while(file >> s) {
        //insertRB(root, s);
    }
    file.close();
}

void rbtree::inorderRB(nodeRB *&p) {
    if(!p)
        return;
    inorderRB(p->left);
    cout << p->value << " ";
    inorderRB(p->right);
}

nodeRB *rbtree::findRBT(nodeRB *&root, string s) {}
nodeRB *rbtree::minRBT(nodeRB * p) {}
nodeRB *rbtree::succRBT(nodeRB * p) {}
void rbtree::rot_L(nodeRB *&root, nodeRB * A) {}
void rbtree::rot_R(nodeRB *&root, nodeRB * A) {}
void rbtree::insertRB(nodeRB *&root, string s) {}
void rbtree::deleteRB(nodeRB *&root, nodeRB * X) {}
void rbtree::search(nodeRB *&root, string s) {}