#include<stdio.h>
#include<assert.h>
#include<math.h>
#include "funs.h"

double k=1;
double a=2; 
double b=4;
double e;
int ll;
int main(void)
{
    assert(f(a)*f(b)<0);
    while(k<9)
    {
        e=pow(10,-k);
        double c=cieciwa(a,b,e);
        printf("Eps=%f, x=%f\n",e,c);
        k++;
    }
    return 0;
}
