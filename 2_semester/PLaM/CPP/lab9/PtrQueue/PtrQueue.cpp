template<typename T>
class PtrQueue {
private:
    T **data;
    int head, tail, max_size;
public:
    PtrQueue(int size) {
        data = new T*[size];
        max_size = size;
        head = 0;
        tail = 0;
    }
    
    bool empty() {
        return head == tail;
    }

    friend PtrQueue<T>& operator<<(PtrQueue<T> &queue, T *ptr) {
        queue.data[queue.tail] = ptr;
        queue.tail = (queue.tail + 1) % queue.max_size;
        return queue;
    }

    friend T* operator!(PtrQueue<T> &queue) {
        T *ptr = queue.data[queue.head];
        queue.head = (queue.head + 1) % queue.max_size;
        return ptr;
    }

    T* operator*() {
        if (empty()) {
            return 0;
        }
        return data[head];
    }

    T* operator->() {
        if (empty()) {
            return 0;
        }
        return data[head];
    }
};