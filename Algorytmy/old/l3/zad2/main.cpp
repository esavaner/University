#include <iostream>
#include <list>
#include "queue.h"

using namespace std;
const int MAXINT = 21474836;

struct neigh {
    neigh * next;
    int v,w;
};

int main() {
    int n, m, u, v, w, start, *prev, *S, *d, sptr=0;
    neigh **graph;
    neigh *nh, *nr;
    queue Q;
    cout << "Liczba wierzcholkow: " << endl;
    cin >> n;
    cout << "Liczba krawedzi: " << endl;
    cin >> m;
    prev = new int [n];
    graph = new neigh * [n];
    S = new int [n];
    d = new int [n];
    for(int i=0; i<n; i++) {
        prev[i] = -1;
        graph[i] = nullptr;
    }

    cout << "Defnicje krawedzi: " << endl;
    for(int i=0; i<m; i++) {
        cin >> u >> v >> w;
        Q.insert(u, MAXINT);
        Q.insert(v, MAXINT);
        nh = new neigh;
        nh->v = v;
        nh->w = w;
        nh->next = graph[u];
        graph[u] = nh;
    }
    cout << "Wierzcholek startowy: " << endl;
    cin >> start;
    Q.priority(start, 0);
    while(!Q.empty()) {
        node *p = Q.pop(false);
        if(!p)
            break;
        for(nr = graph[p->value]; nr; nr = nr->next) {
            if(Q.read(nr->v) && Q.read(nr->v)->prio > p->prio + nr->w) {
                Q.priority(nr->v, p->prio + nr->w);
                prev[nr->v] = p->value;
            }
            d[p->value] = p->prio;
        }
    }

    for(int k=0; k<n; k++) {
        cout << k << ": ";
        for(int j= k; j > -1; j = prev[j]) S[sptr++] = j;
        while(sptr) cout << S[--sptr] << "-";
        cout << "koszt: " << d[k] << endl;
    }
    return 0;
}