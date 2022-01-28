#include<stdio.h>
#include "funs.h"

int n;
int main()
{
    printf("Podaj liczbę n:");
    scanf("%i", &n);
    printf("%i\n", phi(n));
}

