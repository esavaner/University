#include <stdio.h>
#include <stdlib.h>


struct fifo {
    int start, end, size;
    unsigned maxsize;
    int *array;
};


struct fifo *create(unsigned size) {
    struct fifo* queue = (struct fifo*)malloc(sizeof(struct fifo));
    queue->maxsize = size;
    queue->start = queue->size = 0;
    queue->end = size - 1;
    queue->array = (int*)malloc(queue->maxsize * sizeof(int));
    return queue;
}

int isFull(struct fifo *queue) {
    return queue->size == queue->maxsize;
}

int isEmpty(struct fifo *queue) {
    return queue->size == 0;
}

void enqueue(struct fifo *queue, int item) {
    if(isFull(queue)) {
        return;
    }
    queue->end = (queue->end + 1)%queue->maxsize;
    queue->array[queue->end] = item;
    queue->size++;
    printf("%d added\n", item);
}

int dequeue(struct fifo *queue) {
    if(isEmpty(queue)) {
        return NULL;
    }
    int item = queue->array[queue->start];
    queue->start = (queue->start + 1)%queue->maxsize;
    queue->size--;
    printf("%d removed\n", item);
    return item;
}

int start(struct fifo *queue) {
    if(isEmpty(queue)) {
        return NULL;
    }
    return queue->array[queue->start];
}

int end(struct fifo *queue) {
    if(isEmpty(queue)) {
        return NULL;
    }
    return queue->array[queue->end];
}

int main() {
    struct fifo *queue = create(100);

    enqueue(queue, 7);
    enqueue(queue, 12);
    enqueue(queue, 2);
    enqueue(queue, -6);
    enqueue(queue, 0);

    printf("Start: %d\n", start(queue));
    printf("End: %d\n", end(queue));
}