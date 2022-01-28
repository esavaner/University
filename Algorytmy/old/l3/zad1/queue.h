#ifndef ZAD3_1_QUEUE_H
#define ZAD3_1_QUEUE_H

struct node {
    node *next;
    int prio;
    int value;
};

class queue {
private:
    node *head;
    node *tail;
public:
    queue();
    ~queue();
    bool empty(void);
    void pop(bool t);
    void top(void);
    void insert(int prio, int value);
    void priority(int prio, int value);
    void print(void);
};


#endif //ZAD3_1_QUEUE_H