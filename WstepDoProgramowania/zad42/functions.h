#include <stdbool.h>

// algorytm "zgadujący":
int guess(bool available[]);
// funkcja wypisująca odpowiedź:
void printAnswer(int szereg[4]);
// funkcja usuwająca odpowiedzi z possibleAnswers
void removeAns(int czarny, int bialy, int possibleAnswers[1296][4], bool dostepne[1296], int odpowiedz[4]);
// funkcja sprawdzająca ilość elementów o pasującym kolorze
int match (int szereg1[4], int szereg2[4]);
// funkcja sprawdzająca ilość elementów o tym samym kolorze na tej smaej pozycji
int pozycja(int szereg1[4], int szereg2[4]);
