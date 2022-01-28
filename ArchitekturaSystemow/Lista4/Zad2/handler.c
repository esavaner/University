#include <stdio.h>
#include <signal.h>
#include <unistd.h>

void sighandler(int sig) {
    printf("Signal catched %d\n", sig);
}

int main() {
    for(int i=1; i < 60; i++) {
        signal(i, sighandler);
    }
    for(int j=1; j < 60; j++) {
        printf("Sent %d signal\n", j);
        kill(getpid(), j);
        sleep(1);
    }
    return 0;
}

