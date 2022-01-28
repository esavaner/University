#include <iostream>
#include "queue.h"

using namespace std;

int main() {
    string s;
    int m, x, p;
    queue Q;
    cout << "Liczba operacji: " << endl;
    cin >> m;
    cout << "Operacje " << endl;
    for(int i=0; i<m; i++) {
        cin >> s;
        if(s == "insert") {
            cout << "x, p:" << endl;
            cin >> x >> p;
            Q.insert(x, p);
        } else if (s == "empty") {
            cout << Q.empty() << endl;
        } else if (s == "top") {
            Q.top();
        } else if (s == "pop") {
            Q.pop(true);
        } else if (s == "priority") {
            cout << "x, p:" << endl;
            cin >> x >> p;
            Q.priority(x, p);
        } else if (s == "print") {
            Q.print();
        } else
            cout << "Nie ma takiej operacji" << endl;
    }
    return 0;
}