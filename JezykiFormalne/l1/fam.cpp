#include <iostream>
#define MAX_WORD_LENGTH 256
using namespace std;
//string T        tekst do przeszukania
//string P        wzorzec
//int q           stan automatu
//int m           dlugosc wzorca
//int n           dlugosc tekstu
//int TFP[][]     tablica funkcji przejsc

void ct_function(string P, int m, int TFP[][MAX_WORD_LENGTH]) {
    int q, k = 0;
    for(q=0; q<MAX_WORD_LENGTH; q++)
        TFP[0][q] = 0;
    TFP[0][P[0]] = 1;
    for(q=1; q<=m; q++) {
        for(int a=0; a<MAX_WORD_LENGTH; a++) {
            TFP[q][a] = TFP[k][a];
        }
        TFP[q][P[q]] = q + 1;
        if(q < m) {
            k = TFP[k][P[q]];
        }
    }

}

void fa_matcher(string T, string P) {
    int m = P.length();
    int n = T.length();
    int q = 0;
    int TFP[m+1][MAX_WORD_LENGTH];
    ct_function(P, m, TFP);

    for(int i=0; i<n; i++) {
        q = TFP[q][T[i]];
        if(q == m)
            cout << "Znaleziono wzorzec na indeksie " << i-m+1 << endl;
    }
}

