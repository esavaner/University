#include <stdio.h>
#include <signal.h>
#include <unistd.h>

void sigcounter(int sig) {
    signal(SIGUSR1, sigcounter);
    if(sig == SIGKILL) {
        printf("Otrzymano sygnal");
    }
}
int main() {
    pid_t pid =1;
    if(signal(SIGKILL, sigcounter) == SIG_ERR) {
        printf("can't catch SIGKILL\n");
    }
    kill(pid, SIGKILL);
    while(1) {
        sleep(1);
    }
}
