#include <iostream>
#include <string>
#include <sstream>
#include <vector>
#include <chrono>
#include <ctime>
#include "algorithms.h"
#include "common.h"


#define ASC ">="
#define DESC "<="

using namespace std;
using namespace std::chrono;

void sortParam(string type, string comp, int *arr, int n) {
    if(comp == DESC) {
        for(int i=0; i<n; i++) {        //jesli sorotwanie ma byc malejace to najpierw tablica zamieniana jest na wartosci przeciwne
            arr[i] = -arr[i];
        }
    }
    high_resolution_clock::time_point t1, t2;
    t1 = high_resolution_clock::now();          //sortowanie i mierzenie czasu
    if (type == "merge") {
        mergeSort(arr, 0, n-1);
    } else if (type == "insert") {
        insertionSort(arr, n);
    } else if (type == "quick") {
        quickSort(arr, 0, n-1);
    } else if (type == "mquick") {
        mquickSort(arr, 0, n-1);
    } else if (type == "dpquick") {
        dpquickSort(arr, 0, n-1);

    //uzupelnienie
    /******************************/
    } else if (type == "radix") {
        radixSort(arr, n);
    }    
    /******************************/
    
    t2 = high_resolution_clock::now();
    auto duration = duration_cast<nanoseconds>( t2 - t1 ).count();

    cout << "Porownania: " << comparisons << " przestawienia: " << swapCount << " czas: " << duration << " [nanosekund]" << endl;

    if(comp == DESC) {
        for(int i=0; i<n; i++) {            //ponowna zmiana wartosci jesli tablica ma byc malejaca
            arr[i] = -arr[i];               //cala zamiana ma koszt O(2n) wiec i tak jest pomijalna z kazdym algorytmem
        }
    }
    cout << "Liczba elementow: " << n << "\nPosortowana tablica:" << endl;
    int i = 0;
    while (i < n) {
        cout << arr[i] << " ";
        i++;
    }

    bool err = false;
    for(int k = 1; k < n; k++) {                    //sprawdzanie czy tablica jest dobrze posortowana
        if(comp == ASC) {
            if(arr[k-1] > arr[k]) {         
                cout << "\nTablica zle posortowana" << endl;
                err = true;
                break;
            }
        } else if(comp == DESC) {
            if(arr[k-1] < arr[k]) {
                cout << "\nTablica zle posortowana" << endl;
                err = true;
                break;
            }
        }
    }
    if(!err) cout << "\nTablica dobrze posortowana" << endl;
}


int main(int argc, char* argv[]) {
    int n = 0, k = 0;
    string type = "quick", comp = ">=", file = "plik.txt";
    bool stat = false;
    for(int i = 0; i < argc; i++) {         //pobieranie parametrow
        if((string)argv[i] == "--type") {
            type = argv[i+1];
        } else if((string)argv[i] == "--comp") {
            comp = argv[i+1];
        } else if((string)argv[i] == "--stat") {
            file = argv[i+1];
            k = atoi(argv[i+2]);
            stat = true;
        }
    }
    if(!stat) {                     //pobieranie listy zmiennych
        string param;
        cout << "Liczba elementow do posortowania:" << endl;
        getline(cin, param);
        n = stoi(param);
        cout << "Ciag:" << endl;
        getline(cin, param);
        int *arr = new int[n];
        int j = 0;
        stringstream stream(param);
        while(j < n) {
            stream >> arr[j];
            j++;
        }
        sortParam(type, comp, arr, n);
    } else {
        statSort(type, k, file);            //zapisywanie do pliku
    }
}