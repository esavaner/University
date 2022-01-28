#include <stdio.h>
#include <stdbool.h>
#include "match.h"

int main() 
{
    char wzorzec[1000];
    char lancuch[1000];
    printf("Podaj wzorzec:\n");
    fgets(wzorzec, sizeof(wzorzec), stdin);
    printf("Podaj ciąg znaków:\n");
    fgets(lancuch, sizeof(lancuch), stdin);
    match(wzorzec, lancuch);
    if(match(wzorzec, lancuch)==true)
    {
        printf("Ciągi są takie same\n");
    }
    else
    {
        printf("Ciągi różnią się\n");
    }
    return 0;
}
