#include <stdio.h>
#include <stdlib.h>
#include <time.h>

int x=0;
int mat1[5000][5000];
int mat2[5000][5000];
int mat3[5000][5000];
int transp[5000][5000];
char cx[250];

void transpose() {
    for(int i=0; i<x; i++) {
        for(int j=0; j<x; j++) {
            transp[j][i] = mat2[i][j];
        }
    }
    for(int i=0; i<x; i++) {
        for(int j=0; j<x; j++) {
            mat2[i][j] = transp[i][j];
        }
    }
    for(int i=0; i<x; i++) {
        for(int j=0; j<x; j++) {
            mat3[i][j] = '\0';
        }
    }
}

void multi() {
    for(int i=0; i<x; i++) {
        for(int j=0; j<x; j++) {
            for(int k=0; k<x; k++) {
                mat3[i][j] += mat1[i][k] * mat2[k][j];
            }
        }
    }
}
void multit() {
    for(int i=0; i<x; i++) {
        for(int j=0; j<x; j++) {
            for(int k=0; k<x; k++) {
                mat3[i][j] += mat1[i][k] * mat2[j][k];
            }
        }
    }
}
int getRand() {
    double r = (double)rand()/(double)RAND_MAX*10;
    int s = (int)r;
    return s;
}

int main() {
    printf("Podaj wielkosc macierzy: ");
    fgets(cx, 250, stdin);
    x=atoi(cx);
    for(int i=0; i<x; i++) {
        for(int j=0; j<x; j++) {
            mat1[i][j] = getRand();
            mat2[i][j] = getRand();
        }
    }
    /*
    printf("Macierz A\n");
    for(int i=0; i<x; i++) {
        for(int j=0; j<x; j++) {
            printf(" %i", mat1[i][j]);
        }
        printf("\n");
    }
    printf("\nMacierz B\n");
    for(int i=0; i<x; i++) {
        for(int j=0; j<x; j++) {
            printf(" %i", mat2[i][j]);
        }
        printf("\n");
    }
    */
    clock_t begin = clock();
    multi();
    clock_t end = clock();
    double time =(double)(end-begin) / CLOCKS_PER_SEC;
    printf("Czas: %f\n", time);
    transpose();
    clock_t begint = clock();
    multit();
    clock_t endt = clock();
    double timet =(double)(endt-begint) / CLOCKS_PER_SEC;
    printf("Czas po transpozycji: %f\n", timet);
}
