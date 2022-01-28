#include "collection.h"

collection::collection(int n) {
    c = new collectionNode[n];
}

collection::~collection() {
    delete []c;
}

void collection::MakeSet(int v) {
    c[v].up   = v;
    c[v].rank = 0;
}

int collection::FindSet(int v) {
    if(c[v].up != v) c[v].up = FindSet(c[v].up);
    return c[v].up;
}

void collection::UnionSets(edge e) {
    int ru,rv;
    ru = FindSet(e.v1);
    rv = FindSet(e.v2);
    if(ru != rv) {
        if(c[ru].rank > c[rv].rank)
            c[rv].up = ru;
        else {
            c[ru].up = rv;
            if(c[ru].rank == c[rv].rank) c[rv].rank++;
        }
    }
}