#include <stdio.h>
#include <stdlib.h>
#include <time.h>

struct node {
    struct node *next;
    struct node *prev;
    int data;
};

void print(struct node *head) {
    struct node *temp = head;
    printf("Forward\n");
    while(temp->next != head) {
        printf("%d ", temp->data);
        temp = temp->next;
    }
    printf("%d ", temp->data);
    printf("\nReverse\n");
    struct node *last = head->prev;
    temp = last;
    while (temp->prev != last) {
        printf("%d ", temp->data);
        temp = temp->prev;
    }
    printf("%d ", temp->data);
    printf("\n");
}

void find(struct node *head, int id) {
    struct node *temp = head;
    while(temp->next != head) {
        if(temp->data == id) {
            printf("Found id: %d\n", id);
            return;
        }
        temp = temp->next;
    }
    printf("Didn't find id: %d\n", id);
}

void push(struct node **head, int data) {
    struct node *new = (struct node*)malloc(sizeof(struct node));
    struct node *last = (*head)->prev;
    new->data = data;
    new->next = *head;
    new->prev = last;
    last->next = (*head)->prev = new;
    *head = new;
}

void append(struct node **head, int data) {
    struct node *new = (struct node*)malloc(sizeof(struct node));
    new->data = data;
    if(!*head) {
        new->next = new->prev = new;
        *head = new;
        return;
    }
    struct node *last = (*head)->prev;
    new->next = *head;
    (*head)->prev = new;
    new->prev = last;
    last->next = new;
}

void delete(struct node **head, int id) {
    struct node *curr = *head, *prev = NULL;
    if(!*head) {
        return;
    }
    while(curr->data != id) {
        if(curr->next == *head) {           //empty
            printf("Nothing to delete\n");
            return;
        }
        prev = curr;
        curr = curr->next;
    }
    if(curr->next == *head && !prev) {      //one node
        (*head) = NULL;
        free(curr);
        return;
    }                                       //many nodes
    if(curr == *head) {                     //first node
        prev = (*head)->prev;
        *head = (*head)->next;
        prev->next = *head;
        (*head)->prev = prev;
        free(curr);
    } else if(curr->next == *head) {        //last node
        prev->next = *head;
        (*head)->prev = prev;
        free(curr);
    } else {                                //somewhere mid
        struct node *temp = curr->next;
        prev->next = temp;
        temp->prev = prev;
        free(curr);
    }
}

void merge(struct node **h1, struct node **h2) {
    struct node *end1 = (*h1)->prev;
    struct node *end2 = (*h2)->prev;
    end1->next = *h2;
    (*h2)->prev = end1;
    end2->next = *h1;
    (*h1)->prev = end2;
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