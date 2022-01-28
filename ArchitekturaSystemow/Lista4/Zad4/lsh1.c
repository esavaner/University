#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>
#include <stdbool.h>
#include <string.h>
#define LSH_TOK_BUFSIZE 64
#define DELIM "\n\t "

bool shellexit = false;
void pcd(char **args);
void pexit(char **args);
char *func[] = {"cd", "exit"};
void (*wfunc[])(char **) = {&pcd, &pexit};

char **splitter(char *args) {
    int size = LSH_TOK_BUFSIZE, position = 0;
    char **lshsplit = malloc(size * sizeof(char*));
    char *lshs;
    if(!lshsplit) {
        exit(EXIT_FAILURE);
    }
    lshs = strtok(args, DELIM);
    while(lshs != NULL) {
        lshsplit[position] = lshs;
        position++;
        if(position >+ size) {
            size+= LSH_TOK_BUFSIZE;
            lshsplit = realloc(lshsplit, size * sizeof(char*));
            if(!lshsplit) {
                exit(EXIT_FAILURE);
            }
        }
        lshs = strtok(NULL, DELIM);
    }
    lshsplit[position] = NULL;
    return lshsplit;
}

void lshrun(char **args) {
    pid_t pid, wpid;
    int status;
    pid = fork();
    if(pid == 0) {
        if(execvp(args[0], args) == -1) {
            perror("Error");
        }
        exit(EXIT_FAILURE);
    }
    else if(pid < 0) {
        perror("Error");
    }
    else {
        do {
            wpid = waitpid(pid, &status, WUNTRACED);
        }
        while (!WIFEXITED(status) && !WIFSIGNALED(status));
    }
}

int ex(char **args) {
    for(int i=0; i<2; i++) {
        if(strcmp(args[0], func[i]) == 0) {
            (*wfunc[i])(args);
            return 0;
        }
    }
    lshrun(args);
}

void startlsh() {
    char **args;
    char cwd[256];
    getcwd(cwd, sizeof(cwd));
    printf("lsh> %s $ ", cwd);
    char *line = NULL;
    ssize_t size = 0;
    getline(&line, &size, stdin);
    args = splitter(line);
    ex(args);
    if(shellexit == false) {
        startlsh();
    }
    else {
        printf("Lsh closed\n");
    }
}

int main(int argc, char *argv[]) {
    printf("Lsh shell started\n");
    startlsh();
}

void pexit(char **args) {
    shellexit = true;
}

void pcd(char **args) {
    if(chdir(args[1]) != 0) {
        perror("Error");
    }
    startlsh();
}
