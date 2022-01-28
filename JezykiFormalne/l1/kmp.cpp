#include <iostream>
#define MAX_WORD_LENGTH 256

using namespace std;

void cp_function(string P, int *pi) {
    int m = P.length();
    int k = 0;
    pi[0] = 0;
    int i = 1;
    while(i<m) {
        if(P[i] == P[k]) {
            k++;
            pi[i] = k;
            i++;
        } else {
            if(k != 0)
                k = pi[k-1];
            else {
                pi[i] = 0;
                i++;
            }
        }
    }
}

void kmpmatcher(string T, string P) {
    int m = P.length();
    int n = T.length();
    int pi[m];
    cp_function(P, pi);
    int i = 0;
    int j = 0;
    while(i<n) {
        if(P[j] == T[i]) {
            i++;
            j++;
        }
        if(j == m) {
            cout << "Znaleziono wzorzec na indeksie " << i-j << endl;
            j = pi[j-1];
        } else if (i<n && P[j] != T[i]) {
            if(j != 0)
                j = pi[j-1];
            else
                i++;
        }
    }
}
