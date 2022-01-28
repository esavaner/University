#include <iostream>
#include <iomanip>
#include "fam.h"
#include "kmp.h"

using namespace std;

int main() {
    string T = "AAAABABCCCABABAAAAAABAAAABAABAB";
    string P = "ABAB";
    for(int i=0; i<T.length(); i++)
        cout << T[i] << "  ";
    cout << endl;
    for(int j=0; j<T.length(); j++)
        cout << setfill('0') << setw(2) << j << " ";
    cout << endl;
    kmpmatcher(T, P);
    fa_matcher(T, P);
}



