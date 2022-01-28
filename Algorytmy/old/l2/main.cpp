#include <iostream>
#include <vector>
#include <string>
#include <sstream>
#include "algorithms.h"

using namespace std;

//sort depending on type and parameters
int sortParam(vector<string> s, int arr[], int n) {
    if(s[2] == "--desc") {
        for(int i=0; i<n; i++) {
            arr[i] = -arr[i];           //invert every field in array
        }
    }
    if (s[0] == "--type") {             //sort depending on type
        if (s[1] == "select") {
            selectionSort(arr, n);
        } else if (s[1] == "insert") {
            insertionSort(arr, n);
        } else if (s[1] == "heap") {
            heapSort(arr, n);
        } else if (s[1] == "quick") {
            quickSort(arr, 0, n-1);
        } else if (s[1] == "mquick") {
            mquickSort(arr, 0, n-1);
        }
    }
    if(s[2] == "--desc") {
        for(int i=0; i<n; i++) {
            arr[i] = -arr[i];           //again invert
        }
    }
    int i = 0;
    while (i < n) {
        cout << arr[i] << " ";
        i++;
    }
}

//main function
int main() {
    string param, tok;
    cout << "Parametry:" << endl;
    getline(cin, param);                //get parameters
    stringstream sin(param);
    vector<string> internal;
    while (getline(sin, tok, ' ')) {
        internal.push_back(tok);
    }
    if(internal[0] == "--stat") {
        statSort();                     //if --stat skip everything else
    }
    else {
        cout << "Liczba elementow do posortowania:" << endl;
        getline(cin, param);            //get n
        int n = stoi(param);
        cout << "Ciag:" << endl;
        getline(cin, param);            //get array of ints
        int arr[1000];
        int j = 0;
        stringstream stream(param);
        while (stream) {
            stream >> arr[j];
            j++;
        }
        sortParam(internal, arr, n);
    }
}