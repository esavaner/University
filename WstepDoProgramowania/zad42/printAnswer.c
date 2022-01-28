#include <stdio.h>
#include "functions.h"

void printAnswer(int szereg[4]) 
{
    for (int i=0; i<4; i++) 
    {
        printf("[%d] ", szereg[i]);
    }
    printf("\n");
}
