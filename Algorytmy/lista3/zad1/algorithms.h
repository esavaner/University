#ifndef TEST_ALGORITHMS_H
#define TEST_ALGORITHMS_H

using namespace std;

void swap(int *x, int *y);
void insertionSort(int arr[], int n);
void mergeSort(int arr[], int l,int r);
void quickSort(int arr[], int low, int high);
void mquickSort(int arr[], int low, int high);
void dpquickSort(int arr[], int low, int high);
void statSort(string type, int k, string file);

//uzupelnienie
/******************************/
void radixSort(int arr[], int n);
/******************************/

#endif
