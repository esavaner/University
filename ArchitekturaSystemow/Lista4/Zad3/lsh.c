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
int dopipe(char **args1, char **args2);
void lshrun(char **args);
int check(char **args, int j);
void startlsh();
int ex(char **args);
int splitline(char *line, char **args);


int main(int argc, char *argv[]) {
    printf("Lsh shell started\n");
    startlsh();
}

int splitline(char *line, char **args) {
    int i =0;
    int n=-1;
    for(i=0; i< 100; i++) {
        args[i] = strsep(&line, " \n\t\r\a");
        n++;
        if(args[i] == NULL) {
            break;
        }
    }
    return (n);
}
void lshrun(char **args) {
    int status;
    pid_t pid = fork();
    if(pid == 0) {
        if(execvp(args[0], args) == -1) {
            perror("Error1");
        }
        exit(EXIT_FAILURE);
    }
    else if(pid < 0) {
        perror("Error2");
    }
    else {
        waitpid(pid, &status, 0);
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

int check(char **args, int j) {
    char *args1[100];
    char *args2[100];
    int k=0;
    while(args[k] != NULL) {
        if(strcmp(args[k], "|") == 0) {
            int i=0;
            while(i != k) {
                args1[i] = args[i];
                i++;
            } 
            int n=0;
            for(int m=k+1; m<j; m++) {
                args2[n] = args[m];
                n++;
                m++;
            }
            args2[n+1] = NULL;
            dopipe(args1, args2);
            return 0; 
        }
        k++;
    }
    ex(args);
    return 0;
}

int dopipe(char **args1, char **args2) {
    int fds[2];
    pipe(fds);
    pid_t pid = fork();
    if(pid == -1) {
        perror("Error3");
        return 1;
    }
    else if(pid == 0) {
        close(fds[1]);
        dup2(fds[0], 0);
        if(execvp(args2[0], args2) == -1) {
            perror("Error6");
        }
        return 0;
    }
    else {
        close(fds[0]);
        dup2(fds[1], 1);
        if(execvp(args1[0], args1) == -1) {
            perror("Error6");
        }
        return 0;
    }
}

void startlsh() {
    while(true) {
        char *args[100];
        char cwd[256];
        getcwd(cwd, sizeof(cwd));
        printf("lsh> %s $ ", cwd);
        char line[100];
        if(fgets(line, sizeof(line), stdin) == NULL) {
            printf("\nLsh closed\n");
            break;
        }
        int j= splitline(line, args);
        check(args, j);
        if(shellexit == true) {
            printf("Lsh closed\n");
            break;
        }
    }
}

void pexit(char **args) {
    shellexit = true;
}

void pcd(char **args) {
    if(chdir(args[1]) != 0) {
        perror("Error4");
    }
    startlsh();
}
