#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <signal.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <pthread.h>
volatile sig_atomic_t flag = 0;
int sfd = 0;
char nick[100] = {};
/*
void catch_ctrl_c_and_exit(int sig) {
    flag = 1;
}

void recv_msg_handler() {
    char receiveMessage[LENGTH_SEND] = {};
    while (1) {
        int receive = recv(sockfd, receiveMessage, LENGTH_SEND, 0);
        if (receive > 0) {
            printf("\r%s\n", receiveMessage);
            str_overwrite_stdout();
        } else if (receive == 0) {
            break;
        } else { 
            // -1 
        }
    }
}

void send_msg_handler() {
    char message[LENGTH_MSG] = {};
    while (1) {
        str_overwrite_stdout();
        while (fgets(message, LENGTH_MSG, stdin) != NULL) {
            str_trim_lf(message, LENGTH_MSG);
            if (strlen(message) == 0) {
                str_overwrite_stdout();
            } else {
                break;
            }
        }
        send(sockfd, message, LENGTH_MSG, 0);
        if (strcmp(message, "exit") == 0) {
            break;
        }
    }
    catch_ctrl_c_and_exit(2);
}
*/
int main()
{
    printf("Podaj nick: ");
    while(1) {
        if (fgets(nick, 100, stdin) == NULL) {
            printf("Nick nie moze byc pusty, podaj ponownie\n");
        }
        else break;
    }
    sfd = socket(AF_INET , SOCK_STREAM , 0);
    if (sfd == -1) {
        printf("Error\n");
        exit(EXIT_FAILURE);
    }
    struct sockaddr_in serv, client;
    serv.sin_addr.s_addr = inet_addr("127.0.0.1");
    serv.sin_family = AF_INET;
    serv.sin_port = htons(8901);

    if (connect(sfd, (struct sockaddr *)&serv, sizeof(serv)) == -1) {
        printf("Nie mozna polaczyc\n");
        exit(EXIT_FAILURE);
    }
    else printf("Dolaczono\n");
    send(sfd, nick, 100, 0);
/*
    pthread_t send_msg_thread;
    if (pthread_create(&send_msg_thread, NULL, (void *) send_msg_handler, NULL) != 0) {
        printf ("Create pthread error!\n");
        exit(EXIT_FAILURE);
    }

    pthread_t recv_msg_thread;
    if (pthread_create(&recv_msg_thread, NULL, (void *) recv_msg_handler, NULL) != 0) {
        printf ("Create pthread error!\n");
        exit(EXIT_FAILURE);
    }

    while (1) {
        if(flag) {
            printf("\nBye\n");
            break;
        }
    }
*/
    close(sfd);
    return 0;
}
