#define ERR 5000
#include <stdbool.h>
#include "functions.h"

int guess(bool dostepne[]) 
{
    for (int i=0; i<1296; i++)
    {
        if (dostepne[i])
        {
            return i;
        }
    }
    return ERR;
}
