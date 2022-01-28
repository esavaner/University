#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>
#include <grp.h>

int main(int argc, char** argv) {
    char *name[2];
    name[0] = "bash";
    name[1] = NULL;
    setuid(0);
    execvp("/bin/bash", name);
    return 0;
}
