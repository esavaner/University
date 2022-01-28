
//kompilować używając -std=c++11
#include <iostream>
#include <cmath>
#include <string>

using namespace std;

class Figury {
    public:
        virtual double pole()=0;
        virtual double obw()=0;
        double bok;
};
class Czworokat : public Figury {
    public:
        double rozmiar1(double d) {
            bok1 = d;
        }
        double rozmiar2(double d) {
            kat = d;
        }
        double rozmiar3(double d) {
            bok3 = d;
        }
    protected:
        double bok1;
        double bok3;
        double kat;
};
class Kolo : public Figury {
    public:
    double pole() { return bok*bok*M_PI; };
    double obw() { return 2*bok*M_PI; };
};
class Pieciokat : public Figury {
    public:
    double obw() { return bok*5; };
    double pole() { return bok*bok*sqrt(25 + 10*sqrt(5))/4; };
};
class Szesciokat : public Figury {
    public:
    double obw() { return bok*6; };
    double pole() { return 3*bok*sqrt(3)/2; };
};
class Kwadrat : public Czworokat {
    public:
    double obw() { return 4*bok1; };
    double pole() { return bok1*bok1; };
};
class Prostokat : public Czworokat {
    public:
    double obw() { return 2*bok1 + 2*bok3; };
    double pole() { return bok1*bok3; };
};
class Romb : public Czworokat {
    public:
    double obw() { return 4*bok1; };
    double pole() { return bok1*bok1*sin(kat*M_PI/180); };
};
int main(int argc, char* argv[]) {
        string lit = argv[1];
        int j=2;
        for(int i=0; i < lit.length(); i++) {
            try {
                char ch = lit.at(i);
                if(ch=='o') {
                    Kolo *o = new Kolo();
                    o->bok = std::stod(argv[j]);
                    if(o->bok <= 0)
                        cout << ch << ";" << o->bok << " Musi byc wieksza od 0" << endl;
                    else {    
                        cout << "Kolo; Pole=" << o->pole() << "; Obwód=" << o->obw() << endl;
                    }
                    j++;
                    if(j > argc) {
                        cout << "Za mało danych" << endl;
                        break;
                    }
                }
                else if (ch=='p') {
                    Pieciokat *p = new Pieciokat();
                    p->bok = std::stod(argv[j]);
                    if(p->bok <= 0)
                        cout << ch << ";" << p->bok << " Musi byc wieksza od 0" << endl;
                    else {   
                        cout << "Pieciokat; Pole=" << p->pole() << "; Obwód=" << p->obw() << endl;
                    }
                    j++;
                    if(j > argc) {
                        cout << "Za mało danych" << endl;
                        break;
                    }
                }
                else if (ch=='s') {
                    Szesciokat *s = new Szesciokat();
                    s->bok = std::stod(argv[j]);
                    if(s->bok <= 0)
                        cout << ch << ";" << s->bok << " Musi byc wieksza od 0" << endl;
                    else {    
                        cout <<"Szesciokat; Pole=" << s->pole() << "; Obwód=" << s->obw() << endl;
                    }
                    j++;
                    if(j > argc) {
                        cout << "Za mało danych" << endl;
                        break;
                    }
                }
                else if (ch=='c') {
                    double temp=0;
                    for(int k=0; k < 5; k++) {
                        temp = std::stod(argv[j]);
                        j++;
                        if(j >= argc) {
                            cout << "Za mało danych" << endl;
                            break;
                        }
                        if(temp<=0)
                            cout << ch << ";" << temp << " Musi byc wieksza od 0" << endl;
                    }
                    if (temp > 180)
                        cout << ch << ";" << temp << " Kat musi byc mniejszy od 180" << endl;
                    j-=5;
                    if((temp != 90) && (stod(argv[j])==stod(argv[j+1])) && (stod(argv[j+1])==stod(argv[j+2])) && (stod(argv[j+2])==stod(argv[j+3]))) {
                        Romb ro;
                        ro.rozmiar1(stod(argv[j]));
                        ro.rozmiar2(temp);
                        cout << "Romb; Pole=" << ro.pole() << "; Obwód=" << ro.obw() << endl;
                    }
                    else if((temp == 90) && (stod(argv[j])==stod(argv[j+1])) && (stod(argv[j+1])==stod(argv[j+2])) && (stod(argv[j+2])==stod(argv[j+3]))) {
                        Kwadrat kw;
                        kw.rozmiar1(stod(argv[j]));
                        cout << "Kwadrat; Pole=" << kw.pole() << "; Obwód=" << kw.obw() << endl;
                    }
                    else if((temp == 90) && (stod(argv[j])==stod(argv[j+1])) && (stod(argv[j+2])==stod(argv[j+3]))) {
                        Prostokat pr;
                        pr.rozmiar1(stod(argv[j]));
                        pr.rozmiar2(stod(argv[j+2]));
                        cout << "Prostokat; Pole=" << pr.pole() << "; Obwód=" << pr.obw() << endl;
                    }
                    else
                        cout << ch << "; Brak figury dla tych danych" << endl;
                    j+=5;
                }
                else
                    cout << ch << "; Złe dane" << endl;;
            }
            catch (invalid_argument &ia) {
                cout << argv[i] << "; Złe dane" << endl;
            }
        }        
}
