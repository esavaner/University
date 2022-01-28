#include<stdio.h>
#include<stdbool.h>
#include<string.h>
#include "funs.h"

bool palindrom(char napis[])   
{
    int i=0; int j=strlen(napis)-1;
    while(i<j)
    {
        if(napis[i]!=napis[j]) 
        {
            return false;
        }
        i++;
        j--;
    }
    return true;
}
