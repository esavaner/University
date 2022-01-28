#include <iostream>
#include <ctime>
#include <fstream>
#include <chrono>
#include <stdlib.h>
#include "common.h"

using namespace std;
using namespace std::chrono;


// uzupelnienie do zadania 1 z listy 3
/*************************************************************************/


//szukanie maksymalnej wartosci w tablicy, czas O(n)
int max(int arr[], int n) {
    int m = arr[0];
    for(int i = 1; i < n; i++) {
        if(arr[i] > m) {
            m = arr[i];
        }
    }
    return m;
}


void countSort(int arr[], int n, int exp) {
    int *output = new int[n];
    int i, count[10] = {0};

    //przechowywanie zliczonych wartosci liczb
    for (i = 0; i < n; i++) {
        count[(arr[i]/exp)%10]++; 
    }

    //zmiana miejsca w tablicy aby zawierala aktualne pozycje liczb
    for (i = 1; i < 10; i++) {
        count[i] += count[i-1]; 
    }

    //budowanie wyjsciowej tablicy
    for (i = n - 1; i >= 0; i--) { 
        output[count[(arr[i]/exp)%10]-1] = arr[i]; 
        count[(arr[i]/exp)%10]--; 
        swapCount++;
    } 

    //kopiowanie wartosci
    for (i = 0; i < n; i++) {
        arr[i] = output[i];
    }
}

//glowna funkcja sortowania
void radixSort(int arr[], int n) {

    //szukanie maksymalnej warotsci aby wiedziec jakiego rzedu sa liczby
    int m = max(arr, n);

    //dla kazdego miejsca w liczbie wykonuje countSort
    for (int exp = 1; m/exp > 0; exp *= 10) {
        countSort(arr, n, exp); 
    }
}

/*************************************************************************/

int swapCount = 0;
int comparisons = 0;
void swap(int *x, int *y) { 
    int temp = *x;              //zamiana miejscami 2 pozycji w tablici i zwiekszenie licznika zmian
    *x = *y;
    *y = temp;
    swapCount++;                             
}

void insertionSort(int arr[], int n) {
    int i, key, j;
    for (i = 1; i < n; i++) {
        key = arr[i];
        j = i - 1;
        while (j >= 0 && arr[j] > key) {        //wszystkie elementy wieksze od aktualnie wybranego sa przesuwane na prawo
            arr[j + 1] = arr[j];
            j = j - 1;
            swapCount++;
            comparisons++;
        }
        arr[j + 1] = key;
    }
}

void merge(int arr[], int left, int m, int right) { 
    int i, j, k; 
    int n1 = m - left + 1; 
    int n2 =  right - m;
    int *L = new int[n1];
    int *R = new int[n2];
    for (i = 0; i < n1; i++)        //tworzenie 2 tymczasowych tablic, lewej i prawej
        L[i] = arr[left + i]; 
    for (j = 0; j < n2; j++) 
        R[j] = arr[m + 1 + j]; 
    i = 0;
    j = 0;  
    k = left; 
    while (i < n1 && j < n2) {
        if (L[i] <= R[j]) {         //laczenie tablic sprawdzajac wartosci elementow
            arr[k] = L[i]; 
            i++; 
        } 
        else { 
            arr[k] = R[j]; 
            j++; 
        }
        comparisons++;
        swapCount++;
        k++; 
    } 
    while (i < n1) {        //kopiowanie pozostalych elementow jesli jakiekolwiek pozostaly
        comparisons++;
        swapCount++;
        arr[k] = L[i]; 
        i++; 
        k++; 
    } 
    while (j < n2) { 
        comparisons++;
        swapCount++;
        arr[k] = R[j]; 
        j++; 
        k++; 
    } 
} 
  
void mergeSort(int arr[], int left, int right) { 
    if (left < right) {
        comparisons++;                      //dzielenie tablicy na co raz mniejsze czesci, az beda pojedynczymi elementami
        int m = left + (right - left)/2; 
        mergeSort(arr, left, m); 
        mergeSort(arr, m+1, right); 
        merge(arr, left, m, right); 
    } 
} 

int partition (int arr[], int low, int high) {
    int pivot = arr[high];
    int i = (low - 1);
    for (int j = low; j <= high- 1; j++) {
        if (arr[j] <= pivot) {              //wszystkie elementy mniejsze przesuwane sa na lewo, a wieksze na prawo
            i++;
            swap(&arr[i], &arr[j]);
            comparisons++;
        }
    }
    swap(&arr[i + 1], &arr[high]);
    return (i + 1);
}

void quickSort(int arr[], int low, int high) {
    if (low < high){
        comparisons++;
        int pi = partition(arr, low, high);     //podzial listy na 2 osobne i wykonanie algorytmu dla kazdej z nich     
        quickSort(arr, low, pi - 1);
        quickSort(arr, pi + 1, high);
    }
}

void mquickSort(int arr[], int low, int high) {
    if (low < high){
        comparisons++;
        if(high < 16) {                     //jesli w dowolnym momencie elementow jest mniej niz 16 to uzywany jest insertsort zamiast quicksort
            insertionSort(arr, high+1);
        } else {
            int pi = partition(arr, low, high);            
            mquickSort(arr, low, pi - 1);
            mquickSort(arr, pi + 1, high);
        }
    }
}

int dppartition(int arr[], int low, int high, int* lp) {  
    if (arr[low] > arr[high]) {
        swap(&arr[low], &arr[high]);
        comparisons++;
    }
    int j = low + 1;  
    int g = high - 1, k = low + 1, p = arr[low], q = arr[high];  
    while (k <= g) {  
        if (arr[k] < p) {       //jesli elementy sa mniejsze niz lewy dzielnik
            comparisons++;
            swap(&arr[k], &arr[j]);  
            j++;  
        }   
        else if (arr[k] >= q) {     //jesli elementy sa wieksze rowne prawemu dzielnikowi
            comparisons++;
            while (arr[g] > q && k < g) {
                comparisons++;
                g--;  
            }
            swap(&arr[k], &arr[g]);  
            g--;  
            if (arr[k] < p) {  
                comparisons++;
                swap(&arr[k], &arr[j]);  
                j++;  
            }  
        }  
        k++;  
    }  
    j--;  
    g++;        //ustawienie nowej pozycji dzielnikow
    swap(&arr[low], &arr[j]);  
    swap(&arr[high], &arr[g]); 
    *lp = j;        //zwracania lewego i prawego dzielnika
    return g;  
}  

void dpquickSort(int arr[], int low, int high) {  
    if (low < high)  {
        comparisons++;
        int lp, rp;  
        rp = dppartition(arr, low, high, &lp);      //dzielenie listy na 3 czesci i sortowanie kazdej z nich
        dpquickSort(arr, low, lp - 1);  
        dpquickSort(arr, lp + 1, rp - 1);  
        dpquickSort(arr, rp + 1, high);  
    }  
}  

void statSort(string type, int k, string file) {
    ofstream f;     
    f.open(file);
    high_resolution_clock::time_point t1, t2;
    for(int n=100; n<=10000; n+=100) {
        int *arr = new int[n];
        for(int s=0; s<k; s++) {
            srand(time(NULL));
            for(int i=0; i < n; i++) {          //tworzenie losowej tablicy
                arr[i] = rand()%1000 + 1;
            }
            int *arrCopy = new int[n];

            swapCount = 0;
            comparisons = 0;
            copy(arr, arr+n, arrCopy);
            t1 = high_resolution_clock::now();      //sortowanie i mierzenie czasu
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
            f << n << " " << comparisons << " " << swapCount << " " << duration << endl;
        }
    }
    f.close();
}