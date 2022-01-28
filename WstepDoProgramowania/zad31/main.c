#include<stdio.h>
#include<stdbool.h>
#include "funs.h"

int main()
{
    char napis[255];
    printf("Podaj wyraz:");
    scanf("%s", napis);
    if(palindrom(napis)==true)
    {
        printf("To słowo jest palindromem\n");
    }
    else
    {    
        printf("To słowo nie jest palindromem\n");
    }
}   
