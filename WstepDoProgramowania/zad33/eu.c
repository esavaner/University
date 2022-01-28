#include<stdio.h>
#include "funs.h"

int i=1,sumalp=0;
int phi(long int n)
{
    while(i<n)
    {
        int j=i;
        int m=n;
        while(j!=0)
        {
            int c=m%j;
            m=j;
            j=c;
        }
        if(m==1)
        {
            sumalp+=1;
        }
        i++;
    }
    return sumalp;
}
