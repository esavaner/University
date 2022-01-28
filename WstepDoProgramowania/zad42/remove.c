#include <stdbool.h>
#include "functions.h"

void removeAns(int czarny, int bialy, int possibleAnswers[1296][4], bool dostepne[1296], int odpowiedz[4])
{
    for (int i=0; i<1296; i++) 
    {
        if (dostepne[i] == true && pozycja(&(possibleAnswers[i][0]), odpowiedz) != czarny)
        {
            dostepne[i] = false;
        }
        if (dostepne[i] == true && match(&(possibleAnswers[i][0]), odpowiedz) != czarny+bialy)
        {
        dostepne[i] = false;
        }
    }
}
