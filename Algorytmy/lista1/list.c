#include <stdio.h>
#include <stdlib.h>
#include <time.h>

struct node {
    struct node *next;
    int data;
};

void print(struct node *head) {
    while(head) {
        printf("%d ", head->data);
        head = head->next;
    }
    printf("\n");
}

void find(struct node *head, int id) {
    while(head) {
        if(head->data == id) {
            printf("Found id: %d\n", id);
            return;
        }
        head = head->next;
    }
    printf("Didn't find id: %d\n", id);
}

void push(struct node **head, int data) {
    struct node *new = (struct node*)malloc(sizeof(struct node));
    new->data = data;
    new->next = *head;
    *head = new;
}

void append(struct node **head, int data) {
    struct node *new = (struct node*)malloc(sizeof(struct node));
    new->data = data;
    new->next = NULL;
    if(!*head) {
        *head = new;
        return;
    }
    struct node *copy = *head;
    while(copy->next) {
        copy = copy->next;
    }
    copy->next = new;
}

void delete(struct node **head, int id) {
    struct node *temp = *head, *prev;
    if(temp && temp->data == id) {
        *head = temp->next;
        free(temp);
        return;
    }
    while(temp && temp->data != id) {
        prev = temp;
        temp = temp->next;
    }
    if(!temp) {
        return;
    }
    prev->next = temp->next;
    free(temp);
}

void merge(struct node **h1, struct node **h2) {
    struct node *temp = *h1;
    while(temp->next) {
        temp = temp->next;
    }
    temp->next = *h2;
}

int main() {
    struct node *head = NULL;
    append(&head, 4);
    append(&head, 12);
    append(&head, -1);
    append(&head, 0);

    print(head);

    struct node *head2 = NULL;
    append(&head2, -9);
    append(&head2, 2);
    append(&head2, 32);
    append(&head2, 11);

    print(head2);

    merge(&head, &head2);

    print(head);

    struct node *h3 = NULL;
    srand(time(NULL));
    for(int i=0; i<1000; i++) {
        int r = rand()%1000;
        append(&h3, r);
    }
    print(h3);

    clock_t start, end;
    double cpu_time_used = 0;
    for(int i=0; i<1000; i++) {
        int r = rand()%1000;
        start = clock();
        find(h3, r);
        end = clock();
        cpu_time_used += ((double)(end - start))/CLOCKS_PER_SEC;
    }
    printf("Average time: %f\n", cpu_time_used/1000);
}