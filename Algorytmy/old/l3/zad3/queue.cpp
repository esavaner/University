#include "queue.h"
#include <iostream>

queue::queue(int n) {
    heap = new edge [n];
    heappos = 0;
}

queue::~queue() {
    delete [] heap;
}

edge queue::front() {
    return heap[0];
}

void queue::insert(edge e) {
    int i,j;
    i = heappos++;
    j = (i - 1) >> 1;
    while(i && (heap[j].w > e.w)) {
        heap[i] = heap[j];
        i = j;
        j = (i - 1) >> 1;
    }
    heap[i] = e;
}

void queue::pop() {
    int i=0,j=1;
    edge e;
    if(heappos) {
        e = heap[heappos--];
        while(j < heappos) {
            if((j + 1 < heappos) && (heap[j + 1].w < heap[j].w)) j++;
            if(e.w <= heap[j].w) break;
            heap[i] = heap[j];
            i = j;
            j = (j << 1) + 1;
        }
        heap[i] = e;
    }
}
