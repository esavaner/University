#include <stdio.h>
#include <signal.h>
#include <unistd.h>

int counter = 0;
void sigcounter(int sig) {
    signal(SIGUSR1, sigcounter);
    if(sig == SIGINT) {
        counter++;
        printf("%i\n", counter);
    }
    counter++;
}
int main() {
    for(int i=0; i < 10; i++) {
        if(signal(SIGINT, sigcounter) == SIG_ERR) {
            printf("can't catch SIGINT\n");
        }
    }
    while(1) {
        sleep(1);
    }
}
