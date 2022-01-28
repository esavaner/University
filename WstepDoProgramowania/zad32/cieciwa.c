#include<stdio.h>
#include<stdlib.h>
#include<math.h>
#include "funs.h"

double cieciwa(double a, double b,double e)
{
    double bezp=0;
    int ll=0;
    double c=(fabs(f(a))*b+fabs(f(b))*a)/(fabs(f(b))+fabs(f(a)));
    while(fabs(f(c))>e && bezp<100)
    {
        c=(fabs(f(a))*b+fabs(f(b))*a)/(fabs(f(b))+fabs(f(a)));
        if(f(c)*f(b)<0)
            a=c;
        else
            b=c;
        bezp=bezp+1;
        ll+=1;
    }
    printf("Liczba iteracji=%i, ",ll);
    return c;
}
