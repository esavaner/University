#include "functions.h"

int pozycja(int szereg1[4], int szereg2[4])
{
    int licznik = 0;
    for(int i=0; i<4; i++)
    {
        if(szereg1[i] == szereg2[i]) 
        {
            licznik++;
        }
    }
    return licznik;
}
