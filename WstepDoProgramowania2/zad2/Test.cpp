#include <iostream>
#include <vector>

using namespace std;

class LiczbyPierwsze {
    public:
        std::vector<int> pierwsze;
        LiczbyPierwsze(int n);
        ~LiczbyPierwsze();
        virtual int liczba (int k);
        int s=0;
};
LiczbyPierwsze::LiczbyPierwsze(int n) {
    pierwsze=std::vector<int>(n);
    for(int i=1; i < n; i++) {
        int d=0;
        for(int j=1; j < i; j++){
            if(i%j==0)
                d++;
        }
        if(d==1) {
            pierwsze[s]=i;
            s++;
        }                
    }
}
int LiczbyPierwsze::liczba(int k) {
        return pierwsze[k-1];
    }
int main(int argc, char *argv[]) {
    int n= stoi(argv[1]);
    if (n>=0) {
        int k;
        LiczbyPierwsze *p = new LiczbyPierwsze(n);
        for(int i=2; i < argc; i++){
            try {
                k= stoi(argv[i]);
                if(k<0)
                    cout << argv[i] << "; Nie jest dodatnia" << endl;
                else if (k > p->s)
                    cout << argv[i] << "; Liczba spoza zakresu" << endl;
                else
                    cout << argv[i] << "; " << p->liczba(k) << endl;
            }
            catch(invalid_argument &ia) {
                cout << argv[i] << "; Nie jest liczba calkowita" << endl;
            }
        }
    }
    else if (n<0) {
        try {
            n= stoi(argv[1]);
            cout << argv[1] << "; Nie jest dodatnia" << endl;
        }
        catch(invalid_argument &ia) {
            cout << argv[1] << "; Nie jest liczba calkowita" << endl;
        }
    }
}
LiczbyPierwsze::~LiczbyPierwsze() {
}


