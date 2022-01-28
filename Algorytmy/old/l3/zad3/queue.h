#ifndef ZAD3_3_QUEUE_H
#define ZAD3_3_QUEUE_H

struct edge {
    int w;
    int v1, v2;
};

class queue {
private:
    edge *heap;
    int heappos;
public:
    queue(int n);
    ~queue();
    void pop();
    void insert(edge e);
    edge front();
};


#endif //ZAD3_3_QUEUE_H