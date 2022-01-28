#include "queue.h"
#include <iostream>

using namespace std;

queue::queue() {
    head = tail = nullptr;
}

queue::~queue() {
    while(head) pop(false);
}

bool queue::empty() {
    return !head;
}

node* queue::read(int i) {
    node *p = head;
    while(p) {
        if(p->value == i) {
            break;
        }
        p = p->next;
    }
    return p;
}

node* queue::top() {
    if(empty())
        return nullptr;
    else
        return head;
}

node* queue::pop(bool t) {
    if(!empty()) {
        if (t)
            cout << "Priority: " << head->prio << " Value: " << head->value << endl;
        node *p = head;
        head = head->next;
        if(!head) {
            tail = nullptr;
        }
        return p;
    }
    else {
        cout << "Empty" << endl;
        return nullptr;
    }
}

void queue::insert(int value, int prio) {
    node *p, *r, *s;
    p = new node;
    p->next = nullptr;
    p->prio = prio;
    p->value = value;
    if(head) {
        s = head;
        while (s) {
            if (s->value == value) {
                return;
            }
            s = s->next;
        }
    }
    if(!head) {
        head = tail = p;
    }
    else if(head->prio > prio) {
        p->next = head;
        head = p;
    }
    else {
        r = head;
        while((r->next) && (r->next->prio <= prio))
            r = r->next;
        p->next = r->next;
        r->next = p;
        if(!p->next)
            tail = p;
    }
}

void queue::priority(int value, int prio) {
    node *prev = head, *p, *r = head, *s = head;
    if(!head) {
        return;
    }
    while(s) {
        p = s;
        if(p != tail) {
            if (p->prio < prio) {
                r = p;
            }
        }
        if(p->value == value && p->prio > prio) {
            if(head->prio > prio  && p != head) {
                if(p == tail)
                    tail = prev;
                prev->next = p->next;
                p->next = head;
                head = p;
            } else if(prev->prio > prio && p != head) {
                if(p == tail)
                    tail = prev;
                prev->next = p->next;
                p->next = r->next;
                r->next = p;
            }
            p->prio = prio;
        }
        if(s == tail)
            break;
        prev = s;
        s = s->next;
    }
}

void queue::print() {
    if(!head)
        return;
    node *p = head;
    while(p->next || p == tail) {
        cout << "(" << p->value << ", " << p->prio << ") ";
        if(p != tail)
            p = p->next;
        else
            break;
    }
    cout << endl;
}
