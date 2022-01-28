#ifndef ZAD4_BSTTREE_H
#define ZAD4_BSTTREE_H
using namespace std;
struct node {
    node * up, * left, * right;
    string value;
};

class bsttree {
public:
    void insertBST(node *&root, string s);
    void deleteBST(node *&root, string s);
    node *searchBST(node *&root, string s);
    void load(node *&root, string filename);
    void inorderBST(node *&root);
    node *minBST(node *&root);
};
#endif //ZAD4_BSTTREE_H
