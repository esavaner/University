#include "queue.h"
#include <iostream>

using namespace std;

queue::queue() {
    head = tail = NULL;
}

queue::~queue() {
    while(head) pop(false);
}

bool queue::empty() {
    return !head;
}

void queue::top() {
    if(empty())
        cout << "Empty" << endl;
    else
        cout << "Priority: " << head->prio << " Value: " << head->value << endl;
}

void queue::pop(bool t) {
    if(!empty()) {
        if (t)
            cout << "Priority: " << head->prio << " Value: " << head->value << endl;
        node *p = head;
        head = head->next;
        if(!head)
            tail = NULL;
        delete p;
    }
    else
        cout << "Empty" << endl;
}

void queue::insert(int value, int prio) {
    node *p, *r;
    p = new node;
    p->next = NULL;
    p->prio = prio;
    p->value = value;

    if(!head)
        head = tail = p;
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
    node *p = head;
    if(!p) {
        cout << "Empty" << endl;
        return;
    }
    while(p->next || p == tail) {
        cout << "(" << p->value << ", " << p->prio << ") ";
        if(p != tail)
            p = p->next;
        else
            break;
    }
    cout << endl;
}