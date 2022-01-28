#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <pthread.h>


int x=0, t=0, row=0;
int mat1[255][255];
int mat2[255][255];
int mat3[255][255];
char cx[250], ct[250];

int and(int a, int b) {
    if(a == 1 && b ==1) {
        return 1;
    }
    else return 0;
}

int or(int a, int b) {
    if(a == 0 && b == 0) {
        return 0;
    }
    else return 1;
}

void multi() {
    int oldrow = row;
    if(oldrow < x) {
        row++;
        for(int j=0; j<x; j++) {
            for(int k=0; k<x; k++) {
                int c = mat3[oldrow][j];
                if(c == '\0') {
                    mat3[oldrow][j] = and(mat1[oldrow][k], mat2[k][j]);
                }
                else {
                    or(mat3[oldrow][j], and(mat1[oldrow][k], mat2[k][j]));
                }
            }
        }
        multi();
    }
}

void *multiTh(void* arg) {
    multi();
}
int getRand() {
    if((double)rand()/(double)RAND_MAX > 0.5) {
        return 1;
    }
    else return 0;
}
int getTh() {
    printf("Podaj ilosc watkow: ");
    fgets(ct, 250, stdin);
    t = atoi(ct);
    if(t > atoi(cx)) {
        printf("Za duza ilosc watkow\n");
        getTh();
    }
    else return t;
}

int main() {
    printf("Podaj wielkosc macierzy: ");
    fgets(cx, 250, stdin);
    getTh();
    x=atoi(cx);
    for(int i=0; i<x; i++) {
        for(int j=0; j<x; j++) {
            mat1[i][j] = getRand();
            mat2[i][j] = getRand();
        }
    }
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
    pthread_t threads[t];
    for(int u=0; u<t; u++) {
        int* p;
        pthread_create(&threads[u], NULL, multiTh, (void*)(p));    
    }
    for(int u=0; u<t; u++) {
        pthread_join(threads[u], NULL);
    }
    printf("\nWynik mnozenia\n");
    for(int i=0; i<x; i++) {
        for(int j=0; j<x; j++) {
            printf(" %i", mat3[i][j]);
        }
        printf("\n");
    }
}
