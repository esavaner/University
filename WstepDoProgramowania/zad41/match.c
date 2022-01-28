#include <stdbool.h>
#include "match.h"

bool match(char *wzorzec, char *lancuch) 
{
    if(lancuch[0]=='\0' && wzorzec[0]=='\0')
    {
        return true;
    }
    if(wzorzec[0]=='*')
    {
        if(match(wzorzec+1, lancuch))
        {
            return true;
        }
        if(lancuch[0] != '\0')
        {
            return match(wzorzec, lancuch+1);
        }
    }
    if(wzorzec[0] != lancuch[0] && wzorzec[0] != '?') 
    {
        return false;
    }
    return match(wzorzec+1, lancuch+1);
}
