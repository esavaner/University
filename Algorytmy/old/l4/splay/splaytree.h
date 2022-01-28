#ifndef ZAD4_SPLAYTREE_H
#define ZAD4_SPLAYTREE_H
using namespace std;
struct nodeS {
    nodeS *left, *right;
    string value;
};

class splaytree {
public:
    nodeS *insertSplay(nodeS *&root, string s);
    nodeS *deleteSplay(nodeS *&x, string s);
    void load(nodeS *&root, string filename);
    void inorderSplay(nodeS *&root);
    nodeS *rot_L(nodeS *&x);
    nodeS *rot_R(nodeS *&x);
    nodeS *splay(nodeS *&root, string s);
    nodeS *search(nodeS *&root, string s);
};
#endif //ZAD4_SPLAYTREE_H
