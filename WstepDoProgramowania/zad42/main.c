#define ERR 5000
#include <stdio.h>
#include <stdbool.h>
#include "functions.h"

int main() 
{
    int possibleAnswers[1296][4];
    int combNum = 0;
    for (int i=1; i<7; i++) {
        for (int j=1; j<7; j++) {
            for (int k=1; k<7; k++) {
                for (int l=1; l<7; l++) {
                    possibleAnswers[combNum][0] = i;
                    possibleAnswers[combNum][1] = j;
                    possibleAnswers[combNum][2] = k;
                    possibleAnswers[combNum][3] = l;
                    combNum++;
                }
            }
        }
    }
    bool dostepne[1296];
    for (int i=0; i<1296; i++) 
    {
        dostepne[i] = true;
    }
    int bialy = 0;
    int czarny = 0;
    int i = 1;
    int index = 0;
    while (true) 
    {
        printf("%d. ", i);
        index = guess(dostepne);
        if (index != ERR)
        {
            printAnswer(&(possibleAnswers[index][0]));
        }
        else 
        {
            printf("You cheat!\n");
            break;
        }
        printf("black: ");
        scanf("%d", &czarny);
        if (czarny == 4)
        {
            printf("I win\n");
            break;
        }
        printf("white: ");
        scanf("%d", &bialy);
        if (czarny+bialy > 4)
        {
            printf("black+white must be less or equal 4!\n");
            continue;
        }
        removeAns(czarny, bialy, possibleAnswers, dostepne, &(possibleAnswers[index][0]));
        if (i == 10) 
        {
            printf("You win\n");
            break;
        }
        i++;
    }
    return 0;
}
