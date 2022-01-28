#include "functions.h"

int match(int szereg1[4], int szereg2[4])
{
    int licznik1 = 0;
    for (int i=0; i<4; i++) 
    {
        for (int j=0; j<4; j++)
        {
            if (szereg1[i] == szereg2[j]) 
            {
                licznik1++;
                break;
            }
        }
    }
    int licznik2 = 0;
    for (int i=0; i<4; i++)
    {
        for (int j=0; j<4; j++)
        {
            if (szereg1[j] == szereg2[i]) 
            {
                licznik2++;
                break;
            }
        }
    }
    if (licznik1 <= licznik2)
    {
        return licznik1;
    }
    else 
    {
        return licznik2;
    }
}
