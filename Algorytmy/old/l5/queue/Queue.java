package queue;

public class Queue<T> {

    private T[] Q;
    private int head = 0;
    private int tail = 0;
    private int length;
    private int size = 0;

    public Queue(T[] Q) {
        this.Q = Q;
        length = Q.length;
    }

    public void enqueue(T value) {
        size++;
        if (size > length) throw new QueueException("FULL");
        Q[tail] = value;
        if (tail == length)
            tail = 1;
        else
            tail++;
    }

    public T dequeue() {
        size--;
        if (size < 0) throw new QueueException("EMPTY");
        T x = Q[head];
        if (head == length)
            head = 1;
        else head++;
        return x;
    }

    public void clear() {
        head = 0;
        tail = 0;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}