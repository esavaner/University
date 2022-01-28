#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <stdbool.h>

typedef struct node {
    int value;
    struct node *next;
} node_t;

void delete(int val, node_t **head) {
    node_t *current = *head;
    node_t *prev;
    if(current != NULL && current->value == val) {
        *head = current->next;
        free(current);
        return;
    }
    while(current != NULL && current->value != val) {
        prev = current;
        current = current->next;
    }
    if(current == NULL)
        return;
    prev->next = current->next;
    free(current);
}

void insertBeg(int val, node_t **head) {
    node_t *current = malloc(sizeof(node_t));
    current->value = val;
    if(*head != NULL) {
        current->next = *head;
    }
    *head = current;
}

void printAll(node_t *head) {
    printf("[");
    node_t *current = head;
    while(current != NULL) {
        printf("%i,", current->value);
        current = current->next;
    }
    printf("]\n");
}

int findMTF(int val, node_t *head, node_t **head2) {
    node_t *current = head;
    while(current) {
        if(current->value == val) {
            delete(val, &head);
            insertBeg(val, head2);
            return 1;
        }
        current = current->next;
    }
    return 0;
}

int findTRANS(int val, node_t *head, node_t **head2) {
    node_t *current = head;
    node_t *current2 = head;
    node_t *prev, *prev2;
    if(current && current->value == val)
        return 1;
    else if(current && current->next->value == val) {
        delete(val, &head);
        insertBeg(val, head2);
        return 1;
    }
    else {
        while(current && current->value != val) {
            prev = current;
            current = current->next;
        }
        while(current2 && current2->value != prev->value) {
            prev2 = current2;
            current2 = current2->next;
        }
        prev2->next = prev->next;
        prev->next = current->next;
        current->next = prev;
        return 1;
    }
    return 0;
}

void testTrans() {
    node_t *head = NULL;
    int n=100;
    bool arr[100] ={0};
    time_t t;
    srand((unsigned)time(&t));
    for(int i=0; i<n; i++) {
        int r = rand()%100;
        if(!arr[r]) 
            insertBeg(r, &head);
        else
            i--;
        arr[r]=1;
    }
    printAll(head);
    int max = 0;
    int counter=0;
    while(head) {
        counter++;
        for(int i=0; i<100; i++) {
            counter++;
            if(findTRANS(i, head, &head) == 1) {
                counter++;
                max = i;
            }
        }
        printf("max %i\n", max);
        delete(max, &head);
        max =0;
    }
    printf("Trans: %i\n", counter);
    printAll(head);
    free(head);
}

void testMTF() {
    node_t *head = NULL;
    int n=100;
    bool arr[100] ={0};
    time_t t;
    srand((unsigned)time(&t));
    for(int i=0; i<n; i++) {
        int r = rand()%100;
        if(!arr[r]) 
            insertBeg(r, &head);
        else
            i--;
        arr[r]=1;
    }
    printAll(head);
    int max = 0;
    int counter=0;
    while(head) {
        counter++;
        for(int i=0; i<100; i++) {
            counter++;
            if(findMTF(i, head, &head) == 1) {
                counter++;
                max = i;
            }
        }
        printf("max %i\n", max);
        delete(max, &head);
        max =0;
    }
    printf("MTF: %i\n", counter);
    printAll(head);
    free(head);
}
int main() {
    testMTF();
    testTrans();
}

void insertEnd(int val, node_t *head) {
    node_t *current = head;
    while(current->next != NULL) {
        current = current->next;
    }
    current->next = malloc(sizeof(node_t));
    current->next->next = NULL;
    current->next->value = val;
}