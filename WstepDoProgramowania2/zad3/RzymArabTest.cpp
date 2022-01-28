#include <iostream>
#include <string>
#include <algorithm>
#include <vector>

using namespace std;

struct Wyjatek : public std::exception {
    public:
    const char* what() const throw () {
        return "; Zły zapis";
    }
};
class RzymArab {
    public:
    static int rzym2arab(string rzym) {
        int arabtab[]= {1000,900,500,400,100,90,50,40,10,9,5,4,1};
        const char * const rzymst[]= {"M","CM","D","CD","C","XC","L","XL","X","IX","V","IV","I"};
        int i=0;
        int wynik=0;
        int counter = 0;
        while(i < rzym.length()) {
            int n = RzymArab::rzymtoarab(rzym.at(i));
            if(n ==0)
                throw Wyjatek(); 
            i++;
            if(i==rzym.length())
                wynik = wynik + n;
            else {
                int nastepny = RzymArab::rzymtoarab(rzym.at(i));
                if(nastepny == n) {
                    counter += 1;
                    if(counter > 2)
                        throw Wyjatek();
                }
                else {
                    counter = 0;
                }
                if(nastepny > n) {
                    if(nastepny > 10*n)
                        throw Wyjatek();
                    wynik = wynik + nastepny - n;
                    i++;
                    if(i<rzym.length()) {
                        int nastepny2 = rzymtoarab(rzym.at(i));
                        if(nastepny2 == n)
                            throw Wyjatek();
                    }
                }
                else
                    wynik = wynik + n;
            }
        }
        if(wynik > 3999)
            throw Wyjatek();
        return wynik;
    }
    static string arab2rzym(int n) {
        int arabtab[]= {1000,900,500,400,100,90,50,40,10,9,5,4,1};
        const char * const rzymst[]= {"M","CM","D","CD","C","XC","L","XL","X","IX","V","IV","I"};
        string wynik = "";
        if(n <= 3999) {
            int s=0;
            while(n > 0) {
                if(n >= arabtab[s]) {
                    wynik = wynik + rzymst[s];
                    n = n - arabtab[s];
                }
                else if(s<12)
                    s++;
            }
        }
        else 
            throw Wyjatek();
        return wynik;
    }
    static int rzymtoarab(char r) {
        switch(r) {
            case 'M' : return 1000;
            case 'D' : return 500;
            case 'C' : return 100;
            case 'L' : return 50;
            case 'X' : return 10;
            case 'V' : return 5;
            case 'I' : return 1;
            default : return 0;
        }
    }
};

int main(int argc, char *argv[]) {
    for(int i=1; i < argc; i++) {
        try {
            int n = stoi(argv[i]);
            if(n < 0)
                cout << argv[i] << "; Musi byc dodatnia" << endl;
            else if(n >= 4000)
                cout << argv[i] << "; Za duza liczba" << endl;
            else
                cout << argv[i] << "; " << RzymArab::arab2rzym(n) << endl;
        }
        catch (invalid_argument &ia) {
            try {
                std::string rzym = argv[i];
                std::transform(rzym.begin(), rzym.end(), rzym.begin(), ::toupper);
                cout << rzym << "; " << RzymArab::rzym2arab(rzym) << endl;
            }
            catch (std::exception& ex) {            
                cout << argv[i] << ex.what() << endl;
            }
            catch (...) {            
                cout << argv[i] << "; Złe dane" << endl;
            }
        }
    }
}
