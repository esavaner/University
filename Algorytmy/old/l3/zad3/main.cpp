#include <iostream>
#include "queue.h"
#include "tree.h"
#include "collection.h"

using namespace std;

int main() {
    string type;
    int n,m;
    cout << "Typ:" << endl;
    cin >> type;
    cout << "Liczba wierzcholkow: " << endl;
    cin >> n;
    cout << "Liczba krawedzi: " << endl;
    cin >> m;
    cout << "Definicje krawedzi: " << endl;
    if(type == "--k") {
        edge e;
        collection C(n);
        queue Q(m);
        tree T(n);
        for(int i = 0; i < n; i++)
            C.MakeSet(i);
        for(int i = 0; i < m; i++) {
            cin >> e.v1 >> e.v2 >> e.w;
            Q.insert(e);
        }
        for (int i = 1; i < n; i++) {
            do {
                e = Q.front();
                Q.pop();
            } while (C.FindSet(e.v1) == C.FindSet(e.v2));
            T.addEdge(e);
            C.UnionSets(e);
        }
        T.print();
    } else if(type == "--p") {
        edge e;
        treenode * p;
        queue Q(m);
        tree T(n);
        tree G(n);
        bool * visited = new bool [n];
        for(int i = 0; i < n; i++)
            visited[i] = false;
        for(int i = 0; i < m; i++) {
            cin >> e.v1 >> e.v2 >> e.w;
            G.addEdge(e);
        }
        int v=0;
        visited[v] = true;
        for(int i = 1; i < n; i++) {
            for(p = G.getNode(v); p; p = p->next)
                if(!visited[p->v]) {
                    e.v1 = v;
                    e.v2 = p->v;
                    e.w = p->weight;
                    Q.insert(e);
                }
            do {
                e = Q.front();
                Q.pop();
            } while(visited[e.v2]);
            T.addEdge(e);
            visited[e.v2] = true;
            v = e.v2;
        }
        T.print();
    }
    return 0;
}