#ifndef ZAD3_3_COLLECTION_H
#define ZAD3_3_COLLECTION_H

#include "tree.h"

struct collectionNode {
    int up,rank;
};

class collection {
private:
    collectionNode * c;
public:
    collection(int n);
    ~collection();
    void MakeSet(int v);
    int FindSet(int v);
    void UnionSets(edge e);
};

#endif //ZAD3_3_COLLECTION_H