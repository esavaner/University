#ifndef ZAD3_2_QUEUE_H
#define ZAD3_2_QUEUE_H

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
    node* pop(bool t);
    node* read(int i);
    node* top();
    void insert(int value, int prio);
    void priority(int value, int prio);
    void print(void);
};

#endif //ZAD3_2_QUEUE_H